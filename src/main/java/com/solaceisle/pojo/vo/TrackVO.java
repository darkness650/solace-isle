package com.solaceisle.pojo.vo;

import com.solaceisle.pojo.entity.Track;
import lombok.Data;



import java.util.List;

@Data
public class TrackVO {
    private int consecutiveDays;
    private List<Track> tracks;
}
