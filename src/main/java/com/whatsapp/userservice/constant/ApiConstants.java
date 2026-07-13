package com.whatsapp.userservice.constant;

public final class ApiConstants {

    public static final String API_BASE_PATH = "/api/v1/users";
    public static final String API_CONTACTS_PATH = API_BASE_PATH + "/contacts";
    public static final String API_PRIVACY_PATH = API_BASE_PATH + "/privacy";
    public static final String API_BLOCK_PATH = API_BASE_PATH + "/block";
    public static final String API_BLOCKED_PATH = API_BASE_PATH + "/blocked";
    public static final String API_SEARCH_PATH = API_BASE_PATH + "/search";
    public static final String API_ACTIVATE_PATH = API_BASE_PATH + "/{id}/activate";

    public static final String PROBLEM_DETAIL_TYPE = "about:blank";
    public static final String PROBLEM_DETAIL_TITLE_VALIDATION = "Validation Failed";
    public static final String PROBLEM_DETAIL_TITLE_NOT_FOUND = "Not Found";
    public static final String PROBLEM_DETAIL_TITLE_CONFLICT = "Conflict";
    public static final String PROBLEM_DETAIL_TITLE_BAD_REQUEST = "Bad Request";

    private ApiConstants() {
    }
}
