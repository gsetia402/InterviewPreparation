package rc;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {

    Hotel findHotelById(String id);

    List<Hotel> findByPricePerNightLessThan(int maxPrice);

    List<Hotel> findByPricePerNightGreaterThan(int minPrice);

    @Query(value = "{address.city:?0}")
    List<Hotel> findByCityAddress(String city);

}
