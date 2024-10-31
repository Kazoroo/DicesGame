package pl.kazoroo.dices

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
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
import pl.kazoroo.dices.presentation.game.DicesViewModel
import pl.kazoroo.dices.presentation.navigation.Navigation
import pl.kazoroo.dices.presentation.sound.SoundPlayer
import pl.kazoroo.dices.presentation.splashscreen.SplashScreenViewModel
import pl.kazoroo.dices.service.MusicService
import pl.kazoroo.dices.ui.theme.DicesTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SoundPlayer.initialize(this)
        showSplashScreen()
        setContent {
            DicesTheme {
                val viewModel by viewModels<DicesViewModel>()
                val context = LocalContext.current
                LaunchedEffect(Unit) {
                    val intent = Intent(context, MusicService::class.java)
                    context.startService(intent)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(viewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundPlayer.release()
    }

    override fun onPause() {
        super.onPause()
        stopService(Intent(this, MusicService::class.java))
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, MusicService::class.java)
        startService(intent)
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