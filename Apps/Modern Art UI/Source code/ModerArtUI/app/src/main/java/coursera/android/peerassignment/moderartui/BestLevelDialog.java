package coursera.android.peerassignment.moderartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by suzana on 6/1/2015.
 */
public class BestLevelDialog extends DialogFragment {


    private static BestLevelDialog instance = null;
    private int bestLevel;

    public static BestLevelDialog getInstance() {
        if(instance == null) {
            instance = new BestLevelDialog();
        }
        return instance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String prefix = getResources().getString(R.string.level);
        final CharSequence levelOptions[] = {prefix + " 1", prefix + " 2", prefix +  " 3", prefix +  " 4", prefix +  " 5" };

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.best_level_title);
        builder.setSingleChoiceItems(levelOptions, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //the default value starts with 0.
                bestLevel = arg1+1;

            }
        });

        builder.setPositiveButton(R.string.best_level_positive_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Toast.makeText(getActivity(), getActivity().getString(R.string.best_level_final_message) + bestLevel + "!", Toast.LENGTH_LONG).show();

            }

        });

        builder.setNegativeButton(R.string.best_level_negative_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        return builder.create();
    }


}
