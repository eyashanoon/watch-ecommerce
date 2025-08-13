package com.watches.backend.enums;

public enum Role {

    // main Roles
    OWNER,
    ADMIN,
    CUSTOMER,

    // admin controller permissions
    SEE_ADMIN,
    ADD_ADMIN,
    REMOVE_ADMIN,
    UPDATE_ADMIN,

    // cart controller permissions
    SEE_CART,

    // customer controller permissions
    SEE_CUSTOMER,
    DELETE_CUSTOMER,

    // discount controller permissions
    ADD_DISCOUNT,
    SEE_DISCOUNT,
    REMOVE_DISCOUNT,

    // order controller permissions
    SEE_ORDER,
    UPDATE_ORDER,

    // product controller permissions
    SEE_PRODUCT,
    CREATE_PRODUCT,
    UPDATE_PRODUCT,
    DELETE_PRODUCT,

    // wishlist controller permissions
    SEE_WISHLIST,

    // Card controller permissions
    SEE_CARD
}