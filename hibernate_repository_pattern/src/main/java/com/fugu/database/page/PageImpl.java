package com.fugu.database.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.fugu.database.sort.Sort;

/**
 * 建立 Page 
 *
 * @param <T>
 */
public class PageImpl<T> implements Page<T>, Serializable {

	private static final long serialVersionUID = 6889201666775227860L;
	
	private final long total;
	private final List<T> content = new ArrayList<T>();
	private final Pageable pageRequest;

	/**
	 * 使用與參數 {@link List} {@link Pageable} 與 {@link PageImpl} 建立資料來源為 {@link List} 的 {@link Page}
	 * 
	 * @param content 是使用 PageRequst query 後取得的 {@link List} 不可以是 {@literal null}
	 * @param pageable 可以是 {@literal null}
	 */
	public PageImpl(List<T> content, Pageable pageable) {		
		this(content, pageable, null == content ? 0 : content.size());
	}
	
	/**
	 * 使用與參數 {@link List} {@link Pageable} total 與 {@link PageImpl} 建立資料來源為 {@link List} 的 {@link Page}
	 * 
	 * @param content 是使用 PageRequst query 後取得的 {@link List} 不可以是 {@literal null}
	 * @param pageable 可以是 {@literal null}
	 * @param total 總資料筆數 可以是 {@literal null} 當 total 為 null 時使用 offset 與 content.size() 計算總資料筆數 
	 */
	public PageImpl(List<T> content, Pageable pageRequest, long total) {

		this.content.addAll(content);
		this.pageRequest = pageRequest;
		this.total = !content.isEmpty() && pageRequest != null && pageRequest.getOffset() + pageRequest.getPageSize() > total
				? pageRequest.getOffset() + content.size() : total;
	}

	/**
	 * 使用與參數 {@link List} 與 {@link PageImpl} 建立資料來源為 {@link List} 的 {@link Page}
	 * 
	 * @param content 不可以是 {@literal null}
	 */
	public PageImpl(List<T> content) {
		this(content, null, null == content ? 0 : content.size());
	}
	
	/**
	 * {@inheritDoc}}
	 */
	public int getNumber() {
		return pageRequest == null ? 0 : pageRequest.getPageNumber();
	}

	/**
	 * {@inheritDoc}}
	 */
	public int getSize() {
		return pageRequest == null ? 0 : pageRequest.getPageSize();
	}	

	/**
	 * {@inheritDoc}}
	 */
	public int getTotalPages() {
		return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
	}

	/**
	 * {@inheritDoc}}
	 */
	public long getTotalElements() {
		return total;
	}

	/**
	 * {@inheritDoc}}
	 */
	public int getNumberOfElements() {
		return content.size();
	}
	
	/**
	 * {@inheritDoc}}
	 */
	public boolean hasNext() {
		return getNumber() + 1 < getTotalPages();
	}

	/**
	 * {@inheritDoc}}
	 */
	public boolean hasPrevious() {
		return getNumber() > 0;
	}

	/**
	 * {@inheritDoc}}
	 */
	public boolean isFirst() {
		return !hasPrevious();
	}

	/**
	 * {@inheritDoc}}
	 */
	public boolean isLast() {
		return !hasNext();
	}

	/**
	 * {@inheritDoc}}
	 */
	public Pageable nextPageable() {
		return hasNext() ? pageRequest.next() : null;
	}

	/**
	 * {@inheritDoc}}
	 */
	public Pageable previousPageable() {

		if (hasPrevious()) {
			return pageRequest.previousOrFirst();
		}

		return null;
	}

	/**
	 * {@inheritDoc}}
	 */
	public boolean hasContent() {
		return !content.isEmpty();
	}

	/**
	 * {@inheritDoc}}
	 */
	public List<T> getContent() {
		return Collections.unmodifiableList(content);
	}

	/**
	 * {@inheritDoc}}
	 */
	public Sort getSort() {
		return pageRequest == null ? null : pageRequest.getSort();
	}

	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<T> iterator() {
		return content.iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		String contentType = "UNKNOWN";
		List<T> content = getContent();

		if (content.size() > 0) {
			contentType = content.get(0).getClass().getName();
		}

		return String.format("Page [number=%d, size=%d, TotalPages=%d, TotalElements=%d, hasNext=%s, NumberOfElements=%d, contentType=%s]"
				, getNumber(), getSize(), getTotalPages(), getTotalElements(), hasNext()?"有":"沒有", getNumberOfElements(), contentType);
	}	
}
