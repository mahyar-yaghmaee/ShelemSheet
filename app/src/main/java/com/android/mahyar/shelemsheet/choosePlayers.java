package com.android.mahyar.shelemsheet;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
                model.Reset(player1text, player2text, player3text, player4text, handpoint, maxPoints);
            }
        });

        mSubmitButton = (Button)findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText handpoint = (EditText)findViewById(R.id.editText_handPoint);
                EditText maxPoints = (EditText)findViewById(R.id.editText_totalPoint);
                int handPoint_int = Integer.parseInt(handpoint.getText().toString());
                int totalPoint_int = Integer.parseInt(maxPoints.getText().toString());
                //Hand point should not be greater than total points
                if (handPoint_int > totalPoint_int){
                    Toast.makeText(choosePlayers.this,R.string.handpointRangeToast, Toast.LENGTH_SHORT).show();
                }

                //No empty fields
                choosePlayerModel model = new choosePlayerModel();
                ViewGroup group = (ViewGroup)findViewById(R.id.choosePlayerParent);
                if (model.isInputEmpty(group))
                    Toast.makeText(choosePlayers.this,R.string.emptyTextToast, Toast.LENGTH_SHORT).show();

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
