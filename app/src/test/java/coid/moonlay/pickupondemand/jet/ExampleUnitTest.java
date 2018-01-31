package coid.moonlay.pickupondemand.jet;

import com.activeandroid.ActiveAndroid;

import org.junit.Test;

import coid.moonlay.pickupondemand.jet.model.Login;
import coid.moonlay.pickupondemand.jet.model.db.DBQuery;
import coid.moonlay.pickupondemand.jet.request.LoginRequest;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest
{
    @Test
    public void addition_isCorrect() throws Exception
    {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testLogin() throws Exception
    {
        ActiveAndroid.initialize(JetApplication.getInstance());
        String username = "administrator@jet.com";
        String password = "Standar123.";
        LoginRequest loginRequest = new LoginRequest(username, password);
        loginRequest.execute();

        Login login = DBQuery.getSingle(Login.class);
        assertTrue(login != null);
    }
}