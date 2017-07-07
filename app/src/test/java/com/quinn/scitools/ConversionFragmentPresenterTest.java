package com.quinn.scitools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import fragments.tabs.ConversionFragment;
import pojo.database.ConversionDBHelper;
import presenter.ConversionFragmentPresenter;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConversionFragmentPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    Context fakeContext;

    @Mock
    ConversionDBHelper fakeHelper;

    @Mock
    ConversionFragment view;

    @Mock
    SQLiteOpenHelper openHelper;

    @Mock
    SQLiteDatabase db;

    private ConversionFragmentPresenter presenter;

    @Before
    public void setUp() {
        presenter = new ConversionFragmentPresenter(fakeContext, view);
        fakeHelper.insertTypes("One");
    }


    @Test
    public void testOne() {
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("One");
        when(fakeHelper.getTypes()).thenReturn(typeList);

        presenter.assigningTypeSpinnerArray();

        verify(view).fillTypeSpinner(typeList);
    }
}
