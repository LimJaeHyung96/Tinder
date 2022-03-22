package com.example.fastcampus_13

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    private val emailEditText : EditText by lazy {
        findViewById(R.id.emailEditText)
    }

    private val passwordEditText : EditText by lazy {
        findViewById(R.id.passwordEditText)
    }

    private val loginButton : Button by lazy {
        findViewById(R.id.loginButton)
    }

    private val signUpButton: Button by lazy {
        findViewById(R.id.signUpButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth //FirebaseAuth.getInstance()랑 같음

        initLoginButton()
        initSignUpButton()
        initEmailAndPasswordEditText()
    }

    private fun initSignUpButton() {
        signUpButton.setOnClickListener {
            val email = getInputEmail()
            val password = getPasswordEmail()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if(it.isSuccessful){
                        //메인 위에 띄운 로그인 페이지만 finish 해 줌
                        finish()
                    } else {
                        Toast.makeText(this, "로그인에 실패했습니다. 이메일 또는 패스워드를 확인해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun initLoginButton() {
        loginButton.setOnClickListener {
            val email = getInputEmail()
            val password = getPasswordEmail()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if(it.isSuccessful){
                        Toast.makeText(this, "회원가입에 성공했습니다. 로그인 버튼을 눌러 로그인 해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun initEmailAndPasswordEditText() {
        emailEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
            passwordEditText.isEnabled = enable
        }

        passwordEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
            passwordEditText.isEnabled = enable
        }
    }

    private fun getInputEmail() : String {
        return emailEditText.text.toString()
    }

    private fun getPasswordEmail() : String {
        return passwordEditText.text.toString()
    }
}