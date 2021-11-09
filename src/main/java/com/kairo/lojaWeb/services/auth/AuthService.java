package com.kairo.lojaWeb.services.auth;

import com.kairo.lojaWeb.models.Funcionario;

public interface AuthService {

    boolean validateCode(String email, String code) throws Exception;

    boolean sendEmailCode(String email) throws Exception;

    void newPassword(String email, String newPassword) throws Exception;
}
