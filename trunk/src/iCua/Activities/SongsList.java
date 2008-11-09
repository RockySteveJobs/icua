/**
 * 
 */
package iCua.Activities;


import iCua.Data.CtrlDades;
import iCua.Data.Adapters.SongAdapter;
import iCua.Media.Song;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author devil
 *
 */
public class SongsList  extends ListActivity {
private String title = null;
private int id_album = 0;
private SongAdapter adp = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  
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
        setListAdapter(adp);
        getListView().setTextFilterEnabled(true);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	// TODO Auto-generated method stub
    	
    	

    	
    	Intent i = new Intent(this, OnAir.class);
    	i.putExtra("type", 1);
     	i.putExtra("timestamp", System.currentTimeMillis());
    	i.putExtra("song", ""+((Song)adp.getItem(position)).id);
    
    	//i.putExtra("artist", new String[]{mStrings[position] });
    	startActivity(i);
    	
    }
    

}
