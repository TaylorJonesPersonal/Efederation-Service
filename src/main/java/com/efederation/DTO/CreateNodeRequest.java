package com.efederation.DTO;

import lombok.Data;


@Data
public class CreateNodeRequest {
    private String name;
    private String text;
    private String type;
    private String[] availableOptions;
    private Long parentNodeId;
}
