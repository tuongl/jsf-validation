package com.gendata.faces.validation.validators;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.annotations.Name;

import com.gendata.faces.validation.core.ValidationMessage;
import com.gendata.faces.validation.core.validator.FieldValidator;

@Name("emailRule")
public class EmailRule extends FieldValidator {

   private static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)"
                                               + "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

   @Override
   public void validate(final List<ValidationMessage> messages,
                        final UIComponent component,
                        final Object value) {

      if (value != null && (value instanceof String && !StringUtils.isEmpty((String) value))) {
         final String emailAddress = (String) value;
         if (!emailAddress.matches(EMAIL_PATTERN)) {
            messages.add(new ValidationMessage(FacesMessage.SEVERITY_ERROR, "validator.email"));
         }
      }
   }

}
