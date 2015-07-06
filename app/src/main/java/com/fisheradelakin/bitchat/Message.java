package com.fisheradelakin.bitchat;

/**
 * Created by Fisher on 7/5/15.
 */
public class Message {

    private String mText;
    private String mSender;

    Message(String text, String sender) {
        mText = text;
        mSender = sender;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getSender() {
        return mSender;
    }

    public void setSender(String sender) {
        mSender = sender;
    }
}
