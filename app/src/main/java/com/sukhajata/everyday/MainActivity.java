package com.sukhajata.everyday;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.concurrent.Phaser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        IntroSlideCompletedListener,
        SoundHelper.LoadAudioFilesCompletedListener{

    public static final int CODE_INTRO = 1;
    public static final int CODE_TONES = 2;

    private String lastQuery;

    private String currentSubCat;

    private MenuExpandableListAdapter menuExpandableListAdapter;

    private ExpandableListView expandableListView;

    private int newVoice;

    private AlertDialog alert;

    private SearchView searchView;

    private AdView mAdView;

    //AlphaAnimation inAnimation;
    //AlphaAnimation outAnimation;

    FrameLayout progressBarHolder;

    public enum ContentType {
        PHRASES, SEARCH_RESULTS, FAVOURITES, INSTRUCTIONS
    }

    private ContentType currentContentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());

        //GlobalData.getInstance(this).mainActivity = this;

        //register hardware volume buttons to affect audio volume rather than ringer volume
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //adview
        MobileAds.initialize(this, getString(R.string.banner_ad_unit_id));

        progressBarHolder = (FrameLayout)findViewById(R.id.progressBarHolder);
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        expandableListView = (ExpandableListView)findViewById(R.id.category_list);

        ArrayList<Category> categories = EverydayLanguageDbHelper.getInstance(this).getCategories();
        //get the subcategories for each category
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            int catId = category.categoryId;
            ArrayList<SubCategory> subCategories = EverydayLanguageDbHelper.getInstance(this).getSubCategories(catId);
            category.setSubCategories(subCategories);
        }

        menuExpandableListAdapter = new MenuExpandableListAdapter(this, categories);
        expandableListView.setAdapter(menuExpandableListAdapter);

        //check if this is the first launch
        //check if a voice has been selected
        GlobalData.getInstance(this).currentVoice = EverydayLanguageDbHelper.getInstance(this).getVoice();
        if (GlobalData.getInstance(this).currentVoice == -1) {
            //hide actionbar initially
            getSupportActionBar().hide();

            //show instructions
            IntroFragment intro = new IntroFragment();

            FragmentManager fragmentManager = getFragmentManager();
            // GlobalData.getInstance().phrasesFragment = new MainFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, intro)
                    .commit();
            //Log.d("subCategoryName", subCategoryName);
            // getSupportActionBar().setTitle(currentSubCat);
        } else {
            // GlobalData.getInstance().currentVoice = voice;

            //openDrawer
            //mNavigationDrawerFragment.openDrawer();
            drawer.openDrawer(Gravity.LEFT);

            //setup sound
            //SoundHelper.getInstance(this);

            showAds();

            //increment usage
            EverydayLanguageDbHelper.getInstance(this).incrementUsage();
            //int usage = EverydayLanguageDbHelper.getUsage();

        }

    }

    @Override
    public void onRestart() {
        super.onRestart();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(getApplicationContext(), MainActivity.class)));

        // Assumes current activity is the searchable activity
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (item.getItemId()) {
            case R.id.search:
               // showActionBarIcons = false;
                invalidateOptionsMenu();
                return false;

            case R.id.favourite:
                ArrayList<SearchResult> favourites = EverydayLanguageDbHelper
                        .getInstance(this)
                        .getFavourites();
                showFavourites(favourites);
                return true;

            case R.id.switch_voice:
                //showSelectVoiceDialog()
                if (GlobalData.getInstance(this).currentVoice == 0) {
                    GlobalData.getInstance(this).currentVoice = 1;
                } else {
                    GlobalData.getInstance(this).currentVoice = 0;
                }
                loadVoice();
                //GlobalData.getInstance().dbHelper.switchVoice();

                //replace existing content
                //if (currentSubCat != null) {
                //  switchContent(currentSubCat);
                // }
                return true;

            case R.id.rate_app:
                rateApp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchContent(String subCategoryName) {
        currentSubCat = subCategoryName;
        currentContentType = ContentType.PHRASES;

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawers();

        int subCatId = GlobalData.getInstance(this).currentSubCatId;

        //load new fragment with subcategory items
        MainFragment fragment = MainFragment.newInstance(subCatId);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, fragment)
                .commit();


        getSupportActionBar().setTitle(currentSubCat);
    }

    public void onLoadCompleted() {
        //hide loading animation
       // Toast.makeText(this, "Hiding animation", Toast.LENGTH_SHORT).show();
        /*
        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBarHolder.setVisibility(View.GONE);
        */
    }

    public void showInstructions() {

        //show action bar
        getSupportActionBar().show();


        InstructionsFragment instructionsFragment = new InstructionsFragment();
        //Log.d("showingInstructions", "instructions");
        FragmentManager fragmentManager = getFragmentManager();
        // GlobalData.getInstance().phrasesFragment = new MainFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, instructionsFragment)
                .commit();

        //show ads
        showAds();

    }

    public void showAds()
    {
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("08A806511F416069EC553D03B934329A")
                //.addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build();
        mAdView.loadAd(adRequest);
    }


    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }


    private void showFavourites(ArrayList<SearchResult> favourites) {
        currentContentType = ContentType.FAVOURITES;


        /*
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBarHolder.setVisibility(View.VISIBLE);
        */
        /*
        SoundHelper
                .getInstance(this)
                .LoadSearchAudioFiles(favourites, this);
        */
        FragmentManager fragmentManager = getFragmentManager();
        SearchResultsFragment fragment = SearchResultsFragment.newInstance(favourites);
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment)
                .commit();

        getSupportActionBar().setTitle("Favourites");
    }


    public void showSearchResults() {

        ArrayList<SearchResult> searchResults = new ArrayList<>();


        if (lastQuery != null) {
            searchResults = EverydayLanguageDbHelper
                    .getInstance(this)
                    .getSearchResults(lastQuery);

            currentContentType = ContentType.SEARCH_RESULTS;

            FragmentManager fragmentManager = getFragmentManager();
            SearchResultsFragment searchResultsFragment = SearchResultsFragment.newInstance(searchResults);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, searchResultsFragment)
                    .commit();

            getSupportActionBar().setTitle("Search Results");
        }



        //hide searchView so keyboard disappears
        searchView.setQuery("", false);
        searchView.setIconified(true);


        //DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        //mDrawerLayout.closeDrawers();
    }

    /*
    private void showSelectVoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder//.setMessage(R.string.select_voice_message)
                .setTitle(getString(R.string.select_voice))
                .setMessage(getString(R.string.select_voice_message))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (newVoice != GlobalData.getInstance(this).currentVoice) {
                            GlobalData.getInstance(this).currentVoice = newVoice;

                            loadVoice();
                        }
                        showInstructionsContentsDialog();
                    }
                })
                // .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                //    public void onClick(DialogInterface dialog, int id) {
                //       dialog.cancel();
                //    }
                //})
                .setSingleChoiceItems(R.array.voices, GlobalData.getInstance().currentVoice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newVoice = which;
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                });
        alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }
*/
    public void loadVoice()
    {
        //change database
        EverydayLanguageDbHelper.getInstance(this).switchVoice();

        //change content
        if (currentContentType == ContentType.SEARCH_RESULTS) {
            showSearchResults();
        } else if (currentContentType == ContentType.FAVOURITES) {
            ArrayList<SearchResult> favourites =
                    EverydayLanguageDbHelper.getInstance(this).getFavourites();
            showFavourites(favourites);
        } else if (currentContentType == ContentType.PHRASES && currentSubCat != null) {
            switchContent(currentSubCat);
        }
    }

    public void favourite_click(View view) {
        ImageView fav = (ImageView)view;
        Phrase phrase = (Phrase)fav.getTag();
        phrase.isFavourite = !phrase.isFavourite;

        if (phrase.isFavourite) {
            fav.setImageResource(R.drawable.ic_favourite_active);
        } else {
            fav.setImageResource(R.drawable.ic_favourite);
        }

        EverydayLanguageDbHelper
                .getInstance(this)
                .updateIsFavourite(phrase.isFavourite, phrase.pid, phrase.firstLanguage);

    }

    public void speaker_click(View view) {
        ImageView speaker = (ImageView)view;
        Phrase phrase = (Phrase)speaker.getTag();
        SoundHelper.getInstance(this).playPhrase(phrase);

    }

    private void showInstructionsContentsDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder//.setMessage(R.string.select_voice_message)
                .setTitle(getString(R.string.instructions_contents_title))
                .setMessage(getString(R.string.instructions_contents_message))
                .setPositiveButton(getString(R.string.ok), null);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showToneExplanation() {
        ToneExplanationFragment toneExplanationFragment = new ToneExplanationFragment();
        FragmentManager fragmentManager = getFragmentManager();
        // GlobalData.getInstance().phrasesFragment = new MainFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, toneExplanationFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            lastQuery =
                    data.getStringExtra(SearchResultsActivity.ARG_NAME_SEARCH_QUERY);

            showSearchResults();


        }
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            lastQuery = intent.getStringExtra(SearchManager.QUERY);
            showSearchResults();
            //GlobalData.getInstance(this).mainActivity.showSearchResults();

        }


    }


    public void onSlideCompleted(int code) {
        switch (code) {
            case CODE_INTRO:
                EverydayLanguageDbHelper.getInstance(this).switchVoice();
                showToneExplanation();
                break;
            case CODE_TONES:
                showInstructions();
                break;
        }
    }



}
