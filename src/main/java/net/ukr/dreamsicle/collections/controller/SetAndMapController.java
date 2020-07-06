package net.ukr.dreamsicle.collections.controller;

import lombok.RequiredArgsConstructor;
import net.ukr.dreamsicle.collections.model.dto.SetAndMapCollectionsDTO;
import net.ukr.dreamsicle.collections.service.SetAndMapService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collections")
public class SetAndMapController {
    private final SetAndMapService setAndMapService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Page<SetAndMapCollectionsDTO> getAllStatistic(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return setAndMapService.retrieveAllData(page);
    }

    @GetMapping("/{amount}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<SetAndMapCollectionsDTO> action(@PathVariable @Min(1) @Positive long amount) {
        return setAndMapService.allActionWithCollections(amount);
    }
}
