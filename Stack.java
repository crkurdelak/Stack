import java.util.ConcurrentModificationException;
import java.util.EmptyStackException;
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

    private StackNode<E> _top;
    private int _depth;

    /**
     * Creates a new Stack.
     */
    public Stack() {
        _top = null;
        _depth = 0;
    }


    /**
     * Pushes the specified element to the top of this stack.
     *
     * @param element the element to be pushed
     * @return true if this collection has changed as a result of the call
     */
    public boolean push(E element) {
        StackNode<E> newNode;

        newNode = new StackNode<>(element);
        if (this.depth() > 0) {
            newNode.setNext(_top);
        }
        _top = newNode;

        _depth++;

        return true;
    }


    /**
     * Pushes all given elements to this Stack
     *
     * @param elements an iterable collection of elements to push to this Stack
     */
    public void pushAll(Iterable<E> elements) {
        for (E element : elements) {
            this.push(element);
        }
    }


    /**
     * Returns the top of this Stack without removing it.
     *
     * @return the top of this Stack
     * @throws EmptyStackException if this Stack is empty
     */
    public E peek() {
        if (!isEmpty()) {
            return _top.getValue();
        }
        else {
            throw new EmptyStackException();
        }
    }


    /**
     * Removes and returns the top of this Stack.
     *
     * @return the element that was removed from the Stack
     * @throws EmptyStackException if the Stack is empty
     */
    public E pop() {
        if (!isEmpty()) {
            StackNode<E> oldTop = _top;
            _top = oldTop.getNext();

            return oldTop.getValue();
        }
        else {
            throw new EmptyStackException();
        }
    }


    /**
     * Removes all the elements from this list. The list will be empty after this call returns.
     */
    public void clear() {
        _top = null;
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
     * Returns the number of elements in this stack.
     *
     * @return the number of elements in this stack
     */
    public int depth() {
        return _depth;
    }


    /**
     * Returns a new StackIterator object.
     *
     * @return a new StackIterator object
     */
    public Iterator<E> iterator() {
        return new StackIterator();
    }


    /**
     * A node that stores a single element in a Stack as well as a reference to the next node.
     *
     * @param <E> the type of element stored in this node
     */
    private class StackNode<E> {

        private E _value;
        private StackNode<E> _next;

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
         * Constructs a new StackNode with the given value.
         *
         * @param value the value stored in this StackNode
         * @param next the next StackNode
         */
        public StackNode(E value, StackNode<E> next) {
            _value = value;
            _next = next;
        }


        /**
         * Returns the value of this StackNode.
         *
         * @return the value of this StackNode
         */
        public E getValue() {
            return _value;
        }


        /**
         * Returns the next StackNode.
         *
         * @return the next StackNode
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
         * Sets this node's next attribute to a new node.
         *
         * @param next the new next node
         */
        public void setNext(StackNode<E> next) {
            _next = next;
        }
    }


    /**
     * Implements the Iterator<T> interface for the Stack class.
     */
    private class StackIterator implements Iterator<E> {

        /**
         * Constructs a new StackIterator object.
         */
        public StackIterator() {
            // No internal state is needed because the top is always consumed during iteration.
        }

        /**
         * Returns true if there are more elements in this iteration, else returns false.
         *
         * @return true if there are more elements in this iteration, else return false
         */
        public boolean hasNext() {
            return (!isEmpty());
        }


        /**
         * Returns and pops the next element in this iteration.
         *
         * @return the next element in this iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public E next() {
            if (hasNext()) {
                return pop();
            }
            else {
                throw new NoSuchElementException();
            }
        }
    }
}

