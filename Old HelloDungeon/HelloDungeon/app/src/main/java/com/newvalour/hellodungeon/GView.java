package com.newvalour.hellodungeon;

import android.view.View;
import android.graphics.Canvas;
import android.content.Context;
import android.view.MotionEvent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import java.util.ArrayList;

/**
 * Created by Valek on 5/19/2015.
 */
public class GView extends View {
    private ArrayList<Bitmap> pSprites;
    private Sprite player;
    private boolean pMove;
    private int direction;
    private double x;
    private double y;
    private int dpadSize;
    private Bitmap dpad;
    private Rect dpadSect;
    private Bitmap attUp;
    private Bitmap attDown;
    private Rect attLoc;
    private boolean attPressed;
    private Bitmap actUp;
    private Bitmap actDown;
    private Rect actLoc;
    private boolean actPressed;
    private int screenW;
    private int screenH;

    public GView(Context context)   {
        super(context);
        pMove = false;
        direction = 2;
        screenH = 1080;
        screenW = 1920;
        pSprites = new ArrayList<Bitmap>();
        attUp = BitmapFactory.decodeResource(getResources(), R.drawable.attbuttonup);
        attDown = BitmapFactory.decodeResource(getResources(), R.drawable.attbuttondown);
        actUp = BitmapFactory.decodeResource(getResources(), R.drawable.actbuttonup);
        actDown = BitmapFactory.decodeResource(getResources(), R.drawable.actbuttondown);
        dpad = BitmapFactory.decodeResource(getResources(), R.drawable.d_pad);
        pSprites.add(BitmapFactory.decodeResource(getResources(), R.drawable.swordwalk));
        pSprites.add(BitmapFactory.decodeResource(getResources(), R.drawable.swordswing));
        pSprites.add(BitmapFactory.decodeResource(getResources(), R.drawable.sworddeath));
        dpadSize = 312;
        dpadSect = new Rect(0, 0, dpadSize, dpadSize);
        attLoc = new Rect(920, 572, 1048, 700);
        actLoc = new Rect(1048, 444, 1176, 572);
        int[] maxF = {9, 6, 6};
        player = new Sprite(pSprites, maxF);
    }

    @Override
    protected void onDraw(Canvas canvas)    {

        canvas.drawARGB(255, 0, 0, 0);

        //Draw D-Pad
        canvas.drawBitmap(dpad, dpadSect, new Rect(0, screenH - dpadSize, dpadSize, screenH), null);
        //Draw Attack Button
        if(attPressed)  {
            canvas.drawBitmap(attDown, null, attLoc, null);
        }
        else    {
            canvas.drawBitmap(attUp, null, attLoc, null);
        }
        //Draw Action Button
        if(actPressed)  {
            canvas.drawBitmap(actDown, null, actLoc, null);
        }
        else    {
            canvas.drawBitmap(actUp, null, actLoc, null);
        }
        boolean norm = false;
        if(!actPressed && !attPressed)  {
            norm = true;
        }
        player.onDraw(canvas, actPressed, attPressed, norm, pMove, direction);
    }

    public boolean onTouchEvent(MotionEvent event)  {
        int eventaction = event.getAction();
        int X = (int)event.getX();
        int Y = (int)event.getY();

        switch(eventaction) {
            case MotionEvent.ACTION_DOWN:
                //Up
                if(X > 116 &&
                        X < 194 &&
                        Y < (screenH - 194) &&
                        Y > (screenH - 272)) {
                    dpadSect = new Rect(dpadSize, 0 , 2 * dpadSize, dpadSize);
                    pMove = true;
                    direction = 0;
                }
                //Left
                else if(X > 38 &&
                        X < 116 &&
                        Y < (screenH - 116) &&
                        Y > (screenH - 194)) {
                    dpadSect = new Rect(2 * dpadSize, 0 , 3 * dpadSize, dpadSize);
                    pMove = true;
                    direction = 1;
                }
                //Down
                else if(X > 116 &&
                        X < 194 &&
                        Y < screenH &&
                        Y > (screenH - 116)) {
                    dpadSect = new Rect(dpadSize, dpadSize , 2 * dpadSize, 2 * dpadSize);
                    pMove = true;
                    direction = 2;
                }
                //Right
                else if(X > 194 &&
                        X < 272 &&
                        Y < (screenH - 116) &&
                        Y > (screenH - 194)) {
                    dpadSect = new Rect(2 * dpadSize, dpadSize , 3 * dpadSize, 2 * dpadSize);
                    pMove = true;
                    direction = 3;
                }
                else {
                    dpadSect = new Rect(0, 0, dpadSize, dpadSize);
                    pMove = false;
                }
                if(X < 1020 &&
                        X > 864 &&
                        Y > 544 &&
                        Y < 700) {
                    attPressed = true;
                }
                if(X < 1176 &&
                        X > 1020 &&
                        Y > 388 &&
                        Y < 544) {
                    actPressed = true;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                dpadSect = new Rect(0, 0, dpadSize, dpadSize);
                attPressed = false;
                actPressed = false;
                pMove = false;
                break;
        }

        invalidate();
        return true;
    }
}
