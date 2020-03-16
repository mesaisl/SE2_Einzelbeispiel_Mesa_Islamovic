package com.example.se2einzelbeispiel_mesa_islamovic;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TextView textQuersumme;
    private EditText matrikelNrTxt;
    private TextView textAntwort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                textQuersumme = (TextView) findViewById(R.id.textView2);
                matrikelNrTxt = (EditText) findViewById(R.id.editText);
                textAntwort = (TextView) findViewById(R.id.textView3);

                textAntwort.setText(workingWithServer());
                try {
                    int matrikelNr = Integer.parseInt(matrikelNrTxt.getText().toString());
                    textQuersumme.setText(calcAlternierendeQuersumme(matrikelNr));
                }catch (NumberFormatException e ) {
                    Toast.makeText(getApplicationContext(), "Geben Sie Matrikelnummer ein!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
