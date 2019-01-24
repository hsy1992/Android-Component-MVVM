package com.hsy.study.myproject.db;

import android.app.Application;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import android.util.Log;

import com.squareup.sqlbrite3.BriteDatabase;
import com.squareup.sqlbrite3.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;

import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Factory;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;

/**数据库
 * @author haosiyuan
 * @date 2019/1/6 下午8:00
 */
@Module
public final class DbModule {

    @Provides
    @Singleton
    SqlBrite  provideSqlBrite(){
        return new SqlBrite.Builder()
                .logger(new SqlBrite.Logger() {
                    @Override
                    public void log(String message) {
                        Log.e("test", message);
                    }
                }).build();
    }

    @Provides
    @Singleton
    BriteDatabase provideDatabase(SqlBrite sqlBrite, Application application){

        Configuration configuration = Configuration.builder(application)
                .name("test")
                .callback(new DbCallBack())
                .build();

        Factory factory = new FrameworkSQLiteOpenHelperFactory();
        SupportSQLiteOpenHelper helper = factory.create(configuration);
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
        db.setLoggingEnabled(true);
        return db;
    }

}
