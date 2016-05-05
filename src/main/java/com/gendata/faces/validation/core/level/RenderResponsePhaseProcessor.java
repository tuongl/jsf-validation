package com.gendata.faces.validation.core.level;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.Name;

@Name("renderResponsePhaseProcessor")
public class RenderResponsePhaseProcessor extends ValidationLevelProcessor {

   public static ValidationLevelProcessor instance() {
      return (ValidationLevelProcessor) Component.getInstance(RenderResponsePhaseProcessor.class);
   }
}
