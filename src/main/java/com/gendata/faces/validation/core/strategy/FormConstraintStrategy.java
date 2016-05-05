package com.gendata.faces.validation.core.strategy;

import java.util.List;

import com.gendata.faces.validation.core.Rule;
import com.gendata.faces.validation.core.ValidationException;
import com.gendata.faces.validation.core.ValidationMessage;
import com.gendata.faces.validation.core.validator.BeanValidator;
import com.gendata.faces.validation.core.validator.FieldValidator;
import com.gendata.faces.validation.core.validator.MultiFieldValidator;

public class FormConstraintStrategy extends AbstractConstraintStrategy {

   public FormConstraintStrategy(final Rule rule) {

      this.rule = rule;
   }

   @Override
   public List<ValidationMessage> apply(final FieldValidator fieldValidator) {

      return null;
   }

   @Override
   public List<ValidationMessage> apply(final MultiFieldValidator multiFieldValidator) throws ValidationException {

      return super.apply(multiFieldValidator);
   }

   @Override
   public List<ValidationMessage> apply(final BeanValidator listValidator) {

      return super.apply(listValidator);
   }

}
