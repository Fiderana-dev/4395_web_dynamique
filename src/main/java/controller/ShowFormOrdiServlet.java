package controller;

import model.Ordinateur;
import model.Model;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowFormOrdiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isModif = false;

        String idPC = req.getParameter("idPC");

        if (idPC != null && !idPC.isEmpty()) {
            isModif = true;
        }

        List<Model> modeles = new Model().listerModels();

        req.setAttribute("modeles", modeles);

        if (isModif) {
            Ordinateur ordinateur = new Ordinateur().getById(Integer.parseInt(idPC));

            req.setAttribute("ordinateur", ordinateur);
        }

        this.getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
    }
}