package com.dogaozkaraca.bitpops.AdapterItems;

import java.util.Date;

/**
 * Created by doga.ozkaraca on 4/4/2015.
 */




public class FluxItem {
    private Date mTimePosted;
    private String mPostTitle;
    private String mImageURL;
    private String mPostImageURL;

    private String mPostURL;
    private String mType;
    private String mFB_UserId;
    private String gtfoloihc;
    private String gtrr;
    private Boolean isLikedFavedHearted;
    private Boolean isRetweeted;

 //   Twitter twitter;

    private String sourceName;
    public FluxItem(String Type, Date TimePosted, String PostTitle, String ImageURL, String PostURL, String FB_UserId, String postImageURL, String FavLikeHeartCount, String RetweetsReshares, Boolean misLikedFavedHearted) {

        mTimePosted = TimePosted;
        mPostTitle = PostTitle;
        mImageURL = ImageURL;
        mPostURL = PostURL;
        mType = Type;
        mFB_UserId = FB_UserId;
        mPostImageURL = postImageURL;
        gtfoloihc = FavLikeHeartCount;
        gtrr = RetweetsReshares;
        isLikedFavedHearted = misLikedFavedHearted;

    }




    //    public void setName(String name, String string2) {
//        mName = name;
//        mName2 = string2;
//    }
    public String getType() {
        return mType;

    }
    public Date getTimePosted() {
        return mTimePosted;

    }
    public String getPostTitle() {
        return mPostTitle;

    }
    public String getImageURL() {
        return mImageURL;

    }
    public String getPostImageURL() {
        return mPostImageURL;

    }
    public String getPostURL() {
        return mPostURL;

    }
    public String getFB_UserId() {
        return mFB_UserId;

    }

    public String getFavOrLikeOrInstaHeartCount() {
        return gtfoloihc;

    }

    public String getRetweetReshares() {
        return gtrr;

    }

    public void setFavOrLikeOrInstaHeartCount(String aa) {
        gtfoloihc = aa;

    }

    public void setRetweetReshares(String bb) {
        gtrr = bb;

    }
    public void setSourceName(String s) {
       sourceName = s;

    }
    public String getSourceName() {
        return sourceName + "&nbsp;&nbsp;";

    }
    public String getSourceNameUnderlined() {
        return "<u>&nbsp;"+sourceName+"&nbsp;</u>";

    }

    public Boolean getIfLikedFavedHearted()
    {
        return isLikedFavedHearted;
    }

    public void setIfLikedFavedHearted(Boolean b)
    {
        isLikedFavedHearted = b;
    }

    public Boolean getIfRetweeted()
    {
        return isRetweeted;
    }

    public void setIfRetweeted(Boolean b)
    {
        isRetweeted = b;
    }

   // public Twitter getTwitter()
   // {
  //      return twitter;
  //  }
  //  public void setTwitter(Twitter t)
  //  {
  //      twitter = t;
  //  }

}