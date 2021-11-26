package cherry.gamebox.googleads

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cherry.gamebox.googleads.databinding.ActivityBannerBinding
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.admanager.AdManagerAdRequest

class BannerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("ABCDEF012345")).build()
        )

        MobileAds.initialize(this)

        val adRequest = AdManagerAdRequest.Builder().build()

        binding.adView.loadAd(adRequest)

    }

    override fun onPause() {
        binding.adView.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        binding.adView.destroy()
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, BannerActivity::class.java)
            context.startActivity(starter)
        }
    }
}