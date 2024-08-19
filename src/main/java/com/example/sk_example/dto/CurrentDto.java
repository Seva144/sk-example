package com.example.sk_example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CurrentDto implements Serializable {

    private Integer current;

}
