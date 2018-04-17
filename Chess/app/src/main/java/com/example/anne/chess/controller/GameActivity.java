package com.example.anne.chess.controller;


import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.example.anne.chess.R;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Black Pieces
        ImageView a8 = findViewById(R.id.a8);
        ImageView b8 = findViewById(R.id.b8);
        ImageView c8 = findViewById(R.id.c8);
        ImageView d8 = findViewById(R.id.d8);
        ImageView e8 = findViewById(R.id.e8);
        ImageView f8 = findViewById(R.id.f8);
        ImageView g8 = findViewById(R.id.g8);
        ImageView h8 = findViewById(R.id.h8);

        a8.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_br));
        b8.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_bn));
        c8.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_bb));
        d8.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_bq));
        e8.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_bk));
        f8.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_bb));
        g8.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_bn));
        h8.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_br));

        ImageView a7 = findViewById(R.id.a7);
        ImageView b7 = findViewById(R.id.b7);
        ImageView c7 = findViewById(R.id.c7);
        ImageView d7 = findViewById(R.id.d7);
        ImageView e7 = findViewById(R.id.e7);
        ImageView f7 = findViewById(R.id.f7);
        ImageView g7 = findViewById(R.id.g7);
        ImageView h7 = findViewById(R.id.h7);

        a7.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_bp));
        b7.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_bp));
        c7.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_bp));
        d7.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_bp));
        e7.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_bp));
        f7.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_bp));
        g7.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_bp));
        h7.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_bp));

        //White Pieces
        ImageView a2 = findViewById(R.id.a2);
        ImageView b2 = findViewById(R.id.b2);
        ImageView c2 = findViewById(R.id.c2);
        ImageView d2 = findViewById(R.id.d2);
        ImageView e2 = findViewById(R.id.e2);
        ImageView f2 = findViewById(R.id.f2);
        ImageView g2 = findViewById(R.id.g2);
        ImageView h2 = findViewById(R.id.h2);

        a2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wp));
        b2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wp));
        c2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wp));
        d2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wp));
        e2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wp));
        f2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wp));
        g2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wp));
        h2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wp));

        ImageView a1 = findViewById(R.id.a1);
        ImageView b1 = findViewById(R.id.b1);
        ImageView c1 = findViewById(R.id.c1);
        ImageView d1 = findViewById(R.id.d1);
        ImageView e1 = findViewById(R.id.e1);
        ImageView f1 = findViewById(R.id.f1);
        ImageView g1 = findViewById(R.id.g1);
        ImageView h1 = findViewById(R.id.h1);

        a1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wr));
        b1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wn));
        c1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wb));
        d1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wq));
        e1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wk));
        f1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wb));
        g1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wn));
        h1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wr));

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("GameActivity", "Clicked on a1");
            }
        });

        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("GameActivity", "Clicked on a1");
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
