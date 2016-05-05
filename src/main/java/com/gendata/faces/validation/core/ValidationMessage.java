package com.gendata.faces.validation.core;

import javax.faces.application.FacesMessage;

public class ValidationMessage {

   private String componentId;

   private FacesMessage.Severity severity;

   private String messageKey;

   private Object[] messageArgs;

   public ValidationMessage(final FacesMessage.Severity severity, final String messageKey) {

      this.severity = severity;
      this.messageKey = messageKey;
   }

   public ValidationMessage(final FacesMessage.Severity severity, final String messageKey, final Object[] messageArgs) {

      this.severity = severity;
      this.messageKey = messageKey;
      this.messageArgs = messageArgs;
   }

   public FacesMessage.Severity getSeverity() {

      return severity;
   }

   public void setSeverity(final FacesMessage.Severity severity) {

      this.severity = severity;
   }

   public String getMessageKey() {

      return messageKey;
   }

   public void setMessageKey(final String messageKey) {

      this.messageKey = messageKey;
   }

   public Object[] getMessageArgs() {

      return messageArgs;
   }

   public void setMessageArgs(final Object[] messageArgs) {

      this.messageArgs = messageArgs;
   }

   public String getComponentId() {

      return componentId;
   }

   public void setComponentId(final String componentId) {

      this.componentId = componentId;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((messageKey == null) ? 0 : messageKey.hashCode());
      result = prime * result + ((severity == null) ? 0 : severity.hashCode());
      return result;
   }

   @Override
   public boolean equals(final Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (!(obj instanceof ValidationMessage)) {
         return false;
      }
      final ValidationMessage other = (ValidationMessage) obj;
      if (messageKey == null) {
         if (other.messageKey != null) {
            return false;
         }
      }
      else if (!messageKey.equals(other.messageKey)) {
         return false;
      }
      if (severity == null) {
         if (other.severity != null) {
            return false;
         }
      }
      else if (!severity.equals(other.severity)) {
         return false;
      }
      return true;
   }
}
