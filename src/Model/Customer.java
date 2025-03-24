package Model;
import java.util.Date;

public class Customer {
    private int customerId;
    private String name;
    private String phone;
    private Date birthDate;
    private int points;

    public Customer() {}

    public Customer(int id, String name, String phone, Date birthDate, int points) {
        this.customerId = id;
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
        this.points = points;
    }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}
