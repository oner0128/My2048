package com.example.hrong.my2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hrong on 2016/4/6.
 */
public class GameView extends GridLayout {
    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    public void initGameView() {
        setColumnCount(4);
        setRowCount(4);
        setBackgroundColor(0xffbbada0);
        LayoutParams lp=new LayoutParams();
        lp.setMargins(0,0,0,0);
        setLayoutParams(lp);

        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                }
                if (Math.abs(offsetX) > Math.abs(offsetY)) {
                    if (offsetX > 5) {
                        swipeRight();
                    } else if (offsetX < -5)
                        swipeLeft();
                } else {
                    if (offsetY < -5) {
                        swipeUp();
                    } else if (offsetY > 5)
                        swipeDown();
                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (int) ((Math.min(w, h)-30) / 4);
        addCards(cardWidth, cardWidth);
        startGame();
    }

    public void startGame() {

//       MainActivity.getMainActivity().clearScore();此代码导致GameView出现两次
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardMap[x][y].setNum(0);
            }
        }
        addRandomNums();
        addRandomNums();
    }

    private ScaleAnimation aa = new ScaleAnimation(0, 1.2f, 0, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    private void addRandomNums() {
        emptyCard.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardMap[x][y].getNum() == 0)
                    emptyCard.add(cardMap[x][y]);
            }
        }
        Card card = emptyCard.remove((int) (Math.random() * emptyCard.size()));
        card.setNum(Math.random() > 0.1 ? 2 : 4);
        aa.setDuration(200);
        card.label.startAnimation(aa);
        flap = false;
    }


    private void addCards(int cardWidth, int cardHeight) {
        Card card;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(cardWidth, cardHeight);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                card = new Card(getContext());
                addView(card, layoutParams);
                cardMap[x][y] = card;
            }
        }
    }

    private Card[][] cardMap = new Card[4][4];
    private List<Card> emptyCard = new ArrayList<>();
    private static boolean flap = false;


    private void changeScore(int addscore) {

        MainActivity.getMainActivity().addScore(addscore);
    }

    private void checkFinish() {
        if (emptyCard.isEmpty()) {
            boolean flapFinish = true;
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    if ((x < 3 && cardMap[x][y].equals(cardMap[x + 1][y]))
                            || (x > 0 && cardMap[x][y].equals(cardMap[x - 1][y]))
                            || (y < 3 && cardMap[x][y].equals(cardMap[x][y + 1]))
                            || (y > 0 && cardMap[x][y].equals(cardMap[x][y - 1])))
                        flapFinish = false;
                }
            }
            if (flapFinish) {
                new AlertDialog.Builder(getContext()).setTitle("Game Over").setMessage("Your Score is " + MainActivity.getMainActivity().getScore()).setPositiveButton("ReStart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame();
                        MainActivity.getMainActivity().clearScore();
                    }
                }).show();
            }
        }
    }

    private void swipeDown() {
        System.out.println("Down");
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardMap[x][y1].getNum() != 0) {
                        if (cardMap[x][y].equals(cardMap[x][y1])) {
                            cardMap[x][y].setNum(cardMap[x][y1].getNum() * 2);
                            cardMap[x][y1].setNum(0);
                            changeScore(cardMap[x][y].getNum());
                            flap = true;
                        } else if (cardMap[x][y].getNum() == 0) {
                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y1].setNum(0);
                            y++;
                            flap = true;
                        }
                        break;
                    }
                }

            }
        }
        if (flap) {
            addRandomNums();
            checkFinish();
        }
    }

    private void swipeUp() {
        System.out.println("Up");
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardMap[x][y1].getNum() != 0) {
                        if (cardMap[x][y].equals(cardMap[x][y1])) {
                            cardMap[x][y].setNum(cardMap[x][y1].getNum() * 2);
                            cardMap[x][y1].setNum(0);
                            changeScore(cardMap[x][y].getNum());
                            flap = true;
                        } else if (cardMap[x][y].getNum() == 0) {
                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y1].setNum(0);
                            y--;
                            flap = true;
                        }
                        break;
                    }
                }

            }
        }
        if (flap) {
            addRandomNums();
            checkFinish();
        }
    }


    private void swipeLeft() {
        System.out.println("Left");
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cardMap[x1][y].getNum() != 0) {
                        if (cardMap[x][y].equals(cardMap[x1][y])) {
                            cardMap[x][y].setNum(cardMap[x1][y].getNum() * 2);
                            cardMap[x1][y].setNum(0);
                            changeScore(cardMap[x][y].getNum());
                            flap = true;
                        } else if (cardMap[x][y].getNum() == 0) {
                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x1][y].setNum(0);
                            x--;
                            flap = true;
                        }
                        break;
                    }
                }

            }
        }
        if (flap) {
            addRandomNums();
            checkFinish();
        }
    }


    private void swipeRight() {
        System.out.println("Right");
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardMap[x1][y].getNum() != 0) {
                        if (cardMap[x][y].equals(cardMap[x1][y])) {
                            cardMap[x][y].setNum(cardMap[x1][y].getNum() * 2);
                            cardMap[x1][y].setNum(0);
                            changeScore(cardMap[x][y].getNum());
                            flap = true;
                        } else if (cardMap[x][y].getNum() == 0) {
                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x1][y].setNum(0);
                            x++;
                            flap = true;
                        }
                        break;
                    }
                }

            }
        }
        if (flap) {
            addRandomNums();
            checkFinish();
        }
    }
}
