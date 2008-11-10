package iCua.Data;

import iCua.Media.Album;
import iCua.Media.Artist;
import iCua.Media.Song;

import java.io.File;
import java.io.FilenameFilter;

import entagged.audioformats.AudioFile;
import entagged.audioformats.AudioFileIO;
import entagged.audioformats.exceptions.CannotReadException;
import android.database.sqlite.*;
import android.database.*;


public final class CtrlDades {
	
	public static void scan(){
		/*
		 * Escanea la sdcard en busca de ficheros y los mete en la base de datos
		 * 
		 * */
		
		  File f = new File("\\sdcard\\"); //    NullPointerException - If the pathname argument is null
	        

	        if (f.exists()){
	        	
	        	FilenameFilter filter = new FilenameFilter() {
	                public boolean accept(File dir, String name) {
	                    return name.endsWith(".mp3");               }
	            };

	        	File[] lista = f.listFiles(filter);
	        	
	        	AudioFile[] listaID3 = new AudioFile[lista.length];        	        	
	        
 			    File dir = new File("\\sdcard\\iCua\\media\\");
	        	for(int i =0;i< lista.length;i++){
	        	
	        		try{
	        			listaID3[i]= AudioFileIO.read(lista[i]);
	        			
	        				addSong(lista[i].getName(), listaID3[i].getTag().getFirstArtist(), listaID3[i].getTag().getFirstTitle(), listaID3[i].getTag().getFirstAlbum());
	        			
	           			    lista[i].renameTo(new File(dir, lista[i].getName()));
	       
	        		
	        		}catch (CannotReadException e){
	        			/* AudioFileIO.read
	        			 * No se ha podido leer el fichero IGNORE?¿
	        			 * 
	        			 * */
	        			
	        		}catch(SecurityException e){
	      			/*No puede escribir en destino por permisos, dificil
	      			 * File.renameTo
	      			 * 	
	      			 * */
	        			
	        		}catch(NullPointerException e){
		      		/*No existe destino, no se va a dar nunca, el destino es siempre el mismo
		      		 * File.renameTo
		      		 * 
		      		 * */
	        		}
	        		
	        	}
	        	
	        	
		
	        }
	        
			
	}
	private static int addAlbum(String title,int artist,String photo){
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(new File("//sdcard//iCua//data/iCua.db3"),null);

		StringBuilder sb = new StringBuilder();
		
		/* *
		 * Construimos el WHERE
		 * */
		
		sb.append("artist=");
		DatabaseUtils.appendValueToSql(sb,artist);
		sb.append(" and title=");
		DatabaseUtils.appendValueToSql(sb,title);
		
		
		Cursor c = db.query( "albums", new String[] {"_id"}, sb.toString(), null, null, null, null); 
		
		if (c.getCount()==0){
			
			sb.delete(0, sb.length());				
			sb.append("INSERT INTO albums(title,artist,photo) VALUES(");
			DatabaseUtils.appendValueToSql(sb, title);
			sb.append(",");
			DatabaseUtils.appendValueToSql(sb, artist);
			sb.append(",");
			DatabaseUtils.appendValueToSql(sb, photo);
			sb.append(")");
			db.execSQL(sb.toString());
			
			sb.delete(0, sb.length());	
			sb.append("artist=");
			DatabaseUtils.appendValueToSql(sb,artist);
			sb.append(" and title=");
			DatabaseUtils.appendValueToSql(sb,title);
			
			c = db.query( "albums", new String[] {"_id"}, sb.toString(), null, null, null, null); 

			
						
		}
		
		c.moveToNext();
		int res = c.getInt(0);
		db.close();
		
			return res;
	}
	
	
	private static int addArtist(String artist, String full_name,String photo){
		
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(new File("//sdcard//iCua//data/iCua.db3"),null);
		StringBuilder sb = new StringBuilder();
		
		sb.append("name=");
		DatabaseUtils.appendValueToSql(sb,artist);
		
		
		
		Cursor c = db.query( "artists", new String[] {"_id"}, sb.toString(), null, null, null, null); 
			System.out.println("aggg");	
			System.out.flush();
			
			
			
			if (c.getCount()==0){
				
				sb.delete(0, sb.length());				
				sb.append("INSERT INTO artists(name,full_name,photo) VALUES(");
				DatabaseUtils.appendValueToSql(sb, artist);
				sb.append(",");
				DatabaseUtils.appendValueToSql(sb, full_name);
				sb.append(",");
				DatabaseUtils.appendValueToSql(sb, photo);
				sb.append(")");
				db.execSQL(sb.toString());
				
				sb.delete(0, sb.length());	
				sb.append("name=");
				DatabaseUtils.appendValueToSql(sb,artist);
				
				c = db.query( "artists", new String[] {"_id"}, sb.toString(), null, null, null, null); 

				
							
			}
			
			c.moveToNext();
			int res = c.getInt(0);
			db.close();
			
			
			
			return res;
			
	}

	private static void addSong(String filename, String artist, String title, String album){
	
		int id_artist = addArtist(artist, artist, "//sdcard//iCua//art//artist//");
		
		LastFM.getArtistArt(artist,"//sdcard//iCua//art//artist//"+id_artist+".jpg" );
		
		int id_album = addAlbum(album,id_artist,"//sdcard//iCua//art//album//");
		
		LastFM.getCoverArt(album, artist, "//sdcard//iCua//art//album//"+id_album+".jpg" );
	
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(new File("//sdcard//iCua//data/iCua.db3"),null);
		StringBuilder sb = new StringBuilder();
			
			
			sb.delete(0, sb.length());				
			sb.append("INSERT INTO songs(filename,artist,title,album) VALUES(");
			DatabaseUtils.appendValueToSql(sb, filename);
			sb.append(","+ id_artist +",");
			DatabaseUtils.appendValueToSql(sb, title);
			sb.append(",");
			DatabaseUtils.appendValueToSql(sb, id_album);
			sb.append(")");			
			db.execSQL(sb.toString());
			
			
	}
	
	public static Cursor listArtists(){
		
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(new File("//sdcard//iCua//data/iCua.db3"),null);
	
		Cursor c = db.query(true,"artists",new String[] {"_id", "name", "full_name","photo"},null,null,null,null,"name",null);

		return c;
		
	}

	
	public  static String[] getArtists(int n ){
		
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(new File("//sdcard//iCua//data/iCua.db3"),null);
		Cursor c = db.query(true, "artists", new String[] {"_id", "name"}, null, null, null, null, "name", null); 
		
		String[] res = new String[c.getCount()];
		int i =0;
		while(c.moveToNext()){
			
			res[i]= c.getString(1);
			i++;
			
		}
		
		return res;
		
	}
	public  static Artist[] getArtists(){
		
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(new File("//sdcard//iCua//data/iCua.db3"),null);
		Cursor c = db.query(true, "artists", new String[] {"_id", "name"}, null, null, null, null, "name", null); 
		
		Artist[] res = new Artist[c.getCount()];
		int i =0;
		while(c.moveToNext()){
			
			res[i]= new Artist (c.getInt(0),c.getString(1));
			i++;
			
		}
		
		return res;
		
	}
	
	




public  static Cursor getSongs( String artist){
		
		SQLiteDatabase db = SQLiteDatabase.openDatabase("//sdcard//iCua//data/iCua.db3",null,0);
		Cursor c = db.query(true, "songs", new String[] {"_id", "title", "filename", "artist"}, "artist="+artist, null, null, null, "title", null);
		
		
		return c;
			
	}
/*
public static Song getSong(int id){
	
	Song  res = null;
	SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(new File("//sdcard//iCua//data/iCua.db3"),null);
	StringBuilder sb = new StringBuilder();	
	
		sb.append("_id=");
		DatabaseUtils.appendValueToSql(sb,id);
			
		
	
	
	Cursor c = db.query(true, "songs", new String[] {"_id", "title", "filename", "artist", "album"}, sb.toString(), null, null, null, "title", null);
	
	
	while(c.moveToNext()){
		res= new Song(c.getInt(0),c.getString(2),c.getString(1),c.getInt(3),c.getInt(4));
	
	}
	
	return res;
}

public  static Song[] getSongsByAlbum( int artist, int album){
	
	SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(new File("//sdcard//iCua//data/iCua.db3"),null);
	StringBuilder sb = new StringBuilder();	
	
		sb.append("artist=");
		DatabaseUtils.appendValueToSql(sb,artist);
			
	
		sb.append(" and album=");
		DatabaseUtils.appendValueToSql(sb,album);
		
	
	
	Cursor c = db.query(true, "songs", new String[] {"_id", "title", "filename", "artist", "album"}, sb.toString(), null, null, null, "title", null);
	int i =0;
	Song [] res  =  new Song[c.getCount()];
	while(c.moveToNext()){
		res[i]= new Song(c.getInt(0),c.getString(2),c.getString(1),c.getInt(3),c.getInt(4));

		i++;
		
	}
	
	return res;
		
}

*/

public  static Song[] getSongs( String artist, String id_song, String album){
	
	SQLiteDatabase db = SQLiteDatabase.openDatabase("//sdcard//iCua//data/iCua.db3",null,0);
	
	SQLiteQueryBuilder sqb = new SQLiteQueryBuilder();
	
	sqb.setTables("songs s INNER JOIN artists a, albums c  ON (s.artist=a._id and s.album=c._id)");
	
	StringBuilder sb = null;
	
	//sb.append("SELECT a.name as artist, a._id as idartist,c.title as album, c._id as idalbum ,s.title as song, s.filename as filename FROM songs S, artists a, albums c WHERE s.artist=a._id and s.album=c._id");
	
	

	
	
	if (artist!=null){
		sb = new StringBuilder();	
		sb.append("a._id=");
		DatabaseUtils.appendValueToSql(sb,artist);
			}
	if (id_song != null){
		if (sb == null){
			sb = new StringBuilder();	
			
		}else{
			sb.append(" and ");
			
		}
				
		sb.append("s._id=");
		DatabaseUtils.appendValueToSql(sb,id_song);
		
	}
	if (album!=null){
		if (sb == null){
			sb = new StringBuilder();	
			
		}else{
			sb.append(" and ");
			
		}
		sb.append("c._id=");
		DatabaseUtils.appendValueToSql(sb,album);
		
	}
	//db.execSQL(sb.toString());
	if (sb == null)		sb = new StringBuilder();	
	
	Cursor c = sqb.query(db, new String[] {"s.filename",  "s.title", "a.name", "c.title", "a._id","c._id", "s._id"}, sb.toString(), null, null, null, "s.title", null);
	int i =0;
	Song [] res  =  new Song[c.getCount()];
	while(c.moveToNext()){
		res[i]= new Song(c.getInt(6),c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getInt(4),c.getInt(5));

		i++;
		
	}
	db.close();
	return res;
		
}

public  static Album[] getAlbums( ){
	
	SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(new File("//sdcard//iCua//data/iCua.db3"),null);
	StringBuilder sb = new StringBuilder();
	

	Cursor c = db.query(true, "albums", new String[] {"_id", "title", "photo","artist"},null, null, null, null, null, null); 
	
	Album [] res= new Album[c.getCount()];
	int i =0;
	while(c.moveToNext()){
		
		res[i]= new Album(c.getInt(0),c.getString(1),-1);

		i++;
		
	}
	
	return res;
	
}

public  static Album[] getAlbums(int id_artist ){
	
	SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(new File("//sdcard//iCua//data/iCua.db3"),null);
	StringBuilder sb = new StringBuilder();
	
	sb.append("artist=");
	DatabaseUtils.appendValueToSql(sb,id_artist);
	Cursor c = db.query(true, "albums", new String[] {"_id", "title", "photo"},sb.toString(), null, null, null, null, null); 
	
	Album [] res= new Album[c.getCount()];
	int i =0;
	while(c.moveToNext()){
		
		res[i]= new Album(c.getInt(0),c.getString(1),id_artist);

		i++;
		
	}
	
	return res;
	
}
	

}
