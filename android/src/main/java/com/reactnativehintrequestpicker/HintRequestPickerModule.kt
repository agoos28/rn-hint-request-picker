package com.reactnativehintrequestpicker

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import java.util.Objects
import javax.annotation.Nullable


class HintRequestPickerModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), ActivityEventListener {
    private val hintPicker : HintRequestPicker
    init {
        reactContext.addActivityEventListener(this);
        hintPicker = HintRequestPicker(reactContext);
    }

    override fun getName(): String {
        return "HintRequestPicker"
    }

    // Example method
    // See https://reactnative.dev/docs/native-modules-android
    @ReactMethod
    fun getPhoneNumber() {
        val request: GetPhoneNumberHintIntentRequest = GetPhoneNumberHintIntentRequest.builder().build()
        Identity.getSignInClient(reactContext.baseContext).getPhoneNumberHintIntent(request).addOnSuccessListener {
            result: PendingIntent ->
            try {
                reactContext.currentActivity?.let { ActivityCompat.startIntentSenderForResult(it, result.intentSender, Constants.PHONE_PICKER_REQUEST, null, 0, 0, 0, Bundle()) }
            } catch (e: IntentSender.SendIntentException) {
                e.printStackTrace()
            }
        }.addOnFailureListener {
            error: Exception ->
            sendErrorEvent(Constants.PHONE_SELECTED_EVENT, error.toString())
        }
    }

    @ReactMethod
    fun getGoogleAccount(clientId: String?) {
        Log.d("Hint Request Module", "Getting Email");
        hintPicker.getGoogleAccount(clientId);
    }

    private fun sendEvent(eventName: String, params: WritableMap ) {
        reactContext
                .getJSModule(RCTDeviceEventEmitter::class.java) //supply the result in params
                .emit(eventName, params)
    }

    private fun sendErrorEvent(eventName: String, errorMsg: String) {
        val params = Arguments.createMap()
        params.putString("error", errorMsg)
        sendEvent(eventName, params)
    }

    override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.PHONE_PICKER_REQUEST) {
            val params = Arguments.createMap()
            if (activity != null && data != null && resultCode == RESULT_OK) {
                val phoneNumber = Identity.getSignInClient(activity).getPhoneNumberFromIntent(data)
                if (phoneNumber.isNotEmpty()) {
                    params.putString("phoneNumber", phoneNumber)
                } else {
                    params.putString("error", "Null intent data");
                }
            } else {
                params.putString("error", "Null intent data");
            }
            sendEvent(Constants.PHONE_SELECTED_EVENT, params)
        }
        if (requestCode == Constants.EMAIL_PICKER_REQUEST) {
            val params = Arguments.createMap()
//      if (data !== null && resultCode == RESULT_OK) {
//        val credential: Credential? = data.getParcelableExtra(Credential.EXTRA_KEY)
//          if (credential !== null) {
//              val token = credential.idTokens;
//
//              if (token.size > 0) {
//                  params.putString("tokenId", token.first().idToken);
//              }
//
//              params.putString("givenName", credential.givenName);
//              params.putString("name", credential.name);
//              params.putString("id", credential.id);
//              params.putString("email", credential.id);
//              params.putString("familyName", credential.familyName);
//              params.putString("profilePictureUri", credential.profilePictureUri.toString());
//              params.putString("accountType", credential.accountType);
//          }
//      }
//      else {
//        params.putString("email", null);
//        params.putString("id", null);
//      }
            sendEvent(Constants.EMAIL_SELECTED_EVENT, params)
        }
    }

    override fun getConstants(): Map<String, Any> {
        val constants: MutableMap<String, Any> = HashMap()
        constants["PHONE_SELECTED_EVENT"] = Constants.PHONE_SELECTED_EVENT
        constants["EMAIL_SELECTED_EVENT"] = Constants.EMAIL_SELECTED_EVENT
        return constants
    }
    override fun onNewIntent(intent: Intent?) {}
}
