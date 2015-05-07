package com.github.jntakpe.mfm.rest;

/**
 * Urls de l'API REST
 *
 * @author jntakpe
 */
public final class Urls {

    public static final String API = "/api";

    public static final String APPLICATION = API + "/application";

    public static final String PARTNER = API + "/partner";

    public static final String NOTIF = API + "/notification";

    public static final String METRICS = APPLICATION + "/{id}/metrics";

    public static final String PARAM = APPLICATION + "/{id}/params";

    public static final String LOGS = APPLICATION + "/{id}/logs";

    private Urls() {
    }

}
