package iCua.Media;

public class Playlist {

	private int pos = 0;
	private Song[] list = null;
	
	public Song getSong(){
		if (list.length>0)return list[pos];
		else return null;
	}
	
	public int getLenght(){
		return list.length;
		
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
