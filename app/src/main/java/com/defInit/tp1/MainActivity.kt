package com.defInit.tp1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.defInit.tp1.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var preferences: SharedPreferences

    //private val preferences = getSharedPreferences(RegisterActivity.CREDENTIALS, MODE_PRIVATE)
    //private val edit = preferences.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(RegisterActivity.CREDENTIALS, MODE_PRIVATE)
        val edit = preferences.edit()

        val autoLogin = preferences.getBoolean("autoLogin", false)
        if (autoLogin) {
            goToHomeActivity()
        }

        binding.btnSignIn.setOnClickListener {
            val checkBox = binding.checkbox.isChecked
            val user = binding.etUser.text.toString()
            val password = binding.etPass.text.toString()

            //edit.putBoolean("autoLogin", checkBox)
            //edit.apply()

            if (validateAutoLogin()) {
                goToHomeActivity()
            } else {
                if (validateData(user, password)) {
                    edit.putBoolean("autoLogin", checkBox)
                    edit.apply()
                    goToHomeActivity()
                } else {
                    Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_SHORT).show()
                    edit.putBoolean("autoLogin", false)
                    edit.apply()
                }
            }


        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun validateAutoLogin(): Boolean {
        //val autoLogin = preferences.getBoolean("autoLogin", false)
        return preferences.getBoolean("autoLogin", false)

    }

    private fun goToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val autoLogin = preferences.getBoolean("autoLogin", false)
        if (autoLogin) {
            goToHomeActivity()
        }
    }

    private fun validateData(user: String, password: String): Boolean {

        var person = Persona("", "")
        try {
            //val preferences = getSharedPreferences(RegisterActivity.CREDENTIALS, MODE_PRIVATE)
            val personJson = preferences.getString("person", "")
            val gson = Gson()

            person = gson.fromJson(personJson, Persona::class.java)

        } catch (e: Exception) {
            e.printStackTrace()
        }


        return user == person.userName && password == person.password

     }
}