package iCua.Activities;

import java.util.ArrayList;

import iCua.Data.CtrlData;
import iCua.Data.Adapters.SongAdapter;
import iCua.Media.Song;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Playlists extends android.app.Activity implements
AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener  {
	

private Animation anim= null;
private View cache = null;
private int[] _pl= null;
private ListView lv =null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlists);
        
        anim = AnimationUtils.loadAnimation(this, R.anim.magnify2);
        lv =(ListView) findViewById(android.R.id.list);
        
    ArrayList<String> playlists = new ArrayList<String>();
    ContentValues[] vc = CtrlData.getPlaylistsNames();
    _pl= new int[vc.length+1];
    playlists.add(" -> Create Playlist <-");
    for(int i = 0 ;i<vc.length;i++)
    {
        playlists.add(vc[i].getAsString("name"));
        _pl[i+1]=vc[i].getAsInteger("id");
    	
    }  
    
    playlists.add("Most Played");

 

   
    	 
    	 
lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,playlists));
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
    	
    	Intent i = new Intent(this, AddContent.class);

    
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
