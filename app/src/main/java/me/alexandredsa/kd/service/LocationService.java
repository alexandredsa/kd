package me.alexandredsa.kd.service;

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import me.alexandredsa.kd.api.LocationAPIService;
import me.alexandredsa.kd.api.retrofit.RetrofitRequestHandler;
import me.alexandredsa.kd.database.dao.LocalizacaoDAO;
import me.alexandredsa.kd.database.model.Localizacao;
import me.alexandredsa.kd.dto.APIResponse;
import me.alexandredsa.kd.dto.LocalizacaoDTO;
import me.alexandredsa.kd.location.LocationProvider;
import me.alexandredsa.kd.tools.AddressUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by alexa on 22/02/2016.
 */
public class LocationService extends Service {
    private static final String TAG = "LocationService";
    private LocationProvider locationProvider;
    private Geocoder geocoder;
    private Dao<Localizacao, Integer> localizacaoDAO;
    private List<Address> addresses;
    private Location lastLocation;
    private Localizacao localizacao;
    private LocalizacaoDTO localizacaoDTO;
    private LocationAPIService locationAPIService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initLocationProvider();
        initLocalizacaoDAO();

        if (hasPendingData()) {
            localizacao = getFirst();
        } else {
            localizacao = getFromLocation();
        }


        if (localizacao != null) {
            localizacaoDTO = LocalizacaoDTO.build(this, localizacao);
            sendLocation();
        }

        return START_NOT_STICKY;
    }

    private void sendLocation() {
        locationAPIService = RetrofitRequestHandler.getAPIService(this);
        Call<APIResponse> response = locationAPIService.sendLocation(localizacaoDTO);
        response.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Response<APIResponse> response, Retrofit retrofit) {
                try {
                    if(response.isSuccess())
                        localizacaoDAO.delete(localizacao);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

    }

    private void initLocationProvider() {
        if (locationProvider == null)
            locationProvider = new LocationProvider(this);
    }


    private void initLocalizacaoDAO() {
        try {
            localizacaoDAO = new LocalizacaoDAO(this).getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean hasPendingData() {
        try {
            return !localizacaoDAO.queryForAll().isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return false;
    }

    private String retrieveAddress() {
        new Thread() {
            @Override
            public void run() {
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return AddressUtils.extractAddressLines(addresses.get(0));
    }

    public Localizacao getFirst() {
        try {
            return localizacaoDAO.queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Localizacao getFromLocation() {
        lastLocation = locationProvider.getLastLocation();

        if (lastLocation == null)
            return null;

        Localizacao localizacao = new Localizacao();
        localizacao.setlongitude(lastLocation.getLongitude());
        localizacao.setLatitude(lastLocation.getLatitude());

        return localizacao;
    }
}
