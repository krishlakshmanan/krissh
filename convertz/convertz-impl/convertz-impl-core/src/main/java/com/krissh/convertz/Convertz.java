package com.krissh.convertz;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Convertz implements IConvertz {
	
	Logger logger = LoggerFactory.getLogger(Convertz.class);
	
    /**
     * To avoid the bi-directional link between the entity.
     */
    private final List<String> tempClass = new ArrayList<String>();
    
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

	public Object ctrlC(Object src, Object dest) {
        if (logger.isDebugEnabled()) {
            logger.debug("ctrlC(Object, Object) - start");
        }
        try {
            String className = getClassName(src.getClass());
            if (!getTempClass().contains(className)) {
                getTempClass().add(className);
                if (dest != null) {
                    Field[] dtoFields = dest.getClass().getDeclaredFields();
                    for (Field dtoField : dtoFields) {
                        try {
                            if (!Modifier.isFinal(dtoField.getModifiers())) {
                                String dtoFieldName = dtoField.getName();
                                Field entityField = src.getClass().getDeclaredField(dtoFieldName);
                                if (entityField != null) {
                                    if (!dtoField.isAccessible()) {
                                        dtoField.setAccessible(true);
                                    }
                                    if (!entityField.isAccessible()) {
                                        entityField.setAccessible(true);
                                    }
                                    if (Collection.class.isAssignableFrom(dtoField.getType())) {
                                        dtoField.set(dest, ctrlAllC(entityField.get(src), getGenericTypeOfList(dtoField)));
                                    }
                                    else if (entityField.getType().isAssignableFrom(dtoField.getType())) {
                                        dtoField.set(dest, entityField.get(src));
                                    }
                                    else if (entityField.getName().equals(dtoField.getName())) {
                                        dtoField.set(dest, ctrlC(entityField.get(src), ((Class<?>) dtoField.getType()).newInstance()));
                                    }
                                }
                            }
                        }
                        catch (NoSuchFieldException e) {

                        }
                        catch (Exception e) {
                            logger.error("toDto(Object, Object) - " + e);
                        }
                    }
                }
                getTempClass().remove(className);
            }
        }
        catch (Exception e) {
            logger.error("ctrlC(Object, Object) -- " + e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("ctrlC(Object, Object) - end");
        }
		return null;
	}
    private Object getGenericTypeOfList(Field field) throws InstantiationException, IllegalAccessException {
        // TODO Auto-generated method stub
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        Class<?> v1 = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        Object object = v1.newInstance();
        return object;
    }
    /**
     * Retreive the name of the class
     * 
     * @param Class
     *            - to get the name
     * @return name of the class returns String
     * @throws
     */
    public String getClassName(Class<? extends Object> c) {
        // TODO Auto-generated method stub
        String className = c.getName();
        int firstChar;
        firstChar = className.lastIndexOf('.') + 1;
        if (firstChar > 0) {
            className = className.substring(firstChar);
        }
        return className;
    }
    
    public <U, T> List<U> ctrlAllC(Object entityCollections, Object dtoObject) {
        if (logger.isDebugEnabled()) {
            logger.debug("ctrlAllC(Object, Object) - start");
        }
        if (entityCollections != null) {
            List<T> entitiesList;
            List<U> dtosList = new ArrayList<U>();
            try {
                if (List.class.isAssignableFrom(entityCollections.getClass())) {
                    entitiesList = (List<T>) entityCollections;
                    for (T entity : entitiesList) {
                        @SuppressWarnings("unchecked")
                        U temp = (U) ctrlC(entity, dtoObject);
                        if (temp != null) {
                            dtosList.add(temp);
                        }
                        else {
                            break;
                        }
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug("ctrlAllC(Object, Object) - end");
                    }
                    return dtosList;
                }
            }
            catch (Exception e) {
                logger.error("ctrlAllC(Object, Object) " + e);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("ctrlAllC(Object, Object) - end");
        }
        return null;
    }
}
