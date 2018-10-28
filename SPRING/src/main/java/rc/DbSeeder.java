package rc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {

    private HotelRepository hotelRepository;

    public DbSeeder(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Hotel marriot = new Hotel(
                "marriot",
                130,
                new Address("paris", "France"),
                Arrays.asList(
                        new Review("John",8, false),
                        new Review("Marry",7, true)
                )

        );

        Hotel ibis = new Hotel(
                "ibis",
                90,
                new Address("Bucharesr", "Romania"),
                Arrays.asList(
                        new Review("Tedy",9, true),
                        new Review("Marry",7, true)
                )

        );

        Hotel hardRock = new Hotel(
                "hardRock",
                100,
                new Address("delhi", "India"),
                new ArrayList<>()

        );

        // Drop all hotels

        this.hotelRepository.deleteAll();

        List<Hotel> hotels = Arrays.asList(marriot,ibis,hardRock);

        this.hotelRepository.saveAll(hotels);


    }
}
