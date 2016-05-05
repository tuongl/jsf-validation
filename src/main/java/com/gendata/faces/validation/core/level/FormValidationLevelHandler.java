package com.gendata.faces.validation.core.level;

import org.jboss.seam.annotations.Name;

@Name("formValidationLevelHandler")
public class FormValidationLevelHandler extends ValidationLevelHandler {

   public FormValidationLevelHandler() {
      super("formLevel");
   }

}
