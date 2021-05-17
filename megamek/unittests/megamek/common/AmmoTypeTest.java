/*
 * MegaMek -
 * Copyright (C) 2000,2001,2002,2003,2004,2005 Ben Mazur (bmazur@sev.org)
 *
 *  This program is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the Free
 *  Software Foundation; either version 2 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *  for more details.
 */
package megamek.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Enumeration;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

/**
 * Created with IntelliJ IDEA.
 *
 * @version %Id%
 * @lastEditBy Deric "Netzilla" Page (deric dot page at usa dot net)
 * @since 9/21/13 8:13 AM
 */
@RunWith(JUnit4.class)
public class AmmoTypeTest {
    WeaponType mockAC5 = Mockito.mock(WeaponType.class);
    AmmoType mockAC5AmmoType = Mockito.mock(AmmoType.class);
    Mounted mockAmmoAC5 = Mockito.mock(Mounted.class);
    Mounted mockAmmoAC5Empty = Mockito.mock(Mounted.class);
    AmmoType mockAC10AmmoType = Mockito.mock(AmmoType.class);
    Mounted mockAmmoAC10 = Mockito.mock(Mounted.class);
    WeaponType mockPPC = Mockito.mock(WeaponType.class);
    WeaponType mockSRM4 = Mockito.mock(WeaponType.class);
    AmmoType mockSRM4AmmoType = Mockito.mock(AmmoType.class);
    Mounted mockAmmoSrm4 = Mockito.mock(Mounted.class);
    AmmoType mockSRM6AmmoType = Mockito.mock(AmmoType.class);
    Mounted mockAmmoSrm6 = Mockito.mock(Mounted.class);
    AmmoType mockInferno4AmmoType = Mockito.mock(AmmoType.class);
    Mounted mockAmmoInferno4 = Mockito.mock(Mounted.class);
    MiscType notAmmoType = Mockito.mock(MiscType.class);
    Mounted mockNotAmmo = Mockito.mock(Mounted.class);

    @Before
    public void setUp() {
        Mockito.when(mockAC5.getAmmoType()).thenReturn(AmmoType.T_AC);
        Mockito.when(mockAC5.getRackSize()).thenReturn(5);
        Mockito.when(mockPPC.getAmmoType()).thenReturn(AmmoType.T_NA);
        Mockito.when(mockSRM4.getAmmoType()).thenReturn(AmmoType.T_SRM);
        Mockito.when(mockSRM4.getRackSize()).thenReturn(4);
        Mockito.when(mockAC5AmmoType.getAmmoType()).thenReturn(AmmoType.T_AC);
        Mockito.when(mockAC5AmmoType.getRackSize()).thenReturn(5);
        Mockito.when(mockSRM4AmmoType.getAmmoType()).thenReturn(AmmoType.T_SRM);
        Mockito.when(mockSRM4AmmoType.getRackSize()).thenReturn(4);
        Mockito.when(mockSRM4AmmoType.getMunitionType()).thenReturn(AmmoType.M_STANDARD);
        Mockito.when(mockInferno4AmmoType.getAmmoType()).thenReturn(AmmoType.T_SRM);
        Mockito.when(mockInferno4AmmoType.getRackSize()).thenReturn(4);
        Mockito.when(mockInferno4AmmoType.getMunitionType()).thenReturn(AmmoType.M_INFERNO);
        Mockito.when(mockAC10AmmoType.getAmmoType()).thenReturn(AmmoType.T_AC);
        Mockito.when(mockAC10AmmoType.getRackSize()).thenReturn(10);
        Mockito.when(mockSRM6AmmoType.getAmmoType()).thenReturn(AmmoType.T_SRM);
        Mockito.when(mockSRM6AmmoType.getRackSize()).thenReturn(6);
        Mockito.when(mockAmmoSrm4.getType()).thenReturn(mockSRM4AmmoType);
        Mockito.when(mockAmmoSrm4.isAmmoUsable()).thenReturn(true);
        Mockito.when(mockAmmoInferno4.getType()).thenReturn(mockInferno4AmmoType);
        Mockito.when(mockAmmoInferno4.isAmmoUsable()).thenReturn(true);
        Mockito.when(mockAmmoSrm6.getType()).thenReturn(mockSRM6AmmoType);
        Mockito.when(mockAmmoSrm6.isAmmoUsable()).thenReturn(true);
        Mockito.when(mockAmmoAC5.getType()).thenReturn(mockAC5AmmoType);
        Mockito.when(mockAmmoAC5.isAmmoUsable()).thenReturn(true);
        Mockito.when(mockAmmoAC10.getType()).thenReturn(mockAC10AmmoType);
        Mockito.when(mockAmmoAC10.isAmmoUsable()).thenReturn(true);
        Mockito.when(mockAmmoAC5Empty.getType()).thenReturn(mockAC5AmmoType);
        Mockito.when(mockAmmoAC5Empty.isAmmoUsable()).thenReturn(false);
        Mockito.when(mockNotAmmo.getType()).thenReturn(notAmmoType);
        Mockito.doReturn(true).when(mockSRM4AmmoType).equalsAmmoTypeOnly(Mockito.eq(mockSRM4AmmoType));
        Mockito.doReturn(true).when(mockSRM4AmmoType).equalsAmmoTypeOnly(Mockito.eq(mockInferno4AmmoType));
        Mockito.doReturn(true).when(mockInferno4AmmoType).equalsAmmoTypeOnly(Mockito.eq(mockInferno4AmmoType));
        Mockito.doReturn(true).when(mockInferno4AmmoType).equalsAmmoTypeOnly(Mockito.eq(mockSRM4AmmoType));
    }

    @Test
    public void testIsAmmoValid() {
        // Test ammo that matches weapon.
        Assert.assertTrue(AmmoType.isAmmoValid(mockAmmoAC5, mockAC5));
        Assert.assertTrue(AmmoType.isAmmoValid(mockAmmoSrm4, mockSRM4));
        Assert.assertTrue(AmmoType.isAmmoValid(mockAmmoInferno4, mockSRM4));
        // Test an empty ammo bin.
        Assert.assertFalse(AmmoType.isAmmoValid(mockAmmoAC5Empty, mockAC5));
        // Test null ammo.
        Assert.assertFalse(AmmoType.isAmmoValid((Mounted) null, mockAC5));
        Assert.assertFalse(AmmoType.isAmmoValid((AmmoType) null, mockAC5));
        // Test incompatible ammo.
        Assert.assertFalse(AmmoType.isAmmoValid(mockAmmoAC5, mockSRM4));
        Assert.assertFalse(AmmoType.isAmmoValid(mockAmmoSrm4, mockPPC));
        // Test wrong rack size.
        Assert.assertFalse(AmmoType.isAmmoValid(mockAmmoAC10, mockAC5));
        Assert.assertFalse(AmmoType.isAmmoValid(mockAmmoSrm6, mockSRM4));
        // Test something that's not ammo.
        Assert.assertFalse(AmmoType.isAmmoValid(mockNotAmmo, mockAC5));
        assertFalse(AmmoType.isAmmoValid((AmmoType) null, new WeaponType()));
        assertFalse(AmmoType.isAmmoValid((Mounted) null, new WeaponType()));
    }

    @Test
    public void testIsAmmoValid2() {
        AmmoType ammoType = new AmmoType();
        assertTrue(AmmoType.isAmmoValid(ammoType, new WeaponType()));
    }

    @Test
    public void testIsAmmoValid3() {
        Aero entity = new Aero();
        Mounted ammo = new Mounted(entity, new EquipmentType());
        assertFalse(AmmoType.isAmmoValid(ammo, new WeaponType()));
    }

    @Test
    public void testCanSwitchToAmmo() {
        Mounted mockWeapon = Mockito.mock(Mounted.class);
        Mockito.when(mockWeapon.getLinked()).thenReturn(mockAmmoSrm4);
        Assert.assertTrue(AmmoType.canSwitchToAmmo(mockWeapon, mockInferno4AmmoType));
        Assert.assertFalse(AmmoType.canSwitchToAmmo(mockWeapon, mockSRM6AmmoType));
    }

    @Test
    public void testCanSwitchToAmmo2() {
        Aero entity = new Aero();
        Mounted weapon = new Mounted(entity, new EquipmentType());
        assertFalse(AmmoType.canSwitchToAmmo(weapon, new AmmoType()));
    }

    @Test
    public void testCanSwitchToAmmo3() {
        assertFalse(AmmoType.canSwitchToAmmo(null, null));
    }

    @Test
    public void testCanSwitchToAmmo4() {
        assertFalse(AmmoType.canSwitchToAmmo(null, new AmmoType()));
    }

    @Test
    public void testGetMunitionsFor() {
        assertEquals(AmmoType.T_ALAMO, AmmoType.getMunitionsFor(1).size());
    }

    @Test
    public void testConstructor() {
        AmmoType actualAmmoType = new AmmoType();
        assertEquals(0.0, actualAmmoType.getAmmoRatio(), 0.0);
        assertEquals(0, actualAmmoType.getAmmoType());
        assertEquals(0, actualAmmoType.getDamagePerShot());
        assertEquals(AmmoType.M_STANDARD, actualAmmoType.getMunitionType());
        assertEquals(0, actualAmmoType.getRackSize());
        assertEquals(0, actualAmmoType.getShots());
        assertFalse(actualAmmoType.isCapital());
    }

    @Test
    public void testConstructor2() {
        AmmoType actualAmmoType = new AmmoType();
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualAmmoType.tonnage, 0.0);
        assertEquals(0, actualAmmoType.tankslots);
        assertEquals(AmmoType.T_NA, actualAmmoType.svslots);
        assertEquals("", actualAmmoType.shortName);
        assertNull(actualAmmoType.modes);
        assertTrue(actualAmmoType.explosive);
        assertEquals(1, actualAmmoType.criticals);
        assertEquals(0.0, actualAmmoType.bv, 0.0);
        assertFalse(actualAmmoType.isSpreadable());
        assertFalse(actualAmmoType.isOmniFixedOnly());
        assertTrue(actualAmmoType.isMixedTech());
        assertTrue(actualAmmoType.isHittable());
        assertFalse(actualAmmoType.hasInstantModeSwitch());
        assertEquals(0, actualAmmoType.getToHitModifier());
        assertEquals(2, actualAmmoType.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualAmmoType.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualAmmoType.getStaticTechLevel());
        assertEquals("-", actualAmmoType.getStandardRange());
        assertNull(actualAmmoType.getShortName());
        assertEquals("", actualAmmoType.getRulesRefs());
        assertEquals(0.0, actualAmmoType.getRawCost(), 0.0);
        assertNull(actualAmmoType.getInternalName());
        assertEquals("C/A-A-A-A", actualAmmoType.getFullRatingName());
        BigInteger expectedFlags = actualAmmoType.flags;
        assertSame(expectedFlags, actualAmmoType.getFlags());
        assertEquals(AmmoType.T_NA, actualAmmoType.getExtinctionDate());
    }

    @Test
    public void testEqualsAmmoTypeOnly() {
        assertFalse((new AmmoType()).equalsAmmoTypeOnly("Other"));
    }

    @Test
    public void testIsCompatibleWith() {
        AmmoType ammoType = new AmmoType();
        assertFalse(ammoType.isCompatibleWith(new AmmoType()));
    }

    @Test
    public void testIs() {
        assertFalse((new AmmoType()).is(1));
    }

    @Test
    public void testGetKgPerShot() {
        assertEquals(Double.POSITIVE_INFINITY, (new AmmoType()).getKgPerShot(), 0.0);
    }

    @Test
    public void testCanAeroUse() {
        assertTrue((new AmmoType()).canAeroUse());
        assertTrue((new AmmoType()).canAeroUse(true));
    }

    @Test
    public void testCanClearMinefield() {
        assertFalse(AmmoType.canClearMinefield(new AmmoType()));
        assertFalse(AmmoType.canClearMinefield(null));
    }

    @Test
    public void testCanDeliverMinefield() {
        assertFalse(AmmoType.canDeliverMinefield(new AmmoType()));
        assertFalse(AmmoType.canDeliverMinefield(null));
    }

    @Test
    public void testGetProtoBV() {
        assertEquals(-0.0, (new AmmoType()).getProtoBV(1), 0.0);
    }

    @Test
    public void testGetBABV() {
        assertEquals(-0.0, (new AmmoType()).getBABV(), 0.0);
    }

    @Test
    public void testGetShortName() {
        assertNull((new AmmoType()).getShortName());
    }

    @Test
    public void testCreateAmmoType() {
        Enumeration<EquipmentType> allTypes = EquipmentType.getAllTypes();
        while (allTypes.hasMoreElements()) {
            EquipmentType et = allTypes.nextElement();
            if (et instanceof AmmoType) {
                AmmoType ammo = (AmmoType) et;
                switch (ammo.getInternalName()) {
                    case ("ISAMS Ammo"):
                        createISAMSAmmo(ammo);
                        break;
                    case ("CLAMS Ammo"):
                        createCLAMSAmmo(ammo);
                        break;
                    case ("ISArrowIVAmmo"):
                        createISArrowIVAmmo(ammo);
                        break;
                    case ("CLArrowIVAmmo"):
                        createCLArrowIVAmmo(ammo);
                        break;
                    case ("ISLongTomAmmo"):
                        createLongTomAmmo(ammo);
                        break;
                    case ("ISSniperAmmo"):
                        createSniperAmmo(ammo);
                        break;
                    case ("ISThumperAmmo"):
                        createThumperAmmo(ammo);
                        break;
                    case ("ISCruiseMissile50Ammo"):
                        createISCruiseMissile50Ammo(ammo);
                        break;
                    case ("ISCruiseMissile70Ammo"):
                        createISCruiseMissile70Ammo(ammo);
                        break;
                    case ("ISCruiseMissile90Ammo"):
                        createISCruiseMissile90Ammo(ammo);
                        break;
                    case ("ISCruiseMissile120Ammo"):
                        createISCruiseMissile120Ammo(ammo);
                        break;
                    case ("ISLongTomCannonAmmo"):
                        createISLongTomCannonAmmo(ammo);
                        break;
                    case ("ISSniperCannonAmmo"):
                        createISSniperCannonAmmo(ammo);
                        break;
                    case ("ISThumperCannonAmmo"):
                        createISThumperCannonAmmo(ammo);
                        break;
                    case ("ISBATubeArtilleryAmmo"):
                        createBATubeArtyAmmo(ammo);
                        break;
                    case ("IS Ammo AC/2"):
                        createISAC2Ammo(ammo);
                        break;
                    case ("IS Ammo AC/5"):
                        createISAC5Ammo(ammo);
                        break;
                    case ("IS Ammo AC/10"):
                        createISAC10Ammo(ammo);
                        break;
                    case ("IS Ammo AC/20"):
                        createISAC20Ammo(ammo);
                        break;
                    case ("IS Ammo LAC/2"):
                        createISLAC2Ammo(ammo);
                        break;
                    case ("IS Ammo LAC/5"):
                        createISLAC5Ammo(ammo);
                        break;
                    case ("Clan ProtoMech AC/2 Ammo"):
                        createCLPROAC2Ammo(ammo);
                        break;
                    case ("Clan ProtoMech AC/4 Ammo"):
                        createCLPROAC4Ammo(ammo);
                        break;
                    case ("Clan ProtoMech AC/8 Ammo"):
                        createCLPROAC8Ammo(ammo);
                        break;
                    case ("IS Ammo HVAC/2"):
                        createISHVAC2Ammo(ammo);
                        break;
                    case ("IS Ammo HVAC/5"):
                        createISHVAC5Ammo(ammo);
                        break;
                    case ("IS Ammo HVAC/10"):
                        createISHVAC10Ammo(ammo);
                        break;
                    case ("Clan LB 2-X Cluster Ammo"):
                        createCLLB2XClusterAmmo(ammo);
                        break;
                    case ("Clan LB 5-X Cluster Ammo"):
                        createCLLB5XClusterAmmo(ammo);
                        break;
                    case ("Clan LB 10-X Cluster Ammo"):
                        createCLLB10XClusterAmmo(ammo);
                        break;
                    case ("Clan LB 20-X Cluster Ammo"):
                        createCLLB20XClusterAmmo(ammo);
                        break;
                    case ("IS LB 2-X Cluster Ammo"):
                        createISLB2XClusterAmmo(ammo);
                        break;
                    case ("IS LB 5-X Cluster Ammo"):
                        createISLB5XClusterAmmo(ammo);
                        break;
                    case ("IS LB 10-X Cluster Ammo"):
                        createISLB10XClusterAmmo(ammo);
                        break;
                    case ("IS LB 20-X Cluster Ammo"):
                        createISLB20XClusterAmmo(ammo);
                        break;
                    case ("Clan LB 2-X AC Ammo"):
                        createCLLB2XAmmo(ammo);
                        break;
                    case ("Clan LB 5-X AC Ammo"):
                        createCLLB5XAmmo(ammo);
                        break;
                    case ("Clan LB 10-X AC Ammo"):
                        createCLLB10XAmmo(ammo);
                        break;
                    case ("Clan LB 20-X AC Ammo"):
                        createCLLB20XAmmo(ammo);
                        break;
                    case ("IS LB 2-X AC Ammo"):
                        createISLB2XAmmo(ammo);
                        break;
                    case ("IS LB 5-X AC Ammo"):
                        createISLB5XAmmo(ammo);
                        break;
                    case ("IS LB 10-X AC Ammo"):
                        createISLB10XAmmo(ammo);
                        break;
                    case ("IS LB 20-X AC Ammo"):
                        createISLB20XAmmo(ammo);
                        break;
                    case ("Clan Ultra AC/2 Ammo"):
                        createCLUltra2Ammo(ammo);
                        break;
                    case ("Clan Ultra AC/5 Ammo"):
                        createCLUltra5Ammo(ammo);
                        break;
                    case ("Clan Ultra AC/10 Ammo"):
                        createCLUltra10Ammo(ammo);
                        break;
                    case ("Clan Ultra AC/20 Ammo"):
                        createCLUltra20Ammo(ammo);
                        break;
                    case ("IS Ultra AC/2 Ammo"):
                        createISUltra2Ammo(ammo);
                        break;
                    case ("IS Ultra AC/5 Ammo"):
                        createISUltra5Ammo(ammo);
                        break;
                    case ("IS Ultra AC/10 Ammo"):
                        createISUltra10Ammo(ammo);
                        break;
                    case ("IS Ultra AC/20 Ammo"):
                        createISUltra20Ammo(ammo);
                        break;
                    case ("ISRotaryAC2 Ammo"):
                        createISRotary2Ammo(ammo);
                        break;
                    case ("ISRotaryAC5 Ammo"):
                        createISRotary5Ammo(ammo);
                        break;
                    case ("CLRotaryAC2 Ammo"):
                        createCLRotary2Ammo(ammo);
                        break;
                    case ("CLRotaryAC5 Ammo"):
                        createCLRotary5Ammo(ammo);
                        break;
                    case ("IS Ammo Light Rifle"):
                        createISLightRifleAmmo(ammo);
                        break;
                    case ("IS Ammo Medium Rifle"):
                        createISMediumRifleAmmo(ammo);
                        break;
                    case ("IS Ammo Heavy Rifle"):
                        createISHeavyRifleAmmo(ammo);
                        break;
                    case ("CLSmallChemLaserAmmo"):
                        createCLSmallChemicalLaserAmmo(ammo);
                        break;
                    case ("CLMediumChemLaserAmmo"):
                        createCLMediumChemicalLaserAmmo(ammo);
                        break;
                    case ("CLLargeChemLaserAmmo"):
                        createCLLargeChemicalLaserAmmo(ammo);
                        break;
                    case ("IS Heavy Flamer Ammo"):
                        createISHeavyFlamerAmmo(ammo);
                        break;
                    case ("CL Heavy Flamer Ammo"):
                        createCLHeavyFlamerAmmo(ammo);
                        break;
                    case ("IS Vehicle Flamer Ammo"):
                        createISVehicleFlamerAmmo(ammo);
                        break;
                    case ("ISFluidGun Ammo"):
                        createISFluidGunAmmo(ammo);
                        break;
                    case ("CLFluidGun Ammo"):
                        createCLFluidGunAmmo(ammo);
                        break;
                    case ("IS Gauss Ammo"):
                        createISGaussAmmo(ammo);
                        break;
                    case ("Clan Gauss Ammo"):
                        createCLGaussAmmo(ammo);
                        break;
                    case ("IS Light Gauss Ammo"):
                        createISLTGaussAmmo(ammo);
                        break;
                    case ("ISHeavyGauss Ammo"):
                        createISHVGaussAmmo(ammo);
                        break;
                    case ("CLAPGaussRifle Ammo"):
                        createCLAPGaussRifleAmmo(ammo);
                        break;
                    case ("Hyper-Assault Gauss Rifle/20 Ammo"):
                        createCLHAG20Ammo(ammo);
                        break;
                    case ("Hyper-Assault Gauss Rifle/30 Ammo"):
                        createCLHAG30Ammo(ammo);
                        break;
                    case ("Hyper-Assault Gauss Rifle/40 Ammo"):
                        createCLHAG40Ammo(ammo);
                        break;
                    case ("ISImprovedHeavyGauss Ammo"):
                        createISIHVGaussAmmo(ammo);
                        break;
                    case ("ISMagshotGR Ammo"):
                        createISMagshotGRAmmo(ammo);
                        break;
                    case ("Silver Bullet Gauss Ammo"):
                        createISSBGaussRifleAmmo(ammo);
                        break;
                    case ("IS Ammo VGL"):
                        createISVGLAmmo(ammo);
                        break;
                    case ("CL Ammo VGL"):
                        createCLVGLAmmo(ammo);
                        break;
                    case ("IS Ammo MG - Full"):
                        createISMGAmmo(ammo);
                        break;
                    case ("Clan Machine Gun Ammo - Full"):
                        createCLMGAmmo(ammo);
                        break;
                    case ("IS Machine Gun Ammo - Half"):
                        createISMGAmmoHalf(ammo);
                        break;
                    case ("Clan Machine Gun Ammo - Half"):
                        createCLMGAmmoHalf(ammo);
                        break;
                    case ("IS Light Machine Gun Ammo - Full"):
                        createISLightMGAmmo(ammo);
                        break;
                    case ("Clan Light Machine Gun Ammo - Full"):
                        createCLLightMGAmmo(ammo);
                        break;
                    case ("IS Light Machine Gun Ammo - Half"):
                        createISLightMGAmmoHalf(ammo);
                        break;
                    case ("Clan Light Machine Gun Ammo - Half"):
                        createCLLightMGAmmoHalf(ammo);
                        break;
                    case ("IS Heavy Machine Gun Ammo - Full"):
                        createISHeavyMGAmmo(ammo);
                        break;
                    case ("IS Heavy Machine Gun Ammo - Half"):
                        createISHeavyMGAmmoHalf(ammo);
                        break;
                    case ("Clan Heavy Machine Gun Ammo - Full"):
                        createCLHeavyMGAmmo(ammo);
                        break;
                    case ("Clan Heavy Machine Gun Ammo - Half"):
                        createCLHeavyMGAmmoHalf(ammo);
                        break;
                    case ("Clan Ammo ATM-3"):
                        createCLATM3Ammo(ammo);
                        break;
                    case ("Clan Ammo ATM-6"):
                        createCLATM6Ammo(ammo);
                        break;
                    case ("Clan Ammo ATM-9"):
                        createCLATM9Ammo(ammo);
                        break;
                    case ("Clan Ammo ATM-12"):
                        createCLATM12Ammo(ammo);
                        break;
                    case ("Clan Ammo ATM-3 ER"):
                        createCLATM3ERAmmo(ammo);
                        break;
                    case ("Clan Ammo ATM-6 ER"):
                        createCLATM6ERAmmo(ammo);
                        break;
                    case ("Clan Ammo ATM-9 ER"):
                        createCLATM9ERAmmo(ammo);
                        break;
                    case ("Clan Ammo ATM-12 ER"):
                        createCLATM12ERAmmo(ammo);
                        break;
                    case ("Clan Ammo ATM-3 HE"):
                        createCLATM3HEAmmo(ammo);
                        break;
                    case ("Clan Ammo ATM-6 HE"):
                        createCLATM6HEAmmo(ammo);
                        break;
                    case ("Clan Ammo ATM-9 HE"):
                        createCLATM9HEAmmo(ammo);
                        break;
                    case ("Clan Ammo ATM-12 HE"):
                        createCLATM12HEAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-3"):
                        createCLIATM3Ammo(ammo);
                        break;
                    case ("Clan Ammo iATM-6"):
                        createCLIATM6Ammo(ammo);
                        break;
                    case ("Clan Ammo iATM-9"):
                        createCLIATM9Ammo(ammo);
                        break;
                    case ("Clan Ammo iATM-12"):
                        createCLIATM12Ammo(ammo);
                        break;
                    case ("Clan Ammo iATM-3 ER"):
                        createCLIATM3ERAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-6 ER"):
                        createCLIATM6ERAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-9 ER"):
                        createCLIATM9ERAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-12 ER"):
                        createCLIATM12ERAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-3 HE"):
                        createCLIATM3HEAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-6 HE"):
                        createCLIATM6HEAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-9 HE"):
                        createCLIATM9HEAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-12 HE"):
                        createCLIATM12HEAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-3 IIW"):
                        createCLIATM3IIWAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-6 IIW"):
                        createCLIATM6IIWAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-9 IIW"):
                        createCLIATM9IIWAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-12 IIW"):
                        createCLIATM12IIWAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-3 IMP"):
                        createCLIATM3IMPAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-6 IMP"):
                        createCLIATM6IMPAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-9 IMP"):
                        createCLIATM9IMPAmmo(ammo);
                        break;
                    case ("Clan Ammo iATM-12 IMP"):
                        createCLIATM12IMPAmmo(ammo);
                        break;
                    case ("IS Ammo LRM-5"):
                        createISLRM5Ammo(ammo);
                        break;
                    case ("IS Ammo LRM-10"):
                        createISLRM10Ammo(ammo);
                        break;
                    case ("IS Ammo LRM-15"):
                        createISLRM15Ammo(ammo);
                        break;
                    case ("IS Ammo LRM-20"):
                        createISLRM20Ammo(ammo);
                        break;
                    case ("ISEnhancedLRM5 Ammo"):
                        createISEnhancedLRM5Ammo(ammo);
                        break;
                    case ("ISEnhancedLRM10 Ammo"):
                        createISEnhancedLRM10Ammo(ammo);
                        break;
                    case ("ISEnhancedLRM15 Ammo"):
                        createISEnhancedLRM15Ammo(ammo);
                        break;
                    case ("ISEnhancedLRM20 Ammo"):
                        createISEnhancedLRM20Ammo(ammo);
                        break;
                    case ("IS Ammo Extended LRM-5"):
                        createISExtendedLRM5Ammo(ammo);
                        break;
                    case ("IS Ammo Extended LRM-10"):
                        createISExtendedLRM10Ammo(ammo);
                        break;
                    case ("IS Ammo Extended LRM-15"):
                        createISExtendedLRM15Ammo(ammo);
                        break;
                    case ("IS Ammo Extended LRM-20"):
                        createISExtendedLRM20Ammo(ammo);
                        break;
                    case ("Clan Ammo LRM-5"):
                        createCLLRM5Ammo(ammo);
                        break;
                    case ("Clan Ammo LRM-10"):
                        createCLLRM10Ammo(ammo);
                        break;
                    case ("Clan Ammo LRM-15"):
                        createCLLRM15Ammo(ammo);
                        break;
                    case ("Clan Ammo LRM-20"):
                        createCLLRM20Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 5 Ammo"):
                        createCLStreakLRM5Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 10 Ammo"):
                        createCLStreakLRM10Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 15 Ammo"):
                        createCLStreakLRM15Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 20 Ammo"):
                        createCLStreakLRM20Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 1 Ammo"):
                        createCLStreakLRM1Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 2 Ammo"):
                        createCLStreakLRM2Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 3 Ammo"):
                        createCLStreakLRM3Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 4 Ammo"):
                        createCLStreakLRM4Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 6 Ammo"):
                        createCLStreakLRM6Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 7 Ammo"):
                        createCLStreakLRM7Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 8 Ammo"):
                        createCLStreakLRM8Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 9 Ammo"):
                        createCLStreakLRM9Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 11 Ammo"):
                        createCLStreakLRM11Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 12 Ammo"):
                        createCLStreakLRM12Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 13 Ammo"):
                        createCLStreakLRM13Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 14 Ammo"):
                        createCLStreakLRM14Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 16 Ammo"):
                        createCLStreakLRM16Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 17 Ammo"):
                        createCLStreakLRM17Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 18 Ammo"):
                        createCLStreakLRM18Ammo(ammo);
                        break;
                    case ("Clan Streak LRM 19 Ammo"):
                        createCLStreakLRM19Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-1"):
                        createCLLRM1Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-2"):
                        createCLLRM2Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-3"):
                        createCLLRM3Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-4"):
                        createCLLRM4Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-6"):
                        createCLLRM6Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-7"):
                        createCLLRM7Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-8"):
                        createCLLRM8Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-9"):
                        createCLLRM9Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-11"):
                        createCLLRM11Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-12"):
                        createCLLRM12Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-13"):
                        createCLLRM13Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-14"):
                        createCLLRM14Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-16"):
                        createCLLRM16Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-17"):
                        createCLLRM17Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-18"):
                        createCLLRM18Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRM-19"):
                        createCLLRM19Ammo(ammo);
                        break;
                    case ("IS MRM 10 Ammo"):
                        createISMRM10Ammo(ammo);
                        break;
                    case ("IS MRM 20 Ammo"):
                        createISMRM20Ammo(ammo);
                        break;
                    case ("IS MRM 30 Ammo"):
                        createISMRM30Ammo(ammo);
                        break;
                    case ("IS MRM 40 Ammo"):
                        createISMRM40Ammo(ammo);
                        break;
                    case ("IS Ammo SRM-2"):
                        createISSRM2Ammo(ammo);
                        break;
                    case ("IS Ammo SRM-4"):
                        createISSRM4Ammo(ammo);
                        break;
                    case ("IS Ammo SRM-6"):
                        createISSRM6Ammo(ammo);
                        break;
                    case ("Clan Ammo SRM-1"):
                        createCLSRM1Ammo(ammo);
                        break;
                    case ("Clan Ammo SRM-2"):
                        createCLSRM2Ammo(ammo);
                        break;
                    case ("Clan Ammo SRM-3"):
                        createCLSRM3Ammo(ammo);
                        break;
                    case ("Clan Ammo SRM-4"):
                        createCLSRM4Ammo(ammo);
                        break;
                    case ("Clan Ammo SRM-5"):
                        createCLSRM5Ammo(ammo);
                        break;
                    case ("Clan Ammo SRM-6"):
                        createCLSRM6Ammo(ammo);
                        break;
                    case ("IS Ammo MML-3 LRM"):
                        createISMML3LRMAmmo(ammo);
                        break;
                    case ("IS Ammo MML-3 SRM"):
                        createISMML3SRMAmmo(ammo);
                        break;
                    case ("IS Ammo MML-5 LRM"):
                        createISMML5LRMAmmo(ammo);
                        break;
                    case ("IS Ammo MML-5 SRM"):
                        createISMML5SRMAmmo(ammo);
                        break;
                    case ("IS Ammo MML-7 LRM"):
                        createISMML7LRMAmmo(ammo);
                        break;
                    case ("IS Ammo MML-7 SRM"):
                        createISMML7SRMAmmo(ammo);
                        break;
                    case ("IS Ammo MML-9 LRM"):
                        createISMML9LRMAmmo(ammo);
                        break;
                    case ("IS Ammo MML-9 SRM"):
                        createISMML9SRMAmmo(ammo);
                        break;
                    case ("IS Ammo RL-10"):
                        createISRL10Ammo(ammo);
                        break;
                    case ("IS Ammo RL-15"):
                        createISRL15Ammo(ammo);
                        break;
                    case ("IS Ammo RL-20"):
                        createISRL20Ammo(ammo);
                        break;
                    case ("Clan Streak SRM 2 Ammo"):
                        createCLStreakSRM2Ammo(ammo);
                        break;
                    case ("Clan Streak SRM 4 Ammo"):
                        createCLStreakSRM4Ammo(ammo);
                        break;
                    case ("Clan Streak SRM 6 Ammo"):
                        createCLStreakSRM6Ammo(ammo);
                        break;
                    case ("Clan Streak SRM 1 Ammo"):
                        createCLStreakSRM1Ammo(ammo);
                        break;
                    case ("Clan Streak SRM 3 Ammo"):
                        createCLStreakSRM3Ammo(ammo);
                        break;
                    case ("Clan Streak SRM 5 Ammo"):
                        createCLStreakSRM5Ammo(ammo);
                        break;
                    case ("IS Streak SRM 2 Ammo"):
                        createISStreakSRM2Ammo(ammo);
                        break;
                    case ("IS Streak SRM 4 Ammo"):
                        createISStreakSRM4Ammo(ammo);
                        break;
                    case ("IS Streak SRM 6 Ammo"):
                        createISStreakSRM6Ammo(ammo);
                        break;
                    case ("ISNarc Pods"):
                        createISNarcAmmo(ammo);
                        break;
                    case ("ISNarc ExplosivePods"):
                        createISNarcExplosiveAmmo(ammo);
                        break;
                    case ("CLNarc Explosive Pods"):
                        createCLNarcExplosiveAmmo(ammo);
                        break;
                    case ("ISiNarc Pods"):
                        createISiNarcAmmo(ammo);
                        break;
                    case ("ISiNarc ECM Pods"):
                        createISiNarcECMAmmo(ammo);
                        break;
                    case ("ISiNarc Explosive Pods"):
                        createISiNarcExplosiveAmmo(ammo);
                        break;
                    case ("ISiNarc Haywire Pods"):
                        createISiNarcHaywireAmmo(ammo);
                        break;
                    case ("ISiNarc Nemesis Pods"):
                        createISiNarcNemesisAmmo(ammo);
                        break;
                    case ("IS Ammo LRTorpedo-5"):
                        createISLRT5Ammo(ammo);
                        break;
                    case ("IS Ammo LRTorpedo-10"):
                        createISLRT10Ammo(ammo);
                        break;
                    case ("IS Ammo LRTorpedo-15"):
                        createISLRT15Ammo(ammo);
                        break;
                    case ("IS Ammo LRTorpedo-20"):
                        createISLRT20Ammo(ammo);
                        break;
                    case ("IS Ammo SRTorpedo-2"):
                        createISSRT2Ammo(ammo);
                        break;
                    case ("IS Ammo SRTorpedo-4"):
                        createISSRT4Ammo(ammo);
                        break;
                    case ("IS Ammo SRTorpedo-6"):
                        createISSRT6Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-1"):
                        createCLLRT1Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-2"):
                        createCLLRT2Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-3"):
                        createCLLRT3Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-4"):
                        createCLLRT4Ammo(ammo);
                        break;
                    case ("Clan Ammo LRTorpedo-5"):
                        createCLLRT5Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-6"):
                        createCLLRT6Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-7"):
                        createCLLRT7Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-8"):
                        createCLLRT8Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-9"):
                        createCLLRT9Ammo(ammo);
                        break;
                    case ("Clan Ammo LRTorpedo-10"):
                        createCLLRT10Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-11"):
                        createCLLRT11Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-12"):
                        createCLLRT12Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-13"):
                        createCLLRT13Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-14"):
                        createCLLRT14Ammo(ammo);
                        break;
                    case ("Clan Ammo LRTorpedo-15"):
                        createCLLRT15Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-16"):
                        createCLLRT16Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-17"):
                        createCLLRT17Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-18"):
                        createCLLRT18Ammo(ammo);
                        break;
                    case ("Clan Ammo Protomech LRTorpedo-19"):
                        createCLLRT19Ammo(ammo);
                        break;
                    case ("Clan Ammo LRTorpedo-20"):
                        createCLLRT20Ammo(ammo);
                        break;
                    case ("Clan Ammo SRTorpedo-1"):
                        createCLSRT1Ammo(ammo);
                        break;
                    case ("Clan Ammo SRTorpedo-2"):
                        createCLSRT2Ammo(ammo);
                        break;
                    case ("Clan Ammo SRTorpedo-3"):
                        createCLSRT3Ammo(ammo);
                        break;
                    case ("Clan Ammo SRTorpedo-4"):
                        createCLSRT4Ammo(ammo);
                        break;
                    case ("Clan Ammo SRTorpedo-5"):
                        createCLSRT5Ammo(ammo);
                        break;
                    case ("Clan Ammo SRTorpedo-6"):
                        createCLSRT6Ammo(ammo);
                        break;
                    case ("IS Ammo SC Mortar-1"):
                        createISAPMortar1Ammo(ammo);
                        break;
                    case ("IS Ammo SC Mortar-2"):
                        createISAPMortar2Ammo(ammo);
                        break;
                    case ("IS Ammo SC Mortar-4"):
                        createISAPMortar4Ammo(ammo);
                        break;
                    case ("IS Ammo SC Mortar-8"):
                        createISAPMortar8Ammo(ammo);
                        break;
                    case ("Clan Ammo SC Mortar-1"):
                        createCLAPMortar1Ammo(ammo);
                        break;
                    case ("Clan Ammo SC Mortar-2"):
                        createCLAPMortar2Ammo(ammo);
                        break;
                    case ("Clan Ammo SC Mortar-4"):
                        createCLAPMortar4Ammo(ammo);
                        break;
                    case ("Clan Ammo SC Mortar-8"):
                        createCLAPMortar8Ammo(ammo);
                        break;
                    case ("ISPlasmaRifleAmmo"):
                        createISPlasmaRifleAmmo(ammo);
                        break;
                    case ("CLPlasmaCannonAmmo"):
                        createCLPlasmaCannonAmmo(ammo);
                        break;
                    case ("ISAPDS Ammo"):
                        createISAPDSAmmo(ammo);
                        break;
                    case ("Taser Ammo"):
                        createISMekTaserAmmo(ammo);
                        break;
                    case ("Ammo Light Mass Driver"):
                        createLightMassDriverAmmo(ammo);
                        break;
                    case ("Ammo Medium Mass Driver"):
                        createMediumMassDriverAmmo(ammo);
                        break;
                    case ("Ammo Heavy Mass Driver"):
                        createHeavyMassDriverAmmo(ammo);
                        break;
                    case ("Ammo Light N-Gauss"):
                        createLightNGaussAmmo(ammo);
                        break;
                    case ("Ammo Medium N-Gauss"):
                        createMediumNGaussAmmo(ammo);
                        break;
                    case ("Ammo Heavy N-Gauss"):
                        createHeavyNGaussAmmo(ammo);
                        break;
                    case ("Ammo NAC/10"):
                        createNAC10Ammo(ammo);
                        break;
                    case ("Ammo NAC/20"):
                        createNAC20Ammo(ammo);
                        break;
                    case ("Ammo NAC/25"):
                        createNAC25Ammo(ammo);
                        break;
                    case ("Ammo NAC/30"):
                        createNAC30Ammo(ammo);
                        break;
                    case ("Ammo NAC/35"):
                        createNAC35Ammo(ammo);
                        break;
                    case ("Ammo NAC/40"):
                        createNAC40Ammo(ammo);
                        break;
                    case ("Ammo Barracuda"):
                        createBarracudaAmmo(ammo);
                        break;
                    case ("Ammo White Shark"):
                        createWhiteSharkAmmo(ammo);
                        break;
                    case ("Ammo Killer Whale"):
                        createKillerWhaleAmmo(ammo);
                        break;
                    case ("Ammo Barracuda-T"):
                        createBarracudaTAmmo(ammo);
                        break;
                    case ("Ammo White Shark-T"):
                        createWhiteSharkTAmmo(ammo);
                        break;
                    case ("Ammo Killer Whale-T"):
                        createKillerWhaleTAmmo(ammo);
                        break;
                    case ("Ammo KrakenT"):
                        createKrakenAmmo(ammo);
                        break;
                    case ("Ammo Kraken"):
                        createKrakenMAmmo(ammo);
                        break;
                    case ("Ammo Screen"):
                        createScreenLauncherAmmo(ammo);
                        break;
                    case ("Ammo Light SCC"):
                        createLightSCCAmmo(ammo);
                        break;
                    case ("Ammo Medium SCC"):
                        createMediumSCCAmmo(ammo);
                        break;
                    case ("Ammo Heavy SCC"):
                        createHeavySCCAmmo(ammo);
                        break;
                    case ("Ammo Manta Ray"):
                        createMantaRayAmmo(ammo);
                        break;
                    case ("Ammo Swordfish"):
                        createSwordfishAmmo(ammo);
                        break;
                    case ("Ammo Stringray"):
                        createStingrayAmmo(ammo);
                        break;
                    case ("Ammo Piranha"):
                        createPiranhaAmmo(ammo);
                        break;
                    case ("Ammo AR10 Barracuda"):
                        createAR10BarracudaAmmo(ammo);
                        break;
                    case ("Ammo AR10 Killer Whale"):
                        createAR10KillerWhaleAmmo(ammo);
                        break;
                    case ("Ammo AR10 White Shark"):
                        createAR10WhiteSharkAmmo(ammo);
                        break;
                    case ("Ammo AR10 Barracuda-T"):
                        createAR10BarracudaTAmmo(ammo);
                        break;
                    case ("Ammo AR10 Killer Whale-T"):
                        createAR10KillerWhaleTAmmo(ammo);
                        break;
                    case ("Ammo AR10 White Shark-T"):
                        createAR10WhiteSharkTAmmo(ammo);
                        break;
                    case ("IS Ammo Nail/Rivet - Full"):
                        createISNailRivetGunAmmo(ammo);
                        break;
                    case ("IS Ammo Nail/Rivet - Half"):
                        createISNailRivetGunAmmoHalf(ammo);
                        break;
                    case ("ISC3Sensors"):
                        createISC3RemoteSensorAmmo(ammo);
                        break;
                    case ("IS Ammo Thunderbolt-5"):
                        createISThunderbolt5Ammo(ammo);
                        break;
                    case ("IS Ammo Thunderbolt-10"):
                        createISThunderbolt10Ammo(ammo);
                        break;
                    case ("IS Ammo Thunderbolt-15"):
                        createISThunderbolt15Ammo(ammo);
                        break;
                    case ("IS Ammo Thunderbolt-20"):
                        createISThunderbolt20Ammo(ammo);
                        break;
                    case ("IS Ammo AC/2 Primitive"):
                        createISAC2pAmmo(ammo);
                        break;
                    case ("IS Ammo AC/5 Primitive"):
                        createISAC5pAmmo(ammo);
                        break;
                    case ("IS Ammo AC/10 Primitive"):
                        createISAC10pAmmo(ammo);
                        break;
                    case ("IS Ammo AC/20 Primitive"):
                        createISAC20pAmmo(ammo);
                        break;
                    case ("IS Ammo LRM-5 Primitive"):
                        createISLRM5pAmmo(ammo);
                        break;
                    case ("IS Ammo LRM-10 Primitive"):
                        createISLRM10pAmmo(ammo);
                        break;
                    case ("IS Ammo LRM-15 Primitive"):
                        createISLRM15pAmmo(ammo);
                        break;
                    case ("IS Ammo LRM-20 Primitive"):
                        createISLRM20pAmmo(ammo);
                        break;
                    case ("IS Ammo SRM-2 Primitive"):
                        createISSRM2pAmmo(ammo);
                        break;
                    case ("IS Ammo SRM-4 Primitive"):
                        createISSRM4pAmmo(ammo);
                        break;
                    case ("IS Ammo SRM-6 Primitive"):
                        createISSRM6pAmmo(ammo);
                        break;
                    case ("ISPrimitiveLongTomAmmo"):
                        createISPrimitiveLongTomAmmo(ammo);
                        break;
                    case ("ProtoTypeArrowIVAmmo"):
                        createPrototypeArrowIVAmmo(ammo);
                        break;
                    case ("CLIMPAmmoAC2"):
                        createCLImprovedAC2Ammo(ammo);
                        break;
                    case ("CLIMPAmmoAC5"):
                        createCLImprovedAC5Ammo(ammo);
                        break;
                    case ("CLIMPAmmoAC10"):
                        createCLImprovedAC10Ammo(ammo);
                        break;
                    case ("CLIMPAmmoAC20"):
                        createCLImprovedAC20Ammo(ammo);
                        break;
                    case ("ClanImprovedLRM5Ammo"):
                        createCLImprovedLRM5Ammo(ammo);
                        break;
                    case ("ClanImprovedLRM10Ammo"):
                        createCLImprovedLRM10Ammo(ammo);
                        break;
                    case ("ClanImprovedLRM15Ammo"):
                        createCLImprovedLRM15Ammo(ammo);
                        break;
                    case ("ClanImprovedLRM20Ammo"):
                        createCLImprovedLRM20Ammo(ammo);
                        break;
                    case ("CLImpGaussAmmo"):
                        createCLImprovedGaussAmmo(ammo);
                        break;
                    case ("ClanImpAmmoSRM2"):
                        createCLImprovedSRM2Ammo(ammo);
                        break;
                    case ("ClImpAmmoSRM4"):
                        createCLImprovedSRM4Ammo(ammo);
                        break;
                    case ("CLImpAmmoSRM6"):
                        createCLImprovedSRM6Ammo(ammo);
                        break;
                    case ("BA-Micro Bomb Ammo"):
                        createBAMicroBombAmmo(ammo);
                        break;
                    case ("Clan Torpedo/LRM5 Ammo"):
                        createCLTorpedoLRM5Ammo(ammo);
                        break;
                    case ("BA-Mine Launcher Ammo"):
                        createBAMineLauncherAmmo(ammo);
                        break;
                    case ("Clan Heavy Machine Gun Ammo - Proto"):
                        createCLPROHeavyMGAmmo(ammo);
                        break;
                    case ("Clan Machine Gun Ammo - Proto"):
                        createCLPROMGAmmo(ammo);
                        break;
                    case ("Clan Light Machine Gun Ammo - Proto"):
                        createCLPROLightMGAmmo(ammo);
                        break;
                    case ("IS BA Ammo LRM-1"):
                        createBAISLRM1Ammo(ammo);
                        break;
                    case ("IS BA Ammo LRM-2"):
                        createBAISLRM2Ammo(ammo);
                        break;
                    case ("IS BA Ammo LRM-3"):
                        createBAISLRM3Ammo(ammo);
                        break;
                    case ("IS BA Ammo LRM-4"):
                        createBAISLRM4Ammo(ammo);
                        break;
                    case ("IS BA Ammo LRM-5"):
                        createBAISLRM5Ammo(ammo);
                        break;
                    case ("BACL Ammo LRM-1"):
                        createBACLLRM1Ammo(ammo);
                        break;
                    case ("BACL Ammo LRM-2"):
                        createBACLLRM2Ammo(ammo);
                        break;
                    case ("BACL Ammo LRM-3"):
                        createBACLLRM3Ammo(ammo);
                        break;
                    case ("BACL Ammo LRM-4"):
                        createBACLLRM4Ammo(ammo);
                        break;
                    case ("BACL Ammo LRM-5"):
                        createBACLLRM5Ammo(ammo);
                        break;
                    case ("BA-SRM1 Ammo"):
                        createBASRM1Ammo(ammo);
                        break;
                    case ("BA-SRM2 Ammo"):
                        createBASRM2Ammo(ammo);
                        break;
                    case ("BA-SRM3 Ammo"):
                        createBASRM3Ammo(ammo);
                        break;
                    case ("BA-SRM4 Ammo"):
                        createBASRM4Ammo(ammo);
                        break;
                    case ("BA-SRM5 Ammo"):
                        createBASRM5Ammo(ammo);
                        break;
                    case ("BA-SRM6 Ammo"):
                        createBASRM6Ammo(ammo);
                        break;
                    case ("BA-Advanced SRM-1 Ammo"):
                        createAdvancedSRM1Ammo(ammo);
                        break;
                    case ("BA-Advanced SRM-2 Ammo"):
                        createAdvancedSRM2Ammo(ammo);
                        break;
                    case ("BA-Advanced SRM-3 Ammo"):
                        createAdvancedSRM3Ammo(ammo);
                        break;
                    case ("BA-Advanced SRM-4 Ammo"):
                        createAdvancedSRM4Ammo(ammo);
                        break;
                    case ("BA-Advanced SRM-5 Ammo"):
                        createAdvancedSRM5Ammo(ammo);
                        break;
                    case ("BA-Advanced SRM-6 Ammo"):
                        createAdvancedSRM6Ammo(ammo);
                        break;
                    case ("IS MRM 1 Ammo"):
                        createISMRM1Ammo(ammo);
                        break;
                    case ("IS MRM 2 Ammo"):
                        createISMRM2Ammo(ammo);
                        break;
                    case ("IS MRM 3 Ammo"):
                        createISMRM3Ammo(ammo);
                        break;
                    case ("IS MRM 4 Ammo"):
                        createISMRM4Ammo(ammo);
                        break;
                    case ("IS MRM 5 Ammo"):
                        createISMRM5Ammo(ammo);
                        break;
                    case ("BA Taser Ammo"):
                        createISBATaserAmmo(ammo);
                        break;
                    case ("BARL1 Ammo"):
                        createBARL1Ammo(ammo);
                        break;
                    case ("BARL2 Ammo"):
                        createBARL2Ammo(ammo);
                        break;
                    case ("BARL3 Ammo"):
                        createBARL3Ammo(ammo);
                        break;
                    case ("BARL4 Ammo"):
                        createBARL4Ammo(ammo);
                        break;
                    case ("BARL5 Ammo"):
                        createBARL5Ammo(ammo);
                        break;
                    case ("IS M-Pod Ammo"):
                        createISMPodAmmo(ammo);
                        break;
                    case ("Clan M-Pod Ammo"):
                        createCLMPodAmmo(ammo);
                        break;
                    case ("ISBPodAmmo"):
                        createISBPodAmmo(ammo);
                        break;
                    case ("IS Ammo AC/15"):
                        createISAC15Ammo(ammo);
                        break;
                    case ("IS LB 2-X AC Ammo (THB)"):
                        createISTHBLB2XAmmo(ammo);
                        break;
                    case ("IS LB 5-X AC Ammo (THB)"):
                        createISTHBLB5XAmmo(ammo);
                        break;
                    case ("IS LB 20-X AC Ammo (THB)"):
                        createISTHBLB20XAmmo(ammo);
                        break;
                    case ("IS LB 2-X Cluster Ammo (THB)"):
                        createISTHBLB2XClusterAmmo(ammo);
                        break;
                    case ("IS LB 5-X Cluster Ammo (THB)"):
                        createISTHBLB5XClusterAmmo(ammo);
                        break;
                    case ("IS LB 20-X Cluster Ammo (THB)"):
                        createISTHBLB20XClusterAmmo(ammo);
                        break;
                    case ("IS Ultra AC/2 Ammo (THB)"):
                        createISTHBUltra2Ammo(ammo);
                        break;
                    case ("IS Ultra AC/10 Ammo (THB)"):
                        createISTHBUltra10Ammo(ammo);
                        break;
                    case ("IS Ultra AC/20 Ammo (THB)"):
                        createISTHBUltra20Ammo(ammo);
                        break;
                    case ("ISRotaryAC10 Ammo"):
                        createISRotary10Ammo(ammo);
                        break;
                    case ("ISRotaryAC20 Ammo"):
                        createISRotary20Ammo(ammo);
                        break;
                    case ("CLRotaryAC10 Ammo"):
                        createCLRotary10Ammo(ammo);
                        break;
                    case ("CLRotaryAC20 Ammo"):
                        createCLRotary20Ammo(ammo);
                        break;
                    case ("IS Ammo LAC/10"):
                        createISLAC10Ammo(ammo);
                        break;
                    case ("IS Ammo LAC/20"):
                        createISLAC20Ammo(ammo);
                        break;
                    case ("ISRailGun Ammo"):
                        createISRailGunAmmo(ammo);
                        break;
                    case ("ISPhoenixLRM5 Ammo"):
                        createISPXLRM5Ammo(ammo);
                        break;
                    case ("ISPhoenixLRM10 Ammo"):
                        createISPXLRM10Ammo(ammo);
                        break;
                    case ("ISPhoenixLRM15 Ammo"):
                        createISPXLRM15Ammo(ammo);
                        break;
                    case ("ISPhoenixLRM20 Ammo"):
                        createISPXLRM20Ammo(ammo);
                        break;
                    case ("ISHawkSRM2 Ammo"):
                        createISHawkSRM2Ammo(ammo);
                        break;
                    case ("ISHawkSRM4 Ammo"):
                        createISHawkSRM4Ammo(ammo);
                        break;
                    case ("ISHawkSRM6 Ammo"):
                        createISHawkSRM6Ammo(ammo);
                        break;
                    case ("IS Streak MRM 10 Ammo"):
                        createISStreakMRM10Ammo(ammo);
                        break;
                    case ("IS Streak MRM 20 Ammo"):
                        createISStreakMRM20Ammo(ammo);
                        break;
                    case ("IS Streak MRM 30 Ammo"):
                        createISStreakMRM30Ammo(ammo);
                        break;
                    case ("IS Streak MRM 40 Ammo"):
                        createISStreakMRM40Ammo(ammo);
                        break;
                    case ("IS Ammo AC/10i"):
                        createISAC10iAmmo(ammo);
                        break;
                    case ("IS Ammo GAC/2"):
                        createISGAC2Ammo(ammo);
                        break;
                    case ("IS Ammo GAC/4"):
                        createISGAC4Ammo(ammo);
                        break;
                    case ("IS Ammo GAC/6"):
                        createISGAC6Ammo(ammo);
                        break;
                    case ("IS Ammo GAC/8"):
                        createISGAC8Ammo(ammo);
                        break;
                    case ("Ammo AR10 Peacemaker"):
                        createAR10PeacemakerAmmo(ammo);
                        break;
                    case ("Ammo Peacemaker"):
                        createPeacemakerAmmo(ammo);
                        break;
                    case ("Ammo AR10 Santa Anna"):
                        createAR10SantaAnnaAmmo(ammo);
                        break;
                    case ("Ammo Santa Anna"):
                        createSantaAnnaAmmo(ammo);
                        break;
                    case ("Ammo Alamo"):
                        createAlamoAmmo(ammo);
                        break;
                    case (EquipmentTypeLookup.INFANTRY_AMMO):
                        createInfantryAmmo(ammo);
                        break;
                    case (EquipmentTypeLookup.INFANTRY_INFERNO_AMMO):
                        createInfantryInfernoAmmo(ammo);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void createISAMSAmmo(AmmoType actualCreateISAMSAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAMSAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAMSAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAMSAmmoResult.svslots);
        assertEquals("AMS", actualCreateISAMSAmmoResult.shortName);
        assertNull(actualCreateISAMSAmmoResult.modes);
        assertTrue(actualCreateISAMSAmmoResult.explosive);
        assertEquals(1, actualCreateISAMSAmmoResult.criticals);
        assertEquals(11.0, actualCreateISAMSAmmoResult.bv, 0.0);
        assertFalse(actualCreateISAMSAmmoResult.isSpreadable());
        assertFalse(actualCreateISAMSAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISAMSAmmoResult.isMixedTech());
        assertTrue(actualCreateISAMSAmmoResult.isHittable());
        assertFalse(actualCreateISAMSAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAMSAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISAMSAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAMSAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISAMSAmmoResult.getStaticTechLevel());
        assertEquals("204,TM", actualCreateISAMSAmmoResult.getRulesRefs());
        assertEquals(3045, actualCreateISAMSAmmoResult.getReintroductionDate());
        assertEquals(2000.0, actualCreateISAMSAmmoResult.getRawCost(), 0.0);
        assertEquals(2608, actualCreateISAMSAmmoResult.getPrototypeDate());
        assertEquals("Anti-Missile System Ammo [IS]", actualCreateISAMSAmmoResult.getName());
        assertEquals("ISAMS Ammo", actualCreateISAMSAmmoResult.getInternalName());
        assertEquals("E/E-F(F*)-D-C", actualCreateISAMSAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAMSAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAMSAmmoResult.getFlags());
    }

    private void createCLAMSAmmo(AmmoType actualCreateCLAMSAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLAMSAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLAMSAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLAMSAmmoResult.svslots);
        assertEquals("AMS", actualCreateCLAMSAmmoResult.shortName);
        assertNull(actualCreateCLAMSAmmoResult.modes);
        assertTrue(actualCreateCLAMSAmmoResult.explosive);
        assertEquals(1, actualCreateCLAMSAmmoResult.criticals);
        assertEquals(22.0, actualCreateCLAMSAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLAMSAmmoResult.isSpreadable());
        assertFalse(actualCreateCLAMSAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLAMSAmmoResult.isMixedTech());
        assertTrue(actualCreateCLAMSAmmoResult.isHittable());
        assertFalse(actualCreateCLAMSAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLAMSAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLAMSAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLAMSAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLAMSAmmoResult.getStaticTechLevel());
        assertEquals("204,TM", actualCreateCLAMSAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLAMSAmmoResult.getReintroductionDate());
        assertEquals(2000.0, actualCreateCLAMSAmmoResult.getRawCost(), 0.0);
        assertEquals(2819, actualCreateCLAMSAmmoResult.getPrototypeDate());
        assertEquals("Anti-Missile System Ammo [Clan]", actualCreateCLAMSAmmoResult.getName());
        assertEquals("CLAMS Ammo", actualCreateCLAMSAmmoResult.getInternalName());
        assertEquals("F/X-F-D-C", actualCreateCLAMSAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLAMSAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLAMSAmmoResult.getFlags());
    }

    private void createISArrowIVAmmo(AmmoType actualCreateISArrowIVAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISArrowIVAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISArrowIVAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISArrowIVAmmoResult.svslots);
        assertEquals("Arrow IV", actualCreateISArrowIVAmmoResult.shortName);
        assertNull(actualCreateISArrowIVAmmoResult.modes);
        assertTrue(actualCreateISArrowIVAmmoResult.explosive);
        assertEquals(1, actualCreateISArrowIVAmmoResult.criticals);
        assertEquals(30.0, actualCreateISArrowIVAmmoResult.bv, 0.0);
        assertFalse(actualCreateISArrowIVAmmoResult.isSpreadable());
        assertFalse(actualCreateISArrowIVAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISArrowIVAmmoResult.isMixedTech());
        assertTrue(actualCreateISArrowIVAmmoResult.isHittable());
        assertFalse(actualCreateISArrowIVAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISArrowIVAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISArrowIVAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISArrowIVAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISArrowIVAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISArrowIVAmmoResult.getStandardRange());
        assertEquals("284,TO", actualCreateISArrowIVAmmoResult.getRulesRefs());
        assertEquals(3044, actualCreateISArrowIVAmmoResult.getReintroductionDate());
        assertEquals(10000.0, actualCreateISArrowIVAmmoResult.getRawCost(), 0.0);
        assertEquals("Arrow IV Ammo", actualCreateISArrowIVAmmoResult.getName());
        assertEquals("ISArrowIVAmmo", actualCreateISArrowIVAmmoResult.getInternalName());
        assertEquals("E/E-F(F*)-E-D", actualCreateISArrowIVAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISArrowIVAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISArrowIVAmmoResult.getFlags());
    }

    private void createCLArrowIVAmmo(AmmoType actualCreateCLArrowIVAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLArrowIVAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLArrowIVAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLArrowIVAmmoResult.svslots);
        assertEquals("Arrow IV", actualCreateCLArrowIVAmmoResult.shortName);
        assertNull(actualCreateCLArrowIVAmmoResult.modes);
        assertTrue(actualCreateCLArrowIVAmmoResult.explosive);
        assertEquals(1, actualCreateCLArrowIVAmmoResult.criticals);
        assertEquals(30.0, actualCreateCLArrowIVAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLArrowIVAmmoResult.isSpreadable());
        assertFalse(actualCreateCLArrowIVAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLArrowIVAmmoResult.isMixedTech());
        assertTrue(actualCreateCLArrowIVAmmoResult.isHittable());
        assertFalse(actualCreateCLArrowIVAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLArrowIVAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLArrowIVAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLArrowIVAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLArrowIVAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLArrowIVAmmoResult.getStandardRange());
        assertEquals("284,TO", actualCreateCLArrowIVAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLArrowIVAmmoResult.getReintroductionDate());
        assertEquals(10000.0, actualCreateCLArrowIVAmmoResult.getRawCost(), 0.0);
        assertEquals("Arrow IV Ammo", actualCreateCLArrowIVAmmoResult.getName());
        assertEquals("CLArrowIVAmmo", actualCreateCLArrowIVAmmoResult.getInternalName());
        assertEquals("E/E-F-E-D", actualCreateCLArrowIVAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLArrowIVAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLArrowIVAmmoResult.getFlags());
    }

    private void createLongTomAmmo(AmmoType actualCreateLongTomAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateLongTomAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateLongTomAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateLongTomAmmoResult.svslots);
        assertEquals("Long Tom", actualCreateLongTomAmmoResult.shortName);
        assertNull(actualCreateLongTomAmmoResult.modes);
        assertTrue(actualCreateLongTomAmmoResult.explosive);
        assertEquals(1, actualCreateLongTomAmmoResult.criticals);
        assertEquals(46.0, actualCreateLongTomAmmoResult.bv, 0.0);
        assertFalse(actualCreateLongTomAmmoResult.isSpreadable());
        assertFalse(actualCreateLongTomAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateLongTomAmmoResult.isMixedTech());
        assertTrue(actualCreateLongTomAmmoResult.isHittable());
        assertFalse(actualCreateLongTomAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateLongTomAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateLongTomAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateLongTomAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateLongTomAmmoResult.getStaticTechLevel());
        assertEquals("284,TO", actualCreateLongTomAmmoResult.getRulesRefs());
        assertEquals(10000.0, actualCreateLongTomAmmoResult.getRawCost(), 0.0);
        assertEquals(2445, actualCreateLongTomAmmoResult.getPrototypeDate());
        assertEquals("Long Tom Ammo", actualCreateLongTomAmmoResult.getName());
        assertEquals("ISLongTomAmmo", actualCreateLongTomAmmoResult.getInternalName());
        assertEquals("B/C-C-C-C", actualCreateLongTomAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateLongTomAmmoResult.flags;
        assertSame(expectedFlags, actualCreateLongTomAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateLongTomAmmoResult.getExtinctionDate());
    }

    private void createSniperAmmo(AmmoType actualCreateSniperAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateSniperAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateSniperAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateSniperAmmoResult.svslots);
        assertEquals("Sniper", actualCreateSniperAmmoResult.shortName);
        assertNull(actualCreateSniperAmmoResult.modes);
        assertTrue(actualCreateSniperAmmoResult.explosive);
        assertEquals(1, actualCreateSniperAmmoResult.criticals);
        assertEquals(11.0, actualCreateSniperAmmoResult.bv, 0.0);
        assertFalse(actualCreateSniperAmmoResult.isSpreadable());
        assertFalse(actualCreateSniperAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateSniperAmmoResult.isMixedTech());
        assertTrue(actualCreateSniperAmmoResult.isHittable());
        assertFalse(actualCreateSniperAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateSniperAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateSniperAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateSniperAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateSniperAmmoResult.getStaticTechLevel());
        assertEquals("284,TO", actualCreateSniperAmmoResult.getRulesRefs());
        assertEquals(6000.0, actualCreateSniperAmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_PS, actualCreateSniperAmmoResult.getPrototypeDate());
        assertEquals("Sniper Ammo", actualCreateSniperAmmoResult.getName());
        assertEquals("ISSniperAmmo", actualCreateSniperAmmoResult.getInternalName());
        assertEquals("B/C-C-C-C", actualCreateSniperAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateSniperAmmoResult.flags;
        assertSame(expectedFlags, actualCreateSniperAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateSniperAmmoResult.getExtinctionDate());
    }

    private void createThumperAmmo(AmmoType actualCreateThumperAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateThumperAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateThumperAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateThumperAmmoResult.svslots);
        assertEquals("Thumper", actualCreateThumperAmmoResult.shortName);
        assertNull(actualCreateThumperAmmoResult.modes);
        assertTrue(actualCreateThumperAmmoResult.explosive);
        assertEquals(1, actualCreateThumperAmmoResult.criticals);
        assertEquals(5.0, actualCreateThumperAmmoResult.bv, 0.0);
        assertFalse(actualCreateThumperAmmoResult.isSpreadable());
        assertFalse(actualCreateThumperAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateThumperAmmoResult.isMixedTech());
        assertTrue(actualCreateThumperAmmoResult.isHittable());
        assertFalse(actualCreateThumperAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateThumperAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateThumperAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateThumperAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateThumperAmmoResult.getStaticTechLevel());
        assertEquals("284,TO", actualCreateThumperAmmoResult.getRulesRefs());
        assertEquals(4500.0, actualCreateThumperAmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_PS, actualCreateThumperAmmoResult.getPrototypeDate());
        assertEquals("Thumper Ammo", actualCreateThumperAmmoResult.getName());
        assertEquals("ISThumperAmmo", actualCreateThumperAmmoResult.getInternalName());
        assertEquals("B/C-C-C-C", actualCreateThumperAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateThumperAmmoResult.flags;
        assertSame(expectedFlags, actualCreateThumperAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateThumperAmmoResult.getExtinctionDate());
    }

    private void createISCruiseMissile50Ammo(AmmoType actualCreateISCruiseMissile50AmmoResult) {
        assertEquals(25.0, actualCreateISCruiseMissile50AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISCruiseMissile50AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISCruiseMissile50AmmoResult.svslots);
        assertEquals("", actualCreateISCruiseMissile50AmmoResult.shortName);
        assertNull(actualCreateISCruiseMissile50AmmoResult.modes);
        assertTrue(actualCreateISCruiseMissile50AmmoResult.explosive);
        assertEquals(1, actualCreateISCruiseMissile50AmmoResult.criticals);
        assertEquals(75.0, actualCreateISCruiseMissile50AmmoResult.bv, 0.0);
        assertFalse(actualCreateISCruiseMissile50AmmoResult.isSpreadable());
        assertFalse(actualCreateISCruiseMissile50AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISCruiseMissile50AmmoResult.isMixedTech());
        assertTrue(actualCreateISCruiseMissile50AmmoResult.isHittable());
        assertFalse(actualCreateISCruiseMissile50AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISCruiseMissile50AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISCruiseMissile50AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISCruiseMissile50AmmoResult.getSubType());
        assertEquals("", actualCreateISCruiseMissile50AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISCruiseMissile50AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISCruiseMissile50AmmoResult.getStandardRange());
        assertEquals("284,TO", actualCreateISCruiseMissile50AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISCruiseMissile50AmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateISCruiseMissile50AmmoResult.getRawCost(), 0.0);
        assertEquals("ISCruiseMissile50Ammo", actualCreateISCruiseMissile50AmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateISCruiseMissile50AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISCruiseMissile50AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISCruiseMissile50AmmoResult.getFlags());
    }

    private void createISCruiseMissile70Ammo(AmmoType actualCreateISCruiseMissile70AmmoResult) {
        assertEquals(35.0, actualCreateISCruiseMissile70AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISCruiseMissile70AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISCruiseMissile70AmmoResult.svslots);
        assertEquals("", actualCreateISCruiseMissile70AmmoResult.shortName);
        assertNull(actualCreateISCruiseMissile70AmmoResult.modes);
        assertTrue(actualCreateISCruiseMissile70AmmoResult.explosive);
        assertEquals(1, actualCreateISCruiseMissile70AmmoResult.criticals);
        assertEquals(129.0, actualCreateISCruiseMissile70AmmoResult.bv, 0.0);
        assertFalse(actualCreateISCruiseMissile70AmmoResult.isSpreadable());
        assertFalse(actualCreateISCruiseMissile70AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISCruiseMissile70AmmoResult.isMixedTech());
        assertTrue(actualCreateISCruiseMissile70AmmoResult.isHittable());
        assertFalse(actualCreateISCruiseMissile70AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISCruiseMissile70AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISCruiseMissile70AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISCruiseMissile70AmmoResult.getSubType());
        assertEquals("", actualCreateISCruiseMissile70AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISCruiseMissile70AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISCruiseMissile70AmmoResult.getStandardRange());
        assertEquals("284,TO", actualCreateISCruiseMissile70AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISCruiseMissile70AmmoResult.getReintroductionDate());
        assertEquals(50000.0, actualCreateISCruiseMissile70AmmoResult.getRawCost(), 0.0);
        assertEquals("ISCruiseMissile70Ammo", actualCreateISCruiseMissile70AmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateISCruiseMissile70AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISCruiseMissile70AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISCruiseMissile70AmmoResult.getFlags());
    }

    private void createISCruiseMissile90Ammo(AmmoType actualCreateISCruiseMissile90AmmoResult) {
        assertEquals(45.0, actualCreateISCruiseMissile90AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISCruiseMissile90AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISCruiseMissile90AmmoResult.svslots);
        assertEquals("", actualCreateISCruiseMissile90AmmoResult.shortName);
        assertNull(actualCreateISCruiseMissile90AmmoResult.modes);
        assertTrue(actualCreateISCruiseMissile90AmmoResult.explosive);
        assertEquals(1, actualCreateISCruiseMissile90AmmoResult.criticals);
        assertEquals(191.0, actualCreateISCruiseMissile90AmmoResult.bv, 0.0);
        assertFalse(actualCreateISCruiseMissile90AmmoResult.isSpreadable());
        assertFalse(actualCreateISCruiseMissile90AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISCruiseMissile90AmmoResult.isMixedTech());
        assertTrue(actualCreateISCruiseMissile90AmmoResult.isHittable());
        assertFalse(actualCreateISCruiseMissile90AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISCruiseMissile90AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISCruiseMissile90AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISCruiseMissile90AmmoResult.getSubType());
        assertEquals("", actualCreateISCruiseMissile90AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISCruiseMissile90AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISCruiseMissile90AmmoResult.getStandardRange());
        assertEquals("284,TO", actualCreateISCruiseMissile90AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISCruiseMissile90AmmoResult.getReintroductionDate());
        assertEquals(90000.0, actualCreateISCruiseMissile90AmmoResult.getRawCost(), 0.0);
        assertEquals("ISCruiseMissile90Ammo", actualCreateISCruiseMissile90AmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateISCruiseMissile90AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISCruiseMissile90AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISCruiseMissile90AmmoResult.getFlags());
    }

    private void createISCruiseMissile120Ammo(AmmoType actualCreateISCruiseMissile120AmmoResult) {
        assertEquals(60.0, actualCreateISCruiseMissile120AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISCruiseMissile120AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISCruiseMissile120AmmoResult.svslots);
        assertEquals("", actualCreateISCruiseMissile120AmmoResult.shortName);
        assertNull(actualCreateISCruiseMissile120AmmoResult.modes);
        assertTrue(actualCreateISCruiseMissile120AmmoResult.explosive);
        assertEquals(1, actualCreateISCruiseMissile120AmmoResult.criticals);
        assertEquals(285.0, actualCreateISCruiseMissile120AmmoResult.bv, 0.0);
        assertFalse(actualCreateISCruiseMissile120AmmoResult.isSpreadable());
        assertFalse(actualCreateISCruiseMissile120AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISCruiseMissile120AmmoResult.isMixedTech());
        assertTrue(actualCreateISCruiseMissile120AmmoResult.isHittable());
        assertFalse(actualCreateISCruiseMissile120AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISCruiseMissile120AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISCruiseMissile120AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISCruiseMissile120AmmoResult.getSubType());
        assertEquals("", actualCreateISCruiseMissile120AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISCruiseMissile120AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISCruiseMissile120AmmoResult.getStandardRange());
        assertEquals("284,TO", actualCreateISCruiseMissile120AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISCruiseMissile120AmmoResult.getReintroductionDate());
        assertEquals(140000.0, actualCreateISCruiseMissile120AmmoResult.getRawCost(), 0.0);
        assertEquals("ISCruiseMissile120Ammo", actualCreateISCruiseMissile120AmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateISCruiseMissile120AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISCruiseMissile120AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISCruiseMissile120AmmoResult.getFlags());
    }

    private void createISLongTomCannonAmmo(AmmoType actualCreateISLongTomCannonAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLongTomCannonAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLongTomCannonAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLongTomCannonAmmoResult.svslots);
        assertEquals("Long Tom Cannon", actualCreateISLongTomCannonAmmoResult.shortName);
        assertNull(actualCreateISLongTomCannonAmmoResult.modes);
        assertTrue(actualCreateISLongTomCannonAmmoResult.explosive);
        assertEquals(1, actualCreateISLongTomCannonAmmoResult.criticals);
        assertEquals(41.0, actualCreateISLongTomCannonAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLongTomCannonAmmoResult.isSpreadable());
        assertFalse(actualCreateISLongTomCannonAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISLongTomCannonAmmoResult.isMixedTech());
        assertTrue(actualCreateISLongTomCannonAmmoResult.isHittable());
        assertFalse(actualCreateISLongTomCannonAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLongTomCannonAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISLongTomCannonAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLongTomCannonAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISLongTomCannonAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISLongTomCannonAmmoResult.getStandardRange());
        assertEquals("285,TO", actualCreateISLongTomCannonAmmoResult.getRulesRefs());
        assertEquals(20000.0, actualCreateISLongTomCannonAmmoResult.getRawCost(), 0.0);
        assertEquals("Long Tom Cannon Ammo", actualCreateISLongTomCannonAmmoResult.getName());
        assertEquals("ISLongTomCannonAmmo", actualCreateISLongTomCannonAmmoResult.getInternalName());
        assertEquals("B/X-F-E-D", actualCreateISLongTomCannonAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLongTomCannonAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLongTomCannonAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISLongTomCannonAmmoResult.getExtinctionDate());
    }

    private void createISSniperCannonAmmo(AmmoType actualCreateISSniperCannonAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISSniperCannonAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISSniperCannonAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISSniperCannonAmmoResult.svslots);
        assertEquals("Sniper Cannon", actualCreateISSniperCannonAmmoResult.shortName);
        assertNull(actualCreateISSniperCannonAmmoResult.modes);
        assertTrue(actualCreateISSniperCannonAmmoResult.explosive);
        assertEquals(1, actualCreateISSniperCannonAmmoResult.criticals);
        assertEquals(10.0, actualCreateISSniperCannonAmmoResult.bv, 0.0);
        assertFalse(actualCreateISSniperCannonAmmoResult.isSpreadable());
        assertFalse(actualCreateISSniperCannonAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISSniperCannonAmmoResult.isMixedTech());
        assertTrue(actualCreateISSniperCannonAmmoResult.isHittable());
        assertFalse(actualCreateISSniperCannonAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISSniperCannonAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISSniperCannonAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISSniperCannonAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISSniperCannonAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISSniperCannonAmmoResult.getStandardRange());
        assertEquals("285,TO", actualCreateISSniperCannonAmmoResult.getRulesRefs());
        assertEquals(15000.0, actualCreateISSniperCannonAmmoResult.getRawCost(), 0.0);
        assertEquals("Sniper Cannon Ammo", actualCreateISSniperCannonAmmoResult.getName());
        assertEquals("ISSniperCannonAmmo", actualCreateISSniperCannonAmmoResult.getInternalName());
        assertEquals("B/X-F-E-D", actualCreateISSniperCannonAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISSniperCannonAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISSniperCannonAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISSniperCannonAmmoResult.getExtinctionDate());
    }

    private void createISThumperCannonAmmo(AmmoType actualCreateISThumperCannonAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISThumperCannonAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISThumperCannonAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISThumperCannonAmmoResult.svslots);
        assertEquals("Thumper Cannon", actualCreateISThumperCannonAmmoResult.shortName);
        assertNull(actualCreateISThumperCannonAmmoResult.modes);
        assertTrue(actualCreateISThumperCannonAmmoResult.explosive);
        assertEquals(1, actualCreateISThumperCannonAmmoResult.criticals);
        assertEquals(5.0, actualCreateISThumperCannonAmmoResult.bv, 0.0);
        assertFalse(actualCreateISThumperCannonAmmoResult.isSpreadable());
        assertFalse(actualCreateISThumperCannonAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISThumperCannonAmmoResult.isMixedTech());
        assertTrue(actualCreateISThumperCannonAmmoResult.isHittable());
        assertFalse(actualCreateISThumperCannonAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISThumperCannonAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISThumperCannonAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISThumperCannonAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISThumperCannonAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISThumperCannonAmmoResult.getStandardRange());
        assertEquals("285,TO", actualCreateISThumperCannonAmmoResult.getRulesRefs());
        assertEquals(10000.0, actualCreateISThumperCannonAmmoResult.getRawCost(), 0.0);
        assertEquals("Thumper Cannon Ammo", actualCreateISThumperCannonAmmoResult.getName());
        assertEquals("ISThumperCannonAmmo", actualCreateISThumperCannonAmmoResult.getInternalName());
        assertEquals("B/X-F-E-D", actualCreateISThumperCannonAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISThumperCannonAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISThumperCannonAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISThumperCannonAmmoResult.getExtinctionDate());
    }

    private void createBATubeArtyAmmo(AmmoType actualCreateBATubeArtyAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBATubeArtyAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBATubeArtyAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBATubeArtyAmmoResult.svslots);
        assertEquals("Tube Artillery", actualCreateBATubeArtyAmmoResult.shortName);
        assertNull(actualCreateBATubeArtyAmmoResult.modes);
        assertTrue(actualCreateBATubeArtyAmmoResult.explosive);
        assertEquals(1, actualCreateBATubeArtyAmmoResult.criticals);
        assertEquals(4.0, actualCreateBATubeArtyAmmoResult.bv, 0.0);
        assertFalse(actualCreateBATubeArtyAmmoResult.isSpreadable());
        assertFalse(actualCreateBATubeArtyAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBATubeArtyAmmoResult.isMixedTech());
        assertTrue(actualCreateBATubeArtyAmmoResult.isHittable());
        assertFalse(actualCreateBATubeArtyAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBATubeArtyAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBATubeArtyAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBATubeArtyAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBATubeArtyAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateBATubeArtyAmmoResult.getStandardRange());
        assertEquals("284,TO", actualCreateBATubeArtyAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBATubeArtyAmmoResult.getReintroductionDate());
        assertEquals(900.0, actualCreateBATubeArtyAmmoResult.getRawCost(), 0.0);
        assertEquals("BA Tube Artillery Ammo", actualCreateBATubeArtyAmmoResult.getName());
        assertEquals("ISBATubeArtilleryAmmo", actualCreateBATubeArtyAmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateBATubeArtyAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBATubeArtyAmmoResult.flags;
        assertSame(expectedFlags, actualCreateBATubeArtyAmmoResult.getFlags());
    }

    private void createISAC2Ammo(AmmoType actualCreateISAC2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAC2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAC2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAC2AmmoResult.svslots);
        assertEquals("AC/2", actualCreateISAC2AmmoResult.shortName);
        assertNull(actualCreateISAC2AmmoResult.modes);
        assertTrue(actualCreateISAC2AmmoResult.explosive);
        assertEquals(1, actualCreateISAC2AmmoResult.criticals);
        assertEquals(5.0, actualCreateISAC2AmmoResult.bv, 0.0);
        assertFalse(actualCreateISAC2AmmoResult.isSpreadable());
        assertFalse(actualCreateISAC2AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISAC2AmmoResult.isMixedTech());
        assertTrue(actualCreateISAC2AmmoResult.isHittable());
        assertFalse(actualCreateISAC2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAC2AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISAC2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAC2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.INTRO, actualCreateISAC2AmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISAC2AmmoResult.getRulesRefs());
        assertEquals(1000.0, actualCreateISAC2AmmoResult.getRawCost(), 0.0);
        assertEquals(2290, actualCreateISAC2AmmoResult.getPrototypeDate());
        assertEquals("AC/2 Ammo", actualCreateISAC2AmmoResult.getName());
        assertEquals("IS Ammo AC/2", actualCreateISAC2AmmoResult.getInternalName());
        assertEquals("B/C-C-D-D", actualCreateISAC2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAC2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAC2AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISAC2AmmoResult.getExtinctionDate());
    }

    private void createISAC5Ammo(AmmoType actualCreateISAC5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAC5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAC5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAC5AmmoResult.svslots);
        assertEquals("AC/5", actualCreateISAC5AmmoResult.shortName);
        assertNull(actualCreateISAC5AmmoResult.modes);
        assertTrue(actualCreateISAC5AmmoResult.explosive);
        assertEquals(1, actualCreateISAC5AmmoResult.criticals);
        assertEquals(9.0, actualCreateISAC5AmmoResult.bv, 0.0);
        assertFalse(actualCreateISAC5AmmoResult.isSpreadable());
        assertFalse(actualCreateISAC5AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISAC5AmmoResult.isMixedTech());
        assertTrue(actualCreateISAC5AmmoResult.isHittable());
        assertFalse(actualCreateISAC5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAC5AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISAC5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAC5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.INTRO, actualCreateISAC5AmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISAC5AmmoResult.getRulesRefs());
        assertEquals(4500.0, actualCreateISAC5AmmoResult.getRawCost(), 0.0);
        assertEquals(2240, actualCreateISAC5AmmoResult.getPrototypeDate());
        assertEquals("AC/5 Ammo", actualCreateISAC5AmmoResult.getName());
        assertEquals("IS Ammo AC/5", actualCreateISAC5AmmoResult.getInternalName());
        assertEquals("B/C-C-D-D", actualCreateISAC5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAC5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAC5AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISAC5AmmoResult.getExtinctionDate());
    }

    private void createISAC10Ammo(AmmoType actualCreateISAC10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAC10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAC10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAC10AmmoResult.svslots);
        assertEquals("AC/10", actualCreateISAC10AmmoResult.shortName);
        assertNull(actualCreateISAC10AmmoResult.modes);
        assertTrue(actualCreateISAC10AmmoResult.explosive);
        assertEquals(1, actualCreateISAC10AmmoResult.criticals);
        assertEquals(15.0, actualCreateISAC10AmmoResult.bv, 0.0);
        assertFalse(actualCreateISAC10AmmoResult.isSpreadable());
        assertFalse(actualCreateISAC10AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISAC10AmmoResult.isMixedTech());
        assertTrue(actualCreateISAC10AmmoResult.isHittable());
        assertFalse(actualCreateISAC10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAC10AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISAC10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAC10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.INTRO, actualCreateISAC10AmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISAC10AmmoResult.getRulesRefs());
        assertEquals(6000.0, actualCreateISAC10AmmoResult.getRawCost(), 0.0);
        assertEquals(2443, actualCreateISAC10AmmoResult.getPrototypeDate());
        assertEquals("AC/10 Ammo", actualCreateISAC10AmmoResult.getName());
        assertEquals("IS Ammo AC/10", actualCreateISAC10AmmoResult.getInternalName());
        assertEquals("B/C-C-D-D", actualCreateISAC10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAC10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAC10AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISAC10AmmoResult.getExtinctionDate());
    }

    private void createISAC20Ammo(AmmoType actualCreateISAC20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAC20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAC20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAC20AmmoResult.svslots);
        assertEquals("AC/20", actualCreateISAC20AmmoResult.shortName);
        assertNull(actualCreateISAC20AmmoResult.modes);
        assertTrue(actualCreateISAC20AmmoResult.explosive);
        assertEquals(1, actualCreateISAC20AmmoResult.criticals);
        assertEquals(22.0, actualCreateISAC20AmmoResult.bv, 0.0);
        assertFalse(actualCreateISAC20AmmoResult.isSpreadable());
        assertFalse(actualCreateISAC20AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISAC20AmmoResult.isMixedTech());
        assertTrue(actualCreateISAC20AmmoResult.isHittable());
        assertFalse(actualCreateISAC20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAC20AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISAC20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAC20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.INTRO, actualCreateISAC20AmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISAC20AmmoResult.getRulesRefs());
        assertEquals(10000.0, actualCreateISAC20AmmoResult.getRawCost(), 0.0);
        assertEquals(2488, actualCreateISAC20AmmoResult.getPrototypeDate());
        assertEquals("AC/20 Ammo", actualCreateISAC20AmmoResult.getName());
        assertEquals("IS Ammo AC/20", actualCreateISAC20AmmoResult.getInternalName());
        assertEquals("B/D-E-D-D", actualCreateISAC20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAC20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAC20AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISAC20AmmoResult.getExtinctionDate());
    }

    private void createISLAC2Ammo(AmmoType actualCreateISLAC2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLAC2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLAC2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLAC2AmmoResult.svslots);
        assertEquals("LAC/2", actualCreateISLAC2AmmoResult.shortName);
        assertNull(actualCreateISLAC2AmmoResult.modes);
        assertTrue(actualCreateISLAC2AmmoResult.explosive);
        assertEquals(1, actualCreateISLAC2AmmoResult.criticals);
        assertEquals(4.0, actualCreateISLAC2AmmoResult.bv, 0.0);
        assertFalse(actualCreateISLAC2AmmoResult.isSpreadable());
        assertFalse(actualCreateISLAC2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLAC2AmmoResult.isMixedTech());
        assertTrue(actualCreateISLAC2AmmoResult.isHittable());
        assertFalse(actualCreateISLAC2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLAC2AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISLAC2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLAC2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLAC2AmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISLAC2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLAC2AmmoResult.getReintroductionDate());
        assertEquals(2000.0, actualCreateISLAC2AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateISLAC2AmmoResult.getPrototypeDate());
        assertEquals("LAC/2 Ammo", actualCreateISLAC2AmmoResult.getName());
        assertEquals("IS Ammo LAC/2", actualCreateISLAC2AmmoResult.getInternalName());
        assertEquals("B/X-X-D-D", actualCreateISLAC2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLAC2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLAC2AmmoResult.getFlags());
    }

    private void createISLAC5Ammo(AmmoType actualCreateISLAC5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLAC5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLAC5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLAC5AmmoResult.svslots);
        assertEquals("LAC/5", actualCreateISLAC5AmmoResult.shortName);
        assertNull(actualCreateISLAC5AmmoResult.modes);
        assertTrue(actualCreateISLAC5AmmoResult.explosive);
        assertEquals(1, actualCreateISLAC5AmmoResult.criticals);
        assertEquals(8.0, actualCreateISLAC5AmmoResult.bv, 0.0);
        assertFalse(actualCreateISLAC5AmmoResult.isSpreadable());
        assertFalse(actualCreateISLAC5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLAC5AmmoResult.isMixedTech());
        assertTrue(actualCreateISLAC5AmmoResult.isHittable());
        assertFalse(actualCreateISLAC5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLAC5AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISLAC5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLAC5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLAC5AmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISLAC5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLAC5AmmoResult.getReintroductionDate());
        assertEquals(5000.0, actualCreateISLAC5AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateISLAC5AmmoResult.getPrototypeDate());
        assertEquals("LAC/5 Ammo", actualCreateISLAC5AmmoResult.getName());
        assertEquals("IS Ammo LAC/5", actualCreateISLAC5AmmoResult.getInternalName());
        assertEquals("B/X-X-D-D", actualCreateISLAC5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLAC5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLAC5AmmoResult.getFlags());
    }

    private void createCLPROAC2Ammo(AmmoType actualCreateCLPROAC2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLPROAC2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLPROAC2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLPROAC2AmmoResult.svslots);
        assertEquals("Proto AC/2", actualCreateCLPROAC2AmmoResult.shortName);
        assertNull(actualCreateCLPROAC2AmmoResult.modes);
        assertTrue(actualCreateCLPROAC2AmmoResult.explosive);
        assertEquals(1, actualCreateCLPROAC2AmmoResult.criticals);
        assertEquals(4.0, actualCreateCLPROAC2AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLPROAC2AmmoResult.isSpreadable());
        assertFalse(actualCreateCLPROAC2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLPROAC2AmmoResult.isMixedTech());
        assertTrue(actualCreateCLPROAC2AmmoResult.isHittable());
        assertFalse(actualCreateCLPROAC2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLPROAC2AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateCLPROAC2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLPROAC2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.ADVANCED, actualCreateCLPROAC2AmmoResult.getStaticTechLevel());
        assertEquals("286,TO", actualCreateCLPROAC2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLPROAC2AmmoResult.getReintroductionDate());
        assertEquals(1200.0, actualCreateCLPROAC2AmmoResult.getRawCost(), 0.0);
        assertEquals(3065, actualCreateCLPROAC2AmmoResult.getPrototypeDate());
        assertEquals("ProtoMech AC/2 Ammo", actualCreateCLPROAC2AmmoResult.getName());
        assertEquals("Clan ProtoMech AC/2 Ammo", actualCreateCLPROAC2AmmoResult.getInternalName());
        assertEquals("B/X-X-D-D", actualCreateCLPROAC2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLPROAC2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLPROAC2AmmoResult.getFlags());
    }

    private void createCLPROAC4Ammo(AmmoType actualCreateCLPROAC4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLPROAC4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLPROAC4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLPROAC4AmmoResult.svslots);
        assertEquals("Proto AC/4", actualCreateCLPROAC4AmmoResult.shortName);
        assertNull(actualCreateCLPROAC4AmmoResult.modes);
        assertTrue(actualCreateCLPROAC4AmmoResult.explosive);
        assertEquals(1, actualCreateCLPROAC4AmmoResult.criticals);
        assertEquals(6.0, actualCreateCLPROAC4AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLPROAC4AmmoResult.isSpreadable());
        assertFalse(actualCreateCLPROAC4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLPROAC4AmmoResult.isMixedTech());
        assertTrue(actualCreateCLPROAC4AmmoResult.isHittable());
        assertFalse(actualCreateCLPROAC4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLPROAC4AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateCLPROAC4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLPROAC4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.ADVANCED, actualCreateCLPROAC4AmmoResult.getStaticTechLevel());
        assertEquals("286,TO", actualCreateCLPROAC4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLPROAC4AmmoResult.getReintroductionDate());
        assertEquals(4800.0, actualCreateCLPROAC4AmmoResult.getRawCost(), 0.0);
        assertEquals(3065, actualCreateCLPROAC4AmmoResult.getPrototypeDate());
        assertEquals("ProtoMech AC/4 Ammo", actualCreateCLPROAC4AmmoResult.getName());
        assertEquals("Clan ProtoMech AC/4 Ammo", actualCreateCLPROAC4AmmoResult.getInternalName());
        assertEquals("B/X-X-D-D", actualCreateCLPROAC4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLPROAC4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLPROAC4AmmoResult.getFlags());
    }

    private void createCLPROAC8Ammo(AmmoType actualCreateCLPROAC8AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLPROAC8AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLPROAC8AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLPROAC8AmmoResult.svslots);
        assertEquals("Proto AC/8", actualCreateCLPROAC8AmmoResult.shortName);
        assertNull(actualCreateCLPROAC8AmmoResult.modes);
        assertTrue(actualCreateCLPROAC8AmmoResult.explosive);
        assertEquals(1, actualCreateCLPROAC8AmmoResult.criticals);
        assertEquals(8.0, actualCreateCLPROAC8AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLPROAC8AmmoResult.isSpreadable());
        assertFalse(actualCreateCLPROAC8AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLPROAC8AmmoResult.isMixedTech());
        assertTrue(actualCreateCLPROAC8AmmoResult.isHittable());
        assertFalse(actualCreateCLPROAC8AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLPROAC8AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateCLPROAC8AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLPROAC8AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.ADVANCED, actualCreateCLPROAC8AmmoResult.getStaticTechLevel());
        assertEquals("286,TO", actualCreateCLPROAC8AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLPROAC8AmmoResult.getReintroductionDate());
        assertEquals(6300.0, actualCreateCLPROAC8AmmoResult.getRawCost(), 0.0);
        assertEquals(3065, actualCreateCLPROAC8AmmoResult.getPrototypeDate());
        assertEquals("ProtoMech AC/8 Ammo", actualCreateCLPROAC8AmmoResult.getName());
        assertEquals("Clan ProtoMech AC/8 Ammo", actualCreateCLPROAC8AmmoResult.getInternalName());
        assertEquals("B/X-X-D-D", actualCreateCLPROAC8AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLPROAC8AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLPROAC8AmmoResult.getFlags());
    }

    private void createISHVAC2Ammo(AmmoType actualCreateISHVAC2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISHVAC2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISHVAC2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISHVAC2AmmoResult.svslots);
        assertEquals("HVAC/2", actualCreateISHVAC2AmmoResult.shortName);
        assertNull(actualCreateISHVAC2AmmoResult.modes);
        assertTrue(actualCreateISHVAC2AmmoResult.explosive);
        assertEquals(1, actualCreateISHVAC2AmmoResult.criticals);
        assertEquals(7.0, actualCreateISHVAC2AmmoResult.bv, 0.0);
        assertFalse(actualCreateISHVAC2AmmoResult.isSpreadable());
        assertFalse(actualCreateISHVAC2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISHVAC2AmmoResult.isMixedTech());
        assertTrue(actualCreateISHVAC2AmmoResult.isHittable());
        assertFalse(actualCreateISHVAC2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISHVAC2AmmoResult.getToHitModifier());
        assertEquals(3, actualCreateISHVAC2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISHVAC2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISHVAC2AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISHVAC2AmmoResult.getStandardRange());
        assertEquals("285,TO", actualCreateISHVAC2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISHVAC2AmmoResult.getReintroductionDate());
        assertEquals(3000.0, actualCreateISHVAC2AmmoResult.getRawCost(), 0.0);
        assertEquals("HVAC/2 Ammo", actualCreateISHVAC2AmmoResult.getName());
        assertEquals("IS Ammo HVAC/2", actualCreateISHVAC2AmmoResult.getInternalName());
        assertEquals("D/X-X-F-E", actualCreateISHVAC2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISHVAC2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISHVAC2AmmoResult.getFlags());
    }

    private void createISHVAC5Ammo(AmmoType actualCreateISHVAC5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISHVAC5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISHVAC5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISHVAC5AmmoResult.svslots);
        assertEquals("HVAC/5", actualCreateISHVAC5AmmoResult.shortName);
        assertNull(actualCreateISHVAC5AmmoResult.modes);
        assertTrue(actualCreateISHVAC5AmmoResult.explosive);
        assertEquals(1, actualCreateISHVAC5AmmoResult.criticals);
        assertEquals(14.0, actualCreateISHVAC5AmmoResult.bv, 0.0);
        assertFalse(actualCreateISHVAC5AmmoResult.isSpreadable());
        assertFalse(actualCreateISHVAC5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISHVAC5AmmoResult.isMixedTech());
        assertTrue(actualCreateISHVAC5AmmoResult.isHittable());
        assertFalse(actualCreateISHVAC5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISHVAC5AmmoResult.getToHitModifier());
        assertEquals(3, actualCreateISHVAC5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISHVAC5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISHVAC5AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISHVAC5AmmoResult.getStandardRange());
        assertEquals("285,TO", actualCreateISHVAC5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISHVAC5AmmoResult.getReintroductionDate());
        assertEquals(10000.0, actualCreateISHVAC5AmmoResult.getRawCost(), 0.0);
        assertEquals("HVAC/5 Ammo", actualCreateISHVAC5AmmoResult.getName());
        assertEquals("IS Ammo HVAC/5", actualCreateISHVAC5AmmoResult.getInternalName());
        assertEquals("D/X-X-F-E", actualCreateISHVAC5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISHVAC5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISHVAC5AmmoResult.getFlags());
    }

    private void createISHVAC10Ammo(AmmoType actualCreateISHVAC10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISHVAC10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISHVAC10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISHVAC10AmmoResult.svslots);
        assertEquals("HVAC/10", actualCreateISHVAC10AmmoResult.shortName);
        assertNull(actualCreateISHVAC10AmmoResult.modes);
        assertTrue(actualCreateISHVAC10AmmoResult.explosive);
        assertEquals(1, actualCreateISHVAC10AmmoResult.criticals);
        assertEquals(20.0, actualCreateISHVAC10AmmoResult.bv, 0.0);
        assertFalse(actualCreateISHVAC10AmmoResult.isSpreadable());
        assertFalse(actualCreateISHVAC10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISHVAC10AmmoResult.isMixedTech());
        assertTrue(actualCreateISHVAC10AmmoResult.isHittable());
        assertFalse(actualCreateISHVAC10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISHVAC10AmmoResult.getToHitModifier());
        assertEquals(3, actualCreateISHVAC10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISHVAC10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISHVAC10AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISHVAC10AmmoResult.getStandardRange());
        assertEquals("285,TO", actualCreateISHVAC10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISHVAC10AmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateISHVAC10AmmoResult.getRawCost(), 0.0);
        assertEquals("HVAC/10 Ammo", actualCreateISHVAC10AmmoResult.getName());
        assertEquals("IS Ammo HVAC/10", actualCreateISHVAC10AmmoResult.getInternalName());
        assertEquals("D/X-X-F-E", actualCreateISHVAC10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISHVAC10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISHVAC10AmmoResult.getFlags());
    }

    private void createCLLB2XClusterAmmo(AmmoType actualCreateCLLB2XClusterAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLB2XClusterAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLB2XClusterAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLB2XClusterAmmoResult.svslots);
        assertEquals("LB-2X Cluster", actualCreateCLLB2XClusterAmmoResult.shortName);
        assertNull(actualCreateCLLB2XClusterAmmoResult.modes);
        assertTrue(actualCreateCLLB2XClusterAmmoResult.explosive);
        assertEquals(1, actualCreateCLLB2XClusterAmmoResult.criticals);
        assertEquals(6.0, actualCreateCLLB2XClusterAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLB2XClusterAmmoResult.isSpreadable());
        assertFalse(actualCreateCLLB2XClusterAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLB2XClusterAmmoResult.isMixedTech());
        assertTrue(actualCreateCLLB2XClusterAmmoResult.isHittable());
        assertFalse(actualCreateCLLB2XClusterAmmoResult.hasInstantModeSwitch());
        assertEquals(AmmoType.T_NA, actualCreateCLLB2XClusterAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLLB2XClusterAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLB2XClusterAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLB2XClusterAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateCLLB2XClusterAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLB2XClusterAmmoResult.getReintroductionDate());
        assertEquals(3300.0, actualCreateCLLB2XClusterAmmoResult.getRawCost(), 0.0);
        assertEquals(2819, actualCreateCLLB2XClusterAmmoResult.getPrototypeDate());
        assertEquals("LB 2-X Cluster Ammo", actualCreateCLLB2XClusterAmmoResult.getName());
        assertEquals("Clan LB 2-X Cluster Ammo", actualCreateCLLB2XClusterAmmoResult.getInternalName());
        assertEquals("E/X-D-C-B", actualCreateCLLB2XClusterAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLB2XClusterAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLB2XClusterAmmoResult.getFlags());
    }

    private void createCLLB5XClusterAmmo(AmmoType actualCreateCLLB5XClusterAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLB5XClusterAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLB5XClusterAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLB5XClusterAmmoResult.svslots);
        assertEquals("LB-5X Cluster", actualCreateCLLB5XClusterAmmoResult.shortName);
        assertNull(actualCreateCLLB5XClusterAmmoResult.modes);
        assertTrue(actualCreateCLLB5XClusterAmmoResult.explosive);
        assertEquals(1, actualCreateCLLB5XClusterAmmoResult.criticals);
        assertEquals(12.0, actualCreateCLLB5XClusterAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLB5XClusterAmmoResult.isSpreadable());
        assertFalse(actualCreateCLLB5XClusterAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLB5XClusterAmmoResult.isMixedTech());
        assertTrue(actualCreateCLLB5XClusterAmmoResult.isHittable());
        assertFalse(actualCreateCLLB5XClusterAmmoResult.hasInstantModeSwitch());
        assertEquals(AmmoType.T_NA, actualCreateCLLB5XClusterAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLLB5XClusterAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLB5XClusterAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLB5XClusterAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateCLLB5XClusterAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLB5XClusterAmmoResult.getReintroductionDate());
        assertEquals(15000.0, actualCreateCLLB5XClusterAmmoResult.getRawCost(), 0.0);
        assertEquals(2819, actualCreateCLLB5XClusterAmmoResult.getPrototypeDate());
        assertEquals("LB 5-X Cluster Ammo", actualCreateCLLB5XClusterAmmoResult.getName());
        assertEquals("Clan LB 5-X Cluster Ammo", actualCreateCLLB5XClusterAmmoResult.getInternalName());
        assertEquals("E/X-D-C-B", actualCreateCLLB5XClusterAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLB5XClusterAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLB5XClusterAmmoResult.getFlags());
    }

    private void createCLLB10XClusterAmmo(AmmoType actualCreateCLLB10XClusterAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLB10XClusterAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLB10XClusterAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLB10XClusterAmmoResult.svslots);
        assertEquals("LB-10X Cluster", actualCreateCLLB10XClusterAmmoResult.shortName);
        assertNull(actualCreateCLLB10XClusterAmmoResult.modes);
        assertTrue(actualCreateCLLB10XClusterAmmoResult.explosive);
        assertEquals(1, actualCreateCLLB10XClusterAmmoResult.criticals);
        assertEquals(19.0, actualCreateCLLB10XClusterAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLB10XClusterAmmoResult.isSpreadable());
        assertFalse(actualCreateCLLB10XClusterAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLB10XClusterAmmoResult.isMixedTech());
        assertTrue(actualCreateCLLB10XClusterAmmoResult.isHittable());
        assertFalse(actualCreateCLLB10XClusterAmmoResult.hasInstantModeSwitch());
        assertEquals(AmmoType.T_NA, actualCreateCLLB10XClusterAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLLB10XClusterAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLB10XClusterAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLB10XClusterAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateCLLB10XClusterAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLB10XClusterAmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateCLLB10XClusterAmmoResult.getRawCost(), 0.0);
        assertEquals(2819, actualCreateCLLB10XClusterAmmoResult.getPrototypeDate());
        assertEquals("LB 10-X Cluster Ammo", actualCreateCLLB10XClusterAmmoResult.getName());
        assertEquals("Clan LB 10-X Cluster Ammo", actualCreateCLLB10XClusterAmmoResult.getInternalName());
        assertEquals("E/X-D-C-B", actualCreateCLLB10XClusterAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLB10XClusterAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLB10XClusterAmmoResult.getFlags());
    }

    private void createCLLB20XClusterAmmo(AmmoType actualCreateCLLB20XClusterAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLB20XClusterAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLB20XClusterAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLB20XClusterAmmoResult.svslots);
        assertEquals("LB-20X Cluster", actualCreateCLLB20XClusterAmmoResult.shortName);
        assertNull(actualCreateCLLB20XClusterAmmoResult.modes);
        assertTrue(actualCreateCLLB20XClusterAmmoResult.explosive);
        assertEquals(1, actualCreateCLLB20XClusterAmmoResult.criticals);
        assertEquals(30.0, actualCreateCLLB20XClusterAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLB20XClusterAmmoResult.isSpreadable());
        assertFalse(actualCreateCLLB20XClusterAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLB20XClusterAmmoResult.isMixedTech());
        assertTrue(actualCreateCLLB20XClusterAmmoResult.isHittable());
        assertFalse(actualCreateCLLB20XClusterAmmoResult.hasInstantModeSwitch());
        assertEquals(AmmoType.T_NA, actualCreateCLLB20XClusterAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLLB20XClusterAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLB20XClusterAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLB20XClusterAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateCLLB20XClusterAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLB20XClusterAmmoResult.getReintroductionDate());
        assertEquals(34000.0, actualCreateCLLB20XClusterAmmoResult.getRawCost(), 0.0);
        assertEquals(2819, actualCreateCLLB20XClusterAmmoResult.getPrototypeDate());
        assertEquals("LB 20-X Cluster Ammo", actualCreateCLLB20XClusterAmmoResult.getName());
        assertEquals("Clan LB 20-X Cluster Ammo", actualCreateCLLB20XClusterAmmoResult.getInternalName());
        assertEquals("E/X-D-C-B", actualCreateCLLB20XClusterAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLB20XClusterAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLB20XClusterAmmoResult.getFlags());
    }

    private void createISLB2XClusterAmmo(AmmoType actualCreateISLB2XClusterAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLB2XClusterAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLB2XClusterAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLB2XClusterAmmoResult.svslots);
        assertEquals("LB 2-X Cluster", actualCreateISLB2XClusterAmmoResult.shortName);
        assertNull(actualCreateISLB2XClusterAmmoResult.modes);
        assertTrue(actualCreateISLB2XClusterAmmoResult.explosive);
        assertEquals(1, actualCreateISLB2XClusterAmmoResult.criticals);
        assertEquals(5.0, actualCreateISLB2XClusterAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLB2XClusterAmmoResult.isSpreadable());
        assertFalse(actualCreateISLB2XClusterAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLB2XClusterAmmoResult.isMixedTech());
        assertTrue(actualCreateISLB2XClusterAmmoResult.isHittable());
        assertFalse(actualCreateISLB2XClusterAmmoResult.hasInstantModeSwitch());
        assertEquals(AmmoType.T_NA, actualCreateISLB2XClusterAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISLB2XClusterAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLB2XClusterAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLB2XClusterAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISLB2XClusterAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLB2XClusterAmmoResult.getReintroductionDate());
        assertEquals(3300.0, actualCreateISLB2XClusterAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISLB2XClusterAmmoResult.getPrototypeDate());
        assertEquals("LB 2-X Cluster Ammo", actualCreateISLB2XClusterAmmoResult.getName());
        assertEquals("IS LB 2-X Cluster Ammo", actualCreateISLB2XClusterAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISLB2XClusterAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLB2XClusterAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLB2XClusterAmmoResult.getFlags());
    }

    private void createISLB5XClusterAmmo(AmmoType actualCreateISLB5XClusterAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLB5XClusterAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLB5XClusterAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLB5XClusterAmmoResult.svslots);
        assertEquals("LB 5-X Cluster", actualCreateISLB5XClusterAmmoResult.shortName);
        assertNull(actualCreateISLB5XClusterAmmoResult.modes);
        assertTrue(actualCreateISLB5XClusterAmmoResult.explosive);
        assertEquals(1, actualCreateISLB5XClusterAmmoResult.criticals);
        assertEquals(10.0, actualCreateISLB5XClusterAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLB5XClusterAmmoResult.isSpreadable());
        assertFalse(actualCreateISLB5XClusterAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLB5XClusterAmmoResult.isMixedTech());
        assertTrue(actualCreateISLB5XClusterAmmoResult.isHittable());
        assertFalse(actualCreateISLB5XClusterAmmoResult.hasInstantModeSwitch());
        assertEquals(AmmoType.T_NA, actualCreateISLB5XClusterAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISLB5XClusterAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLB5XClusterAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLB5XClusterAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISLB5XClusterAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLB5XClusterAmmoResult.getReintroductionDate());
        assertEquals(15000.0, actualCreateISLB5XClusterAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISLB5XClusterAmmoResult.getPrototypeDate());
        assertEquals("LB 5-X Cluster Ammo", actualCreateISLB5XClusterAmmoResult.getName());
        assertEquals("IS LB 5-X Cluster Ammo", actualCreateISLB5XClusterAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISLB5XClusterAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLB5XClusterAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLB5XClusterAmmoResult.getFlags());
    }

    private void createISLB10XClusterAmmo(AmmoType actualCreateISLB10XClusterAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLB10XClusterAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLB10XClusterAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLB10XClusterAmmoResult.svslots);
        assertEquals("LB 10-X Cluster", actualCreateISLB10XClusterAmmoResult.shortName);
        assertNull(actualCreateISLB10XClusterAmmoResult.modes);
        assertTrue(actualCreateISLB10XClusterAmmoResult.explosive);
        assertEquals(1, actualCreateISLB10XClusterAmmoResult.criticals);
        assertEquals(19.0, actualCreateISLB10XClusterAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLB10XClusterAmmoResult.isSpreadable());
        assertFalse(actualCreateISLB10XClusterAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLB10XClusterAmmoResult.isMixedTech());
        assertTrue(actualCreateISLB10XClusterAmmoResult.isHittable());
        assertFalse(actualCreateISLB10XClusterAmmoResult.hasInstantModeSwitch());
        assertEquals(AmmoType.T_NA, actualCreateISLB10XClusterAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISLB10XClusterAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLB10XClusterAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLB10XClusterAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISLB10XClusterAmmoResult.getRulesRefs());
        assertEquals(3035, actualCreateISLB10XClusterAmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateISLB10XClusterAmmoResult.getRawCost(), 0.0);
        assertEquals(2590, actualCreateISLB10XClusterAmmoResult.getPrototypeDate());
        assertEquals("LB 10-X Cluster Ammo", actualCreateISLB10XClusterAmmoResult.getName());
        assertEquals("IS LB 10-X Cluster Ammo", actualCreateISLB10XClusterAmmoResult.getInternalName());
        assertEquals("E/E-F(F*)-D-C", actualCreateISLB10XClusterAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLB10XClusterAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLB10XClusterAmmoResult.getFlags());
    }

    private void createISLB20XClusterAmmo(AmmoType actualCreateISLB20XClusterAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLB20XClusterAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLB20XClusterAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLB20XClusterAmmoResult.svslots);
        assertEquals("LB 20-X Cluster", actualCreateISLB20XClusterAmmoResult.shortName);
        assertNull(actualCreateISLB20XClusterAmmoResult.modes);
        assertTrue(actualCreateISLB20XClusterAmmoResult.explosive);
        assertEquals(1, actualCreateISLB20XClusterAmmoResult.criticals);
        assertEquals(30.0, actualCreateISLB20XClusterAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLB20XClusterAmmoResult.isSpreadable());
        assertFalse(actualCreateISLB20XClusterAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLB20XClusterAmmoResult.isMixedTech());
        assertTrue(actualCreateISLB20XClusterAmmoResult.isHittable());
        assertFalse(actualCreateISLB20XClusterAmmoResult.hasInstantModeSwitch());
        assertEquals(AmmoType.T_NA, actualCreateISLB20XClusterAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISLB20XClusterAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLB20XClusterAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLB20XClusterAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISLB20XClusterAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLB20XClusterAmmoResult.getReintroductionDate());
        assertEquals(34000.0, actualCreateISLB20XClusterAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISLB20XClusterAmmoResult.getPrototypeDate());
        assertEquals("LB 20-X Cluster Ammo", actualCreateISLB20XClusterAmmoResult.getName());
        assertEquals("IS LB 20-X Cluster Ammo", actualCreateISLB20XClusterAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISLB20XClusterAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLB20XClusterAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLB20XClusterAmmoResult.getFlags());
    }

    private void createCLLB2XAmmo(AmmoType actualCreateCLLB2XAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLB2XAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLB2XAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLB2XAmmoResult.svslots);
        assertEquals("LB-2X", actualCreateCLLB2XAmmoResult.shortName);
        assertNull(actualCreateCLLB2XAmmoResult.modes);
        assertTrue(actualCreateCLLB2XAmmoResult.explosive);
        assertEquals(1, actualCreateCLLB2XAmmoResult.criticals);
        assertEquals(6.0, actualCreateCLLB2XAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLB2XAmmoResult.isSpreadable());
        assertFalse(actualCreateCLLB2XAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLB2XAmmoResult.isMixedTech());
        assertTrue(actualCreateCLLB2XAmmoResult.isHittable());
        assertFalse(actualCreateCLLB2XAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLB2XAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLLB2XAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLB2XAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLB2XAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateCLLB2XAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLB2XAmmoResult.getReintroductionDate());
        assertEquals(2000.0, actualCreateCLLB2XAmmoResult.getRawCost(), 0.0);
        assertEquals(2819, actualCreateCLLB2XAmmoResult.getPrototypeDate());
        assertEquals("LB 2-X AC Ammo", actualCreateCLLB2XAmmoResult.getName());
        assertEquals("Clan LB 2-X AC Ammo", actualCreateCLLB2XAmmoResult.getInternalName());
        assertEquals("E/X-D-C-B", actualCreateCLLB2XAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLB2XAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLB2XAmmoResult.getFlags());
    }

    private void createCLLB5XAmmo(AmmoType actualCreateCLLB5XAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLB5XAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLB5XAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLB5XAmmoResult.svslots);
        assertEquals("LB-5X", actualCreateCLLB5XAmmoResult.shortName);
        assertNull(actualCreateCLLB5XAmmoResult.modes);
        assertTrue(actualCreateCLLB5XAmmoResult.explosive);
        assertEquals(1, actualCreateCLLB5XAmmoResult.criticals);
        assertEquals(12.0, actualCreateCLLB5XAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLB5XAmmoResult.isSpreadable());
        assertFalse(actualCreateCLLB5XAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLB5XAmmoResult.isMixedTech());
        assertTrue(actualCreateCLLB5XAmmoResult.isHittable());
        assertFalse(actualCreateCLLB5XAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLB5XAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLLB5XAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLB5XAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLB5XAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateCLLB5XAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLB5XAmmoResult.getReintroductionDate());
        assertEquals(9000.0, actualCreateCLLB5XAmmoResult.getRawCost(), 0.0);
        assertEquals(2819, actualCreateCLLB5XAmmoResult.getPrototypeDate());
        assertEquals("LB 5-X AC Ammo", actualCreateCLLB5XAmmoResult.getName());
        assertEquals("Clan LB 5-X AC Ammo", actualCreateCLLB5XAmmoResult.getInternalName());
        assertEquals("E/X-D-C-B", actualCreateCLLB5XAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLB5XAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLB5XAmmoResult.getFlags());
    }

    private void createCLLB10XAmmo(AmmoType actualCreateCLLB10XAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLB10XAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLB10XAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLB10XAmmoResult.svslots);
        assertEquals("LB-10X", actualCreateCLLB10XAmmoResult.shortName);
        assertNull(actualCreateCLLB10XAmmoResult.modes);
        assertTrue(actualCreateCLLB10XAmmoResult.explosive);
        assertEquals(1, actualCreateCLLB10XAmmoResult.criticals);
        assertEquals(19.0, actualCreateCLLB10XAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLB10XAmmoResult.isSpreadable());
        assertFalse(actualCreateCLLB10XAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLB10XAmmoResult.isMixedTech());
        assertTrue(actualCreateCLLB10XAmmoResult.isHittable());
        assertFalse(actualCreateCLLB10XAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLB10XAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLLB10XAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLB10XAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLB10XAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateCLLB10XAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLB10XAmmoResult.getReintroductionDate());
        assertEquals(12000.0, actualCreateCLLB10XAmmoResult.getRawCost(), 0.0);
        assertEquals(2819, actualCreateCLLB10XAmmoResult.getPrototypeDate());
        assertEquals("LB 10-X AC Ammo", actualCreateCLLB10XAmmoResult.getName());
        assertEquals("Clan LB 10-X AC Ammo", actualCreateCLLB10XAmmoResult.getInternalName());
        assertEquals("E/X-D-C-B", actualCreateCLLB10XAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLB10XAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLB10XAmmoResult.getFlags());
    }

    private void createCLLB20XAmmo(AmmoType actualCreateCLLB20XAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLB20XAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLB20XAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLB20XAmmoResult.svslots);
        assertEquals("LB-20X", actualCreateCLLB20XAmmoResult.shortName);
        assertNull(actualCreateCLLB20XAmmoResult.modes);
        assertTrue(actualCreateCLLB20XAmmoResult.explosive);
        assertEquals(1, actualCreateCLLB20XAmmoResult.criticals);
        assertEquals(30.0, actualCreateCLLB20XAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLB20XAmmoResult.isSpreadable());
        assertFalse(actualCreateCLLB20XAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLB20XAmmoResult.isMixedTech());
        assertTrue(actualCreateCLLB20XAmmoResult.isHittable());
        assertFalse(actualCreateCLLB20XAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLB20XAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLLB20XAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLB20XAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLB20XAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateCLLB20XAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLB20XAmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateCLLB20XAmmoResult.getRawCost(), 0.0);
        assertEquals(2819, actualCreateCLLB20XAmmoResult.getPrototypeDate());
        assertEquals("LB 20-X AC Ammo", actualCreateCLLB20XAmmoResult.getName());
        assertEquals("Clan LB 20-X AC Ammo", actualCreateCLLB20XAmmoResult.getInternalName());
        assertEquals("E/X-D-C-B", actualCreateCLLB20XAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLB20XAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLB20XAmmoResult.getFlags());
    }

    private void createISLB2XAmmo(AmmoType actualCreateISLB2XAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLB2XAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLB2XAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLB2XAmmoResult.svslots);
        assertEquals("LB 2-X", actualCreateISLB2XAmmoResult.shortName);
        assertNull(actualCreateISLB2XAmmoResult.modes);
        assertTrue(actualCreateISLB2XAmmoResult.explosive);
        assertEquals(1, actualCreateISLB2XAmmoResult.criticals);
        assertEquals(5.0, actualCreateISLB2XAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLB2XAmmoResult.isSpreadable());
        assertFalse(actualCreateISLB2XAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLB2XAmmoResult.isMixedTech());
        assertTrue(actualCreateISLB2XAmmoResult.isHittable());
        assertFalse(actualCreateISLB2XAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLB2XAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISLB2XAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLB2XAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLB2XAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISLB2XAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLB2XAmmoResult.getReintroductionDate());
        assertEquals(2000.0, actualCreateISLB2XAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISLB2XAmmoResult.getPrototypeDate());
        assertEquals("LB 2-X AC Ammo", actualCreateISLB2XAmmoResult.getName());
        assertEquals("IS LB 2-X AC Ammo", actualCreateISLB2XAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISLB2XAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLB2XAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLB2XAmmoResult.getFlags());
    }

    private void createISLB5XAmmo(AmmoType actualCreateISLB5XAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLB5XAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLB5XAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLB5XAmmoResult.svslots);
        assertEquals("LB 5-X", actualCreateISLB5XAmmoResult.shortName);
        assertNull(actualCreateISLB5XAmmoResult.modes);
        assertTrue(actualCreateISLB5XAmmoResult.explosive);
        assertEquals(1, actualCreateISLB5XAmmoResult.criticals);
        assertEquals(10.0, actualCreateISLB5XAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLB5XAmmoResult.isSpreadable());
        assertFalse(actualCreateISLB5XAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLB5XAmmoResult.isMixedTech());
        assertTrue(actualCreateISLB5XAmmoResult.isHittable());
        assertFalse(actualCreateISLB5XAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLB5XAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISLB5XAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLB5XAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLB5XAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISLB5XAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLB5XAmmoResult.getReintroductionDate());
        assertEquals(9000.0, actualCreateISLB5XAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISLB5XAmmoResult.getPrototypeDate());
        assertEquals("LB 5-X AC Ammo", actualCreateISLB5XAmmoResult.getName());
        assertEquals("IS LB 5-X AC Ammo", actualCreateISLB5XAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISLB5XAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLB5XAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLB5XAmmoResult.getFlags());
    }

    private void createISLB10XAmmo(AmmoType actualCreateISLB10XAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLB10XAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLB10XAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLB10XAmmoResult.svslots);
        assertEquals("LB 10-X", actualCreateISLB10XAmmoResult.shortName);
        assertNull(actualCreateISLB10XAmmoResult.modes);
        assertTrue(actualCreateISLB10XAmmoResult.explosive);
        assertEquals(1, actualCreateISLB10XAmmoResult.criticals);
        assertEquals(19.0, actualCreateISLB10XAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLB10XAmmoResult.isSpreadable());
        assertFalse(actualCreateISLB10XAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLB10XAmmoResult.isMixedTech());
        assertTrue(actualCreateISLB10XAmmoResult.isHittable());
        assertFalse(actualCreateISLB10XAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLB10XAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISLB10XAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLB10XAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLB10XAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISLB10XAmmoResult.getRulesRefs());
        assertEquals(3035, actualCreateISLB10XAmmoResult.getReintroductionDate());
        assertEquals(12000.0, actualCreateISLB10XAmmoResult.getRawCost(), 0.0);
        assertEquals(2590, actualCreateISLB10XAmmoResult.getPrototypeDate());
        assertEquals("LB 10-X AC Ammo", actualCreateISLB10XAmmoResult.getName());
        assertEquals("IS LB 10-X AC Ammo", actualCreateISLB10XAmmoResult.getInternalName());
        assertEquals("E/E-F(F*)-D-C", actualCreateISLB10XAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLB10XAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLB10XAmmoResult.getFlags());
    }

    private void createISLB20XAmmo(AmmoType actualCreateISLB20XAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLB20XAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLB20XAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLB20XAmmoResult.svslots);
        assertEquals("LB 20-X", actualCreateISLB20XAmmoResult.shortName);
        assertNull(actualCreateISLB20XAmmoResult.modes);
        assertTrue(actualCreateISLB20XAmmoResult.explosive);
        assertEquals(1, actualCreateISLB20XAmmoResult.criticals);
        assertEquals(30.0, actualCreateISLB20XAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLB20XAmmoResult.isSpreadable());
        assertFalse(actualCreateISLB20XAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLB20XAmmoResult.isMixedTech());
        assertTrue(actualCreateISLB20XAmmoResult.isHittable());
        assertFalse(actualCreateISLB20XAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLB20XAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISLB20XAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLB20XAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLB20XAmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISLB20XAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLB20XAmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateISLB20XAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISLB20XAmmoResult.getPrototypeDate());
        assertEquals("LB 20-X AC Ammo", actualCreateISLB20XAmmoResult.getName());
        assertEquals("IS LB 20-X AC Ammo", actualCreateISLB20XAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISLB20XAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLB20XAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLB20XAmmoResult.getFlags());
    }

    private void createCLUltra2Ammo(AmmoType actualCreateCLUltra2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLUltra2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLUltra2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLUltra2AmmoResult.svslots);
        assertEquals("Ultra AC/2", actualCreateCLUltra2AmmoResult.shortName);
        assertNull(actualCreateCLUltra2AmmoResult.modes);
        assertTrue(actualCreateCLUltra2AmmoResult.explosive);
        assertEquals(1, actualCreateCLUltra2AmmoResult.criticals);
        assertEquals(8.0, actualCreateCLUltra2AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLUltra2AmmoResult.isSpreadable());
        assertFalse(actualCreateCLUltra2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLUltra2AmmoResult.isMixedTech());
        assertTrue(actualCreateCLUltra2AmmoResult.isHittable());
        assertFalse(actualCreateCLUltra2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLUltra2AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLUltra2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLUltra2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLUltra2AmmoResult.getStaticTechLevel());
        assertEquals("208,TM", actualCreateCLUltra2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLUltra2AmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateCLUltra2AmmoResult.getRawCost(), 0.0);
        assertEquals(2820, actualCreateCLUltra2AmmoResult.getPrototypeDate());
        assertEquals("Ultra AC/2 Ammo", actualCreateCLUltra2AmmoResult.getName());
        assertEquals("Clan Ultra AC/2 Ammo", actualCreateCLUltra2AmmoResult.getInternalName());
        assertEquals("E/X-D-D-C", actualCreateCLUltra2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLUltra2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLUltra2AmmoResult.getFlags());
    }

    private void createCLUltra5Ammo(AmmoType actualCreateCLUltra5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLUltra5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLUltra5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLUltra5AmmoResult.svslots);
        assertEquals("Ultra AC/5", actualCreateCLUltra5AmmoResult.shortName);
        assertNull(actualCreateCLUltra5AmmoResult.modes);
        assertTrue(actualCreateCLUltra5AmmoResult.explosive);
        assertEquals(1, actualCreateCLUltra5AmmoResult.criticals);
        assertEquals(15.0, actualCreateCLUltra5AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLUltra5AmmoResult.isSpreadable());
        assertFalse(actualCreateCLUltra5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLUltra5AmmoResult.isMixedTech());
        assertTrue(actualCreateCLUltra5AmmoResult.isHittable());
        assertFalse(actualCreateCLUltra5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLUltra5AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLUltra5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLUltra5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLUltra5AmmoResult.getStaticTechLevel());
        assertEquals("208,TM", actualCreateCLUltra5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLUltra5AmmoResult.getReintroductionDate());
        assertEquals(9000.0, actualCreateCLUltra5AmmoResult.getRawCost(), 0.0);
        assertEquals(2820, actualCreateCLUltra5AmmoResult.getPrototypeDate());
        assertEquals("Ultra AC/5 Ammo", actualCreateCLUltra5AmmoResult.getName());
        assertEquals("Clan Ultra AC/5 Ammo", actualCreateCLUltra5AmmoResult.getInternalName());
        assertEquals("E/X-D-D-C", actualCreateCLUltra5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLUltra5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLUltra5AmmoResult.getFlags());
    }

    private void createCLUltra10Ammo(AmmoType actualCreateCLUltra10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLUltra10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLUltra10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLUltra10AmmoResult.svslots);
        assertEquals("Ultra AC/10", actualCreateCLUltra10AmmoResult.shortName);
        assertNull(actualCreateCLUltra10AmmoResult.modes);
        assertTrue(actualCreateCLUltra10AmmoResult.explosive);
        assertEquals(1, actualCreateCLUltra10AmmoResult.criticals);
        assertEquals(26.0, actualCreateCLUltra10AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLUltra10AmmoResult.isSpreadable());
        assertFalse(actualCreateCLUltra10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLUltra10AmmoResult.isMixedTech());
        assertTrue(actualCreateCLUltra10AmmoResult.isHittable());
        assertFalse(actualCreateCLUltra10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLUltra10AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLUltra10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLUltra10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLUltra10AmmoResult.getStaticTechLevel());
        assertEquals("208,TM", actualCreateCLUltra10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLUltra10AmmoResult.getReintroductionDate());
        assertEquals(12000.0, actualCreateCLUltra10AmmoResult.getRawCost(), 0.0);
        assertEquals(2820, actualCreateCLUltra10AmmoResult.getPrototypeDate());
        assertEquals("Ultra AC/10 Ammo", actualCreateCLUltra10AmmoResult.getName());
        assertEquals("Clan Ultra AC/10 Ammo", actualCreateCLUltra10AmmoResult.getInternalName());
        assertEquals("E/X-D-D-C", actualCreateCLUltra10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLUltra10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLUltra10AmmoResult.getFlags());
    }

    private void createCLUltra20Ammo(AmmoType actualCreateCLUltra20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLUltra20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLUltra20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLUltra20AmmoResult.svslots);
        assertEquals("Ultra AC/20", actualCreateCLUltra20AmmoResult.shortName);
        assertNull(actualCreateCLUltra20AmmoResult.modes);
        assertTrue(actualCreateCLUltra20AmmoResult.explosive);
        assertEquals(1, actualCreateCLUltra20AmmoResult.criticals);
        assertEquals(42.0, actualCreateCLUltra20AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLUltra20AmmoResult.isSpreadable());
        assertFalse(actualCreateCLUltra20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLUltra20AmmoResult.isMixedTech());
        assertTrue(actualCreateCLUltra20AmmoResult.isHittable());
        assertFalse(actualCreateCLUltra20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLUltra20AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLUltra20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLUltra20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLUltra20AmmoResult.getStaticTechLevel());
        assertEquals("208,TM", actualCreateCLUltra20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLUltra20AmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateCLUltra20AmmoResult.getRawCost(), 0.0);
        assertEquals(2820, actualCreateCLUltra20AmmoResult.getPrototypeDate());
        assertEquals("Ultra AC/20 Ammo", actualCreateCLUltra20AmmoResult.getName());
        assertEquals("Clan Ultra AC/20 Ammo", actualCreateCLUltra20AmmoResult.getInternalName());
        assertEquals("E/X-D-D-C", actualCreateCLUltra20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLUltra20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLUltra20AmmoResult.getFlags());
    }

    private void createISUltra2Ammo(AmmoType actualCreateISUltra2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISUltra2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISUltra2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISUltra2AmmoResult.svslots);
        assertEquals("Ultra AC/2", actualCreateISUltra2AmmoResult.shortName);
        assertNull(actualCreateISUltra2AmmoResult.modes);
        assertTrue(actualCreateISUltra2AmmoResult.explosive);
        assertEquals(1, actualCreateISUltra2AmmoResult.criticals);
        assertEquals(7.0, actualCreateISUltra2AmmoResult.bv, 0.0);
        assertFalse(actualCreateISUltra2AmmoResult.isSpreadable());
        assertFalse(actualCreateISUltra2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISUltra2AmmoResult.isMixedTech());
        assertTrue(actualCreateISUltra2AmmoResult.isHittable());
        assertFalse(actualCreateISUltra2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISUltra2AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISUltra2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISUltra2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISUltra2AmmoResult.getStaticTechLevel());
        assertEquals("208,TM", actualCreateISUltra2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISUltra2AmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateISUltra2AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISUltra2AmmoResult.getPrototypeDate());
        assertEquals("Ultra AC/2 Ammo", actualCreateISUltra2AmmoResult.getName());
        assertEquals("IS Ultra AC/2 Ammo", actualCreateISUltra2AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISUltra2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISUltra2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISUltra2AmmoResult.getFlags());
    }

    private void createISUltra5Ammo(AmmoType actualCreateISUltra5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISUltra5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISUltra5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISUltra5AmmoResult.svslots);
        assertEquals("Ultra AC/5", actualCreateISUltra5AmmoResult.shortName);
        assertNull(actualCreateISUltra5AmmoResult.modes);
        assertTrue(actualCreateISUltra5AmmoResult.explosive);
        assertEquals(1, actualCreateISUltra5AmmoResult.criticals);
        assertEquals(14.0, actualCreateISUltra5AmmoResult.bv, 0.0);
        assertFalse(actualCreateISUltra5AmmoResult.isSpreadable());
        assertFalse(actualCreateISUltra5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISUltra5AmmoResult.isMixedTech());
        assertTrue(actualCreateISUltra5AmmoResult.isHittable());
        assertFalse(actualCreateISUltra5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISUltra5AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISUltra5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISUltra5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISUltra5AmmoResult.getStaticTechLevel());
        assertEquals("208,TM", actualCreateISUltra5AmmoResult.getRulesRefs());
        assertEquals(3035, actualCreateISUltra5AmmoResult.getReintroductionDate());
        assertEquals(9000.0, actualCreateISUltra5AmmoResult.getRawCost(), 0.0);
        assertEquals(2630, actualCreateISUltra5AmmoResult.getPrototypeDate());
        assertEquals("Ultra AC/5 Ammo", actualCreateISUltra5AmmoResult.getName());
        assertEquals("IS Ultra AC/5 Ammo", actualCreateISUltra5AmmoResult.getInternalName());
        assertEquals("E/D-F(F*)-D-D", actualCreateISUltra5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISUltra5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISUltra5AmmoResult.getFlags());
    }

    private void createISUltra10Ammo(AmmoType actualCreateISUltra10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISUltra10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISUltra10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISUltra10AmmoResult.svslots);
        assertEquals("Ultra AC/10", actualCreateISUltra10AmmoResult.shortName);
        assertNull(actualCreateISUltra10AmmoResult.modes);
        assertTrue(actualCreateISUltra10AmmoResult.explosive);
        assertEquals(1, actualCreateISUltra10AmmoResult.criticals);
        assertEquals(26.0, actualCreateISUltra10AmmoResult.bv, 0.0);
        assertFalse(actualCreateISUltra10AmmoResult.isSpreadable());
        assertFalse(actualCreateISUltra10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISUltra10AmmoResult.isMixedTech());
        assertTrue(actualCreateISUltra10AmmoResult.isHittable());
        assertFalse(actualCreateISUltra10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISUltra10AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISUltra10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISUltra10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISUltra10AmmoResult.getStaticTechLevel());
        assertEquals("208,TM", actualCreateISUltra10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISUltra10AmmoResult.getReintroductionDate());
        assertEquals(12000.0, actualCreateISUltra10AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISUltra10AmmoResult.getPrototypeDate());
        assertEquals("Ultra AC/10 Ammo", actualCreateISUltra10AmmoResult.getName());
        assertEquals("IS Ultra AC/10 Ammo", actualCreateISUltra10AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISUltra10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISUltra10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISUltra10AmmoResult.getFlags());
    }

    private void createISUltra20Ammo(AmmoType actualCreateISUltra20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISUltra20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISUltra20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISUltra20AmmoResult.svslots);
        assertEquals("Ultra AC/20", actualCreateISUltra20AmmoResult.shortName);
        assertNull(actualCreateISUltra20AmmoResult.modes);
        assertTrue(actualCreateISUltra20AmmoResult.explosive);
        assertEquals(1, actualCreateISUltra20AmmoResult.criticals);
        assertEquals(35.0, actualCreateISUltra20AmmoResult.bv, 0.0);
        assertFalse(actualCreateISUltra20AmmoResult.isSpreadable());
        assertFalse(actualCreateISUltra20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISUltra20AmmoResult.isMixedTech());
        assertTrue(actualCreateISUltra20AmmoResult.isHittable());
        assertFalse(actualCreateISUltra20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISUltra20AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISUltra20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISUltra20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISUltra20AmmoResult.getStaticTechLevel());
        assertEquals("208,TM", actualCreateISUltra20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISUltra20AmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateISUltra20AmmoResult.getRawCost(), 0.0);
        assertEquals(3052, actualCreateISUltra20AmmoResult.getPrototypeDate());
        assertEquals("Ultra AC/20 Ammo", actualCreateISUltra20AmmoResult.getName());
        assertEquals("IS Ultra AC/20 Ammo", actualCreateISUltra20AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISUltra20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISUltra20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISUltra20AmmoResult.getFlags());
    }

    private void createISRotary2Ammo(AmmoType actualCreateISRotary2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISRotary2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISRotary2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISRotary2AmmoResult.svslots);
        assertEquals("RAC/2", actualCreateISRotary2AmmoResult.shortName);
        assertNull(actualCreateISRotary2AmmoResult.modes);
        assertTrue(actualCreateISRotary2AmmoResult.explosive);
        assertEquals(1, actualCreateISRotary2AmmoResult.criticals);
        assertEquals(15.0, actualCreateISRotary2AmmoResult.bv, 0.0);
        assertFalse(actualCreateISRotary2AmmoResult.isSpreadable());
        assertFalse(actualCreateISRotary2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISRotary2AmmoResult.isMixedTech());
        assertTrue(actualCreateISRotary2AmmoResult.isHittable());
        assertFalse(actualCreateISRotary2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISRotary2AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISRotary2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISRotary2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISRotary2AmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISRotary2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISRotary2AmmoResult.getReintroductionDate());
        assertEquals(3000.0, actualCreateISRotary2AmmoResult.getRawCost(), 0.0);
        assertEquals(3055, actualCreateISRotary2AmmoResult.getPrototypeDate());
        assertEquals("Rotary AC/2 Ammo", actualCreateISRotary2AmmoResult.getName());
        assertEquals("ISRotaryAC2 Ammo", actualCreateISRotary2AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISRotary2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISRotary2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISRotary2AmmoResult.getFlags());
    }

    private void createISRotary5Ammo(AmmoType actualCreateISRotary5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISRotary5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISRotary5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISRotary5AmmoResult.svslots);
        assertEquals("RAC/5", actualCreateISRotary5AmmoResult.shortName);
        assertNull(actualCreateISRotary5AmmoResult.modes);
        assertTrue(actualCreateISRotary5AmmoResult.explosive);
        assertEquals(1, actualCreateISRotary5AmmoResult.criticals);
        assertEquals(31.0, actualCreateISRotary5AmmoResult.bv, 0.0);
        assertFalse(actualCreateISRotary5AmmoResult.isSpreadable());
        assertFalse(actualCreateISRotary5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISRotary5AmmoResult.isMixedTech());
        assertTrue(actualCreateISRotary5AmmoResult.isHittable());
        assertFalse(actualCreateISRotary5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISRotary5AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISRotary5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISRotary5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISRotary5AmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISRotary5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISRotary5AmmoResult.getReintroductionDate());
        assertEquals(12000.0, actualCreateISRotary5AmmoResult.getRawCost(), 0.0);
        assertEquals(3055, actualCreateISRotary5AmmoResult.getPrototypeDate());
        assertEquals("Rotary AC/5 Ammo", actualCreateISRotary5AmmoResult.getName());
        assertEquals("ISRotaryAC5 Ammo", actualCreateISRotary5AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISRotary5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISRotary5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISRotary5AmmoResult.getFlags());
    }

    private void createCLRotary2Ammo(AmmoType actualCreateCLRotary2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLRotary2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLRotary2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLRotary2AmmoResult.svslots);
        assertEquals("RAC/2", actualCreateCLRotary2AmmoResult.shortName);
        assertNull(actualCreateCLRotary2AmmoResult.modes);
        assertTrue(actualCreateCLRotary2AmmoResult.explosive);
        assertEquals(1, actualCreateCLRotary2AmmoResult.criticals);
        assertEquals(20.0, actualCreateCLRotary2AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLRotary2AmmoResult.isSpreadable());
        assertFalse(actualCreateCLRotary2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLRotary2AmmoResult.isMixedTech());
        assertTrue(actualCreateCLRotary2AmmoResult.isHittable());
        assertFalse(actualCreateCLRotary2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLRotary2AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLRotary2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLRotary2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLRotary2AmmoResult.getStaticTechLevel());
        assertEquals("286,TO", actualCreateCLRotary2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLRotary2AmmoResult.getReintroductionDate());
        assertEquals(5000.0, actualCreateCLRotary2AmmoResult.getRawCost(), 0.0);
        assertEquals(3073, actualCreateCLRotary2AmmoResult.getPrototypeDate());
        assertEquals("Rotary AC/2 Ammo", actualCreateCLRotary2AmmoResult.getName());
        assertEquals("CLRotaryAC2 Ammo", actualCreateCLRotary2AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLRotary2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLRotary2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLRotary2AmmoResult.getFlags());
    }

    private void createCLRotary5Ammo(AmmoType actualCreateCLRotary5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLRotary5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLRotary5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLRotary5AmmoResult.svslots);
        assertEquals("RAC/5", actualCreateCLRotary5AmmoResult.shortName);
        assertNull(actualCreateCLRotary5AmmoResult.modes);
        assertTrue(actualCreateCLRotary5AmmoResult.explosive);
        assertEquals(1, actualCreateCLRotary5AmmoResult.criticals);
        assertEquals(43.0, actualCreateCLRotary5AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLRotary5AmmoResult.isSpreadable());
        assertFalse(actualCreateCLRotary5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLRotary5AmmoResult.isMixedTech());
        assertTrue(actualCreateCLRotary5AmmoResult.isHittable());
        assertFalse(actualCreateCLRotary5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLRotary5AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLRotary5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLRotary5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLRotary5AmmoResult.getStaticTechLevel());
        assertEquals("286,TO", actualCreateCLRotary5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLRotary5AmmoResult.getReintroductionDate());
        assertEquals(13000.0, actualCreateCLRotary5AmmoResult.getRawCost(), 0.0);
        assertEquals(3073, actualCreateCLRotary5AmmoResult.getPrototypeDate());
        assertEquals("Rotary AC/5 Ammo", actualCreateCLRotary5AmmoResult.getName());
        assertEquals("CLRotaryAC5 Ammo", actualCreateCLRotary5AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLRotary5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLRotary5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLRotary5AmmoResult.getFlags());
    }

    private void createISLightRifleAmmo(AmmoType actualCreateISLightRifleAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLightRifleAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLightRifleAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLightRifleAmmoResult.svslots);
        assertEquals("Light Rifle", actualCreateISLightRifleAmmoResult.shortName);
        assertNull(actualCreateISLightRifleAmmoResult.modes);
        assertTrue(actualCreateISLightRifleAmmoResult.explosive);
        assertEquals(1, actualCreateISLightRifleAmmoResult.criticals);
        assertEquals(3.0, actualCreateISLightRifleAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLightRifleAmmoResult.isSpreadable());
        assertFalse(actualCreateISLightRifleAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLightRifleAmmoResult.isMixedTech());
        assertTrue(actualCreateISLightRifleAmmoResult.isHittable());
        assertFalse(actualCreateISLightRifleAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLightRifleAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISLightRifleAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLightRifleAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISLightRifleAmmoResult.getStaticTechLevel());
        assertEquals("338,TO", actualCreateISLightRifleAmmoResult.getRulesRefs());
        assertEquals(3079, actualCreateISLightRifleAmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateISLightRifleAmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_PS, actualCreateISLightRifleAmmoResult.getPrototypeDate());
        assertEquals("Light Rifle Ammo", actualCreateISLightRifleAmmoResult.getName());
        assertEquals("IS Ammo Light Rifle", actualCreateISLightRifleAmmoResult.getInternalName());
        assertEquals("B/C-F(F*)-X-D", actualCreateISLightRifleAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLightRifleAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLightRifleAmmoResult.getFlags());
    }

    private void createISMediumRifleAmmo(AmmoType actualCreateISMediumRifleAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMediumRifleAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMediumRifleAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMediumRifleAmmoResult.svslots);
        assertEquals("Medium Rifle", actualCreateISMediumRifleAmmoResult.shortName);
        assertNull(actualCreateISMediumRifleAmmoResult.modes);
        assertTrue(actualCreateISMediumRifleAmmoResult.explosive);
        assertEquals(1, actualCreateISMediumRifleAmmoResult.criticals);
        assertEquals(6.0, actualCreateISMediumRifleAmmoResult.bv, 0.0);
        assertFalse(actualCreateISMediumRifleAmmoResult.isSpreadable());
        assertFalse(actualCreateISMediumRifleAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMediumRifleAmmoResult.isMixedTech());
        assertTrue(actualCreateISMediumRifleAmmoResult.isHittable());
        assertFalse(actualCreateISMediumRifleAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMediumRifleAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISMediumRifleAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMediumRifleAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISMediumRifleAmmoResult.getStaticTechLevel());
        assertEquals("338,TO", actualCreateISMediumRifleAmmoResult.getRulesRefs());
        assertEquals(3079, actualCreateISMediumRifleAmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateISMediumRifleAmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_PS, actualCreateISMediumRifleAmmoResult.getPrototypeDate());
        assertEquals("Medium Rifle Ammo", actualCreateISMediumRifleAmmoResult.getName());
        assertEquals("IS Ammo Medium Rifle", actualCreateISMediumRifleAmmoResult.getInternalName());
        assertEquals("B/C-F(F*)-X-D", actualCreateISMediumRifleAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMediumRifleAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMediumRifleAmmoResult.getFlags());
    }

    private void createISHeavyRifleAmmo(AmmoType actualCreateISHeavyRifleAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISHeavyRifleAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISHeavyRifleAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISHeavyRifleAmmoResult.svslots);
        assertEquals("Heavy Rifle", actualCreateISHeavyRifleAmmoResult.shortName);
        assertNull(actualCreateISHeavyRifleAmmoResult.modes);
        assertTrue(actualCreateISHeavyRifleAmmoResult.explosive);
        assertEquals(1, actualCreateISHeavyRifleAmmoResult.criticals);
        assertEquals(11.0, actualCreateISHeavyRifleAmmoResult.bv, 0.0);
        assertFalse(actualCreateISHeavyRifleAmmoResult.isSpreadable());
        assertFalse(actualCreateISHeavyRifleAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISHeavyRifleAmmoResult.isMixedTech());
        assertTrue(actualCreateISHeavyRifleAmmoResult.isHittable());
        assertFalse(actualCreateISHeavyRifleAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISHeavyRifleAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISHeavyRifleAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISHeavyRifleAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISHeavyRifleAmmoResult.getStaticTechLevel());
        assertEquals("338,TO", actualCreateISHeavyRifleAmmoResult.getRulesRefs());
        assertEquals(3079, actualCreateISHeavyRifleAmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateISHeavyRifleAmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_PS, actualCreateISHeavyRifleAmmoResult.getPrototypeDate());
        assertEquals("Heavy Rifle Ammo", actualCreateISHeavyRifleAmmoResult.getName());
        assertEquals("IS Ammo Heavy Rifle", actualCreateISHeavyRifleAmmoResult.getInternalName());
        assertEquals("B/C-F(F*)-X-D", actualCreateISHeavyRifleAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISHeavyRifleAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISHeavyRifleAmmoResult.getFlags());
    }

    private void createCLSmallChemicalLaserAmmo(AmmoType actualCreateCLSmallChemicalLaserAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLSmallChemicalLaserAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLSmallChemicalLaserAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLSmallChemicalLaserAmmoResult.svslots);
        assertEquals("Small Chemical Laser", actualCreateCLSmallChemicalLaserAmmoResult.shortName);
        assertNull(actualCreateCLSmallChemicalLaserAmmoResult.modes);
        assertTrue(actualCreateCLSmallChemicalLaserAmmoResult.explosive);
        assertEquals(1, actualCreateCLSmallChemicalLaserAmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLSmallChemicalLaserAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLSmallChemicalLaserAmmoResult.isSpreadable());
        assertFalse(actualCreateCLSmallChemicalLaserAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLSmallChemicalLaserAmmoResult.isMixedTech());
        assertTrue(actualCreateCLSmallChemicalLaserAmmoResult.isHittable());
        assertFalse(actualCreateCLSmallChemicalLaserAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLSmallChemicalLaserAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLSmallChemicalLaserAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLSmallChemicalLaserAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLSmallChemicalLaserAmmoResult.getStaticTechLevel());
        assertEquals("320,TO", actualCreateCLSmallChemicalLaserAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLSmallChemicalLaserAmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLSmallChemicalLaserAmmoResult.getRawCost(), 0.0);
        assertEquals(3059, actualCreateCLSmallChemicalLaserAmmoResult.getPrototypeDate());
        assertEquals("Small Chemical Laser Ammo", actualCreateCLSmallChemicalLaserAmmoResult.getName());
        assertEquals("CLSmallChemLaserAmmo", actualCreateCLSmallChemicalLaserAmmoResult.getInternalName());
        assertEquals("E/X-X-E-E", actualCreateCLSmallChemicalLaserAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLSmallChemicalLaserAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLSmallChemicalLaserAmmoResult.getFlags());
    }

    private void createCLMediumChemicalLaserAmmo(AmmoType actualCreateCLMediumChemicalLaserAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLMediumChemicalLaserAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLMediumChemicalLaserAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLMediumChemicalLaserAmmoResult.svslots);
        assertEquals("Medium Chemical Laser", actualCreateCLMediumChemicalLaserAmmoResult.shortName);
        assertNull(actualCreateCLMediumChemicalLaserAmmoResult.modes);
        assertTrue(actualCreateCLMediumChemicalLaserAmmoResult.explosive);
        assertEquals(1, actualCreateCLMediumChemicalLaserAmmoResult.criticals);
        assertEquals(5.0, actualCreateCLMediumChemicalLaserAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLMediumChemicalLaserAmmoResult.isSpreadable());
        assertFalse(actualCreateCLMediumChemicalLaserAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLMediumChemicalLaserAmmoResult.isMixedTech());
        assertTrue(actualCreateCLMediumChemicalLaserAmmoResult.isHittable());
        assertFalse(actualCreateCLMediumChemicalLaserAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLMediumChemicalLaserAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLMediumChemicalLaserAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLMediumChemicalLaserAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLMediumChemicalLaserAmmoResult.getStaticTechLevel());
        assertEquals("320,TO", actualCreateCLMediumChemicalLaserAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLMediumChemicalLaserAmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLMediumChemicalLaserAmmoResult.getRawCost(), 0.0);
        assertEquals(3059, actualCreateCLMediumChemicalLaserAmmoResult.getPrototypeDate());
        assertEquals("Medium Chemical Laser Ammo", actualCreateCLMediumChemicalLaserAmmoResult.getName());
        assertEquals("CLMediumChemLaserAmmo", actualCreateCLMediumChemicalLaserAmmoResult.getInternalName());
        assertEquals("E/X-X-E-E", actualCreateCLMediumChemicalLaserAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLMediumChemicalLaserAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLMediumChemicalLaserAmmoResult.getFlags());
    }

    private void createCLLargeChemicalLaserAmmo(AmmoType actualCreateCLLargeChemicalLaserAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLargeChemicalLaserAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLargeChemicalLaserAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLargeChemicalLaserAmmoResult.svslots);
        assertEquals("Large Chemical Laser", actualCreateCLLargeChemicalLaserAmmoResult.shortName);
        assertNull(actualCreateCLLargeChemicalLaserAmmoResult.modes);
        assertTrue(actualCreateCLLargeChemicalLaserAmmoResult.explosive);
        assertEquals(1, actualCreateCLLargeChemicalLaserAmmoResult.criticals);
        assertEquals(12.0, actualCreateCLLargeChemicalLaserAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLargeChemicalLaserAmmoResult.isSpreadable());
        assertFalse(actualCreateCLLargeChemicalLaserAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLargeChemicalLaserAmmoResult.isMixedTech());
        assertTrue(actualCreateCLLargeChemicalLaserAmmoResult.isHittable());
        assertFalse(actualCreateCLLargeChemicalLaserAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLargeChemicalLaserAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLLargeChemicalLaserAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLargeChemicalLaserAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLLargeChemicalLaserAmmoResult.getStaticTechLevel());
        assertEquals("320,TO", actualCreateCLLargeChemicalLaserAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLargeChemicalLaserAmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLLargeChemicalLaserAmmoResult.getRawCost(), 0.0);
        assertEquals(3059, actualCreateCLLargeChemicalLaserAmmoResult.getPrototypeDate());
        assertEquals("Large Chemical Laser Ammo", actualCreateCLLargeChemicalLaserAmmoResult.getName());
        assertEquals("CLLargeChemLaserAmmo", actualCreateCLLargeChemicalLaserAmmoResult.getInternalName());
        assertEquals("E/X-X-E-E", actualCreateCLLargeChemicalLaserAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLargeChemicalLaserAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLargeChemicalLaserAmmoResult.getFlags());
    }

    private void createISHeavyFlamerAmmo(AmmoType actualCreateISHeavyFlamerAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISHeavyFlamerAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISHeavyFlamerAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISHeavyFlamerAmmoResult.svslots);
        assertEquals("Heavy Flamer", actualCreateISHeavyFlamerAmmoResult.shortName);
        assertNull(actualCreateISHeavyFlamerAmmoResult.modes);
        assertTrue(actualCreateISHeavyFlamerAmmoResult.explosive);
        assertEquals(1, actualCreateISHeavyFlamerAmmoResult.criticals);
        assertEquals(2.0, actualCreateISHeavyFlamerAmmoResult.bv, 0.0);
        assertFalse(actualCreateISHeavyFlamerAmmoResult.isSpreadable());
        assertFalse(actualCreateISHeavyFlamerAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISHeavyFlamerAmmoResult.isMixedTech());
        assertTrue(actualCreateISHeavyFlamerAmmoResult.isHittable());
        assertFalse(actualCreateISHeavyFlamerAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISHeavyFlamerAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISHeavyFlamerAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISHeavyFlamerAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISHeavyFlamerAmmoResult.getStaticTechLevel());
        assertEquals("312,TO", actualCreateISHeavyFlamerAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISHeavyFlamerAmmoResult.getReintroductionDate());
        assertEquals(2000.0, actualCreateISHeavyFlamerAmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateISHeavyFlamerAmmoResult.getPrototypeDate());
        assertEquals("Heavy Flamer Ammo", actualCreateISHeavyFlamerAmmoResult.getName());
        assertEquals("IS Heavy Flamer Ammo", actualCreateISHeavyFlamerAmmoResult.getInternalName());
        assertEquals("C/X-X-E-D", actualCreateISHeavyFlamerAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISHeavyFlamerAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISHeavyFlamerAmmoResult.getFlags());
    }

    private void createCLHeavyFlamerAmmo(AmmoType actualCreateCLHeavyFlamerAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLHeavyFlamerAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLHeavyFlamerAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLHeavyFlamerAmmoResult.svslots);
        assertEquals("Heavy Flamer", actualCreateCLHeavyFlamerAmmoResult.shortName);
        assertNull(actualCreateCLHeavyFlamerAmmoResult.modes);
        assertTrue(actualCreateCLHeavyFlamerAmmoResult.explosive);
        assertEquals(1, actualCreateCLHeavyFlamerAmmoResult.criticals);
        assertEquals(2.0, actualCreateCLHeavyFlamerAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLHeavyFlamerAmmoResult.isSpreadable());
        assertFalse(actualCreateCLHeavyFlamerAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLHeavyFlamerAmmoResult.isMixedTech());
        assertTrue(actualCreateCLHeavyFlamerAmmoResult.isHittable());
        assertFalse(actualCreateCLHeavyFlamerAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLHeavyFlamerAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLHeavyFlamerAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLHeavyFlamerAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLHeavyFlamerAmmoResult.getStaticTechLevel());
        assertEquals("312,TO", actualCreateCLHeavyFlamerAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLHeavyFlamerAmmoResult.getReintroductionDate());
        assertEquals(2000.0, actualCreateCLHeavyFlamerAmmoResult.getRawCost(), 0.0);
        assertEquals(3060, actualCreateCLHeavyFlamerAmmoResult.getPrototypeDate());
        assertEquals("Heavy Flamer Ammo", actualCreateCLHeavyFlamerAmmoResult.getName());
        assertEquals("CL Heavy Flamer Ammo", actualCreateCLHeavyFlamerAmmoResult.getInternalName());
        assertEquals("C/X-X-E-D", actualCreateCLHeavyFlamerAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLHeavyFlamerAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLHeavyFlamerAmmoResult.getFlags());
    }

    private void createISVehicleFlamerAmmo(AmmoType actualCreateISVehicleFlamerAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISVehicleFlamerAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISVehicleFlamerAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISVehicleFlamerAmmoResult.svslots);
        assertEquals("Flamer", actualCreateISVehicleFlamerAmmoResult.shortName);
        assertNull(actualCreateISVehicleFlamerAmmoResult.modes);
        assertTrue(actualCreateISVehicleFlamerAmmoResult.explosive);
        assertEquals(1, actualCreateISVehicleFlamerAmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISVehicleFlamerAmmoResult.bv, 0.0);
        assertFalse(actualCreateISVehicleFlamerAmmoResult.isSpreadable());
        assertFalse(actualCreateISVehicleFlamerAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISVehicleFlamerAmmoResult.isMixedTech());
        assertTrue(actualCreateISVehicleFlamerAmmoResult.isHittable());
        assertFalse(actualCreateISVehicleFlamerAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISVehicleFlamerAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISVehicleFlamerAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISVehicleFlamerAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.INTRO, actualCreateISVehicleFlamerAmmoResult.getStaticTechLevel());
        assertEquals("218,TM", actualCreateISVehicleFlamerAmmoResult.getRulesRefs());
        assertEquals(1000.0, actualCreateISVehicleFlamerAmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_PS, actualCreateISVehicleFlamerAmmoResult.getPrototypeDate());
        assertEquals("Vehicle Flamer Ammo", actualCreateISVehicleFlamerAmmoResult.getName());
        assertEquals("IS Vehicle Flamer Ammo", actualCreateISVehicleFlamerAmmoResult.getInternalName());
        assertEquals("B/A-A-B-A", actualCreateISVehicleFlamerAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISVehicleFlamerAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISVehicleFlamerAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISVehicleFlamerAmmoResult.getExtinctionDate());
    }

    private void createISFluidGunAmmo(AmmoType actualCreateISFluidGunAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISFluidGunAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISFluidGunAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISFluidGunAmmoResult.svslots);
        assertEquals("Fluid Gun", actualCreateISFluidGunAmmoResult.shortName);
        assertNull(actualCreateISFluidGunAmmoResult.modes);
        assertFalse(actualCreateISFluidGunAmmoResult.explosive);
        assertEquals(1, actualCreateISFluidGunAmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISFluidGunAmmoResult.bv, 0.0);
        assertFalse(actualCreateISFluidGunAmmoResult.isSpreadable());
        assertFalse(actualCreateISFluidGunAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISFluidGunAmmoResult.isMixedTech());
        assertTrue(actualCreateISFluidGunAmmoResult.isHittable());
        assertFalse(actualCreateISFluidGunAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISFluidGunAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISFluidGunAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISFluidGunAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISFluidGunAmmoResult.getStaticTechLevel());
        assertEquals("313,TO", actualCreateISFluidGunAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISFluidGunAmmoResult.getReintroductionDate());
        assertEquals(500.0, actualCreateISFluidGunAmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_PS, actualCreateISFluidGunAmmoResult.getPrototypeDate());
        assertEquals("Fluid Gun Ammo", actualCreateISFluidGunAmmoResult.getName());
        assertEquals("ISFluidGun Ammo", actualCreateISFluidGunAmmoResult.getInternalName());
        assertEquals("B/B-B-B-B", actualCreateISFluidGunAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISFluidGunAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISFluidGunAmmoResult.getFlags());
    }

    private void createCLFluidGunAmmo(AmmoType actualCreateCLFluidGunAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLFluidGunAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLFluidGunAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLFluidGunAmmoResult.svslots);
        assertEquals("Fluid Gun", actualCreateCLFluidGunAmmoResult.shortName);
        assertNull(actualCreateCLFluidGunAmmoResult.modes);
        assertTrue(actualCreateCLFluidGunAmmoResult.explosive);
        assertEquals(1, actualCreateCLFluidGunAmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLFluidGunAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLFluidGunAmmoResult.isSpreadable());
        assertFalse(actualCreateCLFluidGunAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLFluidGunAmmoResult.isMixedTech());
        assertTrue(actualCreateCLFluidGunAmmoResult.isHittable());
        assertFalse(actualCreateCLFluidGunAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLFluidGunAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateCLFluidGunAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLFluidGunAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLFluidGunAmmoResult.getStaticTechLevel());
        assertEquals("313,TO", actualCreateCLFluidGunAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLFluidGunAmmoResult.getReintroductionDate());
        assertEquals(500.0, actualCreateCLFluidGunAmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_PS, actualCreateCLFluidGunAmmoResult.getPrototypeDate());
        assertEquals("Fluid Gun Ammo", actualCreateCLFluidGunAmmoResult.getName());
        assertEquals("CLFluidGun Ammo", actualCreateCLFluidGunAmmoResult.getInternalName());
        assertEquals("B/B-B-B-B", actualCreateCLFluidGunAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLFluidGunAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLFluidGunAmmoResult.getFlags());
    }

    private void createISGaussAmmo(AmmoType actualCreateISGaussAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISGaussAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISGaussAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISGaussAmmoResult.svslots);
        assertEquals("Gauss", actualCreateISGaussAmmoResult.shortName);
        assertNull(actualCreateISGaussAmmoResult.modes);
        assertFalse(actualCreateISGaussAmmoResult.explosive);
        assertEquals(1, actualCreateISGaussAmmoResult.criticals);
        assertEquals(40.0, actualCreateISGaussAmmoResult.bv, 0.0);
        assertFalse(actualCreateISGaussAmmoResult.isSpreadable());
        assertFalse(actualCreateISGaussAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISGaussAmmoResult.isMixedTech());
        assertTrue(actualCreateISGaussAmmoResult.isHittable());
        assertFalse(actualCreateISGaussAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISGaussAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISGaussAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISGaussAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISGaussAmmoResult.getStaticTechLevel());
        assertEquals("219,TM", actualCreateISGaussAmmoResult.getRulesRefs());
        assertEquals(3033, actualCreateISGaussAmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateISGaussAmmoResult.getRawCost(), 0.0);
        assertEquals(2587, actualCreateISGaussAmmoResult.getPrototypeDate());
        assertEquals("Gauss Rifle Ammo [IS]", actualCreateISGaussAmmoResult.getName());
        assertEquals("IS Gauss Ammo", actualCreateISGaussAmmoResult.getInternalName());
        assertEquals("E/D-F-D-C", actualCreateISGaussAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISGaussAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISGaussAmmoResult.getFlags());
    }

    private void createCLGaussAmmo(AmmoType actualCreateCLGaussAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLGaussAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLGaussAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLGaussAmmoResult.svslots);
        assertEquals("Gauss", actualCreateCLGaussAmmoResult.shortName);
        assertNull(actualCreateCLGaussAmmoResult.modes);
        assertFalse(actualCreateCLGaussAmmoResult.explosive);
        assertEquals(1, actualCreateCLGaussAmmoResult.criticals);
        assertEquals(40.0, actualCreateCLGaussAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLGaussAmmoResult.isSpreadable());
        assertFalse(actualCreateCLGaussAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLGaussAmmoResult.isMixedTech());
        assertTrue(actualCreateCLGaussAmmoResult.isHittable());
        assertFalse(actualCreateCLGaussAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLGaussAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLGaussAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLGaussAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLGaussAmmoResult.getStaticTechLevel());
        assertEquals("219,TM", actualCreateCLGaussAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLGaussAmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateCLGaussAmmoResult.getRawCost(), 0.0);
        assertEquals(2817, actualCreateCLGaussAmmoResult.getPrototypeDate());
        assertEquals("Gauss Rifle Ammo [Clan]", actualCreateCLGaussAmmoResult.getName());
        assertEquals("Clan Gauss Ammo", actualCreateCLGaussAmmoResult.getInternalName());
        assertEquals("F/X-F-D-D", actualCreateCLGaussAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLGaussAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLGaussAmmoResult.getFlags());
    }

    private void createISLTGaussAmmo(AmmoType actualCreateISLTGaussAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLTGaussAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLTGaussAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLTGaussAmmoResult.svslots);
        assertEquals("Light Gauss", actualCreateISLTGaussAmmoResult.shortName);
        assertNull(actualCreateISLTGaussAmmoResult.modes);
        assertFalse(actualCreateISLTGaussAmmoResult.explosive);
        assertEquals(1, actualCreateISLTGaussAmmoResult.criticals);
        assertEquals(20.0, actualCreateISLTGaussAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLTGaussAmmoResult.isSpreadable());
        assertFalse(actualCreateISLTGaussAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLTGaussAmmoResult.isMixedTech());
        assertTrue(actualCreateISLTGaussAmmoResult.isHittable());
        assertFalse(actualCreateISLTGaussAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLTGaussAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISLTGaussAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLTGaussAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLTGaussAmmoResult.getStaticTechLevel());
        assertEquals("219,TM", actualCreateISLTGaussAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLTGaussAmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateISLTGaussAmmoResult.getRawCost(), 0.0);
        assertEquals(3044, actualCreateISLTGaussAmmoResult.getPrototypeDate());
        assertEquals("Light Gauss Rifle Ammo", actualCreateISLTGaussAmmoResult.getName());
        assertEquals("IS Light Gauss Ammo", actualCreateISLTGaussAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISLTGaussAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLTGaussAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLTGaussAmmoResult.getFlags());
    }

    private void createISHVGaussAmmo(AmmoType actualCreateISHVGaussAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISHVGaussAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISHVGaussAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISHVGaussAmmoResult.svslots);
        assertEquals("Heavy Gauss", actualCreateISHVGaussAmmoResult.shortName);
        assertNull(actualCreateISHVGaussAmmoResult.modes);
        assertFalse(actualCreateISHVGaussAmmoResult.explosive);
        assertEquals(1, actualCreateISHVGaussAmmoResult.criticals);
        assertEquals(43.0, actualCreateISHVGaussAmmoResult.bv, 0.0);
        assertFalse(actualCreateISHVGaussAmmoResult.isSpreadable());
        assertFalse(actualCreateISHVGaussAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISHVGaussAmmoResult.isMixedTech());
        assertTrue(actualCreateISHVGaussAmmoResult.isHittable());
        assertFalse(actualCreateISHVGaussAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISHVGaussAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISHVGaussAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISHVGaussAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISHVGaussAmmoResult.getStaticTechLevel());
        assertEquals("218,TM", actualCreateISHVGaussAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISHVGaussAmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateISHVGaussAmmoResult.getRawCost(), 0.0);
        assertEquals(3046, actualCreateISHVGaussAmmoResult.getPrototypeDate());
        assertEquals("Heavy Gauss Rifle Ammo", actualCreateISHVGaussAmmoResult.getName());
        assertEquals("ISHeavyGauss Ammo", actualCreateISHVGaussAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISHVGaussAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISHVGaussAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISHVGaussAmmoResult.getFlags());
    }

    private void createCLAPGaussRifleAmmo(AmmoType actualCreateCLAPGaussRifleAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLAPGaussRifleAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLAPGaussRifleAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLAPGaussRifleAmmoResult.svslots);
        assertEquals("AP Gauss", actualCreateCLAPGaussRifleAmmoResult.shortName);
        assertNull(actualCreateCLAPGaussRifleAmmoResult.modes);
        assertFalse(actualCreateCLAPGaussRifleAmmoResult.explosive);
        assertEquals(1, actualCreateCLAPGaussRifleAmmoResult.criticals);
        assertEquals(3.0, actualCreateCLAPGaussRifleAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLAPGaussRifleAmmoResult.isSpreadable());
        assertFalse(actualCreateCLAPGaussRifleAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLAPGaussRifleAmmoResult.isMixedTech());
        assertTrue(actualCreateCLAPGaussRifleAmmoResult.isHittable());
        assertFalse(actualCreateCLAPGaussRifleAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLAPGaussRifleAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLAPGaussRifleAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLAPGaussRifleAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLAPGaussRifleAmmoResult.getStaticTechLevel());
        assertEquals("218,TM", actualCreateCLAPGaussRifleAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLAPGaussRifleAmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateCLAPGaussRifleAmmoResult.getRawCost(), 0.0);
        assertEquals(3060, actualCreateCLAPGaussRifleAmmoResult.getPrototypeDate());
        assertEquals("Anti-Personnel Gauss Rifle Ammo", actualCreateCLAPGaussRifleAmmoResult.getName());
        assertEquals("CLAPGaussRifle Ammo", actualCreateCLAPGaussRifleAmmoResult.getInternalName());
        assertEquals("F/X-X-E-D", actualCreateCLAPGaussRifleAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLAPGaussRifleAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLAPGaussRifleAmmoResult.getFlags());
    }

    private void createCLHAG20Ammo(AmmoType actualCreateCLHAG20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLHAG20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLHAG20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLHAG20AmmoResult.svslots);
        assertEquals("HAG 20", actualCreateCLHAG20AmmoResult.shortName);
        assertNull(actualCreateCLHAG20AmmoResult.modes);
        assertFalse(actualCreateCLHAG20AmmoResult.explosive);
        assertEquals(1, actualCreateCLHAG20AmmoResult.criticals);
        assertEquals(33.0, actualCreateCLHAG20AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLHAG20AmmoResult.isSpreadable());
        assertFalse(actualCreateCLHAG20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLHAG20AmmoResult.isMixedTech());
        assertTrue(actualCreateCLHAG20AmmoResult.isHittable());
        assertFalse(actualCreateCLHAG20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLHAG20AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLHAG20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLHAG20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLHAG20AmmoResult.getStaticTechLevel());
        assertEquals("219,TM", actualCreateCLHAG20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLHAG20AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLHAG20AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLHAG20AmmoResult.getPrototypeDate());
        assertEquals("Hyper-Assault Gauss Rifle/20 Ammo", actualCreateCLHAG20AmmoResult.getName());
        assertEquals("Hyper-Assault Gauss Rifle/20 Ammo", actualCreateCLHAG20AmmoResult.getInternalName());
        assertEquals("F/X-F-E-D", actualCreateCLHAG20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLHAG20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLHAG20AmmoResult.getFlags());
    }

    private void createCLHAG30Ammo(AmmoType actualCreateCLHAG30AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLHAG30AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLHAG30AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLHAG30AmmoResult.svslots);
        assertEquals("HAG 30", actualCreateCLHAG30AmmoResult.shortName);
        assertNull(actualCreateCLHAG30AmmoResult.modes);
        assertFalse(actualCreateCLHAG30AmmoResult.explosive);
        assertEquals(1, actualCreateCLHAG30AmmoResult.criticals);
        assertEquals(50.0, actualCreateCLHAG30AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLHAG30AmmoResult.isSpreadable());
        assertFalse(actualCreateCLHAG30AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLHAG30AmmoResult.isMixedTech());
        assertTrue(actualCreateCLHAG30AmmoResult.isHittable());
        assertFalse(actualCreateCLHAG30AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLHAG30AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLHAG30AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLHAG30AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLHAG30AmmoResult.getStaticTechLevel());
        assertEquals("219,TM", actualCreateCLHAG30AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLHAG30AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLHAG30AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLHAG30AmmoResult.getPrototypeDate());
        assertEquals("Hyper-Assault Gauss Rifle/30 Ammo", actualCreateCLHAG30AmmoResult.getName());
        assertEquals("Hyper-Assault Gauss Rifle/30 Ammo", actualCreateCLHAG30AmmoResult.getInternalName());
        assertEquals("F/X-F-E-D", actualCreateCLHAG30AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLHAG30AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLHAG30AmmoResult.getFlags());
    }

    private void createCLHAG40Ammo(AmmoType actualCreateCLHAG40AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLHAG40AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLHAG40AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLHAG40AmmoResult.svslots);
        assertEquals("HAG 40", actualCreateCLHAG40AmmoResult.shortName);
        assertNull(actualCreateCLHAG40AmmoResult.modes);
        assertFalse(actualCreateCLHAG40AmmoResult.explosive);
        assertEquals(1, actualCreateCLHAG40AmmoResult.criticals);
        assertEquals(67.0, actualCreateCLHAG40AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLHAG40AmmoResult.isSpreadable());
        assertFalse(actualCreateCLHAG40AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLHAG40AmmoResult.isMixedTech());
        assertTrue(actualCreateCLHAG40AmmoResult.isHittable());
        assertFalse(actualCreateCLHAG40AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLHAG40AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLHAG40AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLHAG40AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLHAG40AmmoResult.getStaticTechLevel());
        assertEquals("219,TM", actualCreateCLHAG40AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLHAG40AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLHAG40AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLHAG40AmmoResult.getPrototypeDate());
        assertEquals("Hyper-Assault Gauss Rifle/40 Ammo", actualCreateCLHAG40AmmoResult.getName());
        assertEquals("Hyper-Assault Gauss Rifle/40 Ammo", actualCreateCLHAG40AmmoResult.getInternalName());
        assertEquals("F/X-F-E-D", actualCreateCLHAG40AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLHAG40AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLHAG40AmmoResult.getFlags());
    }

    private void createISIHVGaussAmmo(AmmoType actualCreateISIHVGaussAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISIHVGaussAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISIHVGaussAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISIHVGaussAmmoResult.svslots);
        assertEquals("iHeavy Gauss", actualCreateISIHVGaussAmmoResult.shortName);
        assertNull(actualCreateISIHVGaussAmmoResult.modes);
        assertFalse(actualCreateISIHVGaussAmmoResult.explosive);
        assertEquals(1, actualCreateISIHVGaussAmmoResult.criticals);
        assertEquals(48.0, actualCreateISIHVGaussAmmoResult.bv, 0.0);
        assertFalse(actualCreateISIHVGaussAmmoResult.isSpreadable());
        assertFalse(actualCreateISIHVGaussAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISIHVGaussAmmoResult.isMixedTech());
        assertTrue(actualCreateISIHVGaussAmmoResult.isHittable());
        assertFalse(actualCreateISIHVGaussAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISIHVGaussAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISIHVGaussAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISIHVGaussAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISIHVGaussAmmoResult.getStaticTechLevel());
        assertEquals("313,TO", actualCreateISIHVGaussAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISIHVGaussAmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateISIHVGaussAmmoResult.getRawCost(), 0.0);
        assertEquals(3065, actualCreateISIHVGaussAmmoResult.getPrototypeDate());
        assertEquals("Improved Heavy Gauss Rifle Ammo", actualCreateISIHVGaussAmmoResult.getName());
        assertEquals("ISImprovedHeavyGauss Ammo", actualCreateISIHVGaussAmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateISIHVGaussAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISIHVGaussAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISIHVGaussAmmoResult.getFlags());
    }

    private void createISMagshotGRAmmo(AmmoType actualCreateISMagshotGRAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMagshotGRAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMagshotGRAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMagshotGRAmmoResult.svslots);
        assertEquals("Magshot", actualCreateISMagshotGRAmmoResult.shortName);
        assertNull(actualCreateISMagshotGRAmmoResult.modes);
        assertFalse(actualCreateISMagshotGRAmmoResult.explosive);
        assertEquals(1, actualCreateISMagshotGRAmmoResult.criticals);
        assertEquals(2.0, actualCreateISMagshotGRAmmoResult.bv, 0.0);
        assertFalse(actualCreateISMagshotGRAmmoResult.isSpreadable());
        assertFalse(actualCreateISMagshotGRAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMagshotGRAmmoResult.isMixedTech());
        assertTrue(actualCreateISMagshotGRAmmoResult.isHittable());
        assertFalse(actualCreateISMagshotGRAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMagshotGRAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISMagshotGRAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMagshotGRAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMagshotGRAmmoResult.getStaticTechLevel());
        assertEquals("314,TO", actualCreateISMagshotGRAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMagshotGRAmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateISMagshotGRAmmoResult.getRawCost(), 0.0);
        assertEquals(3054, actualCreateISMagshotGRAmmoResult.getPrototypeDate());
        assertEquals("Magshot Gauss Rifle Ammo", actualCreateISMagshotGRAmmoResult.getName());
        assertEquals("ISMagshotGR Ammo", actualCreateISMagshotGRAmmoResult.getInternalName());
        assertEquals("E/X-X-D-C", actualCreateISMagshotGRAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMagshotGRAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMagshotGRAmmoResult.getFlags());
    }

    private void createISSBGaussRifleAmmo(AmmoType actualCreateISSBGaussRifleAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISSBGaussRifleAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISSBGaussRifleAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISSBGaussRifleAmmoResult.svslots);
        assertEquals("Silver Bullet", actualCreateISSBGaussRifleAmmoResult.shortName);
        assertNull(actualCreateISSBGaussRifleAmmoResult.modes);
        assertFalse(actualCreateISSBGaussRifleAmmoResult.explosive);
        assertEquals(1, actualCreateISSBGaussRifleAmmoResult.criticals);
        assertEquals(25.0, actualCreateISSBGaussRifleAmmoResult.bv, 0.0);
        assertFalse(actualCreateISSBGaussRifleAmmoResult.isSpreadable());
        assertFalse(actualCreateISSBGaussRifleAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISSBGaussRifleAmmoResult.isMixedTech());
        assertTrue(actualCreateISSBGaussRifleAmmoResult.isHittable());
        assertFalse(actualCreateISSBGaussRifleAmmoResult.hasInstantModeSwitch());
        assertEquals(AmmoType.T_NA, actualCreateISSBGaussRifleAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISSBGaussRifleAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISSBGaussRifleAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISSBGaussRifleAmmoResult.getStaticTechLevel());
        assertEquals("314,TO", actualCreateISSBGaussRifleAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISSBGaussRifleAmmoResult.getReintroductionDate());
        assertEquals(25000.0, actualCreateISSBGaussRifleAmmoResult.getRawCost(), 0.0);
        assertEquals(3051, actualCreateISSBGaussRifleAmmoResult.getPrototypeDate());
        assertEquals("Silver Bullet Gauss Rifle Ammo", actualCreateISSBGaussRifleAmmoResult.getName());
        assertEquals("Silver Bullet Gauss Ammo", actualCreateISSBGaussRifleAmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateISSBGaussRifleAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISSBGaussRifleAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISSBGaussRifleAmmoResult.getFlags());
    }

    private void createISVGLAmmo(AmmoType actualCreateISVGLAmmoResult) {
        assertEquals(0.0, actualCreateISVGLAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISVGLAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISVGLAmmoResult.svslots);
        assertEquals("VGL Fragmentation", actualCreateISVGLAmmoResult.shortName);
        assertNull(actualCreateISVGLAmmoResult.modes);
        assertTrue(actualCreateISVGLAmmoResult.explosive);
        assertEquals(1, actualCreateISVGLAmmoResult.criticals);
        assertEquals(0.0, actualCreateISVGLAmmoResult.bv, 0.0);
        assertFalse(actualCreateISVGLAmmoResult.isSpreadable());
        assertFalse(actualCreateISVGLAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISVGLAmmoResult.isMixedTech());
        assertTrue(actualCreateISVGLAmmoResult.isHittable());
        assertFalse(actualCreateISVGLAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISVGLAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISVGLAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISVGLAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISVGLAmmoResult.getStaticTechLevel());
        assertEquals("315,TO", actualCreateISVGLAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISVGLAmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateISVGLAmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_PS, actualCreateISVGLAmmoResult.getPrototypeDate());
        assertEquals("Fragmentation Grenades [VGL]", actualCreateISVGLAmmoResult.getName());
        assertEquals("IS Ammo VGL", actualCreateISVGLAmmoResult.getInternalName());
        assertEquals("B/B-B-B-B", actualCreateISVGLAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISVGLAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISVGLAmmoResult.getFlags());
    }

    private void createCLVGLAmmo(AmmoType actualCreateCLVGLAmmoResult) {
        assertEquals(0.0, actualCreateCLVGLAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLVGLAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLVGLAmmoResult.svslots);
        assertEquals("VGL Fragmentation", actualCreateCLVGLAmmoResult.shortName);
        assertNull(actualCreateCLVGLAmmoResult.modes);
        assertTrue(actualCreateCLVGLAmmoResult.explosive);
        assertEquals(1, actualCreateCLVGLAmmoResult.criticals);
        assertEquals(0.0, actualCreateCLVGLAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLVGLAmmoResult.isSpreadable());
        assertFalse(actualCreateCLVGLAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLVGLAmmoResult.isMixedTech());
        assertTrue(actualCreateCLVGLAmmoResult.isHittable());
        assertFalse(actualCreateCLVGLAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLVGLAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateCLVGLAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLVGLAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLVGLAmmoResult.getStaticTechLevel());
        assertEquals("315,TO", actualCreateCLVGLAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLVGLAmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLVGLAmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_PS, actualCreateCLVGLAmmoResult.getPrototypeDate());
        assertEquals("Fragmentation Grenades [VGL]", actualCreateCLVGLAmmoResult.getName());
        assertEquals("CL Ammo VGL", actualCreateCLVGLAmmoResult.getInternalName());
        assertEquals("B/B-B-B-B", actualCreateCLVGLAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLVGLAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLVGLAmmoResult.getFlags());
    }

    private void createISMGAmmo(AmmoType actualCreateISMGAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMGAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMGAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMGAmmoResult.svslots);
        assertEquals("Machine Gun", actualCreateISMGAmmoResult.shortName);
        assertNull(actualCreateISMGAmmoResult.modes);
        assertTrue(actualCreateISMGAmmoResult.explosive);
        assertEquals(1, actualCreateISMGAmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMGAmmoResult.bv, 0.0);
        assertFalse(actualCreateISMGAmmoResult.isSpreadable());
        assertFalse(actualCreateISMGAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISMGAmmoResult.isMixedTech());
        assertTrue(actualCreateISMGAmmoResult.isHittable());
        assertFalse(actualCreateISMGAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMGAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISMGAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMGAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.INTRO, actualCreateISMGAmmoResult.getStaticTechLevel());
        assertEquals("228,TM", actualCreateISMGAmmoResult.getRulesRefs());
        assertEquals(1000.0, actualCreateISMGAmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_PS, actualCreateISMGAmmoResult.getPrototypeDate());
        assertEquals("Machine Gun Ammo", actualCreateISMGAmmoResult.getName());
        assertEquals("IS Ammo MG - Full", actualCreateISMGAmmoResult.getInternalName());
        assertEquals("B/A-A-B-A", actualCreateISMGAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMGAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMGAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISMGAmmoResult.getExtinctionDate());
    }

    private void createCLMGAmmo(AmmoType actualCreateCLMGAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLMGAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLMGAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLMGAmmoResult.svslots);
        assertEquals("Machine Gun", actualCreateCLMGAmmoResult.shortName);
        assertNull(actualCreateCLMGAmmoResult.modes);
        assertTrue(actualCreateCLMGAmmoResult.explosive);
        assertEquals(1, actualCreateCLMGAmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLMGAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLMGAmmoResult.isSpreadable());
        assertFalse(actualCreateCLMGAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLMGAmmoResult.isMixedTech());
        assertTrue(actualCreateCLMGAmmoResult.isHittable());
        assertFalse(actualCreateCLMGAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLMGAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLMGAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLMGAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLMGAmmoResult.getStaticTechLevel());
        assertEquals("228,TM", actualCreateCLMGAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLMGAmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateCLMGAmmoResult.getRawCost(), 0.0);
        assertEquals(2816, actualCreateCLMGAmmoResult.getPrototypeDate());
        assertEquals("Machine Gun Ammo", actualCreateCLMGAmmoResult.getName());
        assertEquals("Clan Machine Gun Ammo - Full", actualCreateCLMGAmmoResult.getInternalName());
        assertEquals("C/X-B-B-A", actualCreateCLMGAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLMGAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLMGAmmoResult.getFlags());
    }

    private void createISMGAmmoHalf(AmmoType actualCreateISMGAmmoHalfResult) {
        assertEquals(0.5, actualCreateISMGAmmoHalfResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMGAmmoHalfResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMGAmmoHalfResult.svslots);
        assertEquals("Machine Gun", actualCreateISMGAmmoHalfResult.shortName);
        assertNull(actualCreateISMGAmmoHalfResult.modes);
        assertTrue(actualCreateISMGAmmoHalfResult.explosive);
        assertEquals(1, actualCreateISMGAmmoHalfResult.criticals);
        assertEquals(0.5, actualCreateISMGAmmoHalfResult.bv, 0.0);
        assertFalse(actualCreateISMGAmmoHalfResult.isSpreadable());
        assertFalse(actualCreateISMGAmmoHalfResult.isOmniFixedOnly());
        assertTrue(actualCreateISMGAmmoHalfResult.isMixedTech());
        assertTrue(actualCreateISMGAmmoHalfResult.isHittable());
        assertFalse(actualCreateISMGAmmoHalfResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMGAmmoHalfResult.getToHitModifier());
        assertEquals(1, actualCreateISMGAmmoHalfResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMGAmmoHalfResult.getSubType());
        assertEquals(SimpleTechLevel.INTRO, actualCreateISMGAmmoHalfResult.getStaticTechLevel());
        assertEquals("228,TM", actualCreateISMGAmmoHalfResult.getRulesRefs());
        assertEquals(500.0, actualCreateISMGAmmoHalfResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_PS, actualCreateISMGAmmoHalfResult.getPrototypeDate());
        assertEquals("Half Machine Gun Ammo", actualCreateISMGAmmoHalfResult.getName());
        assertEquals("IS Machine Gun Ammo - Half", actualCreateISMGAmmoHalfResult.getInternalName());
        assertEquals("B/A-A-B-A", actualCreateISMGAmmoHalfResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMGAmmoHalfResult.flags;
        assertSame(expectedFlags, actualCreateISMGAmmoHalfResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISMGAmmoHalfResult.getExtinctionDate());
    }

    private void createCLMGAmmoHalf(AmmoType actualCreateCLMGAmmoHalfResult) {
        assertEquals(0.5, actualCreateCLMGAmmoHalfResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLMGAmmoHalfResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLMGAmmoHalfResult.svslots);
        assertEquals("Machine Gun", actualCreateCLMGAmmoHalfResult.shortName);
        assertNull(actualCreateCLMGAmmoHalfResult.modes);
        assertTrue(actualCreateCLMGAmmoHalfResult.explosive);
        assertEquals(1, actualCreateCLMGAmmoHalfResult.criticals);
        assertEquals(0.5, actualCreateCLMGAmmoHalfResult.bv, 0.0);
        assertFalse(actualCreateCLMGAmmoHalfResult.isSpreadable());
        assertFalse(actualCreateCLMGAmmoHalfResult.isOmniFixedOnly());
        assertFalse(actualCreateCLMGAmmoHalfResult.isMixedTech());
        assertTrue(actualCreateCLMGAmmoHalfResult.isHittable());
        assertFalse(actualCreateCLMGAmmoHalfResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLMGAmmoHalfResult.getToHitModifier());
        assertEquals(2, actualCreateCLMGAmmoHalfResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLMGAmmoHalfResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLMGAmmoHalfResult.getStaticTechLevel());
        assertEquals("228,TM", actualCreateCLMGAmmoHalfResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLMGAmmoHalfResult.getReintroductionDate());
        assertEquals(500.0, actualCreateCLMGAmmoHalfResult.getRawCost(), 0.0);
        assertEquals(2816, actualCreateCLMGAmmoHalfResult.getPrototypeDate());
        assertEquals("Half Machine Gun Ammo", actualCreateCLMGAmmoHalfResult.getName());
        assertEquals("Clan Machine Gun Ammo - Half", actualCreateCLMGAmmoHalfResult.getInternalName());
        assertEquals("C/X-B-B-A", actualCreateCLMGAmmoHalfResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLMGAmmoHalfResult.flags;
        assertSame(expectedFlags, actualCreateCLMGAmmoHalfResult.getFlags());
    }

    private void createISLightMGAmmo(AmmoType actualCreateISLightMGAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLightMGAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLightMGAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLightMGAmmoResult.svslots);
        assertEquals("Light Machine Gun", actualCreateISLightMGAmmoResult.shortName);
        assertNull(actualCreateISLightMGAmmoResult.modes);
        assertTrue(actualCreateISLightMGAmmoResult.explosive);
        assertEquals(1, actualCreateISLightMGAmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLightMGAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLightMGAmmoResult.isSpreadable());
        assertFalse(actualCreateISLightMGAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLightMGAmmoResult.isMixedTech());
        assertTrue(actualCreateISLightMGAmmoResult.isHittable());
        assertFalse(actualCreateISLightMGAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLightMGAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISLightMGAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLightMGAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLightMGAmmoResult.getStaticTechLevel());
        assertEquals("228,TM", actualCreateISLightMGAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLightMGAmmoResult.getReintroductionDate());
        assertEquals(500.0, actualCreateISLightMGAmmoResult.getRawCost(), 0.0);
        assertEquals(3059, actualCreateISLightMGAmmoResult.getPrototypeDate());
        assertEquals("Light Machine Gun Ammo [Full]", actualCreateISLightMGAmmoResult.getName());
        assertEquals("IS Light Machine Gun Ammo - Full", actualCreateISLightMGAmmoResult.getInternalName());
        assertEquals("B/X-X-C-B", actualCreateISLightMGAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLightMGAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLightMGAmmoResult.getFlags());
    }

    private void createCLLightMGAmmo(AmmoType actualCreateCLLightMGAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLightMGAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLightMGAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLightMGAmmoResult.svslots);
        assertEquals("Light Machine Gun", actualCreateCLLightMGAmmoResult.shortName);
        assertNull(actualCreateCLLightMGAmmoResult.modes);
        assertTrue(actualCreateCLLightMGAmmoResult.explosive);
        assertEquals(1, actualCreateCLLightMGAmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLightMGAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLightMGAmmoResult.isSpreadable());
        assertFalse(actualCreateCLLightMGAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLightMGAmmoResult.isMixedTech());
        assertTrue(actualCreateCLLightMGAmmoResult.isHittable());
        assertFalse(actualCreateCLLightMGAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLightMGAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLLightMGAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLightMGAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLightMGAmmoResult.getStaticTechLevel());
        assertEquals("228,TM", actualCreateCLLightMGAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLightMGAmmoResult.getReintroductionDate());
        assertEquals(500.0, actualCreateCLLightMGAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLightMGAmmoResult.getPrototypeDate());
        assertEquals("Light Machine Gun Ammo [Full]", actualCreateCLLightMGAmmoResult.getName());
        assertEquals("Clan Light Machine Gun Ammo - Full", actualCreateCLLightMGAmmoResult.getInternalName());
        assertEquals("C/X-C-C-B", actualCreateCLLightMGAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLightMGAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLightMGAmmoResult.getFlags());
    }

    private void createISLightMGAmmoHalf(AmmoType actualCreateISLightMGAmmoHalfResult) {
        assertEquals(0.5, actualCreateISLightMGAmmoHalfResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLightMGAmmoHalfResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLightMGAmmoHalfResult.svslots);
        assertEquals("Light Machine Gun", actualCreateISLightMGAmmoHalfResult.shortName);
        assertNull(actualCreateISLightMGAmmoHalfResult.modes);
        assertTrue(actualCreateISLightMGAmmoHalfResult.explosive);
        assertEquals(1, actualCreateISLightMGAmmoHalfResult.criticals);
        assertEquals(0.5, actualCreateISLightMGAmmoHalfResult.bv, 0.0);
        assertFalse(actualCreateISLightMGAmmoHalfResult.isSpreadable());
        assertFalse(actualCreateISLightMGAmmoHalfResult.isOmniFixedOnly());
        assertFalse(actualCreateISLightMGAmmoHalfResult.isMixedTech());
        assertTrue(actualCreateISLightMGAmmoHalfResult.isHittable());
        assertFalse(actualCreateISLightMGAmmoHalfResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLightMGAmmoHalfResult.getToHitModifier());
        assertEquals(1, actualCreateISLightMGAmmoHalfResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLightMGAmmoHalfResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLightMGAmmoHalfResult.getStaticTechLevel());
        assertEquals("228,TM", actualCreateISLightMGAmmoHalfResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLightMGAmmoHalfResult.getReintroductionDate());
        assertEquals(250.0, actualCreateISLightMGAmmoHalfResult.getRawCost(), 0.0);
        assertEquals(3059, actualCreateISLightMGAmmoHalfResult.getPrototypeDate());
        assertEquals("Light Machine Gun Ammo [Half]", actualCreateISLightMGAmmoHalfResult.getName());
        assertEquals("IS Light Machine Gun Ammo - Half", actualCreateISLightMGAmmoHalfResult.getInternalName());
        assertEquals("B/X-X-C-B", actualCreateISLightMGAmmoHalfResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLightMGAmmoHalfResult.flags;
        assertSame(expectedFlags, actualCreateISLightMGAmmoHalfResult.getFlags());
    }

    private void createCLLightMGAmmoHalf(AmmoType actualCreateCLLightMGAmmoHalfResult) {
        assertEquals(0.5, actualCreateCLLightMGAmmoHalfResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLightMGAmmoHalfResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLightMGAmmoHalfResult.svslots);
        assertEquals("Light Machine Gun", actualCreateCLLightMGAmmoHalfResult.shortName);
        assertNull(actualCreateCLLightMGAmmoHalfResult.modes);
        assertTrue(actualCreateCLLightMGAmmoHalfResult.explosive);
        assertEquals(1, actualCreateCLLightMGAmmoHalfResult.criticals);
        assertEquals(0.5, actualCreateCLLightMGAmmoHalfResult.bv, 0.0);
        assertFalse(actualCreateCLLightMGAmmoHalfResult.isSpreadable());
        assertFalse(actualCreateCLLightMGAmmoHalfResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLightMGAmmoHalfResult.isMixedTech());
        assertTrue(actualCreateCLLightMGAmmoHalfResult.isHittable());
        assertFalse(actualCreateCLLightMGAmmoHalfResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLightMGAmmoHalfResult.getToHitModifier());
        assertEquals(2, actualCreateCLLightMGAmmoHalfResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLightMGAmmoHalfResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLightMGAmmoHalfResult.getStaticTechLevel());
        assertEquals("228,TM", actualCreateCLLightMGAmmoHalfResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLightMGAmmoHalfResult.getReintroductionDate());
        assertEquals(250.0, actualCreateCLLightMGAmmoHalfResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLightMGAmmoHalfResult.getPrototypeDate());
        assertEquals("Light Machine Gun Ammo [Half]", actualCreateCLLightMGAmmoHalfResult.getName());
        assertEquals("Clan Light Machine Gun Ammo - Half", actualCreateCLLightMGAmmoHalfResult.getInternalName());
        assertEquals("C/X-C-C-B", actualCreateCLLightMGAmmoHalfResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLightMGAmmoHalfResult.flags;
        assertSame(expectedFlags, actualCreateCLLightMGAmmoHalfResult.getFlags());
    }

    private void createISHeavyMGAmmo(AmmoType actualCreateISHeavyMGAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISHeavyMGAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISHeavyMGAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISHeavyMGAmmoResult.svslots);
        assertEquals("Heavy Machine Gun", actualCreateISHeavyMGAmmoResult.shortName);
        assertNull(actualCreateISHeavyMGAmmoResult.modes);
        assertTrue(actualCreateISHeavyMGAmmoResult.explosive);
        assertEquals(1, actualCreateISHeavyMGAmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISHeavyMGAmmoResult.bv, 0.0);
        assertFalse(actualCreateISHeavyMGAmmoResult.isSpreadable());
        assertFalse(actualCreateISHeavyMGAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISHeavyMGAmmoResult.isMixedTech());
        assertTrue(actualCreateISHeavyMGAmmoResult.isHittable());
        assertFalse(actualCreateISHeavyMGAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISHeavyMGAmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISHeavyMGAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISHeavyMGAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISHeavyMGAmmoResult.getStaticTechLevel());
        assertEquals("228,TM", actualCreateISHeavyMGAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISHeavyMGAmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateISHeavyMGAmmoResult.getRawCost(), 0.0);
        assertEquals(3058, actualCreateISHeavyMGAmmoResult.getPrototypeDate());
        assertEquals("Heavy Machine Gun Ammo [Full]", actualCreateISHeavyMGAmmoResult.getName());
        assertEquals("IS Heavy Machine Gun Ammo - Full", actualCreateISHeavyMGAmmoResult.getInternalName());
        assertEquals("B/X-C-C-B", actualCreateISHeavyMGAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISHeavyMGAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISHeavyMGAmmoResult.getFlags());
    }

    private void createISHeavyMGAmmoHalf(AmmoType actualCreateISHeavyMGAmmoHalfResult) {
        assertEquals(0.5, actualCreateISHeavyMGAmmoHalfResult.tonnage, 0.0);
        assertEquals(0, actualCreateISHeavyMGAmmoHalfResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISHeavyMGAmmoHalfResult.svslots);
        assertEquals("Heavy Machine Gun", actualCreateISHeavyMGAmmoHalfResult.shortName);
        assertNull(actualCreateISHeavyMGAmmoHalfResult.modes);
        assertTrue(actualCreateISHeavyMGAmmoHalfResult.explosive);
        assertEquals(1, actualCreateISHeavyMGAmmoHalfResult.criticals);
        assertEquals(0.5, actualCreateISHeavyMGAmmoHalfResult.bv, 0.0);
        assertFalse(actualCreateISHeavyMGAmmoHalfResult.isSpreadable());
        assertFalse(actualCreateISHeavyMGAmmoHalfResult.isOmniFixedOnly());
        assertFalse(actualCreateISHeavyMGAmmoHalfResult.isMixedTech());
        assertTrue(actualCreateISHeavyMGAmmoHalfResult.isHittable());
        assertFalse(actualCreateISHeavyMGAmmoHalfResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISHeavyMGAmmoHalfResult.getToHitModifier());
        assertEquals(1, actualCreateISHeavyMGAmmoHalfResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISHeavyMGAmmoHalfResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISHeavyMGAmmoHalfResult.getStaticTechLevel());
        assertEquals("228,TM", actualCreateISHeavyMGAmmoHalfResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISHeavyMGAmmoHalfResult.getReintroductionDate());
        assertEquals(500.0, actualCreateISHeavyMGAmmoHalfResult.getRawCost(), 0.0);
        assertEquals(3058, actualCreateISHeavyMGAmmoHalfResult.getPrototypeDate());
        assertEquals("Heavy Machine Gun Ammo [Half]", actualCreateISHeavyMGAmmoHalfResult.getName());
        assertEquals("IS Heavy Machine Gun Ammo - Half", actualCreateISHeavyMGAmmoHalfResult.getInternalName());
        assertEquals("B/X-C-C-B", actualCreateISHeavyMGAmmoHalfResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISHeavyMGAmmoHalfResult.flags;
        assertSame(expectedFlags, actualCreateISHeavyMGAmmoHalfResult.getFlags());
    }

    private void createCLHeavyMGAmmo(AmmoType actualCreateCLHeavyMGAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLHeavyMGAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLHeavyMGAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLHeavyMGAmmoResult.svslots);
        assertEquals("Heavy Machine Gun", actualCreateCLHeavyMGAmmoResult.shortName);
        assertNull(actualCreateCLHeavyMGAmmoResult.modes);
        assertTrue(actualCreateCLHeavyMGAmmoResult.explosive);
        assertEquals(1, actualCreateCLHeavyMGAmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLHeavyMGAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLHeavyMGAmmoResult.isSpreadable());
        assertFalse(actualCreateCLHeavyMGAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLHeavyMGAmmoResult.isMixedTech());
        assertTrue(actualCreateCLHeavyMGAmmoResult.isHittable());
        assertFalse(actualCreateCLHeavyMGAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLHeavyMGAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLHeavyMGAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLHeavyMGAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLHeavyMGAmmoResult.getStaticTechLevel());
        assertEquals("228,TM", actualCreateCLHeavyMGAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLHeavyMGAmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateCLHeavyMGAmmoResult.getRawCost(), 0.0);
        assertEquals(3049, actualCreateCLHeavyMGAmmoResult.getPrototypeDate());
        assertEquals("Heavy Machine Gun Ammo [Full]", actualCreateCLHeavyMGAmmoResult.getName());
        assertEquals("Clan Heavy Machine Gun Ammo - Full", actualCreateCLHeavyMGAmmoResult.getInternalName());
        assertEquals("C/X-C-C-B", actualCreateCLHeavyMGAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLHeavyMGAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLHeavyMGAmmoResult.getFlags());
    }

    private void createCLHeavyMGAmmoHalf(AmmoType actualCreateCLHeavyMGAmmoHalfResult) {
        assertEquals(0.5, actualCreateCLHeavyMGAmmoHalfResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLHeavyMGAmmoHalfResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLHeavyMGAmmoHalfResult.svslots);
        assertEquals("Heavy Machine Gun", actualCreateCLHeavyMGAmmoHalfResult.shortName);
        assertNull(actualCreateCLHeavyMGAmmoHalfResult.modes);
        assertTrue(actualCreateCLHeavyMGAmmoHalfResult.explosive);
        assertEquals(1, actualCreateCLHeavyMGAmmoHalfResult.criticals);
        assertEquals(0.5, actualCreateCLHeavyMGAmmoHalfResult.bv, 0.0);
        assertFalse(actualCreateCLHeavyMGAmmoHalfResult.isSpreadable());
        assertFalse(actualCreateCLHeavyMGAmmoHalfResult.isOmniFixedOnly());
        assertFalse(actualCreateCLHeavyMGAmmoHalfResult.isMixedTech());
        assertTrue(actualCreateCLHeavyMGAmmoHalfResult.isHittable());
        assertFalse(actualCreateCLHeavyMGAmmoHalfResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLHeavyMGAmmoHalfResult.getToHitModifier());
        assertEquals(2, actualCreateCLHeavyMGAmmoHalfResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLHeavyMGAmmoHalfResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLHeavyMGAmmoHalfResult.getStaticTechLevel());
        assertEquals("228,TM", actualCreateCLHeavyMGAmmoHalfResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLHeavyMGAmmoHalfResult.getReintroductionDate());
        assertEquals(500.0, actualCreateCLHeavyMGAmmoHalfResult.getRawCost(), 0.0);
        assertEquals(3049, actualCreateCLHeavyMGAmmoHalfResult.getPrototypeDate());
        assertEquals("Heavy Machine Gun Ammo [Half]", actualCreateCLHeavyMGAmmoHalfResult.getName());
        assertEquals("Clan Heavy Machine Gun Ammo - Half", actualCreateCLHeavyMGAmmoHalfResult.getInternalName());
        assertEquals("C/X-C-C-B", actualCreateCLHeavyMGAmmoHalfResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLHeavyMGAmmoHalfResult.flags;
        assertSame(expectedFlags, actualCreateCLHeavyMGAmmoHalfResult.getFlags());
    }

    private void createCLATM3Ammo(AmmoType actualCreateCLATM3AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLATM3AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLATM3AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLATM3AmmoResult.svslots);
        assertEquals("ATM 3", actualCreateCLATM3AmmoResult.shortName);
        assertTrue(actualCreateCLATM3AmmoResult.explosive);
        assertEquals(1, actualCreateCLATM3AmmoResult.criticals);
        assertEquals(14.0, actualCreateCLATM3AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLATM3AmmoResult.isSpreadable());
        assertFalse(actualCreateCLATM3AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLATM3AmmoResult.isMixedTech());
        assertTrue(actualCreateCLATM3AmmoResult.isHittable());
        assertTrue(actualCreateCLATM3AmmoResult.hasModes());
        assertFalse(actualCreateCLATM3AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLATM3AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLATM3AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLATM3AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLATM3AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLATM3AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLATM3AmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLATM3AmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateCLATM3AmmoResult.getPrototypeDate());
        assertEquals("Standard ATM/3 Ammo", actualCreateCLATM3AmmoResult.getName());
        assertEquals("Clan Ammo ATM-3", actualCreateCLATM3AmmoResult.getInternalName());
        assertEquals("F/X-X-D-D", actualCreateCLATM3AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLATM3AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLATM3AmmoResult.getFlags());
    }

    private void createCLATM6Ammo(AmmoType actualCreateCLATM6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLATM6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLATM6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLATM6AmmoResult.svslots);
        assertEquals("ATM 6", actualCreateCLATM6AmmoResult.shortName);
        assertTrue(actualCreateCLATM6AmmoResult.explosive);
        assertEquals(1, actualCreateCLATM6AmmoResult.criticals);
        assertEquals(26.0, actualCreateCLATM6AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLATM6AmmoResult.isSpreadable());
        assertFalse(actualCreateCLATM6AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLATM6AmmoResult.isMixedTech());
        assertTrue(actualCreateCLATM6AmmoResult.isHittable());
        assertTrue(actualCreateCLATM6AmmoResult.hasModes());
        assertFalse(actualCreateCLATM6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLATM6AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLATM6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLATM6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLATM6AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLATM6AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLATM6AmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLATM6AmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateCLATM6AmmoResult.getPrototypeDate());
        assertEquals("Standard ATM/6 Ammo", actualCreateCLATM6AmmoResult.getName());
        assertEquals("Clan Ammo ATM-6", actualCreateCLATM6AmmoResult.getInternalName());
        assertEquals("F/X-X-D-D", actualCreateCLATM6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLATM6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLATM6AmmoResult.getFlags());
    }

    private void createCLATM9Ammo(AmmoType actualCreateCLATM9AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLATM9AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLATM9AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLATM9AmmoResult.svslots);
        assertEquals("ATM 9", actualCreateCLATM9AmmoResult.shortName);
        assertTrue(actualCreateCLATM9AmmoResult.explosive);
        assertEquals(1, actualCreateCLATM9AmmoResult.criticals);
        assertEquals(36.0, actualCreateCLATM9AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLATM9AmmoResult.isSpreadable());
        assertFalse(actualCreateCLATM9AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLATM9AmmoResult.isMixedTech());
        assertTrue(actualCreateCLATM9AmmoResult.isHittable());
        assertTrue(actualCreateCLATM9AmmoResult.hasModes());
        assertFalse(actualCreateCLATM9AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLATM9AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLATM9AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLATM9AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLATM9AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLATM9AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLATM9AmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLATM9AmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateCLATM9AmmoResult.getPrototypeDate());
        assertEquals("Standard ATM/9 Ammo", actualCreateCLATM9AmmoResult.getName());
        assertEquals("Clan Ammo ATM-9", actualCreateCLATM9AmmoResult.getInternalName());
        assertEquals("F/X-X-D-D", actualCreateCLATM9AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLATM9AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLATM9AmmoResult.getFlags());
    }

    private void createCLATM12Ammo(AmmoType actualCreateCLATM12AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLATM12AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLATM12AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLATM12AmmoResult.svslots);
        assertEquals("ATM 12", actualCreateCLATM12AmmoResult.shortName);
        assertTrue(actualCreateCLATM12AmmoResult.explosive);
        assertEquals(1, actualCreateCLATM12AmmoResult.criticals);
        assertEquals(52.0, actualCreateCLATM12AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLATM12AmmoResult.isSpreadable());
        assertFalse(actualCreateCLATM12AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLATM12AmmoResult.isMixedTech());
        assertTrue(actualCreateCLATM12AmmoResult.isHittable());
        assertTrue(actualCreateCLATM12AmmoResult.hasModes());
        assertFalse(actualCreateCLATM12AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLATM12AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLATM12AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLATM12AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLATM12AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLATM12AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLATM12AmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLATM12AmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateCLATM12AmmoResult.getPrototypeDate());
        assertEquals("Standard ATM/12 Ammo", actualCreateCLATM12AmmoResult.getName());
        assertEquals("Clan Ammo ATM-12", actualCreateCLATM12AmmoResult.getInternalName());
        assertEquals("F/X-X-D-D", actualCreateCLATM12AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLATM12AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLATM12AmmoResult.getFlags());
    }

    private void createCLATM3ERAmmo(AmmoType actualCreateCLATM3ERAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLATM3ERAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLATM3ERAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLATM3ERAmmoResult.svslots);
        assertEquals("ATM 3 ER", actualCreateCLATM3ERAmmoResult.shortName);
        assertTrue(actualCreateCLATM3ERAmmoResult.explosive);
        assertEquals(1, actualCreateCLATM3ERAmmoResult.criticals);
        assertEquals(14.0, actualCreateCLATM3ERAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLATM3ERAmmoResult.isSpreadable());
        assertFalse(actualCreateCLATM3ERAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLATM3ERAmmoResult.isMixedTech());
        assertTrue(actualCreateCLATM3ERAmmoResult.isHittable());
        assertTrue(actualCreateCLATM3ERAmmoResult.hasModes());
        assertFalse(actualCreateCLATM3ERAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLATM3ERAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLATM3ERAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLATM3ERAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLATM3ERAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLATM3ERAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLATM3ERAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLATM3ERAmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateCLATM3ERAmmoResult.getPrototypeDate());
        assertEquals("Extended-Range ATM/3 Ammo", actualCreateCLATM3ERAmmoResult.getName());
        assertEquals("Clan Ammo ATM-3 ER", actualCreateCLATM3ERAmmoResult.getInternalName());
        assertEquals("F/X-X-D-D", actualCreateCLATM3ERAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLATM3ERAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLATM3ERAmmoResult.getFlags());
    }

    private void createCLATM6ERAmmo(AmmoType actualCreateCLATM6ERAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLATM6ERAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLATM6ERAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLATM6ERAmmoResult.svslots);
        assertEquals("ATM 6 ER", actualCreateCLATM6ERAmmoResult.shortName);
        assertTrue(actualCreateCLATM6ERAmmoResult.explosive);
        assertEquals(1, actualCreateCLATM6ERAmmoResult.criticals);
        assertEquals(26.0, actualCreateCLATM6ERAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLATM6ERAmmoResult.isSpreadable());
        assertFalse(actualCreateCLATM6ERAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLATM6ERAmmoResult.isMixedTech());
        assertTrue(actualCreateCLATM6ERAmmoResult.isHittable());
        assertTrue(actualCreateCLATM6ERAmmoResult.hasModes());
        assertFalse(actualCreateCLATM6ERAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLATM6ERAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLATM6ERAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLATM6ERAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLATM6ERAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLATM6ERAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLATM6ERAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLATM6ERAmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateCLATM6ERAmmoResult.getPrototypeDate());
        assertEquals("Extended-Range ATM/6 Ammo", actualCreateCLATM6ERAmmoResult.getName());
        assertEquals("Clan Ammo ATM-6 ER", actualCreateCLATM6ERAmmoResult.getInternalName());
        assertEquals("F/X-X-D-D", actualCreateCLATM6ERAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLATM6ERAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLATM6ERAmmoResult.getFlags());
    }

    private void createCLATM9ERAmmo(AmmoType actualCreateCLATM9ERAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLATM9ERAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLATM9ERAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLATM9ERAmmoResult.svslots);
        assertEquals("ATM 9 ER", actualCreateCLATM9ERAmmoResult.shortName);
        assertTrue(actualCreateCLATM9ERAmmoResult.explosive);
        assertEquals(1, actualCreateCLATM9ERAmmoResult.criticals);
        assertEquals(36.0, actualCreateCLATM9ERAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLATM9ERAmmoResult.isSpreadable());
        assertFalse(actualCreateCLATM9ERAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLATM9ERAmmoResult.isMixedTech());
        assertTrue(actualCreateCLATM9ERAmmoResult.isHittable());
        assertTrue(actualCreateCLATM9ERAmmoResult.hasModes());
        assertFalse(actualCreateCLATM9ERAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLATM9ERAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLATM9ERAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLATM9ERAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLATM9ERAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLATM9ERAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLATM9ERAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLATM9ERAmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateCLATM9ERAmmoResult.getPrototypeDate());
        assertEquals("Extended-Range ATM/9 Ammo", actualCreateCLATM9ERAmmoResult.getName());
        assertEquals("Clan Ammo ATM-9 ER", actualCreateCLATM9ERAmmoResult.getInternalName());
        assertEquals("F/X-X-D-D", actualCreateCLATM9ERAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLATM9ERAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLATM9ERAmmoResult.getFlags());
    }

    private void createCLATM12ERAmmo(AmmoType actualCreateCLATM12ERAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLATM12ERAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLATM12ERAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLATM12ERAmmoResult.svslots);
        assertEquals("ATM 12 ER", actualCreateCLATM12ERAmmoResult.shortName);
        assertTrue(actualCreateCLATM12ERAmmoResult.explosive);
        assertEquals(1, actualCreateCLATM12ERAmmoResult.criticals);
        assertEquals(52.0, actualCreateCLATM12ERAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLATM12ERAmmoResult.isSpreadable());
        assertFalse(actualCreateCLATM12ERAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLATM12ERAmmoResult.isMixedTech());
        assertTrue(actualCreateCLATM12ERAmmoResult.isHittable());
        assertTrue(actualCreateCLATM12ERAmmoResult.hasModes());
        assertFalse(actualCreateCLATM12ERAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLATM12ERAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLATM12ERAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLATM12ERAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLATM12ERAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLATM12ERAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLATM12ERAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLATM12ERAmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateCLATM12ERAmmoResult.getPrototypeDate());
        assertEquals("Extended-Range ATM/12 Ammo", actualCreateCLATM12ERAmmoResult.getName());
        assertEquals("Clan Ammo ATM-12 ER", actualCreateCLATM12ERAmmoResult.getInternalName());
        assertEquals("F/X-X-D-D", actualCreateCLATM12ERAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLATM12ERAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLATM12ERAmmoResult.getFlags());
    }

    private void createCLATM3HEAmmo(AmmoType actualCreateCLATM3HEAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLATM3HEAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLATM3HEAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLATM3HEAmmoResult.svslots);
        assertEquals("ATM 3 HE", actualCreateCLATM3HEAmmoResult.shortName);
        assertNull(actualCreateCLATM3HEAmmoResult.modes);
        assertTrue(actualCreateCLATM3HEAmmoResult.explosive);
        assertEquals(1, actualCreateCLATM3HEAmmoResult.criticals);
        assertEquals(14.0, actualCreateCLATM3HEAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLATM3HEAmmoResult.isSpreadable());
        assertFalse(actualCreateCLATM3HEAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLATM3HEAmmoResult.isMixedTech());
        assertTrue(actualCreateCLATM3HEAmmoResult.isHittable());
        assertFalse(actualCreateCLATM3HEAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLATM3HEAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLATM3HEAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLATM3HEAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLATM3HEAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLATM3HEAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLATM3HEAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLATM3HEAmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateCLATM3HEAmmoResult.getPrototypeDate());
        assertEquals("High-Explosive ATM/3 Ammo", actualCreateCLATM3HEAmmoResult.getName());
        assertEquals("Clan Ammo ATM-3 HE", actualCreateCLATM3HEAmmoResult.getInternalName());
        assertEquals("F/X-X-D-D", actualCreateCLATM3HEAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLATM3HEAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLATM3HEAmmoResult.getFlags());
    }

    private void createCLATM6HEAmmo(AmmoType actualCreateCLATM6HEAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLATM6HEAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLATM6HEAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLATM6HEAmmoResult.svslots);
        assertEquals("ATM 6 HE", actualCreateCLATM6HEAmmoResult.shortName);
        assertNull(actualCreateCLATM6HEAmmoResult.modes);
        assertTrue(actualCreateCLATM6HEAmmoResult.explosive);
        assertEquals(1, actualCreateCLATM6HEAmmoResult.criticals);
        assertEquals(26.0, actualCreateCLATM6HEAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLATM6HEAmmoResult.isSpreadable());
        assertFalse(actualCreateCLATM6HEAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLATM6HEAmmoResult.isMixedTech());
        assertTrue(actualCreateCLATM6HEAmmoResult.isHittable());
        assertFalse(actualCreateCLATM6HEAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLATM6HEAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLATM6HEAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLATM6HEAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLATM6HEAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLATM6HEAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLATM6HEAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLATM6HEAmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateCLATM6HEAmmoResult.getPrototypeDate());
        assertEquals("High-Explosive ATM/6 Ammo", actualCreateCLATM6HEAmmoResult.getName());
        assertEquals("Clan Ammo ATM-6 HE", actualCreateCLATM6HEAmmoResult.getInternalName());
        assertEquals("F/X-X-D-D", actualCreateCLATM6HEAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLATM6HEAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLATM6HEAmmoResult.getFlags());
    }

    private void createCLATM9HEAmmo(AmmoType actualCreateCLATM9HEAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLATM9HEAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLATM9HEAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLATM9HEAmmoResult.svslots);
        assertEquals("ATM 9 HE", actualCreateCLATM9HEAmmoResult.shortName);
        assertNull(actualCreateCLATM9HEAmmoResult.modes);
        assertTrue(actualCreateCLATM9HEAmmoResult.explosive);
        assertEquals(1, actualCreateCLATM9HEAmmoResult.criticals);
        assertEquals(36.0, actualCreateCLATM9HEAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLATM9HEAmmoResult.isSpreadable());
        assertFalse(actualCreateCLATM9HEAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLATM9HEAmmoResult.isMixedTech());
        assertTrue(actualCreateCLATM9HEAmmoResult.isHittable());
        assertFalse(actualCreateCLATM9HEAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLATM9HEAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLATM9HEAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLATM9HEAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLATM9HEAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLATM9HEAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLATM9HEAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLATM9HEAmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateCLATM9HEAmmoResult.getPrototypeDate());
        assertEquals("High-Explosive ATM/9 Ammo", actualCreateCLATM9HEAmmoResult.getName());
        assertEquals("Clan Ammo ATM-9 HE", actualCreateCLATM9HEAmmoResult.getInternalName());
        assertEquals("F/X-X-D-D", actualCreateCLATM9HEAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLATM9HEAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLATM9HEAmmoResult.getFlags());
    }

    private void createCLATM12HEAmmo(AmmoType actualCreateCLATM12HEAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLATM12HEAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLATM12HEAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLATM12HEAmmoResult.svslots);
        assertEquals("ATM 12 HE", actualCreateCLATM12HEAmmoResult.shortName);
        assertNull(actualCreateCLATM12HEAmmoResult.modes);
        assertTrue(actualCreateCLATM12HEAmmoResult.explosive);
        assertEquals(1, actualCreateCLATM12HEAmmoResult.criticals);
        assertEquals(52.0, actualCreateCLATM12HEAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLATM12HEAmmoResult.isSpreadable());
        assertFalse(actualCreateCLATM12HEAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLATM12HEAmmoResult.isMixedTech());
        assertTrue(actualCreateCLATM12HEAmmoResult.isHittable());
        assertFalse(actualCreateCLATM12HEAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLATM12HEAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLATM12HEAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLATM12HEAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLATM12HEAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLATM12HEAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLATM12HEAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLATM12HEAmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateCLATM12HEAmmoResult.getPrototypeDate());
        assertEquals("High-Explosive ATM/12 Ammo", actualCreateCLATM12HEAmmoResult.getName());
        assertEquals("Clan Ammo ATM-12 HE", actualCreateCLATM12HEAmmoResult.getInternalName());
        assertEquals("F/X-X-D-D", actualCreateCLATM12HEAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLATM12HEAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLATM12HEAmmoResult.getFlags());
    }

    private void createCLIATM3Ammo(AmmoType actualCreateCLIATM3AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM3AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM3AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM3AmmoResult.svslots);
        assertEquals("iATM 3", actualCreateCLIATM3AmmoResult.shortName);
        assertTrue(actualCreateCLIATM3AmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM3AmmoResult.criticals);
        assertEquals(21.0, actualCreateCLIATM3AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM3AmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM3AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM3AmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM3AmmoResult.isHittable());
        assertTrue(actualCreateCLIATM3AmmoResult.hasModes());
        assertFalse(actualCreateCLIATM3AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM3AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM3AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM3AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLIATM3AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM3AmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM3AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM3AmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM3AmmoResult.getRawCost(), 0.0);
        assertEquals("Standard iATM/3 Ammo", actualCreateCLIATM3AmmoResult.getName());
        assertEquals("Clan Ammo iATM-3", actualCreateCLIATM3AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLIATM3AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM3AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM3AmmoResult.getFlags());
    }

    private void createCLIATM6Ammo(AmmoType actualCreateCLIATM6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM6AmmoResult.svslots);
        assertEquals("iATM 6", actualCreateCLIATM6AmmoResult.shortName);
        assertTrue(actualCreateCLIATM6AmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM6AmmoResult.criticals);
        assertEquals(39.0, actualCreateCLIATM6AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM6AmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM6AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM6AmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM6AmmoResult.isHittable());
        assertTrue(actualCreateCLIATM6AmmoResult.hasModes());
        assertFalse(actualCreateCLIATM6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM6AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLIATM6AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM6AmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM6AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM6AmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM6AmmoResult.getRawCost(), 0.0);
        assertEquals("Standard iATM/6 Ammo", actualCreateCLIATM6AmmoResult.getName());
        assertEquals("Clan Ammo iATM-6", actualCreateCLIATM6AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLIATM6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM6AmmoResult.getFlags());
    }

    private void createCLIATM9Ammo(AmmoType actualCreateCLIATM9AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM9AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM9AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM9AmmoResult.svslots);
        assertEquals("iATM 9", actualCreateCLIATM9AmmoResult.shortName);
        assertTrue(actualCreateCLIATM9AmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM9AmmoResult.criticals);
        assertEquals(54.0, actualCreateCLIATM9AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM9AmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM9AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM9AmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM9AmmoResult.isHittable());
        assertTrue(actualCreateCLIATM9AmmoResult.hasModes());
        assertFalse(actualCreateCLIATM9AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM9AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM9AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM9AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLIATM9AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM9AmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM9AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM9AmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM9AmmoResult.getRawCost(), 0.0);
        assertEquals("Standard iATM/9 Ammo", actualCreateCLIATM9AmmoResult.getName());
        assertEquals("Clan Ammo iATM-9", actualCreateCLIATM9AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLIATM9AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM9AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM9AmmoResult.getFlags());
    }

    private void createCLIATM12Ammo(AmmoType actualCreateCLIATM12AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM12AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM12AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM12AmmoResult.svslots);
        assertEquals("iATM 12", actualCreateCLIATM12AmmoResult.shortName);
        assertTrue(actualCreateCLIATM12AmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM12AmmoResult.criticals);
        assertEquals(78.0, actualCreateCLIATM12AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM12AmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM12AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM12AmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM12AmmoResult.isHittable());
        assertTrue(actualCreateCLIATM12AmmoResult.hasModes());
        assertFalse(actualCreateCLIATM12AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM12AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM12AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM12AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLIATM12AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM12AmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM12AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM12AmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM12AmmoResult.getRawCost(), 0.0);
        assertEquals("Standard iATM/12 Ammo", actualCreateCLIATM12AmmoResult.getName());
        assertEquals("Clan Ammo iATM-12", actualCreateCLIATM12AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLIATM12AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM12AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM12AmmoResult.getFlags());
    }

    private void createCLIATM3ERAmmo(AmmoType actualCreateCLIATM3ERAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM3ERAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM3ERAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM3ERAmmoResult.svslots);
        assertEquals("iATM 3 ER", actualCreateCLIATM3ERAmmoResult.shortName);
        assertTrue(actualCreateCLIATM3ERAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM3ERAmmoResult.criticals);
        assertEquals(21.0, actualCreateCLIATM3ERAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM3ERAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM3ERAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM3ERAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM3ERAmmoResult.isHittable());
        assertTrue(actualCreateCLIATM3ERAmmoResult.hasModes());
        assertFalse(actualCreateCLIATM3ERAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM3ERAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM3ERAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM3ERAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLIATM3ERAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM3ERAmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM3ERAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM3ERAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM3ERAmmoResult.getRawCost(), 0.0);
        assertEquals("Extended-Range iATM/3 Ammo", actualCreateCLIATM3ERAmmoResult.getName());
        assertEquals("Clan Ammo iATM-3 ER", actualCreateCLIATM3ERAmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLIATM3ERAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM3ERAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM3ERAmmoResult.getFlags());
    }

    private void createCLIATM6ERAmmo(AmmoType actualCreateCLIATM6ERAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM6ERAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM6ERAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM6ERAmmoResult.svslots);
        assertEquals("iATM 6 ER", actualCreateCLIATM6ERAmmoResult.shortName);
        assertNull(actualCreateCLIATM6ERAmmoResult.modes);
        assertTrue(actualCreateCLIATM6ERAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM6ERAmmoResult.criticals);
        assertEquals(39.0, actualCreateCLIATM6ERAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM6ERAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM6ERAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM6ERAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM6ERAmmoResult.isHittable());
        assertFalse(actualCreateCLIATM6ERAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM6ERAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM6ERAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM6ERAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLIATM6ERAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM6ERAmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM6ERAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM6ERAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM6ERAmmoResult.getRawCost(), 0.0);
        assertEquals("Extended-Range iATM/6 Ammo", actualCreateCLIATM6ERAmmoResult.getName());
        assertEquals("Clan Ammo iATM-6 ER", actualCreateCLIATM6ERAmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLIATM6ERAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM6ERAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM6ERAmmoResult.getFlags());
    }

    private void createCLIATM9ERAmmo(AmmoType actualCreateCLIATM9ERAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM9ERAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM9ERAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM9ERAmmoResult.svslots);
        assertEquals("iATM 9 ER", actualCreateCLIATM9ERAmmoResult.shortName);
        assertTrue(actualCreateCLIATM9ERAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM9ERAmmoResult.criticals);
        assertEquals(54.0, actualCreateCLIATM9ERAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM9ERAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM9ERAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM9ERAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM9ERAmmoResult.isHittable());
        assertTrue(actualCreateCLIATM9ERAmmoResult.hasModes());
        assertFalse(actualCreateCLIATM9ERAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM9ERAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM9ERAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM9ERAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLIATM9ERAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM9ERAmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM9ERAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM9ERAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM9ERAmmoResult.getRawCost(), 0.0);
        assertEquals("Extended-Range iATM/9 Ammo", actualCreateCLIATM9ERAmmoResult.getName());
        assertEquals("Clan Ammo iATM-9 ER", actualCreateCLIATM9ERAmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLIATM9ERAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM9ERAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM9ERAmmoResult.getFlags());
    }

    private void createCLIATM12ERAmmo(AmmoType actualCreateCLIATM12ERAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM12ERAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM12ERAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM12ERAmmoResult.svslots);
        assertEquals("iATM 12 ER", actualCreateCLIATM12ERAmmoResult.shortName);
        assertTrue(actualCreateCLIATM12ERAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM12ERAmmoResult.criticals);
        assertEquals(78.0, actualCreateCLIATM12ERAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM12ERAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM12ERAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM12ERAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM12ERAmmoResult.isHittable());
        assertTrue(actualCreateCLIATM12ERAmmoResult.hasModes());
        assertFalse(actualCreateCLIATM12ERAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM12ERAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM12ERAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM12ERAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLIATM12ERAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM12ERAmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM12ERAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM12ERAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM12ERAmmoResult.getRawCost(), 0.0);
        assertEquals("Extended-Range iATM/12 Ammo", actualCreateCLIATM12ERAmmoResult.getName());
        assertEquals("Clan Ammo iATM-12 ER", actualCreateCLIATM12ERAmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLIATM12ERAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM12ERAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM12ERAmmoResult.getFlags());
    }

    private void createCLIATM3HEAmmo(AmmoType actualCreateCLIATM3HEAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM3HEAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM3HEAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM3HEAmmoResult.svslots);
        assertEquals("iATM 3 HE", actualCreateCLIATM3HEAmmoResult.shortName);
        assertTrue(actualCreateCLIATM3HEAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM3HEAmmoResult.criticals);
        assertEquals(21.0, actualCreateCLIATM3HEAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM3HEAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM3HEAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM3HEAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM3HEAmmoResult.isHittable());
        assertTrue(actualCreateCLIATM3HEAmmoResult.hasModes());
        assertFalse(actualCreateCLIATM3HEAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM3HEAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM3HEAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM3HEAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLIATM3HEAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM3HEAmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM3HEAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM3HEAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM3HEAmmoResult.getRawCost(), 0.0);
        assertEquals("High-Explosive iATM/3 Ammo", actualCreateCLIATM3HEAmmoResult.getName());
        assertEquals("Clan Ammo iATM-3 HE", actualCreateCLIATM3HEAmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLIATM3HEAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM3HEAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM3HEAmmoResult.getFlags());
    }

    private void createCLIATM6HEAmmo(AmmoType actualCreateCLIATM6HEAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM6HEAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM6HEAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM6HEAmmoResult.svslots);
        assertEquals("iATM 6 HE", actualCreateCLIATM6HEAmmoResult.shortName);
        assertTrue(actualCreateCLIATM6HEAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM6HEAmmoResult.criticals);
        assertEquals(39.0, actualCreateCLIATM6HEAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM6HEAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM6HEAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM6HEAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM6HEAmmoResult.isHittable());
        assertTrue(actualCreateCLIATM6HEAmmoResult.hasModes());
        assertFalse(actualCreateCLIATM6HEAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM6HEAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM6HEAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM6HEAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLIATM6HEAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM6HEAmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM6HEAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM6HEAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM6HEAmmoResult.getRawCost(), 0.0);
        assertEquals("High-Explosive iATM/6 Ammo", actualCreateCLIATM6HEAmmoResult.getName());
        assertEquals("Clan Ammo iATM-6 HE", actualCreateCLIATM6HEAmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLIATM6HEAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM6HEAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM6HEAmmoResult.getFlags());
    }

    private void createCLIATM9HEAmmo(AmmoType actualCreateCLIATM9HEAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM9HEAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM9HEAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM9HEAmmoResult.svslots);
        assertEquals("iATM 9 HE", actualCreateCLIATM9HEAmmoResult.shortName);
        assertTrue(actualCreateCLIATM9HEAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM9HEAmmoResult.criticals);
        assertEquals(54.0, actualCreateCLIATM9HEAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM9HEAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM9HEAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM9HEAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM9HEAmmoResult.isHittable());
        assertTrue(actualCreateCLIATM9HEAmmoResult.hasModes());
        assertFalse(actualCreateCLIATM9HEAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM9HEAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM9HEAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM9HEAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLIATM9HEAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM9HEAmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM9HEAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM9HEAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM9HEAmmoResult.getRawCost(), 0.0);
        assertEquals("High-Explosive iATM/9 Ammo", actualCreateCLIATM9HEAmmoResult.getName());
        assertEquals("Clan Ammo iATM-9 HE", actualCreateCLIATM9HEAmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLIATM9HEAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM9HEAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM9HEAmmoResult.getFlags());
    }

    private void createCLIATM12HEAmmo(AmmoType actualCreateCLIATM12HEAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM12HEAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM12HEAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM12HEAmmoResult.svslots);
        assertEquals("iATM 12 HE", actualCreateCLIATM12HEAmmoResult.shortName);
        assertTrue(actualCreateCLIATM12HEAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM12HEAmmoResult.criticals);
        assertEquals(78.0, actualCreateCLIATM12HEAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM12HEAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM12HEAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM12HEAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM12HEAmmoResult.isHittable());
        assertTrue(actualCreateCLIATM12HEAmmoResult.hasModes());
        assertFalse(actualCreateCLIATM12HEAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM12HEAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM12HEAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM12HEAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLIATM12HEAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM12HEAmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM12HEAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM12HEAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM12HEAmmoResult.getRawCost(), 0.0);
        assertEquals("High-Explosive iATM/12 Ammo", actualCreateCLIATM12HEAmmoResult.getName());
        assertEquals("Clan Ammo iATM-12 HE", actualCreateCLIATM12HEAmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLIATM12HEAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM12HEAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM12HEAmmoResult.getFlags());
    }

    private void createCLIATM3IIWAmmo(AmmoType actualCreateCLIATM3IIWAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM3IIWAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM3IIWAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM3IIWAmmoResult.svslots);
        assertEquals("iATM 3 IIW", actualCreateCLIATM3IIWAmmoResult.shortName);
        assertTrue(actualCreateCLIATM3IIWAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM3IIWAmmoResult.criticals);
        assertEquals(27.0, actualCreateCLIATM3IIWAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM3IIWAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM3IIWAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM3IIWAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM3IIWAmmoResult.isHittable());
        assertTrue(actualCreateCLIATM3IIWAmmoResult.hasModes());
        assertFalse(actualCreateCLIATM3IIWAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM3IIWAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM3IIWAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM3IIWAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLIATM3IIWAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM3IIWAmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM3IIWAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM3IIWAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM3IIWAmmoResult.getRawCost(), 0.0);
        assertEquals("Improved Inferno iATM/3 Ammo", actualCreateCLIATM3IIWAmmoResult.getName());
        assertEquals("Clan Ammo iATM-3 IIW", actualCreateCLIATM3IIWAmmoResult.getInternalName());
        assertEquals("F/X-X-F-X", actualCreateCLIATM3IIWAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM3IIWAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM3IIWAmmoResult.getFlags());
    }

    private void createCLIATM6IIWAmmo(AmmoType actualCreateCLIATM6IIWAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM6IIWAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM6IIWAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM6IIWAmmoResult.svslots);
        assertEquals("iATM 6 IIW", actualCreateCLIATM6IIWAmmoResult.shortName);
        assertTrue(actualCreateCLIATM6IIWAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM6IIWAmmoResult.criticals);
        assertEquals(51.0, actualCreateCLIATM6IIWAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM6IIWAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM6IIWAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM6IIWAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM6IIWAmmoResult.isHittable());
        assertTrue(actualCreateCLIATM6IIWAmmoResult.hasModes());
        assertFalse(actualCreateCLIATM6IIWAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM6IIWAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM6IIWAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM6IIWAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLIATM6IIWAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM6IIWAmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM6IIWAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM6IIWAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM6IIWAmmoResult.getRawCost(), 0.0);
        assertEquals("Improved Inferno iATM/6 Ammo", actualCreateCLIATM6IIWAmmoResult.getName());
        assertEquals("Clan Ammo iATM-6 IIW", actualCreateCLIATM6IIWAmmoResult.getInternalName());
        assertEquals("F/X-X-F-X", actualCreateCLIATM6IIWAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM6IIWAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM6IIWAmmoResult.getFlags());
    }

    private void createCLIATM9IIWAmmo(AmmoType actualCreateCLIATM9IIWAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM9IIWAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM9IIWAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM9IIWAmmoResult.svslots);
        assertEquals("iATM 9 IIW", actualCreateCLIATM9IIWAmmoResult.shortName);
        assertTrue(actualCreateCLIATM9IIWAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM9IIWAmmoResult.criticals);
        assertEquals(70.0, actualCreateCLIATM9IIWAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM9IIWAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM9IIWAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM9IIWAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM9IIWAmmoResult.isHittable());
        assertTrue(actualCreateCLIATM9IIWAmmoResult.hasModes());
        assertFalse(actualCreateCLIATM9IIWAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM9IIWAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM9IIWAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM9IIWAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLIATM9IIWAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM9IIWAmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM9IIWAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM9IIWAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM9IIWAmmoResult.getRawCost(), 0.0);
        assertEquals("Improved Inferno iATM/9 Ammo", actualCreateCLIATM9IIWAmmoResult.getName());
        assertEquals("Clan Ammo iATM-9 IIW", actualCreateCLIATM9IIWAmmoResult.getInternalName());
        assertEquals("F/X-X-F-X", actualCreateCLIATM9IIWAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM9IIWAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM9IIWAmmoResult.getFlags());
    }

    private void createCLIATM12IIWAmmo(AmmoType actualCreateCLIATM12IIWAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM12IIWAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM12IIWAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM12IIWAmmoResult.svslots);
        assertEquals("iATM 12 IIW", actualCreateCLIATM12IIWAmmoResult.shortName);
        assertTrue(actualCreateCLIATM12IIWAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM12IIWAmmoResult.criticals);
        assertEquals(101.0, actualCreateCLIATM12IIWAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM12IIWAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM12IIWAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM12IIWAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM12IIWAmmoResult.isHittable());
        assertTrue(actualCreateCLIATM12IIWAmmoResult.hasModes());
        assertFalse(actualCreateCLIATM12IIWAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM12IIWAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM12IIWAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM12IIWAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLIATM12IIWAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM12IIWAmmoResult.getStandardRange());
        assertEquals("65,IO", actualCreateCLIATM12IIWAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM12IIWAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM12IIWAmmoResult.getRawCost(), 0.0);
        assertEquals("Improved Inferno iATM/12 Ammo", actualCreateCLIATM12IIWAmmoResult.getName());
        assertEquals("Clan Ammo iATM-12 IIW", actualCreateCLIATM12IIWAmmoResult.getInternalName());
        assertEquals("F/X-X-F-X", actualCreateCLIATM12IIWAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM12IIWAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM12IIWAmmoResult.getFlags());
    }

    private void createCLIATM3IMPAmmo(AmmoType actualCreateCLIATM3IMPAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM3IMPAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM3IMPAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM3IMPAmmoResult.svslots);
        assertEquals("iATM 3 IMP", actualCreateCLIATM3IMPAmmoResult.shortName);
        assertTrue(actualCreateCLIATM3IMPAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM3IMPAmmoResult.criticals);
        assertEquals(42.0, actualCreateCLIATM3IMPAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM3IMPAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM3IMPAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM3IMPAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM3IMPAmmoResult.isHittable());
        assertTrue(actualCreateCLIATM3IMPAmmoResult.hasModes());
        assertFalse(actualCreateCLIATM3IMPAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM3IMPAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM3IMPAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM3IMPAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLIATM3IMPAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM3IMPAmmoResult.getStandardRange());
        assertEquals("67,IO", actualCreateCLIATM3IMPAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM3IMPAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM3IMPAmmoResult.getRawCost(), 0.0);
        assertEquals("Improved Magnetic Pulse iATM/3 Ammo", actualCreateCLIATM3IMPAmmoResult.getName());
        assertEquals("Clan Ammo iATM-3 IMP", actualCreateCLIATM3IMPAmmoResult.getInternalName());
        assertEquals("F/X-X-F-X", actualCreateCLIATM3IMPAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM3IMPAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM3IMPAmmoResult.getFlags());
    }

    private void createCLIATM6IMPAmmo(AmmoType actualCreateCLIATM6IMPAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM6IMPAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM6IMPAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM6IMPAmmoResult.svslots);
        assertEquals("iATM 6 IMP", actualCreateCLIATM6IMPAmmoResult.shortName);
        assertTrue(actualCreateCLIATM6IMPAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM6IMPAmmoResult.criticals);
        assertEquals(78.0, actualCreateCLIATM6IMPAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM6IMPAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM6IMPAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM6IMPAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM6IMPAmmoResult.isHittable());
        assertTrue(actualCreateCLIATM6IMPAmmoResult.hasModes());
        assertFalse(actualCreateCLIATM6IMPAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM6IMPAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM6IMPAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM6IMPAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLIATM6IMPAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM6IMPAmmoResult.getStandardRange());
        assertEquals("67,IO", actualCreateCLIATM6IMPAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM6IMPAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM6IMPAmmoResult.getRawCost(), 0.0);
        assertEquals("Improved Magnetic Pulse iATM/6 Ammo", actualCreateCLIATM6IMPAmmoResult.getName());
        assertEquals("Clan Ammo iATM-6 IMP", actualCreateCLIATM6IMPAmmoResult.getInternalName());
        assertEquals("F/X-X-F-X", actualCreateCLIATM6IMPAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM6IMPAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM6IMPAmmoResult.getFlags());
    }

    private void createCLIATM9IMPAmmo(AmmoType actualCreateCLIATM9IMPAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM9IMPAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM9IMPAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM9IMPAmmoResult.svslots);
        assertEquals("iATM 9 IMP", actualCreateCLIATM9IMPAmmoResult.shortName);
        assertTrue(actualCreateCLIATM9IMPAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM9IMPAmmoResult.criticals);
        assertEquals(108.0, actualCreateCLIATM9IMPAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM9IMPAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM9IMPAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM9IMPAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM9IMPAmmoResult.isHittable());
        assertTrue(actualCreateCLIATM9IMPAmmoResult.hasModes());
        assertFalse(actualCreateCLIATM9IMPAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM9IMPAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM9IMPAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM9IMPAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLIATM9IMPAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM9IMPAmmoResult.getStandardRange());
        assertEquals("67,IO", actualCreateCLIATM9IMPAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM9IMPAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM9IMPAmmoResult.getRawCost(), 0.0);
        assertEquals("Improved Magnetic Pulse iATM/9 Ammo", actualCreateCLIATM9IMPAmmoResult.getName());
        assertEquals("Clan Ammo iATM-9 IMP", actualCreateCLIATM9IMPAmmoResult.getInternalName());
        assertEquals("F/X-X-F-X", actualCreateCLIATM9IMPAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM9IMPAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM9IMPAmmoResult.getFlags());
    }

    private void createCLIATM12IMPAmmo(AmmoType actualCreateCLIATM12IMPAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLIATM12IMPAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLIATM12IMPAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLIATM12IMPAmmoResult.svslots);
        assertEquals("iATM 12 IMP", actualCreateCLIATM12IMPAmmoResult.shortName);
        assertTrue(actualCreateCLIATM12IMPAmmoResult.explosive);
        assertEquals(1, actualCreateCLIATM12IMPAmmoResult.criticals);
        assertEquals(156.0, actualCreateCLIATM12IMPAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLIATM12IMPAmmoResult.isSpreadable());
        assertFalse(actualCreateCLIATM12IMPAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLIATM12IMPAmmoResult.isMixedTech());
        assertTrue(actualCreateCLIATM12IMPAmmoResult.isHittable());
        assertTrue(actualCreateCLIATM12IMPAmmoResult.hasModes());
        assertFalse(actualCreateCLIATM12IMPAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLIATM12IMPAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLIATM12IMPAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLIATM12IMPAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLIATM12IMPAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLIATM12IMPAmmoResult.getStandardRange());
        assertEquals("67,IO", actualCreateCLIATM12IMPAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLIATM12IMPAmmoResult.getReintroductionDate());
        assertEquals(75000.0, actualCreateCLIATM12IMPAmmoResult.getRawCost(), 0.0);
        assertEquals("Improved Magnetic Pulse iATM/12 Ammo", actualCreateCLIATM12IMPAmmoResult.getName());
        assertEquals("Clan Ammo iATM-12 IMP", actualCreateCLIATM12IMPAmmoResult.getInternalName());
        assertEquals("F/X-X-F-X", actualCreateCLIATM12IMPAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLIATM12IMPAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLIATM12IMPAmmoResult.getFlags());
    }

    private void createISLRM5Ammo(AmmoType actualCreateISLRM5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLRM5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLRM5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLRM5AmmoResult.svslots);
        assertEquals("LRM 5", actualCreateISLRM5AmmoResult.shortName);
        assertTrue(actualCreateISLRM5AmmoResult.explosive);
        assertEquals(1, actualCreateISLRM5AmmoResult.criticals);
        assertEquals(6.0, actualCreateISLRM5AmmoResult.bv, 0.0);
        assertFalse(actualCreateISLRM5AmmoResult.isSpreadable());
        assertFalse(actualCreateISLRM5AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISLRM5AmmoResult.isMixedTech());
        assertTrue(actualCreateISLRM5AmmoResult.isHittable());
        assertTrue(actualCreateISLRM5AmmoResult.hasModes());
        assertFalse(actualCreateISLRM5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLRM5AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISLRM5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLRM5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.INTRO, actualCreateISLRM5AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISLRM5AmmoResult.getRulesRefs());
        assertEquals(30000.0, actualCreateISLRM5AmmoResult.getRawCost(), 0.0);
        assertEquals(2295, actualCreateISLRM5AmmoResult.getPrototypeDate());
        assertEquals("LRM 5 Ammo", actualCreateISLRM5AmmoResult.getName());
        assertEquals("IS Ammo LRM-5", actualCreateISLRM5AmmoResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISLRM5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLRM5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLRM5AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISLRM5AmmoResult.getExtinctionDate());
    }

    private void createISLRM10Ammo(AmmoType actualCreateISLRM10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLRM10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLRM10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLRM10AmmoResult.svslots);
        assertEquals("LRM 10", actualCreateISLRM10AmmoResult.shortName);
        assertTrue(actualCreateISLRM10AmmoResult.explosive);
        assertEquals(1, actualCreateISLRM10AmmoResult.criticals);
        assertEquals(11.0, actualCreateISLRM10AmmoResult.bv, 0.0);
        assertFalse(actualCreateISLRM10AmmoResult.isSpreadable());
        assertFalse(actualCreateISLRM10AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISLRM10AmmoResult.isMixedTech());
        assertTrue(actualCreateISLRM10AmmoResult.isHittable());
        assertTrue(actualCreateISLRM10AmmoResult.hasModes());
        assertFalse(actualCreateISLRM10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLRM10AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISLRM10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLRM10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.INTRO, actualCreateISLRM10AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISLRM10AmmoResult.getRulesRefs());
        assertEquals(30000.0, actualCreateISLRM10AmmoResult.getRawCost(), 0.0);
        assertEquals(2295, actualCreateISLRM10AmmoResult.getPrototypeDate());
        assertEquals("LRM 10 Ammo", actualCreateISLRM10AmmoResult.getName());
        assertEquals("IS Ammo LRM-10", actualCreateISLRM10AmmoResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISLRM10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLRM10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLRM10AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISLRM10AmmoResult.getExtinctionDate());
    }

    private void createISLRM15Ammo(AmmoType actualCreateISLRM15AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLRM15AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLRM15AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLRM15AmmoResult.svslots);
        assertEquals("LRM 15", actualCreateISLRM15AmmoResult.shortName);
        assertTrue(actualCreateISLRM15AmmoResult.explosive);
        assertEquals(1, actualCreateISLRM15AmmoResult.criticals);
        assertEquals(17.0, actualCreateISLRM15AmmoResult.bv, 0.0);
        assertFalse(actualCreateISLRM15AmmoResult.isSpreadable());
        assertFalse(actualCreateISLRM15AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISLRM15AmmoResult.isMixedTech());
        assertTrue(actualCreateISLRM15AmmoResult.isHittable());
        assertTrue(actualCreateISLRM15AmmoResult.hasModes());
        assertFalse(actualCreateISLRM15AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLRM15AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISLRM15AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLRM15AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.INTRO, actualCreateISLRM15AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISLRM15AmmoResult.getRulesRefs());
        assertEquals(30000.0, actualCreateISLRM15AmmoResult.getRawCost(), 0.0);
        assertEquals(2295, actualCreateISLRM15AmmoResult.getPrototypeDate());
        assertEquals("LRM 15 Ammo", actualCreateISLRM15AmmoResult.getName());
        assertEquals("IS Ammo LRM-15", actualCreateISLRM15AmmoResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISLRM15AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLRM15AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLRM15AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISLRM15AmmoResult.getExtinctionDate());
    }

    private void createISLRM20Ammo(AmmoType actualCreateISLRM20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLRM20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLRM20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLRM20AmmoResult.svslots);
        assertEquals("LRM 20", actualCreateISLRM20AmmoResult.shortName);
        assertTrue(actualCreateISLRM20AmmoResult.explosive);
        assertEquals(1, actualCreateISLRM20AmmoResult.criticals);
        assertEquals(23.0, actualCreateISLRM20AmmoResult.bv, 0.0);
        assertFalse(actualCreateISLRM20AmmoResult.isSpreadable());
        assertFalse(actualCreateISLRM20AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISLRM20AmmoResult.isMixedTech());
        assertTrue(actualCreateISLRM20AmmoResult.isHittable());
        assertTrue(actualCreateISLRM20AmmoResult.hasModes());
        assertFalse(actualCreateISLRM20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLRM20AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISLRM20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLRM20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.INTRO, actualCreateISLRM20AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISLRM20AmmoResult.getRulesRefs());
        assertEquals(30000.0, actualCreateISLRM20AmmoResult.getRawCost(), 0.0);
        assertEquals(2295, actualCreateISLRM20AmmoResult.getPrototypeDate());
        assertEquals("LRM 20 Ammo", actualCreateISLRM20AmmoResult.getName());
        assertEquals("IS Ammo LRM-20", actualCreateISLRM20AmmoResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISLRM20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLRM20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLRM20AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISLRM20AmmoResult.getExtinctionDate());
    }

    private void createISEnhancedLRM5Ammo(AmmoType actualCreateISEnhancedLRM5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISEnhancedLRM5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISEnhancedLRM5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISEnhancedLRM5AmmoResult.svslots);
        assertEquals("NLRM 5", actualCreateISEnhancedLRM5AmmoResult.shortName);
        assertTrue(actualCreateISEnhancedLRM5AmmoResult.explosive);
        assertEquals(1, actualCreateISEnhancedLRM5AmmoResult.criticals);
        assertEquals(7.0, actualCreateISEnhancedLRM5AmmoResult.bv, 0.0);
        assertFalse(actualCreateISEnhancedLRM5AmmoResult.isSpreadable());
        assertFalse(actualCreateISEnhancedLRM5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISEnhancedLRM5AmmoResult.isMixedTech());
        assertTrue(actualCreateISEnhancedLRM5AmmoResult.isHittable());
        assertTrue(actualCreateISEnhancedLRM5AmmoResult.hasModes());
        assertFalse(actualCreateISEnhancedLRM5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISEnhancedLRM5AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISEnhancedLRM5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISEnhancedLRM5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISEnhancedLRM5AmmoResult.getStaticTechLevel());
        assertEquals("326,TO", actualCreateISEnhancedLRM5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISEnhancedLRM5AmmoResult.getReintroductionDate());
        assertEquals(31000.0, actualCreateISEnhancedLRM5AmmoResult.getRawCost(), 0.0);
        assertEquals(3058, actualCreateISEnhancedLRM5AmmoResult.getPrototypeDate());
        assertEquals("Enhanced LRM 5 Ammo", actualCreateISEnhancedLRM5AmmoResult.getName());
        assertEquals("ISEnhancedLRM5 Ammo", actualCreateISEnhancedLRM5AmmoResult.getInternalName());
        assertEquals("E/C-F-E-D", actualCreateISEnhancedLRM5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISEnhancedLRM5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISEnhancedLRM5AmmoResult.getFlags());
    }

    private void createISEnhancedLRM10Ammo(AmmoType actualCreateISEnhancedLRM10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISEnhancedLRM10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISEnhancedLRM10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISEnhancedLRM10AmmoResult.svslots);
        assertEquals("NLRM 10", actualCreateISEnhancedLRM10AmmoResult.shortName);
        assertTrue(actualCreateISEnhancedLRM10AmmoResult.explosive);
        assertEquals(1, actualCreateISEnhancedLRM10AmmoResult.criticals);
        assertEquals(13.0, actualCreateISEnhancedLRM10AmmoResult.bv, 0.0);
        assertFalse(actualCreateISEnhancedLRM10AmmoResult.isSpreadable());
        assertFalse(actualCreateISEnhancedLRM10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISEnhancedLRM10AmmoResult.isMixedTech());
        assertTrue(actualCreateISEnhancedLRM10AmmoResult.isHittable());
        assertTrue(actualCreateISEnhancedLRM10AmmoResult.hasModes());
        assertFalse(actualCreateISEnhancedLRM10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISEnhancedLRM10AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISEnhancedLRM10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISEnhancedLRM10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISEnhancedLRM10AmmoResult.getStaticTechLevel());
        assertEquals("326,TO", actualCreateISEnhancedLRM10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISEnhancedLRM10AmmoResult.getReintroductionDate());
        assertEquals(31000.0, actualCreateISEnhancedLRM10AmmoResult.getRawCost(), 0.0);
        assertEquals(3058, actualCreateISEnhancedLRM10AmmoResult.getPrototypeDate());
        assertEquals("Enhanced LRM 10 Ammo", actualCreateISEnhancedLRM10AmmoResult.getName());
        assertEquals("ISEnhancedLRM10 Ammo", actualCreateISEnhancedLRM10AmmoResult.getInternalName());
        assertEquals("E/C-F-E-D", actualCreateISEnhancedLRM10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISEnhancedLRM10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISEnhancedLRM10AmmoResult.getFlags());
    }

    private void createISEnhancedLRM15Ammo(AmmoType actualCreateISEnhancedLRM15AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISEnhancedLRM15AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISEnhancedLRM15AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISEnhancedLRM15AmmoResult.svslots);
        assertEquals("NLRM 15", actualCreateISEnhancedLRM15AmmoResult.shortName);
        assertTrue(actualCreateISEnhancedLRM15AmmoResult.explosive);
        assertEquals(1, actualCreateISEnhancedLRM15AmmoResult.criticals);
        assertEquals(20.0, actualCreateISEnhancedLRM15AmmoResult.bv, 0.0);
        assertFalse(actualCreateISEnhancedLRM15AmmoResult.isSpreadable());
        assertFalse(actualCreateISEnhancedLRM15AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISEnhancedLRM15AmmoResult.isMixedTech());
        assertTrue(actualCreateISEnhancedLRM15AmmoResult.isHittable());
        assertTrue(actualCreateISEnhancedLRM15AmmoResult.hasModes());
        assertFalse(actualCreateISEnhancedLRM15AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISEnhancedLRM15AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISEnhancedLRM15AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISEnhancedLRM15AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISEnhancedLRM15AmmoResult.getStaticTechLevel());
        assertEquals("326,TO", actualCreateISEnhancedLRM15AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISEnhancedLRM15AmmoResult.getReintroductionDate());
        assertEquals(31000.0, actualCreateISEnhancedLRM15AmmoResult.getRawCost(), 0.0);
        assertEquals(3058, actualCreateISEnhancedLRM15AmmoResult.getPrototypeDate());
        assertEquals("Enhanced LRM 15 Ammo", actualCreateISEnhancedLRM15AmmoResult.getName());
        assertEquals("ISEnhancedLRM15 Ammo", actualCreateISEnhancedLRM15AmmoResult.getInternalName());
        assertEquals("E/C-F-E-D", actualCreateISEnhancedLRM15AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISEnhancedLRM15AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISEnhancedLRM15AmmoResult.getFlags());
    }

    private void createISEnhancedLRM20Ammo(AmmoType actualCreateISEnhancedLRM20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISEnhancedLRM20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISEnhancedLRM20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISEnhancedLRM20AmmoResult.svslots);
        assertEquals("NLRM 20", actualCreateISEnhancedLRM20AmmoResult.shortName);
        assertTrue(actualCreateISEnhancedLRM20AmmoResult.explosive);
        assertEquals(1, actualCreateISEnhancedLRM20AmmoResult.criticals);
        assertEquals(26.0, actualCreateISEnhancedLRM20AmmoResult.bv, 0.0);
        assertFalse(actualCreateISEnhancedLRM20AmmoResult.isSpreadable());
        assertFalse(actualCreateISEnhancedLRM20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISEnhancedLRM20AmmoResult.isMixedTech());
        assertTrue(actualCreateISEnhancedLRM20AmmoResult.isHittable());
        assertTrue(actualCreateISEnhancedLRM20AmmoResult.hasModes());
        assertFalse(actualCreateISEnhancedLRM20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISEnhancedLRM20AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISEnhancedLRM20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISEnhancedLRM20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISEnhancedLRM20AmmoResult.getStaticTechLevel());
        assertEquals("326,TO", actualCreateISEnhancedLRM20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISEnhancedLRM20AmmoResult.getReintroductionDate());
        assertEquals(31000.0, actualCreateISEnhancedLRM20AmmoResult.getRawCost(), 0.0);
        assertEquals(3058, actualCreateISEnhancedLRM20AmmoResult.getPrototypeDate());
        assertEquals("Enhanced LRM 20 Ammo", actualCreateISEnhancedLRM20AmmoResult.getName());
        assertEquals("ISEnhancedLRM20 Ammo", actualCreateISEnhancedLRM20AmmoResult.getInternalName());
        assertEquals("E/C-F-E-D", actualCreateISEnhancedLRM20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISEnhancedLRM20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISEnhancedLRM20AmmoResult.getFlags());
    }

    private void createISExtendedLRM5Ammo(AmmoType actualCreateISExtendedLRM5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISExtendedLRM5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISExtendedLRM5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISExtendedLRM5AmmoResult.svslots);
        assertEquals("ELRM 5", actualCreateISExtendedLRM5AmmoResult.shortName);
        assertTrue(actualCreateISExtendedLRM5AmmoResult.explosive);
        assertEquals(1, actualCreateISExtendedLRM5AmmoResult.criticals);
        assertEquals(8.0, actualCreateISExtendedLRM5AmmoResult.bv, 0.0);
        assertFalse(actualCreateISExtendedLRM5AmmoResult.isSpreadable());
        assertFalse(actualCreateISExtendedLRM5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISExtendedLRM5AmmoResult.isMixedTech());
        assertTrue(actualCreateISExtendedLRM5AmmoResult.isHittable());
        assertTrue(actualCreateISExtendedLRM5AmmoResult.hasModes());
        assertFalse(actualCreateISExtendedLRM5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISExtendedLRM5AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISExtendedLRM5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISExtendedLRM5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISExtendedLRM5AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateISExtendedLRM5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISExtendedLRM5AmmoResult.getReintroductionDate());
        assertEquals(90000.0, actualCreateISExtendedLRM5AmmoResult.getRawCost(), 0.0);
        assertEquals(3054, actualCreateISExtendedLRM5AmmoResult.getPrototypeDate());
        assertEquals("Extended LRM 5 Ammo", actualCreateISExtendedLRM5AmmoResult.getName());
        assertEquals("IS Ammo Extended LRM-5", actualCreateISExtendedLRM5AmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateISExtendedLRM5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISExtendedLRM5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISExtendedLRM5AmmoResult.getFlags());
    }

    private void createISExtendedLRM10Ammo(AmmoType actualCreateISExtendedLRM10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISExtendedLRM10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISExtendedLRM10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISExtendedLRM10AmmoResult.svslots);
        assertEquals("ELRM 10", actualCreateISExtendedLRM10AmmoResult.shortName);
        assertTrue(actualCreateISExtendedLRM10AmmoResult.explosive);
        assertEquals(1, actualCreateISExtendedLRM10AmmoResult.criticals);
        assertEquals(17.0, actualCreateISExtendedLRM10AmmoResult.bv, 0.0);
        assertFalse(actualCreateISExtendedLRM10AmmoResult.isSpreadable());
        assertFalse(actualCreateISExtendedLRM10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISExtendedLRM10AmmoResult.isMixedTech());
        assertTrue(actualCreateISExtendedLRM10AmmoResult.isHittable());
        assertTrue(actualCreateISExtendedLRM10AmmoResult.hasModes());
        assertFalse(actualCreateISExtendedLRM10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISExtendedLRM10AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISExtendedLRM10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISExtendedLRM10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISExtendedLRM10AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateISExtendedLRM10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISExtendedLRM10AmmoResult.getReintroductionDate());
        assertEquals(90000.0, actualCreateISExtendedLRM10AmmoResult.getRawCost(), 0.0);
        assertEquals(3054, actualCreateISExtendedLRM10AmmoResult.getPrototypeDate());
        assertEquals("Extended LRM 10 Ammo", actualCreateISExtendedLRM10AmmoResult.getName());
        assertEquals("IS Ammo Extended LRM-10", actualCreateISExtendedLRM10AmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateISExtendedLRM10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISExtendedLRM10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISExtendedLRM10AmmoResult.getFlags());
    }

    private void createISExtendedLRM15Ammo(AmmoType actualCreateISExtendedLRM15AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISExtendedLRM15AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISExtendedLRM15AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISExtendedLRM15AmmoResult.svslots);
        assertEquals("ELRM 15", actualCreateISExtendedLRM15AmmoResult.shortName);
        assertTrue(actualCreateISExtendedLRM15AmmoResult.explosive);
        assertEquals(1, actualCreateISExtendedLRM15AmmoResult.criticals);
        assertEquals(25.0, actualCreateISExtendedLRM15AmmoResult.bv, 0.0);
        assertFalse(actualCreateISExtendedLRM15AmmoResult.isSpreadable());
        assertFalse(actualCreateISExtendedLRM15AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISExtendedLRM15AmmoResult.isMixedTech());
        assertTrue(actualCreateISExtendedLRM15AmmoResult.isHittable());
        assertTrue(actualCreateISExtendedLRM15AmmoResult.hasModes());
        assertFalse(actualCreateISExtendedLRM15AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISExtendedLRM15AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISExtendedLRM15AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISExtendedLRM15AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISExtendedLRM15AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateISExtendedLRM15AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISExtendedLRM15AmmoResult.getReintroductionDate());
        assertEquals(90000.0, actualCreateISExtendedLRM15AmmoResult.getRawCost(), 0.0);
        assertEquals(3054, actualCreateISExtendedLRM15AmmoResult.getPrototypeDate());
        assertEquals("Extended LRM 15 Ammo", actualCreateISExtendedLRM15AmmoResult.getName());
        assertEquals("IS Ammo Extended LRM-15", actualCreateISExtendedLRM15AmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateISExtendedLRM15AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISExtendedLRM15AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISExtendedLRM15AmmoResult.getFlags());
    }

    private void createISExtendedLRM20Ammo(AmmoType actualCreateISExtendedLRM20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISExtendedLRM20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISExtendedLRM20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISExtendedLRM20AmmoResult.svslots);
        assertEquals("ELRM 20", actualCreateISExtendedLRM20AmmoResult.shortName);
        assertTrue(actualCreateISExtendedLRM20AmmoResult.explosive);
        assertEquals(1, actualCreateISExtendedLRM20AmmoResult.criticals);
        assertEquals(34.0, actualCreateISExtendedLRM20AmmoResult.bv, 0.0);
        assertFalse(actualCreateISExtendedLRM20AmmoResult.isSpreadable());
        assertFalse(actualCreateISExtendedLRM20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISExtendedLRM20AmmoResult.isMixedTech());
        assertTrue(actualCreateISExtendedLRM20AmmoResult.isHittable());
        assertTrue(actualCreateISExtendedLRM20AmmoResult.hasModes());
        assertFalse(actualCreateISExtendedLRM20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISExtendedLRM20AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISExtendedLRM20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISExtendedLRM20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISExtendedLRM20AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateISExtendedLRM20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISExtendedLRM20AmmoResult.getReintroductionDate());
        assertEquals(90000.0, actualCreateISExtendedLRM20AmmoResult.getRawCost(), 0.0);
        assertEquals(3054, actualCreateISExtendedLRM20AmmoResult.getPrototypeDate());
        assertEquals("Extended LRM 20 Ammo", actualCreateISExtendedLRM20AmmoResult.getName());
        assertEquals("IS Ammo Extended LRM-20", actualCreateISExtendedLRM20AmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateISExtendedLRM20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISExtendedLRM20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISExtendedLRM20AmmoResult.getFlags());
    }

    private void createCLLRM5Ammo(AmmoType actualCreateCLLRM5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM5AmmoResult.svslots);
        assertEquals("LRM 5", actualCreateCLLRM5AmmoResult.shortName);
        assertNull(actualCreateCLLRM5AmmoResult.modes);
        assertTrue(actualCreateCLLRM5AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM5AmmoResult.criticals);
        assertEquals(7.0, actualCreateCLLRM5AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM5AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM5AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM5AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM5AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM5AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLLRM5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM5AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLLRM5AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLLRM5AmmoResult.getPrototypeDate());
        assertEquals("LRM 5 Ammo", actualCreateCLLRM5AmmoResult.getName());
        assertEquals("Clan Ammo LRM-5", actualCreateCLLRM5AmmoResult.getInternalName());
        assertEquals("F/X-D-C-C", actualCreateCLLRM5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM5AmmoResult.getFlags());
    }

    private void createCLLRM10Ammo(AmmoType actualCreateCLLRM10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM10AmmoResult.svslots);
        assertEquals("LRM 10", actualCreateCLLRM10AmmoResult.shortName);
        assertNull(actualCreateCLLRM10AmmoResult.modes);
        assertTrue(actualCreateCLLRM10AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM10AmmoResult.criticals);
        assertEquals(14.0, actualCreateCLLRM10AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM10AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM10AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM10AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM10AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM10AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLLRM10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM10AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLLRM10AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLLRM10AmmoResult.getPrototypeDate());
        assertEquals("LRM 10 Ammo", actualCreateCLLRM10AmmoResult.getName());
        assertEquals("Clan Ammo LRM-10", actualCreateCLLRM10AmmoResult.getInternalName());
        assertEquals("F/X-D-C-C", actualCreateCLLRM10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM10AmmoResult.getFlags());
    }

    private void createCLLRM15Ammo(AmmoType actualCreateCLLRM15AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM15AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM15AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM15AmmoResult.svslots);
        assertEquals("LRM 15", actualCreateCLLRM15AmmoResult.shortName);
        assertNull(actualCreateCLLRM15AmmoResult.modes);
        assertTrue(actualCreateCLLRM15AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM15AmmoResult.criticals);
        assertEquals(21.0, actualCreateCLLRM15AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM15AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM15AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM15AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM15AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM15AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM15AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM15AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM15AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM15AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLLRM15AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM15AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLLRM15AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLLRM15AmmoResult.getPrototypeDate());
        assertEquals("LRM 15 Ammo", actualCreateCLLRM15AmmoResult.getName());
        assertEquals("Clan Ammo LRM-15", actualCreateCLLRM15AmmoResult.getInternalName());
        assertEquals("F/X-D-C-C", actualCreateCLLRM15AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM15AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM15AmmoResult.getFlags());
    }

    private void createCLLRM20Ammo(AmmoType actualCreateCLLRM20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM20AmmoResult.svslots);
        assertEquals("LRM 20", actualCreateCLLRM20AmmoResult.shortName);
        assertNull(actualCreateCLLRM20AmmoResult.modes);
        assertTrue(actualCreateCLLRM20AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM20AmmoResult.criticals);
        assertEquals(27.0, actualCreateCLLRM20AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM20AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM20AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM20AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM20AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM20AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLLRM20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM20AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLLRM20AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLLRM20AmmoResult.getPrototypeDate());
        assertEquals("LRM 20 Ammo", actualCreateCLLRM20AmmoResult.getName());
        assertEquals("Clan Ammo LRM-20", actualCreateCLLRM20AmmoResult.getInternalName());
        assertEquals("F/X-D-C-C", actualCreateCLLRM20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM20AmmoResult.getFlags());
    }

    private void createCLStreakLRM5Ammo(AmmoType actualCreateCLStreakLRM5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM5AmmoResult.svslots);
        assertEquals("Streak LRM 5", actualCreateCLStreakLRM5AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM5AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM5AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM5AmmoResult.criticals);
        assertEquals(11.0, actualCreateCLStreakLRM5AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM5AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM5AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM5AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM5AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM5AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM5AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM5AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM5AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 5 Ammo", actualCreateCLStreakLRM5AmmoResult.getName());
        assertEquals("Clan Streak LRM 5 Ammo", actualCreateCLStreakLRM5AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM5AmmoResult.getFlags());
    }

    private void createCLStreakLRM10Ammo(AmmoType actualCreateCLStreakLRM10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM10AmmoResult.svslots);
        assertEquals("Streak LRM 10", actualCreateCLStreakLRM10AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM10AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM10AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM10AmmoResult.criticals);
        assertEquals(22.0, actualCreateCLStreakLRM10AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM10AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM10AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM10AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM10AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM10AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM10AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM10AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM10AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 10 Ammo", actualCreateCLStreakLRM10AmmoResult.getName());
        assertEquals("Clan Streak LRM 10 Ammo", actualCreateCLStreakLRM10AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM10AmmoResult.getFlags());
    }

    private void createCLStreakLRM15Ammo(AmmoType actualCreateCLStreakLRM15AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM15AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM15AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM15AmmoResult.svslots);
        assertEquals("Streak LRM 15", actualCreateCLStreakLRM15AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM15AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM15AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM15AmmoResult.criticals);
        assertEquals(32.0, actualCreateCLStreakLRM15AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM15AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM15AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM15AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM15AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM15AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM15AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM15AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM15AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM15AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM15AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM15AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM15AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM15AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 15 Ammo", actualCreateCLStreakLRM15AmmoResult.getName());
        assertEquals("Clan Streak LRM 15 Ammo", actualCreateCLStreakLRM15AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM15AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM15AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM15AmmoResult.getFlags());
    }

    private void createCLStreakLRM20Ammo(AmmoType actualCreateCLStreakLRM20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM20AmmoResult.svslots);
        assertEquals("Streak LRM 20", actualCreateCLStreakLRM20AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM20AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM20AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM20AmmoResult.criticals);
        assertEquals(43.0, actualCreateCLStreakLRM20AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM20AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM20AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM20AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM20AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM20AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM20AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM20AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM20AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 20 Ammo", actualCreateCLStreakLRM20AmmoResult.getName());
        assertEquals("Clan Streak LRM 20 Ammo", actualCreateCLStreakLRM20AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM20AmmoResult.getFlags());
    }

    private void createCLStreakLRM1Ammo(AmmoType actualCreateCLStreakLRM1AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM1AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM1AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM1AmmoResult.svslots);
        assertEquals("Streak LRM 1", actualCreateCLStreakLRM1AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM1AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM1AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM1AmmoResult.criticals);
        assertEquals(0.016, actualCreateCLStreakLRM1AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM1AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM1AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM1AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM1AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM1AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM1AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM1AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM1AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM1AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM1AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM1AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM1AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM1AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 1 Ammo", actualCreateCLStreakLRM1AmmoResult.getName());
        assertEquals("Clan Streak LRM 1 Ammo", actualCreateCLStreakLRM1AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM1AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM1AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM1AmmoResult.getFlags());
    }

    private void createCLStreakLRM2Ammo(AmmoType actualCreateCLStreakLRM2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM2AmmoResult.svslots);
        assertEquals("Streak LRM 2", actualCreateCLStreakLRM2AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM2AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM2AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM2AmmoResult.criticals);
        assertEquals(0.033, actualCreateCLStreakLRM2AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM2AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM2AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM2AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM2AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM2AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM2AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM2AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM2AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 2 Ammo", actualCreateCLStreakLRM2AmmoResult.getName());
        assertEquals("Clan Streak LRM 2 Ammo", actualCreateCLStreakLRM2AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM2AmmoResult.getFlags());
    }

    private void createCLStreakLRM3Ammo(AmmoType actualCreateCLStreakLRM3AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM3AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM3AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM3AmmoResult.svslots);
        assertEquals("Streak LRM 3", actualCreateCLStreakLRM3AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM3AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM3AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM3AmmoResult.criticals);
        assertEquals(0.05, actualCreateCLStreakLRM3AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM3AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM3AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM3AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM3AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM3AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM3AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM3AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM3AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM3AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM3AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM3AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM3AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM3AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 3 Ammo", actualCreateCLStreakLRM3AmmoResult.getName());
        assertEquals("Clan Streak LRM 3 Ammo", actualCreateCLStreakLRM3AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM3AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM3AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM3AmmoResult.getFlags());
    }

    private void createCLStreakLRM4Ammo(AmmoType actualCreateCLStreakLRM4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM4AmmoResult.svslots);
        assertEquals("Streak LRM 4", actualCreateCLStreakLRM4AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM4AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM4AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM4AmmoResult.criticals);
        assertEquals(0.067, actualCreateCLStreakLRM4AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM4AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM4AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM4AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM4AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM4AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM4AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM4AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM4AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 4 Ammo", actualCreateCLStreakLRM4AmmoResult.getName());
        assertEquals("Clan Streak LRM 4 Ammo", actualCreateCLStreakLRM4AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM4AmmoResult.getFlags());
    }

    private void createCLStreakLRM6Ammo(AmmoType actualCreateCLStreakLRM6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM6AmmoResult.svslots);
        assertEquals("Streak LRM 6", actualCreateCLStreakLRM6AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM6AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM6AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM6AmmoResult.criticals);
        assertEquals(0.1, actualCreateCLStreakLRM6AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM6AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM6AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM6AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM6AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM6AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM6AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM6AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM6AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM6AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM6AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 6 Ammo", actualCreateCLStreakLRM6AmmoResult.getName());
        assertEquals("Clan Streak LRM 6 Ammo", actualCreateCLStreakLRM6AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM6AmmoResult.getFlags());
    }

    private void createCLStreakLRM7Ammo(AmmoType actualCreateCLStreakLRM7AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM7AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM7AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM7AmmoResult.svslots);
        assertEquals("Streak LRM 7", actualCreateCLStreakLRM7AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM7AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM7AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM7AmmoResult.criticals);
        assertEquals(0.117, actualCreateCLStreakLRM7AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM7AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM7AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM7AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM7AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM7AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM7AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM7AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM7AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM7AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM7AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM7AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM7AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM7AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 7 Ammo", actualCreateCLStreakLRM7AmmoResult.getName());
        assertEquals("Clan Streak LRM 7 Ammo", actualCreateCLStreakLRM7AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM7AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM7AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM7AmmoResult.getFlags());
    }

    private void createCLStreakLRM8Ammo(AmmoType actualCreateCLStreakLRM8AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM8AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM8AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM8AmmoResult.svslots);
        assertEquals("Streak LRM 8", actualCreateCLStreakLRM8AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM8AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM8AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM8AmmoResult.criticals);
        assertEquals(0.133, actualCreateCLStreakLRM8AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM8AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM8AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM8AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM8AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM8AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM8AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM8AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM8AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM8AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM8AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM8AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM8AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM8AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 8 Ammo", actualCreateCLStreakLRM8AmmoResult.getName());
        assertEquals("Clan Streak LRM 8 Ammo", actualCreateCLStreakLRM8AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM8AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM8AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM8AmmoResult.getFlags());
    }

    private void createCLStreakLRM9Ammo(AmmoType actualCreateCLStreakLRM9AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM9AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM9AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM9AmmoResult.svslots);
        assertEquals("Streak LRM 9", actualCreateCLStreakLRM9AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM9AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM9AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM9AmmoResult.criticals);
        assertEquals(0.15, actualCreateCLStreakLRM9AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM9AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM9AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM9AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM9AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM9AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM9AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM9AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM9AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM9AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM9AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM9AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM9AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM9AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 9 Ammo", actualCreateCLStreakLRM9AmmoResult.getName());
        assertEquals("Clan Streak LRM 9 Ammo", actualCreateCLStreakLRM9AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM9AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM9AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM9AmmoResult.getFlags());
    }

    private void createCLStreakLRM11Ammo(AmmoType actualCreateCLStreakLRM11AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM11AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM11AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM11AmmoResult.svslots);
        assertEquals("Streak LRM 11", actualCreateCLStreakLRM11AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM11AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM11AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM11AmmoResult.criticals);
        assertEquals(0.183, actualCreateCLStreakLRM11AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM11AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM11AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM11AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM11AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM11AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM11AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM11AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM11AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM11AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM11AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM11AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM11AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM11AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 11 Ammo", actualCreateCLStreakLRM11AmmoResult.getName());
        assertEquals("Clan Streak LRM 11 Ammo", actualCreateCLStreakLRM11AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM11AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM11AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM11AmmoResult.getFlags());
    }

    private void createCLStreakLRM12Ammo(AmmoType actualCreateCLStreakLRM12AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM12AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM12AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM12AmmoResult.svslots);
        assertEquals("Streak LRM 12", actualCreateCLStreakLRM12AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM12AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM12AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM12AmmoResult.criticals);
        assertEquals(0.2, actualCreateCLStreakLRM12AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM12AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM12AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM12AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM12AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM12AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM12AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM12AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM12AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM12AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM12AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM12AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM12AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM12AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 12 Ammo", actualCreateCLStreakLRM12AmmoResult.getName());
        assertEquals("Clan Streak LRM 12 Ammo", actualCreateCLStreakLRM12AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM12AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM12AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM12AmmoResult.getFlags());
    }

    private void createCLStreakLRM13Ammo(AmmoType actualCreateCLStreakLRM13AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM13AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM13AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM13AmmoResult.svslots);
        assertEquals("Streak LRM 13", actualCreateCLStreakLRM13AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM13AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM13AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM13AmmoResult.criticals);
        assertEquals(0.216, actualCreateCLStreakLRM13AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM13AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM13AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM13AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM13AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM13AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM13AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM13AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM13AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM13AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM13AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM13AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM13AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM13AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 13 Ammo", actualCreateCLStreakLRM13AmmoResult.getName());
        assertEquals("Clan Streak LRM 13 Ammo", actualCreateCLStreakLRM13AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM13AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM13AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM13AmmoResult.getFlags());
    }

    private void createCLStreakLRM14Ammo(AmmoType actualCreateCLStreakLRM14AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM14AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM14AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM14AmmoResult.svslots);
        assertEquals("Streak LRM 14", actualCreateCLStreakLRM14AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM14AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM14AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM14AmmoResult.criticals);
        assertEquals(0.233, actualCreateCLStreakLRM14AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM14AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM14AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM14AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM14AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM14AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM14AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM14AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM14AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM14AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM14AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM14AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM14AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM14AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 14 Ammo", actualCreateCLStreakLRM14AmmoResult.getName());
        assertEquals("Clan Streak LRM 14 Ammo", actualCreateCLStreakLRM14AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM14AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM14AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM14AmmoResult.getFlags());
    }

    private void createCLStreakLRM16Ammo(AmmoType actualCreateCLStreakLRM16AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM16AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM16AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM16AmmoResult.svslots);
        assertEquals("Streak LRM 16", actualCreateCLStreakLRM16AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM16AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM16AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM16AmmoResult.criticals);
        assertEquals(0.266, actualCreateCLStreakLRM16AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM16AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM16AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM16AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM16AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM16AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM16AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM16AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM16AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM16AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM16AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM16AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM16AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM16AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 16 Ammo", actualCreateCLStreakLRM16AmmoResult.getName());
        assertEquals("Clan Streak LRM 16 Ammo", actualCreateCLStreakLRM16AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM16AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM16AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM16AmmoResult.getFlags());
    }

    private void createCLStreakLRM17Ammo(AmmoType actualCreateCLStreakLRM17AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM17AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM17AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM17AmmoResult.svslots);
        assertEquals("Streak LRM 17", actualCreateCLStreakLRM17AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM17AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM17AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM17AmmoResult.criticals);
        assertEquals(0.283, actualCreateCLStreakLRM17AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM17AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM17AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM17AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM17AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM17AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM17AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM17AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM17AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM17AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM17AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM17AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM17AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM17AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 17 Ammo", actualCreateCLStreakLRM17AmmoResult.getName());
        assertEquals("Clan Streak LRM 17 Ammo", actualCreateCLStreakLRM17AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM17AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM17AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM17AmmoResult.getFlags());
    }

    private void createCLStreakLRM18Ammo(AmmoType actualCreateCLStreakLRM18AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM18AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM18AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM18AmmoResult.svslots);
        assertEquals("Streak LRM 18", actualCreateCLStreakLRM18AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM18AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM18AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM18AmmoResult.criticals);
        assertEquals(0.3, actualCreateCLStreakLRM18AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM18AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM18AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM18AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM18AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM18AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM18AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM18AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM18AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM18AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM18AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM18AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM18AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM18AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 18 Ammo", actualCreateCLStreakLRM18AmmoResult.getName());
        assertEquals("Clan Streak LRM 18 Ammo", actualCreateCLStreakLRM18AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM18AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM18AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM18AmmoResult.getFlags());
    }

    private void createCLStreakLRM19Ammo(AmmoType actualCreateCLStreakLRM19AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakLRM19AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakLRM19AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM19AmmoResult.svslots);
        assertEquals("Streak LRM 19", actualCreateCLStreakLRM19AmmoResult.shortName);
        assertNull(actualCreateCLStreakLRM19AmmoResult.modes);
        assertTrue(actualCreateCLStreakLRM19AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakLRM19AmmoResult.criticals);
        assertEquals(0.316, actualCreateCLStreakLRM19AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakLRM19AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakLRM19AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakLRM19AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakLRM19AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakLRM19AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakLRM19AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakLRM19AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakLRM19AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLStreakLRM19AmmoResult.getStaticTechLevel());
        assertEquals("327,TO", actualCreateCLStreakLRM19AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakLRM19AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateCLStreakLRM19AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateCLStreakLRM19AmmoResult.getPrototypeDate());
        assertEquals("Streak LRM 19 Ammo", actualCreateCLStreakLRM19AmmoResult.getName());
        assertEquals("Clan Streak LRM 19 Ammo", actualCreateCLStreakLRM19AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLStreakLRM19AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakLRM19AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakLRM19AmmoResult.getFlags());
    }

    private void createCLLRM1Ammo(AmmoType actualCreateCLLRM1AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM1AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM1AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM1AmmoResult.svslots);
        assertEquals("LRM 1", actualCreateCLLRM1AmmoResult.shortName);
        assertNull(actualCreateCLLRM1AmmoResult.modes);
        assertTrue(actualCreateCLLRM1AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM1AmmoResult.criticals);
        assertEquals(0.02, actualCreateCLLRM1AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM1AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM1AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM1AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM1AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM1AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM1AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM1AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM1AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM1AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM1AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM1AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM1AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM1AmmoResult.getPrototypeDate());
        assertEquals("LRM 1 Ammo", actualCreateCLLRM1AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-1", actualCreateCLLRM1AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM1AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM1AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM1AmmoResult.getFlags());
    }

    private void createCLLRM2Ammo(AmmoType actualCreateCLLRM2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM2AmmoResult.svslots);
        assertEquals("LRM 2", actualCreateCLLRM2AmmoResult.shortName);
        assertNull(actualCreateCLLRM2AmmoResult.modes);
        assertTrue(actualCreateCLLRM2AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM2AmmoResult.criticals);
        assertEquals(3.0, actualCreateCLLRM2AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM2AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM2AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM2AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM2AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM2AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM2AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM2AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM2AmmoResult.getPrototypeDate());
        assertEquals("LRM 2 Ammo", actualCreateCLLRM2AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-2", actualCreateCLLRM2AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM2AmmoResult.getFlags());
    }

    private void createCLLRM3Ammo(AmmoType actualCreateCLLRM3AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM3AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM3AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM3AmmoResult.svslots);
        assertEquals("LRM 3", actualCreateCLLRM3AmmoResult.shortName);
        assertNull(actualCreateCLLRM3AmmoResult.modes);
        assertTrue(actualCreateCLLRM3AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM3AmmoResult.criticals);
        assertEquals(5.0, actualCreateCLLRM3AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM3AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM3AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM3AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM3AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM3AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM3AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM3AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM3AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM3AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM3AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM3AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM3AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM3AmmoResult.getPrototypeDate());
        assertEquals("LRM 3 Ammo", actualCreateCLLRM3AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-3", actualCreateCLLRM3AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM3AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM3AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM3AmmoResult.getFlags());
    }

    private void createCLLRM4Ammo(AmmoType actualCreateCLLRM4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM4AmmoResult.svslots);
        assertEquals("LRM 4", actualCreateCLLRM4AmmoResult.shortName);
        assertNull(actualCreateCLLRM4AmmoResult.modes);
        assertTrue(actualCreateCLLRM4AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM4AmmoResult.criticals);
        assertEquals(6.0, actualCreateCLLRM4AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM4AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM4AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM4AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM4AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM4AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM4AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM4AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM4AmmoResult.getPrototypeDate());
        assertEquals("LRM 4 Ammo", actualCreateCLLRM4AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-4", actualCreateCLLRM4AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM4AmmoResult.getFlags());
    }

    private void createCLLRM6Ammo(AmmoType actualCreateCLLRM6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM6AmmoResult.svslots);
        assertEquals("LRM 6", actualCreateCLLRM6AmmoResult.shortName);
        assertNull(actualCreateCLLRM6AmmoResult.modes);
        assertTrue(actualCreateCLLRM6AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM6AmmoResult.criticals);
        assertEquals(9.0, actualCreateCLLRM6AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM6AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM6AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM6AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM6AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM6AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM6AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM6AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM6AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM6AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM6AmmoResult.getPrototypeDate());
        assertEquals("LRM 6 Ammo", actualCreateCLLRM6AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-6", actualCreateCLLRM6AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM6AmmoResult.getFlags());
    }

    private void createCLLRM7Ammo(AmmoType actualCreateCLLRM7AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM7AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM7AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM7AmmoResult.svslots);
        assertEquals("LRM 7", actualCreateCLLRM7AmmoResult.shortName);
        assertNull(actualCreateCLLRM7AmmoResult.modes);
        assertTrue(actualCreateCLLRM7AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM7AmmoResult.criticals);
        assertEquals(10.0, actualCreateCLLRM7AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM7AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM7AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM7AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM7AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM7AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM7AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM7AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM7AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM7AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM7AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM7AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM7AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM7AmmoResult.getPrototypeDate());
        assertEquals("LRM 7 Ammo", actualCreateCLLRM7AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-7", actualCreateCLLRM7AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM7AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM7AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM7AmmoResult.getFlags());
    }

    private void createCLLRM8Ammo(AmmoType actualCreateCLLRM8AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM8AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM8AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM8AmmoResult.svslots);
        assertEquals("LRM 8", actualCreateCLLRM8AmmoResult.shortName);
        assertNull(actualCreateCLLRM8AmmoResult.modes);
        assertTrue(actualCreateCLLRM8AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM8AmmoResult.criticals);
        assertEquals(11.0, actualCreateCLLRM8AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM8AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM8AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM8AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM8AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM8AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM8AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM8AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM8AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM8AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM8AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM8AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM8AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM8AmmoResult.getPrototypeDate());
        assertEquals("LRM 8 Ammo", actualCreateCLLRM8AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-8", actualCreateCLLRM8AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM8AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM8AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM8AmmoResult.getFlags());
    }

    private void createCLLRM9Ammo(AmmoType actualCreateCLLRM9AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM9AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM9AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM9AmmoResult.svslots);
        assertEquals("LRM 9", actualCreateCLLRM9AmmoResult.shortName);
        assertNull(actualCreateCLLRM9AmmoResult.modes);
        assertTrue(actualCreateCLLRM9AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM9AmmoResult.criticals);
        assertEquals(12.0, actualCreateCLLRM9AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM9AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM9AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM9AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM9AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM9AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM9AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM9AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM9AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM9AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM9AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM9AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM9AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM9AmmoResult.getPrototypeDate());
        assertEquals("LRM 9 Ammo", actualCreateCLLRM9AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-9", actualCreateCLLRM9AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM9AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM9AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM9AmmoResult.getFlags());
    }

    private void createCLLRM11Ammo(AmmoType actualCreateCLLRM11AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM11AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM11AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM11AmmoResult.svslots);
        assertEquals("LRM 11", actualCreateCLLRM11AmmoResult.shortName);
        assertNull(actualCreateCLLRM11AmmoResult.modes);
        assertTrue(actualCreateCLLRM11AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM11AmmoResult.criticals);
        assertEquals(18.0, actualCreateCLLRM11AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM11AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM11AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM11AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM11AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM11AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM11AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM11AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM11AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM11AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM11AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM11AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM11AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM11AmmoResult.getPrototypeDate());
        assertEquals("LRM 11 Ammo", actualCreateCLLRM11AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-11", actualCreateCLLRM11AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM11AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM11AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM11AmmoResult.getFlags());
    }

    private void createCLLRM12Ammo(AmmoType actualCreateCLLRM12AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM12AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM12AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM12AmmoResult.svslots);
        assertEquals("LRM 12", actualCreateCLLRM12AmmoResult.shortName);
        assertNull(actualCreateCLLRM12AmmoResult.modes);
        assertTrue(actualCreateCLLRM12AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM12AmmoResult.criticals);
        assertEquals(18.0, actualCreateCLLRM12AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM12AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM12AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM12AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM12AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM12AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM12AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM12AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM12AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM12AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM12AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM12AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM12AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM12AmmoResult.getPrototypeDate());
        assertEquals("LRM 12 Ammo", actualCreateCLLRM12AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-12", actualCreateCLLRM12AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM12AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM12AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM12AmmoResult.getFlags());
    }

    private void createCLLRM13Ammo(AmmoType actualCreateCLLRM13AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM13AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM13AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM13AmmoResult.svslots);
        assertEquals("LRM 13", actualCreateCLLRM13AmmoResult.shortName);
        assertNull(actualCreateCLLRM13AmmoResult.modes);
        assertTrue(actualCreateCLLRM13AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM13AmmoResult.criticals);
        assertEquals(20.0, actualCreateCLLRM13AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM13AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM13AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM13AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM13AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM13AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM13AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM13AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM13AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM13AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM13AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM13AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM13AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM13AmmoResult.getPrototypeDate());
        assertEquals("LRM 13 Ammo", actualCreateCLLRM13AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-13", actualCreateCLLRM13AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM13AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM13AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM13AmmoResult.getFlags());
    }

    private void createCLLRM14Ammo(AmmoType actualCreateCLLRM14AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM14AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM14AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM14AmmoResult.svslots);
        assertEquals("LRM 14", actualCreateCLLRM14AmmoResult.shortName);
        assertNull(actualCreateCLLRM14AmmoResult.modes);
        assertTrue(actualCreateCLLRM14AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM14AmmoResult.criticals);
        assertEquals(21.0, actualCreateCLLRM14AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM14AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM14AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM14AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM14AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM14AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM14AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM14AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM14AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM14AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM14AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM14AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM14AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM14AmmoResult.getPrototypeDate());
        assertEquals("LRM 14 Ammo", actualCreateCLLRM14AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-14", actualCreateCLLRM14AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM14AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM14AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM14AmmoResult.getFlags());
    }

    private void createCLLRM16Ammo(AmmoType actualCreateCLLRM16AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM16AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM16AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM16AmmoResult.svslots);
        assertEquals("LRM 16", actualCreateCLLRM16AmmoResult.shortName);
        assertNull(actualCreateCLLRM16AmmoResult.modes);
        assertTrue(actualCreateCLLRM16AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM16AmmoResult.criticals);
        assertEquals(27.0, actualCreateCLLRM16AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM16AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM16AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM16AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM16AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM16AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM16AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM16AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM16AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM16AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM16AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM16AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM16AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM16AmmoResult.getPrototypeDate());
        assertEquals("LRM 16 Ammo", actualCreateCLLRM16AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-16", actualCreateCLLRM16AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM16AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM16AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM16AmmoResult.getFlags());
    }

    private void createCLLRM17Ammo(AmmoType actualCreateCLLRM17AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM17AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM17AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM17AmmoResult.svslots);
        assertEquals("LRM 17", actualCreateCLLRM17AmmoResult.shortName);
        assertNull(actualCreateCLLRM17AmmoResult.modes);
        assertTrue(actualCreateCLLRM17AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM17AmmoResult.criticals);
        assertEquals(27.0, actualCreateCLLRM17AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM17AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM17AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM17AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM17AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM17AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM17AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM17AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM17AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM17AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM17AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM17AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM17AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM17AmmoResult.getPrototypeDate());
        assertEquals("LRM 17 Ammo", actualCreateCLLRM17AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-17", actualCreateCLLRM17AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM17AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM17AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM17AmmoResult.getFlags());
    }

    private void createCLLRM18Ammo(AmmoType actualCreateCLLRM18AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM18AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM18AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM18AmmoResult.svslots);
        assertEquals("LRM 18", actualCreateCLLRM18AmmoResult.shortName);
        assertNull(actualCreateCLLRM18AmmoResult.modes);
        assertTrue(actualCreateCLLRM18AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM18AmmoResult.criticals);
        assertEquals(27.0, actualCreateCLLRM18AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM18AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM18AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM18AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM18AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM18AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM18AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM18AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM18AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM18AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM18AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM18AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM18AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM18AmmoResult.getPrototypeDate());
        assertEquals("LRM 18 Ammo", actualCreateCLLRM18AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-18", actualCreateCLLRM18AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM18AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM18AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM18AmmoResult.getFlags());
    }

    private void createCLLRM19Ammo(AmmoType actualCreateCLLRM19AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRM19AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRM19AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRM19AmmoResult.svslots);
        assertEquals("LRM 19", actualCreateCLLRM19AmmoResult.shortName);
        assertNull(actualCreateCLLRM19AmmoResult.modes);
        assertTrue(actualCreateCLLRM19AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRM19AmmoResult.criticals);
        assertEquals(27.0, actualCreateCLLRM19AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRM19AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRM19AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRM19AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRM19AmmoResult.isHittable());
        assertFalse(actualCreateCLLRM19AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRM19AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRM19AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRM19AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRM19AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRM19AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRM19AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRM19AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRM19AmmoResult.getPrototypeDate());
        assertEquals("LRM 19 Ammo", actualCreateCLLRM19AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRM-19", actualCreateCLLRM19AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRM19AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRM19AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRM19AmmoResult.getFlags());
    }

    private void createISMRM10Ammo(AmmoType actualCreateISMRM10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMRM10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMRM10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMRM10AmmoResult.svslots);
        assertEquals("MRM 10", actualCreateISMRM10AmmoResult.shortName);
        assertNull(actualCreateISMRM10AmmoResult.modes);
        assertTrue(actualCreateISMRM10AmmoResult.explosive);
        assertEquals(1, actualCreateISMRM10AmmoResult.criticals);
        assertEquals(7.0, actualCreateISMRM10AmmoResult.bv, 0.0);
        assertFalse(actualCreateISMRM10AmmoResult.isSpreadable());
        assertFalse(actualCreateISMRM10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMRM10AmmoResult.isMixedTech());
        assertTrue(actualCreateISMRM10AmmoResult.isHittable());
        assertFalse(actualCreateISMRM10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMRM10AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISMRM10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMRM10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMRM10AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISMRM10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMRM10AmmoResult.getReintroductionDate());
        assertEquals(5000.0, actualCreateISMRM10AmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateISMRM10AmmoResult.getPrototypeDate());
        assertEquals("MRM 10 Ammo", actualCreateISMRM10AmmoResult.getName());
        assertEquals("IS MRM 10 Ammo", actualCreateISMRM10AmmoResult.getInternalName());
        assertEquals("C/X-X-E-D", actualCreateISMRM10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMRM10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMRM10AmmoResult.getFlags());
    }

    private void createISMRM20Ammo(AmmoType actualCreateISMRM20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMRM20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMRM20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMRM20AmmoResult.svslots);
        assertEquals("MRM 20", actualCreateISMRM20AmmoResult.shortName);
        assertNull(actualCreateISMRM20AmmoResult.modes);
        assertTrue(actualCreateISMRM20AmmoResult.explosive);
        assertEquals(1, actualCreateISMRM20AmmoResult.criticals);
        assertEquals(14.0, actualCreateISMRM20AmmoResult.bv, 0.0);
        assertFalse(actualCreateISMRM20AmmoResult.isSpreadable());
        assertFalse(actualCreateISMRM20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMRM20AmmoResult.isMixedTech());
        assertTrue(actualCreateISMRM20AmmoResult.isHittable());
        assertFalse(actualCreateISMRM20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMRM20AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISMRM20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMRM20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMRM20AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISMRM20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMRM20AmmoResult.getReintroductionDate());
        assertEquals(5000.0, actualCreateISMRM20AmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateISMRM20AmmoResult.getPrototypeDate());
        assertEquals("MRM 20 Ammo", actualCreateISMRM20AmmoResult.getName());
        assertEquals("IS MRM 20 Ammo", actualCreateISMRM20AmmoResult.getInternalName());
        assertEquals("C/X-X-E-D", actualCreateISMRM20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMRM20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMRM20AmmoResult.getFlags());
    }

    private void createISMRM30Ammo(AmmoType actualCreateISMRM30AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMRM30AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMRM30AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMRM30AmmoResult.svslots);
        assertEquals("MRM 30", actualCreateISMRM30AmmoResult.shortName);
        assertNull(actualCreateISMRM30AmmoResult.modes);
        assertTrue(actualCreateISMRM30AmmoResult.explosive);
        assertEquals(1, actualCreateISMRM30AmmoResult.criticals);
        assertEquals(21.0, actualCreateISMRM30AmmoResult.bv, 0.0);
        assertFalse(actualCreateISMRM30AmmoResult.isSpreadable());
        assertFalse(actualCreateISMRM30AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMRM30AmmoResult.isMixedTech());
        assertTrue(actualCreateISMRM30AmmoResult.isHittable());
        assertFalse(actualCreateISMRM30AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMRM30AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISMRM30AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMRM30AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMRM30AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISMRM30AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMRM30AmmoResult.getReintroductionDate());
        assertEquals(5000.0, actualCreateISMRM30AmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateISMRM30AmmoResult.getPrototypeDate());
        assertEquals("MRM 30 Ammo", actualCreateISMRM30AmmoResult.getName());
        assertEquals("IS MRM 30 Ammo", actualCreateISMRM30AmmoResult.getInternalName());
        assertEquals("C/X-X-E-D", actualCreateISMRM30AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMRM30AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMRM30AmmoResult.getFlags());
    }

    private void createISMRM40Ammo(AmmoType actualCreateISMRM40AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMRM40AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMRM40AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMRM40AmmoResult.svslots);
        assertEquals("MRM 40", actualCreateISMRM40AmmoResult.shortName);
        assertNull(actualCreateISMRM40AmmoResult.modes);
        assertTrue(actualCreateISMRM40AmmoResult.explosive);
        assertEquals(1, actualCreateISMRM40AmmoResult.criticals);
        assertEquals(28.0, actualCreateISMRM40AmmoResult.bv, 0.0);
        assertFalse(actualCreateISMRM40AmmoResult.isSpreadable());
        assertFalse(actualCreateISMRM40AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMRM40AmmoResult.isMixedTech());
        assertTrue(actualCreateISMRM40AmmoResult.isHittable());
        assertFalse(actualCreateISMRM40AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMRM40AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISMRM40AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMRM40AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMRM40AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISMRM40AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMRM40AmmoResult.getReintroductionDate());
        assertEquals(5000.0, actualCreateISMRM40AmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateISMRM40AmmoResult.getPrototypeDate());
        assertEquals("MRM 40 Ammo", actualCreateISMRM40AmmoResult.getName());
        assertEquals("IS MRM 40 Ammo", actualCreateISMRM40AmmoResult.getInternalName());
        assertEquals("C/X-X-E-D", actualCreateISMRM40AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMRM40AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMRM40AmmoResult.getFlags());
    }

    private void createISSRM2Ammo(AmmoType actualCreateISSRM2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISSRM2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISSRM2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISSRM2AmmoResult.svslots);
        assertEquals("SRM 2", actualCreateISSRM2AmmoResult.shortName);
        assertNull(actualCreateISSRM2AmmoResult.modes);
        assertTrue(actualCreateISSRM2AmmoResult.explosive);
        assertEquals(1, actualCreateISSRM2AmmoResult.criticals);
        assertEquals(3.0, actualCreateISSRM2AmmoResult.bv, 0.0);
        assertFalse(actualCreateISSRM2AmmoResult.isSpreadable());
        assertFalse(actualCreateISSRM2AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISSRM2AmmoResult.isMixedTech());
        assertTrue(actualCreateISSRM2AmmoResult.isHittable());
        assertFalse(actualCreateISSRM2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISSRM2AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISSRM2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISSRM2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.INTRO, actualCreateISSRM2AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISSRM2AmmoResult.getRulesRefs());
        assertEquals(27000.0, actualCreateISSRM2AmmoResult.getRawCost(), 0.0);
        assertEquals(2365, actualCreateISSRM2AmmoResult.getPrototypeDate());
        assertEquals("SRM 2 Ammo", actualCreateISSRM2AmmoResult.getName());
        assertEquals("IS Ammo SRM-2", actualCreateISSRM2AmmoResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISSRM2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISSRM2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISSRM2AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISSRM2AmmoResult.getExtinctionDate());
    }

    private void createISSRM4Ammo(AmmoType actualCreateISSRM4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISSRM4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISSRM4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISSRM4AmmoResult.svslots);
        assertEquals("SRM 4", actualCreateISSRM4AmmoResult.shortName);
        assertNull(actualCreateISSRM4AmmoResult.modes);
        assertTrue(actualCreateISSRM4AmmoResult.explosive);
        assertEquals(1, actualCreateISSRM4AmmoResult.criticals);
        assertEquals(5.0, actualCreateISSRM4AmmoResult.bv, 0.0);
        assertFalse(actualCreateISSRM4AmmoResult.isSpreadable());
        assertFalse(actualCreateISSRM4AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISSRM4AmmoResult.isMixedTech());
        assertTrue(actualCreateISSRM4AmmoResult.isHittable());
        assertFalse(actualCreateISSRM4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISSRM4AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISSRM4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISSRM4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.INTRO, actualCreateISSRM4AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISSRM4AmmoResult.getRulesRefs());
        assertEquals(27000.0, actualCreateISSRM4AmmoResult.getRawCost(), 0.0);
        assertEquals(2365, actualCreateISSRM4AmmoResult.getPrototypeDate());
        assertEquals("SRM 4 Ammo", actualCreateISSRM4AmmoResult.getName());
        assertEquals("IS Ammo SRM-4", actualCreateISSRM4AmmoResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISSRM4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISSRM4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISSRM4AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISSRM4AmmoResult.getExtinctionDate());
    }

    private void createISSRM6Ammo(AmmoType actualCreateISSRM6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISSRM6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISSRM6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISSRM6AmmoResult.svslots);
        assertEquals("SRM 6", actualCreateISSRM6AmmoResult.shortName);
        assertNull(actualCreateISSRM6AmmoResult.modes);
        assertTrue(actualCreateISSRM6AmmoResult.explosive);
        assertEquals(1, actualCreateISSRM6AmmoResult.criticals);
        assertEquals(7.0, actualCreateISSRM6AmmoResult.bv, 0.0);
        assertFalse(actualCreateISSRM6AmmoResult.isSpreadable());
        assertFalse(actualCreateISSRM6AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISSRM6AmmoResult.isMixedTech());
        assertTrue(actualCreateISSRM6AmmoResult.isHittable());
        assertFalse(actualCreateISSRM6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISSRM6AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISSRM6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISSRM6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.INTRO, actualCreateISSRM6AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISSRM6AmmoResult.getRulesRefs());
        assertEquals(27000.0, actualCreateISSRM6AmmoResult.getRawCost(), 0.0);
        assertEquals(2365, actualCreateISSRM6AmmoResult.getPrototypeDate());
        assertEquals("SRM 6 Ammo", actualCreateISSRM6AmmoResult.getName());
        assertEquals("IS Ammo SRM-6", actualCreateISSRM6AmmoResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISSRM6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISSRM6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISSRM6AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISSRM6AmmoResult.getExtinctionDate());
    }

    private void createCLSRM1Ammo(AmmoType actualCreateCLSRM1AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLSRM1AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLSRM1AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLSRM1AmmoResult.svslots);
        assertEquals("SRM 1", actualCreateCLSRM1AmmoResult.shortName);
        assertNull(actualCreateCLSRM1AmmoResult.modes);
        assertTrue(actualCreateCLSRM1AmmoResult.explosive);
        assertEquals(1, actualCreateCLSRM1AmmoResult.criticals);
        assertEquals(2.0, actualCreateCLSRM1AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLSRM1AmmoResult.isSpreadable());
        assertFalse(actualCreateCLSRM1AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLSRM1AmmoResult.isMixedTech());
        assertTrue(actualCreateCLSRM1AmmoResult.isHittable());
        assertFalse(actualCreateCLSRM1AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLSRM1AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLSRM1AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLSRM1AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLSRM1AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLSRM1AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLSRM1AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLSRM1AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLSRM1AmmoResult.getPrototypeDate());
        assertEquals("SRM 1 Ammo", actualCreateCLSRM1AmmoResult.getName());
        assertEquals("Clan Ammo SRM-1", actualCreateCLSRM1AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLSRM1AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLSRM1AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLSRM1AmmoResult.getFlags());
    }

    private void createCLSRM2Ammo(AmmoType actualCreateCLSRM2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLSRM2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLSRM2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLSRM2AmmoResult.svslots);
        assertEquals("SRM 2", actualCreateCLSRM2AmmoResult.shortName);
        assertNull(actualCreateCLSRM2AmmoResult.modes);
        assertTrue(actualCreateCLSRM2AmmoResult.explosive);
        assertEquals(1, actualCreateCLSRM2AmmoResult.criticals);
        assertEquals(3.0, actualCreateCLSRM2AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLSRM2AmmoResult.isSpreadable());
        assertFalse(actualCreateCLSRM2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLSRM2AmmoResult.isMixedTech());
        assertTrue(actualCreateCLSRM2AmmoResult.isHittable());
        assertFalse(actualCreateCLSRM2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLSRM2AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLSRM2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLSRM2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLSRM2AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLSRM2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLSRM2AmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateCLSRM2AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLSRM2AmmoResult.getPrototypeDate());
        assertEquals("SRM 2 Ammo", actualCreateCLSRM2AmmoResult.getName());
        assertEquals("Clan Ammo SRM-2", actualCreateCLSRM2AmmoResult.getInternalName());
        assertEquals("F/X-D-C-C", actualCreateCLSRM2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLSRM2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLSRM2AmmoResult.getFlags());
    }

    private void createCLSRM3Ammo(AmmoType actualCreateCLSRM3AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLSRM3AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLSRM3AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLSRM3AmmoResult.svslots);
        assertEquals("SRM 3", actualCreateCLSRM3AmmoResult.shortName);
        assertNull(actualCreateCLSRM3AmmoResult.modes);
        assertTrue(actualCreateCLSRM3AmmoResult.explosive);
        assertEquals(1, actualCreateCLSRM3AmmoResult.criticals);
        assertEquals(4.0, actualCreateCLSRM3AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLSRM3AmmoResult.isSpreadable());
        assertFalse(actualCreateCLSRM3AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLSRM3AmmoResult.isMixedTech());
        assertTrue(actualCreateCLSRM3AmmoResult.isHittable());
        assertFalse(actualCreateCLSRM3AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLSRM3AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLSRM3AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLSRM3AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLSRM3AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLSRM3AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLSRM3AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLSRM3AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLSRM3AmmoResult.getPrototypeDate());
        assertEquals("SRM 3 Ammo", actualCreateCLSRM3AmmoResult.getName());
        assertEquals("Clan Ammo SRM-3", actualCreateCLSRM3AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLSRM3AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLSRM3AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLSRM3AmmoResult.getFlags());
    }

    private void createCLSRM4Ammo(AmmoType actualCreateCLSRM4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLSRM4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLSRM4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLSRM4AmmoResult.svslots);
        assertEquals("SRM 4", actualCreateCLSRM4AmmoResult.shortName);
        assertNull(actualCreateCLSRM4AmmoResult.modes);
        assertTrue(actualCreateCLSRM4AmmoResult.explosive);
        assertEquals(1, actualCreateCLSRM4AmmoResult.criticals);
        assertEquals(5.0, actualCreateCLSRM4AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLSRM4AmmoResult.isSpreadable());
        assertFalse(actualCreateCLSRM4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLSRM4AmmoResult.isMixedTech());
        assertTrue(actualCreateCLSRM4AmmoResult.isHittable());
        assertFalse(actualCreateCLSRM4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLSRM4AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLSRM4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLSRM4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLSRM4AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLSRM4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLSRM4AmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateCLSRM4AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLSRM4AmmoResult.getPrototypeDate());
        assertEquals("SRM 4 Ammo", actualCreateCLSRM4AmmoResult.getName());
        assertEquals("Clan Ammo SRM-4", actualCreateCLSRM4AmmoResult.getInternalName());
        assertEquals("F/X-D-C-C", actualCreateCLSRM4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLSRM4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLSRM4AmmoResult.getFlags());
    }

    private void createCLSRM5Ammo(AmmoType actualCreateCLSRM5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLSRM5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLSRM5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLSRM5AmmoResult.svslots);
        assertEquals("SRM 5", actualCreateCLSRM5AmmoResult.shortName);
        assertNull(actualCreateCLSRM5AmmoResult.modes);
        assertTrue(actualCreateCLSRM5AmmoResult.explosive);
        assertEquals(1, actualCreateCLSRM5AmmoResult.criticals);
        assertEquals(6.0, actualCreateCLSRM5AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLSRM5AmmoResult.isSpreadable());
        assertFalse(actualCreateCLSRM5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLSRM5AmmoResult.isMixedTech());
        assertTrue(actualCreateCLSRM5AmmoResult.isHittable());
        assertFalse(actualCreateCLSRM5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLSRM5AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLSRM5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLSRM5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLSRM5AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLSRM5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLSRM5AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLSRM5AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLSRM5AmmoResult.getPrototypeDate());
        assertEquals("SRM 5 Ammo", actualCreateCLSRM5AmmoResult.getName());
        assertEquals("Clan Ammo SRM-5", actualCreateCLSRM5AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLSRM5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLSRM5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLSRM5AmmoResult.getFlags());
    }

    private void createCLSRM6Ammo(AmmoType actualCreateCLSRM6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLSRM6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLSRM6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLSRM6AmmoResult.svslots);
        assertEquals("SRM 6", actualCreateCLSRM6AmmoResult.shortName);
        assertNull(actualCreateCLSRM6AmmoResult.modes);
        assertTrue(actualCreateCLSRM6AmmoResult.explosive);
        assertEquals(1, actualCreateCLSRM6AmmoResult.criticals);
        assertEquals(7.0, actualCreateCLSRM6AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLSRM6AmmoResult.isSpreadable());
        assertFalse(actualCreateCLSRM6AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLSRM6AmmoResult.isMixedTech());
        assertTrue(actualCreateCLSRM6AmmoResult.isHittable());
        assertFalse(actualCreateCLSRM6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLSRM6AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLSRM6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLSRM6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLSRM6AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateCLSRM6AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLSRM6AmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateCLSRM6AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLSRM6AmmoResult.getPrototypeDate());
        assertEquals("SRM 6 Ammo", actualCreateCLSRM6AmmoResult.getName());
        assertEquals("Clan Ammo SRM-6", actualCreateCLSRM6AmmoResult.getInternalName());
        assertEquals("F/X-D-C-C", actualCreateCLSRM6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLSRM6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLSRM6AmmoResult.getFlags());
    }

    private void createISMML3LRMAmmo(AmmoType actualCreateISMML3LRMAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMML3LRMAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMML3LRMAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMML3LRMAmmoResult.svslots);
        assertEquals("MML 3/LRM", actualCreateISMML3LRMAmmoResult.shortName);
        assertTrue(actualCreateISMML3LRMAmmoResult.explosive);
        assertEquals(1, actualCreateISMML3LRMAmmoResult.criticals);
        assertEquals(4.0, actualCreateISMML3LRMAmmoResult.bv, 0.0);
        assertFalse(actualCreateISMML3LRMAmmoResult.isSpreadable());
        assertFalse(actualCreateISMML3LRMAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMML3LRMAmmoResult.isMixedTech());
        assertTrue(actualCreateISMML3LRMAmmoResult.isHittable());
        assertTrue(actualCreateISMML3LRMAmmoResult.hasModes());
        assertFalse(actualCreateISMML3LRMAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMML3LRMAmmoResult.getToHitModifier());
        assertEquals(3, actualCreateISMML3LRMAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMML3LRMAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMML3LRMAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISMML3LRMAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMML3LRMAmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISMML3LRMAmmoResult.getRawCost(), 0.0);
        assertEquals(3062, actualCreateISMML3LRMAmmoResult.getPrototypeDate());
        assertEquals("MML 3 LRM Ammo", actualCreateISMML3LRMAmmoResult.getName());
        assertEquals("IS Ammo MML-3 LRM", actualCreateISMML3LRMAmmoResult.getInternalName());
        assertEquals("D/X-X-E-D", actualCreateISMML3LRMAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMML3LRMAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMML3LRMAmmoResult.getFlags());
    }

    private void createISMML3SRMAmmo(AmmoType actualCreateISMML3SRMAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMML3SRMAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMML3SRMAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMML3SRMAmmoResult.svslots);
        assertEquals("MML 3/SRM", actualCreateISMML3SRMAmmoResult.shortName);
        assertNull(actualCreateISMML3SRMAmmoResult.modes);
        assertTrue(actualCreateISMML3SRMAmmoResult.explosive);
        assertEquals(1, actualCreateISMML3SRMAmmoResult.criticals);
        assertEquals(4.0, actualCreateISMML3SRMAmmoResult.bv, 0.0);
        assertFalse(actualCreateISMML3SRMAmmoResult.isSpreadable());
        assertFalse(actualCreateISMML3SRMAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMML3SRMAmmoResult.isMixedTech());
        assertTrue(actualCreateISMML3SRMAmmoResult.isHittable());
        assertFalse(actualCreateISMML3SRMAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMML3SRMAmmoResult.getToHitModifier());
        assertEquals(3, actualCreateISMML3SRMAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMML3SRMAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMML3SRMAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISMML3SRMAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMML3SRMAmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateISMML3SRMAmmoResult.getRawCost(), 0.0);
        assertEquals(3062, actualCreateISMML3SRMAmmoResult.getPrototypeDate());
        assertEquals("MML 3 SRM Ammo", actualCreateISMML3SRMAmmoResult.getName());
        assertEquals("IS Ammo MML-3 SRM", actualCreateISMML3SRMAmmoResult.getInternalName());
        assertEquals("D/X-X-E-D", actualCreateISMML3SRMAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMML3SRMAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMML3SRMAmmoResult.getFlags());
    }

    private void createISMML5LRMAmmo(AmmoType actualCreateISMML5LRMAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMML5LRMAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMML5LRMAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMML5LRMAmmoResult.svslots);
        assertEquals("MML 5/LRM", actualCreateISMML5LRMAmmoResult.shortName);
        assertTrue(actualCreateISMML5LRMAmmoResult.explosive);
        assertEquals(1, actualCreateISMML5LRMAmmoResult.criticals);
        assertEquals(6.0, actualCreateISMML5LRMAmmoResult.bv, 0.0);
        assertFalse(actualCreateISMML5LRMAmmoResult.isSpreadable());
        assertFalse(actualCreateISMML5LRMAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMML5LRMAmmoResult.isMixedTech());
        assertTrue(actualCreateISMML5LRMAmmoResult.isHittable());
        assertTrue(actualCreateISMML5LRMAmmoResult.hasModes());
        assertFalse(actualCreateISMML5LRMAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMML5LRMAmmoResult.getToHitModifier());
        assertEquals(3, actualCreateISMML5LRMAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMML5LRMAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMML5LRMAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISMML5LRMAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMML5LRMAmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISMML5LRMAmmoResult.getRawCost(), 0.0);
        assertEquals(3062, actualCreateISMML5LRMAmmoResult.getPrototypeDate());
        assertEquals("MML 5 LRM Ammo", actualCreateISMML5LRMAmmoResult.getName());
        assertEquals("IS Ammo MML-5 LRM", actualCreateISMML5LRMAmmoResult.getInternalName());
        assertEquals("D/X-X-E-D", actualCreateISMML5LRMAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMML5LRMAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMML5LRMAmmoResult.getFlags());
    }

    private void createISMML5SRMAmmo(AmmoType actualCreateISMML5SRMAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMML5SRMAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMML5SRMAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMML5SRMAmmoResult.svslots);
        assertEquals("MML 5/SRM", actualCreateISMML5SRMAmmoResult.shortName);
        assertNull(actualCreateISMML5SRMAmmoResult.modes);
        assertTrue(actualCreateISMML5SRMAmmoResult.explosive);
        assertEquals(1, actualCreateISMML5SRMAmmoResult.criticals);
        assertEquals(6.0, actualCreateISMML5SRMAmmoResult.bv, 0.0);
        assertFalse(actualCreateISMML5SRMAmmoResult.isSpreadable());
        assertFalse(actualCreateISMML5SRMAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMML5SRMAmmoResult.isMixedTech());
        assertTrue(actualCreateISMML5SRMAmmoResult.isHittable());
        assertFalse(actualCreateISMML5SRMAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMML5SRMAmmoResult.getToHitModifier());
        assertEquals(3, actualCreateISMML5SRMAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMML5SRMAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMML5SRMAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISMML5SRMAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMML5SRMAmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateISMML5SRMAmmoResult.getRawCost(), 0.0);
        assertEquals(3062, actualCreateISMML5SRMAmmoResult.getPrototypeDate());
        assertEquals("MML 5 SRM Ammo", actualCreateISMML5SRMAmmoResult.getName());
        assertEquals("IS Ammo MML-5 SRM", actualCreateISMML5SRMAmmoResult.getInternalName());
        assertEquals("D/X-X-E-D", actualCreateISMML5SRMAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMML5SRMAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMML5SRMAmmoResult.getFlags());
    }

    private void createISMML7LRMAmmo(AmmoType actualCreateISMML7LRMAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMML7LRMAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMML7LRMAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMML7LRMAmmoResult.svslots);
        assertEquals("MML 7/LRM", actualCreateISMML7LRMAmmoResult.shortName);
        assertTrue(actualCreateISMML7LRMAmmoResult.explosive);
        assertEquals(1, actualCreateISMML7LRMAmmoResult.criticals);
        assertEquals(8.0, actualCreateISMML7LRMAmmoResult.bv, 0.0);
        assertFalse(actualCreateISMML7LRMAmmoResult.isSpreadable());
        assertFalse(actualCreateISMML7LRMAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMML7LRMAmmoResult.isMixedTech());
        assertTrue(actualCreateISMML7LRMAmmoResult.isHittable());
        assertTrue(actualCreateISMML7LRMAmmoResult.hasModes());
        assertFalse(actualCreateISMML7LRMAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMML7LRMAmmoResult.getToHitModifier());
        assertEquals(3, actualCreateISMML7LRMAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMML7LRMAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMML7LRMAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISMML7LRMAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMML7LRMAmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISMML7LRMAmmoResult.getRawCost(), 0.0);
        assertEquals(3062, actualCreateISMML7LRMAmmoResult.getPrototypeDate());
        assertEquals("MML 7 LRM Ammo", actualCreateISMML7LRMAmmoResult.getName());
        assertEquals("IS Ammo MML-7 LRM", actualCreateISMML7LRMAmmoResult.getInternalName());
        assertEquals("D/X-X-E-D", actualCreateISMML7LRMAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMML7LRMAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMML7LRMAmmoResult.getFlags());
    }

    private void createISMML7SRMAmmo(AmmoType actualCreateISMML7SRMAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMML7SRMAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMML7SRMAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMML7SRMAmmoResult.svslots);
        assertEquals("MML 7/SRM", actualCreateISMML7SRMAmmoResult.shortName);
        assertNull(actualCreateISMML7SRMAmmoResult.modes);
        assertTrue(actualCreateISMML7SRMAmmoResult.explosive);
        assertEquals(1, actualCreateISMML7SRMAmmoResult.criticals);
        assertEquals(8.0, actualCreateISMML7SRMAmmoResult.bv, 0.0);
        assertFalse(actualCreateISMML7SRMAmmoResult.isSpreadable());
        assertFalse(actualCreateISMML7SRMAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMML7SRMAmmoResult.isMixedTech());
        assertTrue(actualCreateISMML7SRMAmmoResult.isHittable());
        assertFalse(actualCreateISMML7SRMAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMML7SRMAmmoResult.getToHitModifier());
        assertEquals(3, actualCreateISMML7SRMAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMML7SRMAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMML7SRMAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISMML7SRMAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMML7SRMAmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateISMML7SRMAmmoResult.getRawCost(), 0.0);
        assertEquals(3062, actualCreateISMML7SRMAmmoResult.getPrototypeDate());
        assertEquals("MML 7 SRM Ammo", actualCreateISMML7SRMAmmoResult.getName());
        assertEquals("IS Ammo MML-7 SRM", actualCreateISMML7SRMAmmoResult.getInternalName());
        assertEquals("D/X-X-E-D", actualCreateISMML7SRMAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMML7SRMAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMML7SRMAmmoResult.getFlags());
    }

    private void createISMML9LRMAmmo(AmmoType actualCreateISMML9LRMAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMML9LRMAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMML9LRMAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMML9LRMAmmoResult.svslots);
        assertEquals("MML 9/LRM", actualCreateISMML9LRMAmmoResult.shortName);
        assertTrue(actualCreateISMML9LRMAmmoResult.explosive);
        assertEquals(1, actualCreateISMML9LRMAmmoResult.criticals);
        assertEquals(11.0, actualCreateISMML9LRMAmmoResult.bv, 0.0);
        assertFalse(actualCreateISMML9LRMAmmoResult.isSpreadable());
        assertFalse(actualCreateISMML9LRMAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMML9LRMAmmoResult.isMixedTech());
        assertTrue(actualCreateISMML9LRMAmmoResult.isHittable());
        assertTrue(actualCreateISMML9LRMAmmoResult.hasModes());
        assertFalse(actualCreateISMML9LRMAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMML9LRMAmmoResult.getToHitModifier());
        assertEquals(3, actualCreateISMML9LRMAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMML9LRMAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMML9LRMAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISMML9LRMAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMML9LRMAmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISMML9LRMAmmoResult.getRawCost(), 0.0);
        assertEquals(3062, actualCreateISMML9LRMAmmoResult.getPrototypeDate());
        assertEquals("MML 9 LRM Ammo", actualCreateISMML9LRMAmmoResult.getName());
        assertEquals("IS Ammo MML-9 LRM", actualCreateISMML9LRMAmmoResult.getInternalName());
        assertEquals("D/X-X-E-D", actualCreateISMML9LRMAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMML9LRMAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMML9LRMAmmoResult.getFlags());
    }

    private void createISMML9SRMAmmo(AmmoType actualCreateISMML9SRMAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMML9SRMAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMML9SRMAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMML9SRMAmmoResult.svslots);
        assertEquals("MML 9/SRM", actualCreateISMML9SRMAmmoResult.shortName);
        assertNull(actualCreateISMML9SRMAmmoResult.modes);
        assertTrue(actualCreateISMML9SRMAmmoResult.explosive);
        assertEquals(1, actualCreateISMML9SRMAmmoResult.criticals);
        assertEquals(11.0, actualCreateISMML9SRMAmmoResult.bv, 0.0);
        assertFalse(actualCreateISMML9SRMAmmoResult.isSpreadable());
        assertFalse(actualCreateISMML9SRMAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMML9SRMAmmoResult.isMixedTech());
        assertTrue(actualCreateISMML9SRMAmmoResult.isHittable());
        assertFalse(actualCreateISMML9SRMAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMML9SRMAmmoResult.getToHitModifier());
        assertEquals(3, actualCreateISMML9SRMAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMML9SRMAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMML9SRMAmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISMML9SRMAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMML9SRMAmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateISMML9SRMAmmoResult.getRawCost(), 0.0);
        assertEquals(3062, actualCreateISMML9SRMAmmoResult.getPrototypeDate());
        assertEquals("MML 9 SRM Ammo", actualCreateISMML9SRMAmmoResult.getName());
        assertEquals("IS Ammo MML-9 SRM", actualCreateISMML9SRMAmmoResult.getInternalName());
        assertEquals("D/X-X-E-D", actualCreateISMML9SRMAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMML9SRMAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMML9SRMAmmoResult.getFlags());
    }

    private void createISRL10Ammo(AmmoType actualCreateISRL10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISRL10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISRL10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISRL10AmmoResult.svslots);
        assertEquals("", actualCreateISRL10AmmoResult.shortName);
        assertNull(actualCreateISRL10AmmoResult.modes);
        assertTrue(actualCreateISRL10AmmoResult.explosive);
        assertEquals(1, actualCreateISRL10AmmoResult.criticals);
        assertEquals(0.0, actualCreateISRL10AmmoResult.bv, 0.0);
        assertFalse(actualCreateISRL10AmmoResult.isSpreadable());
        assertFalse(actualCreateISRL10AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISRL10AmmoResult.isMixedTech());
        assertTrue(actualCreateISRL10AmmoResult.isHittable());
        assertFalse(actualCreateISRL10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISRL10AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISRL10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISRL10AmmoResult.getSubType());
        assertEquals("", actualCreateISRL10AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISRL10AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISRL10AmmoResult.getRulesRefs());
        assertEquals(1000.0, actualCreateISRL10AmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_ES, actualCreateISRL10AmmoResult.getPrototypeDate());
        assertEquals("IS Ammo RL-10", actualCreateISRL10AmmoResult.getInternalName());
        assertEquals("B/B-B-B-B", actualCreateISRL10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISRL10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISRL10AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISRL10AmmoResult.getExtinctionDate());
    }

    private void createISRL15Ammo(AmmoType actualCreateISRL15AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISRL15AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISRL15AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISRL15AmmoResult.svslots);
        assertEquals("", actualCreateISRL15AmmoResult.shortName);
        assertNull(actualCreateISRL15AmmoResult.modes);
        assertTrue(actualCreateISRL15AmmoResult.explosive);
        assertEquals(1, actualCreateISRL15AmmoResult.criticals);
        assertEquals(0.0, actualCreateISRL15AmmoResult.bv, 0.0);
        assertFalse(actualCreateISRL15AmmoResult.isSpreadable());
        assertFalse(actualCreateISRL15AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISRL15AmmoResult.isMixedTech());
        assertTrue(actualCreateISRL15AmmoResult.isHittable());
        assertFalse(actualCreateISRL15AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISRL15AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISRL15AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISRL15AmmoResult.getSubType());
        assertEquals("", actualCreateISRL15AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISRL15AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISRL15AmmoResult.getRulesRefs());
        assertEquals(1500.0, actualCreateISRL15AmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_ES, actualCreateISRL15AmmoResult.getPrototypeDate());
        assertEquals("IS Ammo RL-15", actualCreateISRL15AmmoResult.getInternalName());
        assertEquals("B/B-B-B-B", actualCreateISRL15AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISRL15AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISRL15AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISRL15AmmoResult.getExtinctionDate());
    }

    private void createISRL20Ammo(AmmoType actualCreateISRL20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISRL20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISRL20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISRL20AmmoResult.svslots);
        assertEquals("", actualCreateISRL20AmmoResult.shortName);
        assertNull(actualCreateISRL20AmmoResult.modes);
        assertTrue(actualCreateISRL20AmmoResult.explosive);
        assertEquals(1, actualCreateISRL20AmmoResult.criticals);
        assertEquals(0.0, actualCreateISRL20AmmoResult.bv, 0.0);
        assertFalse(actualCreateISRL20AmmoResult.isSpreadable());
        assertFalse(actualCreateISRL20AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISRL20AmmoResult.isMixedTech());
        assertTrue(actualCreateISRL20AmmoResult.isHittable());
        assertFalse(actualCreateISRL20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISRL20AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISRL20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISRL20AmmoResult.getSubType());
        assertEquals("", actualCreateISRL20AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISRL20AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISRL20AmmoResult.getRulesRefs());
        assertEquals(2000.0, actualCreateISRL20AmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_ES, actualCreateISRL20AmmoResult.getPrototypeDate());
        assertEquals("IS Ammo RL-20", actualCreateISRL20AmmoResult.getInternalName());
        assertEquals("B/B-B-B-B", actualCreateISRL20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISRL20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISRL20AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISRL20AmmoResult.getExtinctionDate());
    }

    private void createCLStreakSRM2Ammo(AmmoType actualCreateCLStreakSRM2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakSRM2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakSRM2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakSRM2AmmoResult.svslots);
        assertEquals("Streak SRM 2", actualCreateCLStreakSRM2AmmoResult.shortName);
        assertNull(actualCreateCLStreakSRM2AmmoResult.modes);
        assertTrue(actualCreateCLStreakSRM2AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakSRM2AmmoResult.criticals);
        assertEquals(5.0, actualCreateCLStreakSRM2AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakSRM2AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakSRM2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakSRM2AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakSRM2AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakSRM2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakSRM2AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakSRM2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakSRM2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLStreakSRM2AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateCLStreakSRM2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakSRM2AmmoResult.getReintroductionDate());
        assertEquals(54000.0, actualCreateCLStreakSRM2AmmoResult.getRawCost(), 0.0);
        assertEquals(2814, actualCreateCLStreakSRM2AmmoResult.getPrototypeDate());
        assertEquals("Streak SRM 2 Ammo", actualCreateCLStreakSRM2AmmoResult.getName());
        assertEquals("Clan Streak SRM 2 Ammo", actualCreateCLStreakSRM2AmmoResult.getInternalName());
        assertEquals("F/X-D-D-D", actualCreateCLStreakSRM2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakSRM2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakSRM2AmmoResult.getFlags());
    }

    private void createCLStreakSRM4Ammo(AmmoType actualCreateCLStreakSRM4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakSRM4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakSRM4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakSRM4AmmoResult.svslots);
        assertEquals("Streak SRM 4", actualCreateCLStreakSRM4AmmoResult.shortName);
        assertNull(actualCreateCLStreakSRM4AmmoResult.modes);
        assertTrue(actualCreateCLStreakSRM4AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakSRM4AmmoResult.criticals);
        assertEquals(10.0, actualCreateCLStreakSRM4AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakSRM4AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakSRM4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakSRM4AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakSRM4AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakSRM4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakSRM4AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakSRM4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakSRM4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLStreakSRM4AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateCLStreakSRM4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakSRM4AmmoResult.getReintroductionDate());
        assertEquals(54000.0, actualCreateCLStreakSRM4AmmoResult.getRawCost(), 0.0);
        assertEquals(2814, actualCreateCLStreakSRM4AmmoResult.getPrototypeDate());
        assertEquals("Streak SRM 4 Ammo", actualCreateCLStreakSRM4AmmoResult.getName());
        assertEquals("Clan Streak SRM 4 Ammo", actualCreateCLStreakSRM4AmmoResult.getInternalName());
        assertEquals("F/X-D-D-D", actualCreateCLStreakSRM4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakSRM4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakSRM4AmmoResult.getFlags());
    }

    private void createCLStreakSRM6Ammo(AmmoType actualCreateCLStreakSRM6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakSRM6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakSRM6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakSRM6AmmoResult.svslots);
        assertEquals("Streak SRM 6", actualCreateCLStreakSRM6AmmoResult.shortName);
        assertNull(actualCreateCLStreakSRM6AmmoResult.modes);
        assertTrue(actualCreateCLStreakSRM6AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakSRM6AmmoResult.criticals);
        assertEquals(15.0, actualCreateCLStreakSRM6AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakSRM6AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakSRM6AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakSRM6AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakSRM6AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakSRM6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakSRM6AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakSRM6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakSRM6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLStreakSRM6AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateCLStreakSRM6AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakSRM6AmmoResult.getReintroductionDate());
        assertEquals(54000.0, actualCreateCLStreakSRM6AmmoResult.getRawCost(), 0.0);
        assertEquals(2814, actualCreateCLStreakSRM6AmmoResult.getPrototypeDate());
        assertEquals("Streak SRM 6 Ammo", actualCreateCLStreakSRM6AmmoResult.getName());
        assertEquals("Clan Streak SRM 6 Ammo", actualCreateCLStreakSRM6AmmoResult.getInternalName());
        assertEquals("F/X-D-D-D", actualCreateCLStreakSRM6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakSRM6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakSRM6AmmoResult.getFlags());
    }

    private void createCLStreakSRM1Ammo(AmmoType actualCreateCLStreakSRM1AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakSRM1AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakSRM1AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakSRM1AmmoResult.svslots);
        assertEquals("Streak SRM 1", actualCreateCLStreakSRM1AmmoResult.shortName);
        assertNull(actualCreateCLStreakSRM1AmmoResult.modes);
        assertTrue(actualCreateCLStreakSRM1AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakSRM1AmmoResult.criticals);
        assertEquals(3.0, actualCreateCLStreakSRM1AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakSRM1AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakSRM1AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakSRM1AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakSRM1AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakSRM1AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakSRM1AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakSRM1AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakSRM1AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLStreakSRM1AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateCLStreakSRM1AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakSRM1AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLStreakSRM1AmmoResult.getRawCost(), 0.0);
        assertEquals(3055, actualCreateCLStreakSRM1AmmoResult.getPrototypeDate());
        assertEquals("Streak SRM 1 Ammo", actualCreateCLStreakSRM1AmmoResult.getName());
        assertEquals("Clan Streak SRM 1 Ammo", actualCreateCLStreakSRM1AmmoResult.getInternalName());
        assertEquals("F/X-X-D-D", actualCreateCLStreakSRM1AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakSRM1AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakSRM1AmmoResult.getFlags());
    }

    private void createCLStreakSRM3Ammo(AmmoType actualCreateCLStreakSRM3AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakSRM3AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakSRM3AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakSRM3AmmoResult.svslots);
        assertEquals("Streak SRM 3", actualCreateCLStreakSRM3AmmoResult.shortName);
        assertNull(actualCreateCLStreakSRM3AmmoResult.modes);
        assertTrue(actualCreateCLStreakSRM3AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakSRM3AmmoResult.criticals);
        assertEquals(7.0, actualCreateCLStreakSRM3AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakSRM3AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakSRM3AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakSRM3AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakSRM3AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakSRM3AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakSRM3AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakSRM3AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakSRM3AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLStreakSRM3AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateCLStreakSRM3AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakSRM3AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLStreakSRM3AmmoResult.getRawCost(), 0.0);
        assertEquals(3055, actualCreateCLStreakSRM3AmmoResult.getPrototypeDate());
        assertEquals("Streak SRM 3 Ammo", actualCreateCLStreakSRM3AmmoResult.getName());
        assertEquals("Clan Streak SRM 3 Ammo", actualCreateCLStreakSRM3AmmoResult.getInternalName());
        assertEquals("F/X-X-D-D", actualCreateCLStreakSRM3AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakSRM3AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakSRM3AmmoResult.getFlags());
    }

    private void createCLStreakSRM5Ammo(AmmoType actualCreateCLStreakSRM5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLStreakSRM5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLStreakSRM5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLStreakSRM5AmmoResult.svslots);
        assertEquals("Streak SRM 5", actualCreateCLStreakSRM5AmmoResult.shortName);
        assertNull(actualCreateCLStreakSRM5AmmoResult.modes);
        assertTrue(actualCreateCLStreakSRM5AmmoResult.explosive);
        assertEquals(1, actualCreateCLStreakSRM5AmmoResult.criticals);
        assertEquals(13.0, actualCreateCLStreakSRM5AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLStreakSRM5AmmoResult.isSpreadable());
        assertFalse(actualCreateCLStreakSRM5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLStreakSRM5AmmoResult.isMixedTech());
        assertTrue(actualCreateCLStreakSRM5AmmoResult.isHittable());
        assertFalse(actualCreateCLStreakSRM5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLStreakSRM5AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLStreakSRM5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLStreakSRM5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLStreakSRM5AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateCLStreakSRM5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLStreakSRM5AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLStreakSRM5AmmoResult.getRawCost(), 0.0);
        assertEquals(3055, actualCreateCLStreakSRM5AmmoResult.getPrototypeDate());
        assertEquals("Streak SRM 5 Ammo", actualCreateCLStreakSRM5AmmoResult.getName());
        assertEquals("Clan Streak SRM 5 Ammo", actualCreateCLStreakSRM5AmmoResult.getInternalName());
        assertEquals("F/X-X-D-D", actualCreateCLStreakSRM5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLStreakSRM5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLStreakSRM5AmmoResult.getFlags());
    }

    private void createISStreakSRM2Ammo(AmmoType actualCreateISStreakSRM2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISStreakSRM2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISStreakSRM2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISStreakSRM2AmmoResult.svslots);
        assertEquals("Streak SRM 2", actualCreateISStreakSRM2AmmoResult.shortName);
        assertNull(actualCreateISStreakSRM2AmmoResult.modes);
        assertTrue(actualCreateISStreakSRM2AmmoResult.explosive);
        assertEquals(1, actualCreateISStreakSRM2AmmoResult.criticals);
        assertEquals(4.0, actualCreateISStreakSRM2AmmoResult.bv, 0.0);
        assertFalse(actualCreateISStreakSRM2AmmoResult.isSpreadable());
        assertFalse(actualCreateISStreakSRM2AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISStreakSRM2AmmoResult.isMixedTech());
        assertTrue(actualCreateISStreakSRM2AmmoResult.isHittable());
        assertFalse(actualCreateISStreakSRM2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISStreakSRM2AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISStreakSRM2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISStreakSRM2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISStreakSRM2AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateISStreakSRM2AmmoResult.getRulesRefs());
        assertEquals(54000.0, actualCreateISStreakSRM2AmmoResult.getRawCost(), 0.0);
        assertEquals(2645, actualCreateISStreakSRM2AmmoResult.getPrototypeDate());
        assertEquals("Streak SRM 2 Ammo", actualCreateISStreakSRM2AmmoResult.getName());
        assertEquals("IS Streak SRM 2 Ammo", actualCreateISStreakSRM2AmmoResult.getInternalName());
        assertEquals("E/E-E(F)-D-D", actualCreateISStreakSRM2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISStreakSRM2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISStreakSRM2AmmoResult.getFlags());
    }

    private void createISStreakSRM4Ammo(AmmoType actualCreateISStreakSRM4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISStreakSRM4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISStreakSRM4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISStreakSRM4AmmoResult.svslots);
        assertEquals("Streak SRM 4", actualCreateISStreakSRM4AmmoResult.shortName);
        assertNull(actualCreateISStreakSRM4AmmoResult.modes);
        assertTrue(actualCreateISStreakSRM4AmmoResult.explosive);
        assertEquals(1, actualCreateISStreakSRM4AmmoResult.criticals);
        assertEquals(7.0, actualCreateISStreakSRM4AmmoResult.bv, 0.0);
        assertFalse(actualCreateISStreakSRM4AmmoResult.isSpreadable());
        assertFalse(actualCreateISStreakSRM4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISStreakSRM4AmmoResult.isMixedTech());
        assertTrue(actualCreateISStreakSRM4AmmoResult.isHittable());
        assertFalse(actualCreateISStreakSRM4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISStreakSRM4AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISStreakSRM4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISStreakSRM4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISStreakSRM4AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateISStreakSRM4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISStreakSRM4AmmoResult.getReintroductionDate());
        assertEquals(54000.0, actualCreateISStreakSRM4AmmoResult.getRawCost(), 0.0);
        assertEquals(3055, actualCreateISStreakSRM4AmmoResult.getPrototypeDate());
        assertEquals("Streak SRM 4 Ammo", actualCreateISStreakSRM4AmmoResult.getName());
        assertEquals("IS Streak SRM 4 Ammo", actualCreateISStreakSRM4AmmoResult.getInternalName());
        assertEquals("E/X-E-D-D", actualCreateISStreakSRM4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISStreakSRM4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISStreakSRM4AmmoResult.getFlags());
    }

    private void createISStreakSRM6Ammo(AmmoType actualCreateISStreakSRM6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISStreakSRM6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISStreakSRM6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISStreakSRM6AmmoResult.svslots);
        assertEquals("Streak SRM 6", actualCreateISStreakSRM6AmmoResult.shortName);
        assertNull(actualCreateISStreakSRM6AmmoResult.modes);
        assertTrue(actualCreateISStreakSRM6AmmoResult.explosive);
        assertEquals(1, actualCreateISStreakSRM6AmmoResult.criticals);
        assertEquals(11.0, actualCreateISStreakSRM6AmmoResult.bv, 0.0);
        assertFalse(actualCreateISStreakSRM6AmmoResult.isSpreadable());
        assertFalse(actualCreateISStreakSRM6AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISStreakSRM6AmmoResult.isMixedTech());
        assertTrue(actualCreateISStreakSRM6AmmoResult.isHittable());
        assertFalse(actualCreateISStreakSRM6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISStreakSRM6AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISStreakSRM6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISStreakSRM6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISStreakSRM6AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateISStreakSRM6AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISStreakSRM6AmmoResult.getReintroductionDate());
        assertEquals(54000.0, actualCreateISStreakSRM6AmmoResult.getRawCost(), 0.0);
        assertEquals(3055, actualCreateISStreakSRM6AmmoResult.getPrototypeDate());
        assertEquals("Streak SRM 6 Ammo", actualCreateISStreakSRM6AmmoResult.getName());
        assertEquals("IS Streak SRM 6 Ammo", actualCreateISStreakSRM6AmmoResult.getInternalName());
        assertEquals("E/X-E-D-D", actualCreateISStreakSRM6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISStreakSRM6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISStreakSRM6AmmoResult.getFlags());
    }

    private void createISNarcAmmo(AmmoType actualCreateISNarcAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISNarcAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISNarcAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISNarcAmmoResult.svslots);
        assertEquals("Narc", actualCreateISNarcAmmoResult.shortName);
        assertNull(actualCreateISNarcAmmoResult.modes);
        assertTrue(actualCreateISNarcAmmoResult.explosive);
        assertEquals(1, actualCreateISNarcAmmoResult.criticals);
        assertEquals(0.0, actualCreateISNarcAmmoResult.bv, 0.0);
        assertFalse(actualCreateISNarcAmmoResult.isSpreadable());
        assertFalse(actualCreateISNarcAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISNarcAmmoResult.isMixedTech());
        assertTrue(actualCreateISNarcAmmoResult.isHittable());
        assertFalse(actualCreateISNarcAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISNarcAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISNarcAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISNarcAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISNarcAmmoResult.getStaticTechLevel());
        assertEquals("141, TW", actualCreateISNarcAmmoResult.getRulesRefs());
        assertEquals(6000.0, actualCreateISNarcAmmoResult.getRawCost(), 0.0);
        assertEquals(2575, actualCreateISNarcAmmoResult.getPrototypeDate());
        assertEquals("Narc Pods", actualCreateISNarcAmmoResult.getName());
        assertEquals("ISNarc Pods", actualCreateISNarcAmmoResult.getInternalName());
        assertEquals("E/E-F(F*)-D-C", actualCreateISNarcAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISNarcAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISNarcAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISNarcAmmoResult.getExtinctionDate());
    }

    private void createISNarcExplosiveAmmo(AmmoType actualCreateISNarcExplosiveAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISNarcExplosiveAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISNarcExplosiveAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISNarcExplosiveAmmoResult.svslots);
        assertEquals("Narc Explosive", actualCreateISNarcExplosiveAmmoResult.shortName);
        assertNull(actualCreateISNarcExplosiveAmmoResult.modes);
        assertTrue(actualCreateISNarcExplosiveAmmoResult.explosive);
        assertEquals(1, actualCreateISNarcExplosiveAmmoResult.criticals);
        assertEquals(0.0, actualCreateISNarcExplosiveAmmoResult.bv, 0.0);
        assertFalse(actualCreateISNarcExplosiveAmmoResult.isSpreadable());
        assertFalse(actualCreateISNarcExplosiveAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISNarcExplosiveAmmoResult.isMixedTech());
        assertTrue(actualCreateISNarcExplosiveAmmoResult.isHittable());
        assertFalse(actualCreateISNarcExplosiveAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISNarcExplosiveAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISNarcExplosiveAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISNarcExplosiveAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISNarcExplosiveAmmoResult.getStaticTechLevel());
        assertEquals("141, TW", actualCreateISNarcExplosiveAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISNarcExplosiveAmmoResult.getReintroductionDate());
        assertEquals(1500.0, actualCreateISNarcExplosiveAmmoResult.getRawCost(), 0.0);
        assertEquals(3049, actualCreateISNarcExplosiveAmmoResult.getPrototypeDate());
        assertEquals("Narc Explosive Pods", actualCreateISNarcExplosiveAmmoResult.getName());
        assertEquals("ISNarc ExplosivePods", actualCreateISNarcExplosiveAmmoResult.getInternalName());
        assertEquals("E/X-X-D-D", actualCreateISNarcExplosiveAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISNarcExplosiveAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISNarcExplosiveAmmoResult.getFlags());
    }

    private void createCLNarcExplosiveAmmo(AmmoType actualCreateCLNarcExplosiveAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLNarcExplosiveAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLNarcExplosiveAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLNarcExplosiveAmmoResult.svslots);
        assertEquals("Narc Explosive", actualCreateCLNarcExplosiveAmmoResult.shortName);
        assertNull(actualCreateCLNarcExplosiveAmmoResult.modes);
        assertTrue(actualCreateCLNarcExplosiveAmmoResult.explosive);
        assertEquals(1, actualCreateCLNarcExplosiveAmmoResult.criticals);
        assertEquals(0.0, actualCreateCLNarcExplosiveAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLNarcExplosiveAmmoResult.isSpreadable());
        assertFalse(actualCreateCLNarcExplosiveAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLNarcExplosiveAmmoResult.isMixedTech());
        assertTrue(actualCreateCLNarcExplosiveAmmoResult.isHittable());
        assertFalse(actualCreateCLNarcExplosiveAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLNarcExplosiveAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLNarcExplosiveAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLNarcExplosiveAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateCLNarcExplosiveAmmoResult.getStaticTechLevel());
        assertEquals("141, TW", actualCreateCLNarcExplosiveAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLNarcExplosiveAmmoResult.getReintroductionDate());
        assertEquals(1500.0, actualCreateCLNarcExplosiveAmmoResult.getRawCost(), 0.0);
        assertEquals(3049, actualCreateCLNarcExplosiveAmmoResult.getPrototypeDate());
        assertEquals("Narc Explosive Pods", actualCreateCLNarcExplosiveAmmoResult.getName());
        assertEquals("CLNarc Explosive Pods", actualCreateCLNarcExplosiveAmmoResult.getInternalName());
        assertEquals("E/X-X-D-D", actualCreateCLNarcExplosiveAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLNarcExplosiveAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLNarcExplosiveAmmoResult.getFlags());
    }

    private void createISiNarcAmmo(AmmoType actualCreateISiNarcAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISiNarcAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISiNarcAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISiNarcAmmoResult.svslots);
        assertEquals("iNarc", actualCreateISiNarcAmmoResult.shortName);
        assertNull(actualCreateISiNarcAmmoResult.modes);
        assertTrue(actualCreateISiNarcAmmoResult.explosive);
        assertEquals(1, actualCreateISiNarcAmmoResult.criticals);
        assertEquals(0.0, actualCreateISiNarcAmmoResult.bv, 0.0);
        assertFalse(actualCreateISiNarcAmmoResult.isSpreadable());
        assertFalse(actualCreateISiNarcAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISiNarcAmmoResult.isMixedTech());
        assertTrue(actualCreateISiNarcAmmoResult.isHittable());
        assertFalse(actualCreateISiNarcAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISiNarcAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISiNarcAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISiNarcAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISiNarcAmmoResult.getStaticTechLevel());
        assertEquals("141, TW", actualCreateISiNarcAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISiNarcAmmoResult.getReintroductionDate());
        assertEquals(7500.0, actualCreateISiNarcAmmoResult.getRawCost(), 0.0);
        assertEquals(3049, actualCreateISiNarcAmmoResult.getPrototypeDate());
        assertEquals("iNarc Pods", actualCreateISiNarcAmmoResult.getName());
        assertEquals("ISiNarc Pods", actualCreateISiNarcAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISiNarcAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISiNarcAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISiNarcAmmoResult.getFlags());
    }

    private void createISiNarcECMAmmo(AmmoType actualCreateISiNarcECMAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISiNarcECMAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISiNarcECMAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISiNarcECMAmmoResult.svslots);
        assertEquals("iNarc ECM", actualCreateISiNarcECMAmmoResult.shortName);
        assertNull(actualCreateISiNarcECMAmmoResult.modes);
        assertTrue(actualCreateISiNarcECMAmmoResult.explosive);
        assertEquals(1, actualCreateISiNarcECMAmmoResult.criticals);
        assertEquals(0.0, actualCreateISiNarcECMAmmoResult.bv, 0.0);
        assertFalse(actualCreateISiNarcECMAmmoResult.isSpreadable());
        assertFalse(actualCreateISiNarcECMAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISiNarcECMAmmoResult.isMixedTech());
        assertTrue(actualCreateISiNarcECMAmmoResult.isHittable());
        assertFalse(actualCreateISiNarcECMAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISiNarcECMAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISiNarcECMAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISiNarcECMAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISiNarcECMAmmoResult.getStaticTechLevel());
        assertEquals("141, TW", actualCreateISiNarcECMAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISiNarcECMAmmoResult.getReintroductionDate());
        assertEquals(15000.0, actualCreateISiNarcECMAmmoResult.getRawCost(), 0.0);
        assertEquals(3049, actualCreateISiNarcECMAmmoResult.getPrototypeDate());
        assertEquals("iNarc ECM Pods", actualCreateISiNarcECMAmmoResult.getName());
        assertEquals("ISiNarc ECM Pods", actualCreateISiNarcECMAmmoResult.getInternalName());
        assertEquals("E/X-X-D-D", actualCreateISiNarcECMAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISiNarcECMAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISiNarcECMAmmoResult.getFlags());
    }

    private void createISiNarcExplosiveAmmo(AmmoType actualCreateISiNarcExplosiveAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISiNarcExplosiveAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISiNarcExplosiveAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISiNarcExplosiveAmmoResult.svslots);
        assertEquals("iNarc Explosive", actualCreateISiNarcExplosiveAmmoResult.shortName);
        assertNull(actualCreateISiNarcExplosiveAmmoResult.modes);
        assertTrue(actualCreateISiNarcExplosiveAmmoResult.explosive);
        assertEquals(1, actualCreateISiNarcExplosiveAmmoResult.criticals);
        assertEquals(0.0, actualCreateISiNarcExplosiveAmmoResult.bv, 0.0);
        assertFalse(actualCreateISiNarcExplosiveAmmoResult.isSpreadable());
        assertFalse(actualCreateISiNarcExplosiveAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISiNarcExplosiveAmmoResult.isMixedTech());
        assertTrue(actualCreateISiNarcExplosiveAmmoResult.isHittable());
        assertFalse(actualCreateISiNarcExplosiveAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISiNarcExplosiveAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISiNarcExplosiveAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISiNarcExplosiveAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISiNarcExplosiveAmmoResult.getStaticTechLevel());
        assertEquals("141, TW", actualCreateISiNarcExplosiveAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISiNarcExplosiveAmmoResult.getReintroductionDate());
        assertEquals(1500.0, actualCreateISiNarcExplosiveAmmoResult.getRawCost(), 0.0);
        assertEquals(3049, actualCreateISiNarcExplosiveAmmoResult.getPrototypeDate());
        assertEquals("iNarc Explosive Pods", actualCreateISiNarcExplosiveAmmoResult.getName());
        assertEquals("ISiNarc Explosive Pods", actualCreateISiNarcExplosiveAmmoResult.getInternalName());
        assertEquals("E/X-X-D-D", actualCreateISiNarcExplosiveAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISiNarcExplosiveAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISiNarcExplosiveAmmoResult.getFlags());
    }

    private void createISiNarcHaywireAmmo(AmmoType actualCreateISiNarcHaywireAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISiNarcHaywireAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISiNarcHaywireAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISiNarcHaywireAmmoResult.svslots);
        assertEquals("iNarc Haywire", actualCreateISiNarcHaywireAmmoResult.shortName);
        assertNull(actualCreateISiNarcHaywireAmmoResult.modes);
        assertTrue(actualCreateISiNarcHaywireAmmoResult.explosive);
        assertEquals(1, actualCreateISiNarcHaywireAmmoResult.criticals);
        assertEquals(0.0, actualCreateISiNarcHaywireAmmoResult.bv, 0.0);
        assertFalse(actualCreateISiNarcHaywireAmmoResult.isSpreadable());
        assertFalse(actualCreateISiNarcHaywireAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISiNarcHaywireAmmoResult.isMixedTech());
        assertTrue(actualCreateISiNarcHaywireAmmoResult.isHittable());
        assertFalse(actualCreateISiNarcHaywireAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISiNarcHaywireAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISiNarcHaywireAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISiNarcHaywireAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISiNarcHaywireAmmoResult.getStaticTechLevel());
        assertEquals("141, TW", actualCreateISiNarcHaywireAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISiNarcHaywireAmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateISiNarcHaywireAmmoResult.getRawCost(), 0.0);
        assertEquals(3049, actualCreateISiNarcHaywireAmmoResult.getPrototypeDate());
        assertEquals("iNarc Haywire Pods", actualCreateISiNarcHaywireAmmoResult.getName());
        assertEquals("ISiNarc Haywire Pods", actualCreateISiNarcHaywireAmmoResult.getInternalName());
        assertEquals("E/X-X-D-D", actualCreateISiNarcHaywireAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISiNarcHaywireAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISiNarcHaywireAmmoResult.getFlags());
    }

    private void createISiNarcNemesisAmmo(AmmoType actualCreateISiNarcNemesisAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISiNarcNemesisAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISiNarcNemesisAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISiNarcNemesisAmmoResult.svslots);
        assertEquals("iNarc Nemesis", actualCreateISiNarcNemesisAmmoResult.shortName);
        assertNull(actualCreateISiNarcNemesisAmmoResult.modes);
        assertTrue(actualCreateISiNarcNemesisAmmoResult.explosive);
        assertEquals(1, actualCreateISiNarcNemesisAmmoResult.criticals);
        assertEquals(0.0, actualCreateISiNarcNemesisAmmoResult.bv, 0.0);
        assertFalse(actualCreateISiNarcNemesisAmmoResult.isSpreadable());
        assertFalse(actualCreateISiNarcNemesisAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISiNarcNemesisAmmoResult.isMixedTech());
        assertTrue(actualCreateISiNarcNemesisAmmoResult.isHittable());
        assertFalse(actualCreateISiNarcNemesisAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISiNarcNemesisAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISiNarcNemesisAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISiNarcNemesisAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISiNarcNemesisAmmoResult.getStaticTechLevel());
        assertEquals("141, TW", actualCreateISiNarcNemesisAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISiNarcNemesisAmmoResult.getReintroductionDate());
        assertEquals(10000.0, actualCreateISiNarcNemesisAmmoResult.getRawCost(), 0.0);
        assertEquals(3049, actualCreateISiNarcNemesisAmmoResult.getPrototypeDate());
        assertEquals("iNarc Nemesis Pods", actualCreateISiNarcNemesisAmmoResult.getName());
        assertEquals("ISiNarc Nemesis Pods", actualCreateISiNarcNemesisAmmoResult.getInternalName());
        assertEquals("E/X-X-D-D", actualCreateISiNarcNemesisAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISiNarcNemesisAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISiNarcNemesisAmmoResult.getFlags());
    }

    private void createISLRT5Ammo(AmmoType actualCreateISLRT5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLRT5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLRT5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLRT5AmmoResult.svslots);
        assertEquals("LRT 5", actualCreateISLRT5AmmoResult.shortName);
        assertTrue(actualCreateISLRT5AmmoResult.explosive);
        assertEquals(1, actualCreateISLRT5AmmoResult.criticals);
        assertEquals(6.0, actualCreateISLRT5AmmoResult.bv, 0.0);
        assertFalse(actualCreateISLRT5AmmoResult.isSpreadable());
        assertFalse(actualCreateISLRT5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLRT5AmmoResult.isMixedTech());
        assertTrue(actualCreateISLRT5AmmoResult.isHittable());
        assertTrue(actualCreateISLRT5AmmoResult.hasModes());
        assertFalse(actualCreateISLRT5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLRT5AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISLRT5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLRT5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLRT5AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateISLRT5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLRT5AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISLRT5AmmoResult.getRawCost(), 0.0);
        assertEquals(2370, actualCreateISLRT5AmmoResult.getPrototypeDate());
        assertEquals("LRT 5 Ammo", actualCreateISLRT5AmmoResult.getName());
        assertEquals("IS Ammo LRTorpedo-5", actualCreateISLRT5AmmoResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISLRT5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLRT5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLRT5AmmoResult.getFlags());
    }

    private void createISLRT10Ammo(AmmoType actualCreateISLRT10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLRT10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLRT10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLRT10AmmoResult.svslots);
        assertEquals("LRT 10", actualCreateISLRT10AmmoResult.shortName);
        assertTrue(actualCreateISLRT10AmmoResult.explosive);
        assertEquals(1, actualCreateISLRT10AmmoResult.criticals);
        assertEquals(11.0, actualCreateISLRT10AmmoResult.bv, 0.0);
        assertFalse(actualCreateISLRT10AmmoResult.isSpreadable());
        assertFalse(actualCreateISLRT10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLRT10AmmoResult.isMixedTech());
        assertTrue(actualCreateISLRT10AmmoResult.isHittable());
        assertTrue(actualCreateISLRT10AmmoResult.hasModes());
        assertFalse(actualCreateISLRT10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLRT10AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISLRT10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLRT10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLRT10AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISLRT10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLRT10AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISLRT10AmmoResult.getRawCost(), 0.0);
        assertEquals(2365, actualCreateISLRT10AmmoResult.getPrototypeDate());
        assertEquals("LRT 10 Ammo", actualCreateISLRT10AmmoResult.getName());
        assertEquals("IS Ammo LRTorpedo-10", actualCreateISLRT10AmmoResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISLRT10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLRT10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLRT10AmmoResult.getFlags());
    }

    private void createISLRT15Ammo(AmmoType actualCreateISLRT15AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLRT15AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLRT15AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLRT15AmmoResult.svslots);
        assertEquals("LRT 15", actualCreateISLRT15AmmoResult.shortName);
        assertTrue(actualCreateISLRT15AmmoResult.explosive);
        assertEquals(1, actualCreateISLRT15AmmoResult.criticals);
        assertEquals(17.0, actualCreateISLRT15AmmoResult.bv, 0.0);
        assertFalse(actualCreateISLRT15AmmoResult.isSpreadable());
        assertFalse(actualCreateISLRT15AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLRT15AmmoResult.isMixedTech());
        assertTrue(actualCreateISLRT15AmmoResult.isHittable());
        assertTrue(actualCreateISLRT15AmmoResult.hasModes());
        assertFalse(actualCreateISLRT15AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLRT15AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISLRT15AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLRT15AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLRT15AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISLRT15AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLRT15AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISLRT15AmmoResult.getRawCost(), 0.0);
        assertEquals(2365, actualCreateISLRT15AmmoResult.getPrototypeDate());
        assertEquals("LRT 15 Ammo", actualCreateISLRT15AmmoResult.getName());
        assertEquals("IS Ammo LRTorpedo-15", actualCreateISLRT15AmmoResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISLRT15AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLRT15AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLRT15AmmoResult.getFlags());
    }

    private void createISLRT20Ammo(AmmoType actualCreateISLRT20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLRT20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLRT20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLRT20AmmoResult.svslots);
        assertEquals("LRT 20", actualCreateISLRT20AmmoResult.shortName);
        assertTrue(actualCreateISLRT20AmmoResult.explosive);
        assertEquals(1, actualCreateISLRT20AmmoResult.criticals);
        assertEquals(23.0, actualCreateISLRT20AmmoResult.bv, 0.0);
        assertFalse(actualCreateISLRT20AmmoResult.isSpreadable());
        assertFalse(actualCreateISLRT20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLRT20AmmoResult.isMixedTech());
        assertTrue(actualCreateISLRT20AmmoResult.isHittable());
        assertTrue(actualCreateISLRT20AmmoResult.hasModes());
        assertFalse(actualCreateISLRT20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLRT20AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISLRT20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLRT20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLRT20AmmoResult.getStaticTechLevel());
        assertEquals("229,TM", actualCreateISLRT20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLRT20AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISLRT20AmmoResult.getRawCost(), 0.0);
        assertEquals(2365, actualCreateISLRT20AmmoResult.getPrototypeDate());
        assertEquals("LRT 20 Ammo", actualCreateISLRT20AmmoResult.getName());
        assertEquals("IS Ammo LRTorpedo-20", actualCreateISLRT20AmmoResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISLRT20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLRT20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLRT20AmmoResult.getFlags());
    }

    private void createISSRT2Ammo(AmmoType actualCreateISSRT2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISSRT2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISSRT2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISSRT2AmmoResult.svslots);
        assertEquals("SRT 2", actualCreateISSRT2AmmoResult.shortName);
        assertNull(actualCreateISSRT2AmmoResult.modes);
        assertTrue(actualCreateISSRT2AmmoResult.explosive);
        assertEquals(1, actualCreateISSRT2AmmoResult.criticals);
        assertEquals(3.0, actualCreateISSRT2AmmoResult.bv, 0.0);
        assertFalse(actualCreateISSRT2AmmoResult.isSpreadable());
        assertFalse(actualCreateISSRT2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISSRT2AmmoResult.isMixedTech());
        assertTrue(actualCreateISSRT2AmmoResult.isHittable());
        assertFalse(actualCreateISSRT2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISSRT2AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISSRT2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISSRT2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISSRT2AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateISSRT2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISSRT2AmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateISSRT2AmmoResult.getRawCost(), 0.0);
        assertEquals(2365, actualCreateISSRT2AmmoResult.getPrototypeDate());
        assertEquals("SRT 2 Ammo", actualCreateISSRT2AmmoResult.getName());
        assertEquals("IS Ammo SRTorpedo-2", actualCreateISSRT2AmmoResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISSRT2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISSRT2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISSRT2AmmoResult.getFlags());
    }

    private void createISSRT4Ammo(AmmoType actualCreateISSRT4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISSRT4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISSRT4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISSRT4AmmoResult.svslots);
        assertEquals("SRT 4", actualCreateISSRT4AmmoResult.shortName);
        assertNull(actualCreateISSRT4AmmoResult.modes);
        assertTrue(actualCreateISSRT4AmmoResult.explosive);
        assertEquals(1, actualCreateISSRT4AmmoResult.criticals);
        assertEquals(5.0, actualCreateISSRT4AmmoResult.bv, 0.0);
        assertFalse(actualCreateISSRT4AmmoResult.isSpreadable());
        assertFalse(actualCreateISSRT4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISSRT4AmmoResult.isMixedTech());
        assertTrue(actualCreateISSRT4AmmoResult.isHittable());
        assertFalse(actualCreateISSRT4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISSRT4AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISSRT4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISSRT4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISSRT4AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateISSRT4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISSRT4AmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateISSRT4AmmoResult.getRawCost(), 0.0);
        assertEquals(2365, actualCreateISSRT4AmmoResult.getPrototypeDate());
        assertEquals("SRT 4 Ammo", actualCreateISSRT4AmmoResult.getName());
        assertEquals("IS Ammo SRTorpedo-4", actualCreateISSRT4AmmoResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISSRT4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISSRT4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISSRT4AmmoResult.getFlags());
    }

    private void createISSRT6Ammo(AmmoType actualCreateISSRT6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISSRT6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISSRT6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISSRT6AmmoResult.svslots);
        assertEquals("SRT 6", actualCreateISSRT6AmmoResult.shortName);
        assertNull(actualCreateISSRT6AmmoResult.modes);
        assertTrue(actualCreateISSRT6AmmoResult.explosive);
        assertEquals(1, actualCreateISSRT6AmmoResult.criticals);
        assertEquals(7.0, actualCreateISSRT6AmmoResult.bv, 0.0);
        assertFalse(actualCreateISSRT6AmmoResult.isSpreadable());
        assertFalse(actualCreateISSRT6AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISSRT6AmmoResult.isMixedTech());
        assertTrue(actualCreateISSRT6AmmoResult.isHittable());
        assertFalse(actualCreateISSRT6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISSRT6AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISSRT6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISSRT6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISSRT6AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateISSRT6AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISSRT6AmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateISSRT6AmmoResult.getRawCost(), 0.0);
        assertEquals(2365, actualCreateISSRT6AmmoResult.getPrototypeDate());
        assertEquals("SRT 6 Ammo", actualCreateISSRT6AmmoResult.getName());
        assertEquals("IS Ammo SRTorpedo-6", actualCreateISSRT6AmmoResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISSRT6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISSRT6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISSRT6AmmoResult.getFlags());
    }

    private void createCLLRT1Ammo(AmmoType actualCreateCLLRT1AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT1AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT1AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT1AmmoResult.svslots);
        assertEquals("LRT 1", actualCreateCLLRT1AmmoResult.shortName);
        assertNull(actualCreateCLLRT1AmmoResult.modes);
        assertTrue(actualCreateCLLRT1AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT1AmmoResult.criticals);
        assertEquals(2.0, actualCreateCLLRT1AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT1AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT1AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT1AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT1AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT1AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT1AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT1AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT1AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT1AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT1AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT1AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT1AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT1AmmoResult.getPrototypeDate());
        assertEquals("LRT 1 Ammo", actualCreateCLLRT1AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-1", actualCreateCLLRT1AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT1AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT1AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT1AmmoResult.getFlags());
    }

    private void createCLLRT2Ammo(AmmoType actualCreateCLLRT2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT2AmmoResult.svslots);
        assertEquals("LRT 2", actualCreateCLLRT2AmmoResult.shortName);
        assertNull(actualCreateCLLRT2AmmoResult.modes);
        assertTrue(actualCreateCLLRT2AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT2AmmoResult.criticals);
        assertEquals(3.0, actualCreateCLLRT2AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT2AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT2AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT2AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT2AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT2AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT2AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT2AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT2AmmoResult.getPrototypeDate());
        assertEquals("LRT 2 Ammo", actualCreateCLLRT2AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-2", actualCreateCLLRT2AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT2AmmoResult.getFlags());
    }

    private void createCLLRT3Ammo(AmmoType actualCreateCLLRT3AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT3AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT3AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT3AmmoResult.svslots);
        assertEquals("LRT 3", actualCreateCLLRT3AmmoResult.shortName);
        assertNull(actualCreateCLLRT3AmmoResult.modes);
        assertTrue(actualCreateCLLRT3AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT3AmmoResult.criticals);
        assertEquals(5.0, actualCreateCLLRT3AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT3AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT3AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT3AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT3AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT3AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT3AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT3AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT3AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT3AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT3AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT3AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT3AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT3AmmoResult.getPrototypeDate());
        assertEquals("LRT 3 Ammo", actualCreateCLLRT3AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-3", actualCreateCLLRT3AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT3AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT3AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT3AmmoResult.getFlags());
    }

    private void createCLLRT4Ammo(AmmoType actualCreateCLLRT4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT4AmmoResult.svslots);
        assertEquals("LRT 4", actualCreateCLLRT4AmmoResult.shortName);
        assertNull(actualCreateCLLRT4AmmoResult.modes);
        assertTrue(actualCreateCLLRT4AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT4AmmoResult.criticals);
        assertEquals(6.0, actualCreateCLLRT4AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT4AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT4AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT4AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT4AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT4AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT4AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT4AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT4AmmoResult.getPrototypeDate());
        assertEquals("LRT 4 Ammo", actualCreateCLLRT4AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-4", actualCreateCLLRT4AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT4AmmoResult.getFlags());
    }

    private void createCLLRT5Ammo(AmmoType actualCreateCLLRT5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT5AmmoResult.svslots);
        assertEquals("LRT 5", actualCreateCLLRT5AmmoResult.shortName);
        assertNull(actualCreateCLLRT5AmmoResult.modes);
        assertTrue(actualCreateCLLRT5AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT5AmmoResult.criticals);
        assertEquals(7.0, actualCreateCLLRT5AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT5AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT5AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT5AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT5AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLLRT5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT5AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateCLLRT5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT5AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLLRT5AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLLRT5AmmoResult.getPrototypeDate());
        assertEquals("LRT 5 Ammo", actualCreateCLLRT5AmmoResult.getName());
        assertEquals("Clan Ammo LRTorpedo-5", actualCreateCLLRT5AmmoResult.getInternalName());
        assertEquals("C/X-D-C-C", actualCreateCLLRT5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT5AmmoResult.getFlags());
    }

    private void createCLLRT6Ammo(AmmoType actualCreateCLLRT6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT6AmmoResult.svslots);
        assertEquals("LRT 6", actualCreateCLLRT6AmmoResult.shortName);
        assertNull(actualCreateCLLRT6AmmoResult.modes);
        assertTrue(actualCreateCLLRT6AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT6AmmoResult.criticals);
        assertEquals(9.0, actualCreateCLLRT6AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT6AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT6AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT6AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT6AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT6AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT6AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT6AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT6AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT6AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT6AmmoResult.getPrototypeDate());
        assertEquals("LRT 6 Ammo", actualCreateCLLRT6AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-6", actualCreateCLLRT6AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT6AmmoResult.getFlags());
    }

    private void createCLLRT7Ammo(AmmoType actualCreateCLLRT7AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT7AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT7AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT7AmmoResult.svslots);
        assertEquals("LRT 7", actualCreateCLLRT7AmmoResult.shortName);
        assertNull(actualCreateCLLRT7AmmoResult.modes);
        assertTrue(actualCreateCLLRT7AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT7AmmoResult.criticals);
        assertEquals(10.0, actualCreateCLLRT7AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT7AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT7AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT7AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT7AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT7AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT7AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT7AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT7AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT7AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT7AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT7AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT7AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT7AmmoResult.getPrototypeDate());
        assertEquals("LRT 7 Ammo", actualCreateCLLRT7AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-7", actualCreateCLLRT7AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT7AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT7AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT7AmmoResult.getFlags());
    }

    private void createCLLRT8Ammo(AmmoType actualCreateCLLRT8AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT8AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT8AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT8AmmoResult.svslots);
        assertEquals("LRT 8", actualCreateCLLRT8AmmoResult.shortName);
        assertNull(actualCreateCLLRT8AmmoResult.modes);
        assertTrue(actualCreateCLLRT8AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT8AmmoResult.criticals);
        assertEquals(11.0, actualCreateCLLRT8AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT8AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT8AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT8AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT8AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT8AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT8AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT8AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT8AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT8AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT8AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT8AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT8AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT8AmmoResult.getPrototypeDate());
        assertEquals("LRT 8 Ammo", actualCreateCLLRT8AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-8", actualCreateCLLRT8AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT8AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT8AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT8AmmoResult.getFlags());
    }

    private void createCLLRT9Ammo(AmmoType actualCreateCLLRT9AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT9AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT9AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT9AmmoResult.svslots);
        assertEquals("LRT 9", actualCreateCLLRT9AmmoResult.shortName);
        assertNull(actualCreateCLLRT9AmmoResult.modes);
        assertTrue(actualCreateCLLRT9AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT9AmmoResult.criticals);
        assertEquals(12.0, actualCreateCLLRT9AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT9AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT9AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT9AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT9AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT9AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT9AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT9AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT9AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT9AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT9AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT9AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT9AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT9AmmoResult.getPrototypeDate());
        assertEquals("LRT 9 Ammo", actualCreateCLLRT9AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-9", actualCreateCLLRT9AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT9AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT9AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT9AmmoResult.getFlags());
    }

    private void createCLLRT10Ammo(AmmoType actualCreateCLLRT10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT10AmmoResult.svslots);
        assertEquals("LRT 10", actualCreateCLLRT10AmmoResult.shortName);
        assertNull(actualCreateCLLRT10AmmoResult.modes);
        assertTrue(actualCreateCLLRT10AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT10AmmoResult.criticals);
        assertEquals(14.0, actualCreateCLLRT10AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT10AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT10AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT10AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT10AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLLRT10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT10AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateCLLRT10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT10AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLLRT10AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLLRT10AmmoResult.getPrototypeDate());
        assertEquals("LRT 10 Ammo", actualCreateCLLRT10AmmoResult.getName());
        assertEquals("Clan Ammo LRTorpedo-10", actualCreateCLLRT10AmmoResult.getInternalName());
        assertEquals("C/X-D-C-C", actualCreateCLLRT10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT10AmmoResult.getFlags());
    }

    private void createCLLRT11Ammo(AmmoType actualCreateCLLRT11AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT11AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT11AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT11AmmoResult.svslots);
        assertEquals("LRT 11", actualCreateCLLRT11AmmoResult.shortName);
        assertNull(actualCreateCLLRT11AmmoResult.modes);
        assertTrue(actualCreateCLLRT11AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT11AmmoResult.criticals);
        assertEquals(18.0, actualCreateCLLRT11AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT11AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT11AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT11AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT11AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT11AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT11AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT11AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT11AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT11AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT11AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT11AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT11AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT11AmmoResult.getPrototypeDate());
        assertEquals("LRT 11 Ammo", actualCreateCLLRT11AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-11", actualCreateCLLRT11AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT11AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT11AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT11AmmoResult.getFlags());
    }

    private void createCLLRT12Ammo(AmmoType actualCreateCLLRT12AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT12AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT12AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT12AmmoResult.svslots);
        assertEquals("LRT 12", actualCreateCLLRT12AmmoResult.shortName);
        assertNull(actualCreateCLLRT12AmmoResult.modes);
        assertTrue(actualCreateCLLRT12AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT12AmmoResult.criticals);
        assertEquals(18.0, actualCreateCLLRT12AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT12AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT12AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT12AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT12AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT12AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT12AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT12AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT12AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT12AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT12AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT12AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT12AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT12AmmoResult.getPrototypeDate());
        assertEquals("LRT 12 Ammo", actualCreateCLLRT12AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-12", actualCreateCLLRT12AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT12AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT12AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT12AmmoResult.getFlags());
    }

    private void createCLLRT13Ammo(AmmoType actualCreateCLLRT13AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT13AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT13AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT13AmmoResult.svslots);
        assertEquals("LRT 13", actualCreateCLLRT13AmmoResult.shortName);
        assertNull(actualCreateCLLRT13AmmoResult.modes);
        assertTrue(actualCreateCLLRT13AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT13AmmoResult.criticals);
        assertEquals(20.0, actualCreateCLLRT13AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT13AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT13AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT13AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT13AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT13AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT13AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT13AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT13AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT13AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT13AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT13AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT13AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT13AmmoResult.getPrototypeDate());
        assertEquals("LRT 13 Ammo", actualCreateCLLRT13AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-13", actualCreateCLLRT13AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT13AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT13AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT13AmmoResult.getFlags());
    }

    private void createCLLRT14Ammo(AmmoType actualCreateCLLRT14AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT14AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT14AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT14AmmoResult.svslots);
        assertEquals("LRT 14", actualCreateCLLRT14AmmoResult.shortName);
        assertNull(actualCreateCLLRT14AmmoResult.modes);
        assertTrue(actualCreateCLLRT14AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT14AmmoResult.criticals);
        assertEquals(21.0, actualCreateCLLRT14AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT14AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT14AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT14AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT14AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT14AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT14AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT14AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT14AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT14AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT14AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT14AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT14AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT14AmmoResult.getPrototypeDate());
        assertEquals("LRT 14 Ammo", actualCreateCLLRT14AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-14", actualCreateCLLRT14AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT14AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT14AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT14AmmoResult.getFlags());
    }

    private void createCLLRT15Ammo(AmmoType actualCreateCLLRT15AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT15AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT15AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT15AmmoResult.svslots);
        assertEquals("LRT 15", actualCreateCLLRT15AmmoResult.shortName);
        assertNull(actualCreateCLLRT15AmmoResult.modes);
        assertTrue(actualCreateCLLRT15AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT15AmmoResult.criticals);
        assertEquals(21.0, actualCreateCLLRT15AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT15AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT15AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT15AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT15AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT15AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT15AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLLRT15AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT15AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT15AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateCLLRT15AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT15AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLLRT15AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLLRT15AmmoResult.getPrototypeDate());
        assertEquals("LRT 15 Ammo", actualCreateCLLRT15AmmoResult.getName());
        assertEquals("Clan Ammo LRTorpedo-15", actualCreateCLLRT15AmmoResult.getInternalName());
        assertEquals("C/X-D-C-C", actualCreateCLLRT15AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT15AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT15AmmoResult.getFlags());
    }

    private void createCLLRT16Ammo(AmmoType actualCreateCLLRT16AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT16AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT16AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT16AmmoResult.svslots);
        assertEquals("LRT 16", actualCreateCLLRT16AmmoResult.shortName);
        assertNull(actualCreateCLLRT16AmmoResult.modes);
        assertTrue(actualCreateCLLRT16AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT16AmmoResult.criticals);
        assertEquals(27.0, actualCreateCLLRT16AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT16AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT16AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT16AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT16AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT16AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT16AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT16AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT16AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT16AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT16AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT16AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT16AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT16AmmoResult.getPrototypeDate());
        assertEquals("LRT 16 Ammo", actualCreateCLLRT16AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-16", actualCreateCLLRT16AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT16AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT16AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT16AmmoResult.getFlags());
    }

    private void createCLLRT17Ammo(AmmoType actualCreateCLLRT17AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT17AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT17AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT17AmmoResult.svslots);
        assertEquals("LRT 17", actualCreateCLLRT17AmmoResult.shortName);
        assertNull(actualCreateCLLRT17AmmoResult.modes);
        assertTrue(actualCreateCLLRT17AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT17AmmoResult.criticals);
        assertEquals(27.0, actualCreateCLLRT17AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT17AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT17AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT17AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT17AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT17AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT17AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT17AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT17AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT17AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT17AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT17AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT17AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT17AmmoResult.getPrototypeDate());
        assertEquals("LRT 17 Ammo", actualCreateCLLRT17AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-17", actualCreateCLLRT17AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT17AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT17AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT17AmmoResult.getFlags());
    }

    private void createCLLRT18Ammo(AmmoType actualCreateCLLRT18AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT18AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT18AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT18AmmoResult.svslots);
        assertEquals("LRT 18", actualCreateCLLRT18AmmoResult.shortName);
        assertNull(actualCreateCLLRT18AmmoResult.modes);
        assertTrue(actualCreateCLLRT18AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT18AmmoResult.criticals);
        assertEquals(27.0, actualCreateCLLRT18AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT18AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT18AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT18AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT18AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT18AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT18AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT18AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT18AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT18AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT18AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT18AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT18AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT18AmmoResult.getPrototypeDate());
        assertEquals("LRT 18 Ammo", actualCreateCLLRT18AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-18", actualCreateCLLRT18AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT18AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT18AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT18AmmoResult.getFlags());
    }

    private void createCLLRT19Ammo(AmmoType actualCreateCLLRT19AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT19AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT19AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT19AmmoResult.svslots);
        assertEquals("LRT 19", actualCreateCLLRT19AmmoResult.shortName);
        assertNull(actualCreateCLLRT19AmmoResult.modes);
        assertTrue(actualCreateCLLRT19AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT19AmmoResult.criticals);
        assertEquals(27.0, actualCreateCLLRT19AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT19AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT19AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT19AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT19AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT19AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT19AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLLRT19AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT19AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT19AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLLRT19AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT19AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLLRT19AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLLRT19AmmoResult.getPrototypeDate());
        assertEquals("LRT 19 Ammo", actualCreateCLLRT19AmmoResult.getName());
        assertEquals("Clan Ammo Protomech LRTorpedo-19", actualCreateCLLRT19AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLLRT19AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT19AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT19AmmoResult.getFlags());
    }

    private void createCLLRT20Ammo(AmmoType actualCreateCLLRT20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLLRT20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLLRT20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLLRT20AmmoResult.svslots);
        assertEquals("LRT 20", actualCreateCLLRT20AmmoResult.shortName);
        assertNull(actualCreateCLLRT20AmmoResult.modes);
        assertTrue(actualCreateCLLRT20AmmoResult.explosive);
        assertEquals(1, actualCreateCLLRT20AmmoResult.criticals);
        assertEquals(27.0, actualCreateCLLRT20AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLLRT20AmmoResult.isSpreadable());
        assertFalse(actualCreateCLLRT20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLLRT20AmmoResult.isMixedTech());
        assertTrue(actualCreateCLLRT20AmmoResult.isHittable());
        assertFalse(actualCreateCLLRT20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLLRT20AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLLRT20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLLRT20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLLRT20AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateCLLRT20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLLRT20AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLLRT20AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLLRT20AmmoResult.getPrototypeDate());
        assertEquals("LRT 20 Ammo", actualCreateCLLRT20AmmoResult.getName());
        assertEquals("Clan Ammo LRTorpedo-20", actualCreateCLLRT20AmmoResult.getInternalName());
        assertEquals("C/X-D-C-C", actualCreateCLLRT20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLLRT20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLLRT20AmmoResult.getFlags());
    }

    private void createCLSRT1Ammo(AmmoType actualCreateCLSRT1AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLSRT1AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLSRT1AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLSRT1AmmoResult.svslots);
        assertEquals("SRT 1", actualCreateCLSRT1AmmoResult.shortName);
        assertNull(actualCreateCLSRT1AmmoResult.modes);
        assertTrue(actualCreateCLSRT1AmmoResult.explosive);
        assertEquals(1, actualCreateCLSRT1AmmoResult.criticals);
        assertEquals(2.0, actualCreateCLSRT1AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLSRT1AmmoResult.isSpreadable());
        assertFalse(actualCreateCLSRT1AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLSRT1AmmoResult.isMixedTech());
        assertTrue(actualCreateCLSRT1AmmoResult.isHittable());
        assertFalse(actualCreateCLSRT1AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLSRT1AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLSRT1AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLSRT1AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLSRT1AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLSRT1AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLSRT1AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLSRT1AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLSRT1AmmoResult.getPrototypeDate());
        assertEquals("SRT 1 Ammo", actualCreateCLSRT1AmmoResult.getName());
        assertEquals("Clan Ammo SRTorpedo-1", actualCreateCLSRT1AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLSRT1AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLSRT1AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLSRT1AmmoResult.getFlags());
    }

    private void createCLSRT2Ammo(AmmoType actualCreateCLSRT2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLSRT2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLSRT2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLSRT2AmmoResult.svslots);
        assertEquals("SRT 2", actualCreateCLSRT2AmmoResult.shortName);
        assertNull(actualCreateCLSRT2AmmoResult.modes);
        assertTrue(actualCreateCLSRT2AmmoResult.explosive);
        assertEquals(1, actualCreateCLSRT2AmmoResult.criticals);
        assertEquals(3.0, actualCreateCLSRT2AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLSRT2AmmoResult.isSpreadable());
        assertFalse(actualCreateCLSRT2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLSRT2AmmoResult.isMixedTech());
        assertTrue(actualCreateCLSRT2AmmoResult.isHittable());
        assertFalse(actualCreateCLSRT2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLSRT2AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLSRT2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLSRT2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLSRT2AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateCLSRT2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLSRT2AmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateCLSRT2AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLSRT2AmmoResult.getPrototypeDate());
        assertEquals("SRT 2 Ammo", actualCreateCLSRT2AmmoResult.getName());
        assertEquals("Clan Ammo SRTorpedo-2", actualCreateCLSRT2AmmoResult.getInternalName());
        assertEquals("C/X-C-C-C", actualCreateCLSRT2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLSRT2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLSRT2AmmoResult.getFlags());
    }

    private void createCLSRT3Ammo(AmmoType actualCreateCLSRT3AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLSRT3AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLSRT3AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLSRT3AmmoResult.svslots);
        assertEquals("SRT 3", actualCreateCLSRT3AmmoResult.shortName);
        assertNull(actualCreateCLSRT3AmmoResult.modes);
        assertTrue(actualCreateCLSRT3AmmoResult.explosive);
        assertEquals(1, actualCreateCLSRT3AmmoResult.criticals);
        assertEquals(4.0, actualCreateCLSRT3AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLSRT3AmmoResult.isSpreadable());
        assertFalse(actualCreateCLSRT3AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLSRT3AmmoResult.isMixedTech());
        assertTrue(actualCreateCLSRT3AmmoResult.isHittable());
        assertFalse(actualCreateCLSRT3AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLSRT3AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLSRT3AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLSRT3AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLSRT3AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLSRT3AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLSRT3AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLSRT3AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLSRT3AmmoResult.getPrototypeDate());
        assertEquals("SRT 3 Ammo", actualCreateCLSRT3AmmoResult.getName());
        assertEquals("Clan Ammo SRTorpedo-3", actualCreateCLSRT3AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLSRT3AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLSRT3AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLSRT3AmmoResult.getFlags());
    }

    private void createCLSRT4Ammo(AmmoType actualCreateCLSRT4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLSRT4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLSRT4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLSRT4AmmoResult.svslots);
        assertEquals("SRT 4", actualCreateCLSRT4AmmoResult.shortName);
        assertNull(actualCreateCLSRT4AmmoResult.modes);
        assertTrue(actualCreateCLSRT4AmmoResult.explosive);
        assertEquals(1, actualCreateCLSRT4AmmoResult.criticals);
        assertEquals(5.0, actualCreateCLSRT4AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLSRT4AmmoResult.isSpreadable());
        assertFalse(actualCreateCLSRT4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLSRT4AmmoResult.isMixedTech());
        assertTrue(actualCreateCLSRT4AmmoResult.isHittable());
        assertFalse(actualCreateCLSRT4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLSRT4AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLSRT4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLSRT4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLSRT4AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateCLSRT4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLSRT4AmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateCLSRT4AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLSRT4AmmoResult.getPrototypeDate());
        assertEquals("SRT 4 Ammo", actualCreateCLSRT4AmmoResult.getName());
        assertEquals("Clan Ammo SRTorpedo-4", actualCreateCLSRT4AmmoResult.getInternalName());
        assertEquals("C/X-C-C-C", actualCreateCLSRT4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLSRT4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLSRT4AmmoResult.getFlags());
    }

    private void createCLSRT5Ammo(AmmoType actualCreateCLSRT5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLSRT5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLSRT5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLSRT5AmmoResult.svslots);
        assertEquals("SRT 5", actualCreateCLSRT5AmmoResult.shortName);
        assertNull(actualCreateCLSRT5AmmoResult.modes);
        assertTrue(actualCreateCLSRT5AmmoResult.explosive);
        assertEquals(1, actualCreateCLSRT5AmmoResult.criticals);
        assertEquals(6.0, actualCreateCLSRT5AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLSRT5AmmoResult.isSpreadable());
        assertFalse(actualCreateCLSRT5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLSRT5AmmoResult.isMixedTech());
        assertTrue(actualCreateCLSRT5AmmoResult.isHittable());
        assertFalse(actualCreateCLSRT5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLSRT5AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLSRT5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLSRT5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLSRT5AmmoResult.getStaticTechLevel());
        assertEquals("231,TM", actualCreateCLSRT5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLSRT5AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLSRT5AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLSRT5AmmoResult.getPrototypeDate());
        assertEquals("SRT 5 Ammo", actualCreateCLSRT5AmmoResult.getName());
        assertEquals("Clan Ammo SRTorpedo-5", actualCreateCLSRT5AmmoResult.getInternalName());
        assertEquals("F/X-X-C-C", actualCreateCLSRT5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLSRT5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLSRT5AmmoResult.getFlags());
    }

    private void createCLSRT6Ammo(AmmoType actualCreateCLSRT6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLSRT6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLSRT6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLSRT6AmmoResult.svslots);
        assertEquals("SRT 6", actualCreateCLSRT6AmmoResult.shortName);
        assertNull(actualCreateCLSRT6AmmoResult.modes);
        assertTrue(actualCreateCLSRT6AmmoResult.explosive);
        assertEquals(1, actualCreateCLSRT6AmmoResult.criticals);
        assertEquals(7.0, actualCreateCLSRT6AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLSRT6AmmoResult.isSpreadable());
        assertFalse(actualCreateCLSRT6AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLSRT6AmmoResult.isMixedTech());
        assertTrue(actualCreateCLSRT6AmmoResult.isHittable());
        assertFalse(actualCreateCLSRT6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLSRT6AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLSRT6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLSRT6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLSRT6AmmoResult.getStaticTechLevel());
        assertEquals("230,TM", actualCreateCLSRT6AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLSRT6AmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateCLSRT6AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLSRT6AmmoResult.getPrototypeDate());
        assertEquals("SRT 6 Ammo", actualCreateCLSRT6AmmoResult.getName());
        assertEquals("Clan Ammo SRTorpedo-6", actualCreateCLSRT6AmmoResult.getInternalName());
        assertEquals("C/X-C-C-C", actualCreateCLSRT6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLSRT6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLSRT6AmmoResult.getFlags());
    }

    private void createISAPMortar1Ammo(AmmoType actualCreateISAPMortar1AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAPMortar1AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAPMortar1AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAPMortar1AmmoResult.svslots);
        assertEquals("Mortar SC 1", actualCreateISAPMortar1AmmoResult.shortName);
        assertNull(actualCreateISAPMortar1AmmoResult.modes);
        assertTrue(actualCreateISAPMortar1AmmoResult.explosive);
        assertEquals(1, actualCreateISAPMortar1AmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_CLAN_FF, actualCreateISAPMortar1AmmoResult.bv, 0.0);
        assertFalse(actualCreateISAPMortar1AmmoResult.isSpreadable());
        assertFalse(actualCreateISAPMortar1AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISAPMortar1AmmoResult.isMixedTech());
        assertTrue(actualCreateISAPMortar1AmmoResult.isHittable());
        assertFalse(actualCreateISAPMortar1AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAPMortar1AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISAPMortar1AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAPMortar1AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISAPMortar1AmmoResult.getStaticTechLevel());
        assertEquals("324,TO", actualCreateISAPMortar1AmmoResult.getRulesRefs());
        assertEquals(28000.0, actualCreateISAPMortar1AmmoResult.getRawCost(), 0.0);
        assertEquals(2521, actualCreateISAPMortar1AmmoResult.getPrototypeDate());
        assertEquals("Shaped Charge Mortar 1 Ammo", actualCreateISAPMortar1AmmoResult.getName());
        assertEquals("IS Ammo SC Mortar-1", actualCreateISAPMortar1AmmoResult.getInternalName());
        assertEquals("B/D-F(F*)-F-E", actualCreateISAPMortar1AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAPMortar1AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAPMortar1AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISAPMortar1AmmoResult.getExtinctionDate());
    }

    private void createISAPMortar2Ammo(AmmoType actualCreateISAPMortar2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAPMortar2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAPMortar2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAPMortar2AmmoResult.svslots);
        assertEquals("Mortar SC 2", actualCreateISAPMortar2AmmoResult.shortName);
        assertNull(actualCreateISAPMortar2AmmoResult.modes);
        assertTrue(actualCreateISAPMortar2AmmoResult.explosive);
        assertEquals(1, actualCreateISAPMortar2AmmoResult.criticals);
        assertEquals(2.4, actualCreateISAPMortar2AmmoResult.bv, 0.0);
        assertFalse(actualCreateISAPMortar2AmmoResult.isSpreadable());
        assertFalse(actualCreateISAPMortar2AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISAPMortar2AmmoResult.isMixedTech());
        assertTrue(actualCreateISAPMortar2AmmoResult.isHittable());
        assertFalse(actualCreateISAPMortar2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAPMortar2AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISAPMortar2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAPMortar2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISAPMortar2AmmoResult.getStaticTechLevel());
        assertEquals("324,TO", actualCreateISAPMortar2AmmoResult.getRulesRefs());
        assertEquals(28000.0, actualCreateISAPMortar2AmmoResult.getRawCost(), 0.0);
        assertEquals(2521, actualCreateISAPMortar2AmmoResult.getPrototypeDate());
        assertEquals("Shaped Charge Mortar 2 Ammo", actualCreateISAPMortar2AmmoResult.getName());
        assertEquals("IS Ammo SC Mortar-2", actualCreateISAPMortar2AmmoResult.getInternalName());
        assertEquals("B/D-F(F*)-F-E", actualCreateISAPMortar2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAPMortar2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAPMortar2AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISAPMortar2AmmoResult.getExtinctionDate());
    }

    private void createISAPMortar4Ammo(AmmoType actualCreateISAPMortar4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAPMortar4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAPMortar4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAPMortar4AmmoResult.svslots);
        assertEquals("Mortar SC 4", actualCreateISAPMortar4AmmoResult.shortName);
        assertNull(actualCreateISAPMortar4AmmoResult.modes);
        assertTrue(actualCreateISAPMortar4AmmoResult.explosive);
        assertEquals(1, actualCreateISAPMortar4AmmoResult.criticals);
        assertEquals(3.6, actualCreateISAPMortar4AmmoResult.bv, 0.0);
        assertFalse(actualCreateISAPMortar4AmmoResult.isSpreadable());
        assertFalse(actualCreateISAPMortar4AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISAPMortar4AmmoResult.isMixedTech());
        assertTrue(actualCreateISAPMortar4AmmoResult.isHittable());
        assertFalse(actualCreateISAPMortar4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAPMortar4AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISAPMortar4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAPMortar4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISAPMortar4AmmoResult.getStaticTechLevel());
        assertEquals("324,TO", actualCreateISAPMortar4AmmoResult.getRulesRefs());
        assertEquals(28000.0, actualCreateISAPMortar4AmmoResult.getRawCost(), 0.0);
        assertEquals(2521, actualCreateISAPMortar4AmmoResult.getPrototypeDate());
        assertEquals("Shaped Charge Mortar 4 Ammo", actualCreateISAPMortar4AmmoResult.getName());
        assertEquals("IS Ammo SC Mortar-4", actualCreateISAPMortar4AmmoResult.getInternalName());
        assertEquals("B/D-F(F*)-F-E", actualCreateISAPMortar4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAPMortar4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAPMortar4AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISAPMortar4AmmoResult.getExtinctionDate());
    }

    private void createISAPMortar8Ammo(AmmoType actualCreateISAPMortar8AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAPMortar8AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAPMortar8AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAPMortar8AmmoResult.svslots);
        assertEquals("Mortar SC 8", actualCreateISAPMortar8AmmoResult.shortName);
        assertNull(actualCreateISAPMortar8AmmoResult.modes);
        assertTrue(actualCreateISAPMortar8AmmoResult.explosive);
        assertEquals(1, actualCreateISAPMortar8AmmoResult.criticals);
        assertEquals(7.2, actualCreateISAPMortar8AmmoResult.bv, 0.0);
        assertFalse(actualCreateISAPMortar8AmmoResult.isSpreadable());
        assertFalse(actualCreateISAPMortar8AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISAPMortar8AmmoResult.isMixedTech());
        assertTrue(actualCreateISAPMortar8AmmoResult.isHittable());
        assertFalse(actualCreateISAPMortar8AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAPMortar8AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateISAPMortar8AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAPMortar8AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISAPMortar8AmmoResult.getStaticTechLevel());
        assertEquals("324,TO", actualCreateISAPMortar8AmmoResult.getRulesRefs());
        assertEquals(28000.0, actualCreateISAPMortar8AmmoResult.getRawCost(), 0.0);
        assertEquals(2521, actualCreateISAPMortar8AmmoResult.getPrototypeDate());
        assertEquals("Shaped Charge Mortar 8 Ammo", actualCreateISAPMortar8AmmoResult.getName());
        assertEquals("IS Ammo SC Mortar-8", actualCreateISAPMortar8AmmoResult.getInternalName());
        assertEquals("B/D-F(F*)-F-E", actualCreateISAPMortar8AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAPMortar8AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAPMortar8AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISAPMortar8AmmoResult.getExtinctionDate());
    }

    private void createCLAPMortar1Ammo(AmmoType actualCreateCLAPMortar1AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLAPMortar1AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLAPMortar1AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLAPMortar1AmmoResult.svslots);
        assertEquals("Mortar SC 1", actualCreateCLAPMortar1AmmoResult.shortName);
        assertNull(actualCreateCLAPMortar1AmmoResult.modes);
        assertTrue(actualCreateCLAPMortar1AmmoResult.explosive);
        assertEquals(1, actualCreateCLAPMortar1AmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_CLAN_FF, actualCreateCLAPMortar1AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLAPMortar1AmmoResult.isSpreadable());
        assertFalse(actualCreateCLAPMortar1AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLAPMortar1AmmoResult.isMixedTech());
        assertTrue(actualCreateCLAPMortar1AmmoResult.isHittable());
        assertFalse(actualCreateCLAPMortar1AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLAPMortar1AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateCLAPMortar1AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLAPMortar1AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLAPMortar1AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLAPMortar1AmmoResult.getStandardRange());
        assertEquals("324,TO", actualCreateCLAPMortar1AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLAPMortar1AmmoResult.getReintroductionDate());
        assertEquals(28000.0, actualCreateCLAPMortar1AmmoResult.getRawCost(), 0.0);
        assertEquals("Shaped Charge Mortar 1 Ammo", actualCreateCLAPMortar1AmmoResult.getName());
        assertEquals("Clan Ammo SC Mortar-1", actualCreateCLAPMortar1AmmoResult.getInternalName());
        assertEquals("B/D-F-E-E", actualCreateCLAPMortar1AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLAPMortar1AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLAPMortar1AmmoResult.getFlags());
    }

    private void createCLAPMortar2Ammo(AmmoType actualCreateCLAPMortar2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLAPMortar2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLAPMortar2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLAPMortar2AmmoResult.svslots);
        assertEquals("Mortar SC 2", actualCreateCLAPMortar2AmmoResult.shortName);
        assertNull(actualCreateCLAPMortar2AmmoResult.modes);
        assertTrue(actualCreateCLAPMortar2AmmoResult.explosive);
        assertEquals(1, actualCreateCLAPMortar2AmmoResult.criticals);
        assertEquals(2.4, actualCreateCLAPMortar2AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLAPMortar2AmmoResult.isSpreadable());
        assertFalse(actualCreateCLAPMortar2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLAPMortar2AmmoResult.isMixedTech());
        assertTrue(actualCreateCLAPMortar2AmmoResult.isHittable());
        assertFalse(actualCreateCLAPMortar2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLAPMortar2AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateCLAPMortar2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLAPMortar2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLAPMortar2AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLAPMortar2AmmoResult.getStandardRange());
        assertEquals("324,TO", actualCreateCLAPMortar2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLAPMortar2AmmoResult.getReintroductionDate());
        assertEquals(28000.0, actualCreateCLAPMortar2AmmoResult.getRawCost(), 0.0);
        assertEquals("Shaped Charge Mortar 2 Ammo", actualCreateCLAPMortar2AmmoResult.getName());
        assertEquals("Clan Ammo SC Mortar-2", actualCreateCLAPMortar2AmmoResult.getInternalName());
        assertEquals("B/D-F-E-E", actualCreateCLAPMortar2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLAPMortar2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLAPMortar2AmmoResult.getFlags());
    }

    private void createCLAPMortar4Ammo(AmmoType actualCreateCLAPMortar4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLAPMortar4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLAPMortar4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLAPMortar4AmmoResult.svslots);
        assertEquals("Mortar SC 4", actualCreateCLAPMortar4AmmoResult.shortName);
        assertNull(actualCreateCLAPMortar4AmmoResult.modes);
        assertTrue(actualCreateCLAPMortar4AmmoResult.explosive);
        assertEquals(1, actualCreateCLAPMortar4AmmoResult.criticals);
        assertEquals(3.6, actualCreateCLAPMortar4AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLAPMortar4AmmoResult.isSpreadable());
        assertFalse(actualCreateCLAPMortar4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLAPMortar4AmmoResult.isMixedTech());
        assertTrue(actualCreateCLAPMortar4AmmoResult.isHittable());
        assertFalse(actualCreateCLAPMortar4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLAPMortar4AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateCLAPMortar4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLAPMortar4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLAPMortar4AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLAPMortar4AmmoResult.getStandardRange());
        assertEquals("324,TO", actualCreateCLAPMortar4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLAPMortar4AmmoResult.getReintroductionDate());
        assertEquals(28000.0, actualCreateCLAPMortar4AmmoResult.getRawCost(), 0.0);
        assertEquals("Shaped Charge Mortar 4 Ammo", actualCreateCLAPMortar4AmmoResult.getName());
        assertEquals("Clan Ammo SC Mortar-4", actualCreateCLAPMortar4AmmoResult.getInternalName());
        assertEquals("B/D-F-E-E", actualCreateCLAPMortar4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLAPMortar4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLAPMortar4AmmoResult.getFlags());
    }

    private void createCLAPMortar8Ammo(AmmoType actualCreateCLAPMortar8AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLAPMortar8AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLAPMortar8AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLAPMortar8AmmoResult.svslots);
        assertEquals("Mortar SC 8", actualCreateCLAPMortar8AmmoResult.shortName);
        assertNull(actualCreateCLAPMortar8AmmoResult.modes);
        assertTrue(actualCreateCLAPMortar8AmmoResult.explosive);
        assertEquals(1, actualCreateCLAPMortar8AmmoResult.criticals);
        assertEquals(7.2, actualCreateCLAPMortar8AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLAPMortar8AmmoResult.isSpreadable());
        assertFalse(actualCreateCLAPMortar8AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLAPMortar8AmmoResult.isMixedTech());
        assertTrue(actualCreateCLAPMortar8AmmoResult.isHittable());
        assertFalse(actualCreateCLAPMortar8AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLAPMortar8AmmoResult.getToHitModifier());
        assertEquals(1, actualCreateCLAPMortar8AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLAPMortar8AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLAPMortar8AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateCLAPMortar8AmmoResult.getStandardRange());
        assertEquals("324,TO", actualCreateCLAPMortar8AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLAPMortar8AmmoResult.getReintroductionDate());
        assertEquals(28000.0, actualCreateCLAPMortar8AmmoResult.getRawCost(), 0.0);
        assertEquals("Shaped Charge Mortar 8 Ammo", actualCreateCLAPMortar8AmmoResult.getName());
        assertEquals("Clan Ammo SC Mortar-8", actualCreateCLAPMortar8AmmoResult.getInternalName());
        assertEquals("B/D-F-E-E", actualCreateCLAPMortar8AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLAPMortar8AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLAPMortar8AmmoResult.getFlags());
    }

    private void createISPlasmaRifleAmmo(AmmoType actualCreateISPlasmaRifleAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISPlasmaRifleAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISPlasmaRifleAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISPlasmaRifleAmmoResult.svslots);
        assertEquals("Plasma Rifle", actualCreateISPlasmaRifleAmmoResult.shortName);
        assertNull(actualCreateISPlasmaRifleAmmoResult.modes);
        assertFalse(actualCreateISPlasmaRifleAmmoResult.explosive);
        assertEquals(1, actualCreateISPlasmaRifleAmmoResult.criticals);
        assertEquals(26.0, actualCreateISPlasmaRifleAmmoResult.bv, 0.0);
        assertFalse(actualCreateISPlasmaRifleAmmoResult.isSpreadable());
        assertFalse(actualCreateISPlasmaRifleAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISPlasmaRifleAmmoResult.isMixedTech());
        assertTrue(actualCreateISPlasmaRifleAmmoResult.isHittable());
        assertFalse(actualCreateISPlasmaRifleAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISPlasmaRifleAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISPlasmaRifleAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISPlasmaRifleAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISPlasmaRifleAmmoResult.getStaticTechLevel());
        assertEquals("234,TM", actualCreateISPlasmaRifleAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISPlasmaRifleAmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISPlasmaRifleAmmoResult.getRawCost(), 0.0);
        assertEquals(3056, actualCreateISPlasmaRifleAmmoResult.getPrototypeDate());
        assertEquals("Plasma Rifle Ammo", actualCreateISPlasmaRifleAmmoResult.getName());
        assertEquals("ISPlasmaRifleAmmo", actualCreateISPlasmaRifleAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISPlasmaRifleAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISPlasmaRifleAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISPlasmaRifleAmmoResult.getFlags());
    }

    private void createCLPlasmaCannonAmmo(AmmoType actualCreateCLPlasmaCannonAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLPlasmaCannonAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLPlasmaCannonAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLPlasmaCannonAmmoResult.svslots);
        assertEquals("Plasma Cannon", actualCreateCLPlasmaCannonAmmoResult.shortName);
        assertNull(actualCreateCLPlasmaCannonAmmoResult.modes);
        assertFalse(actualCreateCLPlasmaCannonAmmoResult.explosive);
        assertEquals(1, actualCreateCLPlasmaCannonAmmoResult.criticals);
        assertEquals(21.0, actualCreateCLPlasmaCannonAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLPlasmaCannonAmmoResult.isSpreadable());
        assertFalse(actualCreateCLPlasmaCannonAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLPlasmaCannonAmmoResult.isMixedTech());
        assertTrue(actualCreateCLPlasmaCannonAmmoResult.isHittable());
        assertFalse(actualCreateCLPlasmaCannonAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLPlasmaCannonAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLPlasmaCannonAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLPlasmaCannonAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLPlasmaCannonAmmoResult.getStaticTechLevel());
        assertEquals("234,TM", actualCreateCLPlasmaCannonAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLPlasmaCannonAmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLPlasmaCannonAmmoResult.getRawCost(), 0.0);
        assertEquals(3063, actualCreateCLPlasmaCannonAmmoResult.getPrototypeDate());
        assertEquals("Plasma Cannon Ammo", actualCreateCLPlasmaCannonAmmoResult.getName());
        assertEquals("CLPlasmaCannonAmmo", actualCreateCLPlasmaCannonAmmoResult.getInternalName());
        assertEquals("F/X-X-E-D", actualCreateCLPlasmaCannonAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLPlasmaCannonAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLPlasmaCannonAmmoResult.getFlags());
    }

    private void createISAPDSAmmo(AmmoType actualCreateISAPDSAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAPDSAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAPDSAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAPDSAmmoResult.svslots);
        assertEquals("RISC APDS", actualCreateISAPDSAmmoResult.shortName);
        assertNull(actualCreateISAPDSAmmoResult.modes);
        assertTrue(actualCreateISAPDSAmmoResult.explosive);
        assertEquals(1, actualCreateISAPDSAmmoResult.criticals);
        assertEquals(22.0, actualCreateISAPDSAmmoResult.bv, 0.0);
        assertFalse(actualCreateISAPDSAmmoResult.isSpreadable());
        assertFalse(actualCreateISAPDSAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISAPDSAmmoResult.isMixedTech());
        assertTrue(actualCreateISAPDSAmmoResult.isHittable());
        assertFalse(actualCreateISAPDSAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAPDSAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISAPDSAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAPDSAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISAPDSAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISAPDSAmmoResult.getStandardRange());
        assertEquals("91, IO", actualCreateISAPDSAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISAPDSAmmoResult.getReintroductionDate());
        assertEquals(2000.0, actualCreateISAPDSAmmoResult.getRawCost(), 0.0);
        assertEquals("RISC Advanced Point Defense System Ammo", actualCreateISAPDSAmmoResult.getName());
        assertEquals("ISAPDS Ammo", actualCreateISAPDSAmmoResult.getInternalName());
        assertEquals("E/X-X-X-E", actualCreateISAPDSAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAPDSAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAPDSAmmoResult.getFlags());
    }

    private void createISMekTaserAmmo(AmmoType actualCreateISMekTaserAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMekTaserAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMekTaserAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMekTaserAmmoResult.svslots);
        assertEquals("Taser", actualCreateISMekTaserAmmoResult.shortName);
        assertNull(actualCreateISMekTaserAmmoResult.modes);
        assertTrue(actualCreateISMekTaserAmmoResult.explosive);
        assertEquals(1, actualCreateISMekTaserAmmoResult.criticals);
        assertEquals(5.0, actualCreateISMekTaserAmmoResult.bv, 0.0);
        assertFalse(actualCreateISMekTaserAmmoResult.isSpreadable());
        assertFalse(actualCreateISMekTaserAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMekTaserAmmoResult.isMixedTech());
        assertTrue(actualCreateISMekTaserAmmoResult.isHittable());
        assertFalse(actualCreateISMekTaserAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMekTaserAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISMekTaserAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMekTaserAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMekTaserAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISMekTaserAmmoResult.getStandardRange());
        assertEquals("346,TO", actualCreateISMekTaserAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMekTaserAmmoResult.getReintroductionDate());
        assertEquals(2000.0, actualCreateISMekTaserAmmoResult.getRawCost(), 0.0);
        assertEquals("Taser Ammo", actualCreateISMekTaserAmmoResult.getName());
        assertEquals("Taser Ammo", actualCreateISMekTaserAmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateISMekTaserAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMekTaserAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMekTaserAmmoResult.getFlags());
    }

    private void createLightMassDriverAmmo(AmmoType actualCreateLightMassDriverAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateLightMassDriverAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateLightMassDriverAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateLightMassDriverAmmoResult.svslots);
        assertEquals("", actualCreateLightMassDriverAmmoResult.shortName);
        assertNull(actualCreateLightMassDriverAmmoResult.modes);
        assertTrue(actualCreateLightMassDriverAmmoResult.explosive);
        assertEquals(1, actualCreateLightMassDriverAmmoResult.criticals);
        assertEquals(882.0, actualCreateLightMassDriverAmmoResult.bv, 0.0);
        assertFalse(actualCreateLightMassDriverAmmoResult.isSpreadable());
        assertFalse(actualCreateLightMassDriverAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateLightMassDriverAmmoResult.isMixedTech());
        assertTrue(actualCreateLightMassDriverAmmoResult.isHittable());
        assertFalse(actualCreateLightMassDriverAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateLightMassDriverAmmoResult.getToHitModifier());
        assertEquals(3, actualCreateLightMassDriverAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateLightMassDriverAmmoResult.getSubType());
        assertEquals("", actualCreateLightMassDriverAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateLightMassDriverAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateLightMassDriverAmmoResult.getStandardRange());
        assertEquals("323,TO", actualCreateLightMassDriverAmmoResult.getRulesRefs());
        assertEquals(3066, actualCreateLightMassDriverAmmoResult.getReintroductionDate());
        assertEquals(150000.0, actualCreateLightMassDriverAmmoResult.getRawCost(), 0.0);
        assertEquals("Ammo Light Mass Driver", actualCreateLightMassDriverAmmoResult.getInternalName());
        assertEquals("D/F-X-F-F", actualCreateLightMassDriverAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateLightMassDriverAmmoResult.flags;
        assertSame(expectedFlags, actualCreateLightMassDriverAmmoResult.getFlags());
    }

    private void createMediumMassDriverAmmo(AmmoType actualCreateMediumMassDriverAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateMediumMassDriverAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateMediumMassDriverAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateMediumMassDriverAmmoResult.svslots);
        assertEquals("", actualCreateMediumMassDriverAmmoResult.shortName);
        assertNull(actualCreateMediumMassDriverAmmoResult.modes);
        assertTrue(actualCreateMediumMassDriverAmmoResult.explosive);
        assertEquals(1, actualCreateMediumMassDriverAmmoResult.criticals);
        assertEquals(1470.0, actualCreateMediumMassDriverAmmoResult.bv, 0.0);
        assertFalse(actualCreateMediumMassDriverAmmoResult.isSpreadable());
        assertFalse(actualCreateMediumMassDriverAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateMediumMassDriverAmmoResult.isMixedTech());
        assertTrue(actualCreateMediumMassDriverAmmoResult.isHittable());
        assertFalse(actualCreateMediumMassDriverAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateMediumMassDriverAmmoResult.getToHitModifier());
        assertEquals(3, actualCreateMediumMassDriverAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateMediumMassDriverAmmoResult.getSubType());
        assertEquals("", actualCreateMediumMassDriverAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateMediumMassDriverAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateMediumMassDriverAmmoResult.getStandardRange());
        assertEquals("323,TO", actualCreateMediumMassDriverAmmoResult.getRulesRefs());
        assertEquals(3066, actualCreateMediumMassDriverAmmoResult.getReintroductionDate());
        assertEquals(300000.0, actualCreateMediumMassDriverAmmoResult.getRawCost(), 0.0);
        assertEquals("Ammo Medium Mass Driver", actualCreateMediumMassDriverAmmoResult.getInternalName());
        assertEquals("D/F-X-F-F", actualCreateMediumMassDriverAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateMediumMassDriverAmmoResult.flags;
        assertSame(expectedFlags, actualCreateMediumMassDriverAmmoResult.getFlags());
    }

    private void createHeavyMassDriverAmmo(AmmoType actualCreateHeavyMassDriverAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateHeavyMassDriverAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateHeavyMassDriverAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateHeavyMassDriverAmmoResult.svslots);
        assertEquals("", actualCreateHeavyMassDriverAmmoResult.shortName);
        assertNull(actualCreateHeavyMassDriverAmmoResult.modes);
        assertTrue(actualCreateHeavyMassDriverAmmoResult.explosive);
        assertEquals(1, actualCreateHeavyMassDriverAmmoResult.criticals);
        assertEquals(2058.0, actualCreateHeavyMassDriverAmmoResult.bv, 0.0);
        assertFalse(actualCreateHeavyMassDriverAmmoResult.isSpreadable());
        assertFalse(actualCreateHeavyMassDriverAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateHeavyMassDriverAmmoResult.isMixedTech());
        assertTrue(actualCreateHeavyMassDriverAmmoResult.isHittable());
        assertFalse(actualCreateHeavyMassDriverAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateHeavyMassDriverAmmoResult.getToHitModifier());
        assertEquals(3, actualCreateHeavyMassDriverAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateHeavyMassDriverAmmoResult.getSubType());
        assertEquals("", actualCreateHeavyMassDriverAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateHeavyMassDriverAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateHeavyMassDriverAmmoResult.getStandardRange());
        assertEquals("323,TO", actualCreateHeavyMassDriverAmmoResult.getRulesRefs());
        assertEquals(3066, actualCreateHeavyMassDriverAmmoResult.getReintroductionDate());
        assertEquals(600000.0, actualCreateHeavyMassDriverAmmoResult.getRawCost(), 0.0);
        assertEquals("Ammo Heavy Mass Driver", actualCreateHeavyMassDriverAmmoResult.getInternalName());
        assertEquals("D/F-X-F-F", actualCreateHeavyMassDriverAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateHeavyMassDriverAmmoResult.flags;
        assertSame(expectedFlags, actualCreateHeavyMassDriverAmmoResult.getFlags());
    }

    private void createLightNGaussAmmo(AmmoType actualCreateLightNGaussAmmoResult) {
        assertEquals(0.2, actualCreateLightNGaussAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateLightNGaussAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateLightNGaussAmmoResult.svslots);
        assertEquals("", actualCreateLightNGaussAmmoResult.shortName);
        assertNull(actualCreateLightNGaussAmmoResult.modes);
        assertTrue(actualCreateLightNGaussAmmoResult.explosive);
        assertEquals(1, actualCreateLightNGaussAmmoResult.criticals);
        assertEquals(378.0, actualCreateLightNGaussAmmoResult.bv, 0.0);
        assertFalse(actualCreateLightNGaussAmmoResult.isSpreadable());
        assertFalse(actualCreateLightNGaussAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateLightNGaussAmmoResult.isMixedTech());
        assertTrue(actualCreateLightNGaussAmmoResult.isHittable());
        assertFalse(actualCreateLightNGaussAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateLightNGaussAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateLightNGaussAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateLightNGaussAmmoResult.getSubType());
        assertEquals("", actualCreateLightNGaussAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateLightNGaussAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateLightNGaussAmmoResult.getStandardRange());
        assertEquals("323,TO", actualCreateLightNGaussAmmoResult.getRulesRefs());
        assertEquals(45000.0, actualCreateLightNGaussAmmoResult.getRawCost(), 0.0);
        assertEquals("Ammo Light N-Gauss", actualCreateLightNGaussAmmoResult.getInternalName());
        assertEquals("E/E-X-E-E", actualCreateLightNGaussAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateLightNGaussAmmoResult.flags;
        assertSame(expectedFlags, actualCreateLightNGaussAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateLightNGaussAmmoResult.getExtinctionDate());
    }

    private void createMediumNGaussAmmo(AmmoType actualCreateMediumNGaussAmmoResult) {
        assertEquals(0.4, actualCreateMediumNGaussAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateMediumNGaussAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateMediumNGaussAmmoResult.svslots);
        assertEquals("", actualCreateMediumNGaussAmmoResult.shortName);
        assertNull(actualCreateMediumNGaussAmmoResult.modes);
        assertTrue(actualCreateMediumNGaussAmmoResult.explosive);
        assertEquals(1, actualCreateMediumNGaussAmmoResult.criticals);
        assertEquals(630.0, actualCreateMediumNGaussAmmoResult.bv, 0.0);
        assertFalse(actualCreateMediumNGaussAmmoResult.isSpreadable());
        assertFalse(actualCreateMediumNGaussAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateMediumNGaussAmmoResult.isMixedTech());
        assertTrue(actualCreateMediumNGaussAmmoResult.isHittable());
        assertFalse(actualCreateMediumNGaussAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateMediumNGaussAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateMediumNGaussAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateMediumNGaussAmmoResult.getSubType());
        assertEquals("", actualCreateMediumNGaussAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateMediumNGaussAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateMediumNGaussAmmoResult.getStandardRange());
        assertEquals("323,TO", actualCreateMediumNGaussAmmoResult.getRulesRefs());
        assertEquals(75000.0, actualCreateMediumNGaussAmmoResult.getRawCost(), 0.0);
        assertEquals("Ammo Medium N-Gauss", actualCreateMediumNGaussAmmoResult.getInternalName());
        assertEquals("E/E-X-E-E", actualCreateMediumNGaussAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateMediumNGaussAmmoResult.flags;
        assertSame(expectedFlags, actualCreateMediumNGaussAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateMediumNGaussAmmoResult.getExtinctionDate());
    }

    private void createHeavyNGaussAmmo(AmmoType actualCreateHeavyNGaussAmmoResult) {
        assertEquals(0.5, actualCreateHeavyNGaussAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateHeavyNGaussAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateHeavyNGaussAmmoResult.svslots);
        assertEquals("", actualCreateHeavyNGaussAmmoResult.shortName);
        assertNull(actualCreateHeavyNGaussAmmoResult.modes);
        assertTrue(actualCreateHeavyNGaussAmmoResult.explosive);
        assertEquals(1, actualCreateHeavyNGaussAmmoResult.criticals);
        assertEquals(756.0, actualCreateHeavyNGaussAmmoResult.bv, 0.0);
        assertFalse(actualCreateHeavyNGaussAmmoResult.isSpreadable());
        assertFalse(actualCreateHeavyNGaussAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateHeavyNGaussAmmoResult.isMixedTech());
        assertTrue(actualCreateHeavyNGaussAmmoResult.isHittable());
        assertFalse(actualCreateHeavyNGaussAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateHeavyNGaussAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateHeavyNGaussAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateHeavyNGaussAmmoResult.getSubType());
        assertEquals("", actualCreateHeavyNGaussAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateHeavyNGaussAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateHeavyNGaussAmmoResult.getStandardRange());
        assertEquals("323,TO", actualCreateHeavyNGaussAmmoResult.getRulesRefs());
        assertEquals(90000.0, actualCreateHeavyNGaussAmmoResult.getRawCost(), 0.0);
        assertEquals("Ammo Heavy N-Gauss", actualCreateHeavyNGaussAmmoResult.getInternalName());
        assertEquals("E/E-X-E-E", actualCreateHeavyNGaussAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateHeavyNGaussAmmoResult.flags;
        assertSame(expectedFlags, actualCreateHeavyNGaussAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateHeavyNGaussAmmoResult.getExtinctionDate());
    }

    private void createNAC10Ammo(AmmoType actualCreateNAC10AmmoResult) {
        assertEquals(0.2, actualCreateNAC10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateNAC10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateNAC10AmmoResult.svslots);
        assertEquals("", actualCreateNAC10AmmoResult.shortName);
        assertNull(actualCreateNAC10AmmoResult.modes);
        assertTrue(actualCreateNAC10AmmoResult.explosive);
        assertEquals(1, actualCreateNAC10AmmoResult.criticals);
        assertEquals(237.0, actualCreateNAC10AmmoResult.bv, 0.0);
        assertFalse(actualCreateNAC10AmmoResult.isSpreadable());
        assertFalse(actualCreateNAC10AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateNAC10AmmoResult.isMixedTech());
        assertTrue(actualCreateNAC10AmmoResult.isHittable());
        assertFalse(actualCreateNAC10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateNAC10AmmoResult.getToHitModifier());
        assertEquals(3, actualCreateNAC10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateNAC10AmmoResult.getSubType());
        assertEquals("", actualCreateNAC10AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateNAC10AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateNAC10AmmoResult.getStandardRange());
        assertEquals("333,TO", actualCreateNAC10AmmoResult.getRulesRefs());
        assertEquals(30000.0, actualCreateNAC10AmmoResult.getRawCost(), 0.0);
        assertEquals("Ammo NAC/10", actualCreateNAC10AmmoResult.getInternalName());
        assertEquals("D/E-X-E-E", actualCreateNAC10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateNAC10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateNAC10AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateNAC10AmmoResult.getExtinctionDate());
    }

    private void createNAC20Ammo(AmmoType actualCreateNAC20AmmoResult) {
        assertEquals(0.4, actualCreateNAC20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateNAC20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateNAC20AmmoResult.svslots);
        assertEquals("", actualCreateNAC20AmmoResult.shortName);
        assertNull(actualCreateNAC20AmmoResult.modes);
        assertTrue(actualCreateNAC20AmmoResult.explosive);
        assertEquals(1, actualCreateNAC20AmmoResult.criticals);
        assertEquals(474.0, actualCreateNAC20AmmoResult.bv, 0.0);
        assertFalse(actualCreateNAC20AmmoResult.isSpreadable());
        assertFalse(actualCreateNAC20AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateNAC20AmmoResult.isMixedTech());
        assertTrue(actualCreateNAC20AmmoResult.isHittable());
        assertFalse(actualCreateNAC20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateNAC20AmmoResult.getToHitModifier());
        assertEquals(3, actualCreateNAC20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateNAC20AmmoResult.getSubType());
        assertEquals("", actualCreateNAC20AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateNAC20AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateNAC20AmmoResult.getStandardRange());
        assertEquals("333,TO", actualCreateNAC20AmmoResult.getRulesRefs());
        assertEquals(60000.0, actualCreateNAC20AmmoResult.getRawCost(), 0.0);
        assertEquals("Ammo NAC/20", actualCreateNAC20AmmoResult.getInternalName());
        assertEquals("D/E-X-E-E", actualCreateNAC20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateNAC20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateNAC20AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateNAC20AmmoResult.getExtinctionDate());
    }

    private void createNAC25Ammo(AmmoType actualCreateNAC25AmmoResult) {
        assertEquals(0.6, actualCreateNAC25AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateNAC25AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateNAC25AmmoResult.svslots);
        assertEquals("", actualCreateNAC25AmmoResult.shortName);
        assertNull(actualCreateNAC25AmmoResult.modes);
        assertTrue(actualCreateNAC25AmmoResult.explosive);
        assertEquals(1, actualCreateNAC25AmmoResult.criticals);
        assertEquals(593.0, actualCreateNAC25AmmoResult.bv, 0.0);
        assertFalse(actualCreateNAC25AmmoResult.isSpreadable());
        assertFalse(actualCreateNAC25AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateNAC25AmmoResult.isMixedTech());
        assertTrue(actualCreateNAC25AmmoResult.isHittable());
        assertFalse(actualCreateNAC25AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateNAC25AmmoResult.getToHitModifier());
        assertEquals(3, actualCreateNAC25AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateNAC25AmmoResult.getSubType());
        assertEquals("", actualCreateNAC25AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateNAC25AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateNAC25AmmoResult.getStandardRange());
        assertEquals("333,TO", actualCreateNAC25AmmoResult.getRulesRefs());
        assertEquals(75000.0, actualCreateNAC25AmmoResult.getRawCost(), 0.0);
        assertEquals("Ammo NAC/25", actualCreateNAC25AmmoResult.getInternalName());
        assertEquals("D/E-X-E-E", actualCreateNAC25AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateNAC25AmmoResult.flags;
        assertSame(expectedFlags, actualCreateNAC25AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateNAC25AmmoResult.getExtinctionDate());
    }

    private void createNAC30Ammo(AmmoType actualCreateNAC30AmmoResult) {
        assertEquals(0.8, actualCreateNAC30AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateNAC30AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateNAC30AmmoResult.svslots);
        assertEquals("", actualCreateNAC30AmmoResult.shortName);
        assertNull(actualCreateNAC30AmmoResult.modes);
        assertTrue(actualCreateNAC30AmmoResult.explosive);
        assertEquals(1, actualCreateNAC30AmmoResult.criticals);
        assertEquals(711.0, actualCreateNAC30AmmoResult.bv, 0.0);
        assertFalse(actualCreateNAC30AmmoResult.isSpreadable());
        assertFalse(actualCreateNAC30AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateNAC30AmmoResult.isMixedTech());
        assertTrue(actualCreateNAC30AmmoResult.isHittable());
        assertFalse(actualCreateNAC30AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateNAC30AmmoResult.getToHitModifier());
        assertEquals(3, actualCreateNAC30AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateNAC30AmmoResult.getSubType());
        assertEquals("", actualCreateNAC30AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateNAC30AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateNAC30AmmoResult.getStandardRange());
        assertEquals("333,TO", actualCreateNAC30AmmoResult.getRulesRefs());
        assertEquals(90000.0, actualCreateNAC30AmmoResult.getRawCost(), 0.0);
        assertEquals("Ammo NAC/30", actualCreateNAC30AmmoResult.getInternalName());
        assertEquals("D/E-X-E-E", actualCreateNAC30AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateNAC30AmmoResult.flags;
        assertSame(expectedFlags, actualCreateNAC30AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateNAC30AmmoResult.getExtinctionDate());
    }

    private void createNAC35Ammo(AmmoType actualCreateNAC35AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateNAC35AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateNAC35AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateNAC35AmmoResult.svslots);
        assertEquals("", actualCreateNAC35AmmoResult.shortName);
        assertNull(actualCreateNAC35AmmoResult.modes);
        assertTrue(actualCreateNAC35AmmoResult.explosive);
        assertEquals(1, actualCreateNAC35AmmoResult.criticals);
        assertEquals(620.0, actualCreateNAC35AmmoResult.bv, 0.0);
        assertFalse(actualCreateNAC35AmmoResult.isSpreadable());
        assertFalse(actualCreateNAC35AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateNAC35AmmoResult.isMixedTech());
        assertTrue(actualCreateNAC35AmmoResult.isHittable());
        assertFalse(actualCreateNAC35AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateNAC35AmmoResult.getToHitModifier());
        assertEquals(3, actualCreateNAC35AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateNAC35AmmoResult.getSubType());
        assertEquals("", actualCreateNAC35AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateNAC35AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateNAC35AmmoResult.getStandardRange());
        assertEquals("333,TO", actualCreateNAC35AmmoResult.getRulesRefs());
        assertEquals(105000.0, actualCreateNAC35AmmoResult.getRawCost(), 0.0);
        assertEquals("Ammo NAC/35", actualCreateNAC35AmmoResult.getInternalName());
        assertEquals("D/E-X-E-E", actualCreateNAC35AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateNAC35AmmoResult.flags;
        assertSame(expectedFlags, actualCreateNAC35AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateNAC35AmmoResult.getExtinctionDate());
    }

    private void createNAC40Ammo(AmmoType actualCreateNAC40AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_CLAN_FF, actualCreateNAC40AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateNAC40AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateNAC40AmmoResult.svslots);
        assertEquals("", actualCreateNAC40AmmoResult.shortName);
        assertNull(actualCreateNAC40AmmoResult.modes);
        assertTrue(actualCreateNAC40AmmoResult.explosive);
        assertEquals(1, actualCreateNAC40AmmoResult.criticals);
        assertEquals(708.0, actualCreateNAC40AmmoResult.bv, 0.0);
        assertFalse(actualCreateNAC40AmmoResult.isSpreadable());
        assertFalse(actualCreateNAC40AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateNAC40AmmoResult.isMixedTech());
        assertTrue(actualCreateNAC40AmmoResult.isHittable());
        assertFalse(actualCreateNAC40AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateNAC40AmmoResult.getToHitModifier());
        assertEquals(3, actualCreateNAC40AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateNAC40AmmoResult.getSubType());
        assertEquals("", actualCreateNAC40AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateNAC40AmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateNAC40AmmoResult.getStandardRange());
        assertEquals("333,TO", actualCreateNAC40AmmoResult.getRulesRefs());
        assertEquals(120000.0, actualCreateNAC40AmmoResult.getRawCost(), 0.0);
        assertEquals("Ammo NAC/40", actualCreateNAC40AmmoResult.getInternalName());
        assertEquals("D/E-X-E-E", actualCreateNAC40AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateNAC40AmmoResult.flags;
        assertSame(expectedFlags, actualCreateNAC40AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateNAC40AmmoResult.getExtinctionDate());
    }

    private void createBarracudaAmmo(AmmoType actualCreateBarracudaAmmoResult) {
        assertEquals(30.0, actualCreateBarracudaAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBarracudaAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBarracudaAmmoResult.svslots);
        assertEquals("", actualCreateBarracudaAmmoResult.shortName);
        assertNull(actualCreateBarracudaAmmoResult.modes);
        assertTrue(actualCreateBarracudaAmmoResult.explosive);
        assertEquals(1, actualCreateBarracudaAmmoResult.criticals);
        assertEquals(65.0, actualCreateBarracudaAmmoResult.bv, 0.0);
        assertFalse(actualCreateBarracudaAmmoResult.isSpreadable());
        assertFalse(actualCreateBarracudaAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateBarracudaAmmoResult.isMixedTech());
        assertTrue(actualCreateBarracudaAmmoResult.isHittable());
        assertFalse(actualCreateBarracudaAmmoResult.hasInstantModeSwitch());
        assertEquals(WeaponType.DAMAGE_BY_CLUSTERTABLE, actualCreateBarracudaAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBarracudaAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBarracudaAmmoResult.getSubType());
        assertEquals("", actualCreateBarracudaAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBarracudaAmmoResult.getStaticTechLevel());
        assertEquals("210,TM", actualCreateBarracudaAmmoResult.getRulesRefs());
        assertEquals(8000.0, actualCreateBarracudaAmmoResult.getRawCost(), 0.0);
        assertEquals(2195, actualCreateBarracudaAmmoResult.getPrototypeDate());
        assertEquals("Ammo Barracuda", actualCreateBarracudaAmmoResult.getInternalName());
        assertEquals("E/D-E(F)-E-D", actualCreateBarracudaAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBarracudaAmmoResult.flags;
        assertSame(expectedFlags, actualCreateBarracudaAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateBarracudaAmmoResult.getExtinctionDate());
    }

    private void createWhiteSharkAmmo(AmmoType actualCreateWhiteSharkAmmoResult) {
        assertEquals(40.0, actualCreateWhiteSharkAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateWhiteSharkAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateWhiteSharkAmmoResult.svslots);
        assertEquals("", actualCreateWhiteSharkAmmoResult.shortName);
        assertNull(actualCreateWhiteSharkAmmoResult.modes);
        assertTrue(actualCreateWhiteSharkAmmoResult.explosive);
        assertEquals(1, actualCreateWhiteSharkAmmoResult.criticals);
        assertEquals(72.0, actualCreateWhiteSharkAmmoResult.bv, 0.0);
        assertFalse(actualCreateWhiteSharkAmmoResult.isSpreadable());
        assertFalse(actualCreateWhiteSharkAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateWhiteSharkAmmoResult.isMixedTech());
        assertTrue(actualCreateWhiteSharkAmmoResult.isHittable());
        assertFalse(actualCreateWhiteSharkAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateWhiteSharkAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateWhiteSharkAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateWhiteSharkAmmoResult.getSubType());
        assertEquals("", actualCreateWhiteSharkAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateWhiteSharkAmmoResult.getStaticTechLevel());
        assertEquals("210,TM", actualCreateWhiteSharkAmmoResult.getRulesRefs());
        assertEquals(14000.0, actualCreateWhiteSharkAmmoResult.getRawCost(), 0.0);
        assertEquals(2195, actualCreateWhiteSharkAmmoResult.getPrototypeDate());
        assertEquals("Ammo White Shark", actualCreateWhiteSharkAmmoResult.getInternalName());
        assertEquals("E/D-E(F)-E-D", actualCreateWhiteSharkAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateWhiteSharkAmmoResult.flags;
        assertSame(expectedFlags, actualCreateWhiteSharkAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateWhiteSharkAmmoResult.getExtinctionDate());
    }

    private void createKillerWhaleAmmo(AmmoType actualCreateKillerWhaleAmmoResult) {
        assertEquals(50.0, actualCreateKillerWhaleAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateKillerWhaleAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateKillerWhaleAmmoResult.svslots);
        assertEquals("", actualCreateKillerWhaleAmmoResult.shortName);
        assertNull(actualCreateKillerWhaleAmmoResult.modes);
        assertTrue(actualCreateKillerWhaleAmmoResult.explosive);
        assertEquals(1, actualCreateKillerWhaleAmmoResult.criticals);
        assertEquals(96.0, actualCreateKillerWhaleAmmoResult.bv, 0.0);
        assertFalse(actualCreateKillerWhaleAmmoResult.isSpreadable());
        assertFalse(actualCreateKillerWhaleAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateKillerWhaleAmmoResult.isMixedTech());
        assertTrue(actualCreateKillerWhaleAmmoResult.isHittable());
        assertFalse(actualCreateKillerWhaleAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateKillerWhaleAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateKillerWhaleAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateKillerWhaleAmmoResult.getSubType());
        assertEquals("", actualCreateKillerWhaleAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateKillerWhaleAmmoResult.getStaticTechLevel());
        assertEquals("210,TM", actualCreateKillerWhaleAmmoResult.getRulesRefs());
        assertEquals(20000.0, actualCreateKillerWhaleAmmoResult.getRawCost(), 0.0);
        assertEquals(2195, actualCreateKillerWhaleAmmoResult.getPrototypeDate());
        assertEquals("Ammo Killer Whale", actualCreateKillerWhaleAmmoResult.getInternalName());
        assertEquals("E/D-E(F)-E-D", actualCreateKillerWhaleAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateKillerWhaleAmmoResult.flags;
        assertSame(expectedFlags, actualCreateKillerWhaleAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateKillerWhaleAmmoResult.getExtinctionDate());
    }

    private void createBarracudaTAmmo(AmmoType actualCreateBarracudaTAmmoResult) {
        assertEquals(30.0, actualCreateBarracudaTAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBarracudaTAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBarracudaTAmmoResult.svslots);
        assertEquals("", actualCreateBarracudaTAmmoResult.shortName);
        assertNull(actualCreateBarracudaTAmmoResult.modes);
        assertTrue(actualCreateBarracudaTAmmoResult.explosive);
        assertEquals(1, actualCreateBarracudaTAmmoResult.criticals);
        assertEquals(65.0, actualCreateBarracudaTAmmoResult.bv, 0.0);
        assertFalse(actualCreateBarracudaTAmmoResult.isSpreadable());
        assertFalse(actualCreateBarracudaTAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBarracudaTAmmoResult.isMixedTech());
        assertTrue(actualCreateBarracudaTAmmoResult.isHittable());
        assertFalse(actualCreateBarracudaTAmmoResult.hasInstantModeSwitch());
        assertEquals(WeaponType.DAMAGE_BY_CLUSTERTABLE, actualCreateBarracudaTAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateBarracudaTAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBarracudaTAmmoResult.getSubType());
        assertEquals("", actualCreateBarracudaTAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBarracudaTAmmoResult.getStaticTechLevel());
        assertEquals("251,TM", actualCreateBarracudaTAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBarracudaTAmmoResult.getReintroductionDate());
        assertEquals(8000.0, actualCreateBarracudaTAmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateBarracudaTAmmoResult.getPrototypeDate());
        assertEquals("Ammo Barracuda-T", actualCreateBarracudaTAmmoResult.getInternalName());
        assertEquals("F/X-X-E-D", actualCreateBarracudaTAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBarracudaTAmmoResult.flags;
        assertSame(expectedFlags, actualCreateBarracudaTAmmoResult.getFlags());
    }

    private void createWhiteSharkTAmmo(AmmoType actualCreateWhiteSharkTAmmoResult) {
        assertEquals(40.0, actualCreateWhiteSharkTAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateWhiteSharkTAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateWhiteSharkTAmmoResult.svslots);
        assertEquals("", actualCreateWhiteSharkTAmmoResult.shortName);
        assertNull(actualCreateWhiteSharkTAmmoResult.modes);
        assertTrue(actualCreateWhiteSharkTAmmoResult.explosive);
        assertEquals(1, actualCreateWhiteSharkTAmmoResult.criticals);
        assertEquals(72.0, actualCreateWhiteSharkTAmmoResult.bv, 0.0);
        assertFalse(actualCreateWhiteSharkTAmmoResult.isSpreadable());
        assertFalse(actualCreateWhiteSharkTAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateWhiteSharkTAmmoResult.isMixedTech());
        assertTrue(actualCreateWhiteSharkTAmmoResult.isHittable());
        assertFalse(actualCreateWhiteSharkTAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateWhiteSharkTAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateWhiteSharkTAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateWhiteSharkTAmmoResult.getSubType());
        assertEquals("", actualCreateWhiteSharkTAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateWhiteSharkTAmmoResult.getStaticTechLevel());
        assertEquals("251,TM", actualCreateWhiteSharkTAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateWhiteSharkTAmmoResult.getReintroductionDate());
        assertEquals(14000.0, actualCreateWhiteSharkTAmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateWhiteSharkTAmmoResult.getPrototypeDate());
        assertEquals("Ammo White Shark-T", actualCreateWhiteSharkTAmmoResult.getInternalName());
        assertEquals("F/X-X-E-D", actualCreateWhiteSharkTAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateWhiteSharkTAmmoResult.flags;
        assertSame(expectedFlags, actualCreateWhiteSharkTAmmoResult.getFlags());
    }

    private void createKillerWhaleTAmmo(AmmoType actualCreateKillerWhaleTAmmoResult) {
        assertEquals(50.0, actualCreateKillerWhaleTAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateKillerWhaleTAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateKillerWhaleTAmmoResult.svslots);
        assertEquals("", actualCreateKillerWhaleTAmmoResult.shortName);
        assertNull(actualCreateKillerWhaleTAmmoResult.modes);
        assertTrue(actualCreateKillerWhaleTAmmoResult.explosive);
        assertEquals(1, actualCreateKillerWhaleTAmmoResult.criticals);
        assertEquals(96.0, actualCreateKillerWhaleTAmmoResult.bv, 0.0);
        assertFalse(actualCreateKillerWhaleTAmmoResult.isSpreadable());
        assertFalse(actualCreateKillerWhaleTAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateKillerWhaleTAmmoResult.isMixedTech());
        assertTrue(actualCreateKillerWhaleTAmmoResult.isHittable());
        assertFalse(actualCreateKillerWhaleTAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateKillerWhaleTAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateKillerWhaleTAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateKillerWhaleTAmmoResult.getSubType());
        assertEquals("", actualCreateKillerWhaleTAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateKillerWhaleTAmmoResult.getStaticTechLevel());
        assertEquals("251,TM", actualCreateKillerWhaleTAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateKillerWhaleTAmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateKillerWhaleTAmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateKillerWhaleTAmmoResult.getPrototypeDate());
        assertEquals("Ammo Killer Whale-T", actualCreateKillerWhaleTAmmoResult.getInternalName());
        assertEquals("F/X-X-E-D", actualCreateKillerWhaleTAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateKillerWhaleTAmmoResult.flags;
        assertSame(expectedFlags, actualCreateKillerWhaleTAmmoResult.getFlags());
    }

    private void createKrakenAmmo(AmmoType actualCreateKrakenAmmoResult) {
        assertEquals(100.0, actualCreateKrakenAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateKrakenAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateKrakenAmmoResult.svslots);
        assertEquals("", actualCreateKrakenAmmoResult.shortName);
        assertNull(actualCreateKrakenAmmoResult.modes);
        assertTrue(actualCreateKrakenAmmoResult.explosive);
        assertEquals(1, actualCreateKrakenAmmoResult.criticals);
        assertEquals(288.0, actualCreateKrakenAmmoResult.bv, 0.0);
        assertFalse(actualCreateKrakenAmmoResult.isSpreadable());
        assertFalse(actualCreateKrakenAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateKrakenAmmoResult.isMixedTech());
        assertTrue(actualCreateKrakenAmmoResult.isHittable());
        assertFalse(actualCreateKrakenAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateKrakenAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateKrakenAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateKrakenAmmoResult.getSubType());
        assertEquals("", actualCreateKrakenAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateKrakenAmmoResult.getStaticTechLevel());
        assertEquals("251,TM", actualCreateKrakenAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateKrakenAmmoResult.getReintroductionDate());
        assertEquals(55000.0, actualCreateKrakenAmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateKrakenAmmoResult.getPrototypeDate());
        assertEquals("Ammo KrakenT", actualCreateKrakenAmmoResult.getInternalName());
        assertEquals("F/X-X-E-D", actualCreateKrakenAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateKrakenAmmoResult.flags;
        assertSame(expectedFlags, actualCreateKrakenAmmoResult.getFlags());
    }

    private void createKrakenMAmmo(AmmoType actualCreateKrakenMAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateKrakenMAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateKrakenMAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateKrakenMAmmoResult.svslots);
        assertEquals("", actualCreateKrakenMAmmoResult.shortName);
        assertNull(actualCreateKrakenMAmmoResult.modes);
        assertTrue(actualCreateKrakenMAmmoResult.explosive);
        assertEquals(1, actualCreateKrakenMAmmoResult.criticals);
        assertEquals(288.0, actualCreateKrakenMAmmoResult.bv, 0.0);
        assertFalse(actualCreateKrakenMAmmoResult.isSpreadable());
        assertFalse(actualCreateKrakenMAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateKrakenMAmmoResult.isMixedTech());
        assertTrue(actualCreateKrakenMAmmoResult.isHittable());
        assertFalse(actualCreateKrakenMAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateKrakenMAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateKrakenMAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateKrakenMAmmoResult.getSubType());
        assertEquals("", actualCreateKrakenMAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateKrakenMAmmoResult.getStaticTechLevel());
        assertEquals("Unoffical", actualCreateKrakenMAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateKrakenMAmmoResult.getReintroductionDate());
        assertEquals(55000.0, actualCreateKrakenMAmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateKrakenMAmmoResult.getPrototypeDate());
        assertEquals("Ammo Kraken", actualCreateKrakenMAmmoResult.getInternalName());
        assertEquals("F/X-X-E-D", actualCreateKrakenMAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateKrakenMAmmoResult.flags;
        assertSame(expectedFlags, actualCreateKrakenMAmmoResult.getFlags());
    }

    private void createScreenLauncherAmmo(AmmoType actualCreateScreenLauncherAmmoResult) {
        assertEquals(10.0, actualCreateScreenLauncherAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateScreenLauncherAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateScreenLauncherAmmoResult.svslots);
        assertEquals("", actualCreateScreenLauncherAmmoResult.shortName);
        assertNull(actualCreateScreenLauncherAmmoResult.modes);
        assertTrue(actualCreateScreenLauncherAmmoResult.explosive);
        assertEquals(1, actualCreateScreenLauncherAmmoResult.criticals);
        assertEquals(20.0, actualCreateScreenLauncherAmmoResult.bv, 0.0);
        assertFalse(actualCreateScreenLauncherAmmoResult.isSpreadable());
        assertFalse(actualCreateScreenLauncherAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateScreenLauncherAmmoResult.isMixedTech());
        assertTrue(actualCreateScreenLauncherAmmoResult.isHittable());
        assertFalse(actualCreateScreenLauncherAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateScreenLauncherAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateScreenLauncherAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateScreenLauncherAmmoResult.getSubType());
        assertEquals("", actualCreateScreenLauncherAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateScreenLauncherAmmoResult.getStaticTechLevel());
        assertEquals("237,TM", actualCreateScreenLauncherAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateScreenLauncherAmmoResult.getReintroductionDate());
        assertEquals(10000.0, actualCreateScreenLauncherAmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateScreenLauncherAmmoResult.getPrototypeDate());
        assertEquals("Ammo Screen", actualCreateScreenLauncherAmmoResult.getInternalName());
        assertEquals("F/X-X-E-D", actualCreateScreenLauncherAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateScreenLauncherAmmoResult.flags;
        assertSame(expectedFlags, actualCreateScreenLauncherAmmoResult.getFlags());
    }

    private void createLightSCCAmmo(AmmoType actualCreateLightSCCAmmoResult) {
        assertEquals(0.5, actualCreateLightSCCAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateLightSCCAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateLightSCCAmmoResult.svslots);
        assertEquals("", actualCreateLightSCCAmmoResult.shortName);
        assertNull(actualCreateLightSCCAmmoResult.modes);
        assertTrue(actualCreateLightSCCAmmoResult.explosive);
        assertEquals(1, actualCreateLightSCCAmmoResult.criticals);
        assertEquals(47.0, actualCreateLightSCCAmmoResult.bv, 0.0);
        assertFalse(actualCreateLightSCCAmmoResult.isSpreadable());
        assertFalse(actualCreateLightSCCAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateLightSCCAmmoResult.isMixedTech());
        assertTrue(actualCreateLightSCCAmmoResult.isHittable());
        assertFalse(actualCreateLightSCCAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateLightSCCAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateLightSCCAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateLightSCCAmmoResult.getSubType());
        assertEquals("", actualCreateLightSCCAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateLightSCCAmmoResult.getStaticTechLevel());
        assertEquals("343,TO", actualCreateLightSCCAmmoResult.getRulesRefs());
        assertEquals(10000.0, actualCreateLightSCCAmmoResult.getRawCost(), 0.0);
        assertEquals(3065, actualCreateLightSCCAmmoResult.getPrototypeDate());
        assertEquals("Ammo Light SCC", actualCreateLightSCCAmmoResult.getInternalName());
        assertEquals("E/X-X-F-D", actualCreateLightSCCAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateLightSCCAmmoResult.flags;
        assertSame(expectedFlags, actualCreateLightSCCAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateLightSCCAmmoResult.getExtinctionDate());
    }

    private void createMediumSCCAmmo(AmmoType actualCreateMediumSCCAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateMediumSCCAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateMediumSCCAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateMediumSCCAmmoResult.svslots);
        assertEquals("", actualCreateMediumSCCAmmoResult.shortName);
        assertNull(actualCreateMediumSCCAmmoResult.modes);
        assertTrue(actualCreateMediumSCCAmmoResult.explosive);
        assertEquals(1, actualCreateMediumSCCAmmoResult.criticals);
        assertEquals(89.0, actualCreateMediumSCCAmmoResult.bv, 0.0);
        assertFalse(actualCreateMediumSCCAmmoResult.isSpreadable());
        assertFalse(actualCreateMediumSCCAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateMediumSCCAmmoResult.isMixedTech());
        assertTrue(actualCreateMediumSCCAmmoResult.isHittable());
        assertFalse(actualCreateMediumSCCAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateMediumSCCAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateMediumSCCAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateMediumSCCAmmoResult.getSubType());
        assertEquals("", actualCreateMediumSCCAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateMediumSCCAmmoResult.getStaticTechLevel());
        assertEquals("343,TO", actualCreateMediumSCCAmmoResult.getRulesRefs());
        assertEquals(18000.0, actualCreateMediumSCCAmmoResult.getRawCost(), 0.0);
        assertEquals(3065, actualCreateMediumSCCAmmoResult.getPrototypeDate());
        assertEquals("Ammo Medium SCC", actualCreateMediumSCCAmmoResult.getInternalName());
        assertEquals("E/X-X-F-D", actualCreateMediumSCCAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateMediumSCCAmmoResult.flags;
        assertSame(expectedFlags, actualCreateMediumSCCAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateMediumSCCAmmoResult.getExtinctionDate());
    }

    private void createHeavySCCAmmo(AmmoType actualCreateHeavySCCAmmoResult) {
        assertEquals(2.0, actualCreateHeavySCCAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateHeavySCCAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateHeavySCCAmmoResult.svslots);
        assertEquals("", actualCreateHeavySCCAmmoResult.shortName);
        assertNull(actualCreateHeavySCCAmmoResult.modes);
        assertTrue(actualCreateHeavySCCAmmoResult.explosive);
        assertEquals(1, actualCreateHeavySCCAmmoResult.criticals);
        assertEquals(124.0, actualCreateHeavySCCAmmoResult.bv, 0.0);
        assertFalse(actualCreateHeavySCCAmmoResult.isSpreadable());
        assertFalse(actualCreateHeavySCCAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateHeavySCCAmmoResult.isMixedTech());
        assertTrue(actualCreateHeavySCCAmmoResult.isHittable());
        assertFalse(actualCreateHeavySCCAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateHeavySCCAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateHeavySCCAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateHeavySCCAmmoResult.getSubType());
        assertEquals("", actualCreateHeavySCCAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateHeavySCCAmmoResult.getStaticTechLevel());
        assertEquals("343,TO", actualCreateHeavySCCAmmoResult.getRulesRefs());
        assertEquals(25000.0, actualCreateHeavySCCAmmoResult.getRawCost(), 0.0);
        assertEquals(3065, actualCreateHeavySCCAmmoResult.getPrototypeDate());
        assertEquals("Ammo Heavy SCC", actualCreateHeavySCCAmmoResult.getInternalName());
        assertEquals("E/X-X-F-D", actualCreateHeavySCCAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateHeavySCCAmmoResult.flags;
        assertSame(expectedFlags, actualCreateHeavySCCAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateHeavySCCAmmoResult.getExtinctionDate());
    }

    private void createMantaRayAmmo(AmmoType actualCreateMantaRayAmmoResult) {
        assertEquals(18.0, actualCreateMantaRayAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateMantaRayAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateMantaRayAmmoResult.svslots);
        assertEquals("", actualCreateMantaRayAmmoResult.shortName);
        assertNull(actualCreateMantaRayAmmoResult.modes);
        assertTrue(actualCreateMantaRayAmmoResult.explosive);
        assertEquals(1, actualCreateMantaRayAmmoResult.criticals);
        assertEquals(50.0, actualCreateMantaRayAmmoResult.bv, 0.0);
        assertFalse(actualCreateMantaRayAmmoResult.isSpreadable());
        assertFalse(actualCreateMantaRayAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateMantaRayAmmoResult.isMixedTech());
        assertTrue(actualCreateMantaRayAmmoResult.isHittable());
        assertFalse(actualCreateMantaRayAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateMantaRayAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateMantaRayAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateMantaRayAmmoResult.getSubType());
        assertEquals("", actualCreateMantaRayAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateMantaRayAmmoResult.getStaticTechLevel());
        assertEquals("345,TO", actualCreateMantaRayAmmoResult.getRulesRefs());
        assertEquals(30000.0, actualCreateMantaRayAmmoResult.getRawCost(), 0.0);
        assertEquals(3061, actualCreateMantaRayAmmoResult.getPrototypeDate());
        assertEquals("Ammo Manta Ray", actualCreateMantaRayAmmoResult.getInternalName());
        assertEquals("E/X-X-F-D", actualCreateMantaRayAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateMantaRayAmmoResult.flags;
        assertSame(expectedFlags, actualCreateMantaRayAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateMantaRayAmmoResult.getExtinctionDate());
    }

    private void createSwordfishAmmo(AmmoType actualCreateSwordfishAmmoResult) {
        assertEquals(15.0, actualCreateSwordfishAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateSwordfishAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateSwordfishAmmoResult.svslots);
        assertEquals("", actualCreateSwordfishAmmoResult.shortName);
        assertNull(actualCreateSwordfishAmmoResult.modes);
        assertTrue(actualCreateSwordfishAmmoResult.explosive);
        assertEquals(1, actualCreateSwordfishAmmoResult.criticals);
        assertEquals(40.0, actualCreateSwordfishAmmoResult.bv, 0.0);
        assertFalse(actualCreateSwordfishAmmoResult.isSpreadable());
        assertFalse(actualCreateSwordfishAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateSwordfishAmmoResult.isMixedTech());
        assertTrue(actualCreateSwordfishAmmoResult.isHittable());
        assertFalse(actualCreateSwordfishAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateSwordfishAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateSwordfishAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateSwordfishAmmoResult.getSubType());
        assertEquals("", actualCreateSwordfishAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateSwordfishAmmoResult.getStaticTechLevel());
        assertEquals("345,TO", actualCreateSwordfishAmmoResult.getRulesRefs());
        assertEquals(25000.0, actualCreateSwordfishAmmoResult.getRawCost(), 0.0);
        assertEquals(3061, actualCreateSwordfishAmmoResult.getPrototypeDate());
        assertEquals("Ammo Swordfish", actualCreateSwordfishAmmoResult.getInternalName());
        assertEquals("E/X-X-F-D", actualCreateSwordfishAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateSwordfishAmmoResult.flags;
        assertSame(expectedFlags, actualCreateSwordfishAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateSwordfishAmmoResult.getExtinctionDate());
    }

    private void createStingrayAmmo(AmmoType actualCreateStingrayAmmoResult) {
        assertEquals(12.0, actualCreateStingrayAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateStingrayAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateStingrayAmmoResult.svslots);
        assertEquals("", actualCreateStingrayAmmoResult.shortName);
        assertNull(actualCreateStingrayAmmoResult.modes);
        assertTrue(actualCreateStingrayAmmoResult.explosive);
        assertEquals(1, actualCreateStingrayAmmoResult.criticals);
        assertEquals(62.0, actualCreateStingrayAmmoResult.bv, 0.0);
        assertFalse(actualCreateStingrayAmmoResult.isSpreadable());
        assertFalse(actualCreateStingrayAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateStingrayAmmoResult.isMixedTech());
        assertTrue(actualCreateStingrayAmmoResult.isHittable());
        assertFalse(actualCreateStingrayAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateStingrayAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateStingrayAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateStingrayAmmoResult.getSubType());
        assertEquals("", actualCreateStingrayAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateStingrayAmmoResult.getStaticTechLevel());
        assertEquals("345,TO", actualCreateStingrayAmmoResult.getRulesRefs());
        assertEquals(19000.0, actualCreateStingrayAmmoResult.getRawCost(), 0.0);
        assertEquals(3061, actualCreateStingrayAmmoResult.getPrototypeDate());
        assertEquals("Ammo Stringray", actualCreateStingrayAmmoResult.getInternalName());
        assertEquals("E/X-X-F-D", actualCreateStingrayAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateStingrayAmmoResult.flags;
        assertSame(expectedFlags, actualCreateStingrayAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateStingrayAmmoResult.getExtinctionDate());
    }

    private void createPiranhaAmmo(AmmoType actualCreatePiranhaAmmoResult) {
        assertEquals(10.0, actualCreatePiranhaAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreatePiranhaAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreatePiranhaAmmoResult.svslots);
        assertEquals("", actualCreatePiranhaAmmoResult.shortName);
        assertNull(actualCreatePiranhaAmmoResult.modes);
        assertTrue(actualCreatePiranhaAmmoResult.explosive);
        assertEquals(1, actualCreatePiranhaAmmoResult.criticals);
        assertEquals(84.0, actualCreatePiranhaAmmoResult.bv, 0.0);
        assertFalse(actualCreatePiranhaAmmoResult.isSpreadable());
        assertFalse(actualCreatePiranhaAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreatePiranhaAmmoResult.isMixedTech());
        assertTrue(actualCreatePiranhaAmmoResult.isHittable());
        assertFalse(actualCreatePiranhaAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreatePiranhaAmmoResult.getToHitModifier());
        assertEquals(4, actualCreatePiranhaAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreatePiranhaAmmoResult.getSubType());
        assertEquals("", actualCreatePiranhaAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreatePiranhaAmmoResult.getStaticTechLevel());
        assertEquals("345,TO", actualCreatePiranhaAmmoResult.getRulesRefs());
        assertEquals(15000.0, actualCreatePiranhaAmmoResult.getRawCost(), 0.0);
        assertEquals(3061, actualCreatePiranhaAmmoResult.getPrototypeDate());
        assertEquals("Ammo Piranha", actualCreatePiranhaAmmoResult.getInternalName());
        assertEquals("E/X-X-F-D", actualCreatePiranhaAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreatePiranhaAmmoResult.flags;
        assertSame(expectedFlags, actualCreatePiranhaAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreatePiranhaAmmoResult.getExtinctionDate());
    }

    private void createAR10BarracudaAmmo(AmmoType actualCreateAR10BarracudaAmmoResult) {
        assertEquals(30.0, actualCreateAR10BarracudaAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateAR10BarracudaAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateAR10BarracudaAmmoResult.svslots);
        assertEquals("", actualCreateAR10BarracudaAmmoResult.shortName);
        assertNull(actualCreateAR10BarracudaAmmoResult.modes);
        assertTrue(actualCreateAR10BarracudaAmmoResult.explosive);
        assertEquals(1, actualCreateAR10BarracudaAmmoResult.criticals);
        assertEquals(65.0, actualCreateAR10BarracudaAmmoResult.bv, 0.0);
        assertFalse(actualCreateAR10BarracudaAmmoResult.isSpreadable());
        assertFalse(actualCreateAR10BarracudaAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateAR10BarracudaAmmoResult.isMixedTech());
        assertTrue(actualCreateAR10BarracudaAmmoResult.isHittable());
        assertFalse(actualCreateAR10BarracudaAmmoResult.hasInstantModeSwitch());
        assertEquals(WeaponType.DAMAGE_BY_CLUSTERTABLE, actualCreateAR10BarracudaAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateAR10BarracudaAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateAR10BarracudaAmmoResult.getSubType());
        assertEquals("", actualCreateAR10BarracudaAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateAR10BarracudaAmmoResult.getStaticTechLevel());
        assertEquals("210,TM", actualCreateAR10BarracudaAmmoResult.getRulesRefs());
        assertEquals(8000.0, actualCreateAR10BarracudaAmmoResult.getRawCost(), 0.0);
        assertEquals(2535, actualCreateAR10BarracudaAmmoResult.getPrototypeDate());
        assertEquals("Ammo AR10 Barracuda", actualCreateAR10BarracudaAmmoResult.getInternalName());
        assertEquals("E/D-E(F)-E-D", actualCreateAR10BarracudaAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateAR10BarracudaAmmoResult.flags;
        assertSame(expectedFlags, actualCreateAR10BarracudaAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateAR10BarracudaAmmoResult.getExtinctionDate());
    }

    private void createAR10KillerWhaleAmmo(AmmoType actualCreateAR10KillerWhaleAmmoResult) {
        assertEquals(50.0, actualCreateAR10KillerWhaleAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateAR10KillerWhaleAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateAR10KillerWhaleAmmoResult.svslots);
        assertEquals("", actualCreateAR10KillerWhaleAmmoResult.shortName);
        assertNull(actualCreateAR10KillerWhaleAmmoResult.modes);
        assertTrue(actualCreateAR10KillerWhaleAmmoResult.explosive);
        assertEquals(1, actualCreateAR10KillerWhaleAmmoResult.criticals);
        assertEquals(96.0, actualCreateAR10KillerWhaleAmmoResult.bv, 0.0);
        assertFalse(actualCreateAR10KillerWhaleAmmoResult.isSpreadable());
        assertFalse(actualCreateAR10KillerWhaleAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateAR10KillerWhaleAmmoResult.isMixedTech());
        assertTrue(actualCreateAR10KillerWhaleAmmoResult.isHittable());
        assertFalse(actualCreateAR10KillerWhaleAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateAR10KillerWhaleAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateAR10KillerWhaleAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateAR10KillerWhaleAmmoResult.getSubType());
        assertEquals("", actualCreateAR10KillerWhaleAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateAR10KillerWhaleAmmoResult.getStaticTechLevel());
        assertEquals("210,TM", actualCreateAR10KillerWhaleAmmoResult.getRulesRefs());
        assertEquals(20000.0, actualCreateAR10KillerWhaleAmmoResult.getRawCost(), 0.0);
        assertEquals(2535, actualCreateAR10KillerWhaleAmmoResult.getPrototypeDate());
        assertEquals("Ammo AR10 Killer Whale", actualCreateAR10KillerWhaleAmmoResult.getInternalName());
        assertEquals("E/D-E(F)-E-D", actualCreateAR10KillerWhaleAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateAR10KillerWhaleAmmoResult.flags;
        assertSame(expectedFlags, actualCreateAR10KillerWhaleAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateAR10KillerWhaleAmmoResult.getExtinctionDate());
    }

    private void createAR10WhiteSharkAmmo(AmmoType actualCreateAR10WhiteSharkAmmoResult) {
        assertEquals(40.0, actualCreateAR10WhiteSharkAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateAR10WhiteSharkAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateAR10WhiteSharkAmmoResult.svslots);
        assertEquals("", actualCreateAR10WhiteSharkAmmoResult.shortName);
        assertNull(actualCreateAR10WhiteSharkAmmoResult.modes);
        assertTrue(actualCreateAR10WhiteSharkAmmoResult.explosive);
        assertEquals(1, actualCreateAR10WhiteSharkAmmoResult.criticals);
        assertEquals(72.0, actualCreateAR10WhiteSharkAmmoResult.bv, 0.0);
        assertFalse(actualCreateAR10WhiteSharkAmmoResult.isSpreadable());
        assertFalse(actualCreateAR10WhiteSharkAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateAR10WhiteSharkAmmoResult.isMixedTech());
        assertTrue(actualCreateAR10WhiteSharkAmmoResult.isHittable());
        assertFalse(actualCreateAR10WhiteSharkAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateAR10WhiteSharkAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateAR10WhiteSharkAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateAR10WhiteSharkAmmoResult.getSubType());
        assertEquals("", actualCreateAR10WhiteSharkAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateAR10WhiteSharkAmmoResult.getStaticTechLevel());
        assertEquals("210,TM", actualCreateAR10WhiteSharkAmmoResult.getRulesRefs());
        assertEquals(14000.0, actualCreateAR10WhiteSharkAmmoResult.getRawCost(), 0.0);
        assertEquals(2535, actualCreateAR10WhiteSharkAmmoResult.getPrototypeDate());
        assertEquals("Ammo AR10 White Shark", actualCreateAR10WhiteSharkAmmoResult.getInternalName());
        assertEquals("E/D-E(F)-E-D", actualCreateAR10WhiteSharkAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateAR10WhiteSharkAmmoResult.flags;
        assertSame(expectedFlags, actualCreateAR10WhiteSharkAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateAR10WhiteSharkAmmoResult.getExtinctionDate());
    }

    private void createAR10BarracudaTAmmo(AmmoType actualCreateAR10BarracudaTAmmoResult) {
        assertEquals(30.0, actualCreateAR10BarracudaTAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateAR10BarracudaTAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateAR10BarracudaTAmmoResult.svslots);
        assertEquals("", actualCreateAR10BarracudaTAmmoResult.shortName);
        assertNull(actualCreateAR10BarracudaTAmmoResult.modes);
        assertTrue(actualCreateAR10BarracudaTAmmoResult.explosive);
        assertEquals(1, actualCreateAR10BarracudaTAmmoResult.criticals);
        assertEquals(65.0, actualCreateAR10BarracudaTAmmoResult.bv, 0.0);
        assertFalse(actualCreateAR10BarracudaTAmmoResult.isSpreadable());
        assertFalse(actualCreateAR10BarracudaTAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateAR10BarracudaTAmmoResult.isMixedTech());
        assertTrue(actualCreateAR10BarracudaTAmmoResult.isHittable());
        assertFalse(actualCreateAR10BarracudaTAmmoResult.hasInstantModeSwitch());
        assertEquals(WeaponType.DAMAGE_BY_CLUSTERTABLE, actualCreateAR10BarracudaTAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateAR10BarracudaTAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateAR10BarracudaTAmmoResult.getSubType());
        assertEquals("", actualCreateAR10BarracudaTAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateAR10BarracudaTAmmoResult.getStaticTechLevel());
        assertEquals("251,TW", actualCreateAR10BarracudaTAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateAR10BarracudaTAmmoResult.getReintroductionDate());
        assertEquals(8000.0, actualCreateAR10BarracudaTAmmoResult.getRawCost(), 0.0);
        assertEquals(3048, actualCreateAR10BarracudaTAmmoResult.getPrototypeDate());
        assertEquals("Ammo AR10 Barracuda-T", actualCreateAR10BarracudaTAmmoResult.getInternalName());
        assertEquals("F/X-X-E-D", actualCreateAR10BarracudaTAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateAR10BarracudaTAmmoResult.flags;
        assertSame(expectedFlags, actualCreateAR10BarracudaTAmmoResult.getFlags());
    }

    private void createAR10KillerWhaleTAmmo(AmmoType actualCreateAR10KillerWhaleTAmmoResult) {
        assertEquals(50.0, actualCreateAR10KillerWhaleTAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateAR10KillerWhaleTAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateAR10KillerWhaleTAmmoResult.svslots);
        assertEquals("", actualCreateAR10KillerWhaleTAmmoResult.shortName);
        assertNull(actualCreateAR10KillerWhaleTAmmoResult.modes);
        assertTrue(actualCreateAR10KillerWhaleTAmmoResult.explosive);
        assertEquals(1, actualCreateAR10KillerWhaleTAmmoResult.criticals);
        assertEquals(96.0, actualCreateAR10KillerWhaleTAmmoResult.bv, 0.0);
        assertFalse(actualCreateAR10KillerWhaleTAmmoResult.isSpreadable());
        assertFalse(actualCreateAR10KillerWhaleTAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateAR10KillerWhaleTAmmoResult.isMixedTech());
        assertTrue(actualCreateAR10KillerWhaleTAmmoResult.isHittable());
        assertFalse(actualCreateAR10KillerWhaleTAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateAR10KillerWhaleTAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateAR10KillerWhaleTAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateAR10KillerWhaleTAmmoResult.getSubType());
        assertEquals("", actualCreateAR10KillerWhaleTAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateAR10KillerWhaleTAmmoResult.getStaticTechLevel());
        assertEquals("251,TW", actualCreateAR10KillerWhaleTAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateAR10KillerWhaleTAmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateAR10KillerWhaleTAmmoResult.getRawCost(), 0.0);
        assertEquals(3048, actualCreateAR10KillerWhaleTAmmoResult.getPrototypeDate());
        assertEquals("Ammo AR10 Killer Whale-T", actualCreateAR10KillerWhaleTAmmoResult.getInternalName());
        assertEquals("F/X-X-E-D", actualCreateAR10KillerWhaleTAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateAR10KillerWhaleTAmmoResult.flags;
        assertSame(expectedFlags, actualCreateAR10KillerWhaleTAmmoResult.getFlags());
    }

    private void createAR10WhiteSharkTAmmo(AmmoType actualCreateAR10WhiteSharkTAmmoResult) {
        assertEquals(40.0, actualCreateAR10WhiteSharkTAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateAR10WhiteSharkTAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateAR10WhiteSharkTAmmoResult.svslots);
        assertEquals("", actualCreateAR10WhiteSharkTAmmoResult.shortName);
        assertNull(actualCreateAR10WhiteSharkTAmmoResult.modes);
        assertTrue(actualCreateAR10WhiteSharkTAmmoResult.explosive);
        assertEquals(1, actualCreateAR10WhiteSharkTAmmoResult.criticals);
        assertEquals(72.0, actualCreateAR10WhiteSharkTAmmoResult.bv, 0.0);
        assertFalse(actualCreateAR10WhiteSharkTAmmoResult.isSpreadable());
        assertFalse(actualCreateAR10WhiteSharkTAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateAR10WhiteSharkTAmmoResult.isMixedTech());
        assertTrue(actualCreateAR10WhiteSharkTAmmoResult.isHittable());
        assertFalse(actualCreateAR10WhiteSharkTAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateAR10WhiteSharkTAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateAR10WhiteSharkTAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateAR10WhiteSharkTAmmoResult.getSubType());
        assertEquals("", actualCreateAR10WhiteSharkTAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateAR10WhiteSharkTAmmoResult.getStaticTechLevel());
        assertEquals("251,TW", actualCreateAR10WhiteSharkTAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateAR10WhiteSharkTAmmoResult.getReintroductionDate());
        assertEquals(14000.0, actualCreateAR10WhiteSharkTAmmoResult.getRawCost(), 0.0);
        assertEquals(3048, actualCreateAR10WhiteSharkTAmmoResult.getPrototypeDate());
        assertEquals("Ammo AR10 White Shark-T", actualCreateAR10WhiteSharkTAmmoResult.getInternalName());
        assertEquals("F/X-X-E-D", actualCreateAR10WhiteSharkTAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateAR10WhiteSharkTAmmoResult.flags;
        assertSame(expectedFlags, actualCreateAR10WhiteSharkTAmmoResult.getFlags());
    }

    private void createISNailRivetGunAmmo(AmmoType actualCreateISNailRivetGunAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISNailRivetGunAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISNailRivetGunAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISNailRivetGunAmmoResult.svslots);
        assertEquals("Nail/Rivet Gun", actualCreateISNailRivetGunAmmoResult.shortName);
        assertNull(actualCreateISNailRivetGunAmmoResult.modes);
        assertFalse(actualCreateISNailRivetGunAmmoResult.explosive);
        assertEquals(1, actualCreateISNailRivetGunAmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISNailRivetGunAmmoResult.bv, 0.0);
        assertFalse(actualCreateISNailRivetGunAmmoResult.isSpreadable());
        assertFalse(actualCreateISNailRivetGunAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISNailRivetGunAmmoResult.isMixedTech());
        assertTrue(actualCreateISNailRivetGunAmmoResult.isHittable());
        assertFalse(actualCreateISNailRivetGunAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISNailRivetGunAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISNailRivetGunAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISNailRivetGunAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISNailRivetGunAmmoResult.getStaticTechLevel());
        assertEquals("246,TM", actualCreateISNailRivetGunAmmoResult.getRulesRefs());
        assertEquals(300.0, actualCreateISNailRivetGunAmmoResult.getRawCost(), 0.0);
        assertEquals(2304, actualCreateISNailRivetGunAmmoResult.getPrototypeDate());
        assertEquals("Nail/Rivet Gun Ammo", actualCreateISNailRivetGunAmmoResult.getName());
        assertEquals("IS Ammo Nail/Rivet - Full", actualCreateISNailRivetGunAmmoResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISNailRivetGunAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISNailRivetGunAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISNailRivetGunAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISNailRivetGunAmmoResult.getExtinctionDate());
    }

    private void createISNailRivetGunAmmoHalf(AmmoType actualCreateISNailRivetGunAmmoHalfResult) {
        assertEquals(0.5, actualCreateISNailRivetGunAmmoHalfResult.tonnage, 0.0);
        assertEquals(0, actualCreateISNailRivetGunAmmoHalfResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISNailRivetGunAmmoHalfResult.svslots);
        assertEquals("Nail/Rivet Gun", actualCreateISNailRivetGunAmmoHalfResult.shortName);
        assertNull(actualCreateISNailRivetGunAmmoHalfResult.modes);
        assertFalse(actualCreateISNailRivetGunAmmoHalfResult.explosive);
        assertEquals(1, actualCreateISNailRivetGunAmmoHalfResult.criticals);
        assertEquals(0.5, actualCreateISNailRivetGunAmmoHalfResult.bv, 0.0);
        assertFalse(actualCreateISNailRivetGunAmmoHalfResult.isSpreadable());
        assertFalse(actualCreateISNailRivetGunAmmoHalfResult.isOmniFixedOnly());
        assertTrue(actualCreateISNailRivetGunAmmoHalfResult.isMixedTech());
        assertTrue(actualCreateISNailRivetGunAmmoHalfResult.isHittable());
        assertFalse(actualCreateISNailRivetGunAmmoHalfResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISNailRivetGunAmmoHalfResult.getToHitModifier());
        assertEquals(2, actualCreateISNailRivetGunAmmoHalfResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISNailRivetGunAmmoHalfResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISNailRivetGunAmmoHalfResult.getStaticTechLevel());
        assertEquals("246,TM", actualCreateISNailRivetGunAmmoHalfResult.getRulesRefs());
        assertEquals(150.0, actualCreateISNailRivetGunAmmoHalfResult.getRawCost(), 0.0);
        assertEquals(2304, actualCreateISNailRivetGunAmmoHalfResult.getPrototypeDate());
        assertEquals("Nail/Rivet Gun Ammo (Half-ton)", actualCreateISNailRivetGunAmmoHalfResult.getName());
        assertEquals("IS Ammo Nail/Rivet - Half", actualCreateISNailRivetGunAmmoHalfResult.getInternalName());
        assertEquals("C/C-C-C-C", actualCreateISNailRivetGunAmmoHalfResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISNailRivetGunAmmoHalfResult.flags;
        assertSame(expectedFlags, actualCreateISNailRivetGunAmmoHalfResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISNailRivetGunAmmoHalfResult.getExtinctionDate());
    }

    private void createISC3RemoteSensorAmmo(AmmoType actualCreateISC3RemoteSensorAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISC3RemoteSensorAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISC3RemoteSensorAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISC3RemoteSensorAmmoResult.svslots);
        assertEquals("C3 Remote Sensor", actualCreateISC3RemoteSensorAmmoResult.shortName);
        assertNull(actualCreateISC3RemoteSensorAmmoResult.modes);
        assertFalse(actualCreateISC3RemoteSensorAmmoResult.explosive);
        assertEquals(1, actualCreateISC3RemoteSensorAmmoResult.criticals);
        assertEquals(6.0, actualCreateISC3RemoteSensorAmmoResult.bv, 0.0);
        assertFalse(actualCreateISC3RemoteSensorAmmoResult.isSpreadable());
        assertFalse(actualCreateISC3RemoteSensorAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISC3RemoteSensorAmmoResult.isMixedTech());
        assertTrue(actualCreateISC3RemoteSensorAmmoResult.isHittable());
        assertFalse(actualCreateISC3RemoteSensorAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISC3RemoteSensorAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISC3RemoteSensorAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISC3RemoteSensorAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISC3RemoteSensorAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISC3RemoteSensorAmmoResult.getStandardRange());
        assertEquals("", actualCreateISC3RemoteSensorAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISC3RemoteSensorAmmoResult.getReintroductionDate());
        assertEquals(100000.0, actualCreateISC3RemoteSensorAmmoResult.getRawCost(), 0.0);
        assertEquals("C3 Remote Sensors", actualCreateISC3RemoteSensorAmmoResult.getName());
        assertEquals("ISC3Sensors", actualCreateISC3RemoteSensorAmmoResult.getInternalName());
        assertEquals("E/X-X-F-X", actualCreateISC3RemoteSensorAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISC3RemoteSensorAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISC3RemoteSensorAmmoResult.getFlags());
    }

    private void createISThunderbolt5Ammo(AmmoType actualCreateISThunderbolt5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISThunderbolt5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISThunderbolt5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISThunderbolt5AmmoResult.svslots);
        assertEquals("Thunderbolt 5", actualCreateISThunderbolt5AmmoResult.shortName);
        assertTrue(actualCreateISThunderbolt5AmmoResult.explosive);
        assertEquals(1, actualCreateISThunderbolt5AmmoResult.criticals);
        assertEquals(8.0, actualCreateISThunderbolt5AmmoResult.bv, 0.0);
        assertFalse(actualCreateISThunderbolt5AmmoResult.isSpreadable());
        assertFalse(actualCreateISThunderbolt5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISThunderbolt5AmmoResult.isMixedTech());
        assertTrue(actualCreateISThunderbolt5AmmoResult.isHittable());
        assertTrue(actualCreateISThunderbolt5AmmoResult.hasModes());
        assertFalse(actualCreateISThunderbolt5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISThunderbolt5AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISThunderbolt5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISThunderbolt5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISThunderbolt5AmmoResult.getStaticTechLevel());
        assertEquals("347,TO", actualCreateISThunderbolt5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISThunderbolt5AmmoResult.getReintroductionDate());
        assertEquals(50000.0, actualCreateISThunderbolt5AmmoResult.getRawCost(), 0.0);
        assertEquals(3052, actualCreateISThunderbolt5AmmoResult.getPrototypeDate());
        assertEquals("Thunderbolt 5 Ammo", actualCreateISThunderbolt5AmmoResult.getName());
        assertEquals("IS Ammo Thunderbolt-5", actualCreateISThunderbolt5AmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateISThunderbolt5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISThunderbolt5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISThunderbolt5AmmoResult.getFlags());
    }

    private void createISThunderbolt10Ammo(AmmoType actualCreateISThunderbolt10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISThunderbolt10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISThunderbolt10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISThunderbolt10AmmoResult.svslots);
        assertEquals("Thunderbolt 10", actualCreateISThunderbolt10AmmoResult.shortName);
        assertTrue(actualCreateISThunderbolt10AmmoResult.explosive);
        assertEquals(1, actualCreateISThunderbolt10AmmoResult.criticals);
        assertEquals(16.0, actualCreateISThunderbolt10AmmoResult.bv, 0.0);
        assertFalse(actualCreateISThunderbolt10AmmoResult.isSpreadable());
        assertFalse(actualCreateISThunderbolt10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISThunderbolt10AmmoResult.isMixedTech());
        assertTrue(actualCreateISThunderbolt10AmmoResult.isHittable());
        assertTrue(actualCreateISThunderbolt10AmmoResult.hasModes());
        assertFalse(actualCreateISThunderbolt10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISThunderbolt10AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISThunderbolt10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISThunderbolt10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISThunderbolt10AmmoResult.getStaticTechLevel());
        assertEquals("347,TO", actualCreateISThunderbolt10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISThunderbolt10AmmoResult.getReintroductionDate());
        assertEquals(50000.0, actualCreateISThunderbolt10AmmoResult.getRawCost(), 0.0);
        assertEquals(3052, actualCreateISThunderbolt10AmmoResult.getPrototypeDate());
        assertEquals("Thunderbolt 10 Ammo", actualCreateISThunderbolt10AmmoResult.getName());
        assertEquals("IS Ammo Thunderbolt-10", actualCreateISThunderbolt10AmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateISThunderbolt10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISThunderbolt10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISThunderbolt10AmmoResult.getFlags());
    }

    private void createISThunderbolt15Ammo(AmmoType actualCreateISThunderbolt15AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISThunderbolt15AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISThunderbolt15AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISThunderbolt15AmmoResult.svslots);
        assertEquals("Thunderbolt 15", actualCreateISThunderbolt15AmmoResult.shortName);
        assertTrue(actualCreateISThunderbolt15AmmoResult.explosive);
        assertEquals(1, actualCreateISThunderbolt15AmmoResult.criticals);
        assertEquals(29.0, actualCreateISThunderbolt15AmmoResult.bv, 0.0);
        assertFalse(actualCreateISThunderbolt15AmmoResult.isSpreadable());
        assertFalse(actualCreateISThunderbolt15AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISThunderbolt15AmmoResult.isMixedTech());
        assertTrue(actualCreateISThunderbolt15AmmoResult.isHittable());
        assertTrue(actualCreateISThunderbolt15AmmoResult.hasModes());
        assertFalse(actualCreateISThunderbolt15AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISThunderbolt15AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISThunderbolt15AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISThunderbolt15AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISThunderbolt15AmmoResult.getStaticTechLevel());
        assertEquals("347,TO", actualCreateISThunderbolt15AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISThunderbolt15AmmoResult.getReintroductionDate());
        assertEquals(50000.0, actualCreateISThunderbolt15AmmoResult.getRawCost(), 0.0);
        assertEquals(3052, actualCreateISThunderbolt15AmmoResult.getPrototypeDate());
        assertEquals("Thunderbolt 15 Ammo", actualCreateISThunderbolt15AmmoResult.getName());
        assertEquals("IS Ammo Thunderbolt-15", actualCreateISThunderbolt15AmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateISThunderbolt15AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISThunderbolt15AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISThunderbolt15AmmoResult.getFlags());
    }

    private void createISThunderbolt20Ammo(AmmoType actualCreateISThunderbolt20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISThunderbolt20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISThunderbolt20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISThunderbolt20AmmoResult.svslots);
        assertEquals("Thunderbolt 20", actualCreateISThunderbolt20AmmoResult.shortName);
        assertTrue(actualCreateISThunderbolt20AmmoResult.explosive);
        assertEquals(1, actualCreateISThunderbolt20AmmoResult.criticals);
        assertEquals(38.0, actualCreateISThunderbolt20AmmoResult.bv, 0.0);
        assertFalse(actualCreateISThunderbolt20AmmoResult.isSpreadable());
        assertFalse(actualCreateISThunderbolt20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISThunderbolt20AmmoResult.isMixedTech());
        assertTrue(actualCreateISThunderbolt20AmmoResult.isHittable());
        assertTrue(actualCreateISThunderbolt20AmmoResult.hasModes());
        assertFalse(actualCreateISThunderbolt20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISThunderbolt20AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISThunderbolt20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISThunderbolt20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISThunderbolt20AmmoResult.getStaticTechLevel());
        assertEquals("347,TO", actualCreateISThunderbolt20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISThunderbolt20AmmoResult.getReintroductionDate());
        assertEquals(50000.0, actualCreateISThunderbolt20AmmoResult.getRawCost(), 0.0);
        assertEquals(3052, actualCreateISThunderbolt20AmmoResult.getPrototypeDate());
        assertEquals("Thunderbolt 20 Ammo", actualCreateISThunderbolt20AmmoResult.getName());
        assertEquals("IS Ammo Thunderbolt-20", actualCreateISThunderbolt20AmmoResult.getInternalName());
        assertEquals("E/X-X-F-E", actualCreateISThunderbolt20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISThunderbolt20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISThunderbolt20AmmoResult.getFlags());
    }

    private void createISAC2pAmmo(AmmoType actualCreateISAC2pAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAC2pAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAC2pAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAC2pAmmoResult.svslots);
        assertEquals("AC/2p", actualCreateISAC2pAmmoResult.shortName);
        assertNull(actualCreateISAC2pAmmoResult.modes);
        assertTrue(actualCreateISAC2pAmmoResult.explosive);
        assertEquals(1, actualCreateISAC2pAmmoResult.criticals);
        assertEquals(5.0, actualCreateISAC2pAmmoResult.bv, 0.0);
        assertFalse(actualCreateISAC2pAmmoResult.isSpreadable());
        assertFalse(actualCreateISAC2pAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISAC2pAmmoResult.isMixedTech());
        assertTrue(actualCreateISAC2pAmmoResult.isHittable());
        assertFalse(actualCreateISAC2pAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAC2pAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISAC2pAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAC2pAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISAC2pAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISAC2pAmmoResult.getStandardRange());
        assertEquals("118, IO", actualCreateISAC2pAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISAC2pAmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateISAC2pAmmoResult.getRawCost(), 0.0);
        assertEquals("Primitive Prototype Autocannon/2 Ammo", actualCreateISAC2pAmmoResult.getName());
        assertEquals("IS Ammo AC/2 Primitive", actualCreateISAC2pAmmoResult.getInternalName());
        assertEquals("C/F-X-X-X", actualCreateISAC2pAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAC2pAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAC2pAmmoResult.getFlags());
    }

    private void createISAC5pAmmo(AmmoType actualCreateISAC5pAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAC5pAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAC5pAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAC5pAmmoResult.svslots);
        assertEquals("AC/5p", actualCreateISAC5pAmmoResult.shortName);
        assertNull(actualCreateISAC5pAmmoResult.modes);
        assertTrue(actualCreateISAC5pAmmoResult.explosive);
        assertEquals(1, actualCreateISAC5pAmmoResult.criticals);
        assertEquals(9.0, actualCreateISAC5pAmmoResult.bv, 0.0);
        assertFalse(actualCreateISAC5pAmmoResult.isSpreadable());
        assertFalse(actualCreateISAC5pAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISAC5pAmmoResult.isMixedTech());
        assertTrue(actualCreateISAC5pAmmoResult.isHittable());
        assertFalse(actualCreateISAC5pAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAC5pAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISAC5pAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAC5pAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISAC5pAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISAC5pAmmoResult.getStandardRange());
        assertEquals("118, IO", actualCreateISAC5pAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISAC5pAmmoResult.getReintroductionDate());
        assertEquals(4500.0, actualCreateISAC5pAmmoResult.getRawCost(), 0.0);
        assertEquals("Primitive Prototype Autocannon/5 Ammo", actualCreateISAC5pAmmoResult.getName());
        assertEquals("IS Ammo AC/5 Primitive", actualCreateISAC5pAmmoResult.getInternalName());
        assertEquals("C/F-X-X-X", actualCreateISAC5pAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAC5pAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAC5pAmmoResult.getFlags());
    }

    private void createISAC10pAmmo(AmmoType actualCreateISAC10pAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAC10pAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAC10pAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAC10pAmmoResult.svslots);
        assertEquals("AC/10p", actualCreateISAC10pAmmoResult.shortName);
        assertNull(actualCreateISAC10pAmmoResult.modes);
        assertTrue(actualCreateISAC10pAmmoResult.explosive);
        assertEquals(1, actualCreateISAC10pAmmoResult.criticals);
        assertEquals(12.0, actualCreateISAC10pAmmoResult.bv, 0.0);
        assertFalse(actualCreateISAC10pAmmoResult.isSpreadable());
        assertFalse(actualCreateISAC10pAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISAC10pAmmoResult.isMixedTech());
        assertTrue(actualCreateISAC10pAmmoResult.isHittable());
        assertFalse(actualCreateISAC10pAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAC10pAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISAC10pAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAC10pAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISAC10pAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISAC10pAmmoResult.getStandardRange());
        assertEquals("118, IO", actualCreateISAC10pAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISAC10pAmmoResult.getReintroductionDate());
        assertEquals(12000.0, actualCreateISAC10pAmmoResult.getRawCost(), 0.0);
        assertEquals("Primitive Prototype Autocannon/10 Ammo", actualCreateISAC10pAmmoResult.getName());
        assertEquals("IS Ammo AC/10 Primitive", actualCreateISAC10pAmmoResult.getInternalName());
        assertEquals("C/F-X-X-X", actualCreateISAC10pAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAC10pAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAC10pAmmoResult.getFlags());
    }

    private void createISAC20pAmmo(AmmoType actualCreateISAC20pAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAC20pAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAC20pAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAC20pAmmoResult.svslots);
        assertEquals("AC/20p", actualCreateISAC20pAmmoResult.shortName);
        assertNull(actualCreateISAC20pAmmoResult.modes);
        assertTrue(actualCreateISAC20pAmmoResult.explosive);
        assertEquals(1, actualCreateISAC20pAmmoResult.criticals);
        assertEquals(22.0, actualCreateISAC20pAmmoResult.bv, 0.0);
        assertFalse(actualCreateISAC20pAmmoResult.isSpreadable());
        assertFalse(actualCreateISAC20pAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISAC20pAmmoResult.isMixedTech());
        assertTrue(actualCreateISAC20pAmmoResult.isHittable());
        assertFalse(actualCreateISAC20pAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAC20pAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISAC20pAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAC20pAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISAC20pAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISAC20pAmmoResult.getStandardRange());
        assertEquals("118, IO", actualCreateISAC20pAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISAC20pAmmoResult.getReintroductionDate());
        assertEquals(10000.0, actualCreateISAC20pAmmoResult.getRawCost(), 0.0);
        assertEquals("Primitive Prototype Autocannon/20 Ammo", actualCreateISAC20pAmmoResult.getName());
        assertEquals("IS Ammo AC/20 Primitive", actualCreateISAC20pAmmoResult.getInternalName());
        assertEquals("C/F-X-X-X", actualCreateISAC20pAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAC20pAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAC20pAmmoResult.getFlags());
    }

    private void createISLRM5pAmmo(AmmoType actualCreateISLRM5pAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLRM5pAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLRM5pAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLRM5pAmmoResult.svslots);
        assertEquals("LRM 5p", actualCreateISLRM5pAmmoResult.shortName);
        assertTrue(actualCreateISLRM5pAmmoResult.explosive);
        assertEquals(1, actualCreateISLRM5pAmmoResult.criticals);
        assertEquals(6.0, actualCreateISLRM5pAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLRM5pAmmoResult.isSpreadable());
        assertFalse(actualCreateISLRM5pAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLRM5pAmmoResult.isMixedTech());
        assertTrue(actualCreateISLRM5pAmmoResult.isHittable());
        assertTrue(actualCreateISLRM5pAmmoResult.hasModes());
        assertFalse(actualCreateISLRM5pAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLRM5pAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISLRM5pAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLRM5pAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLRM5pAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISLRM5pAmmoResult.getStandardRange());
        assertEquals("118, IO", actualCreateISLRM5pAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLRM5pAmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISLRM5pAmmoResult.getRawCost(), 0.0);
        assertEquals("Primitive Prototype LRM 5 Ammo", actualCreateISLRM5pAmmoResult.getName());
        assertEquals("IS Ammo LRM-5 Primitive", actualCreateISLRM5pAmmoResult.getInternalName());
        assertEquals("C/F-X-X-X", actualCreateISLRM5pAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLRM5pAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLRM5pAmmoResult.getFlags());
    }

    private void createISLRM10pAmmo(AmmoType actualCreateISLRM10pAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLRM10pAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLRM10pAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLRM10pAmmoResult.svslots);
        assertEquals("LRM 10p", actualCreateISLRM10pAmmoResult.shortName);
        assertTrue(actualCreateISLRM10pAmmoResult.explosive);
        assertEquals(1, actualCreateISLRM10pAmmoResult.criticals);
        assertEquals(11.0, actualCreateISLRM10pAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLRM10pAmmoResult.isSpreadable());
        assertFalse(actualCreateISLRM10pAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLRM10pAmmoResult.isMixedTech());
        assertTrue(actualCreateISLRM10pAmmoResult.isHittable());
        assertTrue(actualCreateISLRM10pAmmoResult.hasModes());
        assertFalse(actualCreateISLRM10pAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLRM10pAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISLRM10pAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLRM10pAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLRM10pAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISLRM10pAmmoResult.getStandardRange());
        assertEquals("118, IO", actualCreateISLRM10pAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLRM10pAmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISLRM10pAmmoResult.getRawCost(), 0.0);
        assertEquals("Primitive Prototype LRM 10 Ammo", actualCreateISLRM10pAmmoResult.getName());
        assertEquals("IS Ammo LRM-10 Primitive", actualCreateISLRM10pAmmoResult.getInternalName());
        assertEquals("C/F-X-X-X", actualCreateISLRM10pAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLRM10pAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLRM10pAmmoResult.getFlags());
    }

    private void createISLRM15pAmmo(AmmoType actualCreateISLRM15pAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLRM15pAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLRM15pAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLRM15pAmmoResult.svslots);
        assertEquals("LRM 15p", actualCreateISLRM15pAmmoResult.shortName);
        assertTrue(actualCreateISLRM15pAmmoResult.explosive);
        assertEquals(1, actualCreateISLRM15pAmmoResult.criticals);
        assertEquals(17.0, actualCreateISLRM15pAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLRM15pAmmoResult.isSpreadable());
        assertFalse(actualCreateISLRM15pAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLRM15pAmmoResult.isMixedTech());
        assertTrue(actualCreateISLRM15pAmmoResult.isHittable());
        assertTrue(actualCreateISLRM15pAmmoResult.hasModes());
        assertFalse(actualCreateISLRM15pAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLRM15pAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISLRM15pAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLRM15pAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLRM15pAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISLRM15pAmmoResult.getStandardRange());
        assertEquals("118, IO", actualCreateISLRM15pAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLRM15pAmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISLRM15pAmmoResult.getRawCost(), 0.0);
        assertEquals("Primitive Prototype LRM 15 Ammo", actualCreateISLRM15pAmmoResult.getName());
        assertEquals("IS Ammo LRM-15 Primitive", actualCreateISLRM15pAmmoResult.getInternalName());
        assertEquals("C/F-X-X-X", actualCreateISLRM15pAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLRM15pAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLRM15pAmmoResult.getFlags());
    }

    private void createISLRM20pAmmo(AmmoType actualCreateISLRM20pAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLRM20pAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLRM20pAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLRM20pAmmoResult.svslots);
        assertEquals("LRM 20p", actualCreateISLRM20pAmmoResult.shortName);
        assertTrue(actualCreateISLRM20pAmmoResult.explosive);
        assertEquals(1, actualCreateISLRM20pAmmoResult.criticals);
        assertEquals(23.0, actualCreateISLRM20pAmmoResult.bv, 0.0);
        assertFalse(actualCreateISLRM20pAmmoResult.isSpreadable());
        assertFalse(actualCreateISLRM20pAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLRM20pAmmoResult.isMixedTech());
        assertTrue(actualCreateISLRM20pAmmoResult.isHittable());
        assertTrue(actualCreateISLRM20pAmmoResult.hasModes());
        assertFalse(actualCreateISLRM20pAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLRM20pAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISLRM20pAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLRM20pAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISLRM20pAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISLRM20pAmmoResult.getStandardRange());
        assertEquals("118, IO", actualCreateISLRM20pAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLRM20pAmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISLRM20pAmmoResult.getRawCost(), 0.0);
        assertEquals("Primitive Prototype LRM 20 Ammo", actualCreateISLRM20pAmmoResult.getName());
        assertEquals("IS Ammo LRM-20 Primitive", actualCreateISLRM20pAmmoResult.getInternalName());
        assertEquals("C/F-X-X-X", actualCreateISLRM20pAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLRM20pAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLRM20pAmmoResult.getFlags());
    }

    private void createISSRM2pAmmo(AmmoType actualCreateISSRM2pAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISSRM2pAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISSRM2pAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISSRM2pAmmoResult.svslots);
        assertEquals("SRM 2p", actualCreateISSRM2pAmmoResult.shortName);
        assertNull(actualCreateISSRM2pAmmoResult.modes);
        assertTrue(actualCreateISSRM2pAmmoResult.explosive);
        assertEquals(1, actualCreateISSRM2pAmmoResult.criticals);
        assertEquals(3.0, actualCreateISSRM2pAmmoResult.bv, 0.0);
        assertFalse(actualCreateISSRM2pAmmoResult.isSpreadable());
        assertFalse(actualCreateISSRM2pAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISSRM2pAmmoResult.isMixedTech());
        assertTrue(actualCreateISSRM2pAmmoResult.isHittable());
        assertFalse(actualCreateISSRM2pAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISSRM2pAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISSRM2pAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISSRM2pAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISSRM2pAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISSRM2pAmmoResult.getStandardRange());
        assertEquals("118, IO", actualCreateISSRM2pAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISSRM2pAmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateISSRM2pAmmoResult.getRawCost(), 0.0);
        assertEquals("Primitive Prototype SRM 2 Ammo", actualCreateISSRM2pAmmoResult.getName());
        assertEquals("IS Ammo SRM-2 Primitive", actualCreateISSRM2pAmmoResult.getInternalName());
        assertEquals("C/F-X-X-X", actualCreateISSRM2pAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISSRM2pAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISSRM2pAmmoResult.getFlags());
    }

    private void createISSRM4pAmmo(AmmoType actualCreateISSRM4pAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISSRM4pAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISSRM4pAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISSRM4pAmmoResult.svslots);
        assertEquals("SRM 4p", actualCreateISSRM4pAmmoResult.shortName);
        assertNull(actualCreateISSRM4pAmmoResult.modes);
        assertTrue(actualCreateISSRM4pAmmoResult.explosive);
        assertEquals(1, actualCreateISSRM4pAmmoResult.criticals);
        assertEquals(5.0, actualCreateISSRM4pAmmoResult.bv, 0.0);
        assertFalse(actualCreateISSRM4pAmmoResult.isSpreadable());
        assertFalse(actualCreateISSRM4pAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISSRM4pAmmoResult.isMixedTech());
        assertTrue(actualCreateISSRM4pAmmoResult.isHittable());
        assertFalse(actualCreateISSRM4pAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISSRM4pAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISSRM4pAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISSRM4pAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISSRM4pAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISSRM4pAmmoResult.getStandardRange());
        assertEquals("118, IO", actualCreateISSRM4pAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISSRM4pAmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateISSRM4pAmmoResult.getRawCost(), 0.0);
        assertEquals("Primitive Prototype SRM 4 Ammo", actualCreateISSRM4pAmmoResult.getName());
        assertEquals("IS Ammo SRM-4 Primitive", actualCreateISSRM4pAmmoResult.getInternalName());
        assertEquals("C/F-X-X-X", actualCreateISSRM4pAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISSRM4pAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISSRM4pAmmoResult.getFlags());
    }

    private void createISSRM6pAmmo(AmmoType actualCreateISSRM6pAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISSRM6pAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISSRM6pAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISSRM6pAmmoResult.svslots);
        assertEquals("SRM 6p", actualCreateISSRM6pAmmoResult.shortName);
        assertNull(actualCreateISSRM6pAmmoResult.modes);
        assertTrue(actualCreateISSRM6pAmmoResult.explosive);
        assertEquals(1, actualCreateISSRM6pAmmoResult.criticals);
        assertEquals(7.0, actualCreateISSRM6pAmmoResult.bv, 0.0);
        assertFalse(actualCreateISSRM6pAmmoResult.isSpreadable());
        assertFalse(actualCreateISSRM6pAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISSRM6pAmmoResult.isMixedTech());
        assertTrue(actualCreateISSRM6pAmmoResult.isHittable());
        assertFalse(actualCreateISSRM6pAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISSRM6pAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISSRM6pAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISSRM6pAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISSRM6pAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISSRM6pAmmoResult.getStandardRange());
        assertEquals("118, IO", actualCreateISSRM6pAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISSRM6pAmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateISSRM6pAmmoResult.getRawCost(), 0.0);
        assertEquals("Primitive Prototype SRM 6 Ammo", actualCreateISSRM6pAmmoResult.getName());
        assertEquals("IS Ammo SRM-6 Primitive", actualCreateISSRM6pAmmoResult.getInternalName());
        assertEquals("C/F-X-X-X", actualCreateISSRM6pAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISSRM6pAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISSRM6pAmmoResult.getFlags());
    }

    private void createISPrimitiveLongTomAmmo(AmmoType actualCreateISPrimitiveLongTomAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISPrimitiveLongTomAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISPrimitiveLongTomAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISPrimitiveLongTomAmmoResult.svslots);
        assertEquals("Primitive Long Tom", actualCreateISPrimitiveLongTomAmmoResult.shortName);
        assertNull(actualCreateISPrimitiveLongTomAmmoResult.modes);
        assertTrue(actualCreateISPrimitiveLongTomAmmoResult.explosive);
        assertEquals(1, actualCreateISPrimitiveLongTomAmmoResult.criticals);
        assertEquals(35.0, actualCreateISPrimitiveLongTomAmmoResult.bv, 0.0);
        assertFalse(actualCreateISPrimitiveLongTomAmmoResult.isSpreadable());
        assertFalse(actualCreateISPrimitiveLongTomAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISPrimitiveLongTomAmmoResult.isMixedTech());
        assertTrue(actualCreateISPrimitiveLongTomAmmoResult.isHittable());
        assertFalse(actualCreateISPrimitiveLongTomAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISPrimitiveLongTomAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISPrimitiveLongTomAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISPrimitiveLongTomAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISPrimitiveLongTomAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISPrimitiveLongTomAmmoResult.getStandardRange());
        assertEquals("118, IO", actualCreateISPrimitiveLongTomAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISPrimitiveLongTomAmmoResult.getReintroductionDate());
        assertEquals(10000.0, actualCreateISPrimitiveLongTomAmmoResult.getRawCost(), 0.0);
        assertEquals("Primitive Prototype Long Tom Artillery Ammo", actualCreateISPrimitiveLongTomAmmoResult.getName());
        assertEquals("ISPrimitiveLongTomAmmo", actualCreateISPrimitiveLongTomAmmoResult.getInternalName());
        assertEquals("C/F-X-X-X", actualCreateISPrimitiveLongTomAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISPrimitiveLongTomAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISPrimitiveLongTomAmmoResult.getFlags());
    }

    private void createPrototypeArrowIVAmmo(AmmoType actualCreatePrototypeArrowIVAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreatePrototypeArrowIVAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreatePrototypeArrowIVAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreatePrototypeArrowIVAmmoResult.svslots);
        assertEquals("ProtoType Arrow IV", actualCreatePrototypeArrowIVAmmoResult.shortName);
        assertNull(actualCreatePrototypeArrowIVAmmoResult.modes);
        assertTrue(actualCreatePrototypeArrowIVAmmoResult.explosive);
        assertEquals(1, actualCreatePrototypeArrowIVAmmoResult.criticals);
        assertEquals(30.0, actualCreatePrototypeArrowIVAmmoResult.bv, 0.0);
        assertFalse(actualCreatePrototypeArrowIVAmmoResult.isSpreadable());
        assertFalse(actualCreatePrototypeArrowIVAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreatePrototypeArrowIVAmmoResult.isMixedTech());
        assertTrue(actualCreatePrototypeArrowIVAmmoResult.isHittable());
        assertFalse(actualCreatePrototypeArrowIVAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreatePrototypeArrowIVAmmoResult.getToHitModifier());
        assertEquals(4, actualCreatePrototypeArrowIVAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreatePrototypeArrowIVAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreatePrototypeArrowIVAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreatePrototypeArrowIVAmmoResult.getStandardRange());
        assertEquals("217,IO", actualCreatePrototypeArrowIVAmmoResult.getRulesRefs());
        assertEquals(3044, actualCreatePrototypeArrowIVAmmoResult.getReintroductionDate());
        assertEquals(40000.0, actualCreatePrototypeArrowIVAmmoResult.getRawCost(), 0.0);
        assertEquals("Prototype Arrow IV Ammo", actualCreatePrototypeArrowIVAmmoResult.getName());
        assertEquals("ProtoTypeArrowIVAmmo", actualCreatePrototypeArrowIVAmmoResult.getInternalName());
        assertEquals("E/E-F(F*)-E-D", actualCreatePrototypeArrowIVAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreatePrototypeArrowIVAmmoResult.flags;
        assertSame(expectedFlags, actualCreatePrototypeArrowIVAmmoResult.getFlags());
    }

    private void createCLImprovedAC2Ammo(AmmoType actualCreateCLImprovedAC2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLImprovedAC2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLImprovedAC2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedAC2AmmoResult.svslots);
        assertEquals("Improved Autocannon/2 Ammo", actualCreateCLImprovedAC2AmmoResult.shortName);
        assertNull(actualCreateCLImprovedAC2AmmoResult.modes);
        assertTrue(actualCreateCLImprovedAC2AmmoResult.explosive);
        assertEquals(1, actualCreateCLImprovedAC2AmmoResult.criticals);
        assertEquals(5.0, actualCreateCLImprovedAC2AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLImprovedAC2AmmoResult.isSpreadable());
        assertFalse(actualCreateCLImprovedAC2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLImprovedAC2AmmoResult.isMixedTech());
        assertTrue(actualCreateCLImprovedAC2AmmoResult.isHittable());
        assertFalse(actualCreateCLImprovedAC2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLImprovedAC2AmmoResult.getToHitModifier());
        assertEquals(3, actualCreateCLImprovedAC2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLImprovedAC2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLImprovedAC2AmmoResult.getStaticTechLevel());
        assertEquals("96, IO", actualCreateCLImprovedAC2AmmoResult.getRulesRefs());
        assertEquals(3080, actualCreateCLImprovedAC2AmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateCLImprovedAC2AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedAC2AmmoResult.getPrototypeDate());
        assertEquals("Improved Autocannon/2 Ammo", actualCreateCLImprovedAC2AmmoResult.getName());
        assertEquals("CLIMPAmmoAC2", actualCreateCLImprovedAC2AmmoResult.getInternalName());
        assertEquals("D/X-C-X-X", actualCreateCLImprovedAC2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLImprovedAC2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLImprovedAC2AmmoResult.getFlags());
    }

    private void createCLImprovedAC5Ammo(AmmoType actualCreateCLImprovedAC5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLImprovedAC5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLImprovedAC5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedAC5AmmoResult.svslots);
        assertEquals("Improved Autocannon/5 Ammo", actualCreateCLImprovedAC5AmmoResult.shortName);
        assertNull(actualCreateCLImprovedAC5AmmoResult.modes);
        assertTrue(actualCreateCLImprovedAC5AmmoResult.explosive);
        assertEquals(1, actualCreateCLImprovedAC5AmmoResult.criticals);
        assertEquals(9.0, actualCreateCLImprovedAC5AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLImprovedAC5AmmoResult.isSpreadable());
        assertFalse(actualCreateCLImprovedAC5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLImprovedAC5AmmoResult.isMixedTech());
        assertTrue(actualCreateCLImprovedAC5AmmoResult.isHittable());
        assertFalse(actualCreateCLImprovedAC5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLImprovedAC5AmmoResult.getToHitModifier());
        assertEquals(3, actualCreateCLImprovedAC5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLImprovedAC5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLImprovedAC5AmmoResult.getStaticTechLevel());
        assertEquals("96, IO", actualCreateCLImprovedAC5AmmoResult.getRulesRefs());
        assertEquals(3080, actualCreateCLImprovedAC5AmmoResult.getReintroductionDate());
        assertEquals(4500.0, actualCreateCLImprovedAC5AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedAC5AmmoResult.getPrototypeDate());
        assertEquals("Improved Autocannon/5 Ammo", actualCreateCLImprovedAC5AmmoResult.getName());
        assertEquals("CLIMPAmmoAC5", actualCreateCLImprovedAC5AmmoResult.getInternalName());
        assertEquals("D/X-C-X-X", actualCreateCLImprovedAC5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLImprovedAC5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLImprovedAC5AmmoResult.getFlags());
    }

    private void createCLImprovedAC10Ammo(AmmoType actualCreateCLImprovedAC10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLImprovedAC10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLImprovedAC10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedAC10AmmoResult.svslots);
        assertEquals("Improved Autocannon/10 Ammo", actualCreateCLImprovedAC10AmmoResult.shortName);
        assertNull(actualCreateCLImprovedAC10AmmoResult.modes);
        assertTrue(actualCreateCLImprovedAC10AmmoResult.explosive);
        assertEquals(1, actualCreateCLImprovedAC10AmmoResult.criticals);
        assertEquals(15.0, actualCreateCLImprovedAC10AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLImprovedAC10AmmoResult.isSpreadable());
        assertFalse(actualCreateCLImprovedAC10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLImprovedAC10AmmoResult.isMixedTech());
        assertTrue(actualCreateCLImprovedAC10AmmoResult.isHittable());
        assertFalse(actualCreateCLImprovedAC10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLImprovedAC10AmmoResult.getToHitModifier());
        assertEquals(3, actualCreateCLImprovedAC10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLImprovedAC10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLImprovedAC10AmmoResult.getStaticTechLevel());
        assertEquals("96, IO", actualCreateCLImprovedAC10AmmoResult.getRulesRefs());
        assertEquals(3080, actualCreateCLImprovedAC10AmmoResult.getReintroductionDate());
        assertEquals(6000.0, actualCreateCLImprovedAC10AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedAC10AmmoResult.getPrototypeDate());
        assertEquals("Improved Autocannon/10 Ammo", actualCreateCLImprovedAC10AmmoResult.getName());
        assertEquals("CLIMPAmmoAC10", actualCreateCLImprovedAC10AmmoResult.getInternalName());
        assertEquals("D/X-C-X-X", actualCreateCLImprovedAC10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLImprovedAC10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLImprovedAC10AmmoResult.getFlags());
    }

    private void createCLImprovedAC20Ammo(AmmoType actualCreateCLImprovedAC20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLImprovedAC20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLImprovedAC20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedAC20AmmoResult.svslots);
        assertEquals("Improved Autocannon/20 Ammo", actualCreateCLImprovedAC20AmmoResult.shortName);
        assertNull(actualCreateCLImprovedAC20AmmoResult.modes);
        assertTrue(actualCreateCLImprovedAC20AmmoResult.explosive);
        assertEquals(1, actualCreateCLImprovedAC20AmmoResult.criticals);
        assertEquals(22.0, actualCreateCLImprovedAC20AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLImprovedAC20AmmoResult.isSpreadable());
        assertFalse(actualCreateCLImprovedAC20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLImprovedAC20AmmoResult.isMixedTech());
        assertTrue(actualCreateCLImprovedAC20AmmoResult.isHittable());
        assertFalse(actualCreateCLImprovedAC20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLImprovedAC20AmmoResult.getToHitModifier());
        assertEquals(3, actualCreateCLImprovedAC20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLImprovedAC20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLImprovedAC20AmmoResult.getStaticTechLevel());
        assertEquals("96, IO", actualCreateCLImprovedAC20AmmoResult.getRulesRefs());
        assertEquals(3080, actualCreateCLImprovedAC20AmmoResult.getReintroductionDate());
        assertEquals(10000.0, actualCreateCLImprovedAC20AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedAC20AmmoResult.getPrototypeDate());
        assertEquals("Improved Autocannon/20 Ammo", actualCreateCLImprovedAC20AmmoResult.getName());
        assertEquals("CLIMPAmmoAC20", actualCreateCLImprovedAC20AmmoResult.getInternalName());
        assertEquals("D/X-C-X-X", actualCreateCLImprovedAC20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLImprovedAC20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLImprovedAC20AmmoResult.getFlags());
    }

    private void createCLImprovedLRM5Ammo(AmmoType actualCreateCLImprovedLRM5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLImprovedLRM5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLImprovedLRM5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedLRM5AmmoResult.svslots);
        assertEquals("Improved LRM 5", actualCreateCLImprovedLRM5AmmoResult.shortName);
        assertTrue(actualCreateCLImprovedLRM5AmmoResult.explosive);
        assertEquals(1, actualCreateCLImprovedLRM5AmmoResult.criticals);
        assertEquals(6.0, actualCreateCLImprovedLRM5AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLImprovedLRM5AmmoResult.isSpreadable());
        assertFalse(actualCreateCLImprovedLRM5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLImprovedLRM5AmmoResult.isMixedTech());
        assertTrue(actualCreateCLImprovedLRM5AmmoResult.isHittable());
        assertTrue(actualCreateCLImprovedLRM5AmmoResult.hasModes());
        assertFalse(actualCreateCLImprovedLRM5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLImprovedLRM5AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLImprovedLRM5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLImprovedLRM5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLImprovedLRM5AmmoResult.getStaticTechLevel());
        assertEquals("96, IO", actualCreateCLImprovedLRM5AmmoResult.getRulesRefs());
        assertEquals(3080, actualCreateCLImprovedLRM5AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLImprovedLRM5AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLImprovedLRM5AmmoResult.getPrototypeDate());
        assertEquals("Improved LRM 5 Ammo", actualCreateCLImprovedLRM5AmmoResult.getName());
        assertEquals("ClanImprovedLRM5Ammo", actualCreateCLImprovedLRM5AmmoResult.getInternalName());
        assertEquals("F/X-D-X-X", actualCreateCLImprovedLRM5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLImprovedLRM5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLImprovedLRM5AmmoResult.getFlags());
    }

    private void createCLImprovedLRM10Ammo(AmmoType actualCreateCLImprovedLRM10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLImprovedLRM10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLImprovedLRM10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedLRM10AmmoResult.svslots);
        assertEquals("Improved LRM 10", actualCreateCLImprovedLRM10AmmoResult.shortName);
        assertTrue(actualCreateCLImprovedLRM10AmmoResult.explosive);
        assertEquals(1, actualCreateCLImprovedLRM10AmmoResult.criticals);
        assertEquals(11.0, actualCreateCLImprovedLRM10AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLImprovedLRM10AmmoResult.isSpreadable());
        assertFalse(actualCreateCLImprovedLRM10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLImprovedLRM10AmmoResult.isMixedTech());
        assertTrue(actualCreateCLImprovedLRM10AmmoResult.isHittable());
        assertTrue(actualCreateCLImprovedLRM10AmmoResult.hasModes());
        assertFalse(actualCreateCLImprovedLRM10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLImprovedLRM10AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLImprovedLRM10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLImprovedLRM10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLImprovedLRM10AmmoResult.getStaticTechLevel());
        assertEquals("96, IO", actualCreateCLImprovedLRM10AmmoResult.getRulesRefs());
        assertEquals(3080, actualCreateCLImprovedLRM10AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLImprovedLRM10AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLImprovedLRM10AmmoResult.getPrototypeDate());
        assertEquals("Improved LRM 10 Ammo", actualCreateCLImprovedLRM10AmmoResult.getName());
        assertEquals("ClanImprovedLRM10Ammo", actualCreateCLImprovedLRM10AmmoResult.getInternalName());
        assertEquals("F/X-D-X-X", actualCreateCLImprovedLRM10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLImprovedLRM10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLImprovedLRM10AmmoResult.getFlags());
    }

    private void createCLImprovedLRM15Ammo(AmmoType actualCreateCLImprovedLRM15AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLImprovedLRM15AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLImprovedLRM15AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedLRM15AmmoResult.svslots);
        assertEquals("Improved LRM 15", actualCreateCLImprovedLRM15AmmoResult.shortName);
        assertTrue(actualCreateCLImprovedLRM15AmmoResult.explosive);
        assertEquals(1, actualCreateCLImprovedLRM15AmmoResult.criticals);
        assertEquals(17.0, actualCreateCLImprovedLRM15AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLImprovedLRM15AmmoResult.isSpreadable());
        assertFalse(actualCreateCLImprovedLRM15AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLImprovedLRM15AmmoResult.isMixedTech());
        assertTrue(actualCreateCLImprovedLRM15AmmoResult.isHittable());
        assertTrue(actualCreateCLImprovedLRM15AmmoResult.hasModes());
        assertFalse(actualCreateCLImprovedLRM15AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLImprovedLRM15AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLImprovedLRM15AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLImprovedLRM15AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLImprovedLRM15AmmoResult.getStaticTechLevel());
        assertEquals("96, IO", actualCreateCLImprovedLRM15AmmoResult.getRulesRefs());
        assertEquals(3080, actualCreateCLImprovedLRM15AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLImprovedLRM15AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLImprovedLRM15AmmoResult.getPrototypeDate());
        assertEquals("Improved LRM 15 Ammo", actualCreateCLImprovedLRM15AmmoResult.getName());
        assertEquals("ClanImprovedLRM15Ammo", actualCreateCLImprovedLRM15AmmoResult.getInternalName());
        assertEquals("F/X-D-X-X", actualCreateCLImprovedLRM15AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLImprovedLRM15AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLImprovedLRM15AmmoResult.getFlags());
    }

    private void createCLImprovedLRM20Ammo(AmmoType actualCreateCLImprovedLRM20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLImprovedLRM20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLImprovedLRM20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedLRM20AmmoResult.svslots);
        assertEquals("Improved LRM 20", actualCreateCLImprovedLRM20AmmoResult.shortName);
        assertTrue(actualCreateCLImprovedLRM20AmmoResult.explosive);
        assertEquals(1, actualCreateCLImprovedLRM20AmmoResult.criticals);
        assertEquals(23.0, actualCreateCLImprovedLRM20AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLImprovedLRM20AmmoResult.isSpreadable());
        assertFalse(actualCreateCLImprovedLRM20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLImprovedLRM20AmmoResult.isMixedTech());
        assertTrue(actualCreateCLImprovedLRM20AmmoResult.isHittable());
        assertTrue(actualCreateCLImprovedLRM20AmmoResult.hasModes());
        assertFalse(actualCreateCLImprovedLRM20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLImprovedLRM20AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLImprovedLRM20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLImprovedLRM20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLImprovedLRM20AmmoResult.getStaticTechLevel());
        assertEquals("96, IO", actualCreateCLImprovedLRM20AmmoResult.getRulesRefs());
        assertEquals(3080, actualCreateCLImprovedLRM20AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLImprovedLRM20AmmoResult.getRawCost(), 0.0);
        assertEquals(2815, actualCreateCLImprovedLRM20AmmoResult.getPrototypeDate());
        assertEquals("Improved LRM 20 Ammo", actualCreateCLImprovedLRM20AmmoResult.getName());
        assertEquals("ClanImprovedLRM20Ammo", actualCreateCLImprovedLRM20AmmoResult.getInternalName());
        assertEquals("F/X-D-X-X", actualCreateCLImprovedLRM20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLImprovedLRM20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLImprovedLRM20AmmoResult.getFlags());
    }

    private void createCLImprovedGaussAmmo(AmmoType actualCreateCLImprovedGaussAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLImprovedGaussAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLImprovedGaussAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedGaussAmmoResult.svslots);
        assertEquals("Improved Gauss", actualCreateCLImprovedGaussAmmoResult.shortName);
        assertNull(actualCreateCLImprovedGaussAmmoResult.modes);
        assertFalse(actualCreateCLImprovedGaussAmmoResult.explosive);
        assertEquals(1, actualCreateCLImprovedGaussAmmoResult.criticals);
        assertEquals(40.0, actualCreateCLImprovedGaussAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLImprovedGaussAmmoResult.isSpreadable());
        assertFalse(actualCreateCLImprovedGaussAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLImprovedGaussAmmoResult.isMixedTech());
        assertTrue(actualCreateCLImprovedGaussAmmoResult.isHittable());
        assertFalse(actualCreateCLImprovedGaussAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLImprovedGaussAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateCLImprovedGaussAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLImprovedGaussAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLImprovedGaussAmmoResult.getStaticTechLevel());
        assertEquals("96, IO", actualCreateCLImprovedGaussAmmoResult.getRulesRefs());
        assertEquals(3080, actualCreateCLImprovedGaussAmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateCLImprovedGaussAmmoResult.getRawCost(), 0.0);
        assertEquals(2813, actualCreateCLImprovedGaussAmmoResult.getPrototypeDate());
        assertEquals("Improved Gauss Rifle Ammo", actualCreateCLImprovedGaussAmmoResult.getName());
        assertEquals("CLImpGaussAmmo", actualCreateCLImprovedGaussAmmoResult.getInternalName());
        assertEquals("E/X-E-X-E", actualCreateCLImprovedGaussAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLImprovedGaussAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLImprovedGaussAmmoResult.getFlags());
    }

    private void createCLImprovedSRM2Ammo(AmmoType actualCreateCLImprovedSRM2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLImprovedSRM2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLImprovedSRM2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedSRM2AmmoResult.svslots);
        assertEquals("Improved SRM 2", actualCreateCLImprovedSRM2AmmoResult.shortName);
        assertNull(actualCreateCLImprovedSRM2AmmoResult.modes);
        assertTrue(actualCreateCLImprovedSRM2AmmoResult.explosive);
        assertEquals(1, actualCreateCLImprovedSRM2AmmoResult.criticals);
        assertEquals(4.0, actualCreateCLImprovedSRM2AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLImprovedSRM2AmmoResult.isSpreadable());
        assertFalse(actualCreateCLImprovedSRM2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLImprovedSRM2AmmoResult.isMixedTech());
        assertTrue(actualCreateCLImprovedSRM2AmmoResult.isHittable());
        assertFalse(actualCreateCLImprovedSRM2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLImprovedSRM2AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLImprovedSRM2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLImprovedSRM2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLImprovedSRM2AmmoResult.getStaticTechLevel());
        assertEquals("96, IO", actualCreateCLImprovedSRM2AmmoResult.getRulesRefs());
        assertEquals(3080, actualCreateCLImprovedSRM2AmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateCLImprovedSRM2AmmoResult.getRawCost(), 0.0);
        assertEquals(2810, actualCreateCLImprovedSRM2AmmoResult.getPrototypeDate());
        assertEquals("Improved SRM 2 Ammo", actualCreateCLImprovedSRM2AmmoResult.getName());
        assertEquals("ClanImpAmmoSRM2", actualCreateCLImprovedSRM2AmmoResult.getInternalName());
        assertEquals("F/X-D-X-X", actualCreateCLImprovedSRM2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLImprovedSRM2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLImprovedSRM2AmmoResult.getFlags());
    }

    private void createCLImprovedSRM4Ammo(AmmoType actualCreateCLImprovedSRM4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLImprovedSRM4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLImprovedSRM4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedSRM4AmmoResult.svslots);
        assertEquals("Improved SRM 4", actualCreateCLImprovedSRM4AmmoResult.shortName);
        assertNull(actualCreateCLImprovedSRM4AmmoResult.modes);
        assertTrue(actualCreateCLImprovedSRM4AmmoResult.explosive);
        assertEquals(1, actualCreateCLImprovedSRM4AmmoResult.criticals);
        assertEquals(7.0, actualCreateCLImprovedSRM4AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLImprovedSRM4AmmoResult.isSpreadable());
        assertFalse(actualCreateCLImprovedSRM4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLImprovedSRM4AmmoResult.isMixedTech());
        assertTrue(actualCreateCLImprovedSRM4AmmoResult.isHittable());
        assertFalse(actualCreateCLImprovedSRM4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLImprovedSRM4AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLImprovedSRM4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLImprovedSRM4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLImprovedSRM4AmmoResult.getStaticTechLevel());
        assertEquals("96, IO", actualCreateCLImprovedSRM4AmmoResult.getRulesRefs());
        assertEquals(3080, actualCreateCLImprovedSRM4AmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateCLImprovedSRM4AmmoResult.getRawCost(), 0.0);
        assertEquals(2810, actualCreateCLImprovedSRM4AmmoResult.getPrototypeDate());
        assertEquals("Improved SRM 4 Ammo", actualCreateCLImprovedSRM4AmmoResult.getName());
        assertEquals("ClImpAmmoSRM4", actualCreateCLImprovedSRM4AmmoResult.getInternalName());
        assertEquals("F/X-D-X-X", actualCreateCLImprovedSRM4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLImprovedSRM4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLImprovedSRM4AmmoResult.getFlags());
    }

    private void createCLImprovedSRM6Ammo(AmmoType actualCreateCLImprovedSRM6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLImprovedSRM6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLImprovedSRM6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLImprovedSRM6AmmoResult.svslots);
        assertEquals("Improved SRM 6", actualCreateCLImprovedSRM6AmmoResult.shortName);
        assertNull(actualCreateCLImprovedSRM6AmmoResult.modes);
        assertTrue(actualCreateCLImprovedSRM6AmmoResult.explosive);
        assertEquals(1, actualCreateCLImprovedSRM6AmmoResult.criticals);
        assertEquals(10.0, actualCreateCLImprovedSRM6AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLImprovedSRM6AmmoResult.isSpreadable());
        assertFalse(actualCreateCLImprovedSRM6AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLImprovedSRM6AmmoResult.isMixedTech());
        assertTrue(actualCreateCLImprovedSRM6AmmoResult.isHittable());
        assertFalse(actualCreateCLImprovedSRM6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLImprovedSRM6AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLImprovedSRM6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLImprovedSRM6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateCLImprovedSRM6AmmoResult.getStaticTechLevel());
        assertEquals("96, IO", actualCreateCLImprovedSRM6AmmoResult.getRulesRefs());
        assertEquals(3080, actualCreateCLImprovedSRM6AmmoResult.getReintroductionDate());
        assertEquals(27000.0, actualCreateCLImprovedSRM6AmmoResult.getRawCost(), 0.0);
        assertEquals(2810, actualCreateCLImprovedSRM6AmmoResult.getPrototypeDate());
        assertEquals("Improved SRM 6 Ammo", actualCreateCLImprovedSRM6AmmoResult.getName());
        assertEquals("CLImpAmmoSRM6", actualCreateCLImprovedSRM6AmmoResult.getInternalName());
        assertEquals("F/X-D-X-X", actualCreateCLImprovedSRM6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLImprovedSRM6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLImprovedSRM6AmmoResult.getFlags());
    }

    private void createBAMicroBombAmmo(AmmoType actualCreateBAMicroBombAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBAMicroBombAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBAMicroBombAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBAMicroBombAmmoResult.svslots);
        assertEquals("Micro Bomb", actualCreateBAMicroBombAmmoResult.shortName);
        assertNull(actualCreateBAMicroBombAmmoResult.modes);
        assertTrue(actualCreateBAMicroBombAmmoResult.explosive);
        assertEquals(1, actualCreateBAMicroBombAmmoResult.criticals);
        assertEquals(0.0, actualCreateBAMicroBombAmmoResult.bv, 0.0);
        assertFalse(actualCreateBAMicroBombAmmoResult.isSpreadable());
        assertFalse(actualCreateBAMicroBombAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBAMicroBombAmmoResult.isMixedTech());
        assertTrue(actualCreateBAMicroBombAmmoResult.isHittable());
        assertFalse(actualCreateBAMicroBombAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBAMicroBombAmmoResult.getToHitModifier());
        assertEquals(5, actualCreateBAMicroBombAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBAMicroBombAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBAMicroBombAmmoResult.getStaticTechLevel());
        assertEquals("253,TM", actualCreateBAMicroBombAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBAMicroBombAmmoResult.getReintroductionDate());
        assertEquals(500.0, actualCreateBAMicroBombAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateBAMicroBombAmmoResult.getPrototypeDate());
        assertEquals("Micro Bomb Ammo", actualCreateBAMicroBombAmmoResult.getName());
        assertEquals("BA-Micro Bomb Ammo", actualCreateBAMicroBombAmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateBAMicroBombAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBAMicroBombAmmoResult.flags;
        assertSame(expectedFlags, actualCreateBAMicroBombAmmoResult.getFlags());
    }

    private void createCLTorpedoLRM5Ammo(AmmoType actualCreateCLTorpedoLRM5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLTorpedoLRM5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLTorpedoLRM5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLTorpedoLRM5AmmoResult.svslots);
        assertEquals("Torpedo/LRM 5", actualCreateCLTorpedoLRM5AmmoResult.shortName);
        assertNull(actualCreateCLTorpedoLRM5AmmoResult.modes);
        assertTrue(actualCreateCLTorpedoLRM5AmmoResult.explosive);
        assertEquals(1, actualCreateCLTorpedoLRM5AmmoResult.criticals);
        assertEquals(7.0, actualCreateCLTorpedoLRM5AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLTorpedoLRM5AmmoResult.isSpreadable());
        assertFalse(actualCreateCLTorpedoLRM5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLTorpedoLRM5AmmoResult.isMixedTech());
        assertTrue(actualCreateCLTorpedoLRM5AmmoResult.isHittable());
        assertFalse(actualCreateCLTorpedoLRM5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLTorpedoLRM5AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLTorpedoLRM5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLTorpedoLRM5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLTorpedoLRM5AmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateCLTorpedoLRM5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLTorpedoLRM5AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateCLTorpedoLRM5AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateCLTorpedoLRM5AmmoResult.getPrototypeDate());
        assertEquals("Torpedo/LRM 5 Ammo", actualCreateCLTorpedoLRM5AmmoResult.getName());
        assertEquals("Clan Torpedo/LRM5 Ammo", actualCreateCLTorpedoLRM5AmmoResult.getInternalName());
        assertEquals("C/X-C-C-X", actualCreateCLTorpedoLRM5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLTorpedoLRM5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLTorpedoLRM5AmmoResult.getFlags());
    }

    private void createBACompactNarcAmmo(AmmoType actualCreateBACompactNarcAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBACompactNarcAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBACompactNarcAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBACompactNarcAmmoResult.svslots);
        assertEquals("Compact Narc", actualCreateBACompactNarcAmmoResult.shortName);
        assertNull(actualCreateBACompactNarcAmmoResult.modes);
        assertFalse(actualCreateBACompactNarcAmmoResult.explosive);
        assertEquals(1, actualCreateBACompactNarcAmmoResult.criticals);
        assertEquals(0.0, actualCreateBACompactNarcAmmoResult.bv, 0.0);
        assertFalse(actualCreateBACompactNarcAmmoResult.isSpreadable());
        assertFalse(actualCreateBACompactNarcAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateBACompactNarcAmmoResult.isMixedTech());
        assertTrue(actualCreateBACompactNarcAmmoResult.isHittable());
        assertFalse(actualCreateBACompactNarcAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBACompactNarcAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBACompactNarcAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBACompactNarcAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBACompactNarcAmmoResult.getStaticTechLevel());
        assertEquals("263,TM", actualCreateBACompactNarcAmmoResult.getRulesRefs());
        assertEquals(0.0, actualCreateBACompactNarcAmmoResult.getRawCost(), 0.0);
        assertEquals(2865, actualCreateBACompactNarcAmmoResult.getPrototypeDate());
        assertEquals("Compact Narc Ammo", actualCreateBACompactNarcAmmoResult.getName());
        assertEquals(BattleArmor.DISPOSABLE_NARC_AMMO, actualCreateBACompactNarcAmmoResult.getInternalName());
        assertEquals("E/X-F-E-D", actualCreateBACompactNarcAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBACompactNarcAmmoResult.flags;
        assertSame(expectedFlags, actualCreateBACompactNarcAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateBACompactNarcAmmoResult.getExtinctionDate());
    }

    private void createBAMineLauncherAmmo(AmmoType actualCreateBAMineLauncherAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBAMineLauncherAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBAMineLauncherAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBAMineLauncherAmmoResult.svslots);
        assertEquals("Mine", actualCreateBAMineLauncherAmmoResult.shortName);
        assertNull(actualCreateBAMineLauncherAmmoResult.modes);
        assertTrue(actualCreateBAMineLauncherAmmoResult.explosive);
        assertEquals(1, actualCreateBAMineLauncherAmmoResult.criticals);
        assertEquals(0.0, actualCreateBAMineLauncherAmmoResult.bv, 0.0);
        assertFalse(actualCreateBAMineLauncherAmmoResult.isSpreadable());
        assertFalse(actualCreateBAMineLauncherAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBAMineLauncherAmmoResult.isMixedTech());
        assertTrue(actualCreateBAMineLauncherAmmoResult.isHittable());
        assertFalse(actualCreateBAMineLauncherAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBAMineLauncherAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBAMineLauncherAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBAMineLauncherAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBAMineLauncherAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateBAMineLauncherAmmoResult.getStandardRange());
        assertEquals("267,TM", actualCreateBAMineLauncherAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBAMineLauncherAmmoResult.getReintroductionDate());
        assertEquals(15000.0, actualCreateBAMineLauncherAmmoResult.getRawCost(), 0.0);
        assertEquals("Pop-up Mine Ammo", actualCreateBAMineLauncherAmmoResult.getName());
        assertEquals("BA-Mine Launcher Ammo", actualCreateBAMineLauncherAmmoResult.getInternalName());
        assertEquals("E/X-X-E-F", actualCreateBAMineLauncherAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBAMineLauncherAmmoResult.flags;
        assertSame(expectedFlags, actualCreateBAMineLauncherAmmoResult.getFlags());
    }

    private void createCLPROHeavyMGAmmo(AmmoType actualCreateCLPROHeavyMGAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLPROHeavyMGAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLPROHeavyMGAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLPROHeavyMGAmmoResult.svslots);
        assertEquals("Heavy Machine Gun", actualCreateCLPROHeavyMGAmmoResult.shortName);
        assertNull(actualCreateCLPROHeavyMGAmmoResult.modes);
        assertTrue(actualCreateCLPROHeavyMGAmmoResult.explosive);
        assertEquals(1, actualCreateCLPROHeavyMGAmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLPROHeavyMGAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLPROHeavyMGAmmoResult.isSpreadable());
        assertFalse(actualCreateCLPROHeavyMGAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLPROHeavyMGAmmoResult.isMixedTech());
        assertTrue(actualCreateCLPROHeavyMGAmmoResult.isHittable());
        assertFalse(actualCreateCLPROHeavyMGAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLPROHeavyMGAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLPROHeavyMGAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLPROHeavyMGAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLPROHeavyMGAmmoResult.getStaticTechLevel());
        assertEquals("228,TM", actualCreateCLPROHeavyMGAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLPROHeavyMGAmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLPROHeavyMGAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLPROHeavyMGAmmoResult.getPrototypeDate());
        assertEquals("Heavy Machine Gun Ammo", actualCreateCLPROHeavyMGAmmoResult.getName());
        assertEquals("Clan Heavy Machine Gun Ammo - Proto", actualCreateCLPROHeavyMGAmmoResult.getInternalName());
        assertEquals("C/X-X-B-B", actualCreateCLPROHeavyMGAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLPROHeavyMGAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLPROHeavyMGAmmoResult.getFlags());
    }

    private void createCLPROMGAmmo(AmmoType actualCreateCLPROMGAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLPROMGAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLPROMGAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLPROMGAmmoResult.svslots);
        assertEquals("Machine Gun", actualCreateCLPROMGAmmoResult.shortName);
        assertNull(actualCreateCLPROMGAmmoResult.modes);
        assertTrue(actualCreateCLPROMGAmmoResult.explosive);
        assertEquals(1, actualCreateCLPROMGAmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLPROMGAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLPROMGAmmoResult.isSpreadable());
        assertFalse(actualCreateCLPROMGAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLPROMGAmmoResult.isMixedTech());
        assertTrue(actualCreateCLPROMGAmmoResult.isHittable());
        assertFalse(actualCreateCLPROMGAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLPROMGAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLPROMGAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLPROMGAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLPROMGAmmoResult.getStaticTechLevel());
        assertEquals("228,TM", actualCreateCLPROMGAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLPROMGAmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLPROMGAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLPROMGAmmoResult.getPrototypeDate());
        assertEquals("Machine Gun Ammo", actualCreateCLPROMGAmmoResult.getName());
        assertEquals("Clan Machine Gun Ammo - Proto", actualCreateCLPROMGAmmoResult.getInternalName());
        assertEquals("C/X-X-B-A", actualCreateCLPROMGAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLPROMGAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLPROMGAmmoResult.getFlags());
    }

    private void createCLPROLightMGAmmo(AmmoType actualCreateCLPROLightMGAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLPROLightMGAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLPROLightMGAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLPROLightMGAmmoResult.svslots);
        assertEquals("Light Machine Gun", actualCreateCLPROLightMGAmmoResult.shortName);
        assertNull(actualCreateCLPROLightMGAmmoResult.modes);
        assertTrue(actualCreateCLPROLightMGAmmoResult.explosive);
        assertEquals(1, actualCreateCLPROLightMGAmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLPROLightMGAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLPROLightMGAmmoResult.isSpreadable());
        assertFalse(actualCreateCLPROLightMGAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLPROLightMGAmmoResult.isMixedTech());
        assertTrue(actualCreateCLPROLightMGAmmoResult.isHittable());
        assertFalse(actualCreateCLPROLightMGAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLPROLightMGAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLPROLightMGAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLPROLightMGAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateCLPROLightMGAmmoResult.getStaticTechLevel());
        assertEquals("228,TM", actualCreateCLPROLightMGAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLPROLightMGAmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLPROLightMGAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateCLPROLightMGAmmoResult.getPrototypeDate());
        assertEquals("Light Machine Gun Ammo", actualCreateCLPROLightMGAmmoResult.getName());
        assertEquals("Clan Light Machine Gun Ammo - Proto", actualCreateCLPROLightMGAmmoResult.getInternalName());
        assertEquals("C/X-X-C-B", actualCreateCLPROLightMGAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLPROLightMGAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLPROLightMGAmmoResult.getFlags());
    }

    private void createBAISLRM1Ammo(AmmoType actualCreateBAISLRM1AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBAISLRM1AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBAISLRM1AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBAISLRM1AmmoResult.svslots);
        assertEquals("LRM 1", actualCreateBAISLRM1AmmoResult.shortName);
        assertTrue(actualCreateBAISLRM1AmmoResult.explosive);
        assertEquals(1, actualCreateBAISLRM1AmmoResult.criticals);
        assertEquals(2.0, actualCreateBAISLRM1AmmoResult.bv, 0.0);
        assertFalse(actualCreateBAISLRM1AmmoResult.isSpreadable());
        assertFalse(actualCreateBAISLRM1AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBAISLRM1AmmoResult.isMixedTech());
        assertTrue(actualCreateBAISLRM1AmmoResult.isHittable());
        assertTrue(actualCreateBAISLRM1AmmoResult.hasModes());
        assertFalse(actualCreateBAISLRM1AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBAISLRM1AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBAISLRM1AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBAISLRM1AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBAISLRM1AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBAISLRM1AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBAISLRM1AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateBAISLRM1AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateBAISLRM1AmmoResult.getPrototypeDate());
        assertEquals("BA LRM 1 Ammo", actualCreateBAISLRM1AmmoResult.getName());
        assertEquals("IS BA Ammo LRM-1", actualCreateBAISLRM1AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateBAISLRM1AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBAISLRM1AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBAISLRM1AmmoResult.getFlags());
    }

    private void createBAISLRM2Ammo(AmmoType actualCreateBAISLRM2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBAISLRM2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBAISLRM2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBAISLRM2AmmoResult.svslots);
        assertEquals("LRM 2", actualCreateBAISLRM2AmmoResult.shortName);
        assertTrue(actualCreateBAISLRM2AmmoResult.explosive);
        assertEquals(1, actualCreateBAISLRM2AmmoResult.criticals);
        assertEquals(3.0, actualCreateBAISLRM2AmmoResult.bv, 0.0);
        assertFalse(actualCreateBAISLRM2AmmoResult.isSpreadable());
        assertFalse(actualCreateBAISLRM2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBAISLRM2AmmoResult.isMixedTech());
        assertTrue(actualCreateBAISLRM2AmmoResult.isHittable());
        assertTrue(actualCreateBAISLRM2AmmoResult.hasModes());
        assertFalse(actualCreateBAISLRM2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBAISLRM2AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBAISLRM2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBAISLRM2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBAISLRM2AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBAISLRM2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBAISLRM2AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateBAISLRM2AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateBAISLRM2AmmoResult.getPrototypeDate());
        assertEquals("BA LRM 2 Ammo", actualCreateBAISLRM2AmmoResult.getName());
        assertEquals("IS BA Ammo LRM-2", actualCreateBAISLRM2AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateBAISLRM2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBAISLRM2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBAISLRM2AmmoResult.getFlags());
    }

    private void createBAISLRM3Ammo(AmmoType actualCreateBAISLRM3AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBAISLRM3AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBAISLRM3AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBAISLRM3AmmoResult.svslots);
        assertEquals("LRM 3", actualCreateBAISLRM3AmmoResult.shortName);
        assertTrue(actualCreateBAISLRM3AmmoResult.explosive);
        assertEquals(1, actualCreateBAISLRM3AmmoResult.criticals);
        assertEquals(4.0, actualCreateBAISLRM3AmmoResult.bv, 0.0);
        assertFalse(actualCreateBAISLRM3AmmoResult.isSpreadable());
        assertFalse(actualCreateBAISLRM3AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBAISLRM3AmmoResult.isMixedTech());
        assertTrue(actualCreateBAISLRM3AmmoResult.isHittable());
        assertTrue(actualCreateBAISLRM3AmmoResult.hasModes());
        assertFalse(actualCreateBAISLRM3AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBAISLRM3AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBAISLRM3AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBAISLRM3AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBAISLRM3AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBAISLRM3AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBAISLRM3AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateBAISLRM3AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateBAISLRM3AmmoResult.getPrototypeDate());
        assertEquals("BA LRM 3 Ammo", actualCreateBAISLRM3AmmoResult.getName());
        assertEquals("IS BA Ammo LRM-3", actualCreateBAISLRM3AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateBAISLRM3AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBAISLRM3AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBAISLRM3AmmoResult.getFlags());
    }

    private void createBAISLRM4Ammo(AmmoType actualCreateBAISLRM4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBAISLRM4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBAISLRM4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBAISLRM4AmmoResult.svslots);
        assertEquals("LRM 4", actualCreateBAISLRM4AmmoResult.shortName);
        assertTrue(actualCreateBAISLRM4AmmoResult.explosive);
        assertEquals(1, actualCreateBAISLRM4AmmoResult.criticals);
        assertEquals(5.0, actualCreateBAISLRM4AmmoResult.bv, 0.0);
        assertFalse(actualCreateBAISLRM4AmmoResult.isSpreadable());
        assertFalse(actualCreateBAISLRM4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBAISLRM4AmmoResult.isMixedTech());
        assertTrue(actualCreateBAISLRM4AmmoResult.isHittable());
        assertTrue(actualCreateBAISLRM4AmmoResult.hasModes());
        assertFalse(actualCreateBAISLRM4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBAISLRM4AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBAISLRM4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBAISLRM4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBAISLRM4AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBAISLRM4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBAISLRM4AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateBAISLRM4AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateBAISLRM4AmmoResult.getPrototypeDate());
        assertEquals("BA LRM 4 Ammo", actualCreateBAISLRM4AmmoResult.getName());
        assertEquals("IS BA Ammo LRM-4", actualCreateBAISLRM4AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateBAISLRM4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBAISLRM4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBAISLRM4AmmoResult.getFlags());
    }

    private void createBAISLRM5Ammo(AmmoType actualCreateBAISLRM5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBAISLRM5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBAISLRM5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBAISLRM5AmmoResult.svslots);
        assertEquals("LRM 5", actualCreateBAISLRM5AmmoResult.shortName);
        assertTrue(actualCreateBAISLRM5AmmoResult.explosive);
        assertEquals(1, actualCreateBAISLRM5AmmoResult.criticals);
        assertEquals(6.0, actualCreateBAISLRM5AmmoResult.bv, 0.0);
        assertFalse(actualCreateBAISLRM5AmmoResult.isSpreadable());
        assertFalse(actualCreateBAISLRM5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBAISLRM5AmmoResult.isMixedTech());
        assertTrue(actualCreateBAISLRM5AmmoResult.isHittable());
        assertTrue(actualCreateBAISLRM5AmmoResult.hasModes());
        assertFalse(actualCreateBAISLRM5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBAISLRM5AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBAISLRM5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBAISLRM5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBAISLRM5AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBAISLRM5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBAISLRM5AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateBAISLRM5AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateBAISLRM5AmmoResult.getPrototypeDate());
        assertEquals("BA LRM 5 Ammo", actualCreateBAISLRM5AmmoResult.getName());
        assertEquals("IS BA Ammo LRM-5", actualCreateBAISLRM5AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateBAISLRM5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBAISLRM5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBAISLRM5AmmoResult.getFlags());
    }

    private void createBACLLRM1Ammo(AmmoType actualCreateBACLLRM1AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBACLLRM1AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBACLLRM1AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBACLLRM1AmmoResult.svslots);
        assertEquals("LRM 1", actualCreateBACLLRM1AmmoResult.shortName);
        assertNull(actualCreateBACLLRM1AmmoResult.modes);
        assertTrue(actualCreateBACLLRM1AmmoResult.explosive);
        assertEquals(1, actualCreateBACLLRM1AmmoResult.criticals);
        assertEquals(2.0, actualCreateBACLLRM1AmmoResult.bv, 0.0);
        assertFalse(actualCreateBACLLRM1AmmoResult.isSpreadable());
        assertFalse(actualCreateBACLLRM1AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBACLLRM1AmmoResult.isMixedTech());
        assertTrue(actualCreateBACLLRM1AmmoResult.isHittable());
        assertFalse(actualCreateBACLLRM1AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBACLLRM1AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateBACLLRM1AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBACLLRM1AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBACLLRM1AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBACLLRM1AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBACLLRM1AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateBACLLRM1AmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateBACLLRM1AmmoResult.getPrototypeDate());
        assertEquals("BA LRM 1 Ammo", actualCreateBACLLRM1AmmoResult.getName());
        assertEquals("BACL Ammo LRM-1", actualCreateBACLLRM1AmmoResult.getInternalName());
        assertEquals("F/X-X-F-D", actualCreateBACLLRM1AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBACLLRM1AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBACLLRM1AmmoResult.getFlags());
    }

    private void createBACLLRM2Ammo(AmmoType actualCreateBACLLRM2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBACLLRM2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBACLLRM2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBACLLRM2AmmoResult.svslots);
        assertEquals("LRM 2", actualCreateBACLLRM2AmmoResult.shortName);
        assertNull(actualCreateBACLLRM2AmmoResult.modes);
        assertTrue(actualCreateBACLLRM2AmmoResult.explosive);
        assertEquals(1, actualCreateBACLLRM2AmmoResult.criticals);
        assertEquals(3.0, actualCreateBACLLRM2AmmoResult.bv, 0.0);
        assertFalse(actualCreateBACLLRM2AmmoResult.isSpreadable());
        assertFalse(actualCreateBACLLRM2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBACLLRM2AmmoResult.isMixedTech());
        assertTrue(actualCreateBACLLRM2AmmoResult.isHittable());
        assertFalse(actualCreateBACLLRM2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBACLLRM2AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateBACLLRM2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBACLLRM2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBACLLRM2AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBACLLRM2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBACLLRM2AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateBACLLRM2AmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateBACLLRM2AmmoResult.getPrototypeDate());
        assertEquals("BA LRM 2 Ammo", actualCreateBACLLRM2AmmoResult.getName());
        assertEquals("BACL Ammo LRM-2", actualCreateBACLLRM2AmmoResult.getInternalName());
        assertEquals("F/X-X-F-D", actualCreateBACLLRM2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBACLLRM2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBACLLRM2AmmoResult.getFlags());
    }

    private void createBACLLRM3Ammo(AmmoType actualCreateBACLLRM3AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBACLLRM3AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBACLLRM3AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBACLLRM3AmmoResult.svslots);
        assertEquals("LRM 3", actualCreateBACLLRM3AmmoResult.shortName);
        assertNull(actualCreateBACLLRM3AmmoResult.modes);
        assertTrue(actualCreateBACLLRM3AmmoResult.explosive);
        assertEquals(1, actualCreateBACLLRM3AmmoResult.criticals);
        assertEquals(5.0, actualCreateBACLLRM3AmmoResult.bv, 0.0);
        assertFalse(actualCreateBACLLRM3AmmoResult.isSpreadable());
        assertFalse(actualCreateBACLLRM3AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBACLLRM3AmmoResult.isMixedTech());
        assertTrue(actualCreateBACLLRM3AmmoResult.isHittable());
        assertFalse(actualCreateBACLLRM3AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBACLLRM3AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateBACLLRM3AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBACLLRM3AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBACLLRM3AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBACLLRM3AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBACLLRM3AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateBACLLRM3AmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateBACLLRM3AmmoResult.getPrototypeDate());
        assertEquals("BA LRM 3 Ammo", actualCreateBACLLRM3AmmoResult.getName());
        assertEquals("BACL Ammo LRM-3", actualCreateBACLLRM3AmmoResult.getInternalName());
        assertEquals("F/X-X-F-D", actualCreateBACLLRM3AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBACLLRM3AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBACLLRM3AmmoResult.getFlags());
    }

    private void createBACLLRM4Ammo(AmmoType actualCreateBACLLRM4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBACLLRM4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBACLLRM4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBACLLRM4AmmoResult.svslots);
        assertEquals("LRM 4", actualCreateBACLLRM4AmmoResult.shortName);
        assertNull(actualCreateBACLLRM4AmmoResult.modes);
        assertTrue(actualCreateBACLLRM4AmmoResult.explosive);
        assertEquals(1, actualCreateBACLLRM4AmmoResult.criticals);
        assertEquals(6.0, actualCreateBACLLRM4AmmoResult.bv, 0.0);
        assertFalse(actualCreateBACLLRM4AmmoResult.isSpreadable());
        assertFalse(actualCreateBACLLRM4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBACLLRM4AmmoResult.isMixedTech());
        assertTrue(actualCreateBACLLRM4AmmoResult.isHittable());
        assertFalse(actualCreateBACLLRM4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBACLLRM4AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateBACLLRM4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBACLLRM4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBACLLRM4AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBACLLRM4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBACLLRM4AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateBACLLRM4AmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateBACLLRM4AmmoResult.getPrototypeDate());
        assertEquals("BA LRM 4 Ammo", actualCreateBACLLRM4AmmoResult.getName());
        assertEquals("BACL Ammo LRM-4", actualCreateBACLLRM4AmmoResult.getInternalName());
        assertEquals("F/X-X-F-D", actualCreateBACLLRM4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBACLLRM4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBACLLRM4AmmoResult.getFlags());
    }

    private void createBACLLRM5Ammo(AmmoType actualCreateBACLLRM5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBACLLRM5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBACLLRM5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBACLLRM5AmmoResult.svslots);
        assertEquals("LRM 5", actualCreateBACLLRM5AmmoResult.shortName);
        assertNull(actualCreateBACLLRM5AmmoResult.modes);
        assertTrue(actualCreateBACLLRM5AmmoResult.explosive);
        assertEquals(1, actualCreateBACLLRM5AmmoResult.criticals);
        assertEquals(7.0, actualCreateBACLLRM5AmmoResult.bv, 0.0);
        assertFalse(actualCreateBACLLRM5AmmoResult.isSpreadable());
        assertFalse(actualCreateBACLLRM5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBACLLRM5AmmoResult.isMixedTech());
        assertTrue(actualCreateBACLLRM5AmmoResult.isHittable());
        assertFalse(actualCreateBACLLRM5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBACLLRM5AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateBACLLRM5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBACLLRM5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBACLLRM5AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBACLLRM5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBACLLRM5AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateBACLLRM5AmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateBACLLRM5AmmoResult.getPrototypeDate());
        assertEquals("BA LRM 5 Ammo", actualCreateBACLLRM5AmmoResult.getName());
        assertEquals("BACL Ammo LRM-5", actualCreateBACLLRM5AmmoResult.getInternalName());
        assertEquals("F/X-X-F-D", actualCreateBACLLRM5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBACLLRM5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBACLLRM5AmmoResult.getFlags());
    }

    private void createBASRM1Ammo(AmmoType actualCreateBASRM1AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBASRM1AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBASRM1AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBASRM1AmmoResult.svslots);
        assertEquals("SRM 1", actualCreateBASRM1AmmoResult.shortName);
        assertNull(actualCreateBASRM1AmmoResult.modes);
        assertTrue(actualCreateBASRM1AmmoResult.explosive);
        assertEquals(1, actualCreateBASRM1AmmoResult.criticals);
        assertEquals(2.0, actualCreateBASRM1AmmoResult.bv, 0.0);
        assertFalse(actualCreateBASRM1AmmoResult.isSpreadable());
        assertFalse(actualCreateBASRM1AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateBASRM1AmmoResult.isMixedTech());
        assertTrue(actualCreateBASRM1AmmoResult.isHittable());
        assertFalse(actualCreateBASRM1AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBASRM1AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBASRM1AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBASRM1AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBASRM1AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBASRM1AmmoResult.getRulesRefs());
        assertEquals(0.0, actualCreateBASRM1AmmoResult.getRawCost(), 0.0);
        assertEquals(2860, actualCreateBASRM1AmmoResult.getPrototypeDate());
        assertEquals("BA SRM 1 Ammo", actualCreateBASRM1AmmoResult.getName());
        assertEquals("BA-SRM1 Ammo", actualCreateBASRM1AmmoResult.getInternalName());
        assertEquals("E/X-D-C-B", actualCreateBASRM1AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBASRM1AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBASRM1AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateBASRM1AmmoResult.getExtinctionDate());
    }

    private void createBASRM2Ammo(AmmoType actualCreateBASRM2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBASRM2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBASRM2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBASRM2AmmoResult.svslots);
        assertEquals("SRM 2", actualCreateBASRM2AmmoResult.shortName);
        assertNull(actualCreateBASRM2AmmoResult.modes);
        assertTrue(actualCreateBASRM2AmmoResult.explosive);
        assertEquals(1, actualCreateBASRM2AmmoResult.criticals);
        assertEquals(3.0, actualCreateBASRM2AmmoResult.bv, 0.0);
        assertFalse(actualCreateBASRM2AmmoResult.isSpreadable());
        assertFalse(actualCreateBASRM2AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateBASRM2AmmoResult.isMixedTech());
        assertTrue(actualCreateBASRM2AmmoResult.isHittable());
        assertFalse(actualCreateBASRM2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBASRM2AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBASRM2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBASRM2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBASRM2AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBASRM2AmmoResult.getRulesRefs());
        assertEquals(0.0, actualCreateBASRM2AmmoResult.getRawCost(), 0.0);
        assertEquals(2860, actualCreateBASRM2AmmoResult.getPrototypeDate());
        assertEquals("BA SRM 2 Ammo", actualCreateBASRM2AmmoResult.getName());
        assertEquals("BA-SRM2 Ammo", actualCreateBASRM2AmmoResult.getInternalName());
        assertEquals("E/X-D-C-B", actualCreateBASRM2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBASRM2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBASRM2AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateBASRM2AmmoResult.getExtinctionDate());
    }

    private void createBASRM3Ammo(AmmoType actualCreateBASRM3AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBASRM3AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBASRM3AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBASRM3AmmoResult.svslots);
        assertEquals("SRM 3", actualCreateBASRM3AmmoResult.shortName);
        assertNull(actualCreateBASRM3AmmoResult.modes);
        assertTrue(actualCreateBASRM3AmmoResult.explosive);
        assertEquals(1, actualCreateBASRM3AmmoResult.criticals);
        assertEquals(4.0, actualCreateBASRM3AmmoResult.bv, 0.0);
        assertFalse(actualCreateBASRM3AmmoResult.isSpreadable());
        assertFalse(actualCreateBASRM3AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateBASRM3AmmoResult.isMixedTech());
        assertTrue(actualCreateBASRM3AmmoResult.isHittable());
        assertFalse(actualCreateBASRM3AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBASRM3AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBASRM3AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBASRM3AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBASRM3AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBASRM3AmmoResult.getRulesRefs());
        assertEquals(0.0, actualCreateBASRM3AmmoResult.getRawCost(), 0.0);
        assertEquals(2860, actualCreateBASRM3AmmoResult.getPrototypeDate());
        assertEquals("BA SRM 3 Ammo", actualCreateBASRM3AmmoResult.getName());
        assertEquals("BA-SRM3 Ammo", actualCreateBASRM3AmmoResult.getInternalName());
        assertEquals("E/X-D-C-B", actualCreateBASRM3AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBASRM3AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBASRM3AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateBASRM3AmmoResult.getExtinctionDate());
    }

    private void createBASRM4Ammo(AmmoType actualCreateBASRM4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBASRM4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBASRM4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBASRM4AmmoResult.svslots);
        assertEquals("SRM 4", actualCreateBASRM4AmmoResult.shortName);
        assertNull(actualCreateBASRM4AmmoResult.modes);
        assertTrue(actualCreateBASRM4AmmoResult.explosive);
        assertEquals(1, actualCreateBASRM4AmmoResult.criticals);
        assertEquals(5.0, actualCreateBASRM4AmmoResult.bv, 0.0);
        assertFalse(actualCreateBASRM4AmmoResult.isSpreadable());
        assertFalse(actualCreateBASRM4AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateBASRM4AmmoResult.isMixedTech());
        assertTrue(actualCreateBASRM4AmmoResult.isHittable());
        assertFalse(actualCreateBASRM4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBASRM4AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBASRM4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBASRM4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBASRM4AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBASRM4AmmoResult.getRulesRefs());
        assertEquals(0.0, actualCreateBASRM4AmmoResult.getRawCost(), 0.0);
        assertEquals(2860, actualCreateBASRM4AmmoResult.getPrototypeDate());
        assertEquals("BA SRM 4 Ammo", actualCreateBASRM4AmmoResult.getName());
        assertEquals("BA-SRM4 Ammo", actualCreateBASRM4AmmoResult.getInternalName());
        assertEquals("E/X-D-C-B", actualCreateBASRM4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBASRM4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBASRM4AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateBASRM4AmmoResult.getExtinctionDate());
    }

    private void createBASRM5Ammo(AmmoType actualCreateBASRM5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBASRM5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBASRM5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBASRM5AmmoResult.svslots);
        assertEquals("SRM 5", actualCreateBASRM5AmmoResult.shortName);
        assertNull(actualCreateBASRM5AmmoResult.modes);
        assertTrue(actualCreateBASRM5AmmoResult.explosive);
        assertEquals(1, actualCreateBASRM5AmmoResult.criticals);
        assertEquals(6.0, actualCreateBASRM5AmmoResult.bv, 0.0);
        assertFalse(actualCreateBASRM5AmmoResult.isSpreadable());
        assertFalse(actualCreateBASRM5AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateBASRM5AmmoResult.isMixedTech());
        assertTrue(actualCreateBASRM5AmmoResult.isHittable());
        assertFalse(actualCreateBASRM5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBASRM5AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBASRM5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBASRM5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBASRM5AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBASRM5AmmoResult.getRulesRefs());
        assertEquals(0.0, actualCreateBASRM5AmmoResult.getRawCost(), 0.0);
        assertEquals(2860, actualCreateBASRM5AmmoResult.getPrototypeDate());
        assertEquals("BA SRM 5 Ammo", actualCreateBASRM5AmmoResult.getName());
        assertEquals("BA-SRM5 Ammo", actualCreateBASRM5AmmoResult.getInternalName());
        assertEquals("E/X-D-C-B", actualCreateBASRM5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBASRM5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBASRM5AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateBASRM5AmmoResult.getExtinctionDate());
    }

    private void createBASRM6Ammo(AmmoType actualCreateBASRM6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBASRM6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBASRM6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBASRM6AmmoResult.svslots);
        assertEquals("SRM 6", actualCreateBASRM6AmmoResult.shortName);
        assertNull(actualCreateBASRM6AmmoResult.modes);
        assertTrue(actualCreateBASRM6AmmoResult.explosive);
        assertEquals(1, actualCreateBASRM6AmmoResult.criticals);
        assertEquals(7.0, actualCreateBASRM6AmmoResult.bv, 0.0);
        assertFalse(actualCreateBASRM6AmmoResult.isSpreadable());
        assertFalse(actualCreateBASRM6AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateBASRM6AmmoResult.isMixedTech());
        assertTrue(actualCreateBASRM6AmmoResult.isHittable());
        assertFalse(actualCreateBASRM6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBASRM6AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBASRM6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBASRM6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBASRM6AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBASRM6AmmoResult.getRulesRefs());
        assertEquals(0.0, actualCreateBASRM6AmmoResult.getRawCost(), 0.0);
        assertEquals(2860, actualCreateBASRM6AmmoResult.getPrototypeDate());
        assertEquals("BA SRM 6 Ammo", actualCreateBASRM6AmmoResult.getName());
        assertEquals("BA-SRM6 Ammo", actualCreateBASRM6AmmoResult.getInternalName());
        assertEquals("E/X-D-C-B", actualCreateBASRM6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBASRM6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBASRM6AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateBASRM6AmmoResult.getExtinctionDate());
    }

    private void createAdvancedSRM1Ammo(AmmoType actualCreateAdvancedSRM1AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateAdvancedSRM1AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateAdvancedSRM1AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateAdvancedSRM1AmmoResult.svslots);
        assertEquals("Advanced SRM 1", actualCreateAdvancedSRM1AmmoResult.shortName);
        assertNull(actualCreateAdvancedSRM1AmmoResult.modes);
        assertTrue(actualCreateAdvancedSRM1AmmoResult.explosive);
        assertEquals(1, actualCreateAdvancedSRM1AmmoResult.criticals);
        assertEquals(2.0, actualCreateAdvancedSRM1AmmoResult.bv, 0.0);
        assertFalse(actualCreateAdvancedSRM1AmmoResult.isSpreadable());
        assertFalse(actualCreateAdvancedSRM1AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateAdvancedSRM1AmmoResult.isMixedTech());
        assertTrue(actualCreateAdvancedSRM1AmmoResult.isHittable());
        assertFalse(actualCreateAdvancedSRM1AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateAdvancedSRM1AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateAdvancedSRM1AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateAdvancedSRM1AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateAdvancedSRM1AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateAdvancedSRM1AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateAdvancedSRM1AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateAdvancedSRM1AmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateAdvancedSRM1AmmoResult.getPrototypeDate());
        assertEquals("Advanced SRM 1 Ammo", actualCreateAdvancedSRM1AmmoResult.getName());
        assertEquals("BA-Advanced SRM-1 Ammo", actualCreateAdvancedSRM1AmmoResult.getInternalName());
        assertEquals("F/X-X-F-D", actualCreateAdvancedSRM1AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateAdvancedSRM1AmmoResult.flags;
        assertSame(expectedFlags, actualCreateAdvancedSRM1AmmoResult.getFlags());
    }

    private void createAdvancedSRM2Ammo(AmmoType actualCreateAdvancedSRM2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateAdvancedSRM2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateAdvancedSRM2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateAdvancedSRM2AmmoResult.svslots);
        assertEquals("Advanced SRM 2", actualCreateAdvancedSRM2AmmoResult.shortName);
        assertNull(actualCreateAdvancedSRM2AmmoResult.modes);
        assertTrue(actualCreateAdvancedSRM2AmmoResult.explosive);
        assertEquals(1, actualCreateAdvancedSRM2AmmoResult.criticals);
        assertEquals(4.0, actualCreateAdvancedSRM2AmmoResult.bv, 0.0);
        assertFalse(actualCreateAdvancedSRM2AmmoResult.isSpreadable());
        assertFalse(actualCreateAdvancedSRM2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateAdvancedSRM2AmmoResult.isMixedTech());
        assertTrue(actualCreateAdvancedSRM2AmmoResult.isHittable());
        assertFalse(actualCreateAdvancedSRM2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateAdvancedSRM2AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateAdvancedSRM2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateAdvancedSRM2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateAdvancedSRM2AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateAdvancedSRM2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateAdvancedSRM2AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateAdvancedSRM2AmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateAdvancedSRM2AmmoResult.getPrototypeDate());
        assertEquals("Advanced SRM 2 Ammo", actualCreateAdvancedSRM2AmmoResult.getName());
        assertEquals("BA-Advanced SRM-2 Ammo", actualCreateAdvancedSRM2AmmoResult.getInternalName());
        assertEquals("F/X-X-F-D", actualCreateAdvancedSRM2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateAdvancedSRM2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateAdvancedSRM2AmmoResult.getFlags());
    }

    private void createAdvancedSRM3Ammo(AmmoType actualCreateAdvancedSRM3AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateAdvancedSRM3AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateAdvancedSRM3AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateAdvancedSRM3AmmoResult.svslots);
        assertEquals("Advanced SRM 3", actualCreateAdvancedSRM3AmmoResult.shortName);
        assertNull(actualCreateAdvancedSRM3AmmoResult.modes);
        assertTrue(actualCreateAdvancedSRM3AmmoResult.explosive);
        assertEquals(1, actualCreateAdvancedSRM3AmmoResult.criticals);
        assertEquals(6.0, actualCreateAdvancedSRM3AmmoResult.bv, 0.0);
        assertFalse(actualCreateAdvancedSRM3AmmoResult.isSpreadable());
        assertFalse(actualCreateAdvancedSRM3AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateAdvancedSRM3AmmoResult.isMixedTech());
        assertTrue(actualCreateAdvancedSRM3AmmoResult.isHittable());
        assertFalse(actualCreateAdvancedSRM3AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateAdvancedSRM3AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateAdvancedSRM3AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateAdvancedSRM3AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateAdvancedSRM3AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateAdvancedSRM3AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateAdvancedSRM3AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateAdvancedSRM3AmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateAdvancedSRM3AmmoResult.getPrototypeDate());
        assertEquals("Advanced SRM 3 Ammo", actualCreateAdvancedSRM3AmmoResult.getName());
        assertEquals("BA-Advanced SRM-3 Ammo", actualCreateAdvancedSRM3AmmoResult.getInternalName());
        assertEquals("F/X-X-F-D", actualCreateAdvancedSRM3AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateAdvancedSRM3AmmoResult.flags;
        assertSame(expectedFlags, actualCreateAdvancedSRM3AmmoResult.getFlags());
    }

    private void createAdvancedSRM4Ammo(AmmoType actualCreateAdvancedSRM4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateAdvancedSRM4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateAdvancedSRM4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateAdvancedSRM4AmmoResult.svslots);
        assertEquals("Advanced SRM 4", actualCreateAdvancedSRM4AmmoResult.shortName);
        assertNull(actualCreateAdvancedSRM4AmmoResult.modes);
        assertTrue(actualCreateAdvancedSRM4AmmoResult.explosive);
        assertEquals(1, actualCreateAdvancedSRM4AmmoResult.criticals);
        assertEquals(8.0, actualCreateAdvancedSRM4AmmoResult.bv, 0.0);
        assertFalse(actualCreateAdvancedSRM4AmmoResult.isSpreadable());
        assertFalse(actualCreateAdvancedSRM4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateAdvancedSRM4AmmoResult.isMixedTech());
        assertTrue(actualCreateAdvancedSRM4AmmoResult.isHittable());
        assertFalse(actualCreateAdvancedSRM4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateAdvancedSRM4AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateAdvancedSRM4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateAdvancedSRM4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateAdvancedSRM4AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateAdvancedSRM4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateAdvancedSRM4AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateAdvancedSRM4AmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateAdvancedSRM4AmmoResult.getPrototypeDate());
        assertEquals("Advanced SRM 4 Ammo", actualCreateAdvancedSRM4AmmoResult.getName());
        assertEquals("BA-Advanced SRM-4 Ammo", actualCreateAdvancedSRM4AmmoResult.getInternalName());
        assertEquals("F/X-X-F-D", actualCreateAdvancedSRM4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateAdvancedSRM4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateAdvancedSRM4AmmoResult.getFlags());
    }

    private void createAdvancedSRM5Ammo(AmmoType actualCreateAdvancedSRM5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateAdvancedSRM5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateAdvancedSRM5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateAdvancedSRM5AmmoResult.svslots);
        assertEquals("Advanced SRM 5", actualCreateAdvancedSRM5AmmoResult.shortName);
        assertNull(actualCreateAdvancedSRM5AmmoResult.modes);
        assertTrue(actualCreateAdvancedSRM5AmmoResult.explosive);
        assertEquals(1, actualCreateAdvancedSRM5AmmoResult.criticals);
        assertEquals(10.0, actualCreateAdvancedSRM5AmmoResult.bv, 0.0);
        assertFalse(actualCreateAdvancedSRM5AmmoResult.isSpreadable());
        assertFalse(actualCreateAdvancedSRM5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateAdvancedSRM5AmmoResult.isMixedTech());
        assertTrue(actualCreateAdvancedSRM5AmmoResult.isHittable());
        assertFalse(actualCreateAdvancedSRM5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateAdvancedSRM5AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateAdvancedSRM5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateAdvancedSRM5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateAdvancedSRM5AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateAdvancedSRM5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateAdvancedSRM5AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateAdvancedSRM5AmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateAdvancedSRM5AmmoResult.getPrototypeDate());
        assertEquals("Advanced SRM 5 Ammo", actualCreateAdvancedSRM5AmmoResult.getName());
        assertEquals("BA-Advanced SRM-5 Ammo", actualCreateAdvancedSRM5AmmoResult.getInternalName());
        assertEquals("F/X-X-F-D", actualCreateAdvancedSRM5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateAdvancedSRM5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateAdvancedSRM5AmmoResult.getFlags());
    }

    private void createAdvancedSRM6Ammo(AmmoType actualCreateAdvancedSRM6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateAdvancedSRM6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateAdvancedSRM6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateAdvancedSRM6AmmoResult.svslots);
        assertEquals("Advanced SRM 6", actualCreateAdvancedSRM6AmmoResult.shortName);
        assertNull(actualCreateAdvancedSRM6AmmoResult.modes);
        assertTrue(actualCreateAdvancedSRM6AmmoResult.explosive);
        assertEquals(1, actualCreateAdvancedSRM6AmmoResult.criticals);
        assertEquals(12.0, actualCreateAdvancedSRM6AmmoResult.bv, 0.0);
        assertFalse(actualCreateAdvancedSRM6AmmoResult.isSpreadable());
        assertFalse(actualCreateAdvancedSRM6AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateAdvancedSRM6AmmoResult.isMixedTech());
        assertTrue(actualCreateAdvancedSRM6AmmoResult.isHittable());
        assertFalse(actualCreateAdvancedSRM6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateAdvancedSRM6AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateAdvancedSRM6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateAdvancedSRM6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateAdvancedSRM6AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateAdvancedSRM6AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateAdvancedSRM6AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateAdvancedSRM6AmmoResult.getRawCost(), 0.0);
        assertEquals(3047, actualCreateAdvancedSRM6AmmoResult.getPrototypeDate());
        assertEquals("Advanced SRM 6 Ammo", actualCreateAdvancedSRM6AmmoResult.getName());
        assertEquals("BA-Advanced SRM-6 Ammo", actualCreateAdvancedSRM6AmmoResult.getInternalName());
        assertEquals("F/X-X-F-D", actualCreateAdvancedSRM6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateAdvancedSRM6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateAdvancedSRM6AmmoResult.getFlags());
    }

    private void createISMRM1Ammo(AmmoType actualCreateISMRM1AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMRM1AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMRM1AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMRM1AmmoResult.svslots);
        assertEquals("MRM 1", actualCreateISMRM1AmmoResult.shortName);
        assertNull(actualCreateISMRM1AmmoResult.modes);
        assertTrue(actualCreateISMRM1AmmoResult.explosive);
        assertEquals(1, actualCreateISMRM1AmmoResult.criticals);
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMRM1AmmoResult.bv, 0.0);
        assertFalse(actualCreateISMRM1AmmoResult.isSpreadable());
        assertFalse(actualCreateISMRM1AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMRM1AmmoResult.isMixedTech());
        assertTrue(actualCreateISMRM1AmmoResult.isHittable());
        assertFalse(actualCreateISMRM1AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMRM1AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISMRM1AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMRM1AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMRM1AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateISMRM1AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMRM1AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateISMRM1AmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateISMRM1AmmoResult.getPrototypeDate());
        assertEquals("MRM 1 Ammo", actualCreateISMRM1AmmoResult.getName());
        assertEquals("IS MRM 1 Ammo", actualCreateISMRM1AmmoResult.getInternalName());
        assertEquals("E/X-X-D-B", actualCreateISMRM1AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMRM1AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMRM1AmmoResult.getFlags());
    }

    private void createISMRM2Ammo(AmmoType actualCreateISMRM2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMRM2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMRM2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMRM2AmmoResult.svslots);
        assertEquals("MRM 2", actualCreateISMRM2AmmoResult.shortName);
        assertNull(actualCreateISMRM2AmmoResult.modes);
        assertTrue(actualCreateISMRM2AmmoResult.explosive);
        assertEquals(1, actualCreateISMRM2AmmoResult.criticals);
        assertEquals(2.0, actualCreateISMRM2AmmoResult.bv, 0.0);
        assertFalse(actualCreateISMRM2AmmoResult.isSpreadable());
        assertFalse(actualCreateISMRM2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMRM2AmmoResult.isMixedTech());
        assertTrue(actualCreateISMRM2AmmoResult.isHittable());
        assertFalse(actualCreateISMRM2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMRM2AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISMRM2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMRM2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMRM2AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateISMRM2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMRM2AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateISMRM2AmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateISMRM2AmmoResult.getPrototypeDate());
        assertEquals("MRM 2 Ammo", actualCreateISMRM2AmmoResult.getName());
        assertEquals("IS MRM 2 Ammo", actualCreateISMRM2AmmoResult.getInternalName());
        assertEquals("E/X-X-D-B", actualCreateISMRM2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMRM2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMRM2AmmoResult.getFlags());
    }

    private void createISMRM3Ammo(AmmoType actualCreateISMRM3AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMRM3AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMRM3AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMRM3AmmoResult.svslots);
        assertEquals("MRM 3", actualCreateISMRM3AmmoResult.shortName);
        assertNull(actualCreateISMRM3AmmoResult.modes);
        assertTrue(actualCreateISMRM3AmmoResult.explosive);
        assertEquals(1, actualCreateISMRM3AmmoResult.criticals);
        assertEquals(2.0, actualCreateISMRM3AmmoResult.bv, 0.0);
        assertFalse(actualCreateISMRM3AmmoResult.isSpreadable());
        assertFalse(actualCreateISMRM3AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMRM3AmmoResult.isMixedTech());
        assertTrue(actualCreateISMRM3AmmoResult.isHittable());
        assertFalse(actualCreateISMRM3AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMRM3AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISMRM3AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMRM3AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMRM3AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateISMRM3AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMRM3AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateISMRM3AmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateISMRM3AmmoResult.getPrototypeDate());
        assertEquals("MRM 3 Ammo", actualCreateISMRM3AmmoResult.getName());
        assertEquals("IS MRM 3 Ammo", actualCreateISMRM3AmmoResult.getInternalName());
        assertEquals("E/X-X-D-B", actualCreateISMRM3AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMRM3AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMRM3AmmoResult.getFlags());
    }

    private void createISMRM4Ammo(AmmoType actualCreateISMRM4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMRM4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMRM4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMRM4AmmoResult.svslots);
        assertEquals("MRM 4", actualCreateISMRM4AmmoResult.shortName);
        assertNull(actualCreateISMRM4AmmoResult.modes);
        assertTrue(actualCreateISMRM4AmmoResult.explosive);
        assertEquals(1, actualCreateISMRM4AmmoResult.criticals);
        assertEquals(3.0, actualCreateISMRM4AmmoResult.bv, 0.0);
        assertFalse(actualCreateISMRM4AmmoResult.isSpreadable());
        assertFalse(actualCreateISMRM4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMRM4AmmoResult.isMixedTech());
        assertTrue(actualCreateISMRM4AmmoResult.isHittable());
        assertFalse(actualCreateISMRM4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMRM4AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISMRM4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMRM4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMRM4AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateISMRM4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMRM4AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateISMRM4AmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateISMRM4AmmoResult.getPrototypeDate());
        assertEquals("MRM 4 Ammo", actualCreateISMRM4AmmoResult.getName());
        assertEquals("IS MRM 4 Ammo", actualCreateISMRM4AmmoResult.getInternalName());
        assertEquals("E/X-X-D-B", actualCreateISMRM4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMRM4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMRM4AmmoResult.getFlags());
    }

    private void createISMRM5Ammo(AmmoType actualCreateISMRM5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISMRM5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMRM5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMRM5AmmoResult.svslots);
        assertEquals("MRM 5", actualCreateISMRM5AmmoResult.shortName);
        assertNull(actualCreateISMRM5AmmoResult.modes);
        assertTrue(actualCreateISMRM5AmmoResult.explosive);
        assertEquals(1, actualCreateISMRM5AmmoResult.criticals);
        assertEquals(4.0, actualCreateISMRM5AmmoResult.bv, 0.0);
        assertFalse(actualCreateISMRM5AmmoResult.isSpreadable());
        assertFalse(actualCreateISMRM5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMRM5AmmoResult.isMixedTech());
        assertTrue(actualCreateISMRM5AmmoResult.isHittable());
        assertFalse(actualCreateISMRM5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISMRM5AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISMRM5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMRM5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMRM5AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateISMRM5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMRM5AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateISMRM5AmmoResult.getRawCost(), 0.0);
        assertEquals(3053, actualCreateISMRM5AmmoResult.getPrototypeDate());
        assertEquals("MRM 5 Ammo", actualCreateISMRM5AmmoResult.getName());
        assertEquals("IS MRM 5 Ammo", actualCreateISMRM5AmmoResult.getInternalName());
        assertEquals("E/X-X-D-B", actualCreateISMRM5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMRM5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMRM5AmmoResult.getFlags());
    }

    private void createISBATaserAmmo(AmmoType actualCreateISBATaserAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISBATaserAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISBATaserAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISBATaserAmmoResult.svslots);
        assertEquals("Taser", actualCreateISBATaserAmmoResult.shortName);
        assertNull(actualCreateISBATaserAmmoResult.modes);
        assertTrue(actualCreateISBATaserAmmoResult.explosive);
        assertEquals(1, actualCreateISBATaserAmmoResult.criticals);
        assertEquals(0.0, actualCreateISBATaserAmmoResult.bv, 0.0);
        assertFalse(actualCreateISBATaserAmmoResult.isSpreadable());
        assertFalse(actualCreateISBATaserAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISBATaserAmmoResult.isMixedTech());
        assertTrue(actualCreateISBATaserAmmoResult.isHittable());
        assertFalse(actualCreateISBATaserAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISBATaserAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISBATaserAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISBATaserAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISBATaserAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateISBATaserAmmoResult.getStandardRange());
        assertEquals("345,TO", actualCreateISBATaserAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISBATaserAmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateISBATaserAmmoResult.getRawCost(), 0.0);
        assertEquals("BA Taser Ammo", actualCreateISBATaserAmmoResult.getName());
        assertEquals("BA Taser Ammo", actualCreateISBATaserAmmoResult.getInternalName());
        assertEquals("E/X-X-E-E", actualCreateISBATaserAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISBATaserAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISBATaserAmmoResult.getFlags());
    }

    private void createBARL1Ammo(AmmoType actualCreateBARL1AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBARL1AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBARL1AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBARL1AmmoResult.svslots);
        assertEquals("", actualCreateBARL1AmmoResult.shortName);
        assertNull(actualCreateBARL1AmmoResult.modes);
        assertTrue(actualCreateBARL1AmmoResult.explosive);
        assertEquals(1, actualCreateBARL1AmmoResult.criticals);
        assertEquals(0.0, actualCreateBARL1AmmoResult.bv, 0.0);
        assertFalse(actualCreateBARL1AmmoResult.isSpreadable());
        assertFalse(actualCreateBARL1AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBARL1AmmoResult.isMixedTech());
        assertTrue(actualCreateBARL1AmmoResult.isHittable());
        assertFalse(actualCreateBARL1AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBARL1AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBARL1AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBARL1AmmoResult.getSubType());
        assertEquals("", actualCreateBARL1AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBARL1AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBARL1AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBARL1AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateBARL1AmmoResult.getRawCost(), 0.0);
        assertEquals(3045, actualCreateBARL1AmmoResult.getPrototypeDate());
        assertEquals("BARL1 Ammo", actualCreateBARL1AmmoResult.getInternalName());
        assertEquals("E/X-X-B-B", actualCreateBARL1AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBARL1AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBARL1AmmoResult.getFlags());
    }

    private void createBARL2Ammo(AmmoType actualCreateBARL2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBARL2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBARL2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBARL2AmmoResult.svslots);
        assertEquals("", actualCreateBARL2AmmoResult.shortName);
        assertNull(actualCreateBARL2AmmoResult.modes);
        assertTrue(actualCreateBARL2AmmoResult.explosive);
        assertEquals(1, actualCreateBARL2AmmoResult.criticals);
        assertEquals(0.0, actualCreateBARL2AmmoResult.bv, 0.0);
        assertFalse(actualCreateBARL2AmmoResult.isSpreadable());
        assertFalse(actualCreateBARL2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBARL2AmmoResult.isMixedTech());
        assertTrue(actualCreateBARL2AmmoResult.isHittable());
        assertFalse(actualCreateBARL2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBARL2AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBARL2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBARL2AmmoResult.getSubType());
        assertEquals("", actualCreateBARL2AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBARL2AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBARL2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBARL2AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateBARL2AmmoResult.getRawCost(), 0.0);
        assertEquals(3045, actualCreateBARL2AmmoResult.getPrototypeDate());
        assertEquals("BARL2 Ammo", actualCreateBARL2AmmoResult.getInternalName());
        assertEquals("E/X-X-B-B", actualCreateBARL2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBARL2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBARL2AmmoResult.getFlags());
    }

    private void createBARL3Ammo(AmmoType actualCreateBARL3AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBARL3AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBARL3AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBARL3AmmoResult.svslots);
        assertEquals("", actualCreateBARL3AmmoResult.shortName);
        assertNull(actualCreateBARL3AmmoResult.modes);
        assertTrue(actualCreateBARL3AmmoResult.explosive);
        assertEquals(1, actualCreateBARL3AmmoResult.criticals);
        assertEquals(0.0, actualCreateBARL3AmmoResult.bv, 0.0);
        assertFalse(actualCreateBARL3AmmoResult.isSpreadable());
        assertFalse(actualCreateBARL3AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBARL3AmmoResult.isMixedTech());
        assertTrue(actualCreateBARL3AmmoResult.isHittable());
        assertFalse(actualCreateBARL3AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBARL3AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBARL3AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBARL3AmmoResult.getSubType());
        assertEquals("", actualCreateBARL3AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBARL3AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBARL3AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBARL3AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateBARL3AmmoResult.getRawCost(), 0.0);
        assertEquals(3045, actualCreateBARL3AmmoResult.getPrototypeDate());
        assertEquals("BARL3 Ammo", actualCreateBARL3AmmoResult.getInternalName());
        assertEquals("E/X-X-B-B", actualCreateBARL3AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBARL3AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBARL3AmmoResult.getFlags());
    }

    private void createBARL4Ammo(AmmoType actualCreateBARL4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBARL4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBARL4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBARL4AmmoResult.svslots);
        assertEquals("", actualCreateBARL4AmmoResult.shortName);
        assertNull(actualCreateBARL4AmmoResult.modes);
        assertTrue(actualCreateBARL4AmmoResult.explosive);
        assertEquals(1, actualCreateBARL4AmmoResult.criticals);
        assertEquals(0.0, actualCreateBARL4AmmoResult.bv, 0.0);
        assertFalse(actualCreateBARL4AmmoResult.isSpreadable());
        assertFalse(actualCreateBARL4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBARL4AmmoResult.isMixedTech());
        assertTrue(actualCreateBARL4AmmoResult.isHittable());
        assertFalse(actualCreateBARL4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBARL4AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBARL4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBARL4AmmoResult.getSubType());
        assertEquals("", actualCreateBARL4AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBARL4AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBARL4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBARL4AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateBARL4AmmoResult.getRawCost(), 0.0);
        assertEquals(3045, actualCreateBARL4AmmoResult.getPrototypeDate());
        assertEquals("BARL4 Ammo", actualCreateBARL4AmmoResult.getInternalName());
        assertEquals("E/X-X-B-B", actualCreateBARL4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBARL4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBARL4AmmoResult.getFlags());
    }

    private void createBARL5Ammo(AmmoType actualCreateBARL5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateBARL5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateBARL5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateBARL5AmmoResult.svslots);
        assertEquals("", actualCreateBARL5AmmoResult.shortName);
        assertNull(actualCreateBARL5AmmoResult.modes);
        assertTrue(actualCreateBARL5AmmoResult.explosive);
        assertEquals(1, actualCreateBARL5AmmoResult.criticals);
        assertEquals(0.0, actualCreateBARL5AmmoResult.bv, 0.0);
        assertFalse(actualCreateBARL5AmmoResult.isSpreadable());
        assertFalse(actualCreateBARL5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateBARL5AmmoResult.isMixedTech());
        assertTrue(actualCreateBARL5AmmoResult.isHittable());
        assertFalse(actualCreateBARL5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateBARL5AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateBARL5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateBARL5AmmoResult.getSubType());
        assertEquals("", actualCreateBARL5AmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateBARL5AmmoResult.getStaticTechLevel());
        assertEquals("261,TM", actualCreateBARL5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateBARL5AmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateBARL5AmmoResult.getRawCost(), 0.0);
        assertEquals(3045, actualCreateBARL5AmmoResult.getPrototypeDate());
        assertEquals("BARL5 Ammo", actualCreateBARL5AmmoResult.getInternalName());
        assertEquals("E/X-X-B-B", actualCreateBARL5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateBARL5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateBARL5AmmoResult.getFlags());
    }

    private void createISCoolantPod(AmmoType actualCreateISCoolantPodResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISCoolantPodResult.tonnage, 0.0);
        assertEquals(0, actualCreateISCoolantPodResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISCoolantPodResult.svslots);
        assertEquals(EquipmentTypeLookup.COOLANT_POD, actualCreateISCoolantPodResult.shortName);
        assertTrue(actualCreateISCoolantPodResult.explosive);
        assertEquals(1, actualCreateISCoolantPodResult.criticals);
        assertEquals(0.0, actualCreateISCoolantPodResult.bv, 0.0);
        assertFalse(actualCreateISCoolantPodResult.isSpreadable());
        assertFalse(actualCreateISCoolantPodResult.isOmniFixedOnly());
        assertTrue(actualCreateISCoolantPodResult.isMixedTech());
        assertTrue(actualCreateISCoolantPodResult.isHittable());
        assertTrue(actualCreateISCoolantPodResult.hasModes());
        assertTrue(actualCreateISCoolantPodResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISCoolantPodResult.getToHitModifier());
        assertEquals(3, actualCreateISCoolantPodResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISCoolantPodResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateISCoolantPodResult.getStaticTechLevel());
        assertEquals("303,TO", actualCreateISCoolantPodResult.getRulesRefs());
        assertEquals(50000.0, actualCreateISCoolantPodResult.getRawCost(), 0.0);
        assertEquals(3049, actualCreateISCoolantPodResult.getPrototypeDate());
        assertEquals(EquipmentTypeLookup.COOLANT_POD, actualCreateISCoolantPodResult.getName());
        assertEquals(EquipmentTypeLookup.COOLANT_POD, actualCreateISCoolantPodResult.getInternalName());
        assertEquals("D/X-X-E-D", actualCreateISCoolantPodResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISCoolantPodResult.flags;
        assertSame(expectedFlags, actualCreateISCoolantPodResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISCoolantPodResult.getExtinctionDate());
    }

    private void createISMPodAmmo(AmmoType actualCreateISMPodAmmoResult) {
        assertEquals(0.0, actualCreateISMPodAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISMPodAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISMPodAmmoResult.svslots);
        assertEquals("", actualCreateISMPodAmmoResult.shortName);
        assertNull(actualCreateISMPodAmmoResult.modes);
        assertTrue(actualCreateISMPodAmmoResult.explosive);
        assertEquals(1, actualCreateISMPodAmmoResult.criticals);
        assertEquals(0.0, actualCreateISMPodAmmoResult.bv, 0.0);
        assertFalse(actualCreateISMPodAmmoResult.isSpreadable());
        assertFalse(actualCreateISMPodAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISMPodAmmoResult.isMixedTech());
        assertTrue(actualCreateISMPodAmmoResult.isHittable());
        assertFalse(actualCreateISMPodAmmoResult.hasInstantModeSwitch());
        assertEquals(AmmoType.T_NA, actualCreateISMPodAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISMPodAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISMPodAmmoResult.getSubType());
        assertEquals("", actualCreateISMPodAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISMPodAmmoResult.getStaticTechLevel());
        assertEquals("330,TO", actualCreateISMPodAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISMPodAmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateISMPodAmmoResult.getRawCost(), 0.0);
        assertEquals(3055, actualCreateISMPodAmmoResult.getPrototypeDate());
        assertEquals("IS M-Pod Ammo", actualCreateISMPodAmmoResult.getInternalName());
        assertEquals("C/X-X-E-D", actualCreateISMPodAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISMPodAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISMPodAmmoResult.getFlags());
    }

    private void createCLMPodAmmo(AmmoType actualCreateCLMPodAmmoResult) {
        assertEquals(0.0, actualCreateCLMPodAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLMPodAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLMPodAmmoResult.svslots);
        assertEquals("", actualCreateCLMPodAmmoResult.shortName);
        assertNull(actualCreateCLMPodAmmoResult.modes);
        assertTrue(actualCreateCLMPodAmmoResult.explosive);
        assertEquals(1, actualCreateCLMPodAmmoResult.criticals);
        assertEquals(0.0, actualCreateCLMPodAmmoResult.bv, 0.0);
        assertFalse(actualCreateCLMPodAmmoResult.isSpreadable());
        assertFalse(actualCreateCLMPodAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLMPodAmmoResult.isMixedTech());
        assertTrue(actualCreateCLMPodAmmoResult.isHittable());
        assertFalse(actualCreateCLMPodAmmoResult.hasInstantModeSwitch());
        assertEquals(AmmoType.T_NA, actualCreateCLMPodAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateCLMPodAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLMPodAmmoResult.getSubType());
        assertEquals("", actualCreateCLMPodAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateCLMPodAmmoResult.getStaticTechLevel());
        assertEquals("Unofficial", actualCreateCLMPodAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLMPodAmmoResult.getReintroductionDate());
        assertEquals(0.0, actualCreateCLMPodAmmoResult.getRawCost(), 0.0);
        assertEquals(3055, actualCreateCLMPodAmmoResult.getPrototypeDate());
        assertEquals("Clan M-Pod Ammo", actualCreateCLMPodAmmoResult.getInternalName());
        assertEquals("C/X-X-E-D", actualCreateCLMPodAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLMPodAmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLMPodAmmoResult.getFlags());
    }

    private void createISBPodAmmo(AmmoType actualCreateISBPodAmmoResult) {
        assertEquals(0.0, actualCreateISBPodAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISBPodAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISBPodAmmoResult.svslots);
        assertEquals("", actualCreateISBPodAmmoResult.shortName);
        assertNull(actualCreateISBPodAmmoResult.modes);
        assertTrue(actualCreateISBPodAmmoResult.explosive);
        assertEquals(1, actualCreateISBPodAmmoResult.criticals);
        assertEquals(0.0, actualCreateISBPodAmmoResult.bv, 0.0);
        assertFalse(actualCreateISBPodAmmoResult.isSpreadable());
        assertFalse(actualCreateISBPodAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISBPodAmmoResult.isMixedTech());
        assertTrue(actualCreateISBPodAmmoResult.isHittable());
        assertFalse(actualCreateISBPodAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISBPodAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISBPodAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISBPodAmmoResult.getSubType());
        assertEquals("", actualCreateISBPodAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISBPodAmmoResult.getStaticTechLevel());
        assertEquals("204,TM", actualCreateISBPodAmmoResult.getRulesRefs());
        assertEquals(0.0, actualCreateISBPodAmmoResult.getRawCost(), 0.0);
        assertEquals(3060, actualCreateISBPodAmmoResult.getPrototypeDate());
        assertEquals("ISBPodAmmo", actualCreateISBPodAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISBPodAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISBPodAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISBPodAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISBPodAmmoResult.getExtinctionDate());
    }

    private void createISAC15Ammo(AmmoType actualCreateISAC15AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAC15AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAC15AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAC15AmmoResult.svslots);
        assertEquals("AC/15", actualCreateISAC15AmmoResult.shortName);
        assertNull(actualCreateISAC15AmmoResult.modes);
        assertTrue(actualCreateISAC15AmmoResult.explosive);
        assertEquals(1, actualCreateISAC15AmmoResult.criticals);
        assertEquals(22.0, actualCreateISAC15AmmoResult.bv, 0.0);
        assertFalse(actualCreateISAC15AmmoResult.isSpreadable());
        assertFalse(actualCreateISAC15AmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISAC15AmmoResult.isMixedTech());
        assertTrue(actualCreateISAC15AmmoResult.isHittable());
        assertFalse(actualCreateISAC15AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAC15AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISAC15AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAC15AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISAC15AmmoResult.getStaticTechLevel());
        assertEquals("Unoffical", actualCreateISAC15AmmoResult.getRulesRefs());
        assertEquals(8500.0, actualCreateISAC15AmmoResult.getRawCost(), 0.0);
        assertEquals(2488, actualCreateISAC15AmmoResult.getPrototypeDate());
        assertEquals("AC/15 Ammo", actualCreateISAC15AmmoResult.getName());
        assertEquals("IS Ammo AC/15", actualCreateISAC15AmmoResult.getInternalName());
        assertEquals("C/D-E-D-D", actualCreateISAC15AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAC15AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAC15AmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISAC15AmmoResult.getExtinctionDate());
    }

    private void createISTHBLB2XAmmo(AmmoType actualCreateISTHBLB2XAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISTHBLB2XAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISTHBLB2XAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISTHBLB2XAmmoResult.svslots);
        assertEquals("LB 2-X", actualCreateISTHBLB2XAmmoResult.shortName);
        assertNull(actualCreateISTHBLB2XAmmoResult.modes);
        assertTrue(actualCreateISTHBLB2XAmmoResult.explosive);
        assertEquals(1, actualCreateISTHBLB2XAmmoResult.criticals);
        assertEquals(5.0, actualCreateISTHBLB2XAmmoResult.bv, 0.0);
        assertFalse(actualCreateISTHBLB2XAmmoResult.isSpreadable());
        assertFalse(actualCreateISTHBLB2XAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISTHBLB2XAmmoResult.isMixedTech());
        assertTrue(actualCreateISTHBLB2XAmmoResult.isHittable());
        assertFalse(actualCreateISTHBLB2XAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISTHBLB2XAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISTHBLB2XAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISTHBLB2XAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISTHBLB2XAmmoResult.getStaticTechLevel());
        assertEquals("THB (Unoffical)", actualCreateISTHBLB2XAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISTHBLB2XAmmoResult.getReintroductionDate());
        assertEquals(3000.0, actualCreateISTHBLB2XAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISTHBLB2XAmmoResult.getPrototypeDate());
        assertEquals("LB 2-X AC Ammo (THB)", actualCreateISTHBLB2XAmmoResult.getName());
        assertEquals("IS LB 2-X AC Ammo (THB)", actualCreateISTHBLB2XAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISTHBLB2XAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISTHBLB2XAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISTHBLB2XAmmoResult.getFlags());
    }

    private void createISTHBLB5XAmmo(AmmoType actualCreateISTHBLB5XAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISTHBLB5XAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISTHBLB5XAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISTHBLB5XAmmoResult.svslots);
        assertEquals("LB 5-X", actualCreateISTHBLB5XAmmoResult.shortName);
        assertNull(actualCreateISTHBLB5XAmmoResult.modes);
        assertTrue(actualCreateISTHBLB5XAmmoResult.explosive);
        assertEquals(1, actualCreateISTHBLB5XAmmoResult.criticals);
        assertEquals(11.0, actualCreateISTHBLB5XAmmoResult.bv, 0.0);
        assertFalse(actualCreateISTHBLB5XAmmoResult.isSpreadable());
        assertFalse(actualCreateISTHBLB5XAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISTHBLB5XAmmoResult.isMixedTech());
        assertTrue(actualCreateISTHBLB5XAmmoResult.isHittable());
        assertFalse(actualCreateISTHBLB5XAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISTHBLB5XAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISTHBLB5XAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISTHBLB5XAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISTHBLB5XAmmoResult.getStaticTechLevel());
        assertEquals("THB (Unoffical)", actualCreateISTHBLB5XAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISTHBLB5XAmmoResult.getReintroductionDate());
        assertEquals(15000.0, actualCreateISTHBLB5XAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISTHBLB5XAmmoResult.getPrototypeDate());
        assertEquals("LB 5-X AC Ammo (THB)", actualCreateISTHBLB5XAmmoResult.getName());
        assertEquals("IS LB 5-X AC Ammo (THB)", actualCreateISTHBLB5XAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISTHBLB5XAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISTHBLB5XAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISTHBLB5XAmmoResult.getFlags());
    }

    private void createISTHBLB20XAmmo(AmmoType actualCreateISTHBLB20XAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISTHBLB20XAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISTHBLB20XAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISTHBLB20XAmmoResult.svslots);
        assertEquals("LB 20-X", actualCreateISTHBLB20XAmmoResult.shortName);
        assertNull(actualCreateISTHBLB20XAmmoResult.modes);
        assertTrue(actualCreateISTHBLB20XAmmoResult.explosive);
        assertEquals(1, actualCreateISTHBLB20XAmmoResult.criticals);
        assertEquals(26.0, actualCreateISTHBLB20XAmmoResult.bv, 0.0);
        assertFalse(actualCreateISTHBLB20XAmmoResult.isSpreadable());
        assertFalse(actualCreateISTHBLB20XAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISTHBLB20XAmmoResult.isMixedTech());
        assertTrue(actualCreateISTHBLB20XAmmoResult.isHittable());
        assertFalse(actualCreateISTHBLB20XAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISTHBLB20XAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISTHBLB20XAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISTHBLB20XAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISTHBLB20XAmmoResult.getStaticTechLevel());
        assertEquals("THB (Unoffical)", actualCreateISTHBLB20XAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISTHBLB20XAmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISTHBLB20XAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISTHBLB20XAmmoResult.getPrototypeDate());
        assertEquals("LB 20-X AC Ammo (THB)", actualCreateISTHBLB20XAmmoResult.getName());
        assertEquals("IS LB 20-X AC Ammo (THB)", actualCreateISTHBLB20XAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISTHBLB20XAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISTHBLB20XAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISTHBLB20XAmmoResult.getFlags());
    }

    private void createISTHBLB2XClusterAmmo(AmmoType actualCreateISTHBLB2XClusterAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISTHBLB2XClusterAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISTHBLB2XClusterAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISTHBLB2XClusterAmmoResult.svslots);
        assertEquals("LB 2-X Cluster", actualCreateISTHBLB2XClusterAmmoResult.shortName);
        assertNull(actualCreateISTHBLB2XClusterAmmoResult.modes);
        assertTrue(actualCreateISTHBLB2XClusterAmmoResult.explosive);
        assertEquals(1, actualCreateISTHBLB2XClusterAmmoResult.criticals);
        assertEquals(5.0, actualCreateISTHBLB2XClusterAmmoResult.bv, 0.0);
        assertFalse(actualCreateISTHBLB2XClusterAmmoResult.isSpreadable());
        assertFalse(actualCreateISTHBLB2XClusterAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISTHBLB2XClusterAmmoResult.isMixedTech());
        assertTrue(actualCreateISTHBLB2XClusterAmmoResult.isHittable());
        assertFalse(actualCreateISTHBLB2XClusterAmmoResult.hasInstantModeSwitch());
        assertEquals(AmmoType.T_NA, actualCreateISTHBLB2XClusterAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISTHBLB2XClusterAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISTHBLB2XClusterAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISTHBLB2XClusterAmmoResult.getStaticTechLevel());
        assertEquals("THB (Unoffical)", actualCreateISTHBLB2XClusterAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISTHBLB2XClusterAmmoResult.getReintroductionDate());
        assertEquals(4950.0, actualCreateISTHBLB2XClusterAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISTHBLB2XClusterAmmoResult.getPrototypeDate());
        assertEquals("LB 2-X Cluster Ammo (THB)", actualCreateISTHBLB2XClusterAmmoResult.getName());
        assertEquals("IS LB 2-X Cluster Ammo (THB)", actualCreateISTHBLB2XClusterAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISTHBLB2XClusterAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISTHBLB2XClusterAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISTHBLB2XClusterAmmoResult.getFlags());
    }

    private void createISTHBLB5XClusterAmmo(AmmoType actualCreateISTHBLB5XClusterAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISTHBLB5XClusterAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISTHBLB5XClusterAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISTHBLB5XClusterAmmoResult.svslots);
        assertEquals("LB 5-X Cluster", actualCreateISTHBLB5XClusterAmmoResult.shortName);
        assertNull(actualCreateISTHBLB5XClusterAmmoResult.modes);
        assertTrue(actualCreateISTHBLB5XClusterAmmoResult.explosive);
        assertEquals(1, actualCreateISTHBLB5XClusterAmmoResult.criticals);
        assertEquals(11.0, actualCreateISTHBLB5XClusterAmmoResult.bv, 0.0);
        assertFalse(actualCreateISTHBLB5XClusterAmmoResult.isSpreadable());
        assertFalse(actualCreateISTHBLB5XClusterAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISTHBLB5XClusterAmmoResult.isMixedTech());
        assertTrue(actualCreateISTHBLB5XClusterAmmoResult.isHittable());
        assertFalse(actualCreateISTHBLB5XClusterAmmoResult.hasInstantModeSwitch());
        assertEquals(AmmoType.T_NA, actualCreateISTHBLB5XClusterAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISTHBLB5XClusterAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISTHBLB5XClusterAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISTHBLB5XClusterAmmoResult.getStaticTechLevel());
        assertEquals("THB (Unoffical)", actualCreateISTHBLB5XClusterAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISTHBLB5XClusterAmmoResult.getReintroductionDate());
        assertEquals(25000.0, actualCreateISTHBLB5XClusterAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISTHBLB5XClusterAmmoResult.getPrototypeDate());
        assertEquals("LB 5-X Cluster Ammo (THB)", actualCreateISTHBLB5XClusterAmmoResult.getName());
        assertEquals("IS LB 5-X Cluster Ammo (THB)", actualCreateISTHBLB5XClusterAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISTHBLB5XClusterAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISTHBLB5XClusterAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISTHBLB5XClusterAmmoResult.getFlags());
    }

    private void createISTHBLB20XClusterAmmo(AmmoType actualCreateISTHBLB20XClusterAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISTHBLB20XClusterAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISTHBLB20XClusterAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISTHBLB20XClusterAmmoResult.svslots);
        assertEquals("LB 20-X Cluster", actualCreateISTHBLB20XClusterAmmoResult.shortName);
        assertNull(actualCreateISTHBLB20XClusterAmmoResult.modes);
        assertTrue(actualCreateISTHBLB20XClusterAmmoResult.explosive);
        assertEquals(1, actualCreateISTHBLB20XClusterAmmoResult.criticals);
        assertEquals(26.0, actualCreateISTHBLB20XClusterAmmoResult.bv, 0.0);
        assertFalse(actualCreateISTHBLB20XClusterAmmoResult.isSpreadable());
        assertFalse(actualCreateISTHBLB20XClusterAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISTHBLB20XClusterAmmoResult.isMixedTech());
        assertTrue(actualCreateISTHBLB20XClusterAmmoResult.isHittable());
        assertFalse(actualCreateISTHBLB20XClusterAmmoResult.hasInstantModeSwitch());
        assertEquals(AmmoType.T_NA, actualCreateISTHBLB20XClusterAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISTHBLB20XClusterAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISTHBLB20XClusterAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISTHBLB20XClusterAmmoResult.getStaticTechLevel());
        assertEquals("THB (Unoffical)", actualCreateISTHBLB20XClusterAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISTHBLB20XClusterAmmoResult.getReintroductionDate());
        assertEquals(51000.0, actualCreateISTHBLB20XClusterAmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISTHBLB20XClusterAmmoResult.getPrototypeDate());
        assertEquals("LB 20-X Cluster Ammo (THB)", actualCreateISTHBLB20XClusterAmmoResult.getName());
        assertEquals("IS LB 20-X Cluster Ammo (THB)", actualCreateISTHBLB20XClusterAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISTHBLB20XClusterAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISTHBLB20XClusterAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISTHBLB20XClusterAmmoResult.getFlags());
    }

    private void createISTHBUltra2Ammo(AmmoType actualCreateISTHBUltra2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISTHBUltra2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISTHBUltra2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISTHBUltra2AmmoResult.svslots);
        assertEquals("Ultra AC/2", actualCreateISTHBUltra2AmmoResult.shortName);
        assertNull(actualCreateISTHBUltra2AmmoResult.modes);
        assertTrue(actualCreateISTHBUltra2AmmoResult.explosive);
        assertEquals(1, actualCreateISTHBUltra2AmmoResult.criticals);
        assertEquals(8.0, actualCreateISTHBUltra2AmmoResult.bv, 0.0);
        assertFalse(actualCreateISTHBUltra2AmmoResult.isSpreadable());
        assertFalse(actualCreateISTHBUltra2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISTHBUltra2AmmoResult.isMixedTech());
        assertTrue(actualCreateISTHBUltra2AmmoResult.isHittable());
        assertFalse(actualCreateISTHBUltra2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISTHBUltra2AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISTHBUltra2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISTHBUltra2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISTHBUltra2AmmoResult.getStaticTechLevel());
        assertEquals("THB (Unoffical)", actualCreateISTHBUltra2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISTHBUltra2AmmoResult.getReintroductionDate());
        assertEquals(2000.0, actualCreateISTHBUltra2AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISTHBUltra2AmmoResult.getPrototypeDate());
        assertEquals("Ultra AC/2 Ammo (THB)", actualCreateISTHBUltra2AmmoResult.getName());
        assertEquals("IS Ultra AC/2 Ammo (THB)", actualCreateISTHBUltra2AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISTHBUltra2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISTHBUltra2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISTHBUltra2AmmoResult.getFlags());
    }

    private void createISTHBUltra10Ammo(AmmoType actualCreateISTHBUltra10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISTHBUltra10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISTHBUltra10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISTHBUltra10AmmoResult.svslots);
        assertEquals("Ultra AC/10", actualCreateISTHBUltra10AmmoResult.shortName);
        assertNull(actualCreateISTHBUltra10AmmoResult.modes);
        assertTrue(actualCreateISTHBUltra10AmmoResult.explosive);
        assertEquals(1, actualCreateISTHBUltra10AmmoResult.criticals);
        assertEquals(31.0, actualCreateISTHBUltra10AmmoResult.bv, 0.0);
        assertFalse(actualCreateISTHBUltra10AmmoResult.isSpreadable());
        assertFalse(actualCreateISTHBUltra10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISTHBUltra10AmmoResult.isMixedTech());
        assertTrue(actualCreateISTHBUltra10AmmoResult.isHittable());
        assertFalse(actualCreateISTHBUltra10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISTHBUltra10AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISTHBUltra10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISTHBUltra10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISTHBUltra10AmmoResult.getStaticTechLevel());
        assertEquals("THB (Unoffical)", actualCreateISTHBUltra10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISTHBUltra10AmmoResult.getReintroductionDate());
        assertEquals(15000.0, actualCreateISTHBUltra10AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISTHBUltra10AmmoResult.getPrototypeDate());
        assertEquals("Ultra AC/10 Ammo (THB)", actualCreateISTHBUltra10AmmoResult.getName());
        assertEquals("IS Ultra AC/10 Ammo (THB)", actualCreateISTHBUltra10AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISTHBUltra10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISTHBUltra10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISTHBUltra10AmmoResult.getFlags());
    }

    private void createISTHBUltra20Ammo(AmmoType actualCreateISTHBUltra20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISTHBUltra20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISTHBUltra20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISTHBUltra20AmmoResult.svslots);
        assertEquals("Ultra AC/20", actualCreateISTHBUltra20AmmoResult.shortName);
        assertNull(actualCreateISTHBUltra20AmmoResult.modes);
        assertTrue(actualCreateISTHBUltra20AmmoResult.explosive);
        assertEquals(1, actualCreateISTHBUltra20AmmoResult.criticals);
        assertEquals(42.0, actualCreateISTHBUltra20AmmoResult.bv, 0.0);
        assertFalse(actualCreateISTHBUltra20AmmoResult.isSpreadable());
        assertFalse(actualCreateISTHBUltra20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISTHBUltra20AmmoResult.isMixedTech());
        assertTrue(actualCreateISTHBUltra20AmmoResult.isHittable());
        assertFalse(actualCreateISTHBUltra20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISTHBUltra20AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISTHBUltra20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISTHBUltra20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISTHBUltra20AmmoResult.getStaticTechLevel());
        assertEquals("THB (Unoffical)", actualCreateISTHBUltra20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISTHBUltra20AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISTHBUltra20AmmoResult.getRawCost(), 0.0);
        assertEquals(3050, actualCreateISTHBUltra20AmmoResult.getPrototypeDate());
        assertEquals("Ultra AC/20 Ammo (THB)", actualCreateISTHBUltra20AmmoResult.getName());
        assertEquals("IS Ultra AC/20 Ammo (THB)", actualCreateISTHBUltra20AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISTHBUltra20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISTHBUltra20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISTHBUltra20AmmoResult.getFlags());
    }

    private void createISRotary10Ammo(AmmoType actualCreateISRotary10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISRotary10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISRotary10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISRotary10AmmoResult.svslots);
        assertEquals("RAC/10", actualCreateISRotary10AmmoResult.shortName);
        assertNull(actualCreateISRotary10AmmoResult.modes);
        assertTrue(actualCreateISRotary10AmmoResult.explosive);
        assertEquals(1, actualCreateISRotary10AmmoResult.criticals);
        assertEquals(37.0, actualCreateISRotary10AmmoResult.bv, 0.0);
        assertFalse(actualCreateISRotary10AmmoResult.isSpreadable());
        assertFalse(actualCreateISRotary10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISRotary10AmmoResult.isMixedTech());
        assertTrue(actualCreateISRotary10AmmoResult.isHittable());
        assertFalse(actualCreateISRotary10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISRotary10AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISRotary10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISRotary10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISRotary10AmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateISRotary10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISRotary10AmmoResult.getReintroductionDate());
        assertEquals(30000.0, actualCreateISRotary10AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateISRotary10AmmoResult.getPrototypeDate());
        assertEquals("Rotary AC/10 Ammo", actualCreateISRotary10AmmoResult.getName());
        assertEquals("ISRotaryAC10 Ammo", actualCreateISRotary10AmmoResult.getInternalName());
        assertEquals("E/E-E-E-E", actualCreateISRotary10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISRotary10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISRotary10AmmoResult.getFlags());
    }

    private void createISRotary20Ammo(AmmoType actualCreateISRotary20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISRotary20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISRotary20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISRotary20AmmoResult.svslots);
        assertEquals("RAC/20", actualCreateISRotary20AmmoResult.shortName);
        assertNull(actualCreateISRotary20AmmoResult.modes);
        assertTrue(actualCreateISRotary20AmmoResult.explosive);
        assertEquals(1, actualCreateISRotary20AmmoResult.criticals);
        assertEquals(59.0, actualCreateISRotary20AmmoResult.bv, 0.0);
        assertFalse(actualCreateISRotary20AmmoResult.isSpreadable());
        assertFalse(actualCreateISRotary20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISRotary20AmmoResult.isMixedTech());
        assertTrue(actualCreateISRotary20AmmoResult.isHittable());
        assertFalse(actualCreateISRotary20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISRotary20AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISRotary20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISRotary20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISRotary20AmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateISRotary20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISRotary20AmmoResult.getReintroductionDate());
        assertEquals(80000.0, actualCreateISRotary20AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateISRotary20AmmoResult.getPrototypeDate());
        assertEquals("Rotary AC/20 Ammo", actualCreateISRotary20AmmoResult.getName());
        assertEquals("ISRotaryAC20 Ammo", actualCreateISRotary20AmmoResult.getInternalName());
        assertEquals("E/E-E-E-E", actualCreateISRotary20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISRotary20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISRotary20AmmoResult.getFlags());
    }

    private void createCLRotary10Ammo(AmmoType actualCreateCLRotary10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLRotary10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLRotary10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLRotary10AmmoResult.svslots);
        assertEquals("RAC/10", actualCreateCLRotary10AmmoResult.shortName);
        assertNull(actualCreateCLRotary10AmmoResult.modes);
        assertTrue(actualCreateCLRotary10AmmoResult.explosive);
        assertEquals(1, actualCreateCLRotary10AmmoResult.criticals);
        assertEquals(74.0, actualCreateCLRotary10AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLRotary10AmmoResult.isSpreadable());
        assertFalse(actualCreateCLRotary10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLRotary10AmmoResult.isMixedTech());
        assertTrue(actualCreateCLRotary10AmmoResult.isHittable());
        assertFalse(actualCreateCLRotary10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLRotary10AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLRotary10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLRotary10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateCLRotary10AmmoResult.getStaticTechLevel());
        assertEquals("Unofficial", actualCreateCLRotary10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLRotary10AmmoResult.getReintroductionDate());
        assertEquals(16000.0, actualCreateCLRotary10AmmoResult.getRawCost(), 0.0);
        assertEquals(3073, actualCreateCLRotary10AmmoResult.getPrototypeDate());
        assertEquals("Rotary AC/10 Ammo", actualCreateCLRotary10AmmoResult.getName());
        assertEquals("CLRotaryAC10 Ammo", actualCreateCLRotary10AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLRotary10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLRotary10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLRotary10AmmoResult.getFlags());
    }

    private void createCLRotary20Ammo(AmmoType actualCreateCLRotary20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateCLRotary20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateCLRotary20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateCLRotary20AmmoResult.svslots);
        assertEquals("RAC/20", actualCreateCLRotary20AmmoResult.shortName);
        assertNull(actualCreateCLRotary20AmmoResult.modes);
        assertTrue(actualCreateCLRotary20AmmoResult.explosive);
        assertEquals(1, actualCreateCLRotary20AmmoResult.criticals);
        assertEquals(118.0, actualCreateCLRotary20AmmoResult.bv, 0.0);
        assertFalse(actualCreateCLRotary20AmmoResult.isSpreadable());
        assertFalse(actualCreateCLRotary20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateCLRotary20AmmoResult.isMixedTech());
        assertTrue(actualCreateCLRotary20AmmoResult.isHittable());
        assertFalse(actualCreateCLRotary20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateCLRotary20AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateCLRotary20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateCLRotary20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateCLRotary20AmmoResult.getStaticTechLevel());
        assertEquals("Unofficial", actualCreateCLRotary20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateCLRotary20AmmoResult.getReintroductionDate());
        assertEquals(24000.0, actualCreateCLRotary20AmmoResult.getRawCost(), 0.0);
        assertEquals(3073, actualCreateCLRotary20AmmoResult.getPrototypeDate());
        assertEquals("Rotary AC/20 Ammo", actualCreateCLRotary20AmmoResult.getName());
        assertEquals("CLRotaryAC20 Ammo", actualCreateCLRotary20AmmoResult.getInternalName());
        assertEquals("F/X-X-F-E", actualCreateCLRotary20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateCLRotary20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateCLRotary20AmmoResult.getFlags());
    }

    private void createISLAC10Ammo(AmmoType actualCreateISLAC10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLAC10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLAC10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLAC10AmmoResult.svslots);
        assertEquals("LAC/10", actualCreateISLAC10AmmoResult.shortName);
        assertNull(actualCreateISLAC10AmmoResult.modes);
        assertTrue(actualCreateISLAC10AmmoResult.explosive);
        assertEquals(1, actualCreateISLAC10AmmoResult.criticals);
        assertEquals(9.0, actualCreateISLAC10AmmoResult.bv, 0.0);
        assertFalse(actualCreateISLAC10AmmoResult.isSpreadable());
        assertFalse(actualCreateISLAC10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLAC10AmmoResult.isMixedTech());
        assertTrue(actualCreateISLAC10AmmoResult.isHittable());
        assertFalse(actualCreateISLAC10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLAC10AmmoResult.getToHitModifier());
        assertEquals(3, actualCreateISLAC10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLAC10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISLAC10AmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISLAC10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLAC10AmmoResult.getReintroductionDate());
        assertEquals(10000.0, actualCreateISLAC10AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateISLAC10AmmoResult.getPrototypeDate());
        assertEquals("LAC/10 Ammo", actualCreateISLAC10AmmoResult.getName());
        assertEquals("IS Ammo LAC/10", actualCreateISLAC10AmmoResult.getInternalName());
        assertEquals("D/X-X-F-C", actualCreateISLAC10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLAC10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLAC10AmmoResult.getFlags());
    }

    private void createISLAC20Ammo(AmmoType actualCreateISLAC20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISLAC20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISLAC20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISLAC20AmmoResult.svslots);
        assertEquals("LAC/20", actualCreateISLAC20AmmoResult.shortName);
        assertNull(actualCreateISLAC20AmmoResult.modes);
        assertTrue(actualCreateISLAC20AmmoResult.explosive);
        assertEquals(1, actualCreateISLAC20AmmoResult.criticals);
        assertEquals(15.0, actualCreateISLAC20AmmoResult.bv, 0.0);
        assertFalse(actualCreateISLAC20AmmoResult.isSpreadable());
        assertFalse(actualCreateISLAC20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISLAC20AmmoResult.isMixedTech());
        assertTrue(actualCreateISLAC20AmmoResult.isHittable());
        assertFalse(actualCreateISLAC20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISLAC20AmmoResult.getToHitModifier());
        assertEquals(3, actualCreateISLAC20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISLAC20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISLAC20AmmoResult.getStaticTechLevel());
        assertEquals("207,TM", actualCreateISLAC20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISLAC20AmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateISLAC20AmmoResult.getRawCost(), 0.0);
        assertEquals(3057, actualCreateISLAC20AmmoResult.getPrototypeDate());
        assertEquals("LAC/20 Ammo", actualCreateISLAC20AmmoResult.getName());
        assertEquals("IS Ammo LAC/20", actualCreateISLAC20AmmoResult.getInternalName());
        assertEquals("D/X-X-F-C", actualCreateISLAC20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISLAC20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISLAC20AmmoResult.getFlags());
    }

    private void createISRailGunAmmo(AmmoType actualCreateISRailGunAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISRailGunAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISRailGunAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISRailGunAmmoResult.svslots);
        assertEquals("Rail Gun", actualCreateISRailGunAmmoResult.shortName);
        assertNull(actualCreateISRailGunAmmoResult.modes);
        assertFalse(actualCreateISRailGunAmmoResult.explosive);
        assertEquals(1, actualCreateISRailGunAmmoResult.criticals);
        assertEquals(51.0, actualCreateISRailGunAmmoResult.bv, 0.0);
        assertFalse(actualCreateISRailGunAmmoResult.isSpreadable());
        assertFalse(actualCreateISRailGunAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISRailGunAmmoResult.isMixedTech());
        assertTrue(actualCreateISRailGunAmmoResult.isHittable());
        assertFalse(actualCreateISRailGunAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISRailGunAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISRailGunAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISRailGunAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISRailGunAmmoResult.getStaticTechLevel());
        assertEquals("Unofficial", actualCreateISRailGunAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISRailGunAmmoResult.getReintroductionDate());
        assertEquals(20000.0, actualCreateISRailGunAmmoResult.getRawCost(), 0.0);
        assertEquals(3046, actualCreateISRailGunAmmoResult.getPrototypeDate());
        assertEquals("Rail Gun Ammo", actualCreateISRailGunAmmoResult.getName());
        assertEquals("ISRailGun Ammo", actualCreateISRailGunAmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISRailGunAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISRailGunAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISRailGunAmmoResult.getFlags());
    }

    private void createISPXLRM5Ammo(AmmoType actualCreateISPXLRM5AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISPXLRM5AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISPXLRM5AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISPXLRM5AmmoResult.svslots);
        assertEquals("Phoenix LRM 5", actualCreateISPXLRM5AmmoResult.shortName);
        assertTrue(actualCreateISPXLRM5AmmoResult.explosive);
        assertEquals(1, actualCreateISPXLRM5AmmoResult.criticals);
        assertEquals(7.0, actualCreateISPXLRM5AmmoResult.bv, 0.0);
        assertFalse(actualCreateISPXLRM5AmmoResult.isSpreadable());
        assertFalse(actualCreateISPXLRM5AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISPXLRM5AmmoResult.isMixedTech());
        assertTrue(actualCreateISPXLRM5AmmoResult.isHittable());
        assertTrue(actualCreateISPXLRM5AmmoResult.hasModes());
        assertFalse(actualCreateISPXLRM5AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISPXLRM5AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateISPXLRM5AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISPXLRM5AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISPXLRM5AmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateISPXLRM5AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISPXLRM5AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateISPXLRM5AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateISPXLRM5AmmoResult.getPrototypeDate());
        assertEquals("Phoenix LRM 5 Ammo", actualCreateISPXLRM5AmmoResult.getName());
        assertEquals("ISPhoenixLRM5 Ammo", actualCreateISPXLRM5AmmoResult.getInternalName());
        assertEquals("F/X-X-D-X", actualCreateISPXLRM5AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISPXLRM5AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISPXLRM5AmmoResult.getFlags());
    }

    private void createISPXLRM10Ammo(AmmoType actualCreateISPXLRM10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISPXLRM10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISPXLRM10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISPXLRM10AmmoResult.svslots);
        assertEquals("Phoenix LRM 10", actualCreateISPXLRM10AmmoResult.shortName);
        assertTrue(actualCreateISPXLRM10AmmoResult.explosive);
        assertEquals(1, actualCreateISPXLRM10AmmoResult.criticals);
        assertEquals(14.0, actualCreateISPXLRM10AmmoResult.bv, 0.0);
        assertFalse(actualCreateISPXLRM10AmmoResult.isSpreadable());
        assertFalse(actualCreateISPXLRM10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISPXLRM10AmmoResult.isMixedTech());
        assertTrue(actualCreateISPXLRM10AmmoResult.isHittable());
        assertTrue(actualCreateISPXLRM10AmmoResult.hasModes());
        assertFalse(actualCreateISPXLRM10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISPXLRM10AmmoResult.getToHitModifier());
        assertEquals(5, actualCreateISPXLRM10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISPXLRM10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISPXLRM10AmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateISPXLRM10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISPXLRM10AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateISPXLRM10AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateISPXLRM10AmmoResult.getPrototypeDate());
        assertEquals("Phoenix LRM 10 Ammo", actualCreateISPXLRM10AmmoResult.getName());
        assertEquals("ISPhoenixLRM10 Ammo", actualCreateISPXLRM10AmmoResult.getInternalName());
        assertEquals("F/X-X-D-X", actualCreateISPXLRM10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISPXLRM10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISPXLRM10AmmoResult.getFlags());
    }

    private void createISPXLRM15Ammo(AmmoType actualCreateISPXLRM15AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISPXLRM15AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISPXLRM15AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISPXLRM15AmmoResult.svslots);
        assertEquals("Phoenix LRM 15", actualCreateISPXLRM15AmmoResult.shortName);
        assertTrue(actualCreateISPXLRM15AmmoResult.explosive);
        assertEquals(1, actualCreateISPXLRM15AmmoResult.criticals);
        assertEquals(21.0, actualCreateISPXLRM15AmmoResult.bv, 0.0);
        assertFalse(actualCreateISPXLRM15AmmoResult.isSpreadable());
        assertFalse(actualCreateISPXLRM15AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISPXLRM15AmmoResult.isMixedTech());
        assertTrue(actualCreateISPXLRM15AmmoResult.isHittable());
        assertTrue(actualCreateISPXLRM15AmmoResult.hasModes());
        assertFalse(actualCreateISPXLRM15AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISPXLRM15AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISPXLRM15AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISPXLRM15AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISPXLRM15AmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateISPXLRM15AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISPXLRM15AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateISPXLRM15AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateISPXLRM15AmmoResult.getPrototypeDate());
        assertEquals("Phoenix LRM 15 Ammo", actualCreateISPXLRM15AmmoResult.getName());
        assertEquals("ISPhoenixLRM15 Ammo", actualCreateISPXLRM15AmmoResult.getInternalName());
        assertEquals("C/C-C-C-X", actualCreateISPXLRM15AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISPXLRM15AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISPXLRM15AmmoResult.getFlags());
    }

    private void createISPXLRM20Ammo(AmmoType actualCreateISPXLRM20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISPXLRM20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISPXLRM20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISPXLRM20AmmoResult.svslots);
        assertEquals("Phoenix LRM 20", actualCreateISPXLRM20AmmoResult.shortName);
        assertTrue(actualCreateISPXLRM20AmmoResult.explosive);
        assertEquals(1, actualCreateISPXLRM20AmmoResult.criticals);
        assertEquals(28.0, actualCreateISPXLRM20AmmoResult.bv, 0.0);
        assertFalse(actualCreateISPXLRM20AmmoResult.isSpreadable());
        assertFalse(actualCreateISPXLRM20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISPXLRM20AmmoResult.isMixedTech());
        assertTrue(actualCreateISPXLRM20AmmoResult.isHittable());
        assertTrue(actualCreateISPXLRM20AmmoResult.hasModes());
        assertFalse(actualCreateISPXLRM20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISPXLRM20AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISPXLRM20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISPXLRM20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISPXLRM20AmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateISPXLRM20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISPXLRM20AmmoResult.getReintroductionDate());
        assertEquals(60000.0, actualCreateISPXLRM20AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateISPXLRM20AmmoResult.getPrototypeDate());
        assertEquals("Phoenix LRM 20 Ammo", actualCreateISPXLRM20AmmoResult.getName());
        assertEquals("ISPhoenixLRM20 Ammo", actualCreateISPXLRM20AmmoResult.getInternalName());
        assertEquals("C/C-C-C-X", actualCreateISPXLRM20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISPXLRM20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISPXLRM20AmmoResult.getFlags());
    }

    private void createISHawkSRM2Ammo(AmmoType actualCreateISHawkSRM2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISHawkSRM2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISHawkSRM2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISHawkSRM2AmmoResult.svslots);
        assertEquals("Hawk SRM 2", actualCreateISHawkSRM2AmmoResult.shortName);
        assertNull(actualCreateISHawkSRM2AmmoResult.modes);
        assertTrue(actualCreateISHawkSRM2AmmoResult.explosive);
        assertEquals(1, actualCreateISHawkSRM2AmmoResult.criticals);
        assertEquals(4.0, actualCreateISHawkSRM2AmmoResult.bv, 0.0);
        assertFalse(actualCreateISHawkSRM2AmmoResult.isSpreadable());
        assertFalse(actualCreateISHawkSRM2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISHawkSRM2AmmoResult.isMixedTech());
        assertTrue(actualCreateISHawkSRM2AmmoResult.isHittable());
        assertFalse(actualCreateISHawkSRM2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISHawkSRM2AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISHawkSRM2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISHawkSRM2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISHawkSRM2AmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateISHawkSRM2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISHawkSRM2AmmoResult.getReintroductionDate());
        assertEquals(52000.0, actualCreateISHawkSRM2AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateISHawkSRM2AmmoResult.getPrototypeDate());
        assertEquals("Hawk SRM 2 Ammo", actualCreateISHawkSRM2AmmoResult.getName());
        assertEquals("ISHawkSRM2 Ammo", actualCreateISHawkSRM2AmmoResult.getInternalName());
        assertEquals("C/E-E-E-E", actualCreateISHawkSRM2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISHawkSRM2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISHawkSRM2AmmoResult.getFlags());
    }

    private void createISHawkSRM4Ammo(AmmoType actualCreateISHawkSRM4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISHawkSRM4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISHawkSRM4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISHawkSRM4AmmoResult.svslots);
        assertEquals("Hawk SRM 4", actualCreateISHawkSRM4AmmoResult.shortName);
        assertNull(actualCreateISHawkSRM4AmmoResult.modes);
        assertTrue(actualCreateISHawkSRM4AmmoResult.explosive);
        assertEquals(1, actualCreateISHawkSRM4AmmoResult.criticals);
        assertEquals(6.0, actualCreateISHawkSRM4AmmoResult.bv, 0.0);
        assertFalse(actualCreateISHawkSRM4AmmoResult.isSpreadable());
        assertFalse(actualCreateISHawkSRM4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISHawkSRM4AmmoResult.isMixedTech());
        assertTrue(actualCreateISHawkSRM4AmmoResult.isHittable());
        assertFalse(actualCreateISHawkSRM4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISHawkSRM4AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISHawkSRM4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISHawkSRM4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISHawkSRM4AmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateISHawkSRM4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISHawkSRM4AmmoResult.getReintroductionDate());
        assertEquals(52000.0, actualCreateISHawkSRM4AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateISHawkSRM4AmmoResult.getPrototypeDate());
        assertEquals("Hawk SRM 4 Ammo", actualCreateISHawkSRM4AmmoResult.getName());
        assertEquals("ISHawkSRM4 Ammo", actualCreateISHawkSRM4AmmoResult.getInternalName());
        assertEquals("C/E-E-E-E", actualCreateISHawkSRM4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISHawkSRM4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISHawkSRM4AmmoResult.getFlags());
    }

    private void createISHawkSRM6Ammo(AmmoType actualCreateISHawkSRM6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISHawkSRM6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISHawkSRM6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISHawkSRM6AmmoResult.svslots);
        assertEquals("Hawk SRM 6", actualCreateISHawkSRM6AmmoResult.shortName);
        assertNull(actualCreateISHawkSRM6AmmoResult.modes);
        assertTrue(actualCreateISHawkSRM6AmmoResult.explosive);
        assertEquals(1, actualCreateISHawkSRM6AmmoResult.criticals);
        assertEquals(10.0, actualCreateISHawkSRM6AmmoResult.bv, 0.0);
        assertFalse(actualCreateISHawkSRM6AmmoResult.isSpreadable());
        assertFalse(actualCreateISHawkSRM6AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISHawkSRM6AmmoResult.isMixedTech());
        assertTrue(actualCreateISHawkSRM6AmmoResult.isHittable());
        assertFalse(actualCreateISHawkSRM6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISHawkSRM6AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISHawkSRM6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISHawkSRM6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISHawkSRM6AmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateISHawkSRM6AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISHawkSRM6AmmoResult.getReintroductionDate());
        assertEquals(52000.0, actualCreateISHawkSRM6AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateISHawkSRM6AmmoResult.getPrototypeDate());
        assertEquals("Hawk SRM 6 Ammo", actualCreateISHawkSRM6AmmoResult.getName());
        assertEquals("ISHawkSRM6 Ammo", actualCreateISHawkSRM6AmmoResult.getInternalName());
        assertEquals("C/E-E-E-E", actualCreateISHawkSRM6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISHawkSRM6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISHawkSRM6AmmoResult.getFlags());
    }

    private void createISStreakMRM10Ammo(AmmoType actualCreateISStreakMRM10AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISStreakMRM10AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISStreakMRM10AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISStreakMRM10AmmoResult.svslots);
        assertEquals("Streak MRM 10", actualCreateISStreakMRM10AmmoResult.shortName);
        assertNull(actualCreateISStreakMRM10AmmoResult.modes);
        assertTrue(actualCreateISStreakMRM10AmmoResult.explosive);
        assertEquals(1, actualCreateISStreakMRM10AmmoResult.criticals);
        assertEquals(11.0, actualCreateISStreakMRM10AmmoResult.bv, 0.0);
        assertFalse(actualCreateISStreakMRM10AmmoResult.isSpreadable());
        assertFalse(actualCreateISStreakMRM10AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISStreakMRM10AmmoResult.isMixedTech());
        assertTrue(actualCreateISStreakMRM10AmmoResult.isHittable());
        assertFalse(actualCreateISStreakMRM10AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISStreakMRM10AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISStreakMRM10AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISStreakMRM10AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISStreakMRM10AmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateISStreakMRM10AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISStreakMRM10AmmoResult.getReintroductionDate());
        assertEquals(10000.0, actualCreateISStreakMRM10AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateISStreakMRM10AmmoResult.getPrototypeDate());
        assertEquals("Streak MRM 10 Ammo", actualCreateISStreakMRM10AmmoResult.getName());
        assertEquals("IS Streak MRM 10 Ammo", actualCreateISStreakMRM10AmmoResult.getInternalName());
        assertEquals("C/X-X-E-X", actualCreateISStreakMRM10AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISStreakMRM10AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISStreakMRM10AmmoResult.getFlags());
    }

    private void createISStreakMRM20Ammo(AmmoType actualCreateISStreakMRM20AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISStreakMRM20AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISStreakMRM20AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISStreakMRM20AmmoResult.svslots);
        assertEquals("Streak MRM 20", actualCreateISStreakMRM20AmmoResult.shortName);
        assertNull(actualCreateISStreakMRM20AmmoResult.modes);
        assertTrue(actualCreateISStreakMRM20AmmoResult.explosive);
        assertEquals(1, actualCreateISStreakMRM20AmmoResult.criticals);
        assertEquals(22.0, actualCreateISStreakMRM20AmmoResult.bv, 0.0);
        assertFalse(actualCreateISStreakMRM20AmmoResult.isSpreadable());
        assertFalse(actualCreateISStreakMRM20AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISStreakMRM20AmmoResult.isMixedTech());
        assertTrue(actualCreateISStreakMRM20AmmoResult.isHittable());
        assertFalse(actualCreateISStreakMRM20AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISStreakMRM20AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISStreakMRM20AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISStreakMRM20AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISStreakMRM20AmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateISStreakMRM20AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISStreakMRM20AmmoResult.getReintroductionDate());
        assertEquals(10000.0, actualCreateISStreakMRM20AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateISStreakMRM20AmmoResult.getPrototypeDate());
        assertEquals("Streak MRM 20 Ammo", actualCreateISStreakMRM20AmmoResult.getName());
        assertEquals("IS Streak MRM 20 Ammo", actualCreateISStreakMRM20AmmoResult.getInternalName());
        assertEquals("C/X-X-E-X", actualCreateISStreakMRM20AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISStreakMRM20AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISStreakMRM20AmmoResult.getFlags());
    }

    private void createISStreakMRM30Ammo(AmmoType actualCreateISStreakMRM30AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISStreakMRM30AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISStreakMRM30AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISStreakMRM30AmmoResult.svslots);
        assertEquals("Streak MRM 30", actualCreateISStreakMRM30AmmoResult.shortName);
        assertNull(actualCreateISStreakMRM30AmmoResult.modes);
        assertTrue(actualCreateISStreakMRM30AmmoResult.explosive);
        assertEquals(1, actualCreateISStreakMRM30AmmoResult.criticals);
        assertEquals(33.0, actualCreateISStreakMRM30AmmoResult.bv, 0.0);
        assertFalse(actualCreateISStreakMRM30AmmoResult.isSpreadable());
        assertFalse(actualCreateISStreakMRM30AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISStreakMRM30AmmoResult.isMixedTech());
        assertTrue(actualCreateISStreakMRM30AmmoResult.isHittable());
        assertFalse(actualCreateISStreakMRM30AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISStreakMRM30AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISStreakMRM30AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISStreakMRM30AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISStreakMRM30AmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateISStreakMRM30AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISStreakMRM30AmmoResult.getReintroductionDate());
        assertEquals(10000.0, actualCreateISStreakMRM30AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateISStreakMRM30AmmoResult.getPrototypeDate());
        assertEquals("Streak MRM 30 Ammo", actualCreateISStreakMRM30AmmoResult.getName());
        assertEquals("IS Streak MRM 30 Ammo", actualCreateISStreakMRM30AmmoResult.getInternalName());
        assertEquals("C/X-X-E-X", actualCreateISStreakMRM30AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISStreakMRM30AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISStreakMRM30AmmoResult.getFlags());
    }

    private void createISStreakMRM40Ammo(AmmoType actualCreateISStreakMRM40AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISStreakMRM40AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISStreakMRM40AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISStreakMRM40AmmoResult.svslots);
        assertEquals("Streak MRM 40", actualCreateISStreakMRM40AmmoResult.shortName);
        assertNull(actualCreateISStreakMRM40AmmoResult.modes);
        assertTrue(actualCreateISStreakMRM40AmmoResult.explosive);
        assertEquals(1, actualCreateISStreakMRM40AmmoResult.criticals);
        assertEquals(44.0, actualCreateISStreakMRM40AmmoResult.bv, 0.0);
        assertFalse(actualCreateISStreakMRM40AmmoResult.isSpreadable());
        assertFalse(actualCreateISStreakMRM40AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISStreakMRM40AmmoResult.isMixedTech());
        assertTrue(actualCreateISStreakMRM40AmmoResult.isHittable());
        assertFalse(actualCreateISStreakMRM40AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISStreakMRM40AmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISStreakMRM40AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISStreakMRM40AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateISStreakMRM40AmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateISStreakMRM40AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISStreakMRM40AmmoResult.getReintroductionDate());
        assertEquals(10000.0, actualCreateISStreakMRM40AmmoResult.getRawCost(), 0.0);
        assertEquals(AmmoType.T_NA, actualCreateISStreakMRM40AmmoResult.getPrototypeDate());
        assertEquals("Streak MRM 40 Ammo", actualCreateISStreakMRM40AmmoResult.getName());
        assertEquals("IS Streak MRM 40 Ammo", actualCreateISStreakMRM40AmmoResult.getInternalName());
        assertEquals("C/X-X-E-X", actualCreateISStreakMRM40AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISStreakMRM40AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISStreakMRM40AmmoResult.getFlags());
    }

    private void createISAC10iAmmo(AmmoType actualCreateISAC10iAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISAC10iAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISAC10iAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISAC10iAmmoResult.svslots);
        assertEquals("AC/10i", actualCreateISAC10iAmmoResult.shortName);
        assertNull(actualCreateISAC10iAmmoResult.modes);
        assertTrue(actualCreateISAC10iAmmoResult.explosive);
        assertEquals(1, actualCreateISAC10iAmmoResult.criticals);
        assertEquals(21.0, actualCreateISAC10iAmmoResult.bv, 0.0);
        assertFalse(actualCreateISAC10iAmmoResult.isSpreadable());
        assertFalse(actualCreateISAC10iAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateISAC10iAmmoResult.isMixedTech());
        assertTrue(actualCreateISAC10iAmmoResult.isHittable());
        assertFalse(actualCreateISAC10iAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISAC10iAmmoResult.getToHitModifier());
        assertEquals(2, actualCreateISAC10iAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISAC10iAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISAC10iAmmoResult.getStaticTechLevel());
        assertEquals("Unoffical", actualCreateISAC10iAmmoResult.getRulesRefs());
        assertEquals(12000.0, actualCreateISAC10iAmmoResult.getRawCost(), 0.0);
        assertEquals(2443, actualCreateISAC10iAmmoResult.getPrototypeDate());
        assertEquals("AC/10i Ammo", actualCreateISAC10iAmmoResult.getName());
        assertEquals("IS Ammo AC/10i", actualCreateISAC10iAmmoResult.getInternalName());
        assertEquals("C/C-D-D-D", actualCreateISAC10iAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISAC10iAmmoResult.flags;
        assertSame(expectedFlags, actualCreateISAC10iAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateISAC10iAmmoResult.getExtinctionDate());
    }

    private void createISGAC2Ammo(AmmoType actualCreateISGAC2AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISGAC2AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISGAC2AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISGAC2AmmoResult.svslots);
        assertEquals("GAC/2", actualCreateISGAC2AmmoResult.shortName);
        assertNull(actualCreateISGAC2AmmoResult.modes);
        assertTrue(actualCreateISGAC2AmmoResult.explosive);
        assertEquals(1, actualCreateISGAC2AmmoResult.criticals);
        assertEquals(12.0, actualCreateISGAC2AmmoResult.bv, 0.0);
        assertFalse(actualCreateISGAC2AmmoResult.isSpreadable());
        assertFalse(actualCreateISGAC2AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISGAC2AmmoResult.isMixedTech());
        assertTrue(actualCreateISGAC2AmmoResult.isHittable());
        assertFalse(actualCreateISGAC2AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISGAC2AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISGAC2AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISGAC2AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISGAC2AmmoResult.getStaticTechLevel());
        assertEquals("207,TO", actualCreateISGAC2AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISGAC2AmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateISGAC2AmmoResult.getRawCost(), 0.0);
        assertEquals(3055, actualCreateISGAC2AmmoResult.getPrototypeDate());
        assertEquals("GAC/2 Ammo", actualCreateISGAC2AmmoResult.getName());
        assertEquals("IS Ammo GAC/2", actualCreateISGAC2AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISGAC2AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISGAC2AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISGAC2AmmoResult.getFlags());
    }

    private void createISGAC4Ammo(AmmoType actualCreateISGAC4AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISGAC4AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISGAC4AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISGAC4AmmoResult.svslots);
        assertEquals("GAC/4", actualCreateISGAC4AmmoResult.shortName);
        assertNull(actualCreateISGAC4AmmoResult.modes);
        assertTrue(actualCreateISGAC4AmmoResult.explosive);
        assertEquals(1, actualCreateISGAC4AmmoResult.criticals);
        assertEquals(22.0, actualCreateISGAC4AmmoResult.bv, 0.0);
        assertFalse(actualCreateISGAC4AmmoResult.isSpreadable());
        assertFalse(actualCreateISGAC4AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISGAC4AmmoResult.isMixedTech());
        assertTrue(actualCreateISGAC4AmmoResult.isHittable());
        assertFalse(actualCreateISGAC4AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISGAC4AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISGAC4AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISGAC4AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISGAC4AmmoResult.getStaticTechLevel());
        assertEquals("207,TO", actualCreateISGAC4AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISGAC4AmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateISGAC4AmmoResult.getRawCost(), 0.0);
        assertEquals(3055, actualCreateISGAC4AmmoResult.getPrototypeDate());
        assertEquals("GAC/4 Ammo", actualCreateISGAC4AmmoResult.getName());
        assertEquals("IS Ammo GAC/4", actualCreateISGAC4AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISGAC4AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISGAC4AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISGAC4AmmoResult.getFlags());
    }

    private void createISGAC6Ammo(AmmoType actualCreateISGAC6AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISGAC6AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISGAC6AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISGAC6AmmoResult.svslots);
        assertEquals("GAC/6", actualCreateISGAC6AmmoResult.shortName);
        assertNull(actualCreateISGAC6AmmoResult.modes);
        assertTrue(actualCreateISGAC6AmmoResult.explosive);
        assertEquals(1, actualCreateISGAC6AmmoResult.criticals);
        assertEquals(40.0, actualCreateISGAC6AmmoResult.bv, 0.0);
        assertFalse(actualCreateISGAC6AmmoResult.isSpreadable());
        assertFalse(actualCreateISGAC6AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISGAC6AmmoResult.isMixedTech());
        assertTrue(actualCreateISGAC6AmmoResult.isHittable());
        assertFalse(actualCreateISGAC6AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISGAC6AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISGAC6AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISGAC6AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISGAC6AmmoResult.getStaticTechLevel());
        assertEquals("207,TO", actualCreateISGAC6AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISGAC6AmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateISGAC6AmmoResult.getRawCost(), 0.0);
        assertEquals(3055, actualCreateISGAC6AmmoResult.getPrototypeDate());
        assertEquals("GAC/6 Ammo", actualCreateISGAC6AmmoResult.getName());
        assertEquals("IS Ammo GAC/6", actualCreateISGAC6AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISGAC6AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISGAC6AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISGAC6AmmoResult.getFlags());
    }

    private void createISGAC8Ammo(AmmoType actualCreateISGAC8AmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateISGAC8AmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateISGAC8AmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateISGAC8AmmoResult.svslots);
        assertEquals("GAC/8", actualCreateISGAC8AmmoResult.shortName);
        assertNull(actualCreateISGAC8AmmoResult.modes);
        assertTrue(actualCreateISGAC8AmmoResult.explosive);
        assertEquals(1, actualCreateISGAC8AmmoResult.criticals);
        assertEquals(53.0, actualCreateISGAC8AmmoResult.bv, 0.0);
        assertFalse(actualCreateISGAC8AmmoResult.isSpreadable());
        assertFalse(actualCreateISGAC8AmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateISGAC8AmmoResult.isMixedTech());
        assertTrue(actualCreateISGAC8AmmoResult.isHittable());
        assertFalse(actualCreateISGAC8AmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateISGAC8AmmoResult.getToHitModifier());
        assertEquals(4, actualCreateISGAC8AmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateISGAC8AmmoResult.getSubType());
        assertEquals(SimpleTechLevel.UNOFFICIAL, actualCreateISGAC8AmmoResult.getStaticTechLevel());
        assertEquals("207,TO", actualCreateISGAC8AmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateISGAC8AmmoResult.getReintroductionDate());
        assertEquals(1000.0, actualCreateISGAC8AmmoResult.getRawCost(), 0.0);
        assertEquals(3055, actualCreateISGAC8AmmoResult.getPrototypeDate());
        assertEquals("GAC/8 Ammo", actualCreateISGAC8AmmoResult.getName());
        assertEquals("IS Ammo GAC/8", actualCreateISGAC8AmmoResult.getInternalName());
        assertEquals("E/X-X-E-D", actualCreateISGAC8AmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateISGAC8AmmoResult.flags;
        assertSame(expectedFlags, actualCreateISGAC8AmmoResult.getFlags());
    }

    private void createAR10PeacemakerAmmo(AmmoType actualCreateAR10PeacemakerAmmoResult) {
        assertEquals(50.0, actualCreateAR10PeacemakerAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateAR10PeacemakerAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateAR10PeacemakerAmmoResult.svslots);
        assertEquals("Peacemaker", actualCreateAR10PeacemakerAmmoResult.shortName);
        assertNull(actualCreateAR10PeacemakerAmmoResult.modes);
        assertTrue(actualCreateAR10PeacemakerAmmoResult.explosive);
        assertEquals(1, actualCreateAR10PeacemakerAmmoResult.criticals);
        assertEquals(10000.0, actualCreateAR10PeacemakerAmmoResult.bv, 0.0);
        assertFalse(actualCreateAR10PeacemakerAmmoResult.isSpreadable());
        assertFalse(actualCreateAR10PeacemakerAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateAR10PeacemakerAmmoResult.isMixedTech());
        assertTrue(actualCreateAR10PeacemakerAmmoResult.isHittable());
        assertFalse(actualCreateAR10PeacemakerAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateAR10PeacemakerAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateAR10PeacemakerAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateAR10PeacemakerAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateAR10PeacemakerAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateAR10PeacemakerAmmoResult.getStandardRange());
        assertEquals("", actualCreateAR10PeacemakerAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateAR10PeacemakerAmmoResult.getReintroductionDate());
        assertEquals(4.0E7, actualCreateAR10PeacemakerAmmoResult.getRawCost(), 0.0);
        assertEquals("AR10 Peacemaker Ammo", actualCreateAR10PeacemakerAmmoResult.getName());
        assertEquals("Ammo AR10 Peacemaker", actualCreateAR10PeacemakerAmmoResult.getInternalName());
        assertEquals("E/F-F-F-F", actualCreateAR10PeacemakerAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateAR10PeacemakerAmmoResult.flags;
        assertSame(expectedFlags, actualCreateAR10PeacemakerAmmoResult.getFlags());
    }

    private void createPeacemakerAmmo(AmmoType actualCreatePeacemakerAmmoResult) {
        assertEquals(50.0, actualCreatePeacemakerAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreatePeacemakerAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreatePeacemakerAmmoResult.svslots);
        assertEquals("Peacemaker", actualCreatePeacemakerAmmoResult.shortName);
        assertNull(actualCreatePeacemakerAmmoResult.modes);
        assertTrue(actualCreatePeacemakerAmmoResult.explosive);
        assertEquals(1, actualCreatePeacemakerAmmoResult.criticals);
        assertEquals(10000.0, actualCreatePeacemakerAmmoResult.bv, 0.0);
        assertFalse(actualCreatePeacemakerAmmoResult.isSpreadable());
        assertFalse(actualCreatePeacemakerAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreatePeacemakerAmmoResult.isMixedTech());
        assertTrue(actualCreatePeacemakerAmmoResult.isHittable());
        assertFalse(actualCreatePeacemakerAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreatePeacemakerAmmoResult.getToHitModifier());
        assertEquals(4, actualCreatePeacemakerAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreatePeacemakerAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreatePeacemakerAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreatePeacemakerAmmoResult.getStandardRange());
        assertEquals("", actualCreatePeacemakerAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreatePeacemakerAmmoResult.getReintroductionDate());
        assertEquals(4.0E7, actualCreatePeacemakerAmmoResult.getRawCost(), 0.0);
        assertEquals("Peacemaker Ammo", actualCreatePeacemakerAmmoResult.getName());
        assertEquals("Ammo Peacemaker", actualCreatePeacemakerAmmoResult.getInternalName());
        assertEquals("E/F-F-F-F", actualCreatePeacemakerAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreatePeacemakerAmmoResult.flags;
        assertSame(expectedFlags, actualCreatePeacemakerAmmoResult.getFlags());
    }

    private void createAR10SantaAnnaAmmo(AmmoType actualCreateAR10SantaAnnaAmmoResult) {
        assertEquals(40.0, actualCreateAR10SantaAnnaAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateAR10SantaAnnaAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateAR10SantaAnnaAmmoResult.svslots);
        assertEquals("Santa Anna", actualCreateAR10SantaAnnaAmmoResult.shortName);
        assertNull(actualCreateAR10SantaAnnaAmmoResult.modes);
        assertTrue(actualCreateAR10SantaAnnaAmmoResult.explosive);
        assertEquals(1, actualCreateAR10SantaAnnaAmmoResult.criticals);
        assertEquals(1000.0, actualCreateAR10SantaAnnaAmmoResult.bv, 0.0);
        assertFalse(actualCreateAR10SantaAnnaAmmoResult.isSpreadable());
        assertFalse(actualCreateAR10SantaAnnaAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateAR10SantaAnnaAmmoResult.isMixedTech());
        assertTrue(actualCreateAR10SantaAnnaAmmoResult.isHittable());
        assertFalse(actualCreateAR10SantaAnnaAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateAR10SantaAnnaAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateAR10SantaAnnaAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateAR10SantaAnnaAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateAR10SantaAnnaAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateAR10SantaAnnaAmmoResult.getStandardRange());
        assertEquals("", actualCreateAR10SantaAnnaAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateAR10SantaAnnaAmmoResult.getReintroductionDate());
        assertEquals(1.5E7, actualCreateAR10SantaAnnaAmmoResult.getRawCost(), 0.0);
        assertEquals("AR10 Santa Anna Ammo", actualCreateAR10SantaAnnaAmmoResult.getName());
        assertEquals("Ammo AR10 Santa Anna", actualCreateAR10SantaAnnaAmmoResult.getInternalName());
        assertEquals("E/F-F-F-F", actualCreateAR10SantaAnnaAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateAR10SantaAnnaAmmoResult.flags;
        assertSame(expectedFlags, actualCreateAR10SantaAnnaAmmoResult.getFlags());
    }

    private void createSantaAnnaAmmo(AmmoType actualCreateSantaAnnaAmmoResult) {
        assertEquals(40.0, actualCreateSantaAnnaAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateSantaAnnaAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateSantaAnnaAmmoResult.svslots);
        assertEquals("Santa Anna", actualCreateSantaAnnaAmmoResult.shortName);
        assertNull(actualCreateSantaAnnaAmmoResult.modes);
        assertTrue(actualCreateSantaAnnaAmmoResult.explosive);
        assertEquals(1, actualCreateSantaAnnaAmmoResult.criticals);
        assertEquals(1000.0, actualCreateSantaAnnaAmmoResult.bv, 0.0);
        assertFalse(actualCreateSantaAnnaAmmoResult.isSpreadable());
        assertFalse(actualCreateSantaAnnaAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateSantaAnnaAmmoResult.isMixedTech());
        assertTrue(actualCreateSantaAnnaAmmoResult.isHittable());
        assertFalse(actualCreateSantaAnnaAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateSantaAnnaAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateSantaAnnaAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateSantaAnnaAmmoResult.getSubType());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateSantaAnnaAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateSantaAnnaAmmoResult.getStandardRange());
        assertEquals("", actualCreateSantaAnnaAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateSantaAnnaAmmoResult.getReintroductionDate());
        assertEquals(1.5E7, actualCreateSantaAnnaAmmoResult.getRawCost(), 0.0);
        assertEquals("Santa Anna Ammo", actualCreateSantaAnnaAmmoResult.getName());
        assertEquals("Ammo Santa Anna", actualCreateSantaAnnaAmmoResult.getInternalName());
        assertEquals("E/F-F-F-F", actualCreateSantaAnnaAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateSantaAnnaAmmoResult.flags;
        assertSame(expectedFlags, actualCreateSantaAnnaAmmoResult.getFlags());
    }

    private void createAlamoAmmo(AmmoType actualCreateAlamoAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateAlamoAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateAlamoAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateAlamoAmmoResult.svslots);
        assertEquals("", actualCreateAlamoAmmoResult.shortName);
        assertNull(actualCreateAlamoAmmoResult.modes);
        assertTrue(actualCreateAlamoAmmoResult.explosive);
        assertEquals(1, actualCreateAlamoAmmoResult.criticals);
        assertEquals(100.0, actualCreateAlamoAmmoResult.bv, 0.0);
        assertFalse(actualCreateAlamoAmmoResult.isSpreadable());
        assertFalse(actualCreateAlamoAmmoResult.isOmniFixedOnly());
        assertFalse(actualCreateAlamoAmmoResult.isMixedTech());
        assertTrue(actualCreateAlamoAmmoResult.isHittable());
        assertFalse(actualCreateAlamoAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateAlamoAmmoResult.getToHitModifier());
        assertEquals(4, actualCreateAlamoAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateAlamoAmmoResult.getSubType());
        assertEquals("", actualCreateAlamoAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.EXPERIMENTAL, actualCreateAlamoAmmoResult.getStaticTechLevel());
        assertEquals("-", actualCreateAlamoAmmoResult.getStandardRange());
        assertEquals("", actualCreateAlamoAmmoResult.getRulesRefs());
        assertEquals(AmmoType.T_NA, actualCreateAlamoAmmoResult.getReintroductionDate());
        assertEquals(1000000.0, actualCreateAlamoAmmoResult.getRawCost(), 0.0);
        assertEquals("Ammo Alamo", actualCreateAlamoAmmoResult.getInternalName());
        assertEquals("E/F-F-F-F", actualCreateAlamoAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateAlamoAmmoResult.flags;
        assertSame(expectedFlags, actualCreateAlamoAmmoResult.getFlags());
    }

    private void createInfantryAmmo(AmmoType actualCreateInfantryAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateInfantryAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateInfantryAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateInfantryAmmoResult.svslots);
        assertEquals("", actualCreateInfantryAmmoResult.shortName);
        assertNull(actualCreateInfantryAmmoResult.modes);
        assertTrue(actualCreateInfantryAmmoResult.explosive);
        assertEquals(1, actualCreateInfantryAmmoResult.criticals);
        assertEquals(0.0, actualCreateInfantryAmmoResult.bv, 0.0);
        assertFalse(actualCreateInfantryAmmoResult.isSpreadable());
        assertFalse(actualCreateInfantryAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateInfantryAmmoResult.isMixedTech());
        assertTrue(actualCreateInfantryAmmoResult.isHittable());
        assertFalse(actualCreateInfantryAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateInfantryAmmoResult.getToHitModifier());
        assertEquals(0, actualCreateInfantryAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateInfantryAmmoResult.getSubType());
        assertEquals("", actualCreateInfantryAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateInfantryAmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateInfantryAmmoResult.getRulesRefs());
        assertEquals(0.0, actualCreateInfantryAmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_PS, actualCreateInfantryAmmoResult.getPrototypeDate());
        assertEquals(EquipmentTypeLookup.INFANTRY_AMMO, actualCreateInfantryAmmoResult.getInternalName());
        assertEquals("A/A-A-A-A", actualCreateInfantryAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateInfantryAmmoResult.flags;
        assertSame(expectedFlags, actualCreateInfantryAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateInfantryAmmoResult.getExtinctionDate());
    }

    private void createInfantryInfernoAmmo(AmmoType actualCreateInfantryInfernoAmmoResult) {
        assertEquals(EquipmentType.POINT_MULTIPLIER_UNKNOWN, actualCreateInfantryInfernoAmmoResult.tonnage, 0.0);
        assertEquals(0, actualCreateInfantryInfernoAmmoResult.tankslots);
        assertEquals(AmmoType.T_NA, actualCreateInfantryInfernoAmmoResult.svslots);
        assertEquals("", actualCreateInfantryInfernoAmmoResult.shortName);
        assertNull(actualCreateInfantryInfernoAmmoResult.modes);
        assertTrue(actualCreateInfantryInfernoAmmoResult.explosive);
        assertEquals(1, actualCreateInfantryInfernoAmmoResult.criticals);
        assertEquals(0.0, actualCreateInfantryInfernoAmmoResult.bv, 0.0);
        assertFalse(actualCreateInfantryInfernoAmmoResult.isSpreadable());
        assertFalse(actualCreateInfantryInfernoAmmoResult.isOmniFixedOnly());
        assertTrue(actualCreateInfantryInfernoAmmoResult.isMixedTech());
        assertTrue(actualCreateInfantryInfernoAmmoResult.isHittable());
        assertFalse(actualCreateInfantryInfernoAmmoResult.hasInstantModeSwitch());
        assertEquals(0, actualCreateInfantryInfernoAmmoResult.getToHitModifier());
        assertEquals(0, actualCreateInfantryInfernoAmmoResult.getTechRating());
        assertEquals(AmmoType.M_STANDARD, actualCreateInfantryInfernoAmmoResult.getSubType());
        assertEquals("Inferno", actualCreateInfantryInfernoAmmoResult.getSubMunitionName());
        assertEquals(SimpleTechLevel.STANDARD, actualCreateInfantryInfernoAmmoResult.getStaticTechLevel());
        assertEquals("", actualCreateInfantryInfernoAmmoResult.getRulesRefs());
        assertEquals(0.0, actualCreateInfantryInfernoAmmoResult.getRawCost(), 0.0);
        assertEquals(ITechnology.DATE_PS, actualCreateInfantryInfernoAmmoResult.getPrototypeDate());
        assertEquals(EquipmentTypeLookup.INFANTRY_INFERNO_AMMO, actualCreateInfantryInfernoAmmoResult.getInternalName());
        assertEquals("A/A-A-A-A", actualCreateInfantryInfernoAmmoResult.getFullRatingName());
        BigInteger expectedFlags = actualCreateInfantryInfernoAmmoResult.flags;
        assertSame(expectedFlags, actualCreateInfantryInfernoAmmoResult.getFlags());
        assertEquals(AmmoType.T_NA, actualCreateInfantryInfernoAmmoResult.getExtinctionDate());
    }
}
