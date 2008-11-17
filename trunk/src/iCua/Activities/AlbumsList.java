/**
 * 
 */
package iCua.Activities;

import iCua.Data.CtrlData;
import iCua.Media.Album;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;



/**
 * @author devil
 *
 */
public class AlbumsList extends ListActivity {
	Animation anim = null;
	private int id_artista=0;
	private String nombre = null;
	private EfficientAdapter adp;
    private static class EfficientAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        private Bitmap mIcon1;
        private Bitmap mIcon2;
        private String[] DATA = null;
        private int[] mIds = null;
        private int[] aIds = null;
        
        public EfficientAdapter(Context context, Album[] a) {
          
        	DATA = new String[a.length];
        	mIds = new int[a.length];
        	aIds = new int[a.length];
        	for (int i =0; i< a.length; i++){
        		
        		 DATA[i]= a[i].name;
    	    	   mIds[i]= a[i].id;
    	    	   aIds[i]= a[i].artist;
        		
        	}
        	
        	// Cache the LayoutInflate to avoid asking for a new one each time.
            mInflater = LayoutInflater.from(context);

            // Icons bound to the rows.
            mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.i1);
            mIcon2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.i3);
        }

        /**
         * The number of items in the list is determined by the number of speeches
         * in our array.
         *
         * @see android.widget.ListAdapter#getCount()
         */
        public int getCount() {
            return DATA.length;
        }

        /**
         * Since the data comes from an array, just returning the index is
         * sufficent to get at the data. If we were using a more complex data
         * structure, we would return whatever object represents one row in the
         * list.
         *
         * @see android.widget.ListAdapter#getItem(int)
         */
        public Object getItem(int position) {
            return (new Album(mIds[position], DATA[position], aIds[position]));
        }

        /**
         * Use the array index as a unique id.
         *
         * @see android.widget.ListAdapter#getItemId(int)
         */
        public long getItemId(int position) {
            return position;
        }

        /**
         * Make a view to hold each row.
         *
         * @see android.widget.ListAdapter#getView(int, android.view.View,
         *      android.view.ViewGroup)
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            // A ViewHolder keeps references to children views to avoid unneccessary calls
            // to findViewById() on each row.
            ViewHolder holder;

            // When convertView is not null, we can reuse it directly, there is no need
            // to reinflate it. We only inflate a new View when the convertView supplied
            // by ListView is null.
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_icon_text, null);

                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.text);
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);

                convertView.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }

            // Bind the data efficiently with the holder.
            holder.text.setText(DATA[position]);
            Bitmap b = BitmapFactory.decodeFile("/sdcard/iCua/art/album/"+mIds[position]+".jpg");
            if (b == null){
            	 b = BitmapFactory.decodeFile("/sdcard/iCua/art/artist/"+aIds[position]+".jpg");
            }
            if (b == null){
           	 b = BitmapFactory.decodeFile("/sdcard/iCua/art/artist/none.jpg");
           }
            
            holder.icon.setImageBitmap(b);

            return convertView;
        }

        static class ViewHolder {
            TextView text;
            ImageView icon;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	Album[] a;
    	Bundle extras = getIntent().getExtras();
    	if (extras != null) {
    		 nombre = extras.getString("artist");
    		 id_artista = extras.getInt("id");
    		 a= CtrlData.getAlbums(id_artista);
    	}else{    		
    		a= CtrlData.getAlbums();
    		id_artista = -1;
    	}
    anim = AnimationUtils.loadAnimation(this, R.anim.magnify);
    	
    	
    	super.onCreate(savedInstanceState);
    	adp = new EfficientAdapter(this,a);
        setListAdapter(adp);
       
      
    }
    
    

    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	// TODO Auto-generated method stub
    	  v.startAnimation( anim );

    
    	Album aux =(Album)adp.getItem(position);
    	
    	//Toast.makeText(this,aux.name+" "+position ,Toast.LENGTH_LONG );
    	
    	
    	Intent i = new Intent(this, SongsList.class);
    	i.putExtra("id_artist", id_artista);    	
    	i.putExtra("artist", nombre );
    	i.putExtra("id_album", aux.id);
    	i.putExtra("title", aux.name);
    	
    	startActivity(i);
    	
    }
    

}
