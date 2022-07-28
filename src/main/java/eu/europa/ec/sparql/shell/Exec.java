package eu.europa.ec.sparql.shell;

import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * SPARQL function to execute a shell.
 */
public class Exec extends FunctionBase2
{

    @Override
    public NodeValue exec(NodeValue cmd, NodeValue pipe) {
        try {
            Process p = Runtime.getRuntime().exec(cmd.asString());
            OutputStream outputStream = p.getOutputStream();
            outputStream.write(pipe.asString().getBytes());
            outputStream.close();
            BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            p.waitFor();
            StringBuilder result = new StringBuilder();
            if (p.exitValue() != 0) {
                result.append("Error while executing ").append(cmd.asString()).append(":\n");
                dump(error, result);
                throw new RuntimeException(error.toString());
            }
            dump(output,result);
            return NodeValue.makeString(result.toString().trim());

        } catch (IOException | InterruptedException e )  {
            throw new RuntimeException(e);
        }
    }

    private static StringBuilder dump(BufferedReader reader, StringBuilder builder) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        reader.close();
        return builder;
    }
}
