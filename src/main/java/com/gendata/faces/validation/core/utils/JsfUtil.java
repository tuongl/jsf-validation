package com.gendata.faces.validation.core.utils;

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;

import org.richfaces.component.html.HtmlModalPanel;

public class JsfUtil {

   private JsfUtil() {

      // Empty
   }

   public static UIComponent findComponent(final FacesContext context,
                                           final String clientId) {

      return findComponent(context, context.getViewRoot(), clientId);
   }

   public static UIComponent findComponent(final FacesContext context,
                                           final UIComponent component,
                                           final String clientId) {

      if (component.getClientId(context).equals(clientId)) {
         return component;
      }

      // FIXME The following chunk of code is ugly and hard to maintain. It is
      // there due to the component having an
      // immediate UIData parent as a naming container. In order to get the
      // right index for the component's client
      // id, the UIData needs to be parsed again to get the correct row index.
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

            for (final Iterator<UIComponent> it = component.getFacetsAndChildren(); it.hasNext();) {
               final UIComponent result = JsfUtil.findComponent(context, it.next(), clientId);
               if (result != null) {
                  return result;
               }
            }
         }

         uiData.setRowIndex(-1);
      }
      else {
         for (final Iterator<UIComponent> it = component.getFacetsAndChildren(); it.hasNext();) {
            final UIComponent result = JsfUtil.findComponent(context, it.next(), clientId);

            if (result != null) {
               return result;
            }
         }
      }

      return null;
   }

   public static boolean isRendered(final UIComponent component) {

      if (!component.isRendered() || (component instanceof HtmlModalPanel && !((HtmlModalPanel) component).isShowWhenRendered())) {
         return false;
      }

      final UIComponent parent = component.getParent();

      if ((parent != null) && !isRendered(parent)) {
         return false;
      }

      return true;
   }

}
