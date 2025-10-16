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
 * Cette version évite le problème JAXB en n'utilisant pas hibernate.cfg.xml
 */
public class HibernateUtil {
    
    private static final SessionFactory sessionFactory;
    
    static {
        try {
            Configuration configuration = new Configuration();
            
            // Configuration des propriétés Hibernate
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/my_database?useSSL=false&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, ""); // Modifiez si vous avez un mot de passe MySQL
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            
            // Affichage des requêtes SQL
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.FORMAT_SQL, "true");
            
            // Auto-génération du schéma
            settings.put(Environment.HBM2DDL_AUTO, "update");
            
            configuration.setProperties(settings);
            
            // Enregistrement des entités annotées
            configuration.addAnnotatedClass(Salle.class);
            configuration.addAnnotatedClass(Machine.class);
            
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            
            System.out.println("✓ SessionFactory créée avec succès!");
            
        } catch (Throwable ex) {
            System.err.println("❌ Échec de la création de SessionFactory: " + ex.getMessage());
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

