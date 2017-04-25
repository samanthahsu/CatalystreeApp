package com.example.catalystreeapp.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.catalystreeapp.R;

public class LandingActivity extends Activity {

    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        go = (Button) findViewById(R.id.buttonCont);
        go.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                      Intent intent = new Intent(LandingActivity.this, MainActivity.class);
                                      intent.putExtra("caller", "Home");
                                      startActivity(intent);
                                  }
                              }
        );
    }
}