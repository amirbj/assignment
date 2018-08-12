package comeon.com.assignment.presentation;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Message;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import comeon.com.assignment.R;
import comeon.com.assignment.data.SPDataStorage;

/**
 * Created by amir on 8/8/18.
 */

public class CustomeWebViewClient extends WebViewClient {

    private Context context;
    public static String HOME_PAGE_URL = "https://mobile.comeon.com/casino/explore";
    public static String SPORT_PAGE_URL = "https://mobile.comeon.com/sportsbook";
    public static String LOGIN_PAGE_URL = "https://mobile.comeon.com/login";
    public static String SHOP_PAGE_URL = "https://mobile.comeon.com/shop";
    public static String ABOUT_PAGE_URL = "https://mobile.comeon.com/about";
    private ProgressDialog progressDialog;


    public CustomeWebViewClient(Context context) {

        this.context = context;



    }


    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Toast.makeText(context.getApplicationContext(), error.getDescription().toString(), Toast.LENGTH_LONG);
        }


    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
      if (view.getUrl().equals(HOME_PAGE_URL)) {
            return false;
        } else{

                return true;
        }




    }



    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);





    }






    @Override
    public void onPageFinished(final WebView view, String url) {
        super.onPageFinished(view, url);

        //saving url for Casino and Sports section
        if (url.equals(SPORT_PAGE_URL) || url.equals(HOME_PAGE_URL)) {
            SPDataStorage.saveData(context.getApplicationContext(), "SAVED_URL", url);
        }
        //execute js file to store and send js alert data regarding the number of times page has been visited
        if (url.startsWith(SHOP_PAGE_URL)) {
            view.loadUrl("JavaScript:" + getRawFileFromResource(R.raw.shop, context));


        }






    }






    public static String getRawFileFromResource(int resourceId, Context context) {
        InputStream raw = context.getResources().openRawResource(resourceId);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        int i;
        try
        {
            i = raw.read();

            while (i != -1)

            {
                stream.write(i);
                i = raw.read();

            }raw.close();
        }

        catch (IOException e)

        {
            e.printStackTrace();

        }
        return stream.toString();

    }

}
