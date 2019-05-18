package com.purvanovv.user_store.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purvanovv.user_store.constants.Constants;
import com.purvanovv.user_store.model.LogInfo;

import pascal_frequent_generator.AprioriFrequentItemsetGenerator;
import pascal_frequent_generator.FrequentItemsetData;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	public FileLoader fileLoader;

	@Override
	public List<LogInfo> getAllLogs() {
		return fileLoader.readLog();
	}

	@Override
	public List<LogInfo> getAllLogsForUser(int userId) {
		List<LogInfo> logs = fileLoader.readLog();
		List<LogInfo> result = new ArrayList<LogInfo>();
		logs.forEach(log -> {
			String description = log.getDescription();
			String regex = "id '([0-9]+)'";
			Pattern r = Pattern.compile(regex);
			Matcher m = r.matcher(description);

			if (m.find()) {
				int id = Integer.parseInt(m.group(1));
				if (id == userId) {
					result.add(log);
				}
			}
		});
		return result;
	}

	@Override
	public Set<String> getTopEvents() {
		List<LogInfo> log = fileLoader.readLog();
		List<Set<String>> itemsetList = new ArrayList<>();

		Map<String, List<String>> ipEvents = new HashMap<>();
		for (LogInfo logInfo : log) {
			String ip = logInfo.getIpAdress();
			String eventName = logInfo.getEventName();
			if (ipEvents.containsKey(ip)) {
				ipEvents.get(ip).add(eventName);
			} else {
				List<String> tempLog = new ArrayList<>();
				tempLog.add(eventName);
				ipEvents.put(ip, tempLog);
			}
		}

		for (Entry<String, List<String>> entry : ipEvents.entrySet()) {
			itemsetList.add(new HashSet<>(entry.getValue()));
		}

		AprioriFrequentItemsetGenerator<String> generator = new AprioriFrequentItemsetGenerator<>();
		FrequentItemsetData<String> frequendItemsets = generator.generate(itemsetList, Constants.MINIMUM_SUPPORT);

		return frequendItemsets.getFrequentItemsetList().get(frequendItemsets.getFrequentItemsetList().size() - 1);
	}

}
