import java.util.*;

class Parser {
    private final List<Token> tokens;
    private int currentPosition;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentPosition = 0;
    }

    public void parse() throws SyntaxException {
        while (currentPosition < tokens.size()) {
            parseStatement();
        }
        System.out.println("Análise sintática concluída com sucesso!");
    }

    private void parseStatement() throws SyntaxException {
        Token token = consume("IDENTIFIER");
        consume("EQUAL");
        parseExpression();
        consume("NEWLINE");
    }

    private void parseExpression() throws SyntaxException {
        consume("NUMBER", "IDENTIFIER");
        while (match("PLUS", "MINUS", "MULTIPLY", "DIVIDE")) {
            consume(tokens.get(currentPosition).type);
            consume("NUMBER", "IDENTIFIER");
        }
    }


    private Token consume(String... expectedTypes) throws SyntaxException {
        if (currentPosition >= tokens.size()) {
            throw new SyntaxException("Esperado " + Arrays.toString(expectedTypes) + ", mas fim de entrada encontrado.");
        }
        Token token = tokens.get(currentPosition);
        for (String type : expectedTypes) {
            if (token.type.equals(type)) {
                currentPosition++;
                return token;
            }
        }
        throw new SyntaxException("Esperado " + Arrays.toString(expectedTypes) + " mas encontrado " + token.type);
    }


    private boolean match(String... expectedTypes) {
        if (currentPosition >= tokens.size()) return false;
        for (String type : expectedTypes) {
            if (tokens.get(currentPosition).type.equals(type)) return true;
        }
        return false;
    }
}