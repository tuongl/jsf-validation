package com.gendata.faces.validation.validators;

import java.util.List;

import javax.faces.application.FacesMessage;

import org.jboss.seam.annotations.Name;

import com.gendata.faces.validation.core.ValidationMessage;
import com.gendata.faces.validation.core.validator.MultiFieldValidator;

@Name("assertEquals")
public class AssertEqualsValidator extends MultiFieldValidator {

   @Override
   public int validate(final List<ValidationMessage> messages,
                       final Object[] values) {

      if (values == null || values.length < 2) {
         throw new IllegalArgumentException();
      }

      if (!values[0].equals(values[1])) {
         messages.add(new ValidationMessage(FacesMessage.SEVERITY_ERROR, "{0}", null));
      }

      return 2;
   }
}
