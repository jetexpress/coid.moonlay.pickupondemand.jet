package coid.moonlay.pickupondemand.jet.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseItemSelectDialogFragment;
import coid.moonlay.pickupondemand.jet.config.AppConfig;
import coid.moonlay.pickupondemand.jet.model.Relation;
import coid.moonlay.pickupondemand.jet.model.db.ModelLoader;

public class RelationSelectDialogFragment extends BaseItemSelectDialogFragment<Relation>
{
    @Override
    protected String getTitle()
    {
        return Utility.Message.get(R.string.delivery_choose_relation);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (getLoaderManager().getLoader(AppConfig.LoaderId.RELATION_LOADER_ID) != null)
            getLoaderManager().restartLoader(AppConfig.LoaderId.RELATION_LOADER_ID, null, relationLoader);
        else
            getLoaderManager().initLoader(AppConfig.LoaderId.RELATION_LOADER_ID, null, relationLoader);
    }

    LoaderManager.LoaderCallbacks<List<Relation>> relationLoader = new LoaderManager.LoaderCallbacks<List<Relation>>()
    {
        @Override
        public Loader<List<Relation>> onCreateLoader(int id, Bundle args)
        {
            return new ModelLoader<>(mContext, Relation.class, false);
        }

        @Override
        public void onLoadFinished(Loader<List<Relation>> loader, List<Relation> data)
        {
            updateData(data);
        }

        @Override
        public void onLoaderReset(Loader<List<Relation>> loader)
        {

        }
    };
}
