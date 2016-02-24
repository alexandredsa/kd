package me.alexandredsa.kd.dto;

import android.content.Context;
import android.provider.Settings;

import java.util.Date;

import me.alexandredsa.kd.database.model.Localizacao;

/**
 * Created by Alexandre on 22/02/2016.
 */
public class LocalizacaoDTO {
    private String deviceId;
    private String message;
    private double latitude;
    private double longitude;
    private Date timeRetrieved;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getTimeRetrieved() {
        return timeRetrieved;
    }

    public void setTimeRetrieved(Date timeRetrieved) {
        this.timeRetrieved = timeRetrieved;
    }

    public static LocalizacaoDTO build(Context mContext, Localizacao localizacao) {
        LocalizacaoDTO localizacaoDTO = new LocalizacaoDTO();
        localizacaoDTO.setDeviceId(Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID));
        localizacaoDTO.setLatitude(localizacao.getLatitude());
        localizacaoDTO.setLongitude(localizacao.getlongitude());
        localizacaoDTO.setMessage(localizacao.getMensagem());
        localizacaoDTO.setTimeRetrieved(new Date());

        return localizacaoDTO;
    }
}
