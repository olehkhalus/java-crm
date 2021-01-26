package com.inventorsoft.crm.javacrm.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationConstant {

    @UtilityClass
    public class SpringProfile {

        public static final String LOCAL = "local";

    }

    @UtilityClass
    public class ThymeleafPage {

        public static final String LOGIN_PAGE = "/auth/login";
        public static final String SIGN_UP_PAGE = "/auth/sign-up";
        public static final String DASHBOARD_PAGE = "/dashboard";
        public static final String LEADS_PAGE = "lead/leads";
        public static final String CREATE_LEAD_PAGE = "lead/create-lead";
        public static final String UPDATE_LEAD_PAGE = "lead/update-lead";
        public static final String CHANGE_PASSWORD = "/user/password/changePassword";
        public static final String USER_PROFILE = "/user/profile/userProfile";

    }

    @UtilityClass
    public class PrefixConstants {

        public static final String PRIVATE = "/private-api";
        public static final String PUBLIC = "/public-api";
    }

    @UtilityClass
    public class Initializer {

        public static final String LEAD_DEFAULT_EMAIL = "admin@gmail.com";
        public static final String ADMIN_DEFAULT_EMAIL = "admin@mail.com";
    }

    @UtilityClass
    public class LeadConstant {

        public static final String LEAD_STATUSES = "leadStatuses";
    }
}
