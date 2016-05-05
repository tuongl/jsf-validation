package com.gendata.faces.validation.validators;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.jboss.seam.annotations.Name;

import com.gendata.faces.validation.core.ValidationMessage;
import com.gendata.faces.validation.core.validator.MultiFieldValidator;

@Name("checkDateRange")
public class CheckDateRange extends MultiFieldValidator {

   @Override
   public int validate(final List<ValidationMessage> messages,
                       final Object[] values) {

      if (values == null || values.length < 2) {
         throw new IllegalArgumentException();
      }

      Date start = (Date) values[0];
      Date end = (Date) values[1];
      if (start != null && end != null && start.after(end)) {
         messages.add(new ValidationMessage(FacesMessage.SEVERITY_ERROR, "{0}", null));
      }

      return 2;
   }
}
