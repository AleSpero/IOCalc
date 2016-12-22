package com.sperotti.alessandro.iocalc;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

/**
 * Created by Alessandro on 27/11/2016.
 */

// In this case, the fragment displays simple text based on the page
public class FragmentNumConv extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    Activity activity;
    boolean flag;

    //Dichiaro variabili

    EditText binary;
    EditText decimal;
    EditText hex;
    EditText oct;

    //Risultati delle operazioni di conversione

    String binRes;
    String decRes;
    String hexRes;
    String octRes;

    String binaryEncoding;

    Toast toast;

    //Variabili temporanee

    ArrayList<String> T = new ArrayList<String>();
    int numTemp;
    String temp;
    String tempSpace = "";

    private Tracker mTracker;

    public static FragmentNumConv newInstance() {
        FragmentNumConv fragment = new FragmentNumConv();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_numconv, container, false);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.getActivity()); //Ottengo Preferences dal contesto attuale (la mia main activity)

        boolean format = pref.getBoolean("format", false); //Ottengo la checkbox per il format dei numeri binari

        //PREFERENZA CODIFICA BINARIA

        binaryEncoding = pref.getString("codbin", "compl2");

        toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);

        binary = (EditText) view.findViewById(R.id.binary);
        decimal = (EditText) view.findViewById(R.id.decimal);
        hex = (EditText) view.findViewById(R.id.hex);
        oct = (EditText)view.findViewById(R.id.oct);

        //CAMBIO INPUTTYPE SE "UNSIGNED" E' SELEZIONATO E VICEVERSA

        if(binaryEncoding.equals("unsigned")){
            decimal.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            oct.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
        else{
            decimal.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            oct.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
        }

        hex.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        //clear = (Button)findViewById(R.id.ClearBtn);

        //Controllo variazioni di input nei vari edittext

        decimal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(decimal.isFocused()){

                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Number Calculator")
                            .setAction("Converti")
                            .setLabel("Partendo da Decimale")
                            .build());

                    //Se ho messo solo meno, non faccio niente

                    if(!decimal.getText().toString().equals("-")) {

                        //Il try serve per controllare errori di input da parte dell'utente

                        try {

                            if ((!decimal.getText().toString().isEmpty())) {

                                numTemp = Integer.parseInt(decimal.getText().toString());

                                //SE IL NUMERO E' NEGATIVO

                                if (numTemp < 0) {

                                    //FAI IN MODO CHE NEL CASO DI UNSIGNED NON SIA INSERIRE IL MENO

                                    switch (binaryEncoding) {
                                        case "compl1":
                                            binRes = Calcolatore.onesComplement(Calcolatore.binToDec(Math.abs(numTemp)));

                                            /* Problema uni binario
                                            tempChar=binRes.charAt(0);
                                            if(tempChar=='0'){
                                                binRes="1"+Calcolatore.onesComplement(Calcolatore.binToDec(Math.abs(numTemp)));
                                                Log.d("","1"+Calcolatore.binToDec(Math.abs(numTemp)));
                                            }*/
                                            break;

                                        case "compl2":
                                            binRes = Calcolatore.twosComplement(Calcolatore.binToDec(Math.abs(numTemp)));

                                           /* Problema uni binario
                                            tempChar=binRes.charAt(0);
                                            if(tempChar=='0'){
                                                binRes="1"+Calcolatore.twosComplement(Calcolatore.binToDec(Math.abs(numTemp)));
                                                Log.d("","1"+Calcolatore.binToDec(Math.abs(numTemp)));
                                            }*/
                                            break;
                                    }

                                    //AGGIUNGO ZERI O UNI IN BASE ALLA CODIFICA SCELTA

                                    binary.setText(Calcolatore.formatBinary(binRes));


                                    hexRes = Calcolatore.hexToDec(Integer.parseInt(Calcolatore.binToDec(binRes)));
                                    hex.setText(hexRes.toUpperCase());

                                    octRes = Calcolatore.octToDec(Math.abs(numTemp));
                                    oct.setText("-" + octRes);

                                } else {

                                    binRes = Calcolatore.binToDec(numTemp);
                                    binary.setText(Calcolatore.formatBinary(binRes));

                                    hexRes = Calcolatore.hexToDec(Integer.parseInt(decimal.getText().toString()));
                                    hex.setText(hexRes.toUpperCase());

                                    octRes = Calcolatore.octToDec(Math.abs(numTemp));
                                    oct.setText(octRes);
                                }
                            } else {
                                binary.setText("");
                                hex.setText("");
                                oct.setText("");
                            }
                        } catch (NumberFormatException e) {
                            binary.setText("");
                            hex.setText("");
                            oct.setText("");
                            toast.show();
                        }
                    }


                }

            }
        });

        binary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        /*if (binary.isFocused()) {
                            binary.clearFocus();


                        if((binary.getText().toString().length()%4 == 0 && tempSpace.length()>binary.getText().toString().length()) || tempSpace.isEmpty()){
                            binary.setText(binary.getText()+" ");
                        binary.setSelection(binary.getText().length());
                            tempSpace=binary.getText().toString();

                    }
                        }

                        binary.requestFocus();
*/
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(binary.isFocused()){

                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Number Calculator")
                            .setAction("Converti")
                            .setLabel("Partendo da Binario")
                            .build());

                    temp = binary.getText().toString().replaceAll("\\s+","");

                            /*Log.d("RESTO 4?",(temp.length()%4 == 0)+"");
                            Log.d("LUNGHEZZA DIVERSA DA 0?",(temp.length()!=0)+"");
                            Log.d("lunghez tempspace>temp?",""+tempSpace.length()+" "+temp.length()+"  "+(tempSpace.length()<temp.length()));
                            Log.d("tempspace?",""+tempSpace.isEmpty());
                            Log.d("","");*/


                    if(temp.length()==tempSpace.length()){
                        binary.clearFocus();                                // clear filters
                        binary.setText(binary.getText().toString().substring(0,binary.getText().toString().length()-1));
                        binary.setSelection(binary.getText().toString().length());
                        temp=binary.getText().toString().replaceAll("\\s+","");
                        binary.requestFocus();
                    }

                    if(((temp.length()%4 == 0 && temp.length()!=0) && tempSpace.length()<temp.length()) || (tempSpace.isEmpty() && temp.length()%4 == 0)){
                        InputFilter[] filters = editable.getFilters(); // save filters
                        editable.setFilters(new InputFilter[] {});

                        binary.clearFocus();                                // clear filters
                        editable.insert(editable.length()," ");
                        binary.requestFocus();
                        // edit text
                        editable.setFilters(filters);
                    }

                    tempSpace= temp;

                    //Controllo errori di input da parte dell'utente (se inserisce 2, o un simbolo) e controllo se il campo è vuoto

                    if(temp.matches("[01]+") || temp.isEmpty()) {

                        //Caso particolare: controllo se il campo NON è vuoto

                        if(!temp.isEmpty()) {

                            //Controllo se è attivata una delle due codifiche oppure Unsigned

                            //controllo il primo bit: se è 1 allora il numero è negativo e faccio il complemento a due, altrimenti lo tratto come se fosse unsigned


                            if(!binaryEncoding.equals("unsigned") && temp.charAt(0)=='1'){

                                switch(binaryEncoding){

                                    case "compl1":
                                        decRes = "-"+Calcolatore.binToDec(Calcolatore.onesComplement(temp));
                                        break;

                                    case "compl2":
                                        decRes = "-"+Calcolatore.binToDec(Calcolatore.twosComplement(temp));
                                        break;

                                    default:
                                        decRes = Calcolatore.binToDec(temp);
                                        break;

                                }

                                decimal.setText(decRes);

                                hexRes = Calcolatore.hexToDec(Integer.parseInt(Calcolatore.binToDec(temp)));
                                hex.setText(hexRes.toUpperCase());

                                octRes = Calcolatore.octToDec(Math.abs(Integer.parseInt(decimal.getText().toString())));
                                oct.setText("-" + octRes);

                            }

                            else {


                                decRes = Calcolatore.binToDec(temp);
                                hexRes = Calcolatore.hexToDec(Integer.parseInt(decRes));
                                octRes = Calcolatore.octToDec(Integer.parseInt(decRes));

                                decimal.setText(decRes);
                                hex.setText(hexRes.toUpperCase());
                                oct.setText(octRes);
                            }
                        }

                        //temp.isEmpty()
                        else{
                            decimal.setText("");
                            hex.setText("");
                            oct.setText("");
                        }

                    }
                    //temp.matches("[01]+") || temp.isEmpty()
                    else{
                        decimal.setText("");
                        hex.setText("");
                        oct.setText("");
                        toast.show();

                    }




                }

            }
        });

        hex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(hex.isFocused()){

                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Number Calculator")
                            .setAction("Converti")
                            .setLabel("Partendo da Esadecimale")
                            .build());

                    temp = hex.getText().toString().toUpperCase();


                    if(temp.matches("[0123456789ABCDEF]+") || temp.isEmpty()){

                        if(!temp.isEmpty()) {

                            //Calcolo prima binario, da li trovo il valore negativo

                            decRes = Calcolatore.hexToDec(temp);
                            binRes = Calcolatore.binToDec(Integer.parseInt(decRes));
                            octRes = Calcolatore.octToDec(Integer.parseInt(decRes));

                            decimal.setText(decRes);
                            binary.setText(Calcolatore.formatBinary(binRes));
                            oct.setText(octRes);
                        }

                        else{
                            decimal.setText("");
                            binary.setText("");
                            oct.setText("");
                        }

                    }
                    else{
                        decimal.setText("");
                        binary.setText("");
                        oct.setText("");
                        toast.show();

                    }




                }

            }
        });

        oct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(oct.isFocused()){

                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Number Calculator")
                            .setAction("Converti")
                            .setLabel("Partendo da Octale")
                            .build());

                    temp=oct.getText().toString();

                    if(!temp.equals("-")) {

                        //Il try serve per controllare errori di input da parte dell'utente

                        try {

                            if (temp.matches("[-01234567]+") && !temp.isEmpty()) {

                                numTemp = Integer.parseInt(oct.getText().toString());

                                //SE IL NUMERO E' NEGATIVO

                                if (numTemp < 0) {

                                    //FAI IN MODO CHE NEL CASO DI UNSIGNED NON SIA INSERIRE IL MENO

                                    switch (binaryEncoding) {
                                        case "compl1":
                                            numTemp = Integer.parseInt(Calcolatore.octToDec(""+Math.abs(numTemp)));
                                            decRes = "-"+numTemp;
                                            binRes = Calcolatore.onesComplement(Calcolatore.binToDec(Math.abs(numTemp)));
                                            break;

                                        case "compl2":
                                            numTemp = Integer.parseInt(Calcolatore.octToDec(""+Math.abs(numTemp)));
                                            decRes = "-"+numTemp;
                                            binRes = Calcolatore.twosComplement(Calcolatore.binToDec(Math.abs(numTemp)));
                                            break;
                                    }

                                    //AGGIUNGO ZERI O UNI IN BASE ALLA CODIFICA SCELTA

                                    decimal.setText(decRes);
                                    binary.setText(Calcolatore.formatBinary(binRes));

                                    hexRes = Calcolatore.hexToDec(Integer.parseInt(Calcolatore.binToDec(binRes)));
                                    hex.setText(hexRes.toUpperCase());


                                } else {

                                    decRes = Calcolatore.octToDec(""+Math.abs(numTemp));
                                    decimal.setText(decRes);

                                    binRes = Calcolatore.binToDec(Integer.parseInt(decimal.getText().toString()));
                                    binary.setText(Calcolatore.formatBinary(binRes));

                                    hexRes = Calcolatore.hexToDec(Integer.parseInt(decimal.getText().toString()));
                                    hex.setText(hexRes.toUpperCase());


                                }
                            } else {
                                binary.setText("");
                                hex.setText("");
                                decimal.setText("");
                            }
                        } catch (NumberFormatException e) {
                            binary.setText("");
                            hex.setText("");
                            decimal.setText("");
                            toast.show();
                        }
                    }




                   /* temp = oct.getText().toString();



                        if(temp.matches("[-01234567]+") || temp.isEmpty()) {

                            if(!temp.isEmpty()){

                            decRes = Calcolatore.octToDec(temp);
                            binRes = Calcolatore.binToDec(Integer.parseInt(decRes));
                            hexRes = Calcolatore.hexToDec(Integer.parseInt(decRes));

                            decimal.setText(decRes);
                            binary.setText(binRes);
                            hex.setText(hexRes.toUpperCase());
                        }

                        else{
                            decimal.setText("");
                            binary.setText("");
                            hex.setText("");
                        }


                        }

                    else{
                        binary.setText("");
                        hex.setText("");
                        decimal.setText("");
                            toast.show();
                    }*/

                }
            }

        });

        return view;
    }


   @Override
    public void onResume() {

       mTracker.setScreenName("Image~" + "Number Converter");
       mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        binaryEncoding = pref.getString("codbin", "compl2");


           if (binaryEncoding.equals("unsigned")) {
               decimal.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
               oct.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
           } else {
               decimal.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
               oct.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
           }


        super.onResume();

    }

}