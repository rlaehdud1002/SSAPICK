package com.ssapick.server.domain.pick.dto;

import com.ssapick.server.domain.pick.entity.HintOpen;

import lombok.Data;

public class HintData {
	@Data
	public static class Id{
		Long hintId;



		public static Id fromEntity(HintOpen hintOpen) {
			Id id = new Id();
			id.hintId = hintOpen.getId();
			return id;
		}
	}
}
