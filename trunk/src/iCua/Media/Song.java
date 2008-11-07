package iCua.Media;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable{
	
		
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {
	        public Song createFromParcel(Parcel in) {
	            return new Song(in);
	        }

	        public Song[] newArray(int size) {
	            return new Song[size];
	        }
	    };

	private Song(Parcel in) {
	        readFromParcel(in,0);
	    }
	    
	public void writeToParcel(Parcel out, int flags) {
	        out.writeInt(id_album);
	        out.writeInt(id_artist);
	        out.writeInt(id);
	        out.writeString(filename);
	        out.writeString(title);
	        out.writeString(artist);
	        out.writeString(album);
	        
	    }
	    
	public void readFromParcel(Parcel in, int flags) {
	    	id_album = in.readInt();
	    	id_artist = in.readInt();
	    	id = in.readInt();
	    	filename = in.readString();
	    	title = in.readString();
	    	artist = in.readString();
	    	album = in.readString();
	        
	        
	    }

	
	public String filename, title, artist, album ;
	public int id_album,id_artist,id;

	
	public Song(int id, String filename, String title, String artist, String album, int id_artist, int id_album){
		
		this.filename = filename;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.id_artist = id_artist;
		this.id_album = id_album;
		this.id = id;
			
	} 
}
