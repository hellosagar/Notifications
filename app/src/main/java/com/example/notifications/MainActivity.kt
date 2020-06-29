package com.example.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val channelID = "com.sagar.developer.testing.channel"
    private var notificationManager: NotificationManager? = null
    private var KEY_REPLY = "key_reply"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID, "TestingChannel", "Testing Notification Channel")
        button.setOnClickListener {
            displayNotification()
        }
    }

    private fun displayNotification() {

        val notificationId = 45
        val tapIntent = Intent(this, SecondActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                tapIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Action Button
        val action: NotificationCompat.Action = NotificationCompat.Action.Builder(0, "Details", pendingIntent).build()

        // Reply Action
        val remoteInput = androidx.core.app.RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Insert Your Name")
                    .build()
        }

        val replyAction = NotificationCompat.Action.Builder(
                0,
                "REPLY",
                pendingIntent
        ).addRemoteInput(remoteInput).build()

        // Creating Notification
        val notification = NotificationCompat.Builder(this, channelID)
                .setContentTitle("Testing Title")
                .setContentText("This is a testing Noptiication")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(replyAction)
                .build()



        notificationManager?.notify(notificationId, notification)

    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val importanceLevel = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importanceLevel).apply {
                description = channelDescription
            }

            notificationManager?.createNotificationChannel(channel)

        }

    }
}