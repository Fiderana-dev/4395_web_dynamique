package controller;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UtilisateurServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String motdepasse = request.getParameter("motdepasse");

        model.Utilisateur utilisateur = new model.Utilisateur();

        if (utilisateur.compareLogin(nom, motdepasse) == 1) {
            // Stocker le nom dans la session
            HttpSession session = request.getSession();
            session.setAttribute("nomUtilisateur", nom);

            request.setAttribute("message", "Connexion réussie");
            request.getRequestDispatcher("accueil.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "Nom ou mot de passe incorrect");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

}
