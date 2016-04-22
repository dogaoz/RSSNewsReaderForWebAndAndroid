package t;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class Thin extends TextView {


    public Thin(Context context) {
      super(context);
      if(!isInEditMode())
      {
      Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/rothin"); 
     
      this.setTypeface(face);
      }
    }

    public Thin(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode())
        {
     Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/rothin"); 
  this.setTypeface(face); 
        }
    }

    public Thin(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(!isInEditMode())
        {
     Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/rothin"); 
  this.setTypeface(face); 
        }
    }

    @Override
	protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        
       
    }

}