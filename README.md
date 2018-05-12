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

Speech to Text conversion API: We used Android’s built in function of converting speech to text. There is no added configuration for this

The Server: This part of the system was added after we conducted the pilot study. The problem we faced was, we were manually typing out the scores and the answers of the participants. To solve this problem, we wrote a server that listens to data from the client app. The communication between the server and the client is via sockets. The client app bundles all the information and opens a socket connection with the server. The server is a non blocking thread and is listening to multiple clients and when it sees data coming in, it just appends that data to a file. This file is stored locally on the machine that runs the server 

IBM Tones API: We are using the IBM tones API. IBM does a good job of doing a sentiment analysis of text. The android app makes an API call to the IBM Watson Tone analyzer service. This API call is a GET request. The response is a JSON that contains the individual scores of the emotions. The user app displays the emotions that have a score of 0.3f or greater
https://www.ibm.com/watson/developercloud/tone-analyzer/api/v3/curl.html?curl#introduction
I am using my password and tokenID for this app, but that is fine. We can make 1000 calls to the API in a single day. Again, there will be no configuration needed for this.

All of the code is written in Java for both the android app and the server.

The statistical analysis is carried out in Python version 3.

Wilcoxon signed-rank test and Chi Square test are performed on the data.

The datasets used for the tests are the encoded values of emotions from the user survey.

Joy : 1, Sadness : 2, Anger : 3, Disgust : 4, Fear : 5

"accuracy_score" and "confusion_matrix" APIs from sklearn.metrics package are used to measure accuracy and check the confusion matrix respectively.
