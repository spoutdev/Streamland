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
package org.spout.droplet.streamland.command;

import com.bulletphysics.collision.shapes.BoxShape;

import org.spout.api.Client;
import org.spout.api.Spout;
import org.spout.api.command.Command;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandExecutor;
import org.spout.api.command.CommandSource;
import org.spout.api.component.impl.ModelComponent;
import org.spout.api.component.impl.PhysicsComponent;
import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;
import org.spout.api.exception.CommandException;

import org.spout.droplet.streamland.StreamlandPrefabs;
import org.spout.droplet.streamland.effect.RandomColorizerEntityEffect;

public class StreamlandInputExecutor implements CommandExecutor {
	@Override
	public void processCommand(CommandSource source, Command command, CommandContext args) throws CommandException {
		final Player player = ((Client) Spout.getEngine()).getActivePlayer();
		final String name = command.getPreferredName();
		Entity toSpawn;
		if (name.equalsIgnoreCase("+spawn_trex")) {
			Spout.log("Spawning T-Rex!");
			toSpawn = StreamlandPrefabs.TREX.createEntity(player.getTransform().getPosition());
		} else if (name.equalsIgnoreCase("+spawn_dragon")) {
			Spout.log("Spawning Dragon!");
			toSpawn = StreamlandPrefabs.DRAGON.createEntity(player.getTransform().getPosition());
		} else {
			throw new UnsupportedOperationException();
		}

		final PhysicsComponent physics = toSpawn.add(PhysicsComponent.class);
		physics.setMass(100f);
		physics.setCollisionShape(new BoxShape(5f, 5f, 5f));
		physics.setFriction(1f);
		physics.setDamping(.5f, 0f);
		physics.setRestitution(0.1f);
		//Colorize me!
		toSpawn.get(ModelComponent.class).getModel().getRenderMaterial().addEntityEffect(new RandomColorizerEntityEffect());
		player.getWorld().spawnEntity(toSpawn);
	}
}
