package com.sperotti.alessandro.iocalc;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MipsCalculator extends Fragment {

    //Dichiaro Variabili

    EditText hex;
    EditText text;

    LinearLayout result;
    LinearLayout rtype;
    LinearLayout itype;
    LinearLayout jtype;


    TextView opcodeR;
    TextView rsR;
    TextView rtR;
    TextView rdR;
    TextView shamt;
    TextView funct;
    TextView instruction;

    TextView opcodeI;
    TextView rsI;
    TextView rtI;
    TextView offset;

    TextView opcodeJ;
    TextView target;

    String tempArray[];
    String tempopcode;
    String tempoffset;
    String temprs;
    String temprt;
    String temprd;
    String tempshamt;
    String tempfunct;
    String temp;
    String temphex;

    Tracker mTracker;

    Toast toast;

    public static MipsCalculator newInstance() {
        MipsCalculator fragment = new MipsCalculator();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mips_calculator, container, false);

        text = (EditText) view.findViewById(R.id.instruction);
        hex = (EditText) view.findViewById(R.id.hexadecimal);

        instruction = (TextView) view.findViewById(R.id.description);

        opcodeR = (TextView) view.findViewById(R.id.opcodeR);
        rsR = (TextView) view.findViewById(R.id.rsR);
        rtR = (TextView) view.findViewById(R.id.rtR);
        rdR = (TextView) view.findViewById(R.id.rdR);
        shamt = (TextView) view.findViewById(R.id.shamtR);
        funct = (TextView) view.findViewById(R.id.functR);

        opcodeI = (TextView) view.findViewById(R.id.opcodeI);
        rsI = (TextView) view.findViewById(R.id.rsI);
        rtI = (TextView) view.findViewById(R.id.rtI);
        offset = (TextView) view.findViewById(R.id.offsetI);

        opcodeJ = (TextView) view.findViewById(R.id.opcodeJ);
        target = (TextView) view.findViewById(R.id.jumpJ);


        result = (LinearLayout) view.findViewById(R.id.result);
        itype = (LinearLayout) view.findViewById(R.id.itype);
        rtype = (LinearLayout) view.findViewById(R.id.rtype);
        jtype = (LinearLayout) view.findViewById(R.id.jtype);

        text.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (text.isFocused()) {

                    //Splitto la stringa

                    tempArray = text.getText().toString().split(" ");

                    switch (tempArray[0]) {

                        case "add":


                            try {

                                temprs = tempArray[2];
                                temprt = tempArray[3];
                                temprd = tempArray[1];
                                if (((temprs.length() == 3 || temprs.length() == 2) && (temprs.charAt(0) == 'R' || temprs.charAt(0) == 'r') && Integer.parseInt(temprs.substring(1)) < 32) && ((temprt.length() == 3 || temprt.length() == 2) && (temprt.charAt(0) == 'R' || temprt.charAt(0) == 'r') && Integer.parseInt(temprt.substring(1)) < 32) && ((temprd.length() == 2 || temprd.length() == 3) && (temprd.charAt(0) == 'R' || temprd.charAt(0) == 'r') && Integer.parseInt(temprd.substring(1)) < 32)) {

                                    //Rendo visibile layout, e stampo tipo di istruzione e stampo i vari cosi

                                    result.setVisibility(VISIBLE);
                                    itype.setVisibility(GONE);
                                    jtype.setVisibility(GONE);
                                    rtype.setVisibility(VISIBLE);

                                    instruction.setText("Type R Instruction");
                                    //metti in string.xml

                                    //stampo valori nelle varie aree

                                    Log.d("", temprs.substring(1));

                                    opcodeR.setText("000000");
                                    rsR.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprs.substring(1))));
                                    rtR.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprt.substring(1))));
                                    rdR.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprd.substring(1))));
                                    shamt.setText("00000");
                                    funct.setText("100000");

                                    temp = opcodeR.getText().toString() + rsR.getText().toString() + rtR.getText().toString() + rdR.getText().toString() + shamt.getText().toString() + funct.getText().toString();

                                    //imposto la stringa per essere convertita in esadec

                                    hex.setText(Calcolatore.binToHexMIPS(temp));


                                } else {
                                    Log.d("Add", "Condition not met");
                                    result.setVisibility(GONE);
                                    toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                    hex.setText("");
                                    if (text.getText().toString().length() > 12)
                                        toast.show();
                                    instruction.setText("");
                                }
                            } catch (IndexOutOfBoundsException e) {
                                Log.d("Add", e.toString());
                                hex.setText("");
                                result.setVisibility(GONE);
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                                instruction.setText("");
                            } catch (NumberFormatException e) {
                                Log.d("Add", e.toString());
                                hex.setText("");
                                result.setVisibility(GONE);
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                                instruction.setText("");
                            }

                            break;

                        case "sub":

                            try {

                                temprs = tempArray[2];
                                temprt = tempArray[3];
                                temprd = tempArray[1];
                                if (((temprs.length() == 3 || temprs.length() == 2) && (temprs.charAt(0) == 'R' || temprs.charAt(0) == 'r') && Integer.parseInt(temprs.substring(1)) < 32) && ((temprt.length() == 3 || temprt.length() == 2) && (temprt.charAt(0) == 'R' || temprt.charAt(0) == 'r') && Integer.parseInt(temprt.substring(1)) < 32) && ((temprd.length() == 2 || temprd.length() == 3) && (temprd.charAt(0) == 'R' || temprd.charAt(0) == 'r') && Integer.parseInt(temprd.substring(1)) < 32)) {

                                    //Rendo visibile layout, e stampo tipo di istruzione e stampo i vari cosi

                                    result.setVisibility(VISIBLE);
                                    itype.setVisibility(GONE);
                                    jtype.setVisibility(GONE);
                                    rtype.setVisibility(VISIBLE);

                                    instruction.setText("Type R Instruction");
                                    //metti in string.xml

                                    //stampo valori nelle varie aree

                                    Log.d("", temprs.substring(1));

                                    opcodeR.setText("000000");
                                    rsR.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprs.substring(1))));
                                    rtR.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprt.substring(1))));
                                    rdR.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprd.substring(1))));
                                    shamt.setText("00000");
                                    funct.setText("100010");

                                    temp = opcodeR.getText().toString() + rsR.getText().toString() + rtR.getText().toString() + rdR.getText().toString() + shamt.getText().toString() + funct.getText().toString();

                                    //imposto la stringa per essere convertita in esadec

                                    hex.setText(Calcolatore.binToHexMIPS(temp));


                                } else {
                                    result.setVisibility(GONE);
                                    toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                    hex.setText("");
                                    if (text.getText().toString().length() > 12)
                                        toast.show();
                                    instruction.setText("");
                                }
                            } catch (IndexOutOfBoundsException e) {
                                hex.setText("");
                                result.setVisibility(GONE);
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                                instruction.setText("");
                            } catch (NumberFormatException e) {
                                hex.setText("");
                                result.setVisibility(GONE);
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                                instruction.setText("");
                            }


                            break;

                        case "and":

                            try {

                                temprs = tempArray[2];
                                temprt = tempArray[3];
                                temprd = tempArray[1];
                                if (((temprs.length() == 3 || temprs.length() == 2) && (temprs.charAt(0) == 'R' || temprs.charAt(0) == 'r') && Integer.parseInt(temprs.substring(1)) < 32) && ((temprt.length() == 3 || temprt.length() == 2) && (temprt.charAt(0) == 'R' || temprt.charAt(0) == 'r') && Integer.parseInt(temprt.substring(1)) < 32) && ((temprd.length() == 2 || temprd.length() == 3) && (temprd.charAt(0) == 'R' || temprd.charAt(0) == 'r') && Integer.parseInt(temprd.substring(1)) < 32)) {

                                    //Rendo visibile layout, e stampo tipo di istruzione e stampo i vari cosi

                                    result.setVisibility(VISIBLE);
                                    itype.setVisibility(GONE);
                                    jtype.setVisibility(GONE);
                                    rtype.setVisibility(VISIBLE);

                                    instruction.setText("Type R Instruction");
                                    //metti in string.xml

                                    //stampo valori nelle varie aree

                                    Log.d("", temprs.substring(1));

                                    opcodeR.setText("000000");
                                    rsR.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprs.substring(1))));
                                    rtR.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprt.substring(1))));
                                    rdR.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprd.substring(1))));
                                    shamt.setText("00000");
                                    funct.setText("100100");

                                    temp = opcodeR.getText().toString() + rsR.getText().toString() + rtR.getText().toString() + rdR.getText().toString() + shamt.getText().toString() + funct.getText().toString();

                                    //imposto la stringa per essere convertita in esadec

                                    hex.setText(Calcolatore.binToHexMIPS(temp));


                                } else {
                                    result.setVisibility(GONE);
                                    toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                    hex.setText("");
                                    if (text.getText().toString().length() > 12)
                                        toast.show();
                                    instruction.setText("");
                                }
                            } catch (IndexOutOfBoundsException e) {
                                hex.setText("");
                                result.setVisibility(GONE);
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                                instruction.setText("");
                            } catch (NumberFormatException e) {
                                hex.setText("");
                                result.setVisibility(GONE);
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                                instruction.setText("");
                            }


                            break;

                        case "or":

                            try {

                                temprs = tempArray[2];
                                temprt = tempArray[3];
                                temprd = tempArray[1];
                                if (((temprs.length() == 3 || temprs.length() == 2) && (temprs.charAt(0) == 'R' || temprs.charAt(0) == 'r') && Integer.parseInt(temprs.substring(1)) < 32) && ((temprt.length() == 3 || temprt.length() == 2) && (temprt.charAt(0) == 'R' || temprt.charAt(0) == 'r') && Integer.parseInt(temprt.substring(1)) < 32) && ((temprd.length() == 2 || temprd.length() == 3) && (temprd.charAt(0) == 'R' || temprd.charAt(0) == 'r') && Integer.parseInt(temprd.substring(1)) < 32)) {

                                    //Rendo visibile layout, e stampo tipo di istruzione e stampo i vari cosi

                                    result.setVisibility(VISIBLE);
                                    itype.setVisibility(GONE);
                                    jtype.setVisibility(GONE);
                                    rtype.setVisibility(VISIBLE);

                                    instruction.setText("Type R Instruction");
                                    //metti in string.xml

                                    //stampo valori nelle varie aree

                                    Log.d("", temprs.substring(1));

                                    opcodeR.setText("000000");
                                    rsR.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprs.substring(1))));
                                    rtR.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprt.substring(1))));
                                    rdR.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprd.substring(1))));
                                    shamt.setText("00000");
                                    funct.setText("100101");

                                    temp = opcodeR.getText().toString() + rsR.getText().toString() + rtR.getText().toString() + rdR.getText().toString() + shamt.getText().toString() + funct.getText().toString();

                                    //imposto la stringa per essere convertita in esadec

                                    hex.setText(Calcolatore.binToHexMIPS(temp));


                                } else {
                                    result.setVisibility(GONE);
                                    toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                    hex.setText("");
                                    if (text.getText().toString().length() > 12)
                                        toast.show();
                                    instruction.setText("");
                                }
                            } catch (IndexOutOfBoundsException e) {
                                hex.setText("");
                                result.setVisibility(GONE);
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                                instruction.setText("");
                            } catch (NumberFormatException e) {
                                hex.setText("");
                                result.setVisibility(GONE);
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                                instruction.setText("");
                            }


                            break;

                        case "slt":

                            try {

                                temprs = tempArray[2];
                                temprt = tempArray[3];
                                temprd = tempArray[1];
                                if (((temprs.length() == 3 || temprs.length() == 2) && (temprs.charAt(0) == 'R' || temprs.charAt(0) == 'r') && Integer.parseInt(temprs.substring(1)) < 32) && ((temprt.length() == 3 || temprt.length() == 2) && (temprt.charAt(0) == 'R' || temprt.charAt(0) == 'r') && Integer.parseInt(temprt.substring(1)) < 32) && ((temprd.length() == 2 || temprd.length() == 3) && (temprd.charAt(0) == 'R' || temprd.charAt(0) == 'r') && Integer.parseInt(temprd.substring(1)) < 32)) {

                                    //Rendo visibile layout, e stampo tipo di istruzione e stampo i vari cosi

                                    result.setVisibility(VISIBLE);
                                    itype.setVisibility(GONE);
                                    jtype.setVisibility(GONE);
                                    rtype.setVisibility(VISIBLE);

                                    instruction.setText("Type R Instruction");
                                    //metti in string.xml

                                    //stampo valori nelle varie aree

                                    Log.d("", temprs.substring(1));

                                    opcodeR.setText("000000");
                                    rsR.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprs.substring(1))));
                                    rtR.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprt.substring(1))));
                                    rdR.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprd.substring(1))));
                                    shamt.setText("00000");
                                    funct.setText("101010");

                                    temp = opcodeR.getText().toString() + rsR.getText().toString() + rtR.getText().toString() + rdR.getText().toString() + shamt.getText().toString() + funct.getText().toString();

                                    //imposto la stringa per essere convertita in esadec

                                    hex.setText(Calcolatore.binToHexMIPS(temp));


                                } else {
                                    result.setVisibility(GONE);
                                    toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                    hex.setText("");
                                    if (text.getText().toString().length() > 12)
                                        toast.show();
                                    instruction.setText("");
                                }
                            } catch (IndexOutOfBoundsException e) {
                                hex.setText("");
                                result.setVisibility(GONE);
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                                instruction.setText("");
                            } catch (NumberFormatException e) {
                                hex.setText("");
                                result.setVisibility(GONE);
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                                instruction.setText("");
                            }


                            break;

                        case "lw":

                            try {

                                temprs = tempArray[3].substring(1, tempArray[3].length() - 1);
                                temprt = tempArray[1];
                                tempoffset = tempArray[2];

                                if (((temprs.length() == 3 || temprs.length() == 2) && (temprs.charAt(0) == 'R' || temprs.charAt(0) == 'r') && Integer.parseInt(temprs.substring(1)) < 32) && ((temprt.length() == 3 || temprt.length() == 2) && (temprt.charAt(0) == 'R' || temprt.charAt(0) == 'r') && Integer.parseInt(temprt.substring(1)) < 32) && Integer.parseInt(tempoffset) < 65536) {

                                    //Rendo visibile layout, e stampo tipo di istruzione e stampo i vari cosi

                                    result.setVisibility(VISIBLE);
                                    rtype.setVisibility(GONE);
                                    jtype.setVisibility(GONE);
                                    itype.setVisibility(VISIBLE);


                                    instruction.setText("Type I Instruction");
                                    //metti in string.xml

                                    //stampo valori nelle varie aree

                                    opcodeI.setText("100011");
                                    rsI.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprs.substring(1))));
                                    rtI.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprt.substring(1))));
                                    offset.setText(Calcolatore.addDigitsMIPS(Calcolatore.binToDecMIPS(Integer.parseInt(tempoffset)), 16));

                                    temp = opcodeI.getText().toString() + rsI.getText().toString() + rtI.getText().toString() + offset.getText().toString();

                                    //imposto la stringa per essere convertita in esadec

                                    hex.setText(Calcolatore.binToHexMIPS(temp));


                                } else {
                                    result.setVisibility(GONE);
                                    toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                    hex.setText("");
                                    instruction.setText("");
                                    if (text.getText().toString().length() > 12)
                                        toast.show();
                                }
                            } catch (IndexOutOfBoundsException e) {
                                Log.d("", e.toString());
                                hex.setText("");
                                result.setVisibility(GONE);
                                instruction.setText("");
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                            } catch (NumberFormatException e) {
                                Log.d("", e.toString());
                                hex.setText("");
                                result.setVisibility(GONE);
                                instruction.setText("");
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                            }


                            break;

                        case "sw":

                            try {

                                temprs = tempArray[3].substring(1, tempArray[3].length() - 1);
                                temprt = tempArray[1];
                                tempoffset = tempArray[2];

                                if (((temprs.length() == 3 || temprs.length() == 2) && (temprs.charAt(0) == 'R' || temprs.charAt(0) == 'r') && Integer.parseInt(temprs.substring(1)) < 32) && ((temprt.length() == 3 || temprt.length() == 2) && (temprt.charAt(0) == 'R' || temprt.charAt(0) == 'r') && Integer.parseInt(temprt.substring(1)) < 32) && Integer.parseInt(tempoffset) < 65536) {

                                    //Rendo visibile layout, e stampo tipo di istruzione e stampo i vari cosi

                                    result.setVisibility(VISIBLE);
                                    rtype.setVisibility(GONE);
                                    jtype.setVisibility(GONE);
                                    itype.setVisibility(VISIBLE);


                                    instruction.setText("Type I Instruction");
                                    //metti in string.xml

                                    //stampo valori nelle varie aree

                                    opcodeI.setText("101011");
                                    rsI.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprs.substring(1))));
                                    rtI.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprt.substring(1))));
                                    offset.setText(Calcolatore.addDigitsMIPS(Calcolatore.binToDecMIPS(Integer.parseInt(tempoffset)), 16));

                                    temp = opcodeI.getText().toString() + rsI.getText().toString() + rtI.getText().toString() + offset.getText().toString();

                                    //imposto la stringa per essere convertita in esadec

                                    hex.setText(Calcolatore.binToHexMIPS(temp));


                                } else {
                                    result.setVisibility(GONE);
                                    instruction.setText("");
                                    toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                    hex.setText("");
                                    toast.show();
                                }
                            } catch (IndexOutOfBoundsException e) {
                                Log.d("", e.toString());
                                hex.setText("");
                                result.setVisibility(GONE);
                                instruction.setText("");
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                            } catch (NumberFormatException e) {
                                Log.d("", e.toString());
                                hex.setText("");
                                result.setVisibility(GONE);
                                instruction.setText("");
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                            }


                            break;

                        case "beq":

                            try {

                                temprs = tempArray[1];
                                temprt = tempArray[2];
                                tempoffset = tempArray[3];

                                if (((temprs.length() == 3 || temprs.length() == 2) && (temprs.charAt(0) == 'R' || temprs.charAt(0) == 'r') && Integer.parseInt(temprs.substring(1)) < 32) && ((temprt.length() == 3 || temprt.length() == 2) && (temprt.charAt(0) == 'R' || temprt.charAt(0) == 'r') && Integer.parseInt(temprt.substring(1)) < 32) && Integer.parseInt(tempoffset) < 65536) {

                                    //Rendo visibile layout, e stampo tipo di istruzione e stampo i vari cosi

                                    result.setVisibility(VISIBLE);
                                    rtype.setVisibility(GONE);
                                    jtype.setVisibility(GONE);
                                    itype.setVisibility(VISIBLE);

                                    instruction.setText("Type I Instruction");
                                    //metti in string.xml

                                    //stampo valori nelle varie aree

                                    opcodeI.setText("000100");
                                    rsI.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprs.substring(1))));
                                    rtI.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprt.substring(1))));
                                    offset.setText(Calcolatore.addDigitsMIPS(Calcolatore.binToDecMIPS(Integer.parseInt(tempoffset)), 26));

                                    temp = opcodeI.getText().toString() + rsI.getText().toString() + rtI.getText().toString() + offset.getText().toString();

                                    //imposto la stringa per essere convertita in esadec

                                    hex.setText(Calcolatore.binToHexMIPS(temp));


                                } else {
                                    result.setVisibility(GONE);
                                    toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                    instruction.setText("");
                                    hex.setText("");
                                    if (text.getText().toString().length() > 12)
                                        toast.show();
                                }
                            } catch (IndexOutOfBoundsException e) {
                                Log.d("", e.toString());
                                hex.setText("");
                                instruction.setText("");
                                result.setVisibility(GONE);
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                            } catch (NumberFormatException e) {
                                Log.d("", e.toString());
                                hex.setText("");
                                instruction.setText("");
                                result.setVisibility(GONE);
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                            }


                            break;

                        case "bne":

                            try {

                                temprs = tempArray[1];
                                temprt = tempArray[2];
                                tempoffset = tempArray[3];

                                if (((temprs.length() == 3 || temprs.length() == 2) && (temprs.charAt(0) == 'R' || temprs.charAt(0) == 'r') && Integer.parseInt(temprs.substring(1)) < 32) && ((temprt.length() == 3 || temprt.length() == 2) && (temprt.charAt(0) == 'R' || temprt.charAt(0) == 'r') && Integer.parseInt(temprt.substring(1)) < 32) && Integer.parseInt(tempoffset) < 65536) {

                                    //Rendo visibile layout, e stampo tipo di istruzione e stampo i vari cosi

                                    result.setVisibility(VISIBLE);
                                    rtype.setVisibility(GONE);
                                    jtype.setVisibility(GONE);
                                    itype.setVisibility(VISIBLE);

                                    instruction.setText("Type I Instruction");
                                    //metti in string.xml

                                    //stampo valori nelle varie aree

                                    opcodeI.setText("000101");
                                    rsI.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprs.substring(1))));
                                    rtI.setText(Calcolatore.binToDecMIPS(Integer.parseInt(temprt.substring(1))));
                                    offset.setText(Calcolatore.addDigitsMIPS(Calcolatore.binToDecMIPS(Integer.parseInt(tempoffset)), 16));

                                    temp = opcodeI.getText().toString() + rsI.getText().toString() + rtI.getText().toString() + offset.getText().toString();

                                    //imposto la stringa per essere convertita in esadec

                                    hex.setText(Calcolatore.binToHexMIPS(temp));


                                } else {
                                    result.setVisibility(GONE);
                                    toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                    hex.setText("");
                                    instruction.setText("");
                                    if (text.getText().toString().length() > 12)
                                        toast.show();
                                }
                            } catch (IndexOutOfBoundsException e) {
                                Log.d("", e.toString());
                                hex.setText("");
                                instruction.setText("");
                                result.setVisibility(GONE);
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                            } catch (NumberFormatException e) {
                                Log.d("", e.toString());
                                hex.setText("");
                                instruction.setText("");
                                result.setVisibility(GONE);
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                if (text.getText().toString().length() > 12)
                                    toast.show();
                            }


                            break;

                        case "j":

                            try {

                                tempoffset = tempArray[1];

                                if (Integer.parseInt(tempoffset) < 67108864) {

                                    //Rendo visibile layout, e stampo tipo di istruzione e stampo i vari cosi

                                    result.setVisibility(VISIBLE);
                                    rtype.setVisibility(GONE);
                                    itype.setVisibility(GONE);
                                    jtype.setVisibility(VISIBLE);

                                    instruction.setText("Type J Instruction");
                                    //metti in string.xml

                                    //stampo valori nelle varie aree

                                    opcodeJ.setText("000010");
                                    target.setText(Calcolatore.addDigitsMIPS(Calcolatore.binToDecMIPS(Integer.parseInt(tempoffset)), 26));

                                    temp = opcodeJ.getText().toString() + target.getText().toString();

                                    //imposto la stringa per essere convertita in esadec

                                    hex.setText(Calcolatore.binToHexMIPS(temp));


                                } else {
                                    Log.d("", "carlo");
                                    result.setVisibility(GONE);
                                    toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                    hex.setText("");
                                    toast.show();
                                }
                            } catch (IndexOutOfBoundsException e) {
                                Log.d("", e.toString());
                                hex.setText("");
                                result.setVisibility(GONE);
                                instruction.setText("");
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                toast.show();
                            } catch (NumberFormatException e) {
                                Log.d("", e.toString());
                                hex.setText("");
                                result.setVisibility(GONE);
                                instruction.setText("");
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                toast.show();
                            }


                            break;

                        case "jal":

                            try {

                                tempoffset = tempArray[1];

                                if (Integer.parseInt(tempoffset) < 67108864) {

                                    //Rendo visibile layout, e stampo tipo di istruzione e stampo i vari cosi

                                    result.setVisibility(VISIBLE);
                                    rtype.setVisibility(GONE);
                                    itype.setVisibility(GONE);
                                    jtype.setVisibility(VISIBLE);

                                    instruction.setText("Type J Instruction");
                                    //metti in string.xml

                                    //stampo valori nelle varie aree

                                    opcodeJ.setText("000011");
                                    target.setText(Calcolatore.addDigitsMIPS(Calcolatore.binToDecMIPS(Integer.parseInt(tempoffset)), 26));

                                    temp = opcodeJ.getText().toString() + target.getText().toString();

                                    //imposto la stringa per essere convertita in esadec

                                    hex.setText(Calcolatore.binToHexMIPS(temp));


                                } else {
                                    result.setVisibility(GONE);
                                    toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                    hex.setText("");
                                    instruction.setText("");
                                    toast.show();
                                }
                            } catch (IndexOutOfBoundsException e) {
                                Log.d("", e.toString());
                                hex.setText("");
                                result.setVisibility(GONE);
                                instruction.setText("");
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                toast.show();
                            } catch (NumberFormatException e) {
                                Log.d("", e.toString());
                                hex.setText("");
                                result.setVisibility(GONE);
                                instruction.setText("");
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                toast.show();
                            }


                            break;


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

                if (hex.isFocused()) {

                    if (hex.getText().toString().length() == 8) {

                        temp = hex.getText().toString();

                        //Converto hex in binario e poi prendo singolarmente i vari bit

                        temphex = Calcolatore.binToDec(Integer.parseInt(Calcolatore.hexToDec("" + temp.charAt(0)))) + Calcolatore.binToDec(Integer.parseInt(Calcolatore.hexToDec("" + temp.charAt(1)))) + Calcolatore.binToDec(Integer.parseInt(Calcolatore.hexToDec("" + temp.charAt(2)))) + Calcolatore.binToDec(Integer.parseInt(Calcolatore.hexToDec("" + temp.charAt(3)))) + Calcolatore.binToDec(Integer.parseInt(Calcolatore.hexToDec("" + temp.charAt(4)))) + Calcolatore.binToDec(Integer.parseInt(Calcolatore.hexToDec("" + temp.charAt(5)))) + Calcolatore.binToDec(Integer.parseInt(Calcolatore.hexToDec("" + temp.charAt(6)))) + Calcolatore.binToDec(Integer.parseInt(Calcolatore.hexToDec("" + temp.charAt(7))));

                        tempopcode = temphex.substring(0, 6);


                        switch (tempopcode) {

                            case "000000":
                                //Istruzione di tipo R

                                temprs = temphex.substring(6, 11);
                                temprt = temphex.substring(11, 16);
                                temprd = temphex.substring(16, 21);
                                tempshamt = temphex.substring(21, 26);
                                tempfunct = temphex.substring(26, 32);

                                //Stampo informazioni generali

                                result.setVisibility(VISIBLE);
                                itype.setVisibility(GONE);
                                jtype.setVisibility(GONE);
                                rtype.setVisibility(VISIBLE);

                                instruction.setText("Type R Instruction");

                                opcodeR.setText(tempopcode);
                                rsR.setText(temprs);
                                rtR.setText(temprt);
                                rdR.setText(temprd);
                                shamt.setText(tempshamt);

                                switch (tempfunct) {

                                    case "100000":

                                        text.setText("add R" + Calcolatore.binToDec(temprd) + " R" + Calcolatore.binToDec(temprs) + " R" + Calcolatore.binToDec(temprt));
                                        funct.setText(tempfunct);

                                        break;

                                    case "100010":

                                        text.setText("sub R" + Calcolatore.binToDec(temprd) + " R" + Calcolatore.binToDec(temprs) + " R" + Calcolatore.binToDec(temprt));
                                        funct.setText(tempfunct);

                                        break;

                                    case "100101":

                                        text.setText("or R" + Calcolatore.binToDec(temprd) + " R" + Calcolatore.binToDec(temprs) + " R" + Calcolatore.binToDec(temprt));
                                        funct.setText(tempfunct);

                                        break;

                                    case "100100":

                                        text.setText("and R" + Calcolatore.binToDec(temprd) + " R" + Calcolatore.binToDec(temprs) + " R" + Calcolatore.binToDec(temprt));
                                        funct.setText(tempfunct);

                                        break;

                                    case "101010":

                                        text.setText("slt R" + Calcolatore.binToDec(temprd) + " R" + Calcolatore.binToDec(temprs) + " R" + Calcolatore.binToDec(temprt));
                                        funct.setText(tempfunct);

                                        break;

                                    case "100011":

                                        text.setText("jr R" + Calcolatore.binToDec(temprs));
                                        funct.setText(tempfunct);

                                        break;

                                    default:

                                        text.setText("");
                                        result.setVisibility(GONE);
                                        instruction.setText("");
                                        toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                        toast.show();

                                        break;

                                }


                                break;


                            case "000100":
                                //BEQ

                                temprs = temphex.substring(6, 11);
                                temprt = temphex.substring(11, 16);
                                tempoffset = temphex.substring(16, 32);

                                //Stampo informazioni generali

                                result.setVisibility(VISIBLE);
                                rtype.setVisibility(GONE);
                                jtype.setVisibility(GONE);
                                itype.setVisibility(VISIBLE);

                                instruction.setText("Type I Instruction");

                                opcodeI.setText(tempopcode);
                                rsI.setText(temprs);
                                rtI.setText(temprt);
                                offset.setText(tempoffset);

                                text.setText("beq R" + Calcolatore.binToDec(temprs) + " R" + Calcolatore.binToDec(temprt) + " " + Calcolatore.binToDec(tempoffset));


                                break;

                            case "100011":
                                //Load word

                                temprs = temphex.substring(6, 11);
                                temprt = temphex.substring(11, 16);
                                tempoffset = temphex.substring(16, 32);

                                //Stampo informazioni generali

                                result.setVisibility(VISIBLE);
                                rtype.setVisibility(GONE);
                                jtype.setVisibility(GONE);
                                itype.setVisibility(VISIBLE);

                                instruction.setText("Type I Instruction");

                                opcodeI.setText(tempopcode);
                                rsI.setText(temprs);
                                rtI.setText(temprt);
                                offset.setText(tempoffset);

                                text.setText("lw R" + Calcolatore.binToDec(temprt) + " " + Calcolatore.binToDec(tempoffset) + "(R" + Calcolatore.binToDec(temprs) + ")");

                                break;


                            case "101011":
                                //Store word

                                temprs = temphex.substring(6, 11);
                                temprt = temphex.substring(11, 16);
                                tempoffset = temphex.substring(16, 32);

                                //Stampo informazioni generali

                                result.setVisibility(VISIBLE);
                                rtype.setVisibility(GONE);
                                jtype.setVisibility(GONE);
                                itype.setVisibility(VISIBLE);

                                instruction.setText("Type I Instruction");

                                opcodeI.setText(tempopcode);
                                rsI.setText(temprs);
                                rtI.setText(temprt);
                                offset.setText(tempoffset);

                                text.setText("sw R" + Calcolatore.binToDec(temprt) + " " + Calcolatore.binToDec(tempoffset) + "(R" + Calcolatore.binToDec(temprs) + ")");

                                break;

                            case "000101":

                                temprs = temphex.substring(6, 10);
                                temprt = temphex.substring(11, 15);
                                tempoffset = temphex.substring(16, 31);

                                //Stampo informazioni generali

                                result.setVisibility(VISIBLE);
                                rtype.setVisibility(GONE);
                                jtype.setVisibility(GONE);
                                itype.setVisibility(VISIBLE);

                                instruction.setText("Type I Instruction");

                                opcodeI.setText(tempopcode);
                                rsI.setText(temprs);
                                rtI.setText(temprt);
                                offset.setText(tempoffset);

                                text.setText("bne R" + Calcolatore.binToDec(temprs) + " R" + Calcolatore.binToDec(temprt) + " " + Calcolatore.binToDec(tempoffset));

                                break;

                            case "000010":
                                //J

                                tempoffset = temphex.substring(6, 31);

                                //Stampo informazioni generali

                                result.setVisibility(VISIBLE);
                                rtype.setVisibility(GONE);
                                itype.setVisibility(GONE);
                                jtype.setVisibility(VISIBLE);

                                instruction.setText("Type J Instruction");

                                opcodeJ.setText(tempopcode);
                                target.setText(tempoffset);

                                text.setText("j " + Calcolatore.binToDec(tempoffset));

                                break;


                            case "000011":
                                //jal

                                tempoffset = temphex.substring(6, 31);

                                //Stampo informazioni generali

                                result.setVisibility(VISIBLE);
                                rtype.setVisibility(GONE);
                                itype.setVisibility(GONE);
                                jtype.setVisibility(VISIBLE);

                                instruction.setText("Type J Instruction");

                                opcodeJ.setText(tempopcode);
                                target.setText(tempoffset);

                                text.setText("jal " + Calcolatore.binToDec(tempoffset));

                                break;

                            default:

                                text.setText("");
                                result.setVisibility(GONE);
                                instruction.setText("");
                                toast = Toast.makeText(getActivity(), R.string.wrong, Toast.LENGTH_SHORT);
                                toast.show();

                                break;


                        }

                    }

                }

            }


        });


        return view;


    }


    @Override
    public void onResume() {

        mTracker.setScreenName("Image~" + "MipsCalculator");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());


        super.onResume();

    }

}