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

import org.spout.api.generator.GeneratorPopulator;
import org.spout.api.generator.Populator;
import org.spout.api.generator.WorldGenerator;
import org.spout.api.geo.World;
import org.spout.api.geo.cuboid.Chunk;
import org.spout.api.util.cuboid.CuboidShortBuffer;

import org.spout.droplet.streamland.materials.StreamlandMaterials;

public class StreamlandFlatGenerator implements WorldGenerator {
	private final int height;

	public StreamlandFlatGenerator(int height) {
		this.height = height;
	}

	@Override
	public void generate(CuboidShortBuffer blockData, int chunkX, int chunkY, int chunkZ, World world) {
		int x = chunkX << 4, z = chunkZ << 4;
		for (int dx = x; dx < x + 16; ++dx) {
			for (int dz = z; dz < z + 16; ++dz) {
				final int startY = chunkY << Chunk.BLOCKS.BITS;
				final int endY = Math.min(Chunk.BLOCKS.SIZE + startY, height);
				for (int y = startY; y < endY; y++) {
					if (y <= 0) {
						blockData.set(dx, y, dz, StreamlandMaterials.STONE);
					} else {
						blockData.set(dx, y, dz, StreamlandMaterials.GRASS);
					}
				}
			}
		}
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
		return "Streamland_Flat";
	}

	@Override
	public int[][] getSurfaceHeight(World world, int chunkX, int chunkZ) {
		int[][] heights = new int[Chunk.BLOCKS.SIZE][Chunk.BLOCKS.SIZE];
		for (int x = 0; x < Chunk.BLOCKS.SIZE; x++) {
			for (int z = 0; z < Chunk.BLOCKS.SIZE; z++) {
				heights[x][z] = height - 1;
			}
		}
		return heights;
	}
}
