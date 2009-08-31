package com.hospitalbugs.model;

import static org.junit.Assert.assertThat;

import org.joda.time.Interval;
import org.junit.Test;

import com.madgag.testsupport.matchers.IsMap;


public class PatientTest {

	@Test
	public void shouldDo() throws Exception {
		Patient patient = new Patient();
		Ward ward1 = new WardBuilder().toWard(), ward2= new WardBuilder().toWard();
		patient.addWardStay(ward1, new Interval(100, 107));
		patient.addWardStay(ward2, new Interval(200, 207));
		patient.addWardStay(ward1, new Interval(300, 307));
		
		assertThat(patient.getWardsOccupiedDuring(new Interval(100, 107)), IsMap.containingOnly(new Interval(100, 107), ward1));
		assertThat(patient.getWardsOccupiedDuring(new Interval(190, 210)), IsMap.containingOnly(new Interval(200, 207), ward2));
		assertThat(patient.getWardsOccupiedDuring(new Interval(100, 300)),
				IsMap.containingOnly(new Interval(100, 107), ward1).and(new Interval(200, 207), ward2));
	}
}
