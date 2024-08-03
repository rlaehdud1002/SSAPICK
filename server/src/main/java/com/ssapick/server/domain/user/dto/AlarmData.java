package com.ssapick.server.domain.user.dto;

import com.ssapick.server.domain.user.entity.Alarm;

import lombok.Data;

public class AlarmData {

	@Data
	public static class Response {
		private boolean messageAlarm;
		private boolean nearbyAlarm;
		private boolean pickAlarm;
		private boolean addQuestionAlarm;

		public static Response fromEntity(Alarm alarm) {
			Response response = new Response();
			response.setMessageAlarm(alarm.isMessageAlarm());
			response.setNearbyAlarm(alarm.isNearbyAlarm());
			response.setPickAlarm(alarm.isPickAlarm());
			response.setAddQuestionAlarm(alarm.isAddQuestionAlarm());
			return response;
		}
	}

	@Data
	public static class Update {
		private boolean messageAlarm;
		private boolean nearbyAlarm;
		private boolean pickAlarm;
		private boolean addQuestionAlarm;
	}
}
