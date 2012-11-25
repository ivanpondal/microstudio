package com.ustudio.usb;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ustudio.handlers.ProtocolHandler;
import com.ustudio.main.MainActivity;

public class USBRead implements Runnable{

	@Override
	public void run() {
		int ret = 0;
		byte[] buffer = new byte[16384];
		int bufferUsed = 0;
		Looper.prepare();
		while (ret >= 0) {
			try {
				ret = MainActivity.getInstance().getFileInputStream().read(buffer, bufferUsed,
						buffer.length - bufferUsed);
				bufferUsed += ret;
				int remainder = process(buffer, bufferUsed);
				if (remainder > 0) {
					System.arraycopy(buffer, remainder, buffer, 0, bufferUsed - remainder);
					bufferUsed = remainder;
				} else {
					bufferUsed = 0;
				}
			} catch (IOException e) {
				break;
			}
		}	
		Looper.loop();
	}
	
	public int process(byte[] buffer, int bufferUsed) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer, 0,
				bufferUsed);
		ProtocolHandler ph = new ProtocolHandler(inputStream);
		ph.process();
		return inputStream.available();
	}

}
