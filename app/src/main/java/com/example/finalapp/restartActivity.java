package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class restartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart);
        Button Restart = findViewById(R.id.Restart); // надо получить кнопку для новой игры
        Button Exit = findViewById(R.id.Exit); // надо получить кнопку для выхода из приложения
        Restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //тут логика обработки функцией или напрямую можно
                finish();
                GameView.gameRunning = true;
                Intent intent = new Intent(restartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //тут логика закрытия приложения
                finish();
                System.exit(0);
                finish();
                System.exit(0);
            }
        });

    }

}