package pl.gruszm.profile_service.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "addresses")
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    @NotNull
    @Min(value = 0, message = "User ID must be 0 or higher.")
    private Long userId;

    @NotBlank
    @Column(name = "street", nullable = false)
    private String street;

    @NotNull
    @Min(value = 1, message = "House number must be above 0.")
    @Column(name = "house_number", nullable = false)
    private Short houseNumber;

    @Min(value = 1, message = "Apartment number must be above 0.")
    @Column(name = "apartment_number")
    private Short apartmentNumber;

    @NotBlank
    @Pattern(regexp = "\\d{2}-\\d{3}")
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @NotBlank
    @Column(name = "city", nullable = false)
    private String city;

    @NotBlank
    @Column(name = "voivodeship", nullable = false)
    private String voivodeship;

    @NotBlank
    @Column(name = "country", nullable = false)
    private String country;

    public long getId()
    {
        return id;
    }

    public Long getUserId()
    {
        return userId;
    }

    public Address setUserId(Long userId)
    {
        this.userId = userId;

        return this;
    }

    public String getStreet()
    {
        return street;
    }

    public Address setStreet(String street)
    {
        this.street = street;

        return this;
    }

    public Short getHouseNumber()
    {
        return houseNumber;
    }

    public Address setHouseNumber(Short houseNumber)
    {
        this.houseNumber = houseNumber;

        return this;
    }

    public Short getApartmentNumber()
    {
        return apartmentNumber;
    }

    public Address setApartmentNumber(Short apartmentNumber)
    {
        this.apartmentNumber = apartmentNumber;

        return this;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public Address setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;

        return this;
    }

    public String getCity()
    {
        return city;
    }

    public Address setCity(String city)
    {
        this.city = city;

        return this;
    }

    public String getVoivodeship()
    {
        return voivodeship;
    }

    public Address setVoivodeship(String voivodeship)
    {
        this.voivodeship = voivodeship;

        return this;
    }

    public String getCountry()
    {
        return country;
    }

    public Address setCountry(String country)
    {
        this.country = country;

        return this;
    }
}
