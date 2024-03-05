import { NativeModules, DeviceEventEmitter, Platform } from 'react-native';
// type HintRequestPickerType = {
//     getPhoneNumber(): Promise<PhoneResponseType>;
//     getGoogleAccount(): Promise<EmailResponseType>;
// };
const {
  HintRequestPicker
} = NativeModules;
const Constants = HintRequestPicker.getConstants();
export const getPhoneNumber = function () {
  return new Promise((resolve, reject) => {
    if (Platform.OS !== 'android') {
      reject('Only Android is supported');
      return;
    }

    try {
      DeviceEventEmitter.addListener(Constants.PHONE_SELECTED_EVENT, function listen(data) {
        resolve(data);
      });
      HintRequestPicker.getPhoneNumber();
    } catch (e) {
      reject(e);
    }
  });
};
export const getGoogleAccount = function () {
  return new Promise((resolve, reject) => {
    if (Platform.OS !== 'android') {
      reject('Only Android is supported');
      return;
    }

    try {
      DeviceEventEmitter.addListener(Constants.EMAIL_SELECTED_EVENT, function listen(data) {
        resolve(data);
      });
      HintRequestPicker.getGoogleAccount();
    } catch (e) {
      reject(e);
    }
  });
};
//# sourceMappingURL=index.js.map