package com.rongtuoyouxuan.chatlive.crtuikit;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.rongtuoyouxuan.chatlive.stream.R;

public abstract class BaseNewFragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pl_libutil_layout_base_fragment_activity);
        initIntent();
        initFragment();
    }

    protected void initIntent() {

    }

    protected void initFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, CreateFragment()).commitAllowingStateLoss();
    }

    public abstract Fragment CreateFragment();
}
