package com.clipwiser.views.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.clipwiser.R;


/**
 * Created by naresh on 1/9/16.
 */
public class LinearLayoutHideBehaviour extends CoordinatorLayout.Behavior<LinearLayout> {
    private int toolbarHeight;

    public LinearLayoutHideBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.toolbarHeight = getToolbarHeight(context);
    }

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{ R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    @Override
    public boolean layoutDependsOn( CoordinatorLayout parent, LinearLayout fab, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged( CoordinatorLayout parent, LinearLayout fab, View dependency) {
        if (dependency instanceof AppBarLayout ) {
            CoordinatorLayout.LayoutParams lp               = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
            int                            fabBottomMargin  = lp.bottomMargin;
            int                            distanceToScroll = fab.getHeight() + fabBottomMargin;
            float                          ratio            = (float) dependency.getY() / (float) toolbarHeight;
            fab.setTranslationY(-distanceToScroll * ratio);
        }
        return true;
    }
}