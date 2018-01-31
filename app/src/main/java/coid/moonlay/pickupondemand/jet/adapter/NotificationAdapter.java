package coid.moonlay.pickupondemand.jet.adapter;

public class NotificationAdapter
{

}
//public class NotificationAdapter extends ArrayAdapter<Notification>
//{
//    private Context mContext;
//    private LayoutInflater mLayoutInflater;
//    private ViewBinderHelper mBinderHelper;
//    private ISwipeRevealLayoutOnClickListener mSwipeRevealLayoutOnClickListener;
//    private String mOpenItemId;
//
//    public NotificationAdapter(Context context, List<Notification> notificationList, ISwipeRevealLayoutOnClickListener swipeRevealLayoutOnClickListener)
//    {
//        super(context, R.layout.list_item_notification, notificationList);
//        mContext = context;
//        mLayoutInflater = LayoutInflater.from(context);
//        mSwipeRevealLayoutOnClickListener = swipeRevealLayoutOnClickListener;
//        mBinderHelper = new ViewBinderHelper();
//        mBinderHelper.setOpenOnlyOne(true);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//        ViewHolder holder;
//
//        if (convertView == null)
//        {
//            convertView = mLayoutInflater.inflate(R.layout.list_item_notification, parent, false);
//            holder = new ViewHolder(convertView);
//            convertView.setTag(holder);
//        }
//        else
//        {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        Notification notification = getItem(position);
//        if (notification != null)
//        {
//            holder.position = position;
//            setEvent(holder, notification);
//            setValue(holder, notification);
//        }
//
//        return convertView;
//    }
//
//    private void setEvent(final ViewHolder holder, final Notification notification)
//    {
//        mBinderHelper.bind(holder.swipe_reveal_layout_notification_item_container, String.valueOf(notification.getId()));
//        holder.swipe_reveal_layout_notification_item_container.setSwipeListener(new SwipeRevealLayout.SwipeListener()
//        {
//            @Override
//            public void onClosed(SwipeRevealLayout view)
//            {
//
//            }
//
//            @Override
//            public void onOpened(SwipeRevealLayout view)
//            {
//                mOpenItemId = String.valueOf(notification.getId());
//            }
//
//            @Override
//            public void onSlide(SwipeRevealLayout view, float slideOffset)
//            {
//
//            }
//        });
//        holder.fl_accept_task_item_container.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                mSwipeRevealLayoutOnClickListener.onSecondaryLayoutClicked(holder.position);
//            }
//        });
//
//        holder.ll_notification_container.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                mSwipeRevealLayoutOnClickListener.onMainLayoutClicked(holder.position);
//            }
//        });
//    }
//
//    private void setValue(ViewHolder holder, Notification notification)
//    {
//        String deliveryType = notification.getDeliveryType() + " " + notification.getDistance();
//        String name = notification.getCustomerName() + " - " + notification.getCustomerAddress();
//
//        holder.tv_notification_time.setText(notification.getTime());
//        holder.tv_notification_delivery_type.setText(deliveryType);
//        holder.tv_notification_name.setText(name);
//        if (notification.getActive())
//            holder.ll_notification_container.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorBackgroundPrimary));
//        else
//            holder.ll_notification_container.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPanelPrimary));
//    }
//
//    public void closeOpeneditem()
//    {
//        mBinderHelper.closeLayout(mOpenItemId);
//    }
//
//    public void updateData(List<Notification> notificationList)
//    {
//        clear();
//        addAll(notificationList);
//        notifyDataSetChanged();
//    }
//
//    private class ViewHolder
//    {
//        SwipeRevealLayout swipe_reveal_layout_notification_item_container;
//        FrameLayout fl_accept_task_item_container;
//        LinearLayout ll_notification_container;
//        TextView tv_notification_time, tv_notification_delivery_type, tv_notification_name;
//        Integer position;
//
//        public ViewHolder()
//        {
//
//        }
//
//        public ViewHolder(View convertView)
//        {
//            swipe_reveal_layout_notification_item_container = (SwipeRevealLayout) convertView.findViewById(R.id.swipe_reveal_layout_notification_item_container);
//            fl_accept_task_item_container = (FrameLayout) convertView.findViewById(R.id.fl_accept_task_item_container);
//            ll_notification_container = (LinearLayout) convertView.findViewById(R.id.ll_notification_container);
//            tv_notification_time = (TextView) convertView.findViewById(R.id.tv_time);
//            tv_notification_delivery_type = (TextView) convertView.findViewById(R.id.tv_delivery_type);
//            tv_notification_name = (TextView) convertView.findViewById(R.id.tv_name);
//        }
//    }
//}
