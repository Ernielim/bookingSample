package demo.sonder.payload;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.LocalDate;

public class RelocateBookingPayload {
    private Long bookingId;
    private Long apartmentId;
    private String notes;

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
