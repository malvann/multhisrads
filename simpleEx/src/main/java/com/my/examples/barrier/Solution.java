package com.my.examples.barrier;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class Solution {
    public static void main(String[] args) {
        int startPrice = 50;
        log.info("start price = {}", startPrice);
        int participateNumber = 5;
        Action action = new Action(participateNumber);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        do {
            int cash = random.nextInt(20);
            Participant participant = new Participant(cash, startPrice);
            action.add(participant);
            participant.start();
            participateNumber--;
        } while (participateNumber > 0);
    }
}
