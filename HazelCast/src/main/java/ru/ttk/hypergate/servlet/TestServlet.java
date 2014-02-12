package ru.ttk.hypergate.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;

/**
 *
 *
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet {

    static final String ATTRIBUTE_VALUE_1 = "value1";
    static final String ATTRIBUTE_VALUE_2 = "value2";
    static final String ATTRIBUTE_TTT = "TTT";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // form parameters


        HttpSession httpSession = request.getSession();



        System.out.println("-----------------------");

        String TTT = (String)httpSession.getAttribute(ATTRIBUTE_TTT);
        if(TTT == null){
            httpSession.setAttribute(ATTRIBUTE_TTT, "1234567890 + " + new Date().toString() + " sessionid:" + httpSession.getId());
        }

        String value1 = (String)httpSession.getAttribute(ATTRIBUTE_VALUE_1);
        String value2 = (String)httpSession.getAttribute(ATTRIBUTE_VALUE_2);

        value1 = value1 == null ? "" : value1;
        value2 = value2 == null ? "" : value2;

        System.out.println("Session:" + httpSession.getId());
        System.out.println("Session:" + new Date(httpSession.getLastAccessedTime()));
        System.out.println("Session ATTRIBUTE_TTT:" + TTT);

        System.out.println("");
        // Параметр
        String parameter = request.getParameter("parameter");

        // Старт HTTP сессии
        HttpSession session = request.getSession(true);
//        HttpSession session = request.getSession(false);
        session.setAttribute("parameter", parameter);


        // Process form parameters
        System.out.println("\n\n-- START form parameters");
        Enumeration formParameters = request.getParameterNames();
        while(formParameters.hasMoreElements()){
            String parameterName = (String) formParameters.nextElement();
            System.out.println("name:" + parameterName);
            System.out.println("value:" + request.getParameter(parameterName) + "\n");

            if(parameterName.equalsIgnoreCase(ATTRIBUTE_VALUE_1) && request.getParameter(parameterName) != null){
                value1 = request.getParameter(parameterName);
                httpSession.setAttribute(ATTRIBUTE_VALUE_1, value1);
            }

            if(parameterName.equalsIgnoreCase(ATTRIBUTE_VALUE_2) && request.getParameter(parameterName) != null){
                value2 = request.getParameter(parameterName);
                httpSession.setAttribute(ATTRIBUTE_VALUE_2, value2);
            }

        }
        System.out.println("-- END form parameters ---\n\n");

        /*

            These are classical old school a hardcoded servlet response - no any JSP
            'Cause it only for Test :)

         */


        request.setAttribute("value1","333333333333333");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Distributed HttpSession</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>" +
                    " Ответ сервлета: " +
                    " <br /><br />parameter:" + parameter + " " +
                    " <br /><br />attribute:" + TTT +
                    " </h3>");

            out.println("" +
                    "" +
                    "<br />" +
                    "" +
                    "" +
                    "<form id=\"form_id\" name=\"test_form\" method=\"post\" action=\""+request.getContextPath() + "/test"+"\" enctype=\"application/x-www-form-urlencoded\">" +
                    "<br />" +
                    "" +
                    "<fieldset>\n" +
                    " <legend>Тестовые параметры</legend>" +
                    "" +
                    "" +
                    " <label>Значение №1:</label>" +
                    " <input type=\"text\" name=\"value1\" value=" + value1 + " >" +
                    "" +
                    " <label>Значение №2:</label>" +
                    " <input type=\"text\" name=\"value2\" value=" + value2 + " >" +
                    "" +
                    " <button id=\"button_id\" name=\"button_name\" >Отправить</button>" +
                    "" +
                    "</fieldset>" +
                    "" +
                    "</form>" +
                    "");

            out.println("</body>");
            out.println("</html>");

        } finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

}
