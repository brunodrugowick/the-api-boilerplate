package dev.drugowick.theapiboilerplate.api.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts model to domain objects
 *
 * @param <T> the domain object.
 * @param <U> the model object.
 * @param <V> the input object to copy data from (to the domain object).
 */
@Component
public class GenericMapper<T, U, V> {

    private final ModelMapper mapper;

    public GenericMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public T toDomain(Object object, Class<T> type) {
        return mapper.map(object, type);
    }

    public U toModel(T domain, Class<U> type) {
        return mapper.map(domain, type);
    }

    public void copyToDomainObject(V inputObject, T domainObject) {
        mapper.map(inputObject, domainObject);
    }

    public List<U> toCollectionModel(List<T> domainList, Class<U> type) {
        return domainList.stream()
                .map(t -> toModel(t, type))
                .collect(Collectors.toList());
    }

}
