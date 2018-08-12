package comeon.com.assignment.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import comeon.com.assignment.R;

/**
 * Created by amir on 8/10/18.
 */

public class AboutPage extends Fragment {

    private static Fragment getInstance;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_layout, container, false);
        handleBackBottom();

        return view;
    }


   public void handleBackBottom(){
       if(getView()!= null) {
           this.getView().setOnKeyListener(new View.OnKeyListener() {
               @Override
               public boolean onKey(View view, int i, KeyEvent keyEvent) {
                   if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                       getActivity().getSupportFragmentManager().popBackStack();
                       return true;
                   }
                   return false;
               }

           });
       }

    }

    public static Fragment getInstance(){


        if(getInstance == null){

            getInstance = new AboutPage();
        }

        return getInstance;

    }

}
