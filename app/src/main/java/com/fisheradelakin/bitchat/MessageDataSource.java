package com.fisheradelakin.bitchat;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

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

    public static void fetchMessages(String sender, String recipient, final Listener listener) {
        ParseQuery<ParseObject> querySent = ParseQuery.getQuery("Message");
        querySent.whereEqualTo("sender", sender);
        querySent.whereEqualTo("recipient", recipient);

        ParseQuery<ParseObject> queryRecieved = ParseQuery.getQuery("Message");
        queryRecieved.whereEqualTo("sender", recipient);
        queryRecieved.whereEqualTo("recipient", sender);

        List<ParseQuery<ParseObject>> queries = new ArrayList<>();
        queries.add(querySent);
        queries.add(queryRecieved);

        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
        mainQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                ArrayList<Message> messages = new ArrayList<Message>();
                for(ParseObject object : list) {
                    Message message = new Message((String) object.get("text"), (String) object.get("sender"));
                    messages.add(message);
                }
                listener.onFetchedMessages(messages);
            }
        });
    }

    public interface Listener {
        void onFetchedMessages(ArrayList<Message> messages);
    }
}
