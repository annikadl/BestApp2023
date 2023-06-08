package com.example.bestapp2023.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.bestapp2023.R;

import android.provider.ContactsContract;


public class InviteFriendsFragment extends LogFragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    //VARIABILI GLOBALI
    ListView contactsList;
    long contactId;
    String contactKey;
    Uri contactUri;
    private SimpleCursorAdapter cursorAdapter;

    private static final int CONTACT_ID_INDEX = 0;
    private static final int CONTACT_KEY_INDEX = 1;

    //DEFINISCO LE QUERY DA PASSARE AL CURSORE
    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION = {
            ContactsContract.RawContacts._ID,
            ContactsContract.RawContacts.ACCOUNT_TYPE,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Photo.CONTACT_ID
    };

    String selectionFields =  ContactsContract.RawContacts.ACCOUNT_TYPE + " = ?";
    String[] selectionArgs = new String[]{"com.whatsapp"};




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


        //QUERY PER PRENDERE I CONTATTI
        CursorLoader cursorLoader = new CursorLoader(
                getActivity(),
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PROJECTION,
                selectionFields,
                selectionArgs,
                ContactsContract.Contacts.DISPLAY_NAME
        );

        //EFFETTUO QUERY IN MANIERA ASINCRONA
        Cursor cursor = cursorLoader.loadInBackground();


        //CREO UN ADAPTER DEL CURSORE PER INSERIRE I DATI NELLA LIST VIEW
        cursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.contacts_list_item,
                cursor,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER},
                new int[]{R.id.contact_name, R.id.contact_number},
                0
        );

        // ASSEGNO I VALORI PRESI DAL CURSORE ALLA LISTA
        contactsList.setAdapter(cursorAdapter);
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

        //PRENDO ID ASSOCIATO AL NUMERO E CONVERTO IN STIRNGA IL NUMERO

        int phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        String phoneNumber = cursor.getString(phoneNumberIndex);

        //QUANDO CLICCO INVIO UN MESSAGGIO AL CONTATTO DI INTERESSE

        Uri sms_uri = Uri.parse("smsto:"+phoneNumber);
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        sms_intent.putExtra("sms_body", "Vuoi andare fuori a cena? Scarica la nostra app e " +
                "riceverai il 15% di sconto in tutti i nostri ristoranti!");
        startActivity(sms_intent);

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        return new CursorLoader(
                getActivity(),
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PROJECTION,
                selectionFields,
                selectionArgs,
                ContactsContract.Contacts.DISPLAY_NAME


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