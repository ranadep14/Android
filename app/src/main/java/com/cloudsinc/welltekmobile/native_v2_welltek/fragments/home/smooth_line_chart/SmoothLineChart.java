package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.smooth_line_chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SmoothLineChart extends View {

	private static int CHART_COLOR = 0xFFFF0000;
	private static final int CIRCLE_SIZE = 8;
	private static final int STROKE_SIZE = 3;
	private static final float SMOOTHNESS = 0.3f; // the higher the smoother, but don't go over 0.5

	private final Paint mPaint;
	private final Paint mPaint_border;


	private final Path mPath;
	private final Path mPath_border;

	private final float mCircleSize;
	private final float mStrokeSize;
	private final float mBorder;

	private PointF[] mValues;
	private float mMinY=0;
	private float mMaxY=9;

	private int rowsCount = 5;
	private int columnsCount = 8;
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public SmoothLineChart(Context context) {
		this(context, null, 0);
	}

	public SmoothLineChart(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SmoothLineChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		float scale = context.getResources().getDisplayMetrics().density;

		mCircleSize = scale * CIRCLE_SIZE;
		mStrokeSize = scale * STROKE_SIZE;
		mBorder = mCircleSize;

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(mStrokeSize);


		mPaint_border = new Paint();
		mPaint_border.setColor(Color.GRAY);
		mPaint_border.setAntiAlias(true);
		mPaint_border.setStrokeWidth(1);
		mPaint_border.setStyle(Style.STROKE);

		paint.setColor((Color.BLACK & 0xFFFFFF) | 0x30000000);


		mPath = new Path();
		mPath_border=new Path();

	}

	public void setData(PointF[] values) {
		mValues = values;

		/*if (values != null && values.length > 0) {
			mMaxY = values[0].y;
			//mMinY = values[0].y;
			for (PointF point : values) {
				final float y = point.y;
				if (y > mMaxY)
					mMaxY = y;
				*//*if (y < mMinY)
					mMinY = y;*//*
			}
		}*/

		invalidate();
	}

	public void draw(Canvas canvas) {
		super.draw(canvas);

		//line with color

		int height_grid = getHeight();
		int width_grid = getWidth();
		for (int i = 0; i < rowsCount; ++i) {
			canvas.drawLine(mBorder, height_grid / rowsCount * (i + 1)-mBorder, width_grid, height_grid / rowsCount * (i + 1)-mBorder, paint);
		}


		if (mValues == null || mValues.length == 0)
			return;

		int size = mValues.length;

		final float height = getMeasuredHeight() - 2*mBorder;
		final float width = getMeasuredWidth() - 2*mBorder;

		final float left = mValues[0].x;
		final float right = mValues[mValues.length-1].x;
		final float dX = (right - left) > 0 ? (right - left) : (2);
		final float dY = (mMaxY-mMinY) > 0 ? (mMaxY-mMinY) : (2);

		mPath.reset();

		// calculate point coordinates
		List<PointF> points = new ArrayList<PointF>(size);
		for (PointF point : mValues) {
			float x = mBorder + (point.x-left)*width/dX;
			float y = mBorder + height - (point.y-mMinY)*height/dY;
			points.add(new PointF(x,y));
		}

		// calculate smooth path
		float lX = 0, lY = 0;
		mPath.moveTo(points.get(0).x, points.get(0).y);
		for (int i=1; i<size; i++) {
			PointF p = points.get(i);	// current point

			// first control point
			PointF p0 = points.get(i-1);	// previous point
			float d0 = (float) Math.sqrt(Math.pow(p.x - p0.x, 2)+ Math.pow(p.y-p0.y, 2));	// distance between p and p0
			float x1 = Math.min(p0.x + lX*d0, (p0.x + p.x)/2); 	// min is used to avoid going too much right
			float y1 = p0.y + lY*d0;

			// second control point
			PointF p1 = points.get(i+1 < size ? i+1 : i);	// next point
			float d1 = (float) Math.sqrt(Math.pow(p1.x - p0.x, 2)+ Math.pow(p1.y-p0.y, 2));	// distance between p1 and p0 (length of reference line)
			lX = (p1.x-p0.x)/d1*SMOOTHNESS;		// (lX,lY) is the slope of the reference line
			lY = (p1.y-p0.y)/d1*SMOOTHNESS;
			float x2 = Math.max(p.x - lX*d0, (p0.x + p.x)/2);	// max is used to avoid going too much left
			float y2 = p.y - lY*d0;

			// add line
			mPath.cubicTo(x1,y1,x2, y2, p.x, p.y);
		}


		// draw path
		mPaint.setColor(CHART_COLOR);
		mPaint.setStyle(Style.STROKE);
		canvas.drawPath(mPath, mPaint);

		/*// draw area
		if (size > 0) {
			mPaint.setStyle(Style.FILL);
			mPaint.setColor((CHART_COLOR & 0xFFFFFF) | 0x10000000);
			mPath.lineTo(points.get(size-1).x, height+mBorder);
			mPath.lineTo(points.get(0).x, height+mBorder);
			mPath.close();
			canvas.drawPath(mPath, mPaint);
		}*/
		mPath_border.reset();
		mPath_border.moveTo(points.get(0).x,mBorder);
		mPath_border.lineTo(points.get(0).x, height+mBorder);
		mPath_border.lineTo(points.get(size-1).x, height+mBorder);
		canvas.drawPath(mPath_border, mPaint_border);


	}

	// add below code to change the color of graph curve


	public void setCurveColor(int color) {
		CHART_COLOR=color;
	}
}
