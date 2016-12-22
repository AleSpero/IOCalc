package com.sperotti.alessandro.iocalc;

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

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Alessandro on 19/12/2016.
 */
public class AboutActivity extends AppCompatActivity {

    Button website;
    Button rateapp;

    ImageView img;

    Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        website = (Button) findViewById(R.id.website);
        rateapp = (Button) findViewById(R.id.rateapp);

        img = (ImageView) findViewById(R.id.imageView);

        website.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // Redireziona al sito
                Uri uri = Uri.parse("http://www.alessandrosperotti.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

        });

        rateapp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // Redireziona al sito
                Uri uri = Uri.parse("http://www.alessandrosperotti.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

        });

        img.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Segret Message")
                        .setAction("Discovered!")
                        .build());

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

                                mTracker.send(new HitBuilders.EventBuilder()
                                        .setCategory("Segret Message")
                                        .setAction("Copied to clipboard")
                                        .build());
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
