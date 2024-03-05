package com.reactnativehintrequestpicker
import android.app.Activity
import android.app.PendingIntent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.auth.api.credentials.IdToken;
import com.google.android.gms.auth.api.credentials.IdentityProviders

class HintRequestPicker(private val appContext: ReactApplicationContext) {
  fun getPhoneNumber() {
    val hintRequest = HintRequest.Builder()
      .setPhoneNumberIdentifierSupported(true)
      .build()
    val intent: PendingIntent = Credentials.getClient(appContext.baseContext).getHintPickerIntent(hintRequest)
    try {
      appContext.currentActivity?.let { startIntentSenderForResult(it,intent.intentSender,Constants.PHONE_PICKER_REQUEST, null, 0, 0, 0, Bundle()) }
    } catch (e: SendIntentException) {
      e.printStackTrace()
    }
  }
  fun getGoogleAccount(clientId: String?) {
    val hintRequest = HintRequest.Builder()
        .setEmailAddressIdentifierSupported(true)
        .setIdTokenRequested(true)
        .setAccountTypes(IdentityProviders.GOOGLE)
        .setServerClientId(clientId)
        .build()
    val intent: PendingIntent = Credentials.getClient(appContext.baseContext).getHintPickerIntent(hintRequest)
    if (clientId !== null) {
      Log.i("clientId", clientId)
    }
    try {
      appContext.currentActivity?.let { startIntentSenderForResult(it,intent.intentSender,Constants.EMAIL_PICKER_REQUEST, null, 0, 0, 0, Bundle()) }
    } catch (e: SendIntentException) {
      e.printStackTrace()
    }
  }
}
