package com.example.fooddeliveryapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fooddeliveryapp.data.entity.SepetYemekler
import com.example.fooddeliveryapp.data.entity.Yemekler
import com.example.fooddeliveryapp.data.repo.SepetYemeklerRepository
import com.example.fooddeliveryapp.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnasayfaViewModel @Inject constructor(var yrepo: YemeklerRepository, var syrepo: SepetYemeklerRepository) : ViewModel() {

    var yemeklerListesi = MutableLiveData<List<Yemekler>>()

    init {
        tumYemekleriGetir()
    }

    fun tumYemekleriGetir(){
        CoroutineScope(Dispatchers.Main).launch {
            yemeklerListesi.value = yrepo.tumYemekleriGetir()
        }
    }

    suspend fun sepetYemekKontrol(yemek_adi: String): SepetYemekler? {
        val cartItems = syrepo.sepettekiYemekleriGetir()
        return cartItems.find { it.yemek_adi == yemek_adi }
    }

    fun sepeteYemekEkle(yemek_adi: String,
                        yemek_resim_adi: String,
                        yemek_fiyat: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val mevcutYemek = sepetYemekKontrol(yemek_adi)
            if (mevcutYemek != null) {
                syrepo.sepettenYemekSil(mevcutYemek.sepet_yemek_id)
                syrepo.sepeteYemekEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, mevcutYemek.yemek_siparis_adet + 1)
            } else {
                syrepo.sepeteYemekEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, 1)
            }
        }
    }

}