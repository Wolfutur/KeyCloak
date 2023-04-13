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
            System.out.println("Allow letter "+letter);
            Response challenge = authenticationFlowContext.form()
                    .createForm("secret-question.ftl");
            authenticationFlowContext.challenge(challenge);

            return user.getUsername().contains(letter);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    @Override
    public void action(AuthenticationFlowContext context) {
        boolean validated = validateAnswer(context);
        if (!validated) {
            Response challenge =  context.form()
                    .setError("badSecret")
                    .createForm("secret-question.ftl");
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challenge);
            return;
        }
        context.success();
    }

    protected boolean validateAnswer(AuthenticationFlowContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String secret = formData.getFirst("secret_answer");
        String credentialId = formData.getFirst("credentialId");
        System.out.println(secret);
        System.out.println(credentialId);
        UserModel user = context.getUser();
        String letter = getAllowedLetter(context);
        return user.getUsername().contains(letter);
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
