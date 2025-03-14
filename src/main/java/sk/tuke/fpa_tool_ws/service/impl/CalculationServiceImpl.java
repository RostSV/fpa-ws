package sk.tuke.fpa_tool_ws.service.impl;

import org.springframework.stereotype.Service;
import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.dto.CalculationGroupDto;
import sk.tuke.fpa_tool_ws.enums.CalculationGroupType;
import sk.tuke.fpa_tool_ws.enums.CalculationType;
import sk.tuke.fpa_tool_ws.mapper.CalculationGroupMapper;
import sk.tuke.fpa_tool_ws.mapper.CalculationMapper;
import sk.tuke.fpa_tool_ws.model.Calculation;
import sk.tuke.fpa_tool_ws.model.CalculationGroup;
import sk.tuke.fpa_tool_ws.model.common.Info;
import sk.tuke.fpa_tool_ws.repository.CalculationGroupRepository;
import sk.tuke.fpa_tool_ws.repository.CalculationRepository;
import sk.tuke.fpa_tool_ws.security.CurrentUserService;
import sk.tuke.fpa_tool_ws.service.CalculationService;

import java.util.Collection;


@Service
public class CalculationServiceImpl implements CalculationService {


    private final CalculationRepository calculationRepository;
    private final CalculationGroupRepository calculationGroupRepository;
    private final CurrentUserService currentUserService;

    public CalculationServiceImpl(CalculationRepository calculationRepository, CalculationGroupRepository calculationGroupRepository, CurrentUserService currentUserService) {
        this.calculationRepository = calculationRepository;
        this.calculationGroupRepository = calculationGroupRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public void createCalculation(CalculationDto dto) {
        Calculation calculation = CalculationMapper.toEntity(dto);
        calculationRepository.save(calculation);
    }

    @Override
    public Collection<CalculationDto> getCalculationsByUserId(String userId) {

        return CalculationMapper.toCalculationDtoCollection(calculationRepository.findByCreatedBy(userId));
    }

    @Override
    public Collection<CalculationDto> getCalculationsByGroupId(String groupId) {
        return CalculationMapper.toCalculationDtoCollection(calculationRepository.findByGroupId(groupId));
    }

    @Override
    public Collection<CalculationDto> getCalculations() {
        return CalculationMapper.toCalculationDtoCollection(calculationRepository.findByCreatedBy(currentUserService.getUserId()));
    }

    @Override
    public CalculationGroup createGroupCalculation(Info calculationInfo, CalculationGroupType type) {
        CalculationGroup calculationGroup = new CalculationGroup();
        calculationGroup.setInfo(calculationInfo);
        calculationGroup.setType(type);
        return calculationGroupRepository.save(calculationGroup);
    }

    @Override
    public void saveCalculationToGroup(String groupId, CalculationDto calculationDto) {
        Calculation calculation = CalculationMapper.toEntity(calculationDto);
        calculation.setGroupId(groupId);
        calculationRepository.save(calculation);
    }

    @Override
    public void createGroupWithCalculations(Info calculationInfo, CalculationGroupType type, Collection<CalculationDto> calculations) {
        CalculationGroup group = createGroupCalculation(calculationInfo, type);
        calculations.forEach(calculationDto -> saveCalculationToGroup(group.getId(), calculationDto));
    }

    @Override
    public void importGroupWithCalculations(Info calculationInfo, Collection<CalculationDto> calculations) {
        Collection<CalculationDto> importedCalculations = calculations.stream()
                .peek(calculationDto -> {
                    calculationDto.setType(CalculationType.IMPORTED);
                })
                .toList();
        createGroupWithCalculations(calculationInfo,  CalculationGroupType.IMPORTED, importedCalculations);
    }

    @Override
    public Collection<CalculationGroupDto> getCalculationsGroups() {
        return CalculationGroupMapper.toDto(calculationGroupRepository.findByCreatedBy(currentUserService.getUserId()));
    }

}
