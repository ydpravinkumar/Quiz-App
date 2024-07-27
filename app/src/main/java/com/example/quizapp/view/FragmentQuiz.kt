package com.example.quizapp.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.database.FlagsDao
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.model.FlagsModel
import com.example.quizapp.database.DatabaseCopyHelper

class FragmentQuiz : Fragment() {

    lateinit var fragmentQuizBinding: FragmentQuizBinding
    var flagList = ArrayList<FlagsModel>()

    var correctNumber = 0
    var wrongNumber = 0
    var emptyNumber = 0
    var questionNumber = 0

    lateinit var correctFlag : FlagsModel
    var wrongFlags = ArrayList<FlagsModel>()

    val dao = FlagsDao()

    var optionControl = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentQuizBinding = FragmentQuizBinding.inflate(inflater,container,false)


        flagList = dao.getRandomTenRecords(DatabaseCopyHelper(requireActivity()))

        Log.e("FLAGS:","${flagList.size}")

        for (flag in flagList){

            Log.d("flagsCHECK",flag.id.toString())
            Log.d("flagsCHECK",flag.countryName)
            Log.d("flagsCHECK",flag.flagName)
            Log.d("flagsCHECK","**************************")

        }

        showData()


        fragmentQuizBinding.buttonA.setOnClickListener {
            answerControl(fragmentQuizBinding.buttonA)
        }
        fragmentQuizBinding.buttonB.setOnClickListener {
            answerControl(fragmentQuizBinding.buttonB)
        }
        fragmentQuizBinding.buttonC.setOnClickListener {
            answerControl(fragmentQuizBinding.buttonC)
        }
        fragmentQuizBinding.buttonD.setOnClickListener {
            answerControl(fragmentQuizBinding.buttonD)
        }
        fragmentQuizBinding.buttonNext.setOnClickListener {

            questionNumber++

            if (questionNumber > 9){

                if (!optionControl){
                    emptyNumber++
                }

                val direction = com.example.quizapp.view.FragmentQuizDirections.actionFragmentQuizToFragmentResult()
                    .apply {

                        correct = correctNumber
                        wrong = wrongNumber
                        empty = emptyNumber

                    }

                this.findNavController().apply {
                    navigate(direction)
                    popBackStack(R.id.fragmentResult,false)
                }


                //Toast.makeText(requireActivity(),"The quiz is finished",Toast.LENGTH_SHORT).show()

            }else{

                showData()

                if (!optionControl){
                    emptyNumber++
                    fragmentQuizBinding.textViewEmpty.text = emptyNumber.toString()
                }else{
                    setButtonToInitialProperties()
                }

            }

            optionControl = false


        }

        // Inflate the layout for this fragment
        return fragmentQuizBinding.root
    }


    private fun showData() {
        fragmentQuizBinding.textViewQuestion.text = getString(R.string.question_number).plus(" ").plus(questionNumber + 1)
        Log.e("SHOW DATA","${questionNumber}")
        Log.e("SHOW DATA","${flagList}")
        correctFlag = flagList[questionNumber]
        Log.e("SHOW DATA","$correctFlag")

        fragmentQuizBinding.imageViewFlag.setImageResource(
            resources.getIdentifier(correctFlag.flagName, "drawable", requireActivity().packageName)
        )

        wrongFlags = dao.getRandomThreeRecords(DatabaseCopyHelper(requireActivity()), correctFlag.id)

        // Ensure no duplicates and shuffle options
        val mixOptions = (wrongFlags + correctFlag).shuffled()

        // Assign options to buttons
        fragmentQuizBinding.buttonA.text = mixOptions[0].countryName
        fragmentQuizBinding.buttonB.text = mixOptions[1].countryName
        fragmentQuizBinding.buttonC.text = mixOptions[2].countryName
        fragmentQuizBinding.buttonD.text = mixOptions[3].countryName

        // Reset button styles
        setButtonToInitialProperties()
    }


    private fun answerControl(button : Button){

        val clickedOption = button.text.toString()
        val correctAnswer = correctFlag.countryName

        if (clickedOption == correctAnswer){

            correctNumber++
            fragmentQuizBinding.textViewCorrect.text = correctNumber.toString()
            button.setBackgroundColor(Color.GREEN)

        }else{

            wrongNumber++
            fragmentQuizBinding.textViewWrong.text = wrongNumber.toString()
            button.setBackgroundColor(Color.RED)
            button.setTextColor(Color.WHITE)

            when(correctAnswer){

                fragmentQuizBinding.buttonA.text -> fragmentQuizBinding.buttonA.setBackgroundColor(Color.GREEN)
                fragmentQuizBinding.buttonB.text -> fragmentQuizBinding.buttonB.setBackgroundColor(Color.GREEN)
                fragmentQuizBinding.buttonC.text -> fragmentQuizBinding.buttonC.setBackgroundColor(Color.GREEN)
                fragmentQuizBinding.buttonD.text -> fragmentQuizBinding.buttonD.setBackgroundColor(Color.GREEN)

            }

        }

        fragmentQuizBinding.buttonA.isClickable = false
        fragmentQuizBinding.buttonB.isClickable = false
        fragmentQuizBinding.buttonC.isClickable = false
        fragmentQuizBinding.buttonD.isClickable = false

        optionControl = true

    }

    private fun setButtonToInitialProperties(){

        fragmentQuizBinding.buttonA.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(resources.getColor(R.color.pink,requireActivity().theme))
            isClickable = true
        }
        fragmentQuizBinding.buttonB.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(resources.getColor(R.color.pink,requireActivity().theme))
            isClickable = true
        }
        fragmentQuizBinding.buttonC.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(resources.getColor(R.color.pink,requireActivity().theme))
            isClickable = true
        }
        fragmentQuizBinding.buttonD.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(resources.getColor(R.color.pink,requireActivity().theme))
            isClickable = true
        }

    }
}