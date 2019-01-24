package com.hsy.study.myproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.sqlbrite3.BriteDatabase;
import com.squareup.sqldelight.Transacter;
import com.squareup.sqldelight.android.AndroidSqliteDriver;

import javax.inject.Inject;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class MainActivity extends AppCompatActivity {

    @Inject
    BriteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerMainComponent.builder()
                .mainModule(new MainModule(getApplication()))
                .build()
                .inject(this);

        AndroidSqliteDriver driver = new AndroidSqliteDriver(Database.Schema.INSTANCE, this, "test.db");

        Database database1 = new Database(driver);
        database1.getHockeyPlayerQueries().insert_data("sadf", 123123);
        database1.transaction(true, new Function1<Transacter.Transaction, Unit>() {
            @Override
            public Unit invoke(Transacter.Transaction transaction) {
                return null;
            }
        });
    }
}
