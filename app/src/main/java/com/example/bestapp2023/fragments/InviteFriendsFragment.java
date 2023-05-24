package com.example.bestapp2023.fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.bestapp2023.R;

import android.provider.ContactsContract;

public class InviteFriendsFragment extends LogFragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    ImageButton home;
    ImageButton profile;
    ImageButton invitefriend;

    // Define global mutable variables
    ListView contactsList;
    long contactId;
    String contactKey;
    Uri contactUri;
    private SimpleCursorAdapter cursorAdapter;

    private static final int CONTACT_ID_INDEX = 0;
    private static final int CONTACT_KEY_INDEX = 1;

    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION = {
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    @SuppressLint("InlinedApi")
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";

    private String searchString = "";
    private String[] selectionArgs;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initArguments();

        // Initializes the loader
        getLoaderManager().initLoader(0, null, this);
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View externalView = inflater.inflate(R.layout.fragment_invitefriends, container, false);
        contactsList = externalView.findViewById(android.R.id.list);


        // Perform a query to retrieve the contacts
        CursorLoader cursorLoader = new CursorLoader(
                getActivity(),
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PROJECTION,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        );

        Cursor cursor = cursorLoader.loadInBackground();

        // Create a cursor adapter with the retrieved contacts cursor
        cursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.contacts_list_item,
                cursor,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER},
                new int[]{R.id.contact_name, R.id.contact_number},
                0
        );

        // Sets the adapter for the ListView
        contactsList.setAdapter(cursorAdapter);
        // Set the item click listener to be the current fragment
        contactsList.setOnItemClickListener(this);

        return externalView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
        cursor.moveToPosition(position);
        contactId = cursor.getLong(CONTACT_ID_INDEX);
        contactKey = cursor.getString(CONTACT_KEY_INDEX);
        contactUri = ContactsContract.Contacts.getLookupUri(contactId, contactKey);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        selectionArgs = new String[]{"%" + searchString + "%"};

        return new CursorLoader(
                getActivity(),
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PROJECTION,
                SELECTION,
                selectionArgs,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY + " ASC"

        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
