package com.openclassrooms.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.database.RealEstateDataBase;
import com.openclassrooms.realestatemanager.provider.RealEstateContentProvider;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;


public class RealEstateContentProviderText {

    // DATA SET FOR TEST
    private static long USER_ID = 1;
    // FOR DATA
    private ContentResolver mContentResolver;
    private Context mContext;

    @Before
    public void setUp() {
       mContext= InstrumentationRegistry.getInstrumentation().getContext();
        Room.inMemoryDatabaseBuilder(mContext,
                RealEstateDataBase.class)
                .allowMainThreadQueries()
                .build();
        mContentResolver = mContext.getContentResolver();
    }

    @Test
    public void getItemsWhenNoItemInserted() {
        final Cursor cursor = mContext.getContentResolver().query(ContentUris.withAppendedId(RealEstateContentProvider.URI_REAlESTATE, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());

        if (cursor != null) {
            assertThat(cursor.getCount(), notNullValue());
            cursor.close();
        }
    }

    @Test
    public void insertAndGetItem() {
        // BEFORE : Adding demo item
        final Uri userUri = mContext.getContentResolver().insert(RealEstateContentProvider.URI_REAlESTATE, generateRealEstate());
        assertThat(userUri,notNullValue());
        // TEST
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(RealEstateContentProvider.URI_REAlESTATE, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        if (cursor != null) {
            assertThat(cursor.moveToFirst(), is(true));
            assertThat(cursor.getString(cursor.getColumnIndexOrThrow("type")), is("HOUSE"));
        }
    }

    // ---

    private ContentValues generateRealEstate() {
        final ContentValues values = new ContentValues();
        values.put("type", "Flat");
        values.put("price", "12000000");
        values.put("userId", "1");
        return values;
    }
}
