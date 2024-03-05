/**
 * @flow
 */
import { NativeModules, DeviceEventEmitter, Platform } from 'react-native';

type PhoneResponseType = {
    phoneNumber?: string;
};

type EmailResponseType = {
    email?: string;
    id?: string;
    givenName?: string;
    name?: string;
    familyName?: string;
    profilePictureUri?: string;
    accountType?: string;
};

// type HintRequestPickerType = {
//     getPhoneNumber(): Promise<PhoneResponseType>;
//     getGoogleAccount(): Promise<EmailResponseType>;
// };

const { HintRequestPicker } = NativeModules;
const Constants = HintRequestPicker.getConstants();
export const getPhoneNumber = function (): Promise<PhoneResponseType> {
    return new Promise((resolve, reject) => {
        if (Platform.OS !== 'android') {
            reject('Only Android is supported');
            return;
        }
        try {
            DeviceEventEmitter.addListener(
                Constants.PHONE_SELECTED_EVENT,
                function listen(data) {
                    resolve(data);
                }
            );
            HintRequestPicker.getPhoneNumber();
        } catch (e) {
            reject(e);
        }
    });
};
export const getGoogleAccount = function (clientId?: string): Promise<EmailResponseType> {
    return new Promise((resolve, reject) => {
        if (Platform.OS !== 'android') {
            reject('Only Android is supported');
            return;
        }
        try {
            DeviceEventEmitter.addListener(
                Constants.EMAIL_SELECTED_EVENT,
                function listen(data) {
                    resolve(data);
                }
            );
            HintRequestPicker.getGoogleAccount(clientId);
        } catch (e) {
            reject(e);
        }
    });
};
