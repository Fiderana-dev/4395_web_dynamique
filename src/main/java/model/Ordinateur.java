package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

public class Ordinateur {

    private int id;
    private int idModel;
    private String processeur;
    private int ram;
    private int diskdur;

    public Ordinateur() {
    }

    public Ordinateur(int idModel, String processeur, int ram, int diskdur) {
        this.idModel = idModel;
        this.processeur = processeur;
        this.ram = ram;
        this.diskdur = diskdur;
    }

    public Ordinateur(int id, int idModel, String processeur, int ram, int diskdur) {
        this.id = id;
        this.idModel = idModel;
        this.processeur = processeur;
        this.ram = ram;
        this.diskdur = diskdur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdModel() {
        return idModel;
    }

    public void setIdModel(int idModel) {
        this.idModel = idModel;
    }

    public String getProcesseur() {
        return processeur;
    }

    public void setProcesseur(String processeur) {
        this.processeur = processeur;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getDiskdur() {
        return diskdur;
    }

    public void setDiskdur(int diskdur) {
        this.diskdur = diskdur;
    }

    public List<Ordinateur> getAll() throws SQLException {
        List<Ordinateur> list = new ArrayList<>();
        String sql = "SELECT id, idModel, processeur, ram, diskdur FROM ordinateur";
        try (Connection conn = Maconnexion.getConnexion();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Ordinateur o = new Ordinateur(
                        rs.getInt("id"),
                        rs.getInt("idModel"),
                        rs.getString("processeur"),
                        rs.getInt("ram"),
                        rs.getInt("diskdur"));
                list.add(o);
            }
        }
        return list;
    }

    public Ordinateur getById(int id) {
        String sql = "SELECT id, idModel, processeur, ram, diskdur FROM ordinateur WHERE id = ?";
        try (Connection conn = Maconnexion.getConnexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Ordinateur(
                            rs.getInt("id"),
                            rs.getInt("idModel"),
                            rs.getString("processeur"),
                            rs.getInt("ram"),
                            rs.getInt("diskdur"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert() throws SQLException {
        String sql = "INSERT INTO ordinateur (idModel, processeur, ram, diskdur) VALUES (?, ?, ?, ?)";
        try (Connection conn = Maconnexion.getConnexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.idModel);
            pstmt.setString(2, this.processeur);
            pstmt.setInt(3, this.ram);
            pstmt.setInt(4, this.diskdur);
            pstmt.executeUpdate();
        }
    }

    public void update() throws SQLException {
        String sql = "UPDATE ordinateur SET idModel = ?, processeur = ?, ram = ?, diskdur = ? WHERE id = ?";
        try (Connection conn = Maconnexion.getConnexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.idModel);
            pstmt.setString(2, this.processeur);
            pstmt.setInt(3, this.ram);
            pstmt.setInt(4, this.diskdur);
            pstmt.setInt(5, this.id);
            pstmt.executeUpdate();
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM ordinateur WHERE id = ?";
        try (Connection conn = Maconnexion.getConnexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertOrdiEtat(int idOrdinateur, int etat, String observation, Date date) {
        String sql = "INSERT INTO ordinateurEtat (idPc, idEtat, observation, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = Maconnexion.getConnexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idOrdinateur);
            pstmt.setInt(2, etat);
            pstmt.setString(3, observation);
            pstmt.setDate(4, date);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
