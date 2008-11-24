package iCua.Activities;

import iCua.Data.CtrlData;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Config  extends Activity{
	private Button btn = null;
	private TextView txt1, txt2;
	private String user= null;
	private String pwd = null;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.config);
	        txt1 = (TextView)findViewById(R.id.EditUser);
	        txt2 = (TextView)findViewById(R.id.EditPass);
	        btn = (Button)findViewById(R.id.ButtonConfig);
	       ContentValues cv = CtrlData.getLastFMdata();
	       
	       user=cv.getAsString("user");
	       pwd=cv.getAsString("pwd");
	       
	       txt1.setText(user);
	       txt2.setText(pwd);

	        	
	        btn.setOnClickListener(new Button.OnClickListener() {
	               public void onClick(View v) {
	            	   if( (user!=txt1.getText().toString())||(pwd!=txt2.getText().toString()) )CtrlData.setLastFMdata(txt1.getText().toString(), txt2.getText().toString());
	            	   
	            	   Config.this.finish();
	               }
	           
	             
	          }); 
	        
	 
	 
	 
	 }
	
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	this.finish();
}
	
	
	
	
	
	
	
	
}