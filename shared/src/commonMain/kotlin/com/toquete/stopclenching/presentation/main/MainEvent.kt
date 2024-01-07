package com.toquete.stopclenching.presentation.main

sealed interface MainEvent {
    data object LaunchNotificationPermissionRequest : MainEvent
    data object LaunchAlarmPermissionRequest : MainEvent
}