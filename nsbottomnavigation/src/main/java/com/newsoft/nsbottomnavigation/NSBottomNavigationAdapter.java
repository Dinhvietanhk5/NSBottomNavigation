package com.newsoft.nsbottomnavigation;

import android.app.Activity;
import androidx.annotation.ColorInt;
import androidx.annotation.MenuRes;
import androidx.appcompat.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class NSBottomNavigationAdapter {

	private Menu mMenu;
	private List<NSBottomNavigationItem> navigationItems;

	/**
	 * Constructor
	 *
	 * @param activity
	 * @param menuRes
	 */
	public NSBottomNavigationAdapter(Activity activity, @MenuRes int menuRes) {
		PopupMenu popupMenu = new PopupMenu(activity, null);
		mMenu = popupMenu.getMenu();
		activity.getMenuInflater().inflate(menuRes, mMenu);
	}

	/**
	 * Setup bottom navigation
	 *
	 * @param NSBottomNavigation AHBottomNavigation: Bottom navigation
	 */
	public void setupWithBottomNavigation(NSBottomNavigation NSBottomNavigation) {
		setupWithBottomNavigation(NSBottomNavigation, null);
	}

	/**
	 * Setup bottom navigation (with colors)
	 *
	 * @param NSBottomNavigation AHBottomNavigation: Bottom navigation
	 * @param colors             int[]: Colors of the item
	 */
	public void setupWithBottomNavigation(NSBottomNavigation NSBottomNavigation, @ColorInt int[] colors) {
		if (navigationItems == null) {
			navigationItems = new ArrayList<>();
		} else {
			navigationItems.clear();
		}

		if (mMenu != null) {
			for (int i = 0; i < mMenu.size(); i++) {
				MenuItem item = mMenu.getItem(i);
				if (colors != null && colors.length >= mMenu.size() && colors[i] != 0) {
					NSBottomNavigationItem navigationItem = new NSBottomNavigationItem(String.valueOf(item.getTitle()), item.getIcon(), colors[i]);
					navigationItems.add(navigationItem);
				} else {
					NSBottomNavigationItem navigationItem = new NSBottomNavigationItem(String.valueOf(item.getTitle()), item.getIcon());
					navigationItems.add(navigationItem);
				}
			}
			NSBottomNavigation.removeAllItems();
			NSBottomNavigation.addItems(navigationItems);
		}
	}

	/**
	 * Get Menu Item
	 *
	 * @param index
	 * @return
	 */
	public MenuItem getMenuItem(int index) {
		return mMenu.getItem(index);
	}

	/**
	 * Get Navigation Item
	 *
	 * @param index
	 * @return
	 */
	public NSBottomNavigationItem getNavigationItem(int index) {
		return navigationItems.get(index);
	}

	/**
	 * Get position by menu id
	 *
	 * @param menuId
	 * @return
	 */
	public Integer getPositionByMenuId(int menuId) {
		for (int i = 0; i < mMenu.size(); i++) {
			if (mMenu.getItem(i).getItemId() == menuId)
				return i;
		}
		return null;
	}
}