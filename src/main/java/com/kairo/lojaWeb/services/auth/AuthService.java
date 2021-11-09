package com.kairo.lojaWeb.services.auth;

import com.kairo.lojaWeb.models.Funcionario;

public interface AuthService {

    boolean validateCode(Funcionario funcionario, String code);

    boolean sendEmailCode(String email) throws Exception;
}
