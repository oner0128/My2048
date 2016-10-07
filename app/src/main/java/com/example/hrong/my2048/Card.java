package com.example.hrong.my2048;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by hrong on 2016/4/6.
 */
public class Card extends FrameLayout {
    private int num = 0;

    public Card(Context context) {
        super(context);
        label = new TextView(getContext());
        background = new TextView(getContext());
        background.setBackgroundColor(Color.parseColor("#BFBFBF"));
        label.setTextSize(32);
        label.setGravity(Gravity.CENTER);
        label.setBackgroundColor(Color.parseColor("#BFBFBF"));
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.setMargins(30, 30,0, 0);
        addView(background, layoutParams);
        addView(label, layoutParams);
        setNum(0);

    }

    public TextView label, background;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if (num == 0) {
            label.setText("");
            label.setBackgroundColor(Color.parseColor("#BFBFBF"));
        } else {
            label.setText(num + "");
            switch (num) {
                case 2:
                    label.setBackgroundColor(Color.GRAY);
                    break;
                case 4:
                    label.setBackgroundColor(Color.LTGRAY);
                    break;
                case 8:
                    label.setBackgroundColor(Color.TRANSPARENT);
                    break;
                case 16:
                    label.setBackgroundColor(Color.GREEN);
                    break;
                case 32:
                    label.setBackgroundColor(Color.YELLOW);
                    break;
                case 64:
                    label.setBackgroundColor(Color.CYAN);
                    break;
                case 128:
                    label.setBackgroundColor(Color.WHITE);
                    break;
                case 256:
                    label.setBackgroundColor(Color.TRANSPARENT);
                    break;
                case 512:
                    label.setBackgroundColor(Color.BLUE);
                    break;
                case 1024:
                    label.setBackgroundColor(Color.BLUE);
                    break;
                case 2048:
                    label.setBackgroundColor(Color.BLUE);
                    break;
                default:
                    break;
            }
        }
    }

    public boolean equals(Card card) {
        return num == card.getNum();
    }
}
