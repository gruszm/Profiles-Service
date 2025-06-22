package pl.gruszm.profile_service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gruszm.profile_service.DTOs.AddressDTO;
import pl.gruszm.profile_service.DTOs.UserHeader;
import pl.gruszm.profile_service.services.AddressService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/secure/profiles/addresses")
public class AddressSecureController
{
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AddressService addressService;

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
