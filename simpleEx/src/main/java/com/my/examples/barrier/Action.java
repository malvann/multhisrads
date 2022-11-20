package com.my.examples.barrier;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

@Slf4j
public class Action {
    public static CyclicBarrier BARRIER;
    private List<Participant> participantList;

    public Action(int numOfParticipants) {
        this.participantList = new ArrayList<>();
        BARRIER = new CyclicBarrier(numOfParticipants, Action.this::defineWinner);
    }

    public boolean add(Participant participant){
        return participantList.add(participant);
    }

    public Participant defineWinner() {
        Participant winner = Collections.max(participantList, Comparator.comparingInt(Participant::getCurrentLotPrice));
        log.info("{} - win price: {}", winner, winner.getCurrentLotPrice());
        winner.setCash(winner.getCash() - winner.getCurrentLotPrice());
        return winner;
    }
}
