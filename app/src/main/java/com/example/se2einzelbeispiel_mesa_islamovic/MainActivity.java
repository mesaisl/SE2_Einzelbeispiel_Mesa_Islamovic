package com.example.se2einzelbeispiel_mesa_islamovic;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.*;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private TextView textQuersumme;
    private EditText matrikelNrTxt;
    private TextView textAntwort;
    private Socket socket;
    private OutputStream output;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private String antwortVomServer;

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
                    textQuersumme.setText(calcAlternierendeQuersumme(Integer.parseInt(matrikelNrTxt.getText().toString())));
                }catch (NumberFormatException e ) {
                    Toast.makeText(getApplicationContext(), "Geben Sie Matrikelnummer ein!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public String workingWithServer(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                EditText editText = (EditText) findViewById(R.id.editText);
                try {
                    socket = new Socket("se2-isys.aau.at",53212);
                    output = socket.getOutputStream();
                    printWriter = new PrintWriter(output, true);
                    printWriter.println(editText.getText().toString());
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    antwortVomServer = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return antwortVomServer;
    }
    public String calcAlternierendeQuersumme(int num) {
        int altCrossSum = 0;
        int i = 0;

        while (num > 0) {
            if (i % 2 == 0) {
                altCrossSum += num % 10;
                i++;
            } else {
                altCrossSum -= num % 10;
                i++;
            }
            num = num / 10;
        }
        return "Alternierende Quersumme= "+altCrossSum+ "  Gerade/Ungerade: " +geradeOderUngerade(altCrossSum);
    }
    public String geradeOderUngerade(int num){
        if(num%2==0){
            return "Gerade";
        }else{
            return "Ungerade";
        }
    }
}
