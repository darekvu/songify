package com.apivalidation;

import java.util.List;

public record ApiValidationErrorResponseDto(List<String> errors) {
}
