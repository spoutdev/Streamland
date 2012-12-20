/*
 * This file is part of Streamland.
 *
 * Copyright (c) 2012, Spout LLC <http://www.spout.org/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.spout.droplet.streamland;

import org.spout.api.Engine;
import org.spout.api.Spout;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.math.Quaternion;
import org.spout.api.math.Vector3;
import org.spout.api.plugin.CommonPlugin;

import org.spout.droplet.streamland.generator.StreamlandFlatGenerator;

public class StreamlandPlugin extends CommonPlugin {
	Engine engine;
	@Override
	public void onEnable() {
		engine = Spout.getEngine();

		World mainWorld = engine.loadWorld("streamland", new StreamlandFlatGenerator(10));

		Spout.getEngine().setDefaultWorld(mainWorld);
		if (mainWorld.getAge() <= 0) {
			mainWorld.setSpawnPoint(new Transform(new Point(mainWorld, 1, 3, 1), Quaternion.IDENTITY, Vector3.ONE));
		}
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stud
	}
}
