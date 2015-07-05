package coursera.android.peerassignment.dailyselfie;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class PhotosListActivity extends ActionBarActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String FILE_PICTURES_NAMES = "persistence.txt";

    private PhotosListAdapter mAdapter;
    private ListView listView;
    private PhotoDescriptor currentPhoto;


    private AlarmManager mAlarmManager;
    private Intent mNotificationReceiverIntent;
    private PendingIntent mNotificationReceiverPendingIntent;
    private static final long TWO_MIN = 2 * 60 * 1000L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        mAdapter = new PhotosListAdapter(getApplicationContext());
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(PhotosListActivity.this, ShowImageActivity.class);
                String path = ((PhotoDescriptor) PhotosListActivity.this.mAdapter.getItem(position)).getPath();
                intent.putExtra("PHOTO_PATH", path);
                startActivity(intent);
            }
        });

        readFile();

        // Get the AlarmManager Service
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Create an Intent to broadcast to the AlarmNotificationReceiver
        mNotificationReceiverIntent = new Intent(PhotosListActivity.this,
                PhotosReminderReceiver.class);

        // Create an PendingIntent that holds the NotificationReceiverIntent
        mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(
                PhotosListActivity.this, 0, mNotificationReceiverIntent, 0);

        mAlarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY,
                AlarmManager.INTERVAL_DAY,
                mNotificationReceiverPendingIntent);

    }


    private void readFile() {
        try {

            if (getFileStreamPath(FILE_PICTURES_NAMES).exists()) {
                FileInputStream fis = openFileInput(FILE_PICTURES_NAMES);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                String line = "";

                while (null != (line = br.readLine())) {

                    File photo = new File(line);
                    PhotoDescriptor pd = new PhotoDescriptor(photo);
                    this.mAdapter.add(pd);
                }

                br.close();

            }

        } catch (IOException ex) {
            Toast.makeText(getApplicationContext(), "Storage Error - " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        this.mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        writeFile();


    }

    private void writeFile() {
        int totalElements = this.mAdapter.getCount();

        try {
            File f = new File(FILE_PICTURES_NAMES);
            f.delete();

            FileOutputStream fos = openFileOutput(FILE_PICTURES_NAMES, MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(fos)));

            for (int i=0; i<totalElements; i++) {
                PhotoDescriptor pd = (PhotoDescriptor) this.mAdapter.getItem(i);
                pw.println(pd.getPath());
            }

            pw.close();

        } catch (FileNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "Storage Error - " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.take_photo_action) {
           this.dispatchTakePictureIntent();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = PhotoDescriptor.createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getApplicationContext(), "File creation error -"+  ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                this.currentPhoto = new PhotoDescriptor(photoFile);

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            this.galleryAddPic();
            this.mAdapter.add(this.currentPhoto);
            this.mAdapter.notifyDataSetChanged();

        }
    }


    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(new File(this.currentPhoto.getPath()));
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}
