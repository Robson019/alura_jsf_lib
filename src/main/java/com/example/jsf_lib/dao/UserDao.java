package com.example.jsf_lib.dao;

import com.example.jsf_lib.models.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class UserDao {
    public boolean exists(User user) {
        EntityManager em = new JPAUtil().getEntityManager();
        TypedQuery<User> query = em.createQuery("select u from User u where u.email = :pEmail and u.password = :pPassword", User.class);
        query.setParameter("pEmail", user.getEmail());
        query.setParameter("pPassword", user.getPassword());

        try {
            User result = query.getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
        em.close();

        return true;
    }
}