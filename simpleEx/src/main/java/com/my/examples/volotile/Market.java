package com.my.examples.volotile;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Market {
    private volatile int plateStore = 0;
    private volatile int cupStore = 0;
    private volatile int reservePlates = 0;
    private volatile int reserveCups = 0;

    /**
     * add plates to store
     *
     * @param num - plates to add
     * @return plateStore size
     */
    public int addPlates(int num) {
        if (num <= reservePlates) {
            reservePlates -= num;
            return 0;
        } else {
            int rest = num - reservePlates;
            reservePlates = 0;
            plateStore += rest;
            return plateStore;
        }
    }

    public int addCups(int num) {
        if (num <= reserveCups) {
            reserveCups -= num;
            return 0;
        } else {
            int rest = num - reserveCups;
            reserveCups = 0;
            cupStore += rest;
            return cupStore;
        }
    }

    /**
     * remove plates from platesStore, if num > platesStore -> rest added to reservePlates
     *
     * @param num - plates to remove
     * @return fact num of removed plates
     */
    public int removePlates(int num) {
        if (plateStore < num) {
            int factRemoved = plateStore;
            reservePlates += num - plateStore;
            plateStore = 0;
            return factRemoved;
        } else {
            plateStore -= num;
            return num;
        }
    }

    public int removeCups(int num) {
        if (cupStore < num) {
            int factRemoved = cupStore;
            reserveCups += num - cupStore;
            cupStore = 0;
            return factRemoved;
        } else cupStore -= num;
        return num;
    }
}