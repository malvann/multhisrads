package com.example.multhithradingconcurencyparfomance.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public
class Node<T> {
    private T val;
    private Node<T> next;
}
