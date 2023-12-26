package com.toquete.stopclenching.android

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.toquete.stopclenching.model.AlarmItem
import com.toquete.stopclenching.utils.AndroidAlarmScheduler

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scheduler = AndroidAlarmScheduler(applicationContext, ::getPendingIntent)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingView(
                        onScheduleButtonClick = { scheduler.schedule(it) },
                        onCancelButtonClick = { scheduler.cancel(it) }
                    )
                }
            }
        }
    }

    private fun getPendingIntent(item: AlarmItem): PendingIntent {
        return PendingIntent.getBroadcast(
            applicationContext,
            item.hashCode(),
            Intent(applicationContext, AlarmReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GreetingView(
    onScheduleButtonClick: (AlarmItem) -> Unit,
    onCancelButtonClick: (AlarmItem) -> Unit
) {
    val notificationPermissionState = rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
    var from by remember {
        mutableStateOf("08:00")
    }
    var to by remember {
        mutableStateOf("17:00")
    }
    var interval by remember {
        mutableStateOf("60000")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = from,
            onValueChange = { from = it },
            label = { Text("From") }
        )
        TextField(
            value = to,
            onValueChange = { to = it },
            label = { Text("To") }
        )
        TextField(
            value = interval,
            onValueChange = { interval = it },
            label = { Text("Interval") }
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row {
            Button(
                onClick = {
                    if (notificationPermissionState.status.isGranted) {
                        onScheduleButtonClick(AlarmItem(from, to, interval.toLong()))
                    } else {
                        notificationPermissionState.launchPermissionRequest()
                    }
                },
            ) {
                Text("Schedule")
            }
            Button(onClick = { onCancelButtonClick(AlarmItem(from, to, interval.toLong())) }) {
                Text("Cancel")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView({}, {})
    }
}
