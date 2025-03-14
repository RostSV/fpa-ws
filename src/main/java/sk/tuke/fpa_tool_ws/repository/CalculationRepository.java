package sk.tuke.fpa_tool_ws.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sk.tuke.fpa_tool_ws.model.Calculation;

import java.util.Collection;

@Repository
public interface CalculationRepository extends MongoRepository<Calculation, String> {

    Collection<Calculation> findByCreatedBy(String userId);

    Collection<Calculation> findByGroupId(String groupId);

}
