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

/**
 * The class for all blocks that don't have block data.
 * 
 * @author MorbZ
 */
@SuppressWarnings("javadoc")
public enum SimpleBlock implements IBlock {
	AIR(Material.MATERIALS.get("AIR")),
	GRASS(Material.MATERIALS.get("GRASS")),
	COBBLESTONE(Material.MATERIALS.get("COBBLESTONE")),
	BEDROCK(Material.MATERIALS.get("BEDROCK")),
	WATER(Material.MATERIALS.get("WATER")),
	LAVA(Material.MATERIALS.get("LAVA")),
	GRAVEL(Material.MATERIALS.get("GRAVEL")),
	GOLD_ORE(Material.MATERIALS.get("GOLD_ORE")),
	IRON_ORE(Material.MATERIALS.get("IRON_ORE")),
	COAL_ORE(Material.MATERIALS.get("COAL_ORE")),
	GLASS(Material.MATERIALS.get("GLASS")),
	LAPIS_ORE(Material.MATERIALS.get("LAPIS_ORE")),
	LAPIS_BLOCK(Material.MATERIALS.get("LAPIS_BLOCK")),
	WEB(Material.MATERIALS.get("WEB")),
	DEADBUSH(Material.MATERIALS.get("DEADBUSH")),
	YELLOW_FLOWER(Material.MATERIALS.get("YELLOW_FLOWER")),
	BROWN_MUSHROOM(Material.MATERIALS.get("BROWN_MUSHROOM")),
	RED_MUSHROOM(Material.MATERIALS.get("RED_MUSHROOM")),
	GOLD_BLOCK(Material.MATERIALS.get("GOLD_BLOCK")),
	IRON_BLOCK(Material.MATERIALS.get("IRON_BLOCK")),
	BRICK_BLOCK(Material.MATERIALS.get("BRICK_BLOCK")),
	BOOKSHELF(Material.MATERIALS.get("BOOKSHELF")),
	MOSSY_COBBLESTONE(Material.MATERIALS.get("MOSSY_COBBLESTONE")),
	OBSIDIAN(Material.MATERIALS.get("OBSIDIAN")),
	DIAMOND_ORE(Material.MATERIALS.get("DIAMOND_ORE")),
	DIAMOND_BLOCK(Material.MATERIALS.get("DIAMOND_BLOCK")),
	CRAFTING_TABLE(Material.MATERIALS.get("CRAFTING_TABLE")),
	REDSTONE_ORE(Material.MATERIALS.get("REDSTONE_ORE")),
	LIT_REDSTONE_ORE(Material.MATERIALS.get("LIT_REDSTONE_ORE")),
	ICE(Material.MATERIALS.get("ICE")),
	SNOW(Material.MATERIALS.get("SNOW")),
	CLAY(Material.MATERIALS.get("CLAY")),
	FENCE(Material.MATERIALS.get("FENCE")),
	NETHERRACK(Material.MATERIALS.get("NETHERRACK")),
	SOUL_SAND(Material.MATERIALS.get("SOUL_SAND")),
	GLOWSTONE(Material.MATERIALS.get("GLOWSTONE")),
	PORTAL(Material.MATERIALS.get("PORTAL")),
	IRON_BARS(Material.MATERIALS.get("IRON_BARS")),
	GLASS_PANE(Material.MATERIALS.get("GLASS_PANE")),
	MELON_BLOCK(Material.MATERIALS.get("MELON_BLOCK")),
	MYCELIUM(Material.MATERIALS.get("MYCELIUM")),
	WATERLILY(Material.MATERIALS.get("WATERLILY")),
	NETHER_BRICK(Material.MATERIALS.get("NETHER_BRICK")),
	NETHER_BRICK_FENCE(Material.MATERIALS.get("NETHER_BRICK_FENCE")),
	END_STONE(Material.MATERIALS.get("END_STONE")),
	DRAGON_EGG(Material.MATERIALS.get("DRAGON_EGG")),
	REDSTONE_LAMP(Material.MATERIALS.get("REDSTONE_LAMP")),
	LIT_REDSTONE_LAMP(Material.MATERIALS.get("LIT_REDSTONE_LAMP")),
	EMERALD_ORE(Material.MATERIALS.get("EMERALD_ORE")),
	EMERALD_BLOCK(Material.MATERIALS.get("EMERALD_BLOCK")),
	REDSTONE_BLOCK(Material.MATERIALS.get("REDSTONE_BLOCK")),
	QUARTZ_ORE(Material.MATERIALS.get("QUARTZ_ORE")),
	SLIME_BLOCK(Material.MATERIALS.get("SLIME_BLOCK")),
	BARRIER(Material.MATERIALS.get("BARRIER")),
	SEA_LANTERN(Material.MATERIALS.get("SEA_LANTERN")),
	HARDENED_CLAY(Material.MATERIALS.get("HARDENED_CLAY")),
	COAL_BLOCK(Material.MATERIALS.get("COAL_BLOCK")),
	PACKED_ICE(Material.MATERIALS.get("PACKED_ICE")),
	SPRUCE_FENCE(Material.MATERIALS.get("SPRUCE_FENCE")),
	BIRCH_FENCE(Material.MATERIALS.get("BIRCH_FENCE")),
	JUNGLE_FENCE(Material.MATERIALS.get("JUNGLE_FENCE")),
	DARK_OAK_FENCE(Material.MATERIALS.get("DARK_OAK_FENCE")),
	ACACIA_FENCE(Material.MATERIALS.get("ACACIA_FENCE")),
	REDSTONE_WIRE(Material.MATERIALS.get("REDSTONE_WIRE")),
	CONCRETE(Material.MATERIALS.get("CONCRETE"));
	
	private Material material;
	private SimpleBlock(Material material) {
		this.material = material;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBlockId() {
		return material.getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockData() {
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTransparency() {
		return material.getTransparency();
	}

}
