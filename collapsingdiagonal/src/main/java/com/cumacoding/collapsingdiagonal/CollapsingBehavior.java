package com.cumacoding.collapsingdiagonal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by jumadi on 05/09/19.
 */

public class CollapsingBehavior extends CoordinatorLayout.Behavior<View> {

    private int mCollapsingId;
    private boolean mIsHiding;
    private boolean isTriggerHide;
    private boolean isInitialize = true;

    public CollapsingBehavior(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CollapsingBehavior);
            mCollapsingId = a.getResourceId(R.styleable.CollapsingBehavior_CollapsingId, -1);
            isTriggerHide = a.getBoolean(R.styleable.CollapsingBehavior_isTrigger, false);

            a.recycle();
        }
    }



    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        CollapsingDiagonalLayout cd = parent.findViewById(mCollapsingId);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();

        if (cd != null) {
            int heightToolbar = cd.getScrimVisibleHeightTrigger() - cd.getOverlap();
            int bottom = dependency.getBottom() - cd.getOverlap();

            if (isTriggerHide) {
                int h = (child.getHeight() / 2);
                if (isInitialize) {
                    child.setY((bottom + lp.topMargin) - h);
                    isInitialize = false;
                }
                child.setY((bottom + lp.topMargin) - h);

                if (child.getY() < heightToolbar) {
                    if (child instanceof FloatingActionButton) {
                        ((FloatingActionButton) child).hide();
                    } else {
                        hide(child);
                    }
                } else if (child.getY() > heightToolbar) {
                    if (child instanceof FloatingActionButton) {
                        ((FloatingActionButton) child).show();
                    } else {
                        show(child);
                    }
                }
            } else {
                if (isInitialize) {
                    child.setY((bottom + lp.topMargin));
                    isInitialize = false;
                    if (child.getHeight() >= parent.getHeight() - heightToolbar) {
                        lp.bottomMargin = lp.bottomMargin + heightToolbar;
                        child.setLayoutParams(lp);
                    }
                }
                child.setY((bottom + lp.topMargin));
                if (child.getY() < heightToolbar) {
                    child.setY(heightToolbar);
                }
            }

        }
        return true;
    }

    private void hide(final View child) {
        if(!mIsHiding && child.getVisibility() == VISIBLE) {
            if(ViewCompat.isLaidOut(child) && !child.isInEditMode()) {
                child.animate().scaleX(0.0F).scaleY(0.0F).alpha(0.0F).setDuration(200L).setInterpolator(new FastOutSlowInInterpolator()).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animation) {
                        mIsHiding =true;
                        child.setVisibility(VISIBLE);
                    }

                    public void onAnimationCancel(Animator animation) {
                        mIsHiding = false;
                    }

                    public void onAnimationEnd(Animator animation) {
                        mIsHiding = false;
                        child.setVisibility(GONE);
                    }
                });
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    private void show(final View child) {
        if(child.getVisibility() != VISIBLE) {
            if(ViewCompat.isLaidOut(child) && !child.isInEditMode()) {
                child.setAlpha(0.0F);
                child.setScaleY(0.0F);
                child.setScaleX(0.0F);
                child.animate().scaleX(1.0F).scaleY(1.0F).alpha(1.0F).setDuration(200L).setInterpolator(new FastOutSlowInInterpolator()).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animation) {
                        child.setVisibility(VISIBLE);
                    }
                });
            } else {
                child.setVisibility(VISIBLE);
                child.setAlpha(1.0F);
                child.setScaleY(1.0F);
                child.setScaleX(1.0F);
            }
        }
    }

}
