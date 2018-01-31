package coid.moonlay.pickupondemand.jet.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.model.PickupItem;

public class PickupItemAdapter extends ArrayAdapter<PickupItem>
{
    private LayoutInflater mLayoutInflater;
    private IOnItemClickListener mOnItemClickListener;

    public PickupItemAdapter(Context context, List<PickupItem> pickupItemList, IOnItemClickListener onItemClickListener)
    {
        super(context, R.layout.list_item_pickup_item, pickupItemList);
        mLayoutInflater = LayoutInflater.from(context);
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = mLayoutInflater.inflate(R.layout.list_item_pickup_item, parent, false);
            holder = new ViewHolder();
            holder.ll_pickup_item_container = (LinearLayout) convertView.findViewById(R.id.ll_pickup_item_container);
            holder.tv_pickup_item_code = (TextView) convertView.findViewById(R.id.tv_pickup_item_code);
            holder.img_arrow_next = (ImageView) convertView.findViewById(R.id.img_arrow_next);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        PickupItem pickupItem = getItem(position);
        if (pickupItem != null)
        {
            if (pickupItem.isCompleted())
                holder.tv_pickup_item_code.setText(pickupItem.getCombinedCode());
            else if (pickupItem.isUnlocked())
                holder.tv_pickup_item_code.setText(pickupItem.getCode());
            else
                holder.tv_pickup_item_code.setText(pickupItem.getLockedCode());

            if (pickupItem.isCompleted())
                holder.ll_pickup_item_container.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorSuccess));

            if (!pickupItem.isStatusValid())
            {
                holder.ll_pickup_item_container.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                holder.img_arrow_next.setVisibility(View.GONE);
            }
            else
            {
                if (mOnItemClickListener != null)
                    holder.ll_pickup_item_container.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            mOnItemClickListener.onBookingItemClick(position);
                        }
                    });
                else
                    holder.img_arrow_next.setVisibility(View.GONE);
            }

        }

        return convertView;
    }

//    public void updatePickupItem(PickupItem pickupItem)
//    {
//        for (int i = 0; i < getCount(); i++)
//        {
//            PickupItem adapterPickupItem = getItem(i);
//            if (adapterPickupItem != null && adapterPickupItem.getCode().equals(pickupItem.getCode()))
//            {
//                remove(adapterPickupItem);
//                insert(pickupItem, i);
//            }
//        }
//
//        notifyDataSetChanged();
//    }

    public void updateData(List<PickupItem> pickupItemList)
    {
        clear();
        addAll(pickupItemList);
        notifyDataSetChanged();
    }

    /** HAXX00R */
    public void addPickupItemView(final int position, ViewGroup parent)
    {
        View view = getView(position, null, parent);
        parent.addView(view);
    }

    public void updatePickupItemView(PickupItem pickupItem, ViewGroup parent)
    {
//        int position = getPosition(pickupItem);
//        getView(position, mViewList.get(position), parent);
    }
    /** HAXX00R END */

    private class ViewHolder
    {
        LinearLayout ll_pickup_item_container;
        TextView tv_pickup_item_code;
        ImageView img_arrow_next;
    }

    public interface IOnItemClickListener
    {
        void onBookingItemClick(Integer position);
    }
}
