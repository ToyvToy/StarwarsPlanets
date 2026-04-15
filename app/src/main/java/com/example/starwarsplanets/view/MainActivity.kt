package com.example.starwarsplanets.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsplanets.R
import com.example.starwarsplanets.model.PlanetData
import com.example.starwarsplanets.viewmodel.PlanetsAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var mDataBase: DatabaseReference
    private lateinit var planetList: ArrayList<PlanetData>
    private lateinit var mAdapter: PlanetsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)



        val recyclerView: RecyclerView = findViewById(R.id.recyclerPlanets)
        recyclerView.layoutManager = LinearLayoutManager(this)

        planetList = ArrayList()

        mAdapter = PlanetsAdapter(this, planetList) { planet ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("planetName", planet.planetName)
            intent.putExtra("planetType", planet.planetType)
            intent.putExtra("planetImage", planet.planetImage)
            intent.putExtra("planetDescription", planet.planetDescription)
            startActivity(intent)
        }

        recyclerView.adapter = mAdapter

        mDataBase = FirebaseDatabase
            .getInstance()
            .getReference("Planets")

        getPlanetData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

     private fun getPlanetData() {
        mDataBase.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                planetList.clear()
                for (planetSnapshot in snapshot.children) {
                    val planet = planetSnapshot.getValue(PlanetData::class.java)
                    planet?.let {
                        planetList.add(it)
                    }
                }
                mAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message,
                    Toast.LENGTH_SHORT).show()
            }
        })
    }
}