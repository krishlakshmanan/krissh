package com.krissh.samples;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * This class is used to convert an entity to object
 * 
 * @author Krish Lakshmanan
 * @version 1.6
 */
public class ConvertionUtils {

    /**
     * log for this class.
     */
    private static Logger log = Logger.getLogger(ConvertionUtils.class);
    /**
     * To avoid the bi-directional link between the entity.
     */
    private final List<String> tempClass = new ArrayList<String>();

    /**
     * To retrieve the instance of the current
     */
    public ConvertionUtils() {
    }

    /**
     * Convert an entity to dto.
     * 
     * @param entity
     *            object to convert it into dto object.
     * @return dto object of an entity. returns Object
     * @throws NoSuchFieldException
     *             If entityFieldName is not matched with any of the
     *             declaredFields.
     */
    public Object toDto(Object entity) {
        // TODO Auto-generated method stub
        if (log.isDebugEnabled()) {
            log.debug("toDto(entity) - started for "
                    + entity.getClass().getName());
        }
        Object dto = null;
        try {
            String className = getClassName(entity.getClass());
            if (!getTempClass().contains(className)) {
                dto = new Object();
                StringBuilder dtoPackage = new StringBuilder("com.eServe.marketPlace.globalcommons.dto");
                dtoPackage.append(".").append(className);
                getTempClass().add(className);
                dto = Class.forName(dtoPackage.toString()).newInstance();
                Field[] entityFields = entity.getClass().getDeclaredFields();
                for (Field entityField : entityFields) {
                    try {
                        String entityFieldName = entityField.getName();
                        Field dtoField = dto.getClass().getDeclaredField(entityFieldName);
                        if (dtoField != null) {
                            if (!dtoField.isAccessible()) {
                                dtoField.setAccessible(true);
                            }
                            if (!entityField.isAccessible()) {
                                entityField.setAccessible(true);
                            }
                            if (Collection.class.isAssignableFrom(entityField.getType())
                                    && !(Modifier.isFinal(entityField.getModifiers()))) {
                                dtoField.set(dto, getDtoCollections(entityField.get(entity)));
                            }
                            else if (entityField.getType().isAssignableFrom(dtoField.getType())
                                    && !(Modifier.isFinal(entityField.getModifiers()))) {
                                dtoField.set(dto, entityField.get(entity));
                            }
                            else if (entityField.getName().equals(dtoField.getName())
                                    && !(Modifier.isFinal(entityField.getModifiers()))) {
                                dtoField.set(dto, toDto(entityField.get(entity)));
                            }
                        }
                    }
                    catch (NoSuchFieldException e) {
                    }
                }
                getTempClass().remove(className);
            }
        }
        catch (Exception e) {
            log.warn(" Error at toDto(entity) " + e);
        }
        if (log.isDebugEnabled()) {
            log.debug("toDto(entity) - ended for "
                    + entity.getClass().getName());
        }
        return dto;
    }

    /**
     * Convert an entity to dto , but this method convert only primitive types.
     * 
     * @param entity
     *            object to convert it into dto object.
     * @return dto object of an entity. returns Object
     * @throws NoSuchFieldException
     *             If entityFieldName is not matched with any of the
     *             declaredFields.
     */
    public Object toDtoPrimitives(Object entity) {
        // TODO Auto-generated method stub
        if (log.isDebugEnabled()) {
            log.debug("toDtoPrimitives(entity) - started for "
                    + entity.getClass().getName());
        }
        Object dto = null;
        try {
            String className = getClassName(entity.getClass());
            dto = new Object();
            StringBuilder dtoPackage = new StringBuilder("com.eServe.marketPlace.globalcommons.dto");
            dtoPackage.append(".").append(className);
            getTempClass().add(className);
            dto = Class.forName(dtoPackage.toString()).newInstance();
            Field[] entityFields = entity.getClass().getDeclaredFields();
            for (Field entityField : entityFields) {
                try {
                    String entityFieldName = entityField.getName();
                    Field dtoField = dto.getClass().getDeclaredField(entityFieldName);
                    if (dtoField != null) {
                        if (!dtoField.isAccessible()) {
                            dtoField.setAccessible(true);
                        }
                        if (!entityField.isAccessible()) {
                            entityField.setAccessible(true);
                        }
                        if (entityField.getType().isAssignableFrom(dtoField.getType())
                                && !(Modifier.isFinal(entityField.getModifiers()))
                                && !(Collection.class.isAssignableFrom(entityField.getType()))) {
                            dtoField.set(dto, entityField.get(entity));
                        }
                    }
                }
                catch (NoSuchFieldException e) {
                }
            }
        }
        catch (Exception e) {
            log.warn("Error at toDtoPrimitives(entity) " + e);
        }
        if (log.isDebugEnabled()) {
            log.debug("toDtoPrimitives(entity) - ended for "
                    + entity.getClass().getName());
        }
        return dto;
    }

    /**
     * Convert an entity to dto , but for other than lists
     * 
     * @param entity
     *            object to convert it into dto object.
     * @return dto object of an entity. returns Object
     * @throws NoSuchFieldException
     *             If entityFieldName is not matched with any of the
     *             declaredFields.
     */
    public Object toDtoOtherThanLists(Object entity) {
        if (log.isDebugEnabled()) {
            log.debug("toDtoOtherThanLists(entity) - started for "
                    + entity.getClass().getName());
        }
        Object dto = null;
        try {
            String className = getClassName(entity.getClass());
            dto = new Object();
            StringBuilder dtoPackage = new StringBuilder("com.eServe.marketPlace.globalcommons.dto");
            dtoPackage.append(".").append(className);
            getTempClass().add(className);
            dto = Class.forName(dtoPackage.toString()).newInstance();
            Field[] entityFields = entity.getClass().getDeclaredFields();
            for (Field entityField : entityFields) {
                try {
                    String entityFieldName = entityField.getName();
                    Field dtoField = dto.getClass().getDeclaredField(entityFieldName);
                    if (dtoField != null) {
                        if (!dtoField.isAccessible()) {
                            dtoField.setAccessible(true);
                        }
                        if (!entityField.isAccessible()) {
                            entityField.setAccessible(true);
                        }
                        if (entityField.getType().isAssignableFrom(dtoField.getType())
                                && !(Modifier.isFinal(entityField.getModifiers()))
                                && !(Collection.class.isAssignableFrom(entityField.getType()))) {
                            dtoField.set(dto, entityField.get(entity));
                        }
                        else if (entityField.getName().equals(dtoField.getName())
                                && !(Modifier.isFinal(entityField.getModifiers()))
                                && !(Collection.class.isAssignableFrom(entityField.getType()))) {
                            dtoField.set(dto, toDtoPrimitives(entityField.get(entity)));
                        }
                    }
                }
                catch (NoSuchFieldException e) {
                }
            }
        }
        catch (Exception e) {
            log.warn("Error at toDtoOtherThanLists(entity)" + e);
        }
        if (log.isDebugEnabled()) {
            log.debug("toDtoOtherThanLists(entity) - ended for "
                    + entity.getClass().getName());
        }
        return dto;
    }

    /**
     * Retreive the name of the class
     * 
     * @param Class
     *            - to get the name
     * @return name of the class returns String
     * @throws
     */
    private String getClassName(Class<? extends Object> c) {
        // TODO Auto-generated method stub
        String className = c.getName();
        int firstChar;
        firstChar = className.lastIndexOf('.') + 1;
        if (firstChar > 0) {
            className = className.substring(firstChar);
        }
        return className;
    }

    /**
     * Returns List of dto's object from list of entities ,for other than List
     * Type in the entity
     * 
     * @param <T>
     * @param <T>
     * @param : objectCollection - List of Entity Object
     * @return Object
     * @throws IllegalArgumentException
     *             If ArgumentType is mismatch with the expected Type
     */
    @SuppressWarnings("unchecked")
    public <T> Object getDtoCollectionsOtherThanLists(Object objectCollection) {
        if (log.isDebugEnabled()) {
            log.debug("getDtoCollectionsOtherThanLists(entity) - started for "
                    + objectCollection.getClass().getName());
        }
        if (objectCollection != null) {
            List<T> entitiesList;
            List<T> dtosList = new ArrayList<T>();
            try {
                if (List.class.isAssignableFrom(objectCollection.getClass())) {
                    entitiesList = (List<T>) objectCollection;
                    for (T entity : entitiesList) {
                        Object temp = toDtoOtherThanLists(entity);
                        if (temp != null) {
                            dtosList.add((T) temp);
                        }
                        else {
                            break;
                        }
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("getDtoCollectionsOtherThanLists(entity) - ended for "
                                + objectCollection.getClass().getName());
                    }
                    return dtosList;
                }
            }
            catch (Exception e) {
                log.warn("getDtoCollectionsOtherThanLists(entity)" + e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("getDtoCollectionsOtherThanLists(entity) - ended for "
                    + objectCollection.getClass().getName());
        }
        return null;
    }

    /**
     * Returns List of dto's object from list of entities , but for only direct
     * fields.
     * 
     * @param <T>
     * @param <T>
     * @param : objectCollection - List of Entity Object
     * @return Object
     * @throws IllegalArgumentException
     *             If ArgumentType is mismatch with the expected Type
     */
    @SuppressWarnings("unchecked")
    public <T> Object getDtoCollectionsForDirectField(Object objectCollection) {
        if (log.isDebugEnabled()) {
            log.debug("getDtoCollectionsForDirectField(entity) - started for "
                    + objectCollection.getClass().getName());
        }
        if (objectCollection != null) {
            List<T> entitiesList;
            List<T> dtosList = new ArrayList<T>();
            try {
                if (List.class.isAssignableFrom(objectCollection.getClass())) {
                    entitiesList = (List<T>) objectCollection;
                    for (T entity : entitiesList) {
                        Object temp = toDtoPrimitives(entity);
                        if (temp != null) {
                            dtosList.add((T) temp);
                        }
                        else {
                            break;
                        }
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("getDtoCollectionsForDirectField(entity) - ended for "
                                + objectCollection.getClass().getName());
                    }
                    return dtosList;
                }
            }
            catch (Exception e) {
                log.warn("Error at getDtoCollectionsForDirectField(entity)" + e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("getDtoCollectionsForDirectField(entity) - ended for "
                    + objectCollection.getClass().getName());
        }
        return null;
    }

    /**
     * Returns List of dto's object from list of entities.
     * 
     * @param <T>
     * @param <T>
     * @param : objectCollection - List of Entity Object
     * @return Object
     * @throws IllegalArgumentException
     *             If ArgumentType is mismatch with the expected Type
     */
    @SuppressWarnings("unchecked")
    public <T> Object getDtoCollections(Object objectCollection) {
        if (log.isDebugEnabled()) {
            log.debug("getDtoCollections(entity) - started for "
                    + objectCollection.getClass().getName());
        }
        if (objectCollection != null) {
            List<T> entitiesList;
            List<T> dtosList = new ArrayList<T>();
            try {
                if (List.class.isAssignableFrom(objectCollection.getClass())) {
                    entitiesList = (List<T>) objectCollection;
                    for (T entity : entitiesList) {
                        Object temp = toDto(entity);
                        if (temp != null) {
                            dtosList.add((T) temp);
                        }
                        else {
                            break;
                        }
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("getDtoCollections(entity) - ended for "
                                + objectCollection.getClass().getName());
                    }
                    return dtosList;
                }
            }
            catch (Exception e) {
                log.warn("Error at getDtoCollections(entity)" + e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("getDtoCollections(entity) - ended for "
                    + objectCollection.getClass().getName());
        }
        return null;
    }

    /**
     * 
     * Returns the dto field based on the entity field name.
     * 
     * @param declaredFields
     *            - Declared Fields of Dto Object.
     * @param entityFieldName
     *            -name of the field to get a field the List Of Fields.
     * @return Field - dto field of the appropriate entity field name.
     */
    private Field getField(Field[] declaredFields, String entityFieldName) throws NoSuchFieldException {
        // TODO Auto-generated method stub
        for (Field field : declaredFields) {
            if (entityFieldName.equals(field.getName())) {
                if (!field.isAccessible())
                    field.setAccessible(true);
                return field;
            }

        }
        return null;
    }

    /**
     * 
     * @param returns
     *            of the tempClass of the current instance
     * @return List Of String returns List<String>
     * 
     */
    private List<String> getTempClass() {
        return tempClass;
    }

    /**
     * @param
     * @return returns Object
     * @throws
     */
    public Object copyDirectFieldsIfNotNull(Object srcObject, Object destObject) {
        if (log.isDebugEnabled()) {
            log.debug("copyDirectFieldsIfNotNull(Object srcObject, Object destObject) - started for "
                    + srcObject.getClass().getName());
        }
        try {
            Field[] srcFields = srcObject.getClass().getDeclaredFields();
            for (Field srcField : srcFields) {
                try {
                    String srcFieldName = srcField.getName();
                    Field destField = destObject.getClass().getDeclaredField(srcFieldName);
                    if (destField != null) {
                        if (!destField.isAccessible()) {
                            destField.setAccessible(true);
                        }
                        if (!srcField.isAccessible()) {
                            srcField.setAccessible(true);
                        }
                        if (srcField.getType().isAssignableFrom(destField.getType())
                                && !(Modifier.isFinal(srcField.getModifiers()))
                                && !(Collection.class.isAssignableFrom(srcField.getType()))) {
                            if (srcField.get(srcObject) != null)
                                destField.set(destObject, srcField.get(srcObject));
                        }
                    }
                }
                catch (Exception e) {
                    log.warn("Error at copyDirectFieldsIfNotNull(Object srcObject, Object destObject) "
                            + e);
                }
            }
        }
        catch (Exception e) {
            log.warn("Error at copyDirectFieldsIfNotNull(Object srcObject, Object destObject) "
                    + e);
        }
        if (log.isDebugEnabled()) {
            log.debug("copyDirectFieldsIfNotNull(Object srcObject, Object destObject) - ended for "
                    + srcObject.getClass().getName());
        }
        return destObject;
    }

    public <U, T> List<U> getDtoCollections(Object entityCollections, Object dtoObject) {
        if (log.isDebugEnabled()) {
            log.debug("getDtoCollections(Object, Object) - start");
        }
        if (entityCollections != null) {
            List<T> entitiesList;
            List<U> dtosList = new ArrayList<U>();
            try {
                if (List.class.isAssignableFrom(entityCollections.getClass())) {
                    entitiesList = (List<T>) entityCollections;
                    for (T entity : entitiesList) {
                        @SuppressWarnings("unchecked")
                        U temp = (U) toDto(entity, dtoObject);
                        if (temp != null) {
                            dtosList.add(temp);
                        }
                        else {
                            break;
                        }
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("getDtoCollections(Object, Object) - end");
                    }
                    return dtosList;
                }
            }
            catch (Exception e) {
                log.error("getDtoCollections(Object, Object) " + e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("getDtoCollections(Object, Object) - end");
        }
        return null;
    }

    /**
     * Convert an entity to dto.
     * 
     * @param <U>
     * 
     * @param entity
     *            object to convert it into dto object.
     * @return dto object of an entity. returns Object
     * @throws NoSuchFieldException
     *             If entityFieldName is not matched with any of the
     *             declaredFields.
     */
    @SuppressWarnings("unchecked")
    public Object toDto(Object entity, Object dtoObject) {
        if (log.isDebugEnabled()) {
            log.debug("toDto(Object, Object) - start");
        }
        Object dto = null;
        try {
            String className = getClassName(entity.getClass());
            if (!getTempClass().contains(className)) {
                getTempClass().add(className);
                if (dtoObject != null) {
                    dto = dtoObject.getClass().newInstance();
                    Field[] dtoFields = dto.getClass().getDeclaredFields();
                    for (Field dtoField : dtoFields) {
                        try {
                            if (!Modifier.isFinal(dtoField.getModifiers())) {
                                String dtoFieldName = dtoField.getName();
                                Field entityField = entity.getClass().getDeclaredField(dtoFieldName);
                                if (entityField != null) {
                                    if (!dtoField.isAccessible()) {
                                        dtoField.setAccessible(true);
                                    }
                                    if (!entityField.isAccessible()) {
                                        entityField.setAccessible(true);
                                    }
                                    if (Collection.class.isAssignableFrom(dtoField.getType())) {
                                        dtoField.set(dto, getDtoCollections(entityField.get(entity), getGenericTypeOfList(dtoField)));
                                    }
                                    else if (entityField.getType().isAssignableFrom(dtoField.getType())) {
                                        dtoField.set(dto, entityField.get(entity));
                                    }
                                    else if (entityField.getName().equals(dtoField.getName())) {
                                        dtoField.set(dto, toDto(entityField.get(entity), ((Class<?>) dtoField.getType()).newInstance()));
                                    }
                                }
                            }
                        }
                        catch (NoSuchFieldException e) {

                        }
                        catch (Exception e) {
                            log.error("toDto(Object, Object) - " + e);
                        }
                    }
                }
                getTempClass().remove(className);
            }
        }
        catch (Exception e) {
            log.error("toDto(Object, Object) -- " + e);
        }
        if (log.isDebugEnabled()) {
            log.debug("toDto(Object, Object) - end");
        }
        return dto;
    }

    private Object getGenericTypeOfList(Field field) throws InstantiationException, IllegalAccessException {
        // TODO Auto-generated method stub
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        Class<?> v1 = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        Object object = v1.newInstance();
        return object;
    }
}