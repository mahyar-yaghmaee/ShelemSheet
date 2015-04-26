package com.android.mahyar.shelemsheet;

import android.widget.EditText;

/**
 * Created by mahyar on 4/26/15.
 */
public class choosePlayerReset {
    //constructor
    public choosePlayerReset(EditText p1, EditText p2,EditText p3, EditText p4, EditText handpoint, EditText total){
        p1.setText("");
        p2.setText("");
        p3.setText("");
        p4.setText("");
        handpoint.setText("165");
        total.setText("1165");
    }
}
