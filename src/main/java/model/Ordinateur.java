package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class Ordinateur {

    private int id;
    private int idModel;
    private String processeur;
    private int ram;
    private int diskdur;

    public Ordinateur() {}

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

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdModel() { return idModel; }
    public void setIdModel(int idModel) { this.idModel = idModel; }
    public String getProcesseur() { return processeur; }
    public void setProcesseur(String processeur) { this.processeur = processeur; }
    public int getRam() { return ram; }
    public void setRam(int ram) { this.ram = ram; }
    public int getDiskdur() { return diskdur; }
    public void setDiskdur(int diskdur) { this.diskdur = diskdur; }

    public List<Ordinateur> getAll() throws SQLException {
        List<Ordinateur> list = new ArrayList<>();
        String sql = "SELECT id, idModel, processeur, ram, diskdur FROM ordinateur ORDER BY id";
        try (Connection conn = Maconnexion.getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Ordinateur(rs.getInt("id"), rs.getInt("idModel"),
                        rs.getString("processeur"), rs.getInt("ram"), rs.getInt("diskdur")));
            }
        }
        return list;
    }

    public Ordinateur getById(int id) throws SQLException {
        String sql = "SELECT id, idModel, processeur, ram, diskdur FROM ordinateur WHERE id = ?";
        try (Connection conn = Maconnexion.getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Ordinateur(rs.getInt("id"), rs.getInt("idModel"),
                            rs.getString("processeur"), rs.getInt("ram"), rs.getInt("diskdur"));
                }
            }
        }
        return null;
    }

    public void insert() throws SQLException {
        String sql = "INSERT INTO ordinateur (idModel, processeur, ram, diskdur) VALUES (?, ?, ?, ?)";
        try (Connection conn = Maconnexion.getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idModel);
            pstmt.setString(2, processeur);
            pstmt.setInt(3, ram);
            pstmt.setInt(4, diskdur);
            if (pstmt.executeUpdate() != 1) throw new SQLException("Ajout impossible");
        }
    }

    public void update() throws SQLException {
        String sql = "UPDATE ordinateur SET idModel = ?, processeur = ?, ram = ?, diskdur = ? WHERE id = ?";
        try (Connection conn = Maconnexion.getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idModel);
            pstmt.setString(2, processeur);
            pstmt.setInt(3, ram);
            pstmt.setInt(4, diskdur);
            pstmt.setInt(5, id);
            if (pstmt.executeUpdate() != 1) throw new SQLException("Ordinateur introuvable");
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection conn = Maconnexion.getConnexion()) {
            try (PreparedStatement deleteStates = conn.prepareStatement("DELETE FROM ordinateurEtat WHERE idPc = ?")) {
                deleteStates.setInt(1, id);
                deleteStates.executeUpdate();
            }
            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ordinateur WHERE id = ?")) {
                pstmt.setInt(1, id);
                if (pstmt.executeUpdate() != 1) throw new SQLException("Ordinateur introuvable");
            }
        }
    }

    public void insertOrdiEtat(int idOrdinateur, int etat, Integer idProbleme,
                               String observation, Date date) throws SQLException {
        String sql = "INSERT INTO ordinateurEtat (idPc, idEtat, id_probleme, observation, date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Maconnexion.getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idOrdinateur);
            pstmt.setInt(2, etat);
            if (idProbleme != null) pstmt.setInt(3, idProbleme);
            else pstmt.setNull(3, java.sql.Types.INTEGER);
            pstmt.setString(4, observation);
            pstmt.setDate(5, date);
            if (pstmt.executeUpdate() != 1) throw new SQLException("Enregistrement de l'état impossible");
        }
    }
}
