package com.maximlysik.myapplication.screens



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.maximlysik.domain.entities.User
import com.maximlysik.myapplication.databinding.ActivityLoginBinding
import com.maximlysik.myapplication.screens.common.activities.BaseActivity
import kotlinx.coroutines.launch
import javax.inject.Inject


class LoginActivity : BaseActivity() {

    @Inject
    lateinit var ourViewModelFactory: OurViewModelFactory
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injector.inject(this)
        loginViewModel = ViewModelProvider(this, ourViewModelFactory).get(LoginViewModel::class.java)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            login()
        }
    }


    // Проверяем строки на пустоту, добавляем пользователя в бд + заносим имя текущего юзера в sharedPreferences для дальнейшего использования

    fun login(){


        val login: String = binding.editTextLogin.text.toString()
        val password: String = binding.etPassword.text.toString()

        val sharedPreference =
            getSharedPreferences("USER_NAME", Context.MODE_PRIVATE)

        if(login.length>0 && password.length >0
        ) {
            lifecycleScope.launch {
                loginViewModel.addUser(User(binding.editTextLogin.text.toString(), binding.etPassword.text.toString()))
            }
            sharedPreference.edit().putString("Current_user", binding.editTextLogin.text.toString()).commit()

            val myIntent = Intent(this@LoginActivity, MainActivity::class.java)
            myIntent.putExtra("user_login", login) //Optional parameters
            this@LoginActivity.startActivity(myIntent)

            Toast.makeText(applicationContext, "Success!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(applicationContext, "Both field should be filled!", Toast.LENGTH_SHORT).show()
        }

    }


}