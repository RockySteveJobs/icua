/**
 * 
 */
package iCua.Media;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import iCua.Data.CtrlDades;
import iCua.Media.Eplayer;
import iCua.Media.Filter;
import iCua.Media.Fnone;
import iCua.Media.Playlist;
import iCua.Media.Song;

import com.google.android.maps.MyLocationOverlay;

import android.media.MediaPlayer;
import android.util.Log;
import android.widget.BaseAdapter;

/**
 * @author devil
 *
 */

public class MediaPlayeriCua extends MediaPlayer {

	private Playlist _playlist= null;
	private Filter strategy =  null;
	
	
	public MediaPlayeriCua() {
		
		strategy = new Fnone();
		_playlist = strategy.getPlaylist();
		// TODO Auto-generated constructor stub
	}
	
	public MediaPlayeriCua(Song[] songs) {
		
		strategy = null;
		_playlist = new Playlist(songs);
		// TODO Auto-generated constructor stub
	}
	
	public void setPlaylist (String artist, String song, String album){
		Song[] tmp= CtrlDades.getSongs(artist, song, album);
		_playlist = new Playlist(tmp);
		
		
		
	}
	
	public void setArtist(String artist){
		
		
		
	}
	

	
	
	public void  nextSong(){
		try{
			_playlist.nextSong();
			Song aux = _playlist.getSong();
			System.out.println( "/sdcard/iCua/media/"+aux.filename);
			this.reset();
			this.setDataSource("/sdcard/iCua/media/"+aux.filename);
			this.prepare();
			this.start();
			
		}catch(Exception ex){
			System.out.println( ex.getMessage());
			
		}
		
	}
	
	public Song getSong(){
		
		return _playlist.getSong();
		
	}
	
	
		public void playSong(){
			try{
				Song aux = _playlist.getSong();				
				this.setDataSource("/sdcard/iCua/media/"+aux.filename);
				this.prepare();
				this.start();
				
			}catch(Exception ex){
				Log.w("asda", ex.getMessage());
				
			}
			
		
		}
		
		public void stopSong(){
			try{

				this.stop();
				
			}catch(Exception ex){
				Log.w("asda", ex.getMessage());
				
			}
			
		
		}
		
		public void setDataSourceRadio(String tag){
			try {  
		    			String path = "http://kingpin6.last.fm/user/23965a2c525194d14b8ea0a100e6fc58.mp3";
						URL url = new URL(path);  
			             URLConnection cn = url.openConnection();  
			             cn.connect();  
			             InputStream stream = cn.getInputStream();  
			             if (stream == null)  
			                throw new RuntimeException("stream is null");  
			       //      File temp = File.createTempFile("mediaplayertmp", ".mp3");  
			         File temp = new File("/sdcard/radio.mp3");
			             String tempPath = temp.getAbsolutePath();  
			             FileOutputStream out = new FileOutputStream(temp);  
			             byte buf[] = new byte[128];  
			            do {  
			                 int numread = stream.read(buf);  
			                 if (numread <= 0)  
			                     break;  
			                 out.write(buf, 0, numread);  
			             } while (true);  
			             this.setDataSource(tempPath);  
			         
			                 stream.close();  
			             }  
			             catch (IOException ex) {  
			                 Log.e("RADIO", "error: " + ex.getMessage(), ex);  
	            }  
	 }  
			
			
	
		
	
}
