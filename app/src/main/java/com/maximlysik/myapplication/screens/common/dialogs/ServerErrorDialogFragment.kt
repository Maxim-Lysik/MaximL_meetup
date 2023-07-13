package com.maximlysik.myapplication.screens.common.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle

class ServerErrorDialogFragment : BaseDialog() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity).let {
            it.setTitle("Server Error")
            it.setMessage("Communication with the server failed")
            it.setPositiveButton("OK") { _, _ -> dismiss() }
            it.create()
        }
    }

    companion object {
        fun newInstance(): ServerErrorDialogFragment {
            return ServerErrorDialogFragment()
        }
    }
}