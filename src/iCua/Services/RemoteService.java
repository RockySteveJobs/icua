/* 
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package iCua.Services;

import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;

import android.content.Intent;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.text.style.UpdateLayout;
import android.widget.Toast;


import iCua.Activities.OnAir;
import iCua.Activities.R;

import iCua.Activities.R.drawable;
import iCua.Activities.R.string;
import iCua.Data.CtrlDades;
import iCua.Data.LastFM;
import iCua.Media.LastFMClient;
import iCua.Media.MediaPlayeriCua;
import iCua.Media.Song;



// Need the following import to get access to the app resources, since this
// class is in a sub-package.


/**
 * This is an example of implementing an application service that runs in a
 * different process than the application.  Because it can be in another
 * process, we must use IPC to interact with it.  The
 * {@link RemoteServiceController} and {@link RemoteServiceBinding} classes
 * show how to interact with the service.
 */
public class RemoteService extends Service implements OnCompletionListener {
    /**
     * This is a list of callbacks that have been registered with the
     * service.  Note that this is package scoped (instead of private) so
     * that it can be accessed more efficiently from inner classes.
     */
	MediaPlayeriCua mp = null;
	 LastFMClient lc = null;
	boolean isPlaying ;
	 boolean nuSong =false;
	final RemoteCallbackList<IRemoteServiceCallback> mCallbacks
            = new RemoteCallbackList<IRemoteServiceCallback>();
    long timestamp = 0;
    int mValue = 0;
    NotificationManager mNM;
    
    @Override
    public void onCreate() {
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        mp = new MediaPlayeriCua();
        // Display a notification about us starting.
        showNotification();
       lc = new LastFMClient("cuacua", "bocaboca");
        
       
      // Song[] lastsongs= LastFM.getSongs(key);
        mp.setOnCompletionListener(this);
        // While this service is running, it will continually increment a
        // number.  Send the first message that is used to perform the
        // increment.
        mHandler.sendEmptyMessage(REPORT_MSG);
       
    }

    public void onCompletion(MediaPlayer arg0) {
    	// TODO Auto-generated method stub
    		System.out.println("completatioooon");
    		Song actual = mp.getSong();
    		lc.scroble(actual.artist, actual.title, (mp.getDuration()/1000)+"", actual.album);
            	mp.nextSong();       
            	updateNotification();
            	 final int N = mCallbacks.beginBroadcast();
                 for (int i=0; i<N; i++) {
                     try {
                     	if (mp.isPlaying()){
                         mCallbacks.getBroadcastItem(i).songChanged();
                     	}
                         // mCallbacks.getBroadcastItem(i).songChanged();
                     } catch (RemoteException e) {
                         // The RemoteCallbackList will take care of removing
                         // the dead object for us.
                     }
                 }
                 mCallbacks.finishBroadcast();
          
    }
    
    
    

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        mNM.cancel(R.string.remote_service_started);

        // Tell the user we stopped.
        Toast.makeText(this, R.string.remote_service_stopped, Toast.LENGTH_SHORT).show();
        
        // Unregister all callbacks.
        mCallbacks.kill();
        
        // Remove the next pending message to increment the counter, stopping
        // the increment loop.
        mHandler.removeMessages(REPORT_MSG);
    }
    

    @Override
    public IBinder onBind(Intent intent) {
        // Select the interface to return.  If your service only implements
        // a single interface, you can just return it here without checking
        // the Intent.
    	
    
        if (IRemoteService.class.getName().equals(intent.getAction())) {
            return mBinder;
        }
        if (ISecondary.class.getName().equals(intent.getAction())) {
            return mSecondaryBinder;
        }
        return null;
    }

    /**
     * The IRemoteInterface is defined through IDL
     */
    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        public void registerCallback(IRemoteServiceCallback cb) {
            if (cb != null) mCallbacks.register(cb);
            try{
            cb.songChanged();
            
            }catch (RemoteException rex){}
        }
        public void unregisterCallback(IRemoteServiceCallback cb) {
            if (cb != null) mCallbacks.unregister(cb);
        }
    };

    
    
    /**
     * A secondary interface to the service.
     */
    private final ISecondary.Stub mSecondaryBinder = new ISecondary.Stub() {
        public int getPid() {
            return Process.myPid();
        }
        public void basicTypes(int anInt, long aLong, boolean aBoolean,
                float aFloat, double aDouble, String aString) {
        }
        @Override
        public String getArt() throws RemoteException {
        	// TODO Auto-generated method stub
        	return mp.getSong().art;
        }
        
        @Override
        public int PlayStream() throws RemoteException {
        	// TODO Auto-generated method stub
        	return 0;
        }
        
        @Override
        public int tuneArtist(String artist) throws RemoteException {
        	// TODO Auto-generated method stub
        	return 0;
        }
        
        @Override
        public int tuneTag(String tag) throws RemoteException {
        	// TODO Auto-generated method stub
        	return 0;
        }
        
        
        @Override
        public int playSong() throws RemoteException {
           if (!mp.isPlaying())mp.playSong();
           updateNotification();
        	// TODO Auto-generated method stub
        	return 0;
        }
        @Override
        public int stopSong() throws RemoteException {
        	// TODO Auto-generated method stub
        		mp.stopSong();
        	return 0;
        }
@Override
        public boolean isPlaying() throws RemoteException {
        	// TODO Auto-generated method stub
        	return isPlaying;
        }

 		@Override
 		public int getIdAlbum() throws RemoteException {
 			Song s=	mp.getSong();
 			
 			return s.id_album;
 		}
		@Override
 		public int getIdArtist() throws RemoteException {
 			Song s=	mp.getSong();
 			
 			return s.id_artist;
 		}
		
		@Override
		public int nextSong() throws RemoteException {
			// TODO Auto-generated method stub
			
			mp.nextSong();
			updateNotification();
			return 0;
		}
		@Override
		public int prevSong() throws RemoteException {
			// TODO Auto-generated method stub
			
			mp.prevSong();
			updateNotification();
			return 0;
		}
   
    @Override
    public String getArtist() throws RemoteException {
    	// TODO Auto-generated method stub
    	return mp.getSong().artist ;
    }
    
    @Override
    public int getDuration() throws RemoteException {
    	// TODO Auto-generated method stub
    	return mp.getDuration();
    }
    @Override
    public String getTitle() throws RemoteException {
    	// TODO Auto-generated method stub
    	return mp.getSong().title; 
    }
    
    @Override
    public void seekSong(int pos) throws RemoteException {
    	// TODO Auto-generated method stub
     
    	mp.seekTo(pos);
    }
    
@Override
public void SetPlaylist(String song, String artist, String album)
		throws RemoteException {
	if (mp.isPlaying())	mp.stop();
	mp.reset();
	mp.setPlaylist(artist, song, album);
	mp.playSong();
	
}
    @Override
    public void setTimeStamp(long ts) throws RemoteException {
    	// TODO Auto-generated method stub
    	timestamp = ts;
    }
@Override
public boolean isTimeStamp(long ts) throws RemoteException {
	// TODO Auto-generated method stub
	
	
	return timestamp == ts;
}

    };

    
    
    private static final int REPORT_MSG = 1;
    private static final int UPDATE_MSG = 2;
    
    /**
     * Our Handler used to execute operations on the main thread.  This is used
     * to schedule increments of our value.
     */
    private final Handler mHandler = new Handler() {
        @Override public void handleMessage(Message msg) {
            switch (msg.what) {
                
                // It is time to bump the value!
                case REPORT_MSG: {
                    // Up it goes.
                    int value = ++mValue;
                    
                    // Broadcast to all clients the new value.
                    final int N = mCallbacks.beginBroadcast();
                    for (int i=0; i<N; i++) {
                        try {
                        	if (mp.isPlaying()){
                            mCallbacks.getBroadcastItem(i).valueChanged(mp.getCurrentPosition());
                        	}
                            // mCallbacks.getBroadcastItem(i).songChanged();
                        } catch (RemoteException e) {
                            // The RemoteCallbackList will take care of removing
                            // the dead object for us.
                        }
                    }
                    mCallbacks.finishBroadcast();
                    
                    // Repeat every 1 second.
                    sendMessageDelayed(obtainMessage(REPORT_MSG), 1*1000);
                } break;

                default:
                    super.handleMessage(msg);
            }
        }
    };

    /**
     * Show a notification while this service is running.
     */
    private void updateNotification(){
    
    	Song actual = mp.getSong();
    	lc.playingNow(actual.artist, actual.title,(mp.getDuration()/1000)+"", actual.album);
    	 CharSequence text=actual.artist + " - "+actual.title;
    	Notification notification = new Notification(R.drawable.icon, text,
                   System.currentTimeMillis());
    mNM.cancelAll();
    // The PendingIntent to launch our activity if the user selects this notification
    PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
            new Intent(this, OnAir.class), 0);

    // Set the info for the views that show in the notification panel.
    notification.setLatestEventInfo(this, getText(R.string.remote_service_label),
                   text, contentIntent);

    // Send the notification.
    // We use a string id because it is a unique number.  We use it later to cancel.
    mNM.notify(R.string.remote_service_started, notification);
    	
    }
    
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.remote_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.icon, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, OnAir.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.remote_service_label),
                       text, contentIntent);

        // Send the notification.
        // We use a string id because it is a unique number.  We use it later to cancel.
        mNM.notify(R.string.remote_service_started, notification);
    }
}
