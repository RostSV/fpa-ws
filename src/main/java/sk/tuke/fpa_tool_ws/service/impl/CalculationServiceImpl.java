package sk.tuke.fpa_tool_ws.service.impl;

import org.springframework.stereotype.Service;
import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.mapper.CalculationMapper;
import sk.tuke.fpa_tool_ws.model.Calculation;
import sk.tuke.fpa_tool_ws.repository.CalculationRepository;
import sk.tuke.fpa_tool_ws.security.CurrentUserService;
import sk.tuke.fpa_tool_ws.service.CalculationService;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@Service
public class CalculationServiceImpl implements CalculationService {


    private final CalculationRepository calculationRepository;
    private final CurrentUserService currentUserService;

    public CalculationServiceImpl(CalculationRepository calculationRepository, CurrentUserService currentUserService) {
        this.calculationRepository = calculationRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public void createCalculation(CalculationDto dto) {
        Calculation calculation = CalculationMapper.toEntity(dto);
        calculation.setId(UUID.randomUUID());
        calculation.setCreatedAt(Instant.now());
        calculation.setCreatedBy(UUID.fromString(currentUserService.getUserId()));
        calculationRepository.save(calculation);
    }

    @Override
    public Collection<CalculationDto> getCalculationsByUserId(String userId) {

        return CalculationMapper.toCalculationDtoCollection(calculationRepository.findByCreatedBy(UUID.fromString(userId)));
    }

    @Override
    public Collection<CalculationDto> getCalculations() {
        return CalculationMapper.toCalculationDtoCollection(calculationRepository.findByCreatedBy(UUID.fromString(currentUserService.getUserId())));
    }
}
