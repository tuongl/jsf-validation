package com.gendata.faces.validation.validators;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;

import org.jboss.seam.annotations.Name;

import com.gendata.faces.validation.core.ValidationMessage;
import com.gendata.faces.validation.core.validator.FieldValidator;

@Name("usernameRule")
public class UsernameRule extends FieldValidator {

   private static final String USERNAME_PATTERN = "^[A-Za-z0-9~!@#$^*]{7,}$";

   @Override
   public void validate(final List<ValidationMessage> messages,
                        final UIComponent component,
                        final Object value) {

      if (value == null) {
         throw new IllegalArgumentException();
      }

      final String username = (String) value;
      if (username.length() < 7) {
         messages.add(new ValidationMessage(FacesMessage.SEVERITY_ERROR, "validator.usernameFieldLength", null));
         return;
      }

      if (!username.matches(USERNAME_PATTERN)) {
         messages.add(new ValidationMessage(FacesMessage.SEVERITY_ERROR, "validator.usernameAllowedCharacters", null));
         return;
      }
   }

}
