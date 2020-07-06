package net.ukr.dreamsicle.collections.controller;

import lombok.RequiredArgsConstructor;
import net.ukr.dreamsicle.collections.model.dto.ArrayAndLinkedListDTO;
import net.ukr.dreamsicle.collections.service.ArrayAndLinkedListService;
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
@RequestMapping("/lists")
public class ArrayAndLinkedListController {
    private final ArrayAndLinkedListService arrayAndLinkedListService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Page<ArrayAndLinkedListDTO> getAllStatistic(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return arrayAndLinkedListService.retrieveAllData(page);
    }

    @GetMapping("/{amount}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ArrayAndLinkedListDTO> retrieve(@PathVariable @Min(1) @Positive long amount) {
        return arrayAndLinkedListService.retrieve(amount);
    }

    @PostMapping("/{amount}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ArrayAndLinkedListDTO> insert(@PathVariable @Min(1) @Positive long amount) {
        return arrayAndLinkedListService.insert(amount);
    }

    @PutMapping("/{amount}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ArrayAndLinkedListDTO> update(@PathVariable @Min(1) @Positive long amount) {
        return arrayAndLinkedListService.update(amount);
    }

    @DeleteMapping("/{amount}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ArrayAndLinkedListDTO> delete(@PathVariable @Min(1) @Positive long amount) {
        return arrayAndLinkedListService.delete(amount);
    }
}
