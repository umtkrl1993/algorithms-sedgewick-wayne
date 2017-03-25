package chapter2.section4;

import util.ArrayUtil;

/**
 * Created by rene on 20/03/17.
 */
@SuppressWarnings("unchecked")
public class PriorityQueue<Key extends Comparable<Key>> {

    enum Orientation {
        MAX, MIN;
    }

    private Key[] priorityQueue; // heap-ordered complete binary tree
    private int size = 0; // in priorityQueue[1..n] with pq[0] unused
    private Orientation orientation;

    public PriorityQueue(int size, Orientation orientation) {
        priorityQueue = (Key[]) new Comparable[size + 1];
        this.orientation = orientation;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Key key) {
        if(size != priorityQueue.length - 1) {
            size++;

            priorityQueue[size] = key;
            swim(size);
        }
    }

    public Key deleteMaxOrMin() {
        if(size == 0) {
            throw new RuntimeException("Priority queue underflow");
        }

        size--;

        Key topElement = priorityQueue[1];
        ArrayUtil.exchange(priorityQueue, 1, size + 1);

        priorityQueue[size + 1] = null;
        sink(1);

        return topElement;
    }

    private void swim(int index) {
        while(index / 2 >= 1) {
            if((orientation == Orientation.MAX && ArrayUtil.less(priorityQueue[index / 2], priorityQueue[index]))
                    || (orientation == Orientation.MIN && !ArrayUtil.less(priorityQueue[index / 2], priorityQueue[index]))) {
                ArrayUtil.exchange(priorityQueue, index / 2, index);
            }

            index = index / 2;
        }
    }

    private void sink(int index) {
        while (index * 2 <= size) {
            int selectedChildIndex = index * 2;

            if(index * 2 + 1 <= size &&
                    (
                    (orientation == Orientation.MAX && ArrayUtil.less(priorityQueue[index * 2], priorityQueue[index * 2 + 1]))
                    || (orientation == Orientation.MIN && !ArrayUtil.less(priorityQueue[index * 2], priorityQueue[index * 2 + 1]))
                    )
                    ){
                selectedChildIndex = index * 2 + 1;
            }

            if((orientation == Orientation.MAX && ArrayUtil.less(priorityQueue[selectedChildIndex], priorityQueue[index]))
                || (orientation == Orientation.MIN && !ArrayUtil.less(priorityQueue[selectedChildIndex], priorityQueue[index]))) {
                break;
            } else {
                ArrayUtil.exchange(priorityQueue, index, selectedChildIndex);
            }

            index = selectedChildIndex;
        }
    }

}
