package com.projetotreinoacademia.treino.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.projetotreinoacademia.R;

public class BaseActivity extends /*livroandroid.lib.activity.BaseActivity*/ AppCompatActivity {

    protected  void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }
    }
}
