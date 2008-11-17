package iCua.Media;

import iCua.Data.CtrlData;

public class Fnone extends Filter {

	@Override
	public Playlist getPlaylist() {
		
		Song[] aux = CtrlData.getSongs(null,null,null);
		Playlist p =  new Playlist(aux);
		// TODO Auto-generated method stub
		return p;
	}

}
