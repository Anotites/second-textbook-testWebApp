package com.anotites.util;

import com.anotites.pojos.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.logging.Logger;

public class HibernateSessionFactoryUtil {

    private static final SessionFactory sessionFactory;
    private static final Logger log = Logger.getLogger(HibernateSessionFactoryUtil.class.getName());

    static {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Person.class);   // <-- ЯВНАЯ РЕГИСТРАЦИЯ СУЩНОСТИ
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (Throwable e) {
            log.severe("Initial Session Factory creation failed. " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}
