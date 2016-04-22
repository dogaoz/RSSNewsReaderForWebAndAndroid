package com.dogaozkaraca.bitpops.Viewers;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dogaozkaraca.bitpops.R;
import com.dogaozkaraca.bitpops.Utils.Utils;
import com.squareup.picasso.Picasso;


public class PostReaderActivity extends AppCompatActivity {

    String title;
    String url;
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_reader);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Intent intent = getIntent();
        url = "";
        String imageurl = "";
        title = "";
        String from = "from ";
        if (null != intent) { //Null Checking
            url = intent.getStringExtra("url");
            title = intent.getStringExtra("title");
            from = intent.getStringExtra("from");

            imageurl = intent.getStringExtra("imageurl");
        }
        TextView source = (TextView) findViewById(R.id.from);
        source.setText(Html.fromHtml("via " + from));
        TextView TitleTV = (TextView) findViewById(R.id.textView3);
        TitleTV.setText(title);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.loadingCircle);

        final RelativeLayout mainRL = (RelativeLayout) findViewById(R.id.activity_transition_headerRL);
        final ImageView header = (ImageView) findViewById(R.id.activity_transition_header);
        Picasso.with(this).load(imageurl).into(header);
        final WebView articleView = (WebView) findViewById(R.id.articleWebView);

        articleView.getSettings().setJavaScriptEnabled(true);

        articleView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                int duration = 800;
                //change your progress bar
                if(newProgress >40)
                {

                    int targetHeight2 = (int) Utils.convertDpToPixel(0f);
                    int prevHeight2  = progressBar.getHeight();
                    ValueAnimator valueAnimator2 = ValueAnimator.ofInt(prevHeight2, targetHeight2);
                    valueAnimator2.setInterpolator(new DecelerateInterpolator());
                    valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            progressBar.getLayoutParams().height = (int) animation.getAnimatedValue();
                            progressBar.requestLayout();
                        }
                    });

                    valueAnimator2.setInterpolator(new DecelerateInterpolator());
                    valueAnimator2.setDuration(duration);
                    valueAnimator2.start();

                    int targetHeight = (int) Utils.convertDpToPixel(180f);
                    int prevHeight  = mainRL.getHeight();
                    ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
                    valueAnimator.setInterpolator(new DecelerateInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mainRL.getLayoutParams().height = (int) animation.getAnimatedValue();
                            mainRL.requestLayout();
                        }
                    });

                    valueAnimator.setInterpolator(new DecelerateInterpolator());
                    valueAnimator.setDuration(duration);
                    valueAnimator.start();

                    }



            }


        });

        WebViewClient wd = new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            // When finish loading page
            public void onPageFinished(WebView view, String url) {



            }
        };



        articleView.setWebViewClient(wd);




        final String finalUrl = url;
        new AsyncTask<String,String,String>()
        {

            @Override
            protected String doInBackground(String... params) {
                try {
                /*String content = "<html>" +
                                 "<body>" +
                                    "<style>@import url(https://fonts.googleapis.com/css?family=Delius);" +
                                    " a{font-size:20px;font-family: 'Delius', cursive;}p{font-size:20px;}img{ width: 100%;}" +
                                     "</style>" +
                                     "</body></br></br>";


                    Document doc = Jsoup.connect(finalUrl).get();
                    doc.removeClass("header");
                    doc.removeClass("footer");
                    doc.removeClass("Comments");
                    doc.removeClass("comments");
                    doc.removeClass("commnets");
                    doc.removeClass("comment");
                    doc.removeClass("social");
                    doc.removeClass("wrapper");
                    doc.getElementsByClass("header-container").remove();
                    Elements frm = doc.getElementsByClass("form");
                    for (Element e : frm) {
                        e.remove();
                    }
                    Elements sdb = doc.getElementsByClass("sideBarBox");
                    for (Element e : sdb) {
                        e.remove();
                    }

                    Elements ft = doc.getElementsByTag("footer");
                    for (Element e : ft) {
                        e.remove();
                    }
                    Elements hd = doc.getElementsByTag("header");
                    for (Element e : hd) {
                        e.remove();
                    }
                    doc.getElementsByTag("footer").remove();
                    Elements st = doc.getElementsByTag("style");
                    for (Element e : st) {
                        e.remove();
                    }
                    Elements sty = doc.getElementsByTag("STYLE");
                    for (Element e : sty) {
                        e.remove();
                    }
                    Elements sc = doc.getElementsByTag("script");
                    for (Element e : sc) {
                        e.remove();
                    }
                    Elements scc = doc.getElementsByTag("SCRIPT");
                    for (Element e : scc) {
                        e.remove();
                    }
                    Elements btn = doc.getElementsByTag("button");
                    for (Element e : btn) {
                        e.remove();
                    }
                    Elements inpt = doc.getElementsByTag("input");
                    for (Element e : inpt) {
                        e.remove();
                    }
                    Elements el = doc.getElementsByTag("article");
                    for (Element e : el) {
                        content = content + "</br>" + e.html()+ "</a>";

                    }
                    content = content + "</br></html>";

                    if (content.length() < 500)
                    {
                        content = "<html>" +
                                "<body>" +
                                "<style>@import url(https://fonts.googleapis.com/css?family=Delius);" +
                                " a{font-size:20px;font-family: 'Delius', cursive;}p{font-size:20px;}img{ width: 100%;}" +
                                "</style>" +
                                "</body></br></br>";
                        el = doc.getElementsByTag("articleBody");
                        for (Element e : el) {
                            content = content + "</br><" + e.html()+ "";

                        }
                        content = content + "</br></html>";
                    }
                    if  (content.length() < 500)
                    {

                        content = doc.html();
                    }

                    Log.w("PostReader",content);
                    */

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    //content = null;
                }
                return null;
            }


            @Override
            protected void onPostExecute(String content )
            {
                if (content == null)
                {
                    articleView.loadUrl(finalUrl);

                }
                else{
                  //  articleView.loadDataWithBaseURL("", content, "text/html", "UTF-8", "");
                 //   Snackbar.make(fab, "Article Loaded!", Snackbar.LENGTH_LONG)
                 //           .setAction("Action", null).show();
                }
            }
        }.execute();


    }

    public void onShare(View v)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, title + ", " + url);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share..."));
    }

    public void onFavourites(View v)
    {
        // Add to favorites
        Snackbar.make(v, "Article Saved!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
