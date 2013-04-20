package com.test.jsfbootstrap.render;

import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.LabelRenderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.logging.Level;

/**
 *
 */
public class BootStrapLabelRenderer extends LabelRenderer{

    @Override
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {

        rendererParamsNotNull(context, component);

        if (!shouldEncode(component)) {
            return;
        }

        Object dataToggle = component.getAttributes().get("data-toggle");
        Object dataTarget = component.getAttributes().get("data-target");

        ResponseWriter writer = context.getResponseWriter();
        assert(writer != null);

        String forClientId = null;
        String forValue = (String) component.getAttributes().get("for");
        if (forValue != null) {
            forValue = augmentIdReference(forValue, component);
            UIComponent forComponent = getForComponent(context, forValue, component);
            if (forComponent == null) {
                // it could that the component hasn't been created yet. So
                // construct the clientId for component.
                forClientId = getForComponentClientId(component, context,
                        forValue);
            } else {
                forClientId = forComponent.getClientId(context);
            }
        }

        // set a temporary attribute on the component to indicate that
        // label end element needs to be rendered.
        component.getAttributes().put("com.sun.faces.RENDER_END_ELEMENT", "yes");
        writer.startElement("label", component);
        writeIdAttributeIfNecessary(context, writer, component);

        if (dataTarget != null) {
            context.getResponseWriter().writeAttribute("data-target", dataTarget, null);
        }

        if (dataToggle != null) {
            context.getResponseWriter().writeAttribute("data-toggle", dataToggle, null);
        }

        if (forClientId != null) {
            writer.writeAttribute("for", forClientId, "for");
        }

        RenderKitUtils.renderPassThruAttributes(context,
                writer,
                component,
                AttributeManager.getAttributes(AttributeManager.Key.OUTPUTLABEL));
        String styleClass = (String)
                component.getAttributes().get("styleClass");
        if (null != styleClass) {
            writer.writeAttribute("class", styleClass, "styleClass");
        }

        // render the curentValue as label text if specified.
        String value = getCurrentValue(context, component);
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Value to be rendered " + value);
        }
        if (value != null && value.length() != 0) {
            Object val = component.getAttributes().get("escape");
            boolean escape = (val != null) && Boolean.valueOf(val.toString());

            if (escape) {
                writer.writeText(value, component, "value");
            } else {
                writer.write(value);
            }
        }
        writer.flush();

    }

}
