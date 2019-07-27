package kenneth.com.refardenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by 1002215 on 20/7/19.
 */

public class LogoutFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);

        //Set Fragment Title
        getActivity().setTitle("Logout");


        return view;
    }

}
