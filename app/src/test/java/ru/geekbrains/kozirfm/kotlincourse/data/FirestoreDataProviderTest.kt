package ru.geekbrains.kozirfm.kotlincourse.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.data.errors.NoAuthException
import ru.geekbrains.kozirfm.kotlincourse.data.model.NoteResult
import ru.geekbrains.kozirfm.kotlincourse.data.providers.FirestoreDataProvider

class FirestoreDataProviderTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockDb = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()
    private val mockResultCollection = mockk<CollectionReference>()
    private val mockUser = mockk<FirebaseUser>()

    private val mockDocument1 = mockk<DocumentSnapshot>()
    private val mockDocument2 = mockk<DocumentSnapshot>()
    private val mockDocument3 = mockk<DocumentSnapshot>()

    private val testNotes = listOf(Note("1", "1"), Note("2"), Note("3"))
    private val provider = FirestoreDataProvider(mockAuth, mockDb)

    @Before
    fun setup() {
        clearAllMocks()
        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""
        every { mockDb.collection(any()).document(any()).collection(any()) } returns mockResultCollection
        every { mockDocument1.toObject(Note::class.java) } returns testNotes[0]
        every { mockDocument2.toObject(Note::class.java) } returns testNotes[1]
        every { mockDocument3.toObject(Note::class.java) } returns testNotes[2]

    }

    @Test
    fun `should throw NoAuthException if no auth`() {
        every { mockAuth.currentUser } returns null
        var result: Any? = null
        provider.getNotes().observeForever {
            result = (it as NoteResult.Error).error
        }
        assertTrue(result is NoAuthException)
    }

    @Test
    fun `saveNote calls set`() {
        val mockDocumentReference = mockk<DocumentReference>()
        every { mockResultCollection.document() } returns mockDocumentReference
        provider.saveNote(testNotes[0])
        verify(exactly = 1) { mockDocumentReference.set(testNotes[0]) }
    }

    @Test
    fun `getNotes returns notes`() {
        var result: List<Note>? = null
        val mockShapshot = mockk<QuerySnapshot>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockShapshot.documents } returns listOf(mockDocument1, mockDocument2, mockDocument3)
        every { mockResultCollection.orderBy("lastChanged").addSnapshotListener(capture(slot)) } returns mockk()

        provider.getNotes().observeForever {
            result = (it as? NoteResult.Success<List<Note>>)?.data
        }
        slot.captured.onEvent(mockShapshot, null)
        assertEquals(result, testNotes)
    }

    @Test
    fun `getNotes returns error`() {
        var result: Throwable? = null
        val mockException = mockk<FirebaseFirestoreException>()
        val slot = slot<EventListener<QuerySnapshot>>()
        every { mockResultCollection.orderBy("lastChanged").addSnapshotListener(capture(slot)) } returns mockk()

        provider.getNotes().observeForever {
            result = (it as? NoteResult.Error)?.error
        }
        slot.captured.onEvent(null, mockException)
        assertEquals(result, mockException)
    }

    @Test
    fun `saveNote returns Note`() {
        var result: Note? = null
        val mockDocumentReference = mockk<DocumentReference>()
        val slot = slot<OnSuccessListener<in Void>>()

        every { mockResultCollection.document() } returns mockDocumentReference
        every { mockDocumentReference.set(testNotes[0]).addOnSuccessListener(capture(slot)) } returns mockk()

        provider.saveNote(testNotes[0]).observeForever {
            when (it) {
                is NoteResult.Success<*> -> result = it.data as Note
            }
        }

        slot.captured.onSuccess(null)
        assertEquals(result, testNotes[0])
    }

    @Test
    fun `removeNote returns Note`() {
        val note = testNotes[0]
        var result: Note? = null
        val mockShapshot = mockk<QuerySnapshot>()
        val slot = slot<OnSuccessListener<QuerySnapshot>>()

        every { mockResultCollection.whereEqualTo("lastChanged", note.lastChanged).get().addOnSuccessListener(capture(slot)) } returns mockk()
        every { mockShapshot.documents[0].reference.delete() } returns mockk()

        provider.removeNote(testNotes[0]).observeForever {
            when (it) {
                is NoteResult.Success<*> -> result = it.data as Note
            }
        }

        slot.captured.onSuccess(mockShapshot)

        assertEquals(result, note)
    }

}