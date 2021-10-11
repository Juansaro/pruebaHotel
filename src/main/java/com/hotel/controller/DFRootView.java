package com.hotel.controller;


import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

@Named("dfRootView")
@RequestScoped
public class DFRootView {

    public void openLevel1() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        PrimeFaces.current().dialog().openDynamic("level1", options, null);
    }

    public void onReturnFromLevel1(SelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Data Returned", event.getObject().toString()));
    }
}