import java.util.*;
import java.util.regex.*;


class Lexer {
    private static final List<TokenPattern> TOKEN_PATTERNS = Arrays.asList(
            new TokenPattern("[ \\t]+", null), // Espaços e tabulações (ignorados)
            new TokenPattern("#[^\\n]*", null), // Comentários (ignorados)
            new TokenPattern("\\d+", "NUMBER"), // Números
            new TokenPattern("\\w+", "IDENTIFIER"), // Identificadores
            new TokenPattern("\\+", "PLUS"), // Operador de soma
            new TokenPattern("-", "MINUS"), // Operador de subtração
            new TokenPattern("\\*", "MULTIPLY"), // Operador de multiplicação
            new TokenPattern("/", "DIVIDE"), // Operador de divisão
            new TokenPattern("=", "EQUAL"), // Atribuição
            new TokenPattern("\\(", "LPAREN"), // Parêntese esquerdo
            new TokenPattern("\\)", "RPAREN"), // Parêntese direito
            new TokenPattern("\\n", "NEWLINE") // Nova linha
    );

    private final String sourceCode;
    private int position;
    private final List<Token> tokens;

    public Lexer(String sourceCode) {
        this.sourceCode = sourceCode;
        this.position = 0;
        this.tokens = new ArrayList<>();
    }

    public List<Token> tokenize() throws SyntaxException {
        while (position < sourceCode.length()) {
            boolean matched = false;
            for (TokenPattern pattern : TOKEN_PATTERNS) {
                Pattern regex = Pattern.compile(pattern.regex);
                Matcher matcher = regex.matcher(sourceCode.substring(position));
                if (matcher.lookingAt()) {
                    if (pattern.tokenType != null) { // Ignora espaços e comentários
                        tokens.add(new Token(pattern.tokenType, matcher.group()));
                    }
                    position += matcher.end();
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                throw new SyntaxException("Caractere inválido encontrado: " + sourceCode.charAt(position));
            }
        }
        return tokens;
    }
}