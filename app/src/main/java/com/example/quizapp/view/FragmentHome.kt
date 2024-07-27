package com.example.quizapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.database.DatabaseCopyHelper


class FragmentHome : Fragment() {

    lateinit var fragmentHomeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false)

        createAndOpenDatabase()

        fragmentHomeBinding.buttonStart.setOnClickListener {

            val direction =
                com.example.quizapp.view.FragmentHomeDirections.actionFragmentHomeToFragmentQuiz()
            this.findNavController().navigate(direction)
            //it.findNavController()
            //requireActivity().findNavController()

        }

        // Inflate the layout for this fragment
        return fragmentHomeBinding.root
    }

    private fun createAndOpenDatabase(){

        //try-catch
        try {

            val helper = DatabaseCopyHelper(requireActivity())
            helper.createDataBase()
            helper.openDataBase()

        }catch (e : Exception){

            e.printStackTrace()

        }

    }
}