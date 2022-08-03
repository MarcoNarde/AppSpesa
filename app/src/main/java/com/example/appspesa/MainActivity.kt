package com.example.appspesa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appspesa.adapter.ItemSpesaAdapter
import com.example.appspesa.model.Alimento

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycleviewSpesa = findViewById<RecyclerView>(R.id.rv_item_spesa)

        recycleviewSpesa.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<Alimento>()

        for (i in 1..20) {
            data.add(Alimento("Item " + i, i+10.0, i +10.0, "Alimentari", ""))
        }

        val adapter = ItemSpesaAdapter(data)

        recycleviewSpesa.adapter = adapter
    }
}