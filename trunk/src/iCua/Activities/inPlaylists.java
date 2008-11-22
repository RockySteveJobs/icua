/**
 * 
 */
package iCua.Activities;



import iCua.Data.CtrlData;
import iCua.Data.Adapters.PlaylistAdapter;
import iCua.Data.Adapters.SongAdapter;
import iCua.Media.Song;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * @author devil
 *
 */
public class inPlaylists  extends android.app.Activity implements
AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener  {
	

private PlaylistAdapter adp = null;
private Animation anim= null;
private View cache = null;
private int id_pl=0;
private ListView lv =null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
       
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.animations_main_screen);
        
        anim = AnimationUtils.loadAnimation(this, R.anim.magnify2);
        lv =(ListView) findViewById(android.R.id.list);
    	String id_artista = null;
    	
    	Bundle extras = getIntent().getExtras();
    	if (extras != null) {
    		id_pl = extras.getInt("id_playlist");
    		
    		 
    	}
    
    	Song[] a= CtrlData.getSongs(id_pl);
    
        // Use an existing ListAdapter that will map an array
        // of strings to TextViews
    	adp = new PlaylistAdapter(this,a);
        lv.setAdapter(adp);
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(this);
        lv.setOnItemSelectedListener(this);
    //    mContainer.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
    }

    @Override
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
    	// TODO Auto-generated method stub
  //      mImageView.setImageResource(PHOTOS_RESOURCES[position]);
 
    		v.startAnimation(anim);
    	
    	Intent i = new Intent(this, OnAir.class);
     	i.putExtra("timestamp", System.currentTimeMillis());
     	
     	switch ( position ) {
        case 0:		    	
      	  	   CtrlData.delPlaylist(id_pl);
      	  	   inPlaylists.this.finish();
      	  	    break;
        case 1:		    	
    		
    	    i.putExtra("type", 5);
        	i.putExtra("playlist", id_pl );
        	startActivity(i);
  	  	    break;
        default:		    	
     		i.putExtra("type", 1);
    		i.putExtra("song", ""+(adp.getItemId(position)));
    	  	startActivity(i);
     	}
     	

    	
    	//i.putExtra("artist", new String[]{mStrings[position] });
  
    	
    }
    
   
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
    		long arg3) {
    	// TODO Auto-generated method stub
    	if (cache!= null)cache.clearAnimation();
    		
    		cache =arg1;
    	arg1.startAnimation(anim);
    	
    }
    
   @Override
public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
	
}



}
