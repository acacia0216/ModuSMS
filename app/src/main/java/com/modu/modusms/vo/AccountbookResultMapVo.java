package com.modu.modusms.vo;

import java.util.List;

public class AccountbookResultMapVo {
    private List<AccountbookCategoryVo> categoryList;
    private List<AccountbookVo> accountList;
    private List<AccountbookCategoryVo> chartDataList;

    public AccountbookResultMapVo() {
    }

    public AccountbookResultMapVo(List<AccountbookCategoryVo> categoryList, List<AccountbookVo> accountList, List<AccountbookCategoryVo> chartDataList) {
        this.categoryList = categoryList;
        this.accountList = accountList;
        this.chartDataList = chartDataList;
    }

    public List<AccountbookCategoryVo> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<AccountbookCategoryVo> categoryList) {
        this.categoryList = categoryList;
    }

    public List<AccountbookVo> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<AccountbookVo> accountList) {
        this.accountList = accountList;
    }

    public List<AccountbookCategoryVo> getChartDataList() {
        return chartDataList;
    }

    public void setChartDataList(List<AccountbookCategoryVo> chartDataList) {
        this.chartDataList = chartDataList;
    }

    @Override
    public String toString() {
        return "AccountbookResultMapVo{" +
                "categoryList=" + categoryList +
                ", accountList=" + accountList +
                ", chartDataList=" + chartDataList +
                '}' + "\n";
    }
}
