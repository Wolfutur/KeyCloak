package croc.keycloak.custom.flow.authenticator;

import org.keycloak.Config;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticator;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;

import java.util.*;


public class CrocAuthenticatorFactory implements ConditionalAuthenticatorFactory {

    private static final ConditionalAuthenticator AUTHENTICATOR_INSTANSE = new CrocAuthenticator();
    private static final String ID = "croc_authenticator";
    private static final  String TYPE = "Croc Authenticator";
    private static final  String HELP_TEXT="Test Croc Authentication flow";
    public static final String CONFIG_NAME="letter_in_username";
    /*@Override
    public Authenticator create(KeycloakSession keycloakSession) {return AUTHENTICATOR_INSRANSE;}*/

    @Override
    public ConditionalAuthenticator getSingleton() {
        return AUTHENTICATOR_INSTANSE;
    }
    @Override
    public void init(Config.Scope scope) {
    }
    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getDisplayType() {
        return TYPE;
    }

    @Override
    public String getReferenceCategory() {
        return "condition";
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[]
                {AuthenticationExecutionModel.Requirement.REQUIRED,
                 AuthenticationExecutionModel.Requirement.DISABLED};
    }

    @Override
    public boolean isUserSetupAllowed() {
        return true;
    }

    @Override
    public String getHelpText() {
        return HELP_TEXT;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties(){
        return Collections.unmodifiableList(ProviderConfigurationBuilder.create()
                .property().name(CONFIG_NAME)
                .label("Substring")
                .helpText("Substring that should be in username")
                .type(ProviderConfigProperty.STRING_TYPE).add()
                .build());
    }
}
