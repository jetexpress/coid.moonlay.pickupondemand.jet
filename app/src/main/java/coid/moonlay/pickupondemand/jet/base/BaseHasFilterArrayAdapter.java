package coid.moonlay.pickupondemand.jet.base;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class BaseHasFilterArrayAdapter<T> extends ArrayAdapter<T>
{
    private LayoutInflater mLayoutInflater;
    private String mFilter;
    private Integer mFilterColor;

    public BaseHasFilterArrayAdapter(Context context, Integer layoutResources, List<T> dataList)
    {
        super(context, layoutResources, dataList);
        mLayoutInflater = LayoutInflater.from(context);
        mFilter = "";
        mFilterColor = Color.RED;
    }

    public LayoutInflater getLayoutInflater()
    {
        return mLayoutInflater;
    }

    public void updateFilteredList(List<T> dataList, String filter)
    {
        mFilter = filter;
        clear();
        addAll(dataList);
        notifyDataSetChanged();
    }

    protected SpannableString getColoredStringContainsFilterKey(String filteredText)
    {
        SpannableString coloredString = new SpannableString(filteredText);
        Integer searchIndex = 0;
        List<Integer> startIndexList = new ArrayList<>();
        while (searchIndex <= coloredString.length())
        {
            int startIndex = filteredText.toLowerCase().indexOf(mFilter, searchIndex);
            if (startIndex > -1) startIndexList.add(startIndex);
            else break;
            searchIndex = startIndex + 1;
        }
        for (Integer startIndex : startIndexList)
        {
            int endIndex = startIndex + mFilter.length();
            coloredString.setSpan(new ForegroundColorSpan(mFilterColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return coloredString;
    }

    protected void setFilterColor(Integer color)
    {
        mFilterColor = color;
    }

    protected Boolean hasFilter()
    {
        return mFilter != null && !mFilter.isEmpty();
    }
}
