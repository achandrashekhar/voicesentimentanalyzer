# voicesentimentanalyzer
Android app to analyze sentiment of voice using Google speech to text and IBM sentiment analyzer

The apk file is in the root directory of this repo app-debug.apk. This is an unsigned APK

If you want to run this app without the apk:
1. Clone the repo
2. Delete the app-debug.apk file from root directory
3. Rebuild files
4. Hit the Run button from your editor (Android Studio) you should be able to see a single page app

This app is entirely written in Java 7 and JRE 1.8
This app is copatible android 
The app actually talks to a server (line 126) you will need to edit this line out. First run the server () you will be able to see an IP address on the console log,
paste that on line 126 otherwise it will error out. 
