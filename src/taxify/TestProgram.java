package taxify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestProgram {
    private static final List<String> MALE_NAMES = List.of("John", "Michael", "David", "James", "Robert", "Daniel", "William", "Joseph", "Charles", "Matthew");
    private static final List<String> FEMALE_NAMES = List.of("Emily", "Jessica", "Sarah", "Ashley", "Amanda", "Elizabeth", "Samantha", "Jennifer", "Lauren", "Megan");
    private static final List<String> LAST_NAMES = List.of("Smith", "Johnson", "Brown", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin");
    public static void main(String[] args) {
        
        Random random = new Random();

        List<IUser> users = new ArrayList<IUser>();
        for (int i = 1; i <= 15; i++) {
            char gender = random.nextBoolean() ? 'M' : 'F';
            String firstName = (gender == 'M') ? MALE_NAMES.get(random.nextInt(MALE_NAMES.size())) : FEMALE_NAMES.get(random.nextInt(FEMALE_NAMES.size()));
            String lastName = LAST_NAMES.get(random.nextInt(LAST_NAMES.size()));
            LocalDate birthDate = generateRandomBirthDate(random);
            users.add(new User(i, firstName, lastName, gender, birthDate));
        }

        List<IVehicle> vehicles = new ArrayList<IVehicle>();
        for (int i = 1; i <= 10; i++) {
            ILocation location = ApplicationLibrary.randomLocation();
            if (i % 3 == 0) {
                vehicles.add(new Shuttle(i, location));
            } else {
                vehicles.add(new Taxi(i, location));
            }
        }

        TaxiCompany taxiCompany = new TaxiCompany("Taxi Company", users, vehicles);
        
        ApplicationSimulator appSimulator = new ApplicationSimulator(taxiCompany, users, vehicles);
        
        taxiCompany.addObserver(appSimulator);
        
        for (int i = 0; i < 5; i++) {
            appSimulator.requestService();
        }

        while (appSimulator.getTotalServices() > 0) {
            
            appSimulator.show();
            appSimulator.update();
            
            if (random.nextDouble() <= 0.25) {
                appSimulator.requestService();
            }
        }

        appSimulator.showStatistics();
    }

    private static LocalDate generateRandomBirthDate(Random random) {
        int year = random.nextInt(50) + 1970;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1;
        return LocalDate.of(year, month, day);
    }
}