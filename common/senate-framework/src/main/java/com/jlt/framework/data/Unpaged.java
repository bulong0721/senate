package com.jlt.framework.data;

enum Unpaged implements Pageable {

	INSTANCE;

	@Override
	public boolean isPaged() {
		return false;
	}

	@Override
	public Pageable previousOrFirst() {
		return this;
	}

	@Override
	public Pageable next() {
		return this;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}

	@Override
	public Sort getSort() {
		return Sort.unsorted();
	}

	@Override
	public int getPageSize() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getPageNumber() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getOffset() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Pageable first() {
		return this;
	}
}
