package com.example.fooddeliveryapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentKayitOlBinding
import com.example.fooddeliveryapp.utils.gecis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class KayitOlFragment : Fragment() {

    private lateinit var binding: FragmentKayitOlBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKayitOlBinding.inflate(inflater, container, false)

        auth = Firebase.auth

        binding.textViewGiris.setOnClickListener {
            val gecis = KayitOlFragmentDirections.kayitGecisGiris()
            Navigation.gecis(binding.buttonKayitOl,gecis)
        }

        binding.buttonKayitOl.setOnClickListener {

            val email = binding.editTextEmail.text.toString()
            val sifre = binding.editTextSifre.text.toString()

            if(email.isEmpty() || sifre.isEmpty()) {
                Toast.makeText(requireContext(),"Lutfen bosluklari doldurunuz!",Toast.LENGTH_SHORT).show()
            }else {
                auth.createUserWithEmailAndPassword(email,sifre).addOnSuccessListener {
                    Toast.makeText(requireContext(),"Kayit olundu.",Toast.LENGTH_SHORT).show()
                    val gecis = KayitOlFragmentDirections.kayitGecisGiris()
                    Navigation.gecis(binding.buttonKayitOl,gecis)
                }.addOnFailureListener{
                    Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
                }
            }

        }

        return binding.root
    }
}