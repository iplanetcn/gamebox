package cherry.gamebox.googleads

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.res.Configuration
import android.util.DisplayMetrics
import cherry.gamebox.googleads.databinding.ActivityAdaptiveBannerBinding
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView

class AdaptiveBannerActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdaptiveBannerBinding
    private var initialLayoutComplete = false
    private lateinit var adView: AdManagerAdView
    // Determine the screen width (less decorations) to use for the ad width.
    // If the ad width isn't known, default to the full screen width.
    private val adSize: AdSize
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)
            val density = outMetrics.density

            var adWidthPixels = binding.adViewContainer.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdaptiveBannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loadButton.setOnClickListener { loadBanner(adSize) }

        // Initialize the Mobile Ads SDK with an empty completion listener.
        MobileAds.initialize(this) {}

        // Set your test devices. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        // to get test ads on this device."
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("ABCDEF012345"))
                .build()
        )

        adView = AdManagerAdView(this)
        binding.adViewContainer.addView(adView)
        // Since we're loading the banner based on the adContainerView size, we need to wait until this
        // view is laid out before we can get the width.
        binding.adViewContainer.viewTreeObserver.addOnGlobalLayoutListener {
            if (!initialLayoutComplete) {
                initialLayoutComplete = true
                loadBanner(adSize)
            }
        }
    }

    /** Called when leaving the activity.  */
    public override fun onPause() {
        adView.pause()
        super.onPause()
    }

    /** Called when returning to the activity  */
    public override fun onResume() {
        super.onResume()
        adView.resume()
    }

    /** Called before the activity is destroyed  */
    public override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

    private fun loadBanner(adSize: AdSize) {
        adView.adUnitId = BACKFILL_AD_UNIT_ID
        adView.setAdSizes(adSize)

        // Create an ad request.
        val adRequest = AdManagerAdRequest.Builder().build()

        // Start loading the ad in the background.
        adView.loadAd(adRequest)
    }

    companion object {
        internal val BACKFILL_AD_UNIT_ID = "/30497360/adaptive_banner_test_iu/backfill"
        fun start(context: Context) {
            val starter = Intent(context, AdaptiveBannerActivity::class.java)
            context.startActivity(starter)
        }
    }
}