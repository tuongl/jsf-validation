package com.gendata.faces.validation.core.validator;

import java.util.List;

import com.gendata.faces.validation.core.AbstractValidator;
import com.gendata.faces.validation.core.ValidationMessage;
import com.gendata.faces.validation.core.strategy.AbstractConstraintStrategy;

public abstract class BeanValidator implements AbstractValidator {

   public abstract void validate(List<ValidationMessage> messages,
                                 Object bean);

   @Override
   public List<ValidationMessage> accept(final AbstractConstraintStrategy constraintStrategy) {

      return constraintStrategy.apply(this);
   }

}
