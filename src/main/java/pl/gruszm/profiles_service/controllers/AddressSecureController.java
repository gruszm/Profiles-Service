package pl.gruszm.profiles_service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gruszm.profiles_service.DTOs.AddressDTO;
import pl.gruszm.profiles_service.DTOs.UserHeader;
import pl.gruszm.profiles_service.entities.Address;
import pl.gruszm.profiles_service.services.AddressService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/secure/profiles/addresses")
public class AddressSecureController
{
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<?> getAddresses(@RequestHeader("X-User") String userHeaderJson)
    {
        Map<String, String> errorResponse = new HashMap<>();

        try
        {
            UserHeader userHeader = objectMapper.readValue(userHeaderJson, UserHeader.class);
            List<Address> addresses = addressService.getAddressesByUserId(userHeader.getId());

            if (addresses == null || addresses.isEmpty())
            {
                return ResponseEntity.notFound().build();
            }
            else
            {
                return ResponseEntity.ok(addresses);
            }
        }
        catch (JsonProcessingException e)
        {
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.internalServerError().body(errorResponse);
        }
        catch (IllegalArgumentException e)
        {
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.badRequest().body(errorResponse);
        }
        catch (Exception e)
        {
            errorResponse.put("message", "Internal server error");

            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addAddressForUser(@RequestHeader("X-User") String userHeaderJson, @RequestBody AddressDTO addressDTO)
    {
        Map<String, String> errorResponse = new HashMap<>();
        UserHeader userHeader;

        try
        {
            userHeader = objectMapper.readValue(userHeaderJson, UserHeader.class);

            addressDTO.setUserId(userHeader.getId());

            addressService.addAddress(addressDTO);

            return ResponseEntity.ok().build();
        }
        catch (JsonProcessingException e)
        {
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.internalServerError().body(errorResponse);
        }
        catch (ValidationException | IllegalArgumentException e)
        {
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.badRequest().body(errorResponse);
        }
        catch (Exception e)
        {
            errorResponse.put("message", "Internal server error");

            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
