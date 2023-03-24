Backendless SDK for Java and Android  
========================================
[![Build Status](https://travis-ci.org/Backendless/java-sdk.svg)](https://travis-ci.org/Backendless/java-sdk)

--------------------------------------------------------------------------------

Welcome to Backendless! In this document you will find the instructions for getting up and running with Backendless quickly. The SDK you downloaded contains a library [link-to-maven](https://mvnrepository.com/artifact/com.backendless) with the APIs, which provide access to the Backendless services. These services enable the server-side functionality for developing and running mobile and desktop applications. Follow the steps below to get started with Backendless:

1. **Create Developer Account.** An account is required in order to create and manage your Backendless backend. You can login to our console at: https://develop.backendless.com
2. **Locate Application ID and API Key.** The console is where you can manage the applications, their configuration settings and data. Before you start using any of the APIs, make sure to select an application in the console and open the "Manage" section. The "App Settings" screen contains the application ID and API keys, which you will need to use in your code.
3. **Open Backendless Examples.** The SDK includes several examples demonstrating some of the Backendless functionality. The /samples folder contains an IDEA project file (AndroidSampleApps.ipr) combining all the samples. 
4. **Copy/Paste Application ID and API Key.**  Each example must be configured with the application ID and API key generated for your application. 
5. **Run Sample Apps.**


Setting up your app
========================================

> Minimum supported version: **`Android 8.1 Oreo (API 27)`**


Maven Integration
--------------------------------------------------------------------------------
The backendless client library for Android and Java is available through the central [Maven](http://mvnrepository.com/artifact/com.backendless/backendless) repository. Since the version of Backendless deployed to maven changes frequently, make sure to lookup the latest version number from Maven Central. To add a dependency for the  library, add the following to pom.xml (make sure to replace "VERSION" with a specific version number):  

**Java Plain**
```xml
<dependency>
  <groupId>com.backendless</groupId>
  <artifactId>java-client-sdk</artifactId>
  <version>VERSION</version> 
</dependency>
```

**Java for Android**
```xml
<dependency>
  <groupId>com.backendless</groupId>
  <artifactId>android-client-sdk</artifactId>
  <version>VERSION</version> 
</dependency>
```


Gradle Configuration
--------------------------------------------------------------------------------
To configure Backendless library in Gradle, add the following line into the "dependencies" element in gradle.build (make sure to replace "VERSION" with a specific version number):  

```groovy
dependencies {
  compile "com.backendless:java-client-sdk:VERSION"
}

// or

dependencies {
  compile "com.backendless:android-client-sdk:VERSION"
}
```


Optional dependencies
========================================

**(1)** SocketIO support.  
```xml
<!-- https://mvnrepository.com/artifact/io.socket/socket.io-client -->
<dependency>
    <groupId>io.socket</groupId>
    <artifactId>socket.io-client</artifactId>
    <version>1.0.0</version>
</dependency>
```
or
```groovy
  api 'io.socket:socket.io-client:1.0.0'
```


**(2)**  Android Messaging support.
```groovy
dependencies {
  api 'androidx.appcompat:appcompat:1.6.1'
  api 'com.google.firebase:firebase-messaging:23.1.2'
  api 'com.google.firebase:firebase-iid:21.1.0'
}
```
