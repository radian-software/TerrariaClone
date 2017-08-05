package org.terraframe

/**

TerraFrame (working title) [Pre-alpha 1.3]

developed by Radon Rosborough

Project mission: To program a 2D sandbox game similar to, but with many more
                 features than, Terraria.

**/

import java.awt.{BorderLayout, Color, Dimension, Font, Graphics, Graphics2D, GraphicsConfiguration, GraphicsEnvironment, Point, Rectangle, Transparency}
import java.awt.event._
import java.awt.image._
import java.io._
import java.{util => jul}
import javax.imageio.ImageIO
import javax.swing._
import javax.swing.event._

import scala.collection.JavaConverters._
import scala.util.control.NonFatal

/*

0    Air
1    Dirt
2    Stone
3    Copper Ore
4    Iron Ore
5    Silver Ore
6    Gold Ore
7    Wood
8    Workbench
9    Wooden Chest
10    Stone Chest
11    Copper Chest
12    Iron Chest
13    Silver Chest
14    Gold Chest
15    Tree
16    Leaves
17    Furnace
18    Coal
19    Lumenstone
20    Wooden Torch
21    Coal Torch
22    Lumenstone Torch
23    Furnace (on)
24    Wooden Torch (left wall)
25    Wooden Torch (right wall)
26    Coal Torch (left wall)
27    Coal Torch (right wall)
28    Lumenstone Torch (left wall)
29    Lumenstone Torch (right wall)
30    Tree (root)
31    Zinc Ore
32    Rhymestone Ore
33    Obdurite Ore
34    Aluminum Ore
35    Lead Ore
36    Uranium Ore
37    Zythium Ore
38    Zythium Ore (on)
39    Silicon Ore
40    Irradium Ore
41    Nullstone
42    Meltstone
43    Skystone
44    Magnetite Ore
45    Sand
46    Snow
47    Glass
48    Sunflower (stage 1)
49    Sunflower (stage 2)
50    Sunflower (stage 3)
51    Moonflower (stage 1)
52    Moonflower (stage 2)
53    Moonflower (stage 3)
54    Dryweed (stage 1)
55    Dryweed (stage 2)
56    Dryweed (stage 3)
57    Greenleaf (stage 1)
58    Greenleaf (stage 2)
59    Greenleaf (stage 3)
60    Frostleaf (stage 1)
61    Frostleaf (stage 2)
62    Frostleaf (stage 3)
63    Caveroot (stage 1)
64    Caveroot (stage 2)
65    Caveroot (stage 3)
66    Skyblossom (stage 1)
67    Skyblossom (stage 2)
68    Skyblossom (stage 3)
69    Void Rot (stage 1)
70    Void Rot (stage 2)
71    Void Rot (stage 3)
72    Grass
73    Jungle Grass
74    Swamp Grass
75    Mud
76    Sandstone
77    Marshleaf (stage 1)
78    Marshleaf (stage 2)
79    Marshleaf (stage 3)
80    Zinc Chest
81    Rhymestone Chest
82    Obdurite Chest
83    Tree (no bark)
84    Cobblestone
85    Chiseled Stone
86    Chiseled Cobblestone
87    Stone Bricks
88    Clay
89    Clay Bricks
90    Varnished Wood
91    Dirt (transparent)
92    Magnetite Ore (transparent)
93    Grass (transparent)
94    Zythium Wire
95    Zythium Wire (1 power)
96    Zythium Wire (2 power)
97    Zythium Wire (3 power)
98    Zythium Wire (4 power)
99    Zythium Wire (5 power)
100    Zythium Torch
101    Zythium Torch (left wall)
102    Zythium Torch (right wall)
103    Zythium Lamp
104    Zythium Lamp (on)
105    Lever
106    Lever (on)
107    Lever (left wall)
108    Lever (left wall, on)
109    Lever (right wall)
110    Lever (right wall, on)
111    Zythium Amplifier (right)
112    Zythium Amplifier (down)
113    Zythium Amplifier (left)
114    Zythium Amplifier (up)
115    Zythium Amplifier (right, on)
116    Zythium Amplifier (down, on)
117    Zythium Amplifier (left, on)
118    Zythium Amplifier (up, on)
119    Zythium Inverter (right)
120    Zythium Inverter (down)
121    Zythium Inverter (left)
122    Zythium Inverter (up)
123    Zythium Inverter (right, on)
124    Zythium Inverter (down, on)
125    Zythium Inverter (left, on)
126    Zythium Inverter (up, on)
127    Button (left)
128    Button (left, on)
129    Button (right)
130    Button (right, on)
131    Wooden Pressure Plate
132    Wooden Pressure Plate (on)
133    Stone Pressure Plate
134    Stone Pressure Plate (on)
135    Zythium Pressure Plate
136    Zythium Pressure Plate (on)
137    Zythium Delayer (1 delay, right)
138    Zythium Delayer (1 delay, down)
139    Zythium Delayer (1 delay, left)
140    Zythium Delayer (1 delay, up)
141    Zythium Delayer (1 delay, right, on)
142    Zythium Delayer (1 delay, down, on)
143    Zythium Delayer (1 delay, left, on)
144    Zythium Delayer (1 delay, up, on)
145    Zythium Delayer (2 delay, right)
146    Zythium Delayer (2 delay, down)
147    Zythium Delayer (2 delay, left)
148    Zythium Delayer (2 delay, up)
149    Zythium Delayer (2 delay, right, on)
150    Zythium Delayer (2 delay, down, on)
151    Zythium Delayer (2 delay, left, on)
152    Zythium Delayer (2 delay, up, on)
153    Zythium Delayer (4 delay, right)
154    Zythium Delayer (4 delay, down)
155    Zythium Delayer (4 delay, left)
156    Zythium Delayer (4 delay, up)
157    Zythium Delayer (4 delay, right, on)
158    Zythium Delayer (4 delay, down, on)
159    Zythium Delayer (4 delay, left, on)
160    Zythium Delayer (4 delay, up, on)
161    Zythium Delayer (8 delay, right)
162    Zythium Delayer (8 delay, down)
163    Zythium Delayer (8 delay, left)
164    Zythium Delayer (8 delay, up)
165    Zythium Delayer (8 delay, right, on)
166    Zythium Delayer (8 delay, down, on)
167    Zythium Delayer (8 delay, left, on)
168    Zythium Delayer (8 delay, up, on)

0    center
1    tdown_both
2    tdown_cw
3    tdown_ccw
4    tdown
5    tup_both
6    tup_cw
7    tup_ccw
8    tup
9    leftright
10    tright_both
11    tright_cw
12    tright_ccw
13    tright
14    upleftdiag
15    upleft
16    downleftdiag
17    downleft
18    left
19    tleft_both
20    tleft_cw
21    tleft_ccw
22    tleft
23    uprightdiag
24    upright
25    downrightdiag
26    downright
27    right
28    updown
29    up
30    down
31    single

0    None
1    Dirt/None downleft
2    Dirt/None downright
3    Dirt/None left
4    Dirt/None right
5    Dirt/None up
6    Dirt/None upleft
7    Dirt/None upright
8    Dirt
9    Stone/Dirt downleft
10    Stone/Dirt downright
11    Stone/Dirt left
12    Stone/Dirt right
13    Stone/Dirt up
14    Stone/Dirt upleft
15    Stone/Dirt upright
16    Stone
17    Stone/None down

0    Empty
1    Dirt
2    Stone
3    Copper Ore
4    Iron Ore
5    Silver Ore
6    Gold Ore
7    Copper Pick
8    Iron Pick
9    Silver Pick
10    Gold Pick
11    Copper Axe
12    Iron Axe
13    Silver Axe
14    Gold Axe
15    Wood
16    Copper Sword
17    Iron Sword
18    Silver Sword
19    Gold Sword
20    Workbench
21    Wooden Chest
22    Stone Chest
23    Copper Chest
24    Iron Chest
25    Silver Chest
26    Gold Chest
27    Furnace
28    Coal
29    Copper Ingot
30    Iron Ingot
31    Silver Ingot
32    Gold Ingot
33    Stone Lighter
34    Lumenstone
35    Wooden Torch
36    Coal Torch
37    Lumenstone Torch
38    Zinc Ore
39    Rhymestone Ore
40    Obdurite Ore
41    Aluminum Ore
42    Lead Ore
43    Uranium Ore
44    Zythium Ore
45    Silicon Ore
46    Irradium Ore
47    Nullstone
48    Meltstone
49    Skystone
50    Magnetite Ore
51    Zinc Pick
52    Zinc Axe
53    Zinc Sword
54    Rhymestone Pick
55    Rhymestone Axe
56    Rhymestone Sword
57    Obdurite Pick
58    Obdurite Axe
59    Obdurite Sword
60    Zinc Ingot
61    Rhymestone Ingot
62    Obdurite Ingot
63    Aluminum Ingot
64    Lead Ingot
65    Uranium Bar
66    Refined Uranium
67    Zythium Bar
68    Silicon Bar
69    Irradium Ingot
70    Nullstone Bar
71    Meltstone Bar
72    Skystone Bar
73    Magnetite Ingot
74    Sand
75    Snow
76    Glass
77    Sunflower Seeds
78    Sunflower
79    Moonflower Seeds
80    Moonflower
81    Dryweed Seeds
82    Dryweed
83    Greenleaf Seeds
84    Greenleaf
85    Frostleaf Seeds
86    Frostleaf
87    Caveroot Seeds
88    Caveroot
89    Skyblossom Seeds
90    Skyblossom
91    Void Rot Seeds
92    Void Rot
93    Mud
94    Sandstone
95    Marshleaf Seeds
96    Marshleaf
97    Blue Goo
98    Green Goo
99    Red Goo
100    Yellow Goo
101    Black Goo
102    White Goo
103    Astral Shard
104    Rotten Chunk
105    Copper Helmet
106    Copper Chestplate
107    Copper Leggings
108    Copper Greaves
109    Iron Helmet
110    Iron Chestplate
111    Iron Leggings
112    Iron Greaves
113    Silver Helmet
114    Silver Chestplate
115    Silver Leggings
116    Silver Greaves
117    Gold Helmet
118    Gold Chestplate
119    Gold Leggings
120    Gold Greaves
121    Zinc Helmet
122    Zinc Chestplate
123    Zinc Leggings
124    Zinc Greaves
125    Rhymestone Helmet
126    Rhymestone Chestplate
127    Rhymestone Leggings
128    Rhymestone Greaves
129    Obdurite Helmet
130    Obdurite Chestplate
131    Obdurite Leggings
132    Obdurite Greaves
133    Aluminum Helmet
134    Aluminum Chestplate
135    Aluminum Leggings
136    Aluminum Greaves
137    Lead Helmet
138    Lead Chestplate
139    Lead Leggings
140    Lead Greaves
141    Zythium Helmet
142    Zythium Chestplate
143    Zythium Leggings
144    Zythium Greaves
145    Aluminum Pick
146    Aluminum Axe
147    Aluminum Sword
148    Lead Pick
149    Lead Axe
150    Lead Sword
151    Zinc Chest
152    Rhymestone Chest
153    Obdurite Chest
154    Wooden Pick
155    Wooden Axe
156    Wooden Sword
157    Stone Pick
158    Stone Axe
159    Stone Sword
160    Bark
161    Cobblestone
162    Chiseled Stone
163    Chiseled Cobblestone
164    Stone Bricks
165    Clay
166    Clay Bricks
167    Varnish
168    Varnished Wood
169    Magnetite Pick
170    Magnetite Axe
171    Magnetite Sword
172    Irradium Pick
173    Irradium Axe
174    Irradium Sword
175    Zythium Wire
176    Zythium Torch
177    Zythium Lamp
178    Lever
179    Charcoal
180    Zythium Amplifier
181    Zythium Inverter
182    Button
183    Wooden Pressure Plate
184    Stone Pressure Plate
185    Zythium Pressure Plate
186    Zythium Delayer (1)
187    Zythium Delayer (2)
188    Zythium Delayer (4)
189    Zythium Delayer (8)
190    Wrench

*/

object TerraFrame {
    val config: GraphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration()
    var cic, armor: ItemCollection = _
    var WIDTH: Int = 2400
    var HEIGHT: Int = 2400

    val BLOCKSIZE: Int = 16
    val IMAGESIZE: Int = 8
    val CHUNKBLOCKS: Int = 96
    val CHUNKSIZE: Int = CHUNKBLOCKS*BLOCKSIZE
    val PLAYERSIZEX: Int = 20
    val PLAYERSIZEY: Int = 46
    val seed: Int = new jul.Random().nextInt()

    var random: jul.Random = new jul.Random(seed) // SEED

    val BRIGHTEST: Int = 21
    val PMAX: Int = 10

    val DEBUG_INSTAMINE: Boolean = false
    val DEBUG_ACCEL: Double = 1
    val DEBUG_NOCLIP: Boolean = false
    val DEBUG_LIGHT: Boolean = false
    val DEBUG_REACH: Boolean = true
    val DEBUG_PEACEFUL: Boolean = true
    val DEBUG_HOSTILE: Int = 1
    val DEBUG_F1: Boolean = false
    val DEBUG_SPEED: Boolean = true
    val DEBUG_FLIGHT: Boolean = true
    val DEBUG_INVINCIBLE: Boolean = true
    val DEBUG_HERBGROW: Int = 1
    val DEBUG_GRASSGROW: Int = 1
    val DEBUG_MOBTEST: String = null
    val DEBUG_STATS: Boolean = true
    val DEBUG_ITEMS: String = "testing"
    val DEBUG_GPLACE: Boolean = true

    var WORLDWIDTH: Int = WIDTH / CHUNKBLOCKS + 1
    var WORLDHEIGHT: Int = HEIGHT / CHUNKBLOCKS + 1

    val SUNLIGHTSPEED: Int = 14

    val items: Array[String] = Array("air", "blocks/dirt", "blocks/stone", "ores/copper_ore",
        "ores/iron_ore", "ores/silver_ore", "ores/gold_ore", "tools/copper_pick",
        "tools/iron_pick", "tools/silver_pick", "tools/gold_pick", "tools/copper_axe",
        "tools/iron_axe", "tools/silver_axe", "tools/gold_axe", "blocks/wood",
        "tools/copper_sword", "tools/iron_sword", "tools/silver_sword", "tools/gold_sword",
        "machines/workbench",
        "machines/wooden_chest", "machines/stone_chest",
        "machines/copper_chest", "machines/iron_chest", "machines/silver_chest", "machines/gold_chest",
        "machines/furnace",
        "ores/coal", "ingots/copper_ingot", "ingots/iron_ingot", "ingots/silver_ingot", "ingots/gold_ingot",
        "tools/stone_lighter", "ores/lumenstone",
        "torches/wooden_torch", "torches/coal_torch", "torches/lumenstone_torch",
        "ores/zinc_ore", "ores/rhymestone_ore", "ores/obdurite_ore",
        "ores/aluminum_ore", "ores/lead_ore", "ores/uranium_ore",
        "ores/zythium_ore", "ores/silicon_ore",
        "ores/irradium_ore", "ores/nullstone", "ores/meltstone", "ores/skystone",
        "ores/magnetite_ore",
        "tools/zinc_pick", "tools/zinc_axe", "tools/zinc_sword",
        "tools/rhymestone_pick", "tools/rhymestone_axe", "tools/rhymestone_sword",
        "tools/obdurite_pick", "tools/obdurite_axe", "tools/obdurite_sword",
        "ingots/zinc_ingot", "ingots/rhymestone_ingot", "ingots/obdurite_ingot",
        "ingots/aluminum_ingot", "ingots/lead_ingot", "ingots/uranium_bar", "ingots/refined_uranium",
        "ingots/zythium_bar", "ingots/silicon_bar",
        "ingots/irradium_ingot", "ingots/nullstone_bar", "ingots/meltstone_bar", "ingots/skystone_bar",
        "ingots/magnetite_ingot",
        "blocks/sand", "blocks/snow", "blocks/glass",
        "seeds/sunflower_seeds", "herbs/sunflower", "seeds/moonflower_seeds", "herbs/moonflower",
        "seeds/dryweed_seeds", "herbs/dryweed", "seeds/greenleaf_seeds", "herbs/greenleaf",
        "seeds/frostleaf_seeds", "herbs/frostleaf", "seeds/caveroot_seeds", "herbs/caveroot",
        "seeds/skyblossom_seeds", "herbs/skyblossom", "seeds/void_rot_seeds", "herbs/void_rot",
        "blocks/mud", "blocks/sandstone",
        "seeds/marshleaf_seeds", "herbs/marshleaf",
        "goo/blue_goo", "goo/green_goo", "goo/red_goo", "goo/yellow_goo", "goo/black_goo", "goo/white_goo",
        "goo/astral_shard", "goo/rotten_chunk",
        "armor/copper_helmet", "armor/copper_chestplate", "armor/copper_leggings", "armor/copper_greaves",
        "armor/iron_helmet", "armor/iron_chestplate", "armor/iron_leggings", "armor/iron_greaves",
        "armor/silver_helmet", "armor/silver_chestplate", "armor/silver_leggings", "armor/silver_greaves",
        "armor/gold_helmet", "armor/gold_chestplate", "armor/gold_leggings", "armor/gold_greaves",
        "armor/zinc_helmet", "armor/zinc_chestplate", "armor/zinc_leggings", "armor/zinc_greaves",
        "armor/rhymestone_helmet", "armor/rhymestone_chestplate", "armor/rhymestone_leggings", "armor/rhymestone_greaves",
        "armor/obdurite_helmet", "armor/obdurite_chestplate", "armor/obdurite_leggings", "armor/obdurite_greaves",
        "armor/aluminum_helmet", "armor/aluminum_chestplate", "armor/aluminum_leggings", "armor/aluminum_greaves",
        "armor/lead_helmet", "armor/lead_chestplate", "armor/lead_leggings", "armor/lead_greaves",
        "armor/zythium_helmet", "armor/zythium_chestplate", "armor/zythium_leggings", "armor/zythium_greaves",
        "tools/aluminum_pick", "tools/aluminum_axe", "tools/aluminum_sword",
        "tools/lead_pick", "tools/lead_axe", "tools/lead_sword",
        "machines/zinc_chest", "machines/rhymestone_chest", "machines/obdurite_chest",
        "tools/wooden_pick", "tools/wooden_axe", "tools/wooden_sword",
        "tools/stone_pick", "tools/stone_axe", "tools/stone_sword",
        "goo/bark", "blocks/cobblestone",
        "blocks/chiseled_stone", "blocks/chiseled_cobblestone", "blocks/stone_bricks",
        "blocks/clay", "blocks/clay_bricks", "potions/varnish", "blocks/varnished_wood",
        "tools/magnetite_pick", "tools/magnetite_axe", "tools/magnetite_sword",
        "tools/irradium_pick", "tools/irradium_axe", "tools/irradium_sword",
        "wires/zythium_wire", "torches/zythium_torch", "blocks/zythium_lamp", "misc/lever",
        "dust/charcoal", "wires/zythium_amplifier", "wires/zythium_inverter", "misc/button",
        "misc/wooden_pressure_plate", "misc/stone_pressure_plate", "misc/zythium_pressure_plate",
        "wires/zythium_delayer_1", "wires/zythium_delayer_2", "wires/zythium_delayer_4", "wires/zythium_delayer_8", "tools/wrench")

    var version: String = "0.3_01"

    val blockcds: Array[Boolean] = Array(false, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)
    val solid: Array[Boolean] = Array(false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, true, true, true, false, false, false, true, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)
    val ltrans: Array[Boolean] = Array(false, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, false, false, false, false, false, false, false, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)
    val powers: Array[Boolean] = Array(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, true, false, true, false, true, false, false, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, true, false, true, false, true, false, true, false, true, false, false, false, false, true, true, true, true, false, false, false, false, true, true, true, true, false, false, false, false, true, true, true, true, false, false, false, false, true, true, true, true)
    val conducts: Array[Double] = Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0, 0, 0, -1, -1, -1, 0, -1, 0, -1, 0, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val receives: Array[Boolean] = Array(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, false, false, false, true, true, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)
    val wirec: Array[Boolean] = Array(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)
    val skycolors: Array[Int] = Array(28800, 28980, 29160, 29340, 29520, 29700, 29880, 30060, 30240, 30420, 30600, 30780, 30960, 31140, 31320, 31500, 31680, 31860, 32040, 32220, 72000, 72180, 72360, 72540, 72720, 72900, 73080, 73260, 73440, 73620, 73800, 73980, 74160, 74340, 74520, 74700, 74880, 75060, 75240, 75420)

    var backgroundImgs: Map[Byte, BufferedImage] = _
    var itemImgs: Map[Short, BufferedImage] = _
    var DURABILITY: Map[Short, Map[Int, Int]] = _
    var dur: Map[Int,Int] = _
    var BLOCKTOOLS: Map[Int, Array[Short]] = _
    var TOOLSPEED: Map[Short, Double] = _
    var TOOLDAMAGE: Map[Short, Int] = _
    var BLOCKDROPS: Map[Int, Short] = _
    var ITEMBLOCKS: Map[Short,Int] = _
    var OUTLINES: Map[Int, String] = _
    var UIBLOCKS: Map[String, String] = _
    var UIENTITIES: Map[String, String] = _
    var BLOCKCD: Map[Int, Boolean] = _
    var MAXSTACKS: Map[Short, Short] = _
    var SKYLIGHTS: Map[Int, Int] = _
    var SKYCOLORS: Map[Int, Color] = _
    var LIGHTLEVELS: Map[Int,BufferedImage] = _
    var blockImgs: Map[String, BufferedImage] = _
    var outlineImgs: Map[String, BufferedImage] = _
    var BLOCKLIGHTS: Map[Int, Int] = _
    var GRASSDIRT: Map[Int, Int] = _
    var ARMOR: Map[Short, Int] = _
    var TOOLDURS: Map[Short, Short] = _
    var FUELS: Map[Short, Double] = _
    var WIREP: Map[Int, Int] = _
    var TORCHESL: Map[Int, Int] = _
    var TORCHESR: Map[Int, Int] = _
    var TORCHESB: Map[Int, Boolean] = _
    var GSUPPORT: Map[Int, Boolean] = _
    var FSPEED: Map[Short, Double] = _
    var DDELAY: Map[Int, Int] = _

    var log: BufferedWriter = _

    def main(args: Array[String]): Unit = {
        val f = new JFrame("TerraFrame: Infinite worlds!")
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        f.setVisible(true)

        val ap = new TerraFrame()
        ap.setFocusable(true)
        f.add("Center", ap)
        f.pack()

        ap.init()
    }

    def hasOpenSpace(x: Int, y: Int, blocks: Array2D[Int]): Boolean = {
        blocks(y - 1)(x - 1) == 0 || !blockcds(blocks(y - 1)(x - 1)) ||
          blocks(y - 1)(x) == 0 || !blockcds(blocks(y - 1)(x)) ||
          blocks(y - 1)(x + 1) == 0 || !blockcds(blocks(y - 1)(x + 1)) ||
          blocks(y)(x - 1) == 0 || !blockcds(blocks(y)(x - 1)) ||
          blocks(y)(x + 1) == 0 || !blockcds(blocks(y)(x + 1)) ||
          blocks(y + 1)(x - 1) == 0 || !blockcds(blocks(y + 1)(x - 1)) ||
          blocks(y + 1)(x) == 0 || !blockcds(blocks(y + 1)(x)) ||
          blocks(y + 1)(x + 1) == 0 || !blockcds(blocks(y + 1)(x + 1))
    }


    def loadImage(path: String): BufferedImage = {
        val url: InputStream = getClass.getResourceAsStream(path)
        var image: BufferedImage = null
        try {
            image = ImageIO.read(url)
        }
        catch  {
            case NonFatal(_) =>
        }
        
        image
    }

    def getBLOCKSIZE(): Int = {
        return BLOCKSIZE
    }

    def getIMAGESIZE(): Int = {
        return IMAGESIZE
    }

    def getPLAYERSIZEX(): Int = {
        return PLAYERSIZEX
    }

    def getPLAYERSIZEY(): Int = {
        return PLAYERSIZEY
    }

    def getBLOCKCDS(): Array[Boolean] = {
        return blockcds
    }

    def getBLOCKCD() : Map[Int,Boolean] = {
        return BLOCKCD
    }

    def getTOOLDURS(): Map[Short,Short] = {
        return TOOLDURS
    }

    def getMAXSTACKS(): Map[Short,Short] = {
        return MAXSTACKS
    }

    def getRandom(): jul.Random = {
        return random
    }

    def getItemImgs(): Map[Short,BufferedImage] = {
        return itemImgs
    }

    def getItems(): Array[String] = {
        return items
    }

    def getARMOR(): Map[Short,Int] = {
        return ARMOR
    }

    def postError(e: Throwable): Unit = {
        val sb = new StringBuilder()
        sb.append("Exception in thread " + e.getClass().getName())
        if (e.getMessage() != null) {
            sb.append(": ")
            sb.append(e.getMessage())
        }
        e.getStackTrace().foreach { ste =>
            sb.append("\n        at " + ste.toString())
        }
        try {
            log = new BufferedWriter(new FileWriter("log.txt"))
            log.write(sb.toString())
            log.close()
        }
        catch {
            case _: IOException =>
        }
        finally {
            println(sb.toString())
        }
    }

    def max(a: Int, b: Int, c: Int): Int = {
        return Math.max(Math.max(a, b), c)
    }

    def max(a: Float, b: Float, c: Float): Float = {
        return Math.max(Math.max(a, b), c)
    }

    def max(a: Double, b: Double, c: Double): Double = {
        return Math.max(Math.max(a, b), c)
    }

    def max(a: Int, b: Int, c: Int, d: Int): Int = {
        return Math.max(Math.max(a, b), Math.max(c, d))
    }

    def max(a: Float, b: Float, c: Float, d: Float): Float =  {
        return Math.max(Math.max(a, b), Math.max(c, d))
    }

    def max(a: Double, b: Double, c: Double, d: Double): Double = {
        return Math.max(Math.max(a, b), Math.max(c, d))
    }

    def mod(a: Int, q: Int): Int = {
        return ((a % q) + q) % q
    }

    val theSize: Int = CHUNKBLOCKS*2
}

import TerraFrame._

class TerraFrame extends JApplet 
  with ChangeListener 
  with KeyListener 
  with MouseListener 
  with MouseMotionListener 
  with MouseWheelListener {

    var screen: BufferedImage = _
    var bg: Color = _

    val cl: Array2D[Int] = Array(Array(-1, 0), Array(1, 0), Array(0, -1), Array(0, 1))

    var timer, menuTimer, paIntTimer: javax.swing.Timer = _
    var folder: File = _
    var files: Array[File] = _
    var worldFiles, worldNames: List[String] = _
    var currentWorld: String= _
    var newWorldName: TextField = _

    var blocks: Array3D[Int] = _
    var blockds: Array3D[Byte] = _
    var blockdns: Array2D[Byte] = _
    var blockbgs: Array2D[Byte] = _
    var blockts: Array2D[Byte] = _
    var lights: Array2D[Float] = _
    var power: Array3D[Float] = _
    var lsources: Array2D[Boolean] = _
    var lqx, lqy, pqx, pqy, zqx, zqy, pzqx, pzqy: jul.List[Int] = _
    var lqd, zqd, pqd, pzqd: Array2D[Boolean] = _
    var zqn: Array2D[Byte] = _
    var pzqn: Array3D[Byte] = _
    var arbprd: Array3D[Boolean] = _
    var updatex, updatey, updatet, updatel: jul.List[Int] = _
    var wcnct: Array2D[Boolean] = _
    var drawn, ldrawn, rdrawn: Array2D[Boolean] = _
    var player: Player = _
    var inventory: Inventory = _
    
    var entities: jul.List[Entity] = _
    var cloudsx, cloudsy, cloudsv: jul.List[Double] = _
    var cloudsn: jul.List[Int] = _
    var machinesx, machinesy: jul.List[Int] = _

    var temporarySaveFile: Array2D[Chunk] = _
    var chunkMatrix: Array2D[Chunk] = _

    var rgnc1: Int = 0
    var rgnc2: Int = 0
    var layer: Int = 1
    var iclayer: Int = _
    var layerTemp: Int = _
    var blockTemp: Int = _
    var layerImg: BufferedImage = _

    var entity: Entity = _
    var state: String = "loading_graphics"
    var msg: String = "If you are reading this then\nplease report an error."
    var mobSpawn: String = _

    private[this] var width, height = 0
    var u, v, ou, ov, uNew, vNew: Int = _
    var i, j, k, t, wx, wy, lx, ly, tx, ty, twx, twy, tlx, tly, ux, uy, ux2, uy2, uwx, uwy, uwx2, ulx, uly, ulx2, uly2, ucx, ucy, uclx, ucly, pwx, pwy, icx, icy, n, m, dx, dy, dx2, dy2, mx, my, lsx, lsy, lsn, ax, ay, axl, ayl, nl, vc, xpos, ypos, xpos2, ypos2, x2, y2, rnum, mining, immune, xmin, xmax, ymin, ymax, Intpercent, ground: Int = _
    private[this] var x, y = 0
    var p, q: Double = _
    var s, miningTool: Short = _

    var moveItem, moveNum, moveDur, moveItemTemp, moveNumTemp, moveDurTemp: Short = _
    var msi: Int = 0

    var top, bottom, percent: Double = _

    var toolAngle, toolSpeed: Double = _

    var timeOfDay: Double = 0 // 28000 (before dusk), 32000 (after dusk)
    var currentSkyLight: Int = 28800
    var day: Int = 0

    var mobCount: Int = 0

    var tp1, tp2, tp3, tp4, tp5: Point = _

    var mousePos, mousePos2: Array[Int] = _

    private[this] var font: Font = new Font("Chalkboard", Font.BOLD, 12)
    var mobFont: Font = new Font("Chalkboard", Font.BOLD, 16)
    var loadFont: Font = new Font("Courier", Font.PLAIN, 12)
    var menuFont: Font = new Font("Chalkboard", Font.PLAIN, 30)
    var worldFont: Font = new Font("Andale Mono", Font.BOLD, 16)
    var CYANISH: Color = new Color(75, 163, 243)
    var loadTextPos: Int = 0

    var sun, moon, cloud, logo_white, logo_black, title_screen, select_world, new_world, save_exit: BufferedImage = _
    var clouds: Array[BufferedImage] = Array(loadImage("environment/cloud1.png"))
    var wcnct_px: BufferedImage = loadImage("misc/wcnct.png")

    var thread: Thread = _
    var createWorldTimer: javax.swing.Timer = _
    var queue: Array[Boolean] = _

    var done: Boolean = false
    var ready: Boolean = true
    var showTool: Boolean = false
    var showInv: Boolean = false
    var checkBlocks: Boolean = true
    var mouseClicked: Boolean = true
    var mouseClicked2: Boolean = true
    var mouseNoLongerClicked: Boolean = false
    var mouseNoLongerClicked2: Boolean = false
    var addSources: Boolean = false
    var removeSources: Boolean = false
    var beginLight: Boolean = false
    var doMobSpawn: Boolean = false
    var keepLeaf: Boolean = false
    var newWorldFocus: Boolean = false
    var menuPressed: Boolean = false
    var doGenerateWorld: Boolean = true
    var doGrassGrow: Boolean = false
    var reallyAddPower: Boolean = false
    var reallyRemovePower: Boolean = false
    
    var resunlight: Int = WIDTH
    var sunlightlevel: Int = 19

    var ic: ItemCollection = _

    var worlds, fworlds: Array2D[BufferedImage] = _
    var kworlds: Array2D[Boolean] = _

    var world: BufferedImage = _

    var icmatrix: Array3D[ItemCollection] = _

    var image, tool, mobImage: BufferedImage = _

    var toolList: Array[Short] = Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)

    var blocknames: Array[String] = Array("air", "dirt", "stone",
        "copper_ore", "iron_ore", "silver_ore", "gold_ore",
        "wood", "workbench",
        "wooden_chest", "stone_chest",
        "copper_chest", "iron_chest", "silver_chest", "gold_chest",
        "tree", "leaves", "furnace", "coal",
        "lumenstone",
        "wooden_torch", "coal_torch", "lumenstone_torch",
        "furnace_on",
        "wooden_torch_left", "wooden_torch_right",
        "coal_torch_left", "coal_torch_right",
        "lumenstone_torch_left", "lumenstone_torch_right",
        "tree_root",
        "zinc_ore", "rhymestone_ore", "obdurite_ore",
        "aluminum_ore", "lead_ore", "uranium_ore",
        "zythium_ore", "zythium_ore_on", "silicon_ore",
        "irradium_ore", "nullstone", "meltstone", "skystone",
        "magnetite_ore",
        "sand", "snow", "glass",
        "sunflower_stage_1", "sunflower_stage_2", "sunflower_stage_3",
        "moonflower_stage_1", "moonflower_stage_2", "moonflower_stage_3",
        "dryweed_stage_1", "dryweed_stage_2", "dryweed_stage_3",
        "greenleaf_stage_1", "greenleaf_stage_2", "greenleaf_stage_3",
        "frostleaf_stage_1", "frostleaf_stage_2", "frostleaf_stage_3",
        "caveroot_stage_1", "caveroot_stage_2", "caveroot_stage_3",
        "skyblossom_stage_1", "skyblossom_stage_2", "skyblossom_stage_3",
        "void_rot_stage_1", "void_rot_stage_2", "void_rot_stage_3",
        "grass", "jungle_grass", "swamp_grass",
        "mud", "sandstone",
        "marshleaf_stage_1", "marshleaf_stage_2", "marshleaf_stage_3",
        "zinc_chest", "rhymestone_chest", "obdurite_chest",
        "tree_no_bark", "cobblestone",
        "chiseled_stone", "chiseled_cobblestone", "stone_bricks",
        "clay", "clay_bricks", "varnished_wood",
        "dirt_trans", "magnetite_ore_trans", "grass_trans",
        "zythium_wire_0", "zythium_wire_1", "zythium_wire_2", "zythium_wire_3", "zythium_wire_4", "zythium_wire_5",
        "zythium_torch", "zythium_torch_left", "zythium_torch_right", "zythium_lamp", "zythium_lamp_on",
        "lever", "lever_on", "lever_left", "lever_left_on", "lever_right", "lever_right_on",
        "zythium_amplifier_right", "zythium_amplifier_down", "zythium_amplifier_left", "zythium_amplifier_up",
        "zythium_amplifier_right_on", "zythium_amplifier_down_on", "zythium_amplifier_left_on", "zythium_amplifier_up_on",
        "zythium_inverter_right", "zythium_inverter_down", "zythium_inverter_left", "zythium_inverter_up",
        "zythium_inverter_right_on", "zythium_inverter_down_on", "zythium_inverter_left_on", "zythium_inverter_up_on",
        "button_left", "button_left_on", "button_right", "button_right_on", "wooden_pressure_plate", "wooden_pressure_plate_on",
        "stone_pressure_plate", "stone_pressure_plate_on", "zythium_pressure_plate", "zythium_pressure_plate_on",
        "zythium_delayer_1_right", "zythium_delayer_1_down", "zythium_delayer_1_left", "zythium_delayer_1_up",
        "zythium_delayer_1_right_on", "zythium_delayer_1_down_on", "zythium_delayer_1_left_on", "zythium_delayer_1_up_on",
        "zythium_delayer_2_right", "zythium_delayer_2_down", "zythium_delayer_2_left", "zythium_delayer_2_up",
        "zythium_delayer_2_right_on", "zythium_delayer_2_down_on", "zythium_delayer_2_left_on", "zythium_delayer_2_up_on",
        "zythium_delayer_4_right", "zythium_delayer_4_down", "zythium_delayer_4_left", "zythium_delayer_4_up",
        "zythium_delayer_4_right_on", "zythium_delayer_4_down_on", "zythium_delayer_4_left_on", "zythium_delayer_4_up_on",
        "zythium_delayer_8_right", "zythium_delayer_8_down", "zythium_delayer_8_left", "zythium_delayer_8_up",
        "zythium_delayer_8_right_on", "zythium_delayer_8_down_on", "zythium_delayer_8_left_on", "zythium_delayer_8_up_on")

    var dirs: Array[String] = Array("center", "tdown_both", "tdown_cw", "tdown_ccw",
        "tdown", "tup_both", "tup_cw", "tup_ccw",
        "tup", "leftright", "tright_both", "tright_cw",
        "tright_ccw", "tright", "upleftdiag", "upleft",
        "downleftdiag", "downleft", "left", "tleft_both",
        "tleft_cw", "tleft_ccw", "tleft", "uprightdiag",
        "upright", "downrightdiag", "downright", "right",
        "updown", "up", "down", "single")

    var ui_items: Array[String] = Array("Air", "Dirt", "Stone",
        "Copper Ore", "Iron Ore", "Silver Ore", "Gold Ore",
        "Copper Pick", "Iron Pick", "Silver Pick", "Gold Pick",
        "Copper Axe", "Iron Axe", "Silver Axe", "Gold Axe",
        "Wood", "Copper Sword", "Iron Sword", "Silver Sword", "Gold Sword",
        "Workbench",
        "Wooden Chest", "Stone Chest",
        "Copper Chest", "Iron Chest", "Silver Chest", "Gold Chest",
        "Furnace", "Coal",
        "Copper Ingot", "Iron Ingot", "Silver Ingot", "Gold Ingot",
        "Stone Lighter", "Lumenstone",
        "Wooden Torch", "Coal Torch", "Lumenstone Torch",
        "Zinc Ore", "Rhymestone Ore", "Obdurite Ore",
        "Aluminum Ore", "Lead Ore", "Uranium Ore",
        "Zythium Ore", "Silicon Ore",
        "Irradium Ore", "Nullstone", "Meltstone", "Skystone",
        "Magnetite Ore",
        "Zinc Pick", "Zinc Axe", "Zinc Sword",
        "Rhymestone Pick", "Rhymestone Axe", "Rhymestone Sword",
        "Obdurite Pick", "Obdurite Axe", "Obdurite Sword",
        "Zinc Ingot", "Rhymestone Ingot", "Obdurite Ingot",
        "Aluminum Ingot", "Lead Ingot", "Uranium Bar", "Refined Uranium",
        "Zythium Bar", "Silicon Bar",
        "Irradium Ingot", "Nullstone Bar", "Meltstone Bar", "Skystone Bar",
        "Magnetite Ingot",
        "Sand", "Snow", "Glass",
        "Sunflower Seeds", "Sunflower", "Moonflower Seeds", "Moonflower",
        "Dryweed Seeds", "Dryweed", "Greenleaf Seeds", "Greenleaf",
        "Frostleaf Seeds", "Frostleaf", "Caveroot Seeds", "Caveroot",
        "Skyblossom Seeds", "Skyblossom", "Void Rot Seeds", "Void Rot",
        "Mud", "Sandstone",
        "Marshleaf Seeds", "Marshleaf",
        "Blue Goo", "Green Goo", "Red Goo", "Yellow Goo", "Black Goo", "White Goo",
        "Astral Shard", "Rotten Chunk",
        "Copper Helmet", "Copper Chestplate", "Copper Leggings", "Copper Greaves",
        "Iron Helmet", "Iron Chestplate", "Iron Leggings", "Iron Greaves",
        "Silver Helmet", "Silver Chestplate", "Silver Leggings", "Silver Greaves",
        "Gold Helmet", "Gold Chestplate", "Gold Leggings", "Gold Greaves",
        "Zinc Helmet", "Zinc Chestplate", "Zinc Leggings", "Zinc Greaves",
        "Rhymestone Helmet", "Rhymestone Chestplate", "Rhymestone Leggings", "Rhymestone Greaves",
        "Obdurite Helmet", "Obdurite Chestplate", "Obdurite Leggings", "Obdurite Greaves",
        "Aluminum Helmet", "Aluminum Chestplate", "Aluminum Leggings", "Aluminum Greaves",
        "Lead Helmet", "Lead Chestplate", "Lead Leggings", "Lead Greaves",
        "Zythium Helmet", "Zythium Chestplate", "Zythium Leggings", "Zythium Greaves",
        "Aluminum Pick", "Aluminum Axe", "Aluminum Sword",
        "Lead Pick", "Lead Axe", "Lead Sword",
        "Zinc Chest", "Rhymestone Chest", "Obdurite Chest",
        "Wooden Pick", "Wooden Axe", "Wooden Sword",
        "Stone Pick", "Stone Axe", "Stone Sword",
        "Bark", "Cobblestone",
        "Chiseled Stone", "Chiseled Cobblestone", "Stone Bricks",
        "Clay", "Clay Bricks", "Varnish", "Varnished Wood",
        "Magnetite Pick", "Magnetite Axe", "Magnetite Sword",
        "Irradium Pick", "Irradium Axe", "Irradium Sword",
        "Zythium Wire", "Zythium Torch", "Zythium Lamp", "Lever",
        "Charcoal", "Zythium Amplifier", "Zythium Inverter", "Button",
        "Wooden Pressure Plate", "Stone Pressure Plate", "Zythium Pressure Plate",
        "Zythium Delayer", "Zythium Delayer", "Zythium Delayer", "Zythium Delayer", "Wrench")


    var FRI1, FRN1, FRI2, FRN2: List[Short] = _

    var g2, wg2, fwg2, ug2, pg2: Graphics2D = _

    var output, boutput: ObjectOutputStream = _
    var input: ObjectInputStream = _

    override def init(): Unit = {
        try {
            setLayout(new BorderLayout())
            bg = Color.BLACK

            addKeyListener(this)
            addMouseListener(this)
            addMouseMotionListener(this)
            addMouseWheelListener(this)

            screen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB)

            queue = Array.ofDim(7) // left(0) right(1) up(2) mouse(3) rightmouse(4) shift(5) down(6)

            mousePos = Array.ofDim(2)
            mousePos2 = Array.ofDim(2)

            logo_white = loadImage("Interface/logo_white.png")
            logo_black = loadImage("Interface/logo_black.png")
            title_screen = loadImage("Interface/title_screen.png")
            select_world = loadImage("Interface/select_world.png")
            new_world = loadImage("Interface/new_world.png")
            save_exit = loadImage("Interface/save_exit.png")

            state = "loading_graphics"

            repaint()


            val bgs: Array[String] = Array("solid/empty", "dirt_none/downleft", "dirt_none/downright", "dirt_none/left", "dirt_none/right", "dirt_none/up", "dirt_none/upleft", "dirt_none/upright",
                "solid/dirt", "stone_dirt/downleft", "stone_dirt/downright", "stone_dirt/left", "stone_dirt/right", "stone_dirt/up", "stone_dirt/upleft", "stone_dirt/upright",
                "solid/stone", "stone_none/down")
            
            val backgroundImgsTemp = new jul.HashMap[Byte,BufferedImage](bgs.length)

            (0 until bgs.length).foreach { i =>
                backgroundImgsTemp.put(i.toByte, loadImage("backgrounds/" + bgs(i) + ".png"))
            }

            backgroundImgs = backgroundImgsTemp.asScala.toMap

            
            
            val itemImgsTemp = new jul.HashMap[Short,BufferedImage](items.length)

            (1 until items.length).foreach { i =>
                itemImgsTemp.put(i.toShort, loadImage("items/" + items(i) + ".png"))
                if (itemImgsTemp.get(i.toShort) == null) {
                    println("(ERROR) Could not load item graphic '" + items(i) + "'.")
                }
            }

            itemImgs = itemImgsTemp.asScala.toMap

            
            val blockImgsTemp = new jul.HashMap[String,BufferedImage](blocknames.length)

            (1 until blocknames.length).foreach { i =>
                (0 until 8).foreach { j =>
                    blockImgsTemp.put("blocks/" + blocknames(i) + "/texture" + (j+1) + ".png",
                        loadImage("blocks/" + blocknames(i) + "/texture" + (j+1) + ".png"))
                    if (blockImgsTemp.get("blocks/" + blocknames(i) + "/texture" + (j+1) + ".png") == null) {
                        println("(ERROR) Could not load block graphic '" + blocknames(i) + "'.")
                    }
                }
            }

            blockImgs = blockImgsTemp.asScala.toMap



            val outlineNameList: Array[String] = Array("default", "wood", "none", "tree", "tree_root", "square", "wire")
            
            val outlineImgsTemp = new jul.HashMap[String,BufferedImage](outlineNameList.length * dirs.length)

            (0 until outlineNameList.length).foreach { i =>
                (0 until dirs.length).foreach { j =>
                    (0 until 5).foreach { k =>
                        outlineImgsTemp.put("outlines/" + outlineNameList(i) + "/" + dirs(j) + (k+1) + ".png",
                            loadImage("outlines/" + outlineNameList(i) + "/" + dirs(j) + (k+1) + ".png"))
                    }
                }
            }

            outlineImgs = outlineImgsTemp.asScala.toMap
            

            codeTooLarge()

            

            val blocktools: Array2D[Short] = Array(
                                    Array[Short](),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173),
                                    Array[Short](11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173),
                                    Array[Short](11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173),
                                    Array[Short](),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173),
                                    Array[Short](11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172),
                                    Array[Short](7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172))


            val blockToolsTemp = new jul.HashMap[Int,Array[Short]](blocknames.length)

            (1 until blocknames.length).foreach { i =>
                blockToolsTemp.put(i, blocktools(i))
            }

            BLOCKTOOLS = blockToolsTemp.asScala.toMap

            val toolSpeedTemp = new jul.HashMap[Short,Double](items.length)

            (1 until items.length).foreach { i =>
                toolSpeedTemp.put(i.toShort, 0.175)
            }

            toolSpeedTemp.put(154.toShort, 0.100)// wood:   P100 S100
            toolSpeedTemp.put(155.toShort, 0.100)
            toolSpeedTemp.put(156.toShort, 0.100)
            toolSpeedTemp.put(157.toShort, 0.110)// stone:  P110 S105
            toolSpeedTemp.put(158.toShort, 0.110)
            toolSpeedTemp.put(159.toShort, 0.105)
            toolSpeedTemp.put(7.toShort, 0.120)  // copper: P120 S110
            toolSpeedTemp.put(11.toShort, 0.120)
            toolSpeedTemp.put(16.toShort, 0.110)
            toolSpeedTemp.put(8.toShort, 0.130)  // iron:   P130 S115
            toolSpeedTemp.put(12.toShort, 0.130)
            toolSpeedTemp.put(17.toShort, 0.115)
            toolSpeedTemp.put(9.toShort, 0.140)  // silver: P140 S120
            toolSpeedTemp.put(13.toShort, 0.140)
            toolSpeedTemp.put(18.toShort, 0.120)
            toolSpeedTemp.put(10.toShort, 0.150) // gold:   P150 S125
            toolSpeedTemp.put(14.toShort, 0.150)
            toolSpeedTemp.put(19.toShort, 0.125)
            toolSpeedTemp.put(51.toShort, 0.160) // zinc:   P160 S130
            toolSpeedTemp.put(52.toShort, 0.160)
            toolSpeedTemp.put(53.toShort, 0.130)
            toolSpeedTemp.put(54.toShort, 0.170) // rhyme:  P170 S135
            toolSpeedTemp.put(55.toShort, 0.170)
            toolSpeedTemp.put(56.toShort, 0.135)
            toolSpeedTemp.put(57.toShort, 0.180) // obdur:  P180 S140
            toolSpeedTemp.put(58.toShort, 0.180)
            toolSpeedTemp.put(59.toShort, 0.140)
            toolSpeedTemp.put(145.toShort, 0.350)// alumin: P250 S175
            toolSpeedTemp.put(146.toShort, 0.350)
            toolSpeedTemp.put(147.toShort, 0.245)
            toolSpeedTemp.put(148.toShort, 0.130)// lead:   P130 S115
            toolSpeedTemp.put(149.toShort, 0.130)
            toolSpeedTemp.put(150.toShort, 0.115)
            toolSpeedTemp.put(169.toShort, 0.250) // magne:  P350 S245
            toolSpeedTemp.put(170.toShort, 0.250)
            toolSpeedTemp.put(171.toShort, 0.175)
            toolSpeedTemp.put(172.toShort, 0.350) // irrad:  P350 S245
            toolSpeedTemp.put(173.toShort, 0.350)
            toolSpeedTemp.put(174.toShort, 0.245)

            toolSpeedTemp.put(33.toShort, 0.125) // stone lighter

            TOOLSPEED = toolSpeedTemp.asScala.toMap


            val toolDamageTemp = new jul.HashMap[Short,Int](items.length)

            (0 until items.length).foreach { i =>
                toolDamageTemp.put(i.toShort, 1)
            }

            toolDamageTemp.put(7.toShort, 2)
            toolDamageTemp.put(8.toShort, 3)
            toolDamageTemp.put(9.toShort, 3)
            toolDamageTemp.put(10.toShort, 4)
            toolDamageTemp.put(11.toShort, 3)
            toolDamageTemp.put(12.toShort, 4)
            toolDamageTemp.put(13.toShort, 5)
            toolDamageTemp.put(14.toShort, 6)
            toolDamageTemp.put(16.toShort, 5)
            toolDamageTemp.put(17.toShort, 8)
            toolDamageTemp.put(18.toShort, 13)
            toolDamageTemp.put(19.toShort, 18)
            toolDamageTemp.put(51.toShort, 6)
            toolDamageTemp.put(52.toShort, 9)
            toolDamageTemp.put(53.toShort, 24)
            toolDamageTemp.put(54.toShort, 8)
            toolDamageTemp.put(55.toShort, 11)
            toolDamageTemp.put(56.toShort, 30)
            toolDamageTemp.put(57.toShort, 10)
            toolDamageTemp.put(58.toShort, 15)
            toolDamageTemp.put(59.toShort, 38)
            toolDamageTemp.put(145.toShort, 7)
            toolDamageTemp.put(146.toShort, 10)
            toolDamageTemp.put(147.toShort, 27)
            toolDamageTemp.put(148.toShort, 4)
            toolDamageTemp.put(149.toShort, 5)
            toolDamageTemp.put(150.toShort, 9)
            toolDamageTemp.put(154.toShort, 1)
            toolDamageTemp.put(155.toShort, 1)
            toolDamageTemp.put(156.toShort, 3)
            toolDamageTemp.put(157.toShort, 1)
            toolDamageTemp.put(158.toShort, 2)
            toolDamageTemp.put(159.toShort, 4)
            toolDamageTemp.put(57.toShort, 20)
            toolDamageTemp.put(58.toShort, 30)
            toolDamageTemp.put(59.toShort, 75)

            TOOLDAMAGE = toolDamageTemp.asScala.toMap

            
            val drops: Array[Short] = Array[Short](0, 1, 2, 3, 4, 5, 6, 15, 20, 21, 22, 23, 24, 25, 26, 15, 0, 27, 28, 34, 35, 36, 37, 27, 35, 35, 36, 36, 37, 37, 0, 38, 39, 40, 41, 42, 43, 44, 44, 45, 46, 47, 48, 49, 50, 74, 75, 0, 0, 0, 78, 0, 0, 80, 0, 0, 82, 0, 0, 84, 0, 0, 86, 0, 0, 88, 0, 0, 90, 0, 0, 92, 1, 1, 93, 93, 94, 0, 0, 96, 151, 152, 153, 15, 161, 162, 163, 164, 165, 166, 168, 1, 50, 1, 175, 175, 175, 175, 175, 175, 176, 176, 176, 177, 177, 178, 178, 178, 178, 178, 178, 180, 180, 180, 180, 180, 180, 180, 180, 181, 181, 181, 181, 181, 181, 181, 181, 182, 182, 182, 182, 183, 183, 184, 184, 185, 185, 186, 186, 186, 186, 186, 186, 186, 186, 187, 187, 187, 187, 187, 187, 187, 187, 188, 188, 188, 188, 188, 188, 188, 188, 189, 189, 189, 189, 189, 189, 189, 189)

            val blockDropsTemp = new jul.HashMap[Int,Short](blocknames.length)

            (1 until blocknames.length).foreach { i =>
                blockDropsTemp.put(i, drops(i))
            }

            BLOCKDROPS = blockDropsTemp.asScala.toMap
            

            val itemblocks: Array[Int] = Array(0, 1, 2, 3, 4, 5, 6, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 17, 18, 0, 0, 0, 0, 0, 19, 20, 21, 22, 31, 32, 33, 34, 35, 36, 37, 39, 40, 41, 42, 43, 44, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 45/*72*/, 46, 47, 48, 0, 51, 0, 54, 0, 57, 0, 60, 0, 63, 0, 66, 0, 69, 0, 75, 76, 77, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 80, 81, 82, 0, 0, 0, 0, 0, 0, 0, 84, 85, 86, 87, 88, 89, 0, 90, 0, 0, 0, 0, 0, 0, 94, 100, 103, 105, 0, 111, 119, 127, 131, 133, 135, 137, 145, 153, 161, 0)

            val itemBlocksTemp = new jul.HashMap[Short,Int](items.length)

            (1 until items.length).foreach { i =>
                itemBlocksTemp.put(i.toShort, itemblocks(i))
            }

            ITEMBLOCKS = itemBlocksTemp.asScala.toMap
            

            val outlinesTemp = new jul.HashMap[Int,String](blocknames.length)

            (1 until blocknames.length).foreach { i =>
                outlinesTemp.put(i, "default")
            }

            outlinesTemp.put(7, "wood")
            outlinesTemp.put(8, "none")
            outlinesTemp.put(9, "none")
            outlinesTemp.put(10, "none")
            outlinesTemp.put(11, "none")
            outlinesTemp.put(12, "none")
            outlinesTemp.put(13, "none")
            outlinesTemp.put(14, "none")
            outlinesTemp.put(15, "tree")
            outlinesTemp.put(17, "none")
            outlinesTemp.put(20, "none")
            outlinesTemp.put(21, "none")
            outlinesTemp.put(22, "none")
            outlinesTemp.put(23, "none")
            outlinesTemp.put(24, "none")
            outlinesTemp.put(25, "none")
            outlinesTemp.put(26, "none")
            outlinesTemp.put(27, "none")
            outlinesTemp.put(28, "none")
            outlinesTemp.put(29, "none")
            outlinesTemp.put(30, "tree_root")
            outlinesTemp.put(47, "square")
            outlinesTemp.put(77, "none")
            outlinesTemp.put(78, "none")
            outlinesTemp.put(79, "none")
            outlinesTemp.put(80, "none")
            outlinesTemp.put(81, "none")
            outlinesTemp.put(82, "none")
            outlinesTemp.put(83, "tree")
            outlinesTemp.put(84, "square")
            outlinesTemp.put(85, "square")
            outlinesTemp.put(86, "square")
            outlinesTemp.put(87, "square")
            outlinesTemp.put(89, "square")
            outlinesTemp.put(90, "wood")
            outlinesTemp.put(94, "wire")
            outlinesTemp.put(95, "wire")
            outlinesTemp.put(96, "wire")
            outlinesTemp.put(97, "wire")
            outlinesTemp.put(98, "wire")
            outlinesTemp.put(99, "wire")
            outlinesTemp.put(100, "none")
            outlinesTemp.put(101, "none")
            outlinesTemp.put(102, "none")
            outlinesTemp.put(103, "square")
            outlinesTemp.put(104, "square")
            outlinesTemp.put(105, "none")
            outlinesTemp.put(106, "none")
            outlinesTemp.put(107, "none")
            outlinesTemp.put(108, "none")
            outlinesTemp.put(109, "none")
            outlinesTemp.put(110, "none")
            outlinesTemp.put(111, "none")
            outlinesTemp.put(112, "none")
            outlinesTemp.put(113, "none")
            outlinesTemp.put(114, "none")
            outlinesTemp.put(115, "none")
            outlinesTemp.put(116, "none")
            outlinesTemp.put(117, "none")
            outlinesTemp.put(118, "none")
            outlinesTemp.put(119, "none")
            outlinesTemp.put(120, "none")
            outlinesTemp.put(121, "none")
            outlinesTemp.put(122, "none")
            outlinesTemp.put(123, "none")
            outlinesTemp.put(124, "none")
            outlinesTemp.put(125, "none")
            outlinesTemp.put(126, "none")
            outlinesTemp.put(127, "none")
            outlinesTemp.put(128, "none")
            outlinesTemp.put(129, "none")
            outlinesTemp.put(130, "none")
            outlinesTemp.put(131, "none")
            outlinesTemp.put(132, "none")
            outlinesTemp.put(133, "none")
            outlinesTemp.put(134, "none")
            outlinesTemp.put(135, "none")
            outlinesTemp.put(136, "none")

            (48 until 72).foreach { i =>
                outlinesTemp.put(i, "none")
            }

            (137 until 169).foreach { i =>
                outlinesTemp.put(i, "none")
            }

            OUTLINES = outlinesTemp.asScala.toMap
            

            val uiBlocksTemp = new jul.HashMap[String,String](items.length)

            (1 until items.length).foreach { i =>
                uiBlocksTemp.put(items(i), ui_items(i))
            }

            UIBLOCKS = uiBlocksTemp.asScala.toMap

            
            val uiEntitiesTemp = new jul.HashMap[String,String]()

            uiEntitiesTemp.put("blue_bubble", "Blue Bubble")
            uiEntitiesTemp.put("green_bubble", "Green Bubble")
            uiEntitiesTemp.put("red_bubble", "Red Bubble")
            uiEntitiesTemp.put("black_bubble", "Black Bubble")
            uiEntitiesTemp.put("white_bubble", "White Bubble")
            uiEntitiesTemp.put("zombie", "Zombie")
            uiEntitiesTemp.put("armored_zombie", "Armored Zombie")
            uiEntitiesTemp.put("shooting_star", "Shooting Star")
            uiEntitiesTemp.put("sandbot", "Sandbot")
            uiEntitiesTemp.put("snowman", "Snowman")
            uiEntitiesTemp.put("bat", "Bat")
            uiEntitiesTemp.put("bee", "Bee")
            uiEntitiesTemp.put("skeleton", "Skeleton")

            UIENTITIES = uiEntitiesTemp.asScala.toMap
            
            
            val blockCdTemp = new jul.HashMap[Int,Boolean](blockcds.length)

            (1 until blockcds.length).foreach { i =>
                blockCdTemp.put(i, blockcds(i))
            }

            BLOCKCD = blockCdTemp.asScala.toMap
            

            val maxStacksTemp = new jul.HashMap[Short,Short](items.length)

            val stacks: Array[Short] = Array[Short](100, 100, 100, 100, 100, 100, 100, 1, 1, 1, 1, 1, 1, 1, 1, 100, 1, 1, 1, 1, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 1, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100, 100, 1, 1, 1, 1, 1, 1, 100, 100, 100, 100, 100, 100, 100, 100, 100, 1, 1, 1, 1, 1, 1, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 1)

            (0 until items.length).foreach { i =>
                maxStacksTemp.put(i.toShort, stacks(i))
            }

            MAXSTACKS = maxStacksTemp.asScala.toMap
            

            val skyColorsTemp = new jul.HashMap[Int,Color]()

            skyColorsTemp.put(28800, new Color(71, 154, 230))
            skyColorsTemp.put(28980, new Color(67, 146, 218))
            skyColorsTemp.put(29160, new Color(63, 138, 206))
            skyColorsTemp.put(29340, new Color(60, 130, 194))
            skyColorsTemp.put(29520, new Color(56, 122, 182))
            skyColorsTemp.put(29700, new Color(52, 114, 170))
            skyColorsTemp.put(29880, new Color(48, 105, 157))
            skyColorsTemp.put(30060, new Color(45, 97, 145))
            skyColorsTemp.put(30240, new Color(41, 89, 133))
            skyColorsTemp.put(30420, new Color(37, 81, 121))
            skyColorsTemp.put(30600, new Color(33, 73, 109))
            skyColorsTemp.put(30780, new Color(30, 65, 97))
            skyColorsTemp.put(30960, new Color(26, 57, 85))
            skyColorsTemp.put(31140, new Color(22, 48, 72))
            skyColorsTemp.put(31320, new Color(18, 40, 60))
            skyColorsTemp.put(31500, new Color(15, 32, 48))
            skyColorsTemp.put(31680, new Color(11, 24, 36))
            skyColorsTemp.put(31860, new Color(7, 16, 24))
            skyColorsTemp.put(32040, new Color(3, 8, 12))
            skyColorsTemp.put(32220, new Color(0, 0, 0))

            skyColorsTemp.put(72000, new Color(3, 8, 12))
            skyColorsTemp.put(72180, new Color(7, 16, 24))
            skyColorsTemp.put(72360, new Color(11, 24, 36))
            skyColorsTemp.put(72540, new Color(15, 32, 48))
            skyColorsTemp.put(72720, new Color(18, 40, 60))
            skyColorsTemp.put(72900, new Color(22, 48, 72))
            skyColorsTemp.put(73080, new Color(26, 57, 85))
            skyColorsTemp.put(73260, new Color(30, 65, 97))
            skyColorsTemp.put(73440, new Color(33, 73, 109))
            skyColorsTemp.put(73620, new Color(37, 81, 121))
            skyColorsTemp.put(73800, new Color(41, 89, 133))
            skyColorsTemp.put(73980, new Color(45, 97, 145))
            skyColorsTemp.put(74160, new Color(48, 105, 157))
            skyColorsTemp.put(74340, new Color(52, 114, 170))
            skyColorsTemp.put(74520, new Color(56, 122, 182))
            skyColorsTemp.put(74700, new Color(60, 130, 194))
            skyColorsTemp.put(74880, new Color(63, 138, 206))
            skyColorsTemp.put(75060, new Color(67, 146, 218))
            skyColorsTemp.put(75240, new Color(71, 154, 230))
            skyColorsTemp.put(75420, new Color(75, 163, 243))

            SKYCOLORS = skyColorsTemp.asScala.toMap
            
            
            val skyLightsTemp = new jul.HashMap[Int,Int]()

            skyLightsTemp.put(28883, 18)
            skyLightsTemp.put(29146, 17)
            skyLightsTemp.put(29409, 16)
            skyLightsTemp.put(29672, 15)
            skyLightsTemp.put(29935, 14)
            skyLightsTemp.put(30198, 13)
            skyLightsTemp.put(30461, 12)
            skyLightsTemp.put(30724, 11)
            skyLightsTemp.put(30987, 10)
            skyLightsTemp.put(31250, 9)
            skyLightsTemp.put(31513, 8)
            skyLightsTemp.put(31776, 7)
            skyLightsTemp.put(32039, 6)
            skyLightsTemp.put(32302, 5)
            skyLightsTemp.put(72093, 6)
            skyLightsTemp.put(72336, 7)
            skyLightsTemp.put(72639, 8)
            skyLightsTemp.put(72912, 9)
            skyLightsTemp.put(73185, 10)
            skyLightsTemp.put(73458, 11)
            skyLightsTemp.put(73731, 12)
            skyLightsTemp.put(74004, 13)
            skyLightsTemp.put(74277, 14)
            skyLightsTemp.put(74550, 15)
            skyLightsTemp.put(74823, 16)
            skyLightsTemp.put(75096, 17)
            skyLightsTemp.put(75369, 18)
            skyLightsTemp.put(75642, 19)

            SKYLIGHTS = skyLightsTemp.asScala.toMap
            
            
            val lightLevelsTemp = new jul.HashMap[Int,BufferedImage]()

            (0 until 17).foreach { i =>
                lightLevelsTemp.put(i, loadImage("light/" + i + ".png"))
            }
            
            LIGHTLEVELS = lightLevelsTemp.asScala.toMap
            

            val blockLightsTemp = new jul.HashMap[Int,Int](blocknames.length)

            (0 until blocknames.length).foreach { i =>
                blockLightsTemp.put(i, 0)
            }

            blockLightsTemp.put(19, 21)
            blockLightsTemp.put(20, 15)
            blockLightsTemp.put(21, 18)
            blockLightsTemp.put(22, 21)
            blockLightsTemp.put(23, 15)
            blockLightsTemp.put(24, 15)
            blockLightsTemp.put(25, 15)
            blockLightsTemp.put(26, 18)
            blockLightsTemp.put(27, 18)
            blockLightsTemp.put(28, 21)
            blockLightsTemp.put(29, 21)
            blockLightsTemp.put(36, 15)
            blockLightsTemp.put(36, 15)
            blockLightsTemp.put(38, 18)
            blockLightsTemp.put(51, 15)
            blockLightsTemp.put(52, 15)
            blockLightsTemp.put(53, 15)
            blockLightsTemp.put(95, 6)
            blockLightsTemp.put(96, 7)
            blockLightsTemp.put(97, 8)
            blockLightsTemp.put(98, 9)
            blockLightsTemp.put(99, 10)
            blockLightsTemp.put(100, 12)
            blockLightsTemp.put(101, 12)
            blockLightsTemp.put(102, 12)
            blockLightsTemp.put(104, 21)
            blockLightsTemp.put(112, 12)
            blockLightsTemp.put(114, 12)
            blockLightsTemp.put(116, 12)
            blockLightsTemp.put(118, 12)
            blockLightsTemp.put(123, 12)
            blockLightsTemp.put(124, 12)
            blockLightsTemp.put(125, 12)
            blockLightsTemp.put(126, 12)

            blockLightsTemp.put(137, 12)
            blockLightsTemp.put(138, 12)
            blockLightsTemp.put(139, 12)
            blockLightsTemp.put(140, 12)
            blockLightsTemp.put(145, 12)
            blockLightsTemp.put(146, 12)
            blockLightsTemp.put(147, 12)
            blockLightsTemp.put(148, 12)
            blockLightsTemp.put(153, 12)
            blockLightsTemp.put(154, 12)
            blockLightsTemp.put(155, 12)
            blockLightsTemp.put(156, 12)
            blockLightsTemp.put(161, 12)
            blockLightsTemp.put(162, 12)
            blockLightsTemp.put(163, 12)
            blockLightsTemp.put(164, 12)

            BLOCKLIGHTS = blockLightsTemp.asScala.toMap
            
            
            val grassDirtTemp = new jul.HashMap[Int,Int]()

            grassDirtTemp.put(72, 1)
            grassDirtTemp.put(73, 1)
            grassDirtTemp.put(74, 75)
            grassDirtTemp.put(93, 91)

            GRASSDIRT = grassDirtTemp.asScala.toMap
            
            
            val armorTemp = new jul.HashMap[Short,Int](items.length)

            (0 until items.length).foreach { i =>
                armorTemp.put(i.toShort, 0)
            }

            armorTemp.put(105.toShort, 1)
            armorTemp.put(106.toShort, 2)
            armorTemp.put(107.toShort, 1)
            armorTemp.put(108.toShort, 1)
            armorTemp.put(109.toShort, 1)
            armorTemp.put(110.toShort, 3)
            armorTemp.put(111.toShort, 2)
            armorTemp.put(112.toShort, 1)
            armorTemp.put(113.toShort, 2)
            armorTemp.put(114.toShort, 4)
            armorTemp.put(115.toShort, 3)
            armorTemp.put(116.toShort, 1)
            armorTemp.put(117.toShort, 3)
            armorTemp.put(118.toShort, 6)
            armorTemp.put(119.toShort, 5)
            armorTemp.put(120.toShort, 2)
            armorTemp.put(121.toShort, 4)
            armorTemp.put(122.toShort, 7)
            armorTemp.put(123.toShort, 6)
            armorTemp.put(124.toShort, 3)
            armorTemp.put(125.toShort, 5)
            armorTemp.put(126.toShort, 9)
            armorTemp.put(127.toShort, 7)
            armorTemp.put(128.toShort, 4)
            armorTemp.put(129.toShort, 7)
            armorTemp.put(130.toShort, 12)
            armorTemp.put(131.toShort, 10)
            armorTemp.put(132.toShort, 6)
            armorTemp.put(133.toShort, 4)
            armorTemp.put(134.toShort, 7)
            armorTemp.put(135.toShort, 6)
            armorTemp.put(136.toShort, 3)
            armorTemp.put(137.toShort, 2)
            armorTemp.put(138.toShort, 4)
            armorTemp.put(139.toShort, 3)
            armorTemp.put(140.toShort, 1)
            armorTemp.put(141.toShort, 10)
            armorTemp.put(142.toShort, 18)
            armorTemp.put(143.toShort, 14)
            armorTemp.put(144.toShort, 8)
            
            ARMOR = armorTemp.asScala.toMap
            
            

            val toolDursTemp = new jul.HashMap[Short,Short]()

            toolDursTemp.put(7.toShort, 400.toShort)   // copper: P0200 A0200 S0125
            toolDursTemp.put(8.toShort, 500.toShort)   // iron:   P0250 A0250 S0150
            toolDursTemp.put(9.toShort, 600.toShort)   // silver: P0300 A0300 S0200
            toolDursTemp.put(10.toShort, 800.toShort)  // gold:   P0400 A0400 S0300
            toolDursTemp.put(11.toShort, 400.toShort)
            toolDursTemp.put(12.toShort, 500.toShort)
            toolDursTemp.put(13.toShort, 600.toShort)
            toolDursTemp.put(14.toShort, 800.toShort)
            toolDursTemp.put(16.toShort, 250.toShort)
            toolDursTemp.put(17.toShort, 300.toShort)
            toolDursTemp.put(18.toShort, 400.toShort)
            toolDursTemp.put(19.toShort, 600.toShort)
            toolDursTemp.put(33.toShort, 100.toShort)
            toolDursTemp.put(51.toShort, 1100.toShort)  // zinc:   P0550 A0550 S0475
            toolDursTemp.put(52.toShort, 1100.toShort)
            toolDursTemp.put(53.toShort, 950.toShort)
            toolDursTemp.put(54.toShort, 1350.toShort)  // rhyme:  P0675 A0675 S0625
            toolDursTemp.put(55.toShort, 1350.toShort)
            toolDursTemp.put(56.toShort, 1250.toShort)
            toolDursTemp.put(57.toShort, 1600.toShort)  // obdur:  P0800 A0800 S0800
            toolDursTemp.put(58.toShort, 1600.toShort)
            toolDursTemp.put(59.toShort, 1600.toShort)
            toolDursTemp.put(145.toShort, 200.toShort) // alumin: P0100 A0100 S0050
            toolDursTemp.put(146.toShort, 200.toShort)
            toolDursTemp.put(147.toShort, 100.toShort)
            toolDursTemp.put(148.toShort, 2400.toShort)// lead:   P1200 A1200 S0800
            toolDursTemp.put(149.toShort, 2400.toShort)
            toolDursTemp.put(150.toShort, 1600.toShort)
            toolDursTemp.put(154.toShort, 200.toShort) // wood:   P0100 A0100 S0050
            toolDursTemp.put(155.toShort, 200.toShort)
            toolDursTemp.put(156.toShort, 100.toShort)
            toolDursTemp.put(157.toShort, 300.toShort) // stone:  P0150 A0150 S0075
            toolDursTemp.put(158.toShort, 300.toShort)
            toolDursTemp.put(159.toShort, 150.toShort)
            toolDursTemp.put(169.toShort, 1200.toShort) // magne:  P0600 A0600 S0600
            toolDursTemp.put(170.toShort, 1200.toShort)
            toolDursTemp.put(171.toShort, 1200.toShort)
            toolDursTemp.put(172.toShort, 4000.toShort)// irrad:  P2000 A2000 S2000
            toolDursTemp.put(173.toShort, 4000.toShort)
            toolDursTemp.put(174.toShort, 4000.toShort)
            toolDursTemp.put(190.toShort, 400.toShort)

            toolDursTemp.put(105.toShort, 200.toShort) // copper: 0300
            toolDursTemp.put(106.toShort, 200.toShort) // copper: 0300
            toolDursTemp.put(107.toShort, 200.toShort) // copper: 0300
            toolDursTemp.put(108.toShort, 200.toShort) // copper: 0300
            toolDursTemp.put(109.toShort, 200.toShort) // iron:   0400
            toolDursTemp.put(110.toShort, 200.toShort) // iron:   0400
            toolDursTemp.put(111.toShort, 200.toShort) // iron:   0400
            toolDursTemp.put(112.toShort, 200.toShort) // iron:   0400
            toolDursTemp.put(113.toShort, 200.toShort) // silver: 0550
            toolDursTemp.put(114.toShort, 200.toShort) // silver: 0550
            toolDursTemp.put(115.toShort, 200.toShort) // silver: 0550
            toolDursTemp.put(116.toShort, 200.toShort) // silver: 0550
            toolDursTemp.put(117.toShort, 200.toShort) // gold:   0700
            toolDursTemp.put(118.toShort, 200.toShort) // gold:   0700
            toolDursTemp.put(119.toShort, 200.toShort) // gold:   0700
            toolDursTemp.put(120.toShort, 200.toShort) // gold:   0700
            toolDursTemp.put(121.toShort, 200.toShort) // zinc:   0875
            toolDursTemp.put(122.toShort, 200.toShort) // zinc:   0875
            toolDursTemp.put(123.toShort, 200.toShort) // zinc:   0875
            toolDursTemp.put(124.toShort, 200.toShort) // zinc:   0875
            toolDursTemp.put(125.toShort, 200.toShort) // rhyme:  1000
            toolDursTemp.put(126.toShort, 200.toShort) // rhyme:  1000
            toolDursTemp.put(127.toShort, 200.toShort) // rhyme:  1000
            toolDursTemp.put(128.toShort, 200.toShort) // rhyme:  1000
            toolDursTemp.put(129.toShort, 200.toShort) // obdur:  1400
            toolDursTemp.put(130.toShort, 200.toShort) // obdur:  1400
            toolDursTemp.put(131.toShort, 200.toShort) // obdur:  1400
            toolDursTemp.put(132.toShort, 200.toShort) // obdur:  1400
            toolDursTemp.put(133.toShort, 200.toShort) // alumin: 0150
            toolDursTemp.put(134.toShort, 200.toShort) // alumin: 0150
            toolDursTemp.put(135.toShort, 200.toShort) // alumin: 0150
            toolDursTemp.put(136.toShort, 200.toShort) // alumin: 0150
            toolDursTemp.put(137.toShort, 200.toShort) // lead:   2000
            toolDursTemp.put(138.toShort, 200.toShort) // lead:   2000
            toolDursTemp.put(139.toShort, 200.toShort) // lead:   2000
            toolDursTemp.put(140.toShort, 200.toShort) // lead:   2000

            TOOLDURS = toolDursTemp.asScala.toMap
            
            
            val fuelsTemp = new jul.HashMap[Short,Double]()

            fuelsTemp.put(15.toShort, 0.01)
            fuelsTemp.put(28.toShort, 0.001)
            fuelsTemp.put(160.toShort, 0.02)
            fuelsTemp.put(168.toShort, 0.01)
            fuelsTemp.put(179.toShort, 0.0035)
            fuelsTemp.put(20.toShort, 0.0025)
            fuelsTemp.put(21.toShort, 0.00125)
            fuelsTemp.put(35.toShort, 0.02)
            fuelsTemp.put(36.toShort, 0.011)
            fuelsTemp.put(77.toShort, 0.02)
            fuelsTemp.put(79.toShort, 0.02)
            fuelsTemp.put(81.toShort, 0.02)
            fuelsTemp.put(83.toShort, 0.02)
            fuelsTemp.put(85.toShort, 0.02)
            fuelsTemp.put(87.toShort, 0.02)
            fuelsTemp.put(89.toShort, 0.0035)
            fuelsTemp.put(91.toShort, 0.02)
            fuelsTemp.put(95.toShort, 0.02)
            fuelsTemp.put(78.toShort, 0.01)
            fuelsTemp.put(80.toShort, 0.01)
            fuelsTemp.put(82.toShort, 0.01)
            fuelsTemp.put(84.toShort, 0.01)
            fuelsTemp.put(86.toShort, 0.01)
            fuelsTemp.put(88.toShort, 0.01)
            fuelsTemp.put(90.toShort, 0.01)
            fuelsTemp.put(92.toShort, 0.01)
            fuelsTemp.put(96.toShort, 0.01)
            (97 until 103).foreach { i =>
                fuelsTemp.put(i.toShort, 0.0035)
            }
            fuelsTemp.put(154.toShort, 0.002)
            fuelsTemp.put(155.toShort, 0.002)
            fuelsTemp.put(156.toShort, 0.00333)
            
            FUELS = fuelsTemp.asScala.toMap
            

            val wirepTemp = new jul.HashMap[Int,Int]()

            wirepTemp.put(0, 94)
            wirepTemp.put(1, 95)
            wirepTemp.put(2, 96)
            wirepTemp.put(3, 97)
            wirepTemp.put(4, 98)
            wirepTemp.put(5, 99)

            WIREP = wirepTemp.asScala.toMap
            
            
            val torcheslTemp = new jul.HashMap[Int,Int]()

            torcheslTemp.put(20, 24)
            torcheslTemp.put(21, 26)
            torcheslTemp.put(22, 28)
            torcheslTemp.put(100, 101)
            torcheslTemp.put(105, 107)
            torcheslTemp.put(106, 108)
            torcheslTemp.put(127, 127)
            torcheslTemp.put(128, 128)
            
            TORCHESL = torcheslTemp.asScala.toMap
            

            val torchesrTemp = new jul.HashMap[Int,Int]()

            torchesrTemp.put(20, 25)
            torchesrTemp.put(21, 27)
            torchesrTemp.put(22, 29)
            torchesrTemp.put(100, 102)
            torchesrTemp.put(105, 109)
            torchesrTemp.put(106, 110)
            torchesrTemp.put(127, 129)

            TORCHESR = torchesrTemp.asScala.toMap
            
            
            val torchesbTemp = new jul.HashMap[Int,Boolean](blocknames.length)

            (0 until blocknames.length).foreach { i =>
                torchesbTemp.put(i, false)
            }

            torchesbTemp.put(20, true)
            torchesbTemp.put(21, true)
            torchesbTemp.put(22, true)
            torchesbTemp.put(100, true)
            torchesbTemp.put(24, true)
            torchesbTemp.put(26, true)
            torchesbTemp.put(28, true)
            torchesbTemp.put(101, true)
            torchesbTemp.put(25, true)
            torchesbTemp.put(27, true)
            torchesbTemp.put(29, true)
            torchesbTemp.put(102, true)
            torchesbTemp.put(105, true)
            torchesbTemp.put(106, true)
            torchesbTemp.put(107, true)
            torchesbTemp.put(108, true)
            torchesbTemp.put(109, true)
            torchesbTemp.put(110, true)
            torchesbTemp.put(127, true)
            torchesbTemp.put(128, true)
            torchesbTemp.put(129, true)
            torchesbTemp.put(130, true)

            TORCHESB = torchesbTemp.asScala.toMap
            
            
            val gsupportTemp = new jul.HashMap[Int,Boolean](blocknames.length)

            (0 until blocknames.length).foreach { i =>
                gsupportTemp.put(i, false)
            }

            gsupportTemp.put(15, true)
            gsupportTemp.put(83, true)
            gsupportTemp.put(20, true)
            gsupportTemp.put(21, true)
            gsupportTemp.put(22, true)
            gsupportTemp.put(77, true)
            gsupportTemp.put(78, true)
            gsupportTemp.put(100, true)
            gsupportTemp.put(105, true)
            gsupportTemp.put(106, true)
            gsupportTemp.put(131, true)
            gsupportTemp.put(132, true)
            gsupportTemp.put(133, true)
            gsupportTemp.put(134, true)
            gsupportTemp.put(135, true)
            gsupportTemp.put(136, true)

            (48 until 73).foreach { i =>
                gsupportTemp.put(i, true)
            }
            
            GSUPPORT = gsupportTemp.asScala.toMap
            

            val fspeedTemp = new jul.HashMap[Short,Double](blocknames.length)

            (0 until blocknames.length).foreach { i =>
                fspeedTemp.put(i.toShort, 0.001)
            }

            fspeedTemp.put(85.toShort, -0.001)
            fspeedTemp.put(86.toShort, -0.001)
            
            FSPEED = fspeedTemp.asScala.toMap
            

            val ddelayTemp = new jul.HashMap[Int,Int](169)

            (137 until 145).foreach { i =>
                ddelayTemp.put(i, 10)
            }
            (145 until 153).foreach { i =>
                ddelayTemp.put(i, 20)
            }
            (153 until 161).foreach { i =>
                ddelayTemp.put(i, 40)
            }
            (161 until 169).foreach { i =>
                ddelayTemp.put(i, 80)
            }
            
            DDELAY = ddelayTemp.asScala.toMap 
            

            sun = loadImage("environment/sun.png")
            moon = loadImage("environment/moon.png")
            val fri1Temp = new jul.ArrayList[Short](180)
            val frn1Temp = new jul.ArrayList[Short](180)
            val fri2Temp = new jul.ArrayList[Short](180)
            val frn2Temp = new jul.ArrayList[Short](180)

            fri1Temp.add(3.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(29.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(4.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(30.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(5.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(31.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(6.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(32.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(38.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(60.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(39.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(61.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(40.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(62.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(41.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(63.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(42.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(64.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(43.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(65.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(44.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(67.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(45.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(68.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(46.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(69.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(47.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(70.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(48.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(71.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(49.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(72.toShort)
            frn2Temp.add(1.toShort)
            fri1Temp.add(50.toShort)
            frn1Temp.add(4.toShort)
            fri2Temp.add(73.toShort)
            frn2Temp.add(1.toShort)
            (8 until(2, -1)).foreach { i =>
                fri1Temp.add(74.toShort)
                frn1Temp.add(i.toShort)
                fri2Temp.add(76.toShort)
                frn2Temp.add(i.toShort)
                fri1Temp.add(2.toShort)
                frn1Temp.add(i.toShort)
                fri2Temp.add(162.toShort)
                frn2Temp.add(i.toShort)
                fri1Temp.add(161.toShort)
                frn1Temp.add(i.toShort)
                fri2Temp.add(163.toShort)
                frn2Temp.add(i.toShort)
                fri1Temp.add(165.toShort)
                frn1Temp.add(i.toShort)
                fri2Temp.add(166.toShort)
                frn2Temp.add(i.toShort)
                fri1Temp.add(15.toShort)
                frn1Temp.add(i.toShort)
                fri2Temp.add(179.toShort)
                frn2Temp.add(i.toShort)
            }
            (97 until 103).foreach { j =>
                fri1Temp.add(j.toShort)
                frn1Temp.add(1.toShort)
                fri2Temp.add(167.toShort)
                frn2Temp.add(8.toShort)
            }
            
            FRI1 = fri1Temp.asScala.toList
            FRN1 = frn1Temp.asScala.toList
            FRI2 = fri2Temp.asScala.toList
            FRN2 = frn2Temp.asScala.toList

            bg = CYANISH
            state = "title_screen"

            repaint()

            menuTimer = new javax.swing.Timer(20, null)

            menuTimer.addActionListener(new ActionListener() {
                def actionPerformed(ae: ActionEvent): Unit = {
                    try {
                        if (queue(3)) {
                            val mainthread: Action = new AbstractAction() {
                                def actionPerformed(ae: ActionEvent): Unit = {
                                    try {
                                        if (ready) {
                                            ready = false
                                            uNew = ((player.x - getWidth()/2 + player.width) / CHUNKSIZE.toDouble).toInt
                                            vNew = ((player.y - getHeight()/2 + player.height) / CHUNKSIZE.toDouble).toInt
                                            if (ou != uNew || ov != vNew) {
                                                ou = uNew
                                                ov = vNew
                                                val chunkTemp = new jul.ArrayList[Chunk](2 * 2)
                                                (0 until 2).foreach { twy =>
                                                    (0 until 2).foreach { twx =>
                                                        if (chunkMatrix(twy)(twx) != null) {
                                                            chunkTemp.add(chunkMatrix(twy)(twx))
                                                            chunkMatrix(twy)(twx) = null
                                                        }
                                                    }
                                                }
                                                (0 until 2).foreach { twy =>
                                                    import scala.util.control.Breaks._
                                                    breakable {
                                                        (0 until 2).foreach { twx =>
                                                            (chunkTemp.size() - 1 until(-1, -1)).foreach { i =>
                                                                if (chunkTemp.get(i).cx == twx && chunkTemp.get(i).cy == twy) {
                                                                    chunkMatrix(twy)(twx) = chunkTemp.get(i)
                                                                    chunkTemp.remove(i)
                                                                    break
                                                                }
                                                            }
                                                            if (chunkMatrix(twy)(twx) == null) {
                                                                if (temporarySaveFile(twy)(twx) != null) {
                                                                    chunkMatrix(twy)(twx) = temporarySaveFile(twy)(twx)
                                                                }
                                                                else {
                                                                    chunkMatrix(twy)(twx) = new Chunk(twx + ou, twy + ov)
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                (0 until chunkTemp.size()).foreach { i =>
                                                    temporarySaveFile(twy)(twx) = chunkTemp.get(i)
                                                }
                                                (0 until 2).foreach { twy =>
                                                    (0 until 2).foreach { twx =>
                                                        (0 until CHUNKBLOCKS).foreach { y =>
                                                            (0 until CHUNKBLOCKS).foreach { x =>
                                                                (0 until 3).foreach { l =>
                                                                    blocks(l)(twy*CHUNKBLOCKS+y)(twx*CHUNKBLOCKS+x) = chunkMatrix(twy)(twx).blocks(l)(y)(x)
                                                                    power(l)(twy*CHUNKBLOCKS+y)(twx*CHUNKBLOCKS+x) = chunkMatrix(twy)(twx).power(l)(y)(x)
                                                                    pzqn(l)(twy*CHUNKBLOCKS+y)(twx*CHUNKBLOCKS+x) = chunkMatrix(twy)(twx).pzqn(l)(y)(x)
                                                                    arbprd(l)(twy*CHUNKBLOCKS+y)(twx*CHUNKBLOCKS+x) = chunkMatrix(twy)(twx).arbprd(l)(y)(x)
                                                                    blockds(l)(twy*CHUNKBLOCKS+y)(twx*CHUNKBLOCKS+x) = chunkMatrix(twy)(twx).blockds(l)(y)(x)
                                                                }
                                                                blockdns(twy*CHUNKBLOCKS+y)(twx*CHUNKBLOCKS+x) = chunkMatrix(twy)(twx).blockdns(y)(x)
                                                                blockbgs(twy*CHUNKBLOCKS+y)(twx*CHUNKBLOCKS+x) = chunkMatrix(twy)(twx).blockbgs(y)(x)
                                                                blockts(twy*CHUNKBLOCKS+y)(twx*CHUNKBLOCKS+x) = chunkMatrix(twy)(twx).blockts(y)(x)
                                                                lights(twy*CHUNKBLOCKS+y)(twx*CHUNKBLOCKS+x) = chunkMatrix(twy)(twx).lights(y)(x)
                                                                lsources(twy*CHUNKBLOCKS+y)(twx*CHUNKBLOCKS+x) = chunkMatrix(twy)(twx).lsources(y)(x)
                                                                zqn(twy*CHUNKBLOCKS+y)(twx*CHUNKBLOCKS+x) = chunkMatrix(twy)(twx).zqn(y)(x)
                                                                wcnct(twy*CHUNKBLOCKS+y)(twx*CHUNKBLOCKS+x) = chunkMatrix(twy)(twx).wcnct(y)(x)
                                                                drawn(twy*CHUNKBLOCKS+y)(twx*CHUNKBLOCKS+x) = chunkMatrix(twy)(twx).drawn(y)(x)
                                                                rdrawn(twy*CHUNKBLOCKS+y)(twx*CHUNKBLOCKS+x) = chunkMatrix(twy)(twx).rdrawn(y)(x)
                                                                ldrawn(twy*CHUNKBLOCKS+y)(twx*CHUNKBLOCKS+x) = chunkMatrix(twy)(twx).ldrawn(y)(x)
                                                            }
                                                        }
                                                        worlds(twy)(twx) = null
                                                    }
                                                }
                                            }
                                            u = -ou*CHUNKBLOCKS
                                            v = -ov*CHUNKBLOCKS
                                            (0 until 2).foreach { twy =>
                                                (0 until 2).foreach { twx =>
                                                    kworlds(twy)(twx) = false
                                                }
                                            }
                                            var somevar: Boolean = false
                                            (0 until 2).foreach { twy =>
                                                (0 until 2).foreach { twx =>
                                                    val twxc: Int = twx + ou
                                                    val twyc: Int = twy + ov
                                                    if (((player.ix + getWidth()/2 + player.width >= twxc*CHUNKSIZE &&
                                                          player.ix + getWidth()/2 + player.width <= twxc*CHUNKSIZE+CHUNKSIZE) ||
                                                         (player.ix - getWidth()/2 + player.width + BLOCKSIZE >= twxc*CHUNKSIZE &&
                                                          player.ix - getWidth()/2 + player.width - BLOCKSIZE <= twxc*CHUNKSIZE+CHUNKSIZE)) &&
                                                        ((player.iy + getHeight()/2 + player.height >= twyc*CHUNKSIZE &&
                                                          player.iy + getHeight()/2 + player.height <= twyc*CHUNKSIZE+CHUNKSIZE) ||
                                                         (player.iy - getHeight()/2 + player.height >= twyc*CHUNKSIZE &&
                                                          player.iy - getHeight()/2 + player.height <= twyc*CHUNKSIZE+CHUNKSIZE))) {
                                                        kworlds(twy)(twx) = true
                                                        if (worlds(twy)(twx) == null) {
                                                            worlds(twy)(twx) = config.createCompatibleImage(CHUNKSIZE, CHUNKSIZE, Transparency.TRANSLUCENT)
                                                            fworlds(twy)(twx) = config.createCompatibleImage(CHUNKSIZE, CHUNKSIZE, Transparency.TRANSLUCENT)
                                                            println("Created image at " + twx + " " + twy)
                                                        }
                                                        if (worlds(twy)(twx) != null) {
                                                            wg2 = worlds(twy)(twx).createGraphics()
                                                            fwg2 = fworlds(twy)(twx).createGraphics()
                                                            (Math.max(twy*CHUNKSIZE, (player.iy-getHeight()/2+player.height/2+v*BLOCKSIZE)-64) until(Math.min(twy*CHUNKSIZE+CHUNKSIZE, (player.iy+getHeight()/2-player.height/2+v*BLOCKSIZE).toInt+128),BLOCKSIZE)).foreach { tly =>
                                                                (Math.max(twx*CHUNKSIZE, (player.ix-getWidth()/2+player.width/2+u*BLOCKSIZE)-64) until(Math.min(twx*CHUNKSIZE+CHUNKSIZE, (player.ix+getWidth()/2-player.width/2+u*BLOCKSIZE).toInt+112),BLOCKSIZE)).foreach { tlx =>
                                                                    tx = (tlx/BLOCKSIZE)
                                                                    ty = (tly/BLOCKSIZE)
                                                                    if (tx >= 0 && tx < theSize && ty >= 0 && ty < theSize) {
                                                                        if (!drawn(ty)(tx)) {
                                                                            somevar = true
                                                                            blockts(ty)(tx) = random.nextInt(8).toByte
                                                                            (0 until BLOCKSIZE).foreach { y =>
                                                                                (0 until BLOCKSIZE).foreach { x =>
                                                                                    try {
                                                                                        worlds(twy)(twx).setRGB(tx*BLOCKSIZE-twxc*CHUNKSIZE+x, ty*BLOCKSIZE-twyc*CHUNKSIZE+y, 9539985)
                                                                                        fworlds(twy)(twx).setRGB(tx*BLOCKSIZE-twxc*CHUNKSIZE+x, ty*BLOCKSIZE-twyc*CHUNKSIZE+y, 9539985)
                                                                                    }
                                                                                    catch {
                                                                                        case _ : ArrayIndexOutOfBoundsException =>
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (blockbgs(ty)(tx) != 0) {
                                                                                backgroundImgs.get(blockbgs(ty)(tx)).foreach(img =>
                                                                                    wg2.drawImage(img, tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                                                    null))
                                                                            }
                                                                            (0 until 3).foreach { l => 
                                                                                if (blocks(l)(ty)(tx) != 0) {
                                                                                    if (l == 2) {
                                                                                        OUTLINES.get(blocks(l)(ty)(tx)).foreach { outlineName =>
                                                                                            fwg2.drawImage(loadBlock(blocks(l)(ty)(tx), blockds(l)(ty)(tx), blockdns(ty)(tx), blockts(ty)(tx), blocknames, dirs, outlineName, tx, ty, l),
                                                                                                tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null)
                                                                                        }
                                                                                    }
                                                                                    else {
                                                                                        OUTLINES.get(blocks(l)(ty)(tx)).foreach { outlineName =>
                                                                                            wg2.drawImage(loadBlock(blocks(l)(ty)(tx), blockds(l)(ty)(tx), blockdns(ty)(tx), blockts(ty)(tx), blocknames, dirs, outlineName, tx, ty, l),
                                                                                                tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null)
                                                                                        }

                                                                                    }
                                                                                }
                                                                                if (wcnct(ty)(tx) && blocks(l)(ty)(tx) >= 94 && blocks(l)(ty)(tx) <= 99) {
                                                                                    if (l == 2) {
                                                                                        fwg2.drawImage(wcnct_px,
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null)
                                                                                    }
                                                                                    else {
                                                                                        wg2.drawImage(wcnct_px,
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null)
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (!DEBUG_LIGHT) {
                                                                                LIGHTLEVELS.get(lights(ty)(tx).toInt).foreach(fwg2.drawImage(_,
                                                                                    tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                                                    null))
                                                                            }
                                                                            drawn(ty)(tx) = true
                                                                            rdrawn(ty)(tx) = true
                                                                            ldrawn(ty)(tx) = true
                                                                        }
                                                                        if (!rdrawn(ty)(tx)) {
                                                                            somevar = true
                                                                            (0 until BLOCKSIZE).foreach { y =>
                                                                                (0 until BLOCKSIZE).foreach { x =>
                                                                                    try {
                                                                                        worlds(twy)(twx).setRGB(tx*BLOCKSIZE-twxc*CHUNKSIZE+x, ty*BLOCKSIZE-twyc*CHUNKSIZE+y, 9539985)
                                                                                        fworlds(twy)(twx).setRGB(tx*BLOCKSIZE-twxc*CHUNKSIZE+x, ty*BLOCKSIZE-twyc*CHUNKSIZE+y, 9539985)
                                                                                    }
                                                                                    catch {
                                                                                        case _: ArrayIndexOutOfBoundsException =>
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (blockbgs(ty)(tx) != 0) {
                                                                                backgroundImgs.get(blockbgs(ty)(tx)).foreach(wg2.drawImage(_,
                                                                                    tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                                                    null))
                                                                            }
                                                                            (0 until 3).foreach { l =>
                                                                                if (blocks(l)(ty)(tx) != 0) {
                                                                                    if (l == 2) {
                                                                                        OUTLINES.get(blocks(l)(ty)(tx)).foreach { outlineName =>
                                                                                            fwg2.drawImage(loadBlock(blocks(l)(ty)(tx), blockds(l)(ty)(tx), blockdns(ty)(tx), blockts(ty)(tx), blocknames, dirs, outlineName, tx, ty, l),
                                                                                                tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null)
                                                                                        }
                                                                                    }
                                                                                    else {
                                                                                        OUTLINES.get(blocks(l)(ty)(tx)).foreach { outlineName =>
                                                                                            wg2.drawImage(loadBlock(blocks(l)(ty)(tx), blockds(l)(ty)(tx), blockdns(ty)(tx), blockts(ty)(tx), blocknames, dirs, outlineName, tx, ty, l),
                                                                                                tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null)
                                                                                        }

                                                                                    }
                                                                                }
                                                                                if (wcnct(ty)(tx) && blocks(l)(ty)(tx) >= 94 && blocks(l)(ty)(tx) <= 99) {
                                                                                    if (l == 2) {
                                                                                        fwg2.drawImage(wcnct_px,
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null)
                                                                                    }
                                                                                    else {
                                                                                        wg2.drawImage(wcnct_px,
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null)
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (!DEBUG_LIGHT) {
                                                                                LIGHTLEVELS.get(lights(ty)(tx).toInt).foreach(fwg2.drawImage(_,
                                                                                    tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                                                    null))
                                                                            }
                                                                            drawn(ty)(tx) = true
                                                                            rdrawn(ty)(tx) = true
                                                                            ldrawn(ty)(tx) = true
                                                                        }
                                                                        if (!ldrawn(ty)(tx) && random.nextInt(10) == 0) {
                                                                            somevar = true
                                                                            (0 until BLOCKSIZE).foreach { y =>
                                                                                (0 until BLOCKSIZE).foreach { x =>
                                                                                    try {
                                                                                        worlds(twy)(twx).setRGB(tx*BLOCKSIZE-twxc*CHUNKSIZE+x, ty*BLOCKSIZE-twyc*CHUNKSIZE+y, 9539985)
                                                                                        fworlds(twy)(twx).setRGB(tx*BLOCKSIZE-twxc*CHUNKSIZE+x, ty*BLOCKSIZE-twyc*CHUNKSIZE+y, 9539985)
                                                                                    }
                                                                                    catch {
                                                                                        case _: ArrayIndexOutOfBoundsException => 
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (blockbgs(ty)(tx) != 0) {
                                                                                backgroundImgs.get(blockbgs(ty)(tx)).foreach(wg2.drawImage(_,
                                                                                    tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                                                    null))
                                                                            }
                                                                            (0 until 3).foreach { l =>
                                                                                if (blocks(l)(ty)(tx) != 0) {
                                                                                    if (l == 2) {
                                                                                        OUTLINES.get(blocks(l)(ty)(tx)).foreach { outlineName =>
                                                                                            fwg2.drawImage(loadBlock(blocks(l)(ty)(tx), blockds(l)(ty)(tx), blockdns(ty)(tx), blockts(ty)(tx), blocknames, dirs, outlineName, tx, ty, l),
                                                                                                tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null)
                                                                                        }

                                                                                    }
                                                                                    else {
                                                                                        OUTLINES.get(blocks(l)(ty)(tx)).foreach { outlineName =>
                                                                                            wg2.drawImage(loadBlock(blocks(l)(ty)(tx), blockds(l)(ty)(tx), blockdns(ty)(tx), blockts(ty)(tx), blocknames, dirs, outlineName, tx, ty, l),
                                                                                                tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null)
                                                                                        }

                                                                                    }
                                                                                }
                                                                                if (wcnct(ty)(tx) && blocks(l)(ty)(tx) >= 94 && blocks(l)(ty)(tx) <= 99) {
                                                                                    if (l == 2) {
                                                                                        fwg2.drawImage(wcnct_px,
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null)
                                                                                    }
                                                                                    else {
                                                                                        wg2.drawImage(wcnct_px,
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null)
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (!DEBUG_LIGHT) {
                                                                                LIGHTLEVELS.get(lights(ty)(tx).toInt)
                                                                                  .foreach(fwg2.drawImage(_,
                                                                                    tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                                                    null))
                                                                            }
                                                                            drawn(ty)(tx) = true
                                                                            rdrawn(ty)(tx) = true
                                                                            ldrawn(ty)(tx) = true
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if (somevar) {
                                                println("Drew at least one block.")
                                            }
                                            (0 until 2).foreach { twy =>
                                                (0 until 2).foreach { twx =>
                                                    if (!kworlds(twy)(twx) && worlds(twy)(twx) != null) {
                                                        worlds(twy)(twx) = null
                                                        fworlds(twy)(twx) = null
                                                        (twy*CHUNKBLOCKS until twy*CHUNKBLOCKS+CHUNKBLOCKS).foreach { ty =>
                                                            (twx*CHUNKBLOCKS until twx*CHUNKBLOCKS+CHUNKBLOCKS).foreach { tx =>
                                                                if (tx >= 0 && tx < theSize && ty >= 0 && ty < theSize) {
                                                                    drawn(ty)(tx) = false
                                                                }
                                                            }
                                                        }
                                                        println("Destroyed image at " + twx + " " + twy)
                                                    }
                                                }
                                            }
                                            updateApp()
                                            updateEnvironment()
                                            player.update(blocks(1), queue, u, v)
                                            if (timeOfDay >= 86400) {
                                                timeOfDay = 0
                                                day += 1
                                            }
                                            repaint()
                                            ready = true
                                        }
                                    }
                                    catch {
                                        case NonFatal(e) => postError(e)
                                    }
                                }
                            }
                            timer = new javax.swing.Timer(20, mainthread)

                            if (state.equals("title_screen") && !menuPressed) {
                                if (mousePos(0) >= 239 && mousePos(0) <= 557) {
                                    if (mousePos(1) >= 213 && mousePos(1) <= 249) { // singleplayer
                                        findWorlds()
                                        state = "select_world"
                                        repaint()
                                        menuPressed = true
                                    }
                                }
                            }
                            if (state.equals("select_world") && !menuPressed) {
                                if (mousePos(0) >= 186 && mousePos(0) <= 615 &&
                                    mousePos(1) >= 458 && mousePos(1) <= 484) { // create new world
                                    state = "new_world"
                                    newWorldName = new TextField(400, "New World")
                                    repaint()
                                    menuPressed = true
                                }
                                if (mousePos(0) >= 334 && mousePos(0) <= 457 &&
                                    mousePos(1) >= 504 && mousePos(1) <= 530) { // back
                                    state = "title_screen"
                                    repaint()
                                    menuPressed = true
                                }
                                import scala.util.control.Breaks._
                                breakable {
                                    (0 until worldFiles.length).foreach { i =>
                                        if (mousePos(0) >= 166 && mousePos(0) <= 470 &&
                                          mousePos(1) >= 117 + i * 35 && mousePos(1) <= 152 + i * 35) { // load world
                                            currentWorld = worldNames(i)
                                            state = "loading_world"
                                            bg = Color.BLACK
                                            if (loadWorld(worldFiles(i))) {
                                                menuTimer.stop()
                                                bg = CYANISH
                                                state = "ingame"
                                                ready = true
                                                timer.start()
                                                break
                                            }
                                        }
                                    }
                                }
                            }
                            if (state.equals("new_world") && !menuPressed) {
                                if (mousePos(0) >= 186 && mousePos(0) <= 615 &&
                                    mousePos(1) >= 458 && mousePos(1) <= 484) { // create new world
                                    if (!newWorldName.text.equals("")) {
                                        findWorlds()
                                        doGenerateWorld = true
                                        (0 until worldNames.length).foreach { i =>
                                            if (newWorldName.text.equals(worldNames(i))) {
                                                doGenerateWorld = false
                                            }
                                        }
                                        if (doGenerateWorld) {
                                            menuTimer.stop()
                                            bg = Color.BLACK
                                            state = "generating_world"
                                            currentWorld = newWorldName.text
                                            repaint()
                                            val createWorldThread: Action = new AbstractAction() {
                                                def actionPerformed(ae: ActionEvent): Unit = {
                                                    try {
                                                        createNewWorld()
                                                        bg = CYANISH
                                                        state = "ingame"
                                                        ready = true
                                                        timer.start()
                                                        createWorldTimer.stop()
                                                    }
                                                    catch {
                                                        case NonFatal(e) => postError(e)
                                                    }
                                                }
                                            }
                                            createWorldTimer = new javax.swing.Timer(1, createWorldThread)
                                            createWorldTimer.start()
                                        }
                                    }
                                }
                                if (mousePos(0) >= 334 && mousePos(0) <= 457 &&
                                    mousePos(1) >= 504 && mousePos(1) <= 530) { // back
                                    state = "select_world"
                                    repaint()
                                    menuPressed = true
                                }
                            }
                        }
                    }
                    catch {
                        case NonFatal(e) => postError(e)
                    }
                }
            })

            menuTimer.start()
        }

        catch {
            case NonFatal(e) => postError(e)
        }
    }

    def codeTooLarge(): Unit = {
        
        val M: Int = Int.MaxValue
        //                     DirStoCopIroSilGolWooWor                  TreLeaFurCoaLum         Fur                     ZinRhyObdAluLeaUraZytZytSilIrrNulMelSkyMagSanSnoGla                                                                        GraJGrSGrMudSSt                  TreCobCStCCoSBrClaCBrVWoTDiTMaTGrZyW
        val durList: Array2D[Int] = Array(Array(0, 2, 4, 4, 4, 4, M, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 4, 3, M, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 0, M, M, M, 4, M, M, M, M, M, M, M, M, M, M, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 1, 1, 1, 2, 2, 2, 0, 4, 4, 4, 4, 2, 4, 0, 2, M, 2, 1, 1, 1, 1, 1, 1), // Copper Pick
                           Array(0, 2, 4, 4, 4, 4, 4, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 4, 3, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 0, M, M, M, 4, M, M, M, M, M, M, M, M, 4, 4, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 1, 1, 1, 2, 2, 2, 0, 4, 4, 4, 4, 2, 4, 0, 2, 4, 2, 1, 1, 1, 1, 1, 1), // Iron Pick
                           Array(0, 2, 3, 4, 4, 4, 4, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 4, 2, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 0, 4, M, M, 3, M, 4, M, M, M, M, M, M, 4, 4, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 0, 3, 3, 3, 3, 2, 3, 0, 2, 4, 2, 1, 1, 1, 1, 1, 1), // Silver Pick
                           Array(0, 2, 3, 3, 3, 4, 4, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 3, 2, 4, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 0, 4, 4, M, 3, 4, 4, M, M, M, M, M, M, 3, 3, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 0, 3, 3, 3, 3, 2, 3, 0, 2, 3, 2, 1, 1, 1, 1, 1, 1), // Gold Pick
                           Array(0, 0, 0, 0, 0, 0, 0, 3, 2, 2, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 1, 1, 1, 1, 1, 1), // Copper Axe
                           Array(0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 1, 1, 1, 1, 1), // Iron Axe
                           Array(0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 1, 1, 1, 1, 1), // Silver Axe
                           Array(0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 1, 1, 1, 1, 1), // Gold Axe
                           Array(0, 1, 2, 3, 3, 3, 3, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 3, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 0, 4, 4, M, 2, 4, 3, 4, 4, 4, M, M, M, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 2, 2, 0, 2, 2, 2, 2, 1, 2, 0, 1, 3, 1, 1, 1, 1, 1, 1, 1), // Zinc Pick
                           Array(0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 1, 1, 1, 1, 1), // Zinc Axe
                           Array(0, 1, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 0, 3, 3, 4, 1, 3, 2, 3, 3, 3, M, 4, 4, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 0, 2, 2, 2, 2, 1, 2, 0, 1, 2, 1, 1, 1, 1, 1, 1, 1), // Rhymestone Pick
                           Array(0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1), // Rhymestone Axe
                           Array(0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 0, 2, 2, 3, 1, 2, 2, 2, 2, 2, 4, 4, 4, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1), // Obdurite Pick
                           Array(0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1), // Obdurite Axe
                           Array(0, 2, 3, 3, 3, 4, 4, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 3, 2, 4, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 0, 4, 4, M, 3, 4, 4, M, M, M, M, M, M, 3, 3, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 0, 3, 3, 3, 3, 2, 3, 0, 2, 3, 2, 1, 1, 1, 1, 1, 1), // Aluminum Pick
                           Array(0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 1, 1, 1, 1, 1), // Aluminum Axe
                           Array(0, 1, 2, 3, 3, 3, 3, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 3, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 0, 4, 4, M, 2, 4, 3, 4, 4, 4, M, M, M, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 2, 2, 0, 2, 2, 2, 2, 1, 2, 0, 1, 3, 1, 1, 1, 1, 1, 1, 1), // Lead Pick
                           Array(0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 1, 1, 1, 1, 1), // Lead Axe
                           Array(0, 4, 6, 6, M, M, M, 0, 0, 0, 4, 4, 4, 4, 4, 0, 0, 6, 5, M, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 0, M, M, M, M, M, M, M, M, M, M, M, M, M, M, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 5, 1, 1, 1, 4, 4, 4, 0, 6, 6, 6, 6, 4, 6, 0, 4, M, 4, 1, 1, 1, 1, 1, 1), // Wooden Pick
                           Array(0, 0, 0, 0, 0, 0, 0, 5, 4, 4, 0, 0, 0, 0, 0, 15,0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 15,0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 1, 1, 1, 1, 1, 1), // Wooden Axe
                           Array(0, 3, 5, 5, 6, M, M, 0, 0, 0, 3, 3, 3, 3, 3, 0, 0, 5, 4, M, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 0, M, M, M, 5, M, M, M, M, M, M, M, M, M, M, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 4, 1, 1, 1, 3, 3, 3, 0, 5, 5, 5, 5, 3, 5, 0, 3, M, 3, 1, 1, 1, 1, 1, 1), // Stone Pick
                           Array(0, 0, 0, 0, 0, 0, 0, 4, 3, 3, 0, 0, 0, 0, 0, 11,0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 11,0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 1, 1, 1, 1, 1, 1), // Stone Axe
                           Array(0, 2, 3, 3, 3, 4, 4, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 3, 2, 4, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 0, 4, 4, M, 3, 4, 4, M, M, M, M, M, M, 3, 3, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 0, 3, 3, 3, 3, 2, 3, 0, 2, 3, 2, 1, 1, 1, 1, 1, 1), // Magnetite Pick
                           Array(0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 1, 1, 1, 1, 1), // Magnetite Axe
                           Array(0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1), // Irradium Pick
                           Array(0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1))// Irradium Axe

        //                  ZyT      ZyLZyLLev               ZyR                     ZyI                     But         WoP   StP   ZyP   ZyD
        val durLis2: Array2D[Int] = Array(Array(1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3), // Copper Pick
                           Array(1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3), // Iron Pick
                           Array(1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2), // Silver Pick
                           Array(1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2), // Gold Pick
                           Array(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // Copper Axe
                           Array(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // Iron Axe
                           Array(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // Silver Axe
                           Array(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // Gold Axe
                           Array(1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2), // Zinc Pick
                           Array(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // Zinc Axe
                           Array(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), // Rhymestone Pick
                           Array(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // Rhymestone Axe
                           Array(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), // Obdurite Pick
                           Array(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // Obdurite Axe
                           Array(1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2), // Aluminum Pick
                           Array(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // Aluminum Axe
                           Array(1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2), // Lead Pick
                           Array(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // Lead Axe
                           Array(1, 1, 1, 5, 5, 5, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 0, 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5), // Wooden Pick
                           Array(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // Wooden Axe
                           Array(1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4), // Stone Pick
                           Array(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // Stone Axe
                           Array(1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2), // Magnetite Pick
                           Array(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // Magnetite Axe
                           Array(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), // Irradium Pick
                           Array(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))// Irradium Axe

        val durabilityTemp = new jul.HashMap[Short,Map[Int,Int]](toolList.length * toolList.length)
        
        (0 until toolList.length).foreach { i =>
            val dur = new java.util.HashMap[Int,Int](durList(i).length * durLis2(i).length)
            (0 until durList(i).length).foreach { j =>
                dur.put(j, durList(i)(j))
            }
            (0 until durLis2(i).length).foreach { j =>
                dur.put(j+100, durLis2(i)(j))
            }
            durabilityTemp.put(toolList(i), dur.asScala.toMap)
        }

        DURABILITY = durabilityTemp.asScala.toMap
    }

    def findWorlds(): Unit = {
        folder = new File("worlds")
        folder.mkdir()
        files = folder.listFiles()

        val (wordFilesTemp, worldNamesTemp) =files.filter(f => f.isFile() && f.getName().endsWith(".dat"))
          .foldLeft((List.empty[String], List.empty[String])) {
            (acc, file) =>
            val wfs = acc._1
            val wns = acc._2
            val newFile = file.getName()
            val newName = newFile.substring(0, newFile.length()-4)
            (newFile :: wfs, newName :: wns)
        }

        worldFiles = wordFilesTemp
        worldNames = worldNamesTemp

    }

    def createNewWorld(): Unit = {
        temporarySaveFile = Array.ofDim(WORLDHEIGHT,WORLDWIDTH)
        chunkMatrix = Array.ofDim(2,2)

        blocks = Array.ofDim(3, theSize, theSize)
        blockds = Array.ofDim(3,theSize,theSize)
        blockdns = Array.ofDim(theSize,theSize)
        blockbgs = Array.ofDim(theSize,theSize)
        blockts = Array.ofDim(theSize,theSize)
        lights = Array.ofDim(theSize,theSize)
        power = Array.ofDim(3,theSize,theSize)
        lsources = Array.ofDim(theSize,theSize)
        zqn = Array.ofDim(theSize,theSize)
        pzqn = Array.ofDim(3,theSize,theSize)
        arbprd = Array.ofDim(3,theSize,theSize)
        wcnct = Array.ofDim(theSize,theSize)
        drawn = Array.ofDim(theSize,theSize)
        rdrawn = Array.ofDim(theSize,theSize)
        ldrawn = Array.ofDim(theSize,theSize)

        player = new Player(WIDTH*0.5*BLOCKSIZE, 45)

        inventory = new Inventory()

        if (DEBUG_ITEMS != null) {
            if (DEBUG_ITEMS.equals("normal")) {
                inventory.addItem(172.toShort, 1.toShort)
                inventory.addItem(173.toShort, 1.toShort)
                inventory.addItem(174.toShort, 1.toShort)
                inventory.addItem(164.toShort, 100.toShort)
                inventory.addItem(35.toShort, 100.toShort)
                inventory.addItem(36.toShort, 100.toShort)
                inventory.addItem(37.toShort, 100.toShort)
                inventory.addItem(20.toShort, 5.toShort)
                inventory.addItem(27.toShort, 5.toShort)
                inventory.addItem(33.toShort, 1.toShort)
                inventory.addItem(28.toShort, 100.toShort)
                inventory.addItem(50.toShort, 100.toShort)
                inventory.addItem(1.toShort, 100.toShort)
                inventory.addItem(2.toShort, 100.toShort)
                inventory.addItem(15.toShort, 100.toShort)
            }
            if (DEBUG_ITEMS.equals("tools")) {
                inventory.addItem(154.toShort, 1.toShort)
                inventory.addItem(155.toShort, 1.toShort)
                inventory.addItem(156.toShort, 1.toShort)
                inventory.addItem(157.toShort, 1.toShort)
                inventory.addItem(158.toShort, 1.toShort)
                inventory.addItem(159.toShort, 1.toShort)
                inventory.addItem(7.toShort, 1.toShort)
                inventory.addItem(11.toShort, 1.toShort)
                inventory.addItem(12.toShort, 1.toShort)
                inventory.addItem(8.toShort, 1.toShort)
                inventory.addItem(13.toShort, 1.toShort)
                inventory.addItem(14.toShort, 1.toShort)
                inventory.addItem(9.toShort, 1.toShort)
                inventory.addItem(16.toShort, 1.toShort)
                inventory.addItem(17.toShort, 1.toShort)
                inventory.addItem(10.toShort, 1.toShort)
                inventory.addItem(18.toShort, 1.toShort)
                inventory.addItem(33.toShort, 1.toShort)
                inventory.addItem(51.toShort, 1.toShort)
                inventory.addItem(52.toShort, 1.toShort)
                inventory.addItem(53.toShort, 1.toShort)
                inventory.addItem(54.toShort, 1.toShort)
                inventory.addItem(55.toShort, 1.toShort)
                inventory.addItem(56.toShort, 1.toShort)
                inventory.addItem(57.toShort, 1.toShort)
                inventory.addItem(58.toShort, 1.toShort)
                inventory.addItem(59.toShort, 1.toShort)
                inventory.addItem(145.toShort, 1.toShort)
                inventory.addItem(146.toShort, 1.toShort)
                inventory.addItem(147.toShort, 1.toShort)
                inventory.addItem(148.toShort, 1.toShort)
                inventory.addItem(149.toShort, 1.toShort)
                inventory.addItem(150.toShort, 1.toShort)
                inventory.addItem(169.toShort, 1.toShort)
                inventory.addItem(170.toShort, 1.toShort)
                inventory.addItem(171.toShort, 1.toShort)
                inventory.addItem(172.toShort, 1.toShort)
                inventory.addItem(173.toShort, 1.toShort)
                inventory.addItem(174.toShort, 1.toShort)

                inventory.addItem(19.toShort, 1.toShort)
            }
            if (DEBUG_ITEMS.equals("testing")) {
                inventory.addItem(172.toShort, 1.toShort)
                inventory.addItem(173.toShort, 1.toShort)
                inventory.addItem(175.toShort, 100.toShort)
                inventory.addItem(15.toShort, 100.toShort)
                inventory.addItem(35.toShort, 100.toShort)
                inventory.addItem(36.toShort, 100.toShort)
                inventory.addItem(37.toShort, 100.toShort)
                inventory.addItem(176.toShort, 100.toShort)
                inventory.addItem(177.toShort, 100.toShort)
                inventory.addItem(178.toShort, 100.toShort)
                inventory.addItem(27.toShort, 100.toShort)
                inventory.addItem(33.toShort, 1.toShort)
                inventory.addItem(86.toShort, 100.toShort)
                inventory.addItem(49.toShort, 100.toShort)
                inventory.addItem(180.toShort, 100.toShort)
                inventory.addItem(181.toShort, 100.toShort)
                inventory.addItem(182.toShort, 100.toShort)
                inventory.addItem(183.toShort, 100.toShort)
                inventory.addItem(184.toShort, 100.toShort)
                inventory.addItem(185.toShort, 100.toShort)
                inventory.addItem(186.toShort, 100.toShort)
                inventory.addItem(187.toShort, 100.toShort)
                inventory.addItem(188.toShort, 100.toShort)
                inventory.addItem(189.toShort, 100.toShort)
                inventory.addItem(190.toShort, 1.toShort)
            }
        }

        val tlist1: Array[Short] = Array[Short](0, 0, 0, 0, 0)
        val tlist2: Array[Short] = Array[Short](0, 0, 0, 0, 0)
        val tlist3: Array[Short] = Array[Short](0, 0, 0, 0, 0)
        cic = new ItemCollection("cic", tlist1, tlist2, tlist3)
        inventory.renderCollection(cic)

        val tlist4: Array[Short] = Array[Short](0, 0, 0, 0)
        val tlist5: Array[Short] = Array[Short](0, 0, 0, 0)
        val tlist6: Array[Short] = Array[Short](0, 0, 0, 0)
        armor = new ItemCollection("armor", tlist4, tlist5, tlist6)
        inventory.renderCollection(armor)

        toolAngle = 4.7
        mining = 0
        miningTool = 0
        mx = 0
        my = 0
        moveItem = 0
        moveNum = 0
        moveDur = 0

        entities = new jul.ArrayList[Entity](0)

        cloudsx = new jul.ArrayList[Double](0)
        cloudsy = new jul.ArrayList[Double](0)
        cloudsv = new jul.ArrayList[Double](0)
        cloudsn = new jul.ArrayList[Int](0)

        machinesx = new jul.ArrayList[Int](0)
        machinesy = new jul.ArrayList[Int](0)

        icmatrix = Array.ofDim(3,HEIGHT,WIDTH)

        worlds = Array.ofDim(2,2)
        fworlds = Array.ofDim(2,2)
        kworlds = Array.ofDim(2,2)

        pqx = new jul.ArrayList[Int]()
        pqy = new jul.ArrayList[Int]()

        println("-> Adding light sources...")

        lqx = new jul.ArrayList[Int]()
        lqy = new jul.ArrayList[Int]()
        zqx = new jul.ArrayList[Int]()
        zqy = new jul.ArrayList[Int]()
        pqx = new jul.ArrayList[Int]()
        pqy = new jul.ArrayList[Int]()
        pzqx = new jul.ArrayList[Int]()
        pzqy = new jul.ArrayList[Int]()
        updatex = new jul.ArrayList[Int]()
        updatey = new jul.ArrayList[Int]()
        updatet = new jul.ArrayList[Int]()
        updatel = new jul.ArrayList[Int]()

        (0 until WIDTH).foreach { x =>
//            addSunLighting(x, 0)
        }

        (0 until WIDTH).foreach { x =>
            (0 until HEIGHT).foreach { y =>
//                addBlockLighting(x, y)
//                addBlockPower(x, y)
            }
        }

        println("-> Calculating light...")

        resolvePowerMatrix()
        resolveLightMatrix()

        println("Finished generation.")
    }

    def updateApp(): Unit = {
        mousePos2(0) = mousePos(0) + player.ix - getWidth()/2 + player.width/2
        mousePos2(1) = mousePos(1) + player.iy - getHeight()/2 + player.height/2

        currentSkyLight = skycolors(0)
        (0 until skycolors.length).foreach { i =>
            if (timeOfDay >= skycolors(i)) {
                currentSkyLight = skycolors(i)
            }
        }
        if (player.y/16 > HEIGHT * 0.55) {
            bg = Color.BLACK
        }
        else {
            SKYCOLORS.get(currentSkyLight).foreach{ s =>
              bg = s
            }
        }

        if (rgnc1 == 0) {
            if (rgnc2 == 0) {
                if (player.hp < player.thp) {
                    player.hp += 1
                    rgnc2 = 125
                }
            }
            else {
                rgnc2 -= 1
            }
        }
        else {
            rgnc1 -= 1
        }

        (0 until machinesx.size()).foreach { j =>
            x = machinesx.get(j)
            y = machinesy.get(j)
            (0 until 3).foreach { l =>
                if (icmatrix(l)(y)(x) != null && icmatrix(l)(y)(x).`type`.equals("furnace")) {
                    if (icmatrix(l)(y)(x).F_ON) {
                        if (icmatrix(l)(y)(x).ids(1) == 0) {
                            if (FUELS.get(icmatrix(l)(y)(x).ids(2)) != null) {
                                inventory.addLocationIC(icmatrix(l)(y)(x), 1, icmatrix(l)(y)(x).ids(2), 1.toShort)
                                inventory.removeLocationIC(icmatrix(l)(y)(x), 2, 1.toShort)
                                icmatrix(l)(y)(x).FUELP = 1
                            }
                            else {
                                icmatrix(l)(y)(x).F_ON = false
                                removeBlockLighting(x, y)
                                blocks(l)(y)(x) = 17
                                rdrawn(y)(x) = false
                            }
                        }
                        for {
                            fuel <- FUELS.get(icmatrix(l)(y)(x).ids(1))
                        } yield {
                            icmatrix(l)(y)(x).FUELP -= fuel
                            if (icmatrix(l)(y)(x).FUELP < 0) {
                                icmatrix(l)(y)(x).FUELP = 0
                                inventory.removeLocationIC(icmatrix(l)(y)(x), 1, icmatrix(l)(y)(x).nums(1))
                            }
                            import scala.util.control.Breaks._
                            breakable {
                                (0 until FRI1.length).foreach { i =>
                                    FSPEED.get(icmatrix(l)(y)(x).ids(1)).foreach { fspeed =>
                                        if (icmatrix(l)(y)(x).ids(0) == FRI1(i) && icmatrix(l)(y)(x).nums(0) >= FRN1(i)) {
                                            icmatrix(l)(y)(x).SMELTP += fspeed
                                            if (icmatrix(l)(y)(x).SMELTP > 1) {
                                                icmatrix(l)(y)(x).SMELTP = 0
                                                inventory.removeLocationIC(icmatrix(l)(y)(x), 0, FRN1(i))
                                                inventory.addLocationIC(icmatrix(l)(y)(x), 3, FRI2(i), FRN2(i))
                                            }
                                            break
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else {
                        icmatrix(l)(y)(x).SMELTP -= 0.00025
                        if (icmatrix(l)(y)(x).SMELTP < 0) {
                            icmatrix(l)(y)(x).SMELTP = 0
                        }
                    }
                }
            }
        }

        if (ic != null && ic.`type`.equals("furnace")) {
            if (ic.F_ON) {
                if (ic.ids(1) == 0) {
                    if (FUELS.get(ic.ids(2)) != null) {
                        inventory.addLocationIC(ic, 1, ic.ids(2), 1.toShort)
                        inventory.removeLocationIC(ic, 2, 1.toShort)
                        ic.FUELP = 1
                    }
                    else {
                        ic.F_ON = false
                        removeBlockLighting(icx, icy)
                        blocks(iclayer)(icy)(icx) = 17
                        rdrawn(icy)(icx) = false
                    }
                }
                FUELS.get(ic.ids(1)).foreach { fuels =>
                    ic.FUELP -= fuels
                    if (ic.FUELP < 0) {
                        ic.FUELP = 0
                        inventory.removeLocationIC(ic, 1, ic.nums(1))
                    }
                    import scala.util.control.Breaks._
                    breakable {
                        (0 until FRI1.length).foreach { i =>
                            FSPEED.get(ic.ids(1)).foreach { fspeed =>
                                if (ic.ids(0) == FRI1(i) && ic.nums(0) >= FRN1(i)) {
                                    ic.SMELTP += fspeed
                                    if (ic.SMELTP > 1) {
                                        ic.SMELTP = 0
                                        inventory.removeLocationIC(ic, 0, FRN1(i))
                                        inventory.addLocationIC(ic, 3, FRI2(i), FRN2(i))
                                    }
                                    break
                                }
                            }
                        }
                    }
                }
            }
            else {
                ic.SMELTP -= 0.00025
                if (ic.SMELTP < 0) {
                    ic.SMELTP = 0
                }
            }
            inventory.updateIC(ic, -1)
        }
        if (Math.sqrt(Math.pow(player.x+player.image.getWidth()-icx*BLOCKSIZE+BLOCKSIZE/2, 2) + Math.pow(player.y+player.image.getHeight()-icy*BLOCKSIZE+BLOCKSIZE/2, 2)) > 160) {
            if (ic != null) {
                if (!ic.`type`.equals("workbench")) {
                    machinesx.add(icx)
                    machinesy.add(icy)
                    icmatrix(iclayer)(icy)(icx) = new ItemCollection(ic.`type`, ic.ids, ic.nums, ic.durs)
                }
                if (ic.`type`.equals("workbench")) {
                    if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                        (0 until 9).foreach { i =>
                            if (ic.ids(i) != 0) {
                                entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, 2, -2, ic.ids(i), ic.nums(i), ic.durs(i), 75))
                            }
                        }
                    }
                    if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                        (0 until 9).foreach { i =>
                            if (ic.ids(i) != 0) {
                                entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, -2, -2, ic.ids(i), ic.nums(i), ic.durs(i), 75))
                            }
                        }
                    }
                }
                if (ic.`type`.equals("furnace")) {
                    icmatrix(iclayer)(icy)(icx).FUELP = ic.FUELP
                    icmatrix(iclayer)(icy)(icx).SMELTP = ic.SMELTP
                    icmatrix(iclayer)(icy)(icx).F_ON = ic.F_ON
                }
                ic = null
            }
        }

        (0 until 3).foreach { l =>
            (0 until theSize).foreach { y =>
                (0 until theSize).foreach { x =>
                    if (random.nextInt(22500) == 0) {
                        t = 0
                        blocks(l)(y)(x) match {
                            case 48 if (timeOfDay >= 75913 || timeOfDay < 28883) => t = 49
                            case 49 if (timeOfDay >= 75913 || timeOfDay < 28883) => t = 50
                            case 51 if (timeOfDay >= 32302 && timeOfDay < 72093) => t = 52
                            case 52 if (timeOfDay >= 32302 && timeOfDay < 72093) => t = 53
                            case 54 if (checkBiome(x, y).equals("desert")) => t = 55
                            case 55 if (checkBiome(x, y).equals("desert")) => t = 56
                            case 57 if (checkBiome(x, y).equals("jungle")) => t = 58
                            case 58 if (checkBiome(x, y).equals("jungle")) => t = 59
                            case 60 if (checkBiome(x, y).equals("frost")) => t = 61
                            case 61 if (checkBiome(x, y).equals("frost")) => t = 62
                            case 63 if (checkBiome(x, y).equals("cavern") || y >= 0/*stonelayer(x)*/) => t = 64
                            case 64 if (checkBiome(x, y).equals("cavern") || y >= 0/*stonelayer(x)*/) => t = 65
                            case 66 if (y <= HEIGHT*0.08 && random.nextInt(3) == 0 || y <= HEIGHT*0.04) => t = 67
                            case 67 if (y <= HEIGHT*0.08 && random.nextInt(3) == 0 || y <= HEIGHT*0.04) => t = 68
                            case 69 if (y >= HEIGHT*0.98) => t = 70
                            case 70 if (y >= HEIGHT*0.98) => t = 71
                            case 77 if (checkBiome(x, y).equals("swamp")) => t = 78
                            case 78 if (checkBiome(x, y).equals("swamp")) => t = 79
                            case _ =>
                        }
                        if (t != 0) {
                            blocks(l)(y)(x) = t
                            drawn(y)(x) = false
                        }
                    }
                }
            }
        }

        (0 until 3).foreach { l =>
            (0 until theSize).foreach { y =>
                (0 until theSize).foreach { x =>
                    if (random.nextInt(1000) == 0) {
                        if (y >= 1 && y < HEIGHT-1) {
                            doGrassGrow = false
                            if (blocks(l)(y)(x) == 1 && hasOpenSpace(x+u, y+v, l) && blocks(l)(y+random.nextInt(3)-1+u)(x+random.nextInt(3)-1+v) == 72) {
                                blocks(l)(y)(x) = 72
                                doGrassGrow = true
                            }
                            if (blocks(l)(y)(x) == 1 && hasOpenSpace(x+u, y+v, l) && blocks(l)(y+random.nextInt(3)-1+u)(x+random.nextInt(3)-1+v) == 73) {
                                blocks(l)(y)(x) = 73
                                doGrassGrow = true
                            }
                            if (blocks(l)(y)(x) == 75 && hasOpenSpace(x+u, y+v, l) && blocks(l)(y+random.nextInt(3)-1+u)(x+random.nextInt(3)-1+v) == 74) {
                                blocks(l)(y)(x) = 74
                                doGrassGrow = true
                            }
                            if (doGrassGrow) {
                                (y-1 until y+2).foreach { y2 =>
                                    (x-1 until x+2).foreach { x2 =>
                                        if (y2 >= 0 && y2 < HEIGHT) {
                                            drawn(y2)(x2) = false
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        (0 until 3).foreach { l =>
            (0 until theSize).foreach { y =>
                (0 until theSize).foreach { x =>
                    if (random.nextInt(1000) == 0) {
                        if (blocks(1)(y)(x) == 83) {
                            blocks(1)(y)(x) = 15
                        }
                    }
                }
            }
        }

        (updatex.size()-1 until(-1, -1)).foreach { i =>
            updatet.set(i, updatet.get(i) - 1)
            if (updatet.get(i) <= 0) {
                if (blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) == 128) {
                    blockTemp = blocks(updatel.get(i))(updatey.get(i))(updatex.get(i))
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i))
                    blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) = 127
                    rdrawn(updatey.get(i))(updatex.get(i)) = false
                }
                else if (blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) == 130) {
                    blockTemp = blocks(updatel.get(i))(updatey.get(i))(updatex.get(i))
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i))
                    blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) = 129
                    rdrawn(updatey.get(i))(updatex.get(i)) = false
                }
                else if (blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) == 132) {
                    blockTemp = blocks(updatel.get(i))(updatey.get(i))(updatex.get(i))
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i))
                    blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) = 131
                    rdrawn(updatey.get(i))(updatex.get(i)) = false
                }
                else if (blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) == 134) {
                    blockTemp = blocks(updatel.get(i))(updatey.get(i))(updatex.get(i))
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i))
                    blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) = 133
                    rdrawn(updatey.get(i))(updatex.get(i)) = false
                }
                else if (blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) == 136) {
                    blockTemp = blocks(updatel.get(i))(updatey.get(i))(updatex.get(i))
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i))
                    blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) = 135
                    rdrawn(updatey.get(i))(updatex.get(i)) = false
                }
                else if (blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) >= 141 && blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) <= 144 || blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) >= 149 && blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) <= 152 ||
                    blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) >= 157 && blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) <= 160 || blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) >= 165 && blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) <= 168) {
                    println("(DEBUG2R)")
                    blockTemp = blocks(updatel.get(i))(updatey.get(i))(updatex.get(i))
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i), false)
                    blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) -= 4
                    rdrawn(updatey.get(i))(updatex.get(i)) = false
                }
                else if (blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) >= 137 && blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) <= 140 || blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) >= 145 && blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) <= 148 ||
                    blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) >= 153 && blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) <= 156 || blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) >= 161 && blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) <= 164) {
                    println("(DEBUG2A)")
                    blocks(updatel.get(i))(updatey.get(i))(updatex.get(i)) += 4
                    power(updatel.get(i))(updatey.get(i))(updatex.get(i)) = 5.toFloat
                    addBlockLighting(updatex.get(i), updatey.get(i))
                    addTileToPQueue(updatex.get(i), updatey.get(i))
                    rdrawn(updatey.get(i))(updatex.get(i)) = false
                }
                updatex.remove(i)
                updatey.remove(i)
                updatet.remove(i)
                updatel.remove(i)
            }
        }

        if (!DEBUG_PEACEFUL && mobCount < 100) {
            if (msi == 1) {
                ((player.iy/BLOCKSIZE).toInt-125 until (player.iy/BLOCKSIZE).toInt+125).foreach { ay =>
                    ((player.ix/BLOCKSIZE).toInt-125 until (player.ix/BLOCKSIZE).toInt+125).foreach { ax =>
                        import scala.util.control.Breaks._
                        breakable {
                            if (random.nextInt((100000 / DEBUG_HOSTILE).toInt) == 0) {
                                xpos = ax + random.nextInt(20) - 10
                                ypos = ay + random.nextInt(20) - 10
                                xpos2 = ax + random.nextInt(20) - 10
                                ypos2 = ay + random.nextInt(20) - 10
                                if (xpos > 0 && xpos < WIDTH - 1 && ypos > 0 && ypos < HEIGHT - 1 && (blocks(1)(ypos)(xpos) == 0 || !blockcds(blocks(1)(ypos)(xpos)) &&
                                  xpos2 > 0 && xpos2 < WIDTH - 1 && ypos2 > 0 && ypos2 < HEIGHT - 1 && blocks(1)(ypos2)(xpos2) != 0 && blockcds(blocks(1)(ypos2)(xpos2)))) {
                                    mobSpawn = null
                                    if (!checkBiome(xpos, ypos).equals("underground")) {
                                        if ((day != 0 || DEBUG_HOSTILE > 1) && (timeOfDay >= 75913 || timeOfDay < 28883)) {
                                            if (random.nextInt(350) == 0) {
                                                rnum = random.nextInt(100)
                                                if (rnum >= 0 && rnum < 45) {
                                                    mobSpawn = "blue_bubble" // 45%
                                                }
                                                if (rnum >= 45 && rnum < 85) {
                                                    mobSpawn = "green_bubble" // 40%
                                                }
                                                if (rnum >= 85 && rnum < 100) {
                                                    mobSpawn = "red_bubble" // 15%
                                                }
                                            }
                                        }
                                        if (timeOfDay >= 32302 && timeOfDay < 72093) {
                                            if (random.nextInt(200) == 0) {
                                                rnum = random.nextInt(100)
                                                if (rnum >= 0 && rnum < 80) {
                                                    mobSpawn = "zombie" // 80%
                                                }
                                                if (rnum >= 80 && rnum < 90) {
                                                    mobSpawn = "armored_zombie" // 10%
                                                }
                                                if (rnum >= 90 && rnum < 100) {
                                                    mobSpawn = "shooting_star" // 10%
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        if (random.nextInt(100) == 0) {
                                            rnum = random.nextInt(100)
                                            if (rnum >= 0 && rnum < 25) {
                                                mobSpawn = "yellow_bubble" // 25%
                                            }
                                            if (rnum >= 25 && rnum < 45) {
                                                mobSpawn = "zombie" // 20%
                                            }
                                            if (rnum >= 45 && rnum < 60) {
                                                mobSpawn = "armored_zombie" // 15%
                                            }
                                            if (rnum >= 60 && rnum < 70) {
                                                mobSpawn = "black_bubble" // 10%
                                            }
                                            if (rnum >= 70 && rnum < 85) {
                                                mobSpawn = "bat" // 15%
                                            }
                                            if (rnum >= 85 && rnum < 100) {
                                                mobSpawn = "skeleton" // 15%
                                            }
                                        }
                                    }
                                    if (mobSpawn != null && checkBiome(xpos, ypos).equals("desert")) {
                                        if (random.nextInt(3) == 0) { // 33% of all spawns in desert
                                            mobSpawn = "sandbot"
                                        }
                                    }
                                    if (mobSpawn != null && checkBiome(xpos, ypos).equals("frost")) {
                                        if (random.nextInt(3) == 0) { // 33% of all spawns in desert
                                            mobSpawn = "snowman"
                                        }
                                    }
                                    /*                                if (mobSpawn != null && player.y <= HEIGHT*0.08*BLOCKSIZE) {
                                    mobSpawn = "white_bubble" // 100% of all spawns in sky
                                     }
                                     */
                                    if (mobSpawn == null) {
                                        break // was continue, is same
                                    }
                                    else if (DEBUG_MOBTEST != null) mobSpawn = DEBUG_MOBTEST
                                    if (mobSpawn.equals("blue_bubble") || mobSpawn.equals("green_bubble") || mobSpawn.equals("red_bubble") || mobSpawn.equals("yellow_bubble") || mobSpawn.equals("black_bubble") || mobSpawn.equals("white_bubble")) {
                                        xmax = 2
                                        ymax = 2
                                    }
                                    if (mobSpawn.equals("zombie")) {
                                        xmax = 2
                                        ymax = 3
                                    }
                                    if (mobSpawn.equals("armored_zombie")) {
                                        xmax = 2
                                        ymax = 3
                                    }
                                    if (mobSpawn.equals("shooting_star")) {
                                        xmax = 2
                                        ymax = 2
                                    }
                                    if (mobSpawn.equals("sandbot")) {
                                        xmax = 2
                                        ymax = 2
                                    }
                                    if (mobSpawn.equals("snowman")) {
                                        xmax = 2
                                        ymax = 3
                                    }
                                    if (mobSpawn.equals("bat")) {
                                        xmax = 1
                                        ymax = 1
                                    }
                                    if (mobSpawn.equals("bee")) {
                                        xmax = 1
                                        ymax = 1
                                    }
                                    if (mobSpawn.equals("skeleton")) {
                                        xmax = 1
                                        ymax = 3
                                    }
                                    doMobSpawn = true
                                    ((xpos / BLOCKSIZE) until (xpos / BLOCKSIZE + xmax)).foreach { x =>
                                        ((ypos / BLOCKSIZE) until (ypos / BLOCKSIZE + ymax)).foreach { y =>
                                            if (y > 0 && y < HEIGHT - 1 && blocks(1)(y)(x) != 0 && blockcds(blocks(1)(y)(x))) {
                                                doMobSpawn = false
                                            }
                                        }
                                    }
                                    if (doMobSpawn) {
                                        entities.add(new Entity(xpos * BLOCKSIZE, ypos * BLOCKSIZE, 0, 0, mobSpawn))
                                    }
                                }
                            }
                        }
                    }
                }
                msi = 0
            }
            else {
                msi = 1
            }
        }

        mobCount = 0

        (entities.size()-1 until(-1, -1)).foreach { i =>
            if (entities.get(i).name != null) {
                mobCount += 1
                if (entities.get(i).ix < player.ix - 2000 || entities.get(i).ix > player.ix + 2000 ||
                    entities.get(i).iy < player.iy - 2000 || entities.get(i).iy > player.iy + 2000) {
                    if (random.nextInt(500) == 0) {
                        entities.remove(i)
                    }
                }
            }
        }

        if (queue(3)) {
            checkBlocks = true
            if (showInv) {
                if (mousePos(0) >= getWidth()-save_exit.getWidth()-24 && mousePos(0) <= getWidth()-24 &&
                    mousePos(1) >= getHeight()-save_exit.getHeight()-24 && mousePos(1) <= getHeight()-24) {
                    if (mouseClicked) {
                        mouseNoLongerClicked = true
                        saveWorld()
                        state = "title_screen"
                        timer.stop()
                        menuTimer.start()
                        return
                    }
                }
                (0 until 10).foreach { ux =>
                    (0 until 4).foreach { uy =>
                        if (mousePos(0) >= ux*46+6 && mousePos(0) < ux*46+46 &&
                            mousePos(1) >= uy*46+6 && mousePos(1) < uy*46+46) {
                            checkBlocks = false
                            if (mouseClicked) {
                                mouseNoLongerClicked = true
                                if (uy != 0 || inventory.selection != ux || !showTool) {
                                    moveItemTemp = inventory.ids(uy*10+ux)
                                    moveNumTemp = inventory.nums(uy*10+ux)
                                    moveDurTemp = inventory.durs(uy*10+ux)
                                    if (moveItem == inventory.ids(uy*10+ux)) {
                                        moveNum = inventory.addLocation(uy*10+ux, moveItem, moveNum, moveDur).toShort
                                        if (moveNum == 0) {
                                            moveItem = 0
                                            moveDur = 0
                                        }
                                    }
                                    else {
                                        inventory.removeLocation(uy*10+ux, inventory.nums(uy*10+ux))
                                        if (moveItem != 0) {
                                            inventory.addLocation(uy*10+ux, moveItem, moveNum, moveDur)
                                        }
                                        moveItem = moveItemTemp
                                        moveNum = moveNumTemp
                                        moveDur = moveDurTemp
                                    }
                                }
                            }
                        }
                    }
                }
                (0 until 2).foreach { ux =>
                    (0 until 2).foreach { uy =>
                        if (mousePos(0) >= inventory.image.getWidth()+ux*40+75 &&
                            mousePos(0) < inventory.image.getWidth()+ux*40+115 &&
                            mousePos(1) >= uy*40+52 && mousePos(1) < uy*40+92) {
                            checkBlocks = false
                            if (mouseClicked) {
                                mouseNoLongerClicked = true
                                moveItemTemp = cic.ids(uy*2+ux)
                                moveNumTemp = cic.nums(uy*2+ux)
                                moveDurTemp = cic.durs(uy*2+ux)
                                if (moveItem == cic.ids(uy*2+ux)) {
                                    moveNum = inventory.addLocationIC(cic, uy*2+ux, moveItem, moveNum, moveDur).toShort
                                    if (moveNum == 0) {
                                        moveItem = 0
                                        moveDur = 0
                                    }
                                }
                                else {
                                    inventory.removeLocationIC(cic, uy*2+ux, cic.nums(uy*2+ux))
                                    if (moveItem != 0) {
                                        inventory.addLocationIC(cic, uy*2+ux, moveItem, moveNum, moveDur)
                                    }
                                    moveItem = moveItemTemp
                                    moveNum = moveNumTemp
                                    moveDur = moveDurTemp
                                }
                            }
                        }
                    }
                }
                if (mousePos(0) >= inventory.image.getWidth()+3*40+81 &&
                    mousePos(0) < inventory.image.getWidth()+3*40+121 &&
                    mousePos(1) >= 20+52 && mousePos(1) < 20+92) {
                    checkBlocks = false
                    if (mouseClicked) {
                        if (moveItem == cic.ids(4)) {
                            MAXSTACKS.get(cic.ids(4)).foreach { maxstacks =>
                                if(moveNum + cic.nums(4) <= maxstacks) {
                                    moveNum = (moveNum + cic.nums(4)).toShort
                                    inventory.useRecipeCIC(cic)
                                }
                            }
                        }
                        if (moveItem == 0) {
                            moveItem = cic.ids(4)
                            moveNum = cic.nums(4)
                            TOOLDURS.get(moveItem).foreach { t =>
                                moveDur = t
                            }
                            inventory.useRecipeCIC(cic)
                        }
                    }
                }
                if (ic != null) {
                    if (ic.`type`.equals("workbench")) {
                        (0 until 3).foreach { ux =>
                            (0 until 3).foreach { uy =>
                                if (mousePos(0) >= ux*40+6 && mousePos(0) < ux*40+46 &&
                                    mousePos(1) >= uy*40+inventory.image.getHeight()+46 &&
                                    mousePos(1) < uy*40+inventory.image.getHeight()+86) {
                                    checkBlocks = false
                                    if (mouseClicked) {
                                        mouseNoLongerClicked = true
                                        moveItemTemp = ic.ids(uy*3+ux)
                                        moveNumTemp = ic.nums(uy*3+ux)
                                        if (moveItem == ic.ids(uy*3+ux)) {
                                            moveNum = inventory.addLocationIC(ic, uy*3+ux, moveItem, moveNum, moveDur).toShort
                                            if (moveNum == 0) {
                                                moveItem = 0
                                            }
                                        }
                                        else {
                                            inventory.removeLocationIC(ic, uy*3+ux, ic.nums(uy*3+ux))
                                            if (moveItem != 0) {
                                                inventory.addLocationIC(ic, uy*3+ux, moveItem, moveNum, moveDur)
                                            }
                                            moveItem = moveItemTemp
                                            moveNum = moveNumTemp
                                        }
                                    }
                                }
                            }
                        }
                        if (mousePos(0) >= 4*40+6 && mousePos(0) < 4*40+46 &&
                            mousePos(1) >= 1*40+inventory.image.getHeight()+46 &&
                            mousePos(1) < 1*40+inventory.image.getHeight()+86) {
                            checkBlocks = false
                            if (mouseClicked) {
                                MAXSTACKS.get(ic.ids(9)).foreach { maxstacks =>
                                    if (moveItem == ic.ids(9) && moveNum + ic.nums(9) <= maxstacks) {
                                        moveNum = (moveNum + ic.nums(9)).toShort
                                        inventory.useRecipeWorkbench(ic)
                                    }
                                }

                                if (moveItem == 0) {
                                    moveItem = ic.ids(9)
                                    moveNum = ic.nums(9)
                                    TOOLDURS.get(moveItem).foreach { tooldur =>
                                        moveDur = tooldur
                                    }
                                    inventory.useRecipeWorkbench(ic)
                                }
                            }
                        }
                    }
                    if (ic.`type`.equals("wooden_chest") || ic.`type`.equals("stone_chest") ||
                        ic.`type`.equals("copper_chest") || ic.`type`.equals("iron_chest") ||
                        ic.`type`.equals("silver_chest") || ic.`type`.equals("gold_chest") ||
                        ic.`type`.equals("zinc_chest") || ic.`type`.equals("rhymestone_chest") ||
                        ic.`type`.equals("obdurite_chest")) {
                        (0 until inventory.CX).foreach { ux =>
                            (0 until inventory.CY).foreach { uy =>
                                if (mousePos(0) >= ux*46+6 && mousePos(0) < ux*46+46 &&
                                    mousePos(1) >= uy*46+inventory.image.getHeight()+46 &&
                                    mousePos(1) < uy*46+inventory.image.getHeight()+86) {
                                    checkBlocks = false
                                    if (mouseClicked) {
                                        mouseNoLongerClicked = true
                                        moveItemTemp = ic.ids(uy*inventory.CX+ux)
                                        moveNumTemp = ic.nums(uy*inventory.CX+ux)
                                        if (moveItem == ic.ids(uy*inventory.CX+ux)) {
                                            moveNum = inventory.addLocationIC(ic, uy*inventory.CX+ux, moveItem, moveNum, moveDur).toShort
                                            if (moveNum == 0) {
                                                moveItem = 0
                                            }
                                        }
                                        else {
                                            inventory.removeLocationIC(ic, uy*inventory.CX+ux, ic.nums(uy*inventory.CX+ux))
                                            if (moveItem != 0) {
                                                inventory.addLocationIC(ic, uy*inventory.CX+ux, moveItem, moveNum, moveDur)
                                            }
                                            moveItem = moveItemTemp
                                            moveNum = moveNumTemp
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (ic.`type`.equals("furnace")) {
                        if (mousePos(0) >= 6 && mousePos(0) < 46 &&
                            mousePos(1) >= inventory.image.getHeight()+46 &&
                            mousePos(1) < inventory.image.getHeight()+86) {
                            checkBlocks = false
                            if (mouseClicked) {
                                mouseNoLongerClicked = true
                                moveItemTemp = ic.ids(0)
                                moveNumTemp = ic.nums(0)
                                if (moveItem == ic.ids(0)) {
                                    moveNum = inventory.addLocationIC(ic, 0, moveItem, moveNum, moveDur).toShort
                                    if (moveNum == 0) {
                                        moveItem = 0
                                    }
                                }
                                else {
                                    inventory.removeLocationIC(ic, 0, ic.nums(0))
                                    if (moveItem != 0) {
                                        inventory.addLocationIC(ic, 0, moveItem, moveNum, moveDur)
                                    }
                                    moveItem = moveItemTemp
                                    moveNum = moveNumTemp
                                    ic.SMELTP = 0
                                }
                            }
                        }
                        if (mousePos(0) >= 6 && mousePos(0) < 46 &&
                            mousePos(1) >= inventory.image.getHeight()+142 &&
                            mousePos(1) < inventory.image.getHeight()+182) {
                            checkBlocks = false
                            if (mouseClicked) {
                                mouseNoLongerClicked = true
                                moveItemTemp = ic.ids(2)
                                moveNumTemp = ic.nums(2)
                                if (moveItem == ic.ids(2)) {
                                    moveNum = inventory.addLocationIC(ic, 2, moveItem, moveNum, moveDur).toShort
                                    if (moveNum == 0) {
                                        moveItem = 0
                                    }
                                }
                                else {
                                    inventory.removeLocationIC(ic, 2, ic.nums(2))
                                    if (moveItem != 0) {
                                        inventory.addLocationIC(ic, 2, moveItem, moveNum, moveDur)
                                    }
                                    moveItem = moveItemTemp
                                    moveNum = moveNumTemp
                                }
                            }
                        }
                        if (mousePos(0) >= 62 && mousePos(0) < 102 &&
                            mousePos(1) >= inventory.image.getHeight()+46 &&
                            mousePos(1) < inventory.image.getHeight()+86) {
                            checkBlocks = false
                            if (mouseClicked) {
                                mouseNoLongerClicked = true
                                if (moveItem == 0) {
                                    moveItem = ic.ids(3)
                                    moveNum = ic.nums(3)
                                    inventory.removeLocationIC(ic, 3, ic.nums(3))
                                }
                                else if (moveItem == ic.ids(3)) {
                                    moveNum = (moveNum + ic.nums(3)).toShort
                                    inventory.removeLocationIC(ic, 3, ic.nums(3))
                                    MAXSTACKS.get(moveItem).foreach { maxstacks =>
                                        if (moveNum > maxstacks) {
                                            inventory.addLocationIC(ic, 3, moveItem, (moveNum - maxstacks).toShort, moveDur)
                                            moveNum = maxstacks
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                (0 until 4).foreach { uy =>
                    if (mousePos(0) >= inventory.image.getWidth() + 6 && mousePos(0) < inventory.image.getWidth() + 6 + armor.image.getWidth() &&
                        mousePos(1) >= 6 + uy*46 && mousePos(1) < 6 + uy*46+40) {
                        checkBlocks = false
                        if (mouseClicked) {
                            mouseNoLongerClicked = true
                            i = uy
                            if (uy == 0 && (moveItem == 105.toShort || moveItem == 109.toShort || moveItem == 113.toShort || moveItem == 117.toShort ||
                                            moveItem == 121.toShort || moveItem == 125.toShort || moveItem == 129.toShort || moveItem == 133.toShort ||
                                            moveItem == 137.toShort || moveItem == 141.toShort) ||
                                uy == 1 && (moveItem == 106.toShort || moveItem == 110.toShort || moveItem == 114.toShort || moveItem == 118.toShort ||
                                            moveItem == 122.toShort || moveItem == 126.toShort || moveItem == 130.toShort || moveItem == 134.toShort ||
                                            moveItem == 138.toShort || moveItem == 142.toShort) ||
                                uy == 2 && (moveItem == 107.toShort || moveItem == 111.toShort || moveItem == 115.toShort || moveItem == 119.toShort ||
                                            moveItem == 123.toShort || moveItem == 127.toShort || moveItem == 131.toShort || moveItem == 135.toShort ||
                                            moveItem == 139.toShort || moveItem == 143.toShort) ||
                                uy == 3 && (moveItem == 108.toShort || moveItem == 112.toShort || moveItem == 116.toShort || moveItem == 120.toShort ||
                                            moveItem == 124.toShort || moveItem == 128.toShort || moveItem == 132.toShort || moveItem == 136.toShort ||
                                            moveItem == 140.toShort || moveItem == 144.toShort)) {
                                if (armor.ids(i) == 0) {
                                    inventory.addLocationIC(armor, i, moveItem, moveNum, moveDur)
                                    moveItem = 0
                                    moveNum = 0
                                }
                                else {
                                    moveItemTemp = armor.ids(i)
                                    moveNumTemp = armor.nums(i)
                                    inventory.removeLocationIC(armor, i, moveNumTemp)
                                    inventory.addLocationIC(armor, i, moveItem, moveNum, moveDur)
                                    moveItem = moveItemTemp
                                    moveNum = moveNumTemp
                                }
                            }
                            else if (moveItem == 0) {
                                moveItem = armor.ids(i)
                                moveNum = armor.nums(i)
                                inventory.removeLocationIC(armor, i, moveNum)
                            }
                        }
                    }
                }
            }
            else {
                (0 until 10).foreach { ux =>
                    uy = 0
                    if (mousePos(0) >= ux*46+6 && mousePos(0) < ux*46+46 &&
                        mousePos(1) >= uy*46+6 && mousePos(1) < uy*46+46) {
                        checkBlocks = false
                        if (mouseClicked) {
                            mouseNoLongerClicked = true
                            inventory.select2(ux)
                        }
                    }
                }
            }
            if (mouseNoLongerClicked) {
                mouseClicked = false
            }
            if (checkBlocks) {
                if (inventory.tool() != 0 && !showTool) {
                    itemImgs.get(inventory.tool()).foreach { t =>
                        tool = t
                    }
                    (0 until entities.size()).foreach { i =>
                        entities.get(i).immune = false
                    }
                    TOOLSPEED.get(inventory.tool()).foreach { ts =>
                        toolSpeed = ts
                    }
                    if (inventory.tool() == 169 || inventory.tool() == 170 || inventory.tool() == 171) {
                        TOOLDURS.get(inventory.ids(inventory.selection)).foreach { tooldurs =>
                            toolSpeed *= (inventory.durs(inventory.selection).toDouble/tooldurs) * (-0.714) + 1
                        }
                    }
                    showTool = true
                    toolAngle = 4.7
                    ux = (mousePos2(0)/BLOCKSIZE)
                    uy = (mousePos2(1)/BLOCKSIZE)
                    ux2 = (mousePos2(0)/BLOCKSIZE)
                    uy2 = (mousePos2(1)/BLOCKSIZE)
                    if (Math.sqrt(Math.pow(player.x+player.image.getWidth()-ux2*BLOCKSIZE+BLOCKSIZE/2, 2) + Math.pow(player.y+player.image.getHeight()-uy2*BLOCKSIZE+BLOCKSIZE/2, 2)) <= 160 ||
                        Math.sqrt(Math.pow(player.x+player.image.getWidth()-ux2*BLOCKSIZE+BLOCKSIZE/2+WIDTH*BLOCKSIZE, 2) + Math.pow(player.y+player.image.getHeight()-uy2*BLOCKSIZE+BLOCKSIZE/2, 2)) <= 160 || DEBUG_REACH) {
                        ucx = ux - CHUNKBLOCKS * ((ux/CHUNKBLOCKS))
                        ucy = uy - CHUNKBLOCKS * ((uy/CHUNKBLOCKS))
                        if (jul.Arrays.asList(toolList).contains(inventory.tool())) {
                            if (blocks(layer)(uy)(ux) != 0 && jul.Arrays.asList(BLOCKTOOLS.get(blocks(layer)(uy)(ux))).contains(inventory.tool())) {
                                blockdns(uy)(ux) = random.nextInt(5).toByte
                                drawn(uy)(ux) = false
                                if (ux == mx && uy == my && inventory.tool() == miningTool) {
                                    mining += 1
                                    inventory.durs(inventory.selection) = (inventory.durs(inventory.selection) - 1).toShort
                                    breakCurrentBlock()
                                    if (inventory.durs(inventory.selection) <= 0) {
                                        inventory.removeLocation(inventory.selection, inventory.nums(inventory.selection))
                                    }
                                }
                                else {
                                    mining = 1
                                    miningTool = inventory.tool()
                                    inventory.durs(inventory.selection) = (inventory.durs(inventory.selection) - 1).toShort
                                    breakCurrentBlock()
                                    mx = ux
                                    my = uy
                                    if (inventory.durs(inventory.selection) <= 0) {
                                        inventory.removeLocation(inventory.selection, inventory.nums(inventory.selection))
                                    }
                                }
                                ug2 = null
                            }
                        }
                        else if (inventory.tool() == 33) {
                            if (blocks(layer)(uy)(ux) == 17 || blocks(layer)(uy)(ux) == 23) {
                                if (icmatrix(layer)(uy)(ux) != null && icmatrix(layer)(uy)(ux).`type`.equals("furnace")) {
                                    inventory.durs(inventory.selection) = (inventory.durs(inventory.selection) - 1).toShort
                                    icmatrix(layer)(uy)(ux).F_ON = true
                                    blocks(layer)(uy)(ux) = 23
                                    addBlockLighting(ux, uy)
                                    if (inventory.durs(inventory.selection) <= 0) {
                                        inventory.removeLocation(inventory.selection, inventory.nums(inventory.selection))
                                    }
                                    rdrawn(uy)(ux) = false
                                }
                                else {
                                    if (ic != null && ic.`type`.equals("furnace")) {
                                        inventory.durs(inventory.selection) = (inventory.durs(inventory.selection) - 1).toShort
                                        ic.F_ON = true
                                        blocks(layer)(icy)(icx) = 23
                                        addBlockLighting(ux, uy)
                                        rdrawn(icy)(icx) = false
                                        if (inventory.durs(inventory.selection) <= 0) {
                                            inventory.removeLocation(inventory.selection, inventory.nums(inventory.selection))
                                        }
                                    }
                                }
                            }
                        }
                        else if (inventory.tool() == 190) {
                            if (blocks(layer)(uy)(ux) >= 137 && blocks(layer)(uy)(ux) <= 160) {
                                inventory.durs(inventory.selection) = (inventory.durs(inventory.selection) - 1).toShort
                                blocks(layer)(uy)(ux) += 8
                                rdrawn(uy)(ux) = false
                                if (inventory.durs(inventory.selection) <= 0) {
                                    inventory.removeLocation(inventory.selection, inventory.nums(inventory.selection))
                                }
                            }
                            else if (blocks(layer)(uy)(ux) >= 161 && blocks(layer)(uy)(ux) <= 168) {
                                inventory.durs(inventory.selection) = (inventory.durs(inventory.selection) - 1).toShort
                                blocks(layer)(uy)(ux) -= 24
                                rdrawn(uy)(ux) = false
                                if (inventory.durs(inventory.selection) <= 0) {
                                    inventory.removeLocation(inventory.selection, inventory.nums(inventory.selection))
                                }
                            }
                        }
                        else if (ITEMBLOCKS.get(inventory.tool()).exists(_ != 0)) {
                            ITEMBLOCKS.get(inventory.tool()).foreach { t =>
                                blockTemp = t
                            }
                            if (uy >= 1 && (blocks(layer)(uy)(ux) == 0) &&
                                    (layer == 0 && (
                                    blocks(layer)(uy)(ux-1) != 0 || blocks(layer)(uy)(ux+1) != 0 ||
                                    blocks(layer)(uy-1)(ux) != 0 || blocks(layer)(uy+1)(ux) != 0 ||
                                    blocks(layer+1)(uy)(ux) != 0) ||
                                    layer == 1 && (
                                    blocks(layer)(uy)(ux-1) != 0 || blocks(layer)(uy)(ux+1) != 0 ||
                                    blocks(layer)(uy-1)(ux) != 0 || blocks(layer)(uy+1)(ux) != 0 ||
                                    blocks(layer-1)(uy)(ux) != 0 || blocks(layer+1)(uy)(ux) != 0) ||
                                    layer == 2 && (
                                    blocks(layer)(uy)(ux-1) != 0 || blocks(layer)(uy)(ux+1) != 0 ||
                                    blocks(layer)(uy-1)(ux) != 0 || blocks(layer)(uy+1)(ux) != 0 ||
                                    blocks(layer-1)(uy)(ux) != 0)) &&
                                    !(blockTemp == 48 && (blocks(layer)(uy+1)(ux) != 1 && blocks(layer)(uy+1)(ux) != 72 && blocks(layer)(uy+1)(ux) != 73) || // sunflower
                                                      blockTemp == 51 && (blocks(layer)(uy+1)(ux) != 1 && blocks(layer)(uy+1)(ux) != 72 && blocks(layer)(uy+1)(ux) != 73) || // moonflower
                                                      blockTemp == 54 && (blocks(layer)(uy+1)(ux) != 45) || // dryweed
                                                      blockTemp == 57 && (blocks(layer)(uy+1)(ux) != 73) || // greenleaf
                                                      blockTemp == 60 && (blocks(layer)(uy+1)(ux) != 46) || // frostleaf
                                                      blockTemp == 63 && (blocks(layer)(uy+1)(ux) != 2) || // caveroot
                                                      blockTemp == 66 && (blocks(layer)(uy+1)(ux) != 1 && blocks(layer)(uy+1)(ux) != 72 && blocks(layer)(uy+1)(ux) != 73) || // skyblossom
                                                      blockTemp == 69 && (blocks(layer)(uy+1)(ux) != 2))) { // void_rot
                                if (TORCHESL.get(blockTemp).isEmpty || uy < HEIGHT - 1 && (solid(blocks(layer)(uy+1)(ux)) && blockTemp != 127 || solid(blocks(layer)(uy)(ux+1)) || solid(blocks(layer)(uy)(ux-1)))) {
                                    if (TORCHESL.get(blockTemp).isDefined) {
                                        if (solid(blocks(layer)(uy+1)(ux)) && blockTemp != 127) {
                                            blockTemp = blockTemp
                                        }
                                        else if (solid(blocks(layer)(uy)(ux-1))) {
                                            TORCHESL.get(blockTemp).foreach { t =>
                                                blockTemp = t
                                            }
                                        }
                                        else if (solid(blocks(layer)(uy)(ux+1))) {
                                            TORCHESR.get(blockTemp).foreach { t =>
                                                blockTemp = t
                                            }
                                        }
                                    }
                                    if (layer == 1 && !DEBUG_GPLACE && blockcds(blockTemp)) {
                                        (0 until entities.size()).foreach { i =>
                                            if (entities.get(i).name != null && entities.get(i).rect.intersects(new Rectangle(ux*BLOCKSIZE, uy*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                                                blockTemp = 0
                                            }
                                        }
                                        if (player.rect.intersects(new Rectangle(ux*BLOCKSIZE, uy*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                                            blockTemp = 0
                                        }
                                    }
                                    if (blockTemp != 0) {
                                        blocks(layer)(uy)(ux) = blockTemp
                                        if (receives(blocks(layer)(uy)(ux))) {
                                            addAdjacentTilesToPQueue(ux, uy)
                                        }
                                        if (powers(blocks(layer)(uy)(ux))) {
                                            addBlockPower(ux, uy)
                                        }
                                        if (ltrans(blocks(layer)(uy)(ux))) {
                                            removeSunLighting(ux, uy)
                                            redoBlockLighting(ux, uy)
                                        }
                                        addBlockLighting(ux, uy)
                                    }
                                    if (blockTemp != 0) {
                                        inventory.removeLocation(inventory.selection, 1.toShort)
                                        blockds(layer) = World.generate2b(blocks(layer), blockds(layer), ux, uy)
                                        (uy-1 until uy+2).foreach { uly =>
                                            (ux-1 until ux+2).foreach { ulx =>
                                                blockdns(uly)(ulx) = random.nextInt(5).toByte
                                            }
                                        }
                                        (uy-1 until uy+2).foreach { uly =>
                                            (ux-1 until ux+2).foreach { ulx =>
                                                drawn(uly)(ulx) = false
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else {
            mouseClicked = true
        }
        if (queue(4)) {
            checkBlocks = true
            if (showInv) {
                (0 until 10).foreach { ux =>
                    (0 until 4).foreach { uy =>
                        if (mousePos(0) >= ux*46+6 && mousePos(0) < ux*46+46 &&
                            mousePos(1) >= uy*46+6 && mousePos(1) < uy*46+46) {
                            checkBlocks = false
                            if (mouseClicked2) {
                                mouseNoLongerClicked2 = true
                                moveItemTemp = inventory.ids(uy*10+ux)
                                moveNumTemp = (inventory.nums(uy*10+ux)/2).toShort
                                moveDurTemp = inventory.durs(uy*10+ux)
                                if (inventory.ids(uy*10+ux) == 0) {
                                    inventory.addLocation(uy*10+ux, moveItem, 1.toShort, moveDur)
                                    moveNum = (moveNum - 1).toShort
                                    if (moveNum == 0) {
                                        moveItem = 0
                                        moveDur = 0
                                    }
                                }
                                else if (moveItem == 0 && inventory.nums(uy*10+ux) != 1) {
                                    inventory.removeLocation(uy*10+ux, (inventory.nums(uy*10+ux)/2).toShort)
                                    moveItem = moveItemTemp
                                    moveNum = moveNumTemp
                                    moveDur = moveDurTemp
                                }
                                else if (moveItem == inventory.ids(uy*10+ux)) {
                                    MAXSTACKS.get(inventory.ids(uy*10+ux)).foreach { maxstacks =>
                                      if(inventory.nums(uy*10+ux) < maxstacks) {
                                          inventory.addLocation(uy*10+ux, moveItem, 1.toShort, moveDur)
                                          moveNum = (moveNum - 1).toShort
                                          if (moveNum == 0) {
                                              moveItem = 0
                                              moveDur = 0
                                          }
                                      }
                                    }
                                }
                            }
                        }
                    }
                }
                (0 until 2).foreach { ux =>
                    (0 until 2).foreach { uy =>
                        if (mousePos(0) >= inventory.image.getWidth()+ux*40+75 &&
                            mousePos(0) < inventory.image.getWidth()+ux*40+121 &&
                            mousePos(1) >= uy*40+52 && mousePos(1) < uy*40+92) {
                            checkBlocks = false
                            if (mouseClicked2) {
                                mouseNoLongerClicked2 = true
                                moveItemTemp = cic.ids(uy*2+ux)
                                moveNumTemp = (cic.nums(uy*2+ux)/2).toShort
                                if (cic.ids(uy*2+ux) == 0) {
                                    inventory.addLocationIC(cic, uy*2+ux, moveItem, 1.toShort, moveDur)
                                    moveNum = (moveNum - 1).toShort
                                    if (moveNum == 0) {
                                        moveItem = 0
                                    }
                                }
                                else if (moveItem == 0 && cic.nums(uy*2+ux) != 1) {
                                    inventory.removeLocationIC(cic, uy*2+ux, (cic.nums(uy*2+ux)/2).toShort)
                                    moveItem = moveItemTemp
                                    moveNum = moveNumTemp
                                }
                                else if (moveItem == cic.ids(uy*2+ux)) {
                                    MAXSTACKS.get(cic.ids(uy*2+ux)).foreach { maxstacks =>
                                        if(cic.nums(uy*2+ux) < maxstacks) {
                                            inventory.addLocationIC(cic, uy*2+ux, moveItem, 1.toShort, moveDur)
                                            moveNum = (moveNum - 1).toShort
                                            if (moveNum == 0) {
                                                moveItem = 0
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (ic != null) {
                    if (ic.`type`.equals("workbench")) {
                        (0 until 3).foreach { ux =>
                            (0 until 3).foreach { uy =>
                                if (mousePos(0) >= ux*40+6 && mousePos(0) < ux*40+46 &&
                                    mousePos(1) >= uy*40+inventory.image.getHeight()+46 &&
                                    mousePos(1) < uy*40+inventory.image.getHeight()+86) {
                                    checkBlocks = false
                                    if (mouseClicked2) {
                                        mouseNoLongerClicked2 = true
                                        moveItemTemp = ic.ids(uy*3+ux)
                                        moveNumTemp = (ic.nums(uy*3+ux)/2).toShort
                                        if (ic.ids(uy*3+ux) == 0) {
                                            inventory.addLocationIC(ic, uy*3+ux, moveItem, 1.toShort, moveDur)
                                            moveNum = (moveNum - 1).toShort
                                            if (moveNum == 0) {
                                                moveItem = 0
                                            }
                                        }
                                        else if (moveItem == 0 && ic.nums(uy*3+ux) != 1) {
                                            inventory.removeLocationIC(ic, uy*3+ux, (ic.nums(uy*3+ux)/2).toShort)
                                            moveItem = moveItemTemp
                                            moveNum = moveNumTemp
                                        }
                                        else if (moveItem == ic.ids(uy*3+ux)) {
                                            MAXSTACKS.get(ic.ids(uy*3+ux)).foreach { maxstacks =>
                                              if(ic.nums(uy*3+ux) < maxstacks) {
                                                  if (ic.ids(7) == 160 && ic.nums(7) == 51 && moveItem == 165 && uy*3+ux == 3 && ic.nums(8) == 0) {
                                                      inventory.addLocationIC(ic, 8, 154.toShort, 1.toShort)
                                                  }
                                                  else {
                                                      inventory.addLocationIC(ic, uy*3+ux, moveItem, 1.toShort, moveDur)
                                                      moveNum = (moveNum - 1).toShort
                                                      if (moveNum == 0) {
                                                          moveItem = 0
                                                      }
                                                  }
                                              }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (mousePos(0) >= 4*40+6 && mousePos(0) < 4*40+46 &&
                            mousePos(1) >= 1*40+inventory.image.getHeight()+46 &&
                            mousePos(1) < 1*40+inventory.image.getHeight()+86) {
                            checkBlocks = false
                            if (mouseClicked2) {
                                //
                            }
                        }
                    }
                    if (ic.`type`.equals("wooden_chest") || ic.`type`.equals("stone_chest") ||
                        ic.`type`.equals("copper_chest") || ic.`type`.equals("iron_chest") ||
                        ic.`type`.equals("silver_chest") || ic.`type`.equals("gold_chest") ||
                        ic.`type`.equals("zinc_chest") || ic.`type`.equals("rhymestone_chest") ||
                        ic.`type`.equals("obdurite_chest")) {
                        (0 until inventory.CX).foreach { ux =>
                            (0 until inventory.CY).foreach { uy =>
                                if (mousePos(0) >= ux*46+6 && mousePos(0) < ux*46+46 &&
                                    mousePos(1) >= uy*46+inventory.image.getHeight()+46 &&
                                    mousePos(1) < uy*46+inventory.image.getHeight()+86) {
                                    checkBlocks = false
                                    if (mouseClicked2) {
                                        mouseNoLongerClicked2 = true
                                        moveItemTemp = ic.ids(uy*inventory.CX+ux)
                                        moveNumTemp = (ic.nums(uy*inventory.CX+ux)/2).toShort
                                        if (ic.ids(uy*inventory.CX+ux) == 0) {
                                            inventory.addLocationIC(ic, uy*inventory.CX+ux, moveItem, 1.toShort, moveDur)
                                            moveNum = (moveNum - 1).toShort
                                            if (moveNum == 0) {
                                                moveItem = 0
                                            }
                                        }
                                        else if (moveItem == 0 && ic.nums(uy*inventory.CX+ux) != 1) {
                                            inventory.removeLocationIC(ic, uy*inventory.CX+ux, (ic.nums(uy*inventory.CX+ux)/2).toShort)
                                            moveItem = moveItemTemp
                                            moveNum = moveNumTemp
                                        }
                                        else if (moveItem == ic.ids(uy*inventory.CX+ux)) {
                                            MAXSTACKS.get(ic.ids(uy*inventory.CX+ux)).foreach { maxstacks =>
                                              if(ic.nums(uy*inventory.CX+ux) < maxstacks) {
                                                  inventory.addLocationIC(ic, uy*inventory.CX+ux, moveItem, 1.toShort, moveDur)
                                                  moveNum = (moveNum - 1).toShort
                                                  if (moveNum == 0) {
                                                      moveItem = 0
                                                  }
                                              }
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (ic.`type`.equals("furnace")) {
                        if (mousePos(0) >= 6 && mousePos(0) < 46 &&
                            mousePos(1) >= inventory.image.getHeight()+46 &&
                            mousePos(1) < inventory.image.getHeight()+86) {
                            checkBlocks = false
                            if (mouseClicked2) {
                                mouseNoLongerClicked2 = true
                                moveItemTemp = ic.ids(0)
                                moveNumTemp = (ic.nums(0)/2).toShort
                                if (ic.ids(0) == 0) {
                                    inventory.addLocationIC(ic, 0, moveItem, 1.toShort, moveDur)
                                    moveNum = (moveNum - 1).toShort
                                    if (moveNum == 0) {
                                        moveItem = 0
                                    }
                                }
                                else if (moveItem == 0 && ic.nums(0) != 1) {
                                    inventory.removeLocationIC(ic, 0, (ic.nums(0)/2).toShort)
                                    moveItem = moveItemTemp
                                    moveNum = moveNumTemp
                                }
                                else if (moveItem == ic.ids(0)) {
                                    MAXSTACKS.get(ic.ids(0)).foreach { maxstacks =>
                                        if(ic.nums(0) < maxstacks) {
                                            inventory.addLocationIC(ic, 0, moveItem, 1.toShort, moveDur)
                                            moveNum = (moveNum - 1).toShort
                                            if (moveNum == 0) {
                                                moveItem = 0
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (mousePos(0) >= 6 && mousePos(0) < 46 &&
                            mousePos(1) >= inventory.image.getHeight()+142 &&
                            mousePos(1) < inventory.image.getHeight()+182) {
                            checkBlocks = false
                            if (mouseClicked2) {
                                mouseNoLongerClicked2 = true
                                moveItemTemp = ic.ids(2)
                                moveNumTemp = (ic.nums(2)/2).toShort
                                if (ic.ids(2) == 0) {
                                    inventory.addLocationIC(ic, 2, moveItem, 1.toShort, moveDur)
                                    moveNum = (moveNum - 1).toShort
                                    if (moveNum == 0) {
                                        moveItem = 0
                                    }
                                }
                                else if (moveItem == 0 && ic.nums(2) != 1) {
                                    inventory.removeLocationIC(ic, 2, (ic.nums(2)/2).toShort)
                                    moveItem = moveItemTemp
                                    moveNum = moveNumTemp
                                }
                                else if (moveItem == ic.ids(2)) {
                                    MAXSTACKS.get(ic.ids(2)).foreach { maxstacks =>
                                        if(ic.nums(2) < maxstacks) {
                                            inventory.addLocationIC(ic, 2, moveItem, 1.toShort, moveDur)
                                            moveNum = (moveNum - 1).toShort
                                            if (moveNum == 0) {
                                                moveItem = 0
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (mousePos(0) >= 62 && mousePos(0) < 102 &&
                            mousePos(1) >= inventory.image.getHeight()+46 &&
                            mousePos(1) < inventory.image.getHeight()+86) {
                            checkBlocks = false
                            if (mouseClicked2) {
                                mouseNoLongerClicked2 = true
                                moveItemTemp = ic.ids(3)
                                moveNumTemp = (ic.nums(3)/2).toShort
                                if (moveItem == 0 && ic.nums(3) != 1) {
                                    inventory.removeLocationIC(ic, 3, (ic.nums(3)/2).toShort)
                                    moveItem = moveItemTemp
                                    moveNum = moveNumTemp
                                }
                            }
                        }
                    }
                }
            }
            if (checkBlocks) {
                if (!(mousePos2(0) < 0 || mousePos2(0) >= WIDTH*BLOCKSIZE ||
                      mousePos2(1) < 0 || mousePos2(1) >= HEIGHT*BLOCKSIZE)) {
                    ux = (mousePos2(0)/BLOCKSIZE)
                    uy = (mousePos2(1)/BLOCKSIZE)
                    if (DEBUG_REACH || Math.sqrt(Math.pow(player.x+player.image.getWidth()-ux*BLOCKSIZE+BLOCKSIZE/2, 2) + Math.pow(player.y+player.image.getHeight()-uy*BLOCKSIZE+BLOCKSIZE/2, 2)) <= 160) {
                        ucx = ux - CHUNKBLOCKS * ((ux/CHUNKBLOCKS))
                        ucy = uy - CHUNKBLOCKS * ((uy/CHUNKBLOCKS))
                        if (blocks(layer)(uy)(ux) >= 8 && blocks(layer)(uy)(ux) <= 14 || blocks(layer)(uy)(ux) == 17 || blocks(layer)(uy)(ux) == 23 || blocks(layer)(uy)(ux) >= 80 && blocks(layer)(uy)(ux) <= 82) {
                            if (ic != null) {
                                if (!ic.`type`.equals("workbench")) {
                                    machinesx.add(icx)
                                    machinesy.add(icy)
                                    icmatrix(iclayer)(icy)(icx) = new ItemCollection(ic.`type`, ic.ids, ic.nums, ic.durs)
                                }
                                if (ic.`type`.equals("workbench")) {
                                    if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                                        (0 until 9).foreach { i =>
                                            if (ic.ids(i) != 0) {
                                                entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, 2, -2, ic.ids(i), ic.nums(i), ic.durs(i), 75))
                                            }
                                        }
                                    }
                                    if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                                        (0 until 9).foreach { i =>
                                            if (ic.ids(i) != 0) {
                                                entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, -2, -2, ic.ids(i), ic.nums(i), ic.durs(i), 75))
                                            }
                                        }
                                    }
                                }
                                if (ic.`type`.equals("furnace")) {
                                    icmatrix(iclayer)(icy)(icx).FUELP = ic.FUELP
                                    icmatrix(iclayer)(icy)(icx).SMELTP = ic.SMELTP
                                    icmatrix(iclayer)(icy)(icx).F_ON = ic.F_ON
                                }
                                ic = null
                            }
                            iclayer = layer
                            (0 until 3).foreach { l =>
                                if (blocks(l)(uy)(ux) == 8) {
                                    val tlist1: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist2: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist3: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    if (icmatrix(l)(uy)(ux) != null && icmatrix(l)(uy)(ux).`type`.equals("workbench")) {
                                        ic = new ItemCollection("workbench", icmatrix(l)(uy)(ux).ids, icmatrix(l)(uy)(ux).nums, icmatrix(l)(uy)(ux).durs)
                                    }
                                    else {
                                        ic = new ItemCollection("workbench", tlist1, tlist2, tlist3)
                                    }
                                    icx = ux
                                    icy = uy
                                    inventory.renderCollection(ic)
                                    showInv = true
                                }
                                if (blocks(l)(uy)(ux) == 9) {
                                    val tlist1: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist2: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist3: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    if (icmatrix(l)(uy)(ux) != null && icmatrix(l)(uy)(ux).`type`.equals("wooden_chest")) {
                                        ic = new ItemCollection("wooden_chest", icmatrix(l)(uy)(ux).ids, icmatrix(l)(uy)(ux).nums, icmatrix(l)(uy)(ux).durs)
                                    }
                                    else {
                                        ic = new ItemCollection("wooden_chest", tlist1, tlist2, tlist3)
                                    }
                                    icx = ux
                                    icy = uy
                                    inventory.renderCollection(ic)
                                    showInv = true
                                }
                                if (blocks(l)(uy)(ux) == 10) {
                                    val tlist1: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist2: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist3: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    if (icmatrix(l)(uy)(ux) != null && icmatrix(l)(uy)(ux).`type`.equals("stone_chest")) {
                                        ic = new ItemCollection("stone_chest", icmatrix(l)(uy)(ux).ids, icmatrix(l)(uy)(ux).nums, icmatrix(l)(uy)(ux).durs)
                                    }
                                    else {
                                        ic = new ItemCollection("stone_chest", tlist1, tlist2, tlist3)
                                    }
                                    icx = ux
                                    icy = uy
                                    inventory.renderCollection(ic)
                                    showInv = true
                                }
                                if (blocks(l)(uy)(ux) == 11) {
                                    val tlist1: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist2: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist3: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    if (icmatrix(l)(uy)(ux) != null && icmatrix(l)(uy)(ux).`type`.equals("copper_chest")) {
                                        ic = new ItemCollection("copper_chest", icmatrix(l)(uy)(ux).ids, icmatrix(l)(uy)(ux).nums, icmatrix(l)(uy)(ux).durs)
                                    }
                                    else {
                                        ic = new ItemCollection("copper_chest", tlist1, tlist2, tlist3)
                                    }
                                    icx = ux
                                    icy = uy
                                    inventory.renderCollection(ic)
                                    showInv = true
                                }
                                if (blocks(l)(uy)(ux) == 12) {
                                    val tlist1: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist2: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist3: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    if (icmatrix(l)(uy)(ux) != null && icmatrix(l)(uy)(ux).`type`.equals("iron_chest")) {
                                        ic = new ItemCollection("iron_chest", icmatrix(l)(uy)(ux).ids, icmatrix(l)(uy)(ux).nums, icmatrix(l)(uy)(ux).durs)
                                    }
                                    else {
                                        ic = new ItemCollection("iron_chest", tlist1, tlist2, tlist3)
                                    }
                                    icx = ux
                                    icy = uy
                                    inventory.renderCollection(ic)
                                    showInv = true
                                }
                                if (blocks(l)(uy)(ux) == 13) {
                                    val tlist1: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist2: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist3: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    if (icmatrix(l)(uy)(ux) != null && icmatrix(l)(uy)(ux).`type`.equals("silver_chest")) {
                                        ic = new ItemCollection("silver_chest", icmatrix(l)(uy)(ux).ids, icmatrix(l)(uy)(ux).nums, icmatrix(l)(uy)(ux).durs)
                                    }
                                    else {
                                        ic = new ItemCollection("silver_chest", tlist1, tlist2, tlist3)
                                    }
                                    icx = ux
                                    icy = uy
                                    inventory.renderCollection(ic)
                                    showInv = true
                                }
                                if (blocks(l)(uy)(ux) == 14) {
                                    val tlist1: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist2: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist3: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    if (icmatrix(l)(uy)(ux) != null && icmatrix(l)(uy)(ux).`type`.equals("gold_chest")) {
                                        ic = new ItemCollection("gold_chest", icmatrix(l)(uy)(ux).ids, icmatrix(l)(uy)(ux).nums, icmatrix(l)(uy)(ux).durs)
                                    }
                                    else {
                                        ic = new ItemCollection("gold_chest", tlist1, tlist2, tlist3)
                                    }
                                    icx = ux
                                    icy = uy
                                    inventory.renderCollection(ic)
                                    showInv = true
                                }
                                if (blocks(l)(uy)(ux) == 80) {
                                    val tlist1: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist2: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist3: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    if (icmatrix(l)(uy)(ux) != null && icmatrix(l)(uy)(ux).`type`.equals("zinc_chest")) {
                                        ic = new ItemCollection("zinc_chest", icmatrix(l)(uy)(ux).ids, icmatrix(l)(uy)(ux).nums, icmatrix(l)(uy)(ux).durs)
                                    }
                                    else {
                                        ic = new ItemCollection("zinc_chest", tlist1, tlist2, tlist3)
                                    }
                                    icx = ux
                                    icy = uy
                                    inventory.renderCollection(ic)
                                    showInv = true
                                }
                                if (blocks(l)(uy)(ux) == 81) {
                                    val tlist1: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist2: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist3: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    if (icmatrix(l)(uy)(ux) != null && icmatrix(l)(uy)(ux).`type`.equals("rhymestone_chest")) {
                                        ic = new ItemCollection("rhymestone_chest", icmatrix(l)(uy)(ux).ids, icmatrix(l)(uy)(ux).nums, icmatrix(l)(uy)(ux).durs)
                                    }
                                    else {
                                        ic = new ItemCollection("rhymestone_chest", tlist1, tlist2, tlist3)
                                    }
                                    icx = ux
                                    icy = uy
                                    inventory.renderCollection(ic)
                                    showInv = true
                                }
                                if (blocks(l)(uy)(ux) == 82) {
                                    val tlist1: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist2: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    val tlist3: Array[Short] = Array[Short](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                                    if (icmatrix(l)(uy)(ux) != null && icmatrix(l)(uy)(ux).`type`.equals("obdurite_chest")) {
                                        ic = new ItemCollection("obdurite_chest", icmatrix(l)(uy)(ux).ids, icmatrix(l)(uy)(ux).nums, icmatrix(l)(uy)(ux).durs)
                                    }
                                    else {
                                        ic = new ItemCollection("obdurite_chest", tlist1, tlist2, tlist3)
                                    }
                                    icx = ux
                                    icy = uy
                                    inventory.renderCollection(ic)
                                    showInv = true
                                }
                                if (blocks(l)(uy)(ux) == 17 || blocks(l)(uy)(ux) == 23) {
                                    val tlist1: Array[Short] = Array[Short](0, 0, 0, 0)
                                    val tlist2: Array[Short] = Array[Short](0, 0, 0, 0)
                                    val tlist3: Array[Short] = Array[Short](0, 0, 0, 0)
                                    if (icmatrix(l)(uy)(ux) != null && icmatrix(l)(uy)(ux).`type`.equals("furnace")) {
                                        ic = new ItemCollection("furnace", icmatrix(l)(uy)(ux).ids, icmatrix(l)(uy)(ux).nums, icmatrix(l)(uy)(ux).durs)
                                        ic.FUELP = icmatrix(l)(uy)(ux).FUELP
                                        ic.SMELTP = icmatrix(l)(uy)(ux).SMELTP
                                        ic.F_ON = icmatrix(l)(uy)(ux).F_ON
                                        icmatrix(l)(uy)(ux) = null
                                    }
                                    else {
                                        ic = new ItemCollection("furnace", tlist1, tlist2, tlist3)
                                    }
                                    icx = ux
                                    icy = uy
                                    inventory.renderCollection(ic)
                                    showInv = true
                                }
                                if (ic != null && blocks(l)(uy)(ux) != 8) {
                                    (machinesx.size()-1 until(-1, -1)).foreach { i =>
                                        if (machinesx.get(i) == icx && machinesy.get(i) == icy) {
                                            machinesx.remove(i)
                                            machinesy.remove(i)
                                        }
                                    }
                                }
                            }
                        }
                        if (blocks(layer)(uy)(ux) == 15) {
                            if (random.nextInt(2) == 0) {
                                entities.add(new Entity(ux*BLOCKSIZE, uy*BLOCKSIZE, random.nextDouble()*8-4, -3, 160.toShort, 1.toShort))
                            }
                            blocks(layer)(uy)(ux) = 83
                        }
                        if (mouseClicked2) {
                            mouseNoLongerClicked2 = true
                            blockTemp = blocks(layer)(uy)(ux)
                            if (blocks(layer)(uy)(ux) == 105 || blocks(layer)(uy)(ux) == 107 || blocks(layer)(uy)(ux) == 109) {
                                blocks(layer)(uy)(ux) += 1
                                addBlockPower(ux, uy)
                                rdrawn(uy)(ux) = false
                            }
                            else if (blocks(layer)(uy)(ux) == 106 || blocks(layer)(uy)(ux) == 108 || blocks(layer)(uy)(ux) == 110) {
                                removeBlockPower(ux, uy, layer)
                                if (wcnct(uy)(ux)) {
                                    (0 until 3).foreach { l =>
                                        if (l != layer) {
                                            rbpRecur(ux, uy, l)
                                        }
                                    }
                                }
                                blocks(layer)(uy)(ux) -= 1
                                rdrawn(uy)(ux) = false
                            }
                            if (blocks(layer)(uy)(ux) >= 94 && blocks(layer)(uy)(ux) <= 99) {
                                wcnct(uy)(ux) = !wcnct(uy)(ux)
                                rdrawn(uy)(ux) = false
                                redoBlockPower(ux, uy, layer)
                            }
                            if (blocks(layer)(uy)(ux) >= 111 && blocks(layer)(uy)(ux) <= 118) {
                                removeBlockPower(ux, uy, layer)
                                if (blocks(layer)(uy)(ux) >= 111 && blocks(layer)(uy)(ux) <= 113 || blocks(layer)(uy)(ux) >= 115 && blocks(layer)(uy)(ux) <= 117) {
                                    blocks(layer)(uy)(ux) += 1
                                }
                                else {
                                    blocks(layer)(uy)(ux) -= 3
                                }
                                blockds(layer) = World.generate2b(blocks(layer), blockds(layer), ux, uy)
                                rdrawn(uy)(ux) = false
                                addAdjacentTilesToPQueueConditionally(ux, uy)
                            }
                            if (blocks(layer)(uy)(ux) >= 119 && blocks(layer)(uy)(ux) <= 126) {
                                removeBlockPower(ux, uy, layer)
                                if (blocks(layer)(uy)(ux) >= 119 && blocks(layer)(uy)(ux) <= 121 || blocks(layer)(uy)(ux) >= 123 && blocks(layer)(uy)(ux) <= 125) {
                                    blocks(layer)(uy)(ux) += 1
                                }
                                else {
                                    blocks(layer)(uy)(ux) -= 3
                                }
                                blockds(layer) = World.generate2b(blocks(layer), blockds(layer), ux, uy)
                                rdrawn(uy)(ux) = false
                                addAdjacentTilesToPQueueConditionally(ux, uy)
                            }
                            if (blocks(layer)(uy)(ux) == 127 || blocks(layer)(uy)(ux) == 129) {
                                blocks(layer)(uy)(ux) += 1
                                addBlockPower(ux, uy)
                                rdrawn(uy)(ux) = false
                                println("Srsly?")
                                updatex.add(ux)
                                updatey.add(uy)
                                updatet.add(50)
                                updatel.add(layer)
                            }
                            if (blocks(layer)(uy)(ux) >= 137 && blocks(layer)(uy)(ux) <= 168) {
                                if (blocks(layer)(uy)(ux) >= 137 && blocks(layer)(uy)(ux) <= 139 || blocks(layer)(uy)(ux) >= 141 && blocks(layer)(uy)(ux) <= 143 ||
                                    blocks(layer)(uy)(ux) >= 145 && blocks(layer)(uy)(ux) <= 147 || blocks(layer)(uy)(ux) >= 149 && blocks(layer)(uy)(ux) <= 151 ||
                                    blocks(layer)(uy)(ux) >= 153 && blocks(layer)(uy)(ux) <= 155 || blocks(layer)(uy)(ux) >= 157 && blocks(layer)(uy)(ux) <= 159 ||
                                    blocks(layer)(uy)(ux) >= 161 && blocks(layer)(uy)(ux) <= 163 || blocks(layer)(uy)(ux) >= 165 && blocks(layer)(uy)(ux) <= 167) {
                                    blocks(layer)(uy)(ux) += 1
                                }
                                else {
                                    blocks(layer)(uy)(ux) -= 3
                                }
                                blockds(layer) = World.generate2b(blocks(layer), blockds(layer), ux, uy)
                                rdrawn(uy)(ux) = false
                                redoBlockPower(ux, uy, layer)
                            }
                        }
                    }
                }
            }
            if (mouseNoLongerClicked2) {
                mouseClicked2 = false
            }
        }
        else {
            mouseClicked2 = true
        }
        if (showTool) {
            toolAngle += toolSpeed
            if (toolAngle >= 7.8) {
                toolAngle = 4.8
                showTool = false
            }
        }
        if (!DEBUG_INVINCIBLE && player.y/16 > HEIGHT + 10) {
            vc += 1
            if (vc >= 1/(Math.pow(1.001, player.y/16 - HEIGHT - 10) - 1.0)) {
                player.damage(1, false, inventory)
                vc = 0
            }
        }
        else {
            vc = 0
        }
        (entities.size()-1 to(0, -1)).foreach { i =>
            if (entities.get(i).newMob != null) {
                entities.add(entities.get(i).newMob)
            }
            if (entities.get(i).update(blocks(1), player, u, v)) {
                entities.remove(i)
            }
            else if (player.rect.intersects(entities.get(i).rect)) {
                if (entities.get(i).name != null) {
                    if (immune <= 0) {
                        if (!DEBUG_INVINCIBLE) {
                            player.damage(entities.get(i).atk, true, inventory)
                        }
                        rgnc1 = 750
                        immune = 40
                        if (player.x + player.width/2 < entities.get(i).x + entities.get(i).width/2) {
                            player.vx -= 8
                        }
                        else {
                            player.vx += 8
                        }
                        player.vy -= 3.5
                    }
                }
                else if (entities.get(i).mdelay <= 0) {
                    n = inventory.addItem(entities.get(i).id, entities.get(i).num, entities.get(i).dur)
                    if (n != 0) {
                        entities.add(new Entity(entities.get(i).x, entities.get(i).y, entities.get(i).vx, entities.get(i).vy, entities.get(i).id, (entities.get(i).num - n).toShort, entities.get(i).dur))
                    }
                    entities.remove(i)
                }
            }
        }
        if (player.hp <= 0) {
            (0 until 40).foreach { j =>
                if (inventory.ids(j) != 0) {
                    entities.add(new Entity(player.x, player.y, -1, random.nextDouble()*6-3, inventory.ids(j), inventory.nums(j), inventory.durs(j)))
                    inventory.removeLocation(j, inventory.nums(j))
                }
            }
            if (ic != null) {
                if (!ic.`type`.equals("workbench")) {
                    machinesx.add(icx)
                    machinesy.add(icy)
                    icmatrix(iclayer)(icy)(icx) = new ItemCollection(ic.`type`, ic.ids, ic.nums, ic.durs)
                }
                if (ic.`type`.equals("workbench")) {
                    if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                        (0 until 9).foreach { i =>
                            if (ic.ids(i) != 0) {
                                entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, 2, -2, ic.ids(i), ic.nums(i), ic.durs(i), 75))
                            }
                        }
                    }
                    if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                        (0 until 9).foreach { i =>
                            if (ic.ids(i) != 0) {
                                entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, -2, -2, ic.ids(i), ic.nums(i), ic.durs(i), 75))
                            }
                        }
                    }
                }
                if (ic.`type`.equals("furnace")) {
                    icmatrix(iclayer)(icy)(icx).FUELP = ic.FUELP
                    icmatrix(iclayer)(icy)(icx).SMELTP = ic.SMELTP
                    icmatrix(iclayer)(icy)(icx).F_ON = ic.F_ON
                }
                ic = null
            }
            else {
                if (showInv) {
                    (0 until 4).foreach { i =>
                        if (cic.ids(i) != 0) {
                            if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                                entities.add(new Entity(player.x, player.y, 2, -2, cic.ids(i), cic.nums(i), cic.durs(i), 75))
                            }
                            if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                                entities.add(new Entity(player.x, player.y, -2, -2, cic.ids(i), cic.nums(i), cic.durs(i), 75))
                            }
                            inventory.removeLocationIC(cic, i, cic.nums(i))
                        }
                    }
                }
                showInv = !showInv
            }
            if (moveItem != 0) {
                if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                    entities.add(new Entity(player.x, player.y, 2, -2, moveItem, moveNum, moveDur, 75))
                }
                if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                    entities.add(new Entity(player.x, player.y, -2, -2, moveItem, moveNum, moveDur, 75))
                }
                moveItem = 0
                moveNum = 0
            }
            (0 until 4).foreach { i =>
                if (armor.ids(i) != 0) {
                    if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                        entities.add(new Entity(player.x, player.y, 2, -2, armor.ids(i), armor.nums(i), armor.durs(i), 75))
                    }
                    if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                        entities.add(new Entity(player.x, player.y, -2, -2, armor.ids(i), armor.nums(i), armor.durs(i), 75))
                    }
                    inventory.removeLocationIC(armor, i, armor.nums(i))
                }
            }
            player.x = WIDTH*0.5*BLOCKSIZE
            player.y = 45
            player.vx = 0
            player.vy = 0
            player.hp = player.thp
            tool = null
            showTool = false
        }
        if (showTool) {
            if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                tp1 = new Point((player.x + player.width/2 + 6).toInt, (player.y + player.height/2).toInt)
                tp2 = new Point((player.x + player.width/2 + 6 + tool.getWidth()*2*Math.cos(toolAngle) + tool.getHeight()*2*Math.sin(toolAngle)).toInt,
                                (player.y + player.height/2 + tool.getWidth()*2*Math.sin(toolAngle) - tool.getHeight()*2*Math.cos(toolAngle)).toInt)
                tp3 = new Point((player.x + player.width/2 + 6 + tool.getWidth()*1*Math.cos(toolAngle) + tool.getHeight()*1*Math.sin(toolAngle)).toInt,
                                (player.y + player.height/2 + tool.getWidth()*1*Math.sin(toolAngle) - tool.getHeight()*1*Math.cos(toolAngle)).toInt)
                tp4 = new Point((player.x + player.width/2 + 6 + tool.getWidth()*0.5*Math.cos(toolAngle) + tool.getHeight()*0.5*Math.sin(toolAngle)).toInt,
                                (player.y + player.height/2 + tool.getWidth()*0.5*Math.sin(toolAngle) - tool.getHeight()*0.5*Math.cos(toolAngle)).toInt)
                tp5 = new Point((player.x + player.width/2 + 6 + tool.getWidth()*1.5*Math.cos(toolAngle) + tool.getHeight()*1.5*Math.sin(toolAngle)).toInt,
                                (player.y + player.height/2 + tool.getWidth()*1.5*Math.sin(toolAngle) - tool.getHeight()*1.5*Math.cos(toolAngle)).toInt)
            }
            if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                tp1 = new Point((player.x + player.width/2 - 6).toInt, (player.y + player.height/2).toInt)
                tp2 = new Point((player.x + player.width/2 - 6 + tool.getWidth()*2*Math.cos((Math.PI * 1.5) - toolAngle) + tool.getHeight()*2*Math.sin((Math.PI * 1.5) - toolAngle)).toInt,
                                (player.y + player.height/2 + tool.getWidth()*2*Math.sin((Math.PI * 1.5) - toolAngle) - tool.getHeight()*2*Math.cos((Math.PI * 1.5) - toolAngle)).toInt)
                tp3 = new Point((player.x + player.width/2 - 6 + tool.getWidth()*1*Math.cos((Math.PI * 1.5) - toolAngle) + tool.getHeight()*1*Math.sin((Math.PI * 1.5) - toolAngle)).toInt,
                                (player.y + player.height/2 + tool.getWidth()*1*Math.sin((Math.PI * 1.5) - toolAngle) - tool.getHeight()*1*Math.cos((Math.PI * 1.5) - toolAngle)).toInt)
                tp4 = new Point((player.x + player.width/2 - 6 + tool.getWidth()*0.5*Math.cos((Math.PI * 1.5) - toolAngle) + tool.getHeight()*0.5*Math.sin((Math.PI * 1.5) - toolAngle)).toInt,
                                (player.y + player.height/2 + tool.getWidth()*0.5*Math.sin((Math.PI * 1.5) - toolAngle) - tool.getHeight()*0.5*Math.cos((Math.PI * 1.5) - toolAngle)).toInt)
                tp5 = new Point((player.x + player.width/2 - 6 + tool.getWidth()*1.5*Math.cos((Math.PI * 1.5) - toolAngle) + tool.getHeight()*1.5*Math.sin((Math.PI * 1.5) - toolAngle)).toInt,
                                (player.y + player.height/2 + tool.getWidth()*1.5*Math.sin((Math.PI * 1.5) - toolAngle) - tool.getHeight()*1.5*Math.cos((Math.PI * 1.5) - toolAngle)).toInt)
            }
            (entities.size()-1 to(0, -1)).foreach { i =>
                if (entities.get(i).name != null && !entities.get(i).nohit && showTool && (entities.get(i).rect.contains(tp1) || entities.get(i).rect.contains(tp2) || entities.get(i).rect.contains(tp3) || entities.get(i).rect.contains(tp4) || entities.get(i).rect.contains(tp5)) && (!entities.get(i).name.equals("bee") || random.nextInt(4) == 0)) {
                    if (TOOLDAMAGE.get(inventory.tool()).exists(t => entities.get(i).hit(t, player))) {
                        val dropList: jul.List[Short] = entities.get(i).drops()
                        (0 until dropList.size()).foreach { j =>
                            s = dropList.get(j)
                            entities.add(new Entity(entities.get(i).x, entities.get(i).y, random.nextInt(4)-2, -1, s, 1.toShort))
                        }
                        entities.remove(i)
                    }
                    if (!jul.Arrays.asList(toolList).contains(inventory.ids(inventory.selection))) {
                        inventory.durs(inventory.selection) = (inventory.durs(inventory.selection) - 1).toShort
                    }
                    else {
                        inventory.durs(inventory.selection) = (inventory.durs(inventory.selection) -2).toShort
                    }
                    if (inventory.durs(inventory.selection) <= 0) {
                        inventory.removeLocation(inventory.selection, inventory.nums(inventory.selection))
                    }
                }
            }
        }

        (-1 until entities.size()).foreach { i =>
            if (i == -1) {
                width = player.width
                height = player.height
                p = player.x
                q = player.y
            }
            else {
                width = entities.get(i).width
                height = entities.get(i).height
                p = entities.get(i).x
                q = entities.get(i).y
            }
            var bx1 = (p/BLOCKSIZE).toInt
            var by1 = (q/BLOCKSIZE).toInt
            var bx2 = ((p+width)/BLOCKSIZE).toInt
            var by2 = ((q+height)/BLOCKSIZE).toInt

            bx1 = Math.max(0, bx1)
            by1 = Math.max(0, by1)
            bx2 = Math.min(blocks(0).length - 1, bx2)
            by2 = Math.min(blocks.length - 1, by2)

            (bx1 to bx2).foreach { x =>
                (by1 to by2).foreach { y =>
                    if (blocks(layer)(y)(x) >= 131 && blocks(layer)(y)(x) <= 136 && (i == -1 || blocks(layer)(y)(x) <= 134 && (x != -1 && entities.get(i).name != null || blocks(layer)(y)(x) <= 132))) {
                        if (blocks(layer)(y)(x) == 131 || blocks(layer)(y)(x) == 133 || blocks(layer)(y)(x) == 135) {
                            blocks(layer)(y)(x) += 1
                            rdrawn(y)(x) = false
                            addBlockPower(x, y)
                            println("Srsly?")
                            updatex.add(x)
                            updatey.add(y)
                            updatet.add(0)
                            updatel.add(0)
                        }
                    }
                }
            }
        }

        resolvePowerMatrix()
        resolveLightMatrix()
        immune -= 1
    }

    def hasOpenSpace(x: Int, y: Int, l: Int): Boolean = {
        try {
            return (blocks(l)(y-1)(x-1) == 0 || !blockcds(blocks(l)(y-1)(x-1)) ||
                    blocks(l)(y-1)(x) == 0 || !blockcds(blocks(l)(y-1)(x)) ||
                    blocks(l)(y-1)(x+1) == 0 || !blockcds(blocks(l)(y-1)(x+1)) ||
                    blocks(l)(y)(x-1) == 0 || !blockcds(blocks(l)(y)(x-1)) ||
                    blocks(l)(y)(x+1) == 0 || !blockcds(blocks(l)(y)(x+1)) ||
                    blocks(l)(y+1)(x-1) == 0 || !blockcds(blocks(l)(y+1)(x-1)) ||
                    blocks(l)(y+1)(x) == 0 || !blockcds(blocks(l)(y+1)(x)) ||
                    blocks(l)(y+1)(x+1) == 0 || !blockcds(blocks(l)(y+1)(x+1)))
        }
        catch {
            case _: ArrayIndexOutOfBoundsException => return false
        }
    }

    def empty(x: Int, y: Int): Boolean = {
        return ((blocks(0)(y)(x) == 0 || BLOCKLIGHTS.get(blocks(0)(y)(x)).fold(true)(_ == 0)) &&
                (blocks(1)(y)(x) == 0 || BLOCKLIGHTS.get(blocks(1)(y)(x)).fold(true)(_ == 0)) &&
                (blocks(2)(y)(x) == 0 || BLOCKLIGHTS.get(blocks(2)(y)(x)).fold(true)(_ == 0)))
    }

    def checkBiome(x: Int, y: Int): String = {
        var desert: Int = 0
        var frost: Int = 0
        var swamp: Int = 0
        var jungle: Int = 0
        var cavern: Int = 0
        (x-15 until x+16).foreach { x2 =>
            (y-15 until y+16).foreach { y2 =>
                if (x2+u >= 0 && x2+u < WIDTH && y2+v >= 0 && y2+v < HEIGHT) {
                    if (blocks(1)(y2+v)(x2+u) == 45 || blocks(1)(y2+v)(x2+u) == 76) {
                        desert += 1
                    }
                    else if (blocks(1)(y2+v)(x2+u) != 0) {
                        desert -= 1
                    }
                    if (blocks(1)(y2+v)(x2+u) == 1 || blocks(1)(y2+v)(x2+u) == 72 || blocks(1)(y2+v)(x2+u) == 73) {
                        jungle += 1
                    }
                    else if (blocks(1)(y2+v)(x2+u) != 0) {
                        jungle -= 1
                    }
                    if (blocks(1)(y2+v)(x2+u) == 74 || blocks(1)(y2+v)(x2+u) == 75) {
                        swamp += 1
                    }
                    else if (blocks(1)(y2+v)(x2+u) != 0) {
                        swamp -= 1
                    }
                    if (blocks(1)(y2+v)(x2+u) == 46) {
                        frost += 1
                    }
                    else if (blocks(1)(y2+v)(x2+u) != 0) {
                        frost -= 1
                    }
                    if (blockbgs(y2+v)(x2+u) == 0) {
                        cavern += 1
                    }
                    if (blocks(1)(y2+v)(x2+u) == 1 || blocks(1)(y2+v)(x2+u) == 2) {
                        cavern += 1
                    }
                    else {
                        cavern -= 1
                    }
                }
            }
        }
        if (desert > 0) {
            return "desert"
        }
        if (jungle > 0) {
            return "jungle"
        }
        if (swamp > 0) {
            return "swamp"
        }
        if (frost > 0) {
            return "frost"
        }
        if (cavern > 0) {
            return "cavern"
        }
        return "other"
    }

    def breakCurrentBlock(): Unit = {
        if (DEBUG_INSTAMINE || mining >= DURABILITY.get(inventory.tool()).get(blocks(layer)(uy)(ux))) {
            if (blocks(0)(uy)(ux) == 30) {
                blocks(0)(uy)(ux) = 0
                (uy-1 until uy+2).foreach { uly =>
                    (ux-1 until ux+2).foreach { ulx =>
                        blockdns(uly)(ulx) = random.nextInt(5).toByte
                    }
                }
                (uy until uy+3).foreach { uly =>
                    (ux-1 until ux+2).foreach { ulx =>
                        drawn(uly)(ulx) = false
                    }
                }
            }
            if (blocks(0)(uy+1)(ux) == 30) {
                blocks(0)(uy+1)(ux) = 0
                (uy until uy+3).foreach { uly =>
                    (ux-1 until ux+2).foreach { ulx =>
                        blockdns(uly)(ulx) = random.nextInt(5).toByte
                    }
                }
                (uy until uy+3).foreach { uly =>
                    (ux-1 until ux+2).foreach { ulx =>
                        drawn(uly)(ulx) = false
                    }
                }
            }
            if (blocks(layer)(uy)(ux) >= 8 && blocks(layer)(uy)(ux) <= 14 || blocks(layer)(uy)(ux) == 17 || blocks(layer)(uy)(ux) == 23 || blocks(layer)(uy)(ux) >= 80 && blocks(layer)(uy)(ux) <= 82) {
                if (ic != null) {
                    (0 until ic.ids.length).foreach { i =>
                        if (ic.ids(i) != 0 && !(ic.`type`.equals("furnace") && i == 1)) {
                            entities.add(new Entity(ux*BLOCKSIZE, uy*BLOCKSIZE, random.nextDouble()*4-2, -2, ic.ids(i), ic.nums(i), ic.durs(i)))
                        }
                    }
                }
                if (icmatrix(layer)(uy)(ux) != null) {
                    (0 until icmatrix(layer)(uy)(ux).ids.length).foreach { i =>
                        if (icmatrix(layer)(uy)(ux).ids(i) != 0 && !(icmatrix(layer)(uy)(ux).`type`.equals("furnace") && i == 1)) {
                            entities.add(new Entity(ux*BLOCKSIZE, uy*BLOCKSIZE, random.nextDouble()*4-2, -2, icmatrix(layer)(uy)(ux).ids(i), icmatrix(layer)(uy)(ux).nums(i), icmatrix(layer)(uy)(ux).durs(i)))
                        }
                    }
                    icmatrix(layer)(uy)(ux) = null
                }
                ic = null
                import scala.util.control.Breaks._
                breakable {
                    (0 until machinesx.size()).foreach { i =>
                        if (machinesx.get(i) == ux && machinesy.get(i) == uy) {
                            machinesx.remove(i)
                            machinesy.remove(i)
                            break
                        }
                    }
                }
            }
            BLOCKDROPS.get(blocks(layer)(uy)(ux)).foreach { blockdrops =>
                if (blocks(layer)(uy)(ux) != 0 && blockdrops != 0) {
                    entities.add(new Entity(ux*BLOCKSIZE, uy*BLOCKSIZE, random.nextDouble()*4-2, -2, blockdrops, 1.toShort))
                }
            }

            t = 0
            blocks(layer)(uy)(ux) match {
                case 48 =>
                    t = 77
                    n = random.nextInt(4)-2
                case 49 =>
                    t = 77
                    n = random.nextInt(2)
                case 50 =>
                    t = 77
                    n = random.nextInt(3)+1
                case 51 =>
                    t = 79
                    n = random.nextInt(4)-2
                case 52 =>
                    t = 79
                    n = random.nextInt(2)
                case 53 =>
                    t = 79
                    n = random.nextInt(3)+1
                case 54 =>
                    t = 81
                    n = random.nextInt(4)-2
                case 55 =>
                    t = 81
                    n = random.nextInt(2)
                case 56 =>
                    t = 81
                    n = random.nextInt(3)+1
                case 57 =>
                    t = 83
                    n = random.nextInt(4)-2
                case 58 =>
                    t = 83
                    n = random.nextInt(2)
                case 59 =>
                    t = 83
                    n = random.nextInt(3)+1
                case 60 =>
                    t = 85
                    n = random.nextInt(4)-2
                case 61 =>
                    t = 85
                    n = random.nextInt(2)
                case 62 =>
                    t = 85
                    n = random.nextInt(3)+1
                case 63 =>
                    t = 87
                    n = random.nextInt(4)-2
                case 64 =>
                    t = 87
                    n = random.nextInt(2)
                case 65 =>
                    t = 87
                    n = random.nextInt(3)+1
                case 66 =>
                    t = 89
                    n = random.nextInt(4)-2
                case 67 =>
                    t = 89
                    n = random.nextInt(2)
                case 68 =>
                    t = 89
                    n = random.nextInt(3)+1
                case 69 =>
                    t = 91
                    n = random.nextInt(4)-2
                case 70 =>
                    t = 91
                    n = random.nextInt(2)
                case 71 =>
                    t = 91
                    n = random.nextInt(3)+1
                case 77 =>
                    t = 95
                    n = random.nextInt(4)-2
                case 78 =>
                    t = 95
                    n = random.nextInt(2)
                case 79 =>
                    t = 95
                    n = random.nextInt(3)+1
                case _ =>
            }
            if (t != 0) {
                (0 until Math.max(1, n)).foreach { i =>
                    entities.add(new Entity(ux*BLOCKSIZE, uy*BLOCKSIZE, random.nextDouble()*4-2, -2, t.toShort, 1.toShort))
                }
            }
            removeBlockLighting(ux, uy)
            blockTemp = blocks(layer)(uy)(ux)
            blocks(layer)(uy)(ux) = 0
            if (blockTemp >= 94 && blockTemp <= 99) {
                redoBlockPower(ux, uy, layer)
            }
            if (powers(blockTemp)) {
                removeBlockPower(ux, uy, layer)
            }
            if (ltrans(blockTemp)) {
                addSunLighting(ux, uy)
                redoBlockLighting(ux, uy)
            }
            addSunLighting(ux, uy)
            blockds(layer) = World.generate2b(blocks(layer), blockds(layer), ux, uy)
            (uy-1 until uy+2).foreach { uly =>
                (ux-1 until ux+2).foreach { ulx =>
                    blockdns(uly)(ulx) = random.nextInt(5).toByte
                }
            }
            (uy-1 until uy+2).foreach { uly =>
                (ux-1 until ux+2).foreach { ulx =>
                    drawn(uly)(ulx) = false
                }
            }
            (uy-4 until uy+5).foreach { uly =>
                (ux-4 until ux+5).foreach { ulx =>
                    (0 until(3,2)).foreach { l =>
                        if (uly >= 0 && uly < HEIGHT && blocks(l)(uly)(ulx) == 16) {
                            keepLeaf = false
                            import scala.util.control.Breaks._
                            breakable {
                                (uly - 4 until uly + 5).foreach { uly2 =>
                                    breakable {
                                        (ulx - 4 until ulx + 5).foreach { ulx2 =>
                                            if (uly2 >= 0 && uly2 < HEIGHT && (blocks(1)(uly2)(ulx2) == 15 || blocks(1)(uly2)(ulx2) == 83)) {
                                                keepLeaf = true
                                                break
                                            }
                                        }
                                    }
                                    if (keepLeaf) break
                                }
                            }
                            if (!keepLeaf) {
                                blocks(l)(uly)(ulx) = 0
                                blockds(l) = World.generate2b(blocks(l), blockds(l), ulx, uly)
                                (uly-1 until uly+2).foreach { uly2 =>
                                    (ulx-1 until ulx+2).foreach { ulx2 =>
                                        drawn(uly2)(ulx2) = false
                                    }
                                }
                            }
                        }
                    }
                }
            }
            import scala.util.control.Breaks._
            breakable {
                while (true) {
                    BLOCKDROPS.get(blocks(layer)(uy)(ux - 1)).foreach { blockdrop =>
                        val isTorch =  for {
                            itemblock <- ITEMBLOCKS.get(blockdrop)
                            torch <- TORCHESR.get(itemblock)
                        } yield torch == blocks(layer)(uy)(ux - 1)
                        if (isTorch.exists(identity) || blockdrop == 178 || blockdrop == 182) {
                            entities.add(new Entity((ux - 1) * BLOCKSIZE, uy * BLOCKSIZE, random.nextDouble() * 4 - 2, -2, blockdrop, 1.toShort))
                            removeBlockLighting(ux - 1, uy)
                            if (layer == 1) {
                                addSunLighting(ux - 1, uy)
                            }
                            blockTemp = blocks(layer)(uy)(ux - 1)
                            blocks(layer)(uy)(ux - 1) = 0
                            if (blockTemp >= 94 && blockTemp <= 99) {
                                redoBlockPower(ux, uy, layer)
                            }
                            if (powers(blockTemp)) {
                                removeBlockPower(ux, uy, layer)
                            }
                            drawn(uy)(ux - 1) = false
                        }
                    }

                    BLOCKDROPS.get(blocks(layer)(uy)(ux + 1)).foreach { blockdrop =>
                        val isTorch =  for {
                            itemblock <- ITEMBLOCKS.get(blockdrop)
                            torch <- TORCHESR.get(itemblock)
                        } yield torch == blocks(layer)(uy)(ux + 1)

                        if (isTorch.exists(identity) || blockdrop == 178 || blockdrop == 182) {
                            entities.add(new Entity((ux + 1) * BLOCKSIZE, uy * BLOCKSIZE, random.nextDouble() * 4 - 2, -2, blockdrop, 1.toShort))
                            removeBlockLighting(ux + 1, uy)
                            if (layer == 1) {
                                addSunLighting(ux + 1, uy)
                            }
                            blockTemp = blocks(layer)(uy)(ux + 1)
                            blocks(layer)(uy)(ux + 1) = 0
                            if (blockTemp >= 94 && blockTemp <= 99) {
                                redoBlockPower(ux, uy, layer)
                            }
                            if (powers(blockTemp)) {
                                removeBlockPower(ux, uy, layer)
                            }
                            drawn(uy)(ux + 1) = false
                        }
                    }

                    uy -= 1
                    val notSupported = GSUPPORT.get(blocks(layer)(uy)(ux)).fold(false)( b => !b)
                    if (uy == -1 || notSupported) {
                        addSunLighting(ux, uy)
                        break
                    }
                    BLOCKDROPS.get(blocks(layer)(uy)(ux)).foreach { blockdrop =>
                        if (blockdrop != 0) {
                            entities.add(new Entity(ux * BLOCKSIZE, uy * BLOCKSIZE, random.nextDouble() * 4 - 2, -2, blockdrop, 1.toShort))
                        }
                    }

                    t = 0
                    blocks(layer)(uy)(ux) match {
                        case 48 =>
                            t = 77
                            n = random.nextInt(4) - 2
                        case 49 =>
                            t = 77
                            n = random.nextInt(2)
                        case 50 =>
                            t = 77
                            n = random.nextInt(3) + 1
                        case 51 =>
                            t = 79
                            n = random.nextInt(4) - 2
                        case 52 =>
                            t = 79
                            n = random.nextInt(2)
                        case 53 =>
                            t = 79
                            n = random.nextInt(3) + 1
                        case 54 =>
                            t = 81
                            n = random.nextInt(4) - 2
                        case 55 =>
                            t = 81
                            n = random.nextInt(2)
                        case 56 =>
                            t = 81
                            n = random.nextInt(3) + 1
                        case 57 =>
                            t = 83
                            n = random.nextInt(4) - 2
                        case 58 =>
                            t = 83
                            n = random.nextInt(2)
                        case 59 =>
                            t = 83
                            n = random.nextInt(3) + 1
                        case 60 =>
                            t = 85
                            n = random.nextInt(4) - 2
                        case 61 =>
                            t = 85
                            n = random.nextInt(2)
                        case 62 =>
                            t = 85
                            n = random.nextInt(3) + 1
                        case 63 =>
                            t = 87
                            n = random.nextInt(4) - 2
                        case 64 =>
                            t = 87
                            n = random.nextInt(2)
                        case 65 =>
                            t = 87
                            n = random.nextInt(3) + 1
                        case 66 =>
                            t = 89
                            n = random.nextInt(4) - 2
                        case 67 =>
                            t = 89
                            n = random.nextInt(2)
                        case 68 =>
                            t = 89
                            n = random.nextInt(3) + 1
                        case 69 =>
                            t = 91
                            n = random.nextInt(4) - 2
                        case 70 =>
                            t = 91
                            n = random.nextInt(2)
                        case 71 =>
                            t = 91
                            n = random.nextInt(3) + 1
                        case 77 =>
                            t = 95
                            n = random.nextInt(4) - 2
                        case 78 =>
                            t = 95
                            n = random.nextInt(2)
                        case 79 =>
                            t = 95
                            n = random.nextInt(3) + 1
                        case _ =>
                    }
                    if (t != 0) {
                        (0 until Math.max(1, n)).foreach { i =>
                            entities.add(new Entity(ux * BLOCKSIZE, uy * BLOCKSIZE, random.nextDouble() * 4 - 2, -2, t.toShort, 1.toShort))
                        }
                    }
                    removeBlockLighting(ux, uy)
                    blockTemp = blocks(layer)(uy)(ux)
                    blocks(layer)(uy)(ux) = 0
                    if (blockTemp >= 94 && blockTemp <= 99) {
                        redoBlockPower(ux, uy, layer)
                    }
                    if (powers(blockTemp)) {
                        removeBlockPower(ux, uy, layer)
                    }
                    if (ltrans(blockTemp)) {
                        addSunLighting(ux, uy)
                        redoBlockLighting(ux, uy)
                    }
                    addSunLighting(ux, uy)
                    blockds(layer) = World.generate2b(blocks(layer), blockds(layer), ux, uy)
                    (uy - 1 until uy + 2).foreach { uly =>
                        (ux - 1 until ux + 2).foreach { ulx =>
                            blockdns(uly)(ulx) = random.nextInt(5).toByte
                        }
                    }
                    (uy - 1 until uy + 2).foreach { uly =>
                        (ux - 1 until ux + 2).foreach { ulx =>
                            drawn(uly)(ulx) = false
                        }
                    }
                    (uy - 4 until uy + 5).foreach { uly =>
                        (ux - 4 until ux + 5).foreach { ulx =>
                            (0 until(3, 2)).foreach { l =>
                                if (uly >= 0 && uly < HEIGHT && blocks(l)(uly)(ulx) == 16) {
                                    keepLeaf = false
                                    breakable {
                                        (uly - 4 until uly + 5).foreach { uly2 =>
                                            breakable {
                                                (ulx - 4 until ulx + 5).foreach { ulx2 =>
                                                    if (uly2 >= 0 && uly2 < HEIGHT && (blocks(1)(uly2)(ulx2) == 15 || blocks(1)(uly2)(ulx2) == 83)) {
                                                        keepLeaf = true
                                                        break
                                                    }
                                                }
                                            }
                                            if (keepLeaf) break
                                        }
                                    }
                                    if (!keepLeaf) {
                                        blocks(l)(uly)(ulx) = 0
                                        blockds(l) = World.generate2b(blocks(l), blockds(l), ulx, uly)
                                        (uly - 1 until uly + 2).foreach { uly2 =>
                                            (ulx - 1 until ulx + 2).foreach { ulx2 =>
                                                drawn(uly2)(ulx2) = false
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            mining = 0
        }
    }

    def updateEnvironment(): Unit = {
        timeOfDay += 1.2*DEBUG_ACCEL
        (cloudsx.size()-1 until(-1, -1)).foreach { i =>
            cloudsx.set(i, cloudsx.get(i) + cloudsv.get(i))
            if (cloudsx.get(i) < -250 || cloudsx.get(i) > getWidth() + 250) {
                cloudsx.remove(i)
                cloudsy.remove(i)
                cloudsv.remove(i)
                cloudsn.remove(i)
            }
        }
        if (random.nextInt((1500/DEBUG_ACCEL).toInt) == 0) {
            cloudsn.add(random.nextInt(1))
            cloud = clouds(cloudsn.get(cloudsn.size()-1))
            if (random.nextInt(2) == 0) {
                cloudsx.add((-cloud.getWidth()*2).toDouble)
                cloudsv.add((0.1*DEBUG_ACCEL))
            }
            else {
                cloudsx.add(getWidth().toDouble);
                cloudsv.add((-0.1*DEBUG_ACCEL));
            }
            cloudsy.add(random.nextDouble()*(getHeight()-cloud.getHeight())+cloud.getHeight())
        }
    }

    def addBlockLighting(ux: Int, uy: Int): Unit = {
        n = findNonLayeredBlockLightSource(ux, uy)
        if (n != 0) {
            addTileToZQueue(ux, uy)
            lights(uy)(ux) = Math.max(lights(uy)(ux), n)
            lsources(uy)(ux) = true
            addTileToQueue(ux, uy)
        }
    }

    def addBlockPower(ux: Int, uy: Int): Unit = {
        if (powers(blocks(1)(uy)(ux))) {
            if (blocks(1)(uy)(ux) >= 137 && blocks(1)(uy)(ux) <= 168) {
                println("Whaaat?")
                updatex.add(ux)
                updatey.add(uy)
                DDELAY.get(blocks(1)(uy)(ux)).foreach(updatet.add)
                updatel.add(1)
            }
            else {
                addTileToPZQueue(ux, uy)
                power(1)(uy)(ux) = 5.toFloat
                if (conducts(blocks(1)(uy)(ux)) >= 0 && wcnct(uy)(ux)) {
                    if (receives(blocks(0)(uy)(ux))) {
                        power(0)(uy)(ux) = power(1)(uy)(ux) - conducts(blocks(1)(uy)(ux)).toFloat
                    }
                    if (receives(blocks(2)(uy)(ux))) {
                        power(2)(uy)(ux) = power(1)(uy)(ux) - conducts(blocks(1)(uy)(ux)).toFloat
                    }
                }
                addTileToPQueue(ux, uy)
            }
        }
        if (powers(blocks(0)(uy)(ux))) {
            if (blocks(0)(uy)(ux) >= 137 && blocks(0)(uy)(ux) <= 168) {
                println("Whaaat?")
                updatex.add(ux)
                updatey.add(uy)
                DDELAY.get(blocks(0)(uy)(ux)).foreach(updatet.add)
                updatel.add(0)
            }
            else {
                addTileToPZQueue(ux, uy)
                power(0)(uy)(ux) = 5.toFloat
                if (conducts(blocks(0)(uy)(ux)) >= 0 && wcnct(uy)(ux)) {
                    if (receives(blocks(1)(uy)(ux))) {
                        power(1)(uy)(ux) = power(0)(uy)(ux) - conducts(blocks(0)(uy)(ux)).toFloat
                    }
                    if (receives(blocks(2)(uy)(ux))) {
                        power(2)(uy)(ux) = power(0)(uy)(ux) - conducts(blocks(0)(uy)(ux)).toFloat
                    }
                }
                addTileToPQueue(ux, uy)
            }
        }
        if (powers(blocks(2)(uy)(ux))) {
            if (blocks(2)(uy)(ux) >= 137 && blocks(2)(uy)(ux) <= 168) {
                println("Whaaat?")
                updatex.add(ux)
                updatey.add(uy)
                DDELAY.get(blocks(2)(uy)(ux)).foreach(updatet.add)
                updatel.add(2)
            }
            else {
                addTileToPZQueue(ux, uy)
                power(2)(uy)(ux) = 5.toFloat
                if (conducts(blocks(2)(uy)(ux)) >= 0 && wcnct(uy)(ux)) {
                    if (receives(blocks(0)(uy)(ux))) {
                        power(0)(uy)(ux) = power(2)(uy)(ux) - conducts(blocks(2)(uy)(ux)).toFloat
                    }
                    if (receives(blocks(1)(uy)(ux))) {
                        power(1)(uy)(ux) = power(2)(uy)(ux) - conducts(blocks(2)(uy)(ux)).toFloat
                    }
                }
                addTileToPQueue(ux, uy)
            }
        }
    }

    def removeBlockLighting(ux: Int, uy: Int): Unit = {
        removeBlockLighting(ux, uy, layer)
    }

    def removeBlockLighting(ux: Int, uy: Int, layer: Int): Unit = {
        n = findNonLayeredBlockLightSource(ux, uy)
        if (n != 0) {
            lsources(uy)(ux) = isNonLayeredBlockLightSource(ux, uy, layer)
            (-n until n+1).foreach { axl =>
                (-n until n+1).foreach { ayl =>
                    if (Math.abs(axl)+Math.abs(ayl) <= n && uy+ayl >= 0 && uy+ayl < HEIGHT && lights(uy+ayl)(ux+axl) != 0) {
                        addTileToZQueue(ux+axl, uy+ayl)
                        lights(uy+ayl)(ux+axl) = 0.toFloat
                    }
                }
            }
            (-n-BRIGHTEST until n+1+BRIGHTEST).foreach { axl =>
                (-n-BRIGHTEST until n+1+BRIGHTEST).foreach { ayl =>
                    if (Math.abs(axl)+Math.abs(ayl) <= n+BRIGHTEST && uy+ayl >= 0 && uy+ayl < HEIGHT) {
                        if (lsources(uy+ayl)(ux+axl)) {
                            addTileToQueue(ux+axl, uy+ayl)
                        }
                    }
                }
            }
        }
    }

    def rbpRecur(ux: Int, uy: Int, lyr: Int): Unit = {
        arbprd(lyr)(uy)(ux) = true
        println("(rbpR) " + ux + " " + uy)
        addTileToPZQueue(ux, uy)
        val remember: Array[Boolean] = Array(false, false, false, false)
        var ax3, ay3: Int = 0
        (0 until 4).foreach { ir =>
            ax3 = ux+cl(ir)(0)
            ay3 = uy+cl(ir)(1)
            if (ay3 >= 0 && ay3 < HEIGHT && power(lyr)(ay3)(ax3) != 0) {
                if (power(lyr)(ay3)(ax3) != 0 && !(power(lyr)(ay3)(ax3) == power(lyr)(uy)(ux) - conducts(blocks(lyr)(uy)(ux))) &&
                 (!(blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 || blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126) ||
                  !(blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && ux > ax3 && blocks(lyr)(ay3)(ax3) != 111 && blocks(lyr)(ay3)(ax3) != 115 ||
                    blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && uy > ay3 && blocks(lyr)(ay3)(ax3) != 112 && blocks(lyr)(ay3)(ax3) != 116 ||
                    blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && ux < ax3 && blocks(lyr)(ay3)(ax3) != 113 && blocks(lyr)(ay3)(ax3) != 117 ||
                    blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && uy < ay3 && blocks(lyr)(ay3)(ax3) != 114 && blocks(lyr)(ay3)(ax3) != 118) &&
                  !(blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && ux > ax3 && blocks(lyr)(ay3)(ax3) != 119 && blocks(lyr)(ay3)(ax3) != 123 ||
                    blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && uy > ay3 && blocks(lyr)(ay3)(ax3) != 120 && blocks(lyr)(ay3)(ax3) != 124 ||
                    blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && ux < ax3 && blocks(lyr)(ay3)(ax3) != 121 && blocks(lyr)(ay3)(ax3) != 125 ||
                    blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && uy < ay3 && blocks(lyr)(ay3)(ax3) != 122 && blocks(lyr)(ay3)(ax3) != 126) &&
                  !(blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && ux > ax3 && blocks(lyr)(ay3)(ax3) != 137 && blocks(lyr)(ay3)(ax3) != 141 && blocks(lyr)(ay3)(ax3) != 145 && blocks(lyr)(ay3)(ax3) != 149 && blocks(lyr)(ay3)(ax3) != 153 && blocks(lyr)(ay3)(ax3) != 157 && blocks(lyr)(ay3)(ax3) != 161 && blocks(lyr)(ay3)(ax3) != 165 ||
                    blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && uy > ay3 && blocks(lyr)(ay3)(ax3) != 138 && blocks(lyr)(ay3)(ax3) != 142 && blocks(lyr)(ay3)(ax3) != 146 && blocks(lyr)(ay3)(ax3) != 150 && blocks(lyr)(ay3)(ax3) != 154 && blocks(lyr)(ay3)(ax3) != 158 && blocks(lyr)(ay3)(ax3) != 162 && blocks(lyr)(ay3)(ax3) != 166 ||
                    blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && ux < ax3 && blocks(lyr)(ay3)(ax3) != 139 && blocks(lyr)(ay3)(ax3) != 143 && blocks(lyr)(ay3)(ax3) != 147 && blocks(lyr)(ay3)(ax3) != 151 && blocks(lyr)(ay3)(ax3) != 155 && blocks(lyr)(ay3)(ax3) != 159 && blocks(lyr)(ay3)(ax3) != 163 && blocks(lyr)(ay3)(ax3) != 167 ||
                    blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && uy < ay3 && blocks(lyr)(ay3)(ax3) != 140 && blocks(lyr)(ay3)(ax3) != 144 && blocks(lyr)(ay3)(ax3) != 148 && blocks(lyr)(ay3)(ax3) != 152 && blocks(lyr)(ay3)(ax3) != 156 && blocks(lyr)(ay3)(ax3) != 160 && blocks(lyr)(ay3)(ax3) != 164 && blocks(lyr)(ay3)(ax3) != 168))) {
                    println("Added tile " + ax3 + " " + ay3 + " to PQueue.")
                    addTileToPQueue(ax3, ay3)
                    remember(ir) = true
                }
            }
        }
        (0 until 4).foreach { ir =>
            println("[liek srsly man?] " + ir)
            ax3 = ux+cl(ir)(0)
            ay3 = uy+cl(ir)(1)
            println("(rpbRecur2) " + ax3 + " " + ay3 + " " + power(lyr)(ay3)(ax3))
            if (ay3 >= 0 && ay3 < HEIGHT && power(lyr)(ay3)(ax3) != 0) {
                println("(rbpRecur) " + power(lyr)(ay3)(ax3) + " " + power(lyr)(uy)(ux) + " " + conducts(blocks(lyr)(uy)(ux)))
                if ((power(lyr)(ay3)(ax3) == power(lyr)(uy)(ux) - conducts(blocks(lyr)(uy)(ux))) &&
                 (!(blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 || blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126) ||
                  !(blocks(lyr)(uy)(ux) >= 111 && blocks(lyr)(uy)(ux) <= 118 && ux < ax3 && blocks(lyr)(uy)(ux) != 111 && blocks(lyr)(uy)(ux) != 115 ||
                    blocks(lyr)(uy)(ux) >= 111 && blocks(lyr)(uy)(ux) <= 118 && uy < ay3 && blocks(lyr)(uy)(ux) != 112 && blocks(lyr)(uy)(ux) != 116 ||
                    blocks(lyr)(uy)(ux) >= 111 && blocks(lyr)(uy)(ux) <= 118 && ux > ax3 && blocks(lyr)(uy)(ux) != 113 && blocks(lyr)(uy)(ux) != 117 ||
                    blocks(lyr)(uy)(ux) >= 111 && blocks(lyr)(uy)(ux) <= 118 && uy > ay3 && blocks(lyr)(uy)(ux) != 114 && blocks(lyr)(uy)(ux) != 118) &&
                  !(blocks(lyr)(uy)(ux) >= 119 && blocks(lyr)(uy)(ux) <= 126 && ux < ax3 && blocks(lyr)(uy)(ux) != 119 && blocks(lyr)(uy)(ux) != 123 ||
                    blocks(lyr)(uy)(ux) >= 119 && blocks(lyr)(uy)(ux) <= 126 && uy < ay3 && blocks(lyr)(uy)(ux) != 120 && blocks(lyr)(uy)(ux) != 124 ||
                    blocks(lyr)(uy)(ux) >= 119 && blocks(lyr)(uy)(ux) <= 126 && ux > ax3 && blocks(lyr)(uy)(ux) != 121 && blocks(lyr)(uy)(ux) != 125 ||
                    blocks(lyr)(uy)(ux) >= 119 && blocks(lyr)(uy)(ux) <= 126 && uy > ay3 && blocks(lyr)(uy)(ux) != 122 && blocks(lyr)(uy)(ux) != 126) &&
                  !(blocks(lyr)(uy)(ux) >= 137 && blocks(lyr)(uy)(ux) <= 168 && ux < ax3 && blocks(lyr)(uy)(ux) != 137 && blocks(lyr)(uy)(ux) != 141 && blocks(lyr)(uy)(ux) != 145 && blocks(lyr)(uy)(ux) != 149 && blocks(lyr)(uy)(ux) != 153 && blocks(lyr)(uy)(ux) != 157 && blocks(lyr)(uy)(ux) != 161 && blocks(lyr)(uy)(ux) != 165 ||
                    blocks(lyr)(uy)(ux) >= 137 && blocks(lyr)(uy)(ux) <= 168 && uy < ay3 && blocks(lyr)(uy)(ux) != 138 && blocks(lyr)(uy)(ux) != 142 && blocks(lyr)(uy)(ux) != 146 && blocks(lyr)(uy)(ux) != 150 && blocks(lyr)(uy)(ux) != 154 && blocks(lyr)(uy)(ux) != 158 && blocks(lyr)(uy)(ux) != 162 && blocks(lyr)(uy)(ux) != 166 ||
                    blocks(lyr)(uy)(ux) >= 137 && blocks(lyr)(uy)(ux) <= 168 && ux > ax3 && blocks(lyr)(uy)(ux) != 139 && blocks(lyr)(uy)(ux) != 143 && blocks(lyr)(uy)(ux) != 147 && blocks(lyr)(uy)(ux) != 151 && blocks(lyr)(uy)(ux) != 155 && blocks(lyr)(uy)(ux) != 159 && blocks(lyr)(uy)(ux) != 163 && blocks(lyr)(uy)(ux) != 167 ||
                    blocks(lyr)(uy)(ux) >= 137 && blocks(lyr)(uy)(ux) <= 168 && uy > ay3 && blocks(lyr)(uy)(ux) != 140 && blocks(lyr)(uy)(ux) != 144 && blocks(lyr)(uy)(ux) != 148 && blocks(lyr)(uy)(ux) != 152 && blocks(lyr)(uy)(ux) != 156 && blocks(lyr)(uy)(ux) != 160 && blocks(lyr)(uy)(ux) != 164 && blocks(lyr)(uy)(ux) != 168))) {
                    if (!arbprd(lyr)(ay3)(ax3)) {
                        rbpRecur(ax3, ay3, lyr)
                        if (conducts(blocks(lyr)(ay3)(ax3)) >= 0 && wcnct(ay3)(ax3)) {
                            if (lyr == 0) {
                                if (receives(blocks(1)(ay3)(ax3))) {
                                    rbpRecur(ax3, ay3, 1)
                                    if (powers(blocks(1)(ay3)(ax3))) {
                                        addTileToPQueue(ax3, ay3)
                                    }
                                }
                                if (receives(blocks(2)(ay3)(ax3))) {
                                    rbpRecur(ax3, ay3, 2)
                                    if (powers(blocks(2)(ay3)(ax3))) {
                                        addTileToPQueue(ax3, ay3)
                                    }
                                }
                            }
                            if (lyr == 1) {
                                if (receives(blocks(0)(ay3)(ax3))) {
                                    rbpRecur(ax3, ay3, 0)
                                    if (powers(blocks(0)(ay3)(ax3))) {
                                        addTileToPQueue(ax3, ay3)
                                    }
                                }
                                if (receives(blocks(2)(ay3)(ax3))) {
                                    rbpRecur(ax3, ay3, 2)
                                    if (powers(blocks(2)(ay3)(ax3))) {
                                        addTileToPQueue(ax3, ay3)
                                    }
                                }
                            }
                            if (lyr == 2) {
                                if (receives(blocks(0)(ay3)(ax3))) {
                                    rbpRecur(ax3, ay3, 0)
                                    if (powers(blocks(0)(ay3)(ax3))) {
                                        addTileToPQueue(ax3, ay3)
                                    }
                                }
                                if (receives(blocks(1)(ay3)(ax3))) {
                                    rbpRecur(ax3, ay3, 1)
                                    if (powers(blocks(1)(ay3)(ax3))) {
                                        addTileToPQueue(ax3, ay3)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (blocks(lyr)(ay3)(ax3) == 104 || (blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 || blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 || blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168) &&
              !(blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && ux < ax3 && blocks(lyr)(ay3)(ax3) != 111 && blocks(lyr)(ay3)(ax3) != 115 ||
                blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && uy < ay3 && blocks(lyr)(ay3)(ax3) != 112 && blocks(lyr)(ay3)(ax3) != 116 ||
                blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && ux > ax3 && blocks(lyr)(ay3)(ax3) != 113 && blocks(lyr)(ay3)(ax3) != 117 ||
                blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && uy > ay3 && blocks(lyr)(ay3)(ax3) != 114 && blocks(lyr)(ay3)(ax3) != 118) &&
              !(blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && ux < ax3 && blocks(lyr)(ay3)(ax3) != 119 && blocks(lyr)(ay3)(ax3) != 123 ||
                blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && uy < ay3 && blocks(lyr)(ay3)(ax3) != 120 && blocks(lyr)(ay3)(ax3) != 124 ||
                blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && ux > ax3 && blocks(lyr)(ay3)(ax3) != 121 && blocks(lyr)(ay3)(ax3) != 125 ||
                blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && uy > ay3 && blocks(lyr)(ay3)(ax3) != 122 && blocks(lyr)(ay3)(ax3) != 126) &&
              !(blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && ux < ax3 && blocks(lyr)(ay3)(ax3) != 137 && blocks(lyr)(ay3)(ax3) != 141 && blocks(lyr)(ay3)(ax3) != 145 && blocks(lyr)(ay3)(ax3) != 149 && blocks(lyr)(ay3)(ax3) != 153 && blocks(lyr)(ay3)(ax3) != 157 && blocks(lyr)(ay3)(ax3) != 161 && blocks(lyr)(ay3)(ax3) != 165 ||
                blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && uy < ay3 && blocks(lyr)(ay3)(ax3) != 138 && blocks(lyr)(ay3)(ax3) != 142 && blocks(lyr)(ay3)(ax3) != 146 && blocks(lyr)(ay3)(ax3) != 150 && blocks(lyr)(ay3)(ax3) != 154 && blocks(lyr)(ay3)(ax3) != 158 && blocks(lyr)(ay3)(ax3) != 162 && blocks(lyr)(ay3)(ax3) != 166 ||
                blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && ux > ax3 && blocks(lyr)(ay3)(ax3) != 139 && blocks(lyr)(ay3)(ax3) != 143 && blocks(lyr)(ay3)(ax3) != 147 && blocks(lyr)(ay3)(ax3) != 151 && blocks(lyr)(ay3)(ax3) != 155 && blocks(lyr)(ay3)(ax3) != 159 && blocks(lyr)(ay3)(ax3) != 163 && blocks(lyr)(ay3)(ax3) != 167 ||
                blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && uy > ay3 && blocks(lyr)(ay3)(ax3) != 140 && blocks(lyr)(ay3)(ax3) != 144 && blocks(lyr)(ay3)(ax3) != 148 && blocks(lyr)(ay3)(ax3) != 152 && blocks(lyr)(ay3)(ax3) != 156 && blocks(lyr)(ay3)(ax3) != 160 && blocks(lyr)(ay3)(ax3) != 164 && blocks(lyr)(ay3)(ax3) != 168)) {
                if (blocks(lyr)(ay3)(ax3) >= 123 && blocks(lyr)(ay3)(ax3) <= 126) {
                    blocks(lyr)(ay3)(ax3) -= 4
                    println("Adding power for inverter at (" + ax3 + ", " + ay3 + ").")
                    addBlockPower(ax3, ay3)
                    addBlockLighting(ax3, ay3)
                    rdrawn(ay3)(ax3) = false
                }
                removeBlockPower(ax3, ay3, lyr)
            }
        }
        (0 until 4).foreach { ir =>
            if (remember(ir) && uy+cl(ir)(1) >= 0 && uy+cl(ir)(1) < HEIGHT) {
                power(lyr)(uy+cl(ir)(1))(ux+cl(ir)(0)) = 5.toFloat
            }
        }
        power(lyr)(uy)(ux) = 0.toFloat
        arbprd(lyr)(uy)(ux) = false
    }

    def removeBlockPower(ux: Int, uy: Int, lyr: Int): Unit = {
        removeBlockPower(ux, uy, lyr, true)
    }

    def removeBlockPower(ux: Int, uy: Int, lyr: Int, turnOffDelayer: Boolean): Unit = {
        arbprd(lyr)(uy)(ux) = true
        println("[rbp ] " + ux + " " + uy + " " + lyr + " " + turnOffDelayer)
        if (!((blocks(lyr)(uy)(ux) >= 141 && blocks(lyr)(uy)(ux) <= 144 || blocks(lyr)(uy)(ux) >= 149 && blocks(lyr)(uy)(ux) <= 152 || blocks(lyr)(uy)(ux) >= 157 && blocks(lyr)(uy)(ux) <= 160 || blocks(lyr)(uy)(ux) >= 165 && blocks(lyr)(uy)(ux) <= 168) && turnOffDelayer)) {
            var ax3, ay3: Int = 0
            (0 until 4).foreach { ir =>
                ax3 = ux+cl(ir)(0)
                ay3 = uy+cl(ir)(1)
                if (ay3 >= 0 && ay3 < HEIGHT && power(lyr)(ay3)(ax3) != 0) {
                    if (!(power(lyr)(ay3)(ax3) == power(lyr)(uy)(ux) - conducts(blocks(lyr)(uy)(ux))) &&
                     (!(blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 || blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126) ||
                      !(blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && ux > ax3 && blocks(lyr)(ay3)(ax3) != 111 && blocks(lyr)(ay3)(ax3) != 115 ||
                        blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && uy > ay3 && blocks(lyr)(ay3)(ax3) != 112 && blocks(lyr)(ay3)(ax3) != 116 ||
                        blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && ux < ax3 && blocks(lyr)(ay3)(ax3) != 113 && blocks(lyr)(ay3)(ax3) != 117 ||
                        blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && uy < ay3 && blocks(lyr)(ay3)(ax3) != 114 && blocks(lyr)(ay3)(ax3) != 118) &&
                      !(blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && ux > ax3 && blocks(lyr)(ay3)(ax3) != 119 && blocks(lyr)(ay3)(ax3) != 123 ||
                        blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && uy > ay3 && blocks(lyr)(ay3)(ax3) != 120 && blocks(lyr)(ay3)(ax3) != 124 ||
                        blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && ux < ax3 && blocks(lyr)(ay3)(ax3) != 121 && blocks(lyr)(ay3)(ax3) != 125 ||
                        blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && uy < ay3 && blocks(lyr)(ay3)(ax3) != 122 && blocks(lyr)(ay3)(ax3) != 126) &&
                      !(blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && ux > ax3 && blocks(lyr)(ay3)(ax3) != 137 && blocks(lyr)(ay3)(ax3) != 141 && blocks(lyr)(ay3)(ax3) != 145 && blocks(lyr)(ay3)(ax3) != 149 && blocks(lyr)(ay3)(ax3) != 153 && blocks(lyr)(ay3)(ax3) != 157 && blocks(lyr)(ay3)(ax3) != 161 && blocks(lyr)(ay3)(ax3) != 165 ||
                        blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && uy > ay3 && blocks(lyr)(ay3)(ax3) != 138 && blocks(lyr)(ay3)(ax3) != 142 && blocks(lyr)(ay3)(ax3) != 146 && blocks(lyr)(ay3)(ax3) != 150 && blocks(lyr)(ay3)(ax3) != 154 && blocks(lyr)(ay3)(ax3) != 158 && blocks(lyr)(ay3)(ax3) != 162 && blocks(lyr)(ay3)(ax3) != 166 ||
                        blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && ux < ax3 && blocks(lyr)(ay3)(ax3) != 139 && blocks(lyr)(ay3)(ax3) != 143 && blocks(lyr)(ay3)(ax3) != 147 && blocks(lyr)(ay3)(ax3) != 151 && blocks(lyr)(ay3)(ax3) != 155 && blocks(lyr)(ay3)(ax3) != 159 && blocks(lyr)(ay3)(ax3) != 163 && blocks(lyr)(ay3)(ax3) != 167 ||
                        blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && uy < ay3 && blocks(lyr)(ay3)(ax3) != 140 && blocks(lyr)(ay3)(ax3) != 144 && blocks(lyr)(ay3)(ax3) != 148 && blocks(lyr)(ay3)(ax3) != 152 && blocks(lyr)(ay3)(ax3) != 156 && blocks(lyr)(ay3)(ax3) != 160 && blocks(lyr)(ay3)(ax3) != 164 && blocks(lyr)(ay3)(ax3) != 168))) {
                        println("Added tile " + ax3 + " " + ay3 + " to PQueue.")
                        addTileToPQueue(ax3, ay3)
                    }
                }
            }
            (0 until 4).foreach { ir =>
                ax3 = ux+cl(ir)(0)
                ay3 = uy+cl(ir)(1)
                println(blocks(lyr)(ay3)(ax3) + " " + power(lyr)(ay3)(ax3))
                if (ay3 >= 0 && ay3 < HEIGHT && power(lyr)(ay3)(ax3) != 0) {
                    println(power(lyr)(uy)(ux) + " " + power(lyr)(ay3)(ax3) + " " + conducts(blocks(lyr)(uy)(ux)))
                    if (power(lyr)(ay3)(ax3) == power(lyr)(uy)(ux) - conducts(blocks(lyr)(uy)(ux))) {
                        if (!(blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 || blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126) ||
                          !(blocks(lyr)(uy)(ux) >= 111 && blocks(lyr)(uy)(ux) <= 118 && ux < ax3 && blocks(lyr)(uy)(ux) != 111 && blocks(lyr)(uy)(ux) != 115 ||
                            blocks(lyr)(uy)(ux) >= 111 && blocks(lyr)(uy)(ux) <= 118 && uy < ay3 && blocks(lyr)(uy)(ux) != 112 && blocks(lyr)(uy)(ux) != 116 ||
                            blocks(lyr)(uy)(ux) >= 111 && blocks(lyr)(uy)(ux) <= 118 && ux > ax3 && blocks(lyr)(uy)(ux) != 113 && blocks(lyr)(uy)(ux) != 117 ||
                            blocks(lyr)(uy)(ux) >= 111 && blocks(lyr)(uy)(ux) <= 118 && uy > ay3 && blocks(lyr)(uy)(ux) != 114 && blocks(lyr)(uy)(ux) != 118) &&
                          !(blocks(lyr)(uy)(ux) >= 119 && blocks(lyr)(uy)(ux) <= 126 && ux < ax3 && blocks(lyr)(uy)(ux) != 119 && blocks(lyr)(uy)(ux) != 123 ||
                            blocks(lyr)(uy)(ux) >= 119 && blocks(lyr)(uy)(ux) <= 126 && uy < ay3 && blocks(lyr)(uy)(ux) != 120 && blocks(lyr)(uy)(ux) != 124 ||
                            blocks(lyr)(uy)(ux) >= 119 && blocks(lyr)(uy)(ux) <= 126 && ux > ax3 && blocks(lyr)(uy)(ux) != 121 && blocks(lyr)(uy)(ux) != 125 ||
                            blocks(lyr)(uy)(ux) >= 119 && blocks(lyr)(uy)(ux) <= 126 && uy > ay3 && blocks(lyr)(uy)(ux) != 122 && blocks(lyr)(uy)(ux) != 126) &&
                          !(blocks(lyr)(uy)(ux) >= 137 && blocks(lyr)(uy)(ux) <= 168 && ux < ax3 && blocks(lyr)(uy)(ux) != 137 && blocks(lyr)(uy)(ux) != 141 && blocks(lyr)(uy)(ux) != 145 && blocks(lyr)(uy)(ux) != 149 && blocks(lyr)(uy)(ux) != 153 && blocks(lyr)(uy)(ux) != 157 && blocks(lyr)(uy)(ux) != 161 && blocks(lyr)(uy)(ux) != 165 ||
                            blocks(lyr)(uy)(ux) >= 137 && blocks(lyr)(uy)(ux) <= 168 && uy < ay3 && blocks(lyr)(uy)(ux) != 138 && blocks(lyr)(uy)(ux) != 142 && blocks(lyr)(uy)(ux) != 146 && blocks(lyr)(uy)(ux) != 150 && blocks(lyr)(uy)(ux) != 154 && blocks(lyr)(uy)(ux) != 158 && blocks(lyr)(uy)(ux) != 162 && blocks(lyr)(uy)(ux) != 166 ||
                            blocks(lyr)(uy)(ux) >= 137 && blocks(lyr)(uy)(ux) <= 168 && ux > ax3 && blocks(lyr)(uy)(ux) != 139 && blocks(lyr)(uy)(ux) != 143 && blocks(lyr)(uy)(ux) != 147 && blocks(lyr)(uy)(ux) != 151 && blocks(lyr)(uy)(ux) != 155 && blocks(lyr)(uy)(ux) != 159 && blocks(lyr)(uy)(ux) != 163 && blocks(lyr)(uy)(ux) != 167 ||
                            blocks(lyr)(uy)(ux) >= 137 && blocks(lyr)(uy)(ux) <= 168 && uy > ay3 && blocks(lyr)(uy)(ux) != 140 && blocks(lyr)(uy)(ux) != 144 && blocks(lyr)(uy)(ux) != 148 && blocks(lyr)(uy)(ux) != 152 && blocks(lyr)(uy)(ux) != 156 && blocks(lyr)(uy)(ux) != 160 && blocks(lyr)(uy)(ux) != 164 && blocks(lyr)(uy)(ux) != 168)) {
                            if (!arbprd(lyr)(ay3)(ax3)) {
                                rbpRecur(ax3, ay3, lyr)
                                if (conducts(blocks(lyr)(ay3)(ax3)) >= 0 && wcnct(ay3)(ax3)) {
                                    if (lyr == 0) {
                                        if (receives(blocks(1)(ay3)(ax3))) {
                                            rbpRecur(ax3, ay3, 1)
                                            if (powers(blocks(1)(ay3)(ax3))) {
                                                addTileToPQueue(ax3, ay3)
                                            }
                                        }
                                        if (receives(blocks(2)(ay3)(ax3))) {
                                            rbpRecur(ax3, ay3, 2)
                                            if (powers(blocks(2)(ay3)(ax3))) {
                                                addTileToPQueue(ax3, ay3)
                                            }
                                        }
                                    }
                                    if (lyr == 1) {
                                        if (receives(blocks(0)(ay3)(ax3))) {
                                            rbpRecur(ax3, ay3, 0)
                                            if (powers(blocks(0)(ay3)(ax3))) {
                                                addTileToPQueue(ax3, ay3)
                                            }
                                        }
                                        if (receives(blocks(2)(ay3)(ax3))) {
                                            rbpRecur(ax3, ay3, 2)
                                            if (powers(blocks(2)(ay3)(ax3))) {
                                                addTileToPQueue(ax3, ay3)
                                            }
                                        }
                                    }
                                    if (lyr == 2) {
                                        if (receives(blocks(0)(ay3)(ax3))) {
                                            rbpRecur(ax3, ay3, 0)
                                            if (powers(blocks(0)(ay3)(ax3))) {
                                                addTileToPQueue(ax3, ay3)
                                            }
                                        }
                                        if (receives(blocks(1)(ay3)(ax3))) {
                                            rbpRecur(ax3, ay3, 1)
                                            if (powers(blocks(1)(ay3)(ax3))) {
                                                addTileToPQueue(ax3, ay3)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (blocks(lyr)(ay3)(ax3) == 104 || (blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 || blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 || blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168) &&
                  !(blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && ux < ax3 && blocks(lyr)(ay3)(ax3) != 111 && blocks(lyr)(ay3)(ax3) != 115 ||
                    blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && uy < ay3 && blocks(lyr)(ay3)(ax3) != 112 && blocks(lyr)(ay3)(ax3) != 116 ||
                    blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && ux > ax3 && blocks(lyr)(ay3)(ax3) != 113 && blocks(lyr)(ay3)(ax3) != 117 ||
                    blocks(lyr)(ay3)(ax3) >= 111 && blocks(lyr)(ay3)(ax3) <= 118 && uy > ay3 && blocks(lyr)(ay3)(ax3) != 114 && blocks(lyr)(ay3)(ax3) != 118) &&
                  !(blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && ux < ax3 && blocks(lyr)(ay3)(ax3) != 119 && blocks(lyr)(ay3)(ax3) != 123 ||
                    blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && uy < ay3 && blocks(lyr)(ay3)(ax3) != 120 && blocks(lyr)(ay3)(ax3) != 124 ||
                    blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && ux > ax3 && blocks(lyr)(ay3)(ax3) != 121 && blocks(lyr)(ay3)(ax3) != 125 ||
                    blocks(lyr)(ay3)(ax3) >= 119 && blocks(lyr)(ay3)(ax3) <= 126 && uy > ay3 && blocks(lyr)(ay3)(ax3) != 122 && blocks(lyr)(ay3)(ax3) != 126) &&
                  !(blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && ux < ax3 && blocks(lyr)(ay3)(ax3) != 137 && blocks(lyr)(ay3)(ax3) != 141 && blocks(lyr)(ay3)(ax3) != 145 && blocks(lyr)(ay3)(ax3) != 149 && blocks(lyr)(ay3)(ax3) != 153 && blocks(lyr)(ay3)(ax3) != 157 && blocks(lyr)(ay3)(ax3) != 161 && blocks(lyr)(ay3)(ax3) != 165 ||
                    blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && uy < ay3 && blocks(lyr)(ay3)(ax3) != 138 && blocks(lyr)(ay3)(ax3) != 142 && blocks(lyr)(ay3)(ax3) != 146 && blocks(lyr)(ay3)(ax3) != 150 && blocks(lyr)(ay3)(ax3) != 154 && blocks(lyr)(ay3)(ax3) != 158 && blocks(lyr)(ay3)(ax3) != 162 && blocks(lyr)(ay3)(ax3) != 166 ||
                    blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && ux > ax3 && blocks(lyr)(ay3)(ax3) != 139 && blocks(lyr)(ay3)(ax3) != 143 && blocks(lyr)(ay3)(ax3) != 147 && blocks(lyr)(ay3)(ax3) != 151 && blocks(lyr)(ay3)(ax3) != 155 && blocks(lyr)(ay3)(ax3) != 159 && blocks(lyr)(ay3)(ax3) != 163 && blocks(lyr)(ay3)(ax3) != 167 ||
                    blocks(lyr)(ay3)(ax3) >= 137 && blocks(lyr)(ay3)(ax3) <= 168 && uy > ay3 && blocks(lyr)(ay3)(ax3) != 140 && blocks(lyr)(ay3)(ax3) != 144 && blocks(lyr)(ay3)(ax3) != 148 && blocks(lyr)(ay3)(ax3) != 152 && blocks(lyr)(ay3)(ax3) != 156 && blocks(lyr)(ay3)(ax3) != 160 && blocks(lyr)(ay3)(ax3) != 164 && blocks(lyr)(ay3)(ax3) != 168)) {
                    if (blocks(lyr)(ay3)(ax3) >= 123 && blocks(lyr)(ay3)(ax3) <= 126) {
                        blocks(lyr)(ay3)(ax3) -= 4
                        println("Adding power for inverter at (" + ax3 + ", " + ay3 + ").")
                        addBlockPower(ax3, ay3)
                        addBlockLighting(ax3, ay3)
                        rdrawn(ay3)(ax3) = false
                    }
                    arbprd(lyr)(uy)(ux) = false
                    removeBlockPower(ax3, ay3, lyr)
                }
            }
        }
        if (blocks(lyr)(uy)(ux) == 104) {
            removeBlockLighting(ux, uy)
            blocks(lyr)(uy)(ux) = 103
            rdrawn(uy)(ux) = false
        }
        if (blocks(lyr)(uy)(ux) >= 115 && blocks(lyr)(uy)(ux) <= 118) {
            blockTemp = blocks(lyr)(uy)(ux)
            blocks(lyr)(uy)(ux) -= 4
            removeBlockPower(ux, uy, lyr)
            removeBlockLighting(ux, uy)
            rdrawn(uy)(ux) = false
        }
        if (turnOffDelayer && blocks(lyr)(uy)(ux) >= 137 && blocks(lyr)(uy)(ux) <= 168) {
            println("???")
            updatex.add(ux)
            updatey.add(uy)
            DDELAY.get(blocks(lyr)(uy)(ux)).foreach(updatet.add)
            updatel.add(lyr)
        }
        if (!((blocks(lyr)(uy)(ux) >= 141 && blocks(lyr)(uy)(ux) <= 144 || blocks(lyr)(uy)(ux) >= 149 && blocks(lyr)(uy)(ux) <= 152 || blocks(lyr)(uy)(ux) >= 157 && blocks(lyr)(uy)(ux) <= 160 || blocks(lyr)(uy)(ux) >= 165 && blocks(lyr)(uy)(ux) <= 168) && turnOffDelayer)) {
            power(lyr)(uy)(ux) = 0.toFloat
        }
        arbprd(lyr)(uy)(ux) = false
    }

    def redoBlockLighting(ux: Int, uy: Int): Unit = {
        (-BRIGHTEST until BRIGHTEST+1).foreach { ax =>
            (-BRIGHTEST until BRIGHTEST+1).foreach { ay =>
                if (Math.abs(ax)+Math.abs(ay) <= BRIGHTEST && uy+ay >= 0 && uy+ay < HEIGHT) {
                    addTileToZQueue(ux+ax, uy+ay)
                    lights(uy+ay)(ux+ax) = 0.toFloat
                }
            }
        }
        (-BRIGHTEST*2 until BRIGHTEST*2+1).foreach { ax =>
            (-BRIGHTEST*2 until BRIGHTEST*2+1).foreach { ay =>
                if (Math.abs(ax)+Math.abs(ay) <= BRIGHTEST*2 && uy+ay >= 0 && uy+ay < HEIGHT) {
                    if (lsources(uy+ay)(ux+ax)) {
                        addTileToQueue(ux+ax, uy+ay)
                    }
                }
            }
        }
    }

    def redoBlockPower(ux: Int, uy: Int, lyr: Int): Unit = {
        if (powers(blocks(lyr)(uy)(ux)) || blocks(lyr)(uy)(ux) >= 94 && blocks(lyr)(uy)(ux) <= 99) {
            addAdjacentTilesToPQueue(ux, uy)
        }
        else {
            removeBlockPower(ux, uy, lyr)
        }
    }

    def addSunLighting(ux: Int, uy: Int): Unit = { // And including
        (0 until uy).foreach { y =>
            if (ltrans(blocks(1)(y)(ux))) {
                return
            }
        }
        addSources = false
        (uy until HEIGHT-1).foreach { y =>
            if (ltrans(blocks(1)(y+1)(ux-1)) || ltrans(blocks(1)(y+1)(ux+1))) {
                addSources = true
            }
            if (addSources) {
                addTileToQueue(ux, y)
            }
            if (ltrans(blocks(1)(y)(ux))) {
                return
            }
            addTileToZQueue(ux, y)
            lights(y)(ux) = sunlightlevel.toFloat
            lsources(y)(ux) = true
        }
    }

    def removeSunLighting(ux: Int, uy: Int): Unit = { // And including
        n = sunlightlevel
        (0 until uy).foreach { y =>
            if (ltrans(blocks(1)(y)(ux))) {
                return
            }
        }
        import scala.util.control.Breaks._
        breakable {
            (uy until HEIGHT).foreach { y =>
                lsources(y)(ux) = isBlockLightSource(ux, y)
                if (y != uy && ltrans(blocks(1)(y)(ux))) {
                    break
                }
            }
        }
        (-n until n+1).foreach { ax =>
            (-n until n+(y-uy)+1).foreach { ay =>
                if (uy+ay >= 0 && uy+ay < WIDTH) {
                    addTileToZQueue(ux+ax, uy+ay)
                    lights(uy+ay)(ux+ax) = 0.toFloat
                }
            }
        }
        (-n-BRIGHTEST until n+1+BRIGHTEST).foreach { ax =>
            (-n-BRIGHTEST until n+(y-uy)+1+BRIGHTEST).foreach { ay =>
                if (uy+ay >= 0 && uy+ay < HEIGHT) {
                    if (lsources(uy+ay)(ux+ax)) {
                        addTileToQueue(ux+ax, uy+ay)
                    }
                }
            }
        }
    }

    def isReachedBySunlight(ux: Int, uy: Int): Boolean = {
        (0 until uy+1).foreach { ay =>
            if (ltrans(blocks(1)(ay)(ux))) {
                return false
            }
        }
        return true
    }

    def isBlockLightSource(ux: Int, uy: Int): Boolean = {
        return (blocks(0)(uy)(ux) != 0 && BLOCKLIGHTS.get(blocks(0)(uy)(ux)).exists(_ != 0) ||
                blocks(1)(uy)(ux) != 0 && BLOCKLIGHTS.get(blocks(1)(uy)(ux)).exists(_ != 0) ||
                blocks(2)(uy)(ux) != 0 && BLOCKLIGHTS.get(blocks(2)(uy)(ux)).exists(_ != 0))
    }

    def isNonLayeredBlockLightSource(ux: Int, uy: Int): Boolean = {
        return isNonLayeredBlockLightSource(ux, uy, layer)
    }

    def isNonLayeredBlockLightSource(ux: Int, uy: Int, layer: Int): Boolean = {
        return (layer != 0 && blocks(0)(uy)(ux) != 0 && BLOCKLIGHTS.get(blocks(0)(uy)(ux)).exists(_ != 0) ||
                layer != 1 && blocks(1)(uy)(ux) != 0 && BLOCKLIGHTS.get(blocks(1)(uy)(ux)).exists(_ != 0) ||
                layer != 2 && blocks(2)(uy)(ux) != 0 && BLOCKLIGHTS.get(blocks(2)(uy)(ux)).exists(_ != 0))
    }

    def findBlockLightSource(ux: Int, uy: Int): Int = {
        n = 0
        if (blocks(0)(uy)(ux) != 0) n = BLOCKLIGHTS.get(blocks(0)(uy)(ux)).map(Math.max(_, n)).getOrElse(0)
        if (blocks(1)(uy)(ux) != 0) n = BLOCKLIGHTS.get(blocks(1)(uy)(ux)).map(Math.max(_, n)).getOrElse(0)
        if (blocks(2)(uy)(ux) != 0) n = BLOCKLIGHTS.get(blocks(2)(uy)(ux)).map(Math.max(_, n)).getOrElse(0)
        return n
    }

    def findNonLayeredBlockLightSource(ux: Int, uy: Int): Int = {
        return findNonLayeredBlockLightSource(ux, uy, layer)
    }

    def findNonLayeredBlockLightSource(ux: Int, uy: Int, layer: Int): Int = {
        n = 0
        if (blocks(0)(uy)(ux) != 0) n = BLOCKLIGHTS.get(blocks(0)(uy)(ux)).map(Math.max(_, n)).getOrElse(0)
        if (blocks(1)(uy)(ux) != 0) n = BLOCKLIGHTS.get(blocks(1)(uy)(ux)).map(Math.max(_, n)).getOrElse(0)
        if (blocks(2)(uy)(ux) != 0) n = BLOCKLIGHTS.get(blocks(2)(uy)(ux)).map(Math.max(_, n)).getOrElse(0)
        return n
    }

    def addTileToQueue(ux: Int, uy: Int): Unit = {
        if (!lqd(uy)(ux)) {
            lqx.add(ux)
            lqy.add(uy)
            lqd(uy)(ux) = true
        }
    }

    def addTileToZQueue(ux: Int, uy: Int): Unit = {
        if (!zqd(uy)(ux)) {
            zqx.add(ux)
            zqy.add(uy)
            zqn(uy)(ux) = lights(uy)(ux).toByte
            zqd(uy)(ux) = true
        }
    }

    def addTileToPQueue(ux: Int, uy: Int): Unit = {
        if (!pqd(uy)(ux)) {
            pqx.add(ux)
            pqy.add(uy)
            pqd(uy)(ux) = true
        }
    }

    def addAdjacentTilesToPQueue(ux: Int, uy: Int): Unit = {
        (0 until 4).foreach { i2 =>
            if (uy+cl(i2)(1) >= 0 && uy+cl(i2)(1) < HEIGHT) {
                addTileToPQueue(ux+cl(i2)(0), uy+cl(i2)(1))
            }
        }
    }

    def addAdjacentTilesToPQueueConditionally(ux: Int, uy: Int): Unit = {
        (0 until 4).foreach { i2 =>
            (0 until 3).foreach { l =>
                if (uy+cl(i2)(1) >= 0 && uy+cl(i2)(1) < HEIGHT && power(l)(uy+cl(i2)(1))(ux+cl(i2)(0)) > 0) {
                    addTileToPQueue(ux+cl(i2)(0), uy+cl(i2)(1))
                }
            }
        }
    }

    def addTileToPZQueue(ux: Int, uy: Int): Unit = {
        if (!pzqd(uy)(ux)) {
            pzqx.add(ux)
            pzqy.add(uy)
            pzqn(0)(uy)(ux) = power(0)(uy)(ux).toByte
            pzqn(1)(uy)(ux) = power(1)(uy)(ux).toByte
            pzqn(2)(uy)(ux) = power(2)(uy)(ux).toByte
            pzqd(uy)(ux) = true
        }
    }

    def resolveLightMatrix(): Unit = {
        try {
            while (true) {
                x = lqx.get(0)
                y = lqy.get(0)
                if (lsources(y)(x)) {
                    n = findBlockLightSource(x, y)
                    if (isReachedBySunlight(x, y)) {
                        lights(y)(x) = max(lights(y)(x), n, sunlightlevel)
                    }
                    else {
                        lights(y)(x) = Math.max(lights(y)(x), n)
                    }
                    addTileToZQueue(x, y)
                }
                (0 until 4).foreach { i =>
                    x2 = x + cl(i)(0)
                    y2 = y + cl(i)(1)
                    if (y2 >= 0 && y2 < HEIGHT) {
                        if (!ltrans(blocks(1)(y2)(x2))) {
                            if (lights(y2)(x2) <= lights(y)(x) - 1.0f) {
                                addTileToZQueue(x2, y2)
                                lights(y2)(x2) = lights(y)(x) - 1.0f
                                addTileToQueue(x2, y2)
                            }
                        }
                        else {
                            if (lights(y2)(x2) <= lights(y)(x) - 2.0f) {
                                addTileToZQueue(x2, y2)
                                lights(y2)(x2) = lights(y)(x) - 2.0f
                                addTileToQueue(x2, y2)
                            }
                        }
                    }
                }
                lqx.remove(0)
                lqy.remove(0)
                lqd(y)(x) = false
            }
        }
        catch {
            case _: IndexOutOfBoundsException =>
        }
        (0 until zqx.size()).foreach { i =>
            x = zqx.get(i)
            y = zqy.get(i)
            if (lights(y)(x).toInt != zqn(y)(x)) {
                rdrawn(y)(x) = false
            }
            zqd(y)(x) = false
        }
        zqx.clear()
        zqy.clear()
    }

    def resolvePowerMatrix(): Unit = {
        try {
            while (true) {
                x = pqx.get(0)
                y = pqy.get(0)
                (0 until 3).foreach { l =>
                    if (powers(blocks(l)(y)(x))) {
                        if (!(blocks(l)(y)(x) >= 137 && blocks(l)(y)(x) <= 168)) {
                            addTileToPQueue(x, y)
                            power(l)(y)(x) = 5.toFloat
                        }
                    }
                }
                (0 until 4).foreach { i =>
                    x2 = x + cl(i)(0)
                    y2 = y + cl(i)(1)
                    if (y2 >= 0 && y2 < HEIGHT) {
                        (0 until 3).foreach { l =>
                            if (power(l)(y)(x) > 0) {
                                if (conducts(blocks(l)(y)(x)) >= 0 && receives(blocks(l)(y2)(x2)) && !(blocks(l)(y2)(x2) >= 111 && blocks(l)(y2)(x2) <= 118 && x < x2 && blocks(l)(y2)(x2) != 111 && blocks(l)(y2)(x2) != 115 ||
                                                                                                 blocks(l)(y2)(x2) >= 111 && blocks(l)(y2)(x2) <= 118 && y < y2 && blocks(l)(y2)(x2) != 112 && blocks(l)(y2)(x2) != 116 ||
                                                                                                 blocks(l)(y2)(x2) >= 111 && blocks(l)(y2)(x2) <= 118 && x > x2 && blocks(l)(y2)(x2) != 113 && blocks(l)(y2)(x2) != 117 ||
                                                                                                 blocks(l)(y2)(x2) >= 111 && blocks(l)(y2)(x2) <= 118 && y > y2 && blocks(l)(y2)(x2) != 114 && blocks(l)(y2)(x2) != 118) &&
                                                                                               !(blocks(l)(y)(x) >= 111 && blocks(l)(y)(x) <= 118 && x < x2 && blocks(l)(y)(x) != 111 && blocks(l)(y)(x) != 115 ||
                                                                                                 blocks(l)(y)(x) >= 111 && blocks(l)(y)(x) <= 118 && y < y2 && blocks(l)(y)(x) != 112 && blocks(l)(y)(x) != 116 ||
                                                                                                 blocks(l)(y)(x) >= 111 && blocks(l)(y)(x) <= 118 && x > x2 && blocks(l)(y)(x) != 113 && blocks(l)(y)(x) != 117 ||
                                                                                                 blocks(l)(y)(x) >= 111 && blocks(l)(y)(x) <= 118 && y > y2 && blocks(l)(y)(x) != 114 && blocks(l)(y)(x) != 118) &&
                                                                                               !(blocks(l)(y2)(x2) >= 119 && blocks(l)(y2)(x2) <= 126 && x < x2 && blocks(l)(y2)(x2) != 119 && blocks(l)(y2)(x2) != 123 ||
                                                                                                 blocks(l)(y2)(x2) >= 119 && blocks(l)(y2)(x2) <= 126 && y < y2 && blocks(l)(y2)(x2) != 120 && blocks(l)(y2)(x2) != 124 ||
                                                                                                 blocks(l)(y2)(x2) >= 119 && blocks(l)(y2)(x2) <= 126 && x > x2 && blocks(l)(y2)(x2) != 121 && blocks(l)(y2)(x2) != 125 ||
                                                                                                 blocks(l)(y2)(x2) >= 119 && blocks(l)(y2)(x2) <= 126 && y > y2 && blocks(l)(y2)(x2) != 122 && blocks(l)(y2)(x2) != 126) &&
                                                                                               !(blocks(l)(y)(x) >= 119 && blocks(l)(y)(x) <= 126 && x < x2 && blocks(l)(y)(x) != 119 && blocks(l)(y)(x) != 123 ||
                                                                                                 blocks(l)(y)(x) >= 119 && blocks(l)(y)(x) <= 126 && y < y2 && blocks(l)(y)(x) != 120 && blocks(l)(y)(x) != 124 ||
                                                                                                 blocks(l)(y)(x) >= 119 && blocks(l)(y)(x) <= 126 && x > x2 && blocks(l)(y)(x) != 121 && blocks(l)(y)(x) != 125 ||
                                                                                                 blocks(l)(y)(x) >= 119 && blocks(l)(y)(x) <= 126 && y > y2 && blocks(l)(y)(x) != 122 && blocks(l)(y)(x) != 126) &&
                                                                                               !(blocks(l)(y2)(x2) >= 137 && blocks(l)(y2)(x2) <= 168 && x < x2 && blocks(l)(y2)(x2) != 137 && blocks(l)(y2)(x2) != 141 && blocks(l)(y2)(x2) != 145 && blocks(l)(y2)(x2) != 149 && blocks(l)(y2)(x2) != 153 && blocks(l)(y2)(x2) != 157 && blocks(l)(y2)(x2) != 161 && blocks(l)(y2)(x2) != 165 ||
                                                                                                 blocks(l)(y2)(x2) >= 137 && blocks(l)(y2)(x2) <= 168 && y < y2 && blocks(l)(y2)(x2) != 138 && blocks(l)(y2)(x2) != 142 && blocks(l)(y2)(x2) != 146 && blocks(l)(y2)(x2) != 150 && blocks(l)(y2)(x2) != 154 && blocks(l)(y2)(x2) != 158 && blocks(l)(y2)(x2) != 162 && blocks(l)(y2)(x2) != 166 ||
                                                                                                 blocks(l)(y2)(x2) >= 137 && blocks(l)(y2)(x2) <= 168 && x > x2 && blocks(l)(y2)(x2) != 139 && blocks(l)(y2)(x2) != 143 && blocks(l)(y2)(x2) != 147 && blocks(l)(y2)(x2) != 151 && blocks(l)(y2)(x2) != 155 && blocks(l)(y2)(x2) != 159 && blocks(l)(y2)(x2) != 163 && blocks(l)(y2)(x2) != 167 ||
                                                                                                 blocks(l)(y2)(x2) >= 137 && blocks(l)(y2)(x2) <= 168 && y > y2 && blocks(l)(y2)(x2) != 140 && blocks(l)(y2)(x2) != 144 && blocks(l)(y2)(x2) != 148 && blocks(l)(y2)(x2) != 152 && blocks(l)(y2)(x2) != 156 && blocks(l)(y2)(x2) != 160 && blocks(l)(y2)(x2) != 164 && blocks(l)(y2)(x2) != 168) &&
                                                                                               !(blocks(l)(y)(x) >= 137 && blocks(l)(y)(x) <= 168 && x < x2 && blocks(l)(y)(x) != 137 && blocks(l)(y)(x) != 141 && blocks(l)(y)(x) != 145 && blocks(l)(y)(x) != 149 && blocks(l)(y)(x) != 153 && blocks(l)(y)(x) != 157 && blocks(l)(y)(x) != 161 && blocks(l)(y)(x) != 165 ||
                                                                                                 blocks(l)(y)(x) >= 137 && blocks(l)(y)(x) <= 168 && y < y2 && blocks(l)(y)(x) != 138 && blocks(l)(y)(x) != 142 && blocks(l)(y)(x) != 146 && blocks(l)(y)(x) != 150 && blocks(l)(y)(x) != 154 && blocks(l)(y)(x) != 158 && blocks(l)(y)(x) != 162 && blocks(l)(y)(x) != 166 ||
                                                                                                 blocks(l)(y)(x) >= 137 && blocks(l)(y)(x) <= 168 && x > x2 && blocks(l)(y)(x) != 139 && blocks(l)(y)(x) != 143 && blocks(l)(y)(x) != 147 && blocks(l)(y)(x) != 151 && blocks(l)(y)(x) != 155 && blocks(l)(y)(x) != 159 && blocks(l)(y)(x) != 163 && blocks(l)(y)(x) != 167 ||
                                                                                                 blocks(l)(y)(x) >= 137 && blocks(l)(y)(x) <= 168 && y > y2 && blocks(l)(y)(x) != 140 && blocks(l)(y)(x) != 144 && blocks(l)(y)(x) != 148 && blocks(l)(y)(x) != 152 && blocks(l)(y)(x) != 156 && blocks(l)(y)(x) != 160 && blocks(l)(y)(x) != 164 && blocks(l)(y)(x) != 168)) {
                                    if (power(l)(y2)(x2) <= power(l)(y)(x) - conducts(blocks(l)(y)(x))) {
                                        addTileToPZQueue(x2, y2)
                                        if (blocks(l)(y2)(x2) >= 137 && blocks(l)(y2)(x2) <= 140 ||
                                            blocks(l)(y2)(x2) >= 145 && blocks(l)(y2)(x2) <= 148 ||
                                            blocks(l)(y2)(x2) >= 153 && blocks(l)(y2)(x2) <= 156 ||
                                            blocks(l)(y2)(x2) >= 161 && blocks(l)(y2)(x2) <= 164) {
                                            println("(DEBUG1)")
                                            updatex.add(x2)
                                            updatey.add(y2)
                                            DDELAY.get(blocks(l)(y2)(x2)).foreach(updatet.add)
                                            updatel.add(l)
                                        }
                                        else {
                                            power(l)(y2)(x2) = power(l)(y)(x) - conducts(blocks(l)(y)(x)).toFloat
                                            if (conducts(blocks(l)(y2)(x2)) >= 0 && wcnct(y2)(x2)) {
                                                if (l == 0) {
                                                    if (receives(blocks(1)(y2)(x2))) {
                                                        power(1)(y2)(x2) = power(0)(y2)(x2) - conducts(blocks(0)(y2)(x2)).toFloat
                                                    }
                                                    if (receives(blocks(2)(y2)(x2))) {
                                                        power(2)(y2)(x2) = power(0)(y2)(x2) - conducts(blocks(0)(y2)(x2)).toFloat
                                                    }
                                                }
                                                if (l == 1) {
                                                    if (receives(blocks(0)(y2)(x2))) {
                                                        power(0)(y2)(x2) = power(1)(y2)(x2) - conducts(blocks(1)(y2)(x2)).toFloat
                                                    }
                                                    if (receives(blocks(2)(y2)(x2))) {
                                                        power(2)(y2)(x2) = power(1)(y2)(x2) - conducts(blocks(1)(y2)(x2)).toFloat
                                                    }
                                                }
                                                if (l == 2) {
                                                    if (receives(blocks(0)(y2)(x2))) {
                                                        power(0)(y2)(x2) = power(2)(y2)(x2) - conducts(blocks(2)(y2)(x2)).toFloat
                                                    }
                                                    if (receives(blocks(1)(y2)(x2))) {
                                                        power(1)(y2)(x2) = power(2)(y2)(x2) - conducts(blocks(2)(y2)(x2)).toFloat
                                                    }
                                                }
                                            }
                                        }
                                        if (!(blocks(l)(y2)(x2) >= 119 && blocks(l)(y2)(x2) <= 122)) {
                                            addTileToPQueue(x2, y2)
                                        }
                                    }
                                    if (power(l)(y)(x) - conducts(blocks(l)(y)(x)) > 0 && blocks(l)(y2)(x2) >= 119 && blocks(l)(y2)(x2) <= 122) {
                                        removeBlockPower(x2, y2, l)
                                        blocks(l)(y2)(x2) += 4
                                        removeBlockLighting(x2, y2)
                                        rdrawn(y2)(x2) = false
                                    }
                                }
                            }
                        }
                    }
                }
                pqx.remove(0)
                pqy.remove(0)
                pqd(y)(x) = false
                (0 until 3).foreach { l =>
                    println("(resolvePowerMatrix) " + x + " " + y + " " + l + " " + blocks(l)(y)(x) + " " + power(l)(y)(x))
                    if (power(l)(y)(x) > 0) {
                        if (blocks(l)(y)(x) == 103) {
                            blocks(l)(y)(x) = 104
                            addBlockLighting(x, y)
                            rdrawn(y)(x) = false
                        }
                        if (blocks(l)(y)(x) >= 111 && blocks(l)(y)(x) <= 114) {
                            println("Processed amplifier at " + x + " " + y)
                            blocks(l)(y)(x) += 4
                            addTileToPQueue(x, y)
                            addBlockLighting(x, y)
                            rdrawn(y)(x) = false
                        }
                    }
                }
            }
        }
        catch {
            case _: IndexOutOfBoundsException =>
        }
        (0 until pzqx.size()).foreach { i =>
            x = pzqx.get(i)
            y = pzqy.get(i)
            (0 until 3).foreach { l =>
                if (blocks(l)(y)(x) >= 94 && blocks(l)(y)(x) <= 99 && power(l)(y)(x).toInt != pzqn(l)(y)(x)) {
                    removeBlockLighting(x, y, 0)
                    WIREP.get(power(l)(y)(x).toInt).foreach { w =>
                        blocks(l)(y)(x) = w
                    }
                    addBlockLighting(x, y)
                    rdrawn(y)(x) = false
                }
            }
            pzqd(y)(x) = false
        }
        pzqx.clear()
        pzqy.clear()
    }

    override def paint(g: Graphics): Unit = {
        if (screen == null) return
        pg2 = screen.createGraphics()
        pg2.setColor(bg)
        pg2.fillRect(0, 0, getWidth(), getHeight())
        if (state.equals("ingame")) {
            /*            if (SKYLIGHTS.get(timeOfDay.toInt) != null) {
                sunlightlevel = SKYLIGHTS.get(timeOfDay.toInt)
                resunlight = 0
            }
            if (resunlight < WIDTH) {
                (resunlight until Math.min(resunlight+SUNLIGHTSPEED,WIDTH)).foreach { ux =>
                    removeSunLighting(ux, 0)
                    addSunLighting(ux, 0)
                }
                resunlight += SUNLIGHTSPEED
            }
*/
            if (player.y / 16 < HEIGHT * 0.5) {
                pg2.translate(getWidth() / 2, getHeight() * 0.85)
                pg2.rotate((timeOfDay - 70200) / 86400 * Math.PI * 2)

                pg2.drawImage(sun,
                    (-getWidth() * 0.65).toInt, 0, (-getWidth() * 0.65 + sun.getWidth() * 2).toInt, sun.getHeight() * 2,
                    0, 0, sun.getWidth(), sun.getHeight(),
                    null)

                pg2.rotate(Math.PI)

                pg2.drawImage(moon,
                    (-getWidth() * 0.65).toInt, 0, (-getWidth() * 0.65 + moon.getWidth() * 2).toInt, moon.getHeight() * 2,
                    0, 0, moon.getWidth(), moon.getHeight(),
                    null)

                pg2.rotate(-(timeOfDay - 70200) / 86400 * Math.PI * 2 - Math.PI)
                pg2.translate(-getWidth() / 2, -getHeight() * 0.85)

                (0 until cloudsx.size()).foreach { i =>
                    cloud = clouds(cloudsn.get(i))
                    pg2.drawImage(clouds(cloudsn.get(i)),
                        cloudsx.get(i).toInt, cloudsy.get(i).toInt, (cloudsx.get(i).toDouble + cloud.getWidth() * 2).toInt, (cloudsy.get(i).toDouble + cloud.getHeight() * 2).toInt,
                        0, 0, cloud.getWidth(), cloud.getHeight(),
                        null)
                }
            }

            (0 until 2).foreach { pwy =>
                (0 until 2).foreach { pwx =>
                    val pwxc: Int = pwx + ou
                    val pwyc: Int = pwy + ov
                    if (worlds(pwy)(pwx) != null) {
                        if (((player.ix + getWidth() / 2 + player.width >= pwxc * CHUNKSIZE &&
                          player.ix + getWidth() / 2 + player.width <= pwxc * CHUNKSIZE + CHUNKSIZE) ||
                          (player.ix - getWidth() / 2 + player.width + BLOCKSIZE >= pwxc * CHUNKSIZE &&
                            player.ix - getWidth() / 2 + player.width - BLOCKSIZE <= pwxc * CHUNKSIZE + CHUNKSIZE)) &&
                          ((player.iy + getHeight() / 2 + player.height >= pwyc * CHUNKSIZE &&
                            player.iy + getHeight() / 2 + player.height <= pwyc * CHUNKSIZE + CHUNKSIZE) ||
                            (player.iy - getHeight() / 2 + player.height >= pwyc * CHUNKSIZE &&
                              player.iy - getHeight() / 2 + player.height <= pwyc * CHUNKSIZE + CHUNKSIZE))) {
                            pg2.drawImage(worlds(pwy)(pwx),
                                pwxc * CHUNKSIZE - player.ix + getWidth() / 2 - player.width / 2, pwyc * CHUNKSIZE - player.iy + getHeight() / 2 - player.height / 2, pwxc * CHUNKSIZE - player.ix + getWidth() / 2 - player.width / 2 + CHUNKSIZE, pwyc * CHUNKSIZE - player.iy + getHeight() / 2 - player.height / 2 + CHUNKSIZE,
                                0, 0, CHUNKSIZE, CHUNKSIZE,
                                null)
                        }
                    }
                }
            }

            pg2.drawImage(player.image,
                getWidth() / 2 - player.width / 2, getHeight() / 2 - player.height / 2, getWidth() / 2 + player.width / 2, getHeight() / 2 + player.height / 2,
                0, 0, player.image.getWidth(), player.image.getHeight(),
                null)

            (0 until entities.size()).foreach { i =>
                entity = entities.get(i)
                pg2.drawImage(entity.image,
                    entity.ix - player.ix + getWidth() / 2 - player.width / 2, entity.iy - player.iy + getHeight() / 2 - player.height / 2, entity.ix - player.ix + getWidth() / 2 - player.width / 2 + entity.width, entity.iy - player.iy + getHeight() / 2 - player.height / 2 + entity.height,
                    0, 0, entity.image.getWidth(), entity.image.getHeight(),
                    null)
                pg2.drawImage(entity.image,
                    entity.ix - player.ix + getWidth() / 2 - player.width / 2 - WIDTH * BLOCKSIZE, entity.iy - player.iy + getHeight() / 2 - player.height / 2, entity.ix - player.ix + getWidth() / 2 - player.width / 2 + entity.width - WIDTH * BLOCKSIZE, entity.iy - player.iy + getHeight() / 2 - player.height / 2 + entity.height,
                    0, 0, entity.image.getWidth(), entity.image.getHeight(),
                    null)
                pg2.drawImage(entity.image,
                    entity.ix - player.ix + getWidth() / 2 - player.width / 2 + WIDTH * BLOCKSIZE, entity.iy - player.iy + getHeight() / 2 - player.height / 2, entity.ix - player.ix + getWidth() / 2 - player.width / 2 + entity.width + WIDTH * BLOCKSIZE, entity.iy - player.iy + getHeight() / 2 - player.height / 2 + entity.height,
                    0, 0, entity.image.getWidth(), entity.image.getHeight(),
                    null)
            }

            if (showTool && tool != null) {
                if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                    pg2.translate(getWidth() / 2 + 6, getHeight() / 2)
                    pg2.rotate(toolAngle)

                    pg2.drawImage(tool,
                        0, -tool.getHeight() * 2, tool.getWidth() * 2, 0,
                        0, 0, tool.getWidth(), tool.getHeight(),
                        null)

                    pg2.rotate(-toolAngle)
                    pg2.translate(-getWidth() / 2 - 6, -getHeight() / 2)
                }
                if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                    pg2.translate(getWidth() / 2 - 6, getHeight() / 2)
                    pg2.rotate((Math.PI * 1.5) - toolAngle)

                    pg2.drawImage(tool,
                        0, -tool.getHeight() * 2, tool.getWidth() * 2, 0,
                        0, 0, tool.getWidth(), tool.getHeight(),
                        null)

                    pg2.rotate(-((Math.PI * 1.5) - toolAngle))
                    pg2.translate(-getWidth() / 2 + 6, -getHeight() / 2)
                }
            }

            (0 until 2).foreach { pwy =>
                (0 until 2).foreach { pwx =>
                    val pwxc: Int = pwx + ou
                    val pwyc: Int = pwy + ov
                    if (fworlds(pwy)(pwx) != null) {
                        if (((player.ix + getWidth() / 2 + player.width >= pwxc * CHUNKSIZE &&
                          player.ix + getWidth() / 2 + player.width <= pwxc * CHUNKSIZE + CHUNKSIZE) ||
                          (player.ix - getWidth() / 2 + player.width + BLOCKSIZE >= pwxc * CHUNKSIZE &&
                            player.ix - getWidth() / 2 + player.width - BLOCKSIZE <= pwxc * CHUNKSIZE + CHUNKSIZE)) &&
                          ((player.iy + getHeight() / 2 + player.height >= pwyc * CHUNKSIZE &&
                            player.iy + getHeight() / 2 + player.height <= pwyc * CHUNKSIZE + CHUNKSIZE) ||
                            (player.iy - getHeight() / 2 + player.height >= pwyc * CHUNKSIZE &&
                              player.iy - getHeight() / 2 + player.height <= pwyc * CHUNKSIZE + CHUNKSIZE))) {
                            pg2.drawImage(fworlds(pwy)(pwx),
                                pwxc * CHUNKSIZE - player.ix + getWidth() / 2 - player.width / 2, pwyc * CHUNKSIZE - player.iy + getHeight() / 2 - player.height / 2, pwxc * CHUNKSIZE - player.ix + getWidth() / 2 - player.width / 2 + CHUNKSIZE, pwyc * CHUNKSIZE - player.iy + getHeight() / 2 - player.height / 2 + CHUNKSIZE,
                                0, 0, CHUNKSIZE, CHUNKSIZE,
                                null)
                        }
                    }
                }
            }

            if (showInv) {
                pg2.drawImage(inventory.image,
                    0, 0, inventory.image.getWidth(), inventory.image.getHeight(),
                    0, 0, inventory.image.getWidth(), inventory.image.getHeight(),
                    null)
                pg2.drawImage(armor.image,
                    inventory.image.getWidth() + 6, 6, inventory.image.getWidth() + 6 + armor.image.getWidth(), 6 + armor.image.getHeight(),
                    0, 0, armor.image.getWidth(), armor.image.getHeight(),
                    null)
                pg2.drawImage(cic.image,
                    inventory.image.getWidth() + 75, 52, inventory.image.getWidth() + 75 + cic.image.getWidth(), 52 + cic.image.getHeight(),
                    0, 0, cic.image.getWidth(), cic.image.getHeight(),
                    null)
            }
            else {
                pg2.drawImage(inventory.image,
                    0, 0, inventory.image.getWidth(), (inventory.image.getHeight() / 4),
                    0, 0, inventory.image.getWidth(), (inventory.image.getHeight() / 4),
                    null)
            }

            if (ic != null) {
                pg2.drawImage(ic.image,
                    6, inventory.image.getHeight() + 46, 6 + ic.image.getWidth(), inventory.image.getHeight() + 46 + ic.image.getHeight(),
                    0, 0, ic.image.getWidth(), ic.image.getHeight(),
                    null)
            }

            if (layer == 0) {
                layerImg = loadImage("Interface/layersB.png")
            }
            if (layer == 1) {
                layerImg = loadImage("Interface/layersN.png")
            }
            if (layer == 2) {
                layerImg = loadImage("Interface/layersF.png")
            }

            pg2.drawImage(layerImg,
                inventory.image.getWidth() + 58, 6, inventory.image.getWidth() + 58 + layerImg.getWidth(), 6 + layerImg.getHeight(),
                0, 0, layerImg.getWidth(), layerImg.getHeight(),
                null)

            if (showInv) {
                pg2.drawImage(save_exit,
                    getWidth() - save_exit.getWidth() - 24, getHeight() - save_exit.getHeight() - 24, getWidth() - 24, getHeight() - 24,
                    0, 0, save_exit.getWidth(), save_exit.getHeight(),
                    null)
            }

            if (moveItem != 0) {
                itemImgs.get(moveItem).foreach { i =>
                    width = i.getWidth
                    height = i.getHeight
                    pg2.drawImage(i, mousePos(0) + 12 + ((24 - 12.toDouble / max(width, height, 12) * width * 2) / 2).toInt, mousePos(1) + 12 + ((24 - 12.toDouble / max(width, height, 12) * height * 2) / 2).toInt, mousePos(0) + 36 - ((24 - 12.toDouble / max(width, height, 12) * width * 2) / 2).toInt, mousePos(1) + 36 - ((24 - 12.toDouble / max(width, height, 12) * height * 2) / 2).toInt,
                        0, 0, width, height,
                        null)
                }

                if (moveNum > 1) {
                    pg2.setFont(font)
                    pg2.setColor(Color.WHITE)
                    pg2.drawString(moveNum + " ", mousePos(0) + 13, mousePos(1) + 38)
                }
            }
            import scala.util.control.Breaks._
            breakable {
                (0 until entities.size()).foreach { i =>
                    if (UIENTITIES.get(entities.get(i).name) != null && entities.get(i).rect != null && entities.get(i).rect.contains(new Point(mousePos2(0), mousePos2(1)))) {
                        pg2.setFont(mobFont)
                        pg2.setColor(Color.WHITE)
                        pg2.drawString(UIENTITIES.get(entities.get(i).name) + " (" + entities.get(i).hp + "/" + entities.get(i).thp + ")", mousePos(0), mousePos(1))
                        break
                    }
                }
            }
            if (showInv) {
                ymax = 4
            }
            else {
                ymax = 1
            }
            (0 until 10).foreach { ux =>
                (0 until ymax).foreach { uy =>
                    if (mousePos(0) >= ux*46+6 && mousePos(0) <= ux*46+46 &&
                        mousePos(1) >= uy*46+6 && mousePos(1) <= uy*46+46 && inventory.ids(uy*10+ux) != 0) {
                        pg2.setFont(mobFont)
                        pg2.setColor(Color.WHITE)

                        TOOLDURS.get(inventory.ids(uy*10+ux)).fold {
                            UIBLOCKS.get(items(inventory.ids(uy*10+ux))).foreach(pg2.drawString(_, mousePos(0), mousePos(1)))
                        } { t =>
                            UIBLOCKS.get(items(inventory.ids(uy*10+ux))).foreach(u => pg2.drawString(u + " (" + (inventory.durs(uy*10+ux).toDouble/t*100).toInt + "%)", mousePos(0), mousePos(1)))
                        }
                    }
                }
            }
            pg2.setFont(mobFont)
            pg2.setColor(Color.WHITE)
            pg2.drawString("Health: " + player.hp + "/" + player.thp, getWidth()-125, 20)
            pg2.drawString("Armor: " + player.sumArmor(), getWidth()-125, 40)
            if (DEBUG_STATS) {
                pg2.drawString("(" + (player.ix/16).toInt + ", " + (player.iy/16) + ")", getWidth()-125, 60)
                if (player.iy >= 0 && player.iy < HEIGHT*BLOCKSIZE) {
                    pg2.drawString(checkBiome((player.ix/16+u), (player.iy/16+v)) + " " + lights((player.iy/16+v))((player.ix/16+u)), getWidth()-125, 80)
                }
            }
            if (showInv) {
                (0 until 2).foreach { ux =>
                    (0 until 2).foreach { uy =>
                        if (mousePos(0) >= inventory.image.getWidth()+ux*40+75 &&
                            mousePos(0) < inventory.image.getWidth()+ux*40+115 &&
                            mousePos(1) >= uy*40+52 && mousePos(1) < uy*40+92 && cic.ids(uy*2+ux) != 0) {
                            pg2.setFont(mobFont)
                            pg2.setColor(Color.WHITE)
                            TOOLDURS.get(cic.ids(uy*2+ux)).fold {
                                UIBLOCKS.get(items(cic.ids(uy*2+ux))).foreach(pg2.drawString(_, mousePos(0), mousePos(1)))
                            } { t =>
                                UIBLOCKS.get(items(cic.ids(uy*2+ux))).foreach( u => pg2.drawString(u + " (" + (cic.durs(uy*2+ux).toDouble/t*100).toInt + "%)", mousePos(0), mousePos(1)))
                            }
                        }
                    }
                }
                if (mousePos(0) >= inventory.image.getWidth()+3*40+75 &&
                    mousePos(0) < inventory.image.getWidth()+3*40+115 &&
                    mousePos(1) >= 20+52 && mousePos(1) < 20+92 && cic.ids(4) != 0) {
                    pg2.setFont(mobFont)
                    pg2.setColor(Color.WHITE)
                    TOOLDURS.get(cic.ids(4)).fold {
                        UIBLOCKS.get(items(cic.ids(4))).foreach(pg2.drawString(_, mousePos(0), mousePos(1)))
                    } { t =>
                        UIBLOCKS.get(items(cic.ids(4))).foreach( u => pg2.drawString(u + " (" + (cic.durs(4).toDouble/t*100).toInt + "%)", mousePos(0), mousePos(1)))
                    }

                }
                (0 until 4).foreach { uy =>
                    if (mousePos(0) >= inventory.image.getWidth() + 6 && mousePos(0) < inventory.image.getWidth() + 6 + armor.image.getWidth() &&
                        mousePos(1) >= 6 + uy*46 && mousePos(1) < 6 + uy*46+46 && armor.ids(uy) != 0) {
                        pg2.setFont(mobFont)
                        pg2.setColor(Color.WHITE)

                        TOOLDURS.get(armor.ids(uy)).fold {
                            UIBLOCKS.get(items(armor.ids(uy))).foreach(pg2.drawString(_, mousePos(0), mousePos(1)))
                        } { t =>
                            UIBLOCKS.get(items(armor.ids(uy))).foreach( u => pg2.drawString(u + " (" + (armor.durs(uy).toDouble/t*100).toInt + "%)", mousePos(0), mousePos(1)))
                        }

                    }
                }
            }
            if (ic != null) {
                if (ic.`type`.equals("workbench")) {
                    (0 until 3).foreach { ux =>
                        (0 until 3).foreach { uy =>
                            if (mousePos(0) >= ux*40+6 && mousePos(0) < ux*40+46 &&
                                mousePos(1) >= uy*40+inventory.image.getHeight()+46 &&
                                mousePos(1) < uy*40+inventory.image.getHeight()+86 &&
                                ic.ids(uy*3+ux) != 0) {
                                pg2.setFont(mobFont)
                                pg2.setColor(Color.WHITE)

                                TOOLDURS.get(ic.ids(uy*3+ux)).fold {
                                    UIBLOCKS.get(items(ic.ids(uy*3+ux))).foreach(pg2.drawString(_, mousePos(0), mousePos(1)))
                                } { t =>
                                    UIBLOCKS.get(items(ic.ids(uy*3+ux))).foreach(u => pg2.drawString(u + " (" + (ic.durs(uy*3+ux).toDouble/t*100).toInt + "%)", mousePos(0), mousePos(1)))
                                }
                            }
                        }
                    }
                    if (mousePos(0) >= 4*40+6 && mousePos(0) < 4*40+46 &&
                        mousePos(1) >= 1*40+inventory.image.getHeight()+46 &&
                        mousePos(1) < 1*40+inventory.image.getHeight()+86 &&
                        ic.ids(9) != 0) {
                        pg2.setFont(mobFont)
                        pg2.setColor(Color.WHITE)

                        TOOLDURS.get(ic.ids(9)).fold {
                            UIBLOCKS.get(items(ic.ids(9))).foreach(pg2.drawString(_, mousePos(0), mousePos(1)))
                        } { t =>
                            UIBLOCKS.get(items(ic.ids(9))).foreach( u => pg2.drawString(u + " (" + (ic.durs(9).toDouble/t*100).toInt + "%)", mousePos(0), mousePos(1)))
                        }
                    }
                }
                if (ic.`type`.equals("wooden_chest") || ic.`type`.equals("stone_chest") ||
                    ic.`type`.equals("copper_chest") || ic.`type`.equals("iron_chest") ||
                    ic.`type`.equals("silver_chest") || ic.`type`.equals("gold_chest") ||
                    ic.`type`.equals("zinc_chest") || ic.`type`.equals("rhymestone_chest") ||
                    ic.`type`.equals("obdurite_chest")) {
                    (0 until inventory.CX).foreach { ux =>
                        (0 until inventory.CY).foreach { uy =>
                            if (mousePos(0) >= ux*46+6 && mousePos(0) < ux*46+46 &&
                                mousePos(1) >= uy*46+inventory.image.getHeight()+46 &&
                                mousePos(1) < uy*46+inventory.image.getHeight()+86 &&
                                ic.ids(uy*inventory.CX+ux) != 0) {
                                pg2.setFont(mobFont)
                                pg2.setColor(Color.WHITE)

                                TOOLDURS.get(ic.ids(uy*inventory.CX+ux)).fold {
                                    UIBLOCKS.get(items(ic.ids(uy*inventory.CX+ux))).foreach(pg2.drawString(_, mousePos(0), mousePos(1)))
                                } { t =>
                                    UIBLOCKS.get(items(ic.ids(uy*inventory.CX+ux))).foreach( u => pg2.drawString(u + " (" + (ic.durs(uy*inventory.CX+ux).toDouble/t*100).toInt + "%)", mousePos(0), mousePos(1)))
                                }
                            }
                        }
                    }
                }
                if (ic.`type`.equals("furnace")) {
                    if (mousePos(0) >= 6 && mousePos(0) < 46 &&
                        mousePos(1) >= inventory.image.getHeight()+46 && mousePos(1) < inventory.image.getHeight()+86 &&
                        ic.ids(0) != 0) {
                        pg2.setFont(mobFont)
                        pg2.setColor(Color.WHITE)

                        TOOLDURS.get(ic.ids(0)).fold {
                            UIBLOCKS.get(items(ic.ids(0))).foreach(pg2.drawString(_, mousePos(0), mousePos(1)))
                        } { t =>
                            UIBLOCKS.get(items(ic.ids(0))).foreach(u => pg2.drawString(u + " (" + (ic.durs(0).toDouble/t*100).toInt + "%)", mousePos(0), mousePos(1)))
                        }
                    }
                    if (mousePos(0) >= 6 && mousePos(0) < 46 &&
                        mousePos(1) >= inventory.image.getHeight()+102 && mousePos(1) < inventory.image.getHeight()+142 &&
                        ic.ids(1) != 0) {
                        pg2.setFont(mobFont)
                        pg2.setColor(Color.WHITE)

                        TOOLDURS.get(ic.ids(1)).fold {
                            UIBLOCKS.get(items(ic.ids(1))).foreach(pg2.drawString(_, mousePos(0), mousePos(1)))
                        } { t =>
                            UIBLOCKS.get(items(ic.ids(1))).foreach(u => pg2.drawString(u + " (" + (ic.durs(1).toDouble/t*100).toInt + "%)", mousePos(1), mousePos(1)))
                        }
                    }
                    if (mousePos(0) >= 6 && mousePos(0) < 46 &&
                        mousePos(1) >= inventory.image.getHeight()+142 && mousePos(1) < inventory.image.getHeight()+182 &&
                        ic.ids(2) != 0) {
                        pg2.setFont(mobFont)
                        pg2.setColor(Color.WHITE)

                        TOOLDURS.get(ic.ids(2)).fold {
                            UIBLOCKS.get(items(ic.ids(2))).foreach(pg2.drawString(_, mousePos(0), mousePos(1)))
                        } { t =>
                            UIBLOCKS.get(items(ic.ids(2))).foreach(u => pg2.drawString(u + " (" + (ic.durs(2).toDouble/t*100).toInt + "%)", mousePos(2), mousePos(1)))
                        }
                    }
                    if (mousePos(0) >= 62 && mousePos(0) < 102 &&
                        mousePos(1) >= inventory.image.getHeight()+46 && mousePos(1) < inventory.image.getHeight()+86 &&
                        ic.ids(3) != 0) {
                        pg2.setFont(mobFont)
                        pg2.setColor(Color.WHITE)

                        TOOLDURS.get(ic.ids(3)).fold {
                            UIBLOCKS.get(items(ic.ids(3))).foreach(pg2.drawString(_, mousePos(0), mousePos(1)))
                        } { t =>
                            UIBLOCKS.get(items(ic.ids(3))).foreach(u => pg2.drawString(u + " (" + (ic.durs(3).toDouble/t*100).toInt + "%)", mousePos(3), mousePos(1)))
                        }
                    }
                }
            }
        }
        if (state.equals("loading_graphics")) {
            pg2.setFont(loadFont)
            pg2.setColor(Color.GREEN)
            pg2.drawString("Loading graphics... Please wait.", getWidth()/2-200, getHeight()/2-5)
        }
        if (state.equals("title_screen")) {
            pg2.drawImage(title_screen,
                0, 0, getWidth(), getHeight(),
                0, 0, getWidth(), getHeight(),
                null)
        }
        if (state.equals("select_world")) {
            pg2.drawImage(select_world,
                0, 0, getWidth(), getHeight(),
                0, 0, getWidth(), getHeight(),
                null)
            (0 until worldNames.length).foreach { i =>
                pg2.setFont(worldFont)
                pg2.setColor(Color.BLACK)
                pg2.drawString(worldNames(i), 180, 140+i*35)
                pg2.fillRect(166, 150+i*35, 470, 3)
            }
        }
        if (state.equals("new_world")) {
            pg2.drawImage(new_world,
                0, 0, getWidth(), getHeight(),
                0, 0, getWidth(), getHeight(),
                null)
            pg2.drawImage(newWorldName.image,
                200, 240, 600, 270,
                0, 0, 400, 30,
                null)
        }
        if (state.equals("generating_world")) {
            pg2.setFont(loadFont)
            pg2.setColor(Color.GREEN)
            pg2.drawString("Generating new world... Please wait.", getWidth()/2-200, getHeight()/2-5)
        }
        (g.asInstanceOf[Graphics2D]).drawImage(screen,
            0, 0, getWidth(), getHeight(),
            0, 0, getWidth(), getHeight(),
            null)
    }

    def loadWorld(worldFile: String): Boolean = {
        try {
            val fileIn = new FileInputStream("worlds/" + worldFile)
            val in = new ObjectInputStream(fileIn)
            val wc = in.readObject().asInstanceOf[WorldContainer]
            in.close()
            fileIn.close()
            emptyWorldContainer(wc)
            return true
        }
        catch {
            case NonFatal(_) => return false
        }
    }

    def saveWorld(): Unit = {
        try {
            val wc: WorldContainer = createWorldContainer()
            val fileOut = new FileOutputStream("worlds/" + currentWorld + ".dat")
            val out = new ObjectOutputStream(fileOut)
            out.writeObject(wc)
            out.close()
            fileOut.close()
        }
        catch {
            case e: IOException => e.printStackTrace()
        }
    }

    def emptyWorldContainer(wc: WorldContainer): Unit = {
        blocks = wc.blocks
        blockds = wc.blockds
        blockdns = wc.blockdns
        blockbgs = wc.blockbgs
        blockts = wc.blockts
        lights = wc.lights
        power = wc.power
        resetDrawn()
        player = wc.player
        inventory = wc.inventory
        cic = wc.cic
        entities = wc.entities
        cloudsx = wc.cloudsx
        cloudsy = wc.cloudsy
        cloudsv = wc.cloudsv
        cloudsn = wc.cloudsn
        machinesx = wc.machinesx
        machinesy = wc.machinesy
        lsources = wc.lsources
        lqx = wc.lqx
        lqy = wc.lqy
        lqd = wc.lqd
        rgnc1 = wc.rgnc1
        rgnc2 = wc.rgnc2
        layer = wc.layer
        layerTemp = wc.layerTemp
        blockTemp = wc.blockTemp
        mx = wc.mx
        my = wc.my
        icx = wc.icx
        icy = wc.icy
        mining = wc.mining
        immune = wc.immune
        moveItem = wc.moveItem
        moveNum = wc.moveNum
        moveItemTemp = wc.moveItemTemp
        moveNumTemp = wc.moveNumTemp
        msi = wc.msi
        toolAngle = wc.toolAngle
        toolSpeed = wc.toolSpeed
        timeOfDay = wc.timeOfDay
        currentSkyLight = wc.currentSkyLight
        day = wc.day
        mobCount = wc.mobCount
        ready = wc.ready
        showTool = wc.showTool
        showInv = wc.showInv
        doMobSpawn = wc.doMobSpawn
        WIDTH = wc.WIDTH
        HEIGHT = wc.HEIGHT
        random = wc.random
        WORLDWIDTH = wc.WORLDWIDTH
        WORLDHEIGHT = wc.WORLDHEIGHT
        resunlight = wc.resunlight
        kworlds = wc.kworlds
        ic = wc.ic
        icmatrix = wc.icmatrix
        version = wc.version
        player.reloadImage()
        inventory.reloadImage()
        if (cic != null) {
            inventory.renderCollection(cic)
        }
        else {
            val tlist1: Array[Short] = Array[Short](0, 0, 0, 0, 0)
            val tlist2: Array[Short] = Array[Short](0, 0, 0, 0, 0)
            val tlist3: Array[Short] = Array[Short](0, 0, 0, 0, 0)
            cic = new ItemCollection("cic", tlist1, tlist2, tlist3)
            inventory.renderCollection(cic)
        }
        if (ic != null) {
            inventory.renderCollection(ic)
        }
        (0 until entities.size()).foreach { i =>
            entities.get(i).reloadImage()
        }
        worlds = Array.ofDim(WORLDHEIGHT, WORLDWIDTH)
        fworlds = Array.ofDim(WORLDHEIGHT, WORLDWIDTH)
    }

    def resetDrawn(): Unit =  {
        drawn = Array.ofDim(HEIGHT, WIDTH)
        (0 until HEIGHT).foreach { y =>
            (0 until WIDTH).foreach { x =>
                drawn(y)(x) = false
            }
        }
        ldrawn = Array.ofDim(HEIGHT,WIDTH)
        (0 until HEIGHT).foreach { y =>
            (0 until WIDTH).foreach { x =>
                ldrawn(y)(x) = false
            }
        }
        rdrawn = Array.ofDim(HEIGHT, WIDTH)
        (0 until HEIGHT).foreach { y =>
            (0 until WIDTH).foreach { x =>
                rdrawn(y)(x) = false
            }
        }
    }

    def  createWorldContainer(): WorldContainer = {
        return (new WorldContainer(blocks, blockds, blockdns, blockbgs, blockts,
                                  lights, power, drawn, ldrawn, rdrawn,
                                  player, inventory, cic,
                                  entities, cloudsx, cloudsy, cloudsv, cloudsn,
                                  machinesx, machinesy, lsources, lqx, lqy, lqd,
                                  rgnc1, rgnc2, layer, layerTemp, blockTemp,
                                  mx, my, icx, icy, mining, immune,
                                  moveItem, moveNum, moveItemTemp, moveNumTemp, msi,
                                  toolAngle, toolSpeed, timeOfDay, currentSkyLight, day, mobCount,
                                  ready, showTool, showInv, doMobSpawn,
                                  WIDTH, HEIGHT, random, WORLDWIDTH, WORLDHEIGHT,
                                  resunlight,
                                  ic, kworlds, icmatrix, version))
    }

    def loadBlock(`type`: Int, dir: Byte, dirn: Byte, tnum: Byte,
        blocknames: Array[String], dirs: Array[String], outlineName: String, x: Int, y: Int, lyr: Int): BufferedImage = {
        val dir_is: Int = dir.toInt
        val dir_s: String = dirs(dir_is)
        val dir_i: Int = dirn.toInt
        val bName: String = blocknames(`type`)
        val image: BufferedImage = config.createCompatibleImage(IMAGESIZE, IMAGESIZE, Transparency.TRANSLUCENT)
        var maybeTexture: Option[BufferedImage] = blockImgs.get("blocks/" + bName + "/texture" + (tnum + 1) + ".png")
        GRASSDIRT.get(`type`).foreach { grassdirt =>
            val maybeDirtOriginal: Option[BufferedImage] = blockImgs.get("blocks/" + blocknames(grassdirt) + "/texture" + (tnum+1) + ".png")
            val dirt: BufferedImage = config.createCompatibleImage(IMAGESIZE, IMAGESIZE, Transparency.TRANSLUCENT)
            (0 until IMAGESIZE).foreach { dy =>
                (0 until IMAGESIZE).foreach { dx =>
                    maybeDirtOriginal.foreach { dirtOriginal =>
                        dirt.setRGB(dx, dy, dirtOriginal.getRGB(dx, dy))
                    }
                }
            }
            //val dn: Int = GRASSDIRT.get(`type`)
            val left: Boolean = (blocks(lyr)(y)(x-1) == 0 || !blockcds(blocks(lyr)(y)(x-1)))// && (blocks(lyr)(y-1)(x) != dn && blocks(lyr)(y+1)(x) != dn) && (blocks(lyr)(y-1)(x-1) != dn && blocks(lyr)(y+1)(x-1) != dn)
            val right: Boolean = (blocks(lyr)(y)(x+1) == 0 || !blockcds(blocks(lyr)(y)(x+1)))// && (blocks(lyr)(y-1)(x) != dn && blocks(lyr)(y+1)(x) != dn) && (blocks(lyr)(y-1)(x+1) != dn && blocks(lyr)(y+1)(x+1) != dn)
            val up: Boolean = (blocks(lyr)(y-1)(x) == 0 || !blockcds(blocks(lyr)(y-1)(x)))// && (blocks(lyr)(y)(x-1) != dn && blocks(lyr)(y)(x+1) != dn) && (blocks(lyr)(y-1)(x-1) != dn && blocks(lyr)(y-1)(x+1) != dn)
            val down: Boolean = (blocks(lyr)(y+1)(x) == 0 || !blockcds(blocks(lyr)(y+1)(x)))// && (blocks(lyr)(y)(x-1) != dn && blocks(lyr)(y)(x+1) != dn) && (blocks(lyr)(y+1)(x-1) != dn && blocks(lyr)(y+1)(x+1) != dn)
            val upleft: Boolean = (blocks(lyr)(y-1)(x-1) == 0 || !blockcds(blocks(lyr)(y-1)(x-1)))// && (blocks(lyr)(y-1)(x) != dn && blocks(lyr)(y)(x-1) != dn && blocks(lyr)(y-1)(x-1) != dn && blocks(lyr)(y-2)(x) != dn && blocks(lyr)(y)(x-2) != dn)
            val upright: Boolean = (blocks(lyr)(y-1)(x+1) == 0 || !blockcds(blocks(lyr)(y-1)(x+1)))// && (blocks(lyr)(y-1)(x) != dn && blocks(lyr)(y)(x+1) != dn && blocks(lyr)(y-1)(x+1) != dn && blocks(lyr)(y-2)(x) != dn && blocks(lyr)(y)(x+2) != dn)
            val downleft: Boolean = (blocks(lyr)(y+1)(x-1) == 0 || !blockcds(blocks(lyr)(y+1)(x-1)))// && (blocks(lyr)(y+1)(x) != dn && blocks(lyr)(y)(x-1) != dn && blocks(lyr)(y+1)(x-1) != dn && blocks(lyr)(y+2)(x) != dn && blocks(lyr)(y)(x-2) != dn)
            val downright: Boolean = (blocks(lyr)(y+1)(x+1) == 0 || !blockcds(blocks(lyr)(y+1)(x+1)))// && (blocks(lyr)(y+1)(x) != dn && blocks(lyr)(y)(x+1) != dn && blocks(lyr)(y+1)(x+1) != dn && blocks(lyr)(y+2)(x) != dn && blocks(lyr)(y)(x+2) != dn)
            val pixm: Array2D[Int] = Array.ofDim(IMAGESIZE, IMAGESIZE)
            (0 until 8).foreach { dy =>
                (0 until 8).foreach { dx =>
                    pixm(dy)(dx) = 0
                }
            }
            if (left) {
                pixm(3)(0) = 255
                pixm(4)(0) = 255
            }
            if (right) {
                pixm(3)(7) = 255
                pixm(4)(7) = 255
            }
            if (up) {
                pixm(0)(3) = 255
                pixm(0)(4) = 255
            }
            if (down) {
                pixm(7)(3) = 255
                pixm(7)(4) = 255
            }
            if (upleft) {
                pixm(0)(0) = 255
            }
            if (upright) {
                pixm(0)(7) = 255
            }
            if (downleft) {
                pixm(7)(0) = 255
            }
            if (downright) {
                pixm(7)(7) = 255
            }
            if (left && up) {
                (0 until 4).foreach { dy =>
                    pixm(dy)(0) = 255
                }
                (0 until 4).foreach { dx =>
                    pixm(0)(dx) = 255
                }
            }
            if (up && right) {
                (4 until 8).foreach { dx =>
                    pixm(0)(dx) = 255
                }
                (0 until 4).foreach { dy =>
                    pixm(dy)(7) = 255
                }
            }
            if (right && down) {
                (4 until 8).foreach { dy =>
                    pixm(dy)(7) = 255
                }
                (4 until 8).foreach { dx =>
                    pixm(7)(dx) = 255
                }
            }
            if (down && left) {
                (0 until 4).foreach { dx =>
                    pixm(7)(dx) = 255
                }
                (4 until 8).foreach { dy =>
                    pixm(dy)(0) = 255
                }
            }
            (0 until 8).foreach { dy =>
                (0 until 8).foreach { dx =>
                    if (pixm(dy)(dx) == 255) {
                        (0 until 8).foreach { dy2 =>
                            (0 until 8).foreach { dx2 =>
                                n = (255 - 32*Math.sqrt(Math.pow(dx-dx2, 2) + Math.pow(dy-dy2, 2))).toInt
                                if (pixm(dy2)(dx2) < n) {
                                    pixm(dy2)(dx2) = n
                                }
                            }
                        }
                    }
                }
            }
            (0 until 8).foreach { dy =>
                (0 until 8).foreach { dx =>
                    maybeTexture.foreach { texture =>
                        dirt.setRGB(dx, dy, new Color((pixm(dy)(dx) / 255.0 * new Color(texture.getRGB(dx, dy)).getRed() + (1 - pixm(dy)(dx) / 255.0) * new Color(dirt.getRGB(dx, dy)).getRed()).toInt,
                            (pixm(dy)(dx) / 255.0 * new Color(texture.getRGB(dx, dy)).getGreen() + (1 - pixm(dy)(dx) / 255.0) * new Color(dirt.getRGB(dx, dy)).getGreen()).toInt,
                            (pixm(dy)(dx) / 255.0 * new Color(texture.getRGB(dx, dy)).getBlue() + (1 - pixm(dy)(dx) / 255.0) * new Color(dirt.getRGB(dx, dy)).getBlue()).toInt).getRGB())
                    }
                }
            }
            maybeTexture = Some(dirt)
        }


        outlineImgs.get("outlines/" + outlineName + "/" + dir_s + (dir_i+1) + ".png").foreach { outline =>
            (0 until IMAGESIZE).foreach { fy =>
                (0 until IMAGESIZE).foreach { fx =>
                    if (outline.getRGB(fx, fy) == -65281 || outline.getRGB(fx, fy) == -48897) {
                        maybeTexture.foreach{ texture =>
                            image.setRGB(fx, fy, texture.getRGB(fx, fy))
                        }
                    }
                    else if (outline.getRGB(fx, fy) == -16777216) {
                        image.setRGB(fx, fy, -16777216)
                    }
                }
            }
        }
        return image
    }

    def keyPressed(key: KeyEvent): Unit = {
        if (key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A) {
            queue(0) = true
        }
        if (key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D) {
            queue(1) = true
        }
        if (key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W) {
            queue(2) = true
        }
        if (key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S) {
            queue(6) = true
        }
        if (key.getKeyCode() == KeyEvent.VK_SHIFT) {
            queue(5) = true
        }
        if (state.equals("ingame")) {
            if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
                if (ic != null) {
                    if (!ic.`type`.equals("workbench")) {
                        machinesx.add(icx)
                        machinesy.add(icy)
                        icmatrix(iclayer)(icy)(icx) = new ItemCollection(ic.`type`, ic.ids, ic.nums, ic.durs)
                    }
                    if (ic.`type`.equals("workbench")) {
                        if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                            (0 until 9).foreach { i =>
                                if (ic.ids(i) != 0) {
                                    entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, 2, -2, ic.ids(i), ic.nums(i), ic.durs(i), 75))
                                }
                            }
                        }
                        if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                            (0 until 9).foreach { i =>
                                if (ic.ids(i) != 0) {
                                    entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, -2, -2, ic.ids(i), ic.nums(i), ic.durs(i), 75))
                                }
                            }
                        }
                    }
                    if (ic.`type`.equals("furnace")) {
                        icmatrix(iclayer)(icy)(icx).FUELP = ic.FUELP
                        icmatrix(iclayer)(icy)(icx).SMELTP = ic.SMELTP
                        icmatrix(iclayer)(icy)(icx).F_ON = ic.F_ON
                    }
                    ic = null
                }
                else {
                    if (showInv) {
                        (0 until 4).foreach { i =>
                            if (cic.ids(i) != 0) {
                                if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                                    entities.add(new Entity(player.x, player.y, 2, -2, cic.ids(i), cic.nums(i), cic.durs(i), 75))
                                }
                                if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                                    entities.add(new Entity(player.x, player.y, -2, -2, cic.ids(i), cic.nums(i), cic.durs(i), 75))
                                }
                                inventory.removeLocationIC(cic, i, cic.nums(i))
                            }
                        }
                    }
                    showInv = !showInv
                }
                if (moveItem != 0) {
                    if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                        entities.add(new Entity(player.x, player.y, 2, -2, moveItem, moveNum, moveDur, 75))
                    }
                    if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                        entities.add(new Entity(player.x, player.y, -2, -2, moveItem, moveNum, moveDur, 75))
                    }
                    moveItem = 0
                    moveNum = 0
                }
            }
            if (!showTool) {
                if (key.getKeyCode() == KeyEvent.VK_1) {
                    inventory.select(1)
                }
                if (key.getKeyCode() == KeyEvent.VK_2) {
                    inventory.select(2)
                }
                if (key.getKeyCode() == KeyEvent.VK_3) {
                    inventory.select(3)
                }
                if (key.getKeyCode() == KeyEvent.VK_4) {
                    inventory.select(4)
                }
                if (key.getKeyCode() == KeyEvent.VK_5) {
                    inventory.select(5)
                }
                if (key.getKeyCode() == KeyEvent.VK_6) {
                    inventory.select(6)
                }
                if (key.getKeyCode() == KeyEvent.VK_7) {
                    inventory.select(7)
                }
                if (key.getKeyCode() == KeyEvent.VK_8) {
                    inventory.select(8)
                }
                if (key.getKeyCode() == KeyEvent.VK_9) {
                    inventory.select(9)
                }
                if (key.getKeyCode() == KeyEvent.VK_0) {
                    inventory.select(0)
                }
            }
        }
        var c: Char = 0
        if (key.getKeyCode() == KeyEvent.VK_Q) c = 'q'
        if (key.getKeyCode() == KeyEvent.VK_W) c = 'w'
        if (key.getKeyCode() == KeyEvent.VK_E) c = 'e'
        if (key.getKeyCode() == KeyEvent.VK_R) c = 'r'
        if (key.getKeyCode() == KeyEvent.VK_T) c = 't'
        if (key.getKeyCode() == KeyEvent.VK_Y) c = 'y'
        if (key.getKeyCode() == KeyEvent.VK_U) c = 'u'
        if (key.getKeyCode() == KeyEvent.VK_I) c = 'i'
        if (key.getKeyCode() == KeyEvent.VK_O) c = 'o'
        if (key.getKeyCode() == KeyEvent.VK_P) c = 'p'
        if (key.getKeyCode() == KeyEvent.VK_A) c = 'a'
        if (key.getKeyCode() == KeyEvent.VK_S) c = 's'
        if (key.getKeyCode() == KeyEvent.VK_D) c = 'd'
        if (key.getKeyCode() == KeyEvent.VK_F) c = 'f'
        if (key.getKeyCode() == KeyEvent.VK_G) c = 'g'
        if (key.getKeyCode() == KeyEvent.VK_H) c = 'h'
        if (key.getKeyCode() == KeyEvent.VK_J) c = 'j'
        if (key.getKeyCode() == KeyEvent.VK_K) c = 'k'
        if (key.getKeyCode() == KeyEvent.VK_L) c = 'l'
        if (key.getKeyCode() == KeyEvent.VK_Z) c = 'z'
        if (key.getKeyCode() == KeyEvent.VK_X) c = 'x'
        if (key.getKeyCode() == KeyEvent.VK_C) c = 'c'
        if (key.getKeyCode() == KeyEvent.VK_V) c = 'v'
        if (key.getKeyCode() == KeyEvent.VK_B) c = 'b'
        if (key.getKeyCode() == KeyEvent.VK_N) c = 'n'
        if (key.getKeyCode() == KeyEvent.VK_M) c = 'm'
        if (key.getKeyCode() == KeyEvent.VK_1) c = '1'
        if (key.getKeyCode() == KeyEvent.VK_2) c = '2'
        if (key.getKeyCode() == KeyEvent.VK_3) c = '3'
        if (key.getKeyCode() == KeyEvent.VK_4) c = '4'
        if (key.getKeyCode() == KeyEvent.VK_5) c = '5'
        if (key.getKeyCode() == KeyEvent.VK_6) c = '6'
        if (key.getKeyCode() == KeyEvent.VK_7) c = '7'
        if (key.getKeyCode() == KeyEvent.VK_8) c = '8'
        if (key.getKeyCode() == KeyEvent.VK_9) c = '9'
        if (key.getKeyCode() == KeyEvent.VK_0) c = '0'
        if (key.getKeyCode() == KeyEvent.VK_SPACE) c = ' '
        if (key.getKeyCode() == 192) c = '`'
        if (key.getKeyCode() == KeyEvent.VK_MINUS) c = '-'
        if (key.getKeyCode() == KeyEvent.VK_EQUALS) c = '='
        if (key.getKeyCode() == KeyEvent.VK_OPEN_BRACKET) c = '['
        if (key.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET) c = ']'
        if (key.getKeyCode() == KeyEvent.VK_BACK_SLASH) c = '\\'
        if (key.getKeyCode() == KeyEvent.VK_COLON) c = ':'
        if (key.getKeyCode() == KeyEvent.VK_QUOTE) c = '\''
        if (key.getKeyCode() == KeyEvent.VK_COMMA) c = ','
        if (key.getKeyCode() == KeyEvent.VK_PERIOD) c = '.'
        if (key.getKeyCode() == KeyEvent.VK_SLASH) c = '/'

        if (queue(5)) {
            if (c == 'q') c = 'Q'
            if (c == 'w') c = 'W'
            if (c == 'e') c = 'E'
            if (c == 'r') c = 'R'
            if (c == 't') c = 'T'
            if (c == 'y') c = 'Y'
            if (c == 'u') c = 'U'
            if (c == 'i') c = 'I'
            if (c == 'o') c = 'O'
            if (c == 'p') c = 'P'
            if (c == 'a') c = 'A'
            if (c == 's') c = 'S'
            if (c == 'd') c = 'D'
            if (c == 'f') c = 'F'
            if (c == 'g') c = 'G'
            if (c == 'h') c = 'H'
            if (c == 'j') c = 'J'
            if (c == 'k') c = 'K'
            if (c == 'l') c = 'L'
            if (c == 'z') c = 'Z'
            if (c == 'x') c = 'X'
            if (c == 'c') c = 'C'
            if (c == 'v') c = 'V'
            if (c == 'b') c = 'B'
            if (c == 'n') c = 'N'
            if (c == 'm') c = 'M'
            if (c == '1') c = '!'
            if (c == '2') c = '@'
            if (c == '3') c = '#'
            if (c == '4') c = '$'
            if (c == '5') c = '%'
            if (c == '6') c = '^'
            if (c == '7') c = '&'
            if (c == '8') c = '*'
            if (c == '9') c = '('
            if (c == '0') c = ')'
            if (c == ' ') c = ' '
            if (c == '`') c = '~'
            if (c == '-') c = '_'
            if (c == '=') c = '+'
            if (c == '[') c = '{'
            if (c == ']') c = '}'
            if (c == '\\') c = '|'
            if (c == ';') c = ')'
            if (c == '\'') c = '"'
            if (c == ',') c = '<'
            if (c == '.') c = '>'
            if (c == '/') c = '?'
        }

        if (state.equals("new_world") && !newWorldFocus) {
            if (c != 0) {
                newWorldName.typeKey(c)
                repaint()
            }

            if (key.getKeyCode() == 8) {
                newWorldName.deleteKey()
                repaint()
            }
        }

        if (key.getKeyCode() == KeyEvent.VK_EQUALS && layer < 2) {
            layer += 1
        }
        if (key.getKeyCode() == KeyEvent.VK_MINUS && layer > 0) {
            layer -= 1
        }
    }

    def keyReleased(key: KeyEvent): Unit = {
        if (key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A) {
            queue(0) = false
        }
        if (key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D) {
            queue(1) = false
        }
        if (key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W) {
            queue(2) = false
        }
        if (key.getKeyCode() == KeyEvent.VK_SHIFT) {
            queue(5) = false
        }
        if (key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S) {
            queue(6) = false
        }
    }

    def mousePressed(e: MouseEvent): Unit = {
        if (!queue(3)) {
            queue(3) = e.getButton() == MouseEvent.BUTTON1
        }
        if (!queue(4)) {
            queue(4) = e.getButton() == MouseEvent.BUTTON3
        }
    }

    def mouseReleased(e: MouseEvent): Unit = {
        queue(3) = false
        queue(4) = false
        menuPressed = false
    }

    def mouseClicked(e: MouseEvent): Unit = {
        //
    }

    def mouseMoved(e: MouseEvent): Unit = {
        if (mousePos != null) {
            mousePos(0) = e.getX()
            mousePos(1) = e.getY()
        }
    }

    def mouseDragged(e: MouseEvent): Unit = {
        mousePos(0) = e.getX()
        mousePos(1) = e.getY()
    }

    override def getPreferredSize(): Dimension = {
        return new Dimension(800, 600)
    }

    

    def keyTyped(key: KeyEvent): Unit = {
        //
    }

    def stateChanged(e: ChangeEvent): Unit = {
        //
    }

    def mouseEntered(e: MouseEvent): Unit = {
        //
    }

    def mouseExited(e: MouseEvent): Unit = {
        //
    }

    def mouseWheelMoved(e: MouseWheelEvent): Unit = {
        //
    }

    def update(): Unit = {
        //
    }

/*    def pmsg(msg: String): Unit = {
        pg2 = (Graphics2D) screen.createGraphics()
        if (logo_white != null && msg != null && loadTextPos != 0) {
            pg2.drawImage(logo_white,
                getWidth()/2-logo_white.getWidth()/2, 50, getWidth()/2+logo_white.getWidth()/2, logo_white.getHeight()+50,
                0, 0, logo_white.getWidth(), logo_white.getHeight(),
                null)
            pg2.setFont(loadFont)
            pg2.setColor(Color.GREEN)
            pg2.drawString(msg, getWidth()/2-200, 100+loadTextPos*13)
            if (msg.equals("Created by Radon Rosborough.")) {
                loadTextPos += 2
            }
            else {
                loadTextPos += 1
            }
        }
        else {
            pg2.clearRect(0, 0, getWidth(), getHeight())
            loadTextPos = 1
        }
    }
*/
    
}
