package com.defInit.tp1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.defInit.tp1.databinding.ActivityRegisterBinding
import com.google.gson.Gson

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSignUp.setOnClickListener {
            val user = binding.etUser.text.toString()
            val password = binding.etPass.text.toString()

            if (user.isNotBlank() && password.isNotBlank()) {
                val preferences = getSharedPreferences(CREDENTIALS, MODE_PRIVATE)
                val edit = preferences.edit()

                val persona = Persona(userName = user, password = password)
                val gson = Gson()
                //edit.putString("user", user)
                //edit.putString("password", password)

                val personaInJsonFormat = gson.toJson(persona)
                edit.putString("person", personaInJsonFormat)

                edit.apply()
                goToMainActivity()
            } else {
                Toast.makeText(this, "Debe completar todos los campos!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val CREDENTIALS = "Credenciales"
    }
}