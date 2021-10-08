package com.seamhealthgroup.diagbot.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthModel {
	@JsonProperty("Token")
	private String Token;
	@JsonProperty("ValidThrough")
	private Long ValidThrough;
}
