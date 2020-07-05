package dev.drugowick.theapiboilerplate.api.controller;

import dev.drugowick.theapiboilerplate.api.model.ExampleModel;
import dev.drugowick.theapiboilerplate.api.model.input.ExampleInput;
import dev.drugowick.theapiboilerplate.api.model.mapper.GenericMapper;
import dev.drugowick.theapiboilerplate.domain.model.Example;
import dev.drugowick.theapiboilerplate.domain.repository.ExampleRepository;
import dev.drugowick.theapiboilerplate.domain.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/examples")
@RequiredArgsConstructor
public class ExampleController {

    private final ExampleRepository exampleRepository;
    private final ExampleService exampleService;
    private final GenericMapper<Example, ExampleModel, ExampleInput> mapper;

    @GetMapping
    public List<ExampleModel> list() {
        return mapper.toCollectionModel(exampleRepository.findAll(), ExampleModel.class);
    }

    @GetMapping("/{id}")
    public ExampleModel get(@PathVariable String id) {
        Example example = exampleService.findOrElseThrow(id);
        return mapper.toModel(example, ExampleModel.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExampleModel create(@Valid @RequestBody ExampleInput exampleInput) {
        Example example = mapper.toDomain(exampleInput, Example.class);
        return mapper.toModel(exampleService.create(example), ExampleModel.class);
    }

    @PutMapping("/{id}")
    public ExampleModel update(@PathVariable String id, @Valid @RequestBody ExampleInput exampleInput) {
        Example example = exampleService.findOrElseThrow(id);
        mapper.copyToDomainObject(exampleInput, example);
        return mapper.toModel(exampleService.createOrUpdate(example), ExampleModel.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        exampleService.delete(id);
    }
}
