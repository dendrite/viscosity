package ru.ttk.hzt.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 *
 *
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession httpSession = request.getSession();
        System.out.println("-----------------------");
        String TTT = (String)httpSession.getAttribute("TTT");
        if(TTT == null){
            httpSession.setAttribute("TTT", "1234567890 + " + new Date().toString());
        }

        System.out.println("Session:" + httpSession.getId());
        System.out.println("Session:" + new Date(httpSession.getLastAccessedTime()));
        System.out.println("Session TTT:" + TTT);

        System.out.println("");
        // Параметр
        String parameter = request.getParameter("parameter");

        // Старт HTTP сессии
        HttpSession session = request.getSession(true);
        session.setAttribute("parameter", parameter);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Заголовок</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Пример сервлета" + parameter + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

}
