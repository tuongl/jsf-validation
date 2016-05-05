package com.gendata.faces.validation.core.level;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.Name;

@Name("updateModelValuesPhaseProcessor")
public class UpdateModelValuesPhaseProcessor extends ValidationLevelProcessor {

   public static ValidationLevelProcessor instance() {
      return (ValidationLevelProcessor) Component.getInstance(UpdateModelValuesPhaseProcessor.class);
   }
}
