package com.test.jsfbootstrap.render;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.*;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 *
 *
 */
public class    BootStrapLinkRenderer extends CommandLinkRenderer implements Serializable {

    @Override
    protected void renderAsActive(FacesContext context, UIComponent command)
            throws IOException {


        System.out.println("-------" + command.toString());

        Object dataToggle = command.getAttributes().get("data-toggle");
        Object dataTarget = command.getAttributes().get("data-target");





        ResponseWriter writer = context.getResponseWriter();
        assert(writer != null);
        String formClientId = RenderKitUtils.getFormClientId(command, context);
        if (formClientId == null) {
            return;
        }

        //make link act as if it's a button using javascript
        writer.startElement("a", command);
        writeIdAttributeIfNecessary(context, writer, command);
        writer.writeAttribute("href", "#", "href");

        if (dataTarget != null) {
            context.getResponseWriter().writeAttribute("data-target", dataTarget, null);
        }

        if (dataToggle != null) {
            context.getResponseWriter().writeAttribute("data-toggle", dataToggle, null);
        }

        RenderKitUtils.renderPassThruAttributes(context,
                writer,
                command,
                AttributeManager.getAttributes(AttributeManager.Key.COMMANDLINK),
                getPassThruBehaviors(command, "click", "action"));

        RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, command);

        String target = (String) command.getAttributes().get("target");
        if (target != null) {
            target = target.trim();
        } else {
            target = "";
        }

        Collection<ClientBehaviorContext.Parameter> params = getBehaviorParameters(command);
        RenderKitUtils.renderOnclick(context,
                command,
                params,
                target,
                true);

        writeCommonLinkAttributes(writer, command);

        // render the current value as link text.
        writeValue(command, writer);
        writer.flush();

    }

//    @Override
//    protected void renderAsActive(FacesContext context, UIComponent command) throws IOException {
//
//
//        //super.renderAsActive(context, command);
//    }
//    @Override
//    public void encodeBegin(FacesContext context, UIComponent component)
//            throws IOException {
//
//        super.encodeBegin(context, component);
//
//
//
//
//
//    }
//    @Override
//    protected void getEndTextToRender(FacesContext context, UIComponent component, String currentValue) throws IOException {
//        Object dataToggle = component.getAttributes().get("data-toggle");
//        Object dataTarget = component.getAttributes().get("data-target");
//
//        if (dataToggle != null) {
//            context.getResponseWriter().writeAttribute("data-toggle", dataToggle, null);
//        }
//
//        if (dataTarget != null) {
//            context.getResponseWriter().writeAttribute("data-target", dataTarget, null);
//        }
//
//
//        super.getEndTextToRender(context, component, currentValue);
//    }

//    @Override
//    protected void renderAsActive(FacesContext facesContext, UIComponent uiComponent) throws IOException {
//
//        Object dataToggle = uiComponent.getAttributes().get("data-toggle");
//        Object dataTarget = uiComponent.getAttributes().get("data-target");
//
//        if (dataToggle != null) {
//            facesContext.getResponseWriter().writeAttribute("data-toggle", dataToggle, null);
//        }
//
//        if (dataTarget != null) {
//            facesContext.getResponseWriter().writeAttribute("data-target", dataTarget, null);
//        }
//
//        super.renderAsActive(facesContext, uiComponent);
//    }
}
