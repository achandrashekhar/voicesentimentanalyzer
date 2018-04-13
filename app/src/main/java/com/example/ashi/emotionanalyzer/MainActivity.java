package com.example.ashi.emotionanalyzer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.Tone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

private TextView speakButton;
private EditText userInput;

private final int REQ_CODE_SPEECH_INPUT = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            //code for Speech to Text
            speakButton = (TextView) findViewById(R.id.btnSpeak);
            userInput = (EditText) findViewById(R.id.user_input);
            speakButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    askSpeechInput();
                }
            });
            //code for analyzing tone
            final ToneAnalyzer toneAnalyzer =
                    new ToneAnalyzer("2017-07-01");

            JSONObject credentials = new JSONObject(IOUtils.toString(
                    getResources().openRawResource(R.raw.credentials), "UTF-8"
            )); // Convert the file into a JSON object

// Extract the two values
            String username = credentials.getString("username");
            String password = credentials.getString("password");

            System.out.println("what are these"+username+" " +password);
            toneAnalyzer.setUsernameAndPassword("d2b744e1-f7ba-43b0-9af5-f2c6c66776a2", "dY2KwFjtllHB");
            Button analyzeButton = (Button) findViewById(R.id.analyze_button);

            analyzeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // More code here

                    final String textToAnalyze = userInput.getText().toString();
                    ToneOptions options = new ToneOptions.Builder()
                            .addTone(Tone.EMOTION)
                            .html(false).build();

                    toneAnalyzer.getTone(textToAnalyze, options).enqueue(
                            new ServiceCallback<ToneAnalysis>() {
                                @Override
                                public void onResponse(ToneAnalysis response) {
                                    // More code here
                                    List<ToneScore> scores = response.getDocumentTone()
                                            .getTones()
                                            .get(0)
                                            .getTones();
                                    System.out.println("what this "+response.getDocumentTone()
                                            .getTones()
                                            .get(0));
                                    String detectedTones = "";
                                    for (ToneScore score : scores) {
                                        if (score.getScore() > 0.3f) {
                                            detectedTones += score.getName() + " ";
                                        }
                                    }

                                    final String toastMessage =
                                            "The following emotions were detected:\n\n"
                                                    + detectedTones.toUpperCase();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getBaseContext(),
                                                    toastMessage, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    e.printStackTrace();
                                }
                            });
                }
            });
        } catch (Exception e) {

        }
    }

    private void askSpeechInput(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        //intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi Speak Something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch(ActivityNotFoundException a){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        switch(requestCode){
            case REQ_CODE_SPEECH_INPUT:{
                if(resultCode==RESULT_OK && null !=data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    userInput.setText(result.get(0));
                }
                break;
            }
        }
    }


}
