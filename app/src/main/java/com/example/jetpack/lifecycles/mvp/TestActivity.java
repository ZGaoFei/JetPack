package com.example.jetpack.lifecycles.mvp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jetpack.R;

public class TestActivity extends BaseActivity implements TestPresenter.TestView {

    private TestPresenter presenter;

    private TextView textView;

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView = findViewById(R.id.text_view);

        textView.postDelayed(() -> presenter.getData(), 2000);
    }

    @Override
    protected void addPresenter() {
        presenter = new TestPresenter(this);
    }

    @Override
    public void update() {
        textView.setText("你好！");
    }
}
