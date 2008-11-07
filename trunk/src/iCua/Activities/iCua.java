package iCua.Activities;

import java.sql.Time;

import iCua.Components.HorizontalSlider;
import iCua.Data.CtrlDades;
import iCua.Data.LastFM;
import iCua.Data.Adapters.ImageAdapter;
import iCua.Interfaces.IPod;
import iCua.Media.Song;
import android.app.Activity;
import android.content.Intent;
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
    IPod mService = null;
    Button mKillButton;
    TextView mCallbackText;
   

    boolean mIsBound= false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageView portada = (ImageView) findViewById(R.id.ImageView01);
        portada.setImageResource(R.drawable.logo);
   
        GridView gv = (GridView) findViewById(R.id.GridView01);
        gv.setAdapter(new ImageAdapter(this)); 

        gv.setOnItemClickListener(l);
        //gv.setNumColumns(4);
        
        CtrlDades.scan();
      String key =  LastFM.login("cuacua", "bocaboca");
        LastFM.tuneTag(key, "britpop");
        LastFM.getSongs(key);
        startService(new Intent("iCua.Services.REMOTE_SERVICE"));
  
        
   }

    private void goPlayNow(){
    	Intent i = new Intent(this, OnAir.class);
    	i.putExtra("type", 0);
    	i.putExtra("timestamp", System.currentTimeMillis());
    	startActivity(i);
    	
    }
    
    private void goArtists(){
    	Intent i = new Intent(this, ArtistsList.class);
    	
    	startActivity(i);
    	
    }
    
    private void goAlbums(){
    	Intent i = new Intent(this, AlbumsList.class);
    	
    	startActivity(i);
    	
    }
    
    private void goRadio(){
    	Intent i = new Intent(this, LastRadio.class);
    	
    	startActivity(i);
    	
    }
    private void goStream(){
    	Intent i = new Intent(this, Streaming.class);
    	
    	startActivity(i);
    	
    }
    private void goPlaylists(){
    	Intent i = new Intent(this, About.class);
    	
    	startActivity(i);
    	
    }
    private void goConfig(){
    	Intent i = new Intent(this, About.class);
    	
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
		      default:
		           goAbout();
		           break;
		      }

			
		};
	    };
    
   
}