/**
 * 
 */
package iCua.Activities;


import java.io.File;






import iCua.Components.HorizontalSlider;
import iCua.Data.CtrlData;
import iCua.Data.LastFM;
import iCua.Media.LastFMClient;
import iCua.Media.MediaPlayeriCua;
import iCua.Media.Song;
import iCua.Media.StreamingMediaPlayer;
import iCua.Services.IRemoteService;
import iCua.Services.IRemoteServiceCallback;
import iCua.Services.ISecondary;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author devil
 *
 */
public class OnAirRadio extends Activity implements OnCompletionListener{
	
	private StreamingMediaPlayer mp ;
    
    
	private boolean mIsRegistred = false;
	private boolean mIsBound = false;
	private ImageView img = null;
	private TextView txt = null;
	private ImageButton bNext,bPrev,bPause,bPlay;
	private HorizontalSlider barra;
	private int typePlay = -1;
	private String _artist=null;
	private String _album = null;
	private String _song = null;
	private long timestamp=-1;	
    LastFMClient lc ;
	
    /**
     * testing progress dialog
     * 
     */
    
    private ProgressDialog mProgressDialog;
    private int mProgress;
    private Handler mProgressHandler;
    
    /**	
     * 
     * */
    
    public void onCompletion(MediaPlayer arg0) {
    	mp.nextSong();
    	
    };
	@Override
    public void onCreate(Bundle savedInstanceState) {
		 setTheme(R.style.Theme_notitle);
		super.onCreate(savedInstanceState);
        setContentView(R.layout.onairradio);
        mProgressHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (mProgress >= StreamingMediaPlayer.INTIAL_KB_BUFFER) {
                    mProgressDialog.dismiss();
                } else {
                	mProgress = mp.totalKbRead;
                	System.out.println(mProgress);
                    mProgressDialog.setProgress(mProgress);
                    
                    mProgressHandler.sendEmptyMessageDelayed(0, 100);
                }
            }
        };

        
   

        txt = (TextView) findViewById(R.id.txt);
        img = (ImageView) findViewById(R.id.cover);
        
      
   //     bNext = (ImageButton)findViewById(R.id.next);
  //      bNext.setOnClickListener(lnext);
        mProgressDialog = new ProgressDialog(OnAirRadio.this);
        mProgressDialog.setIcon(R.drawable.icon);
        mProgressDialog.setTitle("Loading the Radio...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(StreamingMediaPlayer.INTIAL_KB_BUFFER);
      
   
        lc = new LastFMClient("cuacua", "bocaboca");
        mp = new StreamingMediaPlayer(this);
        mp.setOnCompletionListener(this);
        init();
            

    }
	
	
	
	public void init() {
    	// TODO Auto-generated method stub
    	
   
           
        try {
     

        		Song[] tmp = lc.getSongs();
        		for (int i = tmp.length-1; i>=0;i--) mp.addSong(tmp[i]);
        		
        	
        		mp.playSong();
                mProgress = 0;
                mProgressDialog.setProgress(0);
                mProgressHandler.sendEmptyMessage(0);
        		mProgressDialog.show();
        		
        	
        	
            } catch (Exception ex) {
               
                Toast.makeText(OnAirRadio.this,
                        "Error al Reproducir",
                        Toast.LENGTH_SHORT).show();
            }
	}
   
	public  void setImage(){
    	
    	mHandler.sendEmptyMessage(UPDATE_MSG);
    	
    	

    }
	

		
	
	
	
	   
	    private OnClickListener lnext = new OnClickListener() {
	        public void onClick(View v) {
	            // Cancel a previous call to startService().  Note that the
	            // service will not actually stop at this point if there are
	            // still bound clients.
	        	
	         
	                	mp.skipSong();
	                 
	             
	                	mHandler.sendEmptyMessage(UPDATE_MSG);
	        }
	    };
	   
	    /* Conexions al servei*/
	  
	    
	   
	    private static final int BUMP_MSG = 1;
	    private static final int UPDATE_MSG = 2;
	    private Handler mHandler = new Handler() {
	        @Override public void handleMessage(Message msg) {
	        
	            switch (msg.what) {

	                	
	         
	                case UPDATE_MSG:

	                	try{
	                		
//	                		txt.setText(saux.artist +" - " +saux.title );
	                		Song s = mp.getSong();
	                		String art = s.art;
	                	    txt.setText(s.artist+" - "+s.title);

	                		File faux = new File(art);
	                	     if (faux.exists())
	                	     {
	                	    	 img.setImageBitmap(BitmapFactory.decodeFile(art));
	                	    	  
	                	     }else{

	                	    		 img.setImageBitmap(BitmapFactory.decodeFile("/sdcard/iCua/art/artist/nofoto.jpg"));
	                	    
	                	     }
	                	     
	                	 
	                	     
	                	}catch(Exception ex11){
	                		
	                		System.out.println(ex11.getMessage());
	                	}
	                break;
	                default:
	                    super.handleMessage(msg);
	            }
	        }
	        
	    };   
	    
	    
	
	    
	    
	    
}
