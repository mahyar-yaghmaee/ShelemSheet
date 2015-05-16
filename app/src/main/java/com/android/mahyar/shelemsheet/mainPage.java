package com.android.mahyar.shelemsheet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mahyar on 5/8/15.
 */
public class mainPage extends Activity{


    private Button mSubmitScoreButton;
    int colNumber = 0;
    int MAX_ROW_NUM = 30;

    boolean isScoreTime = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scores);
        //set font
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "DroidSerif-Regular.ttf");
        ViewGroup group = (ViewGroup)findViewById(R.id.mainPage);
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof TextView)
                ((TextView) view).setTypeface(myTypeface);
        }
        //import from other Activity
        Intent intent = getIntent();
        String player1_name = intent.getStringExtra("Player1");
        String player2_name = intent.getStringExtra("Player2");
        String player3_name = intent.getStringExtra("Player3");
        String player4_name = intent.getStringExtra("Player4");
        //set names in new activity
        TextView TeamANames = (TextView) findViewById(R.id.teamA_names);
        TeamANames.setText(player1_name+"/"+player2_name);
        TextView TeamBNames = (TextView) findViewById(R.id.teamB_names);
        TeamBNames.setText(player3_name+"/"+player4_name);
        //import points
        final int maxPoints = intent.getIntExtra("maxPoints",0);
        final int handPont = intent.getIntExtra("handPoint",0);




        mSubmitScoreButton = (Button)findViewById(R.id.submit_scores_button);
        // call time
        if (!isScoreTime)
            mSubmitScoreButton.setText("Call");
        else
            mSubmitScoreButton.setText("Submit");


        mSubmitScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isScoreTime){//call time
                    EditText callA = (EditText) findViewById(R.id.editTextTeamA);
                    EditText callB = (EditText) findViewById(R.id.editTextTeamB);
                    //Use XOR, one of them should be filled to call
                    if (!(callA.getText().toString().isEmpty() ^ callB.getText().toString().isEmpty())){
                        Toast.makeText(mainPage.this, R.string.callTextToast, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        int callA_int;
                        int callB_int;
                        // get the integer for non-empty one!
                        if (!callA.getText().toString().isEmpty()) {
                            callA_int = Integer.parseInt(callA.getText().toString());
                            updateProgressBar(callA_int, handPont);
                        }
                        else {
                            callB_int = Integer.parseInt(callB.getText().toString());
                            updateProgressBar(handPont-callB_int, handPont);
                        }
                        //toggle button and clear text
                        setCalls();
                        isScoreTime = !isScoreTime;
                        mSubmitScoreButton.setText("Submit");
                        callA.setText("");
                        callB.setText("");
                    }
                }
                else {//Go to score submit
                    mainPageModel model = new mainPageModel();
                    ViewGroup group = (ViewGroup) findViewById(R.id.mainPage);
                    //No empty fields
                    if (model.isInputEmpty(group))
                        Toast.makeText(mainPage.this, R.string.emptyTextToast, Toast.LENGTH_SHORT).show();
                    else {
                        setPoints(maxPoints);
                        isScoreTime = !isScoreTime;
                        mSubmitScoreButton.setText("Call");
                    }
                }
            }
        });

    }

    //to update progress bar
    public void updateProgressBar(int value, int handPont){
        ProgressBar[] progress_bar = new ProgressBar[MAX_ROW_NUM];
        progress_bar[colNumber] = getProgressId(colNumber);
        //set length of bar
        progress_bar[colNumber].setMax(handPont);
        progress_bar[colNumber].setProgress(value);
        //Make current progress bar visible
        progress_bar[colNumber].setVisibility(View.VISIBLE);
    }


   //set calls
    public void setCalls(){
        TextView[] leftCol = new TextView[MAX_ROW_NUM];
        TextView[] rightCol = new TextView[MAX_ROW_NUM];
        //read Edit Texts for call
        EditText pointA = (EditText) findViewById(R.id.editTextTeamA);
        EditText pointB = (EditText) findViewById(R.id.editTextTeamB);
        //set results line by line
        leftCol[colNumber] = getCallIdLeft(colNumber);
        leftCol[colNumber].setText(pointA.getText());
        rightCol[colNumber] = getCallIdRight(colNumber);
        rightCol[colNumber].setText(pointB.getText());

    }

    //set (print) points
    public void setPoints(int maxPoints){
        TextView[] leftCol = new TextView[MAX_ROW_NUM];
        TextView[] rightCol = new TextView[MAX_ROW_NUM];
        //setPoints
        TextView finalA = (TextView) findViewById(R.id.textViewTeamAFinalPoint);
        TextView finalB = (TextView) findViewById(R.id.textViewTeamBFinalPoint);
        EditText pointA = (EditText) findViewById(R.id.editTextTeamA);
        EditText pointB = (EditText) findViewById(R.id.editTextTeamB);
        // TODO: this is useful if I want to limit the numbers! (not yet implemented)
        int pointA_int = Integer.parseInt(pointA.getText().toString());
        int pointB_int = Integer.parseInt(pointB.getText().toString());
        leftCol[colNumber] = getIdLeft(colNumber);
        rightCol[colNumber] = getIdRight(colNumber);
        //set results line by line
        leftCol[colNumber] = getIdLeft(colNumber);
        leftCol[colNumber].setText(pointA.getText());
        rightCol[colNumber] = getIdRight(colNumber);
        rightCol[colNumber].setText(pointB.getText());
        colNumber = colNumber + 1;
        //set final points
        finalA.setText(String.valueOf(calcLeftPoints()));
        finalB.setText(String.valueOf(calcRightPoints()));
        //
        if (calcLeftPoints() >= maxPoints) {
            //If TeamA reaches maxpint, change colors, disable button and edittext
            finalA.setTextColor(Color.GREEN);
            finalB.setTextColor(Color.RED);
            TextView TeamANames = (TextView) findViewById(R.id.teamA_names);
            TeamANames.setTextColor(Color.GREEN);
            TextView TeamBNames = (TextView) findViewById(R.id.teamB_names);
            TeamBNames.setTextColor(Color.RED);
            //disable EditTexts
            pointA.setFocusable(false);
            pointB.setFocusable(false);
            mSubmitScoreButton = (Button)findViewById(R.id.submit_scores_button);
            mSubmitScoreButton.setEnabled(false);
        }
        else if (calcRightPoints() >= maxPoints){
            //If TeamB reaches maxpoint, change colors, disable button and edittext
            finalB.setTextColor(Color.GREEN);
            finalA.setTextColor(Color.RED);
            TextView TeamBNames = (TextView) findViewById(R.id.teamB_names);
            TeamBNames.setTextColor(Color.GREEN);
            TextView TeamANames = (TextView) findViewById(R.id.teamA_names);
            TeamANames.setTextColor(Color.RED);
            pointA.setFocusable(false);
            pointB.setFocusable(false);
            mSubmitScoreButton = (Button)findViewById(R.id.submit_scores_button);
            mSubmitScoreButton.setEnabled(false);
        }
        else {
            //clear numbers after submit
            pointA.setText("");
            pointB.setText("");
        }

    }



    //cal total results for left side
    public int calcLeftPoints(){
        //TODO: Note this should not be hardcoded!
        int result = 0;
        int[] leftPoints = new int[MAX_ROW_NUM];
        for (int i=0 ; i<MAX_ROW_NUM ; i++)
            leftPoints[i]=0;
        TextView[] leftCol = new TextView[MAX_ROW_NUM];

        for (int i=0 ; i<MAX_ROW_NUM ; i++) {
            leftCol[i] = getIdLeft(i);
            //TODO: this is just for empty text, note if we hae text in "smallText"(in strings.xml) it crashes!
            if (!leftCol[i].getText().toString().isEmpty())
                leftPoints[i] = Integer.parseInt(leftCol[i].getText().toString());
            result = result + leftPoints[i];
        }
        return result;
    }

    //cal total results for right side
    public int calcRightPoints(){
        //TODO: Note this should not be hardcoded!
        int result = 0;
        int[] rightPoints = new int[MAX_ROW_NUM];
        for (int i=0 ; i<MAX_ROW_NUM ; i++)
            rightPoints[i]=0;
        TextView[] rightCol = new TextView[MAX_ROW_NUM];

        for (int i=0 ; i<MAX_ROW_NUM ; i++) {
            rightCol[i] = getIdRight(i);
            //TODO: this is just for empty text, note if we hae text in "smallText"(in strings.xml) it crashes!
            if (!rightCol[i].getText().toString().isEmpty())
                rightPoints[i] = Integer.parseInt(rightCol[i].getText().toString());
            result = result + rightPoints[i];
        }
        return result;
    }


    //probably need to map ids for loop
    //TODO: remove hardcodig!!
    public ProgressBar getProgressId(int colNumber) {

        switch (colNumber) {
            case 0:
                return (ProgressBar) findViewById(R.id.progressBar0);
            case 1:
                return (ProgressBar) findViewById(R.id.progressBar01);
            case 2:
                return (ProgressBar) findViewById(R.id.progressBar02);
            case 3:
                return (ProgressBar) findViewById(R.id.progressBar03);
            case 4:
                return (ProgressBar) findViewById(R.id.progressBar04);
            case 5:
                return (ProgressBar) findViewById(R.id.progressBar05);
            case 6:
                return (ProgressBar) findViewById(R.id.progressBar06);
            case 7:
                return (ProgressBar) findViewById(R.id.progressBar07);
            case 8:
                return (ProgressBar) findViewById(R.id.progressBar08);
            case 9:
                return (ProgressBar) findViewById(R.id.progressBar09);
            case 10:
                return (ProgressBar) findViewById(R.id.progressBar10);
            case 11:
                return (ProgressBar) findViewById(R.id.progressBar11);
            case 12:
                return (ProgressBar) findViewById(R.id.progressBar12);
            case 13:
                return (ProgressBar) findViewById(R.id.progressBar13);
            case 14:
                return (ProgressBar) findViewById(R.id.progressBar14);
            case 15:
                return (ProgressBar) findViewById(R.id.progressBar15);
            case 16:
                return (ProgressBar) findViewById(R.id.progressBar16);
            case 17:
                return (ProgressBar) findViewById(R.id.progressBar17);
            case 18:
                return (ProgressBar) findViewById(R.id.progressBar18);
            case 19:
                return (ProgressBar) findViewById(R.id.progressBar19);
            case 20:
                return (ProgressBar) findViewById(R.id.progressBar20);
            case 21:
                return (ProgressBar) findViewById(R.id.progressBar21);
            case 22:
                return (ProgressBar) findViewById(R.id.progressBar22);
            case 23:
                return (ProgressBar) findViewById(R.id.progressBar23);
            case 24:
                return (ProgressBar) findViewById(R.id.progressBar24);
            case 25:
                return (ProgressBar) findViewById(R.id.progressBar25);
            case 26:
                return (ProgressBar) findViewById(R.id.progressBar26);
            case 27:
                return (ProgressBar) findViewById(R.id.progressBar27);
            case 28:
                return (ProgressBar) findViewById(R.id.progressBar28);
            case 29:
                return (ProgressBar) findViewById(R.id.progressBar29);
            case 30:
                return (ProgressBar) findViewById(R.id.progressBar30);
        }
        return null;
    }

    //probably need to map ids for loop
    //TODO: remove hardcodig!!
    public TextView getIdLeft(int colNumber) {

        switch (colNumber) {
            case 0:
                return (TextView) findViewById(R.id.textViewL0);
            case 1:
                return (TextView) findViewById(R.id.textViewL01);
            case 2:
                return (TextView) findViewById(R.id.textViewL02);
            case 3:
                return (TextView) findViewById(R.id.textViewL03);
            case 4:
                return (TextView) findViewById(R.id.textViewL04);
            case 5:
                return (TextView) findViewById(R.id.textViewL05);
            case 6:
                return (TextView) findViewById(R.id.textViewL06);
            case 7:
                return (TextView) findViewById(R.id.textViewL07);
            case 8:
                return (TextView) findViewById(R.id.textViewL08);
            case 9:
                return (TextView) findViewById(R.id.textViewL09);
            case 10:
                return (TextView) findViewById(R.id.textViewL10);
            case 11:
                return (TextView) findViewById(R.id.textViewL11);
            case 12:
                return (TextView) findViewById(R.id.textViewL12);
            case 13:
                return (TextView) findViewById(R.id.textViewL13);
            case 14:
                return (TextView) findViewById(R.id.textViewL14);
            case 15:
                return (TextView) findViewById(R.id.textViewL15);
            case 16:
                return (TextView) findViewById(R.id.textViewL16);
            case 17:
                return (TextView) findViewById(R.id.textViewL17);
            case 18:
                return (TextView) findViewById(R.id.textViewL18);
            case 19:
                return (TextView) findViewById(R.id.textViewL19);
            case 20:
                return (TextView) findViewById(R.id.textViewL20);
            case 21:
                return (TextView) findViewById(R.id.textViewL21);
            case 22:
                return (TextView) findViewById(R.id.textViewL22);
            case 23:
                return (TextView) findViewById(R.id.textViewL23);
            case 24:
                return (TextView) findViewById(R.id.textViewL24);
            case 25:
                return (TextView) findViewById(R.id.textViewL25);
            case 26:
                return (TextView) findViewById(R.id.textViewL26);
            case 27:
                return (TextView) findViewById(R.id.textViewL27);
            case 28:
                return (TextView) findViewById(R.id.textViewL28);
            case 29:
                return (TextView) findViewById(R.id.textViewL29);
            case 30:
                return (TextView) findViewById(R.id.textViewL30);
        }
        return null;
    }





    //probably need to map ids for loop
    //TODO: remove hardcodig!!
    public TextView getIdRight(int colNumber) {

        switch (colNumber) {
            case 0:
                return (TextView) findViewById(R.id.textViewR0);
            case 1:
                return (TextView) findViewById(R.id.textViewR01);
            case 2:
                return (TextView) findViewById(R.id.textViewR02);
            case 3:
                return (TextView) findViewById(R.id.textViewR03);
            case 4:
                return (TextView) findViewById(R.id.textViewR04);
            case 5:
                return (TextView) findViewById(R.id.textViewR05);
            case 6:
                return (TextView) findViewById(R.id.textViewR06);
            case 7:
                return (TextView) findViewById(R.id.textViewR07);
            case 8:
                return (TextView) findViewById(R.id.textViewR08);
            case 9:
                return (TextView) findViewById(R.id.textViewR09);
            case 10:
                return (TextView) findViewById(R.id.textViewR10);
            case 11:
                return (TextView) findViewById(R.id.textViewR11);
            case 12:
                return (TextView) findViewById(R.id.textViewR12);
            case 13:
                return (TextView) findViewById(R.id.textViewR13);
            case 14:
                return (TextView) findViewById(R.id.textViewR14);
            case 15:
                return (TextView) findViewById(R.id.textViewR15);
            case 16:
                return (TextView) findViewById(R.id.textViewR16);
            case 17:
                return (TextView) findViewById(R.id.textViewR17);
            case 18:
                return (TextView) findViewById(R.id.textViewR18);
            case 19:
                return (TextView) findViewById(R.id.textViewR19);
            case 20:
                return (TextView) findViewById(R.id.textViewR20);
            case 21:
                return (TextView) findViewById(R.id.textViewR21);
            case 22:
                return (TextView) findViewById(R.id.textViewR22);
            case 23:
                return (TextView) findViewById(R.id.textViewR23);
            case 24:
                return (TextView) findViewById(R.id.textViewR24);
            case 25:
                return (TextView) findViewById(R.id.textViewR25);
            case 26:
                return (TextView) findViewById(R.id.textViewR26);
            case 27:
                return (TextView) findViewById(R.id.textViewR27);
            case 28:
                return (TextView) findViewById(R.id.textViewR28);
            case 29:
                return (TextView) findViewById(R.id.textViewR29);
            case 30:
                return (TextView) findViewById(R.id.textViewR30);
        }
        return null;
    }







    //probably need to map ids for loop
    //TODO: remove hardcodig!!
    public TextView getCallIdLeft(int colNumber) {

        switch (colNumber) {
            case 0:
                return (TextView) findViewById(R.id.textViewCallL0);
            case 1:
                return (TextView) findViewById(R.id.textViewCallL01);
            case 2:
                return (TextView) findViewById(R.id.textViewCallL02);
            case 3:
                return (TextView) findViewById(R.id.textViewCallL03);
            case 4:
                return (TextView) findViewById(R.id.textViewCallL04);
            case 5:
                return (TextView) findViewById(R.id.textViewCallL05);
            case 6:
                return (TextView) findViewById(R.id.textViewCallL06);
            case 7:
                return (TextView) findViewById(R.id.textViewCallL07);
            case 8:
                return (TextView) findViewById(R.id.textViewCallL08);
            case 9:
                return (TextView) findViewById(R.id.textViewCallL09);
            case 10:
                return (TextView) findViewById(R.id.textViewCallL10);
            case 11:
                return (TextView) findViewById(R.id.textViewCallL11);
            case 12:
                return (TextView) findViewById(R.id.textViewCallL12);
            case 13:
                return (TextView) findViewById(R.id.textViewCallL13);
            case 14:
                return (TextView) findViewById(R.id.textViewCallL14);
            case 15:
                return (TextView) findViewById(R.id.textViewCallL15);
            case 16:
                return (TextView) findViewById(R.id.textViewCallL16);
            case 17:
                return (TextView) findViewById(R.id.textViewCallL17);
            case 18:
                return (TextView) findViewById(R.id.textViewCallL18);
            case 19:
                return (TextView) findViewById(R.id.textViewCallL19);
            case 20:
                return (TextView) findViewById(R.id.textViewCallL20);
            case 21:
                return (TextView) findViewById(R.id.textViewCallL21);
            case 22:
                return (TextView) findViewById(R.id.textViewCallL22);
            case 23:
                return (TextView) findViewById(R.id.textViewCallL23);
            case 24:
                return (TextView) findViewById(R.id.textViewCallL24);
            case 25:
                return (TextView) findViewById(R.id.textViewCallL25);
            case 26:
                return (TextView) findViewById(R.id.textViewCallL26);
            case 27:
                return (TextView) findViewById(R.id.textViewCallL27);
            case 28:
                return (TextView) findViewById(R.id.textViewCallL28);
            case 29:
                return (TextView) findViewById(R.id.textViewCallL29);
            case 30:
                return (TextView) findViewById(R.id.textViewCallL30);
        }
        return null;
    }

    //probably need to map ids for loop
    //TODO: remove hardcodig!!
    public TextView getCallIdRight(int colNumber) {

        switch (colNumber) {
            case 0:
                return (TextView) findViewById(R.id.textViewCallR0);
            case 1:
                return (TextView) findViewById(R.id.textViewCallR01);
            case 2:
                return (TextView) findViewById(R.id.textViewCallR02);
            case 3:
                return (TextView) findViewById(R.id.textViewCallR03);
            case 4:
                return (TextView) findViewById(R.id.textViewCallR04);
            case 5:
                return (TextView) findViewById(R.id.textViewCallR05);
            case 6:
                return (TextView) findViewById(R.id.textViewCallR06);
            case 7:
                return (TextView) findViewById(R.id.textViewCallR07);
            case 8:
                return (TextView) findViewById(R.id.textViewCallR08);
            case 9:
                return (TextView) findViewById(R.id.textViewCallR09);
            case 10:
                return (TextView) findViewById(R.id.textViewCallR10);
            case 11:
                return (TextView) findViewById(R.id.textViewCallR11);
            case 12:
                return (TextView) findViewById(R.id.textViewCallR12);
            case 13:
                return (TextView) findViewById(R.id.textViewCallR13);
            case 14:
                return (TextView) findViewById(R.id.textViewCallR14);
            case 15:
                return (TextView) findViewById(R.id.textViewCallR15);
            case 16:
                return (TextView) findViewById(R.id.textViewCallR16);
            case 17:
                return (TextView) findViewById(R.id.textViewCallR17);
            case 18:
                return (TextView) findViewById(R.id.textViewCallR18);
            case 19:
                return (TextView) findViewById(R.id.textViewCallR19);
            case 20:
                return (TextView) findViewById(R.id.textViewCallR20);
            case 21:
                return (TextView) findViewById(R.id.textViewCallR21);
            case 22:
                return (TextView) findViewById(R.id.textViewCallR22);
            case 23:
                return (TextView) findViewById(R.id.textViewCallR23);
            case 24:
                return (TextView) findViewById(R.id.textViewCallR24);
            case 25:
                return (TextView) findViewById(R.id.textViewCallR25);
            case 26:
                return (TextView) findViewById(R.id.textViewCallR26);
            case 27:
                return (TextView) findViewById(R.id.textViewCallR27);
            case 28:
                return (TextView) findViewById(R.id.textViewCallR28);
            case 29:
                return (TextView) findViewById(R.id.textViewCallR29);
            case 30:
                return (TextView) findViewById(R.id.textViewCallR30);
        }
        return null;
    }

}



