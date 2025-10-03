package com.solaceisle.pojo.po;

import com.solaceisle.pojo.entity.Track;
import lombok.Data;



import java.util.List;

@Data
public class TrackVO {
    private int consecutiveDays;
    private List<Track> tracks;
}
