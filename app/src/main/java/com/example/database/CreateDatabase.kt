package com.example.database

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.database.databinding.FragmentCreateDatabaseBinding
import androidx.databinding.DataBindingUtil

private const val TAG = "CreateDatabase"

class CreateDatabase : Fragment() {

    companion object {
        val columns =
            mutableListOf<ColumnsInfo>() // TODO (Вместо companion object использовать LiveData)

        var FILE_NAME: String = "example"
    }

    private val types = arrayOf("Int", "String", "Date")
    private var counterColumns = 0

    lateinit var binding: FragmentCreateDatabaseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_database, container, false)

        binding.addColumnButton.setOnClickListener {
            addView()
            counterColumns++
            if (counterColumns == 5) binding.addColumnButton.visibility = View.GONE
        }

        binding.createButton.setOnClickListener {
            if (binding.enterTableName.text.isNullOrBlank()) { // TODO(Добавить проверку, введены ли другие колонки (если нужно))
                Toast.makeText(requireContext(), "Enter table name", Toast.LENGTH_LONG).show()
                binding.enterTableName.error = "Enter table name"
            }

            FILE_NAME = binding.enterTableName.text.toString()

            if (checkIfValidAndRead()) {

                DialogPrimaryKey().apply {
                    show(this@CreateDatabase.parentFragmentManager, "myDialog")
                }

            }

        }

        return binding.root
    }

    private fun checkIfValidAndRead(): Boolean {

        columns.clear()

        var result = true

        for (n in 0 until binding.rootLayout.childCount) {

            val view: LinearLayout? = view?.findViewById(R.id.root_layout)
            val childAt = view?.getChildAt(n)

            val columnName: EditText? = childAt?.findViewById(R.id.columnNameText)
            val spinner: Spinner? = childAt?.findViewById(R.id.spinner_type)

            // TODO(Настроить условие ниже, возвращается лист на один больше с null\null)

            val column = ColumnsInfo()

            if (!(columnName?.text.isNullOrEmpty())) {
                column.columnName = columnName?.text.toString()
            } else {
                result = false
                columnName?.error = "Enter column name"
                break
            }

            column.typeOfColumn = spinner?.selectedItem.toString()

            columns.add(column)
        }

        if (columns.size == 0) {
            result = false
            Toast.makeText(requireContext(), "Add columns", Toast.LENGTH_LONG).show()
        } else if (!result) {
            Toast.makeText(requireContext(), "Enter All Details Correctly", Toast.LENGTH_LONG)
                .show()
        }



        return result
    }

    private fun addView() {
        val newColumn: View =
            layoutInflater.inflate(R.layout.row_add_column, binding.rootLayout, false)

        val columnName: EditText = newColumn.findViewById(R.id.columnNameText)
        val spinner: Spinner = newColumn.findViewById(R.id.spinner_type)
        val imageClose: ImageView = newColumn.findViewById(R.id.image_remove)

        val adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            types
        )

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        spinner.adapter = adapter

        val selected: String = spinner.selectedItem.toString()
        // TODO(Ввод только того типа данных, который выбран)

        imageClose.setOnClickListener {
            binding.rootLayout.removeView(newColumn)
            counterColumns--
            if (counterColumns < 5) binding.addColumnButton.visibility = View.VISIBLE
        }

        binding.rootLayout.addView(newColumn)
    }

}