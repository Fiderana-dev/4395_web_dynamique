package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Ordinateur;

public class OrdinateurEtatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String idOrdinateurParam = request.getParameter("idOrdinateur");
            String etatParam = request.getParameter("etat");
            String problemeParam = request.getParameter("idProbleme");
            String observation = request.getParameter("observation");
            String dateParam = request.getParameter("date");

            if (idOrdinateurParam == null || etatParam == null || dateParam == null
                    || idOrdinateurParam.isBlank() || etatParam.isBlank() || dateParam.isBlank()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\":false,\"message\":\"Ordinateur, état et date obligatoires\"}");
                return;
            }

            int idOrdinateur = Integer.parseInt(idOrdinateurParam);
            int etat = Integer.parseInt(etatParam);
            Integer idProbleme = null;
            if (problemeParam != null && !problemeParam.isBlank()) {
                idProbleme = Integer.parseInt(problemeParam);
            }

            if (etat == 1) idProbleme = null;
            if (observation == null) observation = "";

            Date date = Date.valueOf(dateParam);
            new Ordinateur().insertOrdiEtat(idOrdinateur, etat, idProbleme, observation, date);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"success\":true,\"message\":\"État enregistré\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\":false,\"message\":\"Identifiant ou état invalide\"}");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\":false,\"message\":\"Date invalide\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\":false,\"message\":\"Erreur base de données\"}");
        }
    }
}
