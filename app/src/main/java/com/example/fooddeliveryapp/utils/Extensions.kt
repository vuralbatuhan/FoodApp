package com.example.fooddeliveryapp.utils

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

fun Navigation.gecis(it:View,id: NavDirections){
    Navigation.findNavController(it).navigate(id)
}