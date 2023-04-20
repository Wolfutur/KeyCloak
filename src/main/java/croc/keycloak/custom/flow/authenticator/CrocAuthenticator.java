package croc.keycloak.custom.flow.authenticator;


import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticator;
import org.keycloak.models.*;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Map;


public class CrocAuthenticator implements ConditionalAuthenticator {

    private String getAllowedLetter(AuthenticationFlowContext context)
    {
        AuthenticatorConfigModel configModel = context.getAuthenticatorConfig();
        Map<String,String> config = configModel.getConfig();
        return config.get(CrocAuthenticatorFactory.CONFIG_NAME);
    }


    @Override
    public boolean matchCondition(AuthenticationFlowContext authenticationFlowContext) {
        try {
            UserModel user = authenticationFlowContext.getUser();
            String letter = getAllowedLetter(authenticationFlowContext);
            System.out.println("Username "+user.getUsername());
            System.out.println("Allow letter  "+letter);
            Response challenge=null;
            if(user.getUsername().contains(letter))
            {
                challenge = authenticationFlowContext.form()
                        .createForm("success.ftl");
                authenticationFlowContext.challenge(challenge);
                return true;
            }
            else {
                challenge = authenticationFlowContext.form()
                        .createForm("fail.ftl");
                authenticationFlowContext.challenge(challenge);
                return false;
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    @Override
    public void action(AuthenticationFlowContext context) {
        System.out.println("action path");
        context.success();
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    @Override
    public void close() {

    }
}
