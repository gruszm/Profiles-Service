package pl.gruszm.profile_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gruszm.profile_service.entities.Address;
import pl.gruszm.profile_service.repositories.AddressRepository;

import java.util.List;

@Service
public class AddressService
{
    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAddressesByUserId(Long userId) throws IllegalArgumentException
    {
        if (userId < 0L)
        {
            throw new IllegalArgumentException("Illegal argument: userId must not be negative.");
        }

        return addressRepository.findByUserId(userId);
    }
}
