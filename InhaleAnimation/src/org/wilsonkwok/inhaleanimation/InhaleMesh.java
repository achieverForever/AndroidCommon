package org.wilsonkwok.inhaleanimation;

import android.graphics.Path;
import android.graphics.PathMeasure;

public class InhaleMesh extends Mesh {

	private static final int DEF_ROWS = 20;
	private static final int DEF_COLUMNS = 20;
	
	/**
	 * The curves which bounds the inhale animation and determines where it 
	 * goes along.
	 */
	private Path firstPath;
	private Path secondPath;
	
	/**
	 * Used to calculate the vertices on the curves
	 */
	private PathMeasure firstPathMeasure;
	private PathMeasure secondPathMeasure;
	
	/**
	 * Dimensions of the bitmap
	 */
	private float bmpWidth;
	private float bmpHeight;
	
	/**
	 * Inhale position where the bitmap will be sucked into
	 */
	private float inhaleX;
	private float inhaleY;
	
	public InhaleMesh(float bmpWidth, float bmpHeight) {
		super(DEF_ROWS, DEF_COLUMNS);
		this.bmpWidth = bmpWidth;
		this.bmpHeight = bmpHeight;
		this.inhaleX = bmpWidth / 2;
		this.inhaleY = bmpHeight * 2;
		
		firstPath = new Path();
		secondPath = new Path();
		firstPathMeasure = new PathMeasure();
		secondPathMeasure = new PathMeasure();
	}
	
	/**
	 * Build the paths along which the inhale animation will play.
	 */
	public void buildPaths() {
		firstPath.reset();
		secondPath.reset();
		
		firstPath.moveTo(0, 0);
		firstPath.lineTo(0, bmpHeight);
		firstPath.quadTo(0, (bmpHeight + inhaleY) / 2, inhaleX, inhaleY);
		
		secondPath.moveTo(bmpWidth, 0);
		secondPath.lineTo(bmpWidth, bmpHeight);
		secondPath.quadTo(bmpWidth, (bmpHeight + inhaleY) / 2, inhaleX, inhaleY);
		
		firstPathMeasure.setPath(firstPath, false);
		secondPathMeasure.setPath(secondPath, false);
	}
	
	/**
	 * Build the transformed mesh at time t.
	 * 
	 * @param t The time at which the new mesh is built, 0.0 <= t <= 1.0
	 */
	public void buildMesh(float t) {
		float[] pos = new float[2];
		
		final float firstLength = firstPathMeasure.getLength();
		final float secondLength = secondPathMeasure.getLength();
		
		final float startDistance = t * firstLength;
		final float startDistance2 = t * secondLength;
		final float delta = bmpHeight / rows;
		final float delta2 = bmpHeight / rows;
		
		float dist;
		float dist2;
		float fromX, fromY, toX, toY;
		
		int index = 0;

		for (int r = 0; r < rows + 1; r++) {
			dist = startDistance + r * delta;
			dist2 = startDistance2 + r * delta2;
			
			firstPathMeasure.getPosTan(dist, pos, null);
			fromX = pos[0];
			fromY = pos[1];
			
			secondPathMeasure.getPosTan(dist2, pos, null);
			toX = pos[0];
			toY = pos[1];
			
			for (int c = 0; c < columns + 1; c++) {
				float tt = (float) c / columns;
				float x = fromX + tt * (toX - fromX);
				float y = fromY + tt * (toY - fromY);
				verts[index] = x;
				verts[index + 1] = y;
				
				index += 2;
			}
			
		}
	}
	
	/**
	 * Set up a new inhale point
	 * 
	 * @param x X component of the inhale point
	 * @param y Y component of the inhale point
	 */
	public void setInhalePoint(float x, float y) {
		if (x != inhaleX || y != inhaleY) {
			inhaleX = x;
			inhaleY = y;
			buildPaths();
			buildMesh(0);
		}
	}
	
	public Path[] getPaths() {
		return new Path[] { firstPath, secondPath };
	}
	
	public float getInhaleX() {
		return inhaleX;
	}
	
	public float getInhaleY() {
		return inhaleY;
	}
}
