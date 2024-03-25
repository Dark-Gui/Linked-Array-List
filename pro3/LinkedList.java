public class LinkedList<T extends Comparable<T>> implements List<T> {
    private Node<T> head; // Reference to First Node
    private int size; // Size of the linked list
    private boolean isSorted; //Indicates whether the list is sorted

    public LinkedList() {
        head = null;
        size = 0;
        isSorted = true; //Sorted when Empty
    }

    public boolean add(T element) {
        if (element == null) {
            return false; //Not adding if Null
        }
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = newNode;
            size++;
            return true;//adds to the Beginning
        } else {
            Node<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);//Adds to the end
        }
        isSorted = checksSorted();
        size++;
        return true;
    }

    /**
     * Add an element at specific index. This method should
     * also shift the element currently at that position (if
     * any) and any subsequent elements to the right (adds
     * one to their indices). If element is null, or if index
     * index is out-of-bounds (index < 0 or index >= size_of_list),
     * it will NOT add it and return false. Otherwise it will
     * add it and return true. See size() for the definition
     * of size of list. Also updates isSorted variable to false if the
     * element added breaks the current sorted order.
     *
     * @param index   index at which to add the element.
     * @param element element to be added to the list.
     * @return if the addition was successful.
     */
    public boolean add(int index, T element) {
        if (element == null || index < 0 || index >= size) {
            return false; // Do not add null elements or out-of-bounds index
        }
        Node<T> newNode = new Node<>(element);
        if (index == 0) {
            // Special case: Add to the beginning of the list
            newNode.setNext(head);
            head = newNode;
        } else {
            Node<T> current = head.getNext();
            Node<T> previous = head;
            int currentIndex = 1;
            while (current != null && currentIndex < index) {
                previous = current;
                current = current.getNext();
                currentIndex++;
            }
            newNode.setNext(current);
            previous.setNext(newNode); // Add at the specified index
        }
        size++;
        isSorted = checksSorted();
        return true;
    }


    public void clear() {
        head = null;
        size = 0;
        this.isSorted = true;
    }

    /**
     * Return the element at given index. If index is
     * out-of-bounds, it will return null.
     *
     * @param index index to obtain from the list.
     * @return the element at the given index.
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node<T> temp = head;
        int count = 0;
        while (count < index) {
            temp = temp.getNext();
            count++;
        }
        return temp.getData();
    }

    /**
     * Return the first index of element in the list. If element
     * is null or not found in the list, return -1. If isSorted is
     * true, uses the ordering of the list to increase the efficiency
     * of the search.
     *
     * @param element element to be found in the list.
     * @return first index of the element in the list.
     */
    public int indexOf(T element) {
        if (element == null || size == 0) {
            return -1;
        }
        int i = 0;
        Node<T> cur = head;
        while (cur != null) {
            if (cur.getData().equals(element)) {
                // Element found at the current index
                return i;
            }
            cur = cur.getNext();
            i++;
        }
        return -1; // Element not found
    }


    /**
     * Return true if the list is empty and false otherwise.
     *
     * @return if the list is empty.
     */
    public boolean isEmpty() {
        if (head == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * size() return the number of elements in the list. Be careful
     * not to confuse this for the length of a list like for an ArrayList.
     * For example, if 4 elements are added to a list, size will return
     * 4, while the last index in the list will be 3. Another example
     * is that an ArrayList like [5, 2, 3, null, null] would have a size
     * of 3 for an ArrayList.
     * ArrayList and LinkedList hint: create a class variable in both ArrayList
     * and LinkedList to keep track of the sizes of the respective lists.
     *
     * @return size of the list.
     */
    public int size() {
        return size;
    }

    /**
     * Sort the elements of the list in ascending order using insertion sort.
     * If isSorted is true, do NOT re-sort.
     * Hint: Since T extends Comparable, you will find it useful
     * to use the public int compareTo(T other) method.
     * Updates isSorted accordingly.
     */
    public void sort() {
        if (isSorted || size == 0) {
            return;
        }
        Node<T> cur = head.getNext();
        Node<T> prev = head;
        if (cur == null) {
            isSorted = checksSorted();
        }
        for (prev = head; prev != null; prev = prev.getNext()) {
            for (cur = prev.getNext(); cur != null; cur = cur.getNext()) {
                if (prev.getData().compareTo(cur.getData()) > 0) {
                    T item = cur.getData();
                    cur.setData(prev.getData());
                    prev.setData(item);
                }
            }
        }
        isSorted = checksSorted();
    }

    /**
     * Remove whatever is at index 'index' in the list and return
     * it. If index is out-of-bounds, return null. For the ArrayList,
     * elements to the right of index should be shifted over to maintain
     * contiguous storage. Must check to see if the list is sorted after removal
     * of the element at the given index and updates isSorted accordingly.
     *
     * @param index position to remove from the list.
     * @return the removed element.
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        size--;
        Node<T> temp1 = head;
        Node<T> current = head.getNext();
        if (index == 0) {
            T hold = head.getData();
            head = current;
            isSorted = checksSorted();
            return hold;
        }
        int count = 0;
        while (count < index - 1) {
            temp1 = current;
            current = current.getNext();
            count++;
        }
        temp1.setNext(current.getNext());
        isSorted = checksSorted();
        return current.getData();
    }

    /**
     * Removes all elements of the list that are not equal to 'element'. If element is null, don't do anything.
     * When this function returns, the only elements that should be left in this list
     * are equal to 'element'. This method should not change the ordering of the list.
     * If the list is sorted, use this fact to increase the efficiency of this method.
     * This method should be done IN PLACE. Do not use any extra data structures to
     * solve this problem. (You are NOT allowed to create a new array for this function).
     * Updates isSorted accordingly.
     *
     * @param element type of element to be kept in the list.
     */
    public void equalTo(T element) {
        if (element == null || head == null) {
            // Do nothing if the element is null or the list is empty
            return;
        }
        Node<T> current = head;
        Node<T> prev = null;
        while (current != null) {
            if (!current.getData().equals(element)) {
                // Remove the node if the data is not equal to the specified element
                if (prev == null) {
                    // If the node to be removed is the head, update the head
                    head = current.getNext();
                } else {
                    prev.setNext(current.getNext());
                }
                size--;
            } else {
                prev = current;
            }
            current = current.getNext();
        }
        isSorted = checksSorted();
    }


    /**
     * Reverses the list IN PLACE. Any use of intermediate data structures will yield
     * your solution invalid.
     */
    public void reverse() {
        if (head == null || head.getNext() == null) {
            // If the list is empty or has only one element, it's already reversed.
            return;
        }
        Node prev = null;
        Node current = head;
        Node next;
        while (current != null) {
            next = current.getNext();
            current.setNext(prev);
            prev = current;
            current = next;
        }
        // After the loop, 'prev' will be the new head of the reversed list.
        head = prev;
        isSorted = checksSorted();
    }


    /**
     * Creates the intersection of 2 lists. The resulting intersection should be reflected in the calling List
     * If otherList is null, do not attempt to merge.
     * <p>
     * Sort should be called on the List that calls "intersect()", since the resulting list should be sorted.
     * Moreover, should also update isSorted to true.
     * Note, you will have to cast otherList from a List<T> type to a ArrayList<T> type or LinkedList<T>.
     * <p>
     * After checking for possible errors, you're first two lines of code should be:
     * LinkedList<T> other = (LinkedList<T>) otherList; or ArrayList<T> other = (Arraylist<T>) otherList;
     * sort();
     *
     * @param otherList list to be used for finding the intersection.
     */
    public void intersect(List<T> otherList) {
        if (otherList == null || !(otherList instanceof LinkedList)) {
            return;
        }
        LinkedList<T> other = (LinkedList<T>) otherList;
        sort();
        other.sort();
        Node<T> current = head;
        Node<T> otherCurrent = other.head;
        LinkedList<T> intersect = new LinkedList<>();

        while (current != null && otherCurrent != null) {
            int comparison = current.getData().compareTo(otherCurrent.getData());
            if (comparison == 0) {
                boolean dup = false;
                T val = current.getData();
                Node<T> intersectCurrent = intersect.head;
                while (intersectCurrent != null) {
                    if (val.compareTo(intersectCurrent.getData()) == 0) {
                        dup = true;
                        break;
                    }
                    intersectCurrent = intersectCurrent.getNext();
                }
                if (!dup) {
                    intersect.add(val);
                }

                current = current.getNext();
                otherCurrent = otherCurrent.getNext();
            } else if (comparison < 0) {
                current = current.getNext();
            } else {
                otherCurrent = otherCurrent.getNext();
            }
        }
        head = intersect.head;
        size = intersect.size;
        isSorted = intersect.isSorted;
        isSorted = checksSorted();
    }


    /**
     * Returns the minimum value of the List
     * Note, consider how sorting can effect or optimize this solution
     */
    public T getMin() {
        if (head == null) {
            return null;
        }
        if (isSorted) {
            return head.getData();
        } else {
            sort();
            return head.getData();
        }
    }

    /**
     * Returns the Maximum value of the List
     * Note, consider how sorting can effect or optimize this solution
     */
    public T getMax() {
        if (head == null) {
            return null;
        }
        if (isSorted) {
            reverse();
            return head.getData();
        } else {
            sort();
            reverse();
            return head.getData();
        }
    }

    public boolean isSorted() {
        return isSorted;
    }

    public boolean checksSorted() {
        if (head == null || head.getNext() == null) {
            return true; // An empty list or a list with a single element is always sorted
        }
        Node<T> current = head;
        while (current.getNext() != null) {
            if (current.getData().compareTo(current.getNext().getData()) > 0) {
                return false;
            }
            current = current.getNext();
        }
        return true;
    }


    public void print() {
        Node<T> temp = head;
        while (temp != null) {
            System.out.println(temp.getData());
            temp = temp.getNext();
        }
    }

    public static void main(String[] args) {
        LinkedList<Integer> intList = new LinkedList<>();
        intList.add(54);
        intList.add(27);
        intList.add(29);
        intList.add(35);
        intList.add(20);
        intList.add(24);
        intList.add(28);
        intList.add(1, 58);
        intList.remove(1);
        intList.sort();
        intList.print();

    }
}

