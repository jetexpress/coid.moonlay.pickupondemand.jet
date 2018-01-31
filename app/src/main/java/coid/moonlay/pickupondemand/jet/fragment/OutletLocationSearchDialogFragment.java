package coid.moonlay.pickupondemand.jet.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.adapter.OutletLocationAdapter;
import coid.moonlay.pickupondemand.jet.base.BaseDialogFragment;
import coid.moonlay.pickupondemand.jet.config.AppConfig;
import coid.moonlay.pickupondemand.jet.model.OutletLocation;
import coid.moonlay.pickupondemand.jet.model.db.DBContract;
import coid.moonlay.pickupondemand.jet.model.db.ModelLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class OutletLocationSearchDialogFragment extends BaseDialogFragment
{
    public static final String SELECTED_OUTLET_LOCATION_ID_ARGS_PARAM = "SelectedOutletLocationId";

    private EditText et_search;
    private ListView list_view_outlet_location;
    private TextView tv_not_found_info;

    private String mFilter;
    private List<OutletLocation> mOutletLocationList;

    private OutletLocationAdapter mOutletLocationAdapter;

    public OutletLocationSearchDialogFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.OutletLocationSearchDialog);
        mOutletLocationList = new ArrayList<>();
        mOutletLocationAdapter = new OutletLocationAdapter(mContext, mOutletLocationList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_outlet_location_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setEvent();
        getLoaderManager().initLoader(AppConfig.LoaderId.OUTLET_LOCATION_LOADER_ID, null, outletLocationLoader);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog d = getDialog();
        if (d != null && d.getWindow() != null)
        {
            d.getWindow().setGravity(Gravity.CENTER);
            d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            d.setCancelable(true);
            d.setCanceledOnTouchOutside(true);
        }
    }

    private void setView()
    {
        et_search = (EditText) findViewById(R.id.et_search);
        list_view_outlet_location = (ListView) findViewById(R.id.list_view_outlet_location);
        list_view_outlet_location.setAdapter(mOutletLocationAdapter);
        tv_not_found_info = (TextView) findViewById(R.id.tv_not_found_info);
    }

    private void setEvent()
    {
        final TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}
            @Override
            public void afterTextChanged(Editable s)
            {
                startFilterProcess(s.toString());
            }
        };
        et_search.addTextChangedListener(watcher);
        list_view_outlet_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                OutletLocation outletLocation = mOutletLocationAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(SELECTED_OUTLET_LOCATION_ID_ARGS_PARAM, outletLocation.getId());
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
            }
        });
    }

    private void startFilterProcess(String filter)
    {
        mFilter = filter;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (getActivity() != null && isAdded())
                {
                    getLoaderManager().restartLoader(AppConfig.LoaderId.OUTLET_LOCATION_LOADER_ID, null, outletLocationLoader);
                }
            }
        }, 2000);
    }

    private void showContent()
    {
        list_view_outlet_location.setVisibility(View.VISIBLE);
        tv_not_found_info.setVisibility(View.GONE);
    }

    private void showNotFoundInfo()
    {
        list_view_outlet_location.setVisibility(View.GONE);
        tv_not_found_info.setVisibility(View.VISIBLE);
    }

    private LoaderManager.LoaderCallbacks<List<OutletLocation>> outletLocationLoader = new LoaderManager.LoaderCallbacks<List<OutletLocation>>()
    {
        @Override
        public Loader<List<OutletLocation>> onCreateLoader(int id, Bundle args)
        {
            return new ModelLoader<>(
                    mContext,
                    OutletLocation.class,
                    new Select()
                            .from(OutletLocation.class).where("(" + DBContract.OutletLocationEntry.COLUMN_NAME + " LIKE '%" + mFilter + "%'")
                            .or(DBContract.OutletLocationEntry.COLUMN_ADDRESS + " LIKE '%" + mFilter + "%')")
                            .orderBy(DBContract.OutletLocationEntry.COLUMN_NAME + " ASC"), true);
        }

        @Override
        public void onLoadFinished(Loader<List<OutletLocation>> loader, List<OutletLocation> data)
        {
            if (data.size() > 0)
            {
                if (mFilter != null && !mFilter.isEmpty())
                {
                    mOutletLocationList = data;
                    showContent();
                }
                else
                {
                    mOutletLocationList = new ArrayList<>();
                    showContent();

                }
                mOutletLocationAdapter.updateFilteredList(mOutletLocationList, mFilter);
            }
            else
            {
                if (mFilter != null && !mFilter.isEmpty())
                {
                    showNotFoundInfo();
                }
                else
                {
                    showContent();
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<List<OutletLocation>> loader)
        {

        }
    };
}
