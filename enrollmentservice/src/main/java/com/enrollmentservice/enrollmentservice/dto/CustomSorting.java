package com.enrollmentservice.enrollmentservice.dto;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomSorting {

    public Sort customSorting(String sortBy, String sortDir) {
        List<String> validateSortBy = List.of("enrollmentId", "courseId", "studentId", "status");

        // Extracting multiple values of sortBy and sortDir in list

        List<String> sortByValues = Arrays.asList(sortBy.split(","));
        List<String> sortingDirections = Arrays.asList(sortDir.split(","));

        //Sorting technique
        Sort sort = Sort.unsorted();

        for (int i = 0; i < sortByValues.size(); i++) {

            String sortingBy = sortByValues.get(i);

            if (!validateSortBy.contains(sortingBy))
                continue;

            String sortingDirection = (sortingDirections.size() > i)
                    ? sortingDirections.get(i)
                    : "asc";   // Default Value for sorting direction

            Sort sortTemp = ("asc".equalsIgnoreCase(sortingDirection))
                    ? Sort.by(sortingBy).ascending()
                    : Sort.by(sortingBy).descending();

            sort = sort.and(sortTemp);

        }
        return sort;
    }
}
