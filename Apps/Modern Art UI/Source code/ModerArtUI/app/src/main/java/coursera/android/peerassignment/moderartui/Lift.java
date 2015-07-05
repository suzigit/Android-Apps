package coursera.android.peerassignment.moderartui;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by suzana on 5/31/2015.
 */
public class Lift  {

    private static final int SLEEP_TIME_BASE = 200;

    private TextView currentLevel;
    private TextView destinationLevel;
    private TextView[] coloredTextViews;
    private double velocity;

    public Lift(TextView[] coloredTextViews, TextView currentLevel, TextView destinationLevel, double velocity) {
        setColoredTextViews(coloredTextViews);
        setCurrentLevel(currentLevel);
        setDestinationLevel(destinationLevel);
        setVelocity(velocity);
    }

    public synchronized void goToDestinationLevel(Activity ac) {

        try {
            int dest = this.getDestinationLevel();
            int cur = this.getCurrentLevel();
            int sleepTime = (int) (SLEEP_TIME_BASE * (1-velocity));
            if(dest>cur) {
                int delta = dest-cur;
                for (int i=0; i<delta; i++) {
                    Thread.sleep(sleepTime);
                    ac.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            goUp();
                        }
                    });
                }
            }
            else if (cur>dest){
                int delta = cur-dest;
                for (int i=0; i<delta; i++) {
                    Thread.sleep(sleepTime);
                    ac.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            goDown();
                        }
                    });
                }
            }


        } catch (Exception e) {
            Toast.makeText(ac, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public int getDestinationLevel() {
        return Integer.parseInt(destinationLevel.getText()+"");
    }

    public synchronized void setDestinationLevel(int destinationLevel) {
        this.destinationLevel.setText("" + destinationLevel);
    }

    private void setDestinationLevel(TextView destinationLevel) {
        this.destinationLevel = destinationLevel;
    }

    private void setColoredTextViews(TextView[] coloredTextViews) {
        this.coloredTextViews = coloredTextViews;
    }

    public double getVelocity() {
        return velocity;
    }

    public synchronized void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public int getCurrentLevel() {
        return Integer.parseInt(currentLevel.getText()+"");
    }

    public void setCurrentLevel(TextView currentLevel) {
        this.currentLevel = currentLevel;
    }
    private void setCurrentLevel(int currentLevel) {
        this.currentLevel.setText("" + currentLevel);
    }
    private void decreaseLevel() {
        int cl = this.getCurrentLevel();
        cl--;
        this.currentLevel.setText("" + cl);
    }
    private void increaseLevel() {
        int cl = this.getCurrentLevel();
        cl++;
        this.currentLevel.setText("" + cl);
    }

    public void goUp() {

        Drawable lastBackground = this.coloredTextViews[this.coloredTextViews.length-1].getBackground();
        String lastText = this.coloredTextViews[this.coloredTextViews.length-1].getText().toString();

        for (int j=this.coloredTextViews.length-1; j>0; j--) {
            this.coloredTextViews[j].setBackground(this.coloredTextViews[j-1].getBackground());
            this.coloredTextViews[j].setText(this.coloredTextViews[j-1].getText());
        }

        this.coloredTextViews[0].setBackground(lastBackground);
        this.coloredTextViews[0].setText(lastText);
        this.increaseLevel();
    }


    public void goDown() {
        Drawable firstBackground = this.coloredTextViews[0].getBackground();
        String firstText = this.coloredTextViews[0].getText().toString();

        for (int j=0; j<this.coloredTextViews.length-1; j++) {
            this.coloredTextViews[j].setBackground(this.coloredTextViews[j+1].getBackground());
            this.coloredTextViews[j].setText(this.coloredTextViews[j+1].getText());
        }
        this.coloredTextViews[this.coloredTextViews.length-1].setBackground(firstBackground);
        this.coloredTextViews[this.coloredTextViews.length-1].setText(firstText);
        this.decreaseLevel();
    }


    public synchronized void setBackground (int colors[]) {

        for (int i=0; i<this.coloredTextViews.length; i++) {
            int indexColoredTextviews = (i+this.getCurrentLevel()-1)% this.coloredTextViews.length;
            this.coloredTextViews[indexColoredTextviews].setBackgroundColor(colors[i]);
            this.coloredTextViews[indexColoredTextviews].invalidate();
        }


    }

}
