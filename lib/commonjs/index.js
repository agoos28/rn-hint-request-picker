"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.getGoogleAccount = exports.getPhoneNumber = void 0;

var _reactNative = require("react-native");

// type HintRequestPickerType = {
//     getPhoneNumber(): Promise<PhoneResponseType>;
//     getGoogleAccount(): Promise<EmailResponseType>;
// };
const {
  HintRequestPicker
} = _reactNative.NativeModules;
const Constants = HintRequestPicker.getConstants();

const getPhoneNumber = function () {
  return new Promise((resolve, reject) => {
    if (_reactNative.Platform.OS !== 'android') {
      reject('Only Android is supported');
      return;
    }

    try {
      _reactNative.DeviceEventEmitter.addListener(Constants.PHONE_SELECTED_EVENT, function listen(data) {
        resolve(data);
      });

      HintRequestPicker.getPhoneNumber();
    } catch (e) {
      reject(e);
    }
  });
};

exports.getPhoneNumber = getPhoneNumber;

const getGoogleAccount = function () {
  return new Promise((resolve, reject) => {
    if (_reactNative.Platform.OS !== 'android') {
      reject('Only Android is supported');
      return;
    }

    try {
      _reactNative.DeviceEventEmitter.addListener(Constants.EMAIL_SELECTED_EVENT, function listen(data) {
        resolve(data);
      });

      HintRequestPicker.getGoogleAccount();
    } catch (e) {
      reject(e);
    }
  });
};

exports.getGoogleAccount = getGoogleAccount;
//# sourceMappingURL=index.js.map