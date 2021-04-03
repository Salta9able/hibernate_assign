package peaksoft.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import peaksoft.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory = buildFactory();

    private static SessionFactory buildFactory() {
        try{
            Properties props = new Properties();
            props.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
            props.put(Environment.DRIVER, "org.postgresql.Driver");
            props.put(Environment.URL, "jdbc:postgresql://localhost:5432/postgres");
            props.put(Environment.USER, "postgres");
            props.put(Environment.PASS, "root");
            props.put(Environment.HBM2DDL_AUTO, "update");


            Configuration conf = new Configuration();
            conf.setProperties(props);
            conf.addAnnotatedClass(User.class);

            StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder();
            StandardServiceRegistry serviceRegistry = srb.applySettings(conf.getProperties()).build();
            SessionFactory sessionFactory = conf.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        }
        catch(Throwable ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        return buildFactory();
    }
    public static void shutdown(){
        getSessionFactory().close();
    }

    //JDBC Connect
    private static final String user = "postgres";
    private static final String password = "root";
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";

    public Connection connect(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url,user, password);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return connection;
    }
}

