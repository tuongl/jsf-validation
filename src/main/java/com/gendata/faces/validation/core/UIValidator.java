package com.gendata.faces.validation.core;

import javax.el.ValueExpression;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

public class UIValidator extends UIComponentBase {

   private String name;

   private String type;

   private String forId;

   private boolean validateOnUpdate;

   private boolean validateOnUpdateSet;

   private boolean validateOnRender;

   private boolean validateOnRenderSet;

   private String attachedTo;

   @Override
   public String getFamily() {

      return "javax.faces.validator";
   }

   @Override
   public void decode(final FacesContext context) {

      super.decode(context);
   }

   public String getName() {

      if (name != null) {
         return name;
      }
      final ValueExpression expression = getValueExpression("name");
      if (expression != null) {
         return (String) expression.getValue(getFacesContext().getELContext());
      }
      return null;
   }

   public String getAttachedTo() {
      if (attachedTo != null) {
         return attachedTo;
      }
      final ValueExpression expression = getValueExpression("attachedTo");
      if (expression != null) {
         return (String) expression.getValue(getFacesContext().getELContext());
      }
      return null;
   }

   public void setName(final String name) {

      this.name = name;
   }

   public String getType() {

      if (type != null) {
         return type;
      }
      final ValueExpression expression = getValueExpression("type");
      if (expression != null) {
         return (String) expression.getValue(getFacesContext().getELContext());
      }
      return null;
   }

   public void setType(final String type) {

      this.type = type;
   }

   public String getForId() {

      if (forId != null) {
         return forId;
      }
      final ValueExpression expression = getValueExpression("for");
      if (expression != null) {
         return (String) expression.getValue(getFacesContext().getELContext());
      }
      return null;
   }

   public void setForId(final String forId) {

      this.forId = forId;
   }

   public boolean isValidateOnUpdate() {

      if (validateOnUpdateSet) {
         return validateOnUpdate;
      }
      final ValueExpression expression = getValueExpression("validateOnUpdate");
      if (expression != null) {
         return (Boolean) expression.getValue(getFacesContext().getELContext());
      }
      return false;
   }

   public void setValidateOnUpdate(final boolean validateOnUpdate) {

      this.validateOnUpdate = validateOnUpdate;
      this.validateOnUpdateSet = true;
   }

   public boolean isValidateOnRender() {

      if (validateOnRenderSet) {
         return validateOnRender;
      }
      final ValueExpression expression = getValueExpression("validateOnRender");
      if (expression != null) {
         return (Boolean) expression.getValue(getFacesContext().getELContext());
      }
      return false;
   }

   public void setValidateOnRender(final boolean validateOnRender) {

      this.validateOnRender = validateOnRender;
      this.validateOnRenderSet = true;
   }

   @Override
   public Object saveState(final FacesContext facesContext) {

      final Object[] values = new Object[9];
      values[0] = super.saveState(facesContext);
      values[1] = validateOnUpdate;
      values[2] = validateOnUpdateSet;
      values[3] = validateOnRender;
      values[4] = validateOnRenderSet;
      values[5] = name;
      values[6] = type;
      values[7] = forId;
      values[8] = attachedTo;
      return values;
   }

   @Override
   public void restoreState(final FacesContext facesContext,
                            final Object state) {

      final Object[] values = (Object[]) state;
      super.restoreState(facesContext, values[0]);
      validateOnUpdate = (Boolean) values[1];
      validateOnUpdateSet = (Boolean) values[2];
      validateOnRender = (Boolean) values[3];
      validateOnRenderSet = (Boolean) values[4];
      name = (String) values[5];
      type = (String) values[6];
      forId = (String) values[7];
      attachedTo = (String) values[8];
   }

   public void setAttachedTo(final String attachedTo) {
      this.attachedTo = attachedTo;
   }

}
