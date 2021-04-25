package com.example.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.View;
import android.widget.Toast;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;
public class Settings extends AppCompatActivity {

    EditText to;
    EditText subject;
    EditText msg;
    Button send;
    String sEmail;
    String sPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.settings);


        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        //overridePendingTransition(0, 0);
                        break;
                        //return;

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), Account.class));
                        finish();
                        //overridePendingTransition(0, 0);
                        break;
                      //  return;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        finish();
                       // overridePendingTransition(0, 0);
                        break;
                        //return;
                }
            }
        });

        to = findViewById(R.id.et_to);
        to.setText("fastbill.este@gmail.com");
        subject = findViewById(R.id.et_subject);
        msg = findViewById(R.id.et_message);
        //sender email credential
        sEmail = "fastbill.este@gmail.com";
        sPassword = "1234este";
        

        send = findViewById(R.id.bt_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize
                Properties properties = new Properties();
                properties.put("mail.smtp.auth","true");
                properties.put("mail.smtp.starttls.enable","true");
                properties.put("mail.smtp.host","smtp.gmail.com");
                properties.put("mail.smtp.port","587");

                //SESSION
                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sEmail,sPassword);
                    }
                });
                try {
                    //Initialize email content
                    MimeMessage message =new MimeMessage(session);
                    message.setFrom(new InternetAddress(sEmail));
                    //Recipient email
                    message.setRecipients(MimeMessage.RecipientType.TO,InternetAddress.parse(to.getText().toString().trim()));
                    message.setSubject(subject.getText().toString().trim());
                    //email msg
                    message.setText(msg.getText().toString().trim());
                    //sen email
                    new SendMail().execute(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private class SendMail extends AsyncTask<MimeMessage,String,String> {
        //Initialize progress dialog
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Create and show progress dialog
            progressDialog = ProgressDialog.show(Settings.this,"Please Wait","Sending Mail....",true,false);

        }

        @Override
        protected String doInBackground(MimeMessage... mimeMessages) {
            try {
                //When success
                Transport.send(mimeMessages[0]);
                return "Success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Error";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Dismiss progress dialog
            progressDialog.dismiss();
            if(s.equals("Success")){
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color='#509324'>Success</font>"));
                builder.setMessage("Mail send successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        subject.setText("");
                        msg.setText("");
                    }
                });
                //show alert dialog
                builder.show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Something went wrong ?",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

