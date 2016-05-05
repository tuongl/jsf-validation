package com.gendata.faces.validation.core;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import com.sun.facelets.FaceletViewHandler;

public class VisitorViewHandler extends FaceletViewHandler {

   public VisitorViewHandler(final ViewHandler viewHandler) {
      super(viewHandler);
   }

   @Override
   public void buildView(final FacesContext context,
                         final UIViewRoot viewToRender) throws IOException, FacesException {
      super.buildView(context, viewToRender);
      visitView(context, viewToRender);
   }

   private void visitView(final FacesContext context,
                          final UIViewRoot viewToRender) {

      final List<ComponentVisitor> visitors = ComponentVisitorList.instance();
      visitComponents(context, viewToRender, visitors);
   }

   private void visitComponents(final FacesContext context,
                                final UIComponent component,
                                final List<ComponentVisitor> visitors) {

      for (final ComponentVisitor visitor : visitors) {
         visitor.visitComponent(context, component);
      }

      if (component instanceof UIData) {
         final UIData uiData = (UIData) component;

         uiData.setRowIndex(-1);

         final int first = uiData.getFirst();
         final int rows = uiData.getRows();
         int last;
         if (rows == 0) {
            last = uiData.getRowCount();
         }
         else {
            last = first + rows;
         }
         for (int rowIndex = first; last == -1 || rowIndex < last; rowIndex++) {
            uiData.setRowIndex(rowIndex);

            if (!uiData.isRowAvailable()) {
               break;
            }

            final Iterator<UIComponent> childfacets = component.getFacetsAndChildren();
            while (childfacets.hasNext()) {
               final UIComponent child = childfacets.next();
               visitComponents(context, child, visitors);
            }
         }

         uiData.setRowIndex(-1);
      }
      else {
         final Iterator<UIComponent> children = component.getFacetsAndChildren();
         while (children.hasNext()) {
            final UIComponent child = children.next();
            visitComponents(context, child, visitors);
         }
      }
   }

   @Override
   public void initialize(final FacesContext context) {
      super.initialize(context);
   }

}
