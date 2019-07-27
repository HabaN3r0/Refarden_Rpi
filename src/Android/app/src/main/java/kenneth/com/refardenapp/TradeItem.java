package kenneth.com.refardenapp;

import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by 1002215 on 27/7/19.
 */

public class TradeItem {

    private int mImageResource;
    private String mTitle;
    private String mDescription;
    private int mstar1;
    private int mstar2;
    private int mstar3;
    private int mstar4;
    private int mstar5;


    public TradeItem(int imageResource, String title, String description, int star1, int star2, int star3, int star4, int star5) {
        mImageResource = imageResource;
        mTitle = title;
        mDescription = description;
        mstar1 = star1;
        mstar2 = star2;
        mstar3 = star3;
        mstar4 = star4;
        mstar5 = star5;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getStar1() {
        return mstar1;
    }

    public int getStar2() {
        return mstar2;
    }

    public int getStar3() {
        return mstar3;
    }

    public int getStar4() {
        return mstar4;
    }

    public int getStar5() {
        return mstar5;
    }

}
