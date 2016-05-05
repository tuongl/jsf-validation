package com.gendata.faces.validation.core.level;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;

import com.gendata.faces.validation.core.ValidationException;

public abstract interface AbstractValidationLevelHandler {

   boolean handleRequest(FacesContext context,
                         PhaseEvent phaseEvent) throws ValidationException;
}
