package ma.ensa.volley;

import java.util.List;

public class Student extends User{
    private String name;
    private int phone;
    private String email;
    private Filiere filiere;

    public Student(int id, List<Role> roles, String username, String password, String name, int phone, String email, Filiere filiere) {
        super(id, roles, username, password);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.filiere = filiere;
    }

    public Student(List<Role> roles, String username, String password, String name, int phone, String email, Filiere filiere) {
        super(roles, username, password);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.filiere = filiere;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }
}
