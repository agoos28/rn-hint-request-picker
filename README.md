# react-native-hint-request-picker

React native wrapper for HintRequest api on android

## Installation

### NPM
```sh
npm install react-native-hint-request-picker
```

### Yarn
```sh
yarn add react-native-hint-request-picker
```

## Usage

### Picker for Phone number
```js
import { getPhoneNumber } from "react-native-hint-request-picker";

// ...

const result = await getPhoneNumber();
```

### Picker for Google Accounts
```js
import  { getGoogleAccount } from "react-native-hint-request-picker";

// ...

const result = await getGoogleAccount();
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
