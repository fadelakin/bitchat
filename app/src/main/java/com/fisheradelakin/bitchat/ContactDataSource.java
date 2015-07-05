package com.fisheradelakin.bitchat;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fisher on 7/4/15.
 */
public class ContactDataSource implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;
    private Listener mListener;

    ContactDataSource(Context context, Listener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                mContext,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone._ID,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<String> numbers = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String phoneNumber = cursor.getString(1);
            phoneNumber = phoneNumber.replaceAll("-", "");
            phoneNumber = phoneNumber.replaceAll(" ", "");
            phoneNumber = phoneNumber.replaceAll("\\(", "");
            phoneNumber = phoneNumber.replaceAll("\\)", "");
            phoneNumber = phoneNumber.replaceAll("\\+", "");
            numbers.add(phoneNumber);
            cursor.moveToNext();
        }

        // fetch contacts who are also parse users
        // aka people you know who are in the database
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContainedIn("username", numbers);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                if(e == null) {
                    ArrayList<Contact> contacts = new ArrayList<Contact>();
                    for(ParseUser user : list) {
                        Contact contact = new Contact();
                        contact.setName((String) user.get("name"));
                        contact.setPhoneNumber(user.getUsername());
                        contacts.add(contact);
                    }

                    if(mListener != null) {
                        mListener.onFetchedContacts(contacts);
                    }
                } else {
                }
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public interface Listener {
        void onFetchedContacts(ArrayList<Contact> contacts);
    }

}
