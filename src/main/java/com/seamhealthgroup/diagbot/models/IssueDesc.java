package com.seamhealthgroup.diagbot.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDesc {

	    @JsonProperty("Description") 
	    public String description;
	    @JsonProperty("DescriptionShort") 
	    public String descriptionShort;
	    @JsonProperty("MedicalCondition") 
	    public String medicalCondition;
	    @JsonProperty("Name") 
	    public String name;
	    @JsonProperty("PossibleSymptoms") 
	    public String possibleSymptoms;
	    @JsonProperty("ProfName") 
	    public String profName;
	    @JsonProperty("Synonyms") 
	    public Object synonyms;
	    @JsonProperty("TreatmentDescription") 
	    public String treatmentDescription;
	}
