package com.barvepan.restfulwebservices.exception;

/**
 * @author barvepan
 *
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -597093303963477603L;

    public ResourceNotFoundException(Class<?> clazz, String resourceId) {
        this(clazz.getSimpleName(), resourceId);
    }

    public ResourceNotFoundException(String resourceType, String resourceId) {
        super(String.format("The resource %s %s does not exist", resourceType, resourceId));
    }

}
