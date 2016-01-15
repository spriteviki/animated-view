package com.spriteviki.animatedview.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.spriteviki.animatedview.CircularAnimatedDrawable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircularAnimatedDrawable circularAnimatedDrawable = new CircularAnimatedDrawable(getResources().getColor(R.color.colorBar), 12f);
        circularAnimatedDrawable.start();
        ((ImageView) findViewById(R.id.image_circular_animated)).setImageDrawable(circularAnimatedDrawable);
    }
}
