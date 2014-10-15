package org.wilsonkwok.demo;

import org.wilsonkwok.inhaleanimation.InhaleMeshView;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.myinhaleanimation.R;

public class DemoActivity extends Activity {

	private InhaleMeshView inhaleMesh;
	private Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn = (Button) findViewById(R.id.run);
		inhaleMesh = (InhaleMeshView) findViewById(R.id.inhale_mesh_view);
		
		inhaleMesh.setBitmap(BitmapFactory.decodeResource(getResources(), 
				R.drawable.screenshot));
		
		btn.setOnClickListener(new OnClickListener() {
			private boolean reversed = false;
			
			@Override
			public void onClick(View v) {
				inhaleMesh.startAnimation(reversed);
				reversed = !reversed;
			}
		});
		
	}
/*	
	private Bitmap captureLayout() {
		FrameLayout ll = (FrameLayout) findViewById(android.R.id.content);
		Bitmap bitmap = Bitmap.createBitmap(ll.getWidth(), ll.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		ll.draw(canvas);
		
		return bitmap;
	}*/
}
