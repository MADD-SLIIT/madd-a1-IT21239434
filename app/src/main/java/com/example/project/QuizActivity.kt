package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project.data_class.Question

class QuizActivity : AppCompatActivity() {

    // UI components
    private lateinit var bckToDetailedTopic: ImageButton
    private lateinit var quizFlagBtn: ImageButton

    lateinit var questionTextView: TextView
    lateinit var choicesRadioGroup: RadioGroup
    lateinit var submitAnswerButton: Button
    lateinit var nextQuestionButton: Button
    lateinit var progressBar: ProgressBar

    private var currentQuestionIndex = 0
    private var score = 0

    private val flaggedQuestions = mutableListOf<Question>() // List to store flagged questions

    // List of questions, choices, and answers
    private val quizQuestions = listOf(
        Question(
            questionText = "Which is the largest continent by land area?",
            choices = listOf("Africa", "Asia", "Europe", "North America"),
            correctAnswerIndex = 1
        ),
        Question(
            questionText = "What is the longest river in the world?",
            choices = listOf("Neil", "Amazon", "Yangtze", "Mississippi"),
            correctAnswerIndex = 0
        ),
        Question(
            questionText = "Which country has the highest number of active volcanoes?",
            choices = listOf("Japan", "Indonesia", "Italy", "United State"),
            correctAnswerIndex = 1
        ),
        Question(
            questionText = "Which desert is the largest in the world?",
            choices = listOf("Sahara", "Gobi", "Kalahari", "Arctic Desert"),
            correctAnswerIndex = 1
        ),
        Question(
            questionText = "Which mountain range forms the boundary between Europe and Asia?",
            choices = listOf("Himalayas", "ural mountains", "Andes", "Alps"),
            correctAnswerIndex = 1
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        // Initialize UI components
        questionTextView = findViewById(R.id.questionTextView)
        choicesRadioGroup = findViewById(R.id.choicesRadioGroup)
        submitAnswerButton = findViewById(R.id.submitAnswerButton)
        nextQuestionButton = findViewById(R.id.nextQuestionButton)

        bckToDetailedTopic = findViewById(R.id.bck_to_topicDescriptionPage)

        //quiz flag btn
        quizFlagBtn = findViewById(R.id.quiz_flag_btn)

        progressBar = findViewById(R.id.progressBar)

        // Set the first question
        setQuestion()

        // Handle submit button click
        submitAnswerButton.setOnClickListener {
            checkAnswer()
        }

        // Handle next button click
        nextQuestionButton.setOnClickListener {
            loadNextQuestion()
        }

        //bck to Topic Description page
        bckToDetailedTopic.setOnClickListener {
            bckToTopicDesc()
        }

        // Handle flag button click
        quizFlagBtn.setOnClickListener {
            flagCurrentQuestion()
        }


    }

    private fun flagCurrentQuestion() {
        val currentQuestion = quizQuestions[currentQuestionIndex]
        if (!flaggedQuestions.contains(currentQuestion)) {
            flaggedQuestions.add(currentQuestion)
            Toast.makeText(this, "Question flagged", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Already flagged", Toast.LENGTH_SHORT).show()
        }

        // Navigate to FlaggedQuestionsActivity to review flagged questions
        val intent = Intent(this, FlaggedQuestions::class.java)
        intent.putExtra("flaggedQuestions", ArrayList(flaggedQuestions)) // Pass flagged questions to new activity
        startActivity(intent)
    }


    // Set the current question
    private fun setQuestion() {
        val question = quizQuestions[currentQuestionIndex]
        questionTextView.text = question.questionText

        // Set the radio buttons text with the choices
        (choicesRadioGroup.getChildAt(0) as RadioButton).text = question.choices[0]
        (choicesRadioGroup.getChildAt(1) as RadioButton).text = question.choices[1]
        (choicesRadioGroup.getChildAt(2) as RadioButton).text = question.choices[2]
        (choicesRadioGroup.getChildAt(3) as RadioButton).text = question.choices[3]

        // Clear the previous selection
        choicesRadioGroup.clearCheck()

        // Hide next button initially
        nextQuestionButton.visibility = Button.GONE
        submitAnswerButton.visibility = Button.VISIBLE

        // Update progress bar based on current question index
        updateProgressBar()
    }

    private fun updateProgressBar() {
        val progress = ((currentQuestionIndex + 1).toDouble() / quizQuestions.size * 100).toInt()
        progressBar.progress = progress
    }

    // Check the answer chosen by the user
    private fun checkAnswer() {
        val selectedRadioButtonId = choicesRadioGroup.checkedRadioButtonId
        if (selectedRadioButtonId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
        val selectedAnswerIndex = choicesRadioGroup.indexOfChild(selectedRadioButton)
        val correctAnswerIndex = quizQuestions[currentQuestionIndex].correctAnswerIndex

        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()

            // Enable the next button and disable submit button
            nextQuestionButton.visibility = Button.VISIBLE
            submitAnswerButton.visibility = Button.GONE
        } else {
            Toast.makeText(this, "Wrong answer, please try again.", Toast.LENGTH_SHORT).show()

            // Keep the submit button visible until the correct answer is chosen
            nextQuestionButton.visibility = Button.GONE
            submitAnswerButton.visibility = Button.VISIBLE
        }
    }

    // Load the next question
    private fun loadNextQuestion() {
        currentQuestionIndex++

        if (currentQuestionIndex < quizQuestions.size) {
            setQuestion()
        } else {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        // Pass the result back to DetaildedTopic2activity
        val resultIntent = Intent()
        resultIntent.putExtra("quizCompleted", true)
        setResult(RESULT_OK, resultIntent)
        finish() // Close the QuizActivity
    }

    //bck to Topic Description page
    private fun bckToTopicDesc() {
        val intent = Intent(this, DetaildedTopic2activity::class.java)
        startActivity(intent)
    }
}