package com.springweb.notice.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserFindDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String username;
}
