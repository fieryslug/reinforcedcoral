package com.fieryslug.reinforcedcoral.web;

import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
//import sun.misc.Request;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements HttpHandler {

    public FrameCoral frame;

    public RequestHandler(FrameCoral frame) {

        this.frame = frame;

    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String response = "Hi! Dude";
                String query = httpExchange.getRequestURI().getQuery();
                System.out.println(query);
                Map<String, String> queryMap = data2Dic(query);
                try {
                    httpExchange.sendResponseHeaders(200, 0);
                    OutputStream outputStream = httpExchange.getResponseBody();
                    outputStream.write(response.getBytes());
                    outputStream.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }


                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String button = queryMap.get("button");
                        RequestHandler.this.frame.react(queryMap);
                    }
                });

            }
        });
        thread.start();
    }

    public static Map<String,String> data2Dic(String formData ) {
        Map<String,String> result = new HashMap<>();
        if(formData== null || formData.trim().length() == 0) {
            return result;
        }
        final String[] items = formData.split("&");
        Arrays.stream(items).forEach(item ->{
            final String[] keyAndVal = item.split("=");
            if( keyAndVal.length == 2) {
                try{
                    final String key = URLDecoder.decode( keyAndVal[0],"utf8");
                    final String val = URLDecoder.decode( keyAndVal[1],"utf8");
                    result.put(key,val);
                }catch (UnsupportedEncodingException e) {}
            }
        });
        return result;
    }
}
