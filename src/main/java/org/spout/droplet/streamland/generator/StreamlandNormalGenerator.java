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
package org.spout.droplet.streamland.generator;

import net.royawesome.jlibnoise.NoiseQuality;
import net.royawesome.jlibnoise.module.source.Perlin;

import org.spout.api.generator.GeneratorPopulator;
import org.spout.api.generator.Populator;
import org.spout.api.generator.WorldGenerator;
import org.spout.api.geo.World;
import org.spout.api.geo.cuboid.Chunk;
import org.spout.api.math.MathHelper;
import org.spout.api.math.Vector3;
import org.spout.api.util.cuboid.CuboidBlockMaterialBuffer;
import org.spout.api.util.cuboid.CuboidShortBuffer;

import org.spout.droplet.streamland.material.StreamlandMaterials;

public class StreamlandNormalGenerator implements WorldGenerator {
	public static final int MAX_HEIGHT = 40;
	public static final int MIN_HEIGHT = 0;
	private static final int HEIGHT_HALF_DIFF = (MAX_HEIGHT - MIN_HEIGHT) / 2;
	private static final Perlin PERLIN = new Perlin();

	static {
		PERLIN.setFrequency(0.01);
		PERLIN.setLacunarity(2);
		PERLIN.setNoiseQuality(NoiseQuality.BEST);
		PERLIN.setOctaveCount(8);
		PERLIN.setPersistence(0.5);
	}

	@Override
	public void generate(CuboidBlockMaterialBuffer blockData, int x, int y, int z, World world) {
		x <<= 4;
		y <<= 4;
		z <<= 4;
		PERLIN.setSeed((int) world.getSeed());
		final Vector3 size = blockData.getSize();
		final int sizeX = size.getFloorX();
		final int sizeY = size.getFloorY();
		final int sizeZ = size.getFloorZ();
		for (int xx = 0; xx < sizeX; xx++) {
			for (int zz = 0; zz < sizeZ; zz++) {
				final int landHeight = MathHelper.floor(PERLIN.GetValue(x + xx, 0, z + zz) * HEIGHT_HALF_DIFF + HEIGHT_HALF_DIFF + MIN_HEIGHT);
				final int dirtHeight = MathHelper.floor((hash(x + xx, z + zz) >> 8 & 0xF) / 15f * 3) + 1;
				for (int yy = 0; yy < sizeY; yy++) {
					if (y + yy <= landHeight) {
						if (y + yy == landHeight) {
							blockData.set(x + xx, y + yy, z + zz, StreamlandMaterials.GRASS);
						} else if (y + yy >= landHeight - dirtHeight) {
							blockData.set(x + xx, y + yy, z + zz, StreamlandMaterials.DIRT);
						} else {
							blockData.set(x + xx, y + yy, z + zz, StreamlandMaterials.STONE);
						}
					}
				}
			}
		}
	}

	private static int hash(int x, int y) {
		int hash = x * 3422543 ^ y * 432959;
		return hash * hash * (hash + 324319);
	}

	@Override
	public int[][] getSurfaceHeight(World world, int chunkX, int chunkZ) {
		int[][] heights = new int[Chunk.BLOCKS.SIZE][Chunk.BLOCKS.SIZE];
		for (int x = 0; x < Chunk.BLOCKS.SIZE; x++) {
			for (int z = 0; z < Chunk.BLOCKS.SIZE; z++) {
				heights[x][z] = MAX_HEIGHT - 1;
			}
		}
		return heights;
	}

	@Override
	public Populator[] getPopulators() {
		return new Populator[0];
	}

	@Override
	public GeneratorPopulator[] getGeneratorPopulators() {
		return new GeneratorPopulator[0];
	}

	@Override
	public String getName() {
		return "Streamland_Normal";
	}
}
