package com.gendata.faces.validation.core.strategy;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIMessage;
import javax.faces.context.FacesContext;

import com.gendata.faces.validation.core.Rule;
import com.gendata.faces.validation.core.ValidationMessage;
import com.gendata.faces.validation.core.validator.BeanValidator;
import com.gendata.faces.validation.core.validator.FieldValidator;
import com.gendata.faces.validation.core.validator.MultiFieldValidator;

public class GlobalConstraintStrategy extends AbstractConstraintStrategy {

   public GlobalConstraintStrategy(final Rule rule, final UIComponent component) {

      this.rule = rule;
      this.component = component;
   }

   @Override
   public List<ValidationMessage> apply(final FieldValidator fieldValidator) {

      final FacesContext context = FacesContext.getCurrentInstance();

      final List<ValidationMessage> messages = super.apply(fieldValidator);

      if (messages.size() > 0) {
         final UIMessage uiMessage = getUIMessageForComponent(context, component);
         if (uiMessage == null) {
            addUIMessageForComponent(context, component);
         }
      }

      return messages;
   }

   @Override
   public List<ValidationMessage> apply(final MultiFieldValidator multiFieldValidator) {

      return null;
   }

   @Override
   public List<ValidationMessage> apply(final BeanValidator listValidator) {

      return null;
   }

}
