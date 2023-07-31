package com.xxmrk888ytxx.mydictionary.domain.BillingManager

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.ToastManager
import com.xxmrk888ytxx.mydictionary.DI.Qualifiers.BillingScopeQualifier
import com.xxmrk888ytxx.mydictionary.R
import com.xxmrk888ytxx.mydictionary.domain.AdsStateManager.AdsStateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class BillingManagerImpl @Inject constructor(
    private val context: Context,
    @BillingScopeQualifier private val billingScope:CoroutineScope,
    private val logger: Logger,
    private val toastManager: ToastManager,
    private val adsStateManager: AdsStateManager
) : BillingManager {

    private var disableAdProduct:ProductDetails? = null

    private val purchasesUpdatedListener by lazy {
        PurchasesUpdatedListener { _, purchases ->
            purchases?.let { billingScope.launch { verifyPurchase(it) } }
        }
    }

    private val billingClient by lazy {
        BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
    }


    override fun buyRemoveAds(activity: Activity) {
        disableAdProduct?.let {
            sendBuyRequest(it,activity)
        }
    }


    override fun restorePurchases() {
        billingScope.launch {
            billingClient.queryPurchasesAsync(
                QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP)
                    .build()
            ) { billingResult: BillingResult, purchases: List<Purchase> ->
                if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) return@queryPurchasesAsync

                billingScope.launch { verifyPurchase(purchases.filter { it.purchaseState == Purchase.PurchaseState.PURCHASED }) }
            }
        }
    }

    private suspend fun verifyPurchase(purchases: List<Purchase>) {
        purchases.forEach { purchase ->
            val acknowledgePurchaseParams = AcknowledgePurchaseParams
                .newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()


            billingClient.acknowledgePurchase(acknowledgePurchaseParams) { billingClient ->
                if (billingClient.responseCode == BillingClient.BillingResponseCode.OK && purchase.products.contains(DISABLE_ADS_PRODUCT_ID)) {
                    billingScope.launch {
                        if(!adsStateManager.isAdsEnabledFlow.first()) return@launch
                        adsStateManager.disableAds()
                        toastManager.showToast(context.getString(R.string.the_ads_has_been_removed_thanks_for_the_purchase))
                    }
                }
            }
        }
    }

    override fun connectToGooglePlay() {
        billingScope.launch {
            try {
                billingClient.startConnection(object : BillingClientStateListener {
                    override fun onBillingSetupFinished(billingResult: BillingResult) {
                        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                            billingScope.launch { requestProduct() }
                        }
                    }

                    override fun onBillingServiceDisconnected() {
                        billingScope.launch {
                            delay(30000)
                            connectToGooglePlay()
                        }
                    }
                })
            } catch (e: Exception) {
                logger.error(e, LOG_TAG)
            }
        }
    }

    private suspend fun requestProduct() {
        val request = QueryProductDetailsParams.Product.newBuilder()
            .setProductId(DISABLE_ADS_PRODUCT_ID)
            .setProductType(BillingClient.ProductType.INAPP)
            .build()

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(listOf(request))
            .build()

        billingClient.queryProductDetailsAsync(params) { _, prodDetailsList ->
            prodDetailsList.forEach {
                if (it.productId == DISABLE_ADS_PRODUCT_ID) {
                    disableAdProduct = it
                }
            }
        }
    }

    private fun sendBuyRequest(product: ProductDetails, activity: Activity) {
        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(product)
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        billingClient.launchBillingFlow(activity, billingFlowParams)
    }

    companion object {
        private const val LOG_TAG = "BillingManagerImpl"
        private const val DISABLE_ADS_PRODUCT_ID = "remove_ads"
    }
}