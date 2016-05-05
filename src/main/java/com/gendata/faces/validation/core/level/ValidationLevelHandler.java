package com.gendata.faces.validation.core.level;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;

import org.apache.myfaces.shared_tomahawk.util.MessageUtils;

import com.gendata.faces.validation.core.ComponentRuleVisitor;
import com.gendata.faces.validation.core.RuleMap;
import com.gendata.faces.validation.core.ValidationException;
import com.gendata.faces.validation.core.ValidationMessage;

public class ValidationLevelHandler implements AbstractValidationLevelHandler {

   private String validationLevel;

   public ValidationLevelHandler(final String validationLevel) {
      this.validationLevel = validationLevel;
   }

   @Override
   public boolean handleRequest(final FacesContext context,
                                final PhaseEvent phaseEvent) throws ValidationException {

      final ComponentRuleVisitor visitor = ComponentRuleVisitor.instance();
      final RuleMap ruleMap = visitor.getRuleMap();
      final List<ValidationMessage> validationMessages = ruleMap.process(context, phaseEvent.getPhaseId(), validationLevel);

      for (final ValidationMessage message : validationMessages) {
         MessageUtils.addMessage(message.getSeverity(), message.getMessageKey(), message.getMessageArgs(), message.getComponentId());
      }

      if (context.getMaximumSeverity() == null || context.getMaximumSeverity().compareTo(FacesMessage.SEVERITY_ERROR) < 0) {
         return true;
      }

      return false;
   }

}
