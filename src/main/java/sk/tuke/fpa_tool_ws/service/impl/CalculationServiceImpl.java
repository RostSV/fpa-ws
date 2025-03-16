package sk.tuke.fpa_tool_ws.service.impl;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.dto.CalculationGroupDto;
import sk.tuke.fpa_tool_ws.enums.CalculationGroupType;
import sk.tuke.fpa_tool_ws.enums.CalculationSourceType;
import sk.tuke.fpa_tool_ws.enums.CalculationType;
import sk.tuke.fpa_tool_ws.exception.CalculationNotFoundException;
import sk.tuke.fpa_tool_ws.exception.DuplicateValueException;
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
import java.util.Optional;


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
        return CalculationMapper.toCalculationDtoCollection(calculationRepository.findByCreatedByAndSourceType(currentUserService.getUserId(), CalculationSourceType.CREATED));
    }

    @Override
    public CalculationGroup createGroupCalculation(Info calculationInfo, CalculationGroupType type) {
        CalculationGroup calculationGroup = new CalculationGroup();
        calculationGroup.setName(calculationInfo.getName());
        calculationGroup.setDescription(calculationInfo.getDescription());
        calculationGroup.setType(type);

        try{
            calculationGroupRepository.save(calculationGroup);
        }catch(DuplicateKeyException e){
            throw new DuplicateValueException("Group with name " + calculationGroup.getName() + " already exists");
        }

        return calculationGroup;
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
                    calculationDto.setType(CalculationType.NONE);
                    calculationDto.setSourceType(CalculationSourceType.IMPORTED);
                })
                .toList();
        createGroupWithCalculations(calculationInfo,  CalculationGroupType.IMPORTED, importedCalculations);
    }

    @Override
    public Collection<CalculationGroupDto> getCalculationsGroups() {
        return CalculationGroupMapper.toDto(calculationGroupRepository.findByCreatedBy(currentUserService.getUserId()));
    }

    @Override
    public Collection<CalculationDto> getImportedCalculations() {
        return CalculationMapper.toCalculationDtoCollection(calculationRepository.findByCreatedByAndSourceType(currentUserService.getUserId(), CalculationSourceType.IMPORTED));
    }

    @Override
    public void updateCalculation(CalculationDto dto) {
        Optional<Calculation> existingCalculation = calculationRepository.findById(dto.getId());

        if (existingCalculation.isEmpty()) {
            throw new CalculationNotFoundException("Calculation not found");
        }

        Calculation calculation = existingCalculation.get();
        calculation.setName(dto.getName());
        calculation.setDescription(dto.getDescription());

        calculationRepository.save(calculation);
    }

    @Override
    public void deleteCalculation(String id) {
        calculationRepository.deleteById(id);
    }

}
