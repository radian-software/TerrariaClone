'''

CREATED BY RADON (RAXOD502) AND MAINTAINED BY RADIAN LLC

-=-=-

FUTURE CHANGELOG

--> LIGHTING
--> GRAPHICS
* Dynamic block edges
--> HEALTH
* Fall damage
* Death
--> MOBS
* Weapons
* Mobs take damage
* Swing swords
* Zombie
* Mob spawning
--> ENVIRONMENT UPDATES
* Sand gravity
* Grass growth
--> FARMING
* Acorns
--> WEATHER
* Day/night cycle
* Rain
--> GAME FRAMEWORK
* Title screen
* Game saving
--> BACKGROUND FIX
* Clouds
* Sun/moon
* Underground texture
--> LIQUIDS
--> MORE BLOCKS/ITEMS
* Herbs
* Ores
* Tools
* Walls
* Trees
--> GENERATED STRUCTURES
* Villages
--> INTERFACE
* Hearts
* Scrollover item labels
--> MIDGROUND TILES
* Crafting tables
* (that actually work)
--> BUG FIXES
* Random stone in abandoned mine shafts?

'''

import pygame
from math import sqrt, floor, ceil, sin
from random import choice, random, randint, sample
from copy import deepcopy, copy
from time import sleep, time

LENGTH, HEIGHT = 500, 500
DEBUG = False
FASTGEN = False

BLOCKIMAGES = {'Dirt':'dirt.bmp',
               'Grass':'grass.bmp',
               'Jungle Grass':'jungle_grass.bmp',
               'Stone':'stone.bmp',
               'Mud':'mud.bmp',
               'Sand':'sand.bmp',
               'Sandstone':'sandstone.bmp',
               'Rose':'rose.bmp',
               'Bluebell':'bluebell.bmp',
               'Dandelion':'dandelion.bmp',
               'Cactus':'cactus.bmp',
               'Copper Ore Block':'copper_ore_block.bmp',
               'Iron Ore Block':'iron_ore_block.bmp',
               'Gold Ore Block':'gold_ore_block.bmp',
               'Diamond Ore Block':'diamond_ore_block.bmp',
               'Copper Ore':'copper_ore.bmp',
               'Iron Ore':'iron_ore.bmp',
               'Gold Ore':'gold_ore.bmp',
               'Diamond':'diamond.bmp',
               'Wood':'wood.bmp',
               'Wood Wall':'wood_wall.bmp',
               'Stone Brick':'stone_brick.bmp',
               'Torch':'torch.bmp',
               'Gel':'gel.bmp',
               'Red Dye':'red_dye.bmp',
               'Orange Dye':'orange_dye.bmp',
               'Yellow Dye':'yellow_dye.bmp',
               'Green Dye':'green_dye.bmp',
               'Dark Green Dye':'dark_green_dye.bmp',
               'Blue Dye':'blue_dye.bmp',
               'Cyan Dye':'cyan_dye.bmp',
               'Purple Dye':'purple_dye.bmp',
               'Pink Dye':'pink_dye.bmp',
               'Tree Block':'tree_block.bmp',
               'Edge':'edge.bmp',
               'Copper Pick':'copper_pick.bmp',
               'Iron Pick':'iron_pick.bmp',
               'Gold Pick':'gold_pick.bmp',
               'Copper Axe':'copper_axe.bmp',
               'Iron Axe':'iron_axe.bmp',
               'Gold Axe':'gold_axe.bmp',
               'Copper Hammer':'copper_hammer.bmp',
               'Iron Hammer':'iron_hammer.bmp',
               'Gold Hammer':'gold_hammer.bmp',
               'Copper Sword':'copper_sword.bmp',
               'Iron Sword':'iron_sword.bmp',
               'Gold Sword':'gold_sword.bmp',
               'bg1':'bg1.bmp',
               'bg2':'bg2.bmp',
               'Debug Wand':'edge_pick.bmp',
               None:None}
BLOCKSTACKS = {'Dirt':100,
               'Grass':100,
               'Jungle Grass':100,
               'Stone':100,
               'Mud':100,
               'Sand':100,
               'Sandstone':100,
               'Cactus':100,
               'Rose':100,
               'Bluebell':100,
               'Dandelion':100,
               'Red Dye':100,
               'Orange Dye':100,
               'Yellow Dye':100,
               'Green Dye':100,
               'Dark Green Dye':100,
               'Blue Dye':100,
               'Cyan Dye':100,
               'Purple Dye':100,
               'Magenta Dye':100,
               'Pink Dye':100,
               'Copper Ore Block':100,
               'Iron Ore Block':100,
               'Gold Ore Block':100,
               'Diamond Ore Block':100,
               'Copper Ore':100,
               'Iron Ore':100,
               'Gold Ore':100,
               'Diamond':100,
               'Diamond Ore':100,
               'Wood':100,
               'Wood Wall':100,
               'Stone Brick':100,
               'Torch':100,
               'Gel':100,
               'Edge':100,
               'Tree Block':100,
               'Copper Pick':1,
               'Iron Pick':1,
               'Gold Pick':1,
               'Copper Axe':1,
               'Iron Axe':1,
               'Gold Axe':1,
               'Copper Hammer':1,
               'Iron Hammer':1,
               'Gold Hammer':1,
               'Copper Sword':1,
               'Iron Sword':1,
               'Gold Sword':1}
BLOCKDROPS = {'Dirt':'Dirt',
              'Grass':'Dirt',
              'Jungle Grass':'Dirt',
              'Stone':'Stone',
              'Mud':'Mud',
              'Sand':'Sand',
              'Sandstone':'Sandstone',
              'Rose':'Rose',
              'Bluebell':'Bluebell',
              'Dandelion':'Dandelion',
              'Cactus':'Cactus',
              'Copper Ore Block':'Copper Ore',
              'Iron Ore Block':'Iron Ore',
              'Gold Ore Block':'Gold Ore',
              'Diamond Ore Block':'Diamond',
              'Wood':'Wood',
              'Wood Wall':'Wood Wall',
              'Stone Brick':'Stone Brick',
              'Torch':'Torch',
              'Edge':'Edge',
              'Tree Block':'Wood',
              None:None}
BLOCKSTRENGTHS = {'Dirt':6,
                  'Grass':6,
                  'Jungle Grass':6,
                  'Stone':12,
                  'Mud':6,
                  'Sand':6,
                  'Sandstone':12,
                  'Cactus':6,
                  'Rose':0,
                  'Bluebell':0,
                  'Dandelion':0,
                  'Copper Ore Block':12,
                  'Iron Ore Block':12,
                  'Gold Ore Block':24,
                  'Diamond Ore Block':48,
                  'Wood':10,
                  'Wood Wall':6,
                  'Stone Brick':12,
                  'Torch':0,
                  'Edge':0,
                  'Tree Block':15,
                  None:0}
HIERARCHY = {'Copper Pick':['Gold Pick', 'Iron Pick', 'Copper Pick'],
             'Iron Pick':['Gold Pick', 'Iron Pick'],
             'Gold Pick':['Gold Pick'],
             'Copper Axe':['Gold Axe', 'Iron Axe', 'Copper Axe'],
             'Iron Axe':['Gold Axe', 'Iron Axe'],
             'Gold Axe':['Gold Axe'],
             'Copper Hammer':['Gold Hammer', 'Iron Hammer', 'Copper Hammer'],
             'All':['Gold Pick', 'Iron Pick', 'Copper Pick', 'Gold Axe', 'Iron Axe', 'Copper Axe', 'Gold Hammer', 'Iron Hammer', 'Copper Hammer', 'Gold Sword', 'Iron Sword', 'Copper Sword'],
             'None':[],
             None:None}
BLOCKPICKS = {'Dirt':'Copper Pick',
              'Grass':'Copper Pick',
              'Jungle Grass':'Copper Pick',
              'Stone':'Copper Pick',
              'Mud':'Copper Pick',
              'Sand':'Copper Pick',
              'Sandstone':'Copper Pick',
              'Cactus':'Copper Axe',
              'Rose':'All',
              'Bluebell':'All',
              'Dandelion':'All',
              'Copper Ore Block':'Copper Pick',
              'Iron Ore Block':'Copper Pick',
              'Gold Ore Block':'Iron Pick',
              'Diamond Ore Block':'Gold Pick',
              'Wood':'Copper Axe',
              'Wood Wall':'Copper Hammer',
              'Stone Brick':'Copper Pick',
              'Torch':'All',
              'Edge':'None',
              'Tree Block':'Copper Axe',
              None:None}
BACKGROUND = ['Cactus', 'Rose', 'Bluebell', 'Dandelion', 'Wood Wall', 'Torch', 'Tree Block']
CASCADEUP = ['Tree Block', 'Cactus', 'Rose', 'Bluebell', 'Dandelion', 'Torch']
PLACEREQ = {'Tree Block':['Grass', 'Jungle Grass'],
            'Cactus':['Sand'],
            'Rose':['Grass', 'Jungle Grass'],
            'Bluebell':['Grass', 'Jungle Grass'],
            'Dandelion':['Grass', 'Jungle Grass'],
            'Torch':['Dirt', 'Grass', 'Jungle Grass', 'Stone', 'Mud', 'Sand', 'Sandstone', 'Copper Ore Block', 'Iron Ore Block', 'Gold Ore Block', 'Diamond Ore Block', 'Wood']}
for block in BLOCKPICKS:
    BLOCKPICKS[block] = HIERARCHY[BLOCKPICKS[block]]
PFALSE = ['Red Dye', 'Orange Dye', 'Yellow Dye', 'Green Dye', 'Dark Green Dye', 'Blue Dye', 'Cyan Dye', 'Purple Dye', 'Magenta Dye', 'Pink Dye', 'Gel', 'Copper Pick', 'Iron Pick', 'Gold Pick', 'Copper Axe', 'Iron Axe', 'Gold Axe', 'Copper Hammer', 'Iron Hammer', 'Gold Hammer', 'Copper Sword', 'Iron Sword', 'Gold Sword', 'Copper Ore', 'Iron Ore', 'Gold Ore', 'Diamond', 'Debug Wand']
PTRUE = ['Dirt', 'Grass', 'Jungle Grass', 'Stone', 'Mud', 'Sand', 'Sandstone', 'Cactus', 'Rose', 'Bluebell', 'Dandelion', 'Copper Ore Block', 'Iron Ore Block', 'Gold Ore Block', 'Diamond Ore Block', 'Wood', 'Wood Wall', 'Stone Brick', 'Torch', 'Edge', 'Tree Block']
PLACEABLE = {}
for item in PFALSE:
    PLACEABLE[item] = False
for item in PTRUE:
    PLACEABLE[item] = True

if not DEBUG:
    INVENTORY = [('Copper Pick',1), ('Copper Axe',1)]
else:
    INVENTORY = [('Debug Wand',1)]

class Game():
    def __init__(self):
        self.initPygame()
        self.initImages()
        self.createObjects()
        self.runWorld()
    def initPygame(self):
        pygame.init()
        self.screen = pygame.display.set_mode((416, 416))
        pygame.display.set_caption('Box of Wonder')
    def initImages(self):
        for block in BLOCKIMAGES:
            if block:
                image = pygame.Surface((13, 13)).convert_alpha()
                loaded = pygame.image.load(BLOCKIMAGES[block])
                if loaded.get_width() == 13:
                    image.blit(loaded, (0, 0))
                else:
                    image.blit(pygame.transform.scale(loaded, (13, 13)).convert_alpha(), (0, 0))
                mkTrans(image)
                BLOCKIMAGES[block] = image
    def createObjects(self):
        self.HELVETICA = pygame.font.SysFont('Helvetica', 10)
        self.HELVETICAMEDIUM = pygame.font.SysFont('Helvetica', 20)
        self.HELVETICALARGE = pygame.font.SysFont('Helvetica', 30)
        self.blocklist = makeWorld(LENGTH=LENGTH, HEIGHT=HEIGHT, COMPLEX_T=20, COMPLEX_S=30, PARAMS=[5, 25], screen=self.screen, font=self.HELVETICAMEDIUM)
        self.blocks = []
        self.drawn = []
        self.drawnb = []
        for y in range(HEIGHT+1):
            row = []
            drow = []
            drowb = []
            for x in range(LENGTH+1):
                row.append(None)
                drow.append(False)
                drowb.append(False)
            self.blocks.append(row)
            self.drawn.append(drow)
            self.drawnb.append(drowb)
        for block in self.blocklist:
            self.blocks[int(block.rect.top/13)][int(block.rect.left/13)] = block
        del self.blocklist
        self.player = Player((50, 50), './player/player_r1.bmp')
        self.mobs = [Mob((250, 50), 'blue_slime.bmp', 'Blue Slime'),
                     Mob((500, 50), 'red_slime.bmp', 'Red Slime')]
#        self.mobs = []
        self.clock = pygame.time.Clock()
        self.sprites = []
        self.inventory = Inventory(self.HELVETICA)
        for item in INVENTORY:
            self.inventory.add(item[0], item[1])
        self.hotkey = 1
        self.inventory.createBar(self.hotkey)
        self.dispInv = False
        self.item = {ITEM:None, NUMBER:None}
        self.scrollup, self.scrolldown = pygame.image.load('scroll_bar_up.bmp').convert_alpha(), pygame.image.load('scroll_bar_down.bmp').convert_alpha()
        self.scrollrectup, self.scrollrectdown = (pygame.Rect(55, 135, self.scrollup.get_width(), self.scrollup.get_height()),
                                                  pygame.Rect(55, 400, self.scrolldown.get_width(), self.scrolldown.get_height()))
        mkTrans(self.scrollup); mkTrans(self.scrolldown)
        self.offset = 0
    def runWorld(self):
        self.createSurfaces()
        self.updateDisplay()
        while True:
            self.tickClock()
            self.setQueue()
            self.checkQuit()
            self.updateObjects()
            self.updateDisplay()
            self.popObjects()
    def createSurfaces(self):
        genMsg(self.screen, self.HELVETICAMEDIUM, 'Rendering world...')
        self.world = pygame.Surface((LENGTH * 13, HEIGHT * 13)).convert_alpha()
        self.world.fill((0, 0, 0, 0))
        genMsg(self.screen, self.HELVETICAMEDIUM, 'Rendering background...')
        self.background = pygame.Surface((LENGTH * 13, HEIGHT * 13)).convert_alpha()
        self.inventory.updateCrafts([])
    def tickClock(self):
        self.clock.tick(50)
    def setQueue(self):
        self.queue = pygame.event.get()
        self.keys = pygame.key.get_pressed()
    def checkQuit(self):
        for event in self.queue:
            if event.type == pygame.QUIT:
                pygame.quit()
                raise ImportError
    def updateObjects(self):
        for y in range(int(self.player.rect.top/13 - 20), int(self.player.rect.top/13 + 21)):
            for x in range(int(self.player.rect.left/13 - 20), int(self.player.rect.left/13 + 21)):
                if 0 <= x < LENGTH and 0 <= y < HEIGHT and self.blocks[y][x] and not self.drawn[y][x]:
                    self.world.blit(self.blocks[y][x].image, (self.blocks[y][x].rect.left, self.blocks[y][x].rect.top))
                    self.drawn[y][x] = True
        for y in range(int(self.player.rect.top/13 - 20), int(self.player.rect.top/13 + 21)):
            for x in range(int(self.player.rect.left/13 - 20), int(self.player.rect.left/13 + 21)):
                if 0 <= x < LENGTH and 0 <= y < HEIGHT and not self.drawnb[y][x]:
                    if y < 32:
                        self.background.blit(BLOCKIMAGES['bg1'].convert(), (x * 13, y * 13))
                    if y >= 32:
                        self.background.blit(BLOCKIMAGES['bg2'].convert(), (x * 13, y * 13))
                    self.drawnb[y][x] = True
        self.poplist_block = []
        self.poplist = []
        if pygame.mouse.get_pressed()[0]:
            for event in self.queue:
                if event.type == pygame.MOUSEBUTTONDOWN:
                    for i in range(len(self.inventory.hrects)):
                        if rectPoint(self.inventory.hrects[i], pygame.mouse.get_pos()):
                            if not self.item[ITEM]:
                                self.item = {ITEM:self.inventory.itemarray.hotbar[i][ITEM],
                                             NUMBER:self.inventory.itemarray.hotbar[i][NUMBER]}
                                self.inventory.drop(True, i, self.inventory.itemarray.hotbar[i][NUMBER])
                            else:
                                if not self.inventory.itemarray.hotbar[i][ITEM]:
                                    self.inventory.addAt(self.item[ITEM], True, i, self.item[NUMBER])
                                    self.item = {ITEM:None, NUMBER:None}
                                else:
                                    newitem = {ITEM:self.inventory.itemarray.hotbar[i][ITEM],
                                               NUMBER:self.inventory.itemarray.hotbar[i][NUMBER]}
                                    self.inventory.drop(True, i, self.inventory.itemarray.hotbar[i][NUMBER])
                                    self.inventory.addAt(self.item[ITEM], True, i, self.item[NUMBER])
                                    self.item = newitem
                    if self.dispInv:
                        for i in range(len(self.inventory.irects)):
                            if rectPoint(self.inventory.irects[i], pygame.mouse.get_pos()):
                                if not self.item[ITEM]:
                                    self.item = {ITEM:self.inventory.itemarray.inventory[i][ITEM],
                                                 NUMBER:self.inventory.itemarray.inventory[i][NUMBER]}
                                    self.inventory.drop(False, i, self.inventory.itemarray.inventory[i][NUMBER])
                                else:
                                    if not self.inventory.itemarray.inventory[i][ITEM]:
                                        self.inventory.addAt(self.item[ITEM], False, i, self.item[NUMBER])
                                        self.item = {ITEM:None, NUMBER:None}
                                    else:
                                        newitem = {ITEM:self.inventory.itemarray.inventory[i][ITEM],
                                                   NUMBER:self.inventory.itemarray.inventory[i][NUMBER]}
                                        self.inventory.drop(False, i, self.inventory.itemarray.inventory[i][NUMBER])
                                        self.inventory.addAt(self.item[ITEM], False, i, self.item[NUMBER])
                                        self.item = newitem
                        for i in range(len(self.inventory.craftrects)):
                            c = self.inventory.craftnums[i]
                            if rectPoint(self.inventory.craftrects[i], pygame.mouse.get_pos()):
                                do = True
                                locationsall = []
                                for k in range(len(self.inventory.recipes[c][RECIPE])):
                                    dropnum = self.inventory.recipes[c][RECIPE][k][NUMBER]
                                    total = 0
                                    locations = []
                                    try:
                                        for j in range(len(self.inventory.itemarray.hotbar)):
                                            if self.inventory.itemarray.hotbar[j][ITEM] == self.inventory.recipes[c][RECIPE][k][ITEM]:
                                                locations.append((True, j))
                                                total += self.inventory.itemarray.hotbar[j][NUMBER]
                                                if total >= dropnum:
                                                    raise ZeroDivisionError
                                        for j in range(len(self.inventory.itemarray.inventory)):
                                            if self.inventory.itemarray.inventory[j][ITEM] == self.inventory.recipes[c][RECIPE][k][ITEM]:
                                                locations.append((False, j))
                                                total += self.inventory.itemarray.inventory[j][NUMBER]
                                                if total >= dropnum:
                                                    raise ZeroDivisionError
                                    except ZeroDivisionError:
                                        locationsall.extend(locations)
                                        continue
                                    do = False
                                    break
                                if do:
                                    for k in range(len(self.inventory.recipes[c][RECIPE])):
                                        self.inventory.dropItem(self.inventory.recipes[c][RECIPE][k][ITEM], self.inventory.recipes[c][RECIPE][k][NUMBER])
                                    for k in range(len(self.inventory.recipes[c][RESULT])):
                                        self.inventory.add(self.inventory.recipes[c][RESULT][k][ITEM], self.inventory.recipes[c][RESULT][k][NUMBER])
                    poplist_mob = []
                    oldcoords = pygame.mouse.get_pos()
                    coords = [oldcoords[0] + self.player.rect.left - 208, oldcoords[1] + self.player.rect.top - 208]
                    for i in range(len(self.mobs)):
                        if rectPoint(self.mobs[i].rect, coords):
                            if closeEnough(self.player.rect, self.mobs[i].rect, 20) or DEBUG:
                                result = self.mobs[i].hit(self.inventory.itemarray.hotbar[hotbarAssign(self.hotkey)][ITEM])
                                if result:
                                    poplist_mob.append(self.mobs[i])
                                    for drop in result:
                                        self.sprites.append(drop)
                                else:
                                    if self.mobs[i].rect.center < self.player.rect.center:
                                        self.mobs[i].vx = -5
                                        self.mobs[i].vy = -3
                                    else:
                                        self.mobs[i].vx = 5
                                        self.mobs[i].vy = -3
                    for mob in poplist_mob:
                        self.mobs.pop(self.mobs.index(mob))
            if rectPoint(self.scrollrectup, pygame.mouse.get_pos()) and self.offset > 0:
                self.offset -= 2
                for i in range(len(self.inventory.craftrects)):
                    self.inventory.craftrects[i] = self.inventory.craftrects[i].move(0, 2)
            if rectPoint(self.scrollrectdown, pygame.mouse.get_pos()) and self.offset < self.inventory.crafting.get_height() - 248:
                self.offset += 2
                for i in range(len(self.inventory.craftrects)):
                    self.inventory.craftrects[i] = self.inventory.craftrects[i].move(0, -2)
            do = True
            if (rectPoint(pygame.Rect(5, 0, self.inventory.image.get_width(), self.inventory.image.get_height()), pygame.mouse.get_pos()) or
                (self.dispInv and (rectPoint(pygame.Rect(5, 35, self.inventory.image2.get_width(), self.inventory.image2.get_height()), pygame.mouse.get_pos()) or
                                   rectPoint(pygame.Rect(5, 35, self.inventory.crafting.get_width(), self.inventory.crafting.get_height()), pygame.mouse.get_pos())))):
                do = False
            if do:
                oldcoords = list(pygame.mouse.get_pos())
                coords = list(pygame.mouse.get_pos())
                coords[0] += self.player.rect.left - 208
                coords[1] += self.player.rect.top - 208
                block = self.blocks[int(coords[1]/13)][int(coords[0]/13)]
                if block and (closeEnough(self.player.rect, block.rect, 50) or DEBUG):
                    if (rectPoint(block.rect, coords) and block.name != 'Edge' and
                        (self.inventory.itemarray.hotbar[hotbarAssign(self.hotkey)][ITEM] in BLOCKPICKS[block.name] or (DEBUG and self.inventory.itemarray.hotbar[hotbarAssign(self.hotkey)][ITEM] == 'Debug Wand'))):
                        miningblock = block
                        while True:
                            result = miningblock.mine('bg.bmp', self.inventory.itemarray.hotbar[hotbarAssign(self.hotkey)][ITEM])
                            if result:
                                self.sprites.append(result)
                                for col in range(13):
                                    for row in range(13):
                                        self.world.set_at((miningblock.rect.left + col, miningblock.rect.top + row), (0, 0, 0, 0))
                                self.poplist_block.append(miningblock)
                            redo = False
                            block2 = self.blocks[int(miningblock.rect.top/13) - 1][int(miningblock.rect.left/13)]
                            if block2 and miningblock.rect.top == block2.rect.bottom and miningblock.rect.left == block2.rect.left and block2.name in CASCADEUP:
                                redo = True
                                miningblock = block2
                            if not redo:
                                break
                name = self.inventory.itemarray.hotbar[hotbarAssign(self.hotkey)][ITEM]
                if name and not self.blocks[floor(coords[1] / 13)][floor(coords[0] / 13)]:
                    newblock = Block((floor(coords[0] / 13) * 13, (floor(coords[1] / 13) * 13)), BLOCKIMAGES[name], name)
                    if PLACEABLE[name] and (closeEnough(self.player.rect, newblock.rect, 50) or DEBUG):
                        if (next_to_block(newblock, self.blocks) and (not pygame.sprite.collide_rect(self.player, newblock) or name in BACKGROUND) and
                            (newblock.name not in PLACEREQ or (self.blocks[int(newblock.rect.top/13 + 1)][int(newblock.rect.left/13)] and
                                                               self.blocks[int(newblock.rect.top/13 + 1)][int(newblock.rect.left/13)].name in PLACEREQ[newblock.name]))):
                            do = True
                            for mob in self.mobs:
                                if pygame.sprite.collide_rect(mob, newblock):
                                    do = False
                                    break
                            if do:
                                self.blocks[int(newblock.rect.top/13)][int(newblock.rect.left/13)] = newblock
                                self.world.blit(newblock.image, (newblock.rect.left, newblock.rect.top))
                                self.inventory.drop(True, hotbarAssign(self.hotkey), 1)
        for block in self.poplist_block:
            self.blocks[int(block.rect.top/13)][int(block.rect.left/13)] = None
        for sprite in self.sprites:
            b = (sprite.moving or not sprite.onGround) and onScreen(sprite.rect, self.player.rect)
            if b:
                for col in range(13):
                    for row in range(13):
                        self.world.set_at((sprite.rect.left + col, sprite.rect.top + row), (0, 0, 0, 0))
                for offset in ((0, 0), (0, 1), (1, 0), (1, 1)):
                    block = self.blocks[int(sprite.rect.top/13 + offset[1])][int(sprite.rect.left/13 + offset[0])]
                    if block and block.name in BACKGROUND:
                        self.world.blit(block.image, (block.rect.left, block.rect.top))
            if sprite.update(self.blocks, self.player, self.inventory):
                for col in range(13):
                    for row in range(13):
                        self.world.set_at((sprite.rect.left + col, sprite.rect.top + row), (0, 0, 0, 0))
                for offset in ((0, 0), (0, 1), (1, 0), (1, 1)):
                    block = self.blocks[int(sprite.rect.top/13 + offset[1])][int(sprite.rect.left/13 + offset[0])]
                    if block and block.name in BACKGROUND:
                        self.world.blit(block.image, (block.rect.left, block.rect.top))
                self.poplist.append(sprite)
            elif b:
                self.world.blit(sprite.image, (sprite.rect.left, sprite.rect.top))
        self.inventory.updateCrafts([])
        for mob in self.mobs:
            result = mob.update(self.blocks, self.player)
            if result and self.player.hitdelay <= 0:
                self.player.health -= result
                self.player.hitdelay = 35
                if self.player.rect.center < mob.rect.center:
                    self.player.vx = -5
                    self.player.vy = -3
                else:
                    self.player.vx = 5
                    self.player.vy = -3
        hotkey = self.player.update(self.blocks, self.keys)
        if type(hotkey) == int:
            self.inventory.eraseBar(self.hotkey)
            self.hotkey = hotkey
            self.inventory.createBar(self.hotkey)
        for event in self.queue:
            if event.type == pygame.KEYDOWN and event.key == pygame.K_ESCAPE:
                self.dispInv = inv_bool(self.dispInv)
    def updateDisplay(self):
        self.screen.fill((255, 255, 255))
        self.screen.blit(self.background, (-self.player.rect.left + 208, -self.player.rect.top + 208))
        self.screen.blit(self.world, (-self.player.rect.left + 208, -self.player.rect.top + 208))
        self.screen.blit(self.player.image, (208, 208))
        for mob in self.mobs:
            self.screen.blit(mob.image, (-self.player.rect.left + 208 + mob.rect.left, -self.player.rect.top + 208 + mob.rect.top))
        self.screen.blit(self.inventory.image, (5, 0))
        self.screen.blit(self.HELVETICA.render('Health: ' + str(self.player.health), 1, (0, 0, 0)), (353, 2))
        self.screen.blit(self.HELVETICA.render('Magic: 0', 1, (0, 0, 0)), (353, 17))
        if self.dispInv:
            self.screen.blit(self.inventory.image2, (5, 35))
            self.screen.blit(self.inventory.crafting, (5, 150), (0, self.offset, self.inventory.crafting.get_width(), 248))
            self.screen.blit(self.scrollup, (55, 135))
            self.screen.blit(self.scrolldown, (55, 400))
        if self.item[ITEM]:
            location = pygame.mouse.get_pos()
            self.screen.blit(BLOCKIMAGES[self.item[ITEM]], location)
            self.screen.blit(self.HELVETICA.render(str(self.item[NUMBER]), 1, (0, 0, 0)), (location[0] - 10, location[1] + 10))
        pygame.display.update()
    def popObjects(self):
        for sprite in self.poplist:
            self.sprites.pop(self.sprites.index(sprite))

class Object(pygame.sprite.Sprite):
    def __init__(self, location, image):
        pygame.sprite.Sprite.__init__(self)
        if type(image) == str:
            image = pygame.image.load(image).convert_alpha()
            rect = image.get_rect()
            self.image = pygame.Surface((rect[2], rect[3])).convert_alpha()
            self.image.blit(image, (0, 0))
            mkTrans(self.image)
        else:
            rect = image.get_rect()
            self.image = image
        self.rect = pygame.Rect(location[0], location[1], rect[2], rect[3])

SPEED = 7

class Player(Object):
    def __init__(self, location, image):
        Object.__init__(self, location, image)
        self.vx = 0
        self.vy = 0
        self.onGround = False
        self.moving = True
        self.dir = True
        self.health = 50
        self.hitdelay = 0
        self.mstate = 1
        self.mdelay = 0
        # 0 [left, still] 1 [right, still] 2 [left, 2] 3 [right, 2] 4 [left, 3] 5 [right, 3]
    def update(self, blocks, keys):
        self.hitdelay -= 1
        self.mdelay -= 1
        if keys[pygame.K_a] or keys[pygame.K_LEFT]:
            if self.vx > -4:
                self.vx -= 0.5
            if self.mdelay <= 0:
                if self.mstate == 0 or self.mstate == 4:
                    self.image = pygame.image.load('./player/player_l2.bmp').convert_alpha()
                    mkTrans(self.image)
                    self.mstate = 2
                    self.mdelay = SPEED
                else:
                    self.image = pygame.image.load('./player/player_l3.bmp').convert_alpha()
                    mkTrans(self.image)
                    self.mstate = 4
                    self.mdelay = SPEED
            elif self.mstate == 1 or self.mstate == 3 or self.mstate == 5:
                self.image = pygame.image.load('./player/player_l1.bmp').convert_alpha()
                mkTrans(self.image)
                self.mstate = 0
                self.mdelay = SPEED
        if keys[pygame.K_d] or keys[pygame.K_RIGHT]:
            if self.vx < 5:
                self.vx += 0.5
            if self.mdelay <= 0:
                if self.mstate == 1 or self.mstate == 5:
                    self.image = pygame.image.load('./player/player_r2.bmp').convert_alpha()
                    mkTrans(self.image)
                    self.mstate = 3
                    self.mdelay = SPEED
                else:
                    self.image = pygame.image.load('./player/player_r3.bmp').convert_alpha()
                    mkTrans(self.image)
                    self.mstate = 5
                    self.mdelay = SPEED
            elif self.mstate == 0 or self.mstate == 2 or self.mstate == 4:
                self.image = pygame.image.load('./player/player_r1.bmp').convert_alpha()
                mkTrans(self.image)
                self.mstate = 1
                self.mdelay = SPEED
        if keys[pygame.K_w] or keys[pygame.K_UP]:
            if self.onGround:
                self.vy = -7
        if not(keys[pygame.K_a] or keys[pygame.K_d] or
               keys[pygame.K_LEFT] or keys[pygame.K_RIGHT]):
            if abs(self.vx) < 0.3:
                self.vx = 0
            if self.vx >= 0.3:
                self.vx -= 0.3
            if self.vx <= -0.3:
                self.vx += 0.3
            if self.mstate == 2 or self.mstate == 4:
                self.image = pygame.image.load('./player/player_l1.bmp').convert_alpha()
                mkTrans(self.image)
                self.mstate = 0
                self.mdelay = 0
            if self.mstate == 3 or self.mstate == 5:
                self.image = pygame.image.load('./player/player_r1.bmp').convert_alpha()
                mkTrans(self.image)
                self.mstate = 1
                self.mdelay = 0
        j = False
        for i in range(10):
            if eval('keys[pygame.K_' + str(i) + ']'):
                j = i
        self.doMove(blocks)
        return j
    def doMove(self, blocks):
        coords = (self.rect.left, self.rect.top)
        if not self.onGround:
            self.vy += 0.3
            if self.vy > 30: self.vy = 30
        self.rect.left += self.vx
        self.collideX(blocks)
        self.rect.top += self.vy
        self.onGround = False
        self.collideY(blocks)
        if (self.rect.left, self.rect.top) == coords:
            self.moving = False
        else:
            if (self.rect.top) < coords[1]:
                self.dir = True
            else:
                self.dir = False
            self.moving = True
    def collideX(self, blocks):
        blocklist = (blocks[int(self.rect.top/13)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13 + 1)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13 + 2)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13 + 3)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13 + 1)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13 + 2)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13 + 3)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13)][int(self.rect.left/13 + 2)],
                     blocks[int(self.rect.top/13 + 1)][int(self.rect.left/13 + 2)],
                     blocks[int(self.rect.top/13 + 2)][int(self.rect.left/13 + 2)],
                     blocks[int(self.rect.top/13 + 3)][int(self.rect.left/13 + 2)])
        for block in blocklist:
            if block and block.name not in BACKGROUND and pygame.sprite.collide_rect(self, block):
                if self.vx > 0:
                    self.rect.right = block.rect.left
                    self.vx = 0
                elif self.vx < 0:
                    self.rect.left = block.rect.right
                    self.vx = 0
    def collideY(self, blocks):
        blocklist = (blocks[int(self.rect.top/13)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13 + 1)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13 + 2)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13 + 3)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13 + 1)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13 + 2)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13 + 3)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13)][int(self.rect.left/13 + 2)],
                     blocks[int(self.rect.top/13 + 1)][int(self.rect.left/13 + 2)],
                     blocks[int(self.rect.top/13 + 2)][int(self.rect.left/13 + 2)],
                     blocks[int(self.rect.top/13 + 3)][int(self.rect.left/13 + 2)])
        for block in blocklist:
            if block and block.name not in BACKGROUND and pygame.sprite.collide_rect(self, block):
                if self.vy > 0:
                    self.rect.bottom = block.rect.top
                    self.onGround = True
                    self.vy = 0
                elif self.vy < 0:
                    self.rect.top = block.rect.bottom
                    self.vy = 0

class Mob(Player):
    def __init__(self, location, image, name):
        Player.__init__(self, location, image)
        self.name = name
        if self.name[-5:] == 'Slime':
            self.delay = 50
            self.wall = 0
            if self.name == 'Blue Slime':
                self.health = 12
            if self.name == 'Red Slime':
                self.health = 20
    def update(self, blocks, player):
        if self.name[-5:] == 'Slime':
            if self.delay == 0:
                self.delay = -1
                if abs((player.rect.center[0] - self.rect.center[0])/50) > 4:
                    if player.rect.center[0] > self.rect.center[0]:
                        self.vx = 4
                    else:
                        self.vx = -4
                elif abs((player.rect.center[0] - self.rect.center[0])/50) < 1:
                    if player.rect.left > self.rect.left:
                        self.vx = 1
                    else:
                        self.vx = -1
                else:
                    self.vx = (player.rect.center[0] - self.rect.center[0])/50
                self.vy = -7
            elif self.delay == -1:
                if self.onGround:
                    self.delay = 50
            else:
                self.delay -= 1
            self.doMove(blocks)
        if pygame.sprite.collide_rect(self, player):
            if self.name == 'Blue Slime':
                return 3
            if self.name == 'Red Slime':
                return 5
        else:
            return 0
    def doMove(self, blocks):
        coords = (self.rect.left, self.rect.top)
        if not self.onGround:
            self.vy += 0.3
            if self.vy > 30: self.vy = 30
        else:
            self.vx = 0
        self.rect.left += self.vx
        self.collideX(blocks)
        self.rect.top += self.vy
        self.onGround = False
        self.collideY(blocks)
        if (self.rect.left, self.rect.top) == coords:
            self.moving = False
        else:
            if (self.rect.top) < coords[1]:
                self.dir = True
            else:
                self.dir = False
            self.moving = True
    def hit(self, weapon):
        weapons = {'Copper Sword':5,
                   'Iron Sword':10,
                   'Gold Sword':20,
                   'Debug Wand':1000000}
        if weapon in weapons:
            self.health -= weapons[weapon]
        else:
            self.health -= 2
        if self.health <= 0:
            drops = []
            if self.name[-5:] == 'Slime':
                for i in range(randint(1, 3)):
                    drops.append(Resource(self.rect.center, BLOCKIMAGES['Gel'], (random() * 4 - 2, -2), 'Gel'))
            return drops
    def collideX(self, blocks):
        blocklist = (blocks[int(self.rect.top/13)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13 + 1)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13 + 2)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13 + 3)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13 + 1)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13 + 2)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13 + 3)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13)][int(self.rect.left/13 + 2)],
                     blocks[int(self.rect.top/13 + 1)][int(self.rect.left/13 + 2)],
                     blocks[int(self.rect.top/13 + 2)][int(self.rect.left/13 + 2)],
                     blocks[int(self.rect.top/13 + 3)][int(self.rect.left/13 + 2)])
        if self.name[-5:] == 'Slime':
            do = False
            for block in blocklist:
                if block and block.name not in BACKGROUND and pygame.sprite.collide_rect(self, block):
                    do = True
                    if self.vx > 0:
                        self.rect.right = block.rect.left
                        self.vx = 0
                        self.wall = 1
                    elif self.vx < 0:
                        self.rect.left = block.rect.right
                        self.vx = 0
                        self.wall = -1
            if do:
                self.vx = self.wall
                self.wall = 0
        else:
            for block in blocklist:
                if block and block.name not in BACKGROUND and pygame.sprite.collide_rect(self, block):
                    do = False
                    if self.vx > 0:
                        self.rect.right = block.rect.left
                        self.vx = 0
                    elif self.vx < 0:
                        self.rect.left = block.rect.right
                        self.vx = 0
    def collideY(self, blocks):
        blocklist = (blocks[int(self.rect.top/13)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13 + 1)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13 + 2)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13 + 3)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13 + 1)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13 + 2)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13 + 3)][int(self.rect.left/13 + 1)],
                     blocks[int(self.rect.top/13)][int(self.rect.left/13 + 2)],
                     blocks[int(self.rect.top/13 + 1)][int(self.rect.left/13 + 2)],
                     blocks[int(self.rect.top/13 + 2)][int(self.rect.left/13 + 2)],
                     blocks[int(self.rect.top/13 + 3)][int(self.rect.left/13 + 2)])
        for block in blocklist:
            if block and block.name not in BACKGROUND and pygame.sprite.collide_rect(self, block):
                if self.vy > 0:
                    self.rect.bottom = block.rect.top
                    self.onGround = True
                    self.vy = 0
                elif self.vy < 0:
                    self.rect.top = block.rect.bottom
                    self.vy = 0

class Block(Object):
    def __init__(self, location, image, name):
        Object.__init__(self, location, image)
        self.name = name
        if self.name in BLOCKSTRENGTHS:
            self.strength = BLOCKSTRENGTHS[self.name]
    def mine(self, image, pick):
        mining = {'Copper Pick':0.5,
                  'Iron Pick':1,
                  'Gold Pick':2,
                  'Copper Axe':0.5,
                  'Iron Axe':1,
                  'Gold Axe':2,
                  'Copper Hammer':0.5,
                  'Iron Hammer':1,
                  'Gold Hammer':2}
        if self.strength > 0 and not DEBUG:
            self.strength -= mining[pick]
            return
        return Resource((self.rect.left, self.rect.top), BLOCKIMAGES[BLOCKDROPS[self.name]], (random() * 4 - 2, -2), BLOCKDROPS[self.name])

class Resource(Player):
    def __init__(self, location, image, velocity, name):
        Player.__init__(self, location, image)
        self.vx, self.vy = velocity
        self.name = name
    def doMove(self, blocks):
        coords = (self.rect.left, self.rect.top)
        if abs(self.vx) < 0.1:
            self.vx = 0
        if self.vx >= 0.1:
            self.vx -= 0.1
        if self.vx <= -0.1:
            self.vx += 0.1
        if not self.onGround:
            self.vy += 0.3
            if self.vy > 30: self.vy = 30
        self.rect.left += self.vx
        self.collideX(blocks)
        self.rect.top += self.vy
        self.onGround = False
        self.collideY(blocks)
        if (self.rect.left, self.rect.top) == coords:
            self.moving = False
        else:
            if (self.rect.top) < coords[1]:
                self.dir = True
            else:
                self.dir = False
            self.moving = True
    def collideX(self, blocks):
        blocklist = (blocks[int(self.rect.top/13)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13) + 1][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13)][int(self.rect.left/13) + 1],
                     blocks[int(self.rect.top/13) + 1][int(self.rect.left/13) + 1])
        for block in blocklist:
            if block and block.name not in BACKGROUND and pygame.sprite.collide_rect(self, block):
                if self.vx > 0:
                    self.rect.right = block.rect.left
                    self.vx = 0
                elif self.vx < 0:
                    self.rect.left = block.rect.right
                    self.vx = 0
    def collideY(self, blocks):
        blocklist = (blocks[int(self.rect.top/13)][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13) + 1][int(self.rect.left/13)],
                     blocks[int(self.rect.top/13)][int(self.rect.left/13) + 1],
                     blocks[int(self.rect.top/13) + 1][int(self.rect.left/13) + 1])
        for block in blocklist:
            if block and block.name not in BACKGROUND and pygame.sprite.collide_rect(self, block):
                if self.vy > 0:
                    self.rect.bottom = block.rect.top
                    self.onGround = True
                    self.vy = 0
                elif self.vy < 0:
                    self.rect.top = block.rect.bottom
                    self.vy = 0
    def update(self, blocks, player, inventory):
        self.doMove(blocks)
        if closeEnough(player.rect, self.rect, 40):
            inventory.add(self.name, 1)
            return True

class Inventory():
    def __init__(self, font):
        self.font = font
        self.box = pygame.transform.scale(pygame.image.load('inv.bmp').convert_alpha(), (25, 25))
        for row in range(self.box.get_height()):
            for col in range(self.box.get_width()):
                if self.box.get_at((row, col)) == (255, 255, 255):
                    self.box.set_at((row, col), (0, 0, 0, 0))
        self.boxSelected = pygame.transform.scale(pygame.image.load('inv.bmp').convert_alpha(), (25, 25))
        for row in range(self.boxSelected.get_height()):
            for col in range(self.boxSelected.get_width()):
                if self.boxSelected.get_at((row, col)) == (255, 255, 255):
                    self.boxSelected.set_at((row, col), (0, 0, 0, 0))
        for row in range(self.boxSelected.get_height()):
            for col in range(self.boxSelected.get_width()):
                if self.boxSelected.get_at((row, col)) == (0, 0, 0):
                    self.boxSelected.set_at((row, col), (220, 20, 60))
        self.x = []
        for i in range(10):
            self.x.append(5 + 35 * i)
        self.y = []
        for i in range(3):
            self.y.append(3 + 35 * i)
        self.image = pygame.Surface((max(self.x) + 25, 30), pygame.SRCALPHA, 32).convert_alpha()
        self.image.fill((0, 0, 0, 100))
        for x in self.x:
            self.image.blit(self.box, (x, 3))
        self.image2 = pygame.Surface((max(self.x) + 25, 100), pygame.SRCALPHA, 32).convert_alpha()
        self.image2.fill((0, 0, 0, 100))
        for x in self.x:
            for y in self.y:
                self.image2.blit(self.box, (x, y))
        self.hrects = []
        for x in self.x:
            self.hrects.append(pygame.Rect(x + 5, 3, 25, 25))
        self.irects = []
        for y in self.y:
            for x in self.x:
                self.irects.append(pygame.Rect(x + 5, y + 35, 25, 25))
        self.arrow = pygame.image.load('arrow.bmp').convert_alpha()
        mkTrans(self.arrow)
        self.mkCrafts()
        self.itemarray = ItemArray(10, 30)
    def mkCrafts(self):
        self.recipes = RECIPES()
        recipes = self.recipes
        self.craftboxes = []
        for recipe in recipes:
            a, b = len(recipe[RECIPE]) == 1, len(recipe[RESULT]) == 1
            if a and b:
                craftbox = pygame.Surface((18 + 30 * len(recipe[RECIPE]) + 30 * len(recipe[RESULT]), 30)).convert_alpha()
            if a != b:
                craftbox = pygame.Surface((23 + 30 * len(recipe[RECIPE]) + 30 * len(recipe[RESULT]), 30)).convert_alpha()
            if not(a or b):
                craftbox = pygame.Surface((28 + 30 * len(recipe[RECIPE]) + 30 * len(recipe[RESULT]), 30)).convert_alpha()
            craftbox.fill((0, 0, 0, 0))
            for i in range(len(recipe[RECIPE])):
                craftbox.blit(self.box, (5 + 35 * i, 3))
                craftbox.blit(BLOCKIMAGES[recipe[RECIPE][i][ITEM]], (11 + 35 * i, 10))
                craftbox.blit(self.font.render(str(recipe[RECIPE][i][NUMBER]), 1, (0, 0, 0)), (1 + 35 * i, 20))
            j = 50 + 35 * i
            craftbox.blit(self.arrow, (j - 15, 12))
            for i in range(len(recipe[RESULT])):
                craftbox.blit(self.box, (35 * i + j, 3))
                craftbox.blit(BLOCKIMAGES[recipe[RESULT][i][ITEM]], (6 + 35 * i + j, 10))
                craftbox.blit(self.font.render(str(recipe[RESULT][i][NUMBER]), 1, (0, 0, 0)), (-4 + 35 * i + j, 20))
            self.craftboxes.append(craftbox)
    def updateCrafts(self, stations):
        width = 0
        height = 5
        for i in range(len(self.craftboxes)):
            do = True
            for item in self.recipes[i][RECIPE]:
                if self.numOf(item[ITEM]) < item[NUMBER]:
                    do = False
                    break
            if do:
                new_width = self.craftboxes[i].get_width()
                if new_width > width:
                    width = new_width
                height += self.craftboxes[i].get_height() + 5
        self.craftrects = []
        self.craftnums = []
        self.crafting = pygame.Surface((width + 6, height)).convert_alpha()
        self.crafting.fill((0, 0, 0, 100))
        undo = 0
        for i in range(len(self.craftboxes)):
            do = True
            for item in self.recipes[i][RECIPE]:
                if self.numOf(item[ITEM]) < item[NUMBER]:
                    do = False
                    undo += 1
                    break
            if do:
                self.crafting.blit(self.craftboxes[i], (3, 5 + 35 * (i - undo)))
                self.craftrects.append(pygame.Rect(8, 155 + 35 * (i - undo), self.craftboxes[i].get_width(), self.craftboxes[i].get_height()))
                self.craftnums.append(i)
    def add(self, item, num):
        result = self.itemarray.add(item, num)
        if result:
            self.updateBlock(item, self.font)
        return result
    def addAt(self, item, inhotbar, location, num):
        result = self.itemarray.addAt(item, inhotbar, location, num)
        if result:
            self.updateBlock(item, self.font)
        return result
    def drop(self, inhotbar, location, num):
        result = self.itemarray.drop(inhotbar, location, num)
        if result:
            if inhotbar:
                if self.itemarray.hotbar[location][ITEM]:
                    self.updateBlock(self.itemarray.hotbar[location][ITEM], self.font)
                else:
                    self.updateLocation(inhotbar, location)
            else:
                if self.itemarray.inventory[location][ITEM]:
                    self.updateBlock(self.itemarray.inventory[location][ITEM], self.font)
                else:
                    self.updateLocation(inhotbar, location)
        return result
    def dropItem(self, name, num):
        result = self.itemarray.dropItem(name, num)
        if result:
            for location in result:
                if location[0]:
                    if self.itemarray.hotbar[location[1]][ITEM]:
                        self.updateBlock(self.itemarray.hotbar[location[1]][ITEM], self.font)
                    else:
                        self.updateLocation(location[0], location[1])
                else:
                    if self.itemarray.inventory[location[1]][ITEM]:
                        self.updateBlock(self.itemarray.inventory[location[1]][ITEM], self.font)
                    else:
                        self.updateLocation(location[0], location[1])
        return result
    def numOf(self, name):
        return self.itemarray.numOf(name)
    def updateLocation(self, inhotbar, i):
        if inhotbar:
            for col in range(18):
                for row in range(10):
                    self.image.set_at((1 + 35 * i + col, 20 + row), (0, 0, 0, 100))
            for col in range(13):
                for row in range(13):
                    self.image.set_at((11 + 35 * i + col, 10 + row), (0, 0, 0, 100))
        else:
            j, k = invCoords(i)
            for col in range(18):
                for row in range(10):
                    self.image2.set_at((1 + 35 * j + col, 20 + 35 * k + row), (0, 0, 0, 100))
            for col in range(13):
                for row in range(13):
                    self.image2.set_at((11 + 35 * j + col, 10 + 35 * k + row), (0, 0, 0, 100))
    def updateBlock(self, name, font):
        for i in range(10):
            if self.itemarray.hotbar[i][ITEM] == name:
                for col in range(18):
                    for row in range(10):
                        self.image.set_at((1 + 35 * i + col, 20 + row), (0, 0, 0, 100))
                if self.itemarray.hotbar[i][NUMBER]:
                    self.image.blit(BLOCKIMAGES[name], (11 + 35 * i, 10))
                    self.image.blit(font.render(str(self.itemarray.hotbar[i][NUMBER]), 1, (0, 0, 0)), (1 + 35 * i, 20))
                else:
                    for col in range(13):
                        for row in range(13):
                            self.image.set_at((11 + 35 * i + col, 10 + row), (0, 0, 0, 100))
        for j in range(10):
            for k in range(3):
                i = k * 10 + j
                if self.itemarray.inventory[i][ITEM] == name:
                    for col in range(18):
                        for row in range(10):
                            self.image2.set_at((1 + 35 * j + col, 20 + 35 * k + row), (0, 0, 0, 100))
                    if self.itemarray.inventory[i][NUMBER]:
                        self.image2.blit(BLOCKIMAGES[name], (11 + 35 * j, 10 + 35 * k))
                        self.image2.blit(font.render(str(self.itemarray.inventory[i][NUMBER]), 1, (0, 0, 0)), (1 + 35 * j, 20 + 35 * k))
                    else:
                        for col in range(13):
                            for row in range(13):
                                self.image2.set_at((11 + 35 * j + col, 10 + 35 * k + row), (0, 0, 0, 100))
    def createBar(self, hotkey):
        if hotkey != None:
            self.image.blit(self.boxSelected, (self.x[hotbarAssign(hotkey)], 3))
    def eraseBar(self, hotkey):
        if hotkey != None:
            self.image.blit(self.box, (self.x[hotbarAssign(hotkey)], 3))

def hotbarAssign(hotkey):
    if hotkey == 0:
        return 9
    return hotkey - 1

class ItemArray():
    def __init__(self, hotbarsize, inventorysize):
        self.hotbar = []
        for i in range(hotbarsize):
            self.hotbar.append({ITEM:None,NUMBER:None})
        self.inventory = []
        for i in range(inventorysize):
            self.inventory.append({ITEM:None,NUMBER:None})
    def add(self, item, num):
        for i in range(len(self.hotbar)):
            if self.hotbar[i][ITEM] == item and self.hotbar[i][NUMBER] < BLOCKSTACKS[item]:
                if num <= BLOCKSTACKS[item] - self.hotbar[i][NUMBER]:
                    self.hotbar[i][NUMBER] += num
                    return True
                elif num > BLOCKSTACKS[item] - self.hotbar[i][NUMBER]:
                    num -= BLOCKSTACKS[item] - self.hotbar[i][NUMBER]
                    self.hotbar[i][NUMBER] = BLOCKSTACKS[item]
        for i in range(len(self.inventory)):
            if self.inventory[i][ITEM] == item and self.inventory[i][NUMBER] < BLOCKSTACKS[item]:
                if num <= BLOCKSTACKS[item] - self.inventory[i][NUMBER]:
                    self.inventory[i][NUMBER] += num
                    return True
                elif num > BLOCKSTACKS[item] - self.inventory[i][NUMBER]:
                    num -= BLOCKSTACKS[item] - self.inventory[i][NUMBER]
                    self.inventory[i][NUMBER] = BLOCKSTACKS[item]
        for i in range(len(self.hotbar)):
            if not self.hotbar[i][ITEM]:
                self.hotbar[i][ITEM] = item
                self.hotbar[i][NUMBER] = num
                return True
        for i in range(len(self.inventory)):
            if not self.inventory[i][ITEM]:
                self.inventory[i][ITEM] = item
                self.inventory[i][NUMBER] = num
                return True
        return False
    def addAt(self, item, inhotbar, location, num):
        i = location
        if inhotbar:
            if self.hotbar[i][ITEM]:
                if self.hotbar[i][ITEM] == item:
                    if num <= BLOCKSTACKS[item] - self.hotbar[i][NUMBER]:
                        self.hotbar[i][NUMBER] += num
                        return True
                    else:
                        return False
                else:
                    return False
            else:
                self.hotbar[i][ITEM] = item
                self.hotbar[i][NUMBER] = num
                return True
        else:
            if self.inventory[i][ITEM]:
                if self.inventory[i][ITEM] == item:
                    if num <= BLOCKSTACKS[item] - self.inventory[i][NUMBER]:
                        self.inventory[i][NUMBER] += num
                        return True
                    else:
                        return False
                else:
                    return False
            else:
                self.inventory[i][ITEM] = item
                self.inventory[i][NUMBER] = num
                return True
    def drop(self, inhotbar, location, num):
        i = location
        if inhotbar:
            if self.hotbar[i][ITEM] and self.hotbar[i][NUMBER] >= num:
                self.hotbar[i][NUMBER] -= num
                if not self.hotbar[i][NUMBER]:
                    self.hotbar[i] = {ITEM:None,NUMBER:None}
                return True
            return False
        else:
            if self.inventory[i][ITEM] and self.inventory[i][NUMBER] >= num:
                self.inventory[i][NUMBER] -= num
                if not self.inventory[i][NUMBER]:
                    self.inventory[i] = {ITEM:None,NUMBER:None}
                return True
            return False
    def dropItem(self, item, num):
        total = 0
        l = set()
        for i in range(len(self.hotbar)):
            if self.hotbar[i][ITEM] == item:
                total += self.hotbar[i][NUMBER]
        for i in range(len(self.inventory)):
            if self.inventory[i][NUMBER] == item:
                total += self.inventory[i][NUMBER]
        if total >= num:
            for i in range(num):
                for i in range(len(self.hotbar)):
                    if self.hotbar[i][ITEM] == item:
                        self.hotbar[i][NUMBER] -= 1
                        if not self.hotbar[i][NUMBER]:
                            self.hotbar[i] = {ITEM:None,NUMBER:None}
                        l.add((True, i))
                for i in range(len(self.inventory)):
                    if self.inventory[i][ITEM] == item:
                        self.inventory[i][NUMBER] -= 1
                        if not self.inventory[i][NUMBER]:
                            self.inventory[i] = {ITEM:None,NUMBER:None}
                        l.add((False, i))
            return l
        return False
    def numOf(self, item):
        total = 0
        l = set()
        for i in range(len(self.hotbar)):
            if self.hotbar[i][ITEM] == item:
                total += self.hotbar[i][NUMBER]
        for i in range(len(self.inventory)):
            if self.inventory[i][NUMBER] == item:
                total += self.inventory[i][NUMBER]
        return total

class NULL():
    def __init__(self, name):
        self.name = name
    def __str__(self):
        return self.name

class RectObject():
    def __init__(self, rect):
        self.rect = rect

ITEM, NUMBER, RECIPE, RESULT = NULL('ITEM'), NULL('NUMBER'), NULL('RECIPE'), NULL('RESULT')

def RECIPES():
    return [{RECIPE:[{ITEM:'Sand',              NUMBER:4  }],   ### RAW MATERIAL CRAFTING
             RESULT:[{ITEM:'Sandstone',         NUMBER:1  }]},
            {RECIPE:[{ITEM:'Stone',             NUMBER:1  },
                     {ITEM:'Copper Ore',        NUMBER:1  }],
             RESULT:[{ITEM:'Copper Ore Block',  NUMBER:1  }]},
            {RECIPE:[{ITEM:'Stone',             NUMBER:1  },
                     {ITEM:'Iron Ore',          NUMBER:1  }],
             RESULT:[{ITEM:'Iron Ore Block',    NUMBER:1  }]},
            {RECIPE:[{ITEM:'Stone',             NUMBER:1  },
                     {ITEM:'Gold Ore',          NUMBER:1  }],
             RESULT:[{ITEM:'Gold Ore Block',    NUMBER:1  }]},
            {RECIPE:[{ITEM:'Rose',              NUMBER:1  }],
             RESULT:[{ITEM:'Red Dye',           NUMBER:1  }]},  ### DYE CRAFTING - PRIMARY COLORS
            {RECIPE:[{ITEM:'Bluebell',          NUMBER:1  }],
             RESULT:[{ITEM:'Blue Dye',          NUMBER:1  }]},
            {RECIPE:[{ITEM:'Dandelion',         NUMBER:1  }],
             RESULT:[{ITEM:'Yellow Dye',        NUMBER:1  }]},
            {RECIPE:[{ITEM:'Cactus',            NUMBER:1  }],
             RESULT:[{ITEM:'Green Dye',         NUMBER:1  }]},
            {RECIPE:[{ITEM:'Red Dye',           NUMBER:1  },    ### DYE CRAFTING - SECONDARY COLORS
                     {ITEM:'Yellow Dye',        NUMBER:1  }],
             RESULT:[{ITEM:'Orange Dye',        NUMBER:2  }]},
            {RECIPE:[{ITEM:'Green Dye',         NUMBER:1  },
                     {ITEM:'Blue Dye',          NUMBER:1  }],
             RESULT:[{ITEM:'Cyan Dye',          NUMBER:2  }]},
            {RECIPE:[{ITEM:'Red Dye',           NUMBER:1  },
                     {ITEM:'Blue Dye',          NUMBER:1  }],
             RESULT:[{ITEM:'Purple Dye',        NUMBER:2  }]},
            {RECIPE:[{ITEM:'Wood',              NUMBER:1  }],   ### WALL CRAFTING
             RESULT:[{ITEM:'Wood Wall',         NUMBER:4  }]},
            {RECIPE:[{ITEM:'Stone',             NUMBER:3  }],   ### BRICK CRAFTING
             RESULT:[{ITEM:'Stone Brick',       NUMBER:1  }]},
            {RECIPE:[{ITEM:'Gel',               NUMBER:1  },    ### ITEM CRAFTING
                     {ITEM:'Wood',              NUMBER:1  }],
             RESULT:[{ITEM:'Torch',             NUMBER:3  }]},
            {RECIPE:[{ITEM:'Copper Ore',        NUMBER:10 },    ### TOOL CRAFTING - PICKS
                     {ITEM:'Wood',              NUMBER:2  }],
             RESULT:[{ITEM:'Copper Pick',       NUMBER:1  }]},
            {RECIPE:[{ITEM:'Iron Ore',          NUMBER:10 },
                     {ITEM:'Wood',              NUMBER:2  }],
             RESULT:[{ITEM:'Iron Pick',         NUMBER:1  }]},
            {RECIPE:[{ITEM:'Gold Ore',          NUMBER:10 },
                     {ITEM:'Wood',              NUMBER:2  }],
             RESULT:[{ITEM:'Gold Pick',         NUMBER:1  }]},
            {RECIPE:[{ITEM:'Copper Ore',        NUMBER:10 },    ### TOOL CRAFTING - AXES
                     {ITEM:'Wood',              NUMBER:2  }],
             RESULT:[{ITEM:'Copper Axe',        NUMBER:1  }]},
            {RECIPE:[{ITEM:'Iron Ore',          NUMBER:10 },
                     {ITEM:'Wood',              NUMBER:2  }],
             RESULT:[{ITEM:'Iron Axe',          NUMBER:1  }]},
            {RECIPE:[{ITEM:'Gold Ore',          NUMBER:10 },
                     {ITEM:'Wood',              NUMBER:2  }],
             RESULT:[{ITEM:'Gold Axe',          NUMBER:1  }]},
            {RECIPE:[{ITEM:'Copper Ore',        NUMBER:10 },    ### TOOL CRAFTING - HAMMERS
                     {ITEM:'Wood',              NUMBER:2  }],
             RESULT:[{ITEM:'Copper Hammer',     NUMBER:1  }]},
            {RECIPE:[{ITEM:'Iron Ore',          NUMBER:10 },
                     {ITEM:'Wood',              NUMBER:2  }],
             RESULT:[{ITEM:'Iron Hammer',       NUMBER:1  }]},
            {RECIPE:[{ITEM:'Gold Ore',          NUMBER:10 },
                     {ITEM:'Wood',              NUMBER:2  }],
             RESULT:[{ITEM:'Gold Hammer',       NUMBER:1  }]},
            {RECIPE:[{ITEM:'Copper Ore',        NUMBER:10 },    ### TOOL CRAFTING - SWORDS
                     {ITEM:'Wood',              NUMBER:2  }],
             RESULT:[{ITEM:'Copper Sword',      NUMBER:1  }]},
            {RECIPE:[{ITEM:'Iron Ore',          NUMBER:10 },
                     {ITEM:'Wood',              NUMBER:2  }],
             RESULT:[{ITEM:'Iron Sword',        NUMBER:1  }]},
            {RECIPE:[{ITEM:'Gold Ore',          NUMBER:10 },
                     {ITEM:'Wood',              NUMBER:2  }],
             RESULT:[{ITEM:'Gold Sword',        NUMBER:1  }]}]

def mkBlocks(cmap):
    blocks = []
    for j in range(len(cmap[0])):
        blocks.append(Block((j*13, 0), BLOCKIMAGES['Edge'], 'Edge'))
        blocks.append(Block((j*13, len(cmap)*13), BLOCKIMAGES['Edge'], 'Edge'))
    for i in range(len(cmap)):
        blocks.append(Block((0, i*13), BLOCKIMAGES['Edge'], 'Edge'))
        blocks.append(Block((len(cmap[0])*13, i*13), BLOCKIMAGES['Edge'], 'Edge'))
    for i in range(len(cmap)):
        for j in range(len(cmap[i])):
            if cmap[i][j] not in (' '):
                blocks.append(Block((j*13+13, i*13+13), BLOCKIMAGES[cmap[i][j]], cmap[i][j]))
    return blocks

def makeWorld(LENGTH, HEIGHT, COMPLEX_S, COMPLEX_T, screen, font, PARAMS=[5, 25]):
    genMsg(screen, font, 'Generating world...')
    biomes = []
    bcoefs = []
    for i in range(ceil(LENGTH/50)):
        bcoefs.append(randint(50, 150))
    for bcoef in bcoefs:
        biomes.extend([choice(('Plains', 'Desert', 'Jungle'))] * bcoef)
    coefs = []
    for i in range(COMPLEX_T):
        coefs.append(random() * 2 - 1)
    coefs2 = []
    for i in range(COMPLEX_S):
        coefs2.append(random() * 2 - 1)
    terrain = []
    for x in range(LENGTH):
        total = 0
        for coef in coefs:
            total += sin(x / 5 * coef)
        terrain.append(total / COMPLEX_T)
    for x in range(LENGTH):
        terrain[x] = floor(terrain[x] * 25)
    terrain2 = []
    for x in range(LENGTH):
        total = 0
        for coef2 in coefs2:
            total += sin(x / 5 * coef2)
        terrain2.append(total / COMPLEX_S)
    for x in range(LENGTH):
        terrain2[x] = floor(terrain2[x] * 25)
    charmap = []
    for y in range(HEIGHT):
        row = []
        for x in range(LENGTH):
            row.append(' ')
        charmap.append(row)
    genMsg(screen, font, 'Adding terrain...')
    for x in range(LENGTH):
        for y in range(HEIGHT):
            if biomes[x] in ('Plains'):
                if y == -terrain[x] + 20:
                    charmap[y][x] = 'Grass'
                if y > -terrain[x] + 20:
                    charmap[y][x] = 'Dirt'
                if y >= -terrain2[x] + 64:
                    charmap[y][x] = 'Stone'
            if biomes[x] == 'Desert':
                if y >= -terrain[x] + 20:
                    charmap[y][x] = 'Sand'
                if y > -terrain[x] + 30:
                    charmap[y][x] = 'Sandstone'
                if y >= -terrain2[x] + 64:
                    charmap[y][x] = 'Stone'
            if biomes[x] == 'Jungle':
                if y == -terrain[x] + 20:
                    charmap[y][x] = 'Jungle Grass'
                if y > -terrain[x] + 20:
                    charmap[y][x] = 'Dirt'
                if y >= -terrain2[x] + 64:
                    charmap[y][x] = 'Stone'
    genMsg(screen, font, 'Adding vegetation...')
    for x in range(LENGTH):
        if biomes[x] == 'Plains' or biomes[x] == 'Jungle':
            if not randint(0, 34):
                charmap[-terrain[x] + 19][x] = 'Rose'
    for x in range(LENGTH):
        if biomes[x] == 'Plains' or biomes[x] == 'Jungle':
            if not randint(0, 34):
                charmap[-terrain[x] + 19][x] = 'Bluebell'
    for x in range(LENGTH):
        if biomes[x] == 'Plains' or biomes[x] == 'Jungle':
            if not randint(0, 34):
                charmap[-terrain[x] + 19][x] = 'Dandelion'
    for x in range(LENGTH):
        if biomes[x] == 'Plains':
            if not randint(0, 19):
                for y in range(randint(5, 15)):
                    charmap[-terrain[x] + 19 - y][x] = 'Tree Block'
    for x in range(LENGTH):
        if biomes[x] == 'Jungle':
            if not randint(0, 6):
                for y in range(randint(5, 15)):
                    charmap[-terrain[x] + 19 - y][x] = 'Tree Block'
    for x in range(LENGTH):
        if biomes[x] == 'Desert':
            if not randint(0, 19):
                for y in range(randint(3, 6)):
                    charmap[-terrain[x] + 19 - y][x] = 'Cactus'
    if not FASTGEN:
        genMsg(screen, font, 'Adding dirt deposits...')
        for i in range(int(LENGTH * HEIGHT / 4800)):
            location = (randint(0, LENGTH - 1), randint(64, HEIGHT - 1))
            for coords in blob(1500):
                try:
                    charmap[location[1] + coords[1]][location[0] + coords[0]] = 'Dirt'
                except IndexError:
                    pass
        genMsg(screen, font, 'Adding mud deposits...')
        for i in range(int(LENGTH * HEIGHT / 4800)):
            location = (randint(0, LENGTH - 1), randint(64, HEIGHT - 1))
            for coords in blob(1500):
                try:
                    charmap[location[1] + coords[1]][location[0] + coords[0]] = 'Mud'
                except IndexError:
                    pass
        genMsg(screen, font, 'Adding ore deposits...')
        for i in range(int(LENGTH * HEIGHT / 1600)):
            location = (randint(0, LENGTH - 1), randint(64, HEIGHT - 1))
            for coords in blob(100):
                try:
                    charmap[location[1] + coords[1]][location[0] + coords[0]] = 'Copper Ore Block'
                except IndexError:
                    pass
        for i in range(int(LENGTH * HEIGHT / 4800)):
            location = (randint(0, LENGTH - 1), randint(128, HEIGHT - 1))
            for coords in blob(100):
                try:
                    charmap[location[1] + coords[1]][location[0] + coords[0]] = 'Iron Ore Block'
                except IndexError:
                    pass
        for i in range(int(LENGTH * HEIGHT / 14400)):
            location = (randint(0, LENGTH - 1), randint(196, HEIGHT - 1))
            for coords in blob(100):
                try:
                    charmap[location[1] + coords[1]][location[0] + coords[0]] = 'Gold Ore Block'
                except IndexError:
                    pass
        for i in range(int(LENGTH * HEIGHT / 43200)):
            location = (randint(0, LENGTH - 1), randint(260, HEIGHT - 1))
            for coords in blob(100):
                try:
                    charmap[location[1] + coords[1]][location[0] + coords[0]] = 'Diamond Ore Block'
                except IndexError:
                    pass
        for i in range(int(LENGTH * HEIGHT / 6400)):
            genMsg(screen, font, 'Adding caverns (' + str(i+1) + '/' + str(int(LENGTH * HEIGHT / 6400)) + ')...')
            location = (randint(0, LENGTH - 1), randint(64, HEIGHT - 1))
            for coords in blob(10000):
                try:
                    charmap[location[1] + coords[1]][location[0] + coords[0]] = ' '
                except IndexError:
                    pass
        genMsg(screen, font, 'Adding abandoned mine shafts...')
        for i in range(int(LENGTH * HEIGHT / 43200)):
            interior = []
            exterior = []
            location = [randint(0, LENGTH - 1), randint(300, HEIGHT - 1)]
            num = randint(20, 40)
            vectors = []
            for j in range(10):
                vectors.append(choice(([-num, 0], [num, 0], [0, -num], [0, num])))
            for vector in vectors:
                for x in nrange(location[0] - 3, location[0] + vector[0] + 4):
                    for y in nrange(location[1] - 3, location[1] + vector[1] + 4):
                        interior.append([x, y])
                for x in nrange(location[0] - 4, location[0] + vector[0] + 5):
                    exterior.append([x, location[1] - 4])
                    exterior.append([x, location[1] + vector[1] + 4])
                for y in nrange(location[1] - 4, location[1] + vector[1] + 5):
                    exterior.append([location[0] - 4, y])
                    exterior.append([location[0] + vector[0] + 4, y])
                location = [location[0] + vector[0], location[1] + vector[1]]
            try:
                for coords in exterior:
                    assert charmap[coords[1]][coords[0]] not in ('Wood', 'Edge') and coords[1] >= 160
            except (IndexError, AssertionError):
                continue
            for coords in exterior:
                charmap[coords[1]][coords[0]] = 'Wood'
            for coords in interior:
                charmap[coords[1]][coords[0]] = ' '
    genMsg(screen, font, 'Rendering blocks...')
    return mkBlocks(charmap)

def blob(size):
    coordlist = set()
    coordlist.add((0, 0))
    for i in range(size):
        coords = sample(coordlist, 1)[0]
        scale = choice(((-1, 0), (1, 0), (0, -1), (0, 1)))
        newcoords = (coords[0] + scale[0], coords[1] + scale[1])
        coordlist.add(newcoords)
    return coordlist

def genMsg(screen, font, text):
    screen.fill((0, 0, 0, 255))
    screen.blit(font.render(text, 1, (255, 255, 255)), (50, 200))
    pygame.display.update()

def vAdd(v1, v2):
    return (v1[0] + v2[0], v1[1] + v2[1])

def closeEnough(rect1, rect2, n):
    if dist(rect1.center, rect2.center) < n:
        return True
    return False

def nextTo(s1, s2, n):
    if pygame.sprite.collide_rect(s1, s2):
        return True
    if min((change(s1.rect.left, s2.rect.right),
            change(s1.rect.right, s2.rect.left),
            change(s1.rect.top, s2.rect.bottom),
            change(s1.rect.bottom, s2.rect.top))) < n:
        return True
    return False

def dist(p1, p2):
    return sqrt((p1[0] - p2[0]) ** 2 + (p1[1] - p2[1]) ** 2)

def onScreen(srect, prect):
    if change(srect.left, prect.left) < 416 and change(srect.top, prect.top) < 416:
        return True
    return False

def change(a, b):
    return abs(a - b)

def randreal(a, b):
    d = change(a, b)
    return (random() * d) - (d / 2)

def rectPoint(rect, point):
    if point[0] <= rect.right and point[0] >= rect.left and point[1] <= rect.bottom and point[1] >= rect.top:
        return True
    return False

def rectRect(rect1, rect2):
    if (rectPoint(rect1, (rect2.left, rect2.top)) or
        rectPoint(rect1, (rect2.right, rect2.top)) or
        rectPoint(rect1, (rect2.left, rect2.bottom)) or
        rectPoint(rect1, (rect2.right, rect2.bottom)) or
        rectPoint(rect2, (rect1.left, rect1.top)) or
        rectPoint(rect2, (rect1.right, rect1.top)) or
        rectPoint(rect2, (rect1.left, rect1.bottom)) or
        rectPoint(rect2, (rect1.right, rect1.bottom))):
            return True
    return False

def inv_bool(b):
    if b:
        return False
    else:
        return True

def on_off(b):
    if b == True:
        return 'ON'
    elif b == False:
        return 'OFF'
    else:
        return 'DISABLED'

def invCoords(n):
    if n >= 0 and n <= 9:
        return (n, 0)
    if n >= 10 and n <= 19:
        return (n - 10, 1)
    if n >= 20 and n <= 29:
        return (n - 20, 2)

def next_to_block(tblock, blocks):
    if (blocks[int(tblock.rect.top/13 - 1)][int(tblock.rect.left/13)] or
        blocks[int(tblock.rect.top/13)][int(tblock.rect.left/13 - 1)] or
        blocks[int(tblock.rect.top/13 + 1)][int(tblock.rect.left/13)] or
        blocks[int(tblock.rect.top/13)][int(tblock.rect.left/13 + 1)]):
        return True
    return False

def inBlock(block, coords):
    if (coords[0] >= block[0] and coords[0] < block[0] + 13 and
        coords[1] >= block[1] and coords[1] < block[1] + 13):
        return True
    return False

def binary13(n):
    i = 1
    while True:
        if 2 ** i > n:
            return 2 ** i * 13
        i += 1

def mkTrans(image):
    for row in range(image.get_height()):
        for col in range(image.get_width()):
            if image.get_at((col, row)) == (255, 255, 255, 255):
                image.set_at((col, row), (255, 255, 255, 0))

def six2zero(n):
    if abs(n) == 6:
        return 0
    else:
        return n

def nrange(start, stop):
    if stop >= start:
        return range(start, stop)
    else:
        return range(start-1, stop-1, -1)

if __name__ == '__main__':
    try:
        Game()
    except ImportError:
        pass
