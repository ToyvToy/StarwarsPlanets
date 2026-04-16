package com.example.starwarsplanets.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.starwarsplanets.R
import com.example.starwarsplanets.viewmodel.getProgressDrawable
import com.example.starwarsplanets.viewmodel.loadImage

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_detail)

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // View references.
        val planetImage: ImageView = findViewById(R.id.planetImage)
        val planetName: TextView = findViewById(R.id.planetName)
        val planetType: TextView = findViewById(R.id.planetType)
        val planetDescription: TextView = findViewById(R.id.planetDescription)

        val planetNameDetail = intent.getStringExtra("planetName")
        val planetTypeDetail = intent.getStringExtra("planetType")
        val planetDescriptionDetail = intent.getStringExtra("planetDescription")
        val planetImageDetail = intent.getStringExtra("planetImage")

        planetName.text = planetNameDetail
        planetType.text = planetTypeDetail
        planetDescription.text = planetDescriptionDetail

        planetImage.loadImage(
            planetImageDetail,
            getProgressDrawable(this)
        )


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}