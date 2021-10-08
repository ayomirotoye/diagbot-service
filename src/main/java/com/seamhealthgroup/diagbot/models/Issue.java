package com.seamhealthgroup.diagbot.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Issue {
	@JsonProperty("ID")
	public int id;
	@JsonProperty("Name")
	public String name;
	@JsonProperty("ProfName")
	public String profName;
	@JsonProperty("Icd")
	public String icd;
	@JsonProperty("IcdName")
	public String icdName;
	@JsonProperty("Accuracy")
	public int accuracy;
}
