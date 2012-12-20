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
package org.spout.droplet.streamland.component;

import org.spout.api.Spout;
import org.spout.api.component.implementation.CameraComponent;
import org.spout.api.component.type.EntityComponent;
import org.spout.api.component.implementation.TransformComponent;
import org.spout.api.entity.Player;
import org.spout.api.entity.state.PlayerInputState;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.math.MathHelper;
import org.spout.api.math.Vector3;
import org.spout.api.plugin.Platform;

public class InputComponent extends EntityComponent {

	private Player player;
	private CameraComponent camera;

	@Override
	public void onAttached(){
		if(Spout.getPlatform() != Platform.CLIENT){
			throw new IllegalStateException("InputComponent is only for client");
		}

		if(!(getOwner() instanceof Player)){
			throw new IllegalStateException("InputComponent is only for player");
		}

		player = (Player) getOwner();
		camera = player.get(CameraComponent.class);
	}

	@Override
	public void onTick(float dt){
		PlayerInputState inputState = player.input();
		TransformComponent tc = player.getTransform();
		Transform ts = tc.getTransformLive();

		Vector3 offset = Vector3.ZERO;
		float speedMult = 5f;
		if (inputState.getForward()) {
			offset = offset.subtract(ts.forwardVector().multiply(camera.getSpeed()).multiply(dt).multiply(speedMult));
		}
		if (inputState.getBackward()) {
			offset = offset.add(ts.forwardVector().multiply(camera.getSpeed()).multiply(dt).multiply(speedMult));
		}
		if (inputState.getLeft()) {
			offset = offset.subtract(ts.rightVector().multiply(camera.getSpeed()).multiply(dt)).multiply(speedMult);
		}
		if (inputState.getRight()) {
			offset = offset.add(ts.rightVector().multiply(camera.getSpeed()).multiply(dt).multiply(speedMult));
		}
		if (inputState.getJump()) {
			offset = offset.add(ts.upVector().multiply(camera.getSpeed()).multiply(dt).multiply(speedMult));
		}
		if (inputState.getCrouch()) {
			offset = offset.subtract(ts.upVector().multiply(camera.getSpeed()).multiply(dt).multiply(speedMult));
		}
		tc.translateAndSetRotation(offset, MathHelper.rotation(inputState.pitch(), inputState.yaw(), ts.getRotation().getRoll()));
	}
}
