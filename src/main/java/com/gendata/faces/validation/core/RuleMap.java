package com.gendata.faces.validation.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.ajax4jsf.component.UIAjaxSupport;

import com.gendata.faces.validation.core.strategy.AbstractConstraintStrategy;
import com.gendata.faces.validation.core.strategy.ConstraintStrategyFactory;
import com.gendata.faces.validation.core.utils.JsfUtil;
import com.gendata.faces.validation.core.validator.BeanValidator;

public class RuleMap {

   private List<Rule> rules = new ArrayList<Rule>();

   public List<ValidationMessage> process(final FacesContext context,
                                          final PhaseId phaseId,
                                          final String validationLevel) throws ValidationException {

      final List<ValidationMessage> messages = new ArrayList<ValidationMessage>();

      String currentId = null;
      for (final Rule rule : rules) {

         if (!validationLevel.equals(rule.getValidationLevel())) {
            continue;
         }

         if (rule.isValidateOnRender() && PhaseId.RENDER_RESPONSE != phaseId || !rule.isValidateOnRender()
             && PhaseId.RENDER_RESPONSE == phaseId) {
            continue;
         }

         final String clientId = rule.getClientId();
         if (clientId.equals(currentId) && messages.size() > 0) {
            continue;
         }

         final UIComponent component = JsfUtil.findComponent(context, clientId);
         if (component == null) {
            throw new ValidationException("Rule " + rule.getRuleName() + " specified with unknown component id " + clientId);
         }

         if (JsfUtil.isRendered(component)) {
            List<ValidationMessage> list = null;

            if (rule.isValidateOnRender() && PhaseId.RENDER_RESPONSE == phaseId) {
               list = processRule(phaseId, rule, component);

               if (list != null && list.size() > 0) {
                  messages.addAll(list);
                  currentId = clientId;
               }
               continue;
            }

            if (rule.isValidateOnUpdate() && PhaseId.PROCESS_VALIDATIONS == phaseId || !rule.isValidateOnUpdate()
                && PhaseId.UPDATE_MODEL_VALUES == phaseId) {
               continue;
            }

            if (component instanceof HtmlSelectOneMenu && ((HtmlSelectOneMenu) component).isDisabled()) {
               continue;
            }
            if (component instanceof HtmlSelectBooleanCheckbox && ((HtmlSelectBooleanCheckbox) component).isDisabled()) {
               continue;
            }
            if (component instanceof HtmlSelectOneRadio && ((HtmlSelectOneRadio) component).isDisabled()) {
               continue;
            }
            if (component instanceof HtmlInputText && ((HtmlInputText) component).isDisabled()) {
               continue;
            }
            if (component instanceof HtmlInputTextarea && ((HtmlInputTextarea) component).isDisabled()) {
               continue;
            }

            if (context.getExternalContext().getRequestParameterMap().containsKey("ajaxSingle")) {
               if (component.getClientId(context).equals(context.getExternalContext().getRequestParameterMap().get("ajaxSingle"))) {
                  list = processRule(phaseId, rule, component);

                  // Process list of component id's specified by 'process'
                  // property
                  final Set<?> componentIds = context.getExternalContext().getRequestParameterMap().keySet();
                  for (final Object name : componentIds) {
                     final UIComponent uiComponent = JsfUtil.findComponent(context, (String) name);
                     if (uiComponent != null && uiComponent.getParent() != null
                         && component.getClientId(context).equals(uiComponent.getParent().getClientId(context))
                         && uiComponent instanceof UIAjaxSupport) {
                        final Object process = ((UIAjaxSupport) uiComponent).getProcess();
                        if (process != null) {
                           final String[] processClientIds = ((String) process).split(",");
                           for (final String processClientId : processClientIds) {
                              final UIComponent processComponent = JsfUtil.findComponent(context, processClientId.trim());
                              if (processComponent != null) {
                                 list.addAll(processRule(phaseId, rule, processComponent));
                              }
                           }
                        }
                        break;
                     }
                  }
               }
            }
            else {
               String requestClientId = context.getExternalContext().getRequestParameterMap().get("AJAXREQUEST");
               if (requestClientId == null) {
                  requestClientId = context.getViewRoot().getClientId(context);
               }

               UIComponent parent = component.getParent();
               while (parent != null) {
                  if (parent.getClientId(context).equals(requestClientId)) {
                     list = processRule(phaseId, rule, component);
                     break;
                  }
                  parent = parent.getParent();
               }
            }

            if (list != null && list.size() > 0) {
               messages.addAll(list);
               currentId = clientId;
            }
         }
      }

      return messages;
   }

   private List<ValidationMessage> processRule(final PhaseId phaseId,
                                               final Rule rule,
                                               final UIComponent component) throws ValidationException {

      if (rule.getForId() != null) {
         final String forId = rule.getForId();
         final String[] forClientIds = forId.split(",");
         boolean found = false;
         for (final String forClientId : forClientIds) {
            final Set<?> componentIds = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet();
            for (final Object name : componentIds) {
               final UIComponent forComponent = JsfUtil.findComponent(FacesContext.getCurrentInstance(), (String) name);
               if (forComponent instanceof UICommand
                   && forComponent.getClientId(FacesContext.getCurrentInstance()).equals(forClientId.trim())) {
                  found = true;
                  break;
               }
            }
         }
         if (!found) {
            return new ArrayList<ValidationMessage>();
         }
      }

      final AbstractConstraintStrategy strategy = ConstraintStrategyFactory.getConstraintStrategy(rule, component);
      if (strategy == null) {
         throw new ValidationException("Bad bad bad");
      }

      final AbstractValidator validator = ValidatorFactory.getValidator(rule.getRuleName());
      if (validator == null) {
         throw new ValidationException("Unknown validation rule " + rule.getRuleName());
      }

      if (validator instanceof BeanValidator && PhaseId.PROCESS_VALIDATIONS == phaseId) {
         return new ArrayList<ValidationMessage>();
      }

      return validator.accept(strategy);
   }

   public List<Rule> getRules() {
      return rules;
   }

   public void setRules(final List<Rule> rules) {
      this.rules = rules;
   }
}
