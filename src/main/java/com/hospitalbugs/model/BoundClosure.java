package com.hospitalbugs.model;

public enum BoundClosure {
	OPEN(false), CLOSED(true);
	
	
	private final boolean satisfiedByEquality;

	BoundClosure(boolean satisfiedByEquality) {
		this.satisfiedByEquality = satisfiedByEquality;
	}
	
	public boolean isSatisfiedByEquality() {
		return satisfiedByEquality;
	}
}
