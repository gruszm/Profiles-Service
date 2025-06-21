package pl.gruszm.profile_service.DTOs;

import pl.gruszm.profile_service.entities.Address;

public class AddressDTO
{
    private Long userId;
    private String street;
    private Short houseNumber;
    private Short apartmentNumber;
    private String postalCode;
    private String city;
    private String voivodeship;
    private String country;

    public Long getUserId()
    {
        return userId;
    }

    public AddressDTO setUserId(Long userId)
    {
        this.userId = userId;

        return this;
    }

    public String getStreet()
    {
        return street;
    }

    public AddressDTO setStreet(String street)
    {
        this.street = street;

        return this;
    }

    public Short getHouseNumber()
    {
        return houseNumber;
    }

    public AddressDTO setHouseNumber(Short houseNumber)
    {
        this.houseNumber = houseNumber;

        return this;
    }

    public Short getApartmentNumber()
    {
        return apartmentNumber;
    }

    public AddressDTO setApartmentNumber(Short apartmentNumber)
    {
        this.apartmentNumber = apartmentNumber;

        return this;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public AddressDTO setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;

        return this;
    }

    public String getCity()
    {
        return city;
    }

    public AddressDTO setCity(String city)
    {
        this.city = city;

        return this;
    }

    public String getVoivodeship()
    {
        return voivodeship;
    }

    public AddressDTO setVoivodeship(String voivodeship)
    {
        this.voivodeship = voivodeship;

        return this;
    }

    public String getCountry()
    {
        return country;
    }

    public AddressDTO setCountry(String country)
    {
        this.country = country;

        return this;
    }

    public Address toEntity()
    {
        Address address = new Address();

        address.setUserId(userId)
                .setStreet(street)
                .setHouseNumber(houseNumber)
                .setApartmentNumber(apartmentNumber)
                .setPostalCode(postalCode)
                .setCity(city)
                .setVoivodeship(voivodeship)
                .setCountry(country);

        return address;
    }

    public static AddressDTO fromEntity(Address address)
    {
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setUserId(address.getUserId())
                .setStreet(address.getStreet())
                .setHouseNumber(address.getHouseNumber())
                .setApartmentNumber(address.getApartmentNumber())
                .setPostalCode(address.getPostalCode())
                .setCity(address.getCity())
                .setVoivodeship(address.getVoivodeship())
                .setCountry(address.getCountry());

        return addressDTO;
    }
}
