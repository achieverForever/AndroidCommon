package org.wilsonkwok.inhaleanimation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class InhaleAnimation extends Animation {

	private AnimationUpdateListener listener;
	private boolean reversed = true;
	
	public InhaleAnimation(AnimationUpdateListener listener) {
		this.listener = listener;
	}
	
	/**
	 * Used to implement frame by frame animation callback.
	 */
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		if (listener != null) {
			float time = reversed ? 1 - interpolatedTime : interpolatedTime;
			listener.onAnimationUpdate(time);
		}
	}
	
	/**
	 * Interface definition for a callback to be invoked when a animation update 
	 * is to be performed.
	 */
	public interface AnimationUpdateListener {
		/**
		 * Called when an animation update is to be performed
		 * 
		 * @param t The time when this animation update happens, 0.0<=t<=1.0
		 */
		void onAnimationUpdate(float t);		
	}
	
	/**
	 * Make the animation forward or backward
	 * 
	 * @param reversed True to play the animation backward, false forward
	 */
	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}
	
}
