package kenneth.com.refardenapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 1002215 on 27/7/19.
 */

public class TradeAdapter extends RecyclerView.Adapter<TradeAdapter.TradeViewHolder> {

    private ArrayList<TradeItem> mTradeList;

    public static class TradeViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTitleTextView;
        public TextView mDescriptionTextView;
        public ImageView mStar1;
        public ImageView mStar2;
        public ImageView mStar3;
        public ImageView mStar4;
        public ImageView mStar5;

        public TradeViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.tradeImage);
            mTitleTextView = itemView.findViewById(R.id.tradeTitle);
            mDescriptionTextView = itemView.findViewById(R.id.tradeDescription);
            mStar1 = itemView.findViewById(R.id.tradeStar1);
            mStar2 = itemView.findViewById(R.id.tradeStar2);
            mStar3 = itemView.findViewById(R.id.tradeStar3);
            mStar4 = itemView.findViewById(R.id.tradeStar4);
            mStar5 = itemView.findViewById(R.id.tradeStar5);

        }
    }

    public TradeAdapter(ArrayList<TradeItem> tradeList) {

        mTradeList = tradeList;
    }

    @Override
    public TradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trade_item, parent, false);
        TradeViewHolder tvh = new TradeViewHolder(view);
        return  tvh;
    }

    @Override
    public void onBindViewHolder(TradeViewHolder holder, int position) {
        TradeItem currentItem = mTradeList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTitleTextView.setText(currentItem.getTitle());
        holder.mDescriptionTextView.setText(currentItem.getDescription());
        holder.mStar1.setImageResource(currentItem.getStar1());
        holder.mStar2.setImageResource(currentItem.getStar2());
        holder.mStar3.setImageResource(currentItem.getStar3());
        holder.mStar4.setImageResource(currentItem.getStar4());
        holder.mStar5.setImageResource(currentItem.getStar5());
    }

    @Override
    public int getItemCount() {
        return mTradeList.size();
    }
}
