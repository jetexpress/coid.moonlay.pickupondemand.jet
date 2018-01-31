package coid.moonlay.pickupondemand.jet.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseItemSelectDialogFragment;
import coid.moonlay.pickupondemand.jet.config.AppConfig;
import coid.moonlay.pickupondemand.jet.model.PackagingItem;
import coid.moonlay.pickupondemand.jet.model.db.ModelLoader;

public class PackagingItemSelectDialogFragment extends BaseItemSelectDialogFragment<PackagingItem>
{
    @Override
    protected String getTitle()
    {
        return Utility.Message.get(R.string.pickup_item_packaging);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (getLoaderManager().getLoader(AppConfig.LoaderId.PACKAGING_ITEM_LOADER_ID) != null)
            getLoaderManager().restartLoader(AppConfig.LoaderId.PACKAGING_ITEM_LOADER_ID, null, packagingItemLoader);
        else
            getLoaderManager().initLoader(AppConfig.LoaderId.PACKAGING_ITEM_LOADER_ID, null, packagingItemLoader);
    }

    LoaderManager.LoaderCallbacks<List<PackagingItem>> packagingItemLoader = new LoaderManager.LoaderCallbacks<List<PackagingItem>>()
    {
        @Override
        public Loader<List<PackagingItem>> onCreateLoader(int id, Bundle args)
        {
            return new ModelLoader<>(mContext, PackagingItem.class);
        }

        @Override
        public void onLoadFinished(Loader<List<PackagingItem>> loader, List<PackagingItem> data)
        {
            updateData(data);
        }

        @Override
        public void onLoaderReset(Loader<List<PackagingItem>> loader)
        {

        }
    };
}
