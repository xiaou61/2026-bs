package com.emergencysupply.controller;

import com.emergencysupply.service.AuthService;

public abstract class BaseController {
    protected final AuthService authService;

    protected BaseController(AuthService authService) {
        this.authService = authService;
    }

    protected void checkAuthenticated(String role) {
        authService.assertAuthenticated(role);
    }

    protected void checkAdmin(String role) {
        authService.assertAdmin(role);
    }

    protected void checkAnyRole(String role, String... roles) {
        authService.assertAnyRole(role, roles);
    }
}
