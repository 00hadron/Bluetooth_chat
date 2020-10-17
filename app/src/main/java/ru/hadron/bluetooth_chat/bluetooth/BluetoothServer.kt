package ru.hadron.bluetooth_chat.bluetooth

import android.bluetooth.BluetoothSocket
import android.util.Log
import ru.hadron.bluetooth_chat.MainActivity

class BluetoothServer(
    private val activity: MainActivity,
    private val bluetoothSocket: BluetoothSocket
) : Thread() {

    private val input = bluetoothSocket.inputStream
    private val output = bluetoothSocket.outputStream

    private val TAG = "BluetoothServer"

    /**
     *  just read a message from the client and add it to the screen.
     */
    override fun run() {
        try {
            val available = input.available()
            val bytes = ByteArray(available)
            Log.e(TAG, "Reading...")
            input.read(bytes, 0, available)

            val text = String(bytes)
            Log.e("server", "Message received")
            Log.e("server", text)
           // activity.appendText(text)
        } catch (exp: Exception) {
            Log.e("client", "Cannot read data", exp)
        } finally {
            input.close()
            output.close()
            bluetoothSocket.close()
        }
    }
}