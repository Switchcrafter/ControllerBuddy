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

import java.text.MessageFormat;

import de.bwravencl.controllerbuddy.input.Input;
import de.bwravencl.controllerbuddy.input.KeyStroke;
import de.bwravencl.controllerbuddy.input.action.annotation.ActionProperty;
import de.bwravencl.controllerbuddy.input.action.gui.DownUpEditorBuilder;
import de.bwravencl.controllerbuddy.input.action.gui.KeystrokeEditorBuilder;

abstract class ToKeyAction<V extends Number> extends DescribableAction<V> {

	@ActionProperty(label = "DOWN_UP", editorBuilder = DownUpEditorBuilder.class, order = 11)
	boolean downUp = false;

	@ActionProperty(label = "KEYSTROKE", editorBuilder = KeystrokeEditorBuilder.class, order = 10)
	KeyStroke keystroke = new KeyStroke();

	transient boolean wasUp = true;

	@Override
	public Object clone() throws CloneNotSupportedException {
		final var toKeyAction = (ToKeyAction<?>) super.clone();
		toKeyAction.setKeystroke((KeyStroke) keystroke.clone());

		return toKeyAction;
	}

	@Override
	public String getDescription(final Input input) {
		if (!isDescriptionEmpty())
			return super.getDescription(input);

		return MessageFormat.format(strings.getString("PRESS"), keystroke);
	}

	public KeyStroke getKeystroke() {
		return keystroke;
	}

	public boolean isDownUp() {
		return downUp;
	}

	void resetWasUp() {
		wasUp = false;
	}

	public void setDownUp(final boolean downUp) {
		this.downUp = downUp;
	}

	public void setKeystroke(final KeyStroke keystroke) {
		this.keystroke = keystroke;
	}
}
