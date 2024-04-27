package com.dns.rpslottolandbackend.repository;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

import com.dns.rpslottolandbackend.dto.GameResult;
import com.dns.rpslottolandbackend.entity.ScoreEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Component
@AllArgsConstructor
public class PlayerScoreRepository {

	private final ScoreUpdateStrategy scoreUpdateStrategy;

	private final HashMap<String, ScoreEntity> userIdVsScore = new HashMap<>();

	private final ConcurrentHashMap<String, Lock> userIdVsLocks = new ConcurrentHashMap<>();

	public ScoreEntity updatePlayerScore(String userId, GameResult gameResult) {
		ReentrantLock newLock = new ReentrantLock(true);
		Lock lock = userIdVsLocks.putIfAbsent(userId, newLock);
		FunctionalLock functionalLock = tryLock(lock, newLock);
		if (functionalLock.locked) {
			try {
				ScoreEntity scoreEntity = userIdVsScore.getOrDefault(userId, new ScoreEntity());
				scoreUpdateStrategy.updateScore(gameResult, scoreEntity);
				userIdVsScore.put(userId, scoreEntity);
				return scoreEntity;
			} finally {
				functionalLock.lock.unlock();
			}
		}
		throw new RuntimeException("Unexpected error trying to acquire user lock");
	}

	private FunctionalLock tryLock(Lock lock, Lock newLock) {
		try {
			if (lock != null) {
				boolean locked = lock.tryLock(30, TimeUnit.SECONDS);
				return new FunctionalLock(locked, lock);
			} else {
				boolean locked = newLock.tryLock();
				return new FunctionalLock(locked, newLock);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new FunctionalLock(false, newLock);
		}
	}

	@AllArgsConstructor
	@Getter
	private class FunctionalLock {

		private final boolean locked;

		private final Lock lock;
	}

}
