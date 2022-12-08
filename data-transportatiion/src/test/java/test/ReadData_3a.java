package test;

import daniel.fr.datamodel.contacts;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//  question 3a

public class ReadData_3a
{
  private static List<contacts> getList(Connection connection)
  throws SQLException
  {
    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.CONTACTS");
    ResultSet re = preparedStatement.executeQuery();
    List<contacts> contacts = new ArrayList<>();
    while (re.next()) {
      contacts.add(getinfo(re));
    }
    return contacts;
  }
  
  private static contacts getinfo(ResultSet re) throws SQLException {
    return new contacts(
      re.getString("contact_email"),
      re.getString("contact_first_name"),
      re.getString("contact_last_name"),
      re.getString("contact_address"),
      re.getString("contact_city"),
      re.getString("contact_country"),
      re.getString("contact_birthdate"));
  }
  
  public static void main(String[] args) throws SQLException, IOException
  {
    
    Connection cnt = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "username", "password");
    
    PreparedStatement preparedStatement = cnt.prepareStatement(Files.readString(Path.of("src/main/resources/database/create-contacts.sql")));
    preparedStatement.execute();
    
    preparedStatement = cnt.prepareStatement(Files.readString(Path.of("src/main/resources/database/contacts.sql")));
    preparedStatement.execute();
    
    List<contacts> contacts = getList(cnt);
    for(contacts contact : contacts) {
      System.out.println(contact);
    }
  }
  
}
