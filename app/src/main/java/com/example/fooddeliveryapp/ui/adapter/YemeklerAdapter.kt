package com.example.fooddeliveryapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.data.entity.Yemekler
import com.example.fooddeliveryapp.databinding.CardDesignProductBinding
import com.example.fooddeliveryapp.ui.fragment.AnasayfaFragmentDirections
import com.example.fooddeliveryapp.ui.viewmodel.AnasayfaViewModel
import com.example.fooddeliveryapp.utils.gecis

class YemeklerAdapter(var mContext:Context, var yemeklerListesi:List<Yemekler>, var viewModel:AnasayfaViewModel)
    : RecyclerView.Adapter<YemeklerAdapter.CardDesignProductHolder>() {

    inner class CardDesignProductHolder(var design:CardDesignProductBinding) : RecyclerView.ViewHolder(design.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignProductHolder {
        val binding : CardDesignProductBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.card_design_product,parent,false)
        return CardDesignProductHolder(binding)
    }

    override fun getItemCount(): Int {
        return yemeklerListesi.size
    }

    override fun onBindViewHolder(holder: CardDesignProductHolder, position: Int) {
        val yemek = yemeklerListesi.get(position)
        val d = holder.design

        d.yemekNesne = yemek

        d.cardViewProduct.setOnClickListener{
            val gecis = AnasayfaFragmentDirections.anaGecisDetay(yemek = yemek)
            Navigation.gecis(it,gecis)
        }

        d.buttonEkle.setOnClickListener {
            viewModel.sepeteYemekEkle(yemek.yemek_adi, yemek.yemek_resim_adi, yemek.yemek_fiyat)
            Toast.makeText(mContext, "${yemek.yemek_adi} sepete eklendi!", Toast.LENGTH_SHORT).show()
        }

        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
        Glide.with(mContext).load(imageUrl).override(300,300).into(d.imageViewYemek)
    }

}