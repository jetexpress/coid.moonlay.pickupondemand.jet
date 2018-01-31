package coid.moonlay.pickupondemand.jet.fragment;

import android.view.View;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseItemSelectDialogFragment;
import coid.moonlay.pickupondemand.jet.model.Contact;

public class ContactSelectDialogFragment extends BaseItemSelectDialogFragment<Contact>
{
    @Override
    protected String getTitle()
    {
        return Utility.Message.get(R.string.task_choose_contact);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateData(Contact.getContactList());
    }
}
