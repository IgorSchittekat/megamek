/*
 * MegaMek - Copyright (C) 2000,2001,2002,2003,2004 Ben Mazur (bmazur@sev.org)
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 */

/*
 * TeleMissileAttackAction.java
 * 
 */

package megamek.common.actions;

import java.util.ArrayList;
import java.util.Enumeration;

import megamek.common.Compute;
import megamek.common.Entity;
import megamek.common.IGame;
import megamek.common.IPlayer;
import megamek.common.Mounted;
import megamek.common.TargetRoll;
import megamek.common.Targetable;
import megamek.common.TeleMissile;
import megamek.common.ToHitData;
import megamek.common.WeaponType;
import megamek.common.options.OptionsConstants;
import megamek.common.weapons.AttackHandler;

/**
 * Represents one tele-controlled missile attack
 * 
 * @author Ben Mazur
 */
public class TeleMissileAttackAction extends AbstractAttackAction {

    /**
     * 
     */
    private static final long serialVersionUID = -1054613811287285482L;
    // only used server-side for manually guided Telemissile attacks
    private transient ArrayList<Mounted> vCounterEquipment;
    
    //Large Craft Point Defense/AMS Bay Stuff
    public int CounterAVInt = 0;
    private boolean pdOverheated = false; // true if counterfire + offensive weapon attacks made this round cause the defending unit to overheat. Used for reporting.
    private boolean amsBayEngagedCap = false; //true if one or more AMS bays engages this attack. Used for reporting if this is a capital missile attack.
    private boolean pdBayEngagedCap = false; // true if one or more point defense bays engages this attack. Used for reporting if this is a capital missile attack.
    private boolean advancedPD = false; //true if advanced StratOps game rule is on

    public TeleMissileAttackAction(Entity attacker, Targetable target) {
        super(attacker.getId(), target.getTargetType(), target.getTargetId());
    }

    public static int getDamageFor(Entity entity) {      
        if(entity instanceof TeleMissile) {
            return ((TeleMissile)entity).getDamageValue();
        }
        return 0;
    }
    
    /**
     * Returns the list of Counter Equipment used against this physical attack
     * This is for AMS assignment to manual tele-operated missiles
     */
    public ArrayList<Mounted> getCounterEquipment() {
        return vCounterEquipment;
    }
    
    /**
     * Adds 'm' to the list of Counter Equipment used against this physical attack
     * This is for AMS assignment to manual tele-operated missiles
     */
    public void addCounterEquipment(Mounted m) {
        if (vCounterEquipment == null) {
            vCounterEquipment = new ArrayList<Mounted>();
        }
        vCounterEquipment.add(m);
    }
    
    /**
     * Checks to see if the basic conditions needed for point defenses to work are in place
     * Since this normally only applies to weaponhandlers, we must recreate it to deal with telemissile
     * entities
     */
    private boolean checkPDConditions(IGame game, Targetable target) {
        advancedPD = game.getOptions().booleanOption(OptionsConstants.ADVAERORULES_STRATOPS_ADV_POINTDEF);
        if ((target == null)
                || (target.getTargetType() != Targetable.TYPE_ENTITY)
                || !advancedPD) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns the heat generated by a large craft's weapons fire declarations during the round
     * Used to determine whether point defenses can engage.
     * @Param e - the entity you wish to get heat data from
     * Since this normally only applies to weaponhandlers, we must recreate it to deal with telemissile
     * entities
     */
    protected int getLargeCraftHeat(Entity e) {
        int totalheat = 0;
        if (e.hasETypeFlag(Entity.ETYPE_DROPSHIP) 
                || e.hasETypeFlag(Entity.ETYPE_JUMPSHIP)) {
            if (e.usesWeaponBays()) {
                for (Enumeration<AttackHandler> i = e.getGame().getAttacks(); i.hasMoreElements();) {
                    AttackHandler ah = i.nextElement();
                    WeaponAttackAction prevAttack = ah.getWaa();
                    if (prevAttack.getEntityId() == e.getId()) {
                        Mounted prevWeapon = e.getEquipment(prevAttack.getWeaponId());
                        for (int wId : prevWeapon.getBayWeapons()) {
                            Mounted bayW = e.getEquipment(wId);
                            totalheat += bayW.getCurrentHeat();
                        }
                    }
                }
            } else {
                for (Enumeration<AttackHandler> i = e.getGame().getAttacks(); i.hasMoreElements();) {
                    AttackHandler ah = i.nextElement();
                    WeaponAttackAction prevAttack = ah.getWaa();
                    if (prevAttack.getEntityId() == e.getId()) {
                        Mounted prevWeapon = e.getEquipment(prevAttack.getWeaponId());
                        totalheat += prevWeapon.getCurrentHeat();
                    }
                }
            }
        }
        return totalheat;
    }
    
    /**
     * Checks to see if this point defense/AMS bay can engage a capital missile
     * This should return true. Only when handling capital missile attacks can this be false.
     */
    protected boolean canEngageCapitalMissile(Mounted counter) {
        if (counter.getBayWeapons().size() < 2) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Sets the appropriate AMS Bay reporting flag depending on what type of missile this is
     * See also TeleMissileAttackAction, which contains a modified version of this to work against 
     * a TeleMissile entity in the physical phase
     */
    protected void setAMSBayReportingFlag() {
    }
    
    /**
     * Sets the appropriate PD Bay reporting flag depending on what type of missile this is
     * See also TeleMissileAttackAction, which contains a modified version of this to work against 
     * a TeleMissile entity in the physical phase
     */
    protected void setPDBayReportingFlag() {
    }
    
    /**
     * Calculates the attack value of point defense weapons used against a missile bay attack
     * This is the main large craft point defense method
     */    
    public int calcCounterAV(IGame game, Targetable target) {
        if (!checkPDConditions(game, target)) {
            return 0;
        }
        int counterAV = 0;
        int amsAV = 0;
        double pdAV = 0;
        Entity entityTarget = (Entity) target;
        // any AMS bay attacks by the target?
        ArrayList<Mounted> lCounters = getCounterEquipment();
        //We need to know how much heat has been assigned to offensive weapons fire by the defender this round
        int weaponHeat = getLargeCraftHeat(entityTarget) + entityTarget.heatBuildup;
        if (null != lCounters) {
            for (Mounted counter : lCounters) {
                // Point defenses only fire vs attacks against the arc they protect
                Entity pdEnt = counter.getEntity();
                
                //We already checked arc when AMS was assigned. No need to worry about fleet missile defense here
                //Telemissiles are entities. Other craft can just shoot at them.
                
                // Point defenses can't fire if they're not ready for any other reason
                if (!(counter.getType() instanceof WeaponType)
                         || !counter.isReady() || counter.isMissing()
                            // shutdown means no Point defenses
                            || pdEnt.isShutDown()) {
                        continue;
                }
                //Point defense/AMS bays with less than 2 weapons cannot engage capital missiles
                if (!canEngageCapitalMissile(counter)) {
                    continue;
                }
                
                //Set up differences between point defense and AMS bays
                boolean isAMSBay = counter.getType().hasFlag(WeaponType.F_AMSBAY);
                boolean isPDBay = counter.getType().hasFlag(WeaponType.F_PDBAY);
                
                //Point defense bays can only fire at one attack per round
                if (isPDBay) {
                    if (counter.isUsedThisRound()) {
                        continue;
                    }
                }
                
                // Now for heat, damage and ammo we need the individual weapons in the bay
                // First, reset the temporary damage counters
                amsAV = 0;
                pdAV = 0;
                for (int wId : counter.getBayWeapons()) {
                    Mounted bayW = pdEnt.getEquipment(wId);
                    Mounted bayWAmmo = bayW.getLinked();
                    WeaponType bayWType = ((WeaponType) bayW.getType());
                    
                    // build up some heat
                    //First Check to see if we have enough heat capacity to fire
                    if ((weaponHeat + bayW.getCurrentHeat()) > pdEnt.getHeatCapacity()) {
                        pdOverheated = true;
                        break;
                    }
                    if (counter.getType().hasFlag(WeaponType.F_HEATASDICE)) {
                        int heatDice = Compute.d6(bayW
                                .getCurrentHeat());
                        pdEnt.heatBuildup += heatDice;
                        weaponHeat += heatDice;
                    } else {
                        pdEnt.heatBuildup += bayW.getCurrentHeat();
                        weaponHeat += bayW.getCurrentHeat();
                    }
                    
                    //Bays use lots of ammo. Check to make sure we haven't run out
                    if (bayWAmmo != null) {
                        if (bayWAmmo.getBaseShotsLeft() == 0) {
                            continue;
                        }
                        // decrement the ammo
                        bayWAmmo.setShotsLeft(Math.max(0,
                            bayWAmmo.getBaseShotsLeft() - 1));
                    }
                    if (isAMSBay) {
                        // get the attack value
                        amsAV += bayWType.getShortAV();
                        // set the ams as having fired, if it did
                        setAMSBayReportingFlag();
                    }
                    if (isPDBay) {
                        // get the attack value
                        pdAV += bayWType.getShortAV();
                        // set the pdbay as having fired, if it was able to
                        counter.setUsedThisRound(true); 
                        setPDBayReportingFlag();
                    }
                }
                // non-AMS only add half their damage, rounded up
                counterAV += (int) Math.ceil(pdAV / 2.0); 
                // AMS add their full damage
                counterAV += amsAV;
            } //end "for Mounted counter"
        } // end check for counterfire
        CounterAVInt = (int) counterAV;
        return counterAV;
    }
    
    /**
     * To-hit number for a charge, assuming that movement has been handled
     */
    public ToHitData toHit(IGame game) {
        return toHit(game, game.getTarget(getTargetType(), getTargetId()));
    }
    
    public ToHitData toHit(IGame game, Targetable target) {
        final Entity ae = getEntity(game);

        // arguments legal?
        if (ae == null) {
            throw new IllegalStateException("Attacker is null");
        }

        // Do to pretreatment of physical attacks, the target may be null.
        if (target == null) {
            return new ToHitData(TargetRoll.IMPOSSIBLE, "Target is null");
        }
        
        if (!game.getOptions().booleanOption(OptionsConstants.BASE_FRIENDLY_FIRE)) {
            // a friendly unit can never be the target of a direct attack.
            if (target.getTargetType() == Targetable.TYPE_ENTITY
                    && (((Entity)target).getOwnerId() == ae.getOwnerId()
                            || (((Entity)target).getOwner().getTeam() != IPlayer.TEAM_NONE
                                    && ae.getOwner().getTeam() != IPlayer.TEAM_NONE
                                    && ae.getOwner().getTeam() == ((Entity)target).getOwner().getTeam())))
                return new ToHitData(TargetRoll.IMPOSSIBLE, "A friendly unit can never be the target of a direct attack.");
        }

        //set the to-hit
        ToHitData toHit = new ToHitData(2, "base");

        TeleMissile tm = (TeleMissile)ae;
        
        //thrust used
        if(ae.mpUsed > 0) 
            toHit.addModifier(ae.mpUsed, "thrust used");
        
        //out of fuel
        if(tm.getCurrentFuel() <= 0) 
            toHit.addModifier(+6, "out of fuel");
        
        //modifiers for the originating unit need to be added later, because
        //they may change as a result of damage
        
        // done!
        return toHit;
    }

}
