package com.anotites.DAO;

import com.anotites.pojos.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }
}
