package com.ssapick.server.core.service;

import com.nimbusds.jose.shaded.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SentenceSimilarityResponse {

	@SerializedName("value")
	private double value;

	@SerializedName("description")
	private String description;

}