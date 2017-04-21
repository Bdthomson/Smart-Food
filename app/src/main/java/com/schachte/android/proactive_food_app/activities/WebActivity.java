package com.schachte.android.proactive_food_app.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.database.SqlQueries;

public class WebActivity extends AppCompatActivity {

        /** Called when the activity is first created. */

    private WebView web;
    private final String defaultUrl = "https://spoonacular.com/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        String urlToLoad;

        if( getIntent() != null ) {
            String recipeUrl = getIntent().getStringExtra(SqlQueries.RECIPE_SOURCE_URL);
            urlToLoad = recipeUrl;
        } else
            urlToLoad = defaultUrl;

        web = (WebView) findViewById(R.id.webView);
        web.setWebViewClient(new myWebClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(urlToLoad);
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }
    }
}
