package coursera.android.peerassignment.moderartui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Lift lift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView currentLevel = (TextView) findViewById(R.id.currentLevel);
        TextView destinationLevel = (TextView) findViewById(R.id.destinationLevel);

        TextView[] coloredTextViews = new TextView[5];

        coloredTextViews[0] = (TextView) findViewById(R.id.textView1);
        coloredTextViews[1] = (TextView) findViewById(R.id.textView2);
        coloredTextViews[2] = (TextView) findViewById(R.id.textView3);
        coloredTextViews[3] = (TextView) findViewById(R.id.textView4);
        coloredTextViews[4] = (TextView) findViewById(R.id.textView5);

        coloredTextViews[0].setText(R.string.text_current_position);

        registerForContextMenu(coloredTextViews[0]);
        registerForContextMenu(coloredTextViews[1]);
        registerForContextMenu(coloredTextViews[2]);
        registerForContextMenu(coloredTextViews[3]);
        registerForContextMenu(coloredTextViews[4]);

        this.lift = new Lift(coloredTextViews, currentLevel, destinationLevel,0);

        int[] colors = new int[5];
        colors[0] = Color.WHITE;
        colors[1] = Color.RED;
        colors[2] = Color.GREEN;
        colors[3] = Color.BLUE;
        colors[4] = Color.YELLOW;
        this.lift.setBackground(colors);

        final Button buttonGo = (Button) findViewById(R.id.buttonGo);
        buttonGo.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

               Thread movingThread = new Thread() {
                    @Override
                    public void run() {
                        lift.goToDestinationLevel(MainActivity.this);
                   }
                };
               movingThread.start();
            }
        });

        setLevelButtons();

        SeekBar velocityBar = (SeekBar) findViewById(R.id.seekBar_velocity);

        velocityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                double velocity = ((double) this.progress) / seekBar.getMax();
                lift.setVelocity(velocity);
                String txt = getResources().getString(R.string.velocity) + "=" + velocity;

                Toast.makeText(MainActivity.this, txt, Toast.LENGTH_SHORT).show();

            }
        });


        SeekBar changeColorBar = (SeekBar) findViewById(R.id.seekBar_change_color);

        changeColorBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener () {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

//TODO
                //change progress to a value between 0 and 255
                progress =  (progress*255)/100;

 /*
red 255 0 0      ->   magenta 255 0 255
green 0 255 0    ->   blue 0 0 255
blue 0 0 255     ->   yellow 255 255 0
yellow 255 255 0 ->   light blue 0 255 255

*/

                int r = progress << 16;
                int g = progress << 8;
                int b = progress;

                int[] colors = new int[5];
                colors[0] = Color.WHITE;
                colors[1] = Color.RED + b;
                colors[2] = Color.GREEN + b - g;
                colors[3] = Color.BLUE + r + g - b;
                colors[4] = Color.YELLOW - r + b;
                MainActivity.this.lift.setBackground(colors);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private void setLevelButtons() {
        final Button buttonLevel1 = (Button) findViewById(R.id.buttonLevel1);
        buttonLevel1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                lift.setDestinationLevel(1);
            }
        });

        final Button buttonLevel2 = (Button) findViewById(R.id.buttonLevel2);
        buttonLevel2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                lift.setDestinationLevel(2);
            }
        });
        final Button buttonLevel3 = (Button) findViewById(R.id.buttonLevel3);
        buttonLevel3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                lift.setDestinationLevel(3);
            }
        });
        final Button buttonLevel4 = (Button) findViewById(R.id.buttonLevel4);
        buttonLevel4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                lift.setDestinationLevel(4);
            }
        });
        final Button buttonLevel5 = (Button) findViewById(R.id.buttonLevel5);
        buttonLevel5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                lift.setDestinationLevel(5);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.action_moma:
                MoreInformationDialog mid = MoreInformationDialog.getInstance();
                mid.show(getFragmentManager(), "moreInformationDialog");
                return true;

            case R.id.action_help:
                HelpDialog hd = HelpDialog.getInstance();
                hd.show(getFragmentManager(), "helpDialog");

                return true;

            case R.id.action_exit:
                finish();
                return true;

            default:
                return false;
        }
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    // Process clicks on Context Menu Items
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.best_level_election:
                BestLevelDialog dialog = BestLevelDialog.getInstance();
                dialog.show(getFragmentManager(), "bestLevelElection");
                return true;
            default:
                return false;
        }
    }



}
