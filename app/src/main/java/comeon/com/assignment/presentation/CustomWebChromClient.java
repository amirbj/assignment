package comeon.com.assignment.presentation;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;


import comeon.com.assignment.R;

import static comeon.com.assignment.presentation.CustomeWebViewClient.ABOUT_PAGE_URL;
import static comeon.com.assignment.presentation.CustomeWebViewClient.LOGIN_PAGE_URL;
import static comeon.com.assignment.presentation.CustomeWebViewClient.getRawFileFromResource;

/**
 * Created by amir on 8/8/18.
 */

public class CustomWebChromClient extends WebChromeClient {
    private ViewGroup rootview;
    private TextView progresscount;
    private Context context;
    private ILoginEventListener listener;

    public CustomWebChromClient(Context context, ViewGroup viewGroup, TextView prgressCount, ILoginEventListener listener) {

    this.rootview = viewGroup;
        this.progresscount = prgressCount;
        this.context =context;
        this.listener = listener;


    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);


            //by clicking about section onReceivedTitle will be called and dummy about page will be initialized through fragment
            if(view.getUrl().startsWith(ABOUT_PAGE_URL)) {
                MainActivity activity = (MainActivity) context;
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, AboutPage.getInstance()).addToBackStack("back").commit();

                //about section will listen to click and therefore in backend it wants to navigate to javascript about page,
                //after initilizing native about page, with the help of goback method we navigate the javascript page in background
                //to its initial status
                if(view.canGoBack()) {
                    view.goBack();
                }
            }

        if(view.getUrl().startsWith(LOGIN_PAGE_URL)){
            //get event of visiting login page to send to firbase
            listener.onLogin();
            //execute js for the number of times user visit login page
            view.loadUrl("JavaScript:" + getRawFileFromResource(R.raw.login, context));


        }


    }

    @Override
    public boolean onJsAlert(final WebView view, String url, String message, final JsResult result) {

        //display alert from js script
        new AlertDialog.Builder(context).setMessage(message).setCancelable(false).
               setPositiveButton("ok", new DialogInterface.OnClickListener() {

                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                       result.confirm();

                   }
               }).show();
        return true;
    }





    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        //in 85 percent the page almost loaded and the rest of that will be loaded when the view has been displayed to user
      if (newProgress < 85) {

            view.setVisibility(View.INVISIBLE);
            progresscount.setText( newProgress + "%");
        } else {

            view.setVisibility(View.VISIBLE);

        }
    }



}
