package com.android.mahyar.shelemsheet;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;



public class choosePlayers extends ActionBarActivity {

    private Button mResetButton;
    private Button mSubmitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_players);


        mResetButton = (Button)findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText player1text = (EditText)findViewById(R.id.editText_player1);
                EditText player2text = (EditText)findViewById(R.id.editText_player2);
                EditText player3text = (EditText)findViewById(R.id.editText_player3);
                EditText player4text = (EditText)findViewById(R.id.editText_player4);
                EditText handpoint = (EditText)findViewById(R.id.editText_handPoint);
                EditText maxPoints = (EditText)findViewById(R.id.editText_totalPoint);
                choosePlayerModel model = new choosePlayerModel();
                // TODO: Reset Group View instead of one by one
                model.Reset(player1text, player2text, player3text, player4text, handpoint, maxPoints);
            }
        });

        mSubmitButton = (Button)findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePlayerModel model = new choosePlayerModel();
                ViewGroup group = (ViewGroup)findViewById(R.id.scrollable_layout_choose_player);
                EditText handpoint = (EditText)findViewById(R.id.editText_handPoint);
                EditText maxPoints = (EditText)findViewById(R.id.editText_totalPoint);
                //No empty fields
                if (model.isInputEmpty(group))
                    Toast.makeText(choosePlayers.this,R.string.emptyTextToast, Toast.LENGTH_SHORT).show();
                else{
                    //Hand point should not be greater than total points
                    int handPoint_int = Integer.parseInt(handpoint.getText().toString());
                    int totalPoint_int = Integer.parseInt(maxPoints.getText().toString());
                    if (handPoint_int > totalPoint_int){
                        Toast.makeText(choosePlayers.this,R.string.handpointRangeToast, Toast.LENGTH_SHORT).show();
                    }
                    else{// Next page otherwise
                        //TODO: send points (hand/total) as well
                        //send Player Names as intent
                        EditText player1text = (EditText)findViewById(R.id.editText_player1);
                        EditText player2text = (EditText)findViewById(R.id.editText_player2);
                        EditText player3text = (EditText)findViewById(R.id.editText_player3);
                        EditText player4text = (EditText)findViewById(R.id.editText_player4);
                        String player1String = player1text.getText().toString();
                        String player2String = player2text.getText().toString();
                        String player3String = player3text.getText().toString();
                        String player4String = player4text.getText().toString();

                        Intent choose_to_main = new Intent(choosePlayers.this, mainPage.class);
                        choose_to_main.putExtra("Player1", player1String);
                        choose_to_main.putExtra("Player2", player2String);
                        choose_to_main.putExtra("Player3", player3String);
                        choose_to_main.putExtra("Player4", player4String);
                        choose_to_main.putExtra("maxPoints", totalPoint_int);
                        choose_to_main.putExtra("handPoint", handPoint_int);
                        startActivity(choose_to_main);
                    }
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_players, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
