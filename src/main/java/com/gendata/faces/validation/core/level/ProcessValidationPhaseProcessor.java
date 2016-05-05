package com.gendata.faces.validation.core.level;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.Name;

@Name("processValidationPhaseProcessor")
public class ProcessValidationPhaseProcessor extends ValidationLevelProcessor {

   public static ValidationLevelProcessor instance() {
      return (ValidationLevelProcessor) Component.getInstance(ProcessValidationPhaseProcessor.class);
   }
}
