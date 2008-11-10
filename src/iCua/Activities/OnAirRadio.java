/**
 * 
 */
package iCua.Activities;


import java.io.File;





import iCua.Components.HorizontalSlider;
import iCua.Components.HorizontalSlider.OnProgressChangeListener;
import iCua.Data.CtrlDades;
import iCua.Data.LastFM;
import iCua.Media.LastFMClient;
import iCua.Media.MediaPlayeriCua;
import iCua.Media.Song;
import iCua.Services.IRemoteService;
import iCua.Services.IRemoteServiceCallback;
import iCua.Services.ISecondary;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
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
public class OnAirRadio extends Activity{
	
	  /** The primary interface we will be calling on the service. */
    IRemoteService mService = null;
    /** Another interface we use on the service. */
    ISecondary mSecondaryService = null;
    
    
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
    LastFMClient lc = new LastFMClient("cuacua", "bocaboca");
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		 setTheme(R.style.Theme_notitle);
		super.onCreate(savedInstanceState);
        setContentView(R.layout.onairradio);
   

 
        txt = (TextView) findViewById(R.id.txt);
        img = (ImageView) findViewById(R.id.cover);

    	

    	
  	  bindService(new Intent("iCua.Services.IRemoteService"),
              mConnection, Context.BIND_AUTO_CREATE);
      bindService(new Intent("iCua.Services.ISecondary"),
              mSecondaryConnection, Context.BIND_AUTO_CREATE);
      mIsBound = true;

  

        bNext = (ImageButton)findViewById(R.id.next);
        bNext.setOnClickListener(lnext);
        

        
            

    }
	public void init() {
    	// TODO Auto-generated method stub
    	
   
           
        try {
     

        		
        		
        		if (!mSecondaryService.isTimeStamp(timestamp)){
         	
        	       //Song[] sgs = lc.getSongs();
        	       
        	       

        	//        System.out.println(sgs.length);	
        			
        			mSecondaryService.setTimeStamp(timestamp);
        	
        			mSecondaryService.PlayStream();
        		}
        	
        	
        	
        	
            } catch (Exception ex) {
               
                Toast.makeText(OnAirRadio.this,
                        "Error al Reproducir",
                        Toast.LENGTH_SHORT).show();
            }
	}
   
	private void setImage(){
    	
    	//Song saux= mp.getSong();
    	
    	
try{
	
//	txt.setText(saux.artist +" - " +saux.title );
	String art = mSecondaryService.getArt();
	
	File faux = new File(art);
     if (faux.exists())
     {
    	 img.setImageBitmap(BitmapFactory.decodeFile(art));
    	  
     }else{

    		 img.setImageBitmap(BitmapFactory.decodeFile("/sdcard/iCua/art/artist/nofoto.jpg"));
    
     }
     txt.setText(mSecondaryService.getArtist()+" - "+mSecondaryService.getTitle());

     
}catch(Exception ex11){
	
	System.out.println(ex11.getMessage());
}
    }
	

		
	
	
	
	   
	    private OnClickListener lnext = new OnClickListener() {
	        public void onClick(View v) {
	            // Cancel a previous call to startService().  Note that the
	            // service will not actually stop at this point if there are
	            // still bound clients.
	        	
	            if (mSecondaryService != null) {
	                try {
	                	mSecondaryService.nextSong();
	                 
	                } catch (RemoteException ex) {
	                   
	                    Toast.makeText(OnAirRadio.this,
	                            "Error al Reproducir",
	                            Toast.LENGTH_SHORT).show();
	                }
	            }
	     	setImage();
	        }
	    };
	   
	    /* Conexions al servei*/
	  
	    
	    private ServiceConnection mConnection = new ServiceConnection() {
	        public void onServiceConnected(ComponentName className,
	                IBinder service) {
	            // This is called when the connection with the service has been
	            // established, giving us the service object we can use to
	            // interact with the service.  We are communicating with our
	            // service through an IDL interface, so get a client-side
	            // representation of that from the raw service object.
	            mService = IRemoteService.Stub.asInterface(service);
	           // mKillButton.setEnabled(true);
	            //mCallbackText.setText("Attached.");

	            // We want to monitor the service for as long as we are
	            // connected to it.
	            try {
	                mService.registerCallback(mCallback);
	               
	            } catch (RemoteException e) {
	                // In this case the service has crashed before we could even
	                // do anything with it; we can count on soon being
	                // disconnected (and then reconnected if it can be restarted)
	                // so there is no need to do anything here.
	            }
	            
	            // As part of the sample, tell the user what happened.
	            Toast.makeText(OnAirRadio.this, "conectado al player",
	                    Toast.LENGTH_SHORT).show();
	        }

	        public void onServiceDisconnected(ComponentName className) {
	            // This is called when the connection with the service has been
	            // unexpectedly disconnected -- that is, its process crashed.
	            mService = null;
	          //  mKillButton.setEnabled(false);
	     //       mCallbackText.setText("Disconnected.");

	            // As part of the sample, tell the user what happened.
	            Toast.makeText(OnAirRadio.this, "Desconectado del player",
	                    Toast.LENGTH_SHORT).show();
	        }
	    };

	    /**
	     * Class for interacting with the secondary interface of the service.
	     */
	    private ServiceConnection mSecondaryConnection = new ServiceConnection() {
	        public void onServiceConnected(ComponentName className,
	                IBinder service) {
	            // Connecting to a secondary interface is the same as any
	            // other interface.
	            mSecondaryService = ISecondary.Stub.asInterface(service);
	            init();	            
	            setImage();
	                      
	            //   mKillButton.setEnabled(true);
	        }

	        public void onServiceDisconnected(ComponentName className) {
	            mSecondaryService = null;
	       //     mKillButton.setEnabled(false);
	        }
	    };
	    
	    
	    private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() {
	        /**
	         * This is called by the remote service regularly to tell us about
	         * new values.  Note that IPC calls are dispatched through a thread
	         * pool running in each process, so the code executing here will
	         * NOT be running in our main thread like most other things -- so,
	         * to update the UI, we need to use a Handler to hop over there.
	         */
	        public void valueChanged(int value) {
	            mHandler.sendMessage(mHandler.obtainMessage(BUMP_MSG, value, 0));
	         
	           
	        }
	        
	        @Override
	        public void songChanged() {
	        	// TODO Auto-generated method stub
	        	   mHandler.sendMessage(mHandler.obtainMessage(UPDATE_MSG, 0, 0));
	  	         
	        }
	    };
	    
	    private static final int BUMP_MSG = 1;
	    private static final int UPDATE_MSG = 2;
	    private Handler mHandler = new Handler() {
	        @Override public void handleMessage(Message msg) {
	            switch (msg.what) {
	                case BUMP_MSG:
	                
	         
	                    break;
	                case UPDATE_MSG:
	                	setImage();
	                break;
	                default:
	                    super.handleMessage(msg);
	            }
	        }
	        
	    };   
	    
	    
	    
	    
	    
	    
}
