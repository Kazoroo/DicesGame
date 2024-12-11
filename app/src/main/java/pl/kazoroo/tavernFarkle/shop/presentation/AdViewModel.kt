package pl.kazoroo.tavernFarkle.shop.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import pl.kazoroo.tavernFarkle.shop.domain.AdManager

class AdViewModel : ViewModel() {
    fun showRewardedAd(context: Context, onReward: (String) -> Unit) {
        AdManager.showAd(
            onRewardEarned = onReward,
            context = context
        )
    }
}
