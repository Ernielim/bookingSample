package demo.sonder.payload;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import demo.sonder.model.ContactInfo;

public class ApartmentPayload {
    private String name;
    private String amenities;
    private String details;
    @JsonDeserialize(contentUsing = ContactInfo.class)
    private ContactInfo contactInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
}
