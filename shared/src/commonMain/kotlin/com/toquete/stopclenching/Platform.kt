package com.toquete.stopclenching

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform