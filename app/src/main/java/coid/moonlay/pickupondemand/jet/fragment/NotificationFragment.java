package coid.moonlay.pickupondemand.jet.fragment;


import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment
{

}
//public class NotificationFragment extends BaseMainFragment implements ISwipeRevealLayoutOnClickListener
//{
//    private ListView list_view_notification;
//    private NotificationAdapter mAdapter;
//    private ISwipeRevealLayoutOnClickListener mSwipeRevealLayoutOnClickListener;
//
//    public NotificationFragment()
//    {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        mSwipeRevealLayoutOnClickListener = this;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState)
//    {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_notification, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
//    {
//        super.onViewCreated(view, savedInstanceState);
//        setView();
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
//
////        showLoadingDialog();
//        Utility.Executor.execute(getNotificationListRunnable);
//    }
//
//    @Override
//    protected void onNotificationMenuItemClicked()
//    {
//        // do nothing
//    }
//
//    @Override
//    public void onMainLayoutClicked(Integer position)
//    {
//        Notification notification = mAdapter.getItem(position);
//        getNavigator().showFragment(NotificationDetailFragment.newInstance(notification.getId()));
//    }
//
//    @Override
//    public void onSecondaryLayoutClicked(Integer position)
//    {
//        showToast("accept, position : " + String.valueOf(position));
//    }
//
//    private void setView()
//    {
//        list_view_notification = (ListView) findViewById(R.id.list_view_notification);
//    }
//
//    private void setEvent()
//    {
////        list_view_notification.setOnItemClickListener(new AdapterView.OnItemClickListener()
////        {
////            @Override
////            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
////            {
////                Notification notification = mAdapter.getItem(position);
////                getNavigator().showFragment(NotificationDetailFragment.newInstance(notification.getId()));
////            }
////        });
//    }
//
//    private Runnable getNotificationListRunnable = new Runnable()
//    {
//        @Override
//        public void run()
//        {
//            final List<Notification> notificationList = Notification.getMockupNotificationList();
//            if (notificationList.size() > 0)
//            {
//                getActivity().runOnUiThread(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        mAdapter = new NotificationAdapter(mContext, notificationList, mSwipeRevealLayoutOnClickListener);
//                        list_view_notification.setAdapter(mAdapter);
////                        hideLoadingDialog();
//                    }
//                });
//            }
//        }
//    };
//}
