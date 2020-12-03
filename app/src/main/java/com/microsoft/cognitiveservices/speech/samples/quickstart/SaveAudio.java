package com.microsoft.cognitiveservices.speech.samples.quickstart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.microsoft.cognitiveservices.speech.AudioDataStream;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisOutputFormat;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

public class SaveAudio extends AppCompatActivity {

    public static String speechSubscriptionKey = "8b8971466f354ca39fb93c9fb67a1388";
    private static String serviceRegion = "westeurope";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_audio);

        ((Button)findViewById(R.id.save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion);

                // set the output format
                speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Riff24Khz16BitMonoPcm);

                SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, null);
                SpeechSynthesisResult result = synthesizer.SpeakText("Customizing audio output format.");
                AudioDataStream stream = AudioDataStream.fromResult(result);
                stream.saveToWavFile("fileAudio.wav");
            }
        });
    }
}
