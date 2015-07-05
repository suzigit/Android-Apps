package coursera.android.peerassignment.moderartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by suzana on 6/1/2015.
 */
public class HelpDialog extends DialogFragment {

    private static HelpDialog instance = null;

    public static HelpDialog getInstance() {
        if(instance == null) {
            instance = new HelpDialog();
        }
        return instance;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.help_title_message);
        builder.setMessage(R.string.help_message);
        builder.setPositiveButton(R.string.help_positive_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        return builder.create();
    }

}
