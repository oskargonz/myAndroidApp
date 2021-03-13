package com.example.listazakupw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val auth = FirebaseAuth.getInstance() //na wykladzie to bylo definiowane wczesniej nad onCreate jako private lateinit var mAuth: FirebaseAuth

        bt_loginRegister.setOnClickListener{
            auth.signInWithEmailAndPassword(et_login.text.toString(), et_password.text.toString())
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this, "Zalogowano.", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, MainActivity::class.java).also {
                                it.putExtra("user", auth.currentUser?.email)//mogę sobie przekazac całego usera a nie tylko email - auth.currentUser
                            })//na wykladzie inaczej byl tworzony intent (bez also)
                        }else{
                            Toast.makeText(this, "Błąd logowania.", Toast.LENGTH_LONG).show()
                        }
                    }
        }


        bt_loginRegister.setOnLongClickListener{
            auth.createUserWithEmailAndPassword(et_login.text.toString(), et_password.text.toString())
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this, "Rejestracja przebiegła prawidłowo.", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this, "Błąd rejestracji.", Toast.LENGTH_LONG).show()
                        }
                    }

            true
        }

        btPublicList.setOnClickListener{
            startActivity(Intent(this, PublicListActivity::class.java))

        }
    }


}