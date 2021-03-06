package com.ustudio.handlers;

import java.io.IOException;
import java.io.InputStream;

import com.ustudio.main.MainActivity;
import com.ustudio.midi.MIDIMessage;
import com.ustudio.usb.USBAction;
import com.ustudio.usb.Utilities;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class ProtocolHandler {
	InputStream mInputStream;


	public ProtocolHandler(InputStream inputStream) {

		mInputStream = inputStream;
	}

	int readByte() throws IOException {
		int retVal = mInputStream.read();
		if (retVal == -1) {
			throw new RuntimeException("End of stream reached.");
		}
		return retVal;
	}

	byte[] readBuffer(int bufferSize) throws IOException {
		byte readBuffer[] = new byte[bufferSize];
		int index = 0;
		int bytesToRead = bufferSize;
		while (bytesToRead > 0) {
			int amountRead = mInputStream.read(readBuffer, index,
					bytesToRead);
			if (amountRead == -1) {
				throw new RuntimeException("End of stream reached.");
			}
			bytesToRead -= amountRead;
			index += amountRead;
		}
		return readBuffer;
	}

	public void process() {
		mInputStream.mark(0);
		try {
			while (mInputStream.available() > 0) {

					byte sequence[]=readBuffer(3);
					MIDIMessage tmp_msg=new MIDIMessage(sequence[0],(byte)0,sequence[1],sequence[2]);
					USBAction.processMIDI(tmp_msg);
					mInputStream.mark(0);
			}
			mInputStream.reset();
		} catch (IOException e) {
			Log.i("Piano", "ProtocolHandler error " + e.toString());
		}
	}


}