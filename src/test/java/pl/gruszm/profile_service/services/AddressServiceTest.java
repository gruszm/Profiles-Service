package pl.gruszm.profile_service.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.gruszm.profile_service.entities.Address;
import pl.gruszm.profile_service.repositories.AddressRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest
{
    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

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
}