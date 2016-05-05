package com.gendata.faces.validation.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIParameter;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("componentRuleVisitor")
@Scope(ScopeType.PAGE)
public class ComponentRuleVisitor implements ComponentVisitor {

   private RuleMap ruleMap = new RuleMap();

   public static ComponentRuleVisitor instance() {
      return (ComponentRuleVisitor) Component.getInstance(ComponentRuleVisitor.class);
   }

   @Override
   public void visitComponent(final FacesContext context,
                              final UIComponent component) {

      if (component instanceof UIViewRoot) {
         ruleMap.setRules(new ArrayList<Rule>());
      }
      else {
         final Iterator<UIComponent> childfacets = component.getFacetsAndChildren();
         while (childfacets.hasNext()) {
            final UIComponent child = childfacets.next();
            if (child instanceof UIValidator) {
               final UIValidator validator = (UIValidator) child;
               final Rule rule = new Rule();
               rule.setValidationLevel(validator.getType());

               if (StringUtils.isNotEmpty(validator.getAttachedTo())) {
                  rule.setClientId(validator.getAttachedTo());
               }
               else {
                  rule.setClientId(component.getClientId(context));
               }

               rule.setRuleName(validator.getName());
               rule.setValidateOnUpdate(validator.isValidateOnUpdate());
               rule.setValidateOnRender(validator.isValidateOnRender());
               rule.setForId(validator.getForId());

               rule.setRuleParameters(new ArrayList<Object>());
               for (final UIComponent param : validator.getChildren()) {
                  if (param instanceof UIParameter) {
                     rule.getRuleParameters().add(((UIParameter) param).getValue());
                  }
               }
               ruleMap.getRules().add(rule);
            }
         }

         // Global rules
         if (component instanceof UIInput) {
            ruleMap.getRules().addAll(getGlobalRules(component.getClientId(context)));
         }
      }
   }

   private List<Rule> getGlobalRules(final String clientId) {

      List<Rule> rules = new ArrayList<Rule>();
      for (String ruleName : GlobalRuleList.instance()) {
         final Rule rule = new Rule();
         rule.setValidationLevel("globalLevel");
         rule.setClientId(clientId);
         rule.setRuleName(ruleName);
         rule.setRuleParameters(new ArrayList<Object>());
         rules.add(rule);
      }
      return rules;
   }

   public RuleMap getRuleMap() {
      return ruleMap;
   }

   public void setRuleMap(RuleMap ruleMap) {
      this.ruleMap = ruleMap;
   }
}
