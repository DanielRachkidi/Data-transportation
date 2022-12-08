package test;

import javax.mail.MessagingException;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

// question 3d and configuration 3e
public class JavaMail_3d
{
  //doing t with camel-mail
    public static class SimpleRouteBuilder
      extends RouteBuilder
    {

      @Override
      public void configure()
      throws Exception
      {
        from("file:src/email-spool/?fileName=outindex.html?noop=true").doTry().setHeader("subject", simple("JavaInUse Invitation111"))
          .setHeader("to", simple("jbutt@gmail.com"))
          .to("smtps://smtp.gmail.com:465?username=username&password=password");
      }

      public static void  main(String[] args)
      {
        SimpleRouteBuilder routeBuilder = new SimpleRouteBuilder();
        CamelContext ctx = new DefaultCamelContext();
        try
        {
          ctx.addRoutes(routeBuilder);
          ctx.start();
          Thread.sleep(5000);
          System.out.println("successfully sent");
          ctx.stop();
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
  
  
  }
  
}

