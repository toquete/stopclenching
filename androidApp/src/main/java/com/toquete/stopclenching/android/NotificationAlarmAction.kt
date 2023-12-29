package com.toquete.stopclenching.android

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.toquete.stopclenching.utils.AndroidAlarmScheduler

class NotificationAlarmAction(
    private val context: Context
) : AndroidAlarmScheduler.AlarmAction {

    override fun getIntent(time: Long): Intent {
        return Intent(context, AlarmReceiver::class.java).apply {
            data = "${AlarmReceiver.SCHEME}://$time".toUri()
        }
    }
}