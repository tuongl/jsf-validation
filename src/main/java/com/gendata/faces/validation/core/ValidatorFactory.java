package com.gendata.faces.validation.core;

import org.jboss.seam.Component;

public class ValidatorFactory {

   private ValidatorFactory() {
      // empty
   }

   public static AbstractValidator getValidator(final String ruleName) {

      return (AbstractValidator) Component.getInstance(ruleName);
   }

}
