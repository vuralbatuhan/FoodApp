package com.example.fooddeliveryapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentSepetBinding
import com.example.fooddeliveryapp.ui.adapter.SepetYemeklerAdapter
import com.example.fooddeliveryapp.ui.viewmodel.SepetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SepetFragment : Fragment() {

    private lateinit var binding: FragmentSepetBinding
    private lateinit var viewModel: SepetViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sepet, container, false)
        binding.sepetFragment = this

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SepetViewModel by viewModels()
        viewModel = tempViewModel

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sepettekiYemekleriGetir()
        viewModel.sepetYemeklerListesi.observe(viewLifecycleOwner){
            val adapter = SepetYemeklerAdapter(requireContext(),it,viewModel)
            binding.sepetYemeklerAdapter = adapter

            val toplamFiyat = viewModel.toplamFiyatiHesapla()
            binding.textViewToplamTutar.text = "₺ $toplamFiyat"
        }
    }

}