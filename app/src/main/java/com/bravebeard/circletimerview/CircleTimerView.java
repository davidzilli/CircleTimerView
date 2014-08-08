package com.bravebeard.circletimerview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by david on 8/7/14.
 */
public class CircleTimerView extends View implements
        ValueAnimator.AnimatorListener,
        ValueAnimator.AnimatorUpdateListener {

    private static final String TAG = CircleTimerView.class.getSimpleName();

    /*********************************************************************************
     * Static Variables
     *********************************************************************************/

    /*********************************************************************************
     * Member Variables
     *********************************************************************************/

    private Context mContext;

    private float mSweepAngle = 0;
    private int mPrimaryColor;
    private int mSecondaryColor;
    private int mHeight;
    private int mWidth;
    private float mThickness;
    private long mDuration;

    ValueAnimator animator;

    private boolean mReverse = false;

    private Bitmap b;
    private Canvas c;

    /*********************************************************************************
     * Init
     *********************************************************************************/

    public CircleTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        /** Call config with default values on init */
        config(android.R.color.holo_blue_light, android.R.color.holo_blue_dark, 10, 3000, false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Rect bounds = new Rect();
        this.getDrawingRect(bounds);
        // bounds will now contain none zero values
        int shortest_side = Math.min(bounds.width(), bounds.height());
        mWidth = shortest_side;
        mHeight = shortest_side;

        initCanvas();
        initAnimation();
    }

    /*********************************************************************************
     * Public method to configure
     *********************************************************************************/

    public void config(int primaryColor, int secondaryColor, float thickness, long duration, boolean reverse) {
        this.mPrimaryColor = primaryColor;
        this.mSecondaryColor = secondaryColor;
        this.mThickness = convertDPtoPixels(mContext, thickness);
        this.mDuration = duration;
        this.mReverse = reverse;
    }

    /*********************************************************************************
     * Private Methods for updating draw
     *********************************************************************************/

    public static int convertDPtoPixels(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dp * scale + 0.5f);
    }

    private void initCanvas() {
        b = Bitmap.createBitmap(convertDPtoPixels(mContext, mWidth), convertDPtoPixels(mContext, mHeight), Bitmap.Config.ARGB_8888);
        c = new Canvas(b);
    }

    private void initAnimation() {
        if (mReverse) {
            animator = ValueAnimator.ofFloat(360, 0);
        } else {
            animator = ValueAnimator.ofFloat(0, 360);
        }
        animator.setDuration(mDuration);
        animator.addUpdateListener(this);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    @Override
    public void onDraw(Canvas canvas) {

        /** Clear the canvas */
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setFilterBitmap(true);

        p.setColor(getResources().getColor(mSecondaryColor));
        RectF secondary_rect = new RectF(0, 0, mWidth, mHeight);
        c.drawArc(secondary_rect, 270, 360, true, p);

        p.setColor(getResources().getColor(mPrimaryColor));
        RectF rectF = new RectF(0, 0, mWidth, mHeight);
        c.drawArc(rectF, 270, mSweepAngle, true, p);

        RectF center_rect = new RectF(mThickness, mThickness, mWidth - mThickness, mHeight - mThickness);
        p.setColor(getResources().getColor(android.R.color.transparent));
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        c.drawArc(center_rect, 270, 360, true, p);

        canvas.drawBitmap(b, 0, 0, new Paint());

        if (mSweepAngle > 360 || mSweepAngle < 0) {
            mSweepAngle = 0;
            initCanvas();
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        mSweepAngle = ((Float) (animation.getAnimatedValue())).floatValue();
        invalidate();
    }
}