package org.wilsonkwok.inhaleanimation;

import org.wilsonkwok.inhaleanimation.InhaleAnimation.AnimationUpdateListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class InhaleMeshView extends View implements AnimationUpdateListener {
	@SuppressWarnings("unused")
	private static final String TAG = "InhaleMeshView";
	
	private Bitmap bitmap;
	
	private InhaleMesh mesh;
	
	private Paint paint;
	
	private InhaleAnimation animation;
	
	private boolean isDebug = false;
	
	public InhaleMeshView(Context context) {
		this(context, null, 0);
	}
	
	public InhaleMeshView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public InhaleMeshView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		paint = new Paint();
		paint.setAntiAlias(true);
		
		animation = new InhaleAnimation(this);
		animation.setDuration(1000);
		
		setFocusableInTouchMode(true);
		setBackgroundColor(0xffbbbbbb);
	}
	
	/**
	 * Set the bitmap which will be animated
	 * 
	 * @param bitmap The bitmap which will be transformed by the inhale animation 
	 */
	public void setBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			throw new NullPointerException("Bitmap cannot be null");
		}
		this.bitmap = bitmap;
		final int width = bitmap.getWidth();
		final int height = bitmap.getHeight();
		
		mesh = new InhaleMesh(width, height);
		mesh.buildPaths();
		mesh.buildMesh(0);
	}
	
	/**
	 * Set the inhale position
	 * 
	 * @param x
	 * @param y
	 */
	public void setInhalePoint(float x, float y) {
		if (mesh == null) {
			throw new IllegalStateException("InhaleMesh is not yet created, " +
					"did you forget to call setBitmap()");
		}
		mesh.setInhalePoint(x, y);
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.translate(20, 0);
		
		// Draw the paths
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(2);
		
		canvas.drawPath(mesh.getPaths()[0], paint);
		canvas.drawPath(mesh.getPaths()[1], paint);
		
		// Draw the inhale point
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.FILL);
		
		canvas.drawCircle(mesh.getInhaleX(), mesh.getInhaleY(), 8, paint);
		
		// Draw the bitmap mesh
		canvas.drawBitmapMesh(bitmap, mesh.getColumns(), mesh.getRows(),
				mesh.getVertices(), 0, null, 0, paint);
		
		// Draw the debug points (optional) 
		if (isDebug) {
			paint.setColor(Color.WHITE);
			paint.setStrokeWidth(3);
			canvas.drawPoints(mesh.getVertices(), paint);
		}
		
		canvas.translate(-20, 0);
	}
	
	/**
	 * Start the inhale animation
	 * 
	 * @param reversed True to play the animation backward, false forward
	 */
	public void startAnimation(boolean reversed) {
		animation.setReversed(reversed);
		if (!animation.hasStarted() || animation.hasEnded()) {
			startAnimation(animation);
		}
	}

	@Override
	public void onAnimationUpdate(float t) {
		mesh.buildMesh(t);
		invalidate();
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			mesh.setInhalePoint(event.getX(), event.getY());
			invalidate();
		}
		return true;
	}
}
