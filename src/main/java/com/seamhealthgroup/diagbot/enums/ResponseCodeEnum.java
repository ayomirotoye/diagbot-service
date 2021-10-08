package com.seamhealthgroup.diagbot.enums;

import java.util.Arrays;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

public enum ResponseCodeEnum {
	SUCCESSFUL("00", "Success", "Success"), FAILED("01", "Failed", "Failed"),
	FAILED_DO_NOT_REQUERY("01", "Failed from remote system. Do not re-query ",
			"Failed from remote system. Do not re-query "),
	FAILED_DO_REQUERY("02", "Failed from remote system. Re-query to get final status  ",
			"Failed from remote system. Re-query to get final status  "),
	INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY("03", " Internal system error. Do not re-query",
			" Internal system error. Do not re-query"),
	INTERNAL_SYSTEM_ERROR_REQUERY("04", " Internal system error. Do not re-query",
			" Internal system error. Do not re-query"),
	OTHERS("05", " Others", " Others"), NO_RECORD_FOUND("25", "No record found", ""),
	PENDING_TRANSACTION("100", "Pending/Initiated transaction", ""), BAD_REQUEST("400", "Bad request", ""),
	UNAUTHORIZED("401", "Unauthorized", ""), UNSUPPORTED_MEDIA_TYPE("405", "Unsupported media type", ""),
	METHOD_NOT_ALOWED("406", "Method not allowed", ""),
	SOCKET_TIMEOUT("503", "I/O timeout ... Please try again later", ""),
	ERROR_OCCURRED("500", "Internal service error or unknown error", ""),
	SERVICE_UNAVAILABLE("503", "Service provider endpoint is not reachable", "");

	@Getter
	@Setter
	public String responseCode;
	@Getter
	@Setter
	public String responseMessage;
	@Getter
	@Setter
	public String description;

	private ResponseCodeEnum(String responseCode, String responseMessage, String description) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.description = description;
	}

	public static HashMap<String, Object> getEnumValues() {
		ResponseCodeEnum[] enumArr = ResponseCodeEnum.values();
		HashMap<String, Object> myEnumMap = new HashMap<>();
		for (int i = 0; i < enumArr.length; i++) {
			ResponseCodeEnum myCode = enumArr[i];
			myEnumMap.put(myCode.getResponseCode(), myCode.getResponseMessage());
		}
		return myEnumMap;
	}

	public static ResponseCodeEnum getEnumFromValue(String data) {
		ResponseCodeEnum[] enumArr = ResponseCodeEnum.values();
		for (ResponseCodeEnum en : enumArr) {
			if (en.getResponseCode().equalsIgnoreCase(data) || en.getResponseMessage().equalsIgnoreCase(data)) {
				return en;
			}
		}
		return ResponseCodeEnum.FAILED_DO_NOT_REQUERY;
	}

	public static boolean arcaOkResponses(String codeToCheck) {
		if (Arrays.asList(new String[] { FAILED_DO_REQUERY.getResponseCode(), SUCCESSFUL.getResponseCode(),
				INTERNAL_SYSTEM_ERROR_REQUERY.getResponseCode() }).contains(codeToCheck)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isSuccessful(ResponseCodeEnum data) {
		return data == ResponseCodeEnum.SUCCESSFUL;
	}
}
