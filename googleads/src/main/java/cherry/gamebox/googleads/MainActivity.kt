package cherry.gamebox.googleads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cherry.gamebox.googleads.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdaptiveBanner.setOnClickListener {
            AdaptiveBannerActivity.start(this@MainActivity)
        }

        binding.btnBanner.setOnClickListener {
            BannerActivity.start(this@MainActivity)
        }

        binding.btnRewardedVideo.setOnClickListener {
            RewardedVideoActivity.start(this@MainActivity)
        }

    }
}