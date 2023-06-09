package com.example.jsf_lib.beans;

import com.example.jsf_lib.dao.UserDao;
import com.example.jsf_lib.models.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class LoginBean {
    private User user = new User();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String signIn() {
        System.out.println("Logging...");

        boolean userRegistered = new UserDao().exists(this.user);
        FacesContext context = FacesContext.getCurrentInstance();

        if (userRegistered) {
            context.getExternalContext().getSessionMap().put("userLogged", this.user);
            return "book?faces-redirect-true";
        }

        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("[ERROR] User not found"));

        return "login?faces-redirect-true";
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("userLogged");
        return "login?faces-redirect-true";
    }
}