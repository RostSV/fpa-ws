package sk.tuke.fpa_tool_ws.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sk.tuke.fpa_tool_ws.dto.CalculationCompareResultDto;
import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.model.CalculationCompareResult;
import sk.tuke.fpa_tool_ws.repository.CalculationCompareRepository;
import sk.tuke.fpa_tool_ws.security.CurrentUserService;
import sk.tuke.fpa_tool_ws.service.CompareService;
import sk.tuke.fpa_tool_ws.service.ExcelReaderService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompareServiceImpl implements CompareService {

    private final ExcelReaderService excelReaderService;
    private final CurrentUserService currentUserService;
    private final CalculationCompareRepository calculationCompareRepository;

    public CompareServiceImpl(ExcelReaderService excelReaderService, CurrentUserService currentUserService, CalculationCompareRepository calculationCompareRepository) {
        this.excelReaderService = excelReaderService;
        this.currentUserService = currentUserService;
        this.calculationCompareRepository = calculationCompareRepository;
    }

    /**
     * Reads calculation data from exactly two files and compares them for similarity.
     *
     * @param files An array of MultipartFile objects. Must contain exactly two files.
     * @return A similarity score between 0.0 and 1.0.
     * @throws IOException              if there's an error reading the files.
     * @throws IllegalArgumentException if the number of files is not exactly two.
     */
    @Override
    public double compareFiles(MultipartFile[] files) throws IOException {
        if (files == null || files.length != 2) {
            throw new IllegalArgumentException("Exactly two files are required for comparison.");
        }

        List<List<CalculationDto>> groupsOfCalculations = excelReaderService.readCalculationsFromFiles(files);

        if (groupsOfCalculations == null || groupsOfCalculations.size() != 2) {
            throw new IllegalStateException("ExcelReaderService did not return exactly two groups of calculations.");
        }

        List<CalculationDto> group1 = groupsOfCalculations.get(0);
        List<CalculationDto> group2 = groupsOfCalculations.get(1);

        double similarity = compareCalculationGroups(group1, group2);
        similarity = Math.round(similarity * 100.0);

        CalculationCompareResult compareResult = new CalculationCompareResult();
        compareResult.setFile1Name(files[0].getOriginalFilename());
        compareResult.setFile2Name(files[1].getOriginalFilename());
        compareResult.setSimilarity(similarity);

        calculationCompareRepository.save(compareResult);

        return similarity;
    }

    @Override
    public Collection<CalculationCompareResultDto> getCompareHistory() {
        return calculationCompareRepository.findByCreatedBy(currentUserService.getUserId()).stream()
                .map(compareResult -> new CalculationCompareResultDto(
                        compareResult.getFile1Name(),
                        compareResult.getFile2Name(),
                        compareResult.getSimilarity(),
                        Date.from(compareResult.getCreatedAt())))
                .collect(Collectors.toList());
    }

    /**
     * Compares two groups of calculations based on their values using Jaccard similarity.
     * Calculates the average best-match similarity for items in group1 compared to group2.
     *
     * @param group1 List of CalculationDto for the first group.
     * @param group2 List of CalculationDto for the second group.
     * @return Overall similarity score (0.0 to 1.0).
     */
    public double compareCalculationGroups(List<CalculationDto> group1, List<CalculationDto> group2) {

        List<List<String>> group1Values = extractValueLists(group1);
        List<List<String>> group2Values = extractValueLists(group2);

        if (group1Values.isEmpty() && group2Values.isEmpty()) {
            return 1.0;
        }
        if (group1Values.isEmpty() || group2Values.isEmpty()) {
            return 0.0;
        }

        double totalBestMatchSimilarity = 0.0;

        for (List<String> calc1Values : group1Values) {
            double maxSimilarityForCalc1 = 0.0;
            for (List<String> calc2Values : group2Values) {
                double currentSimilarity = calculateJaccardSimilarity(calc1Values, calc2Values);
                if (currentSimilarity > maxSimilarityForCalc1) {
                    maxSimilarityForCalc1 = currentSimilarity;
                }
            }
            totalBestMatchSimilarity += maxSimilarityForCalc1;
        }

        return totalBestMatchSimilarity / group1Values.size();
    }

    /**
     * Calculates the Jaccard similarity between two lists of strings.
     * Jaccard Index = |Intersection| / |Union|
     *
     * @param values1 First list of values.
     * @param values2 Second list of values.
     * @return Similarity score (0.0 to 1.0).
     */
    private double calculateJaccardSimilarity(List<String> values1, List<String> values2) {
        if (values1 == null && values2 == null) return 1.0;
        if (values1 == null || values2 == null) return 0.0;

        Set<String> set1 = new HashSet<>(values1);
        Set<String> set2 = new HashSet<>(values2);

        if (set1.isEmpty() && set2.isEmpty()) {
            return 1.0;
        }
        if (set1.isEmpty() || set2.isEmpty()) {
            return 0.0;
        }

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        return (double) intersection.size() / union.size();
    }

    /**
     * Extracts lists of value strings from a list of CalculationDto objects.
     *
     * @param calculationGroup A list of CalculationDto objects.
     * @return A list where each inner list contains the string values from one CalculationDto.
     */
    private List<List<String>> extractValueLists(List<CalculationDto> calculationGroup) {
        if (calculationGroup == null) {
            return new ArrayList<>();
        }
        return calculationGroup.stream()
                .map(calcData -> {
                    if (calcData == null || calcData.getValues() == null) {
                        return new ArrayList<String>(); // Return explicitly typed empty list
                    }
                    return calcData.getValues().stream()
                            .filter(cv -> cv != null && cv.getValue() != null)
                            .map(cv -> String.valueOf(cv.getValue()))
                            .collect(Collectors.toList());
                })
                .collect(Collectors.toList());
    }
}