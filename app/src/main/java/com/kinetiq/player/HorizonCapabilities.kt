package com.kinetiq.player

import android.content.Context
import metavrx.os.HorizonOsDetector

data class HorizonCapabilities(
    val isHorizonOs: Boolean,
    val detectionSucceeded: Boolean
) {
    companion object {
        fun detect(context: Context): HorizonCapabilities {
            return try {
                HorizonCapabilities(
                    isHorizonOs = HorizonOsDetector.isOnHorizonOs(context),
                    detectionSucceeded = true
                )
            } catch (error: Exception) {
                KinetiqLogger.warn(
                    "HorizonCapabilities",
                    "Horizon OS detection unavailable; using Android fallback",
                    error
                )
                HorizonCapabilities(isHorizonOs = false, detectionSucceeded = false)
            }
        }
    }
}
