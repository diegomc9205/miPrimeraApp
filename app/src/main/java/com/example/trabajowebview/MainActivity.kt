package com.example.trabajowebview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.trabajowebview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            onBackPressedDispatcher.addCallback(
                this@MainActivity,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        println("Back button pressed")
                        if (WebView.canGoBack())
                            WebView.goBack()
                        else
                            finish()
                    }

                })

            val ajustes = webView.settings
            //Permite el uso de JS. Por defecto está desactivado.
            ajustes.javaScriptEnabled = true
            ajustes.setSupportZoom(true)
            ajustes.displayZoomControls = true
            ajustes.builtInZoomControls = true


            webView.loadUrl("https://marca.com")
            webView.webViewClient = MyWebViewClient()
            webView.webChromeClient = MyChrome()


//            MyWebViewClient()
//            MyChrome()

        }

    }

    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val url = request?.url
            val host = url?.host
            if (host!!.contains("as.com"))
                return false
            else {
                val intent = Intent(Intent.ACTION_VIEW, url)
                startActivity(intent)
                return true
            }
        }
    }

    inner class MyChrome : WebChromeClient() {


        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            binding.URL.text = title
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            binding.progressBar.visibility = View.VISIBLE
            binding.progressBar.progress = newProgress
            if (newProgress == 100)
                binding.progressBar.visibility = View.GONE
        }
    }
}