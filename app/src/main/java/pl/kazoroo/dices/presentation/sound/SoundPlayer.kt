package pl.kazoroo.dices.presentation.sound

import android.content.Context
import android.media.SoundPool
import pl.kazoroo.dices.R

object SoundPlayer {
    private val soundPool: SoundPool = SoundPool.Builder().setMaxStreams(5).build()
    private lateinit var soundMap: Map<SoundType, List<Int>>

    fun initialize(context: Context) {
        soundMap = mapOf(
            SoundType.CLICK to listOf(soundPool.load(context, R.raw.click_sound, 1)),
            SoundType.WIN to listOf(soundPool.load(context, R.raw.win_sound, 1)),
            SoundType.FAILURE to listOf(soundPool.load(context, R.raw.failure_sound, 1)),
            SoundType.SKUCHA to listOf(soundPool.load(context, R.raw.skucha_sound, 1)),
            SoundType.DICE_ROLLING to listOf(
                soundPool.load(context, R.raw.dice_rolling, 1),
                soundPool.load(context, R.raw.dice_rolling2, 1),
                soundPool.load(context, R.raw.shaking_and_rolling_dice, 1)
            )
        )
    }

    fun playSound(type: SoundType) {
        soundMap[type]?.let { soundId ->
            soundPool.play(soundId.random(), 1f, 1f, 1, 0, 1f)
        }
    }

    fun release() {
        soundPool.release()
    }
}
