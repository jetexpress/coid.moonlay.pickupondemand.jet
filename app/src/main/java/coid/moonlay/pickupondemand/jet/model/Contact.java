package coid.moonlay.pickupondemand.jet.model;

import java.util.ArrayList;
import java.util.List;

import coid.moonlay.pickupondemand.jet.base.IBaseItemSelectModel;

public class Contact implements IBaseItemSelectModel
{
    private static String CONTACT_TYPE_SMS = "sms";
    private static String CONTACT_TYPE_PHONE = "phone";

    private String type;
    private String description;

    public Contact()
    {

    }

    public Contact(String type, String description)
    {
        this.type = type;
        this.description = description;
    }

    public Boolean isPhone()
    {
        return this.type.equals(CONTACT_TYPE_PHONE);
    }

    public Boolean isSMS()
    {
        return this.type.equals(CONTACT_TYPE_SMS);
    }

    public static List<Contact> getContactList()
    {
        List<Contact> contactList = new ArrayList<>();
        contactList.add(new Contact(CONTACT_TYPE_SMS, "SMS"));
        contactList.add(new Contact(CONTACT_TYPE_PHONE, "Phone Call"));
        return contactList;
    }

    @Override
    public CharSequence getItemSelectCode()
    {
        return this.type;
    }

    @Override
    public CharSequence getItemSelectDescription()
    {
        return this.description;
    }
}
