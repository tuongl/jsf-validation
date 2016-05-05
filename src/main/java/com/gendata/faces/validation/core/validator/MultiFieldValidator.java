package com.gendata.faces.validation.core.validator;

import java.util.List;

import com.gendata.faces.validation.core.AbstractValidator;
import com.gendata.faces.validation.core.ValidationException;
import com.gendata.faces.validation.core.ValidationMessage;
import com.gendata.faces.validation.core.strategy.AbstractConstraintStrategy;

public abstract class MultiFieldValidator implements AbstractValidator {

   public abstract int validate(List<ValidationMessage> messages,
                                Object[] values) throws ValidationException;

   @Override
   public List<ValidationMessage> accept(final AbstractConstraintStrategy constraintStrategy) throws ValidationException {

      return constraintStrategy.apply(this);
   }
}
