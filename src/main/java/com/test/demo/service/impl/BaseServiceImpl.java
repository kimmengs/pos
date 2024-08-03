package com.test.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.test.demo.service.BaseService;

import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public abstract class BaseServiceImpl<T, D, ID extends Serializable> implements BaseService<T, D, ID> {
    private static final Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);

    public abstract JpaRepository<T, ID> getRepository();

    @Override
    public T create(T entity) {
        // return getRepository().save(entity);
        log.info("Creating entity: {}", entity);
        try {
            T createdEntity = getRepository().save(entity);
            log.info("Entity created: {}", createdEntity);
            return createdEntity;
        } catch (Exception e) {
            log.error("Error creating entity", e);
            throw e;
        }
    }

    @Override
    public List<T> getAll() {
        return getRepository().findAll();
    }

    @Override
    public Page<D> getAllWithPaginationAndSearch(Pageable pageable, String content) {
        // Implement the method to fetch data with pagination and search
        // This is just a placeholder and needs to be replaced with your actual
        // implementation
        Page<T> page = getRepository().findAll(pageable);
        return page.map(this::convertToDto);
    }

    @Override
    public T getById(ID id) {
        Optional<T> optionalEntity = getRepository().findById(id);
        return optionalEntity.orElse(null);
    }

    @Override
    @Transactional
    public T update(ID id, T entity) {
        T existingEntity = getRepository().findById(id).orElse(null);
        if (existingEntity != null) {
            // Update only the necessary fields
            updateEntityFields(existingEntity, entity);
            autoUpdateFields(existingEntity);

            return getRepository().save(existingEntity);
        }
        return null;
    }

    private void updateEntityFields(T existingEntity, T newEntity) {
        Field[] fields = newEntity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if ("modifiedDate".equals(field.getName())) {
                    // Set modifyDate to the current date and time
                    field.set(existingEntity, System.currentTimeMillis());
                } else if ("modNum".equals(field.getName())) {
                    // Increment modNumber instead of directly setting it
                    int currentModNumber = (int) field.get(existingEntity);
                    field.set(existingEntity, currentModNumber + 1);
                } else {
                    Object newValue = field.get(newEntity);
                    if (newValue != null) {
                        field.set(existingEntity, newValue);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void autoUpdateFields(T existingEntity) {
        try {
            // Increment modNumber
            Field modNumberField = getDeclaredField(existingEntity.getClass(), "modNum");
            modNumberField.setAccessible(true);
            int currentModNumber = (int) modNumberField.get(existingEntity);
            modNumberField.set(existingEntity, currentModNumber + 1);

            // Update modifyDate
            Field modifyDateField = getDeclaredField(existingEntity.getClass(), "modifiedDate");
            modifyDateField.setAccessible(true);
            modifyDateField.set(existingEntity, System.currentTimeMillis());

        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.info("Error updating fields: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Field getDeclaredField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    @Override
    public void delete(ID id) {
        getRepository().deleteById(id);
    }

    protected abstract D convertToDto(T entity);

}