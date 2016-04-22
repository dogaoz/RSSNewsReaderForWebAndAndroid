package t;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class forgotten extends TextView {


    public forgotten(Context context) {
      super(context);
      if(!isInEditMode())
      {
          Typeface face =null;
          final SharedPreferences settings5 = context.getSharedPreferences("font_updater", 0);

          //RotaryHome.rotarystatusTV.setText("Loading...");
          if(settings5.getString("fonts","forgottensans").equals("forgottensans"))
          {
              face=Typeface.createFromAsset(context.getAssets(), "fonts/forgotten.ttf");

          }
          else if (settings5.getString("fonts","forgottensans").equals("lobster"))
          {
              face=Typeface.createFromAsset(context.getAssets(), "fonts/lobster.ttf");

          }
          else if (settings5.getString("fonts","forgottensans").equals("exo2"))
          {
              face=Typeface.createFromAsset(context.getAssets(), "fonts/Exo2.0_Thin.otf");

          }
          else if (settings5.getString("fonts","forgottensans").equals("android"))
          {
              face = Typeface.DEFAULT;

          }
          else
          {
              face=Typeface.createFromAsset(context.getAssets(), "fonts/forgotten.ttf");

          }

          if (face != null)
              this.setTypeface(face);
      }
    }

    public forgotten(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode())
        {
            Typeface face =null;
            final SharedPreferences settings5 = context.getSharedPreferences("font_updater", 0);

            //RotaryHome.rotarystatusTV.setText("Loading...");
            if(settings5.getString("fonts","forgottensans").equals("forgottensans"))
            {
                face=Typeface.createFromAsset(context.getAssets(), "fonts/forgotten.ttf");

            }
            else if (settings5.getString("fonts","forgottensans").equals("lobster"))
            {
                face=Typeface.createFromAsset(context.getAssets(), "fonts/lobster.ttf");

            }
            else if (settings5.getString("fonts","forgottensans").equals("exo2"))
            {
                face=Typeface.createFromAsset(context.getAssets(), "fonts/Exo2.0_Thin.otf");

            }
            else if (settings5.getString("fonts","forgottensans").equals("android"))
            {
                face = Typeface.DEFAULT;


            }
            else
            {
                face=Typeface.createFromAsset(context.getAssets(), "fonts/forgotten.ttf");

            }
            if (face != null)
            this.setTypeface(face);
        }
    }

    public forgotten(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(!isInEditMode())
        {
            Typeface face = null;
            final SharedPreferences settings5 = context.getSharedPreferences("font_updater", 0);

            //RotaryHome.rotarystatusTV.setText("Loading...");
            if(settings5.getString("fonts","forgottensans").equals("forgottensans"))
            {
                face=Typeface.createFromAsset(context.getAssets(), "fonts/forgotten.ttf");

            }
            else if (settings5.getString("fonts","forgottensans").equals("lobster"))
            {
                face=Typeface.createFromAsset(context.getAssets(), "fonts/lobster.ttf");

            }
            else if (settings5.getString("fonts","forgottensans").equals("exo2"))
            {
                face=Typeface.createFromAsset(context.getAssets(), "fonts/Exo2.0_Thin.otf");

            }
            else if (settings5.getString("fonts","forgottensans").equals("android"))
            {

                face = Typeface.DEFAULT;

            }
            else
            {
                face=Typeface.createFromAsset(context.getAssets(), "fonts/forgotten.ttf");

            }

            if (face != null)
                this.setTypeface(face);
        }
    }

    @Override
	protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        
       
    }

}