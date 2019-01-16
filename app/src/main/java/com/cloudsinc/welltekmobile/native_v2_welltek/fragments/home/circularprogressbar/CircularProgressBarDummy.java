package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.circularprogressbar;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
/**
 * Created by developers on 15/11/17.
 */
public class CircularProgressBarDummy extends View {
    private int mViewWidth;
    private int mViewHeight;
    private final float mStartAngle = -90;      // Always start from top (default is: "3 o'clock on a watch.")
    private float mSweepAngle = 0;              // How long to sweep from mStartAngle
    private float mMaxSweepAngle = 360;         // Max degrees to sweep = full circle
    private int mStrokeWidth = 20;              // Width of outline
    private int mAnimationDuration = 400;       // Animation duration for progress change
    private int mMaxProgress = 100;             // Max progress to use
    private boolean mDrawText = true;           // Set to true if progress text should be drawn
    private int mProgressColor = Color.parseColor("#00FFFF");   // Outline color
    private boolean mRoundedCorners = true;     // Set to true if rounded corners should be applied to outline ends
    private int mProgressBackgroungColor = Color.parseColor("#E0E0E0");
    private int mTextColor = Color.BLACK;      // Progress text color
    private final Paint mPaint;                 // Allocate paint outside onDraw to avoid unnecessary object creation
    private boolean mDrawBackGroundRing=true;
    private final Paint mPaintBackground;
    private static int position_text_progress;
    public CircularProgressBarDummy(Context context) {
        this(context, null);
    }
    public CircularProgressBarDummy(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public CircularProgressBarDummy(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        position_text_progress = getWidth()/2;
        Log.e("diameter1", String.valueOf(position_text_progress));
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initMeasurments();
        drawOutlineArc(canvas);
        if (mDrawText) {
            drawText(canvas);
        }
    }
    private void initMeasurments() {
        mViewWidth = getWidth();
        mViewHeight = getHeight();
    }
    private void drawOutlineArc(Canvas canvas) {
        final int diameter = Math.min(mViewWidth, mViewHeight) - (mStrokeWidth * 2);
        position_text_progress=diameter;
        Log.e("diameter2", String.valueOf(diameter));
        final RectF outerOval = new RectF(mStrokeWidth, mStrokeWidth, diameter, diameter);
        mPaint.setColor(mProgressColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(mRoundedCorners ? Paint.Cap.ROUND : Paint.Cap.BUTT);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaintBackground.setColor(mProgressBackgroungColor);
        mPaintBackground.setStrokeWidth(mStrokeWidth);
        mPaintBackground.setAntiAlias(true);
        mPaintBackground.setStrokeCap(mRoundedCorners ? Paint.Cap.ROUND : Paint.Cap.BUTT);
        mPaintBackground.setStyle(Paint.Style.STROKE);
        if (mDrawBackGroundRing) {
            canvas.drawArc(outerOval, 0, 360, false, mPaintBackground);
        }
        canvas.drawArc(outerOval, mStartAngle, mSweepAngle, false, mPaint);
    }
    private void drawText(Canvas canvas) {
        mPaint.setTextSize(Math.min(mViewWidth, mViewHeight) / 2.5f);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(mTextColor);
        // Center text
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2)) ;
        Log.e("diameter3", String.valueOf(position_text_progress));
        int pos= position_text_progress /2 + (mStrokeWidth * 2);
        Log.e("diameter4", String.valueOf(position_text_progress/2));
        Log.e("diameter3", String.valueOf(pos));
        canvas.drawText(String.valueOf(calcProgressFromSweepAngle(mSweepAngle)+1), pos-(Math.min(mViewWidth, mViewHeight) / 14f),pos+(Math.min(mViewWidth, mViewHeight) / 11f), mPaint);
    }
    private float calcSweepAngleFromProgress(int progress) {
        return (mMaxSweepAngle / mMaxProgress) * progress;
    }
    private int calcProgressFromSweepAngle(float sweepAngle) {
        return (int) ((sweepAngle * mMaxProgress) / mMaxSweepAngle);
    }
    /**
     * Set progress of the circular progress bar.
     * @param progress progress between 0 and 100.
     */
    public void setProgress(int progress) {
        ValueAnimator animator = ValueAnimator.ofFloat(mSweepAngle, calcSweepAngleFromProgress(progress));
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(mAnimationDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mSweepAngle = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }
    public void setProgressColor(int color) {
        mProgressColor = color;
        invalidate();
    }
    public void setProgressWidth(int width) {
        mStrokeWidth = width;
        invalidate();
    }
    public void setTextColor(int color) {
        mTextColor = color;
        invalidate();
    }
    public void showProgressText(boolean show) {
        mDrawText = show;
        invalidate();
    }
    public void setProgressBackgroundRing(boolean state) {
        mDrawBackGroundRing = state;
        invalidate();
    }
    /**
     * Toggle this if you don't want rounded corners on progress bar.
     * Default is true.
     * @param roundedCorners true if you want rounded corners of false otherwise.
     */
    public void useRoundedCorners(boolean roundedCorners) {
        mRoundedCorners = roundedCorners;
        invalidate();
    }
}