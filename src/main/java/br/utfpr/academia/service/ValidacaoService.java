package br.utfpr.academia.service;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidacaoService {
    private static final String DOMINIO_PERMITIDO = "@alunos.utfpr.edu.br";
    
    public static boolean validarEmail(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            return false;
        }
        return email.endsWith(DOMINIO_PERMITIDO);
    }
    
    public static boolean validarSenha(String senha) {
        return senha != null && senha.length() >= 8 
                && senha.matches(".*[!@#$%^&*()\\-+=].*");
    }
    
    public static boolean senhasConferem(String senha, String confirmarSenha) {
        return senha.equals(confirmarSenha);
    }
}
