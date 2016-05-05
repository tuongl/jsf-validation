package com.gendata.faces.validation.validators;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectOne;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.fileupload.HtmlInputFileUpload;
import org.jboss.seam.annotations.Name;

import com.gendata.faces.validation.core.ValidationMessage;
import com.gendata.faces.validation.core.validator.FieldValidator;

@Name("mandatoryRule")
public class MandatoryRule extends FieldValidator {

   @Override
   public void validate(final List<ValidationMessage> messages,
                        final UIComponent component,
                        final Object value) {

      if ((component instanceof UISelectOne || component instanceof HtmlInputFileUpload) && value == null) {
         messages.add(new ValidationMessage(FacesMessage.SEVERITY_ERROR, "validator.requiredSelect"));
         return;
      }
      else if (component instanceof UIInput && (value == null || (value instanceof String && StringUtils.isBlank((String) value)))) {
         messages.add(new ValidationMessage(FacesMessage.SEVERITY_ERROR, "validator.requiredInput"));
         return;
      }

      // this is meant to cover checks for values in HtmlSelectManyCheckbox
      // etc.
      else if (component instanceof UIInput
               && (value == null || (value instanceof ArrayList<?> && ((ArrayList<?>) value).size() == 0))) {
         messages.add(new ValidationMessage(FacesMessage.SEVERITY_ERROR, "validator.requiredSelect"));
         return;
      }
   }
}
