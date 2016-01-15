package com.spriteviki.animatedview;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.animation.LinearInterpolator;

/**
 * A CircularAnimated style drawable, compatible up to 3.0
 *
 * @author Jack Han
 *         <p/>
 *         Licensed under the Apache License 2.0 license see:
 *         http://www.apache.org/licenses/LICENSE-2.0
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CircularAnimatedDrawable extends Drawable implements Animatable {

    public static final String START_ANGLE_PROPERTY = "startAngle";
    public static final String SWEEP_ANGLE_PROPERTY = "sweepAngle";

    private final RectF fBounds;
    private boolean mAllowLoading;
    private float mBorderWidth;
    private Animator mLoadingAnimator;
    private Paint mPaint;
    private boolean mRunning;
    private float mStartAngle;
    private float mSweepAngle;

    public CircularAnimatedDrawable(int color, float borderwidth) {
        super();
        this.fBounds = new RectF();
        this.mLoadingAnimator = null;
        this.mAllowLoading = true;
        this.mBorderWidth = borderwidth;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(borderwidth);
        this.mPaint.setColor(color);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mLoadingAnimator = this.createLoadingAnimator();
    }

    private Animator createLoadingAnimator() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{PropertyValuesHolder
                .ofKeyframe("startAngle", new Keyframe[]{Keyframe.ofFloat(0f, -90f), Keyframe.ofFloat(
                0.5f, 330f), Keyframe.ofFloat(1f, 630f)}), PropertyValuesHolder.ofFloat("sweepAngle",
                new float[]{0f, -120f, 0f})});
        objectAnimator.setDuration(1760);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatCount(-1);
        return objectAnimator;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawArc(this.fBounds, this.mStartAngle, this.mSweepAngle, false, this.mPaint);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    public float getStartAngle() {
        return this.mStartAngle;
    }

    public float getSweepAngle() {
        return this.mSweepAngle;
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.fBounds.left = (((float) rect.left)) + this.mBorderWidth / 2f + 0.5f;
        this.fBounds.right = (((float) rect.right)) - this.mBorderWidth / 2f - 0.5f;
        this.fBounds.top = (((float) rect.top)) + this.mBorderWidth / 2f + 0.5f;
        this.fBounds.bottom = (((float) rect.bottom)) - this.mBorderWidth / 2f - 0.5f;
    }

    public void setAllowLoading(boolean allowLoading) {
        this.mAllowLoading = allowLoading;
    }

    @Override
    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public void setStartAngle(float startAngle) {
        this.mStartAngle = startAngle;
        if (this.mAllowLoading) {
            this.invalidateSelf();
        }
    }

    public void setSweepAngle(float sweepAngle) {
        this.mSweepAngle = sweepAngle;
        if (this.mAllowLoading) {
            this.invalidateSelf();
        }
    }

    public void start() {
        if (!this.isRunning()) {
            this.mRunning = true;
            this.mLoadingAnimator.start();
            this.invalidateSelf();
        }
    }

    public void stop() {
        if (this.isRunning()) {
            this.mRunning = false;
            this.mLoadingAnimator.cancel();
            this.invalidateSelf();
        }
    }
}



