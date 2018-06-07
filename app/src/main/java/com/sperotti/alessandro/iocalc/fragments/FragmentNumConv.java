package com.sperotti.alessandro.iocalc.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.sperotti.alessandro.iocalc.utils.*;
import com.sperotti.alessandro.iocalc.R;

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
    FloatingActionButton clearBtn;

    //Risultati delle operazioni di conversione

    String binRes;
    String decRes;
    String hexRes;
    String octRes;

    String binaryEncoding;

    Toast toast;
    Snackbar inputErrorSnackbar;

    //Variabili temporanee
    int numTemp;
    String temp;
    String tempSpace = "";

    public static FragmentNumConv newInstance() {
        FragmentNumConv fragment = new FragmentNumConv();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_numconv, container, false);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());//Ottengo Preferences dal contesto attuale (la mia main activity)

        clearBtn = view.findViewById(R.id.clearBtn);

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showError(View.NO_ID, false);
            }
        });

        boolean format = pref.getBoolean("format", false); //Ottengo la checkbox per il format dei numeri binari

        //PREFERENZA CODIFICA BINARIA

        binaryEncoding = pref.getString(Constants.BINARY_CODEC, Constants.TWO_COMPLEMENT);

        toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
        inputErrorSnackbar = Snackbar.make(getActivity().findViewById(R.id.activity_main), R.string.wrong, Snackbar.LENGTH_LONG);

        binary = view.findViewById(R.id.binary);
        decimal = view.findViewById(R.id.decimal);
        hex = view.findViewById(R.id.hex);
        oct = view.findViewById(R.id.oct);

        //CAMBIO INPUTTYPE SE "UNSIGNED" E' SELEZIONATO E VICEVERSA

        if(binaryEncoding.equals(Constants.UNSIGNED)){
            decimal.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            oct.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
        else{
            decimal.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            oct.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
        }

        hex.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

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

                    //Se ho messo solo meno, non faccio niente

                    if(!decimal.getText().toString().equals("-")) {

                        //Il try serve per controllare errori di input da parte dell'utente

                        try {

                            if ((!decimal.getText().toString().isEmpty())) {

                                numTemp = Integer.parseInt(decimal.getText().toString());

                                    //FAI IN MODO CHE NEL CASO DI UNSIGNED NON SIA INSERIRE IL MENO

                                    if(numTemp < 0)
                                    binRes = Constants.TWO_COMPLEMENT.equals(binaryEncoding) ? Calcolatore.twosComplement(Calcolatore.binToDec(Math.abs(numTemp)))
                                            : Calcolatore.onesComplement(Calcolatore.binToDec(Math.abs(numTemp)));

                                    else binRes = Calcolatore.binToDec(numTemp);


                                    //AGGIUNGO ZERI O UNI IN BASE ALLA CODIFICA SCELTA

                                    binary.setText(Calcolatore.formatBinary(binRes));

                                    hexRes = numTemp < 0 ? Calcolatore.hexToDec(Integer.parseInt(Calcolatore.binToDec(binRes)))
                                    : Calcolatore.hexToDec(Integer.parseInt(decimal.getText().toString()));

                                    hex.setText(hexRes.toUpperCase());

                                    octRes = Calcolatore.octToDec(Math.abs(numTemp));
                                    oct.setText(numTemp < 0 ? String.format("-%s",octRes) : octRes);

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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {

                if(binary.isFocused()){

                    temp = binary.getText().toString().replaceAll("\\s+","");

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

                    if(temp.matches(Constants.BINARY_REGEX) || temp.isEmpty()) {

                        //Caso particolare: controllo se il campo NON è vuoto

                        if(!temp.isEmpty()) {

                            //Controllo se è attivata una delle due codifiche oppure Unsigned

                            //controllo il primo bit: se è 1 allora il numero è negativo e faccio il complemento a due, altrimenti lo tratto come se fosse unsigned


                            if(!binaryEncoding.equals(Constants.UNSIGNED) && temp.charAt(0)=='1'){

                                switch(binaryEncoding){

                                    case Constants.ONE_COMPLEMENT:
                                        decRes = "-"+Calcolatore.binToDec(Calcolatore.onesComplement(temp));
                                        break;

                                    case Constants.TWO_COMPLEMENT:
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
                                oct.setText(String.format("-%s", octRes));

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
                    else showError(Constants.BINARY);

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

                    temp = hex.getText().toString().toUpperCase();


                    if(temp.matches(Constants.HEX_REGEX) || temp.isEmpty()){

                        if(!temp.isEmpty()) {

                            //Calcolo prima binario, da li trovo il valore negativo

                            decRes = Calcolatore.hexToDec(temp);
                            binRes = Calcolatore.binToDec(Integer.parseInt(decRes));
                            octRes = Calcolatore.octToDec(Integer.parseInt(decRes));

                            decimal.setText(decRes);
                            binary.setText(Calcolatore.formatBinary(binRes));
                            oct.setText(octRes);
                        }

                    }
                    else{
                        showError(Constants.HEX);
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

                    temp=oct.getText().toString();

                    if(!temp.equals("-")) {

                        //Il try serve per controllare errori di input da parte dell'utente

                        try {

                            if (temp.matches(Constants.OCTAL_REGEX) && !temp.isEmpty()) {

                                numTemp = Integer.parseInt(oct.getText().toString());

                                //SE IL NUMERO E' NEGATIVO

                                //if (numTemp < 0) {

                                    //FAI IN MODO CHE NEL CASO DI UNSIGNED NON SIA INSERIRE IL MENO

                                    numTemp = Integer.parseInt(Calcolatore.octToDec(String.valueOf(Math.abs(numTemp))));
                                    decRes = numTemp < 0 ? String.format("-%s",numTemp) : Calcolatore.octToDec(String.valueOf(Math.abs(numTemp)));
                                    binRes = Constants.ONE_COMPLEMENT.equals(binaryEncoding) ? Calcolatore.onesComplement(Calcolatore.binToDec(Math.abs(numTemp)))
                                            : Calcolatore.twosComplement(Calcolatore.binToDec(Math.abs(numTemp)));

                                    //AGGIUNGO ZERI O UNI IN BASE ALLA CODIFICA SCELTA

                                    decimal.setText(decRes);
                                    binary.setText(Calcolatore.formatBinary(binRes));

                                    hexRes = Calcolatore.hexToDec(Integer.parseInt(Calcolatore.binToDec(binRes)));
                                    hex.setText(hexRes.toUpperCase());


                                /*} else {

                                    decRes = Calcolatore.octToDec(String.valueOf(Math.abs(numTemp)));
                                    decimal.setText(decRes);

                                    binRes = Calcolatore.binToDec(Integer.parseInt(decimal.getText().toString()));
                                    binary.setText(Calcolatore.formatBinary(binRes));

                                    hexRes = Calcolatore.hexToDec(Integer.parseInt(decimal.getText().toString()));
                                    hex.setText(hexRes.toUpperCase());


                                }*/
                            }
                        } catch (NumberFormatException e) {
                            showError(Constants.OCTAL);
                        }
                    }
                }
            }

        });

        return view;
    }


   @Override
    public void onResume() {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        binaryEncoding = pref.getString(Constants.BINARY_CODEC, Constants.TWO_COMPLEMENT);


           if (binaryEncoding.equals(Constants.UNSIGNED)) {
               decimal.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
               oct.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
           } else {
               decimal.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
               oct.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
           }


        super.onResume();

    }

    public void showError(int keepEdt, boolean showSnackbar){
        if(keepEdt != Constants.DECIMAL) decimal.setText("");
        if(keepEdt != Constants.BINARY) binary.setText("");
        if(keepEdt != Constants.OCTAL) oct.setText("");
        if(keepEdt != Constants.HEX) hex.setText("");
        if(!inputErrorSnackbar.isShown() && showSnackbar) inputErrorSnackbar.show();
    }

    public void showError(int keepEdt){
        showError(keepEdt, true);
    }

}