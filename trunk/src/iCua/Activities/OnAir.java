/**
 * 
 */
package iCua.Activities;


import java.io.File;





import iCua.Components.HorizontalSlider;
import iCua.Components.HorizontalSlider.OnProgressChangeListener;
import iCua.Data.CtrlData;
import iCua.Data.LastFM;
import iCua.Media.MediaPlayeriCua;
import iCua.Media.Song;
import iCua.Services.IRemoteService;
import iCua.Services.IRemoteServiceCallback;
import iCua.Services.ISecondary;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
public class OnAir extends Activity{
	
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
	private int _playlist = -1;
	private long timestamp=-1;	
	private Dialog d = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		 setTheme(R.style.Theme_notitle);
		super.onCreate(savedInstanceState);
        setContentView(R.layout.onair);
        
        barra= (HorizontalSlider)findViewById(R.id.slider);
        barra.setMax(100);
        barra.setOnProgressChangeListener(lbarra);
    
        txt = (TextView) findViewById(R.id.txt);
        img = (ImageView) findViewById(R.id.cover);
        img.setImageBitmap(BitmapFactory.decodeFile("/sdcard/iCua/art/album/137.jpg"));

     

      bPause = (ImageButton)findViewById(R.id.stop);
      bPause.setOnClickListener(lstop);

        bNext = (ImageButton)findViewById(R.id.next);
        bNext.setOnClickListener(lnext);
        
        bPrev =(ImageButton)findViewById(R.id.prev);
        bPrev.setOnClickListener(lprev);
        bPlay =(ImageButton)findViewById(R.id.pause);
        bPlay.setOnClickListener(lpause);


    }
	
	
	@Override
	protected void onResume() {
	    startService(new Intent("iCua.Services.REMOTE_SERVICE"));
    	Bundle extras = getIntent().getExtras();
		if (extras != null) {
    		timestamp = extras.getLong("timestamp");
    		// id_song = extras.getIntArray("id");
    		typePlay = extras.getInt("type");
    		
    		switch (typePlay){
    		
    		case 1:
    			/* *
    			 * Play only one song selected
    			 * */
    			_song = extras.getString("song");
    			break;    			
    		case 2:
    			/* *
    			 * Play all album's songs of an artist
    			 * */
    			_album = extras.getString("album");
    			_artist = extras.getString("artist");
    			break;
    		case 3:
    			/* *
    			 * Play all artist's songs
    			 * */
    			_artist = extras.getString("artist");
    			break;
    		case 4:
    			/* *
    			 * Play all album's songs
    			 * */
    			_album = extras.getString("album");
    			break;
    		case 5:
    			/* *
    			 * Play all playlists songs
    			 * */
    			_playlist = extras.getInt("playlist");
    			break;		
    		};
    		

    	}
		  mIsBound = true;
	  	  bindService(new Intent("iCua.Services.IRemoteService"),
	              mConnection, Context.BIND_AUTO_CREATE);
	      bindService(new Intent("iCua.Services.ISecondary"),
	              mSecondaryConnection, Context.BIND_AUTO_CREATE);
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	
	
	
	public void init() {
    	// TODO Auto-generated method stub
    	
   boolean b = false;

        try {
        	if(typePlay != -1) {
	        	if (!mSecondaryService.isTimeStamp(timestamp)){        		
	        			b= mSecondaryService.SetPlaylist(_song, _artist, _album);    
	        			mSecondaryService.setTimeStamp(timestamp);
	        	}  
        	}
        	
         	if(_playlist!= -1) {
         	b=	mSecondaryService.LoadPlaylist(_playlist);
         	}
         	

 
        	mSecondaryService.playSong();
        	
            } catch (RemoteException ex) {
               
                Toast.makeText(OnAir.this,
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
    	
    		 img.setImageBitmap(BitmapFactory.decodeFile("/sdcard/iCua/art/artist/no_artist.gif"));
    	 
     }
     txt.setText(mSecondaryService.getArtist()+" - "+mSecondaryService.getTitle());
     barra.setMax(mSecondaryService.getDuration());
     
}catch(Exception ex11){
	
	System.out.println(ex11.getMessage());
}
    }
	
	private OnProgressChangeListener lbarra = new OnProgressChangeListener(){
		public void onProgressChanged(View v, int progress) {
			  if (mSecondaryService != null) {
	                try {
	                	mSecondaryService.seekSong(progress);
	                 
	                } catch (RemoteException ex) {
	                   
	                    Toast.makeText(OnAir.this,
	                            "Error al Reproducir",
	                            Toast.LENGTH_SHORT).show();
	                }
		}
			  }
		};
		 private OnClickListener lpause = new OnClickListener() {
		        public void onClick(View v) {
		        	Intent i = new Intent(OnAir.this, iCua.class);
		        	
		        	startActivity(i);
		        }
		        	
		          
		    };	
	
		    private OnClickListener lstop = new OnClickListener() {
		        public void onClick(View v) {
		            // Cancel a previous call to startService().  Note that the
		            // service will not actually stop at this point if there are
		            // still bound clients.
		        	
		            if (mSecondaryService != null) {
		                try {
		                	mSecondaryService.stopSong();
		                	unbindService(mSecondaryConnection);
		                	unbindService(mConnection);
		                } catch (RemoteException ex) {
		                   
		                    Toast.makeText(OnAir.this,
		                            "Error al Reproducir",
		                            Toast.LENGTH_SHORT).show();
		                }
		            }
		     	//setImage();
		     	
		       stopService(new Intent("iCua.Services.REMOTE_SERVICE"));
		       OnAir.this.finish();
		        }
		    };	
	
	
	 private OnClickListener lprev = new OnClickListener() {
	        public void onClick(View v) {
	            // Cancel a previous call to startService().  Note that the
	            // service will not actually stop at this point if there are
	            // still bound clients.
	        	
	            if (mSecondaryService != null) {
	                try {
	                	mSecondaryService.prevSong();
	                 
	                } catch (RemoteException ex) {
	                   
	                    Toast.makeText(OnAir.this,
	                            "Error al Reproducir",
	                            Toast.LENGTH_SHORT).show();
	                }
	            }
	     	setImage();
	        }
	    };
	    
	   
	    private OnClickListener lnext = new OnClickListener() {
	        public void onClick(View v) {
	            // Cancel a previous call to startService().  Note that the
	            // service will not actually stop at this point if there are
	            // still bound clients.
	        	
	            if (mSecondaryService != null) {
	                try {
	                	mSecondaryService.nextSong();
	                 
	                } catch (RemoteException ex) {
	                   
	                    Toast.makeText(OnAir.this,
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
	            Toast.makeText(OnAir.this, "conectado al player",
	                    Toast.LENGTH_SHORT).show();
	        }

	        public void onServiceDisconnected(ComponentName className) {
	            // This is called when the connection with the service has been
	            // unexpectedly disconnected -- that is, its process crashed.
	            mService = null;
	          //  mKillButton.setEnabled(false);
	     //       mCallbackText.setText("Disconnected.");

	            // As part of the sample, tell the user what happened.
	            Toast.makeText(OnAir.this, "Desconectado del player",
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
	                	   barra.setProgress(msg.arg1);
	                  //  mCallbackText.setText("Received from service: " + );
	                    break;
	                case UPDATE_MSG:
	                	//setImage();
	                break;
	                default:
	                    super.handleMessage(msg);
	            }
	        }
	        
	    };   
	    
	    
	    
	    
	    
	    
}
