package codigoProyecto.Personajes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaccionesTest {
    Matemático mat = new Matemático();
    Ingeniero ing = new Ingeniero();
    Físico fis = new Físico();
    Químico qui = new Químico();
    Filósofo fils = new Filósofo();
    Filólogo filg = new Filólogo();
    Poeta poe = new Poeta();
    Historiador hist = new Historiador();


    @Test
    void getRutaImagen() {
        assertEquals("/Imágenes/matemática.jpg", mat.getRutaImagen());
        assertEquals("/Imágenes/ingeniero.jpg", ing.getRutaImagen());
        assertEquals("/Imágenes/física.jpg", fis.getRutaImagen());
        assertEquals("/Imágenes/químico.jpg", qui.getRutaImagen());
        assertEquals("/Imágenes/filóloga.jpg", filg.getRutaImagen());
        assertEquals("/imágenes/filósofo.jpg", fils.getRutaImagen());
        assertEquals("/Imágenes/poeta.jpg", poe.getRutaImagen());
        assertEquals("/Imágenes/historiador.jpg", hist.getRutaImagen());
    }

    @Test
    void getHp() {
        assertEquals(80,mat.getHp());
        assertEquals(120,ing.getHp());
        assertEquals(35,fis.getHp());
        assertEquals(95,qui.getHp());
        assertEquals(110,fils.getHp());
        assertEquals(70,filg.getHp());
        assertEquals(90,poe.getHp());
        assertEquals(110,hist.getHp());
    }

    @Test
    void setHp() {
        mat.setHp(5);
        fis.setHp(5);
        ing.setHp(5);
        qui.setHp(5);
        fils.setHp(5);
        filg.setHp(5);
        poe.setHp(5);
        hist.setHp(5);

        assertEquals(5,mat.getHp());
        assertEquals(5,ing.getHp());
        assertEquals(5,fis.getHp());
        assertEquals(5,qui.getHp());
        assertEquals(5,fils.getHp());
        assertEquals(5,filg.getHp());
        assertEquals(5,poe.getHp());
        assertEquals(5,hist.getHp());
    }

    @Test
    void getAtaque() {
        assertEquals(20,mat.getAtaque());
        assertEquals(15,ing.getAtaque());
        assertEquals(60,fis.getAtaque());
        assertEquals(20,qui.getAtaque());
        assertEquals(35,fils.getAtaque());
        assertEquals(18,filg.getAtaque());
        assertEquals(12,poe.getAtaque());
        assertEquals(10,hist.getAtaque());
    }

    @Test
    void setAtaque() {
        mat.setAtaque(5);
        fis.setAtaque(5);
        ing.setAtaque(5);
        qui.setAtaque(5);
        fils.setAtaque(5);
        filg.setAtaque(5);
        poe.setAtaque(5);
        hist.setAtaque(5);

        assertEquals(5,mat.getAtaque());
        assertEquals(5,ing.getAtaque());
        assertEquals(5,fis.getAtaque());
        assertEquals(5,qui.getAtaque());
        assertEquals(5,fils.getAtaque());
        assertEquals(5,filg.getAtaque());
        assertEquals(5,poe.getAtaque());
        assertEquals(5,hist.getAtaque());
    }

    @Test
    void getDefensa() {
        assertEquals(5,mat.getDefensa());
        assertEquals(10,ing.getDefensa());
        assertEquals(16,fis.getDefensa());
        assertEquals(7,qui.getDefensa());
        assertEquals(7,fils.getDefensa());
        assertEquals(6,filg.getDefensa());
        assertEquals(8,poe.getDefensa());
        assertEquals(3,hist.getDefensa());
    }

    @Test
    void setDefensa() {
        mat.setDefensa(5);
        fis.setDefensa(5);
        ing.setDefensa(5);
        qui.setDefensa(5);
        fils.setDefensa(5);
        filg.setDefensa(5);
        poe.setDefensa(5);
        hist.setDefensa(5);

        assertEquals(5,mat.getDefensa());
        assertEquals(5,ing.getDefensa());
        assertEquals(5,fis.getDefensa());
        assertEquals(5,qui.getDefensa());
        assertEquals(5,fils.getDefensa());
        assertEquals(5,filg.getDefensa());
        assertEquals(5,poe.getDefensa());
        assertEquals(5,hist.getDefensa());
    }

    @Test
    void getRangoMovimiento() {
        assertEquals(3,mat.getRangoMovimiento());
        assertEquals(3,ing.getRangoMovimiento());
        assertEquals(2,fis.getRangoMovimiento());
        assertEquals(1,qui.getRangoMovimiento());
        assertEquals(1,fils.getRangoMovimiento());
        assertEquals(2,filg.getRangoMovimiento());
        assertEquals(3,poe.getRangoMovimiento());
        assertEquals(2,hist.getRangoMovimiento());
    }

    @Test
    void setRangoMovimiento() {
        mat.setRangoMovimiento(5);
        fis.setRangoMovimiento(5);
        ing.setRangoMovimiento(5);
        qui.setRangoMovimiento(5);
        fils.setRangoMovimiento(5);
        filg.setRangoMovimiento(5);
        poe.setRangoMovimiento(5);
        hist.setRangoMovimiento(5);

        assertEquals(5,mat.getRangoMovimiento());
        assertEquals(5,ing.getRangoMovimiento());
        assertEquals(5,fis.getRangoMovimiento());
        assertEquals(5,qui.getRangoMovimiento());
        assertEquals(5,fils.getRangoMovimiento());
        assertEquals(5,filg.getRangoMovimiento());
        assertEquals(5,poe.getRangoMovimiento());
        assertEquals(5,hist.getRangoMovimiento());
    }

    @Test
    void getRangoAtaque() {
        assertEquals(3,mat.getRangoAtaque());
        assertEquals(2,ing.getRangoAtaque());
        assertEquals(3,fis.getRangoAtaque());
        assertEquals(5,qui.getRangoAtaque());
        assertEquals(5,fils.getRangoAtaque());
        assertEquals(4,filg.getRangoAtaque());
        assertEquals(2,poe.getRangoAtaque());
        assertEquals(2,hist.getRangoAtaque());
    }

    @Test
    void setRangoAtaque() {
        mat.setRangoAtaque(5);
        fis.setRangoAtaque(5);
        ing.setRangoAtaque(5);
        qui.setRangoAtaque(5);
        fils.setRangoAtaque(5);
        filg.setRangoAtaque(5);
        poe.setRangoAtaque(5);
        hist.setRangoAtaque(5);

        assertEquals(5,mat.getRangoAtaque());
        assertEquals(5,ing.getRangoAtaque());
        assertEquals(5,fis.getRangoAtaque());
        assertEquals(5,qui.getRangoAtaque());
        assertEquals(5,fils.getRangoAtaque());
        assertEquals(5,filg.getRangoAtaque());
        assertEquals(5,poe.getRangoAtaque());
        assertEquals(5,hist.getRangoAtaque());
    }



    @Test
    void getNombre() {
        assertEquals("Matemático",mat.getNombre());
        assertEquals("Ingeniero",ing.getNombre());
        assertEquals("Físico",fis.getNombre());
        assertEquals("Químico",qui.getNombre());
        assertEquals("Filósofo",fils.getNombre());
        assertEquals("Filólogo",filg.getNombre());
        assertEquals("Poeta",poe.getNombre());
        assertEquals("Historiador",hist.getNombre());
    }


    @Test
    void isHabilidadEspecialUsada() {
        mat.setHabilidadEspecialUsada(true);
        ing.setHabilidadEspecialUsada(true);
        fis.setHabilidadEspecialUsada(true);
        qui.setHabilidadEspecialUsada(true);
        fils.setHabilidadEspecialUsada(true);
        filg.setHabilidadEspecialUsada(true);
        poe.setHabilidadEspecialUsada(true);
        hist.setHabilidadEspecialUsada(true);
        assertTrue(mat.isHabilidadEspecialUsada());
        assertTrue(fis.isHabilidadEspecialUsada());
        assertTrue(ing.isHabilidadEspecialUsada());
        assertTrue(qui.isHabilidadEspecialUsada());
        assertTrue(hist.isHabilidadEspecialUsada());
        assertTrue(fils.isHabilidadEspecialUsada());
        assertTrue(filg.isHabilidadEspecialUsada());
        assertTrue(poe.isHabilidadEspecialUsada());
    }

    @Test
    void isHabilidadGeneralUsada() {
        mat.setHabilidadGeneralUsada(true);
        ing.setHabilidadGeneralUsada(true);
        fis.setHabilidadGeneralUsada(true);
        qui.setHabilidadGeneralUsada(true);
        fils.setHabilidadGeneralUsada(true);
        filg.setHabilidadGeneralUsada(true);
        poe.setHabilidadGeneralUsada(true);
        hist.setHabilidadGeneralUsada(true);
        assertTrue(mat.isHabilidadGeneralUsada());
        assertTrue(fis.isHabilidadGeneralUsada());
        assertTrue(ing.isHabilidadGeneralUsada());
        assertTrue(qui.isHabilidadGeneralUsada());
        assertTrue(hist.isHabilidadGeneralUsada());
        assertTrue(fils.isHabilidadGeneralUsada());
        assertTrue(filg.isHabilidadGeneralUsada());
        assertTrue(poe.isHabilidadGeneralUsada());
    }


}