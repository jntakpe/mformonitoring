package com.github.jntakpe.mfm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Bean représentant les informations le endpoint d'informations d'une app
 *
 * @author jntakpe
 */
public class Informations {

    public Application app;

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("application", app)
                .toString();
    }
}
