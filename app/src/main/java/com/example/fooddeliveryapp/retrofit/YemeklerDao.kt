package com.example.fooddeliveryapp.retrofit

import com.example.fooddeliveryapp.data.entity.YemeklerCevap
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface YemeklerDao {

    @GET("/yemekler/tumYemekleriGetir.php")
    suspend fun  tumYemekleriGetir() : YemeklerCevap

}