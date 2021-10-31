package com.example.database

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import java.io.File

private const val TAG = "DialogOpenTable"

class DialogOpenTable : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_dialog_open_table, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val arrayOfTableName: Array<String> = getTableList()

        var chosenTable = 0

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Chose table")
                .setSingleChoiceItems(
                    arrayOfTableName, -1
                ) { _, item ->
                    Toast.makeText(
                        activity, "Chosen:  ${arrayOfTableName[item]}",
                        Toast.LENGTH_SHORT
                    ).show()

                    chosenTable = item
                }
                .setPositiveButton("OK") { _, _ ->
                    CreateDatabase.FILE_NAME = arrayOfTableName[chosenTable]
                    requireActivity().findNavController(R.id.myNavHostFragment).navigate(R.id.action_titleFragment_to_tableMainPage)
                }
                .setNegativeButton("Отмена") { _, _ -> }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }

    private fun getTableList(): Array<String> {

        val list = mutableListOf<String>()

        File(requireActivity().filesDir.path).walkTopDown().forEach { line ->
            if (line.toString().contains("txt")) {
                list.add(line.toString().substringAfterLast('/').substringBefore('.'))
            }
        }

        return list.toTypedArray()
    }
}