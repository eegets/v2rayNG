package com.v2ray.ang.custom.activity;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.v2ray.ang.R;
import com.v2ray.ang.custom.fragment.HomeFragment;
import com.v2ray.ang.custom.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class TabMonitor {

    private int[] itemItemIcons = {R.mipmap.ic_launcher, R.mipmap.ic_launcher_foreground};
    private String[] tabItemTitles = {"加速", "我的"};
    private MainActivity activity;
    private TabLayout tabLayout;
    private FrameLayout viewContainer;

    private List<Fragment> fragments = new ArrayList<>();

    public TabMonitor(MainActivity activity) {
        this.activity = activity;
        initialLayout();
    }

    private void initialLayout() {
        tabLayout = activity.findViewById(R.id.tabLayout);
        viewContainer = activity.findViewById(R.id.viewContainer);
        tabLayout.setSelectedTabIndicator(null);
    }

    public void setup() {
        addFragments();
        onTabItemSelected(0);
        // 提供自定义的布局添加Tab
        for (int i = 0; i < tabItemTitles.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.getTabAt(i).setCustomView(getTabView(tabItemTitles[i], itemItemIcons[i]));
        }
        tabItemViewSelector(tabLayout.getTabAt(0).getCustomView(), true);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabItemViewSelector(tab.getCustomView(), true);
                onTabItemSelected(tab.getPosition());
            }

            @Override
            public void onTabUnselected(@Nullable TabLayout.Tab tab) {
                tabItemViewSelector(tab.getCustomView(), false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void addFragments() {
        fragments.add(new HomeFragment());
        fragments.add(new MineFragment());
    }

    private void onTabItemSelected(int position) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment baseFragment = fragmentManager.findFragmentById(R.id.viewContainer);

        Fragment fragment = fragments.get(position);

        List<Fragment> list = fragmentManager.getFragments();

        if (baseFragment == null || !list.contains(fragment)) {
            fragmentTransaction.add(R.id.viewContainer, fragment);
        }
        if (list != null) {
            for (Fragment f : list) {
                fragmentTransaction.hide(f);
            }
        }
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    /**
     * 使用自定义的tabView
     * @param title
     * @param iconSrc
     * @return
     */
    public View getTabView(String title, int iconSrc) {
        View view = LayoutInflater.from(activity).inflate(R.layout.custom_activity_main_tablayout_item, null);
        TextView textView = view.findViewById(R.id.tablayoutItemText);
        ImageView imageView = view.findViewById(R.id.tablayoutItemIcon);
        textView.setText(title);
        imageView.setImageResource(iconSrc);
        return view;
    }

    /**
     * 底部tab Item选择样式调整
     * @param view
     * @param isSelect 是否选择
     */
    private void tabItemViewSelector(View view, boolean isSelect) {
        TextView tabLayoutItemText = view.findViewById(R.id.tablayoutItemText);
        ImageView tabLayoutItemIcon = view.findViewById(R.id.tablayoutItemIcon);
        if (isSelect) {
            tabLayoutItemText.setTextColor(Color.parseColor("#ff0000"));
        } else {
            tabLayoutItemText.setTextColor(Color.parseColor("#ff00ff"));
        }
        tabLayoutItemIcon.setSelected(isSelect);
    }
}
