package coursera.android.peerassignment.dailyselfie;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


public class ShowImageActivity extends ActionBarActivity {

    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);


        mImageView = (ImageView) findViewById(R.id.large_photo_view);

        Bundle bundle = getIntent().getExtras();
        String path = bundle.getString("PHOTO_PATH");

        // Get the dimensions of the View
        int targetW = mImageView.getLayoutParams().width;
        int targetH = mImageView.getLayoutParams().height;

        mImageView.setImageBitmap(PhotoDescriptor.setPic(targetW, targetH, path));

    }


}
