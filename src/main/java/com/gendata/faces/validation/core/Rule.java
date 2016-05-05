package com.gendata.faces.validation.core;

import java.util.List;

public class Rule {

   private String validationLevel;

   private String clientId;

   private String ruleName;

   private List<Object> ruleParameters;

   private String forId;

   private boolean validateOnUpdate;

   private boolean validateOnRender;

   public String getValidationLevel() {

      return validationLevel;
   }

   public void setValidationLevel(final String validationLevel) {

      this.validationLevel = validationLevel;
   }

   public String getClientId() {

      return clientId;
   }

   public void setClientId(final String clientId) {

      this.clientId = clientId;
   }

   public String getRuleName() {

      return ruleName;
   }

   public void setRuleName(final String ruleName) {

      this.ruleName = ruleName;
   }

   public List<Object> getRuleParameters() {

      return ruleParameters;
   }

   public void setRuleParameters(final List<Object> ruleParameters) {

      this.ruleParameters = ruleParameters;
   }

   public String getForId() {

      return forId;
   }

   public void setForId(final String forId) {

      this.forId = forId;
   }

   public boolean isValidateOnUpdate() {

      return validateOnUpdate;
   }

   public void setValidateOnUpdate(final boolean validateOnUpdate) {

      this.validateOnUpdate = validateOnUpdate;
   }

   public boolean isValidateOnRender() {
      return validateOnRender;
   }

   public void setValidateOnRender(final boolean validateOnRender) {
      this.validateOnRender = validateOnRender;
   }

}
