package test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

// question 3b and 3c with configuration 3e

public class ReplaceWords_MoveFile_3b_c
{


    
        public static void main(String[] args) throws Exception {
    
    
            CamelContext context = new DefaultCamelContext();
            context.addRoutes(
              new RouteBuilder() {
                  public void configure() {
                      from("file:src/main/resources/files/?fileName=index.html").to("file:src/email-spool/?fileName=outindex.html&charset=utf-8");
                  }
              });
            context.start();
            Thread.sleep(5000);
            System.out.println(" file copied and moved");
            context.stop();
            Map<String, String> variableMap = fillMap();
//            Path path = Paths.get("C:\\Users\\Daniel\\Desktop\\data transportation and exploration\\exam\\data transportation project Daniel\\data-transportatiion\\src\\main\\resources\\files\\index.html");
//            Path path = Paths.get("src/main/resources/files/index.html");
            Path path = Paths.get("src/email-spool/outindex.html");

            Stream<String> lines;

            try{
                lines = Files.lines(path, Charset.forName("UTF-8"));
                List<String> replacedLines = lines.map(line -> replaceTag(line,variableMap))
                  .collect(Collectors.toList());
                Files.write(path, replacedLines, Charset.forName("UTF-8"));
                lines.close();
                System.out.println("Find and replace done");

            } catch (IOException e) {
                e.printStackTrace();
            }



        }


    public static Map<String, String> fillMap(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("${city}","New York");
        map.put("contact@example.com", "epita@france.fr");
          map.put("${firstName}", "John");
        return map;

    }


    private static  String replaceTag(String str, Map<String, String>map)
    {

        for (Map.Entry<String, String> entry : map.entrySet())
        {
            if (str.contains(entry.getKey()))
            {
                str = str.replace(entry.getKey(), entry.getValue());
            }

    }        return str;


    }
}






