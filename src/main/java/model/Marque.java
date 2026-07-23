package model;
public class Marque {
    private Integer id;
    private String libele;

    public Marque(Integer id, String libele) {
        this.id = id;
        this.libele = libele;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibele() {
        return libele;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

}
