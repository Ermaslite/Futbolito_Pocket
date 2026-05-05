package com.example.futbolito_pocket.ui.cancha

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null

    var ballPosition by mutableStateOf(Offset(500f, 1700f))
    var ballVelocity by mutableStateOf(Offset(0f, 0f))
    
    var americaScore by mutableStateOf(0)
    var chivasScore by mutableStateOf(0)

    // Boundaries and collision scale (matching DisenoCancha)
    private val widthScale = 1000f
    private val heightScale = 1800f
    private val ballRadius = 12f

    fun initSensors(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            // Accelerometer data: x-axis is tilted left/right, y-axis is tilted forward/back
            val ax = -event.values[0] // Invert X for natural movement
            val ay = event.values[1]

            updateBallPhysics(ax, ay)
        }
    }

    private fun updateBallPhysics(ax: Float, ay: Float) {
        // Apply acceleration to velocity
        val friction = 0.95f
        ballVelocity = Offset(
            (ballVelocity.x + ax * 0.5f) * friction,
            (ballVelocity.y + ay * 0.5f) * friction
        )

        // Update position
        var newX = ballPosition.x + ballVelocity.x
        var newY = ballPosition.y + ballVelocity.y

        // --- COLLISION LOGIC ---
        
        // 1. Outer Frame Collisions
        if (newX < 50f + ballRadius) {
            newX = 50f + ballRadius
            ballVelocity = Offset(-ballVelocity.x * 0.6f, ballVelocity.y)
        } else if (newX > 950f - ballRadius) {
            newX = 950f - ballRadius
            ballVelocity = Offset(-ballVelocity.x * 0.6f, ballVelocity.y)
        }

        // 2. Goal Detection and Vertical Boundaries
        // Top Goal Line (The imaginary line from image)
        if (newY < 100f && newX in 400f..600f) {
            // America (Top) receives a goal? No, Chivas scores in America's goal
            chivasScore++
            resetBall()
            return
        }
        
        // Bottom Goal Line
        if (newY > 1700f && newX in 400f..600f) {
            americaScore++
            resetBall()
            return
        }

        // Vertical Wall Bounce (if not in goal)
        if (newY < 50f + ballRadius) {
            newY = 50f + ballRadius
            ballVelocity = Offset(ballVelocity.x, -ballVelocity.y * 0.6f)
        } else if (newY > 1750f - ballRadius) {
            newY = 1750f - ballRadius
            ballVelocity = Offset(ballVelocity.x, -ballVelocity.y * 0.6f)
        }

        ballPosition = Offset(newX, newY)
    }

    private fun resetBall() {
        ballPosition = Offset(500f, 900f) // Center of field
        ballVelocity = Offset(0f, 0f)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onCleared() {
        super.onCleared()
        sensorManager?.unregisterListener(this)
    }
}
