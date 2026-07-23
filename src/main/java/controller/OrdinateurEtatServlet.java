package controller;

import java.io.IOException;
import java.sql.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Ordinateur;

public class OrdinateurEtatServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        int idOrdinateur = Integer.parseInt(request.getParameter("idOrdinateur"));
        int etat = Integer.parseInt(request.getParameter("etat"));
        String observation = request.getParameter("observation");
        Date date = Date.valueOf(request.getParameter("date"));

        Ordinateur ordi = new Ordinateur();
        ordi.insertOrdiEtat(idOrdinateur, etat, observation, date);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(
            "{\"success\":true,\"message\":\"Etat enregistrer\"}"
        );

    }
}
