package demo.sonder.repository;

import demo.sonder.model.Apartment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApartmentRepository extends CrudRepository<Apartment, Long>{
    List<Apartment> findByName(String name);
}
