package com.example.quizapp

import android.content.res.AssetManager
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import android.widget.ImageView
import android.widget.TextView
import java.io.InputStream

class QuizQuestions : AppCompatActivity() {

    private var mySelectedOptionPosition:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        StartQuiz("Africa")
    }

    fun  StartQuiz(region:String)
    {
        FirstOptions()
        val img=findViewById<ImageView>(R.id.FlagImg)
        val option1=findViewById<TextView>(R.id.OptionOne)
        val option2=findViewById<TextView>(R.id.OptionTwo)
        val option3=findViewById<TextView>(R.id.OptionThree)
        val option4=findViewById<TextView>(R.id.OptionFour)

        val checkAnswer=findViewById<Button>(R.id.checkAnswer)

        val asset: AssetManager?= this.assets

        var options=ArrayList<TextView>()
        options.add(option1)
        options.add(option2)
        options.add(option3)
        options.add(option4)
        var answers=ArrayList<String>()


        /**
         *
         * Took Image from assets
         *
         */
        val choice= RandomName("Africa",asset)

        val image: InputStream = asset!!.open("${region}/${region}-${choice}.png")
        val drawImage = Drawable.createFromStream(image, null)
        img.setImageDrawable(drawImage)

        //End Image

        answers.add(RandomName(region,asset))
        answers.add(RandomName(region,asset))
        answers.add(RandomName(region,asset))
        answers.add(choice)
        answers.shuffle()
        var i:Int=0
        for (option in options)
        {
            option.text = answers[i]
            i++
        }

        var rightAnswer:Int = 0

        for(i in 0.. options.size)
        {
            if(options[i].text == choice)
            {
                rightAnswer = i + 1
                break
            }
        }



        // Button Selected Choices
        option1.setOnClickListener{
            selectedButton(findViewById<TextView>(R.id.OptionOne), 1)

        }
        option2.setOnClickListener{
            selectedButton(findViewById<TextView>(R.id.OptionTwo), 2)
        }
        option3.setOnClickListener{
            selectedButton(findViewById<TextView>(R.id.OptionThree), 3)
        }
        option4.setOnClickListener{
            selectedButton(findViewById<TextView>(R.id.OptionFour), 4)
        }



        //Button to check the result

        checkAnswer.setOnClickListener{
            if (checkAnswer.text == "Verify")
            {
                if(mySelectedOptionPosition !=   0) {

                    if (options[mySelectedOptionPosition-1].text != choice)
                    {
                        answerView(mySelectedOptionPosition,R.drawable.wrong_option_border_bg)
                    }
                    answerView(rightAnswer,R.drawable.correct_option_border_bg)
                    checkAnswer.text = "Next Question"

                }
            }else
            {
                StartQuiz(region)
                checkAnswer.text = "Verify"

            }


        }

    }

    fun RandomName(region:String, assetManager: AssetManager?):String
    {
        var ListCountries = ArrayList<String>()
        val assetList=assetManager!!.list("${region}/")
        for(index in assetList!!)
        {
            ListCountries.add(index)
        }

        ListCountries.shuffle()
        val country = ListCountries.random()
        return country.substring(country.indexOf("-")+1,country.lastIndexOf("."))
    }

    private fun FirstOptions()
    {
        val option1 = findViewById<TextView>(R.id.OptionOne)
        val option2 = findViewById<TextView>(R.id.OptionTwo)
        val option3 = findViewById<TextView>(R.id.OptionThree)
        val option4 = findViewById<TextView>(R.id.OptionFour)
        val options = ArrayList<TextView>()
        options.add(0,option1)
        options.add(1,option2)
        options.add(2,option3)
        options.add(3,option4)

        for (option in options )
        {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }
    private fun selectedButton(tv: TextView, selectedOptions: Int)
    {
        FirstOptions()

        mySelectedOptionPosition = selectedOptions

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_style)

    }
    private fun answerView(answer:Int,  drawableView:Int)
    {
        when(answer)
        {
            1 ->{
                findViewById<TextView>(R.id.OptionOne).background    =   ContextCompat.getDrawable(this,drawableView)
            }
            2 ->{
                findViewById<TextView>(R.id.OptionTwo).background    =   ContextCompat.getDrawable(this,drawableView)
            }
            3 ->{
                findViewById<TextView>(R.id.OptionThree).background    =   ContextCompat.getDrawable(this,drawableView)
            }
            4 ->{
                findViewById<TextView>(R.id.OptionFour).background    =   ContextCompat.getDrawable(this,drawableView)
            }
        }
    }
}