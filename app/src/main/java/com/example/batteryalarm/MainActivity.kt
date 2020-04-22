package com.example.batteryalarm

import android.app.AlarmManager
import android.app.PendingIntent.getActivity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.RingtoneManager
import android.net.Uri
import android.os.BatteryManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.logging.Logger


class MainActivity : AppCompatActivity() {

    lateinit var intentFilter: IntentFilter
    lateinit var batteryManager: BatteryManager
//    lateinit var timereciever: TimeReciever
    lateinit var batteryreciever: BatteryReceiver
    lateinit var defaulttone : RingtoneManager

    lateinit var alarmManger: AlarmManager
    var current_count = 0

    val Log = Logger.getLogger(MainActivity::class.java.name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        batteryreciever = BatteryReceiver()
        intentFilter = IntentFilter(Intent.ACTION_POWER_DISCONNECTED)



    }



    override fun onResume() {
        super.onResume()
        Log.info("App is resuming")
        registerReceiver(batteryreciever, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        Log.info("App is paused")

        try{
            unregisterReceiver(batteryreciever)
        }catch (iae: IllegalArgumentException){
            Log.warning(iae.toString())
        }
    }

    fun playRingingTone(){
        val defaultRingtone = RingtoneManager.getRingtone(applicationContext,
                Settings.System.DEFAULT_RINGTONE_URI)
        //fetch current Ringtone
        //fetch current Ringtone
        val currentRintoneUri: Uri = RingtoneManager.getActualDefaultRingtoneUri(applicationContext
                .getApplicationContext(), RingtoneManager.TYPE_RINGTONE)
        val currentRingtone = RingtoneManager.getRingtone(applicationContext, currentRintoneUri)
        //play current Ringtone
        currentRingtone.play()
//        currentRingtone.stop()
    }

    inner class BatteryReceiver: BroadcastReceiver(){

        override fun onReceive(context: Context?, intent: Intent?) {
            var message = "Batter is Charging"
            Log.info(message)
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
            playRingingTone()
        }
    }


//    inner class TimeReciever: BroadcastReceiver(){
//        override fun onReceive(context: Context?, intent: Intent?) {
//            current_count += 1
//            var message = "Counter: ${current_count}"
//            Log.info(message)
//            Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
//        }
//
//    }


}
