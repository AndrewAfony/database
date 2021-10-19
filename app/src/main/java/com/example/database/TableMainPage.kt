package com.example.database

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.database.databinding.TableMainPageFragmentBinding

private const val TAG = "TableMainPage"

class TableMainPage : Fragment() {

    private lateinit var viewModel: TableMainPageViewModel
    private lateinit var binding: TableMainPageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= DataBindingUtil.inflate(inflater, R.layout.table_main_page_fragment, container, false)

        binding.addRecordButton.setOnClickListener {

            val bottomSheet = BottomSheet()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)

        }

        binding.tableName.text = CreateDatabase.FILE_NAME
        binding.columnsNumber.text = CreateDatabase.columns.size.toString()

        binding.updateUIButton.setOnClickListener {

            binding.numberOfRecords.text = BottomSheet.counterOfRecords.toString()
            binding.columnsNumber.text = CreateDatabase.columns.size.toString()
            Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show()

        }

        return binding.root
    }

}