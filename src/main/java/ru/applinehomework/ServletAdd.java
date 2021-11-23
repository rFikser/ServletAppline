package ru.applinehomework;

import com.google.gson.*;
import ru.applinehomework.logic.Model;
import ru.applinehomework.logic.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/add")
public class ServletAdd extends HttpServlet {

    private AtomicInteger counter = new AtomicInteger(5);
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        response.setContentType("text/html;charset=utf8");
//        request.setCharacterEncoding("UTF-8");
//
//        PrintWriter pw = response.getWriter();
//        String name = request.getParameter("name");
//        String surname = request.getParameter("surname");
//        double salary = Double.parseDouble(request.getParameter("salary"));
//
//        User user = new User(name, surname, salary);
//        model.add(user, counter.getAndIncrement());
//
//        pw.print("<html>" +
//                "<h3>Пользователь " + name + " " + surname + " с зарплатой " + salary + " успешно создан! :)</h3>" +
//                "<a href=\"addUser.html\"> Создать нового пользователя</a> <br>" +
//                "<a href=\"index.jsp\">Домой</a>" +
//                "</html>"
//        );
//    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {

        req.setCharacterEncoding("UTF-8");

        StringBuffer jb = new StringBuffer();
        String line;

        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        double salary = Double.parseDouble(req.getParameter("salary"));

        User user = new User(name, surname, salary);
        model.add(user, counter.getAndIncrement());

        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.print(gson.toJson(model.getFromList()));

    }
}
