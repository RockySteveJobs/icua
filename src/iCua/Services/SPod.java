package iCua.Services;



import iCua.Interfaces.IPod;
import android.app.Service;
import android.content.Intent;

import android.media.MediaPlayer;
import android.os.IBinder;

public class SPod extends Service {
	
	private MediaPlayer _mp = null;
	
	
	  public IBinder onBind(Intent intent) {
	        // Select the interface to return.  If your service only implements
	        // a single interface, you can just return it here without checking
	        // the Intent.
	        if (IPod.class.getName().equals(intent.getAction())) {
	        	_mp = new MediaPlayer();
	        	System.out.println("HA INICIAAADO");
				  try{
					  _mp.setDataSource("/sdcard/iCua/media/ADQN.mp3");
				  _mp.prepare();
					  _mp.start();

				  }catch (Exception ex){
						System.out.println("HA PETADO" + ex.getMessage());
					  
				  }
	            return mBinder;
	        }
	       
	        return null;
	    }
	   
	  private final IPod.Stub mBinder = new IPod.Stub() {

		
		  public void play(){
			  
			  try{
				  _mp.setDataSource("/sdcard/iCua/media/ADQN.mp3");
			  _mp.prepare();
				  _mp.start();

			  }catch (Exception ex){
				  
				  
			  }
		  }
		  
		  public void pause(){
			  
			  _mp.pause();
		  }
		  
		  public void exit(){
			  
			 _mp.stop();
		
		  }

	
	  };
}
