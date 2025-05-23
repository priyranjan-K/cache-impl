package com.sample.cache_impl.util;

public class Constants {

    public final static String EMPLOYEE = "EMPLOYEE";
    public final static String BASE_CONTEXT_PATH = "/";
    public final static String FETCH_ALL = "fetchAll";
    public final static String SAVE = "save";
    public final static String FIND_BY_NAME = "getByName";
    public final static String DELETE_BY_FULL_NAME = "deleteByFullName";
    public final static String CLEAR_CACHE = "/clearCache";


    //Error Message
    public final static String INSERT_ERROR = "Unable to insert employee record.";
    public final static String UPDATE_ERROR = "Unable to update employee record.";
    public final static String DELETE_ERROR = "Unable to delete employee record.";
    public final static String FETCH_ERROR = "Unable to fetch employee record.";
    public final static String REDIS_CLEAN_MESSAGE = "Successfully clear the redis cache.";
    public final static String REDIS_UNCLEAN_MESSAGE = "Unable to clear the redis cache.";


    //ERROR CODE
    public final static String INSERT_ERROR_CODE = "ERR-001";
    public final static String UPDATE_ERROR_CODE = "ERR-002";
    public final static String DELETE_ERROR_CODE = "ERR-003";
    public final static String FETCH_ERROR_CODE = "ERR-004";


    //SUCCESS MESSAGE
    public final static String UPDATED_SUCCESSFULLY = "Record updated successfully.";
    public final static String DELETED_SUCCESSFULLY = "Record deleted successfully.";
    public final static String INSERTED_SUCCESSFULLY = "Record saved successfully.";
    public final static String RECORD_NOT_FOUND = "Record does not exist.";

}
