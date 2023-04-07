package croc.keycloak.custom.flow;


import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticator;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import java.util.Map;

public class CrocAuthenticator implements ConditionalAuthenticator {

    //private static final String USER_AGENT = "Mozilla/5.0";

    //private static final String GET_URL = "http://81.23.10.40:8082";

    //private static final String POST_URL = "http://81.23.10.40:8082/reply/";

    /*private static String sendPost(String userName) throws IOException{
        URL url = new URL(POST_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("UserAgent",USER_AGENT);
        String request = "{\\\"username\\\":\\\""+userName+"\\\"}";
        System.out.println(request);
        connection.setDoOutput(true);
        OutputStream outputStream=connection.getOutputStream();

        outputStream.write(request.getBytes());
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        System.out.println("Response code"+  responseCode);

        if(responseCode==HttpURLConnection.HTTP_OK){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine=bufferedReader.readLine())!=null){
                response.append(inputLine);
            }
            bufferedReader.close();
            return  response.toString();
        }
        else {
            return "Error";
        }
    }*/

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
            return user.getUsername().contains(letter);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }

    }


    /*@Override
    public void authenticate(AuthenticationFlowContext context) {

        var userName=context.getUser().getUsername();
        System.out.println("Username "+userName);
        try {
            var res=sendPost(userName);
            System.out.println("SendPost result "+res);
            if(res.contains("Allow")){
                context.success();
            }
            else{
                context.failure(AuthenticationFlowError.CLIENT_DISABLED);
            }
        }
        catch (Exception e)
        {
            context.failure(AuthenticationFlowError.INTERNAL_ERROR);
        }



    }*/
    @Override
    public void action(AuthenticationFlowContext context) {
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    /*@Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return true;
    }*/

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    @Override
    public void close() {

    }
}
