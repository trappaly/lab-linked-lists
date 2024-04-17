import java.io.PrintWriter;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Some simple experiments with SimpleDLLs
 */
public class SCDLLExpt {
  public static void main(String[] args) throws Exception {
    PrintWriter pen = new PrintWriter(System.out, true);
    //SimpleList <Integer> test = new SimpleCDLL<Integer>(); 
    //ListIterator<Integer> i1 = test.listIterator(); 
    //ListIterator<Integer> i2 = test.listIterator(); 

   // i1.add(2);
    //i1.add(3);

    SimpleListExpt.expt1(pen, new SimpleCDLL<String>());
    SimpleListExpt.expt2(pen, new SimpleCDLL<String>());
    SimpleListExpt.expt3(pen, new SimpleCDLL<String>());
    //SimpleListExpt.expt4(pen, new SimpleCDLL<String>());
  } // main(String[]
} // SDLLExpt
