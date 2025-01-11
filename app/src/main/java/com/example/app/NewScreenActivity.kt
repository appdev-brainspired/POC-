package com.example.app
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class NewScreenActivity : AppCompatActivity() {
    private lateinit var currentValue: EditText
    private lateinit var expectedValue: EditText
    private lateinit var fbValue: EditText
    private lateinit var setCurrent: EditText
    private lateinit var ch1FreqValue: EditText
    private lateinit var ch2FreqValue: EditText
    private lateinit var setCh1Freq: EditText
    private lateinit var setCh2Freq: EditText
    private lateinit var btnStartStop: Button
    private lateinit var btnCurrentOnOff: Button
    private lateinit var btnReset: Button
    private lateinit var btnGo: Button
    private lateinit var ch1Go: Button
    private lateinit var ch2Go: Button
    private lateinit var btnUp: ImageButton
    private lateinit var btnDown: ImageButton
    private lateinit var ch1Up: ImageButton
    private lateinit var ch1Down: ImageButton
    private lateinit var ch2Up: ImageButton
    private lateinit var ch2Down: ImageButton

    private var current = 50.0  // in mA
    private var ch1Frequency = 100.0  // in Hz
    private var ch2Frequency = 200.0  // in Hz
    private var isSystemRunning = false
    private var isCurrentOn = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_screen)
        initializeViews()
        setInitialValues()
        setupClickListeners()
    }
    private fun initializeViews() {
        // Initialize all EditText fields
        currentValue = findViewById(R.id.current_value)
        expectedValue = findViewById(R.id.expected_value)
        fbValue = findViewById(R.id.fb_value)
        setCurrent = findViewById(R.id.set_value)
        ch1FreqValue = findViewById(R.id.ch1_freq_value)
        ch2FreqValue = findViewById(R.id.ch2_freq_value)
        setCh1Freq = findViewById(R.id.set_ch1_freq)
        setCh2Freq = findViewById(R.id.set_ch2_freq)

        // Initialize buttons
        btnStartStop = findViewById(R.id.btn_start_stop)
        btnCurrentOnOff = findViewById(R.id.btn_current)
        btnReset = findViewById(R.id.btn_reset)
        btnGo = findViewById(R.id.btn_go)
        ch1Go = findViewById(R.id.ch1_go)
        ch2Go = findViewById(R.id.ch2_go)

        // Initialize ImageButtons
        btnUp = findViewById(R.id.btn_up)
        btnDown = findViewById(R.id.btn_down)
        ch1Up = findViewById(R.id.ch1_up)
        ch1Down = findViewById(R.id.ch1_down)
        ch2Up = findViewById(R.id.ch2_up)
        ch2Down = findViewById(R.id.ch2_down)
    }
    private fun setInitialValues() {
        // Set initial values to EditText fields
        currentValue.setText(String.format("%.1f", current))
        ch1FreqValue.setText(String.format("%.1f", ch1Frequency))
        ch2FreqValue.setText(String.format("%.1f", ch2Frequency))

        // Set initial values to SET fields
        setCurrent.setText(String.format("%.1f", current))
        setCh1Freq.setText(String.format("%.1f", ch1Frequency))
        setCh2Freq.setText(String.format("%.1f", ch2Frequency))
    }
    private fun setupClickListeners() {
        // Master control buttons
        btnStartStop.setOnClickListener { toggleSystem() }
        btnCurrentOnOff.setOnClickListener { toggleCurrent() }
        btnReset.setOnClickListener { resetSystem() }

        // Current control
        btnGo.setOnClickListener { updateCurrent() }
        btnUp.setOnClickListener { adjustValue(setCurrent, 1.0) }
        btnDown.setOnClickListener { adjustValue(setCurrent, -1.0) }

        // CH1 Frequency control
        ch1Go.setOnClickListener { updateCh1Frequency() }
        ch1Up.setOnClickListener { adjustValue(setCh1Freq, 10.0) }
        ch1Down.setOnClickListener { adjustValue(setCh1Freq, -10.0) }

        // CH2 Frequency control
        ch2Go.setOnClickListener { updateCh2Frequency() }
        ch2Up.setOnClickListener { adjustValue(setCh2Freq, 10.0) }
        ch2Down.setOnClickListener { adjustValue(setCh2Freq, -10.0) }
    }
    private fun toggleSystem() {
        isSystemRunning = !isSystemRunning
        btnStartStop.backgroundTintList = ColorStateList.valueOf(
            if (isSystemRunning) Color.RED else resources.getColor(android.R.color.holo_green_light)
        )
        btnStartStop.text = if (isSystemRunning) "Stop" else "Start"
    }

    private fun toggleCurrent() {
        isCurrentOn = !isCurrentOn
        btnCurrentOnOff.backgroundTintList = ColorStateList.valueOf(
            if (isCurrentOn) Color.RED else resources.getColor(android.R.color.holo_green_light)
        )
        btnCurrentOnOff.text = if (isCurrentOn) "Current\nOFF" else "Current\nON"
    }

    private fun resetSystem() {
        // Reset all values to initial state
        current = 50.0
        ch1Frequency = 100.0
        ch2Frequency = 200.0
        isSystemRunning = false
        isCurrentOn = false

        setInitialValues()
        btnStartStop.backgroundTintList = ColorStateList.valueOf(
            resources.getColor(android.R.color.holo_green_light))
        btnCurrentOnOff.backgroundTintList = ColorStateList.valueOf(
            resources.getColor(android.R.color.holo_green_light))
    }

    private fun adjustValue(editText: EditText, increment: Double) {
        try {
            var value = editText.text.toString().toDouble()
            value += increment
            editText.setText(String.format("%.1f", value))
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid input value", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateCurrent() {
        try {
            val newValue = setCurrent.text.toString().toDouble()
            current = newValue
            currentValue.setText(String.format("%.1f", current))
            Toast.makeText(this, "Current updated to $current mA", Toast.LENGTH_SHORT).show()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid current value", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateCh1Frequency() {
        try {
            val newValue = setCh1Freq.text.toString().toDouble()
            ch1Frequency = newValue
            ch1FreqValue.setText(String.format("%.1f", ch1Frequency))
            Toast.makeText(this, "CH1 Frequency updated to $ch1Frequency Hz", Toast.LENGTH_SHORT).show()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid CH1 frequency value", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateCh2Frequency() {
        try {
            val newValue = setCh2Freq.text.toString().toDouble()
            ch2Frequency = newValue
            ch2FreqValue.setText(String.format("%.1f", ch2Frequency))
            Toast.makeText(this, "CH2 Frequency updated to $ch2Frequency Hz", Toast.LENGTH_SHORT).show()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid CH2 frequency value", Toast.LENGTH_SHORT).show()
        }
    }

}
