package com.example.multhithradingconcurencyparfomance.sync;

import com.japplis.virtually.sync.BlockLock;
import com.japplis.virtually.sync.Synchronized;

import java.time.LocalDateTime;
import java.util.List;

import static com.japplis.virtually.sync.SyncUtils.runSynchronized;

public class Solution {
    private final DatabaseRepo databaseRepo = new DatabaseRepo();
    private final BlockLock lock = new BlockLock();

    public List<LocalDateTime> getReservedDatesWithBlockLock(String userId) {
        try (var sync = lock.lockBlock()) {
            return databaseRepo.getDatesForUser(userId);
        }
    }

    public List<LocalDateTime> getReservedDatesWithSyncUtils(String userId) {
        return runSynchronized(() -> databaseRepo.getDatesForUser(userId));
    }

    @Synchronized
    public List<LocalDateTime> getReservedDatesWithAspectJ(String userId) {
        return databaseRepo.getDatesForUser(userId);
    }
}
