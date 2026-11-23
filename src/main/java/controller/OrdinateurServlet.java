package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.google.gson.Gson;

import model.Model;
import model.Ordinateur;

public class OrdinateurServlet extends HttpServlet {

    private boolean utilisateurConnecte(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("nomUtilisateur") != null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/liste".equals(request.getPathInfo())) {
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Access-Control-Allow-Origin", "*");
                List<Ordinateur> ordinateurs = new Ordinateur().getAll();
                response.getWriter().write(new Gson().toJson(ordinateurs));
            } catch (SQLException e) {
                throw new ServletException("Erreur lors du chargement de la liste JSON", e);
            }
            return;
        }

        if ("/listeOrdi".equals(path)) {
            try {
                List<Ordinateur> ordinateurs = new Ordinateur().getAll();
                request.setAttribute("ordinateurs", ordinateurs);
                request.getRequestDispatcher("/listeOrdinateur.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Erreur lors du chargement de la liste", e);
            }
            return;
        }

        if ("/supprimerOrdi".equals(path)) {
            if (!utilisateurConnecte(request)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            String idPC = request.getParameter("idPC");
            if (idPC == null || idPC.isBlank()) {
                response.sendRedirect(request.getContextPath() + "/listeOrdi");
                return;
            }

            try {
                new Ordinateur().delete(Integer.parseInt(idPC));
                response.sendRedirect(request.getContextPath() + "/listeOrdi");
            } catch (NumberFormatException e) {
                throw new ServletException("Identifiant d'ordinateur invalide", e);
            } catch (SQLException e) {
                throw new ServletException("Erreur lors de la suppression", e);
            }
            return;
        }

        if (!utilisateurConnecte(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Model> modeles = new Model().listerModels();
        request.setAttribute("modeles", modeles);

        String idPCStr = request.getParameter("idPC");
        if (idPCStr != null && !idPCStr.isBlank()) {
            try {
                Ordinateur ordi = new Ordinateur().getById(Integer.parseInt(idPCStr));
                request.setAttribute("ordinateur", ordi);
            } catch (NumberFormatException e) {
                throw new ServletException("Identifiant d'ordinateur invalide", e);
            } catch (SQLException e) {
                throw new ServletException("Erreur lors du chargement de l'ordinateur", e);
            }
        }

        request.getRequestDispatcher("/form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!utilisateurConnecte(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String ram = request.getParameter("ram");
        String processeur = request.getParameter("processeur");
        String disqueDur = request.getParameter("disqueDur");
        String modele = request.getParameter("modele");

        if (ram == null || ram.isBlank() || processeur == null || processeur.isBlank()
                || disqueDur == null || disqueDur.isBlank() || modele == null || modele.isBlank()) {
            throw new ServletException("Tous les champs sont obligatoires");
        }

        try {
            int ramValue = Integer.parseInt(ram);
            int disqueDurValue = Integer.parseInt(disqueDur);
            int idModele = Integer.parseInt(modele);
            String processeurValue = processeur.trim();

            if (ramValue <= 0 || disqueDurValue <= 0) {
                throw new ServletException("La RAM et le disque dur doivent être positifs");
            }

            String idPCStr = request.getParameter("idPC");
            if (idPCStr != null && !idPCStr.isBlank()) {
                Ordinateur ordi = new Ordinateur(Integer.parseInt(idPCStr), idModele,
                        processeurValue, ramValue, disqueDurValue);
                ordi.update();
            } else {
                new Ordinateur(idModele, processeurValue, ramValue, disqueDurValue).insert();
            }
            response.sendRedirect(request.getContextPath() + "/listeOrdi");
        } catch (NumberFormatException e) {
            throw new ServletException("Les champs numériques doivent contenir des nombres valides", e);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'enregistrement de l'ordinateur", e);
        }
    }
}