public class Quartic extends Formula
   {
   
   public Quartic( Double[] x, Double[] y )
      {
      super( backSolve( new double[][] {
             { x.length, sigma( x ), sigma( x, 2.0 ), sigma( x, 3.0 ), sigma( x, 4.0 ), sigma( y ) },
	     { sigma( x ), sigma( x, 2.0 ), sigma( x, 3.0 ), sigma( x, 4.0 ), sigma( x, 5.0 ), sigma( x, y ) },
	     { sigma( x, 2.0 ), sigma( x, 3.0 ), sigma( x, 4.0 ), sigma( x, 5.0 ), sigma( x, 6.0 ), sigma( x, y, 2.0 ) },
	     { sigma( x, 3.0 ), sigma( x, 4.0 ), sigma( x, 5.0 ), sigma( x, 6.0 ), sigma( x, 7.0 ), sigma( x, y, 3.0 ) },
	     { sigma( x, 4.0 ), sigma( x, 5.0 ), sigma( x, 6.0 ), sigma( x, 7.0 ), sigma( x, 8.0 ), sigma( x, y, 4.0 ) } } ) );
      }

   public double getY( double x )
      {
      return Params[0] + Params[1] * x + Params[2] * x * x + Params[3] * x * x * x + Params[4] * x * x * x * x;
      }

   }
