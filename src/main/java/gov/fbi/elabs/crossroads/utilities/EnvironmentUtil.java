package gov.fbi.elabs.crossroads.utilities;

import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import gov.fbi.elabs.crossroads.config.EnvironmentConfig;

/**
 * Use this to get CSP service discovery environment variable
 */
public class EnvironmentUtil {

    private static final Logger log = LoggerFactory.getLogger(EnvironmentUtil.class);

    private static Map<String, String> values = null;

    public static String getProperty(String name) throws IllegalArgumentException{
        String app_id = EnvironmentConfig.getProperty("APP_ID");
        String value;
        if(app_id != null && !app_id.isEmpty()){//csp
            if(values == null){
                setEnvironmentValues();
            }

            String environment = EnvironmentConfig.getProperty("ENVIRONMENT");
            value = (String)values.get(environment + "_" + name);
        }else{
            value = EnvironmentConfig.getProperty(name);
        }

        return value;
    }

    private static void setEnvironmentValues(){

        String url = EnvironmentConfig.getProperty("marketplace.service.discovery");

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url,
                    JSONObject.class);
            JSONObject obj = responseEntity.getBody();

            if (obj != null) {
                values = (Map<String, String>)obj.get("key_value");
            }
        }catch (Exception e){
            log.error("Error getting market place details. " + url, e);
            throw new IllegalArgumentException("Error getting market place details.");
        }
    }

}