package iCua.Activities;

import iCua.Data.CtrlDades;
import iCua.Media.Song;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class AddContent extends Activity implements OnItemClickListener{
    private  String[] SONGS;
	private   ListView     lv = null;
	private Song[] todas = null;
	private boolean[] sel  ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	  super.onCreate(savedInstanceState);
          setContentView(R.layout.contentplaylists);
          
          Button bc = (Button) findViewById(R.id.BPLCancel);
          Button bs = (Button) findViewById(R.id.BPLSave);
          bc.setOnClickListener(lcancel);
          bs.setOnClickListener(lsave);
          
          lv =(ListView) findViewById(android.R.id.list);
     
          todas = CtrlDades.getSongs(null, null,null);
          sel = new boolean[todas.length];
          this.SONGS  = new String[todas.length];
          
          
          
          for (int i = 0; i<todas.length; i++){
        	 this.SONGS[i]= todas[i].artist+" - "+ todas[i].title;
        	            
        	  
          }
          lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, this.SONGS));
     
          lv.setOnItemClickListener(this);
       
        lv.setItemsCanFocus(false);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
   
        Toast.makeText(this, "Select songs to add to the playlist", Toast.LENGTH_LONG);
        
    
    
    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    	// TODO Auto-generated method stub
    sel[arg2]= !  sel[arg2];
    	System.out.println("neeeeeeeem"+ todas[arg2].title + " ===> "+ sel[arg2]);
    }
    
    
    	
    
    
    private OnClickListener lcancel = new OnClickListener() {
        public void onClick(View v) {
            // Cancel a previous call to startService().  Note that the
            // service will not actually stop at this point if there are
            // still bound clients.
        	AddContent.this.finish();
        	
            
        }
    };
    private OnClickListener lsave = new OnClickListener() {
        public void onClick(View v) {
            // Cancel a previous call to startService().  Note that the
            // service will not actually stop at this point if there are
            // still bound clients.

        	  
        }
    };
    
    
    
    private static final String[] GENRES = new String[] {
        "Blur - Song 2", "Muse - New Born", "Muse - Supermassive Blackhole", "Children", "Comedy", "Documentary", "Drama",
        "Foreign", "History", "Independent", "Romance", "Sci-Fi", "Television", "Thriller"
    };
    
    
    
    
}