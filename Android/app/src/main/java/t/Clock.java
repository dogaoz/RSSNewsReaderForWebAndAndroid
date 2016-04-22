package t;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class Clock extends TextView {


    public Clock(Context context) {
      super(context);
      if(!isInEditMode())
      {
      Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/Exo2.0_Thin.otf"); 
     
      this.setTypeface(face);
      }
    }

    public Clock(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode())
        {
     Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/Exo2.0_Thin.otf"); 
  this.setTypeface(face); 
        }
    }

    public Clock(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(!isInEditMode())
        {
     Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/Exo2.0_Thin.otf"); 
  this.setTypeface(face); 
        }
    }

    @Override
	protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        
       
    }

}