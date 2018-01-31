package coid.moonlay.pickupondemand.jet.fragment;


import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */

public class NotificationDetailFragment
{

}
//public class NotificationDetailFragment extends BaseMainFragment
//{
//    private static final String NOTIFICATION_ID_ARGS_PARAM = "notificationIdParam";
//    private Notification mNotification;
//
//    private TextView tv_delivery_type, tv_time,
//            tv_name, tv_address, tv_address_detail,
//            tv_phone, tv_weight;
//    private Button btn_receive_pickup;
//
//    public NotificationDetailFragment()
//    {
//        // Required empty public constructor
//    }
//
//    public static NotificationDetailFragment newInstance(Long notificationId)
//    {
//        NotificationDetailFragment fragment = new NotificationDetailFragment();
//        Bundle args = new Bundle();
//        args.putLong(NOTIFICATION_ID_ARGS_PARAM, notificationId);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null)
//        {
//            Long notificationId = getArguments().getLong(NOTIFICATION_ID_ARGS_PARAM);
//            mNotification = Notification.load(Notification.class, notificationId);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState)
//    {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_notification_detail, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
//    {
//        super.onViewCreated(view, savedInstanceState);
//        setView();
//        setValue();
//        setEvent();
//    }
//
//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        setTitle(Utility.Message.get(R.string.notification));
//        setNotificationMenuEnabled(false);
//        setDisplayBackEnabled(true);
//    }
//
//    @Override
//    protected void onNotificationMenuItemClicked()
//    {
//        //do nothing
//    }
//
//    private void setView()
//    {
//        tv_delivery_type = (TextView) findViewById(R.id.tv_delivery_type);
//        tv_time = (TextView) findViewById(R.id.tv_time);
//        tv_name = (TextView) findViewById(R.id.tv_name);
//        tv_address = (TextView) findViewById(R.id.tv_address);
//        tv_address_detail = (TextView) findViewById(R.id.tv_address_detail);
//        tv_phone = (TextView) findViewById(R.id.tv_phone);
//        tv_weight = (TextView) findViewById(R.id.tv_weight);
//        btn_receive_pickup = (Button) findViewById(R.id.btn_receive_pickup);
//    }
//
//    private void setValue()
//    {
//        tv_delivery_type.setText(mNotification.getDeliveryType());
//        tv_time.setText(mNotification.getTime());
//        tv_name.setText(mNotification.getCustomerName());
//        tv_address.setText(mNotification.getCustomerAddress());
//        tv_address_detail.setText(mNotification.getCustomerAddressDetail());
//        tv_phone.setText(mNotification.getCustomerPhone());
//        String weight = String.valueOf(mNotification.getWeight()) + " kg";
//        tv_weight.setText(weight);
//    }
//
//    private void setEvent()
//    {
//        btn_receive_pickup.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                showToast("Menerima Pengiriman");
//                getNavigator().back();
//            }
//        });
//    }
//}
