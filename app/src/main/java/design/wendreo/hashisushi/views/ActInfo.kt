package design.wendreo.hashisushi.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import design.wendreo.hashisushi.R
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class ActInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_info)
        supportActionBar!!.hide()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    fun openBrowser(view: View) {

        //Get url from tag
        val url = view.tag as String

        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)

        //pass the url to intent data
        intent.data = Uri.parse(url)

        startActivity(intent)
    }
}
