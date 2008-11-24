package iCua.Activities;



import iCua.Data.CtrlData;
import iCua.Media.Song;
import android.app.Activity;
import android.app.AlertDialog;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.content.DialogInterface;
import android.app.Dialog;
public class AddContent extends Activity implements OnItemClickListener{
    private  String[] SONGS;
	private   ListView     lv = null;
	private Song[] todas = null;
	private boolean[] sel  ;
	private int id_pl=-1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	  super.onCreate(savedInstanceState);
          setContentView(R.layout.contentplaylists);
          
          Button bc = (Button) findViewById(R.id.BPLCancel);
          Button bs = (Button) findViewById(R.id.BPLSave);
          bc.setOnClickListener(lcancel);
          bs.setOnClickListener(lsave);
          lv =(ListView) findViewById(android.R.id.list);
     
         
     
          lv.setOnItemClickListener(this);
       
        lv.setItemsCanFocus(false);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
   
        Toast.makeText(this, "Select songs to add to the playlist", Toast.LENGTH_LONG);
        
    
    
    }
    
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    	// TODO Auto-generated method stub
    	  sel[arg2]= !  sel[arg2];
    
    if (  sel[arg2])CtrlData.addSongPlaylist(id_pl, todas[arg2].id);
    else CtrlData.delSongPlaylist(id_pl, todas[arg2].id);
    
    	System.out.println("neeeeeeeem"+ todas[arg2].title + " ===> "+ sel[arg2]);
    }
    
    
    	
    
    
    private OnClickListener lcancel = new OnClickListener() {
        public void onClick(View v) {
            // Cancel a previous call to startService().  Note that the
            // service will not actually stop at this point if there are
            // still bound clients.
        	
        	AlertDialog d =  new AlertDialog.Builder(AddContent.this).setIcon(R.drawable.icon).setTitle("Do you want to cancel?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	AddContent.this.finish();
                	CtrlData.delPlaylist(AddContent.this.id_pl);
                    /* User clicked OK so do some stuff */
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	
                    /* User clicked Cancel so do some stuff */
                }
            })
            .create();
        	
        	d.show();

        	
            
        }
    };
    
    private OnClickListener lsave = new OnClickListener() {
        public void onClick(View v) {
            // Cancel a previous call to startService().  Note that the
            // service will not actually stop at this point if there are
            // still bound clients.
        
        	 LayoutInflater factory = LayoutInflater.from(AddContent.this);
             final View textEntryView = factory.inflate(R.layout.alert_dialog_text_entry, null);
             AlertDialog d2= new AlertDialog.Builder(AddContent.this)
                 .setIcon(R.drawable.icon)
                 .setTitle("Put a Playlist name")
                 .setView(textEntryView)
                 .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int whichButton) {
                    	TextView t = (TextView) textEntryView.findViewById(R.id.playlist_edit);
                    	 	CtrlData.updatePlaylist(id_pl, t.getText().toString());
                    	 	AddContent.this.finish();
                    	 	/* User clicked OK so do some stuff */
                     }
                 })
                 .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int whichButton) {
                    	 	
                         /* User clicked cancel so do some stuff */
                     }
                 }) .create();

        
             d2.show();
                 
        	
        	
        	
        	
        	
        	
        	
        	

        	  
        }
    };
    
    
    
    private static final String[] GENRES = new String[] {
        "Blur - Song 2", "Muse - New Born", "Muse - Supermassive Blackhole", "Children", "Comedy", "Documentary", "Drama",
        "Foreign", "History", "Independent", "Romance", "Sci-Fi", "Television", "Thriller"
    };
    
    
    @Override
    protected void onResume() {
    	 todas = CtrlData.getSongs(null, null,null);
         sel = new boolean[todas.length];
         this.SONGS  = new String[todas.length];
         
         
         id_pl= CtrlData.createPlaylist();
         
         for (int i = 0; i<todas.length; i++){
       	 this.SONGS[i]= todas[i].artist+" - "+ todas[i].title;
       	            
       	  
         }
         lv.setAdapter(new ArrayAdapter<String>(this,
               android.R.layout.simple_list_item_multiple_choice, this.SONGS));
    	
    	// TODO Auto-generated method stub
    	super.onResume();
    }
    
    
    
}