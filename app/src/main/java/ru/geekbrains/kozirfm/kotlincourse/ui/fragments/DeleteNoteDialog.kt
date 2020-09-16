package ru.geekbrains.kozirfm.kotlincourse.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.geekbrains.kozirfm.kotlincourse.R

class DeleteNoteDialog : DialogFragment() {

    companion object {
        val TAG = DeleteNoteDialog::class.java.name + " TAG"

        fun createInstance(onDeleteNote: (() -> Unit)): DeleteNoteDialog {
            return DeleteNoteDialog().apply {
                this.onDeleteNote = onDeleteNote
            }
        }

    }

    var onDeleteNote: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.delet_note_title)
                .setMessage(R.string.delete_note_message)
                .setPositiveButton(R.string.logout_ok) { _, _ -> onDeleteNote?.invoke()}
                .setNegativeButton(R.string.logout_cancel) { _, _ -> }
                .create()
    }
}