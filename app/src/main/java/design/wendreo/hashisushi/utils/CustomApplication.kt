/**
 * Classe usa api calligraphy para configurar
 * as fontes do projeto .
 * Usamos a fonte RobotoSlab-Regular como
 * fonte principal.
 * E Japonesa e samurai como fonts secundarias
 */
package design.wendreo.hashisushi.utils

import android.app.Application

import design.wendreo.hashisushi.R
import com.onesignal.OneSignal

import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("RobotoSlab-Regular.ttf")
                //.setDefaultFontPath( "Japonesa.ttf" )
                //.setDefaultFontPath("RagingRedLotusBB.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())
        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init()

        val email = "example@domain.com"
        val emailAuthHash = "..." // Email auth hash generated from your server
        OneSignal.setEmail(email, emailAuthHash, object : OneSignal.EmailUpdateHandler {
            override fun onSuccess() {
                // Email successfully synced with OneSignal
            }

            override fun onFailure(error: OneSignal.EmailUpdateError) {
                // Error syncing email, check error.getType() and error.getMessage() for details
            }
        })
    }
}