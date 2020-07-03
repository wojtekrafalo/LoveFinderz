package com.example.lovefinderz.common

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.widget.EditText
import com.example.lovefinderz.R


fun showGeneralError(from: Context, message: String) {
    AlertDialog.Builder(from)
        .setTitle(message)
        .setMessage(from.getString(R.string.error_message))
        .setPositiveButton(from.getString(R.string.general_positive_button)) { dialog, _ -> dialog.dismiss() }
        .show()
}


//TODO: I guess, this won't work properly. Probably returns empty string.
fun showEditTextModalDialog(from: Context, title: String): String {
    val builder = AlertDialog.Builder(from)
    builder.setTitle(title)

    val input = EditText(from)
    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//    input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    input.inputType = InputType.TYPE_CLASS_TEXT
    builder.setView(input)

    var typedText = ""

    // Set up the buttons
    builder.setPositiveButton("OK") { dialog, which -> typedText = input.text.toString() }
    builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

    builder.show()

    return typedText
}