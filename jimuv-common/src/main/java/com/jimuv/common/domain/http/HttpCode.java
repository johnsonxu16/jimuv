package com.jimuv.common.domain.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HttpCode {
    SUCCESS(200, "success"),
    ERROR(500, "error");

    private final int code;
    private final String msg;
}

