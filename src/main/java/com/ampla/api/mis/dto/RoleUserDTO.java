package com.ampla.api.mis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @AllArgsConstructor @NoArgsConstructor
public class RoleUserDTO {

    private String username;
    private String roleName;

}
