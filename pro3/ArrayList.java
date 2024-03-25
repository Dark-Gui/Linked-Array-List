public class ArrayList<T extends Comparable<T>> implements List<T> {
    private int size;
    public boolean isSorted;
    public T[] a = (T[]) new Comparable[size];
    public int arrayIndex;

    public ArrayList() {
        size = 2;
        isSorted = true;
        a = (T[]) new Comparable[size];
        arrayIndex = 0;
    }

    public boolean add(T element) {
        if (element == null) {
            return false;
        }
        if (arrayIndex == size) {

            int newSize = size * 2;
            T[] newArray = (T[]) new Comparable[newSize];


            for (int i = 0; i < arrayIndex; i++) {
                newArray[i] = a[i];
            }

            size = newSize;
            a = newArray;
        }
        a[arrayIndex] = element;
        arrayIndex++;
        isSorted = checksSorted();
        return true;
    }

    public boolean add(int index, T element) {
        if (element == null || index < 0 || index > size()) {
            return false;
        }
        // Ensure capacity
        if (arrayIndex == size) {
            int newSize = size * 2;
            T[] newArray = (T[]) new Comparable[newSize];
            // Copy elements up to the specified index
            for (int i = 0; i < index; i++) {
                newArray[i] = a[i];
            }
            newArray[index] = element;
            // Copy elements after the specified index
            for (int i = index; i < arrayIndex; i++) {
                newArray[i + 1] = a[i];
            }
            size = newSize;
            a = newArray;
        } else {
            // Shift elements to make room for the new element
            for (int i = arrayIndex; i > index; i--) {
                a[i] = a[i - 1];
            }
            a[index] = element;
        }
        arrayIndex++;
        // Update the sorted flag
        isSorted = checksSorted();
        return true;
    }


    public void clear() {
        size = 2;
        T[] b = (T[]) new Comparable[size];
        arrayIndex = 0;
        a = b;
        isSorted = true;
    }

    public T get(int index) {
        if (index < 0 || index >= arrayIndex) {
            return null;
        }
        return a[index];
    }


    public int indexOf(T element) {
        if (element == null) {
            return -1;
        }
        for (int i = 0; i < size(); i++) {
            if (a[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }


    public boolean isEmpty() {
        if (arrayIndex == 0) {
            return true;
        } else {
            return false;
        }
    }


    public int size() {
        return arrayIndex;
    }


    public void sort() {
        int counter = 0;
        for (int i = 1; i < arrayIndex; i++) {
            T key = a[i];
            int j = i - 1;

            while (j >= 0 && key.compareTo(a[j]) < 0) {
                counter++;
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
        isSorted = true;
    }


    public T remove(int index) {
        if (index < 0 || index >= arrayIndex) {
            return null;
        }

        T removedElement = a[index];

        // Shift elements to the left starting from the index
        for (int i = index; i < arrayIndex - 1; i++) {
            a[i] = a[i + 1];
        }

        // Set the last element to null
        a[arrayIndex - 1] = null;

        arrayIndex--;
        isSorted = checksSorted();
        return removedElement;
    }

    public void equalTo(T element) {
        int count = 0;
        int writeIndex = 0;
        for (int readIndex = 0; readIndex < arrayIndex; readIndex++) {
            if (a[readIndex].compareTo(element) == 0) {
                a[writeIndex++] = a[readIndex];
                count++;
            }
        }
        arrayIndex = count;
        isSorted = checksSorted();
    }


    public void reverse() {
        if (arrayIndex <= 1) {

            return;
        }

        int start = 0;
        int end = arrayIndex - 1;

        while (start < end) {
            T temp = a[start];
            a[start] = a[end];
            a[end] = temp;
            start++;
            end--;
        }
        isSorted = checksSorted();
    }


    public void intersect(List<T> otherList) {
        ArrayList<T> other = (ArrayList<T>) otherList;
        sort();

        int sizeInter = Math.max(this.size(), otherList.size());
        T[] intersect = (T[]) new Comparable[sizeInter];
        int index = 0;

        if (other != null) {
            for (int i = 0; i < size(); i++) {
                boolean dup = false;
                T val = this.get(i);

                for (int j = 0; j < other.size(); j++) {
                    T otherVal = other.get(j);
                    if (val.compareTo(otherVal) == 0) {
                        for (int k = 0; k < index; k++) {
                            if (val.compareTo(intersect[k]) == 0) {
                                dup = true;
                                break;
                            }
                        }
                        if (!dup) {
                            intersect[index] = val;
                            index++;
                        }
                        other.remove(j);
                        break;
                    }
                }
            }
            a = intersect;
        }
    }


    public T getMin() {
        if (arrayIndex == 0) {
            return null;
        }

        T min = a[0];
        for (int i = 1; i < arrayIndex; i++) {
            if (a[i].compareTo(min) < 0) {
                min = a[i];
            }
        }

        return min;
    }

    public T getMax() {
        if (arrayIndex == 0) {
            return null;
        }

        T max = a[0];
        for (int i = 1; i < arrayIndex; i++) {
            if (a[i].compareTo(max) > 0) {
                max = a[i];
            }
        }
        return max;
    }

    public boolean isSorted() {
        return isSorted;
    }

    public boolean checksSorted() {
        for (int i = 0; i < arrayIndex - 1; i++) {
            if (a[i] != null && a[i + 1] != null && a[i].compareTo(a[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }


    public void print() {
        for (int i = 0; i < arrayIndex; i++) {
            System.out.println(a[i]);
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> Array = new ArrayList<>();
        Array.add(30);
        Array.add(56);
        Array.add(98);
        Array.add(60);
        Array.add(54);
        Array.add(36);
        Array.add(5, 39);
        Array.print();
    }
}
