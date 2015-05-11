package com.android.mahyar.shelemsheet;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;



/**
 * Created by mahyar on 5/9/15.
 */
public class mainPageModel {

    int colNumber = 0;
    int MAX_ROW_NUM = 30;
    public boolean isInputEmpty(ViewGroup group){
        for (int i = 0, count = group.getChildCount(); i < count; ++i){
            View view = group.getChildAt(i);
            if (view instanceof EditText && ((EditText) view).getText().toString().isEmpty())
                return true;
        }
        return false;
    }


}
