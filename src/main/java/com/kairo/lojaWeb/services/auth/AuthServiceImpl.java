package com.kairo.lojaWeb.services.auth;

import com.kairo.lojaWeb.models.Funcionario;
import com.kairo.lojaWeb.dto.MailDTO;
import com.kairo.lojaWeb.repositories.FuncionarioRepository;
import com.kairo.lojaWeb.services.email.EmailSenderService;
import com.kairo.lojaWeb.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final EmailSenderService emailService;
    private final PasswordEncoder encoder;
    private final FuncionarioRepository funcionarioRepository;

    @Override
    public boolean sendEmailCode(String email) throws Exception {

        var funcionarioOptional = funcionarioRepository.findByEmail(email);

        if (funcionarioOptional.isPresent()) {
            var funcionario = funcionarioOptional.get();
            var code = sendEmail(funcionario);

            funcionario.setVerificationCode(code);

            funcionarioRepository.save(funcionario);
            return true;
        } else {
            return false;
        }
    }

    private String sendEmail(Funcionario funcionario) throws Exception {
        log.info("START... Sending email");

        try {
            var mail = MailDTO.builder()
                    .mailTo(funcionario.getEmail())
                    .subject("Recovery password - Loja Web!")
                    .build();

            // Gero um código aleatório
            var code = Util.getRandomNumberString();


            Map<String, Object> model = new HashMap<>();
            model.put("name", funcionario.getNome());
            model.put("code", code);
            mail.setProps(model);

            emailService.sendEmail(mail, "code-send");
            log.info("END... Email sent success");

            return encoder.encode(code);
        } catch (Exception e) {
            throw new Exception("Erro ao enviar e-mail: " + e.getMessage());
        }
    }

    @Override
    public boolean validateCode(Funcionario funcionario, String code) {
        return false;
    }

}
