package demo.sonder.model;

import demo.sonder.repository.BookingRepository;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long apartmentId;
    private Long guestId;
    private LocalDate startDate;
    private LocalDate endDate;
    private DateTime timestamp;
    private BookingType bookingType;
    private Boolean active;
    private String notes;
    private Long initialBookingId;

    protected Booking() {}

    public Booking(Long apartmentId, Long guestId, LocalDate startDate, LocalDate endDate, DateTime timestamp, BookingType bookingType, Boolean active, String notes, Long initialBookingId) {
        this.apartmentId = apartmentId;
        this.guestId = guestId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timestamp = timestamp;
        this.bookingType = bookingType;
        this.active = active;
        this.notes = notes;
        this.initialBookingId = initialBookingId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getInitialBookingId() {
        return initialBookingId;
    }

    public void setInitialBookingId(Long initialBookingId) {
        this.initialBookingId = initialBookingId;
    }

    public boolean bookingAvailable(BookingRepository bookingRepository) {
        List<Booking> bookings = bookingRepository.findByStartDateBetweenOrEndDateBetweenAndApartmentIdAndActive(this.startDate, this.endDate.minusDays(1),
                this.startDate.plusDays(1), this.endDate, this.apartmentId, true);

        return bookings.isEmpty();
    }
}
