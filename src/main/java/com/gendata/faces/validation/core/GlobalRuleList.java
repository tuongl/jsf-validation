package com.gendata.faces.validation.core;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Unwrap;

@Name("globalRuleList")
public class GlobalRuleList {

   private List<String> ruleList = new ArrayList<String>();

   public static List<String> instance() {
      return (List<String>) Component.getInstance(GlobalRuleList.class);
   }

   @Unwrap
   public List<String> getRuleList() {
      return this.ruleList;
   }
}
