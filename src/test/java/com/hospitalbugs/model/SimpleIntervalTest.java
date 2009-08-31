package com.hospitalbugs.model;

import static com.hospitalbugs.model.SimpleInterval.instantInterval;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;


public class SimpleIntervalTest {

	@Test
	public void shouldReturnTrueIfPointIsContainedInInterval() {
		SimpleInterval<Integer> simpleInterval = new SimpleInterval<Integer>(10,20);
		assertThat(simpleInterval.contains(10), equalTo(true));
		assertThat(simpleInterval.contains(20), equalTo(false));
		assertThat(simpleInterval.contains(15), equalTo(true));
	}
	
	@Test
	public void shouldReturnTrueIfIntervalsOverlap() {
		assertOverlap(true,new SimpleInterval<Integer>(10,20), new SimpleInterval<Integer>(15,16));
		assertOverlap(true,new SimpleInterval<Integer>(5,15), new SimpleInterval<Integer>(10,20));
		assertOverlap(false,new SimpleInterval<Integer>(0,5), new SimpleInterval<Integer>(10,20));
	}
	
	private void assertOverlap(boolean expectedOverlap, SimpleInterval<Integer> a, SimpleInterval<Integer> b) {
		assertThat(assertOverlapMessage(a, b), a.overlaps(b), equalTo(expectedOverlap));
		assertThat(assertOverlapMessage(b, a), b.overlaps(a), equalTo(expectedOverlap));
	}

	private String assertOverlapMessage(SimpleInterval<Integer> c,	SimpleInterval<Integer> d) {
		return "for "+c+" overlaps "+d+" (c<d="+c.isBefore(d)+" c>d="+c.isAfter(d)+" d<c="+d.isBefore(c)+" d>c="+d.isAfter(c)+")";
	}

	@Test
	public void shouldReturnTrueForOverlapUsingZeroLengthIntervals() {
		SimpleInterval<Integer> thickInterval = new SimpleInterval<Integer>(10,20);
		SimpleInterval<Integer> instantIntervalAtStartOfThick = instantInterval(10);
		SimpleInterval<Integer> instantIntervalAtEndOfThick = instantInterval(20);
		
		assertOverlap(true, thickInterval, instantIntervalAtStartOfThick);
		assertOverlap(false,thickInterval, instantIntervalAtEndOfThick);
	}
	
	@Test
	public void shouldReturnFalseForOverlapIfIntervalsAbut() {
		assertOverlap(false,new SimpleInterval<Integer>(10,20), new SimpleInterval<Integer>(20,30));
	}
	
}
