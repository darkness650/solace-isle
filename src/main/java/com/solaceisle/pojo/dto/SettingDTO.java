package com.solaceisle.pojo.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SettingDTO {

    private boolean shareAggregated;

    private boolean nightlyReminder;

    private boolean breathingNotification;
}
