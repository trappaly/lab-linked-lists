
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Simple circular doubly-linked lists using a dummy node in its implementation
 *
 * @author Alyssa Trapp
 * @author Samuel A. Rebelsky
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
   * The counter representing the number of changes for the overall list
   */
  int lcounter;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Creates a CDLL list
   */
  public SimpleCDLL() {
    // creates a new node to point dummy to
    this.dummy = new Node2<T>(null);
    // initializes the node after dummy to dummy
    this.dummy.next = this.dummy;
    // initializes the node before dummy to dummy
    this.dummy.prev = this.dummy;
    // initializes the size of the list to 0
    this.size = 0;
  } // SimpleCDLL()

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
      int icounter = lcounter;

      /**
       * The cursor is between neighboring values, so we start links
       * to the previous and next value..
       */

      // prev is initialized to node before dummy
      Node2<T> prev = SimpleCDLL.this.dummy.prev;
      // next is initialized to the node after dummy
      Node2<T> next = SimpleCDLL.this.dummy.next;

      /**
       * The node to be updated by remove or set. Has a value of
       * null when there is no such value.
       */
      Node2<T> update = null;

      // +---------+-------------------------------------------------------
      // | Methods |
      // +---------+

      /*
       * If different iterators call on the same list, then it will fail fast
       */

      public void failFast() {
        if (SimpleCDLL.this.lcounter != this.icounter) {
          throw new ConcurrentModificationException();
        } // if
      } // failFast()

      /*
       * Updates the list and iterator counter
       */

      public void updateCount() {
        // increases the list counter
        ++SimpleCDLL.this.lcounter;
        // increases the iterator counter
        ++this.icounter;
      } // updateCount()

      /*
       * Adds a value to the list
       */
      public void add(T val) throws UnsupportedOperationException {
        // makes iterators invalid
        failFast();
        // sets prev to be the next pointer after the insertBefore(val)
        this.prev = this.next.insertBefore(val);
        // sets update to null
        this.update = null;
        // increases the size of the list
        ++SimpleCDLL.this.size;
        // increases the position
        ++this.pos;
        // updates the count
        updateCount();
      } // add(T)

      public boolean hasNext() {
        // makes iterators invalid
        failFast();
        // if position is less than the size, returns true otherwise return false
        return (this.pos < SimpleCDLL.this.size);
      } // hasNext()

      public boolean hasPrevious() {
        // makes iterators invalid
        failFast();
        // if position is greater than 0, returns true otherwise return false
        return (this.pos > 0);
      } // hasPrevious()

      public T next() {
        // makes iterators invalid
        failFast();
        // if doesn't have next, throw an exception
        if (!this.hasNext()) {
          throw new NoSuchElementException();
        } // if
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
        // makes iterators invalid
        failFast();
        return this.pos;
      } // nextIndex()

      public int previousIndex() {
        // makes iterators invalid
        failFast();
        return this.pos - 1;
      } // previousIndex

      public T previous() {
        // makes iterators invalid
        failFast();
        // if it doesn't have a previous, throw an exception
        if (!this.hasPrevious()) {
          throw new NoSuchElementException();
        } // if
        // updates the cursor
        this.update = this.prev;
        this.next = this.prev;
        this.prev = this.prev.prev;
        // decreases the position
        --this.pos;
        // returns the value at update
        return this.update.value;
      } // previous()

      public void remove() {
        // makes iterators invalid
        failFast();
        // if update is equal to null, throw exception
        if (this.update == null) {
          throw new IllegalStateException();
        } // if
        // if prev is equal to update, then set prev to the node before update
        if (this.prev == this.update) {
          this.prev = this.update.prev;
          // decreases position
          --this.pos;
        } // if
        this.update.remove();
        // decreases the size of the list
        --SimpleCDLL.this.size;
        // sets update to null
        this.update = null;
        // updates count
        updateCount();
      } // remove()

      public void set(T val) {
        // makes iterators invalid
        failFast();
        // if update
        if (this.update == null) {
          throw new IllegalStateException();
        } // if
        // Sets the value at update to val
        this.update.value = val;
        // Note that no more updates are possible
        this.update = null;
      } // set(T)
    };
  } // listIterator()
} // class SimpleDLL<T>
