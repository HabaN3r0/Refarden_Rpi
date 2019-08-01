package kenneth.com.refardenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by 1002215 on 20/7/19.
 */

public class TradeFragment extends Fragment implements TradeAdapter.OnClickListener{

    private static final String TAG = "Trade Fragment";
    private ArrayList<TradeItem> mTradeList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFab;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trade, container, false);

        //Set Fragment Title
        getActivity().setTitle("Trade Market");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

//        //Create Recycler view list
//        mTradeList.add(new TradeItem(R.drawable.melon, "Melon",
//                "This melon has been grown since 24/4/19 and has a size of 10x10x10cm and has a bright green colour",
//                R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star,
//                R.drawable.ic_full_star));
//        mTradeList.add(new TradeItem(R.drawable.lemon, "Lemon", "Grown since 19/5/19",
//                R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star,
//                R.drawable.ic_half_star));
//        mTradeList.add(new TradeItem(R.drawable.basil, "Basil", "Fresh basil grown since 03/05/19",
//                R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star,
//                R.drawable.ic_half_star));
//        mTradeList.add(new TradeItem(R.drawable.basil, "Basil", "Fresh basil grown since 03/05/19",
//                R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star,
//                R.drawable.ic_half_star));
//        mTradeList.add(new TradeItem(R.drawable.basil, "Basil", "Fresh basil grown since 03/05/19",
//                R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star,
//                R.drawable.ic_half_star));

        myRef.child("Trading Platform").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Call another function to update the UI on the screen by passing in datasnapshot which is like the info
                extractTradeList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        mRecyclerView = view.findViewById(R.id.tradeRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new TradeAdapter(mTradeList, this);
        mFab = view.findViewById(R.id.addTradeFAB);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        mFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeActivity();
                    }
                }
        );
        return view;
    }

    public void changeActivity(){
        Intent intent = new Intent(this.getActivity(), NewTradePostActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this.getActivity(), TradeItemActivity.class);
        intent.putExtra("uid", mTradeList.get(position).getmUid());
        intent.putExtra("timeStamp", mTradeList.get(position).getmTimeStamp());
        intent.putExtra("userName", mTradeList.get(position).getmUserName());
        intent.putExtra("plantImage", mTradeList.get(position).getmPlantImage());
        intent.putExtra("plantType", mTradeList.get(position).getmPlantType());
        intent.putExtra("plantsWanted", mTradeList.get(position).getmPlantsWanted());
        intent.putExtra("description", mTradeList.get(position).getmDescription());
        intent.putExtra("star1", mTradeList.get(position).getmStar1());
        intent.putExtra("star2", mTradeList.get(position).getmStar2());
        intent.putExtra("star3", mTradeList.get(position).getmStar3());
        intent.putExtra("star4", mTradeList.get(position).getmStar4());
        intent.putExtra("star5", mTradeList.get(position).getmStar5());
        startActivity(intent);
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TradeItemFragment()).commit();
    }

    private void extractTradeList(DataSnapshot dataSnapshot) {

        //Running through "Trading Platform" folder
        for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
            //Running through "UID" folder
            int plantImage = R.drawable.melon;
            String uid = "Nil";
            String timeStamp = "Nil";
            String plantType = "Nil";
            String plantsWanted = "Nil";
            String description = "Nil";
            String userName = "Nil";
            String request = "Nil";
            int star1 = 0;
            int star2 = 0;
            int star3 = 0;
            int star4 = 0;
            int star5 = 0;

            Log.d(TAG, "uid is:  " + ds1.getKey());
            uid = ds1.getKey();
            for (DataSnapshot ds2 : ds1.getChildren()) {
                Log.d(TAG, "timeStamp is:  " + ds2.getKey());
                timeStamp = ds2.getKey();
                for (DataSnapshot ds3 : ds2.getChildren()) {
                    Log.d(TAG, "Info header is:  " + ds3.getKey());
                    if (ds3.getKey().equals("Description")) {
                        if (ds3.getValue().toString().equals(null)) {
                        } else {
                            description = ds3.getValue().toString();
                        }
                    } else if (ds3.getKey().equals("Name")) {
                        if (ds3.getValue().toString().equals(null)) {
                        } else {
                            userName = ds3.getValue().toString();
                        }
                    } else if (ds3.getKey().equals("Plant Type")) {
                        if (ds3.getValue().toString().equals(null)) {
                        } else {
                            plantType = ds3.getValue().toString();
                        }

                    } else if (ds3.getKey().equals("Plants Wanted")) {
                        if (ds3.getValue().toString().equals(null)) {
                        } else {
                            plantsWanted = ds3.getValue().toString();
                        }
                    } else if (ds3.getKey().equals("Request")) {
                        if (ds3.getValue().toString().equals(null)) {
                        } else {
                            request = ds3.getValue().toString();
                        }
                    } else if (ds3.getKey().equals("Stars")) {
                        if (ds3.getValue().toString().equals(null)) {
                        } else {
                            if (ds3.getValue().toString().equals("5")) {
                                star1 = R.drawable.ic_full_star;
                                star2 = R.drawable.ic_full_star;
                                star3 = R.drawable.ic_full_star;
                                star4 = R.drawable.ic_full_star;
                                star5 = R.drawable.ic_full_star;
                            } else if (ds3.getValue().toString().equals("4")) {
                                star1 = R.drawable.ic_full_star;
                                star2 = R.drawable.ic_full_star;
                                star3 = R.drawable.ic_full_star;
                                star4 = R.drawable.ic_full_star;
                                star5 = R.drawable.ic_empty_star;
                            } else if (ds3.getValue().toString().equals("3")) {
                                star1 = R.drawable.ic_full_star;
                                star2 = R.drawable.ic_full_star;
                                star3 = R.drawable.ic_full_star;
                                star4 = R.drawable.ic_empty_star;
                                star5 = R.drawable.ic_empty_star;
                            } else if (ds3.getValue().toString().equals("2")) {
                                star1 = R.drawable.ic_full_star;
                                star2 = R.drawable.ic_full_star;
                                star3 = R.drawable.ic_empty_star;
                                star4 = R.drawable.ic_empty_star;
                                star5 = R.drawable.ic_empty_star;
                            } else if (ds3.getValue().toString().equals("1")) {
                                star1 = R.drawable.ic_full_star;
                                star2 = R.drawable.ic_empty_star;
                                star3 = R.drawable.ic_empty_star;
                                star4 = R.drawable.ic_empty_star;
                                star5 = R.drawable.ic_empty_star;
                            } else {
                                star1 = R.drawable.ic_empty_star;
                                star2 = R.drawable.ic_empty_star;
                                star3 = R.drawable.ic_empty_star;
                                star4 = R.drawable.ic_empty_star;
                                star5 = R.drawable.ic_empty_star;
                            }
                        }
                    }

                }
                mTradeList.add((new TradeItem(uid, timeStamp, plantImage, plantType, plantsWanted, description, userName, request, star1, star2, star3, star4, star5)));
            }
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

}
