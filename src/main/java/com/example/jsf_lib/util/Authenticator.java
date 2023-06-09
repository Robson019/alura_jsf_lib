package com.example.jsf_lib.util;

import com.example.jsf_lib.models.User;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class Authenticator implements PhaseListener {
    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
        FacesContext context = phaseEvent.getFacesContext();
        String viewId = context.getViewRoot().getViewId();

        System.out.println(viewId);

        if("/login.xhtml".equals(viewId)) {
            return;
        }
        User userLogged = (User) context.getExternalContext().getSessionMap().get("userLogged");

        if (userLogged != null) {
            return;
        }

        NavigationHandler handler = context.getApplication().getNavigationHandler();
        handler.handleNavigation(context, null, "/login?faces-redirect-true");
        context.renderResponse();
    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}