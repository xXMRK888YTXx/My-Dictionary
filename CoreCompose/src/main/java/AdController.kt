import androidx.compose.runtime.Composable
import com.xxmrk888ytxx.coreandroid.AdInterstitialController

interface AdController : AdInterstitialController {

    @Composable
    fun MainScreenBanner()

    @Composable
    fun WordGroupScreenBanner()

    @Composable
    fun TrainingBanner()
}