package com.lms.app.dto;

import java.util.List;

import com.lms.app.entity.Draw;

/**
 * DTO for Get Active Draws
 */
public class GetActiveDrawsResponse extends ResponseDTO {

	private List<Draw> draws;

	public List<Draw> getDraws() {
		return draws;
	}

	public void setDraws(List<Draw> draws) {
		this.draws = draws;
	}

}
