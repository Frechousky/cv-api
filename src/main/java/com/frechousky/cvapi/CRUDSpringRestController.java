package com.frechousky.cvapi;


import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Basic REST controller with create, find all, find by id, update and delete methods for an entity of class <T>.
 * Subclass must have @RestController annotation.
 *
 * @param <T> Entity class to generate CRUD REST controller
 */
public abstract class CRUDSpringRestController<T> {

    protected abstract JpaRepository<T, Integer> getRepository();

    /**
     * Generates URI to access entity after creation.
     *
     * @param entity
     * @return URI to access entity after creation
     */
    protected URI onCreateBuildEntityLocationURI(T entity) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(getEntityId(entity))
                .toUri();
        return location;
    }

    /**
     * Build the entity to delete with repository.delete.
     *
     * @param id
     * @return entity to be deleted
     */
    protected abstract T onDeleteBuildEntityToDelete(Integer id);

    /**
     * Method called before updating an entity to database, can be used to set newEntity.id for example.
     *
     * @param urlPathId id of entity to update from URL path
     * @param newEntity entity with updates sent by client
     * @param oldEntity entity to update retrieved from database
     * @throws MethodArgumentNotValidException if newEntiy.id is not null AND urlPathId != newEntity.id
     */
    protected void onUpdateBeforeSave(Integer urlPathId, T newEntity, T oldEntity) throws MethodArgumentNotValidException {
        Integer newEntityId = getEntityId(newEntity);

        // Make sure id from object is same as id from URL path
        if (newEntityId != null && !urlPathId.equals(newEntityId)) {
            BindingResult br = new BeanPropertyBindingResult(newEntity, "entity");
            ObjectError oe = new ObjectError("entity", String.format("Id in URL and in JSON object do not match (ID from url: %s, ID from JSON object: %s)", urlPathId, newEntityId));
            br.addError(oe);
            throw new MethodArgumentNotValidException(null, br);
        }

        // newEntity id may be null, we update newEntity id before saving
        // saving newEntity to database will replace oldEntity
        setEntityId(newEntity, urlPathId);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Validated T entity) {
        entity = getRepository().save(entity);
        URI location = onCreateBuildEntityLocationURI(entity);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<T> entities = getRepository().findAll();
        if (!entities.isEmpty()) {
            return ResponseEntity.ok(entities);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity findById(@PathVariable("id") Integer id) {
        Optional<T> entityOptional = getRepository().findById(id);
        if (entityOptional.isPresent()) {
            return ResponseEntity.ok(entityOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) throws MethodArgumentNotValidException {
        T toDelete = onDeleteBuildEntityToDelete(id);
        getRepository().delete(toDelete);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public void deleteWithoutId() throws NoSuchMethodException, MissingPathVariableException {
        MethodParameter mp = new MethodParameter(
                CRUDSpringRestController.class.getMethod("delete", new Class[] { Integer.class }), 0);
        throw new MissingPathVariableException("id", mp);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody @Validated T newEntity) throws MethodArgumentNotValidException {
        T oldEntity = getRepository().findById(id).orElse(null);
        if (oldEntity == null) {
            return ResponseEntity.notFound().build();
        }
        onUpdateBeforeSave(id, newEntity, oldEntity);
        getRepository().save(newEntity);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public void updateWithoutId() throws NoSuchMethodException, MissingPathVariableException {
        MethodParameter mp = new MethodParameter(
                CRUDSpringRestController.class.getMethod("update", new Class[] { Integer.class, Object.class }), 0);
        throw new MissingPathVariableException("id", mp);
    }

    @Nullable
    public abstract Integer getEntityId(T entity);

    public abstract void setEntityId(T entity, Integer id);

}
