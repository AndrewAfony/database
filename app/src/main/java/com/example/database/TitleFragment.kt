package com.example.database

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.database.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    companion object{
        val columns =
            mutableListOf<ColumnsInfo>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTitleBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)

        binding.createDBButton.setOnClickListener { it?.findNavController()?.navigate(R.id.action_titleFragment_to_createDatabase) }

        binding.openTableButton.setOnClickListener {
            DialogOpenTable().apply {
                show(this@TitleFragment.parentFragmentManager, "DialogOpenTable")
            }
        }

        binding.secretButton.setOnClickListener { it?.findNavController()?.navigate(R.id.action_titleFragment_to_tableMainPage) }

        return binding.root
    }

//    override fun onStart() {
//        super.onStart()
//
//        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback);
//
//    }
}