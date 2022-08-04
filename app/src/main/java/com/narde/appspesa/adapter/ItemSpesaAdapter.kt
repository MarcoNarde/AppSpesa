package com.narde.appspesa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.narde.appspesa.R
import com.narde.appspesa.model.Alimento

class ItemSpesaAdapter(private val items: ArrayList<Alimento>) : RecyclerView.Adapter<ItemSpesaAdapter.viewHolder>() {
    class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNome : TextView
        val tvUltimoPrezzo : TextView
        val tvPrezzoMedio : TextView
        val tvGenere : TextView

        init{
            tvNome = view.findViewById(R.id.item_spesa_nome)
            tvUltimoPrezzo = view.findViewById(R.id.item_spesa_ultimo_prezzo)
            tvPrezzoMedio = view.findViewById(R.id.item_spesa_prezzo_medio)
            tvGenere = view.findViewById(R.id.item_spesa_genere)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSpesaAdapter.viewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_spesa, parent, false)

        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val prodotto = items[position]

        holder.tvNome.text = prodotto.nome
        holder.tvUltimoPrezzo.text = prodotto.prezzoUltimo.toString()
        holder.tvPrezzoMedio.text = prodotto.prezzoMedio.toString()
        holder.tvGenere.text = prodotto.genere
    }

    override fun getItemCount() = items.size
}