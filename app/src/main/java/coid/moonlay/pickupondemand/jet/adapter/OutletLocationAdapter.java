package coid.moonlay.pickupondemand.jet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.base.BaseHasFilterArrayAdapter;
import coid.moonlay.pickupondemand.jet.model.OutletLocation;

public class OutletLocationAdapter extends BaseHasFilterArrayAdapter<OutletLocation>
{

    public OutletLocationAdapter(Context context, List<OutletLocation> outletLocationList)
    {
        super(context, R.layout.list_item_outlet_location, outletLocationList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = getLayoutInflater().inflate(R.layout.list_item_outlet_location, parent, false);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        OutletLocation outletLocation = getItem(position);
        if (outletLocation != null)
        {
            if (hasFilter())
            {
                SpannableString spannableName = getColoredStringContainsFilterKey(outletLocation.getName());
                SpannableString spannableAddress = getColoredStringContainsFilterKey(outletLocation.getAddress());
                holder.tv_name.setText(spannableName);
                holder.tv_address.setText(spannableAddress);
            }
            else
            {
                holder.tv_name.setText(outletLocation.getName());
                holder.tv_address.setText(outletLocation.getAddress());
            }
            holder.tv_distance.setText(outletLocation.getDistance());
        }

        return convertView;
    }

    private class ViewHolder
    {
        TextView tv_name, tv_address, tv_distance;
    }
}
