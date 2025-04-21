package com.efederation.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShowResponse {
    private long wrestlerId;
    private List<ShowData> shows;

    @Data
    @Builder
    public static class ShowData{
        private long id;
        private String name;
        private String importance;
        private String day;
        private String defaultImage;
        private String logo;
    }
}


