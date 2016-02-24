package me.alexandredsa.kd.database.model;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by alexa on 22/02/2016.
 */
@DatabaseTable
public class Localizacao {

    @Expose
    @DatabaseField(generatedId = true)
    public transient int id;

    @DatabaseField
    private String mensagem;
    @DatabaseField
    private double latitude;
    @DatabaseField
    private double longitude;

    public Localizacao(){}

    public Localizacao(String mensagem, double latitude, double longitude) {
        this.mensagem = mensagem;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getlongitude() {
        return longitude;
    }

    public void setlongitude(double longitude) {
        this.longitude = longitude;
    }
}
