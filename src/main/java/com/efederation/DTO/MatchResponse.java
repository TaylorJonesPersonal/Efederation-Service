package com.efederation.DTO;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class MatchResponse {
    private int wrestlerId;
    private List<MatchAttributes> matches;
}
