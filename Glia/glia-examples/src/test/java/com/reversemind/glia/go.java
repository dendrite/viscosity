package com.reversemind.glia;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.*;
import java.net.URLDecoder;

/**
 * Date: 5/27/13
 * Time: 2:46 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class go implements Serializable {

    public static void main(String... args) throws Exception {

        System.setProperty("http.proxyHost", "10.105.0.217");
        System.setProperty("http.proxyPort", "3128");

        System.setProperty("https.proxyHost", "10.105.0.217");
        System.setProperty("https.proxyPort", "3128");

        String parseUrl = "https://twitter.com/i/profiles/show/splix/timeline/with_replies?include_available_features=1&include_entities=1&max_id=285605679744569344";

        Document doc = Jsoup.connect(parseUrl)
                .ignoreContentType(true)
                .userAgent(getAgent())
                .timeout(3000)
                .followRedirects(true)
                .referrer("http://www.google.com")
                .get();


        doc.outputSettings().charset("UTF-8");
        String title = doc.title();

        System.out.println(URLDecoder.decode(doc.body().html().toString(),"UTF-8"));
        System.out.println(doc.html());

        String s2 = Jsoup.clean(doc.html(),Whitelist.basic());
        System.out.println(s2);

//        String htmlText = Jsoup.clean( doc.body().html(), Whitelist.simpleText() );
//        System.out.println("htmlText == " +  htmlText);



        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readValue(doc.html().toString(), JsonNode.class);
        System.out.println(actualObj);




        String result = URLDecoder.decode("\u003cb\u003e\u041e\u0442\u0432\u0435", "UTF-8");
        System.out.println("result = " + result);
//
//        System.out.println("\n\n");
//
//        //StringUtils.newStringUtf8(Base64.decodeBase64(s));
//
//
//        File file2 = new File("/opt/_del/decoded2.txt");
//        BufferedWriter bw = new BufferedWriter(new FileWriter(file2));
//
//
//        File file = new File("/opt/_del/decode.txt");
//        BufferedReader br = new BufferedReader( new FileReader(file));
//
//
//        String tmp = "";
//        while((tmp = br.readLine()) != null){
//            String newStr = URLDecoder.decode(tmp, "UTF-8");
//            bw.write(newStr + "\n");
//
//        }
//        bw.flush();
//        bw.close();
    }

    public static String getAgent(){
        int index = (int)Math.round(Math.random() * (agents.length-1));
        return agents[index];
    }

    public static String[] agents = {
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20130406 Firefox/23.0",
            "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:23.0) Gecko/20131011 Firefox/23.0",

            "Mozilla/5.0 (X11; U; SunOS sun4u; en-US; rv:1.4b) Gecko/20030517 Mozilla Firebird/0.6",
            "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.4b) Gecko/20030630 Mozilla Firebird/0.6",
            "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.4b) Gecko/20030607 Mozilla Firebird/0.6",
            "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.4b) Gecko/20030516 Mozilla Firebird/0.6",
            "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.4b) Gecko/20030505 Mozilla Firebird/0.6",
            "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.4a) Gecko/20030425 Mozilla Firebird/0.6",
            "Mozilla/5.0 (Windows; U; WinNT4.0; en-US; rv:1.4b) Gecko/20030610 Mozilla Firebird/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:x.xx) Gecko/20030504 Mozilla Firebird/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.5a) Gecko/20030702 Mozilla Firebird/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.5a) Gecko/20030630 Mozilla Firebird/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.4b) Gecko/20030615 Mozilla Firebird/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.4b) Gecko/20030516 Mozilla Firebird/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.4b) Gecko/20030503 Mozilla Firebird/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; de-DE; rv:1.4b) Gecko/20030516 Mozilla Firebird/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.0; en-US; rv:1.4b) Gecko/20030516 Mozilla Firebird/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.0; en-US; rv:1.4b) Gecko/20030514 Mozilla Firebird/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.0; en-US; rv:1.4b) Gecko/20030504 Mozilla Firebird/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.0; de-DE; rv:1.4b) Gecko/20030516 Mozilla Firebird/0.6",
            "Mozilla/5.0 (Windows; U; Win98; en-US; rv:1.4b) Gecko/20030516 Mozilla Firebird/0.6",
            "Mozilla/5.0 (Windows; U; Win98; de-DE; rv:1.4b) Gecko/20030516 Mozilla Firebird/0.6",

            "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:14.0) Gecko/20100101 Firefox/14.0.1",
            "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:14.0) Gecko/20100101 Firefox/14.0.1",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; WOW64; en-US; rv:2.0.4) Gecko/20120718 AskTbAVR-IDW/3.12.5.17700 Firefox/14.0.1",
            "Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20120403211507 Firefox/14.0.1",
            "Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/ 20120405 Firefox/14.0.1",
            "Mozilla/5.0 (Windows NT 6.0; rv:14.0) Gecko/20100101 Firefox/14.0.1",

            "Mozilla/5.0 (X11; U; Linux x86_64; en-US) AppleWebKit/534.13 (KHTML, like Gecko) Chrome/9.0.597.0 Safari/534.13",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.13 (KHTML, like Gecko) Chrome/9.0.597.0 Safari/534.13",
            "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US) AppleWebKit/534.13 (KHTML, like Gecko) Chrome/9.0.597.0 Safari/534.13",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.13 (KHTML, like Gecko) Chrome/9.0.597.0 Safari/534.13",
            "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_5; en-US) AppleWebKit/534.13 (KHTML, like Gecko) Chrome/9.0.597.0 Safari/534.13",
            "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; en-US) AppleWebKit/534.13 (KHTML, like Gecko) Chrome/9.0.597.0 Safari/534.13",

            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 Safari/535.11",
            "Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 Safari/535.11",
            "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 Safari/535.11",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 Safari/535.11",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 Safari/535.11",
            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 Safari/535.11",
            "Mozilla/5.0 (Windows NT 6.0; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 Safari/535.11",
            "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 Safari/535.11",
            "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 Safari/535.11",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 Safari/535.11",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 Safari/535.11",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 Safari/535.11",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_8) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 Safari/535.11",
    };

}
