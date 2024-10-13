[![SupportedLanguages](https://img.shields.io/badge/Platforms-iOS%20%7C%20%20Android-green.svg)](https://www.zoho.com/salesiq/help/developer-section/cordova-ionic-installation.html) [![Version](https://img.shields.io/badge/version-8.1.0-blue.svg)](https://mobilisten.io/) [![Mobilisten NPM CD](https://github.com/zoho/SalesIQ-Mobilisten-Cordova/workflows/Mobilisten%20NPM%20CD/badge.svg)](https://github.com/zoho/SalesIQ-Mobilisten-Cordova/actions?query=workflow%3A%22Mobilisten+NPM+CD%22)

# SalesIQ Mobilisten SDK - Cordova Plugin

Connect with customers at every step of their journey. Give them the best in-app live chat experience with Mobilisten. Mobilisten enables customers to reach you from any screen on your app, get their questions answered, and make better purchase decisions.  


## Getting Started

**Mobilisten** comes with a set of highly configurable APIs to suit your needs. Get started by generating an `App` and `Access` key for your app from the SalesIQ console.


## Requirements

**iOS**: iOS 12 or above is required. The minimum version of Xcode required is Xcode 13.

**Android**: 
Ensure that your project meets the following requirements:

   - Minimum Android Version: Android 5.0 (Lollipop) (API Level 21)
   - Compile SDK Version: 34 (Android 14)
   - Required Permissions:
      - android.permission.INTERNET (Required for network operations)  

## Installation

You can integrate Mobilisten to your existing [Apache Cordova](https://cordova.apache.org/)/[PhoneGap](https://phonegap.com/)/[Ionic](https://ionicframework.com/) applications.

#### Installation and Integration Steps

**Cordova Projects**
```ruby
cordova plugin add https://github.com/zoho/SalesIQ-Mobilisten-Cordova.git
```

**Ionic Projects**
```ruby
ionic cordova plugin add https://github.com/zoho/SalesIQ-Mobilisten-Cordova.git
```

**Ionic React Projects using Capacitor**
```ruby
npm install https://github.com/zoho/SalesIQ-Mobilisten-Cordova.git
npx cap sync
```
**Android**:
Open the `android` directory in Android Studio or any IDE used for Android development.  Open the project `build.gradle` or `settings.gradle` file and add the following maven repository.

For Gradle version 6.7 and below
```Gradle
// Add the following to your project's root build.gradle file.

allprojects {
   repositories {
      google()
      mavenCentral()
      // ...
      maven { url 'https://maven.zohodl.com' }
   }
}
```

For Gradle version 6.8 and above
```Gradle
// Add the following to your settings.gradle file.

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // Add the Zoho Maven URL here
        maven { url 'https://maven.zohodl.com' }
    }
}
```


Find our detailed installation guide [here](https://www.zoho.com/salesiq/help/developer-section/cordova-ionic-installation.html).


## API Documentation
You can find the list of all APIs and their documentation [here](https://www.zoho.com/salesiq/help/developer-section/cordova-ionic-sdk-present.html) in the **API Reference** section.

</br>
