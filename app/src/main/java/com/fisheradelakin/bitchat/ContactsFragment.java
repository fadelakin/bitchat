package com.fisheradelakin.bitchat;

import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContactsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "ContactsFragment";

    private Listener mListener;

    public ContactsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);

        ListView listView = (ListView) v.findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        String[] columns  = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
        int[] ids = {R.id.number, R.id.name};
        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone._ID,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME}, null, null, null);

        listView.setAdapter(new SimpleCursorAdapter(
                getActivity(),
                R.layout.contact_list_item,
                cursor,
                columns,
                ids,
                0));

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = ((SimpleCursorAdapter) parent.getAdapter()).getCursor();
        cursor.moveToPosition(position);

        Log.d(TAG, "Phone number is " + cursor.getString(1));
    }

    public interface Listener {
    }
}
