package coursera.android.peerassignment.moderartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by suzana on 6/3/2015.
 */
public class MoreInformationDialog extends DialogFragment {

    private static MoreInformationDialog instance = null;

    public static MoreInformationDialog getInstance() {
        if (instance == null) {
            instance = new MoreInformationDialog();
        }
        return instance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.more_information_message);


        builder.setPositiveButton(R.string.more_information_positive_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent momaIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri
                        .parse(getResources().getString(R.string.address_moma)));
                startActivity(momaIntent);

            }

        });

        builder.setNegativeButton(R.string.more_information_negative_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        return builder.create();

    }

}