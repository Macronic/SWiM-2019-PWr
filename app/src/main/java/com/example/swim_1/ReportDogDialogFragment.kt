package com.example.swim_1

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ReportDogDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.report_dog_dialog, null))
                .setPositiveButton(R.string.reportDogConfirmation)
                { _, _ -> Toast.makeText(activity, R.string.reportDogToast, Toast.LENGTH_LONG).show() }
                .setNegativeButton(R.string.cancel) { _, _ -> dialog.cancel() }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null!")

    }
}