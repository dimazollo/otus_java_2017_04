package ru.otus.homework;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * @Author Dmitry Volovod
 * created on 20.04.2017
 */
public class MyArrayList<T> implements List<T> {

    private static final int ORIGINAL_SIZE = 10;
    private Object[] array;
    private int size;


    public <T> MyArrayList() {
        array = new Object[ORIGINAL_SIZE];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        if (array.length == 0) return true;
        else return false;
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    public Iterator<T> iterator() {
        return new MyListIterator<>();
    }

    public Object[] toArray() {
        Object[] copyOfArray = new Object[array.length];
        System.arraycopy(array, 0, copyOfArray, 0, array.length);
        return copyOfArray;
    }

    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            Object[] copyOfArray = new Object[array.length];
            System.arraycopy(array, 0, copyOfArray, 0, array.length);
            return (T1[]) copyOfArray;
        }
        System.arraycopy(array, 0, a, 0, array.length);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    public boolean add(T t) {
        checkAndExpand(size + 1);
        array[size] = t;
        size++;
        return true;
    }

    private void checkAndExpand(int sizeForCheck) {
        if (array.length < sizeForCheck) {
            Object[] twiceBiggerArray = new Object[array.length << 1];
            System.arraycopy(array, 0, twiceBiggerArray, 0, array.length);
            array = twiceBiggerArray;
        }
    }

    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean addAll(Collection<? extends T> c) {
        for (T t : c) {
            this.add(t);
        }
        return true;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void replaceAll(UnaryOperator<T> operator) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Shell sort
    public void sort(Comparator<? super T> c) {
        int step = size / 2;
        int j;
        while (step > 0) {
            for (int i = 0; i < size - step; i++) {
                j = i;
                while (j >= 0 && c.compare((T) array[j], (T) array[j + step]) > 0) {
                    Object temp = array[j];
                    array[j] = array[j + step];
                    array[j + step] = temp;
                    j--;
                }
            }
            step = step / 2;
        }
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    public T get(int index) {
        return (T) array[index];
    }

    public T set(int index, T element) {
        Object oldValue = array[index];
        array[index] = element;
        return (T) oldValue;
    }

    public void add(int index, T element) {
        checkAndExpand(size + 1);
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    public T remove(int index) {
        Object removedValue = array[index];
        Object[] newArray = new Object[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, array.length - 1);
        array = newArray;
        size--;
        return (T) removedValue;
    }

    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o == null ? get(i) == null : o.equals(get(i))) {
                return i;
            }
        }
        // If element is not found in the list
        return -1;
    }

    public int lastIndexOf(Object o) {
        // Iterate from the end of array
        for (int i = size - 1; i >= 0; i--) {
            if (o == null ? get(i) == null : o.equals(get(i))) {
                return i;
            }
        }
        // If element is not found in the list
        return -1;
    }

    public ListIterator<T> listIterator() {
        return new MyListIterator<>();
    }

    public ListIterator<T> listIterator(int index) {
        MyListIterator<T> iterator = new MyListIterator<>();
        iterator.setCurrentIndex(index);
        return iterator;
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Spliterator<T> spliterator() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void trimToSize() {
        Object[] trimmedArray = new Object[this.size];
        System.arraycopy(array, 0, trimmedArray, 0, size);
        array = trimmedArray;
    }

    private class MyListIterator<T> implements ListIterator<T> {
        int currentIndex = 0;
        int previousStepIndex = 0;

        public void setCurrentIndex(int value) {
            currentIndex = value;
        }

        @Override
        public boolean hasNext() {
            return (currentIndex < size);
        }

        @Override
        public T next() {
            previousStepIndex = currentIndex;
            return (T) array[currentIndex++];
        }

        @Override
        public boolean hasPrevious() {
            return (currentIndex > 0);
        }

        @Override
        public T previous() {
            previousStepIndex = currentIndex;
            return (T) array[currentIndex--];
        }

        @Override
        public int nextIndex() {
            return currentIndex + 1;
        }

        @Override
        public int previousIndex() {
            return currentIndex - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented yet");
        }

        @Override
        public void set(T t) {
            array[previousStepIndex] = t;
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException("Not implemented yet");
        }
    }
}
