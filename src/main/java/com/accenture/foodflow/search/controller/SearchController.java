package com.accenture.foodflow.search.controller;

import com.accenture.foodflow.food.type.Category;
import com.accenture.foodflow.food.type.FoodDetails;
import com.accenture.foodflow.search.dto.response.PaginatedResponseDto;
import com.accenture.foodflow.search.dto.request.FoodSearchCriteriaDto;
import com.accenture.foodflow.search.service.SearchService;
import com.accenture.foodflow.search.type.SortOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/search")
@Tag(name = "Search Controller", description = "APIs for searching food items based on various criteria")
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "Search for food items", description = "Retrieves a paginated list of food items based on search criteria")
    @GetMapping
    public ResponseEntity<PaginatedResponseDto> getGames(
            @Parameter(description = "Maximum number of results to return") @RequestParam(required = false) Integer limit,
            @Parameter(description = "Offset for pagination") @RequestParam(required = false) Integer offset,
            @Parameter(description = "Sorting order (ASC or DESC)") @RequestParam(required = false) SortOrder sortOrder,
            @Parameter(description = "Column to sort by") @RequestParam(required = false) String sortColumn,
            @Parameter(description = "Search query string") @RequestParam(required = false) String search,
            @Parameter(description = "List of categories to filter by") @RequestParam(required = false) List<Category> categories,
            @Parameter(description = "List of food details to filter by") @RequestParam(required = false) List<FoodDetails> foodDetails) {
        return ResponseEntity.ok(searchService.getFood(new FoodSearchCriteriaDto(search, categories, foodDetails, sortColumn, sortOrder, offset, limit)));
    }

}