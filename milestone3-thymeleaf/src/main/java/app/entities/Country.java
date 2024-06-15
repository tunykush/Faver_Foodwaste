package app.entities;

/**
 * Class represeting a Country from the Studio Project database
 *
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class Country {
   // country Code
   private String m49Code;

   // country Name
   private String name;

   /**
    * Create a Country and set the fields
    */
   public Country(String m49Code, String name) {
      this.m49Code = m49Code;
      this.name = name;
   }

   public String getM49Code() {
      return m49Code;
   }

   public String getName() {
      return name;
   }
}
