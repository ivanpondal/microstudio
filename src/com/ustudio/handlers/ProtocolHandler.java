package com.ustudio.handlers;

import java.io.IOException;
import java.io.InputStream;

import com.ustudio.main.MainActivity;
import com.ustudio.usb.Utilities;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class ProtocolHandler {
	InputStream mInputStream;
	Handler mHandler;

	public ProtocolHandler(Handler handler, InputStream inputStream) {
		mHandler = handler;
		mInputStream = inputStream;
	}

	int readByte() throws IOException {
		int retVal = mInputStream.read();
		if (retVal == -1) {
			throw new RuntimeException("End of stream reached.");
		}
		return retVal;
	}

	int readInt16() throws IOException {
		int low = readByte();
		int high = readByte();
		Log.i("Piano", "readInt16 low=" + low + " high=" + high);
		return low | (high << 8);
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

					int sequence = readByte();
					Toast.makeText(MainActivity.getInstance().getBaseContext(), "secuencia:"+sequence,0);

					mInputStream.mark(0);
			}
			mInputStream.reset();
		} catch (IOException e) {
			Log.i("Piano", "ProtocolHandler error " + e.toString());
		}
	}


}