package com.purvanovv.user_store.service;

import java.util.List;
import java.util.Set;

import com.purvanovv.user_store.model.LogInfo;

public interface LogService {
	public List<LogInfo> getAllLogs();

	public List<LogInfo> getAllLogsForUser(int userId);

	public Set<String> getTopEvents();
}
