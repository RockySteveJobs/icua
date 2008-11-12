package iCua.Media;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

public class LastFMClient {

	private String user;
	private String pwd;
	private String key;
	private String skey;
	private String serverNP;
	private String serverSB;
	
	public LastFMClient(String user, String pwd){
		
		this.user= user;
		this.pwd= pwd;		
		 login();
		loginScrb();
		
		
		
		
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

	private  void login (){

		String tmp = stringMD5(this.pwd);
		byte buf[] = new byte[40];  
		try {  
			String path = "http://ws.audioscrobbler.com/radio/handshake.php?username="+this.user+"&passwordmd5="+tmp;
			URL url = new URL(path);  
             URLConnection cn = url.openConnection();  
             cn.connect();  
             InputStream stream = cn.getInputStream();  
             if (stream == null)  
                throw new RuntimeException("stream is null");  
             stream.read(buf);
             System.out.println("passwordiç="+buf.toString());
                 stream.close();  
             }  
             catch (IOException ex) {  
                 Log.e("RADIO", "error: " + ex.getMessage(), ex);  
    }  
		
		
                     String str = new String(buf,8,32);

		System.out.println("passwordiç="+str);
		this.key= str;
		
	}
	
	
	private void loginScrb (){

		String tmp = stringMD5(pwd);
		long ts= System.currentTimeMillis()/1000;
		 tmp += ts;
		 tmp= stringMD5(tmp);
		byte buf[] = new byte[255];  
		try {  
			String path = "http://post.audioscrobbler.com/?hs=true&p=1.2&c=tst&v=1.0&u="+this.user+"&t="+ts+"&a="+tmp;
			URL url = new URL(path);  
             URLConnection cn = url.openConnection();  
             cn.connect();  
             InputStream stream = cn.getInputStream();  
             if (stream == null)  
                throw new RuntimeException("stream is null");  
             stream.read(buf);
                 stream.close();  
             }  
		
		
             catch (IOException ex) {  
                 Log.e("RADIO", "error: " + ex.getMessage(), ex);  
    }  
             	
                     String str = new String(buf);
                   String result[]=  str.split("\n");
                   this.skey = result[1];
                   this.serverNP = result[2];
                   this.serverSB = result[3];

		System.out.println("passwordiç="+str);
	
		
	}

	public void playingNow(String artist, String title, String duration, String album){
		System.out.println("PLAYNOW");
		try {
	             DefaultHttpClient client = new DefaultHttpClient();
	             ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
	            pairs.add( new BasicNameValuePair("s", this.skey));
	            pairs.add(      new BasicNameValuePair("a", artist) );            
	            pairs.add(      new BasicNameValuePair("t", title) );
	            pairs.add(      new BasicNameValuePair("b", album) );
	            pairs.add(      new BasicNameValuePair("l", duration) );
	            pairs.add(      new BasicNameValuePair("n", "") );
	            pairs.add(      new BasicNameValuePair("m", "") );
	    		System.out.println("song="+" "+artist+" "+title+" "+album+" "+duration);
	            
	             UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs);
	            		 
	             HttpPost method = new HttpPost(new URI(this.serverNP));
	             method.setHeader("user-agent", "iCua");
	             method.setEntity(entity);

	             HttpResponse res = client.execute(method);
	    	
	 
	             
	          }catch(Exception exd1 ){
	        	  System.out.println(exd1.getMessage());
	        	  
	          }
		
	}
	public void scroble(String artist, String title,String duration, String album){
		try {
			System.out.println("song="+" "+artist+" "+title+" "+album+" "+duration);
	        
            DefaultHttpClient client = new DefaultHttpClient();
            ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
           pairs.add( new BasicNameValuePair("s", this.skey));
           pairs.add(      new BasicNameValuePair("a[0]", artist) );            
           pairs.add(      new BasicNameValuePair("t[0]", title) );
           pairs.add(      new BasicNameValuePair("i[0]", ""+(System.currentTimeMillis()/1000)) );
           pairs.add(      new BasicNameValuePair("o[0]", "P") );
           pairs.add(      new BasicNameValuePair("r[0]", "") );
           pairs.add(      new BasicNameValuePair("b[0]", album) );
           pairs.add(      new BasicNameValuePair("l[0]", duration) );
           pairs.add(      new BasicNameValuePair("n[0]", "") );
           pairs.add(      new BasicNameValuePair("m[0]", "") );
           
           
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs);
           		 
            HttpPost method = new HttpPost(new URI(this.serverSB));
      //      method.setHeader("user-agent", "iCua webBrowser");
            method.setEntity(entity);

            HttpResponse res = client.execute(method);
   	
            System.out.println("mandadoooo"+res.toString());
         }catch(Exception exd1 ){
       	  System.out.println(exd1.getMessage());
       	  
         }
		
	}

    private void send(String password, String artist, String track){


          String urlReq = "http://post.audioscrobbler.com:80/np_1.2";

          
          try {
             DefaultHttpClient client = new DefaultHttpClient();
             ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
            pairs.add( new BasicNameValuePair("s", password));
            pairs.add(      new BasicNameValuePair("a", artist) );            
            pairs.add(      new BasicNameValuePair("t", track) );
            pairs.add(      new BasicNameValuePair("b", "") );
            pairs.add(      new BasicNameValuePair("l", "") );
            pairs.add(      new BasicNameValuePair("n", "") );
            pairs.add(      new BasicNameValuePair("m", "") );
            
            
             UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs);
            		 
             HttpPost method = new HttpPost(new URI(urlReq));
             method.setHeader("user-agent", "iCua webBrowser");
             method.setEntity(entity);

             HttpResponse res = client.execute(method);
    	
          }catch(Exception exd1 ){
        	  System.out.println(exd1.getMessage());
        	  
          }
          
    }
    
	
	public boolean tuneTag( String tag){
		
			byte buf[] = new byte[11];  
	try {  
		String path = "http://ws.audioscrobbler.com/radio/adjust.php?session="+ this.key +"&url=lastfm://tag/"+tag;
		URL url = new URL(path);  
         URLConnection cn = url.openConnection();  
         cn.connect();  
         InputStream stream = cn.getInputStream();  
         if (stream == null)  
            throw new RuntimeException("stream is null");  
         stream.read(buf);
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
	
	public boolean tuneArtist(String artist){
		
		byte buf[] = new byte[11];  
try {  
	String path = "http://ws.audioscrobbler.com/radio/adjust.php?session="+this.key+"&url=lastfm://artist/"+artist;
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
	
	
	private static void download(String address, String localFileName) {
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


	public  Song[] getSongs(){
		String path = "http://ws.audioscrobbler.com/radio/xspf.php?sk="+this.key+"&dicovery=1&desktop=1";
		download( path, "//sdcard//iCua//playlist.xml");
		System.out.println("//sdcard//iCua//playlist.xml");
		Song[] songs = null; 
		try{
				File file = new File("//sdcard//iCua//playlist.xml");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(file);
				doc.getDocumentElement().normalize();
				//System.out.println("Root element " + doc.getDocumentElement().getAttribute("picture").toString());
				 songs = new Song[5];
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
							System.out.println(filename);
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
		return songs;
	}
	
	
	
}
