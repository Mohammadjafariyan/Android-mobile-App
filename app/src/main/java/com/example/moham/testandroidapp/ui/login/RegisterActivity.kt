package com.example.moham.testandroidapp.ui.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import com.example.moham.testandroidapp.MyWebViewFragment
import com.example.moham.testandroidapp.R
import com.example.moham.testandroidapp.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Get FragmentManager
        // Get FragmentManager
        val fragmentManager = supportFragmentManager

          // Create and add the fragment dynamically
        val myFragment = MyWebViewFragment(this)

        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, myFragment)
            .commit()


    }



}

