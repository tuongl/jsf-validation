package com.gendata.faces.validation.core.level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;

import com.gendata.faces.validation.core.ValidationException;
import com.gendata.faces.validation.core.ViewHandlerFactory;
import com.gendata.faces.validation.core.VisitorViewHandler;

public class ValidationLevelProcessor implements AbstractValidationLevelHandler {

   private final List<ValidationLevelHandler> handlers = new ArrayList<ValidationLevelHandler>();

   public List<ValidationLevelHandler> getHandlers() {
      return this.handlers;
   }

   @Override
   public boolean handleRequest(final FacesContext context,
                                final PhaseEvent phaseEvent) throws ValidationException {

      final VisitorViewHandler viewHandler = (VisitorViewHandler) ViewHandlerFactory.getHandler(context.getApplication().getViewHandler());
      try {
         viewHandler.buildView(context, context.getViewRoot());
      }
      catch (FacesException e) {
         throw new ValidationException(e);
      }
      catch (IOException e) {
         throw new ValidationException(e);
      }

      for (final AbstractValidationLevelHandler handler : handlers) {
         if (!handler.handleRequest(context, phaseEvent)) {
            return false;
         }
      }

      return true;
   }
}
