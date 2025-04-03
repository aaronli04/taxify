package taxify;

import java.time.LocalDate;

public interface IDriver {
    
    public int getId();
    public String getFirstName();
    public String getLastName();
    public char getGender();
    public LocalDate getBirthDate();
    public int getExperience();
    public double getRating();
    public String toString();
    
}
