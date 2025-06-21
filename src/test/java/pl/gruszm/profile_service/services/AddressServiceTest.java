package pl.gruszm.profile_service.services;

import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.gruszm.profile_service.DTOs.AddressDTO;
import pl.gruszm.profile_service.entities.Address;
import pl.gruszm.profile_service.repositories.AddressRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest
{
    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    void setup()
    {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory())
        {
            Validator validator = validatorFactory.getValidator();

            addressService.setValidator(validator);
        }
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenGivenNegativeUserId()
    {
        // Given
        long userId = -1L;

        // When
        // Then
        assertThrows(IllegalArgumentException.class, () -> addressService.getAddressesByUserId(userId));
    }

    @Test
    void shouldReturnAddressesWhenCorrectUserIdIsGiven()
    {
        // Given
        long userId = 0L;
        Address address = new Address();
        List<Address> addressList;
        List<Address> returnedList;

        address.setUserId(userId);
        address.setCity("Katowice");
        address.setCountry("Poland");

        addressList = List.of(address);

        when(addressRepository.findByUserId(userId)).thenReturn(addressList);

        // When
        returnedList = addressService.getAddressesByUserId(userId);

        // Then
        assertNotNull(returnedList);
        assertEquals(1, returnedList.size());
        assertEquals("Katowice", returnedList.get(0).getCity());
    }

    @Test
    void shouldReturnEmptyListWhenUserHasNoAddresses()
    {
        // Given
        long userId = 123L;
        when(addressRepository.findByUserId(userId)).thenReturn(List.of());

        // When
        List<Address> result = addressService.getAddressesByUserId(userId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty(), "Address list should be empty.");
    }

    @Test
    void shouldThrowValidationExceptionWhenAddingEmptyAddress()
    {
        // Given
        AddressDTO addressDTO = new AddressDTO();

        // When
        // Then
        Exception exception = assertThrows(ValidationException.class, () -> addressService.addAddress(addressDTO));
        String errorMessage = exception.getMessage();

        assertTrue(errorMessage.contains("userId"));
        assertTrue(errorMessage.contains("street"));
        assertTrue(errorMessage.contains("houseNumber"));
        assertTrue(errorMessage.contains("apartmentNumber"));
        assertTrue(errorMessage.contains("postalCode"));
        assertTrue(errorMessage.contains("city"));
        assertTrue(errorMessage.contains("voivodeship"));
        assertTrue(errorMessage.contains("country"));
    }

    @Test
    void shouldThrowValidationExceptionWhenGivenInvalidAddress()
    {
        // Given
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setUserId(-5L); // Invalid
        addressDTO.setStreet("Nowa"); // Valid
        addressDTO.setHouseNumber((short) 2); // Valid
        addressDTO.setApartmentNumber(null); // Invalid
        addressDTO.setPostalCode("12345"); // Invalid
        addressDTO.setCity("Warszawa"); // Valid
        addressDTO.setVoivodeship("Mazowieckie"); // Valid
        addressDTO.setCountry("Polska"); // Valid

        // When
        // Then
        ValidationException exception = assertThrows(ValidationException.class, () -> addressService.addAddress(addressDTO));
        String errorMessage = exception.getMessage();

        assertTrue(errorMessage.contains("userId"));
        assertTrue(errorMessage.contains("apartmentNumber"));
        assertTrue(errorMessage.contains("postalCode"));

        assertFalse(errorMessage.contains("street"));
        assertFalse(errorMessage.contains("houseNumber"));
        assertFalse(errorMessage.contains("city"));
        assertFalse(errorMessage.contains("voivodeship"));
        assertFalse(errorMessage.contains("country"));
    }

    @Test
    void shouldAddNewAddressWhenValidAddressIsGiven()
    {
        // Given
        AddressDTO addressDTO = new AddressDTO();

        long userId = 0L;
        String street = "Nowa";
        short houseNumber = 2;
        short apartmentNumber = 5;
        String postalCode = "12-345";
        String city = "Warszawa";
        String voivodeship = "Mazowieckie";
        String country = "Polska";

        addressDTO.setUserId(userId); // Valid
        addressDTO.setStreet(street); // Valid
        addressDTO.setHouseNumber(houseNumber); // Valid
        addressDTO.setApartmentNumber(apartmentNumber); // Valid
        addressDTO.setPostalCode(postalCode); // Valid
        addressDTO.setCity(city); // Valid
        addressDTO.setVoivodeship(voivodeship); // Valid
        addressDTO.setCountry(country); // Valid

        when(addressRepository.save(any(Address.class))).thenReturn(addressDTO.toEntity());

        // When
        Address returnedAddress = addressService.addAddress(addressDTO);

        // Then
        assertNotNull(returnedAddress);
        assertEquals(userId, returnedAddress.getUserId());
        assertEquals(street, returnedAddress.getStreet());
        assertEquals(houseNumber, returnedAddress.getHouseNumber());
        assertEquals(apartmentNumber, returnedAddress.getApartmentNumber());
        assertEquals(postalCode, returnedAddress.getPostalCode());
        assertEquals(city, returnedAddress.getCity());
        assertEquals(voivodeship, returnedAddress.getVoivodeship());
        assertEquals(country, returnedAddress.getCountry());
    }
}