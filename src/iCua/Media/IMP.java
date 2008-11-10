package iCua.Media;

import android.media.MediaPlayer.OnCompletionListener;

public interface IMP {

	 public abstract Song getSong();
	 
	 public abstract void playSong();
	 
	 public abstract void stopSong();
	 
	 public abstract void nextSong();
	 
	 public abstract void setOnCompletionListener(OnCompletionListener o);
	 
	 public abstract int getDuration();
	 
	 public abstract boolean isPlaying();
	 
	 public abstract void  seekTo(int pos);
	
	 public abstract void addSong(Song s);
	 
	 public abstract void reset();
	 
	 public abstract void setPlaylist(String a, String t, String ab);
	 
	 public abstract int  getCurrentPosition();
	 
	 public abstract void prevSong();
	 
	
}
