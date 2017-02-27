/*
 * Copyright © 2016, Craftsvilla.com
 *  Written under contract by Robosoft Technologies Pvt. Ltd.
 */

package com.clipwiser.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.clipwiser.R;


/**
 * Created by ram on 10/5/16.
 */

public class BadgeDrawable extends Drawable {

    private float mTextSize;
    private Paint mBadgePaint;
    private Paint mBadgePaint1;
    private Paint mTextPaint;
    private Rect mTxtRect = new Rect();

    private String mCount = "";
    private boolean mWillDraw = false;

    public BadgeDrawable(Context context) {
        mTextSize = context.getResources().getDimension( R.dimen.badge_text_size);
        mBadgePaint = new Paint();
        mBadgePaint.setColor(Color.WHITE);
        mBadgePaint.setAntiAlias(true);
        mBadgePaint.setStyle(Paint.Style.FILL);
        mBadgePaint1 = new Paint();
        mBadgePaint1.setColor(Color.parseColor("#EEEEEE"));
        mBadgePaint1.setAntiAlias(true);
        mBadgePaint1.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTypeface(Typeface.DEFAULT);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!mWillDraw) {
            return;
        }
        Rect bounds = getBounds();
        float width = bounds.right - bounds.left;
        float height = bounds.bottom - bounds.top;
        // Position the badge in the top-right quadrant of the icon.

  /*Using Math.max rather than Math.min */
        float radius = ((Math.max(width, height) / 2)) / 2;
        float centerX = (width - radius - 1) +10;
        float centerY = radius -5;
        if(mCount.length() <= 2){
            // Draw badge circle.
            canvas.drawCircle(centerX, centerY, radius+9, mBadgePaint1);
            canvas.drawCircle(centerX, centerY, radius+7, mBadgePaint);
        }
        else{
            canvas.drawCircle(centerX, centerY, radius+10, mBadgePaint1);
            canvas.drawCircle(centerX, centerY, radius+8, mBadgePaint);
        }
        // Draw badge count text inside the circle.
        mTextPaint.getTextBounds(mCount, 0, mCount.length(), mTxtRect);
        float textHeight = mTxtRect.bottom - mTxtRect.top;
        float textY = centerY + (textHeight / 2f);
        if(mCount.length() > 2)
        canvas.drawText("99+", centerX, textY, mTextPaint);
        else
        canvas.drawText(mCount, centerX, textY, mTextPaint);
    }

    /*
     Sets the count (i.e notifications) to display.
      */
    public void setCount(String count) {
        mCount = count;
        // Only draw a badge if there are notifications.
        mWillDraw = !count.equalsIgnoreCase("0");
        invalidateSelf();
    }

    @Override
    public void setAlpha(int alpha) {
        // do nothing
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        // do nothing
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
