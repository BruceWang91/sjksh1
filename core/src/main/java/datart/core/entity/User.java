package datart.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private String email;

    private String username;

    private String password;

    private Boolean active;

    private String name;

    private String description;

    private String avatar;

    private Long deptId;

    private String adminCompetence;

    private String deptName;

    private String orgCode;
}