package ru.applinehomework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


@WebServlet("/calc")
public class ServletCalculator extends HttpServlet {


    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    ServletCalculator.Result result = new ServletCalculator.Result();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer jb = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }

        JsonObject jobj = gson.fromJson(String.valueOf(jb), JsonObject.class);
        PrintWriter pw = response.getWriter();

        double a = jobj.get("a").getAsDouble();
        double b = jobj.get("b").getAsDouble();
        String mathOp = jobj.get("math").getAsString();

        switch (mathOp) {
            case "+":
                this.result.setResult(a + b);
                pw.print(this.gson.toJson(this.result));
                break;
            case "-":
                this.result.setResult(a - b);
                pw.print(this.gson.toJson(this.result));
                break;
            case "*":
                this.result.setResult(a * b);
                pw.print(this.gson.toJson(this.result));
                break;
            default:
                if (b == 0) {
                    pw.print("Can't divide by 0");
                } else {
                    this.result.setResult(a / b);
                    pw.print(this.gson.toJson(this.result));
                }


        }


    }

    public class Result {
        public double result;

        public Result() {
            super();
        }

        public double setResult(double result) {
            return this.result = result;
        }
    }
}


