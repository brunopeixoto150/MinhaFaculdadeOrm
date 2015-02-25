package com.example.bruno.minhafaculdade;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import Models.Disciplina;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Disciplina disciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        Fragment flag;
        switch (position) {
            case 0:
                mTitle = getString(R.string.app_name);
                flag = new MenuPrincipalFragment();
                fragmentManager.replace(R.id.container, flag);
                fragmentManager.addToBackStack(null);
                fragmentManager.commit();
                break;
            case 1:
                mTitle = getString(R.string.btn_menu_disciplinas);
                flag = new MenuDisciplinasFragment();
                fragmentManager.replace(R.id.container, flag);
                fragmentManager.addToBackStack(getString(R.string.btn_menu_disciplinas));
                fragmentManager.commit();
                break;
            case 2:
                mTitle = getString(R.string.btn_menu_provas);
                flag = new MenuProvasFragment();
                fragmentManager.replace(R.id.container, flag);
                fragmentManager.addToBackStack(getString(R.string.btn_menu_provas));
                fragmentManager.commit();
                break;
            case 3:
                mTitle = getString(R.string.btn_menu_provas);
                flag = new MenuConfiguracoesFragment();
                fragmentManager.replace(R.id.container, flag);
                fragmentManager.addToBackStack(getString(R.string.btn_menu_configuracoes));
                fragmentManager.commit();
                break;
            case 4:
                finish();
                break;
        }
    }



    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        FragmentTransaction ft;
        Fragment frag;

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.action_settings:
                frag = new MenuConfiguracoesFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, frag);
                ft.addToBackStack(getString(R.string.btn_menu_configuracoes));
                ft.commit();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void addDisciplina(Disciplina disciplina){
        this.disciplina = disciplina;
    }


}
