package info.fandroid.quizapp.quizapplication.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.ads.AdView;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import info.fandroid.quizapp.quizapplication.R;
import info.fandroid.quizapp.quizapplication.adapters.CategoryAdapter;
import info.fandroid.quizapp.quizapplication.constants.AppConstants;
import info.fandroid.quizapp.quizapplication.data.sqlite.NotificationDbController;
import info.fandroid.quizapp.quizapplication.listeners.ListItemClickListener;
import info.fandroid.quizapp.quizapplication.models.notification.NotificationModel;
import info.fandroid.quizapp.quizapplication.models.quiz.CategoryModel;
import info.fandroid.quizapp.quizapplication.utilities.ActivityUtilities;
import info.fandroid.quizapp.quizapplication.utilities.AdsUtilities;
import info.fandroid.quizapp.quizapplication.utilities.AppUtilities;
import info.fandroid.quizapp.quizapplication.utilities.DialogUtilities;

public class predmet extends BaseActivity implements DialogUtilities.OnCompleteListener, BillingProcessor.IBillingHandler{
    private Activity activity;
    private Context context;

    private Toolbar toolbar;

    private RelativeLayout mNotificationView;
    private AccountHeader header = null;
    private Drawer drawer = null;

    private ArrayList<CategoryModel> categoryList;
    private CategoryAdapter adapter = null;
    private RecyclerView recyclerView;

    predmet.CheckSubscribe checks;
    BillingProcessor bp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predmet);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activity = predmet.this;
        context = getApplicationContext();
        categoryList = new ArrayList<>();


        mNotificationView = (RelativeLayout) findViewById(R.id.notificationView);
        recyclerView = (RecyclerView) findViewById(R.id.rvContentScore);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false));

        adapter = new CategoryAdapter(context, activity, categoryList);
        recyclerView.setAdapter(adapter);

        initLoader();
        loadData();
        initListener();

        final IProfile profile = new ProfileDrawerItem().withIcon(R.drawable.ic_dev);


    }
    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            AppUtilities.tapPromtToExit(this);
        }
    }

    private void loadData() {
        showLoader();
        loadJson();

        // show banner ads
        AdsUtilities.getInstance(context).showBannerAd((AdView) findViewById(R.id.adsView));
    }

    private void loadJson() {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try{
            br = new BufferedReader(new InputStreamReader(getAssets().open(AppConstants.CONTENT_FILE)));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        parseJson(sb.toString());
    }

    private void parseJson(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.JSON_KEY_ITEMS);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                String categoryId = object.getString(AppConstants.JSON_KEY_CATEGORY_ID);
                String categoryName = object.getString(AppConstants.JSON_KEY_CATEGORY_NAME);

                categoryList.add(new CategoryModel(categoryId, categoryName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        hideLoader();
        adapter.notifyDataSetChanged();
    }

    private void initListener() {

        //notification view click listener


        // recycler list item click listener
        adapter.setItemClickListener(new ListItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {

                //show snackbar and return if not purchased

                boolean purchased = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(AppConstants.PRODUCT_ID_BOUGHT, false);
                boolean subscribed = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(AppConstants.PRODUCT_ID_SUBSCRIBE, false);

                if (position > 3 && !purchased && !subscribed) {
                    // Toast.makeText(mActivity, R.string.alert_for_purchase , Toast.LENGTH_SHORT).show();
                    Snackbar.make(view, R.string.alert_for_purchase, Snackbar.LENGTH_LONG)
                            .setAction("Ок", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityUtilities.getInstance().invokeNewActivity(activity, SettingsActivity.class, true);
                                }
                            }).setDuration(4000).show();
                    return;
                }

                CategoryModel model = categoryList.get(position);
                ActivityUtilities.getInstance().invokeCommonQuizActivity(activity, QuizPromptActivity.class, model.getCategoryId(), true);
            }
        });
    }
    // received new broadcast
    private BroadcastReceiver newNotificationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            initNotification();
        }
    };

    public void initNotification() {
        NotificationDbController notificationDbController = new NotificationDbController(context);
        TextView notificationCount = (TextView) findViewById(R.id.notificationCount);


        ArrayList<NotificationModel> notiArrayList = notificationDbController.getUnreadData();

        if (notiArrayList != null && !notiArrayList.isEmpty()) {
            int totalUnread = notiArrayList.size();
            if (totalUnread > 0) {
                notificationCount.setVisibility(View.VISIBLE);
                notificationCount.setText(String.valueOf(totalUnread));
            } else {
                notificationCount.setVisibility(View.INVISIBLE);
            }
        }

    }



    @Override
    protected void onResume() {
        super.onResume();

        //register broadcast receiver
        IntentFilter intentFilter = new IntentFilter(AppConstants.NEW_NOTI);
        LocalBroadcastManager.getInstance(this).registerReceiver(newNotificationReceiver, intentFilter);

        initNotification();

        // load full screen ad
        AdsUtilities.getInstance(context).loadFullScreenAd(activity);
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {

    }

    @Override
    public void onPurchaseHistoryRestored() {

        if (bp.isSubscribed(SUBSCRIPTION_ID())) {
            setIsSubscribe(true, context);
            Log.v("TAG", "Subscribe actually restored");

        } else {
            setIsSubscribe(false, context);
        }
    }

    private String SUBSCRIPTION_ID(){
        return getResources().getString(R.string.subscription_id);
    }
    public void setIsSubscribe(boolean purchased, Context c){
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(c);

        SharedPreferences.Editor editor= prefs.edit();

        editor.putBoolean(AppConstants.PRODUCT_ID_SUBSCRIBE, purchased);
        editor.apply();
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    public void onComplete(Boolean isOkPressed, String viewIdText) {
        if (isOkPressed) {
            if (viewIdText.equals(AppConstants.BUNDLE_KEY_EXIT_OPTION)) {
                activity.finishAffinity();
            }
        }

    }

    private class CheckSubscribe extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String license = getResources().getString(R.string.google_play_license);
            bp = new BillingProcessor(context, license, predmet.this);
            bp.loadOwnedPurchasesFromGoogle();
            return null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //unregister broadcast receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(newNotificationReceiver);

    }
}
