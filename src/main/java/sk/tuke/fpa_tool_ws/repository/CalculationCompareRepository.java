package sk.tuke.fpa_tool_ws.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sk.tuke.fpa_tool_ws.model.CalculationCompareResult;

import java.util.Collection;

@Repository
public interface CalculationCompareRepository extends MongoRepository<CalculationCompareResult, String> {
    Collection<CalculationCompareResult> findByCreatedByOrderByCreatedAtDesc(String userId);
}
