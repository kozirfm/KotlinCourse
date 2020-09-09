package ru.geekbrains.kozirfm.kotlincourse.data.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.data.model.NoteResult

class FirestoreDataProvider : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
    }

    private val store = Firebase.firestore
    private val notesReference = store.collection(NOTES_COLLECTION)

    override fun getNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesReference.orderBy("lastChanged").addSnapshotListener { snapshot, e ->
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
        notesReference.document().set(note).addOnSuccessListener {
            result.value = NoteResult.Success(note)
        }.addOnFailureListener { e ->
            result.value = NoteResult.Error(e)
        }
        return result
    }

    override fun changeNote(note: Note, newNote: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesReference.whereEqualTo("lastChanged", note.lastChanged)
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
        notesReference.whereEqualTo("lastChanged", note.lastChanged)
                .get().addOnSuccessListener {
                    it.documents[0].reference.delete()
                    result.value = NoteResult.Success(note)
                }.addOnFailureListener { e ->
                    result.value = NoteResult.Error(e)
                }
        return result
    }
}