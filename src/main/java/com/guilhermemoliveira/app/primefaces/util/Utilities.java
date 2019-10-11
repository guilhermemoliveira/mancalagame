package com.guilhermemoliveira.app.primefaces.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import org.primefaces.PrimeFaces;

public class Utilities {
	
	public static void showMessage(Severity severity, String summary, String detail) {
		PrimeFaces.current().dialog()
		.showMessageDynamic(new FacesMessage(severity, summary, detail));
	}
}
