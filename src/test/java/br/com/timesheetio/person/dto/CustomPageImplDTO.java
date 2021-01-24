package br.com.timesheetio.person.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CustomPageImplDTO <T> implements Page<T> {
	
    private PageImpl<T> pageDelegate= new PageImpl<T>(new ArrayList<>());

    public CustomPageImplDTO() {}
    
    public CustomPageImplDTO(List<T> content) {
    	pageDelegate = new PageImpl<>(content);
    }
    
	@Override
	public int getNumber() {
		return pageDelegate.getNumber();
	}

	@Override
	public int getSize() {
		return pageDelegate.getSize();
	}

	@Override
	public int getNumberOfElements() {
		return pageDelegate.getNumberOfElements();
	}

	@Override
	public List<T> getContent() {
		return pageDelegate.getContent();
	}

	@Override
	public boolean hasContent() {
		return pageDelegate.hasContent();
	}

	@Override
	public Sort getSort() {
		return pageDelegate.getSort();
	}

	@Override
	public boolean isFirst() {
		return pageDelegate.isFirst();
	}

	@Override
	public boolean isLast() {
		return pageDelegate.isLast();
	}

	@Override
	public boolean hasNext() {
		return pageDelegate.hasNext();
	}

	@Override
	public boolean hasPrevious() {
		return pageDelegate.hasPrevious();
	}

	@Override
	public Pageable nextPageable() {
		return pageDelegate.nextPageable();
	}

	@Override
	public Pageable previousPageable() {
		return pageDelegate.previousPageable();
	}

	@Override
	public Iterator<T> iterator() {
		return pageDelegate.iterator();
	}

	@Override
	public int getTotalPages() {
		return pageDelegate.getTotalPages();
	}

	@Override
	public long getTotalElements() {
		return pageDelegate.getTotalElements();
	}

	@Override
	public <U> Page<U> map(Function<? super T, ? extends U> converter) {
		return pageDelegate.map(converter);
	}
}