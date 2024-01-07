package com.toquete.stopclenching.android

import android.app.AlarmManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.toquete.stopclenching.model.AlarmItem
import com.toquete.stopclenching.presentation.main.MainEvent
import com.toquete.stopclenching.presentation.main.MainViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private val alarmManager: AlarmManager? by lazy {
        getSystemService(AlarmManager::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val canScheduleExactAlarms = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                alarmManager?.canScheduleExactAlarms() ?: false
            } else {
                true
            }
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingView(canScheduleExactAlarms = canScheduleExactAlarms)
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GreetingView(
    viewModel: MainViewModel = koinViewModel(),
    canScheduleExactAlarms: Boolean
) {
    val context = LocalContext.current
    val notificationPermissionState = rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS) {
        viewModel.onNotificationPermissionResult(canScheduleExactAlarms)
    }
    var from by remember {
        mutableStateOf("08:00")
    }
    var to by remember {
        mutableStateOf("17:00")
    }
    var intervalMillis by remember {
        mutableStateOf("3600000")
    }
    ObserveAsEvents(viewModel.event) {
        when (it) {
            is MainEvent.LaunchNotificationPermissionRequest -> {
                notificationPermissionState.launchPermissionRequest()
            }
            is MainEvent.LaunchAlarmPermissionRequest -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                    startActivity(context, intent, null)
                }
            }
        }
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
            value = intervalMillis,
            onValueChange = { intervalMillis = it },
            label = { Text("Interval") }
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row {
            Button(
                onClick = {
                    val alarmItem = AlarmItem(
                        from = from,
                        to = to,
                        intervalMillis = intervalMillis
                    )
                    viewModel.onScheduleAlarmClick(
                        alarmItem,
                        notificationPermissionState.status.isGranted,
                        canScheduleExactAlarms
                    )
                },
            ) {
                Text("Schedule")
            }
            Button(
                onClick = {
                    val alarmItem = AlarmItem(
                        from = from,
                        to = to,
                        intervalMillis = intervalMillis
                    )
                    viewModel.onCancelAlarmClick(alarmItem)
                }
            ) {
                Text("Cancel")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView(canScheduleExactAlarms = true)
    }
}
