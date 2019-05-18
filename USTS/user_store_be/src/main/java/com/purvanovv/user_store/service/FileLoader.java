package com.purvanovv.user_store.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.purvanovv.user_store.model.LogInfo;

@Component
public class FileLoader {
	public List<LogInfo> readLog() {

		ClassLoader classLoader = getClass().getClassLoader();

		String line = "";

		List<LogInfo> logs = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(classLoader.getResource("logs.csv").getPath()));

			int counter = 0;
			while ((line = br.readLine()) != null) {
				counter++;
				if (counter < 2) {
					continue;
				}
				String[] lineData = line.trim().split(",");
				LogInfo log = new LogInfo();
				log.setEventContext(lineData[2]);
				log.setComponent(lineData[3]);
				log.setEventName(lineData[4]);
				log.setDescription(lineData[5]);
				log.setOrigin(lineData[6]);
				log.setIpAdress(lineData[7]);

				logs.add(log);

			}
		} catch (Exception e) {
			// TODO throw custom exception
		}

		return logs;
	}

}
