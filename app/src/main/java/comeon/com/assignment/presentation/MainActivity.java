package comeon.com.assignment.presentation;


import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import comeon.com.assignment.R;
import comeon.com.assignment.data.FireBaseDB;
import comeon.com.assignment.data.SPDataStorage;
import comeon.com.assignment.model.User;

import static comeon.com.assignment.presentation.CustomeWebViewClient.HOME_PAGE_URL;
import static comeon.com.assignment.presentation.CustomeWebViewClient.LOGIN_PAGE_URL;


public class MainActivity extends AppCompatActivity implements ILoginEventListener {

    private ViewGroup progressbar;
    private TextView progresscount;
    private   WebView homepage;
    private FirebaseAnalytics firebaseAnalytics;
    private ILoginEventListener eventListener;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        progressbar = (ViewGroup) findViewById(R.id.content_main);
        progresscount = (TextView) findViewById(R.id.progress_count);

        try {
            //initializing firbase
            firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        eventListener = this;

        initHomePage();
        homepage.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                if (homepage.getUrl().startsWith(LOGIN_PAGE_URL)) {
                    User user = new User();
                    user.setMobileSeriaNo(Build.SERIAL);
                    user.setLocale(getCookie(homepage.getUrl(), "locale"));
                    user.setLastProduct(getCookie(homepage.getUrl(), "lastProduct"));
                    user.setUseMobile(getCookie(homepage.getUrl(), "useMobile"));

                    FireBaseDB.saveinFirebaseDB(user);


                }


                return false;
            }
        });


    }

    public String getCookie(String siteName,String CookieName){
        String CookieValue = null;

        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(siteName);
        if(cookies != null){
            String[] temp=cookies.split(";");
            for (String ar1 : temp ){
                if(ar1.contains(CookieName)){
                    String[] temp1=ar1.split("=");
                    CookieValue = temp1[1];
                }
            }
        }
        return CookieValue;
    }



    private void initHomePage() {
        homepage = (WebView) findViewById(R.id.homepage_webview);
        WebSettings settings = homepage.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadsImagesAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        }

        homepage.setWebChromeClient(new CustomWebChromClient(MainActivity.this, progressbar, progresscount, eventListener));
        CustomeWebViewClient webViewClient = new CustomeWebViewClient(MainActivity.this);

        homepage.setWebViewClient(webViewClient);
        //load page from section which user visit app(casion or sport section)
        homepage.loadUrl(SPDataStorage.loadData(this, "SAVED_URL", HOME_PAGE_URL));



    }




    //when navigate to login page
    @Override
    public void onLogin() {
        Bundle params = new Bundle();
        //send event log to firebase
        params.putString(FirebaseAnalytics.Param.ITEM_NAME, "visitNo");
        firebaseAnalytics.logEvent("Visit_LoginScreen",params);

    }

    @Override
    protected void onPause() {
        super.onPause();
        homepage.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        homepage.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        homepage.destroy();
        homepage = null;
    }
}



