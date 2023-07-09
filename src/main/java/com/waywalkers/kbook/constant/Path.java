package com.waywalkers.kbook.constant;

public class Path {
    public static final String API = "/api";
    public static final String ADMIN = "/admin";
    public static final String HOME = "/home";
    public static final String STEPS = "/steps";
    public static final String ACCOUNTS = "/accounts";
    public static final String PROFILES = "/profiles";
    public static final String BOOKS = "/books";
    public static final String RECORDS = "/records";
    public static final String EVALUATATIONS = "/evaluations";
    public static final String EVALUATATION = "/evaluation";
    public static final String MONTHS = "/months";
    public static final String FILE = "/file";
    public static final String API_REDIRECT = API + "/redirect";

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	| AUTH
	|-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public static final String API_APPLE_LOGIN = API_REDIRECT + "/apple";
    public static final String API_REFRESH = API + "/refresh";
    public static final String API_REDIRECT_LOGOUT = API_REDIRECT + "/logout";

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	| PAYMENT
	|-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public static final String API_PAYMENTS = API + "/payments";
    public static final String API_PAYMENT = API + "/payment";
    public static final String API_PAYMENT_CANCEL = API_PAYMENT + "/cancel";
    public static final String API_REDIRECT_PAYMENT = API_REDIRECT + "/payment";
    public static final String API_REDIRECT_PAYMENT_SUCCESS = API_REDIRECT_PAYMENT + "/success";
    public static final String API_REDIRECT_PAYMENT_FAIL = API_REDIRECT_PAYMENT + "/fail";

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	| HOME
	|-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public static final String API_HOME = API + HOME;

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	| STEP
	|-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public static final String API_STEPS = API + STEPS;
    public static final String API_STEP = API_STEPS + "/{step-id}";

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	| ACCOUNT
	|-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public static final String API_ACCOUNTS = API + ACCOUNTS;
    public static final String API_ACCOUNT = API_ACCOUNTS + "/{account-id}";
    public static final String API_ACCOUNT_PAYMENT = API_ACCOUNT + "/payment";
    public static final String API_ACCOUNT_VULNERABLE = API_ACCOUNT + "/vulnerable";

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	| PROFILE
	|-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public static final String API_PROFILES = API_ACCOUNT + PROFILES;
    public static final String API_PROFILE = API_PROFILES + "/{profile-id}";

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	| BOOK
	|-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public static final String API_BOOKS = API + BOOKS;
    public static final String API_BOOK = API_BOOKS + "/{book-id}";
    public static final String API_BOOK_CONTENT = API_BOOK + "/content";
    public static final String API_BOOK_READ = API_BOOK + "/read";
    public static final String API_PROFILE_BOOKS = API_PROFILE + BOOKS;
    public static final String API_PROFILE_BOOK = API_PROFILE_BOOKS + "/{book-id}";

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	| BOOK OF MONTH
	|-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public static final String API_BOOK_OF_MONTHS = API_BOOKS + MONTHS;
    public static final String API_BOOK_OF_MONTH = API_BOOK_OF_MONTHS + "/{month-id}";

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    | RECORD
    |-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public static final String API_PROFILE_RECORDS = API_PROFILE + RECORDS;
    public static final String API_PROFILE_BOOK_RECORDS = API_PROFILE_BOOK + "/records";
    public static final String API_PROFILE_BOOK_RECORD = API_PROFILE_BOOK_RECORDS + "/{record-id}";
    public static final String API_PROFILE_BOOK_RECORD_DOWNLOAD = API_PROFILE_BOOK_RECORDS + "/download";
    public static final String API_EVALUATION_RECORDS = API + EVALUATATION + RECORDS;
    public static final String API_EVALUATION_RECORD = API_EVALUATION_RECORDS + "/{record-id}";
    public static final String API_PROFILE_EVALUATION_RECORDS = API_PROFILE + EVALUATATION + RECORDS;

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	| EVALUATION
	|-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public static final String API_EVALUATIONS = API + EVALUATATIONS;
    public static final String API_EVALUATION = API + EVALUATATIONS + "/{evaluation-id}";

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	| FILE
	|-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public static final String API_FILE_UPLOAD = API + FILE + "/upload";
    public static final String API_FILE_DOWNLOAD = API + FILE + "/download";

}
