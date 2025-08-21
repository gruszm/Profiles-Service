package pl.gruszm.profiles_service.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gruszm.profiles_service.DTOs.AddressDTO;
import pl.gruszm.profiles_service.entities.Address;
import pl.gruszm.profiles_service.repositories.AddressRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AddressService
{
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private Validator validator;

    public List<AddressDTO> getAddressesByUserId(Long userId) throws IllegalArgumentException
    {
        if (userId < 0L)
        {
            throw new IllegalArgumentException("Illegal argument: userId must not be negative.");
        }

        List<Address> addresses = addressRepository.findByUserId(userId);

        if (addresses == null || addresses.isEmpty())
        {
            return List.of();
        }
        else
        {
            return addresses.stream().map(AddressDTO::fromEntity).toList();
        }
    }

    public Address addAddress(AddressDTO addressDTO) throws ValidationException, IllegalArgumentException
    {
        Address address = addressDTO.toEntity();
        Set<ConstraintViolation<Address>> violations = validator.validate(address);

        // Throw exception with field name and message, if there is any violation
        if (!violations.isEmpty())
        {
            String errorMessage = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));

            throw new ValidationException(errorMessage);
        }

        return addressRepository.save(address);
    }

    public void setValidator(Validator validator)
    {
        this.validator = validator;
    }
}
