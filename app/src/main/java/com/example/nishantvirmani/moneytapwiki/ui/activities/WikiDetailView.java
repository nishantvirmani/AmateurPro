package com.example.nishantvirmani.moneytapwiki.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.nishantvirmani.moneytapwiki.R;


public class WikiDetailView extends AppCompatActivity {
    private String newsUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);

        initData();

        WebView mywebview = (WebView) findViewById(R.id.webView);
        mywebview.setWebViewClient(new WebViewClient());
        mywebview.loadUrl(newsUrl);
    }

    private void initData() {
        newsUrl = getIntent().getStringExtra("url");
    }
}
