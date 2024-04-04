package com.reactnativehintrequestpicker
import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext

class HintRequestPicker(private val appContext: ReactApplicationContext) {
  fun getGoogleAccount(clientId: String?) {
    if (clientId !== null) {
      Log.i("clientId", clientId)
    }
  }
}
