package com.courseservice.courseservice.dto;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomSorting {

    public Sort sorting(String sortBy, String sortDir) {
        List<String> validateSortBy = List.of("courseId", "courseName", "courseInstructorName", "coursePrice");

        // Extracting multiple fields values from Sort By and Sort Dir
        List<String> sortByValues = Arrays.asList(sortBy.split(","));
        List<String> sortDirValues = Arrays.asList(sortDir.split(","));

        System.out.println(sortByValues);
        System.out.println(sortDirValues);

        //Creating empty sort
        Sort sort = Sort.unsorted();

        for (int i = 0; i < sortByValues.size(); i++) {

            String sortingBy = sortByValues.get(i);

            System.out.println(sortingBy);

            if (!validateSortBy.contains(sortingBy)) {
                continue;
            }

            String sortingDirection = (sortDirValues.size() > i)
                    ? sortDirValues.get(i)
                    : "asc"; // Default sorting direction

            System.out.println(sortingDirection);

            if (!sortingDirection.contains("asc") && !sortingDirection.contains("desc")) {
                sortingDirection = "asc";
            }

            System.out.println(sortingDirection);

            Sort sortTemp = ("asc".equalsIgnoreCase(sortDir))
                    ? Sort.by(sortingBy).ascending()
                    : Sort.by(sortingBy).descending();

            sort = sort.and(sortTemp);
        }

        return sort;
    }
}
