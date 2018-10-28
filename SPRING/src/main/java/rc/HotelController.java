package rc;


import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private HotelRepository hotelRepository;

    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @GetMapping("/all")
    public List<Hotel> getAll() {
        List<Hotel> hotels = this.hotelRepository.findAll();
        return hotels;
    }

    @PutMapping
    public void insert(@RequestBody Hotel hotel) {
        this.hotelRepository.insert(hotel);
    }

    @PostMapping
    public void update(@RequestBody Hotel hotel) {
        this.hotelRepository.save(hotel);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        this.hotelRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public Hotel getById(@PathVariable("id") String id) {
        Hotel hotel = this.hotelRepository.findHotelById(id);
        return hotel;
    }

    @GetMapping("/priceMaximum/{maxPrice}")
    public List<Hotel> getHotelByPricePerNight(@PathVariable("maxPrice") int maxPrice) {
        List<Hotel> hotels = this.hotelRepository.findByPricePerNightLessThan(maxPrice);
        return hotels;
    }

    @GetMapping("/priceMinimum/{minPrice}")
    public List<Hotel> getHotelByPricePerNightHigher(@PathVariable("minPrice") int minPrice) {
        List<Hotel> hotels = this.hotelRepository.findByPricePerNightGreaterThan(minPrice);
        return hotels;
    }

    @GetMapping("/address/{city}")
    public List<Hotel> findHotelsByCity(@PathVariable("city") String city) {
        List<Hotel> hotels = this.hotelRepository.findByCityAddress(city);
        return hotels;
    }

    @GetMapping("/country/{country}")
    public List<Hotel> findHotelByCountry(@PathVariable("country") String country) {

        // create a query class (QHotel)
        QHotel qHotel = new QHotel("hotel");

        // Using the query class we can create filters
        BooleanExpression filterByCountry = qHotel.address.country.eq(country);

        // we can then pass the filters to the findAll() method
        List<Hotel> hotels = (List<Hotel>) this.hotelRepository.findAll(filterByCountry);

        return hotels;
    }


    @GetMapping("recommended")
    public List<Hotel> getRecommended() {

        final int maxPrice = 100;
        final int minRating = 7;

        // create a query class (QHotel)
        QHotel qHotel = new QHotel("hotel");
        BooleanExpression filterByPrice = qHotel.pricePerNight.lt(maxPrice);
        BooleanExpression filterByRating = qHotel.reviews.any().rating.gt(minRating);

        List<Hotel> hotels = (List<Hotel>) this.hotelRepository.findAll(filterByPrice.and(filterByRating));
        return hotels;
    }

}
