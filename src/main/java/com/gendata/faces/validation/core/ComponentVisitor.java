package com.gendata.faces.validation.core;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public interface ComponentVisitor {

   void visitComponent(FacesContext context,
                       UIComponent component);
}
