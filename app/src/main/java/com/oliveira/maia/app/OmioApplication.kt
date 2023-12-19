package com.oliveira.maia.app

import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OmioApplication : Application()

@AndroidEntryPoint
open class CustomComponentActivity : ComponentActivity()