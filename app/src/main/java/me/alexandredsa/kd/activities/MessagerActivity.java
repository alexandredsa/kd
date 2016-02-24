package me.alexandredsa.kd.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import me.alexandredsa.kd.R;
import me.alexandredsa.kd.location.LocationProvider;


public class MessagerActivity extends AppCompatActivity {
    private EditText txtMessage;
    private LocationProvider locationProvider;
    private Location lastLocation;
    private Dao<me.alexandredsa.kd.database.model.Localizacao, Integer> localizacaoDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_manager);
        locationProvider = new LocationProvider(this);
        txtMessage = (EditText) findViewById(R.id.txtMessage);

        try {
            localizacaoDAO = new me.alexandredsa.kd.database.dao.LocalizacaoDAO(this).getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        scheduleReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void scheduleReceiver() {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), me.alexandredsa.kd.receiver.LocationServiceReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off

        final PendingIntent pIntent = PendingIntent.getBroadcast(this, me.alexandredsa.kd.receiver.LocationServiceReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();

        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                me.alexandredsa.kd.misc.ServiceConstants.INTERVAL_SERVICE, pIntent);
    }

    public void sendLocation(View view) throws SQLException {
        lastLocation = locationProvider.getLastLocation();

        if (lastLocation != null) {
            me.alexandredsa.kd.database.model.Localizacao loc = new me.alexandredsa.kd.database.model.Localizacao();
            loc.setLatitude(lastLocation.getLatitude());
            loc.setlongitude(lastLocation.getLongitude());
            loc.setMensagem(txtMessage.getText().toString());

            Dao.CreateOrUpdateStatus status = localizacaoDAO.createOrUpdate(loc);

            if (status.isCreated()) {
                Toast.makeText(this, getString(R.string.toast_msg_created_successfully), Toast.LENGTH_LONG).show();
                txtMessage.setText("");
            }
        }
    }
}
