package com.example.moham.testandroidapp.ui.login

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.example.moham.testandroidapp.MyWebViewFragment
import com.bulutsoft.attendance.R


class RegisterActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)


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

