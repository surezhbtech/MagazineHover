package com.klm.magazine.magazinehover;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

public class Hovercam extends AppCompatActivity {
    public static final String EXTRAS_KEY_ACTIVITY_TITLE_STRING = "activityTitle";
    public static final String EXTRAS_KEY_ACTIVITY_ARCHITECT_WORLD_URL = "activityArchitectWorldUrl";

    public static final String EXTRAS_KEY_ACTIVITY_IR = "activityIr";
    public static final String EXTRAS_KEY_ACTIVITY_GEO = "activityGeo";

    public static final String EXTRAS_KEY_ACTIVITIES_ARCHITECT_WORLD_URLS_ARRAY = "activitiesArchitectWorldUrls";
    public static final String EXTRAS_KEY_ACTIVITIES_TILES_ARRAY = "activitiesTitles";
    public static final String EXTRAS_KEY_ACTIVITIES_CLASSNAMES_ARRAY = "activitiesClassnames";

    public static final String EXTRAS_KEY_ACTIVITIES_IR_ARRAY = "activitiesIr";
    public static final String EXTRAS_KEY_ACTIVITIES_GEO_ARRAY = "activitiesGeo";

    private static final int WIKITUDE_PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int WIKITUDE_PERMISSIONS_REQUEST_GPS = 2;
    private int _lastSelectedListItemPosition = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        setContentView(R.layout.activity_hovercam);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        _lastSelectedListItemPosition = 4;

        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, WIKITUDE_PERMISSIONS_REQUEST_CAMERA);
        } else {
            if ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, WIKITUDE_PERMISSIONS_REQUEST_GPS);
            } else {
                loadExample();
            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case WIKITUDE_PERMISSIONS_REQUEST_CAMERA: {
                if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    if ( this.getActivitiesGeo()[_lastSelectedListItemPosition] ) {
                        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, WIKITUDE_PERMISSIONS_REQUEST_GPS);
                        }
                    } else {
                        loadExample();
                    }
                } else {
                    Toast.makeText(this, "Sorry, augmented reality doesn't work without reality.\n\nPlease grant camera permission.", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case WIKITUDE_PERMISSIONS_REQUEST_GPS: {
                if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    loadExample();
                } else {
                    Toast.makeText(this, "Sorry, this example requires access to your location in order to work properly.\n\nPlease grant location permission.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hovercam, menu);
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

    private void loadExample() {
        try {

            if ( _lastSelectedListItemPosition >= 0 ) {

                final Intent intent = new Intent(this, Class.forName("com.klm.magazine.magazinehover.SampleCamActivity"));
                intent.putExtra(EXTRAS_KEY_ACTIVITY_TITLE_STRING,"Magazine Hover");
                intent.putExtra(EXTRAS_KEY_ACTIVITY_ARCHITECT_WORLD_URL, "samples/7_Video_4_Bonus-Transparent$Video/index.html");
                intent.putExtra(EXTRAS_KEY_ACTIVITY_IR,true);
                intent.putExtra(EXTRAS_KEY_ACTIVITY_GEO,false);

				/* launch activity */
                this.startActivity(intent);
                this.finish();
            }

        } catch (Exception e) {
			/*
			 * may never occur, as long as all SampleActivities exist and are
			 * listed in manifest
			 */
            final String className = getListActivities()[_lastSelectedListItemPosition];
            Toast.makeText(this, className + "\nnot defined/accessible",
                    Toast.LENGTH_SHORT).show();
        }
    }
    protected final String[] getListLabels() {
        return getIntent().getExtras().getStringArray(
                EXTRAS_KEY_ACTIVITIES_TILES_ARRAY);
    }

    protected String getActivityTitle() {
        return getIntent().getExtras().getString(
                EXTRAS_KEY_ACTIVITY_TITLE_STRING);
    }

    protected String[] getListActivities() {
        return getIntent().getExtras().getStringArray(
                EXTRAS_KEY_ACTIVITIES_CLASSNAMES_ARRAY);
    }

    protected String[] getArchitectWorldUrls() {
        return getIntent().getExtras().getStringArray(
                EXTRAS_KEY_ACTIVITIES_ARCHITECT_WORLD_URLS_ARRAY);
    }

    protected boolean[] getActivitiesIr() {
        return getIntent().getExtras().getBooleanArray(
                EXTRAS_KEY_ACTIVITIES_IR_ARRAY);
    }

    protected boolean[] getActivitiesGeo() {
        return getIntent().getExtras().getBooleanArray(
                EXTRAS_KEY_ACTIVITIES_GEO_ARRAY);
    }

    protected int getContentViewId() {
        return R.layout.list_sample;
    }
}
