package com.gendata.faces.validation.core.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessage;
import javax.faces.component.html.HtmlMessage;
import javax.faces.context.FacesContext;

import com.gendata.faces.validation.core.Rule;
import com.gendata.faces.validation.core.ValidationException;
import com.gendata.faces.validation.core.ValidationMessage;
import com.gendata.faces.validation.core.utils.JsfUtil;
import com.gendata.faces.validation.core.validator.BeanValidator;
import com.gendata.faces.validation.core.validator.FieldValidator;
import com.gendata.faces.validation.core.validator.MultiFieldValidator;

public abstract class AbstractConstraintStrategy {

   protected Rule rule;

   protected UIComponent component;

   public List<ValidationMessage> apply(final FieldValidator fieldValidator) {

      final List<ValidationMessage> messages = new ArrayList<ValidationMessage>();

      final FacesContext context = FacesContext.getCurrentInstance();

      final Object value = component.getAttributes().get("value");

      fieldValidator.validate(messages, component, value);

      final List<Object> ruleParameters = rule.getRuleParameters();
      for (final ValidationMessage message : messages) {
         message.setComponentId(rule.getClientId());

         if (ruleParameters.size() > 0 && ruleParameters.get(0) != null) {
            final Object[] args = new Object[ruleParameters.size()];
            message.setMessageArgs(ruleParameters.toArray(args));
         }
         else {
            message.setMessageArgs(new Object[] { getLabel(context, component) });
         }
      }

      return messages;
   }

   public List<ValidationMessage> apply(final MultiFieldValidator multiFieldValidator) throws ValidationException {

      final List<ValidationMessage> messages = new ArrayList<ValidationMessage>();

      final FacesContext context = FacesContext.getCurrentInstance();
      final Object[] params = new Object[rule.getRuleParameters().size()];
      int count = 0;

      boolean valid = true;

      for (final Object param : rule.getRuleParameters()) {
         if (param instanceof String) {
            final UIComponent object = JsfUtil.findComponent(context, (String) param);

            if (object != null) {
               if (!JsfUtil.isRendered(object)) {
                  valid = false;
                  break;
               }

               params[count++] = object.getAttributes().get("value");
            }
            else {
               final ValueExpression ve = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(),
                                                                                                                (String) param,
                                                                                                                Object.class);
               if (ve != null) {
                  params[count++] = ve.getValue(context.getELContext());
               }
               else {
                  params[count++] = param;
               }
            }
         }
         else {
            params[count++] = param;
         }
      }

      if (!valid) {
         return messages;
      }

      final int noArgs = multiFieldValidator.validate(messages, params);

      for (final ValidationMessage message : messages) {
         message.setComponentId(rule.getClientId());

         if (noArgs > 0) {
            if (rule.getRuleParameters().size() > noArgs) {
               final Object[] args = new Object[rule.getRuleParameters().size() - noArgs];
               for (count = 0; count < rule.getRuleParameters().size() - noArgs; count++) {
                  args[count] = rule.getRuleParameters().get(count + noArgs);
               }
               message.setMessageArgs(args);
            }
         }
      }

      return messages;
   }

   public List<ValidationMessage> apply(final BeanValidator beanValidator) {

      final List<ValidationMessage> messages = new ArrayList<ValidationMessage>();

      final FacesContext context = FacesContext.getCurrentInstance();
      final Object[] params = new Object[rule.getRuleParameters().size()];
      int count = 0;

      for (final Object param : rule.getRuleParameters()) {
         if (param instanceof String) {
            final ValueExpression ve = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(),
                                                                                                             (String) param,
                                                                                                             Object.class);
            if (ve != null) {
               params[count++] = ve.getValue(context.getELContext());
            }
            else {
               params[count++] = param;
            }
         }
         else {
            params[count++] = param;
         }
      }

      beanValidator.validate(messages, params[0]);

      for (final ValidationMessage message : messages) {
         message.setComponentId(rule.getClientId());

         if (rule.getRuleParameters().size() > 1) {
            final Object[] args = new Object[rule.getRuleParameters().size() - 1];
            for (count = 0; count < rule.getRuleParameters().size() - 1; count++) {
               args[count] = rule.getRuleParameters().get(count + 1);
            }
            message.setMessageArgs(args);
         }
      }

      return messages;
   }

   protected UIMessage getUIMessageForComponent(final FacesContext context,
                                                final UIComponent component) {

      return getUIMessageForComponent(context, context.getViewRoot(), component.getId());
   }

   protected UIMessage getUIMessageForComponent(final FacesContext context,
                                                final UIComponent component,
                                                final String id) {

      if (component instanceof UIMessage) {
         final ValueExpression valueExpression = component.getValueExpression("for");

         String forValue = null;
         if (valueExpression != null) {
            forValue = (String) valueExpression.getValue(context.getELContext());
         }
         else {
            forValue = ((UIMessage) component).getFor();
         }

         if (forValue != null && forValue.equals(id)) {
            return (UIMessage) component;
         }
      }

      final Iterator<UIComponent> iter = component.getFacetsAndChildren();

      while (iter.hasNext()) {
         final UIMessage uiMessage = getUIMessageForComponent(context, iter.next(), id);
         if (uiMessage != null) {
            return uiMessage;
         }
      }

      return null;
   }

   protected void addUIMessageForComponent(final FacesContext context,
                                           final UIComponent component) {

      final UIMessage uiMessage = new HtmlMessage();
      uiMessage.setId(context.getViewRoot().createUniqueId());
      uiMessage.setFor(component.getId());
      // uiMessage.setParent(component);
      ((HtmlMessage) uiMessage).setErrorClass("inline-error");
      // component.getChildren().add(uiMessage);
   }

   protected String getLabel(final FacesContext context,
                             final UIComponent component) {

      final UIComponent parent = component.getParent();
      if (parent == null) {
         return null;
      }

      final Iterator<UIComponent> iter = parent.getFacetsAndChildren();

      while (iter.hasNext()) {
         final UIComponent sibling = iter.next();
         ValueExpression valueExpression = sibling.getValueExpression("for");

         if (valueExpression != null) {
            final String forValue = (String) valueExpression.getValue(context.getELContext());

            if (forValue != null && forValue.equals(component.getId())) {
               valueExpression = sibling.getValueExpression("value");

               if (valueExpression != null) {
                  return (String) valueExpression.getValue(context.getELContext());
               }
            }
         }
      }

      return component.getId();
   }

}
