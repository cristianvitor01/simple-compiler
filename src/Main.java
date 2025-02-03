import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java Main <input.txt>");
            return;
        }

        try {
            String sourceCode = new String(Files.readAllBytes(new File(args[0]).toPath()));
            Lexer lexer = new Lexer(sourceCode);
            List<Token> tokens = lexer.tokenize();
            Parser parser = new Parser(tokens);
            parser.parse();
        } catch (IOException | SyntaxException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}