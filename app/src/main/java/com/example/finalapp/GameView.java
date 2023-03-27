package com.example.finalapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

    public static int maxX = 20; // размер по горизонтали
    public static int maxY = 28; // размер по вертикали
    public static float unitW = 0; // пикселей в юните по горизонтали
    public static float unitH = 0; // пикселей в юните по вертикали

    private boolean firstTime = true;
    public static boolean gameRunning = true;
    public static int score = 0;
    private Superman ship;
    private Thread gameThread = null;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;





    private ArrayList<Dot> asteroids = new ArrayList<>(); // тут будут харанится астероиды
    private final int ASTEROID_INTERVAL = 50; // время через которое появляются астероиды (в итерациях)
    private int currentTime = 0;

    public GameView(Context context) {
        super(context);
        //инициализируем обьекты для рисования
        surfaceHolder = getHolder();
        paint = new Paint();

        // инициализируем поток
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {
        while (gameRunning) {

            update();
            draw();
            checkCollision();
            checkIfNewAsteroid();
            control();
        }
    }

    private void update() {
        if(!firstTime) {
            ship.update();
            for (Dot asteroid : asteroids) {
                asteroid.update();
            }
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {  //проверяем валидный ли surface

            if(firstTime){ // инициализация при первом запуске
                firstTime = false;
                unitW = surfaceHolder.getSurfaceFrame().width()/maxX; // вычисляем число пикселей в юните
                unitH = surfaceHolder.getSurfaceFrame().height()/maxY;

                ship = new Superman(getContext()); // добавляем корабль
            }

            canvas = surfaceHolder.lockCanvas(); // закрываем canvas

            Bitmap notebookSheet = BitmapFactory.decodeResource(getResources(), R.drawable.background);
            canvas.drawBitmap(notebookSheet, 0, 0, null);

            ship.drow(paint, canvas); // рисуем корабль

            for(Dot asteroid: asteroids){ // рисуем астероиды
                asteroid.drow(paint, canvas);
            }

            surfaceHolder.unlockCanvasAndPost(canvas); // открываем canvas

        }
    }

    private void control() { // пауза на 17 миллисекунд
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkCollision(){ // перебираем все астероиды и проверяем не касается ли один из них корабля
        for (Dot asteroid : asteroids) {
            if(asteroid.isCollision(ship.x, ship.y, ship.size)){
                // игрок проиграл

                gameRunning = false; // останавливаем игру
                Intent intent = new Intent(getContext(),restartActivity.class);
                getContext().startActivity(intent);
                // TODO добавить анимацию взрыва
            }
            else
            {

                ++score;

            }
        }
    }

    private void checkIfNewAsteroid(){ // каждые 50 итераций добавляем новый астероид
        if(currentTime >= ASTEROID_INTERVAL){
            Dot asteroid = new Dot(getContext());
            asteroids.add(asteroid);
            currentTime = 0;
        }else{
            currentTime ++;
        }
    }

}
