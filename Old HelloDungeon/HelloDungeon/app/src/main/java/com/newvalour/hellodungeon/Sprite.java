package com.newvalour.hellodungeon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import java.util.ArrayList;
import android.view.SurfaceHolder;
import com.newvalour.hellodungeon.GView;

/**
 * Created by Valek on 5/18/2015.
 */
public class Sprite {
    private int x;
    private int y;
    private double xSpd;
    private double ySpd;
    private int height;
    private int width;
    private ArrayList<Bitmap> bmList;
    private int cFrame;
    private int[] maxFrame;
    private int direction;
    private int stance;
    private boolean show;

    public Sprite(ArrayList<Bitmap> bmap, int[] mF)    {
        bmList = new ArrayList<Bitmap>();
        show = false;
        direction = 0;
        stance = 0;
        cFrame = 0;
        height = 128;
        width = 128;
        x = 5;
        y = 1;
        xSpd = 0.0;
        ySpd = 0.0;
        //xGrid = 5;
        //yGrid = 1;
        bmList.addAll(bmap);
        maxFrame = mF;
    } //Sprite()

    public void update(boolean a, boolean n, boolean m)    {

        if(stance > 2 || stance < 0) {
            stance = 0;
        } //if
        if(show)    {
            stance = 2;
            cFrame = ++cFrame % maxFrame[stance];
        } //if
        else if(a)   {
            stance = 1;
            width = 384;
            height = 384;
            cFrame = ++cFrame % maxFrame[stance];
            //xGrid--;
            //yGrid--;
            x--;
            y--;
        } //else if
        else if(n)  {
            stance = 0;
            if(m){
                cFrame = ++cFrame % maxFrame[stance];
                if(direction == 0)  {
                    ySpd -= .5;
                } //if
                else if(direction == 1) {
                    xSpd -= .5;
                } //else if
                else if(direction == 2) {
                    ySpd += .5;
                } //else if
                else if(direction == 3) {
                    xSpd += .5;
                } //else if
            } //if
        } //else if

        xSpd = (xSpd * width) / maxFrame[stance];
        ySpd = (ySpd * height) / maxFrame[stance];

        x = x + (int)(xSpd);
        y = y + (int)(ySpd);

        if(a)   {
            x -= width;
            y += height;
        } //if

        width = 128;
        height = 128;
    } //update()

    public void onDraw(Canvas canvas, boolean s, boolean a, boolean n, boolean m, int drt)   {
        direction = drt;
        show = s;

        int loopMax = 1;
        if(s || a || m)   {loopMax = maxFrame[stance];}
        for(int i = 0; i < loopMax; i++) {

            update(a, n, m);

            Rect src;
            if (show) {
                src = new Rect(cFrame, direction * height, width, direction * height + height);
            } //if
            else {
                src = new Rect(cFrame * width, direction * height, cFrame * width + width, direction * height + height);
            } //else

            Rect dst = new Rect(x, y, x + width, y + height);
            canvas.drawBitmap(bmList.get(stance), src, dst, null);

            xSpd = ySpd = 0;

            if(i < (loopMax - 1)) {
                try {
                    Thread.sleep(10);
                } //try
                catch (InterruptedException e) {
                    e.printStackTrace();
                } //catch
            } //if
        } //for
    } //onDraw
} //Sprite
