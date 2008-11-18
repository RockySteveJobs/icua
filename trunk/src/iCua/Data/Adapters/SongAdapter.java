package iCua.Data.Adapters;

import iCua.Media.Song;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SongAdapter extends BaseAdapter{
	private Context mContext;
	private Song[] mSongs= null; 

	public SongAdapter (Context mContext, Song[] songs){
		this.mContext = mContext;
		this.mSongs = songs;
	
	}
	
	@Override
public int getCount() {

	return mSongs.length+1;
}

@Override
public long getItemId(int position) {
	if(position==0)return -1;
	return mSongs[position-1].id;
}

@Override
public Object getItem(int position) {
	// TODO Auto-generated method stub
	if(position==0)return null;
	return mSongs[position-1];
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub
	TextView txt = new TextView(mContext);
	txt.setPadding(10, 10, 10, 10);
	if (position==0)txt.setText("Play all songs");
	else txt.setText(mSongs[position - 1].title);
	
	return txt;
}

}
