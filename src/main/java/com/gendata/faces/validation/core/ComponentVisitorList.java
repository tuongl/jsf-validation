package com.gendata.faces.validation.core;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Unwrap;

@Name("componentVisitorList")
@Scope(ScopeType.PAGE)
public class ComponentVisitorList {

   private final List<ComponentVisitor> componentVisitors = new ArrayList<ComponentVisitor>();

   public static List<ComponentVisitor> instance() {
      return (List<ComponentVisitor>) Component.getInstance(ComponentVisitorList.class);
   }

   public void setComponentVisitors(final List<ComponentVisitor> componentVisitors) {
      this.componentVisitors.clear();
      if (componentVisitors != null) {
         this.componentVisitors.addAll(componentVisitors);
      }
   }

   @Unwrap
   public List<ComponentVisitor> getComponentVisitors() {
      return this.componentVisitors;
   }

}
