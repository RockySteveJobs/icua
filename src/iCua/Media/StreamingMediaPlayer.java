package iCua.Media;

import iCua.Activities.OnAirRadio;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import java.util.Stack;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

import android.util.Log;


/**
 * MediaPlayer does not yet support streaming from external URLs so this class provides a pseudo-streaming function
 * by downloading the content incrementally & playing as soon as we get enough audio in our temporary storage.
 */
public class StreamingMediaPlayer extends MediaPlayer implements IMP {
	
    public static final int INTIAL_KB_BUFFER =  (128*30)/8;//assume 96kbps*10secs/8bits per byte
    private long tope= 0;
    private boolean streaming = false;
    private boolean init= false;
	//  Track for display by progressBar
	public int totalKbRead = 0;

	// Create Handler to call View updates on the main UI thread.

private Song nowSong= null;
	private OnAirRadio parent;
	private File downloadingMediaFile; 
	private File 	bufferedFile;	
	private Stack<Song> songs  = new Stack<Song>(); 
	private Song downSong;
	private Stack<Song> URLSongs = new Stack<Song>();
	private boolean isInterrupted;
	private boolean skipDownload = false;

	
	
	public StreamingMediaPlayer(OnAirRadio parent) {
		super();
		this.parent= parent;

		// TODO Auto-generated constructor stub
	}
	
@Override
public void setPlaylist(String a, String t, String ab) {
	// TODO Auto-generated method stub
	
}
	
    /**  
     * Progressivly download the media to a temporary location and update the MediaPlayer as new content becomes available.
     */  
	@Override
    public void playSong() {


    	
    	
		Runnable r = new Runnable() {   
	        public void run() {   
	            try {   
	            	while(!URLSongs.isEmpty()){
	            		
	        		downloadAudioIncrement();
	            	}
	        		
	            } catch (IOException e) {
	            	Log.e(getClass().getName(), "Unable to initialize the MediaPlaer for fileUrl=" , e);
	            	return;
	            }   
	        }   
	    };   
	    new Thread(r).start();
	    
		Runnable r2 = new Runnable() {   
	        public void run() {   
	            try {   
	            	
	        startSong();
	        		
	        		
	            } catch (Exception e) {
	            	Log.e(getClass().getName(), "Unable to initialize the MediaPlaer for fileUrl=", e);
	            	return;
	            }   
	        }   
	    };   
	    new Thread(r2).start();
	    
	    
	    
	    
    }
    
    @Override
    public void stopSong() {
    	// TODO Auto-generated method stub
    	interrupt();
    	
    }
    
    @Override
    public void prevSong() {
    	// TODO Auto-generated method stub
    	
    }
    @Override
    public Song getSong(){
    	
    	return nowSong;
    	
    	
    }
    
    @Override
    public void addSong(Song s){
    	
    	URLSongs.push(s);
    	
    	
    }
    
    /**  
     * Download the url stream to a temporary location and then call the setDataSource  
     * for that local file
     */  
    public int getBuffer(){
    	
    	return ((this.totalKbRead / INTIAL_KB_BUFFER)*100) ;
    	
    }
    
    
    public void startSong(){
    	
    	if(songs.empty()){
    		this.init = skipDownload;
    		streaming = true;
    		nowSong = downSong;
    		playIncrement();
    		
    	}else{
    	
    		try{
    				this.nowSong= songs.pop();
    				this.setDataSource(nowSong.filename);
    				this.prepare();
    				this.start();
    		}catch(Exception ex3){
    			
    			
    		}
    		
    	}
    	
    }
    
    
    
    @Override
    public void nextSong(){
    	
    	if(songs.empty()){
    		this.reset();
    		nowSong = downSong;
    		this.streaming = true;
    	
    		this.init = false;
    		playIncrement();
    

    	}else{
    		streaming = false;
    		try{

    				this.reset();
    				this.nowSong= songs.pop();
    				this.setDataSource(nowSong.filename);
    				this.prepare();
    				this.start();
    		}catch(Exception ex3){
    			
    			
    		}
    		
    	}
    	
    	
    	
    }
 public void skipSong(){
	 
    	if(songs.empty()){
    	
    		skipDownload = true;
    		 this.reset();
    		this.reset();
    		nowSong = URLSongs.peek();
    		this.streaming = true;
  
    		playIncrement();
    

    	}else{
    		streaming = false;
    		try{

    				this.reset();
    				this.nowSong= songs.pop();
    				this.setDataSource(nowSong.filename);
    				this.prepare();
    				this.start();
    		}catch(Exception ex3){
    			
    			
    		}
    		
    	}
    	
    	parent.setImage();
    	
    }
    
    public void playIncrement(){
    	
  //  	String 	mediaUrl	= DownSongs.peek();
    		
    		

    	
    	
    }
    

    
    public void downloadAudioIncrement() throws IOException {
    	
    	
    	downSong = URLSongs.pop();
    	URLConnection cn = new URL(downSong.filename).openConnection(); 
    	
        cn.connect();   
        InputStream stream = cn.getInputStream();
        if (stream == null) {
        	Log.e(getClass().getName(), "Unable to create InputStream for mediaUrl:" );
        }
        
		downloadingMediaFile = File.createTempFile("downloadingMedia", ".dat");
		downSong.filename= downloadingMediaFile.getAbsolutePath();
		
		//DownSongs.push(downloadingMediaFile.getAbsolutePath());
		
        FileOutputStream out = new FileOutputStream(downloadingMediaFile);   
        byte buf[] = new byte[4096];
       int   totalBytesRead = 0;
         int incrementalBytesRead = 0;
        do {
        	int numread = stream.read(buf);   
            if (numread <= 0)   
                break;   
            out.write(buf, 0, numread);
            totalBytesRead += numread;
            incrementalBytesRead += numread;
            
            totalKbRead = totalBytesRead/1000;
            
            testMediaBuffer();
         
        } while (validateNotInterrupted()&& !skipDownload);   
        skipDownload = false;
       	stream.close();
        if (validateNotInterrupted() ) {
	       	fireDataFullyLoaded();
        }
    }  

    private boolean validateNotInterrupted() {
		if (isInterrupted) {
			if (this.isPlaying()) {
				this.pause();
				//mediaPlayer.release();
			}
			return false;
		} else {
			return true;
		}
    }

    
    /**
     * Test whether we need to transfer buffered data to the MediaPlayer.
     * Interacting with MediaPlayer on non-main UI thread can causes crashes to so perform this using a Handler.
     */  
    private void  testMediaBuffer() {
    	
    	if(streaming){

	            if (this.init == false) {
	            	//  Only create the MediaPlayer once we have the minimum buffered data
	            	if ( totalKbRead >= INTIAL_KB_BUFFER) {
	            		try {
		            		startMediaPlayer();
		            		this.init= true;
		            		
	            		} catch (Exception e) {
	            			Log.e(getClass().getName(), "Error copying buffered conent.", e);    			
	            		}
	            	}
	            } else {
	            	
	            	long tt = tope - this.getCurrentPosition() ;
	            	if ( tt <= 1000 ){ 
	            		Log.w(getClass().getName(), "ya tocaaaaaaaaaaaaaaa "+tt);
	            		//  NOTE:  The media player has stopped at the end so transfer any existing buffered data
	            	//  We test for < 1second of data because the media player can stop when there is still
	            	//  a few milliseconds of data left to play
	            			transferBufferToMediaPlayer();
	            	}else Log.w(getClass().getName(), "MEDIA "+this.getCurrentPosition()+" tope "+tope);
	            	
	            }
	        
    	}
    }
    
    private void startMediaPlayer() {
        try {   
        	File bufferedFile =downloadingMediaFile;// File.createTempFile("playingMedia", ".dat");
    		
        	//copyFile(downloadingMediaFile,bufferedFile);
    		tope = (bufferedFile.length()*8)/140;
    		this.parent.setImage();
    	
        	this.setDataSource(bufferedFile.getAbsolutePath());
    		this.prepare();
    		this.start();
        } catch (IOException e) {
        	Log.e(getClass().getName(), "Error initializing the MediaPlaer.", e);
        	return;
        }   
    }
    
    /**
     * Transfer buffered data to the MediaPlayer.
     * Interacting with MediaPlayer on non-main UI thread can causes crashes to so perform this using a Handler.
     */  
    private void transferBufferToMediaPlayer() {
	    try {
	    	// First determine if we need to restart the player after transferring data...e.g. perhaps the user pressed pause
	    
	    	int curPosition = this.getCurrentPosition();
	    	
	    
	    	bufferedFile = downloadingMediaFile;// File.createTempFile("playingMedia", ".dat");
	    	//copyFile(downloadingMediaFile,bufferedFile);
	    	tope = (bufferedFile.length()*8)/140;
	    	this.stop();
	    	this.reset();
    		this.setDataSource(bufferedFile.getAbsolutePath());
    		//mediaPlayer.setAudioStreamType(AudioSystem.STREAM_MUSIC);
    		this.prepare();
    		this.seekTo(curPosition);
    		this.start();

  
        	
		}catch (Exception e) {
	    	Log.e(getClass().getName(), "Error updating to newly loaded content.", e);            		
		}
    }
    

    
    /**
     * We have preloaded enough content and started the MediaPlayer so update the buttons & progress meters.
     */


    private void fireDataFullyLoaded() {
    	
		   	        	
   	        	if (streaming){
   	        		transferBufferToMediaPlayer();
   	        			streaming = false;
   	        	
   	        	}else{
   	        		songs.push(downSong);
   	        		
   	        	}
	    

    }
    

	
    /*
    private void copyFile(File fromFile, File toFile) throws IOException {

        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
           
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[409600];
            int bytesRead;

            while ((bytesRead = from.read(buffer)) != -1)
                to.write(buffer, 0, bytesRead); // write
        } finally {
            if (from != null)
                try {
                    from.close();
                } catch (IOException e) {
                    ;
                }
            if (to != null)
                try {
                    to.close();
                } catch (IOException e) {
                    ;
                }
        }
    } 
    
    */

      
    
    public void interrupt() {

    	isInterrupted = true;
    	validateNotInterrupted();
    }
    
    
  
    
    
    
    
    
    
    
    
    
    
    
    
    
}
