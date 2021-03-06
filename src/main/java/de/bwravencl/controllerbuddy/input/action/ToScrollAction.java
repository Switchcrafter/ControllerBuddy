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

import static de.bwravencl.controllerbuddy.gui.Main.strings;
import static java.lang.Math.round;

import java.text.MessageFormat;
import java.util.Locale;

import de.bwravencl.controllerbuddy.input.Input;
import de.bwravencl.controllerbuddy.input.action.annotation.ActionProperty;
import de.bwravencl.controllerbuddy.input.action.gui.ClicksEditorBuilder;

abstract class ToScrollAction<V extends Number> extends InvertableAction<V> {

	private static final int DEFAULT_CLICKS = 10;

	@ActionProperty(label = "CLICKS", editorBuilder = ClicksEditorBuilder.class, order = 10)
	int clicks = DEFAULT_CLICKS;

	transient float remainingD = 0f;

	public int getClicks() {
		return clicks;
	}

	@Override
	public String getDescription(final Input input) {
		if (!isDescriptionEmpty())
			return super.getDescription(input);

		return MessageFormat.format(strings.getString("SCROLL_DIRECTION"),
				strings.getString(invert ? "DIRECTION_DOWN" : "DIRECTION_UP").toLowerCase(Locale.ROOT));
	}

	void scroll(final Input input, float d) {
		d = invert ? -d : d;

		d += remainingD;

		if (d >= -1f && d <= 1f)
			remainingD = d;
		else {
			remainingD = 0f;

			input.setScrollClicks(round(d));
		}
	}

	public void setClicks(final int clicks) {
		this.clicks = clicks;
	}
}
