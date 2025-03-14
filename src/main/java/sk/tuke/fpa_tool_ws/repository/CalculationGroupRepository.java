package sk.tuke.fpa_tool_ws.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sk.tuke.fpa_tool_ws.model.CalculationGroup;

import java.util.Collection;

@Repository
public interface CalculationGroupRepository extends MongoRepository<CalculationGroup, String> {

    Collection<CalculationGroup> findByCreatedBy(String createdBy);
}
