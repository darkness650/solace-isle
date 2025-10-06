package com.solaceisle.pojo.vo;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingVO {

    private boolean shareAggregated;

    private boolean nightlyReminder;

    private boolean breathingNotification;
}
