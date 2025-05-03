package sk.tuke.fpa_tool_ws.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sk.tuke.fpa_tool_ws.enums.CalculationSourceType;
import sk.tuke.fpa_tool_ws.model.Calculation;

import java.util.Collection;

@Repository
public interface CalculationRepository extends MongoRepository<Calculation, String> {

    Collection<Calculation> findByCreatedBy(String userId);

    Collection<Calculation> findByCreatedByAndSourceTypeOrderByCreatedAtDesc(String createdBy, CalculationSourceType sourceType);
    Page<Calculation> findByCreatedByAndSourceTypeOrderByCreatedAtDesc(String createdBy, CalculationSourceType sourceType, Pageable pageable);

    Collection<Calculation> findByGroupId(String groupId);

}
