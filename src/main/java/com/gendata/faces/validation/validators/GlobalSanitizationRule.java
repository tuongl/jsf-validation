package com.gendata.faces.validation.validators;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.annotations.Name;

import com.gendata.faces.validation.core.ValidationMessage;
import com.gendata.faces.validation.core.validator.FieldValidator;

@Name("globalSanitizationRule")
public class GlobalSanitizationRule extends FieldValidator {

   private static final String SANITIZE_PATTERN = "[^<>\"\'%()&+\\\\]+";

   @Override
   public void validate(final List<ValidationMessage> messages,
                        final UIComponent component,
                        final Object value) {

      if (value != null && (value instanceof String && !StringUtils.isEmpty((String) value))) {
         final String stringValue = (String) value;
         if (!stringValue.matches(SANITIZE_PATTERN)) {
            messages.add(new ValidationMessage(FacesMessage.SEVERITY_ERROR, "validator.sanitization"));
            return;
         }
      }
   }

}
