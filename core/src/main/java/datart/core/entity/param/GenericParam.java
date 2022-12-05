package datart.core.entity.param;

import lombok.Data;

@Data
public class GenericParam {

    private Long importId;
    private String script;
    private String condition;
    private String groups;
    private String orders;
}
