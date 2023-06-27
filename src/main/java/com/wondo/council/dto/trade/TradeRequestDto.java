package com.wondo.council.dto.trade;

import com.wondo.council.domain.enums.TradeCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TradeRequestDto", description = "거래 게시글 생성 Dto")
public class TradeRequestDto {

    @ApiModelProperty(name = "title")
    @NotBlank
    private String title;

    @ApiModelProperty(name = "content")
    @NotBlank
    private String content;


    @ApiModelProperty(name = "price")
    @NotNull
    private int price;
}
