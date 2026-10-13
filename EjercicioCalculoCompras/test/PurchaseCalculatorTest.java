import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseCalculatorTest {
PurchaseCalculator pc = new PurchaseCalculator();
    @Test
    @DisplayName("Cálculo del subtotal")
    public void subtotal(){
        //Un producto con precio de $100 y cantidad de 3.
        assertEquals(300.0, pc.calculateSubtotal(100.0,3),0.001);
        //Un producto con cantidad de 1.
        assertEquals(0.0, pc.calculateSubtotal(0.0,1));
        //Un producto con precio de $0.
        assertEquals(0.0,pc.calculateSubtotal(30.0,0));
    }

    @Test
    @DisplayName("Validación de la compra")
    public void compra(){
        //Precio y cantidad positivos.
        assertTrue(pc.isValidPurchase(100.0,20));
        //Precio igual a cero.
        assertFalse(pc.isValidPurchase(0.0,20));
        //Cantidad igual a cero.
        assertFalse(pc.isValidPurchase(10.0,0));
        //Precio negativo.
        assertFalse(pc.isValidPurchase(-100.0,5));
    }

    @Test
    @DisplayName("Cálculo del descuento")
    public void descuento(){
        //Subtotal inferior a $1,000.
        assertEquals(0.0,pc.calculateDiscount(4.50));
        //subtotal exactamente igual a $1,000.
        assertEquals(100,pc.calculateDiscount(1000.0));
        //Subtotal superior a $1,000.
        assertEquals(110.0,pc.calculateDiscount(1100.0));

    }//descuento
    @Test
    @DisplayName("Cálculo  precio final ")
    public void precioFinal(){
        //Compra sin descuento.
        assertEquals(50.0,pc.calculateFinalPrice(50.0));
        //Compra con descuento
        assertEquals(900.0,pc.calculateFinalPrice(1000.0));

    }//Precio Final


}//PurchaseCalculatorTest
