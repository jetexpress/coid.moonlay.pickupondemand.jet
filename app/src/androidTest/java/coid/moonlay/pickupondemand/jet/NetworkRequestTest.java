package coid.moonlay.pickupondemand.jet;

import android.test.AndroidTestCase;

import com.activeandroid.ActiveAndroid;

import java.util.List;

import coid.moonlay.pickupondemand.jet.model.Login;
import coid.moonlay.pickupondemand.jet.model.Relation;
import coid.moonlay.pickupondemand.jet.model.db.DBQuery;
import coid.moonlay.pickupondemand.jet.request.LoginRequest;
import coid.moonlay.pickupondemand.jet.request.RelationRequest;

public class NetworkRequestTest extends AndroidTestCase
{
    public void initialize()
    {
        ActiveAndroid.initialize(getContext());
    }

    public void testLogin() throws Exception
    {
        initialize();
        String username = "administrator@jet.com";
        String password = "Standar123.";
        LoginRequest loginRequest = new LoginRequest(username, password);
        loginRequest.execute();

        Login login = DBQuery.getSingle(Login.class);
        assertTrue(login != null);
    }

    public void testGetRelations() throws Exception
    {
        initialize();
        RelationRequest relationRequest = new RelationRequest();
        relationRequest.execute();
        List<Relation> relationList = DBQuery.getAll(Relation.class);
        assertTrue(relationList.size() > 0);
    }
}
