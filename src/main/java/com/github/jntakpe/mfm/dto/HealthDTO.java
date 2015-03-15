package com.github.jntakpe.mfm.dto;

import com.github.jntakpe.mfm.domain.Partner;

/**
 * Bean wrappant une requête pour récupérer les statuts des healths check
 *
 * @author jntakpe
 */
public class HealthDTO {

    private String status;

    private Partner selCrmHealth;

    private Partner selClientHealth;

    private Partner datamart;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Partner getSelCrmHealth() {
        return selCrmHealth;
    }

    public void setSelCrmHealth(Partner selCrmHealth) {
        this.selCrmHealth = selCrmHealth;
    }

    public Partner getSelClientHealth() {
        return selClientHealth;
    }

    public void setSelClientHealth(Partner selClientHealth) {
        this.selClientHealth = selClientHealth;
    }

    public Partner getDatamart() {
        return datamart;
    }

    public void setDatamart(Partner datamart) {
        this.datamart = datamart;
    }
}
