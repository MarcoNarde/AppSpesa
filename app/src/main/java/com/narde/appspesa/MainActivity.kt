package com.narde.appspesa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.narde.appspesa.`interface`.OnItemClickListener
import com.narde.appspesa.adapter.ItemSpesaAdapter
import com.narde.appspesa.model.Prodotto

private const val TAG: String = "TEST"

class MainActivity : AppCompatActivity(), OnItemClickListener  {
    private lateinit var recycleviewSpesa: RecyclerView
    private lateinit var fbAggiungiProdotto: FloatingActionButton

    private lateinit var adapter: ItemSpesaAdapter

    private lateinit var database: DatabaseReference
    private lateinit var prodotti: ArrayList<Prodotto>
    private var idProd : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //actionBar!!.title =
        supportActionBar!!.title = getString(R.string.lista_spesa)

        database = Firebase.database.reference

        recycleviewSpesa = findViewById(R.id.rv_item_spesa)
        fbAggiungiProdotto = findViewById(R.id.fb_aggiungi_prodotto)

        recycleviewSpesa.layoutManager = LinearLayoutManager(this)
        prodotti = ArrayList()

        getProdottiData()

        fbAggiungiProdotto.setOnClickListener {
            writeNewProdotto()
        }
    }

    private fun writeNewProdotto() {
        //val prodotto = Prodotto("Test", 1.0, 2.0, "Generico", "Test.png")

        //database.child("Prodotti").child("Prod${idProd + 1}").setValue(prodotto)

        startActivity(Intent(this, AggiungiProdottoActivity::class.java))
    }

    private fun getProdottiData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                prodotti.clear()

                val prods = dataSnapshot.children
                if (prods != null) {
                    //inserisco tutti i prodotti nel db
                    for (p in prods){
                        Log.w(TAG, p.getValue<Prodotto>()!!.nome)
                        prodotti.add(p.getValue<Prodotto>()!!)
                    }
                }

                prodotti.sortWith(compareBy({ it.nome }, { it.isComprato }))

                adapter = ItemSpesaAdapter(prodotti, this@MainActivity)

                recycleviewSpesa.adapter = adapter

                if(idProd < prodotti.size)
                    idProd = prodotti.size
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.child("Prodotti").addValueEventListener(postListener)
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

    override fun onItemClick(view: View, position: Int) {
        var itemBefore = prodotti[position]
        var itemAfter = prodotti[position]
        itemAfter.isComprato = !itemAfter.isComprato

        prodotti.remove(itemBefore)
        prodotti.add(itemAfter)

        prodotti.sortWith(compareBy({ it.nome }, { it.isComprato }))
        adapter.notifyDataSetChanged()
    }
}