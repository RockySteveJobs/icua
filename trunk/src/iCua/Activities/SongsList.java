/**
 * 
 */
package iCua.Activities;



import iCua.Data.CtrlDades;
import iCua.Data.Adapters.SongAdapter;
import iCua.Media.Song;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.ListView;


/**
 * @author devil
 *
 */
public class SongsList  extends android.app.Activity implements
AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener  {
	
	private String title = null;
private int id_album = 0;
private SongAdapter adp = null;
private Animation anim= null;
private View cache = null;

private ListView lv =null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animations_main_screen);
        
        anim = AnimationUtils.loadAnimation(this, R.anim.magnify2);
        lv =(ListView) findViewById(android.R.id.list);
    	String id_artista = null;
    	int id_album=0;
    	Bundle extras = getIntent().getExtras();
    	if (extras != null) {
    		 id_album = extras.getInt("id_album");
    		
    		 if (extras.getInt("id_artist") >= 0)id_artista= ""+extras.getInt("id_artist");
    		 
    	}
    
    	Song[] a= CtrlDades.getSongs(id_artista, null,  id_album +"");
    
        // Use an existing ListAdapter that will map an array
        // of strings to TextViews
    	adp = new SongAdapter(this,a);
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
    	i.putExtra("type", 1);
     	i.putExtra("timestamp", System.currentTimeMillis());
    	i.putExtra("song", ""+((Song)adp.getItem(position)).id);
    
    	//i.putExtra("artist", new String[]{mStrings[position] });
    	startActivity(i);
    	
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
