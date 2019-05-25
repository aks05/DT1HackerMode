package com.example.dt1hackermode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etYOD, etGuess;
    private TextView tvCorrectGuess, tvWrongGuess;
    int noOfCorrectGuess=0, noOfWrongGuess=0, YOD=0;
    String C= "No. of Correct Guess : ", W= "No. of Wrong Guess : ";
    Context context= MainActivity.this;
    SharedPreferences values= context.getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etGuess= findViewById(R.id.etGuess);
        etYOD= findViewById(R.id.etYOD);
        tvCorrectGuess= findViewById(R.id.tvCorrectGuess);
        tvWrongGuess= findViewById(R.id.tvWrongGuess);

        noOfCorrectGuess= values.getInt(getString(R.string.correctguess_key),0);
        noOfWrongGuess= values.getInt(getString(R.string.wrongguess_key), 0);

        tvWrongGuess.setText(W+noOfWrongGuess);
        tvCorrectGuess.setText(C+noOfCorrectGuess);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor= values.edit();
        editor.putInt(getString(R.string.correctguess_key), noOfCorrectGuess);
        editor.putInt(getString(R.string.wrongguess_key), noOfWrongGuess);
        editor.apply();
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
            R= 2*(Guess-Set);
            G=255-(20*(Guess-Set));
            if(G>255)
                G=255;
            Color = (A & 0xff) << 24 | (R & 0xff) << 16 | (G & 0xff) << 8 | (B & 0xff);
            getWindow().getDecorView().setBackgroundColor(Color);

        }
        else if (Guess-Set<0) {
            setToast("Guess is lower");
            noOfWrongGuess++;
            tvWrongGuess.setText(W+noOfWrongGuess);
            R= 2*(Set-Guess);
            G=255-(20*(Guess-Set));
            if(G>255)
                G=255;
            Color = (A & 0xff) << 24 | (R & 0xff) << 16 | (G & 0xff) << 8 | (B & 0xff);
            getWindow().getDecorView().setBackgroundColor(Color);
        }
        else {
            setToast("Correct Guess");
            noOfCorrectGuess++;
            YOD= 0;
            tvCorrectGuess.setText(C+noOfCorrectGuess);
            G=255;
            Color = (A & 0xff) << 24 | (R & 0xff) << 16 | (G & 0xff) << 8 | (B & 0xff);
            getWindow().getDecorView().setBackgroundColor(Color);
        }
    }

    public void Set(View view) {
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
                int Guess = Integer.parseInt(etGuess.getText().toString());
                etGuess.getText().clear();
                Calculate(YOD, Guess);
            }
        }
    }
}
