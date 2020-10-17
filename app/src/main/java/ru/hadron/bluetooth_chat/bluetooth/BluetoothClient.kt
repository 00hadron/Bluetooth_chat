package ru.hadron.bluetooth_chat.bluetooth

import android.bluetooth.BluetoothDevice
import android.util.Log
import java.lang.Exception

class BluetoothClient(
    device: BluetoothDevice
) : Thread() {
    private val socket = device.createRfcommSocketToServiceRecord(uuid)
    var message = ""
    val TAG: String = "Client"

    override fun run() {
        Log.e(TAG, "Connecting")
        this.socket.connect()

        Log.e(TAG, "Sending")
        val input = socket.inputStream
        val output = socket.outputStream
        try {
            output.write(message.toByteArray())
            output.flush()
            Log.e(TAG, "Sent")
        } catch (e: Exception) {
            Log.e(TAG, "Cannot send", e)
        } finally {
            input.close()
            output.close()
            socket.close()
        }
    }
}