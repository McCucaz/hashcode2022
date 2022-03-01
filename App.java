import java.io.IOException;

public class App {
    public static void main(String args[]) throws IOException {
        IO io1 = new IO("inputs/a.in"); 
        io1.start(); 
        IO io2 = new IO("inputs/b.in");
        io2.start();
        IO io3 = new IO("inputs/c.in");
        io3.start();
        IO io4 = new IO("inputs/d.in");
        io4.start();
        IO io5 = new IO("inputs/e.in");
        io5.start();
        IO io6 = new IO("inputs/f.in");
        io6.start();
   }
}