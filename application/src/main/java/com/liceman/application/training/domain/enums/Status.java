package com.liceman.application.training.domain.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {

    PENDING_MENTOR,
    PENDING_USER,
    PENDING_ADMIN,
    APPROVED,
    REJECTED,
    FINISHED

}
