package com.whoscared.amonic.domain.utils;

import org.springframework.stereotype.Component;

@Component
public class UnsuccessfulLogout {
    private UnsuccessfulLogoutReason reason;
    private String reasonText;

    public UnsuccessfulLogout() {
    }

    public UnsuccessfulLogoutReason getReason() {
        return reason;
    }

    public void setReason(UnsuccessfulLogoutReason reason) {
        this.reason = reason;
    }

    public String getReasonText() {
        return reasonText;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }
}
