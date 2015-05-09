package com.android.mahyar.shelemsheet;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by mahyar on 5/8/15.
 */
public class mainPage extends Activity{


    private Button mSubmitScoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scores);

        LayoutInflater lay_inflat = (LayoutInflater)getLayoutInflater();
        View v = lay_inflat.inflate(R.layout.sublayout, null);
        RelativeLayout scroll_layout = (RelativeLayout) findViewById(R.id.scrollable_layout);
        scroll_layout.addView(v);


     //   LayoutInflater vi = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     //   View v = vi.inflate(R.layout.activity_main_scores, null);


     //   TextView textView = (TextView) v.findViewById(R.id.scrollable_layout);
      //  textView.setText("your text");
       // ViewGroup insertPoint = (ViewGroup) findViewById(R.id.scrollable_layout);
       // insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
    }
}
