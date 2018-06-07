package com.sperotti.alessandro.iocalc.utils;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Alessandro on 15/11/2016.
 */

public class Calcolatore {

    public Calcolatore(){

    }

    public static String binaryAddition(String s1, String s2) {
        if (s1 == null || s2 == null) return "";
        int first = s1.length() - 1;
        int second = s2.length() - 1;
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        while (first >= 0 || second >= 0) {
            int sum = carry;
            if (first >= 0) {
                sum += s1.charAt(first) - '0';
                first--;
            }
            if (second >= 0) {
                sum += s2.charAt(second) - '0';
                second--;
            }
            carry = sum >> 1;
            sum = sum & 1;
            sb.append(sum == 0 ? '0' : '1');
        }
        if (carry > 0)
            sb.append('1');

        sb.reverse();
        return String.valueOf(sb);
    }

    public static String binToDec(String binary){

        int i,j=0;
        int dec=0;
        String res="";

        String [] vet = binary.split("");



        for(i=(vet.length - 1);i>0;i--){

            if(vet[i].isEmpty())
                return "";

            dec+= Integer.parseInt(vet[i])*Math.pow(2,j);
            j++;
        }

        res+=dec;

        return res;
    }

    public static String binToDec(int decimal){

        String res="";
        ArrayList<String> vet = new ArrayList<String>();
        int i=0;

        if(decimal==0){
            return "0000";
        }

        do{

            if(decimal%2==1){
                vet.add("1");
            }

            else{
                vet.add("0");
            }

            decimal/=2;

        }while(decimal>0);

       while(vet.size()%4 != 0){
               vet.add("0");
        }


        for(i=(vet.size()-1);i>-1;i--){
                res+=vet.get(i);
        }

        return res;
    }

    public static String binToDecMIPS(int decimal){

        String res="";
        ArrayList<String> vet = new ArrayList<String>();
        int i=0;

        do{

            if(decimal%2==1){
                vet.add("1");
            }

            else{
                vet.add("0");
            }

            decimal/=2;

        }while(decimal>0);

        while(vet.size()%5 != 0){
            vet.add("0");
        }

        for(i=(vet.size()-1);i>-1;i--){
            res+=vet.get(i);
        }

        return res;
    }

    public static String formatBinary(String binary){

        String [] vet = binary.split("");
        String res = "";
        int i = 0;

        for(i=0;i<vet.length;i++){

            if((i)%4==0 && (i!=0)){
                res+=vet[i]+" ";
            }
            else{
                res+=vet[i];
            }
        }

        return res;

    }

    public static String addDigits(String binary, int sign){

        String res="";
        String [] vet = binary.split("");


        int i;

        for(i=0;i<vet.length && (vet.length+res.length())%4!=0;i++){
            res+=sign;
        }

        Log.d("",res);
        Log.d("",binary);

        return sign+res+binary;
    }

    public static String addDigitsMIPS(String binary,int num){

        if(binary.length()==num+4){
            return binary.substring(4);
        }

        int i;
        String temp="";

        for(i=0;i<(num-binary.length());i++){

            temp=temp+"0";

        }

        return temp+binary;

    }

    public static String hexToDec(String hex){

        Log.d("Ciao, vengo da hextodec",hex);

        int i,j=0,num=0;
        int dec=0;
        String res="";

        if(hex.equals("0")){
            return "0";
        }

        String [] vet = hex.split("");



        for(i=(vet.length - 1);i>0;i--){

            try{
                num=Integer.parseInt(vet[i]);
            }

            catch(NumberFormatException e){

                switch(vet[i]){
                    case "A":
                        num=10;
                        break;

                    case "B":
                        num=11;
                        break;

                    case "C":
                        num=12;
                        break;

                    case "D":
                        num=13;
                        break;

                    case "E":
                        num=14;
                        break;

                    case "F":
                        num=15;
                        break;
                }

            }

                dec+= num*Math.pow(16,j);
                j++;

        }

        res+=dec;

        return res;



    }

    public static String hexToDec(int decimal){

        return Integer.toHexString(decimal);

    }

    public static String octToDec(int decimal){

        return Integer.toOctalString(decimal);

    }

    public static String octToDec(String octal){

        int i,j=0;
        int dec=0;
        String res="";

        String [] vet = octal.split("");

        for(i=(vet.length - 1);i>0;i--){
            dec+= Integer.parseInt(vet[i])*Math.pow(8,j);
            j++;
        }

        res+=dec;

        return res;

    }

    public static String twosComplement(String binary){

        int i;

        String [] temp = binary.split("");
        String res="";

        for(i=0;i<temp.length;i++){

            switch(temp[i]){
                case "1":
                    temp[i]="0";
                    break;
                case "0":
                    temp[i]="1";
                    break;
            }

        }

        for(i=0;i<temp.length;i++){

            res+=temp[i];

        }


        if(res.charAt(0)=='0' && !res.matches("0+")){
            res = addDigits(res,1);
        }

        return Calcolatore.binaryAddition(res,"1");

    }

    public static String onesComplement(String binary){

        int i;

        String [] temp = binary.split("");
        String res="";

        for(i=0;i<temp.length;i++){

            switch(temp[i]){
                case "1":
                    temp[i]="0";
                    break;
                case "0":
                    temp[i]="1";
                    break;
            }

        }

        for(i=0;i<temp.length;i++){

            res+=temp[i];

        }

        return res;



    }

    public static String binToHexMIPS(String binary){

        String res;
        String [] vet = formatBinary(binary).split(" ");

        res=hexToDec(Integer.parseInt(binToDec(vet[0])))+hexToDec(Integer.parseInt(binToDec(vet[1])))+hexToDec(Integer.parseInt(binToDec(vet[2])))+hexToDec(Integer.parseInt(binToDec(vet[3])))+hexToDec(Integer.parseInt(binToDec(vet[4])))+hexToDec(Integer.parseInt(binToDec(vet[5])))+hexToDec(Integer.parseInt(binToDec(vet[6])))+hexToDec(Integer.parseInt(binToDec(vet[7])));

    return res.toUpperCase();

    }

    public static String hexToStr(String hex, String charset){

            while(hex.length()%2!=0){
                hex = String.format("0%s", hex);
            }

            int l = hex.length();
            byte[] data = new byte[l/2];
            for (int i = 0; i < l; i += 2) {
                data[i/2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                        + Character.digit(hex.charAt(i+1), 16));
            }
        try {
            return new String(data, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}

