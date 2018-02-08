package coid.moonlay.pickupondemand.jet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;
import java.util.List;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.custom.ISwipeRevealLayoutOnClickListener;
import coid.moonlay.pickupondemand.jet.model.Pickup;
import coid.moonlay.pickupondemand.jet.model.Task;

public class TaskAdapter extends ArrayAdapter<Task>
{
    private Context mContext;
    private ViewBinderHelper mBinderHelper;
    private LayoutInflater mLayoutInflater;
    private ISwipeRevealLayoutOnClickListener mSwipeRevealLayoutOnClickListener;
    private String mOpenItemId;
    private boolean mIsSwipeRevealDisabled = false;
    private String mFilterKeyword;

    public TaskAdapter(Context context, List<Task> taskList, boolean isSwipeRevealDisabled)
    {
        super(context, R.layout.list_item_task, taskList);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mBinderHelper = new ViewBinderHelper();
        mBinderHelper.setOpenOnlyOne(true);
        mIsSwipeRevealDisabled = isSwipeRevealDisabled;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = mLayoutInflater.inflate(R.layout.list_item_task, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        Task task = getItem(position);
        if (task != null)
        {
            holder.position = position;
            setEvent(holder, getItem(position));
            setValue(holder, task);
            setState(holder, task);
        }

        return convertView;
    }

    @Override
    public long getItemId(int position)
    {
        return super.getItemId(position);
    }

    public void setValue(ViewHolder holder, Task task)
    {
        try
        {
            if (mFilterKeyword != null && !mFilterKeyword.isEmpty())
            {
                SpannableString spannableDescription = getColoredStringContainsFilterKey(task.getCode());
                holder.tv_task_code.setText(spannableDescription);
            }
            else
                holder.tv_task_code.setText(task.getCode());
        }
        catch (Exception ex)
        {
            Log.d("FILTER", "filter not found, " + ex.getMessage());
        }

        holder.img_task_type.setImageDrawable(task.getTaskTypeDrawable());
        holder.tv_task_type_label.setText(task.getTaskTypeLabel());
        holder.tv_drs_code.setText(task.getDrsCode());
        holder.tv_address.setText(task.getAddress());
        holder.tv_payment_method.setText(task.getPaymentMethod());
        holder.tv_payment_fee.setText(task.getCODFeeString());
        holder.tv_status.setText(task.getStatus());
    }

    private void setState(ViewHolder holder, Task task)
    {
        if (task.isPickup())
        {
            if (task.isAssigned() || task.isTripStarted())
                holder.swipe_reveal_layout_task_item_container.setLockDrag(false);
            else
                holder.swipe_reveal_layout_task_item_container.setLockDrag(true);

            holder.ll_drs_code_container.setVisibility(View.GONE);
            holder.ll_payment_method_container.setVisibility(View.GONE);
            holder.tv_status.setVisibility(View.VISIBLE);

            if (task.isTripStarted())
                holder.tv_reject_task.setText(Utility.Message.get(R.string.task_cancel));
            else
                holder.tv_reject_task.setText(Utility.Message.get(R.string.pickup_reject));
        }
        else
        {
            holder.swipe_reveal_layout_task_item_container.setLockDrag(true);
            holder.ll_drs_code_container.setVisibility(View.VISIBLE);
            holder.ll_payment_method_container.setVisibility(View.VISIBLE);
            holder.tv_status.setVisibility(View.GONE);
            if (task.isCOD())
                holder.ll_payment_fee_container.setVisibility(View.VISIBLE);
            else
                holder.ll_payment_fee_container.setVisibility(View.GONE);
        }

        if (mIsSwipeRevealDisabled)
            holder.swipe_reveal_layout_task_item_container.setLockDrag(true);
    }

    private void setEvent(final ViewHolder holder, final Task task)
    {
        if (task == null)
            return;

        mBinderHelper.bind(holder.swipe_reveal_layout_task_item_container, String.valueOf(task.getCode()));
        if (!task.isPickup())
        holder.swipe_reveal_layout_task_item_container.setSwipeListener(new SwipeRevealLayout.SwipeListener()
        {
            @Override
            public void onClosed(SwipeRevealLayout view) {}
            @Override
            public void onOpened(SwipeRevealLayout view)
            {
                mOpenItemId = String.valueOf(task.getCode());
            }
            @Override
            public void onSlide(SwipeRevealLayout view, float slideOffset) {}
        });
        holder.fl_reject_task_item_container.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mSwipeRevealLayoutOnClickListener != null)
                    mSwipeRevealLayoutOnClickListener.onSecondaryLayoutClicked(holder.position);
            }
        });
        holder.ll_main_task_item_container.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mSwipeRevealLayoutOnClickListener != null)
                    mSwipeRevealLayoutOnClickListener.onMainLayoutClicked(holder.position);
            }
        });
    }

    public void setSwipeRevealLayoutOnClickListener(ISwipeRevealLayoutOnClickListener swipeRevealLayoutOnClickListener)
    {
        mSwipeRevealLayoutOnClickListener = swipeRevealLayoutOnClickListener;
    }

    public void closeOpeneditem()
    {
        mBinderHelper.closeLayout(mOpenItemId);
    }

    public void update(List<Task> taskList, Boolean isReplace)
    {
        if (isReplace)
        {
            clear();
//            /** ULETBE DUMMY TASK*/
//            add(Task.getDummy());
//            /** ULETBE DUMMY TASK*/
        }
        addAll(taskList);
        notifyDataSetChanged();
    }

    public void updateFilteredList(String filterKeyword, List<Task> taskList, Boolean isReplace)
    {
        mFilterKeyword = filterKeyword;
        update(taskList, isReplace);
    }

    private SpannableString getColoredStringContainsFilterKey(String text)
    {
        SpannableString coloredString = new SpannableString(text);
        Integer searchIndex = 0;
        List<Integer> startIndexList = new ArrayList<>();
        while (searchIndex <= coloredString.length())
        {
            int startIndex = text.toLowerCase().indexOf(mFilterKeyword, searchIndex);
            if (startIndex > -1) startIndexList.add(startIndex);
            else break;
            searchIndex = startIndex + 1;
        }
        for (Integer startIndex : startIndexList)
        {
            int endIndex = startIndex + mFilterKeyword.length();
            coloredString.setSpan(new ForegroundColorSpan(Color.RED), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return coloredString;
    }

    private class ViewHolder
    {
        SwipeRevealLayout swipe_reveal_layout_task_item_container;
        FrameLayout fl_reject_task_item_container;
        LinearLayout ll_main_task_item_container, ll_drs_code_container, ll_payment_method_container, ll_payment_fee_container;
        ImageView img_task_type;
        TextView tv_reject_task, tv_task_type_label, tv_task_code, tv_drs_code, tv_address, tv_payment_method, tv_payment_fee, tv_time, tv_status;
        Integer position;

        private ViewHolder()
        {

        }

        private ViewHolder(View convertView)
        {
            swipe_reveal_layout_task_item_container = (SwipeRevealLayout) convertView.findViewById(R.id.swipe_reveal_layout_task_item_container);
            fl_reject_task_item_container = (FrameLayout) convertView.findViewById(R.id.fl_reject_task_item_container);
            tv_reject_task = (TextView) convertView.findViewById(R.id.tv_reject_task);
            ll_main_task_item_container = (LinearLayout) convertView.findViewById(R.id.ll_main_task_item_container);
            img_task_type = (ImageView) convertView.findViewById(R.id.img_task_type);
            tv_task_type_label = (TextView) convertView.findViewById(R.id.tv_task_type_label);
            tv_task_code = (TextView) convertView.findViewById(R.id.tv_task_code);
            ll_drs_code_container = (LinearLayout) convertView.findViewById(R.id.ll_drs_code_container);
            tv_drs_code = (TextView) convertView.findViewById(R.id.tv_drs_code);
            tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            tv_payment_method = (TextView) convertView.findViewById(R.id.tv_payment_method);
            tv_payment_fee = (TextView) convertView.findViewById(R.id.tv_payment_fee);
            ll_payment_method_container = (LinearLayout) convertView.findViewById(R.id.ll_payment_method_container);
            ll_payment_fee_container = (LinearLayout) convertView.findViewById(R.id.ll_payment_fee_container);
            tv_status = (TextView) convertView.findViewById(R.id.tv_status);

            ll_main_task_item_container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
            {
                @Override
                public void onGlobalLayout()
                {
                    ll_main_task_item_container.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    fl_reject_task_item_container.getLayoutParams().height = ll_main_task_item_container.getHeight();
                    fl_reject_task_item_container.setLayoutParams(fl_reject_task_item_container.getLayoutParams());
                }
            });
        }
    }
}
