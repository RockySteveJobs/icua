package iCua.Media;

import iCua.Data.CtrlDades;

public class Fnone extends Filter {

	@Override
	public Playlist getPlaylist() {
		
		Song[] aux = CtrlDades.getSongs(null,null,null);
		Playlist p =  new Playlist(aux);
		// TODO Auto-generated method stub
		return p;
	}

}
