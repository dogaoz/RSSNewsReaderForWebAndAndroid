package t;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CondensedTextView extends TextView {


    public CondensedTextView(Context context) {
      super(context);
      if(!isInEditMode())
      {
      Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/rcr.ttf"); 
      this.setTypeface(face); 
      }
    }

    public CondensedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode())
        {
     Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/rcr.ttf"); 
  this.setTypeface(face); 
        }
    }

    public CondensedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(!isInEditMode())
        {
     Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/rcr.ttf"); 
  this.setTypeface(face); 
        }
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        
       
    }

}