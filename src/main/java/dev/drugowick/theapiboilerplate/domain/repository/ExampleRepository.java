package dev.drugowick.theapiboilerplate.domain.repository;

import dev.drugowick.theapiboilerplate.domain.model.Example;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExampleRepository extends MongoRepository<Example, String> {

}
