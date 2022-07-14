package com.example.projekatjava;

import java.util.ArrayList;

public class Pagination {

    private int itemsPerPage,lastPageItems,lastPage;
    private ArrayList<String> currencies;

    public Pagination(int itemsPerPage,ArrayList<String> currencies)
    {
        this.itemsPerPage=itemsPerPage;
        this.currencies=currencies;
        int totalItems=currencies.size();
        this.lastPage=totalItems/itemsPerPage;
        this.lastPageItems=totalItems % itemsPerPage;
    }
    public ArrayList<String> generateData(int currentPage)
    {
        int startItem=currentPage*itemsPerPage;
        ArrayList<String> newPageData=new ArrayList<>();
        if(currentPage==lastPage)
            for(int count=0;count<lastPageItems;count++)
                newPageData.add(currencies.get(startItem+count));
            else
            for(int count=0;count<itemsPerPage;count++)
                newPageData.add(currencies.get(startItem+count));
            return newPageData;
    }
    public int getLastPage()
    {
        return lastPage;
    }
}
