package ru.applinehomework;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.applinehomework.logic.Model;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/get")
public class ServletList extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer jb = new StringBuffer();

        try {
            BufferedReader reader = request.getReader();

            String line;
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }

        JsonObject jsonObject = this.gson.fromJson(String.valueOf(jb), JsonObject.class);
        request.setCharacterEncoding("UTF-8");
        int id = jsonObject.get("id").getAsInt();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();
        if (id == 0) {
            pw.print(this.gson.toJson(this.model.getFromList()));
        } else if (id > 0) {
            if (id > this.model.getFromList().size()) {
                pw.print(this.gson.toJson("Такого пользователя нет"));
            } else {
                pw.print(this.gson.toJson(this.model.getFromList().get(id)));
            }
        } else {
            pw.print(this.gson.toJson("ID должен быть больше нуля"));
        }

    }
}
