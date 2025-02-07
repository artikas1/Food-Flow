package com.accenture.foodflow.search.controller;

import com.accenture.foodflow.food.type.Category;
import com.accenture.foodflow.food.type.FoodDetails;
import com.accenture.foodflow.search.dto.response.PaginatedResponseDto;
import com.accenture.foodflow.search.dto.request.FoodSearchCriteriaDto;
import com.accenture.foodflow.search.service.SearchService;
import com.accenture.foodflow.search.type.SortOrder;
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
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto> getGames(@RequestParam(required = false) Integer limit,
                                                         @RequestParam(required = false) Integer offset,
                                                         @RequestParam(required = false) SortOrder sortOrder,
                                                         @RequestParam(required = false) String sortColumn,
                                                         @RequestParam(required = false) String search,
                                                         @RequestParam(required = false) List<Category> categories,
                                                         @RequestParam(required = false) List<FoodDetails> foodDetails) {
        return ResponseEntity.ok(searchService.getFood(new FoodSearchCriteriaDto(search, categories, foodDetails, sortColumn, sortOrder, offset, limit)));
    }

}
