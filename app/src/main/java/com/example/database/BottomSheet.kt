package com.example.database

import android.app.Dialog
import android.content.Context.MODE_APPEND
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
import androidx.lifecycle.ViewModelProvider
import com.example.database.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File

private const val TAG = "BottomSheet"

class BottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding

    private val listOfRecordsValue: MutableList<String> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        provider = ViewModelProvider(this).get(BottomSheetViewModel::class.java)

        addView(CreateDatabase.columns.size, CreateDatabase.columns)

        binding.buttonCreateRecord.setOnClickListener {

            if (checkIfValid()){
                Toast.makeText(requireContext(), "Record created", Toast.LENGTH_LONG).show()

                cleanEditText()

                addRecordToFile()
            }
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
                listOfRecordsValue.add("null")
            } else {
                listOfRecordsValue.add(editTextValue?.text.toString())
            }

        }

        return true
    }

    private fun cleanEditText(){
        val view: LinearLayout? = view?.findViewById(R.id.columnValueRootLayout)

        for (n in 0 until binding.columnValueRootLayout.childCount) {

            val childAt = view?.getChildAt(n)

            val editTextValue: EditText? = childAt?.findViewById(R.id.writtenValue)

            editTextValue?.text?.clear()
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


    private fun addRecordToFile(){
        requireActivity().openFileOutput("${CreateDatabase.FILE_NAME}.txt", MODE_APPEND).use {
//            File("${CreateDatabase.FILE_NAME}.txt").readLines().forEach { _ ->
//                counter++
//            }
            it.write(arrayRecordToString(listOfRecordsValue).toByteArray())
        }
    }

    private fun arrayRecordToString(list: MutableList<String>): String{
        var result = ""

        val plusStr = buildString {
            for (item in list){
                append("$item | ")
            }
        }
        result += plusStr + "\n"

        return result
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

}