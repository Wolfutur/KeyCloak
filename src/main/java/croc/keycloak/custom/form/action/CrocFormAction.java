package croc.keycloak.custom.form.action;

import org.keycloak.Config;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.authentication.FormContext;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.events.Details;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.*;
import org.keycloak.provider.ProviderConfigProperty;
import javax.ws.rs.core.MultivaluedMap;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.services.validation.Validation;

import java.util.ArrayList;
import java.util.List;

public class CrocFormAction implements FormAction, FormActionFactory {
    private static final String ID = "croc_form_action";
    private static final AuthenticationExecutionModel.Requirement[] REQUIMENTS={
            AuthenticationExecutionModel.Requirement.ALTERNATIVE,
            AuthenticationExecutionModel.Requirement.REQUIRED
    };
    @Override
    public void close(){}

    @Override
    public void buildPage(FormContext formContext, LoginFormsProvider loginFormsProvider)
    {}
    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realmModel, UserModel userModel)
    {
        return false;
    }
    @Override
    public boolean requiresUser()
    {
        return false;
    }
    @Override
    public void  setRequiredActions(KeycloakSession session,RealmModel realmModel,UserModel userModel)
    {

    }
    @Override
    public  void  success(FormContext formContext){

    }
    @Override
    public void validate(ValidationContext context)
    {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        List<FormMessage> errors = new ArrayList<>();
        context.getEvent().detail(Details.REGISTER_METHOD,"form");
        if(Validation.isBlank(formData.getFirst("user.attributes.companyName"))){
            errors.add(new FormMessage("companyName","missingOrgNameMessage"));
        }
        if(errors.size()>0){
            context.validationError(formData,errors);
        }
        else {
            context.success();
        }

    }
    @Override
    public FormAction create(KeycloakSession session)
    {
        return this;
    }
    @Override
    public String getId(){
        return ID;
    }
    @Override
    public void  init(Config.Scope scope)
    {

    }
    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory)
    {

    }
    @Override
    public String getDisplayType(){
        return "CrocFormAction";
    }
    @Override
    public String getReferenceCategory(){
        return null;
    }
    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices(){
        return REQUIMENTS;
    }
    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return null;
    }

    @Override
    public String getHelpText() {
        return "Validates Signup form fields.";
    }
}
