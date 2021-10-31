package com.example.database

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.database.databinding.FragmentEditRecordBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File

private const val TAG = "EditRecordFragment"

class EditRecordBottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditRecordBottomBinding

    private val listOfRecordsValue: MutableList<String> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_record_bottom, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addView(CreateDatabase.columns.size, CreateDatabase.columns)

        binding.buttonDeleteRecord.setOnClickListener {
            if (checkIfValid()){

                val listToDelete = mutableListOf<String>()

                File("/data/data/com.example.database/files/${CreateDatabase.FILE_NAME}.txt").useLines { lines ->
                    listToDelete.addAll(lines.filterNot { it.contains(listOfRecordsValue[0]) })
                } // TODO (Добавить удаление по всем введеным данным)

                File("/data/data/com.example.database/files/${CreateDatabase.FILE_NAME}.txt").deleteRecursively()
                requireActivity().openFileOutput("${CreateDatabase.FILE_NAME}.txt",
                    Context.MODE_PRIVATE
                ).use {
                    listToDelete.forEach{ line ->
                        it.write("${line}\n".toByteArray())
                    }
                }

                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun addView(number: Int, columns: List<ColumnsInfo>){

        for (n in 0 until number){
            val newColumnValue: View = layoutInflater.inflate(R.layout.row_add_value_column, null, false)

            val columnValue: EditText = newColumnValue.findViewById(R.id.writtenValue)
            columnValue.hint = "Enter ${columns[n].columnName.lowercase()}"

            binding.columnValueRootLayout.addView(newColumnValue)
        }

    }

    private fun cleanEditText(){
        val view: LinearLayout? = view?.findViewById(R.id.columnValueRootLayout)

        for (n in 0 until binding.columnValueRootLayout.childCount) {

            val childAt = view?.getChildAt(n)

            val editTextValue: EditText? = childAt?.findViewById(R.id.writtenValue)

            editTextValue?.text?.clear()
        }
    }

    private fun checkIfValid(): Boolean{

        // TODO (ColumnsInfo)

        listOfRecordsValue.clear()

        val view: LinearLayout? = view?.findViewById(R.id.columnValueRootLayout)

        for (n in 0 until binding.columnValueRootLayout.childCount){

            val childAt = view?.getChildAt(n)

            val editTextValue: EditText? = childAt?.findViewById(R.id.writtenValue)

            if (editTextValue?.text.isNullOrBlank()) {
                return false
            } else {
                listOfRecordsValue.add(editTextValue?.text.toString())
            }

        }

        return true
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }


}