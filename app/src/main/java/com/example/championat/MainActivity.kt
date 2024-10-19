package com.example.championat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private lateinit var textView: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var textView4: TextView

    private lateinit var btnNext: Button
    private lateinit var btnSignUp: Button
    private lateinit var btnSkip: Button



    private val queue = mutableListOf<Triple<Int, String,String>>() // Список изображений и текста
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)
        btnNext = findViewById(R.id.btnNext)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnSkip = findViewById(R.id.btnSkip)
        textView4 = findViewById(R.id.textView4)

        // Инициализация очереди изображений и текста
        queue.addAll(listOf(
            Triple(R.drawable.image1, "Quick Delivery At Your Doorstep", "Enjoy quick pick-up and delivery to your destination"),
            Triple(R.drawable.image2, "Flexible Payment","Different modes of payment either before and after delivery without stress"),
            Triple(R.drawable.image3, "Real-time Tracking","Track your packages/items from the comfort of your home till final destination")
        ))

        // Настройка начального состояния
        displayCurrentItem()

        // Установка слушателей кнопок
        btnNext.setOnClickListener { showNextItem() }
        btnSignUp.setOnClickListener { goToHolderScreen() }
        btnSkip.setOnClickListener { skipOnboarding() }
        textView4.setOnClickListener { goToHolderScreen() }
    }

    private fun displayCurrentItem() {
        if (currentIndex < queue.size-1) {
            val (imageRes, text, text2) = queue[currentIndex]
            imageView.clearAnimation()
            textView.clearAnimation()
            textView2.clearAnimation()
            val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 300 }
            fadeOut.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
                override fun onAnimationStart(animation: android.view.animation.Animation?) {}

                override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                    imageView.setImageResource(imageRes)
                    textView.text = text
                    textView2.text = text2
                    val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 300 }
                    imageView.startAnimation(fadeIn)
                    textView.startAnimation(fadeIn)
                    textView2.startAnimation(fadeIn)
                }

                override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
            })

            imageView.startAnimation(fadeOut)
            textView.startAnimation(fadeOut)
            textView2.startAnimation(fadeOut)
        } else {
            val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 300 }
            val fadeIn2 = AlphaAnimation(0f, 1f).apply { duration = 300 }
            btnSignUp.startAnimation(fadeIn2)
            textView3.startAnimation(fadeIn2)
            textView4.startAnimation(fadeIn2)
            val (imageRes, text, text2) = queue[currentIndex]
            imageView.clearAnimation()
            textView.clearAnimation()
            textView2.clearAnimation()

            fadeOut.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
                override fun onAnimationStart(animation: android.view.animation.Animation?) {}

                override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                    imageView.setImageResource(imageRes)
                    textView.text = text
                    textView2.text = text2
                    val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 300 }
                    imageView.startAnimation(fadeIn)
                    textView.startAnimation(fadeIn)
                    textView2.startAnimation(fadeIn)

                }

                override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
            })

            imageView.startAnimation(fadeOut)
            textView.startAnimation(fadeOut)
            textView2.startAnimation(fadeOut)
            handleLastItem()
        }
    }

    private fun showNextItem() {
        currentIndex++
        displayCurrentItem()
    }

    private fun handleLastItem() {
        btnNext.visibility = View.GONE
        btnSkip.visibility = View.GONE
        btnSignUp.visibility = View.VISIBLE
        textView3.visibility = View.VISIBLE
        textView4.visibility = View.VISIBLE


    }

    private fun goToHolderScreen() {
        // Здесь можно настроить переход на пустой экран "Holder"
        startActivity(Intent(this, HolderActivity::class.java))
    }

    private fun skipOnboarding() {
        // Сохранить состояние, что пользователь просмотрел Onboarding
        val sharedPrefs = getSharedPreferences("app_preferences", MODE_PRIVATE)
        sharedPrefs.edit().putBoolean("isOnboarded", true).apply()

        // Переход на экран "Holder"
        startActivity(Intent(this, HolderActivity::class.java))
        finish()
    }
}