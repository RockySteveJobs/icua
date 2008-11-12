package iCua.Activities;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AddContent extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	  super.onCreate(savedInstanceState);
          setContentView(R.layout.playlists);
          
          ListView     lv =(ListView) findViewById(android.R.id.list);
       lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, GENRES));
        


        lv.setItemsCanFocus(false);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        Toast.makeText(this, "Select songs to add to the playlist", Toast.LENGTH_LONG);
    }
    
    
    private static final String[] GENRES = new String[] {
        "Blur - Song 2", "Muse - New Born", "Muse - Supermassive Blackhole", "Children", "Comedy", "Documentary", "Drama",
        "Foreign", "History", "Independent", "Romance", "Sci-Fi", "Television", "Thriller"
    };
    
}