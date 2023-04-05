package croc.keycloak.custom.flow;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.*;


public class CrocAuthenticatorFactory implements AuthenticatorFactory {

    private static final Authenticator AUTHENTICATOR_INSRANSE = new CrocAuthenticator();
    private static final String ID = "croc_authenticator";
    private static final  String TYPE = "Croc Authenticator";
    private static final  String HELP_TEXT="Test Croc Authentication flow";

    @Override
    public Authenticator create(KeycloakSession keycloakSession) {return AUTHENTICATOR_INSRANSE;}

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
        return null;
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[]
                {AuthenticationExecutionModel.Requirement.REQUIRED};
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getHelpText() {
        return HELP_TEXT;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties(){
        ArrayList<ProviderConfigProperty> properties = new ArrayList<>(){
        };
        ProviderConfigProperty property1 = new ProviderConfigProperty();
        property1.setType(ProviderConfigProperty.STRING_TYPE);
        property1.setName("Property 1");
        property1.setLabel("Prop 1");
        property1.setHelpText("Help 1");

        ProviderConfigProperty property2 = new ProviderConfigProperty();
        property1.setType(ProviderConfigProperty.STRING_TYPE);
        property1.setName("Property 2");
        property1.setLabel("Prop 2");
        property1.setHelpText("Help 2");

        properties.add(property1);
        properties.add(property2);
        return properties;
    }
}
