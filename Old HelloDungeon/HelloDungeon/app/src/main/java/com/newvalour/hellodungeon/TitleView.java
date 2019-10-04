package com.newvalour.hellodungeon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
/**
 * Created by Valek on 5/13/2015.
 * Title Screen for Hello Dungeon
 */

public class TitleView extends View {

    private Context myContext;
    private Bitmap titleScreen;
    private Bitmap playUp;
    private Bitmap playDown;
    private int screenW;
    private int screenH;
    private boolean playPressed;

    public TitleView(Context context)   {
        super(context);
        myContext = context;
        titleScreen = BitmapFactory.decodeResource(getResources(), R.drawable.titleview);
        playUp = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_up);
        playDown = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_down);
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenH = h;
        screenW = w;
    }
    /**
     * Draws the title screen
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas)    {
        canvas.drawBitmap(titleScreen, 0, 0, null);
        if(playPressed) {
            canvas.drawBitmap(playDown, (screenW - playDown.getWidth()) / 2, (int)(screenH * 2 / 3), null);
        }
        else    {
            canvas.drawBitmap(playUp, (screenW - playUp.getWidth()) / 2, (int)(screenH * 2 / 3), null);
        }
    }

    /**
     * Check touches for uses in the advancement of the game
     * @param event
     * @return true
     */
    public boolean onTouchEvent(MotionEvent event)  {
        int eventaction = event.getAction();
        int X = (int)event.getX();
        int Y = (int)event.getY();

        switch(eventaction)    {
            case MotionEvent.ACTION_DOWN:
                if((X > (screenW - playUp.getWidth()) / 2) &&
                   (X < ((screenW - playUp.getWidth()) / 2) + playUp.getWidth()) &&
                   (Y > (int)(screenH * 2 / 3)) &&
                   (Y < (int)(screenH * 2 / 3) + playUp.getHeight())) {
                    playPressed = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(playPressed) {
                    Intent gameIntent = new Intent(myContext, HDGameActivity.class);
                    myContext.startActivity(gameIntent);
                }
                playPressed = false;
                break;
        }

        invalidate();
        return true;
    }
}
