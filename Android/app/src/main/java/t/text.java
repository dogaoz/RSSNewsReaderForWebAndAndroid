package t;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class text extends TextView {


    public text(Context context) {
      super(context);
      if(!isInEditMode())
      {            //MainClockFont.ttf

          Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/MainClockFont.ttf");

      this.setTypeface(face);
      }
    }

    public text(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode())
        {            //MainClockFont.ttf

            Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/MainClockFont.ttf");
  this.setTypeface(face);
        }
    }

    public text(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(!isInEditMode())
        {
            //MainClockFont.ttf
     Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/MainClockFont.ttf");
  this.setTypeface(face); 
        }
    }

    @Override
	protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        
       
    }

}