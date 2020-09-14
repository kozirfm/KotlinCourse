package ru.geekbrains.kozirfm.kotlincourse.data.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.data.entity.User
import ru.geekbrains.kozirfm.kotlincourse.data.errors.NoAuthException
import ru.geekbrains.kozirfm.kotlincourse.data.model.NoteResult

class FirestoreDataProvider : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USER_COLLECTION = "users"
    }

    private val store by lazy { Firebase.firestore }
    private val auth by lazy { Firebase.auth }

    private val currentUser
        get() = auth.currentUser

    private val userNotesCollection
        get() = currentUser?.let { store.collection(USER_COLLECTION).document(it.uid).collection(NOTES_COLLECTION) }
                ?: throw NoAuthException()

    override fun getCurrentUser(): LiveData<User?> {
        val result = MutableLiveData<User?>()
        result.value = currentUser?.let { User(it.displayName ?: "", it.email ?: "") }
        return result
    }

    override fun getNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        userNotesCollection.orderBy("lastChanged").addSnapshotListener { snapshot, e ->
            e?.let {
                result.value = NoteResult.Error(e)
            } ?: let {
                snapshot?.let {
                    val notes = snapshot.documents.map { doc ->
                        doc.toObject(Note::class.java)
                    }
                    result.value = NoteResult.Success(notes)
                }
            }
        }
        return result
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        userNotesCollection.document().set(note).addOnSuccessListener {
            result.value = NoteResult.Success(note)
        }.addOnFailureListener { e ->
            result.value = NoteResult.Error(e)
        }
        return result
    }

    override fun changeNote(note: Note, newNote: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        userNotesCollection.whereEqualTo("lastChanged", note.lastChanged)
                .get().addOnSuccessListener {
                    it.documents[0].reference.update("title", newNote.title,
                            "text", newNote.text,
                            "lastChanged", newNote.lastChanged)
                    result.value = NoteResult.Success(newNote)
                }.addOnFailureListener { e ->
                    result.value = NoteResult.Error(e)
                }
        return result
    }

    override fun removeNote(note: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        userNotesCollection.whereEqualTo("lastChanged", note.lastChanged)
                .get().addOnSuccessListener {
                    it.documents[0].reference.delete()
                    result.value = NoteResult.Success(note)
                }.addOnFailureListener { e ->
                    result.value = NoteResult.Error(e)
                }
        return result
    }
}