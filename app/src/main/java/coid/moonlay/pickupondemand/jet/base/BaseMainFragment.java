package coid.moonlay.pickupondemand.jet.base;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.activity.MainActivity;
import coid.moonlay.pickupondemand.jet.fragment.NotificationFragment;

public class BaseMainFragment extends BaseHasBasicLayoutFragment
{
    @Override
    protected View getBaseContentLayout()
    {
        return null;
    }
    //    MainActivity mActivity;
//    Menu mMenu;
//    MenuItem mNotificationMenuItem;
//    Boolean mIsNotificationEnabled;
//    Integer mNotificationCount;
//
//    ImageView img_badge;
//    TextView tv_badge_count;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        if (getActivity() instanceof MainActivity)
//        {
//            mActivity = (MainActivity) getActivity();
//            setHasOptionsMenu(true);
//            mIsNotificationEnabled = true;
//        }
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
//    {
//        super.onCreateOptionsMenu(menu, inflater);
//
//        mMenu = mActivity.getMenu();
//        if (mMenu != null)
//        {
//            mNotificationMenuItem = mMenu.findItem(R.id.action_notification);
//            if (mNotificationMenuItem != null)
//            {
//                mNotificationMenuItem.setVisible(mIsNotificationEnabled);
//
//                img_badge = (ImageView) mNotificationMenuItem.getActionView().findViewById(R.id.img_badge);
//                img_badge.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_notification));
//                tv_badge_count = (TextView) mNotificationMenuItem.getActionView().findViewById(R.id.tv_badge_count);
//                mNotificationCount = 99;
//                tv_badge_count.setText(String.valueOf(mNotificationCount));
//
//                img_badge.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View view)
//                    {
//                        onNotificationMenuItemClicked();
//                    }
//                });
//            }
//        }
//    }
//
//    @Override
//    public void onDestroy()
//    {
//        mActivity.invalidateOptionsMenu();
//        super.onDestroy();
//    }

    protected void setNotificationMenuEnabled(Boolean isEnabled)
    {
//        mIsNotificationEnabled = isEnabled;
//        if (mNotificationMenuItem == null)
//            return;
//
//        mNotificationMenuItem.setVisible(isEnabled);
    }

//    protected void addNotificationCount()
//    {
//        mNotificationCount += 10;
//        tv_badge_count.setText(String.valueOf(mNotificationCount));
//    }
//
//    protected void subtractNotificationCount()
//    {
//        mNotificationCount--;
//        tv_badge_count.setText(String.valueOf(mNotificationCount));
//    }

    protected void onNotificationMenuItemClicked()
    {
//        getNavigator().showFragment(new NotificationFragment());
    }
}
