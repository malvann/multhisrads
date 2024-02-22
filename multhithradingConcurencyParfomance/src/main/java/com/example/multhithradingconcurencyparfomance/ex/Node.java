package com.example.multhithradingconcurencyparfomance.ex;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class Node<T> {
    private T val;
    private Node<T> next;
}
