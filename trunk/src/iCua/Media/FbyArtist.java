package iCua.Media;

import iCua.Data.CtrlDades;

public class FbyArtist extends Filter {

	String id_a = null;

	 public FbyArtist(String album){
		
		id_a= album;
	}
	 
	@Override
	public Playlist getPlaylist() {
		Song[] aux = CtrlDades.getSongs(id_a,null,null);
		Playlist p =  new Playlist(aux);
		// TODO Auto-generated method stub
		return p;
	
	}
	
	
}
