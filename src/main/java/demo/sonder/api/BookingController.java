package demo.sonder.api;

import demo.sonder.model.Apartment;
import demo.sonder.model.Booking;
import demo.sonder.model.BookingType;
import demo.sonder.payload.ApartmentPayload;
import demo.sonder.payload.BookingPayload;
import demo.sonder.payload.ExtendBookingPayload;
import demo.sonder.payload.RelocateBookingPayload;
import demo.sonder.repository.ApartmentRepository;
import demo.sonder.repository.BookingRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class BookingController {

    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @RequestMapping(method=GET, value="/apartments")
    @ResponseBody
    public List<Apartment> getAllApartment() {
        Iterable<Apartment> iterable = apartmentRepository.findAll();
        List<Apartment> apartments = new ArrayList<>();
        iterable.forEach(apartments::add);
        return apartments;
    }

    @RequestMapping(method=POST, value="/apartments")
    public void createApartment(@RequestBody ApartmentPayload payload) {
        Apartment apartment = new Apartment(payload.getName(), payload.getAmenities(), payload.getDetails(), payload.getContactInfo());
        apartmentRepository.save(apartment);
    }

    @RequestMapping(method=GET, value="/bookings")
    @ResponseBody
    public List<Booking> getAllBookings() {
        Iterable<Booking> iterable = bookingRepository.findAll();
        List<Booking> bookings = new ArrayList<>();
        iterable.forEach(bookings::add);
        return bookings;
    }

    @RequestMapping(method=POST, value="/bookings")
    public void createBooking(@RequestBody BookingPayload payload) throws ValidationException {
        Booking booking = new Booking(payload.getApartmentId(), payload.getGuestId(), payload.getStartDate(), payload.getEndDate(),
                new DateTime(), BookingType.NEW, true, payload.getNotes(), null);

        if (!booking.bookingAvailable(bookingRepository)) {
            throw new ValidationException("Booking time not available, select different date ranges.");
        }
        booking = bookingRepository.save(booking);
        booking.setInitialBookingId(booking.getId());
        bookingRepository.save(booking);
    }

    @RequestMapping(method=POST, value="/bookings/{id}/extend")
    public void extendBooking(@PathVariable("id") Long id, @RequestBody ExtendBookingPayload payload) throws ValidationException {
        Booking previousBooking = bookingRepository.findById(id).get();

        if (!previousBooking.getActive()) {
            throw new ValidationException("Unable to extend inactive booking, use active booking.");
        }

        Booking extendedBooking = new Booking(previousBooking.getApartmentId(), previousBooking.getGuestId(), payload.getStartDate(), payload.getEndDate(),
                new DateTime(), BookingType.EXTEND, true, payload.getNotes(), previousBooking.getId());

        if (!extendedBooking.bookingAvailable(bookingRepository)) {
            throw new ValidationException("Booking time not available, select different date ranges.");
        }
        previousBooking.setActive(false);
        bookingRepository.save(previousBooking);
        bookingRepository.save(extendedBooking);
    }

    @RequestMapping(method=POST, value="/bookings/{id}/relocate")
    public void relocateBooking(@PathVariable("id") Long id, @RequestBody RelocateBookingPayload payload) throws ValidationException {
        Booking previousBooking = bookingRepository.findById(id).get();
        Booking relocateBooking = new Booking(payload.getApartmentId(), previousBooking.getGuestId(), previousBooking.getStartDate(), previousBooking.getEndDate(),
                new DateTime(), BookingType.RELOCATE, true, payload.getNotes(), previousBooking.getId());

        if (!relocateBooking.bookingAvailable(bookingRepository)) {
            throw new ValidationException("Unable to relocate, dates not available for selected apartment, select different apartment.");
        }
        previousBooking.setActive(false);
        bookingRepository.save(previousBooking);
        bookingRepository.save(relocateBooking);
    }

    @ExceptionHandler(ValidationException.class)
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}

