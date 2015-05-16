package com.android.mahyar.shelemsheet;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by mahyar on 4/26/15.
 */
public class choosePlayerModel {

    public void Reset(EditText p1, EditText p2, EditText p3, EditText p4, EditText handpoint, EditText total){
        p1.setText("");
        p2.setText("");
        p3.setText("");
        p4.setText("");
        handpoint.setText("165");
        total.setText("1165");
    }

    public boolean isInputEmpty(ViewGroup group){
        for (int i = 0, count = group.getChildCount(); i < count; ++i){
            View view = group.getChildAt(i);
            if (view instanceof EditText && ((EditText) view).getText().toString().isEmpty())
                return true;
        }
        return false;
    }

}
