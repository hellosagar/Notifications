package com.example.notifications

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        receiveInput()

    }

    fun receiveInput() {
        val KEY_REPLY = "key_reply"
        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {

            val inputString = remoteInput.getCharSequence(KEY_REPLY)
            textView.text = inputString
        }

        val channelID = "com.sagar.developer.testing.channel"
        val notificationId = 45

        val repliedNotification = NotificationCompat.Builder(this,channelID)
            .setContentText("Your reply received")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        val notificatioManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificatioManager.notify(notificationId,repliedNotification)
    }

}