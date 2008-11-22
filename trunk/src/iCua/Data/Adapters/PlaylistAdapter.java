package iCua.Data.Adapters;

import iCua.Media.Song;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PlaylistAdapter extends BaseAdapter{
	private Context mContext;
	private Song[] mSongs= null; 

	public PlaylistAdapter (Context mContext, Song[] songs){
		this.mContext = mContext;
		this.mSongs = songs;
	
	}
	
	@Override
public int getCount() {

	return mSongs.length+2;
}

@Override
public long getItemId(int position) {
	if(position<2)return -1;
	return mSongs[position-2].id;
}

@Override
public Object getItem(int position) {
	// TODO Auto-generated method stub
	if(position<2)return null;
	return mSongs[position-2];
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub
	TextView txt = new TextView(mContext);
	txt.setPadding(10, 10, 10, 10);
	switch ( position ) {
    case 0:		    	
  	  	    txt.setText("Delete Playlist");
  	  	    break;
    case 1:
	  	    txt.setText("Play all songs");
  	  	   
    	  	break;
	default :
	  	 txt.setText(mSongs[position - 2].title);
	}
	return txt;
}

}
