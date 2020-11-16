package com.flyinkk.flyingforestbird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View {

    public static boolean IS_RUNNING = false;

    //Canvas
    private int canvasWidth;
    private int canvasHeight;

    //Blue balls
    private int blueX;
    private int blueY;
    private int blueBallSpeed = 15;
    private Paint blueBallPaint = new Paint();


    //Black balls
    private int blackX;
    private int blackY;
    private int blackBallSpeed = 15;
    private Paint blackBallPaint = new Paint();


    //bird
    private Bitmap bird[] = new Bitmap[2];
    private int birdX = 10;
    private int birdY = 500;
    private int birdSpeed;
    private int minBirdY;
    private int maxBirdY;

    //background
    private Bitmap background;

    //Score
    private Paint scorePaint = new Paint();
    private int score = 0;

    //Lvl
    private Paint lvlPaint = new Paint();

    //life
    private Bitmap lifes[] = new Bitmap[2];
    private int life_count = 3;

    //Screen
    private int screenX;
    private int screenY;

    //Status check
    private boolean touch_flag = false;

    private MainActivity mainActivity;

    public MainView(Context context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        IS_RUNNING = true;
        initElement();
        mainActivity = (MainActivity) context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);
        canvas.drawBitmap(background, 0, 0, null);

        //Bird
        minBirdY = bird[0].getHeight();
        maxBirdY = canvasHeight - bird[0].getHeight();
        birdY += birdSpeed;
        if (birdY < minBirdY) {
            birdY = minBirdY;
        } else if (birdY > maxBirdY) {
            birdY = maxBirdY;
        }
        birdSpeed += 4;
        if (touch_flag) {
            canvas.drawBitmap(bird[1], birdX, birdY, null);
            touch_flag = false;
        } else {
            canvas.drawBitmap(bird[0], birdX, birdY, null);
        }

        blueBallRun(canvas);
        blackBallRun(canvas);

        canvas.drawText("Score : " + score, 20, 60, scorePaint);
        canvas.drawText("Lvl. 1", canvasWidth / 2, 60, lvlPaint);

        //Life
        for (int i = 0; i < 3; i++) {
            int x = (int) (canvasWidth - 350 + lifes[0].getWidth() * 1.5 * i);
            int y = 30;
            if (i < life_count) {
                canvas.drawBitmap(lifes[0], x, y, null);
            } else {
                canvas.drawBitmap(lifes[1], x, y, null);
            }
        }
    }

    private boolean hit(int ballX, int ballY) {
        if (birdX < ballX && ballX < birdX + bird[0].getWidth() && birdY
                < ballY && ballY < birdY + bird[0].getHeight()) {
            return true;
        }
        return false;
    }

    private void blackBallRun(Canvas canvas) {
        //Black balls
        blackX -= blackBallSpeed;
        if (hit(blackX, blackY)) {
            blackX -= 10000;
            life_count --;
            if (life_count == 0) {
                mainActivity.callNewActivity(score);
            }
        } else if (blackX < 0) {
            blackX = canvasWidth + 200;
            blackY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }

        canvas.drawCircle(blackX, blackY, 20, blackBallPaint);
    }

    private void blueBallRun(Canvas canvas) {
        //Blue balls
        blueX -= blueBallSpeed;
        if (hit(blueX, blueY)) {
            score += 10;
            blueX = -10000;
        }
        if (blueX < 0) {
            blueX = canvasWidth + 20;
            blueY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }
        canvas.drawCircle(blueX, blueY, 8, blueBallPaint);
    }

    private void initElement() {
        bird[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird1);
        bird[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird2);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.bg);

        blueBallPaint.setColor(Color.BLUE);
        blueBallPaint.setAntiAlias(false);

        blackBallPaint.setColor(Color.BLACK);
        blackBallPaint.setAntiAlias(false);

        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(32);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        lvlPaint.setColor(Color.DKGRAY);
        lvlPaint.setTextSize(32);
        lvlPaint.setTypeface(Typeface.DEFAULT_BOLD);
        lvlPaint.setAntiAlias(true);
        lvlPaint.setTextAlign(Paint.Align.CENTER);

        lifes[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        lifes[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_g);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch_flag = true;
            birdSpeed = -30;
        }
        return true;
    }

}
