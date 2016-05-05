package com.gendata.faces.validation.validators;

import java.util.List;

import javax.faces.application.FacesMessage;

import org.jboss.seam.annotations.Name;

import com.gendata.faces.validation.core.ValidationMessage;
import com.gendata.faces.validation.core.validator.MultiFieldValidator;

@Name("assertTrue")
public class AssertTrueValidator extends MultiFieldValidator {

   @Override
   public int validate(final List<ValidationMessage> messages,
                       final Object[] values) {

      if (values == null || values.length < 1) {
         throw new IllegalArgumentException();
      }

      if (values[0] instanceof Boolean && !Boolean.valueOf((Boolean) values[0])) {
         messages.add(new ValidationMessage(FacesMessage.SEVERITY_ERROR, "{0}", null));
      }

      return 1;
   }
}
