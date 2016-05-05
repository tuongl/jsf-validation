package com.gendata.faces.validation.core;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.gendata.faces.validation.core.level.ProcessValidationPhaseProcessor;
import com.gendata.faces.validation.core.level.RenderResponsePhaseProcessor;
import com.gendata.faces.validation.core.level.UpdateModelValuesPhaseProcessor;
import com.gendata.faces.validation.core.level.ValidationLevelProcessor;

public class ValidationPhaseListener implements PhaseListener {

   private static final long serialVersionUID = 1L;

   @Override
   public void beforePhase(final PhaseEvent phaseEvent) {

      final FacesContext context = phaseEvent.getFacesContext();

      try {
         if (phaseEvent.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {

            final ValidationLevelProcessor processor = RenderResponsePhaseProcessor.instance();
            processor.handleRequest(context, phaseEvent);
         }
      }
      catch (final ValidationException e) {
         throw new RuntimeException(e);
      }
      catch (final FacesException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public void afterPhase(final PhaseEvent phaseEvent) {

      final FacesContext context = phaseEvent.getFacesContext();

      try {
         if (phaseEvent.getPhaseId().equals(PhaseId.PROCESS_VALIDATIONS)) {

            final ValidationLevelProcessor processor = ProcessValidationPhaseProcessor.instance();
            processor.handleRequest(context, phaseEvent);
         }

         else if (phaseEvent.getPhaseId().equals(PhaseId.UPDATE_MODEL_VALUES)
                  && (context.getMaximumSeverity() == null || FacesContext.getCurrentInstance().getMaximumSeverity().compareTo(FacesMessage.SEVERITY_ERROR) < 0)) {

            final ValidationLevelProcessor processor = UpdateModelValuesPhaseProcessor.instance();
            processor.handleRequest(context, phaseEvent);
         }

         if (context.getMaximumSeverity() != null && context.getMaximumSeverity().compareTo(FacesMessage.SEVERITY_WARN) >= 0) {

            context.renderResponse();
         }
      }
      catch (final ValidationException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public PhaseId getPhaseId() {

      return PhaseId.ANY_PHASE;
   }

}
