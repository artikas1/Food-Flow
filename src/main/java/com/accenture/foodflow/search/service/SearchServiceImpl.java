package com.accenture.foodflow.search.service;

import com.accenture.foodflow.food.dao.FoodDao;
import com.accenture.foodflow.food.entity.Food;
import com.accenture.foodflow.search.dto.response.PaginatedResponseDto;
import com.accenture.foodflow.search.dto.request.FoodSearchCriteriaDto;
import com.accenture.foodflow.food.dto.FoodResponseDto;
import com.accenture.foodflow.food.mapper.FoodMapper;
import com.accenture.foodflow.search.type.SortOrder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final FoodDao foodDao;
    private final FoodMapper foodMapper;

    @Override
    public PaginatedResponseDto getFood(FoodSearchCriteriaDto criteria) {
        Pageable pageable = createPageable(criteria);
        Specification<Food> specification = createFoodSpecification(criteria);

        Page<Food> foodPage = foodDao.findAll(specification, pageable);
        List<FoodResponseDto> dtoList = foodMapper.toDto(foodPage.getContent());

        return new PaginatedResponseDto(
                dtoList, foodPage.getNumber() + 1, foodPage.getTotalPages(), foodPage.getTotalElements());
    }

    private Pageable createPageable(FoodSearchCriteriaDto criteria) {
        int pageNumber = Optional.ofNullable(criteria.getOffset()).orElse(0);
        int pageSize = Optional.ofNullable(criteria.getLimit()).orElse(10);
        Sort sort = determineSortOrder(criteria);
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    private Sort determineSortOrder(FoodSearchCriteriaDto criteria) {
        String sortBy = Optional.ofNullable(criteria.getSortBy()).orElse("createdAt");
        Sort.Direction direction = criteria.getSortOrder() == SortOrder.DESC ? Sort.Direction.DESC : Sort.Direction.ASC;

        List<String> validSortFields = Arrays.asList("expiryDate", "createdAt");
        if (!validSortFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }

        return Sort.by(direction, sortBy);
    }

    private Specification<Food> createFoodSpecification(FoodSearchCriteriaDto criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(criteria.getSearch())) {
                String[] words = criteria.getSearch().toLowerCase().split("\\s+");
                List<Predicate> wordPredicates = new ArrayList<>();

                for (String word : words) {
                    Predicate titlePredicate = cb.like(cb.lower(root.get("title")), "%" + word + "%");
                    Predicate descPredicate = cb.like(cb.lower(root.get("description")), "%" + word + "%");
                    wordPredicates.add(cb.or(titlePredicate, descPredicate));
                }
                predicates.add(cb.and(wordPredicates.toArray(new Predicate[0])));
            }

            if (criteria.getCategories() != null && !criteria.getCategories().isEmpty()) {
                predicates.add(root.get("category").in(criteria.getCategories()));
            }

            if (criteria.getFoodDetails() != null && !criteria.getFoodDetails().isEmpty()) {
                predicates.add(root.get("foodDetails").in(criteria.getFoodDetails()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}