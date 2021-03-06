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

import de.bwravencl.controllerbuddy.input.Input;
import de.bwravencl.controllerbuddy.input.action.annotation.Action;

public interface IAction<V extends Number> extends Cloneable {

	static String getDefaultDescription(final IAction<?> action) {
		return getLabel(action.getClass());
	}

	static String getLabel(final Class<?> actionClass) {
		final var annotation = actionClass.getAnnotation(Action.class);
		if (annotation == null)
			throw new RuntimeException(
					actionClass.getName() + ": missing " + Action.class.getSimpleName() + " annotation");

		return strings.getString(annotation.label());
	}

	Object clone() throws CloneNotSupportedException;

	void doAction(final Input input, int component, V value);

	String getDescription(final Input input);
}
