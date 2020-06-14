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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class defines all the basic block with their IDs.
 * 
 * @author MorbZ
 */
@SuppressWarnings("javadoc")
public class Material {
	private int value;
	private int transparency;

	private Material(int value, int transparency) {
		this.value = value;
		this.transparency = transparency;
	}

	/**
	 * Returns the value of this material (block ID)
	 *
	 * @return The block ID
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns the transparency level of this material
	 *
	 * @return The transparency level
	 */
	public int getTransparency() {
		return transparency;
	}

	public static HashMap<String, Material> MATERIALS = new HashMap<String, Material>() {{
		put("AIR", new Material(0, 1)); // √
		put("STONE", new Material(1, 0)); // √
		put("GRASS", new Material(2, 0)); // √
		put("DIRT", new Material(3, 0)); // √
		put("COBBLESTONE", new Material(4, 0)); // √
		put("PLANKS", new Material(5, 0)); // √
		put("SAPLING", new Material(6, 1)); // ~ (Growth stage missing)
		put("BEDROCK", new Material(7, 0)); // √
		put("FLOWING_WATER", new Material(8, 2));
		put("WATER", new Material(9, 2)); // √
		put("FLOWING_LAVA", new Material(10, 1));
		put("LAVA", new Material(11, 1)); // √
		put("SAND", new Material(12, 0)); // √
		put("GRAVEL", new Material(13, 0)); // √
		put("GOLD_ORE", new Material(14, 0)); // √
		put("IRON_ORE", new Material(15, 0)); // √
		put("COAL_ORE", new Material(16, 0)); // √
		put("LOG", new Material(17, 0));
		put("LEAVES", new Material(18, 2)); // Diffuses sky light
		put("SPONGE", new Material(19, 0));
		put("GLASS", new Material(20, 1)); // √
		put("LAPIS_ORE", new Material(21, 0)); // √
		put("LAPIS_BLOCK", new Material(22, 0)); // √
		put("DISPENSER", new Material(23, 0));
		put("SANDSTONE", new Material(24, 0)); // √
		put("NOTEBLOCK", new Material(25, 0));
		put("BED", new Material(26, 1));
		put("GOLDEN_RAIL", new Material(27, 1)); // √
		put("DETECTOR_RAIL", new Material(28, 1)); // √
		put("STICKY_PISTON", new Material(29, 1));
		put("WEB", new Material(30, 2)); // √, Diffuses sky light
		put("TALLGRASS", new Material(31, 1));
		put("DEADBUSH", new Material(32, 1)); // √
		put("PISTON", new Material(33, 1));
		put("PISTON_HEAD", new Material(34, 1));
		put("WOOL", new Material(35, 0)); // √
		put("PISTON_EXTENSION", new Material(36, 1));
		put("YELLOW_FLOWER", new Material(37, 1)); // √ (Double implemented)
		put("RED_FLOWER", new Material(38, 1)); // √
		put("BROWN_MUSHROOM", new Material(39, 1)); // √
		put("RED_MUSHROOM", new Material(40, 1)); // √
		put("GOLD_BLOCK", new Material(41, 0)); // √
		put("IRON_BLOCK", new Material(42, 0)); // √
		put("DOUBLE_STONE_SLAB", new Material(43, 0));
		put("STONE_SLAB", new Material(44, 1));
		put("BRICK_BLOCK", new Material(45, 0)); // √
		put("TNT", new Material(46, 0));
		put("BOOKSHELF", new Material(47, 0)); // √
		put("MOSSY_COBBLESTONE", new Material(48, 0)); // √
		put("OBSIDIAN", new Material(49, 0)); // √
		put("TORCH", new Material(50, 1));
		put("FIRE", new Material(51, 1));
		put("MOB_SPAWNER", new Material(52, 0)); // Only graphical transparency
		put("OAK_STAIRS", new Material(53, 0)); // Partial transparency
		put("CHEST", new Material(54, 1));
		put("REDSTONE_WIRE", new Material(55, 1));
		put("DIAMOND_ORE", new Material(56, 0)); // √
		put("DIAMOND_BLOCK", new Material(57, 0)); // √
		put("CRAFTING_TABLE", new Material(58, 0)); // √
		put("WHEAT", new Material(59, 1));
		put("FARMLAND", new Material(60, 0)); // Partial transparency
		put("FURNACE", new Material(61, 0));
		put("LIT_FURNACE", new Material(62, 0));
		put("STANDING_SIGN", new Material(63, 1));
		put("WOODEN_DOOR", new Material(64, 1)); // √
		put("LADDER", new Material(65, 1));
		put("RAIL", new Material(66, 1)); // √
		put("STONE_STAIRS", new Material(67, 0)); // Partial transparency
		put("WALL_SIGN", new Material(68, 0));
		put("LEVER", new Material(69, 1));
		put("STONE_PRESSURE_PLATE", new Material(70, 1));
		put("IRON_DOOR", new Material(71, 1)); // √
		put("WOODEN_PRESSURE_PLATE", new Material(72, 1));
		put("REDSTONE_ORE", new Material(73, 0)); // √
		put("LIT_REDSTONE_ORE", new Material(74, 1)); // √
		put("UNLIT_REDSTONE_TORCH", new Material(75, 1)); // √
		put("REDSTONE_TORCH", new Material(76, 1)); // √
		put("STONE_BUTTON", new Material(77, 1));
		put("SNOW_LAYER", new Material(78, 1));
		put("ICE", new Material(79, 2)); // √
		put("SNOW", new Material(80, 1)); // √
		put("CACTUS", new Material(81, 1));
		put("CLAY", new Material(82, 0)); // √
		put("REEDS", new Material(83, 1));
		put("JUKEBOX", new Material(84, 0));
		put("FENCE", new Material(85, 1)); // √
		put("PUMPKIN", new Material(86, 0));
		put("NETHERRACK", new Material(87, 0)); // √
		put("SOUL_SAND", new Material(88, 0)); // √
		put("GLOWSTONE", new Material(89, 1)); // √
		put("PORTAL", new Material(90, 1)); // √
		put("LIT_PUMPKIN", new Material(91, 0));
		put("CAKE", new Material(92, 1));
		put("UNPOWERED_REPEATER", new Material(93, 1));
		put("POWERED_REPEATER", new Material(94, 1));
		put("STAINED_GLASS", new Material(95, 1)); // √
		put("TRAPDOOR", new Material(96, 1));
		put("MONSTER_EGG", new Material(97, 0));
		put("STONEBRICK", new Material(98, 0)); // √
		put("BROWN_MUSHROOM_BLOCK", new Material(99, 0));
		put("RED_MUSHROOM_BLOCK", new Material(100, 0));
		put("IRON_BARS", new Material(101, 1)); // √
		put("GLASS_PANE", new Material(102, 1)); // √
		put("MELON_BLOCK", new Material(103, 0)); // √
		put("PUMPKIN_STEM", new Material(104, 1));
		put("MELON_STEM", new Material(105, 1));
		put("VINE", new Material(106, 1));
		put("FENCE_GATE", new Material(107, 1));
		put("BRICK_STAIRS", new Material(108, 0)); // Partial transparency
		put("STONE_BRICK_STAIRS", new Material(109, 0)); // Partial transparency
		put("MYCELIUM", new Material(110, 0)); // √
		put("WATERLILY", new Material(111, 1)); // √
		put("NETHER_BRICK", new Material(112, 0)); // √
		put("NETHER_BRICK_FENCE", new Material(113, 1)); // √
		put("NETHER_BRICK_STAIRS", new Material(114, 0)); // Partial transparency
		put("NETHER_WART", new Material(115, 1));
		put("ENCHANTING_TABLE", new Material(116, 1));
		put("BREWING_STAND", new Material(117, 1));
		put("CAULDRON", new Material(118, 1));
		put("END_PORTAL", new Material(119, 1));
		put("END_PORTAL_FRAME", new Material(120, 0));
		put("END_STONE", new Material(121, 0)); // √
		put("DRAGON_EGG", new Material(122, 1)); // √
		put("REDSTONE_LAMP", new Material(123, 0)); // √
		put("LIT_REDSTONE_LAMP", new Material(124, 1)); // √
		put("DOUBLE_WOODEN_SLAB", new Material(125, 0));
		put("WOODEN_SLAB", new Material(126, 1));
		put("COCOA", new Material(127, 1));
		put("SANDSTONE_STAIRS", new Material(128, 0)); // Partial transparency
		put("EMERALD_ORE", new Material(129, 0)); // √
		put("ENDER_CHEST", new Material(130, 1));
		put("TRIPWIRE_HOOK", new Material(131, 1));
		put("TRIPWIRE", new Material(132, 1));
		put("EMERALD_BLOCK", new Material(133, 0)); // √
		put("SPRUCE_STAIRS", new Material(134, 0)); // Partial transparency
		put("BIRCH_STAIRS", new Material(135, 0)); // Partial transparency
		put("JUNGLE_STAIRS", new Material(136, 0)); // Partial transparency
		put("COMMAND_BLOCK", new Material(137, 0));
		put("BEACON", new Material(138, 1));
		put("COBBLESTONE_WALL", new Material(139, 1));
		put("FLOWER_POT", new Material(140, 1));
		put("CARROTS", new Material(141, 1));
		put("POTATOES", new Material(142, 1));
		put("WOODEN_BUTTON", new Material(143, 1));
		put("SKULL", new Material(144, 1));
		put("ANVIL", new Material(145, 1));
		put("TRAPPED_CHEST", new Material(146, 1));
		put("LIGHT_WEIGHTED_PRESSURE_PLATE", new Material(147, 1));
		put("HEAVY_WEIGHTED_PRESSURE_PLATE", new Material(148, 1));
		put("UNPOWERED_COMPARATOR", new Material(149, 1));
		put("POWERED_COMPARATOR", new Material(150, 1));
		put("DAYLIGHT_DETECTOR", new Material(151, 1));
		put("REDSTONE_BLOCK", new Material(152, 0)); // √, Partial transparency
		put("QUARTZ_ORE", new Material(153, 0)); // √
		put("HOPPER", new Material(154, 1));
		put("QUARTZ_BLOCK", new Material(155, 0)); // √
		put("QUARTZ_STAIRS", new Material(156, 0)); // Partial transparency
		put("ACTIVATOR_RAIL", new Material(157, 1)); // √
		put("DROPPER", new Material(158, 0));
		put("STAINED_HARDENED_CLAY", new Material(159, 0)); // √
		put("STAINED_GLASS_PANE", new Material(160, 1)); // √
		put("LEAVES2", new Material(161, 2)); // Diffuses sky light
		put("LOG2", new Material(162, 0));
		put("ACACIA_STAIRS", new Material(163, 0)); // Partial transparency
		put("DARK_OAK_STAIRS", new Material(164, 0)); // Partial transparency
		put("SLIME_BLOCK", new Material(165, 1)); // √
		put("BARRIER", new Material(166, 1)); // √
		put("IRON_TRAPDOOR", new Material(167, 1));
		put("PRISMARINE", new Material(168, 0)); // √
		put("SEA_LANTERN", new Material(169, 1)); // √, Transparency not clear
		put("HAY_BLOCK", new Material(170, 0));
		put("CARPET", new Material(171, 1)); // √
		put("HARDENED_CLAY", new Material(172, 0)); // √
		put("COAL_BLOCK", new Material(173, 0)); // √
		put("PACKED_ICE", new Material(174, 0)); // √
		put("DOUBLE_PLANT", new Material(175, 1)); // Transparency not clear
		put("STANDING_BANNER", new Material(176, 1));
		put("WALL_BANNER", new Material(177, 1));
		put("DAYLIGHT_DETECTOR_INVERTED", new Material(178, 1));
		put("RED_SANDSTONE", new Material(179, 0)); // √
		put("RED_SANDSTONE_STAIRS", new Material(180, 0)); // Partial transparency
		put("DOUBLE_STONE_SLAB2", new Material(181, 0));
		put("STONE_SLAB2", new Material(182, 1));
		put("SPRUCE_FENCE_GATE", new Material(183, 1));
		put("BIRCH_FENCE_GATE", new Material(184, 1));
		put("JUNGLE_FENCE_GATE", new Material(185, 1));
		put("DARK_OAK_FENCE_GATE", new Material(186, 1));
		put("ACACIA_FENCE_GATE", new Material(187, 1));
		put("SPRUCE_FENCE", new Material(188, 1)); // √
		put("BIRCH_FENCE", new Material(189, 1)); // √
		put("JUNGLE_FENCE", new Material(190, 1)); // √
		put("DARK_OAK_FENCE", new Material(191, 1)); // √
		put("ACACIA_FENCE", new Material(192, 1)); // √
		put("SPRUCE_DOOR", new Material(193, 1)); // √
		put("BIRCH_DOOR", new Material(194, 1)); // √
		put("JUNGLE_DOOR", new Material(195, 1)); // √
		put("ACACIA_DOOR", new Material(196, 1)); // √
		put("DARK_OAK_DOOR", new Material(197, 1)); // √
		put("CONCRETE", new Material(251, 0));
	}};
}
