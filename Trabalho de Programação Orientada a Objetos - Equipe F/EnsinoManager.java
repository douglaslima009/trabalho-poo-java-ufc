import View.*;
import Model.*;

public class EnsinoManager {
   public static void main(String[] args){
        Model model = Model.getInstanciaModel();
        EnsinoManagerView view = new EnsinoManagerView();
        view.iniciarEnsinoManagerView(model);
   } 
}