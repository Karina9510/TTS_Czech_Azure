//
// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE.md file in the project root for full license information.
//
// <code>
package com.microsoft.cognitiveservices.speech.samples.quickstart;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisCancellationDetails;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import static android.Manifest.permission.*;

public class MainActivity extends AppCompatActivity {

    // Replace below with your own subscription key
    public static String speechSubscriptionKey = "8b8971466f354ca39fb93c9fb67a1388";
    // Replace below with your own service region (e.g., "westus").
    private static String serviceRegion = "westeurope";

    private SpeechConfig speechConfig;
    private SpeechSynthesizer synthesizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Note: we need to request the permissions
        int requestCode = 5; // unique code for the permission request
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{INTERNET}, requestCode);

        // Initialize speech synthesizer and its dependencies
       speechConfig = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion);

       //speechConfig.setSpeechSynthesisVoiceName("cs-CZ-VlastaNeural");
       speechConfig.setSpeechSynthesisVoiceName("cs-CZ-Jakub");

        assert(speechConfig != null);

        synthesizer = new SpeechSynthesizer(speechConfig);
        assert(synthesizer != null);

        Log.d("Jazyk", speechConfig.getSpeechSynthesisLanguage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Release speech synthesizer and its dependencies
        synthesizer.close();
        speechConfig.close();
    }

    public void onSpeechButtonClicked(View v) {
        TextView outputMessage = this.findViewById(R.id.outputMessage);
        EditText speakText = this.findViewById(R.id.speakText);

        try {
            // Note: this will block the UI thread, so eventually, you want to register for the event 
            SpeechSynthesisResult result = synthesizer.SpeakText(speakText.getText().toString());
            assert(result != null);

            if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
                outputMessage.setText("Speech synthesis succeeded." + "Jazyk je: " + result.getProperties());
            }
            else if (result.getReason() == ResultReason.Canceled) {
                String cancellationDetails =
                        SpeechSynthesisCancellationDetails.fromResult(result).toString();
                outputMessage.setText("Error synthesizing. Error detail: " +
                        System.lineSeparator() + cancellationDetails +
                        System.lineSeparator() + "Did you update the subscription info?");

                Log.d("Chyba", cancellationDetails);
            }

            result.close();
        } catch (Exception ex) {
            Log.e("SpeechSDKDemo", "unexpected " + ex.getMessage());
            assert(false);
        }
    }
}
