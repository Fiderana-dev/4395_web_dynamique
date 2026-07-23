package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private Integer id;
    private Marque marque;
    private String libele;

    public Model() {
    }

    public Model(Integer id, Marque marque, String libele) {
        this.id = id;
        this.marque = marque;
        this.libele = libele;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public String getLibele() {
        return libele;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public List<Model> listerModels() {
        List<Model> models = new ArrayList<>();
        try (Connection conn = Maconnexion.getConnexion();
                PreparedStatement pstmt = conn.prepareStatement("SELECT id, libele FROM model")) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Model model = new Model();
                model.setId(rs.getInt("id"));
                model.setLibele(rs.getString("libele"));
                models.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return models;
    }

}
