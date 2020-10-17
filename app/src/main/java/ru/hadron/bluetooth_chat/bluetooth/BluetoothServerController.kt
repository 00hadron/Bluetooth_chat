package ru.hadron.bluetooth_chat.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.util.Log
import ru.hadron.bluetooth_chat.MainActivity
import java.io.IOException
import java.util.*

/**
 * 1. Create a server socket, identified by the uuid, in the class constructor
 * 2. Once thread execution started wait for the client connections using accept() method
 * 3. Once client established connection accept() method returns a BluetoothSocket reference
 * that gives access to the input and output streams. We use this socket to start the Server thread.
 */
val uuid: UUID = UUID.fromString("8989063a-c9af-463a-b3f1-f21d9b2b827b")

class BluetoothServerController (
    activity: MainActivity
) : Thread() {

    private var canceled: Boolean = false
    private var serverSocket: BluetoothServerSocket? = null
    private val activity = activity

    private val TAG: String = "BluetoothServerController"

    init {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter != null) {
            this.serverSocket = bluetoothAdapter
                .listenUsingInsecureRfcommWithServiceRecord("test", uuid)
        }
    }

    override fun run() {
        var socket: BluetoothServerSocket
        while (true) {
            if (this.canceled) {
                break
            }
            try {
                socket = serverSocket.accept()
            } catch (exp: IOException) {
                Log.e(TAG, "Exception")
                break
            }

            if (!canceled && socket != null) {
                Log.e(TAG, "Connecting")
                BluetoothServer(this.activity, socket).start()
            }
        }
    }

    fun cancel() {
        this.canceled = true
        this.serverSocket!!.close()
    }

}