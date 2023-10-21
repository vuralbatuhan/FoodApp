package com.example.fooddeliveryapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentGirisYapBinding
import com.example.fooddeliveryapp.utils.gecis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GirisYapFragment : Fragment() {

    private lateinit var binding: FragmentGirisYapBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGirisYapBinding.inflate(inflater, container, false)

        auth = Firebase.auth

        binding.textViewKayit.setOnClickListener {
            val gecis = GirisYapFragmentDirections.girisGecisKayit()
            Navigation.gecis(binding.buttonGirisYap,gecis)
        }

        binding.buttonGirisYap.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val sifre = binding.editTextSifre.text.toString()

            if(email.isNotBlank() || sifre.isNotBlank()) {
                auth.signInWithEmailAndPassword(email,sifre).addOnSuccessListener {
                    val gecis = GirisYapFragmentDirections.girisGecisAna()
                    Navigation.gecis(binding.buttonGirisYap,gecis)
                }.addOnFailureListener{
                    Toast.makeText(requireContext(),"Lutfen bilgilerinizi kontrol ediniz",Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(requireContext(),"Lutfen bosluklari doldurunuz!",Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}