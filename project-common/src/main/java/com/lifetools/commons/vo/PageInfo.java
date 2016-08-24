package com.lifetools.commons.vo;

/**
 * 页码信息(分页用的)
 * @author zk
 * @date 2016年1月20日 上午9:40:42
 */
public class PageInfo {
	/** 当前页码 */
	private int pageNum;
	/** 每页个数 */
	private int pageSize;
	/** 总个数 */
	private int totalCount;
	/** 最大页码 */
	private int maxPageNum;
	/** 是否有前一页 */
	private boolean hasPrevious;
	/** 是否有后一页 */
	private boolean hasNext;

	public PageInfo(int pageNum, int pageSize, int totalCount) {
		if (pageNum <= 0) {
			pageNum = 1;
		}
		if (pageSize <= 0) {
			pageSize = 20;
		}
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalCount = totalCount;

		int maxPageNum = totalCount / pageSize + 1;
		if (totalCount != 0 && totalCount % pageSize == 0) {
			maxPageNum--;
		}
		this.maxPageNum = maxPageNum;

		pageNum = pageNum > maxPageNum ? maxPageNum : pageNum;
		this.hasPrevious = pageNum > 1;
		this.hasNext = pageNum < maxPageNum;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getMaxPageNum() {
		return maxPageNum;
	}

	public void setMaxPageNum(int maxPageNum) {
		this.maxPageNum = maxPageNum;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
}
