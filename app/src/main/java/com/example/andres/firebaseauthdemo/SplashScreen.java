package com.example.andres.firebaseauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        Thread hilo= new Thread(){

            @Override
            public void run(){

                try {
                    sleep(4000);

                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        };

        hilo.start();

    }


}
