package com.example.app

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private lateinit var pairedDevices: Set<BluetoothDevice>
    private val REQUEST_BLUETOOTH_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Device does not support Bluetooth", Toast.LENGTH_SHORT).show()
            return
        }

        val onBtn = findViewById<Button>(R.id.button_turn_on)
        val offBtn = findViewById<Button>(R.id.button_turn_off)
        val pairBtn = findViewById<Button>(R.id.button_paired_devices)
        val listView = findViewById<ListView>(R.id.list_paired_devices)

        // Enable Bluetooth
        onBtn.setOnClickListener {
            if (!bluetoothAdapter.isEnabled) {
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBluetoothIntent, REQUEST_BLUETOOTH_PERMISSION)
                Toast.makeText(this, "Bluetooth enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Bluetooth is already enabled", Toast.LENGTH_SHORT).show()
            }
        }

        // Disable Bluetooth
        offBtn.setOnClickListener {
            if (bluetoothAdapter.isEnabled) {
                bluetoothAdapter.disable()
                Toast.makeText(this, "Bluetooth disabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Bluetooth is already disabled", Toast.LENGTH_SHORT).show()
            }
        }

        // Show Paired Devices
        pairBtn.setOnClickListener {
            if (bluetoothAdapter.isEnabled) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                        REQUEST_BLUETOOTH_PERMISSION
                    )
                    return@setOnClickListener
                }

                pairedDevices = bluetoothAdapter.bondedDevices
                val deviceList = ArrayList<String>()
                for (device in pairedDevices) {
                    deviceList.add("${device.name}\n${device.address}")
                }

                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceList)
                listView.adapter = adapter
            } else {
                Toast.makeText(this, "Please enable Bluetooth first", Toast.LENGTH_SHORT).show()
            }
        }

        val goToNewScreenBtn = findViewById<Button>(R.id.button_go_to_new_screen)
        goToNewScreenBtn.setOnClickListener {
            val intent = Intent(this, NewScreenActivity::class.java)
            startActivity(intent) // Start the NewScreenActivity
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_BLUETOOTH_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Bluetooth permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Bluetooth permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
