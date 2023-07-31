import androidx.compose.runtime.Composable
import com.xxmrk888ytxx.coreandroid.AdInterstitialController


/**
 * [Ru]
 *  Интрейфейс для показа рекламы
 */

/**
 * [En]
 * Interface for view ads
 */
interface AdController : AdInterstitialController {

    @Composable
    fun MainScreenBanner()

    @Composable
    fun WordGroupScreenBanner()

    @Composable
    fun TrainingBanner()
}