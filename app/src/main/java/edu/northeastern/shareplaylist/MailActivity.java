package edu.northeastern.shareplaylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MailActivity extends AppCompatActivity{

    private EditText email;
    private EditText subject;
    private EditText message;
    private Button button;
    private ArrayList<String> playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        email = findViewById(R.id.email);
        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);
        button = findViewById(R.id.send);
        playlist = getIntent().getStringArrayListExtra("playlist");
        subject.setText("Your SharePlayList");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString();
                String mMessage = message.getText().toString() + "\n";
                for (String s: playlist) {

                    mMessage += "Here's the playlist that you created: \n \n " + s;
                    mMessage += "\n";
                }
                String mSubject = subject.getText().toString();
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String [] {mEmail});
                email.putExtra(Intent.EXTRA_SUBJECT, mSubject);
                email.putExtra(Intent.EXTRA_TEXT, mMessage);
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }


        });
    }

    private void sendEmail() {

        String mEmail = email.getText().toString();
        String mSubject = subject.getText().toString();
        String mMessage = message.getText().toString();
//        for (String s : playlist) {
//            mMessage += s;
//        }

        JavaMailAPI javaMailAPI = new JavaMailAPI(this, mEmail, mSubject, mMessage);

        javaMailAPI.execute();
    }

}