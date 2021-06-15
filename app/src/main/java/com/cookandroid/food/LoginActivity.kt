package com.cookandroid.food

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()


        val id = "hansung"
        val password = "1234"
        val editId = findViewById<View>(R.id.edit_id) as EditText
        val editPw = findViewById<View>(R.id.edit_pw) as EditText
        val login = findViewById<View>(R.id.btn_login) as Button
        login.setOnClickListener {
            val inputLogin = editId.text.trim().toString()
                val inputPassword = editPw.text.trim().toString()
                if (!(inputLogin != id || inputPassword != password)) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("name", id)
                    startActivity(intent)
                } else {
                    if (inputLogin.isNullOrEmpty() && inputPassword.isNullOrEmpty()) {
                        Toast.makeText(this, "ID와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                    } else if (inputLogin != id) {
                        Toast.makeText(this, "존재하지 않는 ID입니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                    }
                }


            }





        }



    }
