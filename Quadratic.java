public class Quadratic extends Formula
   {

   public Quadratic( Double[] x, Double[] y )
      {
      super( backSolve( new double[][] {
          { x.length, sigma( x ), sigma( x, 2.0 ), sigma( y ) },
          { sigma( x ), sigma( x, 2.0 ), sigma( x, 3.0 ), sigma( x, y ) },
	  { sigma( x, 2.0 ), sigma( x, 3.0 ), sigma( x, 4.0 ), sigma( x, y, 2.0 ) } } ) );
      }

   public double getY( double x )
      {
      return Params[0] + Params[1] * x + Params[2] * x * x;
      }


   }


