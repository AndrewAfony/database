package com.example.database

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.database.databinding.TableMainPageFragmentBinding
import java.io.BufferedReader
import java.io.File

private const val TAG = "TableMainPage"
private const val KEY_INDEX = "index"

class TableMainPage : Fragment() {

    private lateinit var viewModel: TableMainPageViewModel
    private lateinit var binding: TableMainPageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= DataBindingUtil.inflate(inflater, R.layout.table_main_page_fragment, container, false)

        // TODO(check)
        val currentNumberOfRecords = savedInstanceState?.getString(KEY_INDEX) ?: "0"
        binding.numberOfRecords.text = currentNumberOfRecords

        binding.editButton.setOnClickListener { view ->
            view.visibility = View.INVISIBLE
            binding.searchButton.visibility = View.INVISIBLE
            binding.testButton.visibility = View.INVISIBLE
            binding.settingsButton.visibility = View.INVISIBLE

            binding.addRecordButton.visibility = View.VISIBLE
            binding.addColumnToTableButton.visibility = View.VISIBLE
            binding.deleteRecordButton.visibility = View.VISIBLE
            binding.backButton.visibility = View.VISIBLE

            binding.backButton.setOnClickListener {
                binding.addRecordButton.visibility = View.INVISIBLE
                binding.addColumnToTableButton.visibility = View.INVISIBLE
                binding.deleteRecordButton.visibility = View.INVISIBLE
                binding.backButton.visibility = View.INVISIBLE

                view.visibility = View.VISIBLE
                binding.searchButton.visibility = View.VISIBLE
                binding.testButton.visibility = View.VISIBLE
                binding.settingsButton.visibility = View.VISIBLE
            }

            binding.deleteRecordButton.setOnClickListener {

                val bottomSheet = EditRecordBottomFragment()
                bottomSheet.show(parentFragmentManager, bottomSheet.tag)

            }

        }

        binding.settingsButton.setOnClickListener { view ->
            view.visibility = View.INVISIBLE
            binding.editButton.visibility = View.INVISIBLE
            binding.searchButton.visibility = View.INVISIBLE
            binding.testButton.visibility = View.INVISIBLE

            binding.deleteTableButton.visibility = View.VISIBLE
            binding.clearTableButton.visibility = View.VISIBLE
            binding.backButton.visibility = View.VISIBLE

            binding.backButton.setOnClickListener {
                view.visibility = View.VISIBLE
                binding.editButton.visibility = View.VISIBLE
                binding.searchButton.visibility = View.VISIBLE
                binding.testButton.visibility = View.VISIBLE

                binding.deleteTableButton.visibility = View.INVISIBLE
                binding.clearTableButton.visibility = View.INVISIBLE
                binding.backButton.visibility = View.INVISIBLE
            }

            binding.deleteTableButton.setOnClickListener {
                File("/data/data/com.example.database/files/${CreateDatabase.FILE_NAME}.txt").deleteRecursively()
                Toast.makeText(requireContext(), "Table deleted", Toast.LENGTH_SHORT).show()
                //TODO (Можно добавить подтверждение в виде AlertDialog)
                findNavController().navigate(R.id.action_tableMainPage_to_titleFragment)
            }

            binding.clearTableButton.setOnClickListener {
                var columns = File("/data/data/com.example.database/files/${CreateDatabase.FILE_NAME}.txt").bufferedReader().use { it.readLine() }
                columns += "\n"
                File("/data/data/com.example.database/files/${CreateDatabase.FILE_NAME}.txt").deleteRecursively()
                requireActivity().openFileOutput("${CreateDatabase.FILE_NAME}.txt",
                    Context.MODE_PRIVATE
                ).use {
                    it.write(columns.toByteArray())
                }
                Toast.makeText(requireContext(), "File cleared", Toast.LENGTH_SHORT).show()
            }
        }

        binding.testButton.setOnClickListener {

            val myList = mutableListOf<String>()

            File("/data/data/com.example.database/files/${CreateDatabase.FILE_NAME}.txt").useLines { lines -> myList.addAll(lines) }

        //Log.d(TAG, myList[1].first().toString())

//            for (n in  1 until myList.size){
//                if (myList[n].contains("Andrew")){
//                    Log.d(TAG, myList[n])
//                }
//            }
        }

        binding.addRecordButton.setOnClickListener {

            val bottomSheet = BottomSheet()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)

        }

        binding.testButton.setOnClickListener {

        }

        binding.updateUIButton.setOnClickListener {

            updateUI()
            Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show()

        }

        binding.tableName.text = CreateDatabase.FILE_NAME
        updateUI()
        updateVariables()

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(KEY_INDEX, binding.numberOfRecords.text.toString())
    }

    private fun updateUI(){

        val list = mutableListOf<String>()

        File("/data/data/com.example.database/files/${CreateDatabase.FILE_NAME}.txt").useLines { lines -> list.addAll(lines) }

        binding.numberOfRecords.text = (list.size-1).toString()
        binding.columnsNumber.text = (list.elementAt(0).split('|').size - 1).toString()

    }

    private fun updateVariables(){

        CreateDatabase.columns.clear()

        val list = mutableListOf<String>()

        File("/data/data/com.example.database/files/${CreateDatabase.FILE_NAME}.txt").useLines { lines -> list.addAll(lines) }

        val columns = list.elementAt(0).split('|').drop(1).dropLast(1)

        for (n in 0 until columns.size) {
            val element = ColumnsInfo(columns[n].substringAfter(']').substringBefore(' '), columns[n].substringAfter('(').substringBefore(')'))

            if(columns[n].contains("key")) element.isPrimaryKey = true

            CreateDatabase.columns.add(element)

        }
    }

}