package com.application.utils;

import com.message.Message;

import java.time.LocalTime;

public class Messages {

    public static final String WELCOME_MESSAGE = "Welcome to Java chat";
    public static final String ANNOUNCE_NEW_USER = "%s just entered the room";
    public static final String ANNOUNCE_LEAVER = "%s just left the room. We'll miss you";

    public static String personalize(String message, String... args){
        return String.format(message, args);
    }

    public static Message objectify(String content/*, String... args*/){
        return new Message(content, "Duke Bot"/*, LocalTime.now().toString(), args*/);
    }

    public static Message objectify(String content, String user/*, String... args*/){
        return new Message(content, user/*, LocalTime.now().toString(), args*/);
    }

    public static Message objectify(String stock, String side, String exch, String price, String qty, String client/*, String tm*/){
        return objectify(stock, side, exch, price, qty, client, LocalTime.now().toString());
    }

    //String stock, String side, String exch, float price, int qty, String client, String received
    public static Message objectify(String stock, String side, String exch, String price, String qty, String client, String received){
        return new Message(stock, side, exch, price, qty, client, received);
    }
}
