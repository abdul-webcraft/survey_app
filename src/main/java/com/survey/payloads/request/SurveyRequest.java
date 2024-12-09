package com.survey.payloads.request;

import com.survey.model.Dimension;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyRequest {

    @NotBlank(message = "Survey name is required")
    private String name;

    private List<DimensionRequest> dimensions;

    public List<DimensionRequest> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<DimensionRequest> dimensions) {
        this.dimensions = dimensions;
    }

    public  String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }
}
