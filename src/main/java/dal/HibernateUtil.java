package dal;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil
{
    //region Field
    private static final Logger log = Logger.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory = createSessionFactory();
    //endregion


    //region Method
    protected static SessionFactory createSessionFactory()
    {
        log.info("# Start 'HibernateUtil' => buildSessionFactory() method.");
        try
        {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            SessionFactory newSession = metadata.getSessionFactoryBuilder().build();
            log.debug( String.format("## Class 'HibernateUtil' => 'sessionFactory': Created\n session = ['%s'] ", newSession.toString()));
            return newSession;
        }
        catch(HibernateException exception)
        {
            log.error(String.format("@ Problem 'HibernateUtil' => Problem creating sessionFactory!: '%s'", exception.getMessage()));
            exception.printStackTrace();
        }

        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            throw new ExceptionInInitializerError("@ Fail 'HibernateUtil' => Initial sessionFactory failed " + e);
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory()
    {
        log.info("# Start 'HibernateUtil' => getSessionFactory() method");
        log.debug("## Class 'HibernateUtil' => sessionFactory: [" + sessionFactory.toString()+"]");
        return sessionFactory;
    }

    public static void shutdown()
    {
        log.info("# Start 'HibernateUtil' => shutdown() method");
        // Close caches and connection pools
        log.debug("\n## session = ['%s']" + sessionFactory.toString());
        getSessionFactory().close();
        log.debug("## Class 'HibernateUtil' => sessionFactory: Closed");
    }

    public static void shutdown(SessionFactory someSession)
    {
        log.info("# Start 'HibernateUtil' => shutdown(someSession) method");
        // Close caches and connection pools
        log.debug("\n## someSession = ['%s']" + someSession.toString());
        someSession.close();
        log.debug("## Class 'HibernateUtil' => sessionFactory: Closed");
    }
    //endregion
}
