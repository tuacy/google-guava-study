package com.tuacy.guava.study.range;

import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import org.junit.Test;

/**
 * @name: RangeTest
 * @author: tuacy.
 * @date: 2019/7/15.
 * @version: 1.0
 * @Description:
 */
public class RangeTest {

    @Test
    public void boundType() {
        // [0, 10]的一个区间
        Range<Integer> range0 = Range.range(1, BoundType.CLOSED, 10, BoundType.CLOSED);
        System.out.println(range0.contains(1)); // true

        // (0, 10)的一个区间
        Range<Integer> range1 = Range.range(1, BoundType.OPEN, 10, BoundType.OPEN);
        System.out.println(range1.contains(1)); // false
    }

    @Test
    public void query() {
        Range.closedOpen(4, 4).isEmpty(); // returns true
        Range.openClosed(4, 4).isEmpty(); // returns true
        Range.closed(4, 4).isEmpty(); // returns false
//        Range.open(4, 4).isEmpty(); // Range.open throws IllegalArgumentException
        Range.closed(3, 10).lowerEndpoint(); // returns 3
        Range.open(3, 10).lowerEndpoint(); // returns 3
        Range.closed(3, 10).lowerBoundType(); // returns CLOSED
        Range.open(3, 10).upperBoundType(); // returns OPEN
    }

    @Test
    public void contain() {
        Range.closed(1, 3).contains(2);//return true
        Range.closed(1, 3).contains(4);//return false
        Range.lessThan(5).contains(5); //return false
        Range.closed(1, 4).containsAll(Ints.asList(1, 2, 3)); //return true
    }

    @Test
    public void enclose() {
        // (3, 6)
        Range<Integer> range0Parent = Range.open(3, 6);
        // [4, 5]
        Range<Integer> range0Child = Range.range(4, BoundType.CLOSED, 5, BoundType.CLOSED);
        System.out.println(range0Parent.encloses(range0Child)); // true

        // [3, 6]
        Range<Integer> range1Parent = Range.closed(3, 6);
        // [4, 4) -> 里面没有元素
        Range<Integer> range1Child = Range.closedOpen(4, 4);
        System.out.println(range1Parent.encloses(range1Child)); // true

        // [3, 6]
        Range<Integer> range2Parent = Range.closed(3, 6);
        // [4, 7)
        Range<Integer> range2Child = Range.closedOpen(4, 7);
        System.out.println(range2Parent.encloses(range2Child)); // false
    }

    @Test
    public void isConnected() {
        Range.closed(3, 5).isConnected(Range.open(5, 10)); // returns true
        Range.closed(0, 9).isConnected(Range.closed(3, 4)); // returns true
        Range.closed(0, 5).isConnected(Range.closed(3, 9)); // returns true
        Range.open(3, 5).isConnected(Range.open(5, 10)); // returns false
        Range.closed(1, 5).isConnected(Range.closed(6, 10)); // returns false
    }

    @Test
    public void intersection() {
        Range.closed(3, 5).intersection(Range.open(5, 10)); // returns (5, 5]
        Range.closed(0, 9).intersection(Range.closed(3, 4)); // returns [3, 4]
        Range.closed(0, 5).intersection(Range.closed(3, 9)); // returns [3, 5]
        Range.open(3, 5).intersection(Range.open(5, 10)); // throws IAE
        Range.closed(1, 5).intersection(Range.closed(6, 10)); // throws IAE
    }

    @Test
    public void span() {
        Range.closed(3, 5).span(Range.open(5, 10)); // returns [3, 10)
        Range.closed(0, 9).span(Range.closed(3, 4)); // returns [0, 9]
        Range.closed(0, 5).span(Range.closed(3, 9)); // returns [0, 9]
        Range.open(3, 5).span(Range.open(5, 10)); // returns (3, 10)
        Range.closed(1, 5).span(Range.closed(6, 10)); // returns [1, 10]
        Range.open(1, 3).span(Range.open(5, 10)); // return (1, 10)
    }

    @Test
    public void gap() {
        Range.closed(1, 5).gap(Range.closed(6, 10)); // returns (5, 6)
        Range.closed(1, 10).gap(Range.closed(6, 20)); // IllegalArgumentException
        Range.closed(1, 10).gap(Range.closed(-10, -5)); // IllegalArgumentException
    }

    @Test
    public void discreteDomain() {
        //set包含[2, 3, 4]
        ImmutableSortedSet set0 = ContiguousSet.create(Range.open(1, 5), DiscreteDomain.integers());
        //set包含[1, 2, ..., Integer.MAX_VALUE]
        ImmutableSortedSet set1 = ContiguousSet.create(Range.greaterThan(0), DiscreteDomain.integers());

        // canonical
        Range.open(1, 5).canonical(DiscreteDomain.integers());

    }

    @Test
    public void discreteDomain1() {
        // canonical
        Range<Integer> range = Range.closed(2, 10).canonical(new EvenInteger()); // range: 2、4、6、8、10
        System.out.println(range.contains(4)); // true
        System.out.println(range.contains(3)); // false
    }

    /**
     * 简单实现一个0到100的偶数的离散型结构
     */
    static class EvenInteger extends DiscreteDomain<Integer> {


        @Override
        public Integer next(Integer value) {
            Integer adapterValue = value % 2 == 0 ? value : value - 1;
            System.out.println("next = " + adapterValue);
            return adapterValue > 100 ? null : adapterValue + 2;
        }

        @Override
        public Integer previous(Integer value) {
            Integer adapterValue = value % 2 == 0 ? value : value - 1;
            System.out.println("previous = " + adapterValue);
            return adapterValue < 0 ? null : adapterValue - 2;
        }

        @Override
        public long distance(Integer start, Integer end) {
            Integer adapterStart = start % 2 == 0 ? start : start - 1;
            Integer adapterEnd = end % 2 == 0 ? end : end - 1;
            return adapterEnd - adapterStart;
        }

        @Override
        public Integer minValue() {
            return 0;
        }

        @Override
        public Integer maxValue() {
            return 100;
        }
    }

}
