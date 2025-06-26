package br.utfpr.academia.service;

import java.util.Arrays;
import java.util.List;

public class ModeracaoService {
    private static final List<String> PALAVROES = Arrays.asList(
        "palavrão1", "palavrão2", "palavrão3" // Adicione a lista real de termos
    );

    public static String filtrarPalavroes(String texto) {
        if (texto == null) return null;
        
        String resultado = texto;
        for (String palavrao : PALAVROES) {
            resultado = resultado.replaceAll("(?i)" + palavrao, "***");
        }
        return resultado;
    }

    public static boolean conteudoInadequado(String texto) {
        if (texto == null) return false;
        return PALAVROES.stream().anyMatch(palavrao -> 
            texto.toLowerCase().contains(palavrao.toLowerCase()));
    }
}
