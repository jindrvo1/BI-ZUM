package bi.zum.lab4;

public class BIZUMLab4 {

    public static void main(String[] args) {
        int n = 3;
         
        System.out.print("(:objects");
        
        for ( int i = 0; i < n*n-1; i++ ) {
            System.out.print(" p"+i);
        }
        
        for ( int i = 0; i < n; i++ ) {
            for ( int j = 0; j < n; j++ ) {
                System.out.print(" k"+i+j);
            }
        }
        
        System.out.print(")\n");
        System.out.print("(:init\n");
        
        for ( int i = 0; i < n; i++ ) {
            for ( int j = 0; j < n; j++ ) {
                if ( i == 0 && j == 0 ) {
                    System.out.println("(accessible k"+i+j+" k"+(i+1)+j+")");
                    System.out.println("(accessible k"+i+j+" k"+i+(j+1)+")");
                } else if ( i == n-1 && j == n-1) {
                    System.out.println("(accessible k"+i+j+" k"+(i-1)+j+")");
                    System.out.println("(accessible k"+i+j+" k"+i+(j-1)+")");
                } else if ( i == 0 && j == n-1 ) {
                    System.out.println("(accessible k"+i+j+" k"+(i+1)+j+")");
                    System.out.println("(accessible k"+i+j+" k"+i+(j-1)+")");
                } else if ( i == n-1 && j == 0 ) {
                    System.out.println("(accessible k"+i+j+" k"+(i-1)+j+")");
                    System.out.println("(accessible k"+i+j+" k"+i+(j+1)+")");
                } else if ( i == 0 ) {
                    System.out.println("(accessible k"+i+j+" k"+i+(j-1)+")");
                    System.out.println("(accessible k"+i+j+" k"+i+(j+1)+")");
                    System.out.println("(accessible k"+i+j+" k"+(i+1)+j+")");
                } else if ( j == 0 ) {
                    System.out.println("(accessible k"+i+j+" k"+(i-1)+j+")");
                    System.out.println("(accessible k"+i+j+" k"+(i+1)+j+")");
                    System.out.println("(accessible k"+i+j+" k"+i+(j+1)+")");
                } else if ( i == n-1 ) {
                    System.out.println("(accessible k"+i+j+" k"+(i-1)+j+")");
                    System.out.println("(accessible k"+i+j+" k"+i+(j+1)+")");
                    System.out.println("(accessible k"+i+j+" k"+i+(j-1)+")");
                } else if ( j == n-1 ) {
                    System.out.println("(accessible k"+i+j+" k"+(i-1)+j+")");
                    System.out.println("(accessible k"+i+j+" k"+(i+1)+j+")");
                    System.out.println("(accessible k"+i+j+" k"+i+(j-1)+")");
                } else {
                    System.out.println("(accessible k"+i+j+" k"+(i-1)+j+")");
                    System.out.println("(accessible k"+i+j+" k"+(i+1)+j+")");
                    System.out.println("(accessible k"+i+j+" k"+i+(j-1)+")");
                    System.out.println("(accessible k"+i+j+" k"+i+(j+1)+")");
                }
            }
        }
        System.out.println(")");
        
        System.out.println("(:goal (and");
        for ( int i = 0; i < n; i++ ) {
            for ( int j = 0; j < n; j++ ) {
                if ( i != n-1 || j != n-1 ) {
                    System.out.println("(at p"+(i*n+j)+" k"+i+j+")");
                }
            }
        }
        System.out.println(")");
        System.out.println(")");
    }
    
}
