package com.example.anthony.rover;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.VideoView;

public class MainActivity extends Fragment {

    public MainActivity() {

    }

    public WebView webView;
    public String url;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        webView = (WebView) view.findViewById(R.id.webView);
        //webView.getSettings().setBuiltInZoomControls(true);
        //webView.getSettings().setSupportZoom(true);
        //webView.getSettings().setUseWideViewPort(true);
        //webView.getSettings().setLoadWithOverviewMode(true);
        webView.setInitialScale(50);
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView.getSettings().setUseWideViewPort(true);
        //webView.getSettings();
        url = "http://192.168.12.1:9090/stream";
        webView.loadUrl(url);



    }
}
