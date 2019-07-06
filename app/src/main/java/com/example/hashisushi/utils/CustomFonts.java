package com.example.hashisushi.utils;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;

public class CustomFonts extends AppCompatActivity {
	
	//Altera fonte do txtLogo
	public Typeface fontLogo ( ) {
		Typeface font = Typeface.createFromAsset ( getAssets ( ), "RagingRedLotusBB.ttf" );
		return font;
	}
}
