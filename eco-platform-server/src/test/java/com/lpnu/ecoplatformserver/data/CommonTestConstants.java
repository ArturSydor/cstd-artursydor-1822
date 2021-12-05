package com.lpnu.ecoplatformserver.data;

public final class CommonTestConstants {

    public static final String BASE_URL = "http://localhost:%s/";
    public static final String ORGANISATION_URL = BASE_URL + "organisations";
    public static final String AUTH_LOGIN_URL = BASE_URL + "auth/login";
    public static final String AUTH_REGISTER_URL = BASE_URL + "auth/registration";
    public static final String ROLES_URL = BASE_URL + "roles";
    public static final String USER_APPROVALS_URL = BASE_URL + "users/approvals";
    public static final String ECO_PROJECTS_URL = BASE_URL + "eco-projects";


    public static final Long ORGANISATION_TESTS_STEP = 0L;
    public static final Long AUTH_TESTS_STEP = 100L;
    public static final Long USER_TESTS_STEP = 200L;
    public static final Long ECO_PROJECT_TESTS_STEP = 300L;


    public static final String FIRST_NAME = "Test_FirstName";
    public static final String LAST_NAME = "Test_LastName";

    public static final Long USER_ID = 1L;
    public static final String MANAGER_USER_EMAIL = "user.test@mail.com";
    public static final String MANAGER_USER_PASSWORD = "1111";

    public static final Long ORGANISATION_ID = 1L;
    public static final String ORGANISATION_NAME = "Test_Organisation_Name";
    public static final String ORGANISATION_EMAIL = "organisation.test@mail.coml";

    public static final String ORGANISATION_NAME_FOR_UPDATE = "Test_Organisation_Name_Updated";
    public static final String ORGANISATION_EMAIL_FOR_UPDATE = "organisation.updated.test@mail.coml";

    public static final String INHABITANT_USER_EMAIL = "user.test.usual@mail.com";
    public static final String INHABITANT_USER_PASSWORD = "2222";

    public static final String ECO_PROJECT_NAME = "Eco_test";
    public static final String ECO_PROJECT_DESCRIPTION = "Eco_description";
    public static final int ECO_PROJECT_MAX_ALLOWED_POINTS = 3;

}
