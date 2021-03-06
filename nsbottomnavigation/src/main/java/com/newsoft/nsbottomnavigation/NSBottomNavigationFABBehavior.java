package com.newsoft.nsbottomnavigation;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public class NSBottomNavigationFABBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

	private int navigationBarHeight = 0;
	private long lastSnackbarUpdate = 0;

	public NSBottomNavigationFABBehavior(int navigationBarHeight) {
		this.navigationBarHeight = navigationBarHeight;
	}

	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
		if (dependency != null && dependency instanceof Snackbar.SnackbarLayout) {
			return true;
		} else if (dependency != null && dependency instanceof NSBottomNavigation) {
			return true;
		}
		return super.layoutDependsOn(parent, child, dependency);
	}

	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
		updateFloatingActionButton(child, dependency);
		return super.onDependentViewChanged(parent, child, dependency);
	}

	/**
	 * Update floating action button bottom margin
	 */
	private void updateFloatingActionButton(FloatingActionButton child, View dependency) {
		if (child != null && dependency != null && dependency instanceof Snackbar.SnackbarLayout) {
			lastSnackbarUpdate = System.currentTimeMillis();
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
			int fabDefaultBottomMargin = p.bottomMargin;
			child.setY(dependency.getY() - fabDefaultBottomMargin);
		} else if (child != null && dependency != null && dependency instanceof NSBottomNavigation) {
			// Hack to avoid moving the FAB when the AHBottomNavigation is moving (showing or hiding animation)
			if (System.currentTimeMillis() - lastSnackbarUpdate < 30) {
				return;
			}
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
			int fabDefaultBottomMargin = p.bottomMargin;
			child.setY(dependency.getY() - fabDefaultBottomMargin);
		}
	}

}