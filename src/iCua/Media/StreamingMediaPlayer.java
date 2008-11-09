package iCua.Media;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.media.MediaPlayer;
import android.os.FileObserver;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * MediaPlayer does not yet support streaming from external URLs so this class provides a pseudo-streaming function
 * by downloading the content incrementally & playing as soon as we get enough audio in our temporary storage.
 */
public class StreamingMediaPlayer {
	
    private static final int INTIAL_KB_BUFFER =  (128*20)/8;//assume 96kbps*10secs/8bits per byte
    private long tope= 0;
	//  Track for display by progressBar
	private int totalKbRead = 0;
	
	// Create Handler to call View updates on the main UI thread.
	private final Handler handler = new Handler();

	private MediaPlayer 	mediaPlayer;
	
	private File downloadingMediaFile; 
	private File 	bufferedFile;	
	
	private boolean isInterrupted;
	
 	public StreamingMediaPlayer(TextView textStreamed, Button	playButton, Button	streamButton,ProgressBar	progressBar) {

	}
	
    /**  
     * Progressivly download the media to a temporary location and update the MediaPlayer as new content becomes available.
     */  
    public void startStreaming(final String mediaUrl, long	mediaLengthInKb, long	mediaLengthInSeconds) throws IOException {
    	
   	
		Runnable r = new Runnable() {   
	        public void run() {   
	            try {   
	        		downloadAudioIncrement(mediaUrl);
	            } catch (IOException e) {
	            	Log.e(getClass().getName(), "Unable to initialize the MediaPlaer for fileUrl=" + mediaUrl, e);
	            	return;
	            }   
	        }   
	    };   
	    new Thread(r).start();
    }
    
    /**  
     * Download the url stream to a temporary location and then call the setDataSource  
     * for that local file
     */  
    public void downloadAudioIncrement(String mediaUrl) throws IOException {
    	
    	URLConnection cn = new URL(mediaUrl).openConnection();   
        cn.connect();   
        InputStream stream = cn.getInputStream();
        if (stream == null) {
        	Log.e(getClass().getName(), "Unable to create InputStream for mediaUrl:" + mediaUrl);
        }
        
		downloadingMediaFile = File.createTempFile("downloadingMedia", ".dat");
        FileOutputStream out = new FileOutputStream(downloadingMediaFile);   
        byte buf[] = new byte[16384];
        int totalBytesRead = 0, incrementalBytesRead = 0;
        do {
        	int numread = stream.read(buf);   
            if (numread <= 0)   
                break;   
            out.write(buf, 0, numread);
            totalBytesRead += numread;
            incrementalBytesRead += numread;
            totalKbRead = totalBytesRead/1000;
            
            testMediaBuffer();
        } while (validateNotInterrupted());   

       	stream.close();
        if (validateNotInterrupted()) {
	       	fireDataFullyLoaded();
        }
    }  

    private boolean validateNotInterrupted() {
		if (isInterrupted) {
			if (mediaPlayer != null) {
				mediaPlayer.pause();
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

	            if (mediaPlayer == null) {
	            	//  Only create the MediaPlayer once we have the minimum buffered data
	            	if ( totalKbRead >= INTIAL_KB_BUFFER) {
	            		try {
		            		startMediaPlayer();
	            		} catch (Exception e) {
	            			Log.e(getClass().getName(), "Error copying buffered conent.", e);    			
	            		}
	            	}
	            } else {
	            	
	            	long tt = tope - mediaPlayer.getCurrentPosition() ;
	            	if ( tt <= 1000 ){ 
	            		Log.w(getClass().getName(), "ya tocaaaaaaaaaaaaaaa "+tt);
	            		//  NOTE:  The media player has stopped at the end so transfer any existing buffered data
	            	//  We test for < 1second of data because the media player can stop when there is still
	            	//  a few milliseconds of data left to play
	            			transferBufferToMediaPlayer();
	            	}else Log.w(getClass().getName(), "MEDIA "+mediaPlayer.getCurrentPosition()+" tope "+tope);
	            	
	            }
	        

    }
    
    private void startMediaPlayer() {
        try {   
        	File bufferedFile = File.createTempFile("playingMedia", ".dat");
    		copyFile(downloadingMediaFile,bufferedFile);
    		tope = (bufferedFile.length()*8)/132;
    		
    		mediaPlayer = new MediaPlayer();
        	mediaPlayer.setDataSource(bufferedFile.getAbsolutePath());
    		mediaPlayer.prepare();
    		mediaPlayer.start();
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
	    	boolean wasPlaying = mediaPlayer.isPlaying();
	    	int curPosition = mediaPlayer.getCurrentPosition();
	    	MediaPlayer tmp =mediaPlayer;
	    	File tempfile = bufferedFile;
	    	bufferedFile = File.createTempFile("playingMedia", ".dat");
	    	copyFile(downloadingMediaFile,bufferedFile);
	    	tope = (bufferedFile.length()*8)/132;
			mediaPlayer = new MediaPlayer();
    		mediaPlayer.setDataSource(bufferedFile.getAbsolutePath());
    		//mediaPlayer.setAudioStreamType(AudioSystem.STREAM_MUSIC);
    		mediaPlayer.prepare();
    		mediaPlayer.seekTo(curPosition);
    		
    		//  Restart if at end of prior beuffered content or mediaPlayer was previously playing.  
    		//	NOTE:  We test for < 1second of data because the media player can stop when there is still
        	//  a few milliseconds of data left to play
    		boolean atEndOfFile = mediaPlayer.getCurrentPosition() >= tope - 1000;
        	if (wasPlaying || atEndOfFile){
        		 tmp.pause();
        		mediaPlayer.start();
        		tempfile.delete();
        	}
		}catch (Exception e) {
	    	Log.e(getClass().getName(), "Error updating to newly loaded content.", e);            		
		}
    }
    

    
    /**
     * We have preloaded enough content and started the MediaPlayer so update the buttons & progress meters.
     */


    private void fireDataFullyLoaded() {
		Runnable updater = new Runnable() { 
			public void run() {
   	        	transferBufferToMediaPlayer();

	        }
	    };
	    new Thread(updater).start();
    }
    
    public MediaPlayer getMediaPlayer() {
    	return mediaPlayer;
	}
	
    
    private void copyFile(File fromFile, File toFile) throws IOException {

        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[4096];
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
    
    
    public void interrupt() {

    	isInterrupted = true;
    	validateNotInterrupted();
    }
}
