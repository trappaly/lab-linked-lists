import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.PrintWriter;
//import java.util.Iterator;
//import java.util.ListIterator;
import java.util.ListIterator;

/**
 * Some simple experiments with SimpleDLLs
 */
public class CDLLExpt {
  public static void main(String[] args) throws Exception {
    PrintWriter pen = new PrintWriter(System.out, true);
    SimpleListExpt.expt1(pen, new SimpleCDLL<String>());
    SimpleListExpt.expt2(pen, new SimpleCDLL<String>());
    SimpleListExpt.expt3(pen, new SimpleCDLL<String>());
  } // main
} // CDLLExpt