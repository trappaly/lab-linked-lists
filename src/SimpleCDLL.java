
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Simple doubly-linked lists.
 *
 * These do *not* (yet) support the Fail Fast policy.
 */

public class SimpleCDLL<T> implements SimpleList<T> {

  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------

  /**
   * The dummy node in the list
   */
  private Node2<T> dummy;

  /**
   * The number of values in the list.
   */
  int size;

  /*
   * The counter for the overall list
   */
  int lcounter;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create an empty list.
   */
  public SimpleCDLL() {
    this(null);
  } // SimpleDLL

  /**
   * Create a list with an entered val.
   */
  public SimpleCDLL(T val) {
    // this.front = null;
    this.dummy = new Node2<T>(null);
    // this.front = new Node2<T>(val);
    this.dummy.next = null;
    this.dummy.prev = null;
    // this.front.next = this.dummy;
    // this.front.prev = this.dummy;
    this.size = 0;
    // this.lcounter = 0;
  } // SimpleCDLL(val)

  // +-----------+---------------------------------------------------------
  // | Iterators |
  // +-----------+

  public Iterator<T> iterator() {
    return listIterator();
  } // iterator()

  public ListIterator<T> listIterator() {
    return new ListIterator<T>() {
      // +--------+--------------------------------------------------------
      // | Fields |
      // +--------+

      /**
       * The position in the list of the next value to be returned.
       * Included because ListIterators must provide nextIndex and
       * prevIndex.
       */
      int pos = 0;

      /*
       * The iterator counter which counts the number of changes made within an
       * iterator
       */
      int icounter = 0;

      /**
       * The cursor is between neighboring values, so we start links
       * to the previous and next value..
       */

      // Node2<T> prev = null;
      // Node2<T> next = SimpleDLL.this.front;
      Node2<T> prev = SimpleCDLL.this.dummy.prev; // dummy node
      Node2<T> next = SimpleCDLL.this.dummy.next; // dummy node.next
      // Node2<T> dummy = next;

      /**
       * The node to be updated by remove or set. Has a value of
       * null when there is no such value.
       */
      Node2<T> update = null;

      // +---------+-------------------------------------------------------
      // | Methods |
      // +---------+
      // UnsupportedOperationException
      public void add(T val) throws UnsupportedOperationException {
        if (SimpleCDLL.this.lcounter != this.icounter){
         throw new ConcurrentModificationException();
           }
        if (dummy.next == null) {
          Node2<T> temp = new Node2<T>(val);
          dummy.next = temp;
          dummy.prev = temp;
          temp.next = dummy;
          temp.prev = dummy;
          this.prev = temp;
          this.next = temp;
        } else {
          // want: we want to use the insert after somehow, we don't know what node to
          // call it on
          // checking if adding at the end or beginning of the list
          this.prev = this.next.insertBefore(val);
          //this.prev = this.prev.insertAfter(val);
        }
        // Note that we cannot update
        this.update = null;
        // Increase the size
        ++SimpleCDLL.this.size;
        // Update the position. (See SimpleArrayList.java for more of
        // an explanation.)
        ++this.pos;
        ++SimpleCDLL.this.lcounter;
        ++this.icounter;
        // i want this check somehwre I just don't know where I want it
        // if
      } // add(T)

      public boolean hasNext() {
        if (SimpleCDLL.this.lcounter != this.icounter) {
          //System.out.println("lcounter" + lcounter + "icounter" + icounter);
          throw new ConcurrentModificationException();
        }
        // dummy != this.next
        return (this.pos < SimpleCDLL.this.size);
      } // hasNext()

      public boolean hasPrevious() {
        if (SimpleCDLL.this.lcounter != icounter) {
          throw new ConcurrentModificationException();
        }
        // dummy != this.prev
        return (this.pos > 0);
      } // hasPrevious()

      public T next() {
        if (!this.hasNext()) {
          throw new NoSuchElementException();
        } // if
        if (SimpleCDLL.this.lcounter != icounter) {
          throw new ConcurrentModificationException();
        }
        // Identify the node to update
        this.update = this.next;
        // Advance the cursor
        this.prev = this.next;
        this.next = this.next.next;
        // Note the movement
        ++this.pos;
        // And return the value
        return this.update.value;
      } // next()

      public int nextIndex() {
        if (SimpleCDLL.this.lcounter != icounter) {
          throw new ConcurrentModificationException();
        }
        return this.pos;
      } // nextIndex()

      public int previousIndex() {
        if (SimpleCDLL.this.lcounter != icounter) {
          throw new ConcurrentModificationException();
        }
        return this.pos - 1;
      } // previousIndex

      // was STUB method, this might be implemented wrong

      public T previous() {
        if (SimpleCDLL.this.lcounter != icounter) {
          throw new ConcurrentModificationException();
        }
        if (!this.hasPrevious()) {
          throw new NoSuchElementException();
        }
        //this.update = this.prev;
        //this.prev = this.prev.prev;
        //this.next = this.update.next;
        return this.update.prev.value;
      } // previous()

      public void remove() {
        // Update the curso
        if (SimpleCDLL.this.lcounter != icounter) {
          throw new ConcurrentModificationException();
        }
        if (this.update == null) {
          throw new IllegalStateException();
        }
        if (this.next == this.update) {
          this.next = this.update.next;
        } // if
        if (this.prev == this.update) {
          this.prev = this.update.prev;
          --this.pos;
        } // if

        // Update the front
        if (dummy.next == this.update) {
          dummy.next = this.update.next;
        }
        // } // if
        // pretty sure want to keep this
        // Do the real work
        this.update.remove();
        --SimpleCDLL.this.size;
        this.update = null;
        ++SimpleCDLL.this.lcounter;
        ++this.icounter;
      }

      public void set(T val) {
        if (SimpleCDLL.this.lcounter != icounter) {
          throw new ConcurrentModificationException();
        }
        // Sanity check
        if (this.update == null) {
          throw new IllegalStateException();
        } // if
        // Do the real work
        this.update.value = val;
        // Note that no more updates are possible
        this.update = null;
      } // set(T)
    };
  } // listIterator()
} // class SimpleDLL<T>

// add
// Special case: The list is empty)
/*
 * if (SimpleDLL.this.front == null) {
 * SimpleDLL.this.front = new Node2<T>(val);
 * this.prev = SimpleDLL.this.front;
 * } // empty list
 * // Special case: At the front of a list
 * else if (prev == null) {
 * this.prev = this.next.insertBefore(val);
 * SimpleDLL.this.front = this.prev;
 * } // front of list
 * // Normal case
 * else {
 */

// Note that we cannot update
// this.update = null;

// Increase the size
// ++SimpleDLL.this.size;

// Update the position. (See SimpleArrayList.java for more of
// an explanation.)
// ++this.pos;
// } // add(T)

// remove
// Update the cursor
// if (this.next == this.update) {
// this.next = this.update.next;
// } // if
// if (this.prev == this.update) {
// this.prev = this.update.prev;
// --this.pos;
// } // if

// Update the front
// if (SimpleDLL.this.front == this.update) {
// SimpleDLL.this.front = this.update.next;
// } // if
// pretty sure want to keep this
// Do the real work
// this.update.remove();
// --SimpleDLL.this.size;

// Note that no more updates are possible
// this.update = null;
// } // remove()
