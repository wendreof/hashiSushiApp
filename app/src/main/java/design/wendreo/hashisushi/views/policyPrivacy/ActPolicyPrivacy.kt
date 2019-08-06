package design.wendreo.hashisushi.views.policyPrivacy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView

import design.wendreo.hashisushi.R

class ActPolicyPrivacy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_act_policy_privacy)

        val webView = findViewById<WebView>(R.id.wv_content)
        webView.loadUrl("file:///android_asset/politica_privacidade.html")
    }
}
