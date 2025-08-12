package org.example.crm_back.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderStatus {
    @JsonProperty("In Work")
    In_Work,
    Agree,
    Disagree,
    Dubbing,
    New
}
