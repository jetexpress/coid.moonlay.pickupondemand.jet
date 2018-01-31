package coid.moonlay.pickupondemand.jet.fragment;


import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryDetailFragment
{

}
//public class HistoryDetailFragment extends BaseMainFragment implements PickupItemAdapter.IOnItemClickListener
//{
//    private static final String HISTORY_ID_ARGS_PARAM = "historyIdParam";
//    private History mHistory;
//    private List<HistoryBooking> mHistoryBookingList;
//    private PickupItemAdapter mBookingNumberAdapter;
//
//    private TextView tv_pickup_number, tv_status, tv_date, tv_name, tv_phone_number,
//            tv_address, tv_weight, tv_dimension, tv_volume, tv_payment_method, tv_price;
//    private LinearLayout ll_booking_number_container;
//
//    public HistoryDetailFragment()
//    {
//        // Required empty public constructor
//    }
//
//    public static HistoryDetailFragment newInstance(Long historyId)
//    {
//        HistoryDetailFragment fragment = new HistoryDetailFragment();
//        Bundle args = new Bundle();
//        args.putLong(HISTORY_ID_ARGS_PARAM, historyId);
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
//            Long historyId = getArguments().getLong(HISTORY_ID_ARGS_PARAM);
//            mHistory = History.load(History.class, historyId);
//            mHistoryBookingList = mHistory.getHistoryBookingList();
//            mBookingNumberAdapter = new PickupItemAdapter(mContext, mHistoryBookingList, this);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState)
//    {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_history_detail, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
//    {
//        super.onViewCreated(view, savedInstanceState);
//        setView();
//        setValue();
//    }
//
//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        setTitle(Utility.Message.get(R.string.history));
//        setNotificationMenuEnabled(false);
//        setDisplayBackEnabled(true);
//    }
//
//    @Override
//    public void onBookingItemClick(Integer position)
//    {
//        HistoryBooking historyBooking = (HistoryBooking) mBookingNumberAdapter.getItem(position);
//        showToast(historyBooking.toString());
//    }
//
//    private void setView()
//    {
//        tv_pickup_number = (TextView) findViewById(R.id.tv_pickup_number);
//        tv_status = (TextView) findViewById(R.id.tv_status);
//        tv_date = (TextView) findViewById(R.id.tv_date);
//        tv_name = (TextView) findViewById(R.id.tv_name);
//        tv_phone_number = (TextView) findViewById(R.id.tv_phone_number);
//        tv_address = (TextView) findViewById(R.id.tv_address);
//        tv_weight = (TextView) findViewById(R.id.tv_weight);
//        tv_dimension = (TextView) findViewById(R.id.tv_dimension);
//        tv_volume = (TextView) findViewById(R.id.tv_volume);
//        tv_payment_method = (TextView) findViewById(R.id.tv_payment_method);
//        tv_price = (TextView) findViewById(R.id.tv_price);
//        ll_booking_number_container = (LinearLayout) findViewById(R.id.ll_booking_number_container);
//    }
//
//    private void setValue()
//    {
//        tv_pickup_number.setText(mHistory.getPickupNumberDetail());
//        tv_status.setText(mHistory.getStatus());
//        tv_date.setText(mHistory.getDate());
//        tv_name.setText(mHistory.getCustomerName());
//        tv_phone_number.setText(mHistory.getCustomerPhoneNumber());
//        tv_address.setText(mHistory.getCustomerAddress());
//        tv_weight.setText(mHistory.getWeightInKgString());
//        tv_dimension.setText(mHistory.getDimension());
//        tv_volume.setText(mHistory.getSpannableVolumeString());
//        tv_payment_method.setText(mHistory.getPaymentMethod());
//        tv_price.setText(mHistory.getPriceString());
//
//        for (int i = 0; i < mHistoryBookingList.size(); i++)
//        {
//            View bookingNumberListItem = mBookingNumberAdapter.getView(i, null, ll_booking_number_container);
//            ll_booking_number_container.addView(bookingNumberListItem);
//        }
//    }
//}
