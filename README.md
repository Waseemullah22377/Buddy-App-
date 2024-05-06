# Tourism Assistant App

## Overview
This Tourism Assistant App is designed to solve basic tourism problems such as booking taxis, hotels, ordering food, and providing user guidance. Developed using Kotlin, it offers a seamless experience for travelers looking for convenient solutions during their trips.

## Features
- **Taxi Booking:** Users can easily book taxis to travel around their destination.
- **Hotel Booking:** Find and book hotels based on preferences and location.
- **Food Ordering:** Order food from nearby restaurants or hotels.
- **User Guidance:** Provides guidance and information about popular tourist spots, local attractions, and more.

## Technologies Used
- Kotlin
- Android Studio
- Firebase (Authentication, Database, Storage)
- Glide (Image loading library)
- Navigation Component
- Material Components for Android
- Espresso (UI testing framework)
- JUnit (Unit testing framework)

## Installation
To install and run the app, follow these steps:
1. Clone this repository to your local machine.
2. Open the project in Android Studio.
3. Connect your Android device or start an emulator.
4. Build and run the app.

## Usage
Upon launching the app, users can:
- Navigate through various sections such as taxi booking, hotel booking, food ordering, and user guidance.
- Sign in or register using Firebase authentication to access personalized features.
- Book taxis, hotels, and order food seamlessly.
- Explore tourist spots and attractions with user guidance.

## Dependencies
```gradle
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'com.google.gms.google-services'
}

android {
    namespace 'com.example.buddyapp'
    compileSdk 34

    defaultConfig {
        applicationId "com.example.buddyapp"
        minSdk 23
        targetSdk 34
        versionCode 1
        versionName "1.0"
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.10.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.google.firebase:firebase-database-ktx:20.3.0'
    implementation 'androidx.navigation:navigation-fragment-ktx:2.7.5'
    implementation 'com.google.firebase:firebase-auth:22.2.0'
    implementation 'com.google.firebase:firebase-storage:20.3.0'
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    implementation 'com.google.firebase:firebase-auth-ktx:22.2.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    implementation 'androidx.navigation:navigation-ui-ktx:2.7.5'
    implementation 'com.google.firebase:firebase-database:20.2.2'
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    implementation 'androidx.navigation:navigation-ui-ktx:2.7.5'
    implementation 'com.google.android.material:material:1.12.0-alpha01'
    implementation 'com.google.android.material:material:1.10.0'
    implementation "com.google.firebase:firebase-auth:$firebaseAuthVersion"
    implementation 'com.google.firebase:firebase-auth:22.3.0'
}
