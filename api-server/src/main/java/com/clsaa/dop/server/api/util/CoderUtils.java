package com.clsaa.dop.server.api.util;


import java.io.UnsupportedEncodingException;
import java.util.Base64;


public class CoderUtils {

    public static String base64Encode(String text){
        final Base64.Encoder encoder = Base64.getEncoder();
        try{
            byte[] textByte = text.getBytes("UTF-8");
            return encoder.encodeToString(textByte);
        }catch (UnsupportedEncodingException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static String base64Decode(String base64Text){
        final Base64.Decoder decoder = Base64.getDecoder();
        try{
            return new String(decoder.decode(base64Text),"UTF-8");
        }catch (UnsupportedEncodingException ex){
            ex.printStackTrace();
            return null;
        }
    }


}
