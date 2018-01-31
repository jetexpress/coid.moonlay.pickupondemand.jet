package coid.moonlay.pickupondemand.jet.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.activeandroid.query.Select;

import java.util.List;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseItemSelectDialogFragment;
import coid.moonlay.pickupondemand.jet.config.AppConfig;
import coid.moonlay.pickupondemand.jet.model.OperationStatus;
import coid.moonlay.pickupondemand.jet.model.Product;
import coid.moonlay.pickupondemand.jet.model.db.DBContract;
import coid.moonlay.pickupondemand.jet.model.db.ModelLoader;

public class OperationStatusSelectDialogFragment extends BaseItemSelectDialogFragment<OperationStatus>
{
    @Override
    protected String getTitle()
    {
        return Utility.Message.get(R.string.delivery_choose_status);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (getLoaderManager().getLoader(AppConfig.LoaderId.OPERATION_STATUS_LOADER_ID) != null)
            getLoaderManager().restartLoader(AppConfig.LoaderId.OPERATION_STATUS_LOADER_ID, null, operationStatusLoader);
        else
            getLoaderManager().initLoader(AppConfig.LoaderId.OPERATION_STATUS_LOADER_ID, null, operationStatusLoader);
    }

    LoaderManager.LoaderCallbacks<List<OperationStatus>> operationStatusLoader = new LoaderManager.LoaderCallbacks<List<OperationStatus>>()
    {
        @Override
        public Loader<List<OperationStatus>> onCreateLoader(int id, Bundle args)
        {
            return new ModelLoader<>(mContext,
                    OperationStatus.class,
                    new Select()
                            .from(OperationStatus.class)
                            .where(DBContract.OperationStatusEntry.COLUMN_CODE + " <> '" + OperationStatus.CODE_SUCCESS + "'"),
                    false);
        }

        @Override
        public void onLoadFinished(Loader<List<OperationStatus>> loader, List<OperationStatus> data)
        {
            updateData(data);
        }

        @Override
        public void onLoaderReset(Loader<List<OperationStatus>> loader)
        {

        }
    };
}
