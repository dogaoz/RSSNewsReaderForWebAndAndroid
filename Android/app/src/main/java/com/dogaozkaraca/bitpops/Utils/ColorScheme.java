package com.dogaozkaraca.bitpops.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;

/**
 * Created by doga.ozkaraca on 4/4/2015.
 */


public class ColorScheme {
    static int darkercolor ;

    public static int getBackgroundColor(Context ct)
    {
        final SharedPreferences settings = ct.getSharedPreferences("DO_StatusBarColors", 0);

        final String color = settings.getString("DO_StatusBarColor", "#FF0D98FD");
        return Color.parseColor(color);
    }



    public static int getTextColor(Context ct)
    {

        final SharedPreferences settings = ct.getSharedPreferences("DO_StatusBarColors", 0);

        final String color = settings.getString("DO_StatusBarTextColor", "#FFFFFFFF");
        return Color.parseColor(color);
    }

    public static int getSubColor(Context ct) {
        final SharedPreferences settings = ct.getSharedPreferences("DO_StatusBarColors", 0);

        final String color2 = settings.getString("DO_StatusBarTextColor", "#FFFFFFFF");
        int color = Color.parseColor(color2);
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f; // value component
        color = Color.HSVToColor(hsv);

        return color;
    }

    public static int getTransparentBgColor(Context ct,String transparencyhex) {
        final SharedPreferences settings = ct.getSharedPreferences("DO_StatusBarColors", 0);

        final String color2 = settings.getString("DO_StatusBarColor", "#FF0D98FD");



        String s2 = transparencyhex.toUpperCase();
        char[] tempArr2 = s2.toCharArray();

        String s = color2.toUpperCase();
        char[] tempArr = s.toCharArray();

        String tempColor = ("#"+tempArr2[0]+""+tempArr2[1]+""+tempArr[3]+""+tempArr[4]+""+tempArr[5]+""+tempArr[6]+""+tempArr[7]+""+tempArr[8]);
        //	Log.w("DoColor","created Temp color :" + tempColor);
        int color = Color.parseColor(tempColor);


        return color;
    }
    public static void setGradientColor(final Context ct,View v)
    {
        float[] hsv = new float[3];
        darkercolor = getBackgroundColor(ct);
        Color.colorToHSV(darkercolor, hsv);
        hsv[2] *= 0.6f; // value component
        darkercolor = Color.HSVToColor(hsv);
        final View view = v;
        Drawable[] layers = new Drawable[1];

        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient lg = new LinearGradient(
                        0,
                        0,
                        0,
                        view.getHeight(),
                        new int[] {

                                darkercolor,
                                getBackgroundColor(ct)

                        },
                        new float[] { 0, 1 },
                        Shader.TileMode.CLAMP);
                return lg;
            }
        };
        PaintDrawable p = new PaintDrawable();
        p.setShape(new RectShape());
        p.setShaderFactory(sf);
        p.setCornerRadii(new float[] { 5, 5, 5, 5, 0, 0, 0, 0 });
        layers[0] = (Drawable) p;

        LayerDrawable composite = new LayerDrawable(layers);
        view.setBackgroundDrawable(composite);

    }


    public static int getActionBarColor(Context ctx) {
        final SharedPreferences settings = ctx.getSharedPreferences("DO_StatusBarColors", 0);

        final String color2 = settings.getString("DO_StatusBarColor", "#FF0D98FD");
        int color = Color.parseColor(color2);
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f; // value component
        color = Color.HSVToColor(hsv);

        return color;
    }
    public static int getStatusBarColor(Context ctx) {
        final SharedPreferences settings = ctx.getSharedPreferences("DO_StatusBarColors", 0);

        final String color2 = settings.getString("DO_StatusBarColor", "#FF0D98FD");
        int color = Color.parseColor(color2);
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.6f; // value component
        color = Color.HSVToColor(hsv);

        return color;
    }
}
