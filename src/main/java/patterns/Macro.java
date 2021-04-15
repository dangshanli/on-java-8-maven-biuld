package patterns;// patterns/Macro.java
// (c)2021 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.
import java.util.*;

public class Macro {
  public static void main(String[] args) {
    List<Runnable> macro = new ArrayList<>(
      Arrays.asList(
        () -> System.out.print("utils.Hello "),
        () -> System.out.println("World! ")
    ));
    macro.forEach(Runnable::run);
    macro.add(
      () -> System.out.print("I'm the command pattern!")
    );
    macro.forEach(Runnable::run);
  }
}
/* Output:
utils.Hello World!
utils.Hello World!
I'm the command pattern!
*/
