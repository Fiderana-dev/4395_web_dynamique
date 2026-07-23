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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();


        if("/liste".equals(request.getPathInfo())){
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Access-Control-Allow-Origin", "*");
                List<Ordinateur> ordinateurs = new Ordinateur().getAll();
                String json = new Gson().toJson(ordinateurs);
                response.getWriter().write(json);
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
            String idPC = request.getParameter("idPC");
            HttpSession session = request.getSession();
            String nomUtilisateur = (String) session.getAttribute("nomUtilisateur");
            if (nomUtilisateur == null) {
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                if (idPC != null && !idPC.isEmpty()) {
                    new Ordinateur().delete(idPC);
                }
                response.sendRedirect(request.getContextPath() + "/listeOrdi");
            }
            return;
        }

        // Afficher le formulaire d'ajout/modification avec la liste des modèles
        List<Model> modeles = new Model().listerModels();
        request.setAttribute("modeles", modeles);

        String idPCStr = request.getParameter("idPC");
        if (idPCStr != null && !idPCStr.isEmpty()) {
            int idPC = Integer.parseInt(idPCStr);
            Ordinateur ordi = new Ordinateur().getById(idPC);
            request.setAttribute("ordinateur", ordi);
        }

        request.getRequestDispatcher("/form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ram = request.getParameter("ram");
        String processeur = request.getParameter("processeur");
        String disqueDur = request.getParameter("disqueDur");
        String modele = request.getParameter("modele");

        if (ram == null || processeur == null || disqueDur == null || modele == null
                || ram.isEmpty() || processeur.isEmpty() || disqueDur.isEmpty() || modele.isBlank()) {
            throw new ServletException("Tous les champs sont obligatoires");
        }

        try {
            int ramValue = Integer.parseInt(ram);
            int disqueDurValue = Integer.parseInt(disqueDur);
            int idModele = Integer.parseInt(modele);
            String processeurValue = processeur.trim();

            if (ramValue <= 0) {
                throw new ServletException("La RAM doit être positive");
            }
            if (disqueDurValue <= 0) {
                throw new ServletException("Le disque dur doit être positif");
            }

            Ordinateur ordi;
            String idPCStr = request.getParameter("idPC");
            HttpSession session = request.getSession();
            String nomUtilisateur = (String) session.getAttribute("nomUtilisateur");
            
            if (nomUtilisateur == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            
            if (idPCStr != null && !idPCStr.isEmpty()) {
                int idPC = Integer.parseInt(idPCStr);
                ordi = new Ordinateur(idPC, idModele, processeurValue, ramValue, disqueDurValue);
                ordi.update();
            } else {
                ordi = new Ordinateur(idModele, processeurValue, ramValue, disqueDurValue);
                ordi.insert();
            }
            response.sendRedirect(request.getContextPath() + "/listeOrdi");
        } catch (NumberFormatException e) {
            throw new ServletException("Les champs numériques doivent contenir des nombres valides", e);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'enregistrement de l'ordinateur", e);
        }
    }
}