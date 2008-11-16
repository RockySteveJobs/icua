package iCua.Media;

public class Playlist {

	private int pos = 0;
	private Song[] list = null;
	
	public Song getSong(){
		
		return list[pos];
			
	}
	
	public void nextSong(){
		
		if (pos == list.length-1){			
			pos = 0;
		}else pos++;
			
	}
	
	public void prevSong(){
		
		if (pos == 0){			
			pos = list.length-1;
		}else pos--;
			
	}
	
	public Playlist(Song[] s){
		this.list = s;
		pos = 0;
	}
	
	
}
