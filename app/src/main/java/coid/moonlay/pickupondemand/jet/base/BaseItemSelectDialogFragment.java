package coid.moonlay.pickupondemand.jet.base;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseItemSelectDialogFragment <T extends IBaseItemSelectModel> extends BaseHasBasicLayoutDialogFragment
{
    public static final String SELECTED_ITEM_CODE_ARGS_PARAM = "selectedItemCodeParam";
    public static final String SELECTED_ITEM_DESCRIPTION_ARGS_PARAM = "selectedItemDescriptionParam";

    private LinearLayout ll_content_container;
    private TextView tv_title;
    private EditText et_filter;
    private ListView list_view_item_select;

    private List<T> mDataList;
    private BaseItemSelectListAdapter<T> mAdapter;
    private Handler mHandler;
    private String mFilter;
    private Long mFilterDelay = 2000L;

    public BaseItemSelectDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.CustomDialog);
        mDataList = new ArrayList<>();
        mAdapter = new BaseItemSelectListAdapter<>(mContext, mDataList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.base_dialog_fragment_item_select, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setValue();
        setEvent();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null && d.getWindow() != null)
        {
            d.getWindow().setGravity(Gravity.CENTER);
            d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            d.setCancelable(true);
            d.setCanceledOnTouchOutside(true);
        }
        hideFilter();
    }

    @Override
    public void onDestroy() {
        Utility.UI.hideKeyboard(mView);
        if (filterRunnable != null)
            filterRunnable = null;
        if (mHandler != null)
        {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        super.onDestroy();
    }

    @Override
    protected View getBaseContentLayout()
    {
        return list_view_item_select;
    }

    private void setView()
    {
        ll_content_container = (LinearLayout) findViewById(R.id.ll_content_container);
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_filter = (EditText) findViewById(R.id.et_filter);
        list_view_item_select = (ListView) findViewById(R.id.list_view_item_select);
    }

    private void setValue()
    {
        tv_title.setText(getTitle());
        list_view_item_select.setAdapter(mAdapter);
    }

    private void setEvent()
    {
        list_view_item_select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                T model =  mDataList.get(position);
                Intent intent = new Intent();
                intent.putExtra(SELECTED_ITEM_CODE_ARGS_PARAM, model.getItemSelectCode());
                intent.putExtra(SELECTED_ITEM_DESCRIPTION_ARGS_PARAM, model.getItemSelectDescription());
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
            }
        });
        et_filter.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (mHandler != null)
                    mHandler.removeCallbacksAndMessages(null);
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (mHandler == null)
                    mHandler = new Handler(Looper.getMainLooper());

                mFilter = s.toString();
                mHandler.postDelayed(filterRunnable, mFilterDelay);
            }
        });
    }

    public String getFilter()
    {
        return mFilter;
    }

    public void showFilter()
    {
        et_filter.setVisibility(View.VISIBLE);
    }

    public void hideFilter()
    {
        et_filter.setVisibility(View.GONE);
    }

    public void setFilterDelay(Long filterDelay)
    {
        mFilterDelay = filterDelay;
    }

    public Runnable filterRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            if (mFilter != null)
                doFilter(mFilter);
        }
    };

    protected void updateData(List<T> dataList)
    {
        mAdapter.updateData(dataList);
    }
    protected void clearData()
    {
        mAdapter.clearData();
    }

    protected void doFilter(String filter){}

    protected abstract String getTitle();
}
