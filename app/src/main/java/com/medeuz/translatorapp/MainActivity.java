package com.medeuz.translatorapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.medeuz.translatorapp.view.HistoryFragment;
import com.medeuz.translatorapp.view.TranslatorFragment;

import io.realm.Realm;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_main);
        setCurrentFragment(new TranslatorFragment(), false);
        //setCurrentFragment(new HistoryFragment(), false);
    }

    public void setCurrentFragment(Fragment fragment, boolean addToBackStack) {
        if (!isFinishing()) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.replace(R.id.fragment_container, fragment);
            if (addToBackStack) {
                ft.addToBackStack(fragment.getTag());
            }
            ft.commit();
        }
    }

}
