/**
 * 
 */
package iCua.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * @author devil
 *
 */
public class LastRadio extends Activity implements OnCheckedChangeListener, OnClickListener{
    protected static int ans = -1;
    protected static int choice = -2;
    protected static RadioGroup mRadioGroup; 
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.lastradio);
	        mRadioGroup = (RadioGroup) findViewById(R.id.RadioGroupLast);
	          RadioButton r2 = (RadioButton) findViewById(R.id.RadioArtist);
	          Button clearButton = (Button) findViewById(R.id.ButtonTune);
	       
	          // save the correct answer id
	          ans = r2.getId();
	          mRadioGroup.setOnCheckedChangeListener(this);
	          
	          clearButton.setOnClickListener(new Button.OnClickListener() {
	               public void onClick(View v) {
	            	   
	               }
	          }); 
	 
	 
	 
	 }
	 @Override
     public void onCheckedChanged(RadioGroup arg0, int checkedId) {
          choice = checkedId;
     }

     @Override
     public void onClick(View arg0) {
          mRadioGroup.clearCheck();
     } 
	 
	 
	 
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	this.finish();
}
	
	
	
	
	
	
	
	
}