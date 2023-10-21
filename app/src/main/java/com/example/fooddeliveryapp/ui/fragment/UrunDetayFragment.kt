package com.example.fooddeliveryapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentUrunDetayBinding
import com.example.fooddeliveryapp.ui.viewmodel.UrunDetayViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UrunDetayFragment : Fragment() {

    private lateinit var binding: FragmentUrunDetayBinding
    private lateinit var viewModel: UrunDetayViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_urun_detay, container, false)
        binding.urunDetayFragment = this

        val bundle:UrunDetayFragmentArgs by navArgs()
        binding.yemekNesne = bundle.yemek
        val yemek = bundle.yemek

        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
        Glide.with(binding.imageViewYemekDetay).load(imageUrl).into(binding.imageViewYemekDetay)

        binding.buttonDetaySepeteEkle.setOnClickListener {
            viewModel.sepeteYemekEkle(yemek.yemek_adi, yemek.yemek_resim_adi, yemek.yemek_fiyat)

            Toast.makeText(requireContext(), "${yemek.yemek_adi} sepete eklendi!", Toast.LENGTH_SHORT).show()
        }

        binding.textViewMiktar.text = "1"

        viewModel.miktar.observe(viewLifecycleOwner) { deger ->
            binding.textViewMiktar.text = deger.toString()

            val birimFiyat = bundle.yemek.yemek_fiyat.toString().toDoubleOrNull() ?: 0.0
            val toplamFiyat = birimFiyat * deger
            binding.textViewDetayToplamFiyat.text = String.format("₺ %.2f", toplamFiyat)

            if (deger <= 1) {
                binding.buttonEksi.alpha = 0.1f
                binding.buttonEksi.isClickable = false
            } else {
                binding.buttonEksi.alpha = 1.0f
                binding.buttonEksi.isClickable = true
            }
        }

        binding.buttonArti.setOnClickListener {
            viewModel.miktarArttir()
        }

        binding.buttonEksi.setOnClickListener {
            viewModel.miktarAzalt()
        }


        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: UrunDetayViewModel by viewModels()
        viewModel = tempViewModel
    }

}