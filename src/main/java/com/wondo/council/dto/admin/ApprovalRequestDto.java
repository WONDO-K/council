package com.wondo.council.dto.admin;

import com.wondo.council.domain.enums.UserIsMember;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ApprovalRequestDto",description = "가입 승인 처리 요청 Dto")
public class ApprovalRequestDto {
    @ApiModelProperty(name = "username")
    private String username;

    @ApiModelProperty(name = "isMember")
    private UserIsMember isMember;
}
