package com.novaraspace.model.other;

import java.util.List;

public class PageResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public List<T> getContent() {
        return content;
    }

    public PageResponse<T> setContent(List<T> content) {
        this.content = content;
        return this;
    }

    public int getPage() {
        return page;
    }

    public PageResponse<T> setPage(int page) {
        this.page = page;
        return this;
    }

    public int getSize() {
        return size;
    }

    public PageResponse<T> setSize(int size) {
        this.size = size;
        return this;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public PageResponse<T> setTotalElements(long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public PageResponse<T> setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }
}
