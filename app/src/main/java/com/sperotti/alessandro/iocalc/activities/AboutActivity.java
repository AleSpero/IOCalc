package com.sperotti.alessandro.iocalc.activities;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sperotti.alessandro.iocalc.BuildConfig;
import com.sperotti.alessandro.iocalc.R;


/**
 * Created by Alessandro on 19/12/2016.
 */
public class AboutActivity extends AppCompatActivity {

    Button website;
    Button rateapp;
    TextView headerApp;

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        headerApp = findViewById(R.id.headerApp);

        website = findViewById(R.id.website);
        rateapp = findViewById(R.id.rateapp);

        img = findViewById(R.id.imageView);

        headerApp.setText(String.format("IOCalc - v%s", BuildConfig.VERSION_NAME));

        website.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // Redireziona al sito
                Uri uri = Uri.parse("https://www.alessandrosperotti.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

        });

        rateapp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // Redireziona al sito
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + AboutActivity.this.getPackageName()));
                startActivity(intent);
            }

        });

        img.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(AboutActivity.this)
                        .setTitle(R.string.secret)
                        .setMessage("/4+UbK1pPW0Y4cIsx36BZgwk4lb4yIrR4REzPJ53Us9jsRd011TK72jSu/XmMHLEORGcjbctvp+8giP5D7s11rU36Vv0TRYYNdT/97Cx6uVwwK+I+897HzE8Q7gd13s/AV3Yag5B8RDsbHfzVbIG4/Mw5bozaRutaCS/aByuZOw=" +
                                " " +
                                " (AES-256, key = see this app's name)")
                        .setPositiveButton(R.string.copy, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("Ciao", "/4+UbK1pPW0Y4cIsx36BZgwk4lb4yIrR4REzPJ53Us9jsRd011TK72jSu/XmMHLEORGcjbctvp+8giP5D7s11rU36Vv0TRYYNdT/97Cx6uVwwK+I+897HzE8Q7gd13s/AV3Yag5B8RDsbHfzVbIG4/Mw5bozaRutaCS/aByuZOw=");
                                clipboard.setPrimaryClip(clip);

                            }
                        })
                        .setNeutralButton(android.R.string.cancel,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }

        });



    }

}
