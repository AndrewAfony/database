package com.example.database

import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import java.lang.StringBuilder

private const val TAG = "Dialog"

class DialogPrimaryKey : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_dialog_primary_key, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val arrayOfColumnsName: Array<String> =
            CreateDatabase.columns.map { it.columnName }.toTypedArray()

        var chosenNum = 0

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Primary key column")
                .setSingleChoiceItems(
                    arrayOfColumnsName, 0
                ) { _, item ->
                    Toast.makeText(
                        activity, "Chosen:  ${arrayOfColumnsName[item]}",
                        Toast.LENGTH_SHORT
                    ).show()

                    chosenNum = item
                }
                .setPositiveButton("OK") { _, _ ->

                    CreateDatabase.columns[chosenNum].isPrimaryKey = true

                    Log.d(TAG, columnInfoToString(CreateDatabase.columns))

                    requireActivity().openFileOutput("${CreateDatabase.FILE_NAME}.txt", MODE_PRIVATE).use {
                        it.write(columnInfoToString(CreateDatabase.columns).toByteArray())
                    }

                    requireActivity().findNavController(R.id.myNavHostFragment).navigate(R.id.action_createDatabase_to_tableMainPage)
                }
                .setNegativeButton("Отмена") { _, _ -> }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun columnInfoToString(list: MutableList<ColumnsInfo>): String {

        var result = ""

        for (item in list) {

            val plusStr = buildString{
                if (item.isPrimaryKey) append("[key]")
                append(item.columnName)
                append(" (${item.typeOfColumn})|")
            }

            result += plusStr
        }
        result += "\n"

        return result
    }
}

