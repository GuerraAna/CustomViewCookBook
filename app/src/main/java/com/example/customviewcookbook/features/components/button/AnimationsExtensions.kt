package com.example.customviewcookbook.features.components.button

import android.content.Context
import android.media.MediaPlayer
import java.io.IOException
import android.os.Build
import android.util.Log
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

/**
 * Vibração com força customizável (usa efeitos nativos fortes)
 * @param strength LEVE, MEDIO, FORTE, MAXIMO
 */
internal fun Context.vibrateClick(strength: VibrationStrength = VibrationStrength.LEVE) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    if (!vibrator.hasVibrator()) return

    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            val effectId = when (strength) {
                VibrationStrength.LEVE -> VibrationEffect.EFFECT_TICK
                VibrationStrength.MEDIO -> VibrationEffect.EFFECT_CLICK
                VibrationStrength.FORTE -> VibrationEffect.EFFECT_HEAVY_CLICK
                VibrationStrength.MAXIMO -> VibrationEffect.EFFECT_DOUBLE_CLICK
            }
            vibrator.vibrate(VibrationEffect.createPredefined(effectId))
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
            val duration = when (strength) {
                VibrationStrength.LEVE -> 30L
                VibrationStrength.MEDIO -> 50L
                VibrationStrength.FORTE -> 80L
                VibrationStrength.MAXIMO -> 120L
            }
            vibrator.vibrate(VibrationEffect.createOneShot(duration, 255))
        }
        else -> {
            @Suppress("DEPRECATION")
            vibrator.vibrate(80)
        }
    }
}

enum class VibrationStrength {
    LEVE, MEDIO, FORTE, MAXIMO
}


/**
 * Plays a sound from assets, expects the context of the caller.
 */
fun Context.playSoundFromAssets(soundPath: String) {
    try {
        val mp = MediaPlayer()
        val afd = assets.openFd(soundPath)
        mp.setDataSource(
            afd.fileDescriptor,
            afd.startOffset,
            afd.length
        )
        afd.close()
        mp.prepare()
        mp.start()
        mp.setOnCompletionListener { it.release() }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
