import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A stack of elements of type E.
 *
 * @param <E> the type of elements in this list
 *
 * @author ckurdelak20@georgefox.edu
 */
public class Stack<E> implements Iterable<E> {
    // TODO implement Stack
    // TODO LIFO
    // TODO remove all index stuff
    // TODO throw EmptyStackException

    // add -> push
    // remove -> pop
    // peek -> get

    // only modification at top
    // no indexing

    private StackNode<E> _top;
    private int _depth;
    private long _modCount;

    /**
     * Creates a new Stack.
     */
    public Stack() {
        _top = null;
        _depth = 0;
        _modCount = 0;
    }


    /**
     * Pushes the specified element to the top of this stack.
     *
     * @param element the element to be pushed
     * @return true if this collection has changed as a result of the call
     */
    public boolean push(E element) {
        // TODO change to push @ top
        LinkedListNode<E> newNode;
        if (this.size() == 0) {
            newNode = new LinkedListNode<>(element);
            _head = newNode;
            _tail = newNode;
        }
        else {
            newNode = new LinkedListNode<>(element);
            newNode.setPrevious(_tail);
            _tail.setNext(newNode);
            _tail = newNode;
        }
        _size++;
        _modCount++;

        return true;
    }


    /**
     * Removes all the elements from this list. The list will be empty after this call returns.
     */
    public void clear() {
        // TODO implement for Stack
        // let go of top
        if (_top != null) {
            _top = null;
            _modCount++;
        }
    }


    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements
     * false if this list contains elements
     */
    public boolean isEmpty() {
        return (_depth == 0);
    }


    /**
     * Removes and returns the element from the specified position in this list.
     *
     * Maintains constant-time access to head and tail
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the specified index is out of range
     */
    public E remove(int index) {
        // TODO change to pop
        if (this.isValidIndex(index) && !isEmpty()) {
            LinkedListNode<E> oldNode;
            LinkedListNode<E> prevNode;
            LinkedListNode<E> nextNode;
            E oldValue;

            if (index == 0) {
                oldNode = _head;
                oldValue = oldNode.getValue();
                if (this.size() > 1) {
                    nextNode = oldNode.getNext();

                    nextNode.setPrevious(null);
                    _head = nextNode;
                }
                else {
                    _head = null;
                }
            }
            else if (index == size() - 1) {
                oldNode = _tail;
                oldValue = oldNode.getValue();
                prevNode = oldNode.getPrevious();

                prevNode.setNext(null);
                _tail = prevNode;
            }
            else {
                oldNode = this.seek(index);
                oldValue = oldNode.getValue();
                prevNode = oldNode.getPrevious();
                nextNode = oldNode.getNext();

                prevNode.setNext(nextNode);
                nextNode.setPrevious(prevNode);
            }

            oldNode.setPrevious(null);
            oldNode.setNext(null);
            oldNode.setValue(null);

            _size--;
            _modCount++;

            return oldValue;
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }


    /**
     * Returns the number of elements in this stack.
     *
     * @return the number of elements in this stack
     */
    public int depth() {
        return _depth;
    }


    /**
     * Returns a new LinkedListIterator object that iterates from head to tail.
     *
     * @return a new LinkedListIterator object that iterates from head to tail
     */
    public Iterator<E> iterator() {
        return new StackIterator();
    }


    // TODO implement pushAll
    // takes an iterable thing
    // for each element in collectn of elements, push element


    /**
     * A node that stores a single element in a LinkedList as well as references to its adjacent
     * nodes.
     *
     * @param <E> the type of element stored in this node
     */
    private class StackNode<E> {

        E _value;
        StackNode<E> _next;

        /**
         * Constructs a new StackNode.
         */
        public StackNode() {
            this(null);
        }


        /**
         * Constructs a new StackNode with the given value.
         *
         * @param value the value stored in this StackNode
         */
        public StackNode(E value) {
            this(value, null);
        }


        /**
         * Constructs a new LinkedListNode with the given value.
         *
         * @param value the value stored in this LinkedListNode
         * @param prev the previous LinkedListNode
         * @param next the next LinkedListNode
         */
        public StackNode(E value, StackNode<E> prev, StackNode<E> next) {
            _value = value;
            _next = next;
        }


        /**
         * Returns the value of this LinkedListNode.
         *
         * @return the value of this LinkedListNode
         */
        public E getValue() {
            return _value;
        }


        /**
         * Returns the next LinkedListNode.
         *
         * @return the next LinkedListNode
         */
        public StackNode<E> getNext() {
            return _next;
        }


        /**
         * Sets the value of this node to a new value.
         *
         * @param value the new value of this node
         */
        public void setValue(E value) {
            _value = value;
        }


        /**
         * Sets the next attribute to a new node.
         *
         * @param next the new next node
         */
        public void setNext(StackNode<E> next) {
            _next = next;
        }
    }


    /**
     * Implements the Iterator<T> interface for the LinkedList class.
     *
     * Uses fail-fast iteration.
     */
    private class StackIterator implements Iterator<E> {

        private StackNode<E> _currentNode;

        /**
         * Constructs a new LinkedListIterator object.
         */
        public StackIterator() {
            // TODO implement StackIterator
            // TODO pop every time it iterates -> consumes stack
            _currentNode = _top;
            _modCountCopy = _modCount;
        }

        /**
         * Returns true if the current node has a next, else returns false.
         *
         * @return true if the current node has a next, else returns false
         */
        public boolean hasNext() {
            // TODO remove fail fast iteration bc stack needs to be consumed during iteration
            if (_modCountCopy == _modCount) {
                if (! _reverse) {
                    return _currentIndex < _size;
                }
                else {
                    return _currentIndex >= 0;
                }
            }
            else {
                throw new ConcurrentModificationException();
            }
        }


        /**
         * Returns and pops the next element in this iteration.
         *
         * @return the next element in this iteration
         * @throws ConcurrentModificationException if the list has been modified since iteration
         * started
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public E next() {
            // TODO pop top every iteration
            if (_modCountCopy == _modCount) {
                if (hasNext()) {
                    E item = get(_currentIndex);
                    if (item == null) {
                        throw new NoSuchElementException();
                    }
                    if (!_reverse) {
                        _currentNode = _currentNode.getNext();
                        _currentIndex++;
                    } else {
                        _currentNode = _currentNode.getPrevious();
                        _currentIndex--;
                    }
                    return item;
                }
                else {
                    throw new NoSuchElementException();
                }
            }
            else {
                throw new ConcurrentModificationException();
            }
        }
    }
}

