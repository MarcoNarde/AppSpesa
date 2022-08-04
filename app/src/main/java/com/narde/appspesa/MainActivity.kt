package com.narde.appspesa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.narde.appspesa.adapter.ItemSpesaAdapter
import com.narde.appspesa.model.Alimento

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //actionBar!!.title =
        supportActionBar!!.title = getString(R.string.lista_spesa)

        val recycleviewSpesa = findViewById<RecyclerView>(R.id.rv_item_spesa)

        recycleviewSpesa.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<Alimento>()

        for (i in 1..20) {
            data.add(Alimento("Item $i", i+10.0, i +10.0, "Alimentari", ""))
        }

        val adapter = ItemSpesaAdapter(data)

        recycleviewSpesa.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principale, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        when(id){
            R.id.menu_principale_logout -> {
                Firebase.auth.signOut()
                updateUI(Firebase.auth.currentUser)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}