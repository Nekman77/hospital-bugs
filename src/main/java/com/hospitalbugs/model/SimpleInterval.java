package com.hospitalbugs.model;

import static com.hospitalbugs.model.BoundClosure.CLOSED;
import static com.hospitalbugs.model.BoundClosure.OPEN;
import static com.hospitalbugs.model.BoundType.MAX;
import static com.hospitalbugs.model.BoundType.MIN;

import java.util.Comparator;


public class SimpleInterval<T extends Comparable<T>> {

	private final Bound<T> startBound, endBound;

	public SimpleInterval(T start, T end) {
		this(start,CLOSED, end, OPEN);
	}
	
	public SimpleInterval(T start, BoundClosure startClosure, T end, BoundClosure endClosure) {
		if (start.compareTo(end) > 0) {
			throw new IllegalArgumentException();
		}
		this.startBound = new Bound<T>(BoundTypeWithClosure.get(MIN, startClosure), start);
		this.endBound = new Bound<T>(BoundTypeWithClosure.get(MAX, endClosure), end);
	}
	
	public static <T extends Comparable<T>> SimpleInterval<T> instantInterval(T instant) {
		return new SimpleInterval<T>(instant, CLOSED, instant, CLOSED);
	}
	
	public T getStart() {
		return startBound.getValue();
	}
	
	public T getEnd() {
		return endBound.getValue();
	}
	
	public boolean contains(T point) {
		return startBound.encloses(point) && endBound.encloses(point);
	}

	public boolean isAfter(T point) {
		return !startBound.encloses(point);
	}
	
	public boolean isBefore(T point) {
		return !endBound.encloses(point);
	}
	
	public boolean isAfter(SimpleInterval<T> other) {
		return isAfter(other.getEnd());
	}
	
	public boolean isBefore(SimpleInterval<T> other) {
		return isBefore(other.getStart());
	}
	
	public boolean overlaps(SimpleInterval<T> other) {
		return !isBefore(other) && !other.isBefore(this);
	}

	
	@Override
	public String toString() {
		return startBound+" - "+ endBound;
	}

	
	public static class OverlapIsEqualityComparator<T extends Comparable<T>> implements Comparator<SimpleInterval<T>> {
		
		@SuppressWarnings("unchecked")
		private static OverlapIsEqualityComparator INSTANCE = new OverlapIsEqualityComparator();
		
	    @SuppressWarnings("unchecked")
	    public static final <T extends Comparable<T>> Comparator<SimpleInterval<T>> instance() {
	        return (Comparator<SimpleInterval<T>>) INSTANCE;
	    }

		@Override
		public int compare(SimpleInterval<T> o1, SimpleInterval<T> o2) {
			if (o1.overlaps(o2)) {
				return 0;
			}
			return o1.isBefore(o2)?-1:1;
		}
	}
}
