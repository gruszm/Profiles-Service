package pl.gruszm.profiles_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gruszm.profiles_service.entities.Address;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>
{
    List<Address> findByUserId(Long userId);
}
