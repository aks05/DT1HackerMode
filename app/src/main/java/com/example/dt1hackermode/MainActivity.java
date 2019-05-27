package com.example.dt1hackermode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etYOD, etGuess;
    private TextView tvCorrectGuess, tvWrongGuess;
    private int noOfCorrectGuess=0, noOfWrongGuess=0, YOD=0;
    private String C= "No. of Correct Guess : ", W= "No. of Wrong Guess : ";
    View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref= getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE);

        etGuess= findViewById(R.id.etGuess);
        etYOD= findViewById(R.id.etYOD);
        tvCorrectGuess= findViewById(R.id.tvCorrectGuess);
        tvWrongGuess= findViewById(R.id.tvWrongGuess);

        noOfCorrectGuess= sharedPref.getInt(getString(R.string.correctguess_key),0);
        noOfWrongGuess= sharedPref.getInt(getString(R.string.wrongguess_key), 0);
        YOD= sharedPref.getInt(getString(R.string.yod_key), 0);

        setBackground();
        tvWrongGuess.setText(W+noOfWrongGuess);
        tvCorrectGuess.setText(C+noOfCorrectGuess);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPref= getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPref.edit();

        editor.putInt(getString(R.string.correctguess_key), noOfCorrectGuess);
        editor.putInt(getString(R.string.wrongguess_key), noOfWrongGuess);
        editor.putInt(getString(R.string.yod_key), YOD);

        editor.apply();
    }

    private void setBackground () {
        View layout;
        layout= findViewById(R.id.layout);
        root= layout.getRootView();
        Configuration conf = getResources().getConfiguration();
        boolean isLandscape =(conf.orientation==Configuration.ORIENTATION_LANDSCAPE);

        if (isLandscape)
            root.setBackgroundResource(R.drawable.ryuk_landscape);

        else
            root.setBackgroundResource(R.drawable.ryuk);


    }

    private void setToast(String s) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, s, duration);
        toast.show();
    }

    private void Calculate(int Set, int Guess) {
        int A=255, R=0, G, B=0, Color;

        if (Guess-Set>0) {
            setToast("Guess is higher");
            noOfWrongGuess++;
            tvWrongGuess.setText(W+noOfWrongGuess);
            R= 10*(Guess-Set);
            if(R>255)
                R=255;
            G=255-(10*(Guess-Set));
            if(G<0)
                G=0;
            Color = (A & 0xff) << 24 | (R & 0xff) << 16 | (G & 0xff) << 8 | (B & 0xff);
            root.setBackgroundColor(Color);

        }
        else if (Guess-Set<0) {
            setToast("Guess is lower");
            noOfWrongGuess++;
            tvWrongGuess.setText(W+noOfWrongGuess);
            R= 10*(Set-Guess);
            if(R>255)
                R=255;
            G=255-(10*(Set-Guess));
            if(G<0)
                G=0;
            Color = (A & 0xff) << 24 | (R & 0xff) << 16 | (G & 0xff) << 8 | (B & 0xff);
            root.setBackgroundColor(Color);
        }
        else {
            setToast("Correct Guess");
            noOfCorrectGuess++;
            YOD= 0;
            tvCorrectGuess.setText(C+noOfCorrectGuess);
            G=255;
            Color = (A & 0xff) << 24 | (R & 0xff) << 16 | (G & 0xff) << 8 | (B & 0xff);
            root.setBackgroundColor(Color);
        }
    }

    public void Set(View view) {
        setBackground();

        if(etYOD.getText().length()==0) {
            setToast("Set the year of Death");
        }

        else if (Integer.parseInt(etYOD.getText().toString())>100 || Integer.parseInt(etYOD.getText().toString())==0 ) {
            etYOD.getText().clear();
            setToast("Choose age from 1-100");
        }

        else {
            YOD=Integer.parseInt(etYOD.getText().toString());
            etYOD.getText().clear();
            setToast("Successfully set. Now Guess can be made");
        }
    }

    public void Guess(View view) {
        if(YOD==0) {
            setToast("Set the year of Death");
        }

        else {
            if (etGuess.getText().length() == 0) {
                setToast("Guess the year of Death");
            } else if (Integer.parseInt(etGuess.getText().toString()) > 100 || Integer.parseInt(etGuess.getText().toString()) == 0) {
                etGuess.getText().clear();
                setToast("Guess age from 1-100");
            } else {
                int Blue=0xFF03A9F4;
                int Guess = Integer.parseInt(etGuess.getText().toString());

                etGuess.getText().clear();
                tvCorrectGuess.setTextColor(Blue);
                tvWrongGuess.setTextColor(Blue);
                Calculate(YOD, Guess);
            }
        }
    }
}
