package iCua.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class About extends Activity{
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.about);

	 
	 
	 
	 }
	
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	this.finish();
}
	
	
	
	
	
	
	
	
}