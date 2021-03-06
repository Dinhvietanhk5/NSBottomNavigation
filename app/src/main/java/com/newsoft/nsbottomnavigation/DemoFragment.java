package com.newsoft.nsbottomnavigation;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DemoFragment extends Fragment {
	
	private FrameLayout fragmentContainer;
	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	
	/**
	 * Create a new instance of the fragment
	 */
	public static DemoFragment newInstance(int index) {
		DemoFragment fragment = new DemoFragment();
		Bundle b = new Bundle();
		b.putInt("index", index);
		fragment.setArguments(b);
		return fragment;
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (getArguments().getInt("index", 0) == 0) {
			View view = inflater.inflate(R.layout.fragment_demo_settings, container, false);
			initDemoSettings(view);
			return view;
		} else {
			View view = inflater.inflate(R.layout.fragment_demo_list, container, false);
			initDemoList(view);
			return view;
		}
	}
	
	/**
	 * Init demo settings
	 */
	private void initDemoSettings(View view) {
		
		final MainActivity MainActivity = (MainActivity) getActivity();
		final SwitchCompat switchColored = view.findViewById(R.id.fragment_demo_switch_colored);
		final SwitchCompat switchFiveItems = view.findViewById(R.id.fragment_demo_switch_five_items);
		final SwitchCompat showHideBottomNavigation = view.findViewById(R.id.fragment_demo_show_hide);
		final SwitchCompat showSelectedBackground = view.findViewById(R.id.fragment_demo_selected_background);
		final Spinner spinnerTitleState = view.findViewById(R.id.fragment_demo_title_state);
		final SwitchCompat switchTranslucentNavigation = view.findViewById(R.id.fragment_demo_translucent_navigation);
		
		switchColored.setChecked(MainActivity.isBottomNavigationColored());
		switchFiveItems.setChecked(MainActivity.getBottomNavigationNbItems() == 5);
		switchTranslucentNavigation.setChecked(getActivity()
				.getSharedPreferences("shared", Context.MODE_PRIVATE)
				.getBoolean("translucentNavigation", false));
		switchTranslucentNavigation.setVisibility(
				Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? View.VISIBLE : View.GONE);
		
		switchTranslucentNavigation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				getActivity()
						.getSharedPreferences("shared", Context.MODE_PRIVATE)
						.edit()
						.putBoolean("translucentNavigation", isChecked)
						.apply();
				MainActivity.reload();
			}
		});
		switchColored.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				MainActivity.updateBottomNavigationColor(isChecked);
			}
		});
		switchFiveItems.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				MainActivity.updateBottomNavigationItems(isChecked);
			}
		});
		showHideBottomNavigation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				MainActivity.showOrHideBottomNavigation(isChecked);
			}
		});
		showSelectedBackground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				MainActivity.updateSelectedBackgroundVisibility(isChecked);
			}
		});
		final List<String> titleStates = new ArrayList<>();
		for (NSBottomNavigation.TitleState titleState : NSBottomNavigation.TitleState.values()) {
			titleStates.add(titleState.toString());
		}
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, titleStates);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTitleState.setAdapter(spinnerAdapter);
		spinnerTitleState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				NSBottomNavigation.TitleState titleState = NSBottomNavigation.TitleState.valueOf(titleStates.get(position));
				MainActivity.setTitleState(titleState);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// do nothing
			}
		});
	}
	
	/**
	 * Init the fragment
	 */
	private void initDemoList(View view) {
		
		fragmentContainer = view.findViewById(R.id.fragment_container);
		recyclerView = view.findViewById(R.id.fragment_demo_recycler_view);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);
		
		ArrayList<String> itemsData = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			itemsData.add("Fragment " + getArguments().getInt("index", -1) + " / Item " + i);
		}
		
		DemoAdapter adapter = new DemoAdapter(itemsData);
		recyclerView.setAdapter(adapter);
	}
	
	/**
	 * Refresh
	 */
	public void refresh() {
		if (getArguments().getInt("index", 0) > 0 && recyclerView != null) {
			recyclerView.smoothScrollToPosition(0);
		}
	}
	
	/**
	 * Called when a fragment will be displayed
	 */
	public void willBeDisplayed() {
		// Do what you want here, for example animate the content
		if (fragmentContainer != null) {
			Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
			fragmentContainer.startAnimation(fadeIn);
		}
	}
	
	/**
	 * Called when a fragment will be hidden
	 */
	public void willBeHidden() {
		if (fragmentContainer != null) {
			Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
			fragmentContainer.startAnimation(fadeOut);
		}
	}
}
