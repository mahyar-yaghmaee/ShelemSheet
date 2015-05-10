package com.android.mahyar.shelemsheet;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup;
/**
 * Created by mahyar on 5/8/15.
 */
public class mainPage extends Activity{


    private Button mSubmitScoreButton;
    private LinearLayout mLayout;
    private EditText mEditText;
    RelativeLayout.LayoutParams layoutParams;
    static int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scores);

    }

}
