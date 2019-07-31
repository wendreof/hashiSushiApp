package design.wendreo.hashisushi.views.policyPrivacy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import design.wendreo.hashisushi.R;

public class ActPolicyPrivacy extends AppCompatActivity {
	
	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.activity_act_policy_privacy );
		
		WebView webView = findViewById(R.id.wv_content);
		webView.loadUrl("file:///android_asset/politica_privacidade.html");
	}
}
