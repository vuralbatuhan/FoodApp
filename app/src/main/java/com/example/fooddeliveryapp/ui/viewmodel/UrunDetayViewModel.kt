package com.example.fooddeliveryapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fooddeliveryapp.data.entity.SepetYemekler
import com.example.fooddeliveryapp.data.repo.SepetYemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UrunDetayViewModel @Inject constructor(var syrepo: SepetYemeklerRepository) : ViewModel() {

    val miktar = MutableLiveData(1)

    fun miktarArttir() {
        miktar?.value = miktar?.value?.plus(1)
    }

    fun miktarAzalt() {
        if ((miktar.value ?: 1) > 1)
            miktar.value = miktar.value!! - 1
    }

    suspend fun sepetYemekKontrol(yemek_adi: String): SepetYemekler? {
        val sepetYemek = syrepo.sepettekiYemekleriGetir()
        return sepetYemek.find { it.yemek_adi == yemek_adi }
    }

    fun sepeteYemekEkle(yemek_adi: String,
                        yemek_resim_adi: String,
                        yemek_fiyat: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val mevcutYemek = sepetYemekKontrol(yemek_adi)
            if (mevcutYemek != null) {
                syrepo.sepettenYemekSil(mevcutYemek.sepet_yemek_id)
                syrepo.sepeteYemekEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, mevcutYemek.yemek_siparis_adet + miktar.value!!)
            } else {
                syrepo.sepeteYemekEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, miktar.value!!)
            }
        }
    }

}