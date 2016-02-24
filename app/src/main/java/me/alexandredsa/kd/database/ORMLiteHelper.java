package me.alexandredsa.kd.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import me.alexandredsa.kd.database.model.Localizacao;

/**
 * Created by alexandre on 10/02/2016.
 */
public class ORMLiteHelper extends OrmLiteSqliteOpenHelper {

    // Nome da base de dados.
    private static final String DATABASE_NAME = "kd_base.db";

    // Versão da base de dados.
    private static final int DATABASE_VERSION = 1;

    // Caso você queria ter apenas uma instancia da base de dados.
    private static ORMLiteHelper mInstance = null;

    // Daos da tabela Usuario.
    private Dao<Localizacao, Integer> localizacaoDao = null;
    private RuntimeExceptionDao<Localizacao, Integer> localizacaoRuntimeDao = null;

    public ORMLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            // TableUtils é responsábel por algumas operações sobre tabelas,
            // como, por exemplo, deletar/inserir tabelas.
            TableUtils.createTable(connectionSource, Localizacao.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Localizacao.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    public static ORMLiteHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ORMLiteHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public Dao<Localizacao, Integer> getLocalizacaoDao() throws java.sql.SQLException {
        if (localizacaoDao == null) {
            localizacaoDao = getDao(Localizacao.class);
        }
        return localizacaoDao;
    }

    public RuntimeExceptionDao<Localizacao, Integer> getLocalizacaoRuntimeDao() {
        if (localizacaoRuntimeDao == null) {
            localizacaoRuntimeDao = getRuntimeExceptionDao(Localizacao.class);
        }
        return localizacaoRuntimeDao;
    }

}