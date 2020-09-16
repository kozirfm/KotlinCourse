package ru.geekbrains.kozirfm.kotlincourse.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.geekbrains.kozirfm.kotlincourse.R

class LogoutDialog : DialogFragment() {

    companion object {
        val TAG = LogoutDialog::class.java.name + " TAG"

        fun createInstance(onLogout: (() -> Unit)): LogoutDialog {
            return LogoutDialog().apply {
                this.onLogout = onLogout
            }
        }

    }

    var onLogout: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.logout_title)
                .setMessage(R.string.logout_message)
                .setPositiveButton(R.string.logout_ok) { _, _ -> onLogout?.invoke() }
                .setNegativeButton(R.string.logout_cancel) { _, _ -> dismiss() }
                .create()
    }

}
