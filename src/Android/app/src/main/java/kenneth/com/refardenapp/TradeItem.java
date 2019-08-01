package kenneth.com.refardenapp;

import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by 1002215 on 27/7/19.
 */

public class TradeItem {

    private String mUid;
    private String mTimeStamp;
    private int mPlantImage;
    private String mPlantType;
    private String mPlantsWanted;
    private String mDescription;
    private String mUserName;
    private String mRequest;
    private int mstar1;
    private int mstar2;
    private int mstar3;
    private int mstar4;
    private int mstar5;


    public TradeItem(String uid, String timeStamp, int plantImage, String plantType, String plantsWanted, String description, String userName, String request, int star1, int star2, int star3, int star4, int star5) {
        mUid = uid;
        mTimeStamp = timeStamp;
        mPlantImage = plantImage;
        mPlantType = plantType;
        mPlantsWanted = plantsWanted;
        mDescription = description;
        mUserName = userName;
        mRequest = request;
        mstar1 = star1;
        mstar2 = star2;
        mstar3 = star3;
        mstar4 = star4;
        mstar5 = star5;
    }

    public String getmTimeStamp() {
        return mTimeStamp;
    }

    public String getmUid() {
        return mUid;
    }

    public int getmPlantImage() {
        return mPlantImage;
    }

    public String getmPlantType() {
        return mPlantType;
    }

    public String getmPlantsWanted() { return mPlantsWanted; }

    public String getmDescription() {
        return mDescription;
    }

    public String getmUserName() { return mUserName; }

    public String getmRequest() { return mRequest; }

    public int getmStar1() {
        return mstar1;
    }

    public int getmStar2() {
        return mstar2;
    }

    public int getmStar3() {
        return mstar3;
    }

    public int getmStar4() {
        return mstar4;
    }

    public int getmStar5() {
        return mstar5;
    }

}
