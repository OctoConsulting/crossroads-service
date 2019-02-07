package gov.fbi.elabs.crossroads.config;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
//FORCE
@Configuration
public class EnvironmentConfig  implements EnvironmentAware {

    private static Environment env = null;

    @Override
    public void setEnvironment(Environment env) {
        EnvironmentConfig.env = env;
    }

    public static String getProperty(String propertyName) {
        if(env != null)
            return env.getProperty(propertyName);
        else
            return null;
    }
    
    @Bean
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
		return hemf.getSessionFactory();
	}
}
