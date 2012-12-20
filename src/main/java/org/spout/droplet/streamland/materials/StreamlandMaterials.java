/*
 * This file is part of Streamland.
 *
 * Copyright (c) 2012-2012, Spout LLC <http://www.spout.org/>
 * Streamland is licensed under the Spout License Version 1.
 *
 * Streamland is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the Spout License Version 1.
 *
 * Streamland is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the Spout License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://spout.in/licensev1> for the full license, including
 * the MIT license.
 */
package org.spout.droplet.streamland.materials;

import org.spout.api.material.BlockMaterial;
import org.spout.api.material.basic.BasicSolid;



public class StreamlandMaterials {
	public static BlockMaterial GRASS = new BasicSolid("Streamland_Grass", "model://Spout/models/solidGreen.spm");
	public static BlockMaterial DIRT = new BasicSolid("Streamland_Dirt", "model://Spout/models/solidBrown.spm");
	public static BlockMaterial STONE = new BasicSolid("Streamland_Dirt", "model://Spout/models/solidGray.spm");
}
