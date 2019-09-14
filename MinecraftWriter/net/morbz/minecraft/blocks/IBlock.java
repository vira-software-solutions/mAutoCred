package net.morbz.minecraft.blocks;

/*
* The MIT License (MIT)
* 
* Copyright (c) 2014-2015 Merten Peetz
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
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

import net.therealvira.minecraft.blocks.SimpleBlockAdvanced;

import java.util.HashMap;

/**
 * Interface for all blocks.
 * 
 * @author MorbZ
 */
public interface IBlock {
	public final static String DEFAULT_MC_PREFIX = "minecraft";

	/**
	 * Returns the block ID. That is the basic ID of the material without additional data.
	 * 
	 * @return The block ID
	 */
	public int getBlockId();
	
	/**
	 * Returns the block data. It can hold additional information about the block depending on the 
	 * material.
	 * 
	 * @return The block data. Only the 4 rightmost bits are relevant.
	 */
	public byte getBlockData();
	
	/**
	 * Returns the transparency level of this block. 0 means fully opaque, 1 means fully transparent
	 * and values > 1 mean transparent but the light level is decreased by n at this block.
	 * 
	 * @return The transparency level
	 */
	public int getTransparency();

	static HashMap<Integer, IBlock> AvailableBlocks = new HashMap<Integer, IBlock>();

	public static IBlock getBlockById(int id) {
		if(AvailableBlocks.isEmpty()){
			InitializeBlocks();
		}

		return AvailableBlocks.getOrDefault(id, null);
	}

	public static String getStringIDbyIntID(int id){
		for (var entry :
				Material.MATERIALS.entrySet()) {
			if(entry.getValue().getValue()==id){
				return DEFAULT_MC_PREFIX+":"+entry.getKey().toLowerCase();
			}
		}

		return "failed..";
	}

	private static void InitializeBlocks(){
		var simpleBlocks = (IBlock[])SimpleBlock.class.getEnumConstants();
		var advancedBlocks = (IBlock[])SimpleBlockAdvanced.class.getEnumConstants();

		for (IBlock block : simpleBlocks) {
			AvailableBlocks.put(block.getBlockId(), block);
		}

		for (IBlock block : advancedBlocks) {
			AvailableBlocks.put(block.getBlockId(), block);
		}
	}
}
