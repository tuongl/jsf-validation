package com.gendata.faces.validation.core.validator;

import java.util.List;

import javax.faces.component.UIComponent;

import com.gendata.faces.validation.core.AbstractValidator;
import com.gendata.faces.validation.core.ValidationMessage;
import com.gendata.faces.validation.core.strategy.AbstractConstraintStrategy;

public abstract class FieldValidator implements AbstractValidator {

   public abstract void validate(List<ValidationMessage> messages,
                                 UIComponent component,
                                 Object value);

   @Override
   public List<ValidationMessage> accept(final AbstractConstraintStrategy constraintStrategy) {

      return constraintStrategy.apply(this);
   }
}
