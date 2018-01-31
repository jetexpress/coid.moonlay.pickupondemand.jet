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
import coid.moonlay.pickupondemand.jet.model.Product;
import coid.moonlay.pickupondemand.jet.model.db.DBContract;
import coid.moonlay.pickupondemand.jet.model.db.ModelLoader;

public class ProductItemSelectDialogFragment extends BaseItemSelectDialogFragment<Product>
{
    @Override
    protected String getTitle()
    {
        return Utility.Message.get(R.string.pickup_item_product);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (getLoaderManager().getLoader(AppConfig.LoaderId.PRODUCT_LOADER_ID) != null)
            getLoaderManager().restartLoader(AppConfig.LoaderId.PRODUCT_LOADER_ID, null, productLoader);
        else
            getLoaderManager().initLoader(AppConfig.LoaderId.PRODUCT_LOADER_ID, null, productLoader);
    }

    LoaderManager.LoaderCallbacks<List<Product>> productLoader = new LoaderManager.LoaderCallbacks<List<Product>>()
    {
        @Override
        public Loader<List<Product>> onCreateLoader(int id, Bundle args)
        {
            return new ModelLoader<>(mContext, Product.class);
        }

        @Override
        public void onLoadFinished(Loader<List<Product>> loader, List<Product> data)
        {
            updateData(data);
        }

        @Override
        public void onLoaderReset(Loader<List<Product>> loader)
        {

        }
    };
}
