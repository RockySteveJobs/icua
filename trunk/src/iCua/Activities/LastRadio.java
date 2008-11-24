/**
 * 
 */
package iCua.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * @author devil
 *
 */
public class LastRadio extends Activity implements OnCheckedChangeListener, OnClickListener{
    protected static int ans = -1;
    private Button btn = null;
    protected static int choice = -2;
    protected static RadioGroup mRadioGroup; 
    private RadioButton r2 = null;
  private TextView txt = null;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.lastradio);
	        mRadioGroup = (RadioGroup) findViewById(R.id.RadioGroupLast);
	          r2= (RadioButton) findViewById(R.id.RadioArtist);
	          btn = (Button) findViewById(R.id.ButtonTune);
	          txt = (TextView) findViewById(R.id.RadioText);
	          // save the correct answer id
	          ans = r2.getId();
	          choice = ans;
	          r2.setChecked(true);
	          mRadioGroup.setOnCheckedChangeListener(this);
	          
	          btn.setOnClickListener(new Button.OnClickListener() {
	               public void onClick(View v) {
	               	Intent i = new Intent(LastRadio.this, OnAirRadio.class);
	            	
	               	if (r2.isChecked())i.putExtra("type", 1);
	               	else i.putExtra("type", 2);
	              
	               	i.putExtra("val", txt.getText().toString());
	                startActivity(i);
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