package ru.geekbrains.kozirfm.kotlincourse.data.providers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.data.entity.User
import ru.geekbrains.kozirfm.kotlincourse.data.errors.NoAuthException
import ru.geekbrains.kozirfm.kotlincourse.data.model.NoteResult
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirestoreDataProvider(private val auth: FirebaseAuth, private val store: FirebaseFirestore) : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USER_COLLECTION = "users"
    }

    private val currentUser
        get() = auth.currentUser

    private val userNotesCollection
        get() = currentUser?.let { store.collection(USER_COLLECTION).document(it.uid).collection(NOTES_COLLECTION) }
                ?: throw NoAuthException()

    override suspend fun getCurrentUser(): User? {
        return suspendCoroutine { continuation ->
            currentUser?.let {
                User(it.displayName ?: "", it.email ?: "")
            }.let { continuation.resume(it) }
        }
    }

    override fun getNotes(): ReceiveChannel<NoteResult> {
        return Channel<NoteResult>(Channel.CONFLATED).apply {
            var registration: ListenerRegistration? = null

            try {
                registration = userNotesCollection.orderBy("lastChanged").addSnapshotListener { snapshot, e ->
                    val value = e?.let {
                        NoteResult.Error(it)
                    } ?: snapshot?.let {
                        val notes = snapshot.documents.map { doc ->
                            doc.toObject(Note::class.java)
                        }
                        NoteResult.Success(notes)
                    }

                    value?.let { offer(it) }
                }

            } catch (e: Throwable) {
                offer(NoteResult.Error(e))
            }
            invokeOnClose {
                registration?.remove()
            }
        }
    }

    override suspend fun saveNote(note: Note): Note {
        return suspendCoroutine { continuation ->
            try {
                userNotesCollection.document().set(note)
                        .addOnSuccessListener {
                            continuation.resume(note)
                        }.addOnFailureListener { e ->
                            continuation.resumeWithException(e)
                        }
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }

        }
    }

    override suspend fun changeNote(note: Note, newNote: Note): Note {
        return suspendCoroutine { continuation ->
            try {
                userNotesCollection.whereEqualTo("lastChanged", note.lastChanged)
                        .get().addOnSuccessListener {
                            it.documents[0].reference.update("title", newNote.title,
                                    "text", newNote.text,
                                    "lastChanged", newNote.lastChanged)
                            continuation.resume(newNote)
                        }.addOnFailureListener { e ->
                            continuation.resumeWithException(e)
                        }
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }
    }

    override suspend fun removeNote(note: Note): Note {
        return suspendCoroutine { continuation ->
            try {
                userNotesCollection.whereEqualTo("lastChanged", note.lastChanged)
                        .get().addOnSuccessListener {
                            it.documents[0].reference.delete()
                            continuation.resume(note)
                        }.addOnFailureListener { e ->
                            continuation.resumeWithException(e)
                        }
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }
    }
}