package com.thorkane.playground

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Hilt isn't my first choice for DI but it is the easiest to get started with. Favoring quick set up
 * over feature richness here.
 */
@HiltAndroidApp
class PlaygroundApplication : Application() { }
