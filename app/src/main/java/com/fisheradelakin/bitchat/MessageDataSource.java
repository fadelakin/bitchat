package com.fisheradelakin.bitchat;

import com.parse.ParseObject;

/**
 * Created by Fisher on 7/5/15.
 */
public class MessageDataSource {

    public static void sendMessage(String sender, String recipient, String text) {
        ParseObject message = new ParseObject("Message");
        message.put("sender", sender);
        message.put("recipient", recipient);
        message.put("text", text);
        message.saveInBackground();
    }
}
