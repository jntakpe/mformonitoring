package com.github.jntakpe.mfm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Bean représentant une notification
 *
 * @author jntakpe
 */
@Entity
public class Notification extends GenericDomain {

    private String target;

    private String type;

    @Transient
    private ApplicationInfos appInfos;

    public Notification() {
    }

    public Notification(Application origin) {
        setTarget(Target.APPPLICATION);
        setType(Type.STOP);
        appInfos = new ApplicationInfos(origin);
    }

    public Notification(Application origin, Application dest, Type type) {
        setTarget(Target.APPPLICATION);
        setType(type);
        appInfos = type == Type.START ? new ApplicationInfos(origin) : new ApplicationInfos(origin, dest);
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

    public ApplicationInfos getAppInfos() {
        return appInfos;
    }

    public void setAppInfos(ApplicationInfos appInfos) {
        this.appInfos = appInfos;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("target", target)
                .append("type", type)
                .append("appInfos", appInfos)
                .toString();
    }
}
