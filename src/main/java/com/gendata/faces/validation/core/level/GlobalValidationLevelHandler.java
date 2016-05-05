package com.gendata.faces.validation.core.level;

import org.jboss.seam.annotations.Name;

@Name("globalValidationLevelHandler")
public class GlobalValidationLevelHandler extends ValidationLevelHandler {

   public GlobalValidationLevelHandler() {
      super("globalLevel");
   }

}
