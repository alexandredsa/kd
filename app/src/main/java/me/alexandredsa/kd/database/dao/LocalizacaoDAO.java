package me.alexandredsa.kd.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import me.alexandredsa.kd.database.ORMLiteHelper;
import me.alexandredsa.kd.database.model.Localizacao;

/**
 * Created by Alexandre on 22/02/2016.
 */
public class LocalizacaoDAO {
    private Context mContext;


    public LocalizacaoDAO(Context mContext) {
        this.mContext = mContext;
    }

    public Dao<Localizacao, Integer> getInstance() throws SQLException {
        return ORMLiteHelper.getInstance(mContext).getLocalizacaoDao();
    }
}
