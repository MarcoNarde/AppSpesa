package com.narde.appspesa.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.narde.appspesa.R
import com.narde.appspesa.model.Prodotto
import com.google.firebase.storage.FirebaseStorage

import com.google.firebase.storage.StorageReference
import com.narde.appspesa.`interface`.OnItemClickListener
import java.io.File


class ItemSpesaAdapter(private val items: ArrayList<Prodotto>, private val mOnItemClickListener: OnItemClickListener) : RecyclerView.Adapter<ItemSpesaAdapter.viewHolder>() {

    class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNome : TextView
        val tvUltimoPrezzo : TextView
        val tvPrezzoMedio : TextView
        val tvGenere : TextView
        val ivImmagine: ImageView
        val cvMainLayout: MaterialCardView

        init{
            tvNome = view.findViewById(R.id.item_spesa_nome)
            tvUltimoPrezzo = view.findViewById(R.id.item_spesa_ultimo_prezzo)
            tvPrezzoMedio = view.findViewById(R.id.item_spesa_prezzo_medio)
            tvGenere = view.findViewById(R.id.item_spesa_genere)
            ivImmagine = view.findViewById(R.id.item_spesa_image)
            cvMainLayout = view.findViewById(R.id.item_spesa_main_layout)
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

        val storageRef: StorageReference =
            FirebaseStorage.getInstance().reference.child("Immagini/${prodotto.immagine}")
        var file = File.createTempFile("tempImage", "jpg")
        storageRef.getFile(file).addOnSuccessListener {
            var bitmap = BitmapFactory.decodeFile(file.absolutePath)

            holder.ivImmagine.setImageBitmap(bitmap)
        }.addOnFailureListener{
        }

        holder.cvMainLayout.isChecked = prodotto.isComprato
        holder.cvMainLayout.setOnClickListener{
            mOnItemClickListener.onItemClick(it,position)

            /*var itemBefore = prodotto
            var itemAfter = prodotto
            itemAfter.isComprato = !itemAfter.isComprato

            items.remove(itemBefore)
            items.add(itemAfter)

            notifyDataSetChanged()*/
        }
    }

    override fun getItemCount() = items.size

}