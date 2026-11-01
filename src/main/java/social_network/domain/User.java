package social_network.domain;
/*
* User that links everithing together
 */
public class User extends Entity<Long> implements Comparable<User>{
    private String lastname;
    private String firstname;

    public User(long id, String nume, String prenume) {
        super(id);
        this.lastname = nume;
        this.firstname = prenume;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setLastname(String name2) {
        this.lastname = name2;
    }

    public void setFirtName(String firstName2) {
        this.firstname = firstName2;
    }

    @Override
    public int compareTo(User o) {
        return getId().compareTo(o.getId());
    }

    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", nume='" + lastname +
                ", prenume='" + firstname +
                '}';
    }

    public boolean equals(User o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        return getId().equals(o.getId()) && lastname.equals(o.lastname) && firstname.equals(o.firstname);
    }
}
