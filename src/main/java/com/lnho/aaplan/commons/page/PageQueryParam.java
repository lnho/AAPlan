package com.lnho.aaplan.commons.page;

/**
 * User: littlehui
 * Date: 14-5-6
 * Time: 上午11:27
 */
public class PageQueryParam {

    /**
     * 不分页时最多查询前2000条。
     */
    private final static Integer DEFAULT_ROWS = 2000;

    private final static Integer DEFAULT_PATENO = 0;
    /**
     * 起始时间
     */
    protected Long startTime;
    /**
     * 结束时间
     */
    protected Long endTime;
    /**
     * 页号
     */
    private int pageNo;
    /**
     * 每页条数
     */
    private int pageSize;
    /**
     * 起始行号：通过页号及每页条数计算得出
     */
    private int rowStart;
    /**
     * 是否分页,当此参数为false时，查询时无视pageNo、pageSize、rowStart参数。
     */
    private boolean paged = true;


    private Integer totalHit;

    public Integer getTotalHit() {
        return totalHit;
    }

    public void setTotalHit(Integer totalHit) {
        this.totalHit = totalHit;
    }

    public Long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getPageNo() {
        if( pageNo <= 0){
            pageNo = 1;
        }
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        if(pageSize <= 0){
            pageSize = 15;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    public Integer getRowStart() {
        if( pageNo < 1){
            pageNo = 1;
        }
        return  (pageNo - 1) * pageSize;
    }

    public boolean isPaged() {
        return paged;
    }

    public void setPaged(boolean paged) {
        this.paged = paged;
        if (!paged) {
            //如果不是分页
            this.pageNo = DEFAULT_PATENO;
            this.pageSize = DEFAULT_ROWS;
        }
    }
}
