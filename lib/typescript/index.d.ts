declare type PhoneResponseType = {
    phoneNumber?: string;
};
declare type EmailResponseType = {
    email?: string;
    id?: string;
    givenName?: string;
    name?: string;
    familyName?: string;
    profilePictureUri?: string;
    accountType?: string;
};
export declare const getPhoneNumber: () => Promise<PhoneResponseType>;
export declare const getGoogleAccount: () => Promise<EmailResponseType>;
export {};
