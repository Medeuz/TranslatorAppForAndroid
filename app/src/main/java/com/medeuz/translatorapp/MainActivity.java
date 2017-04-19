package com.medeuz.translatorapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.medeuz.translatorapp.adapter.FragmentsAdapter;
import com.medeuz.translatorapp.view.HistoryFragment;
import com.medeuz.translatorapp.view.TranslatorFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;

public class MainActivity extends Activity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener {

    /**
     * View of view pager where located fragments with content
     */
    @BindView(R.id.pager)
    ViewPager mViewPager;

    /**
     * View of Bottom Navigation
     */
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    /**
     * Unbinder for views
     */
    private Unbinder mUnbinder;

    /**
     * Previous selected item in bottom navigation
     */
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);

        FragmentsAdapter adapter = new FragmentsAdapter(getFragmentManager());
        adapter.addFragment(TranslatorFragment.newInstance(getString(R.string.translate)));
        adapter.addFragment(HistoryFragment.newInstance(getString(R.string.history), false));
        adapter.addFragment(HistoryFragment.newInstance(getString(R.string.favorite), true));

        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(this);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_translator:
                mViewPager.setCurrentItem(0, true);
                break;
            case R.id.action_history:
                mViewPager.setCurrentItem(1, true);
                break;
            case R.id.action_favorite:
                mViewPager.setCurrentItem(2, true);
                break;
            default:
                mViewPager.setCurrentItem(0, true);
                break;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        }
        else {
            mBottomNavigationView.getMenu().getItem(0).setChecked(false);
        }

        mBottomNavigationView.getMenu().getItem(position).setChecked(true);
        prevMenuItem = mBottomNavigationView.getMenu().getItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
