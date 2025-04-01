package com.example.jiyulearning.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.jiyulearning.fragment.ListFragment;

public class ListPagerAdapter extends FragmentPagerAdapter {

    private final String myearMonth;

    public ListPagerAdapter(@NonNull FragmentManager fm, String yearMonth) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.myearMonth=yearMonth;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {//此时position+1就是day
        int day = position+1;
        //由于需要显示该格式：如2024-09，故当month小于10的时候，需要在前面加上0
        String zeroDay = day < 10? "0" + day : String.valueOf(day);
        String monthDay=myearMonth+"-"+zeroDay;
        Log.d("list",monthDay);
        return ListFragment.newInstance(monthDay);
    }

    @Override
    public int getCount() {
        return 31;//一个月最多31天
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (position + 1) + "日";
    }
}
