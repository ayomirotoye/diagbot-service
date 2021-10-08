package com.seamhealthgroup.diagbot.models;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.seamhealthgroup.diagbot.utilities.validations.Gender;
import com.seamhealthgroup.diagbot.utilities.validations.YearOfBirth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDiagnosis {
	@NotNull
	@NotEmpty
	private List<Integer> symptoms;
	@Gender
	private String gender;
	@YearOfBirth
	private Integer yearOfBirth;
}
