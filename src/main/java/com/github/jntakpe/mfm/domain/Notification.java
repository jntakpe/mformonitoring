package com.github.jntakpe.mfm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Bean représentant une notification
 *
 * @author jntakpe
 */
@Document
public class Notification extends GenericDomain {

    private String message;

    private String target;

    private String type;

    public Notification(Application origin) {
        setTarget(Target.APPPLICATION);
        setType(Type.STOP);
        message = "L'application " + origin.getName() + " n'est plus accessible en " + origin.getEnvironment();
    }

    public Notification(Application origin, Application dest, Type type) {
        setTarget(Target.APPPLICATION);
        setType(type);
        switch (type) {
            case START:
                message = "L'application " + origin.getName() + " est à présent démarrée en " + origin.getEnvironment();
                break;
            case CHANGE_VERSION:
                message = "La version de l'application " + origin.getName() + " en " + origin.getEnvironment()
                        + "a été modifié de " + origin.getVersion() + " à " + dest.getVersion();
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Target getTarget() {
        return Target.valueOf(target);
    }

    public void setTarget(Target target) {
        this.target = target.name();
    }

    public Type getType() {
        return Type.valueOf(type);
    }

    public void setType(Type type) {
        this.type = type.name();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("message", message)
                .append("target", target)
                .append("status", type)
                .toString();
    }
}
