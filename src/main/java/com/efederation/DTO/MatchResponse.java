package com.efederation.DTO;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class MatchResponse {
    private List<MatchAttributes> matches;
}
