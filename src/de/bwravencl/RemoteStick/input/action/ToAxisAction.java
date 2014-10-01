package de.bwravencl.RemoteStick.input.action;

import de.bwravencl.RemoteStick.input.Input;

public abstract class ToAxisAction extends InvertableAction {

	protected int axisId = Input.ID_AXIS_NONE;

	public int getAxisId() {
		return axisId;
	}

	public void setAxisId(int axisId) {
		this.axisId = axisId;
	}

}