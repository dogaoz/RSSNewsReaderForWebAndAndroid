package t;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class BoldTextView extends TextView {


    public BoldTextView(Context context) {
      super(context);
      if(!isInEditMode())
      {
      Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/rcb.ttf"); 
      this.setTypeface(face); 
      }
    }

    public BoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode())
        {
     Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/rcb.ttf"); 
  this.setTypeface(face); 
        }
    }

    public BoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(!isInEditMode())
        {
     Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/rcb.ttf"); 
  this.setTypeface(face); 
        }
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        
       
    }

}