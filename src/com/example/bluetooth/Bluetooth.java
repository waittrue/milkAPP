package com.example.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera.ShutterCallback;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class Bluetooth {
	
	BluetoothAdapter  adapter;
	private static final int  REQUEST_ENABLE_BT = 1;
	private static final int MESSAGE_READ = 88;
	private static BluetoothDevice paired = null;
	boolean isService = false;
	boolean isconnnect = false;
	Activity activity;
	private static UUID uuid = new UUID( 511024l,19910808l );
	ArrayAdapter<String> mArrayAdapter; 
	IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	List<BluetoothDevice> listdevice = new ArrayList<BluetoothDevice>();
	ConnectedThread connectedThread;
	AcceptThread acceptThread;
	ConnectThread connectThread;
	
	Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			if(msg.what == MESSAGE_READ){
				
				Toast.makeText(activity.getApplicationContext(), new String( (byte[])(msg.obj)).subSequence(0, msg.arg1) ,
						Toast.LENGTH_LONG).show();
				Log.i("消息",    new String(  (byte[])(msg.obj) ))  ;
				
				//( (byte[])msg.obj ).
			}
		}
	};
	
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        //Toast.makeText(activity.getApplicationContext(),"aa", Toast.LENGTH_SHORT).show();
	        // When discovery finds a device
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	            // Get the BluetoothDevice object from the Intent
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	            listdevice.add(device);
				// Add the name and address to an array adapter to show in a ListView
	            mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
	            Toast.makeText(activity.getApplicationContext(),device.getName(), Toast.LENGTH_LONG).show();
	        }
	    }
	};
	
	
	

	
	public Bluetooth(Activity act) {
		// TODO Auto-generated constructor stub
		adapter = BluetoothAdapter.getDefaultAdapter();
		activity = act;
		if(adapter == null){
			Toast.makeText(activity.getApplicationContext(), "你没有蓝牙设备,无法传输数据", Toast.LENGTH_LONG).show();
		}
		paired = selectFromBonded();
		mArrayAdapter =  new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_list_item_1);
	}
	public void openBlue(){
		if(adapter == null)
			return;
		if(!adapter.isEnabled()){
			Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			activity.startActivityForResult(enableBluetooth,REQUEST_ENABLE_BT);
		}
	}
	
	public void kejian(){
		 
		Intent discoverableIntent = new  Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		activity.startActivityForResult(discoverableIntent,REQUEST_ENABLE_BT );
		
	}
	public boolean startSearch(){
		if(adapter == null)
			return false;
		 boolean result;
		 mArrayAdapter.clear();
		 listdevice.clear();
		activity.registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
		result = adapter.startDiscovery();
		return result;
		 
	}
	
	public ArrayAdapter<String> getListViewAdapter(){
		return mArrayAdapter;
	}
	
	public void setPaired(int pos){
		paired = listdevice.get(pos);
		Log.i("device_name", paired.getName());
		
	}
	private BluetoothDevice selectFromBonded(){
		Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		        // Add the name and address to an array adapter to show in a ListView
		       if (device.getName().equals( "HS-E917")){
		    	   Log.i(" find device",device.getName());
		    	   return device;
		       }
		    }
		}
		Log.i("no find device","null");
		return null;
	}
	
	
	public void asClient(){
		if(paired == null){	
			Log.i("paired", "null");
			return ;
		}
		if(connectThread == null){
			connectThread = new ConnectThread(paired);
			connectThread.start();
			isService = false;
		}
	}
	
	
	private boolean  connect(BluetoothSocket socket ){
		if(socket !=null)
			connectedThread = new ConnectedThread(socket);
		connectedThread.start();
		Log.i(  "连入", String.valueOf(  socket.isConnected() )  );
		return socket.isConnected();
		
		
	}
	
	public void asServer(){
		isService = true;
		if(acceptThread == null ){
			acceptThread = new AcceptThread();
			acceptThread.start();
		}
		else {
			ShutConnect();
			acceptThread = new AcceptThread();
			acceptThread.start();
		}
		
	}
	
	public void ShutConnect(){
		if(connectedThread == null)
			if(isService){
				if(acceptThread != null)
					acceptThread.cancel();
			}
			else {
				if(connectThread !=null)
					connectThread.cancel();
			}
		else {
			connectedThread.cancel();
		}
				
	}	
	

	
	public void sendStream(byte[] bytes){
		if( connectedThread !=null){
			connectedThread.write(bytes);
		}
	}
	
	private class ConnectThread extends Thread {
	    private final BluetoothDevice mmDevice;
	    BluetoothSocket socket = null;
	    public ConnectThread(BluetoothDevice device) {
	        // Use a temporary object that is later assigned to mmSocket,
	        // because mmSocket is final
	        BluetoothSocket tmp = null;
	        mmDevice = device;
	 
	        // Get a BluetoothSocket to connect with the given BluetoothDevice
	        try {
	            // MY_UUID is the app's UUID string, also used by the server code
	            tmp = device.createRfcommSocketToServiceRecord(uuid);
	        } catch (IOException e) { }
	        socket = tmp;
	    }
	 
	    public void run() {
	        // Cancel discovery because it will slow down the connection
	        adapter.cancelDiscovery();
	 
	        try {
	            // Connect the device through the socket. This will block
	            // until it succeeds or throws an exception
	           socket.connect();
	        } catch (IOException connectException) {
	            // Unable to connect; close the socket and get out
	            
	        	Log.i("find socket", "flase");
	        	try {
	                socket.close();
	            } catch (IOException closeException) { }
	            return;
	        }
	        Log.i("find socket", "true");
	        connect(socket);
	        // Do work to manage the connection (in a separate thread)
	       
	    }
	 
	    /** Will cancel an in-progress connection, and close the socket */
	    public void cancel() {
	        try {
	           socket.close();
	        } catch (IOException e) { }
	    }
	}
	
	
	
	private class ConnectedThread extends Thread {
	   
		private final BluetoothSocket mmSocket;
	    private final InputStream mmInStream;
	    private final OutputStream mmOutStream;
	 
	    public ConnectedThread(BluetoothSocket mmsocket) {
	        mmSocket = mmsocket;
	        InputStream tmpIn = null;
	        OutputStream tmpOut = null;
	 
	        // Get the input and output streams, using temp objects because
	        // member streams are final
	        try {
	            tmpIn = mmsocket.getInputStream();
	            tmpOut = mmsocket.getOutputStream();
	        } catch (IOException e) { }
	 
	        mmInStream = tmpIn;
	        mmOutStream = tmpOut;
	    }
	 
	    public void run() {
	        byte[] buffer = new byte[1024];  // buffer store for the stream
	        int bytes; // bytes returned from read()
	 
	        // Keep listening to the InputStream until an exception occurs
	        while (true) {
	        	if(mmSocket.isConnected() == false){
	        		ShutConnect();
	        	}
	            try {
	                // Read from the InputStream
	                bytes = mmInStream.read(buffer);
	                // Send the obtained bytes to the UI activity
	                mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
	                        .sendToTarget();
	                Log.i("len", String.valueOf(bytes));
	                Log.i("run ",    (String) new String(  buffer  ).subSequence(0 , bytes)  )  ;
	            } catch (IOException e) {
	                break;
	            }
	        }
	    }
	 
	    /* Call this from the main activity to send data to the remote device */
	    public void write(byte[] bytes) {
	        try {
	            mmOutStream.write(bytes);
	        } catch (IOException e) { }
	    }
	 
	    /* Call this from the main activity to shutdown the connection */
	    public void cancel() {
	        try {
	            mmSocket.close();
	        } catch (IOException e) { }
	    }
	}
	
	
	private class AcceptThread extends Thread {
	    private final BluetoothServerSocket mmServerSocket;
	 
	    public AcceptThread() {
	        // Use a temporary object that is later assigned to mmServerSocket,
	        // because mmServerSocket is final
	        BluetoothServerSocket tmp = null;
	        try {
	            // MY_UUID is the app's UUID string, also used by the client code
	            tmp = adapter.listenUsingRfcommWithServiceRecord("milk", uuid);
	        } catch (IOException e) { }
	        mmServerSocket = tmp;
	    }
	 
	    public void run() {

	    	BluetoothSocket socket = null;
	        // Keep listening until exception occurs or a socket is returned
	        while (true) {
	        	
	            try {
	            	Log.i("accept","before");
	                socket = mmServerSocket.accept();
	                Log.i("accept","after");
	            } catch (IOException e) {
	                break;
	            }
	            // If a connection was accepted
	            if (socket != null) {
	                // Do work to manage the connection (in a separate thread)
	            	
	            	Log.i("accept","socket!=null");
	                try {
						mmServerSocket.close();
						connect(socket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                break;
	            }

            	Log.i("accept","socket==null");
	        }
	    }
	 
	    /** Will cancel the listening socket, and cause the thread to finish */
	    public void cancel() {
	        try {
	            mmServerSocket.close();
	        } catch (IOException e) { }
	    }
	}
 	
}


