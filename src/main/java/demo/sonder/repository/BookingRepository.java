package demo.sonder.repository;

import demo.sonder.model.Booking;
import org.joda.time.LocalDate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long>{
    List<Booking> findByStartDateBetweenOrEndDateBetweenAndApartmentIdAndActive(LocalDate startDate, LocalDate endDate,
                        LocalDate startDate1, LocalDate endDate2, Long apartmentId, boolean active);
    List<Booking> findByInitialBookingId(Long bookingId);
}
