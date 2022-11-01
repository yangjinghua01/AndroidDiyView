package com.rgsc.myapplication.SwipeBackLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.rgsc.myapplication.R;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class IntentActivity extends SwipeBackActivity {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        btn = (Button) findViewById(R.id.jump);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntentActivity.this, IntentTestMainActivity.class));
            }
        });
    }
}