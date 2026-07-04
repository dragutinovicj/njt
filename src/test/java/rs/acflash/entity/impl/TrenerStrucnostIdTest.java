package rs.acflash.entity.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TrenerStrucnostIdTest {

    @Test
    void testGetteri() {
        TrenerStrucnostId id = new TrenerStrucnostId(1L, 2L);

        assertEquals(1L, id.getIdTrener());
        assertEquals(2L, id.getIdStrucnost());
    }

    @Test
    void testEqualsIstiObjekat() {
        TrenerStrucnostId id = new TrenerStrucnostId(1L, 2L);

        assertTrue(id.equals(id));
    }

    @Test
    void testEqualsJednakeVrednosti() {
        TrenerStrucnostId id1 = new TrenerStrucnostId(1L, 2L);
        TrenerStrucnostId id2 = new TrenerStrucnostId(1L, 2L);

        assertTrue(id1.equals(id2));
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    void testEqualsRazliciteVrednosti() {
        TrenerStrucnostId id1 = new TrenerStrucnostId(1L, 2L);
        TrenerStrucnostId id2 = new TrenerStrucnostId(3L, 4L);

        assertNotEquals(id1, id2);
    }

    @Test
    void testEqualsSaNull() {
        TrenerStrucnostId id = new TrenerStrucnostId(1L, 2L);

        assertFalse(id.equals(null));
    }

    @Test
    void testEqualsSaDrugimTipom() {
        TrenerStrucnostId id = new TrenerStrucnostId(1L, 2L);

        assertFalse(id.equals("nije isti tip"));
    }
}
