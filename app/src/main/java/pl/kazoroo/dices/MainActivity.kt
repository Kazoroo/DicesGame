package pl.kazoroo.dices

import android.animation.ObjectAnimator
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PowerManager
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import pl.kazoroo.dices.core.data.local.UserDataRepository
import pl.kazoroo.dices.core.data.presentation.BettingViewModel
import pl.kazoroo.dices.core.domain.ReadUserDataUseCase
import pl.kazoroo.dices.core.domain.SaveUserDataUseCase
import pl.kazoroo.dices.game.presentation.game.GameViewModel
import pl.kazoroo.dices.game.presentation.navigation.Navigation
import pl.kazoroo.dices.game.presentation.sound.SoundPlayer
import pl.kazoroo.dices.game.presentation.splashscreen.SplashScreenViewModel
import pl.kazoroo.dices.game.service.MusicService
import pl.kazoroo.dices.ui.theme.DicesTheme

class MainActivity : ComponentActivity() {
    private lateinit var powerManager: PowerManager
    private val screenStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Intent.ACTION_SCREEN_ON -> SoundPlayer.setAppOnFocusState(true)
                Intent.ACTION_SCREEN_OFF -> SoundPlayer.setAppOnFocusState(false)
            }
        }
    }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SoundPlayer.initialize(this)
        powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        SoundPlayer.setAppOnFocusState(powerManager.isInteractive)
        registerReceiver(screenStateReceiver, IntentFilter(Intent.ACTION_SCREEN_ON))
        registerReceiver(screenStateReceiver, IntentFilter(Intent.ACTION_SCREEN_OFF))
        showSplashScreen()

        setContent {
            DicesTheme {
                val userDataRepository = UserDataRepository(dataStore)
                val bettingViewModel = BettingViewModel(
                    saveUserDataUseCase = SaveUserDataUseCase(userDataRepository),
                    readUserDataUseCase = ReadUserDataUseCase(userDataRepository)
                )
                val gameViewModel = GameViewModel(
                    bettingActions = bettingViewModel
                )
                val context = LocalContext.current
                LaunchedEffect(Unit) {
                    val intent = Intent(context, MusicService::class.java)
                    context.startService(intent)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(gameViewModel, bettingViewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(screenStateReceiver)
        SoundPlayer.release()
    }

    override fun onPause() {
        super.onPause()
        stopService(Intent(this, MusicService::class.java))
        SoundPlayer.pauseAllSounds()
        SoundPlayer.setAppOnFocusState(false)
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, MusicService::class.java)
        startService(intent)
        SoundPlayer.resumeAllSounds()
        SoundPlayer.setAppOnFocusState(true)
    }

    private fun showSplashScreen() {
        val splashScreenViewModel by viewModels<SplashScreenViewModel>()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !splashScreenViewModel.isReady.value
            }
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView, View.SCALE_X, 0.4f, 0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView, View.SCALE_Y, 0.4f, 0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }
    }
}