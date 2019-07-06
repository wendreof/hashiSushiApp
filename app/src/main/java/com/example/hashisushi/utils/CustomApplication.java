/**
 * Classe usa api calligraphy para configurar
 * as fontes do projeto .
 * Usamos a fonte RobotoSlab-Regular como
 * fonte principal.
 * E Japonesa e samurai como fonts secundarias
 */
package com.example.hashisushi.utils;

import android.app.Application;

import com.example.hashisushi.R;
import com.onesignal.OneSignal;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class CustomApplication extends Application {
	@Override
	public void onCreate ( ) {
		super.onCreate ( );
		CalligraphyConfig.initDefault ( new CalligraphyConfig.Builder ( )
				.setDefaultFontPath ( "RobotoSlab-Regular.ttf" )
				//.setDefaultFontPath( "Japonesa.ttf" )
				//.setDefaultFontPath("RagingRedLotusBB.ttf")
				.setFontAttrId ( R.attr.fontPath )
				.build ( ) );
		// OneSignal Initialization
		OneSignal.startInit ( this )
				.inFocusDisplaying ( OneSignal.OSInFocusDisplayOption.Notification )
				.unsubscribeWhenNotificationsAreDisabled ( true )
				.init ( );
		
		String email = "example@domain.com";
		String emailAuthHash = "..."; // Email auth hash generated from your server
		OneSignal.setEmail ( email, emailAuthHash, new OneSignal.EmailUpdateHandler ( ) {
			@Override
			public void onSuccess ( ) {
				// Email successfully synced with OneSignal
			}
			
			@Override
			public void onFailure ( OneSignal.EmailUpdateError error ) {
				// Error syncing email, check error.getType() and error.getMessage() for details
			}
		} );
	}
}