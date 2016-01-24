package animaldatabase;

/*
This program demonstrates the delete edit and insert features of the mysql jdbc driver
To use this program you must use a mysql database which is included with this program (Animals.sql)
each of the classes that call delete, insert, or update functionality call the connections independently



*/
import java.sql.*;
import java.text.NumberFormat;
import java.util.Scanner;


/**
 *
 * @author Michael "Ben" Gabbard
 */
public class AnimalDatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // TODO code application logic here
        System.out.println("This program will add, edit, and delete entries from a MySQL database");
        main_Menu();
         
     
   
    }
     
    private static void main_Menu()
            
        {
        System.out.println("----------------------------------");
        System.out.println("Select an option");
        System.out.println("1. add to the database");
        System.out.println("2. edit an entry");
        System.out.println("3. delete list");
        System.out.println("4. display list");
        System.out.println("any other number will exit");
        System.out.println("");
        Scanner keyboard = new Scanner(System.in);
        int mainSelection;
        mainSelection = keyboard.nextInt();
        if (mainSelection == 1){addEntry();}
        if (mainSelection == 2){updateEntry();}
        if (mainSelection == 3){deleteEntry();}
        if (mainSelection == 4){System.out.println("-------------");displayResults();main_Menu();}
        
        }


    ///Add entry menu
    private static void addEntry()
        {
        
            System.out.println("");
            System.out.println("");
            System.out.println("");
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter the Species");
            String animalSpecies = keyboard.nextLine();
            System.out.println("Enter the Color");
            String animalColor = keyboard.nextLine();
            System.out.println("Enter the Habitat");
            String animalHabitat = keyboard.nextLine();
            insertIT(animalSpecies,animalColor,animalHabitat);
            main_Menu();       
        }
    
    //update entry menu
    private static void updateEntry()
        {
            displayResults();
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("Enter the Id number of the Animal you wish to update");
            Scanner keyboard = new Scanner(System.in);
            int animalInt = keyboard.nextInt();
            ///new line because nextInt doesnt strip a new line for some really annoying reason
            keyboard.nextLine();
            System.out.println("Enter the Species");
            String animalSpecies = keyboard.nextLine();
            System.out.println("Enter the Color");
            String animalColor = keyboard.nextLine();
            System.out.println("Enter the Habitat");
            String animalHabitat = keyboard.nextLine();
            //System.out.println("Enter the ID #");
            
            updateIT(animalInt,animalSpecies,animalColor,animalHabitat);
            main_Menu();       
        }
    //delete entry menu
    private static void deleteEntry()
        {
         displayResults();
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("Enter the Id number of the Animal you wish to update");
            Scanner keyboard = new Scanner(System.in);
            int animalInt = keyboard.nextInt();
            ///new line because nextInt doesnt strip a new line for some really annoying reason
            keyboard.nextLine();

            
           deleteIT(animalInt);
            main_Menu();          
        }
    //method to insert new entries to the database
    private static void insertIT(String Aspecies, String Acolor, String Ahabitat)
    {
        
    try{
        String driver="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/Animals";
        String uname="root";
        String pass="password";
        Class.forName(driver);
        Connection c=(Connection) DriverManager.getConnection(url,uname,pass);
        Statement s=c.createStatement();
        ///sql statement definitions 
        s.executeUpdate("INSERT INTO animals(species,color,habitat) VALUE('"+Aspecies+"','"+Acolor+"','"+Ahabitat+"')");
        }
    catch(SQLException e)
        {
        System.out.println(e.getMessage());
        main_Menu();
        }
    catch (ClassNotFoundException e)
        {
        
        System.out.println(e.getMessage());
        System.out.println("did not work.. going back to main menu");
        main_Menu();
        }
        
        
    }
    //method to update an entry by id
      private static void updateIT(int Aid, String Aspecies, String Acolor, String Ahabitat)
    {
        
    try{
        String driver="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/Animals";
        String uname="root";
        String pass="password";
        Class.forName(driver);
        Connection c=(Connection) DriverManager.getConnection(url,uname,pass);
        Statement s=c.createStatement();
        ///sql statement 
    
        s.executeUpdate("UPDATE animals SET species='"+Aspecies+"', color='"+Acolor+"', habitat='"+Ahabitat+"' WHERE id='"+Aid+"';");
        }
    catch(SQLException e)
        {
        System.out.println(e.getMessage());
        }
    catch (ClassNotFoundException e)
        {
        
        System.out.println(e.getMessage());
        System.out.println("did not work.. class not found");
        System.exit(0);
        }

    }  
      //method to delete a specific entry by id
    private static void deleteIT(int Aid)
        {
            
        try{
            String driver="com.mysql.jdbc.Driver";
            String url="jdbc:mysql://localhost:3306/Animals";
            String uname="root";
            String pass="password";
            Class.forName(driver);
            Connection c=(Connection) DriverManager.getConnection(url,uname,pass);
            Statement s=c.createStatement();
            ///sql statement definitions 
            s.executeUpdate("DELETE FROM animals WHERE id='"+Aid+"';");
            }
        catch(SQLException e)
            {
            System.out.println(e.getMessage());
            }
        catch (ClassNotFoundException e)
            {
            System.out.println(e.getMessage());
            System.out.println("did not work.. class not found");
            System.exit(0);
            }
            
        }
    //method to displays the rsults of the list
    private static void displayResults()
    
        {
            
            ResultSet animals = getAnimals();
        try
        {
        while (animals.next())
            {
                Animal a = getAnimals(animals);
                String msg = a.id +". "+ a.species;
                msg += ": " + a.color;
                msg += " "+ a.habitat + "";
                System.out.println(msg);
            }
        
        
        
        }
        catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        
        }
    private static ResultSet getAnimals()
    {
    
    Connection con = getConnection();
    try
        {
            
        Statement s = con.createStatement();
        String select = "SELECT id, species, color, habitat FROM animals ORDER BY id ASC";
        ResultSet rows;
        rows = s.executeQuery(select);
        return rows;
        }
    catch (SQLException e)
        {
        System.out.println(e.getMessage());
        }
        return null;
    }
    private static Connection getConnection()
    {
    Connection con = null;
    try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Animals";
            String usr = "root";
            String pwd = "password";
            con = DriverManager.getConnection(url, usr, pwd);
            
        }
    catch (ClassNotFoundException e)
        {
        
        System.out.println(e.getMessage());
        System.out.println("did not work.. class not found");
        System.exit(0);
        }
    catch (SQLException e)
        {
        System.out.println(e.getMessage());
        System.out.println("did not work.. sql problem");
        System.exit(0);
        }
    return con;
    
    }
    private static Animal getAnimals(ResultSet animals)
    {
    try
        {
        int id = animals.getInt("Id");
        String species = animals.getString("Species");
        String color = animals.getString("Color");
        String habitat = animals.getString("Habitat");
        return new Animal(id, species, color, habitat);
        }
    catch (SQLException e)
        {
    System.out.println(e.getMessage());
        }
    return null;
    }
    private static class Animal
    {
    public int id;
    public String species;
    public String color;
    public String habitat;
    public Animal(int id, String species, String color, String habitat)
        {
        this.id = id;
        this.species = species;
        this.color = color;
        this.habitat = habitat;
        }
    }
 
}