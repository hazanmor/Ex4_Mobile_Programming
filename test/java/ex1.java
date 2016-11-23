/**
 * Created by barlesh on 07/11/16.
 */

class outer_class {

    public class inner_public_Class {

        int first;
        String Seconed;

        public void print(){
            System.out.println("innet_public print");
        }

    }

    //member class of outer class. private class so it is only accassiable using outer calss's methods
    private class inner_private_class {
        void print(){
            System.out.println("inner_private class print");
        }

    }

    public void print_inner_private(){
        inner_private_class ipc = new inner_private_class();
        ipc.print();
    }


}//outer


class singeltone{
    int val ;
    private static singeltone instance = null;
    private singeltone(int val) {
        this.val = val;
        // Exists only to defeat instantiation.
    }

    public static singeltone getInstance(int val) {
        if(instance == null) {
            instance = new singeltone(val);
        }
        return instance;
    }

    public void print(){
        System.out.println("singeltine print. val is:" + val);
    }
}




public class ex1 {


    public static void main(String[] args){

        /*#####################
        ########  1   #########
        #######################*/
        //create instance of outer class, which contain inner class
        outer_class OC = new outer_class();
        //can init inner public class
        outer_class.inner_public_Class IPC = OC.new inner_public_Class();
        //and print it
        IPC.print();
        //but cant initialize inner private class (compiling error)
        //outer_class.inner_prvate_Class IPC2 = OC.new inner_private_Class();
        //but i can create and print it using outer calss
        OC.print_inner_private();



        /*#####################
        ########  2   #########
        #######################*/
        //create instance of Anonymous class (sub calss of inner public class)
        outer_class.inner_public_Class IPCA = OC.new inner_public_Class() {
            public void print(){
                System.out.println("inner_public_anonymous print");
            }

        };
        //now print it
        IPCA.print();


        /*#####################
        ########  3   #########
        #######################*/
        //create instance of singeltone class
        singeltone tmp = singeltone.getInstance( 6);
        //print instance
        tmp.print();
        //now, try to get another instance of singeltone
        singeltone tmp2 = singeltone.getInstance( 10);
        //and print it, but it prints 6, so this instance is the same as the first one
        tmp2.print();


    }

}
