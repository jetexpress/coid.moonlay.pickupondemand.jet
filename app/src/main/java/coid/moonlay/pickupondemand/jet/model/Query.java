package coid.moonlay.pickupondemand.jet.model;

public class Query
{
    private Boolean asc;
    private Long count;
    private String keyword;
    private String orderBy;
    private String sort;
    private Long page;
    private Long size;
    private Long totalPage;

    public Boolean getAsc()
    {
        return asc;
    }

    public void setAsc(Boolean asc)
    {
        this.asc = asc;
    }

    public Long getCount()
    {
        return count;
    }

    public void setCount(Long count)
    {
        this.count = count;
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public String getOrderBy()
    {
        return orderBy;
    }

    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }

    public String getSort()
    {
        return sort;
    }

    public void setSort(String sort)
    {
        this.sort = sort;
    }

    public Long getPage()
    {
        return page;
    }

    public void setPage(Long page)
    {
        this.page = page;
    }

    public void addPage()
    {
        this.page += 1;
    }

    public Long getSize()
    {
        return size;
    }

    public void setSize(Long size)
    {
        this.size = size;
    }

    public Long getTotalPage()
    {
        return totalPage;
    }

    public void setTotalPage(Long totalPage)
    {
        this.totalPage = totalPage;
    }
}
