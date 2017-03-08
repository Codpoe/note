package me.codpoe.note.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Codpoe on 2016/12/15.
 */

public class FabScrollBehavior extends FloatingActionButton.Behavior {

    private Context mContext;

    public FabScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
//            Animator animator = AnimatorInflater.loadAnimator(mContext, R.animator.fab_out);
//            animator.setTarget(child);
//            animator.start();
            child.hide();
        } else if (dxConsumed < 0 && child.getVisibility() == View.INVISIBLE) {
//            Animator animator = AnimatorInflater.loadAnimator(mContext, R.animator.fab_in);
//            animator.setTarget(child);
//            animator.start();
            child.show();
        }
    }
}
