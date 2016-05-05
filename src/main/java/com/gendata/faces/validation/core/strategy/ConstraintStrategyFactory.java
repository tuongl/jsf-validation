package com.gendata.faces.validation.core.strategy;

import javax.faces.component.UIComponent;

import com.gendata.faces.validation.core.Rule;

public class ConstraintStrategyFactory {

   private ConstraintStrategyFactory() {

   }

   public static AbstractConstraintStrategy getConstraintStrategy(final Rule rule,
                                                                  final UIComponent component) {

      AbstractConstraintStrategy strategy = null;

      if ("globalLevel".equals(rule.getValidationLevel())) {
         strategy = new GlobalConstraintStrategy(rule, component);
      }
      else if ("fieldLevel".equals(rule.getValidationLevel())) {
         strategy = new FieldConstraintStrategy(rule, component);
      }
      else if ("formLevel".equals(rule.getValidationLevel())) {
         strategy = new FormConstraintStrategy(rule);
      }

      return strategy;
   }
}
