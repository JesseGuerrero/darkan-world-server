// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright (C) 2021 Trenton Kress
//  This file is part of project: Darkan
//
package com.rs.game.model.entity.npc.combat.impl;

import com.rs.game.World;
import com.rs.game.model.entity.Entity;
import com.rs.game.model.entity.npc.NPC;
import com.rs.game.model.entity.npc.combat.CombatScript;
import com.rs.game.model.entity.npc.combat.NPCCombatDefinitions.AttackStyle;
import com.rs.game.model.entity.npc.familiar.Familiar;
import com.rs.lib.game.Animation;
import com.rs.lib.game.SpotAnim;
import com.rs.lib.util.Utils;

public class DreadFowlCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 6825, 6824 };
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		Familiar familiar = (Familiar) npc;
		boolean usingSpecial = familiar.hasSpecialOn();
		if (usingSpecial) {// priority over regular attack
			npc.setNextAnimation(new Animation(7810));
			npc.setNextSpotAnim(new SpotAnim(1318));
			delayHit(npc, 1, target, getMagicHit(npc, getMaxHit(npc, 40, AttackStyle.MAGE, target)));
			World.sendProjectile(npc, target, 1376, 34, 16, 30, 35, 16, 0);
		} else if (Utils.getRandomInclusive(10) == 0) {// 1/10 chance of random special
			// (weaker)
			npc.setNextAnimation(new Animation(7810));
			npc.setNextSpotAnim(new SpotAnim(1318));
			delayHit(npc, 1, target, getMagicHit(npc, getMaxHit(npc, 30, AttackStyle.MAGE, target)));
			World.sendProjectile(npc, target, 1376, 34, 16, 30, 35, 16, 0);
		} else {
			npc.setNextAnimation(new Animation(7810));
			delayHit(npc, 1, target, getMeleeHit(npc, getMaxHit(npc, 30, AttackStyle.MELEE, target)));
		}
		return npc.getAttackSpeed();
	}
}
