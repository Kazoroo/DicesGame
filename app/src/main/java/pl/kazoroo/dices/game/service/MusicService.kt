package pl.kazoroo.dices.game.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import pl.kazoroo.dices.R

class MusicService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private val musicFiles = listOf(R.raw.tavern, R.raw.kempps, R.raw.arabish, R.raw.sonnical, R.raw.inki, R.raw.cowboy)

    override fun onCreate() {
        super.onCreate()
        playRandomSong()
    }

    private fun playRandomSong() {
        if(::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }

        val randomResId = musicFiles.random()

        mediaPlayer = MediaPlayer.create(this, randomResId).apply {
            start()
            setOnCompletionListener {
                playRandomSong()
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
