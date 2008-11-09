package iCua.Data;
import iCua.Media.Song;

import java.net.*;
import java.security.MessageDigest;
import java.io.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;


public final class LastFM{
	
	public static void getCoverArt(String album, String artist,String downloadFilename){
		System.out.println("http://ws.audioscrobbler.com/1.0/album/"+ java.net.URLEncoder.encode(artist) + "/"+ java.net.URLEncoder.encode(album)+"/info.xml");
		String art = null;
		download("http://ws.audioscrobbler.com/1.0/album/"+ java.net.URLEncoder.encode(artist) + "/"+ java.net.URLEncoder.encode(album)+"/info.xml","//sdcard//info.xml");

	//	download("http://ws.audioscrobbler.com/1.0/artist/"+ java.net.URLEncoder.encode(artist) + "/similar.xml","tmp.xml");

		//http://ws.audioscrobbler.com/1.0/artist/Metallica/similar.xml
		  try{
				File file = new File("//sdcard//info.xml");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(file);
				doc.getDocumentElement().normalize();
				//System.out.println("Root element " + doc.getDocumentElement().getAttribute("picture").toString());
				 NodeList nodeLst = doc.getElementsByTagName("coverart");
				 
				  for (int s = 0; s < nodeLst.getLength(); s++) 
				  {

					    Node fstNode = nodeLst.item(s);
					    
					    if (fstNode.getNodeType() == Node.ELEMENT_NODE) 
					    {
					  
							      Element fstElmnt = (Element) fstNode;
							      NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("large");
							      Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
							      NodeList fstNm = fstNmElmnt.getChildNodes();
							      art =((Node) fstNm.item(0)).getNodeValue();
							   //   System.out.println("cover : "  + ((Node) fstNm.item(0)).getNodeValue());
					    }

				  }				 
				 
				download(art,downloadFilename);

		  }catch(Exception E){
			  
			  System.out.println("no puedo descargar");
				
			  
		  }
		  
		
		
	
		

	//http://ws.audioscrobbler.com/1.0/album/Metallica/Ride%20the%20Lightning/info.xml
	
	}

	public static void getArtistArt(String artist,String downloadFilename){
		
	//	System.out.println("http://ws.audioscrobbler.com/1.0/album/"+ java.net.URLEncoder.encode(artist) + "/"+ java.net.URLEncoder.encode(album)+"/info.xml");
		String art = null;
	//	download("http://ws.audioscrobbler.com/1.0/album/"+ java.net.URLEncoder.encode(artist) + "/"+ java.net.URLEncoder.encode(album)+"/info.xml","tmp.xml");

		download("http://ws.audioscrobbler.com/1.0/artist/"+ java.net.URLEncoder.encode(artist) + "/similar.xml","//sdcard//similar.xml");

		//http://ws.audioscrobbler.com/1.0/artist/Metallica/similar.xml
		  try{
				File file = new File("//sdcard//similar.xml");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(file);
				doc.getDocumentElement().normalize();
				//System.out.println("Root element " + doc.getDocumentElement().getAttribute("picture").toString());
				art=doc.getDocumentElement().getAttribute("picture").toString();
				download(art,downloadFilename);

		  }catch(Exception E){
			  
			  System.out.println("no puedo descargar");
				
			  
		  }
	}

	
	private static  String stringMD5(String msg) {
        String digestAlgo = "MD5";
        int md5DigestLen = 16;
    	byte[] message = msg.getBytes();
        byte[] newDigest = new byte[md5DigestLen];
    	String md5 = new String("");
        try {
    	        MessageDigest md;
            	md = MessageDigest.getInstance(digestAlgo);
                md.update(message, 0, message.length);
    	        md.digest(newDigest, 0, md5DigestLen);
            	for(int i=0; i< md5DigestLen; i++) {
                    	md5 = md5.concat(Integer.toHexString((newDigest[i] & 0xFF) | 0x100).substring(1,3));
                }
		return md5;
        } 
	catch (Exception e) {
	    return null;
	}
}

	public static String login (String user, String password){

		String tmp = stringMD5(password);
		byte buf[] = new byte[40];  
		try {  
			String path = "http://ws.audioscrobbler.com/radio/handshake.php?username="+user+"&passwordmd5="+tmp;
			URL url = new URL(path);  
             URLConnection cn = url.openConnection();  
             cn.connect();  
             InputStream stream = cn.getInputStream();  
             if (stream == null)  
                throw new RuntimeException("stream is null");  
       //      File temp = File.createTempFile("mediaplayertmp", ".mp3");  

               stream.read(buf);

                 stream.close();  
             }  
             catch (IOException ex) {  
                 Log.e("RADIO", "error: " + ex.getMessage(), ex);  
    }  
	//	String str = new String(buf);
		

                    String str = new String(buf,8,32);

		System.out.println("passwordiç="+str);
		return str;
		
	}
	
	public static boolean tuneTag(String key, String tag){
		
			byte buf[] = new byte[11];  
	try {  
		String path = "http://ws.audioscrobbler.com/radio/adjust.php?session="+key+"&url=lastfm://tag/"+tag;
		URL url = new URL(path);  
         URLConnection cn = url.openConnection();  
         cn.connect();  
         InputStream stream = cn.getInputStream();  
         if (stream == null)  
            throw new RuntimeException("stream is null");  
   //      File temp = File.createTempFile("mediaplayertmp", ".mp3");  



             stream.close();  
         }  
         catch (IOException ex) {  
             Log.e("RADIO", "error: " + ex.getMessage(), ex);  
}  
	
	
                 String str = new String(buf,9,2);

	System.out.println("passwordiç="+str);
	return str.equals("OK");	
		
	}
	
	public static boolean tuneArtist(String key, String artist){
		
		byte buf[] = new byte[11];  
try {  
	String path = "http://ws.audioscrobbler.com/radio/adjust.php?session="+key+"&url=lastfm://artist/"+artist;
	URL url = new URL(path);  
     URLConnection cn = url.openConnection();  
     cn.connect();  
     InputStream stream = cn.getInputStream();  
     if (stream == null)  
        throw new RuntimeException("stream is null");  
//      File temp = File.createTempFile("mediaplayertmp", ".mp3");  

      

         stream.close();  
     }  
     catch (IOException ex) {  
         Log.e("RADIO", "error: " + ex.getMessage(), ex);  
}  


             String str = new String(buf,9,2);

System.out.println("passwordiç="+str);
return str.equals("OK");	
	
}
	
	
	public static void download(String address, String localFileName) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream  in = null;
		try {
			URL url = new URL(address);
			out = new BufferedOutputStream(
				new FileOutputStream(localFileName));
			conn = url.openConnection();
			in = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int numRead;
			long numWritten = 0;
			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
				numWritten += numRead;
			}
			System.out.println(localFileName + "\t" + numWritten);
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ioe) {
			}
		}
	}


	public static Song[] getSongs(String key){
		String path = "http://ws.audioscrobbler.com/radio/xspf.php?sk="+key+"&dicovery=1&desktop=1";
		download( path, "//sdcard//iCua//playlist.xml");
		  try{
				File file = new File("//sdcard//iCua//playlist.xml");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(file);
				doc.getDocumentElement().normalize();
				//System.out.println("Root element " + doc.getDocumentElement().getAttribute("picture").toString());
				 Song[] songs = new Song[5];
				 String artist,songname,album, filename, art;
				
				NodeList nodeLst = doc.getElementsByTagName("track");
				 
				  for (int s = 0; s < nodeLst.getLength(); s++) 
				  {

					    Node fstNode = nodeLst.item(s);
					    
					    if (fstNode.getNodeType() == Node.ELEMENT_NODE) 
					    {
					  
							      Element fstElmnt = (Element) fstNode;
							      NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("location");
							      Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
							      NodeList fstNm = fstNmElmnt.getChildNodes();
							      filename =((Node) fstNm.item(0)).getNodeValue();
							
							      fstElmnt = (Element) fstNode;
							      fstNmElmntLst = fstElmnt.getElementsByTagName("creator");
							      fstNmElmnt = (Element) fstNmElmntLst.item(0);
							      fstNm = fstNmElmnt.getChildNodes();
							      artist =((Node) fstNm.item(0)).getNodeValue();
							      
							      fstElmnt = (Element) fstNode;
							      fstNmElmntLst = fstElmnt.getElementsByTagName("album");
							      fstNmElmnt = (Element) fstNmElmntLst.item(0);
							      fstNm = fstNmElmnt.getChildNodes();
							      album =((Node) fstNm.item(0)).getNodeValue();
							      
							      fstElmnt = (Element) fstNode;
							      fstNmElmntLst = fstElmnt.getElementsByTagName("title");
							      fstNmElmnt = (Element) fstNmElmntLst.item(0);
							      fstNm = fstNmElmnt.getChildNodes();
							      songname =((Node) fstNm.item(0)).getNodeValue();
							      
							      fstElmnt = (Element) fstNode;
							      fstNmElmntLst = fstElmnt.getElementsByTagName("image");
							      fstNmElmnt = (Element) fstNmElmntLst.item(0);
							      fstNm = fstNmElmnt.getChildNodes();
							      art =((Node) fstNm.item(0)).getNodeValue();
							      download(art,"/sdcard/iCua/tmp"+s+".jpg");
							      songs[s]= new Song(0,filename,songname,artist,album,0,0,"/sdcard/iCua/tmp"+s+".jpg");
							   //   System.out.println("cover : "  + ((Node) fstNm.item(0)).getNodeValue());
					    }

				  }	

		  }catch(Exception E){
			  
			  System.out.println("no puedo descargar");
				
			  
		  }
		return null;
	}
	
}

