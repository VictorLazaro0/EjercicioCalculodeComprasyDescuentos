public class PurchaseCalculator {


    public double calculateSubtotal(double price, int quantity){
        double subtotal = price * quantity;
        return subtotal;
    } // calculateSubtotal


    public boolean isValidPurchase(double price, int quantity) {
        if(price > 0 &&  quantity > 0) {
            return true;
        }
        return false ;
    }//isValidPurchase
   public double calculateDiscount(double subtotal){
        if(subtotal >= 1000){
            return subtotal * 0.10;
        }

    return 0.0;
    }// class calculateDiscount

   public  double calculateFinalPrice(double subtotal){

        return subtotal - calculateDiscount(subtotal);
   }// class calculateFinalPrice

}// Class PurchaseCalculator
