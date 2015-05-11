package com.android.mahyar.shelemsheet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
        String handPoint = intent.getStringExtra("handPoint");
        String maxPoints = intent.getStringExtra("maxPoints");




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
                    //Use XOR, one of tehm shoudl be filled
                    if (!(callA.getText().toString().isEmpty() ^ callB.getText().toString().isEmpty())){
                        Toast.makeText(mainPage.this, R.string.callTextToast, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        int callA_int;
                        int callB_int;
                        // get the integer for non-empty one!
                        if (!callA.getText().toString().isEmpty())
                            callA_int = Integer.parseInt(callA.getText().toString());
                        else
                            callB_int = Integer.parseInt(callB.getText().toString());
                        //toggle button and clear text
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
                        setPoints();
                    }
                }
            }
        });

    }


    //set (print) points
    public void setPoints(){
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
        //Add 3 spaces after points, because EditText size changes dynamically with points
        //TODO: change to fix size
        finalA.setText(calcLeftPoints() + "      ");
        finalB.setText(calcRightPoints() + "      ");
        //clear numbers after submit
        pointA.setText("");
        pointB.setText("");

    }




    //cal total results for left side
    public String calcLeftPoints(){
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
        return String.valueOf(result);
    }

    //cal total results for right side
    public String calcRightPoints(){
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
        return String.valueOf(result);
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

}
