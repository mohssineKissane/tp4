package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import entities.Machine;
import entities.Salle;

import java.util.Properties;

/**
 * Classe utilitaire Hibernate avec configuration programmatique
 * Cette version n'utilise PAS hibernate.cfg.xml et évite donc le problème JAXB
 */
public class HibernateUtilProgrammatic {
    
    private static SessionFactory sessionFactory;
    
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                
                // Configuration des propriétés Hibernate
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/my_database?useSSL=false&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, ""); // Modifiez si vous avez un mot de passe
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                
                // Affichage des requêtes SQL
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.FORMAT_SQL, "true");
                settings.put(Environment.USE_SQL_COMMENTS, "true");
                
                // Auto-génération du schéma
                settings.put(Environment.HBM2DDL_AUTO, "update");
                
                // Configuration du pool de connexions
                settings.put(Environment.C3P0_MIN_SIZE, "5");
                settings.put(Environment.C3P0_MAX_SIZE, "20");
                settings.put(Environment.C3P0_TIMEOUT, "300");
                settings.put(Environment.C3P0_MAX_STATEMENTS, "50");
                settings.put(Environment.C3P0_IDLE_TEST_PERIOD, "3000");
                
                configuration.setProperties(settings);
                
                // Enregistrement des entités
                configuration.addAnnotatedClass(Salle.class);
                configuration.addAnnotatedClass(Machine.class);
                
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();
                
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                
                System.out.println("✓ SessionFactory créée avec succès (configuration programmatique)");
                
            } catch (Exception e) {
                System.err.println("❌ Échec de la création de SessionFactory: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
