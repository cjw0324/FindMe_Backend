package com.findme.FindMeBack.Controller.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PaginatedSearchResultDto {
    private Long totalResults;
    private List<SearchDto> items;
}
