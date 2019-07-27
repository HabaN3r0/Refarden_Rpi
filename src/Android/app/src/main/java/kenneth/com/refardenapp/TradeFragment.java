package kenneth.com.refardenapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by 1002215 on 20/7/19.
 */

public class TradeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trade, container, false);

        //Set Fragment Title
        getActivity().setTitle("Trade Market");

        ArrayList<TradeItem> tradeList = new ArrayList<>();
        tradeList.add(new TradeItem(R.drawable.melon, "Melon",
                "This melon has been grown since 24/4/19 and has a size of 10x10x10cm and has a bright green colour",
                R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star,
                R.drawable.ic_full_star));
        tradeList.add(new TradeItem(R.drawable.lemon, "Lemon", "Grown since 19/5/19",
                R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star,
                R.drawable.ic_half_star));
        tradeList.add(new TradeItem(R.drawable.basil, "Basil", "Fresh basil grown since 03/05/19",
                R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star, R.drawable.ic_full_star,
                R.drawable.ic_half_star));

        mRecyclerView = view.findViewById(R.id.tradeRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new TradeAdapter(tradeList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
