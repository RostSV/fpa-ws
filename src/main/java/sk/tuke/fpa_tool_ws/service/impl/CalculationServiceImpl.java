package sk.tuke.fpa_tool_ws.service.impl;

import org.springframework.stereotype.Service;
import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.enums.CalculationType;
import sk.tuke.fpa_tool_ws.mapper.CalculationMapper;
import sk.tuke.fpa_tool_ws.model.Calculation;
import sk.tuke.fpa_tool_ws.model.common.Info;
import sk.tuke.fpa_tool_ws.repository.CalculationRepository;
import sk.tuke.fpa_tool_ws.security.CurrentUserService;
import sk.tuke.fpa_tool_ws.service.CalculationService;

import java.util.Collection;


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
        calculation.setCreatedBy(currentUserService.getUserId());
        calculationRepository.save(calculation);
    }

    @Override
    public Collection<CalculationDto> getCalculationsByUserId(String userId) {

        return CalculationMapper.toCalculationDtoCollection(calculationRepository.findByCreatedBy(userId));
    }

    @Override
    public Collection<CalculationDto> getCalculations() {
        return CalculationMapper.toCalculationDtoCollection(calculationRepository.findByCreatedBy(currentUserService.getUserId()));
    }

    @Override
    public Calculation createGroupCalculation(Info calculationInfo) {
        Calculation calculation = new Calculation();
        calculation.setName(calculationInfo.getName());
        calculation.setDescription(calculationInfo.getDescription());
        calculation.setType(CalculationType.GROUP);
        calculation.setCreatedBy(currentUserService.getUserId());
        return calculationRepository.save(calculation);
    }

    @Override
    public void saveCalculationToGroup(String groupId, CalculationDto calculationDto) {
        Calculation calculation = CalculationMapper.toEntity(calculationDto);
        calculation.setGroupId(groupId);
        calculationRepository.save(calculation);
    }

    @Override
    public void createGroupWithCalculations(Info calculationInfo, Collection<CalculationDto> calculations) {
        Calculation group = createGroupCalculation(calculationInfo);
        calculations.forEach(calculationDto -> saveCalculationToGroup(group.getId(), calculationDto));
    }

}
