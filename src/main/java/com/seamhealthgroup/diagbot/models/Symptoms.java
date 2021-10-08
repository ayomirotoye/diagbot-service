package com.seamhealthgroup.diagbot.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Symptoms {
	@JsonProperty("ID")
	private int id;
	@JsonProperty("Name")
	private String name;
}
