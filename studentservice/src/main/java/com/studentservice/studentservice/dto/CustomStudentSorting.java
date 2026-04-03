package com.studentservice.studentservice.dto;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomStudentSorting {

    public Sort sorting(String sortBy, String sortDir) {

        // Values allowed for sortBy
        List<String> sortByValidation = List.of("stuId", "stuName", "stuAddress");

        //Extracting sortby and sortDir multiple values in the list to apply multiple sorting technique
        List<String> sortingByValues = Arrays.asList(sortBy.split(","));
        List<String> sortingDirectionValues = Arrays.asList(sortDir.split(","));

        //Creating Empty sort
        Sort sort = Sort.unsorted();

        for (int i = 0; i < sortingByValues.size(); i++) {

            String sortingBy = sortingByValues.get(i);

            if (!sortByValidation.contains(sortingBy)) {
                continue;
            }

            String sortingDirection = (sortingDirectionValues.size() > i)
                    ? sortingDirectionValues.get(i)
                    : "asc";  // Default Sorting direction

            if (!sortingDirection.equalsIgnoreCase("asc") &&
                    !sortingDirection.equalsIgnoreCase("desc")) {
                sortingDirection = "asc";
            }

            Sort tempSort = (sortingDirection.equalsIgnoreCase("asc"))
                    ? Sort.by(sortingBy).ascending()
                    : Sort.by(sortingBy).descending();

            sort = sort.and(tempSort);
        }

        return sort;
    }
}
