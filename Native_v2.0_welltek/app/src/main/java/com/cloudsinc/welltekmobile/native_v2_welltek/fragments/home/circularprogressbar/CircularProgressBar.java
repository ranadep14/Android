package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.circularprogressbar;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
/**
 * Created by developers on 14/11/17.
 */
public class CircularProgressBar   extends View {
    private int mViewWidth;
    private int mViewHeight;
    private final float mStartAngle = -90;      // Always start from top (default is: "3 o'clock on a watch.")
    private float mSweepAngle = 0;              // How long to sweep from mStartAngle
    private float mMaxSweepAngle = 360;         // Max degrees to sweep = full circle
    private int mStrokeWidth = 15;              // Width of outline
    private int mAnimationDuration = 400;       // Animation duration for progress change
    private int mMaxProgress = 100;             // Max progress to use
    private boolean mDrawText = true;           // Set to true if progress text should be drawn
    private boolean mDrawProgress=false;
    private boolean mRoundedCorners = true;     // Set to true if rounded corners should be applied to outline ends
    private int mProgressColor = Color.BLACK;   // Outline color
    private int mProgressBackgroungColor = Color.parseColor("#E0E0E0");
    private int mTextColor = Color.BLACK;       // Progress text color
    private boolean mDrawBackGroundRing=true;
    private int mProgress;
    private String title,title1,title2,title3;
    private int titleSize,titleSize2,titleSize3;
    private final Paint mPaint;                 // Allocate paint outside onDraw to avoid unnecessary object creation
    private final Paint mPaintBackground;
    public CircularProgressBar(Context context) {
        this(context, null);
    }
    public CircularProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public CircularProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initMeasurments();
        drawOutlineArc(canvas);
        if (mDrawText) {
            drawText(canvas);
            drawTextL2(canvas);
            drawSubScriptTitle(canvas);
        }
        if (mDrawProgress) {
            drawProgress(canvas);
        }
    }
    private void initMeasurments() {
        mViewWidth = getWidth();
        mViewHeight = getHeight();
    }
    private void drawOutlineArc(Canvas canvas) {
        final int diameter = Math.min(mViewWidth, mViewHeight) - (mStrokeWidth * 2);
        final RectF outerOval;
        if (mViewWidth>mViewHeight) {
            outerOval = new RectF(mStrokeWidth + ((mViewWidth - mViewHeight) / 2), mStrokeWidth, ((mViewWidth - mViewHeight) / 2) + mViewHeight - mStrokeWidth, mViewHeight - mStrokeWidth);
        }else {
            outerOval = new RectF(mStrokeWidth,mStrokeWidth+((mViewHeight - mViewWidth) / 2),mViewWidth-mStrokeWidth ,((mViewHeight - mViewWidth) / 2) + mViewWidth - mStrokeWidth);
        }
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
    private void drawProgress(Canvas canvas) {
        mPaint.setTextSize(Math.min(mViewWidth, mViewHeight) / 5f);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(mTextColor);
        // Center text
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2)) ;
        canvas.drawText(calcProgressFromSweepAngle(mSweepAngle)+"", xPos, yPos, mPaint);
    }
    private void drawText(Canvas canvas) {
        if (titleSize!=0) {
            mPaint.setTextSize(titleSize);
        }else{
            mPaint.setTextSize(Math.min(mViewWidth, mViewHeight) / 6f);
        }
/*
        mPaint.setTextSize(Math.min(mViewWidth, mViewHeight) / 6f);
*/
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(mTextColor);
        // Center text
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2)) ;
        if (title!=null) {
            canvas.drawText(title, xPos, yPos, mPaint);
        }
    }
    private void drawTextL2(Canvas canvas) {
        if (titleSize2>0) {
            mPaint.setTextSize(titleSize2);
        }else{
            mPaint.setTextSize(Math.min(mViewWidth, mViewHeight) / 8f);
        }
/*
        mPaint.setTextSize(Math.min(mViewWidth, mViewHeight) / 8f);
*/
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(mTextColor);
        // Center text
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2)) ;
        if (title2!=null) {
            canvas.drawText(title2, xPos, yPos+30, mPaint);
        }
    }
    private void drawSubScriptTitle(Canvas canvas) {
        if (titleSize3>0) {
            mPaint.setTextSize(titleSize3);
        }else{
            mPaint.setTextSize(Math.min(mViewWidth, mViewHeight) / 8f);
        }
/*
        mPaint.setTextSize(Math.min(mViewWidth, mViewHeight) / 8f);
*/
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(mTextColor);
        // Center text
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2)) ;
        if (title3!=null) {
            if (title3=="3") {
                canvas.drawText(title3, xPos+20, yPos+10, mPaint);
            }else {
                canvas.drawText(title3, xPos+32, yPos+10, mPaint);
            }
        }
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
        mProgress=progress;
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
    public void setProgressBackgroundColor(int color) {
        mProgressBackgroungColor = color;
        invalidate();
    }
    public void setProgressBackgroundRing(boolean state) {
        mDrawBackGroundRing = state;
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
    /**
     * Toggle this if you don't want rounded corners on progress bar.
     * Default is true.
     * @param roundedCorners true if you want rounded corners of false otherwise.
     */
    public void useRoundedCorners(boolean roundedCorners) {
        mRoundedCorners = roundedCorners;
        invalidate();
    }
    public int getProgress(){
        return mProgress;
    }
    /*public void drawCOTitle(String CO) {
        title1=CO;
    }*/public void drawTitle(String p_title) {
        title=p_title;
    }
    public void drawTitleL2(String p_title2) {
        title2=p_title2;
    }
    public void drawSubscript(String p_title3) {
        title3=p_title3;
    }
    public void titleSize(int i) {
        titleSize=i;
    }
    public void titleSize2(int i) {
        titleSize2=i;
    }
    public void titleSize3(int i) {
        titleSize3=i;
    }
}