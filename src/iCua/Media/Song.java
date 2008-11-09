package iCua.Media;

public class Song {
	
		

	public String filename, title, artist, album, art ;
	public int id_album,id_artist,id;

	
	public Song(int id, String filename, String title, String artist, String album, int id_artist, int id_album){
		
		this.filename = filename;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.id_artist = id_artist;
		this.id_album = id_album;
		this.id = id;
		this.art= "/sdcard/iCua/art/album/"+id_album+".jpg";
			
	} 
	
	public Song(String filename, String title, String artist, String album,String art){
		this.filename = filename;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.id_artist = -1;
		this.id_album = -1;
		this.id = -1;
		this.art= art;	
	}
	
	
	public Song(int id, String filename, String title, String artist, String album, int id_artist, int id_album, String art){
		
		this.filename = filename;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.id_artist = id_artist;
		this.id_album = id_album;
		this.id = id;
		this.art= art;	
			
	} 
}
