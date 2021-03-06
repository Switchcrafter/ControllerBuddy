/* Copyright (C) 2020  Matteo Hausner
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package de.bwravencl.controllerbuddy.input.action;

import static de.bwravencl.controllerbuddy.input.Input.normalize;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;

import de.bwravencl.controllerbuddy.input.Input;
import de.bwravencl.controllerbuddy.input.action.annotation.Action;
import de.bwravencl.controllerbuddy.input.action.annotation.Action.ActionCategory;
import de.bwravencl.controllerbuddy.input.action.annotation.ActionProperty;
import de.bwravencl.controllerbuddy.input.action.gui.DeadZoneEditorBuilder;
import de.bwravencl.controllerbuddy.input.action.gui.ExponentEditorBuilder;
import de.bwravencl.controllerbuddy.input.action.gui.MaxCursorSpeedEditorBuilder;

@Action(label = "TO_CURSOR_ACTION", category = ActionCategory.AXIS, order = 25)
public final class AxisToCursorAction extends ToCursorAction<Float> implements IAxisToAction {

	private static final float DEFAULT_DEAD_ZONE = 0.15f;
	private static final float DEFAULT_EXPONENT = 2f;
	private static final float DEFAULT_MAX_CURSOR_SPEED = 2000f;

	@ActionProperty(label = "DEAD_ZONE", editorBuilder = DeadZoneEditorBuilder.class, order = 13)
	private float deadZone = DEFAULT_DEAD_ZONE;

	@ActionProperty(label = "EXPONENT", editorBuilder = ExponentEditorBuilder.class, order = 12)
	private float exponent = DEFAULT_EXPONENT;

	@ActionProperty(label = "MAX_CURSOR_SPEED", editorBuilder = MaxCursorSpeedEditorBuilder.class, order = 11)
	private float maxCursorSpeed = DEFAULT_MAX_CURSOR_SPEED;

	@Override
	public void doAction(final Input input, final int component, final Float value) {
		final var absValue = abs(value);

		if (!input.isAxisSuspended(component) && absValue > deadZone) {
			final var inMax = (float) pow((1f - deadZone) * 100f, exponent);

			final var d = normalize(signum(value) * (float) pow((absValue - deadZone) * 100f, exponent), -inMax, inMax,
					-maxCursorSpeed, maxCursorSpeed) * input.getRateMultiplier();
			moveCursor(input, d);
		} else
			remainingD = 0f;
	}

	public float getDeadZone() {
		return deadZone;
	}

	public float getExponent() {
		return exponent;
	}

	public float getMaxCursorSpeed() {
		return maxCursorSpeed;
	}

	public void setDeadZone(final float deadZone) {
		this.deadZone = deadZone;
	}

	public void setExponent(final float exponent) {
		this.exponent = exponent;
	}

	public void setMaxCursorSpeed(final float maxCursorSpeed) {
		this.maxCursorSpeed = maxCursorSpeed;
	}
}
