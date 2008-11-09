/**
 * 
 */
package iCua.Activities;



import iCua.Data.CtrlDades;
import iCua.Media.Artist;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author devil
 *
 */
public class ArtistsList extends ListActivity implements  ListView.OnScrollListener {

	  private final class RemoveWindow implements Runnable {
	        public void run() {
	            removeWindow();
	        }
	    }

	    private RemoveWindow mRemoveWindow = new RemoveWindow();
	    Handler mHandler = new Handler();
	    private WindowManager mWindowManager;
	    private TextView mDialogText;
	    private boolean mShowing;
	    private boolean mReady;
	    private char mPrevLetter = Character.MIN_VALUE;
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	       Artist[] a = CtrlDades.getArtists();
	       
	       mStrings = new String[a.length];
	       mIds = new int[a.length];
	       for (int i = 0 ; i< a.length ; i++){
	    	   
	    	   
	    	   mStrings[i]= a[i].name;
	    	   mIds[i]= a[i].id;
	       }
	       
	        mWindowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
	        
	        // Use an existing ListAdapter that will map an array
	        // of strings to TextViews
	        setListAdapter(new ArrayAdapter<String>(this,
	                android.R.layout.simple_list_item_1, mStrings));
	        
	        
	        
	        getListView().setOnScrollListener(this);
	        
	        LayoutInflater inflate = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        
	        mDialogText = (TextView) inflate.inflate(R.layout.list_position, null);
	        
	      
	        mDialogText.setVisibility(View.INVISIBLE);
	        
	        mHandler.post(new Runnable() {

	            public void run() {
	                mReady = true;
	                WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
	                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
	                        WindowManager.LayoutParams.TYPE_APPLICATION,
	                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
	                                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
	                        PixelFormat.TRANSLUCENT);
	                mWindowManager.addView(mDialogText, lp);
	            }});
	    }
	    
	    @Override
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	    	// TODO Auto-generated method stub
	    	
	    	Toast.makeText(this, mStrings[position]+" "+position ,Toast.LENGTH_LONG );
	    	
	    	
	    	Intent i = new Intent(this, AlbumsList.class);
	    	i.putExtra("id", mIds[position]);
	    	
	    	i.putExtra("artist", mStrings[position] );
	    	startActivity(i);
	    	
	    }
	    
	    
	    @Override
	    protected void onResume() {
	        super.onResume();
	        mReady = true;
	    }

	    
	    @Override
	    protected void onPause() {
	        super.onPause();
	        removeWindow();
	        mReady = false;
	    }

	    @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        mWindowManager.removeView(mDialogText);
	        mReady = false;
	    }

	    
	   
	    
	    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	        int lastItem = firstVisibleItem + visibleItemCount - 1;
	        if (mReady) {
	            char firstLetter = mStrings[firstVisibleItem].charAt(0);
	            
	            if (!mShowing && firstLetter != mPrevLetter) {

	                mShowing = true;
	                mDialogText.setVisibility(View.VISIBLE);
	               

	            }
	            mDialogText.setText(((Character)firstLetter).toString());
	            mHandler.removeCallbacks(mRemoveWindow);
	            mHandler.postDelayed(mRemoveWindow, 3000);
	            mPrevLetter = firstLetter;
	        }
	    }
	    

	    public void onScrollStateChanged(AbsListView view, int scrollState) {
	    }
	    
	    
	    private void removeWindow() {
	        if (mShowing) {
	            mShowing = false;
	            mDialogText.setVisibility(View.INVISIBLE);
	        }
	    }

	    private String[] mStrings = null;
	    private int[] mIds = null;
	}