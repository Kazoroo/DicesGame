package pl.kazoroo.dices.shop.domain

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

object AdManager {
    private var rewardedAd: RewardedAd? = null
    val TAG = "AdManager"

    fun setupAdMob(context: Context) {
        val adRequest = AdManagerAdRequest.Builder().build()

        MobileAds.initialize(context)
        RewardedAd.load(
            context,
            "ca-app-pub-3940256099942544/5224354917",
            adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, "The ad failed to load - $adError")
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(TAG, "The ad has been loaded successfully")
                    rewardedAd = ad
                }
            }
        )
    }

    fun showAd(context: Context) {
        var reward = ""

        rewardedAd?.let { ad ->
            ad.show(context as Activity) { rewardItem ->
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type

                reward = rewardType.plus(rewardAmount)
                Log.d(TAG, "Reward - $reward")
            }
        } ?: run {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
        }
    }
}