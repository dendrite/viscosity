package com.reversemind.glia.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Date: 5/28/13
 * Time: 9:16 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class go2 implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(go2.class);

    public static void main(String... args) {
        String gg = "{&quot;max_id&quot;:&quot;283150187722076159&quot;,&quot;has_more_items&quot;:true,&quot;items_html&quot;:&quot; \u003cli class=\\&quot;js-stream-item stream-item stream-item expanding-stream-item\\&quot; data-item-id=\\&quot;285605679744569344\\&quot; id=\\&quot;stream-item-tweet-285605679744569344\\&quot; data-item-type=\\&quot;tweet\\&quot;\\u003e\\n \\n\\n\\n \\n\\n\\n \\u003cdiv class=\\&quot;tweet original-tweet js-stream-tweet js-actionable-tweet js-profile-popup-actionable js-original-tweet \\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\&quot; \\ndata-feedback-key=\\&quot;stream_status_285605679744569344\\&quot;\\ndata-tweet-id=\\&quot;285605679744569344\\&quot;";


        String values = gg.replaceAll("\\&quot;", "'").replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("&amp;", "&").replaceAll("&apos;", "'")
                //.replaceAll("&quot;","'")
                .replaceAll("\\n{1,}", " ").
                        replaceAll("\\'", "'")
                .replaceAll("\u003c", "<")
                .replaceAll("\u003e", ">");
        LOG.debug(values);
    }

}
