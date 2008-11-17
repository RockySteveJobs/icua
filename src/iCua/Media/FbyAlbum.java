package iCua.Media;

import iCua.Data.CtrlData;

public class FbyAlbum extends Filter {
String id_a = null;

	public FbyAlbum(String album){
		
		id_a= album;
	}
	
	@Override
	public Playlist getPlaylist() {
		Song[] aux = CtrlData.getSongs(null,null,id_a);
		Playlist p =  new Playlist(aux);
		// TODO Auto-generated method stub
		return p;
	
	}
	
	
}
