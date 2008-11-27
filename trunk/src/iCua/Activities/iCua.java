package iCua.Activities;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;





import iCua.Components.HorizontalSlider;
import iCua.Data.CtrlData;
import iCua.Data.LastFM;
import iCua.Data.Adapters.ImageAdapter;
import iCua.Media.LastFMClient;
import iCua.Media.Song;
import iCua.Media.StreamingMediaPlayer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class iCua extends Activity {
    /** Called when the activity is first created. */

    Button mKillButton;
    TextView mCallbackText;
    private int totalSongs =0;
    private Dialog d = null;
    

    boolean mIsBound= false;
    
    @Override
    protected void onResume() {
        totalSongs = CtrlData.getTotalSongs();
    	super.onResume();
    }
    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

       
        
        setContentView(R.layout.main);
      //  ImageView portada = (ImageView) findViewById(R.id.ImageView01);
  
        GridView gv = (GridView) findViewById(R.id.GridView01);
        gv.setAdapter(new ImageAdapter(this)); 
        gv.setOnItemClickListener(l);   

        
        //gv.setNumColumns(4);

        sdcard();
 //       send(password,artist,track);
        CtrlData.scan();
        

        
         d =new AlertDialog.Builder(this)
        .setIcon(R.drawable.icon)
        .setTitle("No Songs found.")
        .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	
          
                /* User clicked OK so do some stuff */

            	}
        }).create();
       


   
        
        
   }
    
    private void sdcard(){
    
    	
    	
    	File f = new File("/sdcard/iCua");
    	if (!f.exists()){
    	
		    	f = new File("/sdcard/iCua/media");
		 		boolean b = f.mkdirs();
		 		f = new File("/sdcard/iCua/data");
		 		b = b && f.mkdirs();
		 		f = new File("/sdcard/iCua/art/artist");
		 		b = b && f.mkdirs();
		 		f = new File("/sdcard/iCua/art/album");
		 		b = b && f.mkdirs();
		 		f = new File("/sdcard/iCua/lyrics");
		 		b = b && f.mkdirs();
    	

    		if (!b){
    		Dialog d =new AlertDialog.Builder(iCua.this)
            .setIcon(R.drawable.icon)
            .setTitle("Needs a SDCARD to run iCua")
            .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	
                iCua.this.finish();
                    /* User clicked OK so do some stuff */

                	}
            }).create();
    		
    		d.show();
    
    		}else{
    			
    			CtrlData.createDatabase();
    		}
    	}
   
    	
    }

    private void goPlayNow(){
    	
    	if (totalSongs ==0){
        	

         	
            d.show();
       		
       
       		
       		
       	}else{
    	
    	
	    	Intent i = new Intent(this, OnAir.class);
	    	i.putExtra("type", 0);
	    	i.putExtra("timestamp", System.currentTimeMillis());
	    	startActivity(i);
    	}	
    }
    
    private void goArtists(){
    	if (totalSongs ==0){
        	

         	
            d.show();
       		
       
       		
       		
       	}else{
    	Intent i = new Intent(this, ArtistsList.class);
    	
    	startActivity(i);
       	}
    }
    
    private void goAlbums(){
    	if (totalSongs ==0){
        	

         	
            d.show();
       		
       
       		
       		
       	}else{
    	Intent i = new Intent(this, AlbumsList.class);
    	
    	startActivity(i);
       	}
    }
    
    private void goRadio(){
    	Intent i = new Intent(this, LastRadio.class);
    
    //	Intent i = new Intent(this, LastRadio.class);
    	
    	startActivity(i);
    	
    }
    private void goStream(){
    	Intent i = new Intent(this, About.class);
    	i.putExtra("type", -10);
    //	Intent i = new Intent(this, LastRadio.class);
    	
    	startActivity(i);
    	
    }
    private void goPlaylists(){
    	if (totalSongs ==0){
        	

         	
            d.show();
       		
       
       		
       		
       	}else{
    	Intent i = new Intent(this, Playlists.class);
    	startActivity(i);
       	}
    }
    private void goConfig(){
    	Intent i = new Intent(this, Config.class);
    	i.putExtra("type", -10);
    	
    	startActivity(i);
    	
    }
    

    
    private void goAbout(){
     	Intent i = new Intent(this, About.class);
    	
    	startActivity(i);
    	
    }
    
	 private AdapterView.OnItemClickListener l = new AdapterView.OnItemClickListener() {
		public void onItemClick(android.widget.AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			
			switch ( arg2 ) {
		      case 0:		    	
		    	  	    	  
		          goPlayNow();
		           break;
		      case 1:
		          goArtists();
		           break;
		      case 2:
		          goAlbums();
		           break;
		      case 3:
		          goRadio();
		           break;
		      case 4 :
		    	  goStream();
		    	  break;
		      case 5:
		          goPlaylists();
		           break;
		      case 6 :
		    	  goConfig();
		    	  break;
		      case 7:
		    	  goAbout();
		           break;

		      default:
		           goAbout();
		           break;
		      }

			
		};
	    };
    
   
}