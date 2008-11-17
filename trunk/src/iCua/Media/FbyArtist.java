package iCua.Media;

import iCua.Data.CtrlData;

public class FbyArtist extends Filter {

	String id_a = null;

	 public FbyArtist(String album){
		
		id_a= album;
	}
	 
	@Override
	public Playlist getPlaylist() {
		Song[] aux = CtrlData.getSongs(id_a,null,null);
		Playlist p =  new Playlist(aux);
		// TODO Auto-generated method stub
		return p;
	
	}
	
	
}
